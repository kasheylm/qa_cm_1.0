package com.playtech.cm.pages.dashboard;

import com.playtech.cm.pages.dashboard.HomePage;
import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.playtech.cm.BaseTest.driver;

/**
 * Created with IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 9/24/12
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class TemplateToolsPage extends HomePage {
    @FindBy(linkText = "Campaign Manager")
    WebElement linkCMDashboard;

    public CMDashboardPage clickLinkCMDashboard() {
        acceptAlert();
        linkCMDashboard.click();
        acceptAlert();
        return PageFactory.initElements(driver.getDriver(), CMDashboardPage.class);
    }

}
