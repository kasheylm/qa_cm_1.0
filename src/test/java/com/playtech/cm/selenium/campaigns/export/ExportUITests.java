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
import com.playtech.cm.utils.dataProviders.CampaignExportData;
import com.playtech.cm.utils.entities.campaignExport.CampaignExportEntity;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * User: Denis Veselovskiy
 * Date: 10/22/12, Time: 4:23 PM
 */
public class ExportUITests extends BaseExport{
    CMDashboardPage cmDashboardPage;
    CampaignTabs campaignTabs;
    CampaignExportPage campaignExportPage;
    CampaignTable campaignTable;
    CampaignExportEntity expectedData;
    CampaignExportEntity actualData;
    Boolean isPreviousTestFailed;
    String campaignName = "CampaignWithoutTestGroups";

    @BeforeClass
    public void setupDatabaseAndOpenCampaignDetails(){
        new InitDb("campaignExport/TwoCampaignsTestAndNonTest.xml");
        isPreviousTestFailed = false;
        cmDashboardPage = goToCMDashboardDirectly();
        campaignTabs = cmDashboardPage.clickEdit(campaignName);
    }

    @BeforeMethod
    public void openExportScreen() {
        //GIVEN
        expectedData = new CampaignExportEntity(false);
        actualData = new CampaignExportEntity(false);
        if(isPreviousTestFailed){
            setupDatabaseAndOpenCampaignDetails();
        }
        campaignExportPage = campaignTabs.clickExport();
    }

    @AfterMethod
    public void checkIfTestIsFailed(ITestContext c, ITestResult r, Method m){
        if(!r.isSuccess()){
            isPreviousTestFailed = true;
        } else {
            isPreviousTestFailed = false;
            campaignTabs = campaignExportPage.clickCancel();
        }
    }

    @Test
    public void checkUniqueActionedPlayerDefaultsTest() {
        expectedData.fillDefaultUniqueExportEntity();
//        WHEN
        campaignExportPage.selectExportType(expectedData.getExportType());
        campaignExportPage.typeExportFileName("");
        actualData = campaignExportPage.getFieldsWithoutTestGroups();
//        THEN
        assertObjectsEquals(expectedData, actualData);
    }

    @Test
    public void checkActionedPlayerHistoryDefaultsTest() {
//        WHEN
        campaignExportPage.selectExportType(expectedData.getExportType());
        campaignExportPage.typeExportFileName("");
        actualData = campaignExportPage.getFieldsWithoutTestGroups();
//        THEN
        assertObjectsEquals(expectedData, actualData);
    }
}
