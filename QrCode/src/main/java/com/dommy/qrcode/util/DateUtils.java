package com.dommy.qrcode.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by root on 18-7-18.
 */

public class DateUtils {

    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }


    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }



    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDateOneBigger(String str1, String str2,String str3) {
        boolean isBigger = false;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");


        Date dt1 = null;
        Date dt2 = null;
        Date dt3 = null;
        try {
            dt1 = sdf1.parse(str1);
            dt2 = sdf1.parse(str2);
            dt3 = sdf1.parse(str3);
            Log.e("wzf","dt1="+dt1);
            Log.e("wzf","dt2="+dt2);
            Log.e("wzf","dt3="+dt3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime() && dt1.getTime() < dt3.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime() || dt1.getTime() > dt3.getTime()) {
            isBigger = false;
        }else if (dt1.getTime() > dt3.getTime()){
            isBigger = false;
        }
        return isBigger;
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDate2Bigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = false;
        } else if (dt1.getTime() <= dt2.getTime()) {
            isBigger = true;
        }
        return isBigger;
    }
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }
    public static String strToDateFormat(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setLenient(false);
        Date newDate= formatter.parse(date);
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(newDate);
    }
}
