package com.playtech.cm.selenium.campaigns.actions;

import com.playtech.cm.BaseTest;
import com.playtech.cm.db.DbUnitUtil;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignDetailsPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignTabs;
import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.pages.dashboard.campaign.activities.actions.ActionsPage;
import com.playtech.cm.pages.dashboard.campaign.activities.actions.EditTestGroupsPage;
import com.playtech.cm.pages.dashboard.campaign.activities.actions.TestGroupsPage;
import com.playtech.cm.pages.dashboard.campaign.activities.details.ActivityDetailsPage;
import com.playtech.cm.utils.dataProviders.CampaignData;
import com.playtech.cm.utils.entities.actions.ImsBonusEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * User: Denis Veselovskiy
 * Date: 10/29/12 * Time: 4:02 PM
 */
public class TestGroupsTest extends BaseTest {

    CampaignData campaignData;
    String activityName;
    String activityID;
    ImsBonusEntity expectedImsBonusEntity;
    ImsBonusEntity actualImsBonusEntity;
    CMDashboardPage cmDashboardPage;
    CampaignDetailsPage campaignDetailsPage;
    CampaignTabs campaignTabs;
    ActivityTabPage activityTab;
    TestGroupsPage testGroupsPage;
    EditTestGroupsPage editTestGroupsPage;
    ActivityDetailsPage activityDetailsPage;
    ActionsPage actionsPage;

    String errorEmptyName = "Textfield should not be empty.";
    String errorTotalMore100 = "Total percent should be equal to 100 %.";
    String errorNotUniqueName = "All the test group names should be unique.";

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

    @Test
    public void verifyDefaultAllocation() {
        //WHEN
        editTestGroupsPage = testGroupsPage.clickEditTestGroupsLink();
        editTestGroupsPage.waitFormAppear();
        assertEquals(editTestGroupsPage.count(), 3);
        assertEquals(editTestGroupsPage.getControlGroupPercent(), "30");
        assertEquals(editTestGroupsPage.getPercent("Test Group 1"), "35");
        assertEquals(editTestGroupsPage.getPercent("Test Group 2"), "35");
    }

    @Test
    public void setEmptyGroupName() {
        //WHEN
        editTestGroupsPage = testGroupsPage.clickEditTestGroupsLink();
        editTestGroupsPage.waitFormAppear();
        editTestGroupsPage.typeGroupName("Test Group 1", "");
        editTestGroupsPage.clickOk();
        assertEquals(editTestGroupsPage.getErrorsCount(), 1);
        assertTrue(editTestGroupsPage.isErrorPresent(errorEmptyName));
    }

    @Test
    public void setNotUniqueGroupName() {
        //WHEN
        editTestGroupsPage = testGroupsPage.clickEditTestGroupsLink();
        editTestGroupsPage.waitFormAppear();
        editTestGroupsPage.typeGroupName("Test Group 1", "Test Group 2");
        editTestGroupsPage.clickOk();
        assertEquals(editTestGroupsPage.getErrorsCount(), 1);
        assertTrue(editTestGroupsPage.isErrorPresent(errorNotUniqueName));
    }

    @Test
    public void setTotalPercentMore100() {
        //WHEN
        editTestGroupsPage = testGroupsPage.clickEditTestGroupsLink();
        editTestGroupsPage.waitFormAppear();
        editTestGroupsPage.typePercent("Test Group 1", "200");
        editTestGroupsPage.clickOk();
        assertEquals(editTestGroupsPage.getErrorsCount(), 1);
        assertTrue(editTestGroupsPage.isErrorPresent(errorTotalMore100));
    }

    @Test
    public void checkSessionIsCleaned() {
        //WHEN
        editTestGroupsPage = testGroupsPage.clickEditTestGroupsLink();
        editTestGroupsPage.waitFormAppear();
        editTestGroupsPage.typePercent("Test Group 1", "200");
        editTestGroupsPage.clickOk();
        editTestGroupsPage.typePercent("Test Group 1", "45");
        editTestGroupsPage.typePercent("Test Group 2", "25");
        testGroupsPage = editTestGroupsPage.clickOk();
        testGroupsPage.waitUntilGroupNameAppear("(45%) Test Group 1");
        assertTrue(testGroupsPage.isGroupExist("(45%) Test Group 1"));
        assertTrue(testGroupsPage.isGroupExist("(25%) Test Group 2"));
    }

    @Test
    public void deleteGroupAndClickCancel() {
        //WHEN
        editTestGroupsPage = testGroupsPage.clickEditTestGroupsLink();
        editTestGroupsPage.waitFormAppear();
        editTestGroupsPage.clickDelete("Test Group 1");
        editTestGroupsPage.clickCancel();
        testGroupsPage.waitUntilGroupNameAppear("(35%) Test Group 1");
        assertTrue(testGroupsPage.isGroupExist("(35%) Test Group 1"));
    }

    @Test
    public void deleteGroupFromDefaultAllocation() {
        //WHEN
        editTestGroupsPage = testGroupsPage.clickEditTestGroupsLink();
        editTestGroupsPage.waitFormAppear();
        editTestGroupsPage.clickDelete("Test Group 1");
        testGroupsPage = editTestGroupsPage.clickOk();
        testGroupsPage.waitUntilGroupNameAppear("(50%) Test Group 2");
        assertTrue(testGroupsPage.isGroupExist("(50%) Control Group"));
    }

    @Test
    public void verifyAddingNewGroups() {
        //WHEN
        editTestGroupsPage = testGroupsPage.clickEditTestGroupsLink();
        editTestGroupsPage.waitFormAppear();
        editTestGroupsPage.addGroup();
        editTestGroupsPage.waitUntilGroupAppear("Test Group 3");
        testGroupsPage = editTestGroupsPage.clickOk();
        testGroupsPage.waitUntilGroupNameAppear("(25%) Test Group 1");
        assertTrue(testGroupsPage.isGroupExist("(25%) Test Group 2"));
        assertTrue(testGroupsPage.isGroupExist("(25%) Test Group 3"));
        assertTrue(testGroupsPage.isGroupExist("(25%) Control Group"));
        editTestGroupsPage = testGroupsPage.clickEditTestGroupsLink();
        editTestGroupsPage.waitFormAppear();
        editTestGroupsPage.addGroup();
        editTestGroupsPage.waitUntilGroupAppear("Test Group 4");
        testGroupsPage = editTestGroupsPage.clickOk();
        testGroupsPage.waitUntilGroupNameAppear("(20%) Test Group 1");
        assertTrue(testGroupsPage.isGroupExist("(20%) Test Group 2"));
        assertTrue(testGroupsPage.isGroupExist("(20%) Test Group 3"));
        assertTrue(testGroupsPage.isGroupExist("(20%) Test Group 4"));
        assertTrue(testGroupsPage.isGroupExist("(20%) Control Group"));
    }

    @Test
    public void verifyNewGroupsWithNullPercent() {
        //WHEN
        editTestGroupsPage = testGroupsPage.clickEditTestGroupsLink();
        editTestGroupsPage.waitFormAppear();
        editTestGroupsPage.typePercent("Test Group 1", "45");
        editTestGroupsPage.typePercent("Test Group 2", "25");
        editTestGroupsPage.addGroup();
        editTestGroupsPage.waitUntilGroupAppear("Test Group 3");
        assertEquals(editTestGroupsPage.getPercent("Test Group 3"), "0");
        testGroupsPage = editTestGroupsPage.clickOk();
        testGroupsPage.waitUntilGroupNameAppear("(45%) Test Group 1");
        assertTrue(testGroupsPage.isGroupExist("(25%) Test Group 2"));
        assertTrue(testGroupsPage.isGroupExist("(0%) Test Group 3"));
        assertTrue(testGroupsPage.isGroupExist("(30%) Control Group"));
    }

    @Test
    public void verifyTestGroupsExistWhenActivityIsTest() {
        //WHEN
        editTestGroupsPage = testGroupsPage.clickEditTestGroupsLink();
        editTestGroupsPage.waitFormAppear();
        assertEquals(editTestGroupsPage.count(), 3);
        //THEN
        assertEquals(editTestGroupsPage.getControlGroupPercent(), "30");
        assertEquals(editTestGroupsPage.getPercent("Test Group 1"), "35");
        assertEquals(editTestGroupsPage.getPercent("Test Group 2"), "35");
        testGroupsPage = editTestGroupsPage.clickCancel();
        activityDetailsPage = testGroupsPage.clickDetails();
        actionsPage = activityDetailsPage.openActivityTab().clickActions();
        actionsPage.clickTestNo();
        campaignDetailsPage = actionsPage.clickSave();
        activityTab = campaignDetailsPage.openActivityTab();
        actionsPage = activityTab.clickActions();
        assertTrue(actionsPage.isActionsTableEmpty());
    }
}
