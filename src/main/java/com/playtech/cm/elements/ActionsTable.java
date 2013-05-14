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
 * Date: 11/16/12 * Time: 1:02 PM
 */
public class ActionsTable {

    private HashMap<Integer , ActionRow> actionsTable = new HashMap<Integer, ActionRow>();
    private final String XPATH_TABLE = "//*[@id='actionsGridZone']/div[2]//table/tbody/tr";

    public ActionsTable() {
        refresh();
    }

    public void refresh() {
        actionsTable.clear();
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            List<WebElement> map = driver.findElements(By.xpath(XPATH_TABLE));
            for (int i = 1; i <= map.size(); i++ ) {
                ActionRow actionRow = new ActionRow();
                actionRow.setAction(driver.findElement(By.xpath("(" + XPATH_TABLE + ")[" + i + "]/td[1]")).getText());
                actionRow.setSummary(driver.findElement(By.xpath("(" + XPATH_TABLE + ")[" + i + "]/td[2]")).getText());
                actionsTable.put(i, actionRow);
            }
        } finally {
            driver.manage().timeouts().implicitlyWait(Config.shortTimeoutMS, TimeUnit.MILLISECONDS);
        }
    }

    public HashMap<Integer, ActionRow> getActionsTable() {
        refresh();
        return actionsTable;
    }

    public int count() {
        return actionsTable.size();
    }

    public ActionRow getAction(int i) {
        return actionsTable.get(i);
    }
}
