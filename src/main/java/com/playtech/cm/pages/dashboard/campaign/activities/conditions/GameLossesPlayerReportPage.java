package com.playtech.cm.pages.dashboard.campaign.activities.conditions;

import com.google.common.base.Function;
import com.playtech.cm.utils.entities.conditions.Filter;
import com.playtech.cm.utils.entities.conditions.GameLossesPlayerReportEntity;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static com.playtech.cm.BaseTest.driver;


/**
 * User: Denis Veselovskiy
 * Date: 10/8/12 * Time: 12:31 PM
 */
public class GameLossesPlayerReportPage extends ConditionDialogPage {
    String daysAgoXpath = "//span[@id='startDateBlockZone']/input[@type='text']";
    String endDateXpath = "//span[@id='endDateBlockZone']/input[contains(@id, 'datefield')]";
    String startDateXpath = "//span[@id='startDateBlockZone']/input[contains(@id, 'datefield')]";

    @FindBy(xpath = "//select[contains(@id, 'clientType_')]")
    WebElement clientType;

    @FindBy(xpath = "//select[contains(@id, 'clientPlatform_')]")
    WebElement clientPlatform;

    @FindBy(id = "gameCategory")
    WebElement gameCategory;

    @FindBy(xpath = "//select[contains(@id, 'startDateSelectOption_')]")
    WebElement startDateOptions;

    @FindBy(xpath = "//select[contains(@id, 'endDateSelectOption_')]")
    WebElement endDateOptions;

    @FindBy(xpath = "//*[contains(@name, 'gameLossCalculation_') and @value='1']")
    WebElement allMoney;

    @FindBy(xpath = "//*[contains(@name, 'gameLossCalculation_') and @value='2']")
    WebElement realMoney;

    @FindBy(id = "game1")
    WebElement game1;

    @FindBy(id = "game2")
    WebElement game2;

    @FindBy(id = "game3")
    WebElement game3;

    @FindBy(id = "game4")
    WebElement game4;

    @FindBy(id = "game5")
    WebElement game5;

    @FindBy(id = "radio_3")
    WebElement suspendedAll;

    @FindBy(id = "radio_2")
    WebElement suspendedNo;

    @FindBy(id = "radio_1")
    WebElement suspendedYes;

    @FindBy(id = "radio_6")
    WebElement frozenAll;

    @FindBy(id = "radio_5")
    WebElement frozenNo;

    @FindBy(id = "radio_4")
    WebElement frozenYes;

    @FindBy(id = "vipLevel")
    WebElement vipLevel;

    @FindBy(id = "minLosses")
    WebElement minLosses;

    @FindBy(id = "minNetLoss")
    WebElement minNetLosses;

    @FindBy(id = "minDeposits")
    WebElement minDeposits;

    @FindBy(id = "minAverageBet")
    WebElement minAverageBet;

    public void fillForm(GameLossesPlayerReportEntity gameLossesPlayerReportEntity) {
        driver.waitForAjax();
        setStartDateOptions(gameLossesPlayerReportEntity.getStartDateOptions());
        if(gameLossesPlayerReportEntity.getStartDateOptions().equals("Number of days ago")){
            typeDaysAgo(gameLossesPlayerReportEntity.getDaysAgo());
        } else if(gameLossesPlayerReportEntity.getStartDateOptions().equals("Specific date")){
            typeStartDate(gameLossesPlayerReportEntity.getStartDateValue());
        }
        setEndDateOptions(gameLossesPlayerReportEntity.getEndDateOptions());
        if(gameLossesPlayerReportEntity.getEndDateOptions().equals("Specific date")){
            typeEndDate(gameLossesPlayerReportEntity.getEndDateValue());
        }
        setRealMoney(gameLossesPlayerReportEntity.getRealMoney());
        setAllMoney(gameLossesPlayerReportEntity.getAllMoney());
        setClientType(gameLossesPlayerReportEntity.getClientType());
        setClientPlatform(gameLossesPlayerReportEntity.getClientPlatform());
        setGameCategory(gameLossesPlayerReportEntity.getGameCategory());
        if(gameLossesPlayerReportEntity.getGameCategory().equals("All")){
            setGame1(gameLossesPlayerReportEntity.getGame1());
            setGame2(gameLossesPlayerReportEntity.getGame2());
            setGame3(gameLossesPlayerReportEntity.getGame3());
            setGame4(gameLossesPlayerReportEntity.getGame4());
            setGame5(gameLossesPlayerReportEntity.getGame5());
        }
        typeVipLevel(gameLossesPlayerReportEntity.getVipLevel());
        typeMinLosses(gameLossesPlayerReportEntity.getMinLosses());
        typeMinNetLosses(gameLossesPlayerReportEntity.getMinNetLoss());
        typeMinDeposits(gameLossesPlayerReportEntity.getMinDeposits());
        typeMinAverageBet(gameLossesPlayerReportEntity.getMinAverageBet());
        setSuspend(gameLossesPlayerReportEntity.getSuspended());
        setFrozen(gameLossesPlayerReportEntity.getFrozen());
    }

    public void fillFormOnlyMandatory(GameLossesPlayerReportEntity gameLossesPlayerReportEntity){
        driver.waitForAjax();
        setStartDateOptions(gameLossesPlayerReportEntity.getStartDateOptions());
        if(gameLossesPlayerReportEntity.getStartDateOptions().equals("Number of days ago")){
            typeDaysAgo(gameLossesPlayerReportEntity.getDaysAgo());
        } else if(gameLossesPlayerReportEntity.getStartDateOptions().equals("Specific date")){
            typeStartDate(gameLossesPlayerReportEntity.getStartDateValue());
        }
        setEndDateOptions(gameLossesPlayerReportEntity.getEndDateOptions());
        if(gameLossesPlayerReportEntity.getEndDateOptions().equals("Specific date")){
            typeEndDate(gameLossesPlayerReportEntity.getEndDateValue());
        }
    }

    public void setStartDateOptions(String type){
        Select s = new Select(startDateOptions);
        s.selectByVisibleText(type);
    }

    public void setEndDateOptions(String type){
        Select s = new Select(endDateOptions);
        s.selectByVisibleText(type);
    }

    public void typeDaysAgo(String s){
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                return driver.isElementVisible(By.xpath(daysAgoXpath));
            }
        });
        WebElement dateField = driver.findElement(By.xpath(daysAgoXpath));
        dateField.clear();
        typeQuickly(dateField, s);
    }

    public void typeEndDate(String s){
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                return driver.isElementVisible(By.xpath(endDateXpath));
            }
        });
        WebElement dateField = driver.findElement(By.xpath(endDateXpath));
        dateField.clear();
        typeQuickly(dateField, s);
    }

    public void typeStartDate(String s){
        driver.waitUntil(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver input) {
                return driver.isElementVisible(By.xpath(startDateXpath));
            }
        });
        WebElement dateField = driver.findElement(By.xpath(startDateXpath));
        dateField.clear();
        typeQuickly(dateField, s);
    }

    public void setClientType(String type) {
        Select s = new Select(clientType);
        s.selectByVisibleText(type);
    }

    public void setClientPlatform(String type) {
        Select s = new Select(clientPlatform);
        s.selectByVisibleText(type);
    }

    public void setGameCategory(String type) {
        Select s = new Select(gameCategory);
        s.selectByVisibleText(type);
    }

    public void typeVipLevel(String s) {
        vipLevel.clear();
        typeQuickly(vipLevel, s);
    }

    public void typeMinLosses(String s) {
        minLosses.clear();
        typeQuickly(minLosses, s);
    }

    public void typeMinNetLosses(String s) {
        minNetLosses.clear();
        typeQuickly(minNetLosses, s);
    }

    public void typeMinDeposits(String s) {
        minDeposits.clear();
        typeQuickly(minDeposits, s);
    }

    public void setGame1(String type){
        Select s = new Select(game1);
        s.selectByVisibleText(type);
    }

    public void setGame2(String type){
        Select s = new Select(game2);
        s.selectByVisibleText(type);
    }

    public void setGame3(String type){
        Select s = new Select(game3);
        s.selectByVisibleText(type);
    }

    public void setGame4(String type){
        Select s = new Select(game4);
        s.selectByVisibleText(type);
    }

    public void setGame5(String type){
        Select s = new Select(game5);
        s.selectByVisibleText(type);
    }

    public void typeMinAverageBet(String s) {
        minAverageBet.clear();
        typeQuickly(minAverageBet, s);
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

    public void setRealMoney(Boolean check){
        if(!realMoney.isSelected()&&check){
            realMoney.click();
        }else if(realMoney.isSelected()&&!check){
            realMoney.click();
        }

    }

    public void setAllMoney(Boolean check){
        if(!allMoney.isSelected()&&check){
            allMoney.click();
        }else if(allMoney.isSelected()&&!check){
            allMoney.click();
        }
    }

//G E T T E R SECTION
    public GameLossesPlayerReportEntity getAllFormValues(){
        driver.waitUntilElementAppear(By.id("game1"));
        driver.waitForAjax();
        GameLossesPlayerReportEntity res = new GameLossesPlayerReportEntity();
        res.clearAllNotMandatory();

        res.setStartDateOptions(getStartDateOptions());
        if(getStartDateOptions().equals("Number of days ago")){
            res.setDaysAgo(getDaysAgo());

        } else if(getStartDateOptions().equals("Specific date")){
            res.setStartDateValue(getStartDateValue());
        }
        res.setEndDateOptions(getEndDateOptions());
        if(getEndDateOptions().equals("Specific date")){
            res.setEndDateValue(getEndDateValue());
        }

        res.setRealMoney(getRealMoney());
        res.setAllMoney(getAllMoney());

        res.setClientType(getClientType());
        res.setClientPlatform(getClientPlatform());
        res.setGameCategory(getGameCategory());
        res.setGame1(getGame1());
        res.setGame2(getGame2());
        res.setGame3(getGame3());
        res.setGame4(getGame4());
        res.setGame5(getGame5());
        res.setVipLevel(getVipLevel());
        res.setMinLosses(getMinLosses());
        res.setMinNetLoss(getMinNetLosses());
        res.setMinDeposits(getMinDeposits());
        res.setMinAverageBet(getMinAverageBet());
        res.setSuspended(getSuspended());
        res.setFrozen(getFrozen());
        return res;
    }

    public String getDaysAgo(){
        return driver.findElement(By.xpath(daysAgoXpath)).getAttribute("value");
    }

    public String getStartDateValue(){
        return driver.findElement(By.xpath(startDateXpath)).getAttribute("value");
    }

    public String getEndDateValue(){
        return driver.findElement(By.xpath(endDateXpath)).getAttribute("value");
    }

    public String getClientType() {
        String optionName = clientType.getAttribute("value");
        return clientType.findElement(By.xpath("option[@value='"+optionName+"']")).getText();
    }

    public String getClientPlatform() {
        String optionName = clientPlatform.getAttribute("value");
        return clientPlatform.findElement(By.xpath("option[@value='"+optionName+"']")).getText();
    }

    public String getGameCategory() {
        String optionName = gameCategory.getAttribute("value");
        return gameCategory.findElement(By.xpath("option[@value='"+optionName+"']")).getText();

    }

    public String getStartDateOptions() {
        String optionName = startDateOptions.getAttribute("value");
        return startDateOptions.findElement(By.xpath("option[@value='"+optionName+"']")).getText();
    }

    public String getEndDateOptions() {
        String optionName = endDateOptions.getAttribute("value");
        return endDateOptions.findElement(By.xpath("option[@value='"+optionName+"']")).getText();
    }

    public Boolean getAllMoney() {
        return allMoney.isSelected();
    }

    public Boolean getRealMoney() {
        return realMoney.isSelected();
    }

    public String getGame1() {
        String optionName = game1.getAttribute("value");
        return game1.findElement(By.xpath("option[@value='"+optionName+"']")).getText();
    }

    public String getGame2() {
        String optionName = game2.getAttribute("value");
        return game2.findElement(By.xpath("option[@value='"+optionName+"']")).getText();
    }

    public String getGame3() {
        String optionName = game3.getAttribute("value");
        return game3.findElement(By.xpath("option[@value='"+optionName+"']")).getText();
    }

    public String getGame4() {
        String optionName = game4.getAttribute("value");
        return game4.findElement(By.xpath("option[@value='"+optionName+"']")).getText();
    }

    public String getGame5() {
        String optionName = game5.getAttribute("value");
        return game5.findElement(By.xpath("option[@value='"+optionName+"']")).getText();
    }

    public Boolean getSuspendedAll() {
        return suspendedAll.isSelected();
    }

    public Boolean getSuspendedNo() {
        return suspendedNo.isSelected();
    }

    public Boolean getSuspendedYes() {
        return suspendedYes.isSelected();
    }

    public Boolean getFrozenAll() {
        return frozenAll.isSelected();
    }

    public Boolean getFrozenNo() {
        return frozenNo.isSelected();
    }

    public Boolean getFrozenYes() {
        return frozenYes.isSelected();
    }

    public String getVipLevel() {
        return vipLevel.getAttribute("value");
    }

    public String getMinLosses() {
        return minLosses.getAttribute("value");
    }

    public String getMinNetLosses() {
        return minNetLosses.getAttribute("value");
    }

    public String getMinDeposits() {
        return minDeposits.getAttribute("value");
    }

    public String getMinAverageBet() {
        return minAverageBet.getAttribute("value");
    }

    public Filter getSuspended() {
        Filter res = null;
        if(getSuspendedAll()){
            res = Filter.ALL;
        } else if(getSuspendedNo()){
            res = Filter.NO;
        } else if(getSuspendedYes()){
            res = Filter.YES;
        }
        return res;
    }

    public Filter getFrozen(){
        Filter res = null;
        if(getFrozenAll()){
            res = Filter.ALL;
        } else if(getFrozenNo()){
            res = Filter.NO;
        } else if(getFrozenYes()){
            res = Filter.YES;
        }
        return res;
    }

    public Boolean areAllGameFieldsEnabled(){
        Boolean res;
        res = game1.isEnabled() && game2.isEnabled() && game3.isEnabled() && game4.isEnabled() && game5.isEnabled();
        return res;
    }

    public Boolean isGameCategoryFieldEnabled(){
        Boolean res;
        res = gameCategory.isEnabled();
        return res;
    }
}
