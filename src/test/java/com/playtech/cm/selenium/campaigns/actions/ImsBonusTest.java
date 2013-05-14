package com.playtech.cm.selenium.campaigns.actions;

import com.playtech.cm.BaseTest;
import com.playtech.cm.db.DbUnitUtil;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignDetailsPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignTabs;
import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.pages.dashboard.campaign.activities.actions.ActionsPage;
import com.playtech.cm.pages.dashboard.campaign.activities.actions.ImsBonusPage;
import com.playtech.cm.utils.Config;
import com.playtech.cm.utils.dataProviders.CampaignData;
import com.playtech.cm.utils.entities.actions.ImsBonusEntity;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * User: Denis Veselovskiy
 * Date: 10/29/12 * Time: 2:45 PM
 */
public class ImsBonusTest extends BaseTest {

    CampaignData campaignData;
    String activityName;
    String activityID;
    ImsBonusEntity expectedImsBonusEntity;
    ImsBonusEntity actualImsBonusEntity;
    CMDashboardPage cmDashboardPage;
    CampaignDetailsPage campaignDetailsPage;
    CampaignTabs campaignTabs;
    ActivityTabPage activityTab;
    ActionsPage actionSectionPage;
    ImsBonusPage imsBonusPage;
    String noTemplateError = "Bonus Template should not be empty.";
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
        actionSectionPage = (ActionsPage) activityTab.clickActions();
        expectedImsBonusEntity = new ImsBonusEntity();
        actualImsBonusEntity = new ImsBonusEntity();
    }

//    @AfterMethod
//    public void saveFailedDataAndLogOut(ITestContext c, ITestResult r, Method m) {
//        super.saveFailedDataAndLogOut(c, r, m);
//        if (!r.isSuccess()) {
//            Config.killAllFirefoxApplications();
//        }
//    }

    @Test
    public void leaveSearchFormEmpty() {
        //WHEN
        imsBonusPage = (ImsBonusPage) actionSectionPage.addAction(expectedImsBonusEntity.getAction());
        imsBonusPage.clickOk(false);
        actualImsBonusEntity = imsBonusPage.getAllFields();
        //THEN
        assertTrue(imsBonusPage.isErrorPresent(noTemplateError), "No error when trying to add Action - IMS Bonus. Bonus Template Name is not selected.");
    }

    @Test
    public void enterNonExistingTemplateName() {
        expectedImsBonusEntity.setSearchFieldText("This template name is a falsehood");
        expectedImsBonusEntity.setAvailableTemplates(new ArrayList<String>());
        //WHEN
        imsBonusPage = (ImsBonusPage) actionSectionPage.addAction(expectedImsBonusEntity.getAction());
        imsBonusPage.typeBonusTemplateNameToFilter(expectedImsBonusEntity.getSearchFieldText());
        imsBonusPage.clickOk(false);
        //THEN
        actualImsBonusEntity = imsBonusPage.getAllFields();
        assertObjectsEquals(expectedImsBonusEntity, actualImsBonusEntity);
        assertTrue(imsBonusPage.isErrorPresent(noTemplateError), "No error when trying to add Action - IMS Bonus. Bonus Template Name is not selected.");
    }

    @Test
    public void searchBonusTemplateSpecSymbol1Test() {
        System.out.println("BUG_ID: PRO-474");
        expectedImsBonusEntity.setSearchFieldText("%");
        expectedImsBonusEntity.setAvailableTemplates(new ArrayList<String>());
        //WHEN
        imsBonusPage = (ImsBonusPage) actionSectionPage.addAction(expectedImsBonusEntity.getAction());
        imsBonusPage.typeBonusTemplateNameToFilter(expectedImsBonusEntity.getSearchFieldText());
        actualImsBonusEntity = imsBonusPage.getAllFields();
        //THEN
        assertObjectsEquals(expectedImsBonusEntity, actualImsBonusEntity);
    }


    @Test
    public void searchBonusTemplateSpecSymbol2Test() {
        expectedImsBonusEntity.setSearchFieldText("?");
        expectedImsBonusEntity.setAvailableTemplates(new ArrayList<String>());
        //WHEN
        imsBonusPage = (ImsBonusPage) actionSectionPage.addAction(expectedImsBonusEntity.getAction());
        imsBonusPage.typeBonusTemplateNameToFilter(expectedImsBonusEntity.getSearchFieldText());
        actualImsBonusEntity = imsBonusPage.getAllFields();
        //THEN
        assertObjectsEquals(expectedImsBonusEntity, actualImsBonusEntity);
    }

    @Test
    public void searchBonusTemplateSpecSymbol3Test() {
        expectedImsBonusEntity.setSearchFieldText("*");
        List expectedAvailableTemplatesList = new ArrayList();
        expectedAvailableTemplatesList.add(":!@#$%^&*()_.");
        expectedImsBonusEntity.setAvailableTemplates(expectedAvailableTemplatesList);
        //WHEN
        imsBonusPage = (ImsBonusPage) actionSectionPage.addAction(expectedImsBonusEntity.getAction());
        imsBonusPage.typeBonusTemplateNameToFilter(expectedImsBonusEntity.getSearchFieldText());
        actualImsBonusEntity = imsBonusPage.getAllFields();
        //THEN
        assertObjectsEquals(expectedImsBonusEntity, actualImsBonusEntity);
    }

    @Test
    public void searchBonusTemplateSpecSymbol4Test() {
        System.out.println("BUG_ID: PRO-474");
        expectedImsBonusEntity.setSearchFieldText("_");
        expectedImsBonusEntity.setAvailableTemplates(new ArrayList<String>());
        //WHEN
        imsBonusPage = (ImsBonusPage) actionSectionPage.addAction(expectedImsBonusEntity.getAction());
        imsBonusPage.typeBonusTemplateNameToFilter(expectedImsBonusEntity.getSearchFieldText());
        actualImsBonusEntity = imsBonusPage.getAllFields();
        //THEN
        assertObjectsEquals(expectedImsBonusEntity, actualImsBonusEntity);
    }

    @Test
    public void searchBonusTemplateSpecSymbol5Test() {
        expectedImsBonusEntity.setSearchFieldText("'");
        List expectedAvailableTemplatesList = new ArrayList();
        expectedAvailableTemplatesList.add("<script language='JavaScript'> alert('До");
        expectedImsBonusEntity.setAvailableTemplates(expectedAvailableTemplatesList);
        //WHEN
        imsBonusPage = (ImsBonusPage) actionSectionPage.addAction(expectedImsBonusEntity.getAction());
        imsBonusPage.typeBonusTemplateNameToFilter(expectedImsBonusEntity.getSearchFieldText());
        actualImsBonusEntity = imsBonusPage.getAllFields();
        //THEN
        assertObjectsEquals(expectedImsBonusEntity, actualImsBonusEntity);
    }

    @Test
    public void validateSearchField1Test() {
        expectedImsBonusEntity.setSearchFieldText("12345678901234567890");
        expectedImsBonusEntity.setAvailableTemplates(Arrays.asList("12345678901234567890"));
        //WHEN
        imsBonusPage = (ImsBonusPage) actionSectionPage.addAction(expectedImsBonusEntity.getAction());
        imsBonusPage.typeBonusTemplateNameToFilter(expectedImsBonusEntity.getSearchFieldText());
        actualImsBonusEntity = imsBonusPage.getAllFields();
        //THEN
        assertObjectsEquals(expectedImsBonusEntity, actualImsBonusEntity);
    }

    @Test
    public void validateSearchField2Test() {
        expectedImsBonusEntity.setSearchFieldText("ASDASDasdas-123123211212&^%&^%");
        expectedImsBonusEntity.setAvailableTemplates(Arrays.asList("ASDASDasdas-123123211212&^%&^%"));
        //WHEN
        imsBonusPage = (ImsBonusPage) actionSectionPage.addAction(expectedImsBonusEntity.getAction());
        imsBonusPage.typeBonusTemplateNameToFilter(expectedImsBonusEntity.getSearchFieldText());
        actualImsBonusEntity = imsBonusPage.getAllFields();
        //THEN
        assertObjectsEquals(expectedImsBonusEntity, actualImsBonusEntity);
    }

    @Test
    public void validateSearchField4Test() {
        expectedImsBonusEntity.setSearchFieldText("ASDGFFLKLKLKlddvddsghdsh");
        expectedImsBonusEntity.setAvailableTemplates(Arrays.asList("ASDGFFLKLKLKlddvddsghdsh"));
        //WHEN
        imsBonusPage = (ImsBonusPage) actionSectionPage.addAction(expectedImsBonusEntity.getAction());
        imsBonusPage.typeBonusTemplateNameToFilter(expectedImsBonusEntity.getSearchFieldText());
        actualImsBonusEntity = imsBonusPage.getAllFields();
        //THEN
        assertObjectsEquals(expectedImsBonusEntity, actualImsBonusEntity);
    }

    @Test
    public void validateSearchField5Test() {
        expectedImsBonusEntity.setSearchFieldText(":!@#$%^&*()_.");
        expectedImsBonusEntity.setAvailableTemplates(Arrays.asList(":!@#$%^&*()_."));
        //WHEN
        imsBonusPage = (ImsBonusPage) actionSectionPage.addAction(expectedImsBonusEntity.getAction());
        imsBonusPage.typeBonusTemplateNameToFilter(expectedImsBonusEntity.getSearchFieldText());
        actualImsBonusEntity = imsBonusPage.getAllFields();
        //THEN
        assertObjectsEquals(expectedImsBonusEntity, actualImsBonusEntity);
    }

    @Test
    public void clickCancelTest() {
        expectedImsBonusEntity.setSelectedTemplateName(existingTemplate);
        expectedImsBonusEntity.setAvailableTemplates(new ArrayList<String>());
        //WHEN
        imsBonusPage = (ImsBonusPage) actionSectionPage.addAction(expectedImsBonusEntity.getAction());
        imsBonusPage.selectBonusTemplate(expectedImsBonusEntity.getSelectedTemplateName());
        actionSectionPage = (ActionsPage) imsBonusPage.clickCancel(false);
        //THEN
        assertTrue(actionSectionPage.isActionsTableEmpty(), "IMS Bonus is in the Actions table after pressing Cancel button on IMS Bonus form.");
        campaignDetailsPage = actionSectionPage.clickSave();
        activityTab = campaignTabs.openActivityTab();
        actionSectionPage = (ActionsPage) activityTab.clickActions();
        assertTrue(actionSectionPage.isActionsTableEmpty(), "IMS Bonus is in the Actions table after pressing Cancel button on IMS Bonus form and saving Campaign.");
    }

    @Test
    public void saveBonusTest() {
        expectedImsBonusEntity.setSearchFieldText(existingTemplate);
        expectedImsBonusEntity.setSelectedTemplateName(existingTemplate);
        expectedImsBonusEntity.setAvailableTemplates(Arrays.asList(existingTemplate));
        expectedImsBonusEntity.setSourceForBonusCalcAmount("No amount required");
        //WHEN
        imsBonusPage = (ImsBonusPage) actionSectionPage.addAction(expectedImsBonusEntity.getAction());
        imsBonusPage.setAllFields(expectedImsBonusEntity);
        actionSectionPage = (ActionsPage) imsBonusPage.clickOk(false);
        campaignDetailsPage = actionSectionPage.clickSave();
        activityTab = campaignDetailsPage.openActivityTab();
        actionSectionPage = (ActionsPage) activityTab.clickActions();
        //THEN
        assertEquals(expectedImsBonusEntity.getAction(), actionSectionPage.getAction(1));
        assertEquals(expectedImsBonusEntity.getSelectedTemplateName() + " - Amount Source: " + expectedImsBonusEntity.getSourceForBonusCalcAmount(), actionSectionPage.getSummary(1));
        activityTab = campaignTabs.openActivityTab();
        actionSectionPage = (ActionsPage) activityTab.clickActions();
        imsBonusPage = (ImsBonusPage) actionSectionPage.clickEdit(0);
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
        imsBonusPage = (ImsBonusPage) actionSectionPage.addAction(expectedImsBonusEntity.getAction());
        imsBonusPage.setAllFields(expectedImsBonusEntity);
        actionSectionPage = (ActionsPage) imsBonusPage.clickOk(false);
        campaignDetailsPage = actionSectionPage.clickSave();
        activityTab = campaignDetailsPage.openActivityTab();
        actionSectionPage = (ActionsPage) activityTab.clickActions();
        actionSectionPage.clickDelete(0);
        //THEN
        assertTrue(actionSectionPage.isActionsTableEmpty(), "IMS Bonus is not disappeared from actions table after deleting.");
        campaignDetailsPage = actionSectionPage.clickSave();
        activityTab = campaignDetailsPage.openActivityTab();
        actionSectionPage = (ActionsPage) activityTab.clickActions();
        assertTrue(actionSectionPage.isActionsTableEmpty(), "IMS Bonus is not disappeared from actions table after deleting.");
    }

    @Test
    public void clickOnCreateNewBonusLinkTest() {
        String expectedAwardSystemLinkToOpen = "http://localhost:8080/ims/bexcore/PHPTabNaviPage/bonus_search";
        //WHEN
        imsBonusPage = (ImsBonusPage) actionSectionPage.addAction(expectedImsBonusEntity.getAction());
//        THEN
        assertEquals(imsBonusPage.clickNewImsBonusLink(), expectedAwardSystemLinkToOpen, "Expected Award System link has differences with actual one.");
    }

    @Test
    public void editBonusTest() {
        expectedImsBonusEntity.setSearchFieldText(existingTemplate);
        expectedImsBonusEntity.setSelectedTemplateName(existingTemplate);
        expectedImsBonusEntity.setAvailableTemplates(Arrays.asList(existingTemplate));
        expectedImsBonusEntity.setSourceForBonusCalcAmount("No amount required");
        //WHEN
        imsBonusPage = (ImsBonusPage) actionSectionPage.addAction(expectedImsBonusEntity.getAction());
        imsBonusPage.setAllFields(expectedImsBonusEntity);
        actionSectionPage = (ActionsPage) imsBonusPage.clickOk(false);
        campaignDetailsPage = actionSectionPage.clickSave();

        //TODO Add real new values when some new templates will be a
        expectedImsBonusEntity.setSearchFieldText(existingTemplate);
        expectedImsBonusEntity.setSelectedTemplateName(existingTemplate);
        expectedImsBonusEntity.setAvailableTemplates(Arrays.asList(existingTemplate));
        expectedImsBonusEntity.setSourceForBonusCalcAmount("No amount required");

        activityTab = campaignTabs.openActivityTab();
        actionSectionPage = (ActionsPage) activityTab.clickActions();
        imsBonusPage = (ImsBonusPage) actionSectionPage.clickEdit(0);

        imsBonusPage.setAllFields(expectedImsBonusEntity);
        actionSectionPage = (ActionsPage) imsBonusPage.clickOk(false);
        campaignDetailsPage = actionSectionPage.clickSave();

        activityTab = campaignTabs.openActivityTab();
        actionSectionPage = (ActionsPage) activityTab.clickActions();
        //THEN
        assertEquals(actionSectionPage.getAction(1), expectedImsBonusEntity.getAction());
        assertEquals(actionSectionPage.getSummary(1), expectedImsBonusEntity.getSelectedTemplateName() + " - Amount Source: " + expectedImsBonusEntity.getSourceForBonusCalcAmount());
        imsBonusPage = (ImsBonusPage) actionSectionPage.clickEdit(0);
        actualImsBonusEntity = imsBonusPage.getAllFields();
        assertObjectsEquals(expectedImsBonusEntity, actualImsBonusEntity);
    }
}
