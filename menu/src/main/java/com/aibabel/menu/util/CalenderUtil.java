package com.aibabel.menu.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalenderUtil {
    /**
     *使用Calendar对象计算时间差，可以按照需求定制自己的计算逻辑
     * @param strDate
     * @throws ParseException
     */
    public static void calculateTimeDifferenceByCalendar(String strDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = formatter.parse(strDate);

        Calendar c1 = Calendar.getInstance();   //当前日期
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date);   //设置为另一个时间

        int year = c1.get(Calendar.DATE);
        int oldYear = c2.get(Calendar.DATE);

        //这里只是简单的对两个年份数字进行相减，而没有考虑月份的情况
        System.out.println("传入的日期与今年的年份差为：" + (year - oldYear));
    }
    /**
     * 使用java 8的Period的对象计算两个LocalDate对象的时间差，严格按照年、月、日计算，如：2018-03-12 与 2014-05-23 相差 3 年 9 个月 17 天
     * @param year
     * @param month
     * @param dayOfMonth
     */

    @TargetApi(Build.VERSION_CODES.O)
    public static void calculateTimeDifferenceByPeriod(int year, Month month, int dayOfMonth) {
        LocalDate today = LocalDate.now();
        System.out.println("Today：" + today);
        LocalDate oldDate = LocalDate.of(year, month, dayOfMonth);
        System.out.println("OldDate：" + oldDate);

        Period p = Period.between(oldDate, today);
        System.out.printf("目标日期距离今天的时间差：%d 年 %d 个月 %d 天\n", p.getYears(), p.getMonths(), p.getDays());
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static String instant2Str(Instant instant) {
        try {
            SimpleDateFormat   sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
           String date = sdf.format(new Date(instant.toEpochMilli()));
            return date;
        } catch (ArithmeticException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public static String[] calculateTimeDifferenceByDuration() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, 2);//增加一天
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
        Date time = calendar.getTime();
        String format = df.format(time);

        calendar.add(Calendar.DATE, 90);//增加一天
        Date time90 = calendar.getTime();
        String format2 = df.format(time90);

        String[] rst = new String[2];
        rst[0] = (format);
        rst[1] = (format2);
        System.out.println(format);
        System.out.println(format2);

        return  rst;
    }
    public static String getyyyyMMddHHmmss() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        String format = df.format(time);
        return format;
    }


    public static String calculateTimeDifferenceadd90(String strtime) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Date fromDate1 = df.parse(strtime);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fromDate1);
            calendar.add(Calendar.DATE, 91);//增加一天
            Date time90 = calendar.getTime();
            String format2 = df.format(time90);

            System.out.println(format2);

            return format2;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  "";
    }

//    public static String[] calculateTimeDifferenceByDuration() {
//        Instant inst1 = Instant.now();  //当前的时间
//        System.out.println("Inst1：" + instant2Str(inst1));
//        Instant inst3 = inst1.plus(Duration.ofDays(90));
//        System.out.println("inst3：" + instant2Str(inst3));
//        String[] rst = new String[2];
//        rst[0] = instant2Str(inst1);
//        rst[1] = instant2Str(inst3);
//        System.out.println("以天的时间差：" + Duration.between(inst1, inst3).toDays());
//        return  rst;
//    }
    /**
     * 使用java 8的ChronoUnit，ChronoUnit可以以多种单位（基本涵盖了所有的，看源码发现竟然还有“FOREVER”这种单位。。）表示两个时间的时间差
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void calculateTimeDifferenceByChronoUnit() {
        LocalDate startDate = LocalDate.of(2003, Month.MAY, 9);
        System.out.println("开始时间：" + startDate);

        LocalDate endDate = LocalDate.of(2015, Month.JANUARY, 26);
        System.out.println("结束时间：" + endDate);

        long daysDiff = ChronoUnit.DAYS.between(startDate, endDate);
        System.out.println("两个时间之间的天数差为：" + daysDiff);
//  long hoursDiff = ChronoUnit.HOURS.between(startDate, endDate);  //这句会抛出异常，因为LocalDate表示的时间中不包含时分秒等信息
    }
    /**
     * 用SimpleDateFormat计算时间差
     * @throws ParseException
     */
    public static void calculateTimeDifferenceBySimpleDateFormat() throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        /*天数差*/
        Date fromDate1 = simpleFormat.parse("20180301120000");
        Date toDate1 = simpleFormat.parse("20180312120000");
        long from1 = fromDate1.getTime();
        long to1 = toDate1.getTime();
        int days = (int) ((to1 - from1) / (1000 * 60 * 60 * 24));
        System.out.println("两个时间之间的天数差为：" + days);

        /*小时差*/
        Date fromDate2 = simpleFormat.parse("20180301120000");
        Date toDate2 = simpleFormat.parse("20180312120000");
        long from2 = fromDate2.getTime();
        long to2 = toDate2.getTime();
        int hours = (int) ((to2 - from2) / (1000 * 60 * 60));
        System.out.println("两个时间之间的小时差为：" + hours);

        /*分钟差*/
        Date fromDate3 = simpleFormat.parse("20180301120000");
        Date toDate3 = simpleFormat.parse("20180312120000");
        long from3 = fromDate3.getTime();
        long to3 = toDate3.getTime();
        int minutes = (int) ((to3 - from3) / (1000 * 60));
        System.out.println("两个时间之间的分钟差为：" + minutes);
    }

    /**
     * 比较两个时间差,endtime - nowdata;
     * @param endtime
     * @return
     */
    public static int compaeTimeWithAfter24(String endtime) throws ParseException {
        if((endtime).equalsIgnoreCase(""))return 0;
        try {
            endtime = calendaradd24(endtime);
            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddhhmmss");
            Date now_data = new Date();
            Date toDate2 = simpleFormat.parse(endtime);
            long from2 = now_data.getTime();
            long to2 = toDate2.getTime();
            int hours = (int) ((to2 - from2) / (1000 * 60 * 60));
            System.out.println("两个时间之间的小时差为：" + hours);
            return hours;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }


    /**
     * 比较两个时间差,endtime - nowdata;
     * @param endtime
     * @return
     */
    public static int compaeTimeWithNow(String endtime) throws ParseException {
        try {
            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddhhmmss");
            Date now_data = new Date();
            Date toDate2 = simpleFormat.parse(endtime);
            long from2 = now_data.getTime();
            long to2 = toDate2.getTime();
            int hours = (int) ((to2 - from2) / (1000 * 60 * 60));
            System.out.println("两个时间之间的小时差为：" + hours);
            return hours;
        }catch (Exception e){

        }
        return 0;
    }
    /**
     * 比较两个时间差  endtime - starttime
     * @param endtime - starttime
     * @return
     */
    public static int compae2Time(String starttime,String endtime) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddhhmmss");


        Date fromDate2 = simpleFormat.parse(starttime);
        Date toDate2 = simpleFormat.parse(endtime);
        long from2 = fromDate2.getTime();
        long to2 = toDate2.getTime();
        int hours = (int) ((to2 - from2) / (1000 * 60 * 60));
        System.out.println("两个时间之间的小时差为：" + hours);
        return hours;
    }

    private int weeks = 0;// 用来全局控制 上一周，本周，下一周的周数变化
    private int MaxDate; // 一月最大天数
    private int MaxYear; // 一年最大天数

    /**
     * 获得当前年份
     *
     * @return
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获得当前月份
     *
     * @return
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 获得今天在本年的第几天
     *
     * @return
     */
    public static int getDayOfYear() {
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获得今天在本月的第几天(获得当前日)
     *
     * @return
     */
    public static int getDayOfMonth() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得今天在本周的第几天
     *
     * @return
     */
    public static int getDayOfWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获得今天是这个月的第几周
     *
     * @return
     */
    public static int getWeekOfMonth() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    /**
     * 获得半年后的日期
     *
     * @return
     */
    public static Date getTimeYearNext() {
        Calendar.getInstance().add(Calendar.DAY_OF_YEAR, 183);
        return Calendar.getInstance().getTime();
    }

    /**
     * 将日期转换成字符串
     *
     * @param dateTime
     * @return
     */
    public static String convertDateToString(Date dateTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(dateTime);
    }

    /**
     * 得到二个日期间的间隔天数
     *
     * @param sj1
     * @param sj2
     * @return
     */
    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            java.util.Date date = myFormatter.parse(sj1);
            java.util.Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param sdate
     * @return
     */
    public static String getWeek(String sdate) {
        // 再转换为时间
        Date date = CalenderUtil.strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 两个时间之间的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        java.util.Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return day;
    }

    /**
     * 计算当月最后一天,返回字符串
     *
     * @return
     */
    public String getDefaultDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 上月第一天
     *
     * @return
     */
    public String getPreviousMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
        // lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获取当月第一天
     *
     * @return
     */
    public String getFirstDayOfMonth() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得本周星期日的日期
     *
     * @return
     */
    public String getCurrentWeekday() {
        weeks = 0;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * 获取当天时间
     *
     * @param dateformat
     * @return
     */
    public String getNowTime(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        return hehe;
    }

    /**
     * 获得当前日期与本周日相差的天数
     *
     * @return
     */
    private int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    /**
     * 获得本周一的日期
     *
     * @return
     */
    public String getMondayOFWeek() {
        weeks = 0;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * 获得相应周的周六的日期
     *
     * @return
     */
    public String getSaturday() {
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * 获得上周星期日的日期
     *
     * @return
     */
    public String getPreviousWeekSunday() {
        weeks = 0;
        weeks--;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * 获得上周星期一的日期
     *
     * @return
     */
    public String getPreviousWeekday() {
        weeks--;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * 获得下周星期一的日期
     */
    public String getNextMonday() {
        weeks++;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * 获得下周星期日的日期
     */
    public String getNextSunday() {

        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    private int getMonthPlus() {
        Calendar cd = Calendar.getInstance();
        int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
        cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        MaxDate = cd.get(Calendar.DATE);
        if (monthOfNumber == 1) {
            return -MaxDate;
        } else {
            return 1 - monthOfNumber;
        }
    }

    /**
     * 获得上月最后一天的日期
     *
     * @return
     */
    public String getPreviousMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, -1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得下个月第一天的日期
     *
     * @return
     */
    public String getNextMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得下个月最后一天的日期
     *
     * @return
     */
    public String getNextMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 加一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得明年最后一天的日期
     *
     * @return
     */
    public String getNextYearEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);// 加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        lastDate.roll(Calendar.DAY_OF_YEAR, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得明年第一天的日期
     *
     * @return
     */
    public String getNextYearFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);// 加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        str = sdf.format(lastDate.getTime());
        return str;

    }

    /**
     * 获得本年有多少天
     *
     * @return
     */
    private int getMaxYear() {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        return MaxYear;
    }

    private int getYearPlus() {
        Calendar cd = Calendar.getInstance();
        int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
        cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        if (yearOfNumber == 1) {
            return -MaxYear;
        } else {
            return 1 - yearOfNumber;
        }
    }

    /**
     * 获得本年第一天的日期
     *
     * @return
     */
    public String getCurrentYearFirst() {
        int yearPlus = this.getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus);
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        return preYearDay;
    }

    // 获得本年最后一天的日期 *
    public String getCurrentYearEnd() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        return years + "-12-31";
    }

    // 获得上年第一天的日期 *
    public String getPreviousYearFirst() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);
        years_value--;
        return years_value + "-1-1";
    }

    // 获得上年最后一天的日期
    public String getPreviousYearEnd() {
        weeks--;
        int yearPlus = this.getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks
                + (MaxYear - 1));
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        return preYearDay;
    }

    /**
     * 获得本季度第一天
     *
     * @param month
     * @return
     */
    public String getThisSeasonFirstTime(int month) {
        int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int start_month = array[season - 1][0];
        int end_month = array[season - 1][2];

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);

        int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
        int end_days = getLastDayOfMonth(years_value, end_month);
        String seasonDate = years_value + "-" + start_month + "-" + start_days;
        return seasonDate;

    }

    /**
     * 获得本季度最后一天
     *
     * @param month
     * @return
     */
    public String getThisSeasonFinallyTime(int month) {
        int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int start_month = array[season - 1][0];
        int end_month = array[season - 1][2];

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);

        int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
        int end_days = getLastDayOfMonth(years_value, end_month);
        String seasonDate = years_value + "-" + end_month + "-" + end_days;
        return seasonDate;

    }

    /**
     * 获取某年某月的最后一天
     *
     * @param year
     *            年
     * @param month
     *            月
     * @return 最后一天
     */
    private int getLastDayOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }

    /**
     * 是否闰年
     *
     * @param year
     *            年
     * @return
     */
    public boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 是否闰年
     *
     * @param year
     * @return
     */
    public boolean isLeapYear2(int year) {
        return new GregorianCalendar().isLeapYear(year);
    }

//    public static void main(String args[]){
////        try {
////            calculateTimeDifferenceBySimpleDateFormat();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//       String[] sss =  calculateTimeDifferenceByDuration();
//        System.out.println("start"+sss[0]);
//        System.out.println(end);
//
//        Long l1 = System.currentTimeMillis();
//        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddhhmmss");
//        System.out.println("simple = "+simpleFormat.format(l1));
//
//        try {
//            System.out.println(compaeTimeWithNow("20190424155000"));
//            compae2Time("20190224155000","20190225155000");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//    }


    public static String  calendaradd24(String endtime) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date =df.parse(endtime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 2);//增加一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date time = calendar.getTime();

        String format = df.format(time);
       return  (format);
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
//            calculateTimeDifferenceByDuration();
            System.out.print(calculateTimeDifferenceadd90("20190112102745"));
            System.out.println(compaeTimeWithAfter24("20190415202745"));
            System.out.println(compae2Time("20190112102745","20190415202745")>24*30);
        } catch (Exception e) {
            e.printStackTrace();
        }


//        CalenderUtil tt = new CalenderUtil();
//        System.out.println("获取当天日期:" + tt.getNowTime("yyyy-MM-dd"));
//        System.out.println("获取本周一日期:" + tt.getMondayOFWeek());
//        System.out.println("获取本周日的日期~:" + tt.getCurrentWeekday());
//        System.out.println("获取上周一日期:" + tt.getPreviousWeekday());
//        System.out.println("获取上周日日期:" + tt.getPreviousWeekSunday());
//        System.out.println("获取下周一日期:" + tt.getNextMonday());
//        System.out.println("获取下周日日期:" + tt.getNextSunday());
//        System.out.println("获得相应周的周六的日期:" + tt.getNowTime("yyyy-MM-dd"));
//        System.out.println("获取本月第一天日期:" + tt.getFirstDayOfMonth());
//        System.out.println("获取本月最后一天日期:" + tt.getDefaultDay());
//        System.out.println("获取上月第一天日期:" + tt.getPreviousMonthFirst());
//        System.out.println("获取上月最后一天的日期:" + tt.getPreviousMonthEnd());
//        System.out.println("获取下月第一天日期:" + tt.getNextMonthFirst());
//        System.out.println("获取下月最后一天日期:" + tt.getNextMonthEnd());
//        System.out.println("获取本年的第一天日期:" + tt.getCurrentYearFirst());
//        System.out.println("获取本年最后一天日期:" + tt.getCurrentYearEnd());
//        System.out.println("获取去年的第一天日期:" + tt.getPreviousYearFirst());
//        System.out.println("获取去年的最后一天日期:" + tt.getPreviousYearEnd());
//        System.out.println("获取明年第一天日期:" + tt.getNextYearFirst());
//        System.out.println("获取明年最后一天日期:" + tt.getNextYearEnd());
//        System.out.println("获取本季度第一天:" + tt.getThisSeasonFirstTime(11));
//        System.out.println("获取本季度最后一天:" + tt.getThisSeasonFinallyTime(11));
//        System.out.println("获取两个日期之间间隔天数2008-12-1~2008-29:"
//                + CalenderUtil.getTwoDay("2008-12-1", "2008-9-29"));
//        System.out.println("获取当前月的第几周：" + tt.getWeekOfMonth());
//        System.out.println("获取当前年份：" + tt.getYear());
//        System.out.println("获取当前月份：" + tt.getMonth());
//        System.out.println("获取今天在本年的第几天：" + tt.getDayOfYear());
//        System.out.println("获得今天在本月的第几天(获得当前日)：" + tt.getDayOfMonth());
//        System.out.println("获得今天在本周的第几天：" + tt.getDayOfWeek());
//        System.out.println("获得半年后的日期："
//                + tt.convertDateToString(tt.getTimeYearNext()));
    }


}
