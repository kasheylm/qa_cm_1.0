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
 * Time: 11:27
 */
public class SearchByNameTest extends BaseSearch {
    @BeforeClass
    public void init() {
        new InitDb("search/SearchByNameTest.xml");
    }

    @Test(groups = "search", dataProvider = "getNameValidateData", dataProviderClass = SearchData.class)
    public void searchByNameTest(String inputString, List<SearchData> expectedList, int resultCount) {
//        WHEN
        searchPage.typeName(inputString);
        searchPage.clickOnSearchButton();
//        THEN
        assertSearchResultsContains(expectedList,resultCount);
    }
}
