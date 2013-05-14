package com.playtech.cm.pages.dashboard.campaign.activities.triggers;

import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

import static com.playtech.cm.BaseTest.driver;
import static org.testng.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Denis Veselovskiy
 * Date: 24/09/12
 * Time: 14:50
 */
public class TriggerSectionPage extends ActivityTabPage {
    @FindBy(id = "addTrigger")
    WebElement triggerSelect;

    @FindBy(xpath = "//*[@ID='triggersList']//table[@class='t-entities-grid']")
    WebElement triggerTable;

    @FindBy(xpath = "//*[@id='triggersGrid']//*[@title='Edit']")
    List<WebElement> editButton;

    public ScheduledTriggerDialog selectNewTrigger(String triggerType){
        Select triggerDropdown = new Select(triggerSelect);
        triggerDropdown.selectByVisibleText(triggerType);
        return StalePageFactory.initElements(driver.getDriver(), ScheduledTriggerDialog.class);
    }

    private ArrayList<ArrayList<String>> getTriggerList(){
        List<WebElement> rows = triggerTable.findElements(By.xpath("//tbody/tr"));
        ArrayList<String> rowValues = new ArrayList<String>();
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        for(WebElement row:rows){
            rowValues.add(row.findElement(By.xpath("/td[@class='name']")).getText());
            rowValues.add(row.findElement(By.xpath("/td[@class='description']")).getText());
            result.add(rowValues);
        }
        return result;
    }

    public void deleteTrigger(String trigger, String summary){
        triggerTable.findElement(By.xpath("//tbody/tr/td[@class='name'][text()='"+trigger+"']/../td[@class='description']/a[text()='"+summary+"']/../../td[@class='options']/a[@title='Delete']/span[@id='any']")).click();
        System.out.println("Stop here");
        driver.switchTo().alert().accept();
        }

    public ScheduledTriggerDialog editTrigger(Integer i){
        editButton.get(i).click();
        driver.waitForAjax();
        return StalePageFactory.initElements(driver.getDriver(), ScheduledTriggerDialog.class);
    }

    public void checkThatTriggerIsNotCreated(){
        driver.waitUntilElementAppear(By.id("triggersList"));
        driver.sleep(1500);
        assertEquals(driver.isElementPresent(By.xpath("//tbody/tr/td[@class='name'][text()='Scheduled - Weekdays']/../td[@class='options']/a[@title='Edit']")), false);
    }
}
