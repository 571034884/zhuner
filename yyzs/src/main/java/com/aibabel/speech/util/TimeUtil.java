package com.aibabel.speech.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {


    /**
     * 从时间(毫秒)中提取出时间(:分:秒)
     * 时间格式:  时:分
     *
     * @param millisecond
     * @return
     */
    public static String getTimeFromMillisecond(Long millisecond) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Date date = new Date(millisecond);
        String timeStr = simpleDateFormat.format(date);
        return timeStr;
    }
}
