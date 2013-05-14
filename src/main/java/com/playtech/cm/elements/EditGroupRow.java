package com.playtech.cm.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.playtech.cm.BaseTest.driver;

/**
 * User: Denis Veselovskiy
 * Date: 10/31/12 * Time: 3:39 PM
 */
public class EditGroupRow {

    private String groupName;
    private String percent;
    private String xpathControlGroup = "//*[contains(text(),'Control Group')]";

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public WebElement getNameCell() {
        return driver.findElement(By.xpath("//*[@value='" + getGroupName() + "']"));
    }

    public String getPercent() {
        return  driver.findElement(By.xpath("//*[@value='" + getGroupName() + "']"
                + "/../following-sibling::*//*[@class='percentBox']")).getAttribute("value");
    }

    public void typeGroupName(String name) {
        getNameCell().clear();
        getNameCell().sendKeys(name);
    }

    public void typePercent(String percent) {
        driver.findElement(By.xpath("//*[@value='" + getGroupName() + "']/../following-sibling::*//*[@class='percentBox']")).clear();
        driver.findElement(By.xpath("//*[@value='" + getGroupName() + "']/../following-sibling::*//*[@class='percentBox']")).sendKeys(percent);
    }

    public String getControlGroupPercent() {
        return driver.findElement(By.xpath(xpathControlGroup + "/following-sibling::*//*[@class='percentBox']")).getAttribute("value");
    }

    public void typeControlGroupPercent(String percent) {
        driver.findElement(By.xpath(xpathControlGroup + "/../following-sibling::*//*[@class='percentBox']")).clear();
        driver.findElement(By.xpath(xpathControlGroup + "/../following-sibling::*//*[@class='percentBox']")).sendKeys(percent);
    }

    public void clickDelete() {
        driver.findElement(By.xpath("//*[@value='" + getGroupName() + "']/../following-sibling::*//*[@class='button']")).click();
    }

}
