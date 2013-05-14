package com.playtech.cm.pages.dashboard.campaign;

import com.playtech.cm.pages.dashboard.HomePage;
import com.playtech.cm.utils.entities.campaignExport.CampaignExportEntity;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.playtech.cm.BaseTest.driver;

/**
 * User: Denis Veselovskiy
 * Date: 10/22/12, Time: 3:37 PM
 */
public class CampaignExportPage extends HomePage {
    @FindBy(id = "exportType")
    WebElement exportType;

    @FindBy(id = "exportActivities")
    WebElement exportActivities;

    @FindBy(id = "actionedDateRange-from")
    WebElement actionedFrom;

    @FindBy(id = "actionedDateRange-to")
    WebElement actionedTo;

    @FindBy(name = "exportFields-avail")
    WebElement availableExportFields;

    @FindBy(name = "exportFields-selected")
    WebElement selectedExportFields;

    @FindBy(xpath = "//*[@id='exportFields-select']")
    WebElement addButton;

    @FindBy(xpath = "//*[@id='exportFields-deselect']")
    WebElement removeButton;

    @FindBy(id = "fileName")
    WebElement exportFileName;

    @FindBy(id = "fileFormat")
    WebElement exportFileFormat;

    @FindBy(css = "#exportCampaignHistoryForm #submit")
    WebElement exportButton;

    @FindBy(id = "exportGroups")
    WebElement testGroups;

    @FindBy(xpath = "//div[@id='exportCampaignHistory']//*[@value='Cancel']")
    WebElement cancelButton;

//    Elementary set-functions
    public void selectExportType(String s){
        driver.waitUntilElementAppearVisible(exportType);
        Select selectControl = new Select(exportType);
        driver.waitForAjax();
        selectControl.selectByVisibleText(s);
        driver.waitForAjax();
    }

    public void selectExportActivities(String s){
        driver.waitUntilElementAppearVisible(exportActivities);
        Select selectControl = new Select(exportActivities);
        selectControl.selectByVisibleText(s);
    }

    public void selectExportFileFormat(String s){
        driver.waitUntilElementAppearVisible(exportFileFormat);
        Select selectControl = new Select(exportFileFormat);
        selectControl.selectByVisibleText(s);
    }

    public void typeActionedFrom(String s){
        driver.waitUntilElementAppearVisible(actionedFrom);
        actionedFrom.clear();
        typeQuickly(actionedFrom, s);
    }

    public void typeActionedTo(String s){
        driver.waitUntilElementAppearVisible(actionedTo);
        actionedTo.clear();
        typeQuickly(actionedTo, s);
    }

    public void typeExportFileName(String s){
        driver.waitUntilElementAppearVisible(exportFileName);
        exportFileName.clear();
        typeQuickly(exportFileName, s);
    }

    public void selectAvailableExportFields(List<String> fields){
        driver.waitUntilElementAppearVisible(availableExportFields);
        driver.waitForAjax();
        Select selectControl = new Select(availableExportFields);
        selectControl.deselectAll();
        for(String f:fields){
            selectControl.selectByVisibleText(f);
            driver.waitForAjax();
        }
    }

    public void selectTestGroups(List<String> groups){
        driver.waitUntilElementAppearVisible(testGroups);
        List<String> groupsToDeselect = getTestGroups();
        testGroups.click();
        if(!groupsToDeselect.equals(Arrays.asList("All Test Groups"))){
            for(String group:groupsToDeselect){
                String xpath = "//*[text()='"+group+"']/../input";
                testGroups.findElement(By.xpath(xpath)).click();
            }
        }

        for(String group:groups){
            String xpath = "//*[text()='"+group+"']/../input";
            if(!testGroups.findElement(By.xpath(xpath)).isSelected()){
                testGroups.findElement(By.xpath(xpath)).click();
            }
        }
        testGroups.click();
    }

    public void deselectAllTestGroups(){
        driver.waitUntilElementAppearVisible(testGroups);
        testGroups.click();
        List<WebElement> items = testGroups.findElements(By.xpath("..//li/input[@type='checkbox']"));
        for(WebElement item:items){
            if(item.isSelected()){
                item.click();
            }
        }
        testGroups.click();
    }

    public void selectSelectedExportFields(List<String> fields){
        driver.waitUntilElementAppearVisible(selectedExportFields);
        Select selectControl = new Select(selectedExportFields);
        selectControl.deselectAll();
        for(String f:fields){
            selectControl.selectByVisibleText(f);
        }
    }

    public void deselectAllSelectedExportFields(){
        List<WebElement> selectedItems;
        driver.waitUntilElementAppearVisible(selectedExportFields);
        Select selectControl = new Select(selectedExportFields);
        selectedItems = selectControl.getAllSelectedOptions();
        List<WebElement> elements = selectControl.getAllSelectedOptions();
        for(WebElement e:elements){
            selectControl.selectByVisibleText(e.getText());
        }
        removeButton.click();
    }

//    Elementary get-functions
    public String getExportType(){
        driver.waitUntilElementAppearVisible(exportType);
        Select selectControl = new Select(exportType);
        return selectControl.getFirstSelectedOption().getText();
    }

    public String getExportActivities(){
        driver.waitUntilElementAppearVisible(exportActivities);
        Select selectControl = new Select(exportActivities);
        return selectControl.getFirstSelectedOption().getText();
    }

    public String getExportFileFormat(){
        driver.waitUntilElementAppearVisible(exportFileFormat);
        Select selectControl = new Select(exportFileFormat);
        return selectControl.getFirstSelectedOption().getText();
    }

    public  String getActionedFrom(){
        driver.waitUntilElementAppearVisible(actionedFrom);
        return actionedFrom.getAttribute("value");
    }

    public  String getExportFileName(){
        driver.waitUntilElementAppearVisible(exportFileName);
        return exportFileName.getAttribute("value");
    }

    public  String getActionedTo(){
        driver.waitUntilElementAppearVisible(actionedTo);
        return actionedTo.getAttribute("value");
    }

    public List<String> getAvailableExportFields(){
        List<String> res = new ArrayList();
        int i = 0;
        driver.waitUntilElementAppearVisible(availableExportFields);
        do{
            try{
                Select selectControl = new Select(availableExportFields);
                List<WebElement> elements = selectControl.getOptions();
                for(WebElement e:elements){
                    res.add(e.getText());
                }
                break;
            } catch(Exception e){
                res.clear();
                driver.sleep(100);
            }
            i+=1;
        }
        while (i<=3);
        return res;
    }

    public List<String> getSelectedExportFields(){
        List<String> res = new ArrayList();
        int i = 0;
        driver.waitUntilElementAppearVisible(selectedExportFields);
        do{
            try{
                Select selectControl = new Select(selectedExportFields);
                List<WebElement> elements = selectControl.getOptions();
                for(WebElement e:elements){
                    res.add(e.getText());
                }
                break;
            } catch(Exception e){
                res.clear();
                driver.sleep(100);
            }
            i+=1;
        }
        while (i<=3);
        return res;
    }

    public void setAllFields(CampaignExportEntity data){
        driver.waitForAjax();
        selectExportType(data.getExportType());
        selectExportActivities(data.getExportActivities());
        selectTestGroups(data.getTestGroups());

        typeActionedFrom(data.getActionedFrom());
        typeActionedTo(data.getActionedTo());
        deselectAllSelectedExportFields();
        selectAvailableExportFields(data.getSelectedFields());
        addButton.click();
        typeExportFileName(data.getExportFileName());
        selectExportFileFormat(data.getExportFileFormat());
    }

    public void setFieldsWithoutTestGroups(CampaignExportEntity data){
        driver.waitForAjax();
        selectExportType(data.getExportType());
        selectExportActivities(data.getExportActivities());
        typeActionedFrom(data.getActionedFrom());
        typeActionedTo(data.getActionedTo());
        deselectAllSelectedExportFields();
        selectAvailableExportFields(data.getSelectedFields());
        addButton.click();
        typeExportFileName(data.getExportFileName());
        selectExportFileFormat(data.getExportFileFormat());
    }

    public CampaignExportEntity getFieldsWithoutTestGroups(){
        driver.waitForAjax();
        CampaignExportEntity data = new CampaignExportEntity(false);
        data.setExportType(getExportType());
        data.setExportActivities(getExportActivities());
        data.setActionedFrom(getActionedFrom());
        data.setActionedTo(getActionedTo());
        data.setAvailableFields(getAvailableExportFields());
        data.setSelectedFields(getSelectedExportFields());
        data.setExportFileName(getExportFileName());
        data.setExportFileFormat(getExportFileFormat());
        return data;
    }

    public CampaignExportEntity getAllFields(){
        driver.waitForAjax();
        CampaignExportEntity data = new CampaignExportEntity(true);
        data.setExportType(getExportType());
        data.setTestGroups(getTestGroups());
        data.setExportActivities(getExportActivities());
        data.setActionedFrom(getActionedFrom());
        data.setActionedTo(getActionedTo());
        data.setAvailableFields(getAvailableExportFields());
        data.setSelectedFields(getSelectedExportFields());
        data.setExportFileName(getExportFileName());
        data.setExportFileFormat(getExportFileFormat());
        return data;
    }

    public void clickExportButton(){
        driver.waitUntilElementAppearVisible(exportButton);
        exportButton.click();
    }

    public List<String> getTestGroups() {
        driver.waitUntilElementAppearVisible(testGroups);
        String groups = testGroups.getText();
        String[] res = groups.split(",\\s");
        return Arrays.asList(res);
    }

    public CampaignTabs clickCancel(){
        driver.waitForAjax();
        cancelButton.click();
        driver.waitForAjax();
        return StalePageFactory.initElements(driver, CampaignTabs.class);
    }

    public void clickAddExportFields(){
        addButton.click();
        driver.waitForAjax();
    }

    public void clickRemoveExportFields(){
        removeButton.click();
        driver.waitForAjax();
    }
}
