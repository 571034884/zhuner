package com.aibabel.map.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by fytworks on 2018/12/15.
 */

public class TimerDataUtil {


    public static String getStartMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String result = format.format(today);
        Log.e(null, result);
        return result;
    }

    public static String getStartTimer(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        String ssMin = getHoursMinAddZero(min);
        String ssHour = getHoursMinAddZero(hour);
        return ssHour+":"+ssMin;
    }



    //----------------------------以下获取日期、小时、分钟方法---------------------------------------
    public static List<String> getMonthValues(int index){
        List<String> month = new ArrayList<String>();
        for (int i = 0; i <index; i++) {
            month.add(getFetureDates(i));
        }
        return month;

    }

    public static String getFetureDates(int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + index);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String result = format.format(today);
        Log.e(null, result);
        return result;
    }


    /**
     * 获取日期
     * @param index 当前之后多少天的角标
     * @return
     */
    public static List<String> getMonth(int index){
        List<String> month = new ArrayList<String>();
        for (int i = 0; i <index; i++) {
            if (i == 0){
                month.add("今天");
            }else{
                month.add(getFetureDate(i));
            }
        }
        return month;

    }
    /**
     * 获取未来 第 past 天的日期
     * @param index
     * @return
     */
    public static String getFetureDate(int index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + index);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        String result = format.format(today);
        Log.e(null, result);
        return result;
    }


    /**
     * 获取小时
     * @return
     */
    public static List<String> getHours(){
        List<String> hours = new ArrayList<String>();
        for (int i = 0;i<=23;i++){
            String ssHour = getHoursMinAddZero(i);
            hours.add(ssHour+"");
        }
        return hours;

    }

    /**
     * 获取分钟
     * @return
     */
    public static List<String> getMin(){
        List<String> min = new ArrayList<String>();
        for (int i = 0;i < 60 ;i++){
            min.add(getHoursMinAddZero(i));
        }
        return min;

    }



    //----------------------------以下获取当前时间---------------------------------------

    /**
     * 获取当前是几点
     * @return
     */
    public static String getCurrentHours(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);//系统当前时间
        String ssHour = getHoursMinAddZero(hour);
        return ssHour+"";
    }

    /**
     * 获取当前分钟
     * @return
     */
    public static String getCurrentMin(){
        Calendar calendar = Calendar.getInstance();
        int min = calendar.get(Calendar.MINUTE);//系统当前时间
        String ss = getHoursMinAddZero(min);
        return ss+"";
    }

    //------------------------------以下传递数组进去  和当前时间  返回角标-----------------------------------------




    /**
     * 获取角标
     * @param monthList 日期集合
     * @param index 当前日期
     * @return  角标
     */
    public static int getIndexMonth(List<String> monthList,String index){
        for (int i = 0 ; i < monthList.size(); i++){
            if (index.equals(monthList.get(i))){
                return i;
            }
        }
        return 0;
    }

    /**
     * 获取角标
     * @param hoursList 小时集合
     * @param index 当前小时
     * @return  角标
     */
    public static int getIndexHours(List<String> hoursList,String index){
        for (int i = 0 ; i < hoursList.size(); i++){
            if (index.equals(hoursList.get(i))){
                return i;
            }
        }
        return 0;
    }

    /**
     * 获取角标
     * @param minList 分钟集合
     * @param index 当前分钟
     * @return  角标
     */
    public static int getIndexMin(List<String> minList,String index){
        for (int i = 0 ; i < minList.size(); i++){
            if (index.equals(minList.get(i))){
                return i;
            }
        }
        return 0;
    }

    //------------------------------判断之前的小时分钟  当前的小时分钟 之后的小时分钟  --------------------------------------------------

    /**
     * 获取滑动小时的角标
     * @param hoursList 当前小时集合
     * @param userMonth 用户选择日期
     * @param userHours 用户选择小时
     * @return  返回角标
     */
    public static int getIndexHoursType(List<String> hoursList,String userMonth,String userHours){
        //首先获取当前小时
        int currentHours = Integer.parseInt(getCurrentHours());
        int userCurrentHours = Integer.parseInt(userHours);
        if (userMonth.equals("今天")){
            if (userCurrentHours >= currentHours){
                for (int i = 0;i<hoursList.size();i++){
                    if (userHours.equals(hoursList.get(i))){
                        return i;
                    }
                }
            }else{
                String ss = getHoursMinAddZero(currentHours);
                for (int i = 0;i<hoursList.size();i++){
                    if (ss.equals(hoursList.get(i))){
                        return i;
                    }
                }
            }
        }else{
            for (int i = 0 ; i < hoursList.size();i++){
                if (userHours.equals(hoursList.get(i))){
                    return i;
                }
            }
        }
        return 0;
    }

    /**
     * 加零
     * @param ss
     * @return
     */
    public static String getHoursMinAddZero(int ss){
        String s = "";
        switch (ss){
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                s = "0"+ss;
                break;
            default:
                s = ss+"";
                break;
        }
        return s;
    }



    /**
     * 获取滑动分钟的角标
     * @param minList   分钟集合
     * @param userMin   当前用户选择的分钟
     * @return  角标
     */
    public static int getIndexMinType(List<String> minList,String userMin,String userMonth){
        //首先获取当前分钟
        int currentMin = Integer.parseInt(getCurrentMin());
        //用户选择的分钟
        int userCurrentMin = Integer.parseInt(userMin);

        if (userMonth.equals("今天")) {
            if (userCurrentMin >= currentMin) {
                for (int i = 0; i < minList.size(); i++) {
                    if (userMin.equals(minList.get(i))) {
                        return i;
                    }
                }
            } else {
                String ss = getHoursMinAddZero(currentMin);
                for (int i = 0; i < minList.size(); i++) {
                    if (ss.equals(minList.get(i))) {
                        return i;
                    }
                }
            }
        }else{
            for (int i = 0 ; i < minList.size();i++){
                if (userMin.equals(minList.get(i))){
                    return i;
                }
            }
        }
        return 0;
    }

    //------------------------------判断点击确定的逻辑   分钟  小时------------------------------------------

    /**
     * 返回小时角标
     * @param hoursList 小时集合
     * @param hoursConfirm  用户选择小时
     * @return  角标
     */
    public static int getHoursConFirm(List<String> hoursList,String hoursConfirm){
        //首先获取当前小时
        int currentHours = Integer.parseInt(getCurrentHours());
        int userCurrentHours = Integer.parseInt(hoursConfirm);
        if (userCurrentHours >= currentHours){
            //返回用户当前小时角标
            for (int i = 0;i<hoursList.size();i++){
                if (hoursConfirm.equals(hoursList.get(i))){
                    return i;
                }
            }
        }else{
            //返回当前小时角标
            String ss = getHoursMinAddZero(currentHours);
            for (int i = 0;i<hoursList.size();i++){
                if (ss.equals(hoursList.get(i))){
                    return i;
                }
            }
        }
        return 0;
    }

    /**
     * 返回分钟角标
     * @param minList 分钟集合
     * @param minConfirm  用户选择分钟
     * @return  角标
     */
    public static int getMinConFirm(List<String> minList,String minConfirm){
        //首先获取当前分钟
        int currentMin = Integer.parseInt(getCurrentMin());
        //用户选择的分钟
        int userCurrentMin = Integer.parseInt(minConfirm);

        if (userCurrentMin >= currentMin){
            for (int i = 0;i<minList.size();i++){
                if (minConfirm.equals(minList.get(i))){
                    return i;
                }
            }
        }else{
            String ss = getHoursMinAddZero(currentMin);
            for (int i = 0;i<minList.size();i++){
                if (ss.equals(minList.get(i))){
                    return i;
                }
            }
        }
        return 0;
    }

}
