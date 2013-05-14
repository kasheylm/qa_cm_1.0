package com.playtech.cm.utils.entities;

import com.playtech.cm.utils.Config;
import org.testng.annotations.DataProvider;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 9/25/12
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class TriggerData {
    private String triggerType;

    private String shownTrigger;
    private String shownSummary;
    private ArrayList<String> maskInDb;

    private String triggerOnSpecified;
    private ArrayList<String> excludingDates;

//    IF SPECIFIED ON DAYS
    private ArrayList<String> weekDaysDays;
    private ArrayList<String> weekDaysAtTimes;
//    IF SPECIFIED ON FREQUENCY
    private String freqRepeatEvery;
    private String freqHoursOrDays;
    private String freqEveryAtTime;
    private String freqStartingFromDay;
    private String freqStartingFromTime;
    private Boolean freqUseActivityStartDateAndTime;
//    IF SPECIFIED ON DATE AND TIME
    List<String[]> specOnDateDateAndTime;


    public TriggerData setDateAndTimeTrigger() {
        String excDate1 = Config.getFloatingFutureDate(1,"yyyy-MM-dd");
        String excDate1Expected = Config.getFloatingFutureDate(1,"dd MMM yyyy");
        String cronExpected1 = Config.getFloatingFutureDate(1, "d M ? yyyy");
        String[] dateTime1 = new String[2];
        dateTime1[0] = excDate1;
        dateTime1[1] = "23:30";
        List<String[]> dateTimeList = new ArrayList<String[]>();
        dateTimeList.add(dateTime1);
//        TriggerData triggerData = new TriggerData();
        this.setTriggerType("Scheduled (Date / Time / Frequency)");
        this.setTriggerOnSpecified("Date & Time");
        this.setSpecOnDateDateAndTime(dateTimeList);
        this.setExcludingDates(new ArrayList<String>());
        this.setShownSummary("On " + excDate1Expected + " at " + dateTime1[1]);
        this.setShownTrigger("Scheduled - Date & Time");
        this.setMaskInDb(new ArrayList<String>(Arrays.asList("0 30 23 " + cronExpected1)));
        return this;
    }

    @DataProvider(name = "getWeekDaysTriggerData")
    public static Object[][] getWeekDaysTriggerData() {
        String excDate1 = Config.getFloatingFutureDate(1,"yyyy-MM-dd");
        String excDate2 = Config.getFloatingFutureDate(3,"yyyy-MM-dd");
        String excDate1Expected = Config.getFloatingFutureDate(1,"dd MMM yyyy");
        String excDate2Expected = Config.getFloatingFutureDate(3,"dd MMM yyyy");

        TriggerData triggerData1 = new TriggerData();
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Weekdays");
        triggerData1.setWeekDaysDays(new ArrayList<String>(Arrays.asList("Mon", "Tue", "Fri")));
        triggerData1.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("0:15", "3:30", "2:45")));
        triggerData1.setShownSummary("Every FRI,MON,TUE at 0:15, 3:30, 2:45 excluding " + excDate1Expected + ", " + excDate2Expected);
        triggerData1.setShownTrigger("Scheduled - Weekdays");
        triggerData1.setExcludingDates(new ArrayList<String>(Arrays.asList(excDate1, excDate2)));
        triggerData1.setMaskInDb(new ArrayList<String>(Arrays.asList("0 15 0 ? * FRI,TUE,MON", "0 30 3 ? * FRI,TUE,MON", "0 45 2 ? * FRI,TUE,MON")));

        return new Object[][]{
                {triggerData1},
        };
    }

    @DataProvider(name = "getEditWeekDaysTriggerData")
    public static Object[][] getEditWeekDaysTriggerData() {
        String excDate1 = Config.getFloatingFutureDate(1,"yyyy-MM-dd");
        String excDate2 = Config.getFloatingFutureDate(3,"yyyy-MM-dd");
        String excDate1Expected = Config.getFloatingFutureDate(1,"dd MMM yyyy");
        String excDate2Expected = Config.getFloatingFutureDate(3,"dd MMM yyyy");

        TriggerData triggerData1 = new TriggerData();
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Weekdays");
        triggerData1.setWeekDaysDays(new ArrayList<String>(Arrays.asList("Mon", "Tue", "Fri")));
        triggerData1.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("0:15", "3:30", "2:45")));
        triggerData1.setExcludingDates(new ArrayList<String>(Arrays.asList(excDate1, excDate2)));
        triggerData1.setShownSummary("Every FRI,MON,TUE at 0:15, 3:30, 2:45 excluding " + excDate1Expected + ", " + excDate2Expected);
        triggerData1.setShownTrigger("Scheduled - Weekdays");
        triggerData1.setMaskInDb(new ArrayList<String>(Arrays.asList("0 15 0 ? * FRI,TUE,MON", "0 30 3 ? * FRI,TUE,MON", "0 45 2 ? * FRI,TUE,MON")));

        TriggerData triggerData2 = new TriggerData();
        triggerData2.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData2.setTriggerOnSpecified("Weekdays");
        triggerData2.setWeekDaysDays(new ArrayList<String>(Arrays.asList("Fri", "Sun")));
        triggerData2.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("0:30")));
        triggerData2.setShownSummary("Every FRI,SUN at 0:30");
        triggerData2.setShownTrigger("Scheduled - Weekdays");
        triggerData2.setExcludingDates(new ArrayList<String>());
        triggerData2.setMaskInDb(new ArrayList<String>(Arrays.asList("0 30 0 ? * FRI,SUN")));

        return new Object[][]{
                {triggerData1, triggerData2},
        };
    }

    @DataProvider(name = "getExlusionDateWeekDaysTriggerData")
    public static Object[][] getExlusionDateWeekDaysTriggerData() {
        String invalidString1 = "asdASd";
        String invalidString2 = "!@#$%^&*()_-+=?/>.<,";
        String invalidString3 = "12.10.2010";
        String invalidString4 = "13/14/201";
        String invalidString5 = "2/9/19";
        String invalidString6 = "2012-01-01";

        List<String> errorMessages = Arrays.asList("Datefield. Please use the format yyyy-MM-dd for specifying dates.");
        List<String> errorMessages1 = Arrays.asList("New exclude date can't be in the past.");

        TriggerData triggerData1 = new TriggerData();
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Weekdays");
        triggerData1.setWeekDaysDays(new ArrayList<String>(Arrays.asList("Mon")));
        triggerData1.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("0:15")));
        triggerData1.setShownSummary("");
        triggerData1.setShownTrigger("");
        triggerData1.setExcludingDates(new ArrayList<String>(Arrays.asList(invalidString1)));
        triggerData1.setMaskInDb(new ArrayList<String>(Arrays.asList("")));

        TriggerData triggerData2 = new TriggerData();
        triggerData2.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData2.setTriggerOnSpecified("Weekdays");
        triggerData2.setWeekDaysDays(new ArrayList<String>(Arrays.asList("Mon")));
        triggerData2.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("0:15")));
        triggerData2.setShownSummary("");
        triggerData2.setShownTrigger("");
        triggerData2.setExcludingDates(new ArrayList<String>(Arrays.asList(invalidString2)));
        triggerData2.setMaskInDb(new ArrayList<String>(Arrays.asList("")));

        TriggerData triggerData3 = new TriggerData();
        triggerData3.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData3.setTriggerOnSpecified("Weekdays");
        triggerData3.setWeekDaysDays(new ArrayList<String>(Arrays.asList("Mon")));
        triggerData3.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("0:15")));
        triggerData3.setShownSummary("");
        triggerData3.setShownTrigger("");
        triggerData3.setExcludingDates(new ArrayList<String>(Arrays.asList(invalidString3)));
        triggerData3.setMaskInDb(new ArrayList<String>(Arrays.asList("")));

        TriggerData triggerData4 = new TriggerData();
        triggerData4.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData4.setTriggerOnSpecified("Weekdays");
        triggerData4.setWeekDaysDays(new ArrayList<String>(Arrays.asList("Mon")));
        triggerData4.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("0:15")));
        triggerData4.setShownSummary("");
        triggerData4.setShownTrigger("");
        triggerData4.setExcludingDates(new ArrayList<String>(Arrays.asList(invalidString4)));
        triggerData4.setMaskInDb(new ArrayList<String>(Arrays.asList("")));

        TriggerData triggerData5 = new TriggerData();
        triggerData5.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData5.setTriggerOnSpecified("Weekdays");
        triggerData5.setWeekDaysDays(new ArrayList<String>(Arrays.asList("Mon")));
        triggerData5.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("0:15")));
        triggerData5.setShownSummary("");
        triggerData5.setShownTrigger("");
        triggerData5.setExcludingDates(new ArrayList<String>(Arrays.asList(invalidString5)));
        triggerData5.setMaskInDb(new ArrayList<String>(Arrays.asList("")));

        TriggerData triggerData6 = new TriggerData();
        triggerData6.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData6.setTriggerOnSpecified("Weekdays");
        triggerData6.setWeekDaysDays(new ArrayList<String>(Arrays.asList("Mon")));
        triggerData6.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("0:15")));
        triggerData6.setShownSummary("");
        triggerData6.setShownTrigger("");
        triggerData6.setExcludingDates(new ArrayList<String>(Arrays.asList(invalidString6)));
        triggerData6.setMaskInDb(new ArrayList<String>(Arrays.asList("")));

        return new Object[][]{
                {triggerData1, errorMessages},
                {triggerData2, errorMessages},
                {triggerData3, errorMessages},
                {triggerData4, errorMessages},
                {triggerData5, errorMessages},
                {triggerData6, errorMessages1},
        };
    }

    @DataProvider(name = "getAtTimeFieldWeekDaysTriggerData")
    public static Object[][] getAtTimeFieldWeekDaysTriggerData() {
        String invalidString1 = "asdASd";
        String invalidString2 = "!@#$%^&*()_-+=?/>.<,";

        List<String> invalidValues = new ArrayList(Arrays.asList(invalidString1,invalidString2));

        TriggerData triggerData1 = new TriggerData();
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Weekdays");
        triggerData1.setWeekDaysDays(new ArrayList<String>(Arrays.asList("Mon")));
        triggerData1.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("0:00")));
        triggerData1.setExcludingDates(new ArrayList<String>());

        return new Object[][]{
                {invalidValues, triggerData1},
        };
    }

    @DataProvider(name = "getAddExclusionDateWeekDaysTriggerData")
    public static Object[][] getAddExclusionDateWeekDaysTriggerData() {
        String excDate1 = Config.getFloatingFutureDate(1,"yyyy-MM-dd");
        String excDate2 = Config.getFloatingFutureDate(3,"yyyy-MM-dd");
        String excDate1Expected = Config.getFloatingFutureDate(1,"dd MMM yyyy");
        String excDate2Expected = Config.getFloatingFutureDate(3,"dd MMM yyyy");

        TriggerData triggerData1 = new TriggerData();
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Weekdays");
        triggerData1.setWeekDaysDays(new ArrayList<String>(Arrays.asList("Mon", "Tue", "Fri")));
        triggerData1.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("0:00", "3:30", "23:45")));
        triggerData1.setExcludingDates(new ArrayList<String>());
        triggerData1.setShownSummary("Every FRI,MON,TUE at 0:00, 3:30, 23:45");
        triggerData1.setShownTrigger("Scheduled - Weekdays");
        triggerData1.setMaskInDb(new ArrayList<String>(Arrays.asList("0 0 0 ? * FRI,TUE,MON", "0 30 3 ? * FRI,TUE,MON", "0 45 23 ? * FRI,TUE,MON")));

        TriggerData triggerData2 = new TriggerData();
        triggerData2.setShownSummary("Every FRI,MON,TUE at 0:00, 3:30, 23:45 excluding " + excDate1Expected + ", " + excDate2Expected);
        triggerData2.setShownTrigger("Scheduled - Weekdays");
        triggerData2.setExcludingDates(new ArrayList<String>(Arrays.asList(excDate1, excDate2)));
        triggerData2.setMaskInDb(new ArrayList<String>(Arrays.asList("0 0 0 ? * FRI,TUE,MON", "0 30 3 ? * FRI,TUE,MON", "0 45 23 ? * FRI,TUE,MON")));

        return new Object[][]{
                {triggerData1, triggerData2},
        };
    }

    @DataProvider(name = "getRemoveExclusionDateWeekDaysTriggerData")
    public static Object[][] getRemoveExclusionDateWeekDaysTriggerData() {
        String excDate1 = Config.getFloatingFutureDate(1,"yyyy-MM-dd");
        String excDate2 = Config.getFloatingFutureDate(3,"yyyy-MM-dd");
        String excDate1Expected = Config.getFloatingFutureDate(1,"dd MMM yyyy");
        String excDate2Expected = Config.getFloatingFutureDate(3,"dd MMM yyyy");

        TriggerData triggerData1 = new TriggerData();
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Weekdays");
        triggerData1.setWeekDaysDays(new ArrayList<String>(Arrays.asList("Mon", "Tue", "Fri")));
        triggerData1.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("0:00", "3:30", "23:45")));
        triggerData1.setExcludingDates(new ArrayList<String>(Arrays.asList(excDate1, excDate2)));
        triggerData1.setShownSummary("Every FRI,MON,TUE at 0:00, 3:30, 23:45 excluding "+ excDate1Expected + ", " + excDate2Expected);
        triggerData1.setShownTrigger("Scheduled - Weekdays");
        triggerData1.setMaskInDb(new ArrayList<String>(Arrays.asList("0 0 0 ? * FRI,TUE,MON", "0 30 3 ? * FRI,TUE,MON", "0 45 23 ? * FRI,TUE,MON")));

        TriggerData triggerData2 = new TriggerData();
        triggerData2.setShownSummary("Every FRI,MON,TUE at 0:00, 3:30, 23:45");
        triggerData2.setShownTrigger("Scheduled - Weekdays");
        triggerData2.setExcludingDates(new ArrayList<String>());
        triggerData2.setMaskInDb(new ArrayList<String>(Arrays.asList("0 0 0 ? * FRI,TUE,MON", "0 30 3 ? * FRI,TUE,MON", "0 45 23 ? * FRI,TUE,MON")));

        return new Object[][]{
                {triggerData1, triggerData2},
        };
    }

    @DataProvider(name = "getAddTimesWeekDaysTriggerData")
    public static Object[][] getAddTimesWeekDaysTriggerData() {
        TriggerData triggerData1 = new TriggerData();
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Weekdays");
        triggerData1.setWeekDaysDays(new ArrayList<String>(Arrays.asList("Mon", "Tue", "Fri")));
        triggerData1.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("0:00")));
        triggerData1.setExcludingDates(new ArrayList<String>());
        triggerData1.setShownSummary("Every FRI,MON,TUE at 0:00");
        triggerData1.setShownTrigger("Scheduled - Weekdays");
        triggerData1.setMaskInDb(new ArrayList<String>(Arrays.asList("0 0 0 ? * FRI,TUE,MON")));

        TriggerData triggerData2 = new TriggerData();
        triggerData2.setShownSummary("Every FRI,MON,TUE at 0:00, 3:30, 23:45");
        triggerData2.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("3:30", "23:45")));
        triggerData2.setExcludingDates(new ArrayList<String>());
        triggerData2.setShownTrigger("Scheduled - Weekdays");
        triggerData2.setMaskInDb(new ArrayList<String>(Arrays.asList("0 0 0 ? * FRI,TUE,MON", "0 30 3 ? * FRI,TUE,MON", "0 45 23 ? * FRI,TUE,MON")));

        return new Object[][]{
                {triggerData1, triggerData2},
        };
    }

    @DataProvider(name = "getRemoveTimesWeekDaysTriggerData")
    public static Object[][] getRemoveTimesWeekDaysTriggerData() {
        TriggerData triggerData1 = new TriggerData();
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Weekdays");
        triggerData1.setWeekDaysDays(new ArrayList<String>(Arrays.asList("Mon", "Tue", "Fri")));
        triggerData1.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("0:00", "3:30", "23:45")));
        triggerData1.setExcludingDates(new ArrayList<String>());
        triggerData1.setShownSummary("Every FRI,MON,TUE at 0:00, 3:30, 23:45");
        triggerData1.setShownTrigger("Scheduled - Weekdays");
        triggerData1.setMaskInDb(new ArrayList<String>(Arrays.asList("0 0 0 ? * FRI,TUE,MON", "0 30 3 ? * FRI,TUE,MON", "0 45 23 ? * FRI,TUE,MON")));

        TriggerData triggerData2 = new TriggerData();
        triggerData2.setShownSummary("Every FRI,MON,TUE at 0:00");
        triggerData2.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("0:00")));
        triggerData2.setExcludingDates(new ArrayList<String>());
        triggerData2.setShownTrigger("Scheduled - Weekdays");
        triggerData2.setMaskInDb(new ArrayList<String>(Arrays.asList("0 0 0 ? * FRI,TUE,MON")));

        return new Object[][]{
                {triggerData1, triggerData2},
        };
    }

    @DataProvider(name = "singleDayForWeekDaysTriggerData")
    public static Object[][] singleDayForWeekDaysTriggerData() {
        String[] days = {"Mon","Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        Object data [][] = new Object[days.length][1];
        int i = 0;

        for(String day:days){
            TriggerData triggerData = new TriggerData();
            triggerData.setTriggerType("Scheduled (Date / Time / Frequency)");
            triggerData.setTriggerOnSpecified("Weekdays");
            triggerData.setWeekDaysDays(new ArrayList<String>(Arrays.asList(day)));
            triggerData.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("3:30")));
            triggerData.setExcludingDates(new ArrayList<String>());
            triggerData.setShownSummary("Every "+day.toUpperCase()+" at 3:30");
            triggerData.setShownTrigger("Scheduled - Weekdays");
            triggerData.setMaskInDb(new ArrayList<String>(Arrays.asList("0 30 3 ? * "+day.toUpperCase())));

            data[i] = new Object[]{triggerData};
            i+=1;
        }
        return data;
    }

    @DataProvider(name = "getEmptyFieldsWeekDaysTriggerData")
    public static Object[][] getEmptyFieldsWeekDaysTriggerData() {
        String excDate1 = Config.getFloatingFutureDate(1,"yyyy-MM-dd");
        String excDate2 = Config.getFloatingFutureDate(3,"yyyy-MM-dd");

//        List<String> errorMessages1 = Arrays.asList("Should be specifed at minimum one time.");
//        List<String> errorMessages2 = Arrays.asList("No days selected.");
        List<String> errorMessages1 = Arrays.asList("Should be specifed at minimum one time.", "No days selected.");

        TriggerData triggerData1 = new TriggerData();
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Weekdays");
        triggerData1.setWeekDaysDays(new ArrayList<String>());
        triggerData1.setWeekDaysAtTimes(new ArrayList<String>());
        triggerData1.setExcludingDates(new ArrayList<String>());

//        TriggerData triggerData2 = new TriggerData();
//        triggerData2.setTriggerType("Scheduled (Date / Time / Frequency)");
//        triggerData2.setTriggerOnSpecified("Weekdays");
//        triggerData2.setWeekDaysDays(new ArrayList<String>());
//        triggerData2.setWeekDaysAtTimes(new ArrayList<String>(Arrays.asList("0:00", "3:30", "23:45")));
//        triggerData2.setExcludingDates(new ArrayList<String>(Arrays.asList(excDate1, excDate2)));
//
//        TriggerData triggerData3 = new TriggerData();
//        triggerData3.setTriggerType("Scheduled (Date / Time / Frequency)");
//        triggerData3.setTriggerOnSpecified("Weekdays");
//        triggerData3.setWeekDaysDays(new ArrayList<String>());
//        triggerData3.setWeekDaysAtTimes(new ArrayList<String>());
//        triggerData3.setExcludingDates(new ArrayList<String>(Arrays.asList(excDate1, excDate2)));

        return new Object[][]{
                {triggerData1, errorMessages1},
//                {triggerData2, errorMessages2},
//                {triggerData3, errorMessages3},
        };
    }

    @DataProvider(name = "getSessionCleanedWeekDaysTriggerData")
    public static Object[][] getSessionCleanedWeekDaysTriggerData() {
        String invalidString1 = "asdASd";
        String errorMessage1 = "No days selected.";
        String errorMessage2 = "Datefield. Please use the format yyyy-MM-dd for specifying dates.";

        TriggerData triggerData = new TriggerData();
        triggerData.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData.setTriggerOnSpecified("Weekdays");

        return new Object[][]{ {triggerData, invalidString1, errorMessage1, errorMessage2} };
    }


    @DataProvider(name = "getHoursFrequencyTriggerData")
     public static Object[][] getHoursFrequencyTriggerData() {
        String excDate1 = Config.getFloatingFutureDate(1,"yyyy-MM-dd");
        String excDate2 = Config.getFloatingFutureDate(3,"yyyy-MM-dd");
        String excDate1Expected = Config.getFloatingFutureDate(1,"dd MMM yyyy");
        String excDate2Expected = Config.getFloatingFutureDate(3,"dd MMM yyyy");

        TriggerData triggerData1 = new TriggerData();
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Frequency");
        triggerData1.setFreqRepeatEvery("5");
        triggerData1.setFreqHoursOrDays("Hours");
        triggerData1.setFreqUseActivityStartDateAndTime(false);
        triggerData1.setFreqStartingFromDay(Config.getDate());
        triggerData1.setFreqStartingFromTime("13:15");
        triggerData1.setShownSummary("Every 5 hours starting from " + Config.getFloatingFutureDate(0, "dd MMM yyyy") + " at 13:15 excluding " +
                excDate1Expected + ", " + excDate2Expected);
        triggerData1.setShownTrigger("Scheduled - Frequency");
        triggerData1.setExcludingDates(new ArrayList<String>(Arrays.asList(excDate1, excDate2)));
        triggerData1.setMaskInDb(new ArrayList<String>(Arrays.asList("0 15 3/5 * * ?")));

        return new Object[][]{ {triggerData1} };
    }

    @DataProvider(name = "getDaysFrequencyTriggerData")
    public static Object[][] getDaysFrequencyTriggerData() {
        String excDate1 = Config.getFloatingFutureDate(1,"yyyy-MM-dd");
        String excDate2 = Config.getFloatingFutureDate(3,"yyyy-MM-dd");
        String excDate1Expected = Config.getFloatingFutureDate(1,"dd MMM yyyy");
        String excDate2Expected = Config.getFloatingFutureDate(3,"dd MMM yyyy");

        TriggerData triggerData1 = new TriggerData();
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Frequency");
        triggerData1.setFreqRepeatEvery("3");
        triggerData1.setFreqHoursOrDays("Days");
        triggerData1.setFreqEveryAtTime("10:30");
        triggerData1.setFreqUseActivityStartDateAndTime(false);
        triggerData1.setFreqStartingFromDay("2012-10-06");
        triggerData1.setShownSummary("Every 3 days at 10:30 starting from 06 Oct 2012 excluding " +
                excDate1Expected + ", " + excDate2Expected);
        triggerData1.setShownTrigger("Scheduled - Frequency");
        triggerData1.setExcludingDates(new ArrayList<String>(Arrays.asList(excDate1, excDate2)));
        triggerData1.setMaskInDb(new ArrayList<String>(Arrays.asList("0 30 10 /3 * ?")));

        return new Object[][]{ {triggerData1} };
    }


    @DataProvider(name = "getEditFrequencyTriggerData")
    public static Object[][] getEditFrequencyTriggerData() {
        String excDate1 = Config.getFloatingFutureDate(1,"yyyy-MM-dd");
        String excDate2 = Config.getFloatingFutureDate(3,"yyyy-MM-dd");
        String excDate1Expected = Config.getFloatingFutureDate(1,"dd MMM yyyy");
        String excDate2Expected = Config.getFloatingFutureDate(3,"dd MMM yyyy");

        TriggerData triggerData1 = new TriggerData();
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Frequency");
        triggerData1.setFreqRepeatEvery("5");
        triggerData1.setFreqHoursOrDays("Hours");
        triggerData1.setFreqUseActivityStartDateAndTime(false);
        triggerData1.setFreqStartingFromDay(Config.getDate());
        triggerData1.setFreqStartingFromTime("13:15");
        triggerData1.setShownSummary("Every 5 hours starting from " + Config.getFloatingFutureDate(0, "dd MMM yyyy") + " at 13:15 excluding " +
                excDate1Expected + ", " + excDate2Expected);
        triggerData1.setShownTrigger("Scheduled - Frequency");
        triggerData1.setExcludingDates(new ArrayList<String>(Arrays.asList(excDate1, excDate2)));
        triggerData1.setMaskInDb(new ArrayList<String>(Arrays.asList("0 15 3/5 * * ?")));


         excDate1 = Config.getFloatingFutureDate(2,"yyyy-MM-dd");
         excDate2 = Config.getFloatingFutureDate(4,"yyyy-MM-dd");
         excDate1Expected = Config.getFloatingFutureDate(2,"dd MMM yyyy");
         excDate2Expected = Config.getFloatingFutureDate(4,"dd MMM yyyy");

        TriggerData triggerData2 = new TriggerData();
        triggerData2.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData2.setTriggerOnSpecified("Frequency");
        triggerData2.setFreqRepeatEvery("3");
        triggerData2.setFreqHoursOrDays("Hours");
        triggerData2.setFreqUseActivityStartDateAndTime(false);
        triggerData2.setFreqStartingFromDay(Config.getFloatingFutureDate(1, "yyyy-MM-dd"));
        triggerData2.setFreqStartingFromTime("14:45");
        triggerData2.setShownSummary("Every 3 hours starting from " + Config.getFloatingFutureDate(1, "dd MMM yyyy") + " at 14:45 excluding " +
                excDate1Expected + ", " + excDate2Expected);
        triggerData2.setShownTrigger("Scheduled - Frequency");
        triggerData2.setExcludingDates(new ArrayList<String>(Arrays.asList(excDate1, excDate2)));
        triggerData2.setMaskInDb(new ArrayList<String>(Arrays.asList("0 45 2/3 * * ?")));

        return new Object[][]{ {triggerData1, triggerData2} };
    }

    @DataProvider(name = "getHoursFrequencyTriggerDataWithFieldsData")
    public static Object[][] getHoursFrequencyTriggerDataWithFieldsData() {

        String text1 = "asdASd";
        String text2 = "!@#$%^&*()-_=+/?.>,<";
        String text3 = "31.12.0923";
        String text4 = "32/13/0001";
        List<String> list = null;
        return new Object[][]{
                {getHoursFrequencyTriggerData()[0][0], text1},
                {getHoursFrequencyTriggerData()[0][0], text2},
                {getHoursFrequencyTriggerData()[0][0], text3},
                {getHoursFrequencyTriggerData()[0][0], text4},
        };
    }

    @DataProvider(name = "getDateTimeTriggerData")
    public static Object[][] getDateTimeTriggerData() {
        String excDate1 = Config.getFloatingFutureDate(1,"yyyy-MM-dd");
        String excDate1Expected = Config.getFloatingFutureDate(1,"dd MMM yyyy");
        String cronExpected1 = Config.getFloatingFutureDate(1, "d M ? yyyy");

        String[] dateTime1 = new String[2];
        dateTime1[0] = excDate1;
        dateTime1[1] = "23:30";

        List<String[]> dateTimeList = new ArrayList<String[]>();
        dateTimeList.add(dateTime1);

        TriggerData triggerData1 = new TriggerData();
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Date & Time");
        triggerData1.setSpecOnDateDateAndTime(dateTimeList);
        triggerData1.setExcludingDates(new ArrayList<String>());
        triggerData1.setShownSummary("On "+excDate1Expected+" at "+dateTime1[1]);
        triggerData1.setShownTrigger("Scheduled - Date & Time");
        triggerData1.setMaskInDb(new ArrayList<String>(Arrays.asList("0 30 23 "+cronExpected1)));

        return new Object[][]{
                {triggerData1},
        };
    }

    @DataProvider(name = "getEditDateTimeTriggerData")
    public static Object[][] getEditDateTimeTriggerData() {
        String excDate1 = Config.getFloatingFutureDate(1,"yyyy-MM-dd");
        String excDate1Expected = Config.getFloatingFutureDate(1,"dd MMM yyyy");
        String cronExpected1 = Config.getFloatingFutureDate(1, "d M ? yyyy");

        String excDate2 = Config.getFloatingFutureDate(3,"yyyy-MM-dd");
        String excDate2Expected = Config.getFloatingFutureDate(3,"dd MMM yyyy");
        String cronExpected2 = Config.getFloatingFutureDate(3, "d M ? yyyy");

        String[] dateTime1 = new String[2];
        dateTime1[0] = excDate1;
        dateTime1[1] = "23:30";

        String[] dateTime2 = new String[2];
        dateTime2[0] = excDate2;
        dateTime2[1] = "0:15";

        List<String[]> dateTimeList1 = new ArrayList<String[]>();
        dateTimeList1.add(dateTime1);

        List<String[]> dateTimeList2 = new ArrayList<String[]>();
        dateTimeList2.add(dateTime2);

        TriggerData triggerData1 = new TriggerData();
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Date & Time");
        triggerData1.setSpecOnDateDateAndTime(dateTimeList1);
        triggerData1.setExcludingDates(new ArrayList<String>());
        triggerData1.setShownSummary("On "+excDate1Expected+" at "+dateTime1[1]);
        triggerData1.setShownTrigger("Scheduled - Date & Time");
        triggerData1.setMaskInDb(new ArrayList<String>(Arrays.asList("0 30 23 "+cronExpected1)));

        TriggerData triggerData2 = new TriggerData();
        triggerData2.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData2.setTriggerOnSpecified("Date & Time");
        triggerData2.setSpecOnDateDateAndTime(dateTimeList2);
        triggerData2.setExcludingDates(new ArrayList<String>());
        triggerData2.setShownSummary("On "+excDate2Expected+" at "+dateTime2[1]);
        triggerData2.setShownTrigger("Scheduled - Date & Time");
        triggerData2.setMaskInDb(new ArrayList<String>(Arrays.asList("0 15 0 "+cronExpected2)));

        return new Object[][]{
                {triggerData1, triggerData2}
        };
    }

    @DataProvider(name = "addNewDateTimeTriggerData")
    public static Object[][] addNewDateTimeTriggerData() {
        String excDate1 = Config.getFloatingFutureDate(1,"yyyy-MM-dd");
        String excDate1Expected = Config.getFloatingFutureDate(1,"dd MMM yyyy");
        String cronExpected1 = Config.getFloatingFutureDate(1, "d M ? yyyy");

        String excDate2 = Config.getFloatingFutureDate(3,"yyyy-MM-dd");
        String excDate2Expected = Config.getFloatingFutureDate(3,"dd MMM yyyy");
        String cronExpected2 = Config.getFloatingFutureDate(3, "d M ? yyyy");

        String[] dateTime1 = new String[2];
        dateTime1[0] = excDate1;
        dateTime1[1] = "23:30";

        String[] dateTime2 = new String[2];
        dateTime2[0] = excDate2;
        dateTime2[1] = "0:15";

        List<String[]> dateTimeList1 = new ArrayList<String[]>();
        dateTimeList1.add(dateTime1);

        List<String[]> dateTimeList2 = new ArrayList<String[]>();
        dateTimeList2.add(dateTime2);

        TriggerData triggerData1 = new TriggerData();
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Date & Time");
        triggerData1.setSpecOnDateDateAndTime(dateTimeList1);
        triggerData1.setExcludingDates(new ArrayList<String>());
        triggerData1.setShownSummary("On "+excDate1Expected+" at "+dateTime1[1]);
        triggerData1.setShownTrigger("Scheduled - Date & Time");
        triggerData1.setMaskInDb(new ArrayList<String>(Arrays.asList("0 30 23 "+cronExpected1)));

        TriggerData triggerData2 = new TriggerData();
        triggerData2.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData2.setTriggerOnSpecified("Date & Time");
        triggerData2.setSpecOnDateDateAndTime(dateTimeList2);
        triggerData2.setExcludingDates(new ArrayList<String>());
        triggerData2.setShownSummary("On " + excDate1Expected + " at " + dateTime1[1] + ", " + excDate2Expected + " at " + dateTime2[1]);
        triggerData2.setShownTrigger("Scheduled - Date & Time");
        triggerData2.setMaskInDb(new ArrayList<String>(Arrays.asList("0 30 23 "+cronExpected1, "0 15 0 "+cronExpected2)));

        return new Object[][]{
                {triggerData1, triggerData2}
        };
    }

    @DataProvider(name = "getSaveEmptyDateTimeTriggerData")
    public static Object[][] getSaveEmptyDateTimeTriggerData() {
        List<String> errorMessages1 = Arrays.asList("Should be specifed at minimum one date.");
        TriggerData triggerData1 = new TriggerData();
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Date & Time");
        return new Object[][]{
                {triggerData1, errorMessages1}
        };
    }

    @DataProvider(name = "getValidateDateFieldDateTimeTriggerData")
    public static Object[][] getValidateDateFieldDateTimeTriggerData() {
        List<String> errorMessages1 = Arrays.asList("Date is required.");
        List<String> errorMessages2 = Arrays.asList("New date can't be in the past.");
        List<String> errorMessages3 = Arrays.asList("Date is required.","Datefield 0. Please use the format yyyy-MM-dd for specifying dates.");

        String[] dateTime1 = new String[2];
        dateTime1[0] = "";
        dateTime1[1] = "0:15";
        List<String[]> dateTimeList1 = new ArrayList<String[]>();
        dateTimeList1.add(dateTime1);

        String[] dateTime2 = new String[2];
        dateTime2[0] = "2012-01-01";
        dateTime2[1] = "0:15";
        List<String[]> dateTimeList2 = new ArrayList<String[]>();
        dateTimeList2.add(dateTime2);

        String[] dateTime3 = new String[2];
        dateTime3[0] = "asdASd";
        dateTime3[1] = "0:15";
        List<String[]> dateTimeList3 = new ArrayList<String[]>();
        dateTimeList3.add(dateTime3);

        String[] dateTime4 = new String[2];
        dateTime4[0] = "!@#$%^&*()_-+=?/>.<,";
        dateTime4[1] = "0:15";
        List<String[]> dateTimeList4 = new ArrayList<String[]>();
        dateTimeList4.add(dateTime4);

        String[] dateTime5 = new String[2];
        dateTime5[0] = "12.10.2010";
        dateTime5[1] = "0:15";
        List<String[]> dateTimeList5 = new ArrayList<String[]>();
        dateTimeList5.add(dateTime5);

        String[] dateTime6 = new String[2];
        dateTime6[0] = "13/14/201";
        dateTime6[1] = "0:15";
        List<String[]> dateTimeList6 = new ArrayList<String[]>();
        dateTimeList6.add(dateTime6);

        TriggerData triggerData1 = new TriggerData();
        triggerData1.setSpecOnDateDateAndTime(dateTimeList1);
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Date & Time");

        TriggerData triggerData2 = new TriggerData();
        triggerData2.setSpecOnDateDateAndTime(dateTimeList2);
        triggerData2.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData2.setTriggerOnSpecified("Date & Time");

        TriggerData triggerData3 = new TriggerData();
        triggerData3.setSpecOnDateDateAndTime(dateTimeList3);
        triggerData3.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData3.setTriggerOnSpecified("Date & Time");

        TriggerData triggerData4 = new TriggerData();
        triggerData4.setSpecOnDateDateAndTime(dateTimeList4);
        triggerData4.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData4.setTriggerOnSpecified("Date & Time");

        TriggerData triggerData5 = new TriggerData();
        triggerData5.setSpecOnDateDateAndTime(dateTimeList5);
        triggerData5.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData5.setTriggerOnSpecified("Date & Time");

        TriggerData triggerData6 = new TriggerData();
        triggerData6.setSpecOnDateDateAndTime(dateTimeList6);
        triggerData6.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData6.setTriggerOnSpecified("Date & Time");

        return new Object[][]{
                {triggerData1, errorMessages1},
                {triggerData2, errorMessages2},
                {triggerData3, errorMessages3},
                {triggerData4, errorMessages3},
                {triggerData5, errorMessages3},
                {triggerData6, errorMessages3},
        };
    }

    @DataProvider(name = "getRemoveRowDateTimeTriggerData")
    public static Object[][] getRemoveRowDateTimeTriggerData() {
        String excDate1 = Config.getFloatingFutureDate(1,"yyyy-MM-dd");
        String excDate1Expected = Config.getFloatingFutureDate(1,"dd MMM yyyy");
        String cronExpected1 = Config.getFloatingFutureDate(1, "d M ? yyyy");

        String excDate2 = Config.getFloatingFutureDate(3,"yyyy-MM-dd");
        String excDate2Expected = Config.getFloatingFutureDate(3,"dd MMM yyyy");
        String cronExpected2 = Config.getFloatingFutureDate(3, "d M ? yyyy");

        String[] dateTime1 = new String[2];
        dateTime1[0] = excDate1;
        dateTime1[1] = "23:30";

        String[] dateTime2 = new String[2];
        dateTime2[0] = excDate2;
        dateTime2[1] = "0:15";

        String[] dateTime3 = new String[2];
        dateTime3[0] = excDate1;
        dateTime3[1] = "0:45";

        String[] dateTime4 = new String[2];
        dateTime4[0] = excDate1;
        dateTime4[1] = "19:30";

        String[] dateTime5 = new String[2];
        dateTime5[0] = excDate2;
        dateTime5[1] = "3:00";

        List<String[]> dateTimeList1 = new ArrayList<String[]>();
        dateTimeList1.add(dateTime1);
        dateTimeList1.add(dateTime2);
        dateTimeList1.add(dateTime3);
        dateTimeList1.add(dateTime4);
        dateTimeList1.add(dateTime5);

        List<String[]> dateTimeList2 = new ArrayList<String[]>();
        dateTimeList2.add(dateTime2);

        List<String[]> dateTimesToDelete = new ArrayList<String[]>();
        dateTimesToDelete.add(dateTime1);
        dateTimesToDelete.add(dateTime3);
        dateTimesToDelete.add(dateTime4);
        dateTimesToDelete.add(dateTime5);

        TriggerData triggerData1 = new TriggerData();
        triggerData1.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData1.setTriggerOnSpecified("Date & Time");
        triggerData1.setSpecOnDateDateAndTime(dateTimeList1);
        triggerData1.setExcludingDates(new ArrayList<String>());
        triggerData1.setShownSummary("On "+excDate1Expected+" at "+dateTime1[1]+", "+excDate2Expected+" at "+dateTime2[1]+", "+excDate1Expected+" at "+dateTime3[1]+", "+excDate1Expected+" at "+dateTime4[1]+", "+excDate2Expected+" at "+dateTime5[1]);
        triggerData1.setShownTrigger("Scheduled - Date & Time");
        triggerData1.setMaskInDb(new ArrayList<String>(Arrays.asList("0 30 23 "+cronExpected1,"0 15 0 "+cronExpected2,"0 45 0 "+cronExpected1,"0 30 19 "+cronExpected1,"0 0 3 "+cronExpected2)));

        TriggerData triggerData2 = new TriggerData();
        triggerData2.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData2.setTriggerOnSpecified("Date & Time");
        triggerData2.setExcludingDates(new ArrayList<String>());
        triggerData2.setShownSummary("On "+excDate2Expected+" at "+dateTime2[1]);
        triggerData2.setShownTrigger("Scheduled - Date & Time");
        triggerData2.setMaskInDb(new ArrayList<String>(Arrays.asList("0 15 0 "+cronExpected2)));

        return new Object[][]{
                {triggerData1, triggerData2, dateTimesToDelete}
        };
    }

    @DataProvider(name = "getSessionCleanedDateTimeTriggerData")
    public static Object[][] getSessionCleanedDateTimeTriggerData() {
        String invalidString1 = "asdASd";
        String errorMessage1 = "Date is required.";
        String errorMessage2 = "Datefield 0. Please use the format yyyy-MM-dd for specifying dates.";

        TriggerData triggerData = new TriggerData();
        triggerData.setTriggerType("Scheduled (Date / Time / Frequency)");
        triggerData.setTriggerOnSpecified("Date & Time");

        return new Object[][]{ {triggerData, invalidString1, errorMessage1, errorMessage2} };
    }

    public String getShownTrigger() {
        return shownTrigger;
    }

    public void setShownTrigger(String shownTrigger) {
        this.shownTrigger = shownTrigger;
    }

    public String getShownSummary() {
        return shownSummary;
    }

    public void setShownSummary(String shownSummary) {
        this.shownSummary = shownSummary;
    }

    public ArrayList<String> getMaskInDb() {
        return maskInDb;
    }

    public void setMaskInDb(ArrayList<String> maskInDb) {
        this.maskInDb = maskInDb;
    }

    public String getTriggerOnSpecified() {
        return triggerOnSpecified;
    }

    public void setTriggerOnSpecified(String triggerOnSpecified) {
        this.triggerOnSpecified = triggerOnSpecified;
    }

    public ArrayList<String> getExcludingDates() {
        return excludingDates;
    }

    public void setExcludingDates(ArrayList<String> excludingDates) {
        this.excludingDates = excludingDates;
    }

    public ArrayList<String> getWeekDaysDays() {
        return weekDaysDays;
    }

    public void setWeekDaysDays(ArrayList<String> weekDaysDays) {
        this.weekDaysDays = weekDaysDays;
    }

    public ArrayList<String> getWeekDaysAtTimes() {
        return weekDaysAtTimes;
    }

    public void setWeekDaysAtTimes(ArrayList<String> weekDaysAtTimes) {
        this.weekDaysAtTimes = weekDaysAtTimes;
    }

    public String getFreqRepeatEvery() {
        return freqRepeatEvery;
    }

    public void setFreqRepeatEvery(String freqRepeatEvery) {
        this.freqRepeatEvery = freqRepeatEvery;
    }

    public String getFreqHoursOrDays() {
        return freqHoursOrDays;
    }

    public void setFreqHoursOrDays(String freqHoursOrDays) {
        this.freqHoursOrDays = freqHoursOrDays;
    }

    public String getFreqStartingFromDay() {
        return freqStartingFromDay;
    }

    public void setFreqStartingFromDay(String freqStartingFromDay) {
        this.freqStartingFromDay = freqStartingFromDay;
    }

    public String getFreqStartingFromTime() {
        return freqStartingFromTime;
    }

    public void setFreqStartingFromTime(String freqStartingFromTime) {
        this.freqStartingFromTime = freqStartingFromTime;
    }

    public Boolean getFreqUseActivityStartDateAndTime() {
        return freqUseActivityStartDateAndTime;
    }

    public void setFreqUseActivityStartDateAndTime(Boolean freqUseActivityStartDateAndTime) {
        this.freqUseActivityStartDateAndTime = freqUseActivityStartDateAndTime;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public List<String[]> getSpecOnDateDateAndTime() {
        return specOnDateDateAndTime;
    }

    public void setSpecOnDateDateAndTime(List<String[]> specOnDateDateAndTime) {
        this.specOnDateDateAndTime = specOnDateDateAndTime;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getFreqEveryAtTime() {
        return freqEveryAtTime;
    }

    public void setFreqEveryAtTime(String freqEveryAtTime) {
        this.freqEveryAtTime = freqEveryAtTime;
    }
}
