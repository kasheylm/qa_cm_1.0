package com.playtech.cm.pages.dashboard.campaign.activities.conditions;

import com.google.common.base.Function;
import com.playtech.cm.utils.entities.conditions.CustomReportEntity;
import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import static com.playtech.cm.BaseTest.driver;

/**
 * User: Denis Veselovskiy
 * Date: 10/8/12 * Time: 11:04 AM
 */
public class ConditionSectionPage extends ActivityTabPage {

    @FindBy(id = "addConditionSelect")
    WebElement addConditionSelect;

    @FindBy(xpath = "//*[@class='options']//*[@title='Edit']")
    List<WebElement> editButton;

    @FindBy(xpath = "//*[@class='options']//*[@title='Delete']")
    List<WebElement> deleteButton;

    @FindBy(xpath = "//*[@id='conditionsZone']//*[@class='summary']")
    WebElement summary;

    @FindBy(xpath = "//*[@id='conditionsZone']//*[@class='name']")
    WebElement name;

    @FindBy(xpath = "//*[@id='confirmMessageModalBox']//*[@value='OK']")
    WebElement confirmOK;

    String nameEmpty = "//*[@id='accordionmenusection_1']//*[@class=\"t-first t-last\"]/td[1]";

    String modalContainer = "//*[@class=\"modalContainer\" and contains(@style,\"display: block;\")]";

    public ConditionDialogPage addCondition(String condition){
        Select conditionDropdown = new Select(addConditionSelect);
        conditionDropdown.selectByVisibleText(condition);
        return StalePageFactory.initElements(driver.getDriver(), ConditionDialogPage.class);
    }

    public void waitUntilRowAdded() {
        driver.waitForAjax();
        driver.waitUntilElementAppearVisible(deleteButton.get(0));
    }

    public void deleteCondition(Integer i){
        deleteButton.get(i).click();
        confirmOK.click();
        driver.waitForAjax();
    }

    public LapsedPlayerReportPage editConditionLapsedReport(Integer i){
        driver.clickAndWait(editButton.get(i), modalContainer);
        return StalePageFactory.initElements(driver.getDriver(), LapsedPlayerReportPage.class);
    }
//
    public GameLossesPlayerReportPage editConditionPlayerGameLossesReport(Integer i){
        driver.clickAndWait(editButton.get(i), modalContainer);
        return StalePageFactory.initElements(driver.getDriver(), GameLossesPlayerReportPage.class);
    }

    public UploadCSVPage editConditionCSVFile(Integer i){
        editButton.get(i).click();
        return StalePageFactory.initElements(driver.getDriver(), UploadCSVPage.class);
    }

    public String getSummary(){
        return summary.getText();
    }

    public String getName(){
        return name.getText();
    }

    public String getNameEmpty(){
        return driver.findElement(By.xpath(nameEmpty)).getText();
    }

    public void waitUntilNameTextChangesToEmpty() {
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return (getNameEmpty().equals(""));
            }
        });
    }

    public void waitUntilSummaryChangesTo(final String text) {
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return (getSummary().equals(text));
            }
        });
    }
}
