package com.playtech.cm.pages.dashboard.campaign;

import com.playtech.cm.utils.dataProviders.CampaignData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static com.playtech.cm.BaseTest.driver;
/**
 * User: Denis Veselovskiy
 * Date: 5/3/12
 * Time: 9:59 AM
 */
public class CampaignDetailsPage extends CampaignTabs {

    @FindBy(id = "campaignName")
    WebElement name;

    @FindBy(id = "campaignDescription")
    WebElement description;

    @FindBy(id = "instance")
    WebElement instance;

    @FindBy(id = "category")
    WebElement category;

    @FindBy(id = "tags")
    WebElement tags;

    @FindBy(id = "campaignStartDate")
    WebElement startDate;

    @FindBy(id = "campaignPublicationDate")
    WebElement publicationDate;

    @FindBy(id = "campaignEndDate")
    WebElement endDate;

    @FindBy(id = "campaignEndDateOngoing")
    WebElement campaignEndDateOngoing;

    @FindBy(id = "links")
    WebElement links;

    public void fillForm(CampaignData campaignData){
        driver.waitUntilElementAppearVisible(name);
        typeName(campaignData.getName());
        typeDescription(campaignData.getDescription());
        selectInstance(campaignData.getCasinoBrand());
        typeTags(campaignData.getTags());
        typeStartDate(campaignData.getStartDate());
        typePublicationDate(campaignData.getPublicationDate());
        setOngoing(campaignData.getOngoing());
        typeEndDate(campaignData.getEndDate());
        typeLinks(campaignData.getLinks());
        selectCategory((campaignData.getCategory()));
    }

    public void fillRequiredFields(CampaignData campaignData){
        typeName(campaignData.getName());
        selectInstance(campaignData.getCasinoBrand());
        typeStartDate(campaignData.getStartDate());
    }

    public void typeName(String cName){
        name.clear();
        typeQuickly(name, cName);
    }

    public void typeDescription(String cDescription){
        description.clear();
        typeQuickly(description, cDescription);
    }

    public void typeTags(String cTags){
        tags.clear();
        typeQuickly(tags, cTags);
    }

    public void typeStartDate(String cStartDate){
        startDate.clear();
        typeQuickly(startDate, cStartDate);
    }

    public void typePublicationDate(String cPublicationDate){
        publicationDate.clear();
        typeQuickly(publicationDate, cPublicationDate);
    }

    public void typeEndDate(String cEndDate){
        endDate.clear();
        typeQuickly(endDate, cEndDate);
    }

    public void typeLinks(String cLinks){
        links.clear();
        typeQuickly(links, cLinks);
    }

    public void selectInstance(String i) {
        Select s = new Select(instance);
        s.selectByVisibleText(i);
    }

    public String getName() {
        return name.getAttribute("value");
    }

    public String getStatus() {
        return driver.findElement(By.xpath("//div[span[contains(text(),'Status')]]")).getText().split(": ")[1];
    }

    public String getLinks() {
        return links.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public String getTags() {
        return tags.getAttribute("value");
    }

    public String getStartDate() {
        return startDate.getAttribute("value");
    }

    public String getPublicationDate() {
        return publicationDate.getAttribute("value");
    }

    public String getEndDate() {
        return endDate.getAttribute("value");
    }

    public String getInstance() {
        Select casino = new Select(instance);
        return casino.getFirstSelectedOption().getText();
    }

    public String getCategory() {
        Select cat = new Select(category);
        return cat.getFirstSelectedOption().getText();
    }

    public void setOngoing(boolean rez) {
        if (rez) {
            clickOngoingTrue();
        }
        else {
            clickOngoingFalse();
        }
    }

    public boolean getOngoing(){
        if (campaignEndDateOngoing.isSelected()) return true;
        else return false;
    }

    public void selectCategory(String c) {
        Select s = new Select(category);
        s.selectByVisibleText(c);
    }

    public void clickOngoingFalse() {
        if (!campaignEndDateOngoing.isSelected()) return;
        campaignEndDateOngoing.click();
    }

    public void clickOngoingTrue() {
        if (campaignEndDateOngoing.isSelected()) return;
        campaignEndDateOngoing.click();
    }

    private WebElement getInstanceElement() {
        driver.waitUntilElementAppearVisible(instance);
        return instance;
    }

    public boolean isDisplayed() {
        return getInstanceElement().isDisplayed();
    }



}
