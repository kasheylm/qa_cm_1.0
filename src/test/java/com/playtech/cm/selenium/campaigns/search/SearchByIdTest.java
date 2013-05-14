package com.playtech.cm.selenium.campaigns.search;

import com.playtech.cm.db.InitDb;
import com.playtech.cm.utils.entities.SearchData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 9/12/12
 * Time: 7:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchByIdTest extends BaseSearch {
    @BeforeClass
    public void init() {
        new InitDb("search/SearchByIdTest.xml");
    }

    @Test(groups = "search", dataProvider = "getIdValidateData", dataProviderClass = SearchData.class)
    public void searchByIdTest(String inputString, List<SearchData> expectedList, int resultCount, String expectedErrorMsg) {
//        WHEN
        searchPage.typeId(inputString);
        searchPage.clickOnSearchButton();
        checkFieldValidationMessage(expectedErrorMsg);
//        THEN
        assertSearchResultsContains(expectedList, resultCount);
    }
}
