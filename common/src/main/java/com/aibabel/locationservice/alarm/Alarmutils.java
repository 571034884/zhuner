package com.aibabel.locationservice.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aibabel.locationservice.receiver.AlarmclockReceive;
import com.aibabel.locationservice.utils.Constants;

import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

/**
 * ==========================================================================================
 *
 * @Author：CreateBy 张文颖
 * @Date：2018/7/11
 * @Desc：闹钟工具类 ==========================================================================================
 */
public class Alarmutils {


    private static final int INTERVAL = 1000 * 3600 * 24;// 24h


    /**
     * 设置闹钟
     *
     * @param context
     */
    public static void startAlarm(Context context, List<Calendar> instances) {
        AlarmManager alarmService = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(Constants.ACTION_ALARM);
        alarmIntent.putExtra("msg",Constants.ACTION_ALARM);

        for (int i = 0; i < instances.size(); i++) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, i + 1, alarmIntent, 0);
            alarmService.cancel(pendingIntent);
            alarmService.setRepeating(AlarmManager.RTC_WAKEUP, instances.get(i).getTimeInMillis(), INTERVAL, pendingIntent);
        }


    }


    /**
     * 设置闹钟
     *
     * @param context
     */
    public static void setAlarm(Context context, List<Calendar> instances) {
        AlarmManager alarmService = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(Constants.ACTION_ALARM);
        for (int i = 0; i < instances.size(); i++) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, i + 1, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmService.cancel(pendingIntent);
            alarmService.setRepeating(AlarmManager.RTC_WAKEUP, instances.get(i).getTimeInMillis(), INTERVAL, pendingIntent);
        }


    }

    /**
     * 设置闹钟
     *
     * @param context
     */
    public static void setAlarm(Context context, int hour, int minute, int id) {
        AlarmManager alarmService = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hour, minute, 10);
        Intent alarmIntent = new Intent(Constants.ACTION_ALARM);
        alarmIntent.putExtra("id", id);
        Log.e("Alarmutils","11111111111111111");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, alarmIntent, 0);
        alarmService.cancel(pendingIntent);
        alarmService.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), INTERVAL, pendingIntent);


    }


    /**
     * 取消闹钟
     *
     * @param context
     */
    public static void cancelAlarm(Context context) {
        AlarmManager manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent i = new Intent(context, AlarmclockReceive.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        manager.cancel(pi);
    }


}
