package com.playtech.cm.elements;

import com.google.common.base.Function;
import com.playtech.cm.utils.Config;
import com.playtech.cm.utils.entities.SearchData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.playtech.cm.BaseTest.driver;

/**
 * User: Denis Veselovskiy
 * Date: 9/12/12 * Time: 4:57 PM
 */
public class CampaignTable {

    private List<CampaignRow> campaignRowList = new ArrayList<CampaignRow>();
    private List<SearchData> searchDataList = new ArrayList<SearchData>();
    private final String XPATH_TABLE = "//*[@id='campaignGrid']/table/tbody/tr";

    public CampaignTable() {
        refresh();
    }

    public void refresh() {
        campaignRowList.clear();
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                boolean res = false;
                res = isExpanded();
                return res;
            }
        });
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        List<WebElement> list = driver.findElements(By.xpath(XPATH_TABLE));
        for (WebElement element : list) {
            CampaignRow campaignRow = new CampaignRow();
            campaignRow.setId(element.findElement(By.className("id")).getText());
            campaignRow.setName(element.findElement(By.className("name")).getText());
            campaignRow.setCategory(element.findElement(By.className("category")).getText());
            campaignRow.setCasino(element.findElement(By.className("casinoName")).getText());
            campaignRow.setStartDate(element.findElement(By.className("startDate")).getText());
            campaignRow.setEndDate(element.findElement(By.className("endDate")).getText());
            campaignRow.setStatus(element.findElement(By.className("status")).getText());
            campaignRowList.add(campaignRow);
        }
        driver.manage().timeouts().implicitlyWait(Config.shortTimeoutMS, TimeUnit.MILLISECONDS);
    }

    public List<CampaignRow> getCampaignRowList() {
        return campaignRowList;
    }

    public int count() {
        return campaignRowList.size();
    }

    public boolean isCampaignExist(String campaignName) {
        for (int i=0; i<count(); i++) {
            if (campaignRowList.get(i).getName().equals(campaignName))
                return true;
        }
        return false;
    }

    public CampaignRow getCampaignRow(String campaignName) {
        for (int i=0; i<count(); i++) {
            if (campaignRowList.get(i).getName().equals(campaignName))
                return campaignRowList.get(i);
        }
        return null;
    }

    public Boolean isExpanded(){
        String title;
        WebElement expandedIcon = driver.findElement(By.xpath("//*[@id='campaignsAccordion']//*[text()='Campaigns']/../..//a[contains(@class,'iconexpanded')]"));
        title = expandedIcon.getAttribute("title");
        return (title.equals("Click to collapse"));
    }

    public void waitForExpandedResult(final int resultCount){
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                boolean res=false;
                res = isExpanded() && (driver.findElements(By.xpath("//div[@id='campaignGrid']//tbody//tr")).size() == resultCount);
                return res;
            }
        });
    }

    public List<SearchData> getAllCampaigns() {
        searchDataList.clear();
        for (int i=0; i<count(); i++) {
            SearchData searchData = new SearchData();
            searchData.setId(campaignRowList.get(i).getId());
            searchData.setName(campaignRowList.get(i).getName());
            searchData.setCategory(campaignRowList.get(i).getCategory());
            searchData.setInstance(campaignRowList.get(i).getCasino());
            searchData.setStartDate(campaignRowList.get(i).getStartDate());
            searchData.setEndDate(campaignRowList.get(i).getEndDate());
            searchData.setStatus(campaignRowList.get(i).getStatus());
            searchDataList.add(searchData);
        }
        return searchDataList;
    }

    public void expand(){
        if(!isExpanded()){
            driver.waitUntil(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver input) {
                    return driver.findElement(By.xpath("//*[@id='campaignsAccordion']//span[text()='Campaigns']")).isDisplayed();
                }
            });
            driver.findElement(By.xpath("//*[@id='campaignsAccordion']//span[text()='Campaigns']")).click();
            driver.waitUntil(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver input) {
                    return isExpanded();
                }
            });
        }
    }

}
