package com.playtech.cm.selenium.campaigns.search;

import com.playtech.cm.db.InitDb;
import com.playtech.cm.utils.entities.SearchData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Denis Veselovskiy
 * Date: 23/05/12
 * Time: 11:27
 */
public class SearchByTagTest extends BaseSearch {
    @BeforeClass
    public void init() {
        new InitDb("search/SearchByTagTest.xml");
    }

    @Test(groups = "search", dataProvider = "getTagValidateData", dataProviderClass = SearchData.class)
    public void searchByTagTest(String inputString, List<SearchData> expectedList, int resultCount) {
//        WHEN
        searchPage.typeTag(inputString);
        searchPage.clickOnSearchButton();
//        THEN
        assertSearchResultsContains(expectedList, resultCount);
    }
}
