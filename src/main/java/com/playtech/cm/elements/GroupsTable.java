package com.playtech.cm.elements;

import com.playtech.cm.utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static com.playtech.cm.BaseTest.driver;

/**
 * User: Denis Veselovskiy
 * Date: 10/29/12 * Time: 6:04 PM
 */
public class GroupsTable {

    private HashMap<String , GroupRow> groupsTable = new HashMap<String, GroupRow>();
    private final String XPATH_TABLE = "//*[@id='actionsGridZone']/div[2]//table/tbody/tr";

    public GroupsTable() {
        refresh();
    }

    public void refresh() {
        groupsTable.clear();
        driver.waitForAjax();
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            List<WebElement> map = driver.findElements(By.xpath(XPATH_TABLE));
            for (int i = 1; i <= map.size(); i++ ) {
                GroupRow groupRow = new GroupRow();
                groupRow.setName(driver.findElement(By.xpath("(" + XPATH_TABLE + ")[" + i + "]")).findElement(By.className("name")).getText());
                if(driver.findElements(By.xpath("(" + XPATH_TABLE + ")[" + i + "]//select[contains(@id,'selectTestAction')]")).size() == 0){
                    groupRow.setAction(driver.findElement(By.xpath("(" + XPATH_TABLE + ")[" + i + "]")).findElement(By.className("action")).getText());
                } else {
                    Select select = new Select(driver.findElement(By.xpath("(" + XPATH_TABLE + ")[" + i + "]//*[contains(@id,'selectTestAction')]")));
                    groupRow.setAction(select.getFirstSelectedOption().getText());
                }

                groupRow.setSummary(driver.findElement(By.xpath("(" + XPATH_TABLE + ")[" + i + "]")).findElement(By.className("summary")).getText());
                groupsTable.put(groupRow.getName(), groupRow);
            }
        } finally {
            driver.manage().timeouts().implicitlyWait(Config.shortTimeoutMS, TimeUnit.MILLISECONDS);
        }
    }

    public HashMap<String, GroupRow> getGroupsTable() {
        refresh();
        return groupsTable;
    }

    public int count() {
        return groupsTable.size();
    }

    public boolean isGroupExist(String groupName) {
        return groupsTable.containsKey(groupName);
    }

    public GroupRow getGroup(String groupName) {
        return groupsTable.get(groupName);
    }
}
