package com.aibabel.locationservice.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import com.aibabel.locationservice.R;
import com.aibabel.locationservice.activity.DialogActivity;
import com.aibabel.locationservice.utils.CommonUtils;


/**
 * Created by kris on 16/4/14.
 * 常驻通知帮助类
 */
public class ResidentNotificationHelper {
    public static final String NOTICE_ID_KEY = "NOTICE_ID";
    public static int NOTICE_ID_TYPE_0 = -1;

    public static void sendResidentNotice(Context context, String title, String content,Intent intent) {
        if(NOTICE_ID_TYPE_0>10000){
            NOTICE_ID_TYPE_0 = -1;
        }
        NOTICE_ID_TYPE_0++;
        intent.setClass(context, DialogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("msg",content);
        intent.putExtra("title",title);
        Uri uri = CommonUtils.getSound(context);
        long[] vibrates = {0, 1000, 1000, 1000};
        int requestCode = (int) SystemClock.uptimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_notice);
        builder.setFullScreenIntent(pendingIntent, true);
//        builder.setContentIntent(pendingIntent);
//        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setSound(uri);
        builder.setVibrate(vibrates);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setGroup(String.valueOf(System.currentTimeMillis()));
        builder.setColor(Color.parseColor("#FE5000"));
        builder.setWhen(System.currentTimeMillis());//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
        builder.setContentTitle( "通知" );
        builder.setContentText(content);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
       final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTICE_ID_TYPE_0, notification);

    }

    public static void sendResidentNotice12(Context context, String title, String content,Intent intent) {

        intent.setClass(context, DialogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("msg",content);
        intent.putExtra("title",title);
//        Uri uri = getSound(context);
//        long[] vibrates = {0, 1000, 1000, 1000};
//        int requestCode = (int) SystemClock.uptimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        builder.setAutoCancel(true);
//        builder.setSmallIcon(R.mipmap.ic_notice);
//        builder.setFullScreenIntent(pendingIntent, true);
//        builder.setSound(uri);
//        builder.setVibrate(vibrates);
//        builder.setPriority(NotificationCompat.PRIORITY_MAX);
//        builder.setGroup(String.valueOf(System.currentTimeMillis()));
//        builder.setColor(Color.parseColor("#FE5000"));
//        builder.setWhen(System.currentTimeMillis());//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
//        builder.setContentTitle( "通知" );
//        builder.setContentText(content);
//
//        Notification notification = builder.build();
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//        final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(NOTICE_ID_TYPE_0, notification);

    }


//
//    public static int getIconColor() {
//        return Color.parseColor("#00000000");
//
//    }
//
//    private static String getTime() {
//        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.SIMPLIFIED_CHINESE);
//        return format.format(new Date());
//    }


    public static void clearNotification(Context context, int noticeId) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(noticeId);
    }




}
