package com.playtech.cm.pages.dashboard.campaign.activities.actions;

import com.google.common.base.Function;
import com.playtech.cm.elements.GroupsTable;
import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.playtech.cm.BaseTest.driver;

/**
 * User: Denis Veselovskiy
 * Date: 10/29/12 * Time: 4:04 PM
 */
public class TestGroupsPage extends ActionsPage{

    @FindBy(xpath = "//*[@id='actionsGridZone']//a[contains(@title,'Edit Test Groups')]")
    WebElement editTestGroupsLink;

    private GroupsTable groupsTable;

    public TestGroupsPage() {
        this.groupsTable = new GroupsTable();
    }

    public EditTestGroupsPage clickEditTestGroupsLink() {
        editTestGroupsLink.click();
        return StalePageFactory.initElements(driver.getDriver(), EditTestGroupsPage.class);
    }

    public GroupsTable getGroupsTable() {
        groupsTable.refresh();
        return groupsTable;
    }

    public ActivityTabPage clickEdit(String groupName) {
        return getGroupsTable().getGroup(groupName).clickEdit();
    }

    public void clickDelete(String groupName) {
        getGroupsTable().getGroup(groupName).clickDelete();
    }

    public ActivityTabPage addAction(String groupName, String actionType) {
        driver.waitForAjax();
        return getGroupsTable().getGroup(groupName).addAction(actionType);
    }

    public String getAction(String groupName) {
        return getGroupsTable().getGroup(groupName).getAction();
    }

    public String getSummary(String groupName) {
        return getGroupsTable().getGroup(groupName).getSummary();
    }

    public int groupsCount() {
        return getGroupsTable().count();
    }

    public boolean isGroupExist(String groupName) {     //group name should equals key
        return getGroupsTable().isGroupExist(groupName);
    }

    public void waitFormAppear(){
        driver.waitUntilElementAppearVisible(editTestGroupsLink);
    }

    public void waitUntilGroupNameAppear(final String newGroupName) {
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return (isGroupExist(newGroupName));
            }
        });
    }
}
