package com.playtech.cm.selenium.campaigns.search;

import com.playtech.cm.db.InitDb;
import com.playtech.cm.utils.entities.SearchData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Denis Veselovskiy
 * Date: 23/05/12
 * Time: 11:28
 */
public class SearchByCategoryTest extends BaseSearch {
    @BeforeClass
    public void init() {
        new InitDb("search/SearchByCategoryTest.xml");
    }

    @Test(groups = "search", dataProvider = "getCategoryData", dataProviderClass = SearchData.class)
    public void searchByCategoryTest(String inputString, List<SearchData> expectedList, int resultCount) {
//        WHEN
        searchPage.selectCategory(inputString);
        searchPage.clickOnSearchButton();
//        THEN
        assertSearchResultsContains(expectedList, resultCount);
    }
}
