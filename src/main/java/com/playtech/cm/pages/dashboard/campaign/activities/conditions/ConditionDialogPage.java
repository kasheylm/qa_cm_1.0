package com.playtech.cm.pages.dashboard.campaign.activities.conditions;

import com.playtech.cm.pages.dashboard.HomePage;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static com.playtech.cm.BaseTest.driver;

/**
 * User: Denis Veselovskiy
 * Date: 10/8/12 * Time: 11:10 AM
 */
public class ConditionDialogPage extends HomePage {

    @FindBy(xpath = "//select[contains(@id, 'conditionSelectBox_')]")
    WebElement conditionSelectBox;

    @FindBy(xpath = "//*[@id='conditionForm']//*[@value='OK']")
    WebElement okButton;

    @FindBy(xpath = "//*[@id='conditionForm']//*[@value='Cancel']")
    WebElement cancelButton;

    @FindBy(xpath = "//table[@style='display: block;']//*[@class='closeBtn']")
    WebElement closeLink;

    public ConditionSectionPage clickOk(){
        driver.waitUntilElementAppearVisible(okButton);
        driver.waitForAjax();
        okButton.click();
        driver.waitForAjax();
        return StalePageFactory.initElements(driver.getDriver(), ConditionSectionPage.class);
    }

    public ConditionSectionPage clickCancel(){
        driver.waitUntilElementAppearVisible(cancelButton);
        cancelButton.click();
        return StalePageFactory.initElements(driver.getDriver(), ConditionSectionPage.class);
    }

    public ConditionSectionPage close(){
        closeLink.click();
        return StalePageFactory.initElements(driver.getDriver(), ConditionSectionPage.class);
    }

    public ConditionDialogPage selectConditionType(String type){
        driver.waitUntilElementAppearVisible(conditionSelectBox);
        Select conditionDropdown = new Select(conditionSelectBox);
        conditionDropdown.selectByValue(type);
        if (type.equals("CUSTOM_REPORT")) {
            return StalePageFactory.initElements(driver.getDriver(), CustomReportDialogPage.class);
        } else if (type.equals("CSV_FILE")) {
            return StalePageFactory.initElements(driver.getDriver(), UploadCSVPage.class);
        }
        return null;
    }
}
