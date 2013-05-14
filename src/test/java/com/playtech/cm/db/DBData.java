package com.playtech.cm.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 User: Denis Veselovskiy
 * Date: 9/27/12
 * Time: 10:44 AM
 * To change this template use File | Settings | File Templates.
 */

public class DBData {
    public static ResultSet resSet = null;

    public static String getActivityStartDate(String activityID){
        Timestamp ts = null;
        try {
            resSet = DbUnitUtil.executeQuery(
                    String.format("select a.start_date from activity a where a.activity_id =%s", activityID));
            while(resSet.next()){
                ts = resSet.getTimestamp(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ts.toString().substring(0, 16);
    }

    public static String getActivityEndDate(String activityID){
        Timestamp ts = null;
        try {
            resSet = DbUnitUtil.executeQuery(
                    String.format("select a.end_date from activity a where a.activity_id =%s", activityID));

            while(resSet.next()){
                ts = resSet.getTimestamp(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ts.toString().substring(0, 16);
    }

    public static boolean getActivityIsTest(String activityID){
        boolean result = false;
        try {
            resSet = DbUnitUtil.executeQuery(
                    String.format("select a.test from activity a where a.activity_id =%s", activityID));

            while(resSet.next()){
                result = resSet.getBoolean(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<String> getCronExpressions(String campaignName, String activityName){
        ArrayList<String> res = new ArrayList<String>();
        try {
            resSet = DbUnitUtil.executeQuery("select CRON_EXPRESSION from CRON_EXPRESSION t\n" +
                    "left join ACTIVITY_FILTER p on t.ACTIVITY_FILTER_ID = p.ACTIVITY_FILTER_ID left join ACTIVITY q on p.ACTIVITY_FILTER_ID = q.ACTIVITY_FILTER_ID\n" +
                    "left join CAMPAIGN s on q.CAMPAIGN_ID = s.CAMPAIGN_ID where s.NAME = '" + campaignName + "' and q.NAME = '" + activityName + "'");
            while(resSet.next()){
                res.add(resSet.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static ArrayList<String> getActivityFilters(String campaignName, String activityName){
        ArrayList<String> res = new ArrayList<String>();
        try {
            resSet = DbUnitUtil.executeQuery("select p.DESCRIPTION ||';'|| p.NAME from ACTIVITY_FILTER p\n" +
                    "left join ACTIVITY q on p.ACTIVITY_FILTER_ID = q.ACTIVITY_FILTER_ID\n" +
                    "left join CAMPAIGN s on q.CAMPAIGN_ID = s.CAMPAIGN_ID where s.NAME = '" + campaignName + "' and q.NAME = '" + activityName + "'");
            while(resSet.next()){
                res.add(resSet.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static List<String> getExclusionDates(String campaignName, String activityName){
        ArrayList<String> res = new ArrayList<String>();
        try {
            resSet = DbUnitUtil.executeQuery("select TO_CHAR(t.exclusion_date,'yyyy-MM-dd') from EXCLUSION_DATE t\n" +
                    "left join ACTIVITY_FILTER p on t.ACTIVITY_FILTER_ID = p.ACTIVITY_FILTER_ID left join ACTIVITY q on p.ACTIVITY_FILTER_ID = q.ACTIVITY_FILTER_ID\n" +
                    "left join CAMPAIGN s on q.CAMPAIGN_ID = s.CAMPAIGN_ID where s.NAME = '" + campaignName + "' and q.NAME = '" + activityName + "'");

            while(resSet.next()){
                res.add(resSet.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
