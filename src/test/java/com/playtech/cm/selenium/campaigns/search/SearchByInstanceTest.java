package com.playtech.cm.selenium.campaigns.search;

import com.playtech.cm.db.InitDb;
import com.playtech.cm.utils.entities.SearchData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 9/14/12
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */

public class SearchByInstanceTest extends BaseSearch {
    @BeforeClass
    public void init() {
        new InitDb("search/SearchByInstanceTest.xml");
    }

    @Test(groups = "search", dataProvider = "getInstanceValidateData", dataProviderClass = SearchData.class)
    public void searchByInstanceTest(String inputString, List<SearchData> expectedList, int resultCount) {
//        WHEN
        searchPage.selectInstance(inputString);
        searchPage.clickOnSearchButton();
//        THEN
        assertSearchResultsContains(expectedList,resultCount);
    }
}
