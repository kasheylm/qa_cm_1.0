package com.playtech.cm.pages.dashboard.campaign.activities.details;

import com.playtech.cm.pages.dashboard.campaign.CampaignTabs;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.testng.Assert.assertTrue;
import static com.playtech.cm.BaseTest.driver;
/**
 * User: Denis Veselovskiy
 * Date: 9/14/12 * Time: 11:05 AM
 */
public class ActivityDetailsPage extends CampaignTabs {

    @FindBy(id = "activityName")
    WebElement activityName;

    @FindBy(id = "activityDescription")
    WebElement activityDescription;

    @FindBy(id = "activityId")
    WebElement activityId;

    public void typeName(String name){
        activityName.clear();
        typeQuickly(activityName, name);
    }

    public void typeDescription(String description){
        activityDescription.clear();
        typeQuickly(activityDescription, description);
    }

    public String getActivityID(){
        return activityId.getAttribute("value");
    }

    public String getActivityName(){
        return activityName.getAttribute("value");
    }

    public String getActivityDescription(){
        return activityDescription.getAttribute("value");
    }
}
