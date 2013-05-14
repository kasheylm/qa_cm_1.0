package com.playtech.cm.pages.dashboard;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

import static com.playtech.cm.BaseTest.driver;


/**
 * Created with IntelliJ IDEA.
 * User: Denis Veselovskiy
 * Date: 19/04/12
 * Time: 09:44
 */
public class Page {

    public void typeQuickly(WebElement e, String str) {
        driver.waitUntilElementAppearVisible(e);
        StringSelection ss = new StringSelection(str);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
        e.click();
        Actions builder =new Actions(driver.getDriver());
        builder.sendKeys(Keys.chord(Keys.LEFT_CONTROL, "v")).perform();
        builder.sendKeys(Keys.ARROW_UP).perform();

    }

    public boolean typeText(By by, String text) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 2) {
            try {
                driver.findElement(by).sendKeys(text);
                result = true;
                break;
            } catch(StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
    }

    public void acceptAlert(){
        driver.sleep(1000);
        if (driver.isAlertPresent()) {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
    }

}
