package com.playtech.cm.pages.dashboard.campaign.activities.actions;

import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.utils.entities.actions.CustomFieldsEntity;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static com.playtech.cm.BaseTest.driver;

/**
 * User: Denis Veselovskiy
 * Date: 10/29/12 * Time: 3:01 PM
 */
public class UpdateCustomFieldsPage extends ActivityTabPage {

    @FindBy(id = "selectedUpdatePlayerParamsCustomFieldNameField")
    WebElement fieldName;

    @FindBy(id = "selectedUpdatePlayerParamsCustomFieldValueField")
    WebElement fieldValue;

    @FindBy(id = "updatePlayerSubmitButton")
    WebElement okButton;

    @FindBy(xpath = "//*[@id='updatePlayerParamsActionForm']//*[@value='Cancel']")
    WebElement cancelButton;

    @FindBy(id="updatePlayerParamsActionForm")
    WebElement form;

    public void selectField(String field) {
        driver.waitUntilElementAppearVisible(fieldName);
        driver.waitForAjax();
        Select s = new Select(fieldName);
        s.selectByVisibleText(field);
    }

    public void typeFieldValue(String value) {
        fieldValue.clear();
        typeQuickly(fieldValue, value);
    }

    public ActionsPage clickOK() {
        driver.waitForAjax();
        okButton.click();
        driver.waitForAjax();
        return StalePageFactory.initElements(driver.getDriver(), ActionsPage.class);
    }

    public ActionsPage clickOkAndValidateAlert(String expectedAlertText) {
//This piece of js is here because there is no other way to handle alert on page
//                 String js = "var evt = document.createEvent('HTMLEvents');\n" +
//                "evt.initEvent('click', true, true);\n" +
//                "$('updatePlayerSubmitButton').dispatchEvent(evt);";
//                 driver.getJavascriptExecutor().executeScript(js);
        String alertMessage = (String) driver.getJavascriptExecutor().executeScript("return arguments[0].click();", okButton);
        okButton.submit();
        assert(alertMessage.equals(expectedAlertText));
        return StalePageFactory.initElements(driver.getDriver(), ActionsPage.class);
    }

    public ActionsPage clickCancel() {
        driver.waitForAjax();
        cancelButton.click();
        driver.waitForAjax();
        return StalePageFactory.initElements(driver.getDriver(), ActionsPage.class);
    }

    public String getFieldName() {
        Select s = new Select(fieldName);
        return s.getFirstSelectedOption().getText();
    }

    public String getFieldValue() {
        return fieldValue.getAttribute("value");
    }

    public void waitForm() {
        driver.waitUntilElementAppearVisible(form);
        driver.waitForAjax();
    }

    public CustomFieldsEntity getForm() {
        driver.waitUntilElementAppearVisible(fieldValue);
        waitForm();
        CustomFieldsEntity customFieldsEntity = new CustomFieldsEntity();
        customFieldsEntity.setName(getFieldName());
        customFieldsEntity.setValue(getFieldValue());
        return customFieldsEntity;
    }
}
