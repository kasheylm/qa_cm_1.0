package com.playtech.cm.pages.dashboard.campaign.activities.triggers;

import com.google.common.base.Function;
import com.playtech.cm.pages.dashboard.HomePage;
import com.playtech.cm.pages.dashboard.campaign.CampaignDetailsPage;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

import static com.playtech.cm.BaseTest.driver;

/**
 * Created with IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 9/24/12
 * Time: 4:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduledTriggerDialog extends HomePage {
    @FindBy(xpath = "//*[contains(@id, 'scheduleType_')]")
    WebElement triggerOnSpecified;

    @FindBy(xpath = "//*[@id='triggerForm']//*[@value='OK']")
    WebElement okButton;

    @FindBy(xpath = "//*[@id='triggerForm']//*[@value='Cancel']")
    WebElement cancelButton;

    @FindBy(xpath = "//table[@style='display: block;']//*[@class='closeBtn']")
    WebElement closeLink;
    int excludeDateListCount;
    public TriggerSectionPage clickOk(){
        okButton.click();
        return StalePageFactory.initElements(driver.getDriver(), TriggerSectionPage.class);
    }

    public TriggerSectionPage clickCancel(){
        cancelButton.click();
        return StalePageFactory.initElements(driver.getDriver(), TriggerSectionPage.class);
    }

    public TriggerSectionPage close(){
        closeLink.click();
        return StalePageFactory.initElements(driver.getDriver(), TriggerSectionPage.class);
    }

    public CampaignDetailsPage saveTrigger(){
        driver.waitForAjax();
        driver.findElement(By.xpath("//*[contains(@id, 'submit_')]")).click();
        driver.waitUntilElementDisappear(By.xpath("//div[@class='modalContainer']"));
        driver.waitUntilElementDisappear(By.id("progressBarFrame_mask"));
        driver.sleep(1000);
        return PageFactory.initElements(driver.getDriver(), CampaignDetailsPage.class);
    }

    public ScheduledTriggerDialog selectSchedulerType(String type){
        driver.waitUntilElementAppearVisible(triggerOnSpecified);
        Select triggerDropdown = new Select(triggerOnSpecified);
        triggerDropdown.selectByVisibleText(type);
        if (type.equals("Weekdays")) {
            return StalePageFactory.initElements(driver.getDriver(),WeekDaysTriggerPage.class);
        } else if (type.equals("Frequency")) {
            return StalePageFactory.initElements(driver.getDriver(),FrequencyTriggerPage.class);
        } else if (type.equals("Date & Time")) {
            return StalePageFactory.initElements(driver.getDriver(),DateTimeTriggerPage.class);
        }
        return null;
    }

    public void selectExcludingDates(ArrayList<String> excludingDates){
        WebElement addDateButton = driver.findElement(By.id("addexcluderowlink"));
        WebElement dateField;
        for(int i=0; i < excludingDates.size(); i++){
            dateField = driver.findElement(By.xpath("(//input[contains(@id,'excludeDate_')])["+(i+1)+"]"));
            dateField.sendKeys(excludingDates.get(i));
            driver.sleep(1500);
            if(i!=excludingDates.size()-1){
                addDateButton.click();
                driver.waitUntilElementAppear(By.xpath("(//input[contains(@id,'excludeDate_')])["+(i+2)+"]"));
            }
        }
    }

    public void addExcludingDate(String date) {
        List<WebElement> excludeDateList = driver.findElements(By.xpath("//div[@id='excludeDatesPart']//td/input"));
        excludeDateListCount = excludeDateList.size();
        driver.findElement(By.id("addexcluderowlink")).click();
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                  return driver.findElements(By.xpath("//div[@id='excludeDatesPart']//td/input")).size() == excludeDateListCount+1;
                  }
            });
        if(!date.equals("")){
            driver.findElement(By.xpath("(//input[contains(@id,'excludeDate')])[last()]")).sendKeys(date);
        }
    }

    public void clearAllExcludingDates(){
        List<WebElement> exludeDateFields = driver.findElements(By.xpath("//input[contains(@id,'excludeDate')]"));
        for(WebElement field:exludeDateFields){
            field.clear();
        }
    }

    public void deleteExcludingDates(){
        List<WebElement> delButtons = driver.findElements(By.xpath("//a[contains(@id, 'removeexcluderowlink')]"));
        String id;
        for(WebElement delButton:delButtons){
            id = delButton.getAttribute("id");
            delButton.click();
            driver.waitUntilElementDisappear(By.id(id));
        }
    }

    public void deleteExcludingDateByFieldValue(String value){
        driver.findElement(By.xpath("//input[@value='"+value+"']/../../..//a[@title='Remove Date']")).click();
    }

    public List<String> getExcludingDates(){
        List<WebElement> exludeDateFields = driver.findElements(By.xpath("//input[contains(@id,'excludeDate')]"));
        ArrayList<String> exDates = new ArrayList<String>();
        for(WebElement field:exludeDateFields){
            exDates.add(field.getText());
        }
        return exDates;
    }

}
