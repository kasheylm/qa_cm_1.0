package com.playtech.cm.selenium.campaigns.search;

import com.playtech.cm.db.InitDb;
import com.playtech.cm.utils.entities.SearchData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

/**
 * User: Denis Veselovskiy
 * Date: 5/24/12
 * Time: 2:49 PM
 */
public class SearchByUpdatedByTest extends BaseSearch {

    @BeforeClass
    public void setUp() {
        new InitDb("search/SearchByUserTests.xml");
    }

    @Test(groups = "search", dataProvider = "getUpdatedByUserData", dataProviderClass = SearchData.class)
    public void searchByUpdatedByTest(String state, String inputString, List<SearchData> expectedList, int resultCount){
//        WHEN
        searchPage.selectCreatedApprovedUpdated(state);
        searchPage.selectUser(inputString);
        searchPage.clickOnSearchButton();
//        THEN
        assertSearchResultsContains(expectedList, resultCount);
    }
}
