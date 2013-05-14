package com.playtech.cm.pages.dashboard.search;

import com.google.common.base.Function;
import com.playtech.cm.pages.dashboard.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static com.playtech.cm.BaseTest.driver;

/**
 * Created with IntelliJ IDEA.
 * User: Denis Veselovskiy
 * Date: 03/05/12
 * Time: 18:06
 */
public class SearchPage extends HomePage {
    @FindBy(xpath = "//*[@id='campaignsAccordion']//*[text()='Search']")
    WebElement accordionSearch;

    @FindBy(id = "searchName")
    WebElement name;

    @FindBy(id = "searchId")
    WebElement id;

    @FindBy(id = "searchInstance")
    WebElement instance;

    @FindBy(id = "searchTags")
    WebElement tag;

    @FindBy(id = "search")
    WebElement searchButton;

    @FindBy(xpath = "//form/p")
    WebElement msg;

    @FindBy(id = "searchStatus")
    WebElement status;

    @FindBy(id = "searchCategory")
    WebElement category;

    @FindBy(id = "testCampaignAll")
    WebElement testCampaignAll;

    @FindBy(id = "testCampaignYes")
    WebElement testCampaignYes;

    @FindBy(id = "testCampaignNo")
    WebElement testCampaignNo;

    @FindBy(id = "searchActionCriteria")
    WebElement createdApprovedUpdatedBy;

    @FindBy(id = "searchStartDate")
    WebElement startDate;

    @FindBy(id = "searchEndDate")
    WebElement endDate;

    @FindBy(id = "searchPublicationDate")
    WebElement publicationDate;

    @FindBy(id = "searchActionFromDate")
    WebElement betweenStartDate;

    @FindBy(id = "searchActionToDate")
    WebElement betweenEndDate;

    @FindBy(id = "adminUser")
    WebElement userSelectBox;

    @FindBy(id = "clearFilters")
    WebElement clearButton;


    //TODO fix method name to Test Activity, there is should one income parameters
    public void selectTestCampaign(Boolean yes, Boolean no, Boolean both){
        if(yes){
            testCampaignYes.click();
        }
        if(no) {
            testCampaignNo.click();
        }
        if(both){
            testCampaignAll.click();
        }
    }

    public Boolean isExpanded(){
        String title;
        WebElement expandedIcon = driver.findElement(By.xpath("//*[@id='campaignsAccordion']//*[text()='Search']/..//a[contains(@class,'iconexpanded')]"));
        title = expandedIcon.getAttribute("title");
        return (title.equals("Click to collapse"));
    }

    public void waitForExpanded(){
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                boolean res=false;
                res = isExpanded();
                return res;
            }
        });
    }

    public void expand(){
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                return accordionSearch.isDisplayed();
            }
        });
        accordionSearch.click();
        waitForExpanded();
    }

    public void selectUser(String user) {
        typeQuickly(userSelectBox, user);
    }

    public void selectCreatedApprovedUpdated(String criteria){
        Select s = new Select(createdApprovedUpdatedBy);
        s.selectByVisibleText(criteria);
    }

    public void selectInstance(String instanceName){
        Select s = new Select(instance);
        s.selectByVisibleText(instanceName);
    }

    public void typeName(String nameString){
       name.clear();
       typeQuickly(name, nameString);
    }

    public void typeTag(String tagName){
        tag.clear();
        tag.sendKeys(tagName);
    }

    public void typeId(String idString){
        id.sendKeys(idString);
    }

   public void selectStatus(String statusName){
       Select statusSelect = new Select(status);
       statusSelect.selectByVisibleText(statusName);
   }

   public void selectCategory(String categoryName){
        Select categorySelect = new Select(category);
        categorySelect.selectByVisibleText(categoryName);
   }

   public void clickOnSearchButton(){
       searchButton.click();
   }

   public String getMsg(){
       return msg.getText();
   }

   public String getNameValue(){
       return name.getAttribute("value");
   }

   public String getTagValue(){
       return tag.getAttribute("value");
   }

   public String getStatus(){
       return status.getAttribute("value");
   }


   public void selectStartDate(String date){
       startDate.sendKeys(date);
   }

    public void selectEndDate(String date){
        endDate.sendKeys(date);
    }

    public void selectPublicationDate(String date){
        publicationDate.sendKeys(date);
    }

    public void selectBetweenStartDate(String date){
        betweenStartDate.sendKeys(date);
    }

    public void selectBetweenEndDate(String date){
        betweenEndDate.sendKeys(date);
    }

    public String getStartDate(){
        return startDate.getAttribute("value");
    }

    public String getEndDate(){
        return endDate.getAttribute("value");
    }

    public String getPublicationDate(){
        return publicationDate.getAttribute("value");
    }

    public String getBetweenStartDate(){
        return betweenStartDate.getAttribute("value");
    }

    public String getBetweenEndDate(){
        return betweenEndDate.getAttribute("value");
    }

    public String getId(){
        return id.getAttribute("value");
    }

    public String getUser(){
        return userSelectBox.getText();
    }

    public String getName() {
        return name.getAttribute("value");
    }

    public String getCategory() {
        return category.getAttribute("value");
    }

    public String getInstance() {
        return instance.getAttribute("value");
    }

    public String getCreatedApprovedUpdated() {
        return createdApprovedUpdatedBy.getAttribute("value");
    }

    public String getTestActivity() {
        String res ="default";
        Boolean yes = driver.isElementPresent(By.xpath("//input[@name='testCampaign'][@checked='checked'][@value='Yes']"));
        Boolean no = driver.isElementPresent(By.xpath("//input[@name='testCampaign'][@checked='checked'][@value='No']"));
        Boolean both = driver.isElementPresent(By.xpath("//input[@name='testCampaign'][@checked='checked'][@value='']"));
        if(yes)
            res="Yes";
        if(no)
            res="No";
        if(both)
            res="Both";
        return res;
    }

    public String getTags() {
        return tag.getAttribute("value");
    }

    public void clickClear(){
        driver.waitUntilElementAppearVisible(clearButton);
        clearButton.click();
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                return !driver.isElementVisible(By.xpath("//div[@class='progressBar_content']"));
            }
        });
    }
}
