package com.playtech.cm.pages.dashboard;

import com.playtech.cm.pages.dashboard.campaign.CMDashboardPage;
import com.playtech.cm.utils.Config;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.playtech.cm.BaseTest.driver;

/**
 * Created with IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 4/24/12
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class HomePage extends Page {

    @FindBy(linkText = "Player Management")
    WebElement linkPlayerManagement;

    @FindBy(linkText = "Template Tools")
    WebElement linkTemplateTools;

    @FindBy(linkText = "Traffic Management")
    WebElement linkTrafficManagement;

    @FindBy(linkText = "System Management")
    WebElement linkSystemManagement;

    @FindBy(linkText = "Payments")
    WebElement linkPayments;

    @FindBy(linkText = "Products")
    WebElement linkProducts;

    @FindBy(linkText = "Risk Management")
    WebElement linkRiskManagement;

    @FindBy(linkText = "Monitoring & Reporting")
    WebElement linkMonitoringAndReporting;

    @FindBy(linkText = "Player Management")
    WebElement link;

    @FindBy(id = "logoLink")
    WebElement logoLink;

    @FindBy(css = "span.dotted>b")
    WebElement userLoggedIn;

    @FindBy(linkText = "Log out")
    WebElement linkLogout;

    @FindBy(linkText = "My account")
    WebElement linkMyAccount;

    public MonitoringAndReportingPage clickLinkMonitoringAndReporting() {
        linkMonitoringAndReporting.click();
        return PageFactory.initElements(driver.getDriver(), MonitoringAndReportingPage.class);
    }

    public TemplateToolsPage clickLinkTemplateToolsPage(){
        acceptAlert();
        linkTemplateTools.click();
        return PageFactory.initElements(driver.getDriver(), TemplateToolsPage.class);
    }

    public void logout() {
        linkLogout.click();
    }

    public boolean isErrorPresent(String error) {
        driver.waitForAjax();
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            List<WebElement> list = driver.findElements(By.xpath("//*[contains(@id,'errors')]/div"));
            for  (WebElement webElement : list) {
                if (webElement.getText().contains(error)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.manage().timeouts().implicitlyWait(Config.shortTimeoutMS, TimeUnit.MILLISECONDS);
        }
        return false;
    }

    public int getErrorsCount(){
        driver.waitForAjax();
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            return driver.findElements(By.xpath("//*[contains(@id,'errors')]/div")).size();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.manage().timeouts().implicitlyWait(Config.shortTimeoutMS, TimeUnit.MILLISECONDS);
        }
        return 0;
    }

}
