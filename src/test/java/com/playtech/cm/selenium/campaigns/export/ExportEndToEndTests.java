package com.playtech.cm.selenium.campaigns.export;

import com.playtech.cm.db.InitDb;
import com.playtech.cm.elements.CampaignTable;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignDetailsPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignExportPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignTabs;
import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.pages.dashboard.campaign.activities.details.ActivityDetailsPage;
import com.playtech.cm.utils.dataProviders.CampaignData;
import com.playtech.cm.utils.entities.campaignExport.CampaignExportEntity;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * User: Denis Veselovskiy
 * Date: 10/22/12, Time: 4:23 PM
 */
public class ExportEndToEndTests extends BaseExport {
    CampaignData campaignData;
    CampaignDetailsPage campaignDetailsPage;
    CMDashboardPage cmDashboardPage;
    CampaignTabs campaignTabs;
    ActivityTabPage activityTab;
    CampaignExportPage campaignExportPage;
    CampaignTable campaignTable;
    ActivityDetailsPage activityDetailsPage;
    CampaignExportEntity expectedData;
    CampaignExportEntity actualData;  
    String campaignNameWithTG = "CampaignWithTestGroups";
    String campaignNameNoTG = "CampaignWithoutTestGroups";

    @BeforeMethod
    public void openExportScreen() {
        //GIVEN
        new InitDb("campaignExport/TwoCampaignsTestAndNonTest.xml");
        expectedData = new CampaignExportEntity(false);
        actualData = new CampaignExportEntity(false);
        cmDashboardPage = goToCMDashboardDirectly();
    }

    @Test
    public void exportOneTestGroupTest(Method m) {
        expectedData.fillDefaultUniqueExportEntity();
        expectedData.setTestGroups(Arrays.asList("Test Group 1"));
        expectedData.setAvailableFields(expectedData.allTestActivityFieldsUniquePlayers);
        expectedData.setSelectedFields(Arrays.asList("Username", "Test Group"));
        expectedData.removeAvailableFields(expectedData.getSelectedFields());
        expectedData.setExportFileName(m.getName());
        expectedData.setExportFileFormat("XML");
//        WHEN
        campaignTable = cmDashboardPage.getCampaignTable();
        campaignTabs = campaignTable.getCampaignRow(campaignNameWithTG).clickEdit();
        campaignExportPage = campaignTabs.clickExport();
        campaignExportPage.setAllFields(expectedData);
        campaignExportPage.clickExportButton();
//        THEN
        waitUntilFileDownloaded(expectedData.getExportFileName(), expectedData.getExportFileFormat());
        assertObjectsEquals(expectedData, campaignExportPage.getAllFields());
        compareXML(expectedData.getExportFileName());
    }

    @Test
    public void exportTestGroupsByDefaultTest(Method m) {
        expectedData.fillDefaultUniqueExportEntity();
        expectedData.setTestGroups(Arrays.asList("Control Group", "Test Group 1", "Test Group 2"));
        expectedData.setAvailableFields(expectedData.allTestActivityFieldsUniquePlayers);
        expectedData.setSelectedFields(Arrays.asList("Username", "Test Group"));
        expectedData.removeAvailableFields(expectedData.getSelectedFields());
        expectedData.setExportFileName(m.getName());
        expectedData.setExportFileFormat("XML");
//        WHEN
        campaignTable = cmDashboardPage.getCampaignTable();
        campaignTabs = campaignTable.getCampaignRow(campaignNameWithTG).clickEdit();
        campaignExportPage = campaignTabs.clickExport();
        campaignExportPage.selectExportType(expectedData.getExportType());
        campaignExportPage.selectExportActivities(expectedData.getExportActivities());
        campaignExportPage.selectAvailableExportFields(expectedData.getSelectedFields());
        campaignExportPage.clickAddExportFields();
        campaignExportPage.typeExportFileName(expectedData.getExportFileName());
        campaignExportPage.selectExportType(expectedData.getExportType());
        campaignExportPage.selectExportFileFormat(expectedData.getExportFileFormat());
        campaignExportPage.clickExportButton();
//        THEN
        waitUntilFileDownloaded(expectedData.getExportFileName(), expectedData.getExportFileFormat());
        assertObjectsEquals(expectedData, campaignExportPage.getAllFields());
        compareXML(expectedData.getExportFileName());
    }

    @Test
    public void exportAllTestGroupsTest(Method m) {
        expectedData.fillDefaultUniqueExportEntity();
        expectedData.setTestGroups(Arrays.asList("Control Group", "Test Group 1", "Test Group 2"));
        expectedData.setAvailableFields(expectedData.allTestActivityFieldsUniquePlayers);
        expectedData.setSelectedFields(Arrays.asList("Username", "Test Group"));
        expectedData.removeAvailableFields(expectedData.getSelectedFields());
        expectedData.setExportFileName(m.getName());
        expectedData.setExportFileFormat("XML");
//        WHEN
        campaignTable = cmDashboardPage.getCampaignTable();
        campaignTabs = campaignTable.getCampaignRow(campaignNameWithTG).clickEdit();
        campaignExportPage = campaignTabs.clickExport();
        campaignExportPage.setAllFields(expectedData);
        campaignExportPage.clickExportButton();
//        THEN
        waitUntilFileDownloaded(expectedData.getExportFileName(), expectedData.getExportFileFormat());
        assertObjectsEquals(expectedData, campaignExportPage.getAllFields());
        compareXML(expectedData.getExportFileName());
    }

    @Test
    public void matchCampaignDatesTest(Method m) {
        expectedData.fillDefaultHistoryExportEntity();
        expectedData.setActionedFrom("2013-03-16");
        expectedData.setActionedTo("2013-03-18");
        expectedData.setSelectedFields(Arrays.asList("Username"));
        expectedData.removeAvailableFields(expectedData.getSelectedFields());
        expectedData.setExportFileName(m.getName());
//        WHEN
        campaignTable = cmDashboardPage.getCampaignTable();
        campaignTabs = campaignTable.getCampaignRow(campaignNameNoTG).clickEdit();
        campaignExportPage = campaignTabs.clickExport();
        campaignExportPage.setFieldsWithoutTestGroups(expectedData);
        campaignExportPage.clickExportButton();
//        THEN
        waitUntilFileDownloaded(expectedData.getExportFileName(), expectedData.getExportFileFormat());
        assertObjectsEquals(expectedData, campaignExportPage.getFieldsWithoutTestGroups());
        compareCSV(expectedData.getExportFileName());
    }

    @Test
    public void betweenStartAndEndDatesTest(Method m) {
        expectedData.fillDefaultHistoryExportEntity();
        expectedData.setActionedFrom("2013-03-15");
        expectedData.setActionedTo("2013-03-17");
        expectedData.setSelectedFields(Arrays.asList("Username"));
        expectedData.removeAvailableFields(expectedData.getSelectedFields());
        expectedData.setExportFileName(m.getName());
//        WHEN
        campaignTable = cmDashboardPage.getCampaignTable();
        campaignTabs = campaignTable.getCampaignRow(campaignNameNoTG).clickEdit();
        campaignExportPage = campaignTabs.clickExport();
        campaignExportPage.setFieldsWithoutTestGroups(expectedData);
        campaignExportPage.clickExportButton();
//        THEN
        waitUntilFileDownloaded(expectedData.getExportFileName(), expectedData.getExportFileFormat());
        assertObjectsEquals(expectedData, campaignExportPage.getFieldsWithoutTestGroups());
        compareCSV(expectedData.getExportFileName());
    }

    @Test
    public void fromDateBetweenCampaignDatesTest(Method m) {
        expectedData.fillDefaultHistoryExportEntity();
        expectedData.setActionedFrom("2013-03-16");
        expectedData.setSelectedFields(Arrays.asList("Username"));
        expectedData.removeAvailableFields(expectedData.getSelectedFields());
        expectedData.setExportFileName(m.getName());
//        WHEN
        campaignTable = cmDashboardPage.getCampaignTable();
        campaignTabs = campaignTable.getCampaignRow(campaignNameNoTG).clickEdit();
        campaignExportPage = campaignTabs.clickExport();
        campaignExportPage.setFieldsWithoutTestGroups(expectedData);
        campaignExportPage.clickExportButton();
//        THEN
        waitUntilFileDownloaded(expectedData.getExportFileName(), expectedData.getExportFileFormat());
        assertObjectsEquals(expectedData, campaignExportPage.getFieldsWithoutTestGroups());
        compareCSV(expectedData.getExportFileName());
    }

    @Test
    public void toDateIsLessThanCampaignStartDateTest(Method m) {
        expectedData.fillDefaultHistoryExportEntity();
        expectedData.setActionedTo("2012-10-01");
        expectedData.setSelectedFields(Arrays.asList("Username"));
        expectedData.removeAvailableFields(expectedData.getSelectedFields());
        expectedData.setExportFileName(m.getName());
//        WHEN
        campaignTable = cmDashboardPage.getCampaignTable();
        campaignTabs = campaignTable.getCampaignRow(campaignNameNoTG).clickEdit();
        campaignExportPage = campaignTabs.clickExport();
        campaignExportPage.setFieldsWithoutTestGroups(expectedData);
        campaignExportPage.clickExportButton();
//        THEN
        waitUntilFileDownloaded(expectedData.getExportFileName(), expectedData.getExportFileFormat());
        assertObjectsEquals(expectedData, campaignExportPage.getFieldsWithoutTestGroups());
        compareCSV(expectedData.getExportFileName());
    }

    @Test
    public void fromDateIsGreaterThanCampaignEndDateTest(Method m) {
        expectedData.fillDefaultHistoryExportEntity();
        expectedData.setActionedFrom("2014-11-06");
        expectedData.setSelectedFields(Arrays.asList("Username"));
        expectedData.removeAvailableFields(expectedData.getSelectedFields());
        expectedData.setExportFileName(m.getName());
//        WHEN
        campaignTable = cmDashboardPage.getCampaignTable();
        campaignTabs = campaignTable.getCampaignRow(campaignNameNoTG).clickEdit();
        campaignExportPage = campaignTabs.clickExport();
        campaignExportPage.setFieldsWithoutTestGroups(expectedData);
        campaignExportPage.clickExportButton();
//        THEN
        waitUntilFileDownloaded(expectedData.getExportFileName(), expectedData.getExportFileFormat());
        assertObjectsEquals(expectedData, campaignExportPage.getFieldsWithoutTestGroups());
        compareCSV(expectedData.getExportFileName());
    }

    @Test
    public void toDateBetweenCampaignDatesTest(Method m) {
        expectedData.fillDefaultHistoryExportEntity();
        expectedData.setActionedTo("2013-03-16");
        expectedData.setSelectedFields(Arrays.asList("Username"));
        expectedData.removeAvailableFields(expectedData.getSelectedFields());
        expectedData.setExportFileName(m.getName());
//        WHEN
        campaignTable = cmDashboardPage.getCampaignTable();
        campaignTabs = campaignTable.getCampaignRow(campaignNameNoTG).clickEdit();
        campaignExportPage = campaignTabs.clickExport();
        campaignExportPage.setFieldsWithoutTestGroups(expectedData);
        campaignExportPage.clickExportButton();
//        THEN
        waitUntilFileDownloaded(expectedData.getExportFileName(), expectedData.getExportFileFormat());
        assertObjectsEquals(expectedData, campaignExportPage.getFieldsWithoutTestGroups());
        compareCSV(expectedData.getExportFileName());
    }

    @Test
    public void exportAllFieldsNonTestHistoryCsvTest(Method m) {
        expectedData.fillDefaultHistoryExportEntity();
        expectedData.setSelectedFields(expectedData.getAvailableFields());
        expectedData.setAvailableFields(new ArrayList<String>());
        expectedData.setExportFileName(m.getName());
//        WHEN
        campaignTable = cmDashboardPage.getCampaignTable();
        campaignTabs = campaignTable.getCampaignRow(campaignNameNoTG).clickEdit();
        campaignExportPage = campaignTabs.clickExport();
        campaignExportPage.setFieldsWithoutTestGroups(expectedData);
        campaignExportPage.clickExportButton();
//        THEN
        waitUntilFileDownloaded(expectedData.getExportFileName(), expectedData.getExportFileFormat());
        assertObjectsEquals(expectedData, campaignExportPage.getFieldsWithoutTestGroups());
        compareCSV(expectedData.getExportFileName());
    }

    @Test
    public void exportAllFieldsNonTestHistoryXmlTest(Method m) {
        expectedData.fillDefaultHistoryExportEntity();
        expectedData.setSelectedFields(expectedData.getAvailableFields());
        expectedData.setAvailableFields(new ArrayList<String>());
        expectedData.setExportFileName(m.getName());
        expectedData.setExportFileFormat("XML");
//        WHEN
        campaignTable = cmDashboardPage.getCampaignTable();
        campaignTabs = campaignTable.getCampaignRow(campaignNameNoTG).clickEdit();
        campaignExportPage = campaignTabs.clickExport();
        campaignExportPage.setFieldsWithoutTestGroups(expectedData);
        campaignExportPage.clickExportButton();
//        THEN
        waitUntilFileDownloaded(expectedData.getExportFileName(), expectedData.getExportFileFormat());
        assertObjectsEquals(expectedData, campaignExportPage.getFieldsWithoutTestGroups());
        compareXML(expectedData.getExportFileName());
    }

    @Test
    public void exportAllFieldsNonTestUniqueCsvTest(Method m) {
        expectedData.fillDefaultUniqueExportEntity();
        expectedData.setSelectedFields(expectedData.getAvailableFields());
        expectedData.setAvailableFields(new ArrayList<String>());
        expectedData.setExportFileName(m.getName());
//        WHEN
        campaignTable = cmDashboardPage.getCampaignTable();
        campaignTabs = campaignTable.getCampaignRow(campaignNameNoTG).clickEdit();
        campaignExportPage = campaignTabs.clickExport();
        campaignExportPage.setFieldsWithoutTestGroups(expectedData);
        campaignExportPage.clickExportButton();
//        THEN
        waitUntilFileDownloaded(expectedData.getExportFileName(), expectedData.getExportFileFormat());
        assertObjectsEquals(expectedData, campaignExportPage.getFieldsWithoutTestGroups());
        compareCSV(expectedData.getExportFileName());
    }

    @Test
    public void exportAllFieldsNonTestUniqueXmlTest(Method m) {
        expectedData.fillDefaultUniqueExportEntity();
        expectedData.setSelectedFields(expectedData.getAvailableFields());
        expectedData.setAvailableFields(new ArrayList<String>());
        expectedData.setExportFileName(m.getName());
        expectedData.setExportFileFormat("XML");
//        WHEN
        campaignTable = cmDashboardPage.getCampaignTable();
        campaignTabs = campaignTable.getCampaignRow(campaignNameNoTG).clickEdit();
        campaignExportPage = campaignTabs.clickExport();
        campaignExportPage.setFieldsWithoutTestGroups(expectedData);
        campaignExportPage.clickExportButton();
//        THEN
        waitUntilFileDownloaded(expectedData.getExportFileName(), expectedData.getExportFileFormat());
        assertObjectsEquals(expectedData, campaignExportPage.getFieldsWithoutTestGroups());
        compareXML(expectedData.getExportFileName());
    }

    @Test
    public void noFieldsSelectedNonTestHistoryXmlTest(Method m) {
        expectedData.fillDefaultHistoryExportEntity();
        expectedData.setExportFileName(m.getName());
        expectedData.setExportFileFormat("XML");
//        WHEN
        campaignTable = cmDashboardPage.getCampaignTable();
        campaignTabs = campaignTable.getCampaignRow(campaignNameNoTG).clickEdit();
        campaignExportPage = campaignTabs.clickExport();
        campaignExportPage.setFieldsWithoutTestGroups(expectedData);
        campaignExportPage.clickExportButton();
//        THEN
        waitUntilFileDownloaded(expectedData.getExportFileName(), expectedData.getExportFileFormat());
        assertObjectsEquals(expectedData, campaignExportPage.getFieldsWithoutTestGroups());
        compareXML(expectedData.getExportFileName());
    }

    @Test
    public void noFieldsSelectedNonTestUniqueCsvTest(Method m) {
        expectedData.fillDefaultUniqueExportEntity();
        expectedData.setExportFileName(m.getName());
//        WHEN
        campaignTable = cmDashboardPage.getCampaignTable();
        campaignTabs = campaignTable.getCampaignRow(campaignNameNoTG).clickEdit();
        campaignExportPage = campaignTabs.clickExport();
        campaignExportPage.setFieldsWithoutTestGroups(expectedData);
        campaignExportPage.clickExportButton();
//        THEN
        waitUntilFileDownloaded(expectedData.getExportFileName(), expectedData.getExportFileFormat());
        assertObjectsEquals(expectedData, campaignExportPage.getFieldsWithoutTestGroups());
        compareCSV(expectedData.getExportFileName());
    }



    @Test
    public void selectSomeFieldsCsvTest(Method m) {
        expectedData.fillDefaultUniqueExportEntity();
        expectedData.setTestGroups(Arrays.asList("Control Group", "Test Group 1", "Test Group 2"));
        expectedData.setAvailableFields(expectedData.allTestActivityFieldsUniquePlayers);
        expectedData.setSelectedFields(Arrays.asList("Username", "Activity", "Action Count"));
        expectedData.removeAvailableFields(expectedData.getSelectedFields());
        expectedData.setExportFileName(m.getName());
//        WHEN
        campaignTable = cmDashboardPage.getCampaignTable();
        campaignTabs = campaignTable.getCampaignRow(campaignNameWithTG).clickEdit();
        campaignExportPage = campaignTabs.clickExport();
        campaignExportPage.setAllFields(expectedData);
        campaignExportPage.clickExportButton();
//        THEN
        waitUntilFileDownloaded(expectedData.getExportFileName(), expectedData.getExportFileFormat());
        assertObjectsEquals(expectedData, campaignExportPage.getAllFields());
        compareCSV(expectedData.getExportFileName());
    }

    @Test
    public void selectSomeFieldsXmlTest(Method m) {
        expectedData.fillDefaultUniqueExportEntity();
        expectedData.setTestGroups(Arrays.asList("Control Group", "Test Group 1", "Test Group 2"));
        expectedData.setAvailableFields(expectedData.allTestActivityFieldsUniquePlayers);
        expectedData.setSelectedFields(Arrays.asList("Username", "Activity", "Action Count"));
        expectedData.removeAvailableFields(expectedData.getSelectedFields());
        expectedData.setExportFileName(m.getName());
        expectedData.setExportFileFormat("XML");
//        WHEN
        campaignTable = cmDashboardPage.getCampaignTable();
        campaignTabs = campaignTable.getCampaignRow(campaignNameWithTG).clickEdit();
        campaignExportPage = campaignTabs.clickExport();
        campaignExportPage.setAllFields(expectedData);
        campaignExportPage.clickExportButton();
//        THEN
        waitUntilFileDownloaded(expectedData.getExportFileName(), expectedData.getExportFileFormat());
        assertObjectsEquals(expectedData, campaignExportPage.getAllFields());
        compareXML(expectedData.getExportFileName());
    }
}
