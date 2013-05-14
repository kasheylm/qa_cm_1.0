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

import static org.testng.Assert.assertTrue;

/**
 * User: Denis Veselovskiy
 * Date: 10/12/12, Time: 11:47 AM
 */
public class GameLossesReportFieldValidationTest extends BaseTest {
    CampaignData campaignData;
    String activityName;
    String activityID;
    CampaignDetailsPage campaignDetailsPage;
    NewCampaignPage newCampaignPage;
    CampaignTabs campaignTabs;
    ActivityTabPage activityTab;
    ConditionSectionPage conditionSectionPage;
    ConditionDialogPage conditionDialog;
    CustomReportDialogPage customReportDialog;
    GameLossesPlayerReportPage playerGameLossesPage;
    CMDashboardPage cmDashboardPage;

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

    @Test(dataProvider = "getStartDateFieldNegativeData", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateStartDateFieldNegative (GameLossesPlayerReportEntity data, String incorrectFieldValue, String expectedErrorMsg) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.setStartDateOptions(data.getStartDateOptions());
        playerGameLossesPage.typeStartDate(incorrectFieldValue);
        playerGameLossesPage.clickOk();
//        THEN
        assertTrue(playerGameLossesPage.getErrorsCount() == 1);
        assertTrue(playerGameLossesPage.isErrorPresent(expectedErrorMsg));
    }

    @Test(dataProvider = "getEndDateFieldNegativeData", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateEndDateFieldNegative (GameLossesPlayerReportEntity data, String incorrectFieldValue, String expectedErrorMsg) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.setStartDateOptions(data.getStartDateOptions());
        playerGameLossesPage.typeDaysAgo(data.getDaysAgo());
        playerGameLossesPage.setEndDateOptions(data.getEndDateOptions());
        playerGameLossesPage.typeEndDate(incorrectFieldValue);
        playerGameLossesPage.clickOk();
//        THEN
        assertTrue(playerGameLossesPage.getErrorsCount() == 1);
        assertTrue(playerGameLossesPage.isErrorPresent(expectedErrorMsg));
    }

    @Test(dataProvider = "getValidateVipLevelFieldData", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateVipLevelFieldNegative (GameLossesPlayerReportEntity data, String incorrectFieldValue, String expectedErrorMsg) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillFormOnlyMandatory(data);
        playerGameLossesPage.typeVipLevel(incorrectFieldValue);
        playerGameLossesPage.clickOk();
//        THEN
        assertTrue(playerGameLossesPage.getErrorsCount() == 1);
        assertTrue(playerGameLossesPage.isErrorPresent(expectedErrorMsg));
    }

    @Test(dataProvider = "getValidateMinLossesFieldData", dataProviderClass = GameLossesPlayerReportData.class)
         public void validateMinLossesFieldNegative (GameLossesPlayerReportEntity data, String incorrectFieldValue, String expectedErrorMsg) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillFormOnlyMandatory(data);
        playerGameLossesPage.typeMinLosses(incorrectFieldValue);
        playerGameLossesPage.clickOk();
//        THEN
        assertTrue(playerGameLossesPage.getErrorsCount() == 1);
        assertTrue(playerGameLossesPage.isErrorPresent(expectedErrorMsg));
    }

    @Test(dataProvider = "getValidateMinNetLossesFieldData", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateMinNetLossesFieldNegative (GameLossesPlayerReportEntity data, String incorrectFieldValue, String expectedErrorMsg) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillFormOnlyMandatory(data);
        playerGameLossesPage.typeMinNetLosses(incorrectFieldValue);
        playerGameLossesPage.clickOk();
//        THEN
        assertTrue(playerGameLossesPage.getErrorsCount() == 1);
        assertTrue(playerGameLossesPage.isErrorPresent(expectedErrorMsg));
    }

    @Test(dataProvider = "getValidateMinDepositsFieldData", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateMinDepositsFieldNegative (GameLossesPlayerReportEntity data, String incorrectFieldValue, String expectedErrorMsg) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillFormOnlyMandatory(data);
        playerGameLossesPage.typeMinDeposits(incorrectFieldValue);
        playerGameLossesPage.clickOk();
//        THEN
        assertTrue(playerGameLossesPage.getErrorsCount() == 1);
        assertTrue(playerGameLossesPage.isErrorPresent(expectedErrorMsg));
    }

    @Test(dataProvider = "getValidateMinAverageBetFieldData", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateMinAverageBetFieldNegative (GameLossesPlayerReportEntity data, String incorrectFieldValue, String expectedErrorMsg) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillFormOnlyMandatory(data);
        playerGameLossesPage.typeMinAverageBet(incorrectFieldValue);
        playerGameLossesPage.clickOk();
//        THEN
        assertTrue(playerGameLossesPage.getErrorsCount() == 1);
        assertTrue(playerGameLossesPage.isErrorPresent(expectedErrorMsg));
    }

    @Test(dataProvider = "getValidateDaysAgoFieldData", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateDaysAgoFieldNegative (GameLossesPlayerReportEntity data, String incorrectFieldValue, String expectedErrorMsg) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillFormOnlyMandatory(data);
        playerGameLossesPage.typeDaysAgo(incorrectFieldValue);
        playerGameLossesPage.clickOk();
//        THEN
        assertTrue(playerGameLossesPage.getDaysAgo().length()==3);
        assertTrue(playerGameLossesPage.getErrorsCount() == 1);
        assertTrue(playerGameLossesPage.isErrorPresent(expectedErrorMsg));
    }

    @Test(dataProvider = "getValidateLessZeroValuesData", dataProviderClass = GameLossesPlayerReportData.class)
         public void validateFieldsWithLessZeroValues (GameLossesPlayerReportEntity data, String expectedErrorMsg) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillForm(data);
        playerGameLossesPage.clickOk();
//        THEN
        assertTrue(playerGameLossesPage.getErrorsCount() == 1);
        assertTrue(playerGameLossesPage.isErrorPresent(expectedErrorMsg));
    }

    @Test(dataProvider = "getValidateIssue366Data", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateIntFieldsLengthBug366(GameLossesPlayerReportEntity data) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillForm(data);
        playerGameLossesPage.clickOk();
//        THEN
        assertTrue(playerGameLossesPage.getErrorsCount() == 0);
    }

}
