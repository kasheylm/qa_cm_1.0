package com.playtech.cm.pages.dashboard.campaign;

import com.playtech.cm.elements.CampaignTable;
import com.playtech.cm.pages.dashboard.HomePage;
import com.playtech.cm.pages.dashboard.newCampaign.NewCampaignPage;
import com.playtech.cm.pages.dashboard.search.SearchPage;
import com.playtech.cm.utils.dataProviders.CampaignData;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static com.playtech.cm.BaseTest.driver;

/**
 User: Denis Veselovskiy
 * Date: 9/5/12 * Time: 5:05 PM
 */
public class CMDashboardPage extends HomePage {
    @FindBy(xpath = "//*[@id='createNewCampaignLink' and @onclick='javascript:return Tapestry.waitForPage(event);']")
    WebElement linkNewCampaign;

    @FindBy(xpath = "//*[@class='label' and @title='Campaigns']")
    WebElement campaignsListTab;

    @FindBy(xpath = "//*[text()=\"Search\"]")
    WebElement searchTab;

    private SearchPage searchPage;
    private CampaignTable campaignTable;

    public CMDashboardPage() {
        this.campaignTable = new CampaignTable();
        this.searchPage = PageFactory.initElements(driver.getDriver(), SearchPage.class);
    }

    public CampaignTable getCampaignTable() {
        campaignTable.refresh();
        return campaignTable;
    }

    public NewCampaignPage clickNewCampaign() {
        driver.waitForAjax();
        linkNewCampaign.click();
        return StalePageFactory.initElements(driver, NewCampaignPage.class);
    }

    public void clickCampaignsTab() {
        campaignsListTab.click();
    }

    public void clickSearchTab() {
        searchTab.click();
    }

    public SearchPage getSearchPage() {
        return searchPage;
    }

    public void setSearchPage(SearchPage searchPage) {
        this.searchPage = searchPage;
    }

    public CampaignTabs clickEdit(String campaignName) {
       return getCampaignTable().getCampaignRow(campaignName).clickEdit();
    }

    public void clickDelete(String campaignName) {
        getCampaignTable().getCampaignRow(campaignName).clickDelete();
    }

    public NewCampaignPage clickCopy(String campaignName) {
        return getCampaignTable().getCampaignRow(campaignName).clickCopy();
    }

    public boolean isCampaignExist(String campaignName) {
        return getCampaignTable().isCampaignExist(campaignName);
    }

    public boolean isLinkNewCampaignEnabled(){
        return linkNewCampaign.isEnabled();
    }

    public CampaignDetailsPage createCampaign(CampaignData campaignData) {
        NewCampaignPage newCampaignPage = clickNewCampaign();
        newCampaignPage.fillForm(campaignData);
        return newCampaignPage.clickOK();
    }
}
