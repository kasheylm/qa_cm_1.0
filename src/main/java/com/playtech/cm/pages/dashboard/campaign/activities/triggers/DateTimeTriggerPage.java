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
import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 9/28/12
 * Time: 4:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateTimeTriggerPage extends ScheduledTriggerDialog{
    final String errorMessageXpath = "//div[@class='bexerror']";

    @FindBy(xpath = "//div[@id='specificDynamicPart']//tr[(contains(@id, 'fragment') or contains(@id, 'rowInjector'))]/child::td[text()='On:']/..")
    List<WebElement> onDateTimeRows;

    @FindBy(id = "addspecificrowlink")
    WebElement addDateAndTimeButton;

    public void deleteAllDatesAndTimes(){
        for(WebElement row:onDateTimeRows){
           WebElement removeRowButton = row.findElement(By.xpath("//a[contains(@id,'removespecificrowlink')]"));
           removeRowButton.click();
           driver.waitUntilElementDisappear(removeRowButton);
        }
    }

    public void addDateAndTime(String date, String time){
        driver.waitForAjax();
        addDateAndTimeButton.click();
        driver.waitForAjax();
        int initialRowCount = onDateTimeRows.size();
        driver.waitUntilElementAppearVisible(By.xpath("(//a[contains(@id,'removespecificrowlink')])[" + (initialRowCount) + "]"));
        driver.waitForAjax();
        WebElement dateField = driver.findElement(By.xpath("(//input[contains(@id,'specificDate')])[" + (initialRowCount) + "]"));
        Select timeField = new Select(driver.findElement(By.xpath("(//select[contains(@id,'specificTime')])[" + (initialRowCount) + "]")));
        typeQuickly(dateField,date);
        timeField.selectByVisibleText(time);
    }

    public void fillTriggerData(TriggerData data){
        List<String[]> datesAndTimes = data.getSpecOnDateDateAndTime();
        deleteAllDatesAndTimes();
        for(String[] rowData:datesAndTimes){
            addDateAndTime(rowData[0], rowData[1]);
        }
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

    public void deleteDateAndTime(List<String[]> rows){
        for(String[] rowValue:rows){
            WebElement rowToDeleteDelButton = driver.findElement(By.xpath("//input[contains(@id,'specificDate')][@value='"+rowValue[0]+"']/../select/option[@selected='selected'][text()='"+rowValue[1]+"']/../..//a[contains(@id,'removespecificrowlink')]"));
            rowToDeleteDelButton.click();
            driver.waitUntilElementDisappear(rowToDeleteDelButton);
        }

    }

}
