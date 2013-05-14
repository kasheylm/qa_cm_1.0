package com.playtech.cm.pages.dashboard.campaign.activities.actions;

import com.playtech.cm.elements.ActionsTable;
import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.playtech.cm.BaseTest.driver;

/**
 * User: Denis Veselovskiy
 * Date: 10/29/12 * Time: 12:55 PM
 */
public class ActionsPage extends ActivityTabPage {

    @FindBy(id = "selectAction")
    WebElement selectAction;

    @FindBy(xpath = "//*[@id='actionsGridZone']//*[contains(@title,'Delete')]/span")
    List<WebElement> deleteButtons;

    @FindBy(xpath = "//*[@id='actionsGridZone']//*[@class='options']//*[@title='Edit']")
    List<WebElement> editButtons;

    @FindBy(xpath = "//div[@id='confirmMessageModalBox']//input[@type='button' and @value='OK']")
    WebElement bConfirmationMessageOk;

//    @FindBy(id = "//*[@id='testTrue']")
//    WebElement activityTestTrue;
//
//    @FindBy(id = "//*[@id='testFalse']")
//    WebElement activityTestFalse;

    private ActionsTable actionsTable;

    public ActionsPage() {
        actionsTable = new ActionsTable();
    }

    public ActivityTabPage addAction(String type){
        driver.waitUntilElementAppearVisible(selectAction);
        Select conditionDropdown = new Select(selectAction);
        conditionDropdown.selectByVisibleText(type);
        if (type.equals("Update Player Details")) {
            return StalePageFactory.initElements(driver.getDriver(), UpdateCustomFieldsPage.class);
        } else if (type.equals("IMS Bonus")) {
            return StalePageFactory.initElements(driver.getDriver(), ImsBonusPage.class);
        }
        return null;
    }

    public String getAction(int i){
        return actionsTable.getAction(i).getAction();
    }

    public String getSummary(int i){
        return actionsTable.getAction(i).getSummary();
    }

    public void clickDelete(int i){
        WebElement buttonToDisappear = deleteButtons.get(i);
        deleteButtons.get(i).click();
        driver.waitUntilElementAppearVisible(bConfirmationMessageOk);
        bConfirmationMessageOk.click();
        driver.waitUntilElementDisappear(buttonToDisappear);
        actionsTable.refresh();
        driver.waitForAjax();
    }

    public ActivityTabPage clickEdit(int i){
        String type = getAction(i+1);
        editButtons.get(i).click();
        if (type.equals("Update Player Details")) {
            return StalePageFactory.initElements(driver.getDriver(), UpdateCustomFieldsPage.class);
        } else if (type.equals("IMS Bonus")) {
            return StalePageFactory.initElements(driver.getDriver(), ImsBonusPage.class);
        }
        return null;
    }

    public Boolean isActionsTableEmpty(){
        actionsTable.refresh();
        Boolean res = false;
        if((actionsTable.count() == 1)&&(actionsTable.getAction(1).getAction().equals(""))){
            res = true;
        }
        return res;
    }

    public ActionsPage clickTestNo() {
        driver.findElement(By.xpath("//*[@id='testFalse']")).click();
        return StalePageFactory.initElements(driver.getDriver(), ActionsPage.class);
    }

    public TestGroupsPage clickTestYes() {
        driver.findElement(By.xpath("//*[@id='testTrue']")).click();
        return StalePageFactory.initElements(driver.getDriver(), TestGroupsPage.class);
    }

//    public boolean getTest(){
//        return activityTestTrue.isSelected();
//    }

    public void setTest(boolean rez) {
        if (rez) {
            clickTestYes();
        }
        else {
            clickTestNo();
        }
        driver.sleep(1000);
    }
}
