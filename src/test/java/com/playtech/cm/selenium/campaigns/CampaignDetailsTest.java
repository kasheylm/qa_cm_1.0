package com.playtech.cm.selenium.campaigns;

import com.playtech.cm.BaseTest;
import com.playtech.cm.db.DbUnitUtil;
import com.playtech.cm.pages.dashboard.newCampaign.NewCampaignPage;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignDetailsPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignTabs;
import com.playtech.cm.utils.Config;
import com.playtech.cm.utils.dataProviders.CampaignData;
import com.playtech.cm.utils.entities.Category;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * User: Denis Veselovskiy
 * Date: 9/14/12 * Time: 12:09 PM
 */
public class CampaignDetailsTest extends BaseTest{
    CampaignTabs campaignTabs;
    CMDashboardPage cmDashboardPage;
    CampaignDetailsPage campaignDetailsPage;
    NewCampaignPage newCampaignPage;
    String errorName = "Name should not be empty.";
    String errorStartDate = "You must provide a value for Campaign Start Date.";

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
    public void leaveNameEmpty() {
        //given
        CampaignData data  = new CampaignData();
        //when
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        data.setName("");
        campaignDetailsPage.fillRequiredFields(data);
        campaignDetailsPage.clickSave();
        //then
        assertEquals(campaignDetailsPage.getErrorsCount(), 1);
        assertTrue(campaignDetailsPage.isErrorPresent(errorName));
    }

    @Test
    public void leaveStartDateEmpty() {
        //given
        CampaignData data  = new CampaignData();
        //when
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        data.setStartDate("");
        campaignDetailsPage.fillRequiredFields(data);
        campaignDetailsPage.clickSave();
        //then
        assertEquals(campaignDetailsPage.getErrorsCount(), 1);
        assertTrue(campaignDetailsPage.isErrorPresent(errorStartDate));
    }

    @Test
    public void leaveNameAndStartDateFieldsEmpty() {
        //given
        CampaignData data  = new CampaignData();
        //when
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        data.setName("");
        data.setStartDate("");
        campaignDetailsPage.fillRequiredFields(data);
        campaignDetailsPage.clickSave();
        //then
        assertEquals(campaignDetailsPage.getErrorsCount(), 2);
        assertTrue(campaignDetailsPage.isErrorPresent(errorName));
        assertTrue(campaignDetailsPage.isErrorPresent(errorStartDate));
    }

    @Test
    public void checkSessionCleaned() {
        //given
        CampaignData data  = new CampaignData();
        //when
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        data.setStartDate("");
        campaignDetailsPage.fillRequiredFields(data);
        campaignDetailsPage.clickSave();
        //then
        assertEquals(campaignDetailsPage.getErrorsCount(), 1);
        assertTrue(campaignDetailsPage.isErrorPresent(errorStartDate));
        data.setStartDate(Config.getDate());
        campaignDetailsPage.fillRequiredFields(data);
        campaignDetailsPage.clickSave();
        assertEquals(campaignDetailsPage.getErrorsCount(), 0);
        assertFalse(campaignDetailsPage.isErrorPresent(errorStartDate));
    }

    @Test
    public void setNameAlreadyExists() {
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
        assertEquals(campaignDetailsPage.getErrorsCount(), 1);
        assertTrue(newCampaignPage.isErrorPresent(getErrorSameNameText(campaignName)));
        newCampaignPage.clickCancel();
    }

    @Test
    public void fillAllFields() {
        //given
        CampaignData data  = new CampaignData();
        //when
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        campaignDetailsPage.fillForm(data);
        campaignDetailsPage.clickSave();
        //then
        checkCampaignCreated(data);
        cmDashboardPage = campaignDetailsPage.clickHomeLink();
        campaignTabs = cmDashboardPage.clickEdit(data.getName());
        campaignDetailsPage = campaignTabs.openCampaignDetailsTab();
        checkCampaignCreated(data);
    }

    @Test
    public void editCampaignSaveChanges() {
        //given
        CampaignData data  = new CampaignData();
        String campaignOldName = data.getName();
        //when
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        cmDashboardPage = campaignDetailsPage.clickHomeLink();
        assertTrue(cmDashboardPage.isCampaignExist(campaignOldName));
        campaignTabs = cmDashboardPage.clickEdit(campaignOldName);
        campaignDetailsPage = campaignTabs.openCampaignDetailsTab();
        CampaignData dataNew  = new CampaignData();
        dataNew.setCategory(Category.CONVERSION);
        campaignDetailsPage.fillForm(dataNew);
        campaignDetailsPage.clickSave();
        //then
        checkCampaignCreated(dataNew);
        cmDashboardPage = campaignDetailsPage.clickHomeLink();
        assertTrue(cmDashboardPage.isCampaignExist(dataNew.getName()));
        assertFalse(cmDashboardPage.isCampaignExist(campaignOldName));
        campaignTabs = cmDashboardPage.clickEdit(dataNew.getName());
        campaignDetailsPage = campaignTabs.openCampaignDetailsTab();
        checkCampaignCreated(dataNew);
    }

    @Test
    public void editCampaignCancelChanges() {
        //given
        CampaignData data  = new CampaignData();
        String campaignOldName = data.getName();
        //when
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        campaignDetailsPage.fillForm(data);
        campaignDetailsPage.clickSave();
        cmDashboardPage = campaignDetailsPage.clickHomeLink();
        assertTrue(cmDashboardPage.isCampaignExist(campaignOldName));
        campaignTabs = cmDashboardPage.clickEdit(campaignOldName);
        campaignDetailsPage = campaignTabs.openCampaignDetailsTab();
        CampaignData dataNew  = new CampaignData();
        dataNew.setCategory(Category.CONVERSION);
        campaignDetailsPage.fillForm(dataNew);
        cmDashboardPage = campaignDetailsPage.clickHomeLinkWithAlert();
        //then
        assertTrue(cmDashboardPage.isCampaignExist(campaignOldName));
        assertFalse(cmDashboardPage.isCampaignExist(dataNew.getName()));
        campaignTabs = cmDashboardPage.clickEdit(campaignOldName);
        campaignDetailsPage = campaignTabs.openCampaignDetailsTab();
        checkCampaignCreated(data);
    }

    @Test(dataProvider = "validateFieldName", dataProviderClass = CampaignData.class )
    public void validateNameField(String campaignName) {
        //given
        CampaignData data  = new CampaignData();
        data.setName(campaignName);
        //when
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        campaignDetailsPage.fillForm(data);
        campaignDetailsPage.clickSave();
        //then
        checkCampaignCreated(data);
        cmDashboardPage = campaignDetailsPage.clickHomeLink();
        campaignTabs = cmDashboardPage.clickEdit(data.getName());
        campaignDetailsPage = campaignTabs.openCampaignDetailsTab();
        checkCampaignCreated(data);
    }

    @Test(dataProvider = "validateFieldDescription", dataProviderClass = CampaignData.class )
    public void validateDescriptionField(String description) {
        //given
        CampaignData data  = new CampaignData();
        data.setDescription(description);
        //when
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        campaignDetailsPage.fillForm(data);
        campaignDetailsPage.clickSave();
        //then
        checkCampaignCreated(data);
        cmDashboardPage = campaignDetailsPage.clickHomeLink();
        campaignTabs = cmDashboardPage.clickEdit(data.getName());
        campaignDetailsPage = campaignTabs.openCampaignDetailsTab();
        checkCampaignCreated(data);
    }

    @Test(dataProvider = "validateFieldTags", dataProviderClass = CampaignData.class )
    public void validateTagsField(String tags) {
        //given
        CampaignData data  = new CampaignData();
        data.setTags(tags);
        //when
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        campaignDetailsPage.fillForm(data);
        campaignDetailsPage.clickSave();
        //then
        cmDashboardPage = campaignDetailsPage.clickHomeLink();
        campaignTabs = cmDashboardPage.clickEdit(data.getName());
        campaignDetailsPage = campaignTabs.openCampaignDetailsTab();

        if (tags.contains("tag1")) {
            String[] result = tags.split("[,;]\\s?");
            for (String tag : result) {
                assertTrue(campaignDetailsPage.getTags().contains(tag));
            }
        }
        else assertEquals(campaignDetailsPage.getTags(), data.getTags());
    }

    @Test(dataProvider = "validateFieldLinks", dataProviderClass = CampaignData.class )
    public void validateLinksField(String link) {
        //given
        CampaignData data  = new CampaignData();
        data.setLinks(link);
        //when
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        campaignDetailsPage.fillForm(data);
        campaignDetailsPage.clickSave();
        //then
        checkCampaignCreated(data);
        cmDashboardPage = campaignDetailsPage.clickHomeLink();
        campaignTabs = cmDashboardPage.clickEdit(data.getName());
        campaignDetailsPage = campaignTabs.openCampaignDetailsTab();
        checkCampaignCreated(data);
    }

    private void checkCampaignCreated(CampaignData data){
        assertEquals(campaignDetailsPage.getName(), data.getName());
        assertEquals(campaignDetailsPage.getInstance(), data.getCasinoBrand());
        assertEquals(campaignDetailsPage.getCategory(), data.getCategory());
        assertEquals(campaignDetailsPage.getDescription(), data.getDescription());
        assertEquals(campaignDetailsPage.getOngoing(), data.getOngoing());
        assertEquals(campaignDetailsPage.getStartDate(), data.getStartDate());
        assertEquals(campaignDetailsPage.getPublicationDate(), data.getPublicationDate());
        assertEquals(campaignDetailsPage.getEndDate(), data.getEndDate());
        assertEquals(campaignDetailsPage.getTags(), data.getTags());
        assertEquals(campaignDetailsPage.getLinks(), data.getLinks());
    }
}
