package com.playtech.cm.selenium.campaigns.actions;

import com.playtech.cm.BaseTest;
import com.playtech.cm.db.DbUnitUtil;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignDetailsPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignTabs;
import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.pages.dashboard.campaign.activities.actions.ActionsPage;
import com.playtech.cm.pages.dashboard.campaign.activities.actions.ImsBonusPage;
import com.playtech.cm.pages.dashboard.campaign.activities.actions.TestGroupsPage;
import com.playtech.cm.pages.dashboard.campaign.activities.details.ActivityDetailsPage;
import com.playtech.cm.utils.Config;
import com.playtech.cm.utils.dataProviders.CampaignData;
import com.playtech.cm.utils.entities.actions.ImsBonusEntity;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * User: Denis Veselovskiy
 * Date: 12/5/12, Time: 4:17 PM
 */
public class ImsBonusTestActivityTest extends BaseTest {
    CampaignData campaignData;
    String activityName;
    String activityID;
    ImsBonusEntity expectedImsBonusEntity;
    ImsBonusEntity actualImsBonusEntity;
    CMDashboardPage cmDashboardPage;
    CampaignDetailsPage campaignDetailsPage;
    CampaignTabs campaignTabs;
    ActivityTabPage activityTab;
    ActionsPage actionsPage;
    ActivityDetailsPage activityDetailsPage;
    TestGroupsPage testGroupsPage;
    String testGroupToTest = "(35%) Test Group 1";
    ImsBonusPage imsBonusPage;
    String existingTemplate = "cm_automation_test1";

    @BeforeMethod
    public void createNewCampaign() {
        //GIVEN
        DbUnitUtil.clean("demodb-jdbc.properties");
        campaignData = new CampaignData();
        cmDashboardPage = goToCMDashboardDirectly();
        campaignDetailsPage = cmDashboardPage.createCampaign(campaignData);
        campaignTabs = campaignDetailsPage.clickSave();
        activityTab = campaignTabs.openActivityTab();
        activityName = activityTab.getOpenedDetails().getActivityName();
        activityID = activityTab.getOpenedDetails().getActivityID();
        actionsPage = activityTab.clickActions();
        testGroupsPage = actionsPage.clickTestYes();
        expectedImsBonusEntity = new ImsBonusEntity();
        actualImsBonusEntity = new ImsBonusEntity();
    }

    @AfterMethod
    public void saveFailedDataAndLogOut(ITestContext c, ITestResult r, Method m){
        super.saveFailedDataAndLogOut(c,r,m);
        if (!r.isSuccess()){
            Config.killAllFirefoxApplications();
        }
    }

    @Test
    public void clickCancelTest() {
        expectedImsBonusEntity.setSelectedTemplateName(existingTemplate);
        expectedImsBonusEntity.setAvailableTemplates(new ArrayList<String>());
        //WHEN
        imsBonusPage = (ImsBonusPage) testGroupsPage.addAction(testGroupToTest, expectedImsBonusEntity.getAction());
        imsBonusPage.selectBonusTemplate(expectedImsBonusEntity.getSelectedTemplateName());
        testGroupsPage = (TestGroupsPage) imsBonusPage.clickCancel(true);
        //THEN
        assertTrue(testGroupsPage.getAction(testGroupToTest).equals("Add Action"), "IMS Bonus is in the Actions table after pressing Cancel button on IMS Bonus form.");
        campaignDetailsPage = testGroupsPage.clickSave();
        activityTab = campaignTabs.openActivityTab();
        testGroupsPage = activityTab.clickActionsWithTestGroups();
        assertTrue(testGroupsPage.getAction(testGroupToTest).equals("Add Action"), "IMS Bonus is in the Actions table after pressing Cancel button on IMS Bonus form and saving Campaign.");
    }

    @Test
    public void saveBonusTest() {
        expectedImsBonusEntity.setSearchFieldText(existingTemplate);
        expectedImsBonusEntity.setSelectedTemplateName(existingTemplate);
        expectedImsBonusEntity.setAvailableTemplates(Arrays.asList(existingTemplate));
        expectedImsBonusEntity.setSourceForBonusCalcAmount("No amount required");
        //WHEN
        imsBonusPage = (ImsBonusPage) testGroupsPage.addAction(testGroupToTest, expectedImsBonusEntity.getAction());
        imsBonusPage.setAllFields(expectedImsBonusEntity);
        testGroupsPage = (TestGroupsPage) imsBonusPage.clickOk(true);
        campaignDetailsPage = testGroupsPage.clickSave();
        activityTab = campaignDetailsPage.openActivityTab();
        testGroupsPage = activityTab.clickActionsWithTestGroups();
        //THEN
        assertEquals(expectedImsBonusEntity.getAction(), testGroupsPage.getAction(testGroupToTest));
        assertEquals(expectedImsBonusEntity.getSelectedTemplateName()+" - Amount Source: " + expectedImsBonusEntity.getSourceForBonusCalcAmount(), testGroupsPage.getSummary(testGroupToTest));
        activityTab = campaignTabs.openActivityTab();
        testGroupsPage = activityTab.clickActionsWithTestGroups();
        imsBonusPage = (ImsBonusPage) testGroupsPage.clickEdit(testGroupToTest);
        actualImsBonusEntity = imsBonusPage.getAllFields();
        assertObjectsEquals(expectedImsBonusEntity, actualImsBonusEntity);
    }

    @Test
    public void deleteBonusTest() {
        expectedImsBonusEntity.setSearchFieldText(existingTemplate);
        expectedImsBonusEntity.setSelectedTemplateName(existingTemplate);
        expectedImsBonusEntity.setAvailableTemplates(Arrays.asList(existingTemplate));
        expectedImsBonusEntity.setSourceForBonusCalcAmount("No amount required");
        //WHEN
        imsBonusPage = (ImsBonusPage) testGroupsPage.addAction(testGroupToTest, expectedImsBonusEntity.getAction());
        imsBonusPage.setAllFields(expectedImsBonusEntity);
        testGroupsPage = (TestGroupsPage)imsBonusPage.clickOk(true);
        campaignDetailsPage = testGroupsPage.clickSave();
        activityTab = campaignDetailsPage.openActivityTab();
        testGroupsPage = activityTab.clickActionsWithTestGroups();
        testGroupsPage.clickDelete(testGroupToTest);
        //THEN
        assertTrue(testGroupsPage.getAction(testGroupToTest).equals("Add Action"), "IMS Bonus is not disappeared from test group after deleting.");
        campaignDetailsPage = testGroupsPage.clickSave();
        activityTab = campaignDetailsPage.openActivityTab();
        testGroupsPage = activityTab.clickActionsWithTestGroups();
        assertTrue(testGroupsPage.getAction(testGroupToTest).equals("Add Action"),"IMS Bonus is not disappeared from test group after deleting.");
    }

    @Test
    public void editBonusTest() {
        expectedImsBonusEntity.setSearchFieldText(existingTemplate);
        expectedImsBonusEntity.setSelectedTemplateName(existingTemplate);
        expectedImsBonusEntity.setAvailableTemplates(Arrays.asList(existingTemplate));
        expectedImsBonusEntity.setSourceForBonusCalcAmount("No amount required");
        //WHEN
        imsBonusPage = (ImsBonusPage) testGroupsPage.addAction(testGroupToTest,expectedImsBonusEntity.getAction());
        imsBonusPage.setAllFields(expectedImsBonusEntity);
        testGroupsPage = (TestGroupsPage) imsBonusPage.clickOk(true);
        campaignDetailsPage = testGroupsPage.clickSave();

        expectedImsBonusEntity.setSearchFieldText(existingTemplate);
        expectedImsBonusEntity.setSelectedTemplateName(existingTemplate);
        expectedImsBonusEntity.setAvailableTemplates(Arrays.asList(existingTemplate));
        expectedImsBonusEntity.setSourceForBonusCalcAmount("No amount required");

        activityTab = campaignDetailsPage.openActivityTab();
        testGroupsPage = activityTab.clickActionsWithTestGroups();
        imsBonusPage = (ImsBonusPage) testGroupsPage.clickEdit(testGroupToTest);
        imsBonusPage.setAllFields(expectedImsBonusEntity);
        testGroupsPage = (TestGroupsPage) imsBonusPage.clickOk(true);
        campaignDetailsPage = testGroupsPage.clickSave();

        activityTab = campaignDetailsPage.openActivityTab();
        testGroupsPage = activityTab.clickActionsWithTestGroups();
        //THEN
        assertEquals(testGroupsPage.getAction(testGroupToTest), expectedImsBonusEntity.getAction());
        assertEquals(testGroupsPage.getSummary(testGroupToTest),expectedImsBonusEntity.getSelectedTemplateName()+" - Amount Source: " + expectedImsBonusEntity.getSourceForBonusCalcAmount());
        imsBonusPage = (ImsBonusPage) testGroupsPage.clickEdit(testGroupToTest);
        actualImsBonusEntity = imsBonusPage.getAllFields();
        assertObjectsEquals(expectedImsBonusEntity, actualImsBonusEntity);
    }
}
