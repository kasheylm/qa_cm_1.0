package com.playtech.cm.pages.dashboard.newCampaign;

import com.playtech.cm.pages.dashboard.Page;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.pages.dashboard.campaign.CampaignDetailsPage;
import com.playtech.cm.utils.dataProviders.CampaignData;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.playtech.cm.BaseTest.driver;

/**
 * User: Denis Veselovskiy
 * Date: 9/14/12 * Time: 3:02 PM
 */
public class NewCampaignPage extends Page {

    @FindBy(xpath = "//*[@name='campaignName']")
    WebElement campaignName;

    @FindBy(xpath = "//*[@name='category']")
    WebElement category;

    @FindBy(xpath = "//*[@name='instance']")
    WebElement instance;

    @FindBy(xpath = "//*[@id='createNewCampaignZone']//*[@id='submit']")
    WebElement okButton;

    @FindBy(xpath = "//*[@id='createNewCampaignZone']//*[@value='Cancel']")
    WebElement cancelButton;

    public void selectCategory(String c) {
        Select s = new Select(category);
        s.selectByVisibleText(c);
    }

    public void selectInstance(String i) {
        Select s = new Select(instance);
        s.selectByVisibleText(i);
    }

    public void typeName(String name){
        driver.waitUntilElementAppearVisible(campaignName);
        campaignName.clear();
        typeQuickly(campaignName, name);
    }

    public CampaignDetailsPage clickOK(){
        driver.waitUntilElementAppearVisible(okButton);
        okButton.click();
        driver.waitForAjax();
        return StalePageFactory.initElements(driver.getDriver(), CampaignDetailsPage.class);
    }

    public CMDashboardPage clickCancel(){
        driver.waitUntilElementAppearVisible(cancelButton);
        cancelButton.click();
        return PageFactory.initElements(driver.getDriver(), CMDashboardPage.class);
    }

    public void fillForm(CampaignData data){
        typeName(data.getName());
        selectCategory(data.getCategory());
        selectInstance(data.getCasinoBrand());
    }

    public int getErrorsCount(){
        driver.waitUntilElementAppearVisible(okButton);
        return driver.findElements(By.xpath("//*[contains(@id,'errors')]/div")).size();
    }

    public boolean isErrorPresent(String error) {
        List<WebElement> list = driver.findElements(By.xpath("//*[contains(@id,'errors')]/div"));
        for  (WebElement webElement : list) {
            if (webElement.getText().contains(error)) {
                return true;
            }
        }
        return false;
    }

    public WebElement getCampaignName() {
        driver.waitUntilElementAppearVisible(campaignName);
        return campaignName;
    }

    public WebElement getCategory() {
        driver.waitUntilElementAppearVisible(category);
        return category;
    }

    private WebElement getInstance() {
        driver.waitUntilElementAppearVisible(instance);
        return instance;
    }

    public Select getSelectInstance() {
        driver.waitUntilElementAppearVisible(getInstance());
        Select select = new Select(getInstance());
        return select;
    }

    public String getCampaignNameValue() {
        driver.waitUntilElementAppearVisible(getCampaignName());
        return getCampaignName().getAttribute("value");
    }

    public String getSelectedCategory() {
        driver.waitUntilElementAppearVisible(getCategory());
        Select select = new Select(getCategory());
        return select.getFirstSelectedOption().getText();
    }

    public String getSelectedInstance() {
        driver.waitUntilElementAppearVisible(getInstance());
        return getSelectInstance().getFirstSelectedOption().getText();
    }

}
