package com.playtech.cm.utils.entities.conditions;

import com.playtech.cm.utils.Config;

/**
 * User: Denis Veselovskiy
 * Date: 10/11/12 * Time: 2:43 PM
 */
public class GameLossesPlayerReportEntity extends CustomReportEntity {
    private String reportType;
    private String startDateOptions;
    private String startDateValue;
    private String endDateOptions;
    private String endDateValue;
    private Boolean realMoney;
    private Boolean allMoney;
    private String gameCategory;
    private String game1;
    private String game2;
    private String game3;
    private String game4;
    private String game5;
    private String minLosses;
    private String minNetLoss;
    private String minDeposits;
    private String minAverageBet;
    private String daysAgo;

    public GameLossesPlayerReportEntity(){
        this.setReportType("Player game losses");
        this.setConditionToAdd("Eligibility List");
        this.setCondition("CUSTOM_REPORT");
        this.setReportCategory("Players");
        this.setCustomReport("Player game losses");
        this.setClientType("Admin");
        this.setClientPlatform("Flash");
        this.setDaysAgo("5");
        this.setVipLevel("15");
        this.setMinLosses("999");
        this.setMinNetLoss("1");
        this.setMinDeposits("1");
        this.setMinAverageBet("999");
        this.setSuspended(Filter.YES);
        this.setFrozen(Filter.YES);
        this.setRealMoney(false);
        this.setAllMoney(true);
        this.setStartDateOptions("Number of days ago");
        this.setEndDateOptions("Specific date");
        this.setEndDateValue(Config.getFloatingFutureDate(-2,"yyyy-MM-dd"));
        this.setGameCategory("All");
        this.setGame1("$5 Million Winning Streak");
        this.setGame2("All");
        this.setGame3("All");
        this.setGame4("All");
        this.setGame5("All");
        this.setSummary("Player game losses");
    }

    public void clearAllNotMandatory(){
        this.setReportType("Player game losses");
        this.setConditionToAdd("Eligibility List");
        this.setCondition("CUSTOM_REPORT");
        this.setReportCategory("Players");
        this.setCustomReport("Player game losses");
        this.setClientType("All");
        this.setClientPlatform("All");
        this.setDaysAgo("2");
        this.setVipLevel("");
        this.setMinLosses("");
        this.setMinNetLoss("");
        this.setMinDeposits("");
        this.setMinAverageBet("");
        this.setSuspended(Filter.ALL);
        this.setFrozen(Filter.ALL);
        this.setRealMoney(false);
        this.setAllMoney(true);
        this.setStartDateOptions("Number of days ago");
        this.setEndDateOptions("Last full day (yesterday)");
        this.setEndDateValue("");
        this.setGameCategory("All");
        this.setGame1("All");
        this.setGame2("All");
        this.setGame3("All");
        this.setGame4("All");
        this.setGame5("All");
    }

    public void setAllDefault(){
        this.setReportType("Player game losses");
        this.setConditionToAdd("Eligibility List");
        this.setCondition("CUSTOM_REPORT");
        this.setReportCategory("Players");
        this.setCustomReport("Player game losses");
        this.setClientType("All");
        this.setClientPlatform("All");
        this.setDaysAgo("");
        this.setVipLevel("");
        this.setMinLosses("");
        this.setMinNetLoss("");
        this.setMinDeposits("");
        this.setMinAverageBet("");
        this.setSuspended(Filter.ALL);
        this.setFrozen(Filter.ALL);
        this.setRealMoney(false);
        this.setAllMoney(true);
        this.setStartDateOptions("Number of days ago");
        this.setEndDateOptions("Last full day (yesterday)");
        this.setEndDateValue("");
        this.setGameCategory("All");
        this.setGame1("All");
        this.setGame2("All");
        this.setGame3("All");
        this.setGame4("All");
        this.setGame5("All");
    }

    public String getStartDateOptions() {
        return startDateOptions;
    }

    public void setStartDateOptions(String startDateOptions) {
        this.startDateOptions = startDateOptions;
    }

    public String getStartDateValue() {
        return startDateValue;
    }

    public void setStartDateValue(String startDateValue) {
        this.startDateValue = startDateValue;
    }

    public String getEndDateOptions() {
        return endDateOptions;
    }

    public void setEndDateOptions(String endDateOptions) {
        this.endDateOptions = endDateOptions;
    }

    public String getDaysAgo() {
        return daysAgo;
    }

    public void setDaysAgo(String daysAgo) {
        this.daysAgo = daysAgo;
    }

    public String getEndDateValue() {
        return endDateValue;
    }

    public void setEndDateValue(String endDateValue) {
        this.endDateValue = endDateValue;
    }

    public Boolean getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Boolean realMoney) {
        this.realMoney = realMoney;
    }

    public String getGame1() {
        return game1;
    }

    public void setGame1(String game1) {
        this.game1 = game1;
    }

    public String getGame2() {
        return game2;
    }

    public void setGame2(String game2) {
        this.game2 = game2;
    }

    public String getGame3() {
        return game3;
    }

    public void setGame3(String game3) {
        this.game3 = game3;
    }

    public Boolean getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(Boolean allMoney) {
        this.allMoney = allMoney;
    }

    public String getGame4() {
        return game4;
    }

    public void setGame4(String game4) {
        this.game4 = game4;
    }

    public String getGame5() {
        return game5;
    }

    public void setGame5(String game5) {
        this.game5 = game5;
    }

    public String getMinLosses() {
        return minLosses;
    }

    public void setMinLosses(String minLosses) {
        this.minLosses = minLosses;
    }

    public String getMinNetLoss() {
        return minNetLoss;
    }

    public void setMinNetLoss(String minNetLoss) {
        this.minNetLoss = minNetLoss;
    }

    public String getMinDeposits() {
        return minDeposits;
    }

    public void setMinDeposits(String minDeposits) {
        this.minDeposits = minDeposits;
    }

    public String getMinAverageBet() {
        return minAverageBet;
    }

    public void setMinAverageBet(String minAverageBet) {
        this.minAverageBet = minAverageBet;
    }

    public String getGameCategory() {
        return gameCategory;
    }

    public void setGameCategory(String gameCategory) {
        this.gameCategory = gameCategory;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
}
