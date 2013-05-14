package com.playtech.cm.selenium.campaigns.search;

import com.google.common.base.Function;
import com.playtech.cm.BaseTest;
import com.playtech.cm.elements.CampaignTable;
import com.playtech.cm.pages.dashboard.search.SearchPage;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.utils.entities.SearchData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * User: Denis Veselovskiy
 * Date: 6/11/12
 * Time: 5:44 PM
 */
public class BaseSearch extends BaseTest {
    SearchPage searchPage;
    CampaignTable campaignTable;
    CMDashboardPage cmDashboardPage;

    @BeforeMethod
    public void openCmDashboard(){
        cmDashboardPage = goToCMDashboardDirectly();
        searchPage = cmDashboardPage.getSearchPage();
        campaignTable = cmDashboardPage.getCampaignTable();
        searchPage.expand();
        searchPage.clickClear();
    }

    public void assertSearchResults(List<SearchData> expectedList) {
        campaignTable.waitForExpandedResult(expectedList.size());
        campaignTable.refresh();
        List<SearchData> actualResult = campaignTable.getAllCampaigns();
        assertEquals(actualResult, expectedList);
    }

    public void assertSearchResultsContains(List<SearchData> expectedList, int resultCount) {
        campaignTable.waitForExpandedResult(resultCount);
        campaignTable.refresh();
        List<SearchData> actualResult = campaignTable.getAllCampaigns();
        assertTrue(actualResult.containsAll(expectedList));
    }

    public void checkFieldValidationMessage(String expectedMessage){
        if(!expectedMessage.equals("")){
            driver.waitUntil(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver input) {
                    return searchPage.isExpanded();
                }
            });
            WebElement vldMsg = driver.findElement(By.xpath("//*[@id='searchZone']//div[@class='bexerror']"));
            assertEquals(vldMsg.getText(), expectedMessage);
            campaignTable.expand();
        }
    }
}


