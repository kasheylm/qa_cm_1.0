package com.playtech.cm.pages.dashboard.campaign.activities.actions;

import com.google.common.base.Function;
import com.playtech.cm.pages.dashboard.campaign.activities.ActivityTabPage;
import com.playtech.cm.utils.Config;
import com.playtech.cm.utils.entities.actions.ImsBonusEntity;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.playtech.cm.BaseTest.driver;

/**
 * User: Denis Veselovskiy
 * Date: 10/29/12 * Time: 2:56 PM
 */
public class ImsBonusPage extends ActivityTabPage {
    @FindBy(id = "bonusTemplateNameFilterField")
    WebElement bonusTemplateName;

    @FindBy(xpath = "//div[@id='filterBonusTemplatesZone']/select")
    WebElement templateList;

    @FindBy(id = "selectedAmountSourceCodeField")
    WebElement sourceBonusCalcAmount;

    @FindBy(xpath = "//*[@id='findBonusTemplateForm']//*[@id='submitAction']")
    WebElement okButton;

    @FindBy(xpath = "//*[@id='findBonusTemplateForm']//*[@value='Cancel']")
    WebElement cancelButton;

    @FindBy(xpath = "//a[text()='Create New IMS Bonus']")
    WebElement createNewImsBonusLink;

    public ActivityTabPage clickOk(Boolean isActivityTest){
        driver.waitUntilElementAppearVisible(okButton);
        okButton.click();
        driver.waitForAjax();
        if(isActivityTest){
            return StalePageFactory.initElements(driver.getDriver(), TestGroupsPage.class);
        }
        return StalePageFactory.initElements(driver.getDriver(), ActionsPage.class);
    }

    public ActivityTabPage clickCancel(Boolean isActivityTest){
        driver.waitUntilElementAppearVisible(cancelButton);
        driver.waitForAjax();
        cancelButton.click();
        driver.waitForAjax();
        if(isActivityTest){
            return StalePageFactory.initElements(driver.getDriver(), TestGroupsPage.class);
        }
        return StalePageFactory.initElements(driver.getDriver(), ActionsPage.class);
    }

    public String clickNewImsBonusLink(){
        String resultLink;
        driver.waitUntilElementAppearVisible(createNewImsBonusLink);
        driver.waitForAjax();
        createNewImsBonusLink.click();

        driver.waitUntil(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                Set<String> handles = driver.getWindowHandles();
                return (handles.size() > 1);
            }
        });
        Set<String> handles = driver.getWindowHandles();
        driver.switchTo().window((String) handles.toArray()[1]);
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                Set<String> handles = driver.getWindowHandles();
                return (!driver.getCurrentUrl().equals("about:blank"));
            }
        });
        resultLink = driver.getCurrentUrl();
        driver.close();
        driver.switchTo().window((String) handles.toArray()[0]);
        return resultLink;
    }

    public void typeBonusTemplateNameToFilter(String templateName){
        driver.waitForAjax();
        driver.waitUntilElementAppearVisible(bonusTemplateName);
        driver.waitForAjax();
        driver.addAttributeToElement(templateList, "attr_to_dissappear", "test");
        bonusTemplateName.clear();
        typeQuickly(bonusTemplateName, templateName);
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                return !driver.isElementPresent(By.xpath("//*[@attr_to_dissappear='test']"));
            }
        });
    }

    public void selectBonusTemplate(String templateName){
        driver.waitUntilElementAppearVisible(templateList);
        driver.waitForAjax();
        Select select = new Select(templateList);
        select.selectByVisibleText(templateName);
    }

    public void selectSourceBonusCalcAmount(String value){
        driver.waitUntilElementAppearVisible(sourceBonusCalcAmount);
        driver.waitForAjax();
        Select select = new Select(sourceBonusCalcAmount);
        select.selectByVisibleText(value);
    }

    public String getSelectedBonusTemplateName(){
        String res = null;
        driver.waitUntilElementAppearVisible(templateList);
        driver.waitForAjax();
        if(driver.isElementPresent(By.xpath("option[@value='" + templateList.getAttribute("value") + "']"))){
            res = templateList.findElement(By.xpath("option[@value='"+templateList.getAttribute("value")+"']")).getText();
        } else if(templateList.findElements(By.xpath("option[@selected='selected']")).size() > 0){
            res = templateList.findElement(By.xpath("option[@selected='selected']")).getText();
        }
        else {
            res = null;
        }
        return res;
    }

    public String getBonusTemplateNameFilter(){
        driver.waitUntilElementAppearVisible(bonusTemplateName);
        driver.waitForAjax();
        return bonusTemplateName.getAttribute("value");
    }

    public String getSelectedSourceBonusCalcAmount(){
        driver.waitUntilElementAppearVisible(sourceBonusCalcAmount);
        driver.waitForAjax();
        Select select = new Select(sourceBonusCalcAmount);
        return select.getFirstSelectedOption().getText();
    }

    public List<String> getAvailableBonusTemplateNames(){
        List<String> res = new ArrayList<String>();
        driver.waitForAjax();
        driver.waitUntilElementAppearVisible(templateList);
        driver.waitForAjax();
        Select select = new Select(templateList);
        for(WebElement option:select.getOptions()){
            res.add(option.getText());
        }
        return res;
    }

    public ImsBonusEntity getAllFields(){
        driver.waitForAjax();
        ImsBonusEntity res = new ImsBonusEntity();
        res.setAvailableTemplates(getAvailableBonusTemplateNames());
        res.setSearchFieldText(getBonusTemplateNameFilter());
        res.setSourceForBonusCalcAmount(getSelectedSourceBonusCalcAmount());
        res.setSelectedTemplateName(getSelectedBonusTemplateName());
        return res;
    }

    public void setAllFields(ImsBonusEntity data){
        driver.waitForAjax();
        typeBonusTemplateNameToFilter(data.getSearchFieldText());
        driver.waitForAjax();
        selectBonusTemplate(data.getSelectedTemplateName());
        selectSourceBonusCalcAmount(data.getSourceForBonusCalcAmount());
    }
}
