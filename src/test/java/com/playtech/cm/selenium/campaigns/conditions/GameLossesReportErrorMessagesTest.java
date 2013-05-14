package com.playtech.cm.selenium.campaigns.conditions;

import com.playtech.cm.BaseTest;
import com.playtech.cm.db.DbUnitUtil;
import com.playtech.cm.pages.dashboard.newCampaign.NewCampaignPage;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignDetailsPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignTabs;
import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.pages.dashboard.campaign.activities.conditions.ConditionDialogPage;
import com.playtech.cm.pages.dashboard.campaign.activities.conditions.ConditionSectionPage;
import com.playtech.cm.pages.dashboard.campaign.activities.conditions.CustomReportDialogPage;
import com.playtech.cm.pages.dashboard.campaign.activities.conditions.GameLossesPlayerReportPage;
import com.playtech.cm.utils.dataProviders.CampaignData;
import com.playtech.cm.utils.dataProviders.conditions.GameLossesPlayerReportData;
import com.playtech.cm.utils.entities.conditions.GameLossesPlayerReportEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 10/10/12
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameLossesReportErrorMessagesTest extends BaseTest{
    CampaignData campaignData;
    String activityName;
    String activityID;
    CampaignDetailsPage campaignDetailsPage;
    CMDashboardPage cmDashboardPage;
    NewCampaignPage newCampaignPage;
    CampaignTabs campaignTabs;
    ActivityTabPage activityTab;
    ConditionSectionPage conditionSectionPage;
    ConditionDialogPage conditionDialog;
    CustomReportDialogPage customReportDialog;
    GameLossesPlayerReportPage playerGameLossesPage;

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
        conditionSectionPage = activityTab.clickConditions();
    }

    @Test(dataProvider = "getDaysAgoFieldEmptyData", dataProviderClass = GameLossesPlayerReportData.class)
         public void leaveDaysAgoFieldEmpty (GameLossesPlayerReportEntity data, String expectedErrorMsg) {
//        WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.setStartDateOptions(data.getStartDateOptions());
        playerGameLossesPage.typeDaysAgo("");
        playerGameLossesPage.clickOk();
//        THEN
        assertTrue(playerGameLossesPage.getErrorsCount() == 1);
        assertTrue(playerGameLossesPage.isErrorPresent(expectedErrorMsg));
    }

    @Test(dataProvider = "getStartDateFieldEmptyData", dataProviderClass = GameLossesPlayerReportData.class)
    public void leaveStartDateFieldEmpty (GameLossesPlayerReportEntity data, String expectedErrorMsg) {
//        WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.setStartDateOptions(data.getStartDateOptions());
        playerGameLossesPage.typeStartDate("");
        playerGameLossesPage.clickOk();
//        THEN
        assertTrue(playerGameLossesPage.getErrorsCount() == 1);
        assertTrue(playerGameLossesPage.isErrorPresent(expectedErrorMsg));
    }

    @Test(dataProvider = "getEndDateFieldEmptyData", dataProviderClass = GameLossesPlayerReportData.class)
    public void leaveEndDateEmpty(GameLossesPlayerReportEntity data, String expectedErrorMsg) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.setStartDateOptions(data.getStartDateOptions());
        playerGameLossesPage.typeDaysAgo(data.getDaysAgo());
        playerGameLossesPage.setEndDateOptions(data.getEndDateOptions());
        playerGameLossesPage.typeEndDate("");
        playerGameLossesPage.clickOk();
//        THEN
        assertTrue(playerGameLossesPage.getErrorsCount() == 1);
        assertTrue(playerGameLossesPage.isErrorPresent(expectedErrorMsg));
    }

    @Test(dataProvider = "getSessionCleanedData", dataProviderClass = GameLossesPlayerReportData.class)
    public void checkThatSessionIsCleaned(GameLossesPlayerReportEntity data, String emptyDaysAgoMessage, String emptyEndDateMessage) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.setStartDateOptions(data.getStartDateOptions());
        playerGameLossesPage.typeDaysAgo(data.getDaysAgo());
        playerGameLossesPage.setEndDateOptions(data.getEndDateOptions());
        playerGameLossesPage.typeEndDate(data.getEndDateValue());
        playerGameLossesPage.clickOk();
        assertTrue(playerGameLossesPage.getErrorsCount() == 2);
        assertTrue(playerGameLossesPage.isErrorPresent(emptyEndDateMessage));
        assertTrue(playerGameLossesPage.isErrorPresent(emptyDaysAgoMessage));
        playerGameLossesPage.clickCancel();
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        //THEN
        assertTrue(playerGameLossesPage.getErrorsCount() == 0);
    }

}
