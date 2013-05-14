package com.playtech.cm.selenium.campaigns.search;

import com.playtech.cm.db.InitDb;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.playtech.cm.utils.entities.SearchData;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Denis Veselovskiy
 * Date: 23/05/12
 * Time: 11:28
 */
public class SearchByAllTest extends BaseSearch {
    @BeforeClass
    public void init() {
        new InitDb("search/SearchByAllTest.xml");
    }

    @Test(groups = "search", dataProvider = "getAllData", dataProviderClass = SearchData.class)
    public void searchByAllTest(SearchData data, List<SearchData> expectedList) {
//        WHEN
        searchPage.typeId(data.getId());
        searchPage.typeName(data.getName());
        searchPage.selectStatus(data.getStatus());
        searchPage.selectCategory(data.getCategory());
        searchPage.selectInstance(data.getInstance());
        searchPage.selectUser(data.getUser());
        searchPage.selectCreatedApprovedUpdated(data.getCreatedUpdatedApprovedBy());
        searchPage.selectTestCampaign(data.getTestActivityYes(),data.getTestActivityNo(), data.getTestActivityBoth());
        searchPage.selectStartDate(data.getStartDate());
        searchPage.selectEndDate(data.getEndDate());
        searchPage.selectPublicationDate(data.getPublicationDate());
        searchPage.selectBetweenStartDate(data.getBetweenDateFirst());
        searchPage.selectBetweenEndDate(data.getBetweenDateLast());
        searchPage.typeTag(data.getTags());

        searchPage.clickOnSearchButton();
//        THEN
        assertSearchResults(expectedList);
    }

}
