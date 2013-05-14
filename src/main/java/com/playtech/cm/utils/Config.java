package com.playtech.cm.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 User: Denis Veselovskiy
 * Date: 02.05.12 12:28
 */
public class Config {
    @ConfigParam("login.url")
    public static String loginUrl;
    
    @ConfigParam("login")
    public static String login;
    
    @ConfigParam("password")
    public static String password;

    @ConfigParam("timeout.short.ms")
    public static long shortTimeoutMS;

    @ConfigParam("timeout.long.s")
    public static long longTimeoutS;

    @ConfigParam("pullup.interval.ms")
    public static long pullUpIntervalMS;

    @ConfigParam("CMDashboard.url")
    public static String cmDashboardUrl;

    @ConfigParam("isDebug")
    public static Boolean isDebug;

    private static String cname;
    private static String date;




    public static String getNow() {
        cname = "" + System.currentTimeMillis();
        return cname;
    }

    public static String getDate() {
        Format formatter;
        Date data = new Date();
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        date = formatter.format(data);
        return date;
    }

    public static String getFloatingFutureDate(Integer nextDays, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, nextDays);
        Date date=cal.getTime();
        return dateFormat.format(date);
    }

    public static String getWorkDir(){
        java.io.File file = new java.io.File("");//Dummy file
        String  absPath=file.getAbsolutePath();
        return absPath;
    }

    public static String getExportFilesDownloadPath(){
        String downloadFolder = "\\target\\exportedCampaigns";
        return getWorkDir()+downloadFolder;
    }

    public static void killAllFirefoxApplications(){
        try{
            Process p = Runtime.getRuntime().exec("tasklist");
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("firefox.exe")) {
                    Runtime.getRuntime().exec("taskkill /F /IM firefox.exe");
                    Thread.sleep(250);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
