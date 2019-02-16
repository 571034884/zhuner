package com.aibabel.travel.broadcastreceiver;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.aibabel.travel.MainActivity;
import com.aibabel.travel.R;
import com.aibabel.travel.activity.InformationActivity;
import com.aibabel.travel.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by kris on 16/4/14.
 * 常驻通知帮助类
 */
public class ResidentNotificationHelper {
    public static final String NOTICE_ID_KEY = "NOTICE_ID";
    public static int NOTICE_ID_TYPE_0 = -1;

//    @TargetApi(16)
//    public static void sendResidentNoticeType0(Context context, String title, String content, @DrawableRes int res) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
////        builder.setOngoing(true);
//        builder.setAutoCancel(true);
//        builder.setPriority(NotificationCompat.PRIORITY_MAX);
//        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.view_notification_type_0);
//        remoteViews.setTextViewText(R.id.title_tv, "您有新通知啦！");
//        remoteViews.setTextViewText(R.id.content_tv, "准儿为您推荐附近好玩的地方，快点击查看吧！");
//        remoteViews.setTextViewText(R.id.time_tv, getTime());
//        remoteViews.setImageViewResource(R.id.icon_iv, R.drawable.ic_notice);
////        remoteViews.setInt(R.id.close_iv, "setColorFilter", getIconColor());
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra(NOTICE_ID_KEY, NOTICE_ID_TYPE_0);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        int requestCode = (int) SystemClock.uptimeMillis();
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
////        remoteViews.setOnClickPendingIntent(R.id.notice_view_type_0, pendingIntent);
////        int requestCode1 = (int) SystemClock.uptimeMillis();
////        Intent intent1 = new Intent(ACTION_CLOSE_NOTICE);
////        intent1.putExtra(NOTICE_ID_KEY, NOTICE_ID_TYPE_0);
////        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, requestCode1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
////        remoteViews.setOnClickPendingIntent(R.id.close_iv, pendingIntent1);
//        builder.setSmallIcon(R.drawable.ic_notice);
//        builder.setFullScreenIntent(pendingIntent, true);
//        builder.setDefaults(Notification.DEFAULT_ALL);
//
//        Notification notification = builder.build();
//
//
//        if (android.os.Build.VERSION.SDK_INT >= 16) {
//            notification = builder.build();
//            notification.bigContentView = remoteViews;
//        }
//
//
//        notification.contentView = remoteViews;
//        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(NOTICE_ID_TYPE_0, notification);
//    }


    public static void sendResidentNotice(Context context, String title, String content, Intent intent) {
//        clearNotification(context,NOTICE_ID_TYPE_0);
        if (NOTICE_ID_TYPE_0 > 10000) {
            NOTICE_ID_TYPE_0 = -1;
        }
        NOTICE_ID_TYPE_0++;
        /**
         * 手机设置的默认提示音
         */


//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Uri uri = getSound(context);
        long[] vibrates = {0, 1000, 1000, 1000};
        intent.setClass(context, InformationActivity.class);
        intent.putExtra(NOTICE_ID_KEY, NOTICE_ID_TYPE_0);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        int requestCode = (int) SystemClock.uptimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_notice);
        builder.setFullScreenIntent(pendingIntent, true);
//        builder.setStyle(Notification.BigTextStyle);
//        builder.setContentIntent(pendingIntent);
//        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setSound(uri);
        builder.setVibrate(vibrates);
//        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.setGroup(String.valueOf(System.currentTimeMillis()));
        builder.setColor(Color.parseColor("#fe5000"));
        builder.setWhen(System.currentTimeMillis());//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
        builder.setContentTitle("您有新通知啦！");
//        builder.setContentText("准儿为您推荐附近好玩的地方，快点击查看吧！");
        builder.setContentText("准儿发现附近有免费的景区讲解，快去看看吧！");
//        NotificationCompat.BigTextStyle mBigTextStyle = new NotificationCompat.BigTextStyle();
//        mBigTextStyle.setBigContentTitle("您有新通知啦");
//        mBigTextStyle.bigText("准儿为您推荐了附近免费的景区导览，块点击查看吧！");
//        builder.setStyle(mBigTextStyle);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTICE_ID_TYPE_0, notification);

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


    public static Uri getSound(Context context) {
        Uri uri = null;
//        AssetManager assetManager = context.getAssets();
//        AssetFileDescriptor fileDescriptor = assetManager.openFd("8855.mp3");
//        fileDescriptor.getFileDescriptor();


        return Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.sound);

    }

}
