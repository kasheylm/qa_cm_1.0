package com.playtech.cm.pages.dashboard;

import com.playtech.cm.pages.dashboard.HomePage;
import com.playtech.cm.pages.dashboard.campaign.CampaignsPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.playtech.cm.BaseTest.driver;

/**
 * Created with IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 4/25/12
 * Time: 7:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class MonitoringAndReportingPage extends HomePage {

    @FindBy(linkText = "Operational Dashboard")
    WebElement linkOperationalDashboard;

    @FindBy(linkText = "Campaign Manager")
    WebElement linkProTrackDashboard;

    public CampaignsPage clickLinkProTrackDashboard() {
        linkProTrackDashboard.click();
        return PageFactory.initElements(driver.getDriver(), CampaignsPage.class);
    }
}
