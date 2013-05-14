package com.playtech.cm.elements;

import com.playtech.cm.pages.dashboard.campaign.CampaignTabs;
import com.playtech.cm.pages.dashboard.newCampaign.NewCampaignPage;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.playtech.cm.BaseTest.driver;

/**
 * User: Denis Veselovskiy
 * Date: 9/11/12 * Time: 1:39 PM
 */
public class CampaignRow {

    private String id;
    private String name;
    private String category;
    private String casino;
    private String startDate;
    private String endDate;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCasino() {
        return casino;
    }

    public void setCasino(String casino) {
        this.casino = casino;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void clickDelete(){
        driver.findElement(By.xpath("//*[@class='name' and text()=\"" + getName() +
                "\"]/../descendant::*[contains(@id,'delete')]")).click();
        driver.findElement(By.xpath("//*[@id='confirmMessageModalBox']//*[@value='OK']")).click();
    }

    public NewCampaignPage clickCopy(){

        driver.findElement(By.xpath("//*[@class='name' and text()=\"" + getName() +
                "\"]/../descendant::*[contains(@alt,'Copy')]")).click();
        return StalePageFactory.initElements(driver.getDriver(), NewCampaignPage.class);
    }

    public CampaignTabs clickEdit(){
        driver.findElement(By.xpath("//*[@class='name' and text()=\"" + getName() +
                "\"]/../descendant::*[contains(@alt,'Edit')]")).click();
        return StalePageFactory.initElements(driver.getDriver(), CampaignTabs.class);
    }

    //TODO refactoring campaignsRow to webElement!  then can find element like row.findElement(By.id("pause"))
    public WebElement getPause(){
        return driver.findElement(By.xpath("//*[@class='name' and text()=\"" + getName() +
                "\"]/../descendant::*[contains(@alt,'Pause')]"));
    }

    public void clickPauseAndAcceptAlert(){
        getPause().click();
        driver.waitUntilAlertAppear().accept();
    }

    public void clickResume(){
        driver.findElement(By.xpath("//*[@class='name' and text()=\"" + getName() +
                "\"]/../descendant::*[contains(@alt,'Resume')]")).click();
    }
}
