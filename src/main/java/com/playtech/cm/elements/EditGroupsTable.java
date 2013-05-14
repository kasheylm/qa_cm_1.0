package com.playtech.cm.elements;

import com.playtech.cm.utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.playtech.cm.BaseTest.driver;

/**
 * User: Denis Veselovskiy
 * Date: 10/31/12 * Time: 3:25 PM
 */
public class EditGroupsTable {

    private HashMap<String , EditGroupRow> editGroupsTable = new HashMap<String, EditGroupRow>();
    private static String xpathTable = "//*[contains(@class,'testGroups')]/tbody/tr";

    public EditGroupsTable() {
        refresh();
    }

    public void refresh() {
        editGroupsTable.clear();
        try {
            EditGroupRow controlGroupRow = new EditGroupRow();
            controlGroupRow.setGroupName("Control Group");
            controlGroupRow.setPercent(controlGroupRow.getControlGroupPercent());
            editGroupsTable.put(controlGroupRow.getGroupName(), controlGroupRow);
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            List<WebElement> map = driver.findElements(By.xpath(xpathTable));
            for ( int i = 2; i <= map.size()-2; i++) {
                EditGroupRow editGroupRow = new EditGroupRow();
                editGroupRow.setGroupName(driver.findElement(By.xpath("(" + xpathTable + "//input[contains(@id,\"testGroup_\")])[" + (i-1) + "]"))
                                                .getAttribute("value"));
                editGroupRow.setPercent(driver.findElement(By.xpath("(" + xpathTable + "//input[contains(@id,\"testGroupPercent\")])[" + i + "]"))
                        .getAttribute("value"));
                editGroupsTable.put(editGroupRow.getGroupName(), editGroupRow);
            }
        } finally {
            driver.manage().timeouts().implicitlyWait(Config.shortTimeoutMS, TimeUnit.MILLISECONDS);
        }
    }

    public HashMap<String, EditGroupRow> getEditGroupsTable() {
        return editGroupsTable;
    }

    public int count() {
        return editGroupsTable.size();
    }

    public boolean isGroupExist(String groupName) {
        return editGroupsTable.containsKey(groupName);
    }

    public EditGroupRow getGroup(String groupName) {
        return editGroupsTable.get(groupName);
    }

    public void addGroup() {
        driver.findElement(By.id("addrowlink")).click();
    }

    public String getTotal() {
        return driver.findElement(By.id("totalPercent")).getText();
    }
}
