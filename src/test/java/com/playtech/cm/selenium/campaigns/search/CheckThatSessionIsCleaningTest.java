package com.playtech.cm.selenium.campaigns.search;

import com.playtech.cm.db.InitDb;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.utils.entities.SearchData;
import org.testng.annotations.*;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Denis Veselovskiy
 * Date: 23/05/12
 * Time: 11:27
 */
public class CheckThatSessionIsCleaningTest extends BaseSearch {
    CMDashboardPage cmDashboardPage;

    @BeforeClass
    public void init() {
        new InitDb("search/SearchCheckThatSessionIsCleaningTest.xml");
    }

    @Test(groups = "search", dataProvider = "getAllData", dataProviderClass = SearchData.class)
    public void checkThatSessionIsCleaningTest(SearchData data, List<SearchData> expectedList) {
        System.out.println("BUG_ID: PRO-375");
//        WHEN
        searchPage.typeId("error");
        searchPage.typeName(data.getName());
        searchPage.selectStatus(data.getStatus());
        searchPage.selectCategory(data.getCategory());
        searchPage.selectInstance(data.getInstance());
        searchPage.selectUser(data.getUser());
        searchPage.selectCreatedApprovedUpdated(data.getCreatedUpdatedApprovedBy());
        searchPage.selectTestCampaign(data.getTestActivityYes(),data.getTestActivityNo(), data.getTestActivityBoth());
        searchPage.selectStartDate("error");
        searchPage.selectEndDate("error");
        searchPage.selectPublicationDate("error");
        searchPage.selectBetweenStartDate(data.getBetweenDateFirst());
        searchPage.selectBetweenEndDate(data.getBetweenDateLast());
        searchPage.typeTag(data.getTags());

        searchPage.clickOnSearchButton();
//        THEN
        cmDashboardPage = goToCMDashboardDirectly();
        searchPage.expand();

        assertEquals(searchPage.getId(), "");
        assertEquals(searchPage.getName(), "");
        assertEquals(searchPage.getStatus(), "");
        assertEquals(searchPage.getCategory(), "");
        assertEquals(searchPage.getInstance(), "");
        assertEquals(searchPage.getUser(), "Any admin user");
        assertEquals(searchPage.getCreatedApprovedUpdated(), "CREATED");
        assertEquals(searchPage.getTestActivity(), "Both");
        assertEquals(searchPage.getStartDate(), "");
        assertEquals(searchPage.getEndDate(), "");
        assertEquals(searchPage.getPublicationDate(), "");
        assertEquals(searchPage.getBetweenStartDate(), "");
        assertEquals(searchPage.getBetweenEndDate(), "");
        assertEquals(searchPage.getTags(), "");

        assertTrue(searchPage.getErrorsCount() == 0);
        assertEquals(campaignTable.getAllCampaigns().size(),6);
    }
}
