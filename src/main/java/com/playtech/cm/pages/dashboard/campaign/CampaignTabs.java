package com.playtech.cm.pages.dashboard.campaign;

import com.playtech.cm.pages.dashboard.HomePage;
import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static com.playtech.cm.BaseTest.driver;

/**
 * Created with IntelliJ IDEA.
 * User: Denis Veselovskiy
 * Date: 24/09/12
 * Time: 14:49
 */
public class CampaignTabs extends HomePage {

    @FindBy(id = "campaignTabSet-span-Details")
    WebElement campaignDetailsTab;

    @FindBy(id = "campaignTabSet-span-Activities")
    WebElement campaignActivitiesTab;

    @FindBy(xpath = "//a[@title='Export']")
    WebElement linkExport;

    @FindBy(xpath = "//a[@title='Home']")
    WebElement linkHome;

    @FindBy(xpath = "//*[@title='Save']/img[@alt='Save']")
    WebElement linkSave;

    @FindBy(id = "campaignIdInfo")
    WebElement campaignId;

    @FindBy(id = "campaignStatusInfo")
    WebElement campaignStatus;

    String savedCampaign = "//*[@id='campaignName']";

    public ActivityTabPage openActivityTab() {
        driver.waitUntilElementAppearVisible(campaignActivitiesTab);
        driver.waitForAjax();
        campaignActivitiesTab.click();
        return StalePageFactory.initElements(driver.getDriver(), ActivityTabPage.class);
    }

    public CampaignDetailsPage openCampaignDetailsTab() {
        campaignDetailsTab.click();
        return StalePageFactory.initElements(driver.getDriver(), CampaignDetailsPage.class);
    }

    public CampaignExportPage clickExport(){
        driver.waitUntilElementAppearVisible(linkExport);
        driver.waitForAjax();
        linkExport.click();
        driver.waitUntilElementAppearVisible(By.id("exportCampaignHistory"));
        return StalePageFactory.initElements(driver, CampaignExportPage.class);
    }

    public CampaignDetailsPage clickSave(){
        this.openActivityTab();
        driver.waitUntilElementAppearVisible(linkSave);
        driver.waitForAjax();
        driver.clickAndWaitVisible(linkSave, savedCampaign);
        driver.waitForAjax();
        return StalePageFactory.initElements(driver, CampaignDetailsPage.class);
    }

    public CMDashboardPage clickHomeLinkWithAlert(){
        driver.setNullOnBeforeUnload();
        linkHome.click();
        return StalePageFactory.initElements(driver, CMDashboardPage.class);
    }

    public CMDashboardPage clickHomeLink(){
        linkHome.click();
        return StalePageFactory.initElements(driver, CMDashboardPage.class);
    }

    public boolean isSaved() {
        return driver.isElementVisible(By.id("successMessage"));
    }

    public void waitForMessageDisappear() {
        driver.waitUntilElementDisappear(By.id("successMessage"));
    }

    public String getCampaignID() {
        driver.waitUntilElementAppearVisible(campaignId);
        return campaignId.getAttribute("value");
    }

    public void changeStatusTo(String value) {
        driver.waitUntilElementAppearVisible(campaignStatus);
        Select select = new Select(campaignStatus);
        select.selectByVisibleText(value);
    }

}
