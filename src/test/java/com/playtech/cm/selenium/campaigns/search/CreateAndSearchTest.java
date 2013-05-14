package com.playtech.cm.selenium.campaigns.search;

import com.playtech.cm.BaseTest;
import com.playtech.cm.db.InitDb;
import com.playtech.cm.elements.CampaignTable;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignDetailsPage;
import com.playtech.cm.pages.dashboard.newCampaign.NewCampaignPage;
import com.playtech.cm.pages.dashboard.search.SearchPage;
import com.playtech.cm.utils.Config;
import com.playtech.cm.utils.Utils;
import com.playtech.cm.utils.dataProviders.CampaignData;
import com.playtech.cm.utils.entities.SearchData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Denis Veselovskiy
 * Date: 23/05/12
 * Time: 11:28
 */
public class CreateAndSearchTest extends BaseTest {
    CMDashboardPage cmDashboardPage;
    CampaignDetailsPage campaignDetailsPage;
    NewCampaignPage newCampaignPage;

    @BeforeClass
    public void init() {
        new InitDb("search/SearchByNameTest.xml");
    }

    @BeforeMethod
    public void openCreateNewcampaignPage() {
//        GIVEN
        cmDashboardPage = goToCMDashboardDirectly();
        newCampaignPage = cmDashboardPage.clickNewCampaign();
    }

    @Test(groups = "search")
    public void createAndSearchTest() {
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        Date start = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        cal.add(Calendar.DATE, 1);
        Date end=cal.getTime();

        String createdFromDate = dateFormat.format(start);
        String createdToDate = dateFormat.format(end);

        CampaignData data  = new CampaignData();
//        WHEN
        newCampaignPage.fillForm(data);
        campaignDetailsPage = newCampaignPage.clickOK();
        campaignDetailsPage.fillForm(data);
        campaignDetailsPage.clickSave();
        campaignDetailsPage.clickHomeLink();
        SearchPage searchPage = cmDashboardPage.getSearchPage();
        searchPage.expand();

        searchPage.typeName(data.getName());
        searchPage.selectCategory(data.getCategory());
        searchPage.selectInstance(data.getCasinoBrand());
        searchPage.selectUser(Config.login);
        searchPage.selectTestCampaign(false, true, false);
        searchPage.selectCreatedApprovedUpdated("Created");
        searchPage.selectStartDate(createdFromDate);
        searchPage.selectEndDate(data.getEndDate());
        searchPage.selectPublicationDate(data.getPublicationDate());
        searchPage.selectBetweenStartDate(createdFromDate);
        searchPage.selectBetweenEndDate(createdToDate);
        searchPage.typeTag(data.getTags());

        searchPage.clickOnSearchButton();

//        THEN
        CampaignTable campaignTable = cmDashboardPage.getCampaignTable();
        campaignTable.waitForExpandedResult(1);

        SearchData expectedData = new SearchData();
        expectedData.setName(data.getName());
        expectedData.setCategory(data.getCategory());
        expectedData.setInstance(data.getCasinoBrand());
        expectedData.setUser(Config.login);
        expectedData.setStartDate(Utils.getConvertedDateToCommonFormat(data.getStartDate()));
        expectedData.setEndDate(Utils.getConvertedDateToCommonFormat(data.getEndDate()));
        expectedData.setId(campaignTable.getCampaignRow(data.getName()).getId());
        expectedData.setPublicationDate(data.getPublicationDate());
        List<SearchData> expectedList= Arrays.asList(expectedData);

        List<SearchData> actualResult = campaignTable.getAllCampaigns();
        assertTrue(actualResult.containsAll(expectedList));
    }
}
