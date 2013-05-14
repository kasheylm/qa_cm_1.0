package com.playtech.cm.selenium.campaigns.search;

/**
 * User: Denis Veselovskiy
 * Date: 5/24/12
 * Time: 3:00 PM
 */

import com.playtech.cm.db.InitDb;
import com.playtech.cm.utils.entities.SearchData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class SearchByCreatedByTest extends BaseSearch {
    @BeforeClass
    public void setUp() {
        new InitDb("search/SearchByUserTests.xml");
    }

    @Test(groups = "search",dataProvider = "getCreatedByUserData", dataProviderClass = SearchData.class)
    public void searchByCreatedByTest(String state, String searchString, List<SearchData> expectedList, int resultCount){
//        WHEN
        searchPage.selectCreatedApprovedUpdated(state);
        searchPage.selectUser(searchString);
        searchPage.clickOnSearchButton();
//        THEN
        assertSearchResultsContains(expectedList, resultCount);
    }
}


