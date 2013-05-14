package com.playtech.cm.utils.dataProviders.conditions;

import com.playtech.cm.utils.Config;
import com.playtech.cm.utils.entities.conditions.Filter;
import com.playtech.cm.utils.entities.conditions.GameLossesPlayerReportEntity;
import org.testng.annotations.DataProvider;

import java.util.Arrays;
import java.util.List;

/**
 * User: Denis Veselovskiy
 * Date: 10/11/12, Time: 3:28 PM
 */
public class GameLossesPlayerReportData {
    static final String errorStartDateFormat = "Specific start date. Please use the format yyyy-MM-dd for specifying dates.";
    static final String errorEndDateFormat = "Specific end date. Please use the format yyyy-MM-dd for specifying dates.";
    static final String errorDateMaxRangeExceeded = "Maximum date range exceeded. Please select a date range of 7 days or less for this report.";
    static final String errorEndDateLowerThanStart = "End date should be greater than Start date.";

    static final String errorEmptyDaysAgo = "Days ago should not be empty.";
    static final String errorEmptyStartDate = "Specific start date should not be empty.";
    static final String errorEmptyEndDate = "Specific end date should not be empty.";

    static final String errorDaysAgoFormat = "Days ago. Please enter a non-decimal numeric value.";
    static final String errorVipLevelFormat = "VIP Level. Please enter a non-decimal numeric value.";
    static final String errorMinLossesFormat = "Minimum Losses. Please enter a non-decimal numeric value.";
    static final String errorMinNetLossFormat = "Minimum Net Loss. Please enter a non-decimal numeric value.";
    static final String errorMinDepositsFormat = "Minimum Deposits. Please enter a non-decimal numeric value.";
    static final String errorMinAverageBetFormat = "Minimum Average Bet. Please enter a non-decimal numeric value.";

    static final String errorDaysAgoLessZero = "Days ago requires a value of at least 0.";
    static final String errorVipLevelLessZero = "VIP Level requires a value of at least 0.";
    static final String errorMinLossesLessZero = "Minimum Losses requires a value of at least 0.";
    static final String errorMinNetLossLessZero = "Minimum Net Loss requires a value of at least 0.";
    static final String errorMinDepositsLessZero = "Minimum Deposits requires a value of at least 0.";
    static final String errorMinAverageBetLessZero = "Minimum Average Bet requires a value of at least 0.";

    static final List<String> badDateFieldValues = Arrays.asList("aXdASd","!@#$%^&*()_-+=?/>.<,","12.10.2010","12.10.2010","13/14/201","2/9/19");
    static final List<String> badIntegerValues = Arrays.asList("aXdASd","1!23456!@#$%^&(*()","<script language=\"JavaScript\"> alert('Добрый день') </script>");


    @DataProvider(name = "getDaysAgoFieldEmptyData")
         public static Object[][] getDaysAgoFieldEmptyData() {
        GameLossesPlayerReportEntity gameLossesPlayerReportEntity = new GameLossesPlayerReportEntity();
        gameLossesPlayerReportEntity.setStartDateOptions("Number of days ago");

        return new Object[][]{{gameLossesPlayerReportEntity,errorEmptyDaysAgo}};
    }

    @DataProvider(name = "getStartDateFieldEmptyData")
         public static Object[][] getStartDateFieldEmptyData() {
        GameLossesPlayerReportEntity gameLossesPlayerReportEntity = new GameLossesPlayerReportEntity();
        gameLossesPlayerReportEntity.setStartDateOptions("Specific date");

        return new Object[][]{{gameLossesPlayerReportEntity,errorEmptyStartDate}};
    }

    @DataProvider(name = "getEndDateFieldEmptyData")
    public static Object[][] getEndDateFieldEmptyData() {
        GameLossesPlayerReportEntity gameLossesPlayerReportEntity = new GameLossesPlayerReportEntity();
        gameLossesPlayerReportEntity.setStartDateValue("Number of days ago");
        gameLossesPlayerReportEntity.setDaysAgo("2");
        gameLossesPlayerReportEntity.setEndDateOptions("Specific date");

        return new Object[][]{{gameLossesPlayerReportEntity,errorEmptyEndDate}};
    }

    @DataProvider(name = "getSessionCleanedData")
    public static Object[][] getSessionCleanedData() {
        GameLossesPlayerReportEntity gameLossesPlayerReportEntity = new GameLossesPlayerReportEntity();
        gameLossesPlayerReportEntity.setStartDateValue("Number of days ago");
        gameLossesPlayerReportEntity.setDaysAgo("");
        gameLossesPlayerReportEntity.setEndDateOptions("Specific date");
        gameLossesPlayerReportEntity.setEndDateValue("");

        return new Object[][]{{gameLossesPlayerReportEntity,errorEmptyDaysAgo, errorEmptyEndDate}};
    }

    @DataProvider(name = "getStartDateFieldNegativeData")
    public static Object[][] getStartDateFieldNegativeData() {
        GameLossesPlayerReportEntity gameLossesPlayerReportEntity = new GameLossesPlayerReportEntity();
        gameLossesPlayerReportEntity.setStartDateOptions("Specific date");

        Object data [][] = new Object[badDateFieldValues.size()][1];
        for(int i = 0; i < badDateFieldValues.size(); i++){
            data[i] = new Object[]{gameLossesPlayerReportEntity, badDateFieldValues.get(i), errorStartDateFormat};
        }
        return data;
    }

    @DataProvider(name = "getEndDateFieldNegativeData")
    public static Object[][] getEndDateFieldNegativeData() {
        GameLossesPlayerReportEntity gameLossesPlayerReportEntity = new GameLossesPlayerReportEntity();
        gameLossesPlayerReportEntity.setStartDateValue("Number of days ago");
        gameLossesPlayerReportEntity.setDaysAgo("2");
        gameLossesPlayerReportEntity.setEndDateOptions("Specific date");

        Object data [][] = new Object[badDateFieldValues.size()][1];
        for(int i = 0; i < badDateFieldValues.size(); i++){
            data[i] = new Object[]{gameLossesPlayerReportEntity, badDateFieldValues.get(i), errorEndDateFormat};
        }
        return data;
    }

    @DataProvider(name = "getValidateVipLevelFieldData")
    public static Object[][] getValidateVipLevelFieldData() {
        GameLossesPlayerReportEntity gameLossesPlayerReportEntity = new GameLossesPlayerReportEntity();
        gameLossesPlayerReportEntity.setStartDateValue("Number of days ago");
        gameLossesPlayerReportEntity.setDaysAgo("2");
        gameLossesPlayerReportEntity.setEndDateOptions("Last update (approx. hourly)");

        Object data [][] = new Object[badIntegerValues.size()][1];
        for(int i = 0; i < badIntegerValues.size(); i++){
            data[i] = new Object[]{gameLossesPlayerReportEntity, badIntegerValues.get(i), errorVipLevelFormat};
        }
        return data;
    }

    @DataProvider(name = "getValidateMinLossesFieldData")
    public static Object[][] getValidateMinLossesFieldData() {
        GameLossesPlayerReportEntity gameLossesPlayerReportEntity = new GameLossesPlayerReportEntity();
        gameLossesPlayerReportEntity.setStartDateValue("Number of days ago");
        gameLossesPlayerReportEntity.setDaysAgo("2");
        gameLossesPlayerReportEntity.setEndDateOptions("Last update (approx. hourly)");

        Object data [][] = new Object[badIntegerValues.size()][1];
        for(int i = 0; i < badIntegerValues.size(); i++){
            data[i] = new Object[]{gameLossesPlayerReportEntity, badIntegerValues.get(i), errorMinLossesFormat};
        }
        return data;
    }

    @DataProvider(name = "getValidateMinNetLossesFieldData")
         public static Object[][] getValidateMinNetLossesFieldData() {
        GameLossesPlayerReportEntity gameLossesPlayerReportEntity = new GameLossesPlayerReportEntity();
        gameLossesPlayerReportEntity.setStartDateValue("Number of days ago");
        gameLossesPlayerReportEntity.setDaysAgo("2");
        gameLossesPlayerReportEntity.setEndDateOptions("Last update (approx. hourly)");

        Object data [][] = new Object[badIntegerValues.size()][1];
        for(int i = 0; i < badIntegerValues.size(); i++){
            data[i] = new Object[]{gameLossesPlayerReportEntity, badIntegerValues.get(i), errorMinNetLossFormat};
        }
        return data;
    }

    @DataProvider(name = "getValidateMinDepositsFieldData")
    public static Object[][] getValidateMinDepositsFieldData() {
        GameLossesPlayerReportEntity gameLossesPlayerReportEntity = new GameLossesPlayerReportEntity();
        gameLossesPlayerReportEntity.setStartDateValue("Number of days ago");
        gameLossesPlayerReportEntity.setDaysAgo("2");
        gameLossesPlayerReportEntity.setEndDateOptions("Last update (approx. hourly)");

        Object data [][] = new Object[badIntegerValues.size()][1];
        for(int i = 0; i < badIntegerValues.size(); i++){
            data[i] = new Object[]{gameLossesPlayerReportEntity, badIntegerValues.get(i), errorMinDepositsFormat};
        }
        return data;
    }

    @DataProvider(name = "getValidateMinAverageBetFieldData")
    public static Object[][] getValidateMinAverageBetFieldData() {
        GameLossesPlayerReportEntity gameLossesPlayerReportEntity = new GameLossesPlayerReportEntity();
        gameLossesPlayerReportEntity.setStartDateValue("Number of days ago");
        gameLossesPlayerReportEntity.setDaysAgo("2");
        gameLossesPlayerReportEntity.setEndDateOptions("Last update (approx. hourly)");

        Object data [][] = new Object[badIntegerValues.size()][1];
        for(int i = 0; i < badIntegerValues.size(); i++){
            data[i] = new Object[]{gameLossesPlayerReportEntity, badIntegerValues.get(i), errorMinAverageBetFormat};
        }
        return data;
    }

    @DataProvider(name = "getValidateDaysAgoFieldData")
    public static Object[][] getValidateDaysAgoFieldData() {
        GameLossesPlayerReportEntity gameLossesPlayerReportEntity = new GameLossesPlayerReportEntity();
        gameLossesPlayerReportEntity.setStartDateValue("Number of days ago");
        gameLossesPlayerReportEntity.setDaysAgo("2");
        gameLossesPlayerReportEntity.setEndDateOptions("Last update (approx. hourly)");

        Object data [][] = new Object[badIntegerValues.size()][1];
        for(int i = 0; i < badIntegerValues.size(); i++){
            data[i] = new Object[]{gameLossesPlayerReportEntity, badIntegerValues.get(i), errorDaysAgoFormat};
        }
        return data;
    }

    @DataProvider(name = "getValidateLessZeroValuesData")
    public static Object[][] getValidateLessZeroValuesData() {
        String negativeValue = "-2";
        GameLossesPlayerReportEntity data1 = new GameLossesPlayerReportEntity();
        data1.clearAllNotMandatory();
        data1.setDaysAgo(negativeValue);

        GameLossesPlayerReportEntity data2 = new GameLossesPlayerReportEntity();
        data2.clearAllNotMandatory();
        data2.setVipLevel(negativeValue);

        GameLossesPlayerReportEntity data3 = new GameLossesPlayerReportEntity();
        data3.clearAllNotMandatory();
        data3.setMinLosses(negativeValue);

        GameLossesPlayerReportEntity data4 = new GameLossesPlayerReportEntity();
        data4.clearAllNotMandatory();
        data4.setMinNetLoss(negativeValue);

        GameLossesPlayerReportEntity data5 = new GameLossesPlayerReportEntity();
        data5.clearAllNotMandatory();
        data5.setMinDeposits(negativeValue);

        GameLossesPlayerReportEntity data6 = new GameLossesPlayerReportEntity();
        data6.clearAllNotMandatory();
        data6.setMinAverageBet(negativeValue);

        return new Object[][]{
                {data1,errorDaysAgoLessZero},
                {data2,errorVipLevelLessZero},
                {data3,errorMinLossesLessZero},
                {data4,errorMinNetLossLessZero},
                {data5,errorMinDepositsLessZero},
                {data6,errorMinAverageBetLessZero}
        };
    }

    @DataProvider(name = "getValidateIssue366Data")
    public static Object[][] getValidateIssue366Data() {
        String testValue = "10000000000000000000";

        GameLossesPlayerReportEntity data1 = new GameLossesPlayerReportEntity();
        data1.clearAllNotMandatory();
        data1.setVipLevel(testValue);

        GameLossesPlayerReportEntity data2 = new GameLossesPlayerReportEntity();
        data2.clearAllNotMandatory();
        data2.setMinLosses(testValue);

        GameLossesPlayerReportEntity data3 = new GameLossesPlayerReportEntity();
        data3.clearAllNotMandatory();
        data3.setMinNetLoss(testValue);

        GameLossesPlayerReportEntity data4 = new GameLossesPlayerReportEntity();
        data4.clearAllNotMandatory();
        data4.setMinDeposits(testValue);

        GameLossesPlayerReportEntity data5 = new GameLossesPlayerReportEntity();
        data5.clearAllNotMandatory();
        data5.setMinAverageBet(testValue);

        return new Object[][]{
                {data1},
                {data2},
                {data3},
                {data4},
                {data5}         };
    }

    @DataProvider(name = "getDatesPositiveData")
    public static Object[][] getDatesPositiveData() {
        String futureSixDaysDate = Config.getFloatingFutureDate(6,"yyyy-MM-dd");
        String pastSixDaysDate = Config.getFloatingFutureDate(-6,"yyyy-MM-dd");
        String yesterdayDate = Config.getFloatingFutureDate(-1,"yyyy-MM-dd");
        String todayDate = Config.getFloatingFutureDate(0,"yyyy-MM-dd");

        GameLossesPlayerReportEntity data1 = new GameLossesPlayerReportEntity();
        data1.clearAllNotMandatory();
        data1.setStartDateOptions("Specific date");
        data1.setEndDateOptions("Last full day (yesterday)");
        data1.setStartDateValue(yesterdayDate);

        GameLossesPlayerReportEntity data2 = new GameLossesPlayerReportEntity();
        data2.clearAllNotMandatory();
        data2.setStartDateOptions("Specific date");
        data2.setEndDateOptions("Last full day (yesterday)");
        data2.setStartDateValue(pastSixDaysDate);

        GameLossesPlayerReportEntity data3 = new GameLossesPlayerReportEntity();
        data3.clearAllNotMandatory();
        data3.setStartDateOptions("Specific date");
        data3.setEndDateOptions("Last update (approx. hourly)");
        data3.setStartDateValue(yesterdayDate);

        GameLossesPlayerReportEntity data4 = new GameLossesPlayerReportEntity();
        data4.clearAllNotMandatory();
        data4.setStartDateOptions("Specific date");
        data4.setEndDateOptions("Last update (approx. hourly)");
        data4.setStartDateValue(pastSixDaysDate);

        GameLossesPlayerReportEntity data5 = new GameLossesPlayerReportEntity();
        data5.clearAllNotMandatory();
        data5.setStartDateOptions("Specific date");
        data5.setEndDateOptions("Last update (approx. hourly)");
        data5.setStartDateValue(todayDate);

        GameLossesPlayerReportEntity data6 = new GameLossesPlayerReportEntity();
        data6.clearAllNotMandatory();
        data6.setStartDateOptions("Specific date");
        data6.setEndDateOptions("Specific date");
        data6.setStartDateValue(todayDate);
        data6.setEndDateValue(todayDate);

        GameLossesPlayerReportEntity data7 = new GameLossesPlayerReportEntity();
        data7.clearAllNotMandatory();
        data7.setStartDateOptions("Specific date");
        data7.setEndDateOptions("Specific date");
        data7.setStartDateValue(yesterdayDate);
        data7.setEndDateValue(todayDate);

        GameLossesPlayerReportEntity data8 = new GameLossesPlayerReportEntity();
        data8.clearAllNotMandatory();
        data8.setStartDateOptions("Specific date");
        data8.setEndDateOptions("Specific date");
        data8.setStartDateValue(pastSixDaysDate);
        data8.setEndDateValue(todayDate);

        GameLossesPlayerReportEntity data9 = new GameLossesPlayerReportEntity();
        data9.clearAllNotMandatory();
        data9.setStartDateOptions("Specific date");
        data9.setEndDateOptions("Specific date");
        data9.setStartDateValue(todayDate);
        data9.setEndDateValue(futureSixDaysDate);

        GameLossesPlayerReportEntity data10 = new GameLossesPlayerReportEntity();
        data10.clearAllNotMandatory();
        data10.setStartDateOptions("Specific date");
        data10.setEndDateOptions("Specific date");
        data10.setStartDateValue(todayDate);
        data10.setEndDateValue(futureSixDaysDate);



         return new Object[][]{
                {data1},
                {data2},
                {data3},
                {data4},
                {data5},
                {data6},
                {data7},
                {data8},
                {data9}
      };
    }

    @DataProvider(name = "getDatesNegativeData")
    public static Object[][] getDatesNegativeData() {
        String pastMoreSevenDaysDate = Config.getFloatingFutureDate(-7,"yyyy-MM-dd");
        String pastMoreEightDaysDate = Config.getFloatingFutureDate(-8,"yyyy-MM-dd");
        String futureDate = Config.getFloatingFutureDate(1,"yyyy-MM-dd");
        String todayDate = Config.getFloatingFutureDate(0,"yyyy-MM-dd");

        GameLossesPlayerReportEntity data1 = new GameLossesPlayerReportEntity();
        data1.clearAllNotMandatory();
        data1.setStartDateOptions("Specific date");
        data1.setEndDateOptions("Last full day (yesterday)");
        data1.setStartDateValue(todayDate);

        GameLossesPlayerReportEntity data2 = new GameLossesPlayerReportEntity();
        data2.clearAllNotMandatory();
        data2.setStartDateOptions("Specific date");
        data2.setEndDateOptions("Last full day (yesterday)");
        data2.setStartDateValue(futureDate);

        GameLossesPlayerReportEntity data3 = new GameLossesPlayerReportEntity();
        data3.clearAllNotMandatory();
        data3.setStartDateOptions("Specific date");
        data3.setEndDateOptions("Last update (approx. hourly)");
        data3.setStartDateValue(futureDate);

        GameLossesPlayerReportEntity data4 = new GameLossesPlayerReportEntity();
        data4.clearAllNotMandatory();
        data4.setStartDateOptions("Specific date");
        data4.setEndDateOptions("Last update (approx. hourly)");
        data4.setStartDateValue(pastMoreSevenDaysDate);

        GameLossesPlayerReportEntity data5 = new GameLossesPlayerReportEntity();
        data5.clearAllNotMandatory();
        data5.setStartDateOptions("Specific date");
        data5.setEndDateOptions("Last full day (yesterday)");
        data5.setStartDateValue(pastMoreEightDaysDate);

        return new Object[][]{
                {data1,errorEndDateLowerThanStart},
                {data2,errorEndDateLowerThanStart},
                {data3,errorEndDateLowerThanStart},
                {data4,errorDateMaxRangeExceeded},
                {data5,errorDateMaxRangeExceeded}
        };
    }

    @DataProvider(name = "getDatesAndDaysAgoPositiveData")
    public static Object[][] getDatesAndDaysAgoPositiveData() {
        String yesterdayDate = Config.getFloatingFutureDate(-1,"yyyy-MM-dd");
        String tomorrow = Config.getFloatingFutureDate(1,"yyyy-MM-dd");

        GameLossesPlayerReportEntity data1 = new GameLossesPlayerReportEntity();
        data1.clearAllNotMandatory();
        data1.setStartDateOptions("Number of days ago");
        data1.setEndDateOptions("Last full day (yesterday)");
        data1.setDaysAgo("1");

        GameLossesPlayerReportEntity data2 = new GameLossesPlayerReportEntity();
        data2.clearAllNotMandatory();
        data2.setStartDateOptions("Number of days ago");
        data2.setEndDateOptions("Last full day (yesterday)");
        data2.setDaysAgo("7");

        GameLossesPlayerReportEntity data3 = new GameLossesPlayerReportEntity();
        data3.clearAllNotMandatory();
        data3.setStartDateOptions("Number of days ago");
        data3.setEndDateOptions("Last update (approx. hourly)");
        data3.setDaysAgo("0");

        GameLossesPlayerReportEntity data4 = new GameLossesPlayerReportEntity();
        data4.clearAllNotMandatory();
        data4.setStartDateOptions("Number of days ago");
        data4.setEndDateOptions("Last update (approx. hourly)");
        data4.setDaysAgo("6");

        GameLossesPlayerReportEntity data5 = new GameLossesPlayerReportEntity();
        data5.clearAllNotMandatory();
        data5.setStartDateOptions("Number of days ago");
        data5.setEndDateOptions("Specific date");
        data5.setEndDateValue(yesterdayDate);
        data5.setDaysAgo("1");

        GameLossesPlayerReportEntity data6 = new GameLossesPlayerReportEntity();
        data6.clearAllNotMandatory();
        data6.setStartDateOptions("Number of days ago");
        data6.setEndDateOptions("Specific date");
        data6.setEndDateValue(tomorrow);
        data6.setDaysAgo("5");

        return new Object[][]{
                {data1},
                {data2},
                {data3},
                {data4},
                {data5},
                {data6}
        };
    }

    @DataProvider(name = "getDatesAndDaysAgoNegativeData")
    public static Object[][] getDatesAndDaysAgoNegativeData() {
        String yesterdayDate = Config.getFloatingFutureDate(-1,"yyyy-MM-dd");

        GameLossesPlayerReportEntity data1 = new GameLossesPlayerReportEntity();
        data1.clearAllNotMandatory();
        data1.setStartDateOptions("Number of days ago");
        data1.setEndDateOptions("Last full day (yesterday)");
        data1.setDaysAgo("9");

        GameLossesPlayerReportEntity data2 = new GameLossesPlayerReportEntity();
        data2.clearAllNotMandatory();
        data2.setStartDateOptions("Number of days ago");
        data2.setEndDateOptions("Last full day (yesterday)");
        data2.setDaysAgo("0");

        GameLossesPlayerReportEntity data3 = new GameLossesPlayerReportEntity();
        data3.clearAllNotMandatory();
        data3.setStartDateOptions("Number of days ago");
        data3.setEndDateOptions("Last update (approx. hourly)");
        data3.setDaysAgo("7");

        GameLossesPlayerReportEntity data4 = new GameLossesPlayerReportEntity();
        data4.clearAllNotMandatory();
        data4.setStartDateOptions("Number of days ago");
        data4.setEndDateOptions("Specific date");
        data4.setEndDateValue(yesterdayDate);
        data4.setDaysAgo("9");

        return new Object[][]{
                {data1,errorDateMaxRangeExceeded},
                {data2,errorEndDateLowerThanStart},
                {data3,errorDateMaxRangeExceeded},
                {data4,errorDateMaxRangeExceeded}
        };
    }

    @DataProvider(name = "getGameLossCalcData")
    public static Object[][] getGameLossCalcData() {
        GameLossesPlayerReportEntity data1 = new GameLossesPlayerReportEntity();
        data1.clearAllNotMandatory();
        data1.setAllMoney(true);
        data1.setRealMoney(false);

        GameLossesPlayerReportEntity data2 = new GameLossesPlayerReportEntity();
        data2.clearAllNotMandatory();
        data2.setAllMoney(false);
        data2.setRealMoney(true);


        return new Object[][]{
                {data1},
                {data2}
        };
    }

    @DataProvider(name = "getClientTypeData")
    public static Object[][] getClientTypeData() {
        GameLossesPlayerReportEntity data1 = new GameLossesPlayerReportEntity();
        data1.clearAllNotMandatory();
        data1.setClientType("All");

        GameLossesPlayerReportEntity data2 = new GameLossesPlayerReportEntity();
        data2.clearAllNotMandatory();
        data2.setClientType("Admin");

        GameLossesPlayerReportEntity data3 = new GameLossesPlayerReportEntity();
        data3.clearAllNotMandatory();
        data3.setClientType("Casino");

        return new Object[][]{
                {data1},
                {data2},
                {data3},

        };
    }

    @DataProvider(name = "getClientPlatformData")
    public static Object[][] getClientPlatformData() {
        GameLossesPlayerReportEntity data1 = new GameLossesPlayerReportEntity();
        data1.clearAllNotMandatory();
        data1.setClientPlatform("All");

        GameLossesPlayerReportEntity data2 = new GameLossesPlayerReportEntity();
        data2.clearAllNotMandatory();
        data2.setClientPlatform("Flash");

        GameLossesPlayerReportEntity data3 = new GameLossesPlayerReportEntity();
        data3.clearAllNotMandatory();
        data3.setClientPlatform("Admin");

        GameLossesPlayerReportEntity data4 = new GameLossesPlayerReportEntity();
        data4.clearAllNotMandatory();
        data4.setClientPlatform("Download");

        return new Object[][]{
                {data1},
                {data2},
                {data3},
                {data4}
        };
    }

    @DataProvider(name = "getGameCategoryData")
    public static Object[][] getGameCategoryData() {
        GameLossesPlayerReportEntity data1 = new GameLossesPlayerReportEntity();
        data1.clearAllNotMandatory();
        data1.setGameCategory("All");

        GameLossesPlayerReportEntity data2 = new GameLossesPlayerReportEntity();
        data2.clearAllNotMandatory();
        data2.setGameCategory("Video Pokers");

        GameLossesPlayerReportEntity data3 = new GameLossesPlayerReportEntity();
        data3.clearAllNotMandatory();
        data3.setGameCategory("GTS Games");

        GameLossesPlayerReportEntity data4 = new GameLossesPlayerReportEntity();
        data4.clearAllNotMandatory();
        data4.setGameCategory("Slot Machines");

        GameLossesPlayerReportEntity data5 = new GameLossesPlayerReportEntity();
        data5.clearAllNotMandatory();
        data5.setGameCategory("None");

        return new Object[][]{
                {data1},
                {data2},
                {data3},
                {data4},
                {data5}
        };
    }

    @DataProvider(name = "getGameFieldsData")
    public static Object[][] getGameFieldsData() {
        GameLossesPlayerReportEntity data1 = new GameLossesPlayerReportEntity();
        data1.clearAllNotMandatory();
        data1.setGame2("#Gold Rally 8 Lines (NAG)");
        data1.setGame3("100k Pyraminds (VF)");
        data1.setGame5("21 Duel Blackjack 2 Up bet");

        GameLossesPlayerReportEntity data2 = new GameLossesPlayerReportEntity();
        data2.clearAllNotMandatory();
        data2.setGame1("60 Seconds (SA)");
        data2.setGame2("Vikingmania");
        data2.setGame3("WW-50kPyramidRGS");
        data2.setGame4("Zero Jacks or Better Double");
        data2.setGame5("Ässäkisa Double");

        GameLossesPlayerReportEntity data3 = new GameLossesPlayerReportEntity();
        data3.clearAllNotMandatory();
        data3.setGame4("AG-LepLuck");

        GameLossesPlayerReportEntity data4 = new GameLossesPlayerReportEntity();
        data4.clearAllNotMandatory();
        data4.setGame5("Ässäkisa Double");

        GameLossesPlayerReportEntity data5 = new GameLossesPlayerReportEntity();
        data5.clearAllNotMandatory();
        data5.setGame2("AG-8Horse");
        data5.setGame5("AG-8Horse");

        return new Object[][]{
                {data1},
                {data2},
                {data3},
                {data4},
                {data5}
        };
    }

    @DataProvider(name = "getSuspendedData")
         public static Object[][] getSuspendedData() {
        GameLossesPlayerReportEntity data1 = new GameLossesPlayerReportEntity();
        data1.clearAllNotMandatory();
        data1.setSuspended(Filter.YES);

        GameLossesPlayerReportEntity data2 = new GameLossesPlayerReportEntity();
        data2.clearAllNotMandatory();
        data2.setSuspended(Filter.NO);

        GameLossesPlayerReportEntity data3 = new GameLossesPlayerReportEntity();
        data3.clearAllNotMandatory();
        data3.setSuspended(Filter.ALL);

        return new Object[][]{
                {data1},
                {data2},
                {data3}
        };
    }

    @DataProvider(name = "getFrozenData")
    public static Object[][] getFrozenData() {
        GameLossesPlayerReportEntity data1 = new GameLossesPlayerReportEntity();
        data1.clearAllNotMandatory();
        data1.setFrozen(Filter.YES);

        GameLossesPlayerReportEntity data2 = new GameLossesPlayerReportEntity();
        data2.clearAllNotMandatory();
        data2.setFrozen(Filter.NO);

        GameLossesPlayerReportEntity data3 = new GameLossesPlayerReportEntity();
        data3.clearAllNotMandatory();
        data3.setFrozen(Filter.ALL);

        return new Object[][]{
                {data1},
                {data2},
                {data3}
        };
    }

    @DataProvider(name = "getCreateEditConditionData")
    public static Object[][] getCreateEditConditionData() {
        GameLossesPlayerReportEntity data1 = new GameLossesPlayerReportEntity();
        data1.clearAllNotMandatory();
        data1.setStartDateOptions("Specific date");
        data1.setStartDateValue(Config.getFloatingFutureDate(-1,"yyyy-MM-dd"));
        data1.setEndDateOptions("Last update (approx. hourly)");
        data1.setRealMoney(true);
        data1.setAllMoney(false);
        data1.setClientType("Admin");
        data1.setClientPlatform("Flash");
        data1.setGameCategory("Scratchcards");
        data1.setSuspended(Filter.ALL);
        data1.setFrozen(Filter.YES);
        data1.setVipLevel("2");
        data1.setMinLosses("3");
        data1.setMinNetLoss("1");
        data1.setMinDeposits("5");
        data1.setMinAverageBet("0");

        GameLossesPlayerReportEntity data2 = new GameLossesPlayerReportEntity();

        return new Object[][]{
                {data1, data2}
        };
    }

    @DataProvider(name = "getPressCancelData")
    public static Object[][] getPressCancelData() {
        GameLossesPlayerReportEntity data1 = new GameLossesPlayerReportEntity();
        GameLossesPlayerReportEntity data2 = new GameLossesPlayerReportEntity();
        data2.setAllDefault();

        return new Object[][]{
                {data1, data2}
        };
    }

    @DataProvider(name = "getGameFieldsAndGameCategoryData")
    public static Object[][] getGameFieldsAndGameCategoryData() {
        GameLossesPlayerReportEntity data1 = new GameLossesPlayerReportEntity();
        data1.clearAllNotMandatory();
        data1.setGame1("#Gold Rally 8 Lines (NAG)");

        GameLossesPlayerReportEntity data2 = new GameLossesPlayerReportEntity();
        data2.clearAllNotMandatory();
        data2.setGame2("Vikingmania");

        GameLossesPlayerReportEntity data3 = new GameLossesPlayerReportEntity();
        data3.clearAllNotMandatory();
        data3.setGame3("AG-LepLuck");

        GameLossesPlayerReportEntity data4 = new GameLossesPlayerReportEntity();
        data4.clearAllNotMandatory();
        data4.setGame4("Ässäkisa Double");

        GameLossesPlayerReportEntity data5 = new GameLossesPlayerReportEntity();
        data5.clearAllNotMandatory();
        data5.setGame5("AG-8Horse");

        GameLossesPlayerReportEntity data6 = new GameLossesPlayerReportEntity();
        data6.clearAllNotMandatory();

        GameLossesPlayerReportEntity data7 = new GameLossesPlayerReportEntity();
        data7.clearAllNotMandatory();
        data7.setGameCategory("Video Pokers");

        return new Object[][]{
                {data1, false, true},
                {data2, false, true},
                {data3, false, true},
                {data4, false, true},
                {data5, false, true},
                {data6, true, true},
                {data7, true, false}
        };
    }

}
