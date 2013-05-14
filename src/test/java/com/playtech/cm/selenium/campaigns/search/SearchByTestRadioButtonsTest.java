package com.playtech.cm.selenium.campaigns.search;

import com.playtech.cm.db.InitDb;
import com.playtech.cm.utils.entities.SearchData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 9/13/12
 * Time: 10:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class SearchByTestRadioButtonsTest extends BaseSearch {
    @BeforeClass
    public void init() {
        new InitDb("search/SearchByTestRadioButtonsTest.xml");
    }

    @Test(groups = "search", dataProvider = "getTestValidateData", dataProviderClass = SearchData.class)
    public void searchByTestRadioButtonsTest(SearchData searchData, List<SearchData> expectedList, int resultCount) {
//        WHEN
        searchPage.selectTestCampaign(searchData.getTestActivityYes(),searchData.getTestActivityNo(),searchData.getTestActivityBoth());
        searchPage.clickOnSearchButton();
//        THEN
        assertSearchResultsContains(expectedList,resultCount);
    }
}
