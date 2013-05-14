package com.playtech.cm.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 User: Denis Veselovskiy
 * Date: 10/30/12
 * Comment:
 */
public class Utils {
    // 86,400,000 (24×60×60×10×10×10) milliseconds — one day
    //     60,000       (60×10×10×10) milliseconds — one minute
    private static final long dayMillis = 86400000L;
    private static final long minuteMillis = 60000L;

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp getFutureTimestamp(long days) {
        return new Timestamp(System.currentTimeMillis() + days*dayMillis);
    }

    public static Date getFutureDate(long days) {
        return new Date(System.currentTimeMillis() + days*dayMillis);
    }

    public static String getConvertedDateToCommonFormat(String date){
        Date actualDate;
        String convertedDate = "";
        try {
            actualDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy HH:mm");
            convertedDate = dateFormatter.format(actualDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertedDate;
    }
}
