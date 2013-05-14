package com.playtech.cm.selenium.campaigns.search;

/**
 * User: Denis Veselovskiy
 * Date: 5/24/12
 * Time: 3:02 PM
 */

import com.playtech.cm.db.InitDb;
import com.playtech.cm.utils.entities.SearchData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class SearchByApprovedByTest extends BaseSearch {
    @BeforeClass
    public void setUp() {
        new InitDb("search/SearchByUserTests.xml");
    }

    @Test(groups = "search",dataProvider = "getApprovedByUserData", dataProviderClass = SearchData.class)
    public void searchByApprovedByTest(String state, String searchString, List<SearchData> expectedList, int resultCount){
//        WHEN
        searchPage.selectCreatedApprovedUpdated(state);
        searchPage.selectUser(searchString);
        searchPage.clickOnSearchButton();
//        THEN
        assertSearchResultsContains(expectedList, resultCount);
    }
}