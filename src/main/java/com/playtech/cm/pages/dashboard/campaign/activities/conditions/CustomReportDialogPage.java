package com.playtech.cm.pages.dashboard.campaign.activities.conditions;

import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static com.playtech.cm.BaseTest.driver;

/**
 * User: Denis Veselovskiy
 * Date: 10/8/12 * Time: 11:45 AM
 */
public class CustomReportDialogPage extends ConditionDialogPage {

    @FindBy(xpath = "//select[contains(@id, 'customReportSelect_')]")
    WebElement customReportSelect;

    public ConditionDialogPage addReport(String report){
        Select conditionDropdown = new Select(customReportSelect);
        conditionDropdown.selectByVisibleText(report);
        if (report.equals("Lapsed player report")) {
            return StalePageFactory.initElements(driver.getDriver(), LapsedPlayerReportPage.class);
        } else if (report.equals("Player game losses")) {
            return StalePageFactory.initElements(driver.getDriver(), GameLossesPlayerReportPage.class);
        }
        return null;
    }
}
