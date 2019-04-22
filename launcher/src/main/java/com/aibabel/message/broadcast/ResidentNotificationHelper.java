package com.aibabel.message.broadcast;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;


import com.aibabel.menu.R;
import com.aibabel.message.receiver.NotificationClickReceiver;

import java.util.List;


/**
 * Created by kris on 16/4/14.
 * 常驻通知帮助类
 */
public class ResidentNotificationHelper {
    public static final String NOTICE_ID_KEY = "NOTICE_ID";
    public static int NOTICE_ID_TYPE_0 = -1;

    public static final String intentjson = "json";
    public static final String intenttitle = "title";


    public static void sendResidentNotice(Context context, String title, String content, Intent intent) {
        if (NOTICE_ID_TYPE_0 > 10000) {
            NOTICE_ID_TYPE_0 = -1;
        }
        NOTICE_ID_TYPE_0++;

//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Uri uri = getSound(context);
        long[] vibrates = {0, 1000, 1000, 1000};
//        intent.setClass(context, InformationActivity.class);
        intent.putExtra(NOTICE_ID_KEY, NOTICE_ID_TYPE_0);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, NotificationClickReceiver.class);
        intent.putExtra(intenttitle, title);
        int requestCode = (int) SystemClock.uptimeMillis();
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//        Intent intent_click =new Intent (context,com.aibabel.messagemanage.MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Intent intentClick = new Intent(context, NotificationClickReceiver.class);
        intentClick.setAction("notification_clicked");
        intentClick.putExtra(NotificationClickReceiver.TYPE, NOTICE_ID_TYPE_0);
        intentClick.putExtra("MESSAGE", "消息");
        intentClick.putExtra(intenttitle, title);
        intentClick.putExtra(intentjson, intent.getStringExtra(intentjson));

        PendingIntent pendingIntentClick = PendingIntent.getBroadcast(context, 0, intentClick, PendingIntent.FLAG_ONE_SHOT);
        //cancle广播监听
        Intent intentCancel = new Intent(context, NotificationClickReceiver.class);
        intentCancel.setAction("notification_cancelled");
        intentCancel.putExtra(NotificationClickReceiver.TYPE, NOTICE_ID_TYPE_0);
        intentCancel.putExtra(intenttitle, title);
        intentCancel.putExtra(intentjson, intent.getStringExtra(intentjson));
        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(context, 0, intentCancel, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_notice);
        builder.setFullScreenIntent(pendingIntent, true);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntentClick);
        builder.setDeleteIntent(pendingIntentCancel);
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
//        builder.setContentTitle("您有新通知啦！");
//        builder.setContentText("准儿为您推荐附近好玩的地方，快点击查看吧！");
//        builder.setContentText("准儿发现附近有免费的景区讲解，快去看看吧！");
        builder.setContentTitle(""+title);
        builder.setContentText(""+content);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTICE_ID_TYPE_0, notification);

    }


    public static void clearNotification(Context context, int noticeId) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(noticeId);
    }


    public static Uri getSound(Context context) {
        Uri uri = null;
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.sound);

    }


    /**
     * 调起应用  没有启动直接启动   启动过直接调起
     *
     * @param context
     * @param packageName
     * @return
     */
    public static Intent getAppOpenIntentByPackageName(Context context, String packageName) {
        String mainAct = null;
        PackageManager pkgMag = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);

        @SuppressLint("WrongConstant")
        List<ResolveInfo> list = pkgMag.queryIntentActivities(intent, PackageManager.GET_ACTIVITIES);
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo info = list.get(i);
            if (info.activityInfo.packageName.equals(packageName)) {
//                mainAct = info.activityInfo.name;
                mainAct = "com.aibabel.travel.activity.SpotDetailActivity";
                break;
            }
        }
        if (TextUtils.isEmpty(mainAct)) {
            return null;
        }
        intent.setComponent(new ComponentName(packageName, mainAct));
        return intent;
    }


}
