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
import com.playtech.cm.pages.dashboard.campaign.activities.conditions.LapsedPlayerReportPage;
import com.playtech.cm.utils.dataProviders.CampaignData;
import com.playtech.cm.utils.dataProviders.conditions.LapsedPlayerReportData;
import com.playtech.cm.utils.entities.conditions.Filter;
import com.playtech.cm.utils.entities.conditions.LapsedPlayerReportEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * User: Denis Veselovskiy
 * Date: 10/8/12 * Time: 12:50 PM
 */
public class LapsedPlayerReportTest extends BaseTest {
    CampaignData campaignData;
    String activityName;
    String activityID;
    LapsedPlayerReportEntity expectedLapsedEntity;
    LapsedPlayerReportEntity actualLapsedEntity;
    CampaignDetailsPage campaignDetailsPage;
    NewCampaignPage newCampaignPage;
    CampaignTabs campaignTabs;
    ActivityTabPage activityTab;
    ConditionSectionPage conditionSectionPage;
    ConditionDialogPage conditionDialog;
    CustomReportDialogPage customReportDialog;
    LapsedPlayerReportPage lapsedPlayerReport;
    CMDashboardPage cmDashboardPage;


    String errorEmpty = "Last Real Gaming Activity should not be empty.";
    String errorDigitalValue = "Last Real Gaming Activity. Please enter a non-decimal numeric value.";

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
        expectedLapsedEntity = new LapsedPlayerReportEntity();
        actualLapsedEntity = new LapsedPlayerReportEntity();
    }

    @Test
    public void leaveLastPlayedFieldEmpty() {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(expectedLapsedEntity.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(expectedLapsedEntity.getCondition());
        lapsedPlayerReport = (LapsedPlayerReportPage) customReportDialog.addReport(expectedLapsedEntity.getCustomReport());
        lapsedPlayerReport.clickOk();
        //THEN
        assertTrue(lapsedPlayerReport.getErrorsCount() == 1);
        assertTrue(lapsedPlayerReport.isErrorPresent(errorEmpty));
    }

    @Test
    public void checkSessionIsCleaned() {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(expectedLapsedEntity.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(expectedLapsedEntity.getCondition());
        lapsedPlayerReport = (LapsedPlayerReportPage) customReportDialog.addReport(expectedLapsedEntity.getCustomReport());
        lapsedPlayerReport.clickOk();
        assertTrue(lapsedPlayerReport.getErrorsCount() == 1);
        assertTrue(lapsedPlayerReport.isErrorPresent(errorEmpty));
        lapsedPlayerReport.clickCancel();
        conditionDialog = conditionSectionPage.addCondition(expectedLapsedEntity.getConditionToAdd());
        //THEN
        assertTrue(lapsedPlayerReport.getErrorsCount() == 0);
        assertFalse(lapsedPlayerReport.isErrorPresent(errorEmpty));
        lapsedPlayerReport.clickCancel();
    }

    @Test(dataProvider = "validateFieldBase", dataProviderClass = LapsedPlayerReportData.class)
    public void validatePlayerLastPlayedField (String text) {
        conditionDialog = conditionSectionPage.addCondition(expectedLapsedEntity.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(expectedLapsedEntity.getCondition());
        lapsedPlayerReport = (LapsedPlayerReportPage) customReportDialog.addReport(expectedLapsedEntity.getCustomReport());
        lapsedPlayerReport.typeLastPlayed(text);
        lapsedPlayerReport.clickOk();
        assertTrue(lapsedPlayerReport.getErrorsCount() == 1);
        assertTrue(lapsedPlayerReport.isErrorPresent(errorDigitalValue));
    }

    @Test(dataProvider = "validateFieldWithDigits", dataProviderClass = LapsedPlayerReportData.class)
    public void validateVipLevelField (String text) {
        conditionDialog = conditionSectionPage.addCondition(expectedLapsedEntity.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(expectedLapsedEntity.getCondition());
        lapsedPlayerReport = (LapsedPlayerReportPage) customReportDialog.addReport(expectedLapsedEntity.getCustomReport());
        lapsedPlayerReport.typeLastPlayed(expectedLapsedEntity.getLastPlayedAgo());
        lapsedPlayerReport.typeVipLevel(text);
        expectedLapsedEntity.setVipLevel(lapsedPlayerReport.getVipLevel());
        conditionSectionPage = lapsedPlayerReport.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        assertConditionCreated(expectedLapsedEntity);
    }

    @Test(dataProvider = "validateFieldWithDigits", dataProviderClass = LapsedPlayerReportData.class)
    public void validateMaxRealBalanceField (String text) {
        conditionDialog = conditionSectionPage.addCondition(expectedLapsedEntity.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(expectedLapsedEntity.getCondition());
        lapsedPlayerReport = (LapsedPlayerReportPage) customReportDialog.addReport(expectedLapsedEntity.getCustomReport());
        lapsedPlayerReport.typeLastPlayed(expectedLapsedEntity.getLastPlayedAgo());
        lapsedPlayerReport.typeMaxBalance(text);
        expectedLapsedEntity.setMaxBalance(lapsedPlayerReport.getMaxBalance());
        conditionSectionPage = lapsedPlayerReport.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        assertConditionCreated(expectedLapsedEntity);
    }

    @Test(dataProvider = "validateFieldWithDigits", dataProviderClass = LapsedPlayerReportData.class)
    public void validateMinRealBalanceField (String text) {
        conditionDialog = conditionSectionPage.addCondition(expectedLapsedEntity.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(expectedLapsedEntity.getCondition());
        lapsedPlayerReport = (LapsedPlayerReportPage) customReportDialog.addReport(expectedLapsedEntity.getCustomReport());
        lapsedPlayerReport.typeLastPlayed(expectedLapsedEntity.getLastPlayedAgo());
        lapsedPlayerReport.typeMinBalance(text);
        expectedLapsedEntity.setMinBalance(lapsedPlayerReport.getMinBalance());
        conditionSectionPage = lapsedPlayerReport.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        assertConditionCreated(expectedLapsedEntity);
    }

    @Test(dataProvider = "validateFieldWithDigits", dataProviderClass = LapsedPlayerReportData.class)
    public void validateMinEarningsField (String text) {
        conditionDialog = conditionSectionPage.addCondition(expectedLapsedEntity.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(expectedLapsedEntity.getCondition());
        lapsedPlayerReport = (LapsedPlayerReportPage) customReportDialog.addReport(expectedLapsedEntity.getCustomReport());
        lapsedPlayerReport.typeLastPlayed(expectedLapsedEntity.getLastPlayedAgo());
        lapsedPlayerReport.typeMinEarnings(text);
        expectedLapsedEntity.setMinEarnings(lapsedPlayerReport.getMinEarnings());
        conditionSectionPage = lapsedPlayerReport.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        assertConditionCreated(expectedLapsedEntity);
    }

    @Test(dataProvider = "validateFieldWithDigits", dataProviderClass = LapsedPlayerReportData.class)
    public void validateMaxEarningsField (String text) {
        conditionDialog = conditionSectionPage.addCondition(expectedLapsedEntity.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(expectedLapsedEntity.getCondition());
        lapsedPlayerReport = (LapsedPlayerReportPage) customReportDialog.addReport(expectedLapsedEntity.getCustomReport());
        lapsedPlayerReport.typeLastPlayed(expectedLapsedEntity.getLastPlayedAgo());
        lapsedPlayerReport.typeMaxEarnings(text);
        expectedLapsedEntity.setMaxEarnings(lapsedPlayerReport.getMaxEarnings());
        conditionSectionPage = lapsedPlayerReport.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        assertConditionCreated(expectedLapsedEntity);
    }

    @Test(dataProvider = "getClientTypes", dataProviderClass = LapsedPlayerReportData.class)
    public void selectVariousClientTypes (String text) {
        conditionDialog = conditionSectionPage.addCondition(expectedLapsedEntity.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(expectedLapsedEntity.getCondition());
        lapsedPlayerReport = (LapsedPlayerReportPage) customReportDialog.addReport(expectedLapsedEntity.getCustomReport());
        lapsedPlayerReport.typeLastPlayed(expectedLapsedEntity.getLastPlayedAgo());
        lapsedPlayerReport.setClientType(text);
        expectedLapsedEntity.setClientType(text);
        conditionSectionPage = lapsedPlayerReport.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        assertConditionCreated(expectedLapsedEntity);
    }

    @Test(dataProvider = "getClientPlatforms", dataProviderClass = LapsedPlayerReportData.class)
    public void selectVariousClientPlatforms (String text) {
        conditionDialog = conditionSectionPage.addCondition(expectedLapsedEntity.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(expectedLapsedEntity.getCondition());
        lapsedPlayerReport = (LapsedPlayerReportPage) customReportDialog.addReport(expectedLapsedEntity.getCustomReport());
        lapsedPlayerReport.typeLastPlayed(expectedLapsedEntity.getLastPlayedAgo());
        lapsedPlayerReport.setClientPlatform(text);
        expectedLapsedEntity.setClientPlatform(text);
        conditionSectionPage = lapsedPlayerReport.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        assertConditionCreated(expectedLapsedEntity);
    }

    @Test(dataProvider = "getFilters", dataProviderClass = LapsedPlayerReportData.class)
    public void checkVariousSuspendedAccounts (Filter suspended) {
        conditionDialog = conditionSectionPage.addCondition(expectedLapsedEntity.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(expectedLapsedEntity.getCondition());
        lapsedPlayerReport = (LapsedPlayerReportPage) customReportDialog.addReport(expectedLapsedEntity.getCustomReport());
        lapsedPlayerReport.typeLastPlayed(expectedLapsedEntity.getLastPlayedAgo());
        expectedLapsedEntity.setSuspended(suspended);
        lapsedPlayerReport.setSuspend(suspended);
        conditionSectionPage = lapsedPlayerReport.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        assertConditionCreated(expectedLapsedEntity);
    }

    @Test
    public void fillOnlyRequiredFields() {
        conditionDialog = conditionSectionPage.addCondition(expectedLapsedEntity.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(expectedLapsedEntity.getCondition());
        lapsedPlayerReport = (LapsedPlayerReportPage) customReportDialog.addReport(expectedLapsedEntity.getCustomReport());
        lapsedPlayerReport.fillForm(expectedLapsedEntity);
        conditionSectionPage = lapsedPlayerReport.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        assertConditionCreated(expectedLapsedEntity);
    }

    @Test
    public void createAndEditCondition() {
        conditionDialog = conditionSectionPage.addCondition(expectedLapsedEntity.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(expectedLapsedEntity.getCondition());
        lapsedPlayerReport = (LapsedPlayerReportPage) customReportDialog.addReport(expectedLapsedEntity.getCustomReport());
        expectedLapsedEntity.setAllFields();
        lapsedPlayerReport.fillForm(expectedLapsedEntity);
        conditionSectionPage = lapsedPlayerReport.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        assertConditionCreated(expectedLapsedEntity);
        expectedLapsedEntity.setAllFieldsForEdit();
        lapsedPlayerReport = conditionSectionPage.editConditionLapsedReport(0);
        lapsedPlayerReport.fillForm(expectedLapsedEntity);
        conditionSectionPage = lapsedPlayerReport.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        assertConditionCreated(expectedLapsedEntity);
    }

    @Test
    public void fillAllFieldsAndClickCancel() {
        conditionDialog = conditionSectionPage.addCondition(expectedLapsedEntity.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(expectedLapsedEntity.getCondition());
        lapsedPlayerReport = (LapsedPlayerReportPage) customReportDialog.addReport(expectedLapsedEntity.getCustomReport());
        expectedLapsedEntity.setAllFields();
        lapsedPlayerReport.fillForm(expectedLapsedEntity);
        conditionSectionPage = lapsedPlayerReport.clickCancel();
        campaignDetailsPage = conditionSectionPage.clickSave();
        campaignTabs = campaignDetailsPage.openActivityTab();
        activityTab = campaignTabs.openActivityTab();
        conditionSectionPage = activityTab.clickConditions();
        assertEquals(conditionSectionPage.getNameEmpty(), "");
    }

    @Test
    public void fillAllFields() {
        conditionDialog = conditionSectionPage.addCondition(expectedLapsedEntity.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(expectedLapsedEntity.getCondition());
        lapsedPlayerReport = (LapsedPlayerReportPage) customReportDialog.addReport(expectedLapsedEntity.getCustomReport());
        expectedLapsedEntity.setAllFields();
        lapsedPlayerReport.fillForm(expectedLapsedEntity);
        conditionSectionPage = lapsedPlayerReport.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        assertConditionCreated(expectedLapsedEntity);
    }

    private void assertConditionCreated(LapsedPlayerReportEntity expectedLapsedEntity) {
            activityTab = campaignDetailsPage.openActivityTab();
            conditionSectionPage = activityTab.clickConditions();
            assertEquals(conditionSectionPage.getSummary(), this.expectedLapsedEntity.getSummary());
            assertTrue(conditionSectionPage.getName().contains(this.expectedLapsedEntity.getConditionToAdd()));
            lapsedPlayerReport = conditionSectionPage.editConditionLapsedReport(0);
            actualLapsedEntity = lapsedPlayerReport.getForm(actualLapsedEntity);
            assertObjectsEquals(actualLapsedEntity, expectedLapsedEntity);
    }
}
