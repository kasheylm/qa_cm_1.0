package com.playtech.cm.pages.dashboard.campaign.activities.triggers;

import com.playtech.cm.utils.entities.TriggerData;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.playtech.cm.BaseTest.driver;

/**
 * Created with IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 9/28/12
 * Time: 4:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class FrequencyTriggerPage extends ScheduledTriggerDialog{

    @FindBy(id = "everyNumber")
    WebElement everyNumber;

    @FindBy(id = "everyType")
    WebElement everyType;

    @FindBy(id = "everyAtTime")
    WebElement everyAtTime;

    @FindBy(id = "frequencyStartDate")
    WebElement frequencyStartDate;

    @FindBy(id = "frequencyStartTime")
    WebElement frequencyStartTime;

    @FindBy(id = "useDefaultStartDate")
    WebElement useDefaultStartDate;

    @FindBy(xpath = "//*[contains(@id,'excludeDate_')]")
    List<WebElement> excludeDate;

    @FindBy(xpath = "//*[contains(@id,'removeexcluderowlink_')]")
    List<WebElement> removeExcludeRowLink;

    @FindBy(id = "addexcluderowlink")
    WebElement addExcludeRowLink;

    public void typeRepeatEvery(String s){
        everyNumber.clear();
        everyNumber.sendKeys(s);
    }

    public void selectDaysHours(String s){
        Select select = new Select(everyType);
        select.selectByVisibleText(s);
    }

    public void selectDaysAt(String s){
        Select select = new Select(everyAtTime);
        select.selectByVisibleText(s);
    }

    public void typeStartDate(String s){
        frequencyStartDate.clear();
        frequencyStartDate.sendKeys(s);
    }

    public void selectStartTime(String s){
        Select select = new Select(frequencyStartTime);
        select.selectByVisibleText(s);
    }

    public void clickUseDefaultStartDateNo() {
        if (!useDefaultStartDate.isSelected()) return;
        useDefaultStartDate.click();
    }

    public void clickUseDefaultStartDateYes() {
        if (useDefaultStartDate.isSelected()) return;
        useDefaultStartDate.click();
    }

    public void setUseDefaultStartDate(boolean rez) {
        if (rez) {
            clickUseDefaultStartDateYes();
        }
        else {
            clickUseDefaultStartDateNo();
        }
        driver.sleep(1000);
    }

    public void fillFormHours(TriggerData data){
        typeRepeatEvery(data.getFreqRepeatEvery());
        selectDaysHours(data.getFreqHoursOrDays());
        setUseDefaultStartDate(data.getFreqUseActivityStartDateAndTime());
        typeStartDate(data.getFreqStartingFromDay());
        selectStartTime(data.getFreqStartingFromTime());
        deleteExcludingDates();
        addExcludingDate("");
        selectExcludingDates(data.getExcludingDates());
    }

    public void fillFormDays(TriggerData data){
        typeRepeatEvery(data.getFreqRepeatEvery());
        selectDaysHours(data.getFreqHoursOrDays());
        selectDaysAt(data.getFreqEveryAtTime());
        setUseDefaultStartDate(data.getFreqUseActivityStartDateAndTime());
        typeStartDate(data.getFreqStartingFromDay());
        deleteExcludingDates();
        addExcludingDate("");
        selectExcludingDates(data.getExcludingDates());
    }
}
