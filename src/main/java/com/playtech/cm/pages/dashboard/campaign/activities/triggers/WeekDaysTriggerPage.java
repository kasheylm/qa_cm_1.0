package com.playtech.cm.pages.dashboard.campaign.activities.triggers;

import com.google.common.base.Function;
import com.playtech.cm.utils.entities.TriggerData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

import static com.playtech.cm.BaseTest.driver;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 9/28/12
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class WeekDaysTriggerPage extends ScheduledTriggerDialog{
    final String daysFieldId = "weekdayList";
    final String deleteButtonsXpath = "//a[contains(@id, 'removeweekdaysrowlink')]";
    final String atTimeSectionsXpath = "(//select[contains(@id,'weekdaysTime')])";
    final String errorMessageXpath = "//div[@class='bexerror']";

    @FindBy(id = daysFieldId)
    WebElement weekDays;

    @FindBy(xpath = "//select[contains(@id,'weekdaysTime')]")
    List<WebElement> timeFields;

    @FindBy(id = "addweekdaysrowlink")
    WebElement addTimeButton;


    public void selectWeekdaysDays(ArrayList<String> days){
        weekDays.click();
        for(String day:days){
            String xpath = "//*[text()='"+day+"']/../input";
            if(!weekDays.findElement(By.xpath(xpath)).isSelected()){
                weekDays.findElement(By.xpath(xpath)).click();
            }
        }
        weekDays.click();
    }

   public void selectTimes(ArrayList<String> timesToAdd){
       WebElement atTimeSection;

       if ( timesToAdd.size() != 1)  {
           for(int i = 0; i < timesToAdd.size(); i++) {
               addTimeButton.click();
               driver.waitUntilElementAppear(By.xpath("(//select[contains(@id,'weekdaysTime')])[" + (i+1) + "]"));
               atTimeSection = timeFields.get(i);
               Select atTimeSelect = new Select(atTimeSection);
               atTimeSelect.selectByValue(timesToAdd.get(i));
           }
       }
       else {
           addTimeButton.click();
           driver.waitUntilElementAppear(By.xpath("//select[contains(@id,'weekdaysTime')]"));
           atTimeSection = timeFields.get(0);
           Select atTimeSelect = new Select(atTimeSection);
           atTimeSelect.selectByValue(timesToAdd.get(0));
       }
    }

    public void deselectAllDays(){
        String selctAllOptionXpath = "//div[@id='"+daysFieldId+"']/..//*[text()='Select all']/../input";
        weekDays.click();
        driver.findElement(By.xpath(selctAllOptionXpath)).click();
        if(driver.findElement(By.xpath(selctAllOptionXpath)).isSelected()){
            driver.findElement(By.xpath(selctAllOptionXpath)).click();
        }
        weekDays.click();

    }

    public void deleteAllTimes(){
        List<WebElement> delButtons = driver.findElements(By.xpath(deleteButtonsXpath));
        for(WebElement delButton:delButtons){
            delButton.click();
            driver.waitUntilElementDisappear(delButton);
        }
    }

    public String getDays(){
        return weekDays.getText();
    }

    public List<String> getTimes(){
        ArrayList<String> atTimes = new ArrayList<String>();
        for(WebElement field:timeFields){
            atTimes.add(field.getAttribute("value"));
        }
        return atTimes;
    }

    public void addTime(String time){
        int initialCount =  (!driver.isDisplayed(By.xpath(atTimeSectionsXpath))) ? 0 : timeFields.size();
        addTimeButton.click();
        driver.waitUntilElementAppearVisible(By.xpath(atTimeSectionsXpath+"["+(initialCount+1)+"]"));
        if(!time.equals("")){
            Select sel = new Select(timeFields.get(initialCount));
            sel.selectByVisibleText(time);
        }
    }

    public void fillWeekDaysTrigger(TriggerData data){
        driver.waitForAjax();
        deselectAllDays();
        selectWeekdaysDays(data.getWeekDaysDays());

        deleteAllTimes();
        selectTimes(data.getWeekDaysAtTimes());

        deleteExcludingDates();
        addExcludingDate("");
        selectExcludingDates(data.getExcludingDates());
    }

    public void checkThatValidationErrorShown(List<String> expectedMessages){
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                return driver.isElementVisible(By.xpath(errorMessageXpath));
            }
        });
        List<WebElement> errMsgList = driver.findElements(By.xpath(errorMessageXpath));
        List<String> actualMessages = new ArrayList<String>();
        for(WebElement errMsg:errMsgList){
            actualMessages.add(errMsg.getText());
        }
        assertTrue(actualMessages.containsAll(expectedMessages));
        assertTrue(expectedMessages.containsAll(actualMessages));
    }

    public void typeTimeValue(String value){
        WebElement sel = driver.findElement(By.xpath(atTimeSectionsXpath+"[last()]"));
        sel.sendKeys(value);
    }
}
