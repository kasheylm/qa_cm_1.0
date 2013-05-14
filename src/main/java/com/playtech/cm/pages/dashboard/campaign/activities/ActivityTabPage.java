package com.playtech.cm.pages.dashboard.campaign.activities;

import com.playtech.cm.pages.dashboard.campaign.CampaignTabs;
import com.playtech.cm.pages.dashboard.campaign.activities.actions.*;
import com.playtech.cm.pages.dashboard.campaign.activities.conditions.ConditionSectionPage;
import com.playtech.cm.pages.dashboard.campaign.activities.triggers.TriggerSectionPage;
import com.playtech.cm.pages.dashboard.campaign.activities.details.ActivityDetailsPage;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.playtech.cm.BaseTest.driver;

/**
 * Created with IntelliJ IDEA.
 * User: Denis Veselovskiy
 * Date: 24/09/12
 * Time: 14:49
 */
public class ActivityTabPage extends CampaignTabs {
    @FindBy(xpath = "//span[@title='Triggers']")
    WebElement triggersAccordionSection;

    @FindBy(xpath = "//span[@title='Conditions']")
    WebElement conditionSection;

    @FindBy(xpath = "//span[@title='Actions']")
    WebElement actionSection;

    String openedSection = "//div[@class=\"yui3-widget-bd\" and @style=\"height: 290px;\"]";
    String isConditionSectionOpened = openedSection + "//*[@id='conditionsZone']";
    String isActionSectionOpened = openedSection + "//*[@id='actionsGridZone']";

    @FindBy(xpath = "//*[@id='accordionmenusection']//*[contains(text(),'Details')]")
    WebElement detailsSection;

    public ActivityDetailsPage clickDetails(){
        detailsSection.click();
        return StalePageFactory.initElements(driver.getDriver(), ActivityDetailsPage.class);
    }

    public ActivityDetailsPage getOpenedDetails(){
        return StalePageFactory.initElements(driver.getDriver(), ActivityDetailsPage.class);
    }

    public TriggerSectionPage clickTriggers(){
        triggersAccordionSection.click();
        return StalePageFactory.initElements(driver.getDriver(), TriggerSectionPage.class);
    }

    public ConditionSectionPage clickConditions(){
        driver.clickAndWait(conditionSection, isConditionSectionOpened);
        return StalePageFactory.initElements(driver.getDriver(), ConditionSectionPage.class);
    }

    public ActionsPage clickActions(){
        driver.clickAndWait(actionSection, isActionSectionOpened);
        driver.waitForAjax();
        return StalePageFactory.initElements(driver.getDriver(), ActionsPage.class);
    }

    public TestGroupsPage clickActionsWithTestGroups(){
        driver.clickAndWait(actionSection, isActionSectionOpened);
        driver.waitForAjax();
        return StalePageFactory.initElements(driver.getDriver(), TestGroupsPage.class);
    }
}
