package com.playtech.cm.selenium.campaigns;

import com.playtech.cm.BaseTest;
import com.playtech.cm.db.DbUnitUtil;
import com.playtech.cm.db.InitDb;
import com.playtech.cm.pages.dashboard.newCampaign.NewCampaignPage;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignDetailsPage;
import com.playtech.cm.utils.Config;
import com.playtech.cm.utils.dataProviders.CampaignData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 User: Denis Veselovskiy
 * Date: 9/5/12
 * Time: 4:25 PM
 */
public class CreateNewCampaignTest extends BaseTest {
    CampaignDetailsPage campaignDetailsPage;
    NewCampaignPage newCampaignPage;
    CMDashboardPage cmDashboardPage;
    String errorEmptyName = "Campaign name should not be empty";
    String errorEmptyCategory = "Campaign category should not be empty.";
    String errorInstance = "Invalid casino";
    String errorLongName = "Campaign name should be less then 200 symbols";

    private String getErrorSameNameText(String campaignName) {
        return "Campaign with name \"" + campaignName + "\" exists";
    }

    @BeforeMethod
    public void openCreateNewcampaignPage() {
        //when  "Open 'Create new compaign' form"
        DbUnitUtil.clean("demodb-jdbc.properties");
        cmDashboardPage = goToCMDashboardDirectly();
        newCampaignPage = cmDashboardPage.clickNewCampaign();
    }

    @Test
    public void checkWarningsUnfilledRequiredFields() {
        //given
        newCampaignPage.clickOK();
        //then
        assertEquals(newCampaignPage.getErrorsCount(), 3);
        assertTrue(newCampaignPage.isErrorPresent(errorEmptyName));
        assertTrue(newCampaignPage.isErrorPresent(errorEmptyCategory));
        assertTrue(newCampaignPage.isErrorPresent(errorInstance));
        newCampaignPage.clickCancel();
    }

    @Test
    public void checkWarningCampaignWithOnlyEmptyName() {
        //given
        CampaignData data  = new CampaignData();
        data.setName("");
        //when
        newCampaignPage.typeName("");
        newCampaignPage.selectCategory(data.getCategory());
        newCampaignPage.selectInstance(data.getCasinoBrand());
        newCampaignPage.clickOK();
        //then
        assertEquals(newCampaignPage.getErrorsCount(), 1);
        assertTrue(newCampaignPage.isErrorPresent(errorEmptyName));
        newCampaignPage.clickCancel();
    }

    @Test
    public void checkWarningCampaignWithOnlyEmptyInstance() {
        //given
        CampaignData data  = new CampaignData();
        data.setCasinoBrand("Select instance");
        //when
        newCampaignPage.typeName(data.getName());
        newCampaignPage.selectCategory(data.getCategory());
        newCampaignPage.clickOK();
        //then
        assertEquals(newCampaignPage.getErrorsCount(), 1);
        assertTrue(newCampaignPage.isErrorPresent(errorInstance));
        newCampaignPage.clickCancel();
    }

    @Test(dataProvider = "validateLongNameWarn", dataProviderClass = CampaignData.class )
    public void checkWarningLongName(String campaignName) {
        //given
        CampaignData data  = new CampaignData();
        data.setName(campaignName);
        //when
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        //then
        //then
        assertEquals(newCampaignPage.getErrorsCount(), 1);
        assertTrue(newCampaignPage.isErrorPresent(errorLongName));
    }

    @Test
    public void checkWarningsCreateCampaignWithNameAlreadyExists() {
        //given
        CampaignData data  = new CampaignData();
        String campaignName = "Campaign_" + Config.getNow();
        data.setName(campaignName);
        //when
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        cmDashboardPage = campaignDetailsPage.clickHomeLink();

        newCampaignPage = cmDashboardPage.clickNewCampaign();
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        //then
        assertEquals(newCampaignPage.getErrorsCount(), 1);
        assertTrue(newCampaignPage.isErrorPresent(getErrorSameNameText(campaignName)));
        newCampaignPage.clickCancel();
    }

    @Test
    public void checkSessionIsClearedAfterError() {
        //given
        CampaignData data  = new CampaignData();
        data.setName("");
        //when "Fill all required fields except name and click save button"
        newCampaignPage.fillForm(data);
        newCampaignPage.clickOK();
        assertEquals(newCampaignPage.getErrorsCount(), 1);
        assertTrue(newCampaignPage.isErrorPresent(errorEmptyName));
        cmDashboardPage = newCampaignPage.clickCancel();
        newCampaignPage = cmDashboardPage.clickNewCampaign();
        //then
        assertTrue(newCampaignPage.getErrorsCount() == 0);
        assertFalse(newCampaignPage.isErrorPresent(errorEmptyName));
        newCampaignPage.clickCancel();
    }

    @Test(dataProvider = "validateFieldName", dataProviderClass = CampaignData.class )
    public void validateNameField(String campaignName) {
        //given
        CampaignData data  = new CampaignData();
        data.setName(campaignName);
        //when
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        //then
        checkCampaignData(data);
    }

    @Test
    public void campaignWithNamePreviouslyDeleted() {
        CampaignData data  = new CampaignData();
        String campaignName = "Campaign_" + Config.getNow();
        data.setName(campaignName);
        //when
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        cmDashboardPage = campaignDetailsPage.clickHomeLink();
        cmDashboardPage.clickDelete(campaignName);
        cmDashboardPage.clickNewCampaign();
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        //then
        assertEquals(campaignDetailsPage.getErrorsCount(), 0);
        checkCampaignData(data);
    }

    @Test
    public void checkCampaignCreated() {
        //given
        CampaignData data  = new CampaignData();
        //when
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        //then
        checkCampaignData(data);
    }

    private void checkCampaignData(CampaignData data){
        assertEquals(campaignDetailsPage.getName(), data.getName());
        assertEquals(campaignDetailsPage.getInstance(), data.getCasinoBrand());
        assertEquals(campaignDetailsPage.getCategory(), data.getCategory());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        try {
            assertTrue((dateFormat.parse(campaignDetailsPage.getStartDate()).getTime() -
                        dateFormat.parse(data.getStartDate()).getTime()) < 2* 60*1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        campaignDetailsPage.clickHomeLink();
        assertTrue(cmDashboardPage.isCampaignExist(data.getName()));
    }
}
