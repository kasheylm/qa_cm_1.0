package com.playtech.cm.pages.dashboard.campaign.activities.conditions;

import com.google.common.base.Function;
import com.playtech.cm.utils.extendedFactory.StalePageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;

import static com.playtech.cm.BaseTest.driver;
import static org.testng.AssertJUnit.assertTrue;

/**
 * User: Denis Veselovskiy
 * Date: 10/8/12 * Time: 11:46 AM
 */
public class UploadCSVPage extends ConditionDialogPage {

    @FindBy (id="uploadForm")
    WebElement uploadForm;

    @FindBy (id="file")
    WebElement uploadButton;

    @FindBy (id="checkbox")
    WebElement includeHeaders;

    @FindBy (xpath="//*[@id='addConditionPanelZone']/div[2]")
    WebElement uploadedFile;

    @FindBy (xpath="//*[@id='progressBarFrame']/a")
    WebElement cancelUpload;

    String includeHeadersCheckBox = "checkbox";

    public UploadCSVPage() {
        driver.waitUntilElementAppearVisible(By.id(includeHeadersCheckBox));
    }

    public void uploadFile(String name) {
        driver.waitForAjax();
        driver.waitUntilElementAppearVisible(uploadButton);
        File file = new File(getClass().getClassLoader().getResource("upload/" + name).getPath());
        uploadButton.sendKeys(file.getAbsolutePath().replace("%20"," "));
    }

    private void clickIncludeHeadersNo() {
        if (includeHeaders.isSelected()) return;
        includeHeaders.click();
    }

    private void clickIncludeHeadersYes() {
        if (includeHeaders.isSelected()) return;
        includeHeaders.click();
    }

    public void setIncludeHeaders(boolean rez) {
        if (rez) {
            clickIncludeHeadersYes();
        }
        else {
            clickIncludeHeadersNo();
        }
    }

    public void switchToFrame() {
        driver.switchTo().frame(1);
    }
    public void switchToPage() {
        driver.switchTo().defaultContent();
    }

    public void waitUntilFileUpload() {
        driver.waitUntilElementAppearVisible(uploadedFile);
    }

    public void waitUntilUploadFileTextChanges(final String text) {
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return (getUploadedFileName().contains(text));
            }
        });
    }

    public UploadCSVPage clickCancelUpload() {
        cancelUpload.click();
        return StalePageFactory.initElements(driver.getDriver(), UploadCSVPage.class);
    }

    public String getUploadedFileName() {
        return uploadedFile.getText();
    }

    public boolean getIncludeHeaders() {
        return includeHeaders.isSelected();
    }
}
