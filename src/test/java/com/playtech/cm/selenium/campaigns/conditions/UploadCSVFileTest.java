package com.playtech.cm.selenium.campaigns.conditions;

import com.playtech.cm.BaseTest;
import com.playtech.cm.db.DbUnitUtil;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignDetailsPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignTabs;
import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.pages.dashboard.campaign.activities.conditions.ConditionDialogPage;
import com.playtech.cm.pages.dashboard.campaign.activities.conditions.ConditionSectionPage;
import com.playtech.cm.pages.dashboard.campaign.activities.conditions.UploadCSVPage;
import com.playtech.cm.utils.dataProviders.CampaignData;
import com.playtech.cm.utils.entities.conditions.UploadCSVEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * User: Denis Veselovskiy
 * Date: 10/17/12 * Time: 5:29 PM
 */
public class UploadCSVFileTest extends BaseTest {
    CampaignData campaignData;
    String activityName;
    String activityID;
    UploadCSVEntity expectedEntity;
    UploadCSVEntity actualEntity;
    CMDashboardPage cmDashboardPage;
    CampaignDetailsPage campaignDetailsPage;
    CampaignTabs campaignTabs;
    ActivityTabPage activityTab;
    ConditionSectionPage conditionSectionPage;
    ConditionDialogPage conditionDialog;
    UploadCSVPage uploadCSVPage;

    String errorEmpty = "Please, upload CSV file.";
    String errorNotCSV = "Only CSV file is allowed to upload.";


    @BeforeMethod
    public void createNewCampaign() {
        //GIVEN
        DbUnitUtil.clean("demodb-jdbc.properties");
        campaignData = new CampaignData();
        cmDashboardPage = goToCMDashboardDirectly();
        campaignDetailsPage = cmDashboardPage.createCampaign(campaignData);
        campaignDetailsPage.fillForm(campaignData);
        campaignTabs = campaignDetailsPage.clickSave();
        activityTab = campaignTabs.openActivityTab();
        activityName = activityTab.getOpenedDetails().getActivityName();
        activityID = activityTab.getOpenedDetails().getActivityID();
        conditionSectionPage = activityTab.clickConditions();
        expectedEntity = new UploadCSVEntity();
        actualEntity = new UploadCSVEntity();
    }

    @Test
    public void saveFormWithoutFile() {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(expectedEntity.getConditionToAdd());
        uploadCSVPage = (UploadCSVPage) conditionDialog.selectConditionType(expectedEntity.getCondition());
        uploadCSVPage.clickOk();
        //THEN
        assertEquals(uploadCSVPage.getErrorsCount(), 1);
        assertTrue(uploadCSVPage.isErrorPresent(errorEmpty));
    }

    @Test
    public void uploadNotCsvFile() {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(expectedEntity.getConditionToAdd());
        uploadCSVPage = (UploadCSVPage) conditionDialog.selectConditionType(expectedEntity.getCondition());
        uploadCSVPage.switchToFrame();
        uploadCSVPage.uploadFile("pic.jpg");

        //THEN
        assertEquals(uploadCSVPage.getErrorsCount(), 1);
        assertTrue(uploadCSVPage.isErrorPresent(errorNotCSV));
    }

    @Test
    public void uploadTwoCsvFiles() {
        //GIVEN
        String tempFile = "withHeaders.csv";
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(expectedEntity.getConditionToAdd());
        uploadCSVPage = (UploadCSVPage) conditionDialog.selectConditionType(expectedEntity.getCondition());
        uploadCSVPage.switchToFrame();
        uploadCSVPage.uploadFile(tempFile);
        uploadCSVPage.switchToPage();
        uploadCSVPage.waitUntilFileUpload();
        uploadCSVPage.switchToFrame();
        uploadCSVPage.uploadFile(expectedEntity.getFile());
        uploadCSVPage.switchToPage();
        uploadCSVPage.waitUntilFileUpload();
        uploadCSVPage.waitUntilUploadFileTextChanges(expectedEntity.getFile());
        conditionSectionPage = uploadCSVPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        activityTab = campaignDetailsPage.openActivityTab();
        conditionSectionPage = activityTab.clickConditions();
        //THEN
        assertEquals(conditionSectionPage.getName(), expectedEntity.getName());
        assertEquals(conditionSectionPage.getSummary(), expectedEntity.getSummary());
        uploadCSVPage = conditionSectionPage.editConditionCSVFile(0);
        assertTrue(uploadCSVPage.getUploadedFileName().contains(expectedEntity.getFile()));
    }

    @Test
    public void uploadFileWithHeaders() {
        //GIVEN
        expectedEntity.setFile("withHeaders.csv");
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(expectedEntity.getConditionToAdd());
        uploadCSVPage = (UploadCSVPage) conditionDialog.selectConditionType(expectedEntity.getCondition());
        uploadCSVPage.setIncludeHeaders(true);
        uploadCSVPage.switchToFrame();
        uploadCSVPage.uploadFile(expectedEntity.getFile());
        uploadCSVPage.switchToPage();
        uploadCSVPage.waitUntilFileUpload();
        uploadCSVPage.waitUntilUploadFileTextChanges(expectedEntity.getFile());
        conditionSectionPage = uploadCSVPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        activityTab = campaignDetailsPage.openActivityTab();
        conditionSectionPage = activityTab.clickConditions();
        //THEN
        assertEquals(conditionSectionPage.getName(), expectedEntity.getName());
        assertEquals(conditionSectionPage.getSummary(), expectedEntity.getSummary());
        uploadCSVPage = conditionSectionPage.editConditionCSVFile(0);
        assertTrue(uploadCSVPage.getUploadedFileName().contains(expectedEntity.getFile()));
    }

    @Test
    public void uploadFileWithoutHeaders() {
        //GIVEN
        expectedEntity.setFile("withoutHeaders.csv");
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(expectedEntity.getConditionToAdd());
        uploadCSVPage = (UploadCSVPage) conditionDialog.selectConditionType(expectedEntity.getCondition());
        uploadCSVPage.switchToFrame();
        uploadCSVPage.uploadFile(expectedEntity.getFile());
        uploadCSVPage.switchToPage();
        uploadCSVPage.waitUntilFileUpload();
        uploadCSVPage.waitUntilUploadFileTextChanges(expectedEntity.getFile());
        conditionSectionPage = uploadCSVPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        activityTab = campaignDetailsPage.openActivityTab();
        conditionSectionPage = activityTab.clickConditions();
        //THEN
        assertEquals(conditionSectionPage.getName(), expectedEntity.getName());
        assertEquals(conditionSectionPage.getSummary(), expectedEntity.getSummary());
        uploadCSVPage = conditionSectionPage.editConditionCSVFile(0);
        assertTrue(uploadCSVPage.getUploadedFileName().contains(expectedEntity.getFile()));
    }

    @Test
    public void uploadFileAndClickCancel() {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(expectedEntity.getConditionToAdd());
        uploadCSVPage = (UploadCSVPage) conditionDialog.selectConditionType(expectedEntity.getCondition());
        uploadCSVPage.switchToFrame();
        uploadCSVPage.uploadFile(expectedEntity.getFile());
        uploadCSVPage.switchToPage();
        uploadCSVPage.waitUntilFileUpload();
        uploadCSVPage.waitUntilUploadFileTextChanges(expectedEntity.getFile());
        conditionSectionPage = uploadCSVPage.clickCancel();
        campaignDetailsPage = conditionSectionPage.clickSave();
        activityTab = campaignDetailsPage.openActivityTab();
        conditionSectionPage = activityTab.clickConditions();
        //THEN
        assertEquals(conditionSectionPage.getNameEmpty(), "");
    }

    @Test
    public void editUploadCSV() {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(expectedEntity.getConditionToAdd());
        uploadCSVPage = (UploadCSVPage) conditionDialog.selectConditionType(expectedEntity.getCondition());
        uploadCSVPage.switchToFrame();
        uploadCSVPage.uploadFile(expectedEntity.getFile());
        uploadCSVPage.switchToPage();
        uploadCSVPage.waitUntilFileUpload();
        uploadCSVPage.waitUntilUploadFileTextChanges(expectedEntity.getFile());
        conditionSectionPage = uploadCSVPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        activityTab = campaignDetailsPage.openActivityTab();
        conditionSectionPage = activityTab.clickConditions();
        assertEquals(conditionSectionPage.getName(), expectedEntity.getName());
        assertEquals(conditionSectionPage.getSummary(), expectedEntity.getSummary());
        uploadCSVPage = conditionSectionPage.editConditionCSVFile(0);
        assertTrue(uploadCSVPage.getUploadedFileName().contains(expectedEntity.getFile()));
        //edit
        expectedEntity.setFile("withHeaders.csv");
        uploadCSVPage.setIncludeHeaders(true);
        uploadCSVPage.switchToFrame();
        uploadCSVPage.uploadFile(expectedEntity.getFile());
        uploadCSVPage.switchToPage();
        uploadCSVPage.waitUntilFileUpload();
        uploadCSVPage.waitUntilUploadFileTextChanges(expectedEntity.getFile());
        conditionSectionPage = uploadCSVPage.clickOk();
        conditionSectionPage.waitUntilSummaryChangesTo(expectedEntity.getFile());
        campaignDetailsPage = conditionSectionPage.clickSave();
        activityTab = campaignDetailsPage.openActivityTab();
        conditionSectionPage = activityTab.clickConditions();
        conditionSectionPage.waitUntilSummaryChangesTo(expectedEntity.getSummary());
        //THEN
        assertEquals(conditionSectionPage.getName(), expectedEntity.getName());
        assertEquals(conditionSectionPage.getSummary(), expectedEntity.getSummary());
        uploadCSVPage = conditionSectionPage.editConditionCSVFile(0);
        assertTrue(uploadCSVPage.getUploadedFileName().contains(expectedEntity.getFile()));
        assertTrue(uploadCSVPage.getIncludeHeaders());
    }

    @Test
    public void deleteUploadCSV() {
        //WHEN
        conditionDialog = conditionSectionPage.addCondition(expectedEntity.getConditionToAdd());
        uploadCSVPage = (UploadCSVPage) conditionDialog.selectConditionType(expectedEntity.getCondition());
        uploadCSVPage.switchToFrame();
        uploadCSVPage.uploadFile(expectedEntity.getFile());
        uploadCSVPage.switchToPage();
        uploadCSVPage.waitUntilFileUpload();
        uploadCSVPage.waitUntilUploadFileTextChanges(expectedEntity.getFile());
        conditionSectionPage = uploadCSVPage.clickOk();
        conditionSectionPage.waitUntilRowAdded();
        campaignDetailsPage = conditionSectionPage.clickSave();
        campaignDetailsPage.waitForMessageDisappear();
        activityTab = campaignDetailsPage.openActivityTab();
        conditionSectionPage = activityTab.clickConditions();
        conditionSectionPage.deleteCondition(0);
        //THEN
        conditionSectionPage.waitUntilNameTextChangesToEmpty();
        assertEquals(conditionSectionPage.getNameEmpty(), "");
        campaignDetailsPage = conditionSectionPage.clickSave();
        activityTab = campaignDetailsPage.openActivityTab();
        conditionSectionPage = activityTab.clickConditions();
        assertEquals(conditionSectionPage.getNameEmpty(), "");
    }

}
