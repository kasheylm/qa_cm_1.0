package com.playtech.cm.selenium.campaigns.conditions;

import com.playtech.cm.BaseTest;
import com.playtech.cm.db.DbUnitUtil;
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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * User: Denis Veselovskiy
 * Date: 10/12/12, Time: 11:48 AM
 */
public class GameLossesReportTest extends BaseTest{
    CampaignData campaignData;
    String activityName;
    String activityID;
    CampaignDetailsPage campaignDetailsPage;
    CampaignTabs campaignTabs;
    ActivityTabPage activityTab;
    ConditionSectionPage conditionSectionPage;
    ConditionDialogPage conditionDialog;
    CustomReportDialogPage customReportDialog;
    GameLossesPlayerReportPage playerGameLossesPage;
    CMDashboardPage cmDashboardPage;

    private void assertConditionCreated(GameLossesPlayerReportEntity expectedGameLossesReportEntity) {
        GameLossesPlayerReportEntity actualGameLossesReportEntity;
        activityTab = campaignDetailsPage.openActivityTab();
        conditionSectionPage = activityTab.clickConditions();
        assertEquals(conditionSectionPage.getSummary(), expectedGameLossesReportEntity.getSummary());
        assertTrue(conditionSectionPage.getName().contains(expectedGameLossesReportEntity.getConditionToAdd()));
        playerGameLossesPage = conditionSectionPage.editConditionPlayerGameLossesReport(0);
        actualGameLossesReportEntity = playerGameLossesPage.getAllFormValues();
        assertObjectsEquals(expectedGameLossesReportEntity, actualGameLossesReportEntity);
    }

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

    @Test(dataProvider = "getDatesPositiveData", dataProviderClass = GameLossesPlayerReportData.class)
         public void validateDatesPositive(GameLossesPlayerReportEntity data) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillFormOnlyMandatory(data);
        conditionSectionPage = playerGameLossesPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        //THEN
        assertConditionCreated(data);
    }

    @Test(dataProvider = "getDatesNegativeData", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateDatesNegative(GameLossesPlayerReportEntity data, String expectedErrorMsg) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillFormOnlyMandatory(data);
        conditionSectionPage = playerGameLossesPage.clickOk();
        //THEN
        assertTrue(playerGameLossesPage.getErrorsCount() == 1);
        assertTrue(playerGameLossesPage.isErrorPresent(expectedErrorMsg));
    }

    @Test(dataProvider = "getDatesAndDaysAgoPositiveData", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateDaysAgoPositive(GameLossesPlayerReportEntity data) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillFormOnlyMandatory(data);
        conditionSectionPage = playerGameLossesPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        //THEN
        assertConditionCreated(data);
    }

    @Test(dataProvider = "getDatesAndDaysAgoNegativeData", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateDaysAgoNegative(GameLossesPlayerReportEntity data, String expectedErrorMsg) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillFormOnlyMandatory(data);
        conditionSectionPage = playerGameLossesPage.clickOk();
        //THEN
        assertTrue(playerGameLossesPage.getErrorsCount() == 1);
        assertTrue(playerGameLossesPage.isErrorPresent(expectedErrorMsg));
    }

    @Test(dataProvider = "getGameLossCalcData", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateSelectGameLossCalculation(GameLossesPlayerReportEntity data) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillForm(data);
        conditionSectionPage = playerGameLossesPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        //THEN
        assertConditionCreated(data);
    }

    @Test(dataProvider = "getClientTypeData", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateSelectClientType(GameLossesPlayerReportEntity data) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillForm(data);
        conditionSectionPage = playerGameLossesPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        //THEN
        assertConditionCreated(data);
    }

    @Test(dataProvider = "getClientPlatformData", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateSelectClientPlatform(GameLossesPlayerReportEntity data) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillForm(data);
        conditionSectionPage = playerGameLossesPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        //THEN
        assertConditionCreated(data);
    }

    @Test(dataProvider = "getGameCategoryData", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateSelectGameCategory(GameLossesPlayerReportEntity data) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillForm(data);
        //THEN
        if(!playerGameLossesPage.getGameCategory().equals("All")){
            assertTrue(playerGameLossesPage.isGameCategoryFieldEnabled());
            assertFalse(playerGameLossesPage.areAllGameFieldsEnabled());
        }
        conditionSectionPage = playerGameLossesPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        assertConditionCreated(data);
    }

    @Test(dataProvider = "getGameFieldsData", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateGameFields(GameLossesPlayerReportEntity data) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillForm(data);
        //THEN
        assertFalse(playerGameLossesPage.isGameCategoryFieldEnabled());
        assertTrue(playerGameLossesPage.areAllGameFieldsEnabled());
        conditionSectionPage = playerGameLossesPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        assertConditionCreated(data);
    }

    @Test(dataProvider = "getSuspendedData", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateCheckSuspended(GameLossesPlayerReportEntity data) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillForm(data);
        conditionSectionPage = playerGameLossesPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        //THEN
        assertConditionCreated(data);
    }

    @Test(dataProvider = "getFrozenData", dataProviderClass = GameLossesPlayerReportData.class)
    public void validateCheckFrozen(GameLossesPlayerReportEntity data) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(data.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(data.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(data.getReportType());
        playerGameLossesPage.fillForm(data);
        conditionSectionPage = playerGameLossesPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        //THEN
        assertConditionCreated(data);
    }

    @Test(dataProvider = "getCreateEditConditionData", dataProviderClass = GameLossesPlayerReportData.class)
    public void createAndEditCondition(GameLossesPlayerReportEntity createData, GameLossesPlayerReportEntity editData) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(createData.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(createData.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(createData.getReportType());
        playerGameLossesPage.fillForm(createData);
        conditionSectionPage = playerGameLossesPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        //THEN
        assertConditionCreated(createData);
        playerGameLossesPage = conditionSectionPage.editConditionPlayerGameLossesReport(0);
        playerGameLossesPage.setGameCategory("All");
        playerGameLossesPage.fillForm(editData);
        conditionSectionPage = playerGameLossesPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        assertConditionCreated(editData);
    }

    @Test(dataProvider = "getPressCancelData", dataProviderClass = GameLossesPlayerReportData.class)
    public void checkPressCancel(GameLossesPlayerReportEntity dataToFill, GameLossesPlayerReportEntity defaultData) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(dataToFill.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(dataToFill.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(dataToFill.getReportType());
        playerGameLossesPage.fillForm(dataToFill);
        //THEN
        assertEquals(dataToFill,playerGameLossesPage.getAllFormValues());
        conditionSectionPage = playerGameLossesPage.clickCancel();
        conditionDialog = conditionSectionPage.addCondition(dataToFill.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(dataToFill.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(dataToFill.getReportType());
        assertEquals(defaultData,playerGameLossesPage.getAllFormValues());
    }

    @Test(dataProvider = "getGameFieldsAndGameCategoryData", dataProviderClass = GameLossesPlayerReportData.class)
    public void checkGameCategoryAndGamesDisabling(GameLossesPlayerReportEntity dataToFill, Boolean expectedGameCategoryEnabled, Boolean expectedGamesEnabled) {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(dataToFill.getConditionToAdd());
        customReportDialog = (CustomReportDialogPage) conditionDialog.selectConditionType(dataToFill.getCondition());
        playerGameLossesPage = (GameLossesPlayerReportPage) customReportDialog.addReport(dataToFill.getReportType());
        playerGameLossesPage.fillForm(dataToFill);
        //THEN
        assertEquals(dataToFill,playerGameLossesPage.getAllFormValues());
        assertEquals(playerGameLossesPage.areAllGameFieldsEnabled(), expectedGamesEnabled);
        assertEquals(playerGameLossesPage.isGameCategoryFieldEnabled(), expectedGameCategoryEnabled);
        conditionSectionPage = playerGameLossesPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        assertConditionCreated(dataToFill);
        playerGameLossesPage = conditionSectionPage.editConditionPlayerGameLossesReport(0);
        assertEquals(playerGameLossesPage.areAllGameFieldsEnabled(), expectedGamesEnabled);
        assertEquals(playerGameLossesPage.isGameCategoryFieldEnabled(), expectedGameCategoryEnabled);
    }
}
