package com.playtech.cm.pages.dashboard.campaign.activities.conditions;

import com.playtech.cm.utils.entities.conditions.Filter;
import com.playtech.cm.utils.entities.conditions.LapsedPlayerReportEntity;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import static com.playtech.cm.BaseTest.driver;

/**
 * User: Denis Veselovskiy
 * Date: 10/8/12 * Time: 12:31 PM
 */
public class LapsedPlayerReportPage extends ConditionDialogPage {

    @FindBy(id = "addConditionPanel")
    WebElement addConditionPanel;

    @FindBy(id = "clientType")
    WebElement clientType;

    @FindBy(id = "clientPlatform")
    WebElement clientPlatform;

    @FindBy(id = "lastPlayedAgo")
    WebElement lastdaysAgo;

    @FindBy(id = "vipLevel")
    WebElement vipLevel;

    @FindBy(id = "maximumBalance")
    WebElement maxBalance;

    @FindBy(id = "minimumBalance")
    WebElement minBalance;

    @FindBy(id = "minEarnings")
    WebElement minEarnings;

    @FindBy(id = "maxEarnings")
    WebElement maxEarnings;

    @FindBy(id = "radio_1")
    WebElement suspendedAll;

    @FindBy(id = "radio_0")
    WebElement suspendedNo;

    @FindBy(id = "radio")
    WebElement suspendedYes;

    @FindBy(id = "radio_4")
    WebElement frozenAll;

    @FindBy(id = "radio_3")
    WebElement frozenNo;

    @FindBy(id = "radio_2")
    WebElement frozenYes;

    public void fillForm(LapsedPlayerReportEntity lapsedPlayerReportEntity) {
        driver.waitForAjax();
        setClientType(lapsedPlayerReportEntity.getClientType());
        setClientPlatform(lapsedPlayerReportEntity.getClientPlatform());
        typeLastPlayed(lapsedPlayerReportEntity.getLastPlayedAgo());
        typeVipLevel(lapsedPlayerReportEntity.getVipLevel());
        typeMaxBalance(lapsedPlayerReportEntity.getMaxBalance());
        typeMinBalance(lapsedPlayerReportEntity.getMinBalance());
        typeMinEarnings(lapsedPlayerReportEntity.getMinEarnings());
        typeMaxEarnings(lapsedPlayerReportEntity.getMaxEarnings());
        setSuspend(lapsedPlayerReportEntity.getSuspended());
        setFrozen(lapsedPlayerReportEntity.getFrozen());
    }

    public LapsedPlayerReportEntity getForm(LapsedPlayerReportEntity entity) {
        driver.waitForAjax();
        entity.setClientType(getClientType());
        entity.setClientPlatform(getClientPlatform());
        entity.setLastPlayedAgo(getLastdaysAgo());
        entity.setVipLevel(getVipLevel());
        entity.setMaxBalance(getMaxBalance());
        entity.setMinBalance(getMinBalance());
        entity.setMinEarnings(getMinEarnings());
        entity.setMaxEarnings(getMaxEarnings());
        entity.setSuspended(getSuspended());
        entity.setFrozen(getFrozen());
        return entity;
    }

    public void setClientType(String type) {
        Select s = new Select(clientType);
        s.selectByVisibleText(type);
    }

    public void setClientPlatform(String type) {
        Select s = new Select(clientPlatform);
        s.selectByVisibleText(type);
    }

    public void typeLastPlayed(String s) {
        lastdaysAgo.clear();
        typeQuickly(lastdaysAgo, s);
    }

    public void typeVipLevel(String s) {
        vipLevel.clear();
        typeQuickly(vipLevel, s);
    }

    public void typeMaxBalance(String s) {
        maxBalance.clear();
        typeQuickly(maxBalance, s);
    }

    public void typeMinBalance(String s) {
        minBalance.clear();
        typeQuickly(minBalance, s);
    }

    public void typeMinEarnings(String s) {
        minEarnings.clear();
        typeQuickly(minEarnings, s);
    }

    public void typeMaxEarnings(String s) {
        maxEarnings.clear();
        typeQuickly(maxEarnings, s);
    }

    public void setSuspend(Filter s) {
        switch(s) {
            case ALL: suspendedAll.click(); break;
            case NO:  suspendedNo.click();  break;
            case YES: suspendedYes.click(); break;
        }
    }

    public void setFrozen(Filter s) {
        switch(s) {
            case ALL: frozenAll.click(); break;
            case NO:  frozenNo.click();  break;
            case YES: frozenYes.click(); break;
        }
    }

    public String getClientType() {
        driver.waitUntilElementAppearVisible(clientType);
        Select s = new Select(clientType);
        return s.getFirstSelectedOption().getText();
    }

    public String getClientPlatform() {
        Select s = new Select(clientPlatform);
        return s.getFirstSelectedOption().getText();
    }

    public String getLastdaysAgo() {
        return lastdaysAgo.getAttribute("value");
    }

    public String getVipLevel() {
        return vipLevel.getAttribute("value");
    }

    public String getMaxBalance() {
        return maxBalance.getAttribute("value");
    }

    public String getMinBalance() {
        return minBalance.getAttribute("value");
    }

    public String getMinEarnings() {
        return minEarnings.getAttribute("value");
    }

    public String getMaxEarnings() {
        return maxEarnings.getAttribute("value");
    }

    public Filter getSuspended() {
        if (suspendedAll.isSelected()) {
            return Filter.ALL;
        }
        else if (suspendedNo.isSelected()) {
            return Filter.NO;
        }
        else return Filter.YES;
    }

    public Filter getFrozen() {
        if (frozenAll.isSelected()) {
            return Filter.ALL;
        }
        else if (frozenNo.isSelected()) {
            return Filter.NO;
        }
        else return Filter.YES;
    }
}
