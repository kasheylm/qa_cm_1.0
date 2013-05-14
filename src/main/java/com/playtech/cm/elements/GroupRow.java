package com.playtech.cm.elements;

import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.pages.dashboard.campaign.activities.actions.ImsBonusPage;
import com.playtech.cm.pages.dashboard.campaign.activities.actions.UpdateCustomFieldsPage;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static com.playtech.cm.BaseTest.driver;

/**
 * User: Denis Veselovskiy
 * Date: 10/29/12 * Time: 6:04 PM
 */
public class GroupRow {

    private String name;
    private String action;
    private String summary;

    public WebElement getNameCell() {
        return driver.findElement(By.xpath("//*[@class='name' and contains(text(),\"" + getName() + "\")]"));
    }

    public String getAction(){
        String result = null;
        WebElement actionCell = getNameCell().findElement(By.xpath("../descendant::*[@class='action']"));
        if(actionCell.findElements(By.xpath("div/select[contains(@id,'selectTestAction')]")).size()> 0){
            Select select = new Select(actionCell.findElement(By.xpath("div/select[contains(@id,'selectTestAction')]")));
            result = select.getFirstSelectedOption().getText();
        } else {
            result = actionCell.getText();
        }
        return result;
    }

    public String getSummary(){
        return getNameCell().findElement(By.xpath("../descendant::*[@class='summary']")).getText();
    }

    public ActivityTabPage clickEdit(){
        getNameCell().findElement(By.xpath("../descendant::*[@class='options']//*[@title='Edit']")).click();
        if (getAction().equals("Update Player Details")) {
            return StalePageFactory.initElements(driver.getDriver(), UpdateCustomFieldsPage.class);
        } else if (getAction().equals("IMS Bonus")) {
            return StalePageFactory.initElements(driver.getDriver(), ImsBonusPage.class);
        } else return null;
    }

    public void clickDelete(){
        driver.waitForAjax();
        WebElement buttonToDisappear = getNameCell().findElement(By.xpath("../descendant::*[@class='options']//*[@title='Delete']"));
//        Alert was closing by webdriver automatically without any possibility to handle it here
        buttonToDisappear.click();
        WebElement bOk = driver.findElement(By.xpath("//div[@id='confirmMessageModalBox']//input[@type='button' and @value='OK']"));
        bOk.click();
        driver.waitUntilElementDisappear(buttonToDisappear);
        driver.waitForAjax();
    }

    public ActivityTabPage addAction(String actionType){
        WebElement el = getNameCell().findElement(By.xpath("../descendant::*[contains(@id,'selectTestAction')]"));
        Select actionDropdown = new Select(el);
        actionDropdown.selectByVisibleText(actionType);
        if (actionType.equals("Update Player Details")) {
            return StalePageFactory.initElements(driver.getDriver(), UpdateCustomFieldsPage.class);
        } else if (actionType.equals("IMS Bonus")) {
            return StalePageFactory.initElements(driver.getDriver(), ImsBonusPage.class);
        } else return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


}
