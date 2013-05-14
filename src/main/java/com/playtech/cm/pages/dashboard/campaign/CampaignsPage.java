package com.playtech.cm.pages.dashboard.campaign;

import com.playtech.cm.pages.dashboard.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.playtech.cm.BaseTest.driver;
/**
 * User: Denis Veselovskiy
 * Date: 5/3/12
 * Time: 10:10 AM
 */
public class CampaignsPage extends HomePage {

    @FindBy(linkText = "Simple Search")
    WebElement linkSimpleSearch;

    @FindBy(linkText = "Advanced Search")
    WebElement linkAdvancedSearch;

    @FindBy(linkText = "New")
    WebElement linkCreateNewCampaign;

    public CampaignDetailsPage clickLinkCreateNewCampaign() {
        linkCreateNewCampaign.click();
        return PageFactory.initElements(driver.getDriver(), CampaignDetailsPage.class);
    }

    public boolean isCampaignExists(String name) {
        return driver.isElementVisible(By.xpath(String.format("//*[@class='name' and contains(text(),\"%s\")]", name)));
    }

    public CampaignDetailsPage clickEdit(String name) {
        driver.click(By.xpath(String.format("//*[@class='name' and contains(text(),'%s')]/..//*[contains(text(),'Edit')]", name)));
        return PageFactory.initElements(driver.getDriver(), CampaignDetailsPage.class);
    }

}
