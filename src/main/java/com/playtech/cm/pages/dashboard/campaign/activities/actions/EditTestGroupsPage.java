package com.playtech.cm.pages.dashboard.campaign.activities.actions;

import com.google.common.base.Function;
import com.playtech.cm.elements.EditGroupsTable;
import com.playtech.cm.elements.GroupsTable;
import com.playtech.cm.pages.dashboard.HomePage;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.playtech.cm.BaseTest.driver;

/**
 * User: Denis Veselovskiy
 * Date: 10/29/12 * Time: 4:32 PM
 */
public class EditTestGroupsPage extends HomePage{

    @FindBy(id = "campaignTestGroupsForm")
    WebElement campaignTestGroupsForm;

    @FindBy(xpath = "//*[@id='campaignTestGroupsForm']//*[@value='OK']")
    WebElement okButton;

    @FindBy(xpath = "//*[@id='campaignTestGroupsForm']//*[@value='Cancel']")
    WebElement cancelButton;

    private EditGroupsTable editGroupsTable;

    public EditTestGroupsPage() {
        this.editGroupsTable = new EditGroupsTable();
    }

    public TestGroupsPage clickOk(){
        okButton.click();
        return StalePageFactory.initElements(driver.getDriver(), TestGroupsPage.class);
    }

    public TestGroupsPage clickCancel(){
        cancelButton.click();
        return StalePageFactory.initElements(driver.getDriver(), TestGroupsPage.class);
    }

    public EditGroupsTable getGroupsTable() {
        editGroupsTable.refresh();
        return editGroupsTable;
    }

    public boolean isGroupExist(String groupName) {     //group name should equals key
        return getGroupsTable().isGroupExist(groupName);
    }

    public String getPercent(String group) {
        return getGroupsTable().getGroup(group).getPercent();
    }

    public String getControlGroupPercent() {
        return getGroupsTable().getGroup("Control Group").getControlGroupPercent();
    }

    public void typePercent(String group, String percent) {
        getGroupsTable().getGroup(group).typePercent(percent);
    }

    public void typeGroupName(String oldName, String newName) {
        getGroupsTable().getGroup(oldName).typeGroupName(newName);
    }

    public void clickDelete(String group) {
        getGroupsTable().getGroup(group).clickDelete();
    }

    public void addGroup() {
        getGroupsTable().addGroup();
    }

    public String getTotal() {
        return getGroupsTable().getTotal();
    }

    public int count() {
        return getGroupsTable().count();
    }

    public void waitFormAppear(){
        driver.waitUntilElementAppearVisible(campaignTestGroupsForm);
    }

    public void waitUntilGroupAppear(final String newGroup) {
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return (isGroupExist(newGroup));
            }
        });
    }
}
