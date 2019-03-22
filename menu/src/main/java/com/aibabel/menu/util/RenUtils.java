package com.aibabel.menu.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.aibabel.baselibrary.utils.SharePrefUtil;
import com.aibabel.menu.R;
import com.aibabel.menu.service.MyService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 包含租赁逻辑的 工具类
 */
public class RenUtils {

    //泽峰
    private final Uri CONTENT_URI = Uri.parse("content://com.dommy.qrcode/aibabel_information");
    public MyReceiver receiver;
    public Receiver_yanqi msgReceiver;
    public String delay_wzf;
    public String startTime, endTime, date, ffdTime;
    public SimpleDateFormat sdf;
    public String time_day = null;


    private Activity mActivity;

    public RenUtils(Activity activity) {
        mActivity = activity;
    }


    public void startZl() {
        try {
            if (isServiceWork(mActivity, "com.aibabel.locationservice.service.LocationService")) {
                //Toast.makeText(MainActivity.this, "已启动", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.setPackage("com.aibabel.locationservice");
                intent.setAction("android.intent.action.START_B_SERVICE");
                mActivity.startService(intent);
                Log.d("weiqidong", "yiqidong");
                //Toast.makeText(MainActivity.this, "未启动", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
        }


        String tt = getDeviceInfo().substring(9, 10);
        if (TextUtils.equals("L", tt)) {
            Log.e("wzf_device", "tt=" + tt);
            mActivity.startService(new Intent(mActivity, MyService.class));
            //注册广播接收器
            receiver = new MyReceiver();
            IntentFilter filter1 = new IntentFilter();
            filter1.addAction("com.example.root.testhuaping.service.MyService");
            mActivity.registerReceiver(receiver, filter1);


            msgReceiver = new RenUtils.Receiver_yanqi();
            IntentFilter filter2 = new IntentFilter();
            filter2.addAction("com.android.zhuner.time");
            mActivity.registerReceiver(msgReceiver, filter2);
        } else {
        }

    }


    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }


    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDateOneBigger(String str1, String str2, String str3) {
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
            Log.e("wzf", "dt1=" + dt1);
            Log.e("wzf", "dt2=" + dt2);
            Log.e("wzf", "dt3=" + dt3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime() && dt1.getTime() < dt3.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime() || dt1.getTime() > dt3.getTime()) {
            isBigger = false;
        } else if (dt1.getTime() > dt3.getTime()) {
            isBigger = false;
        }
        return isBigger;
    }

    /**
     * 判断是否到期
     */
    public void IsToday() {

        //Log.e("wzf>>>>>>>>>>>>","服务已启动");
        //String date ="20180620";
        sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        date = sdf.format(new Date().getTime());
        L.e("wzf>>>>>>>>>>>>", "date=" + date);
        try {
            Cursor cursor = mActivity.getContentResolver().query(CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {

                    //Log.e("wzf","11111111111111111111111");
                    ffdTime = cursor.getString(cursor.getColumnIndex("fft"));
                    startTime = cursor.getString(cursor.getColumnIndex("start_time"));
                    //Log.e("wzf","ffdTime="+ffdTime);
                    endTime = cursor.getString(cursor.getColumnIndex("end_time"));
                    //Log.e("wzf","endTime="+endTime);
                    //Toast.makeText(MainmActivity.this, "startTime="+startTime, Toast.LENGTH_SHORT).show();
                    //Log.e("wzf","4444444444444444444444444444");
                    //Toast.makeText(MainmActivity.this, "endTime="+endTime, Toast.LENGTH_SHORT).show();
                    //Log.e("wzf","end");

                } while (cursor.moveToNext());
            }

            Log.e("wzf", "现" + date);
            Log.e("wzf", "从" + ffdTime);
            Log.e("wzf", "到" + endTime);
            long time_ef = (stringToLong(endTime, "yyyyMMddHHmmss") - stringToLong(ffdTime, "yyyyMMddHHmmss"));
            Log.e("wzf", "time_ef=" + time_ef);
            long time_df = (stringToLong(date, "yyyyMMddHHmmss") - stringToLong(ffdTime, "yyyyMMddHHmmss"));
            long time_ed = (stringToLong(endTime, "yyyyMMddHHmmss") - stringToLong(date, "yyyyMMddHHmmss") - time_ef);
            Log.e("wzf", "time_df=" + time_df);
            Log.e("wzf", "time_ed=" + time_ed);
            if (time_df > time_ef || time_ed > time_ef) {


                Intent LaunchIntent = mActivity.getPackageManager().getLaunchIntentForPackage("com.dommy.qrcode");
                LaunchIntent.putExtra("erweima", "daoqi");
                mActivity.startActivity(LaunchIntent);

                Intent intent = new Intent();
                intent.setAction("com.android.zhuner");
                intent.putExtra("Zhuner_devices", "time_end");
                mActivity.sendBroadcast(intent);
                Toast.makeText(mActivity, R.string.my_weizaizulinqi, Toast.LENGTH_SHORT).show();
            } else {
            }
        } catch (Exception e) {

            // Log.e("wzfff",e.getMessage());
        }

    }

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

    //到期广播
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            //IsToday();

        }
    }

    //延期广播
    public class Receiver_yanqi extends BroadcastReceiver {
        long t;

        @Override
        public void onReceive(Context context, Intent intent) {
            try {

                time_day = intent.getExtras().getString("Zhuner_Time");
                    /*t = stringToLong(endTime, "yyyyMMddHHmmss");
                    endTime = getDateDelay(t, time_day);*/
                Log.e("wzf", "延期到=" + time_day);
                SharePrefUtil.saveString(mActivity, "delaytime", time_day);

            } catch (Exception e) {
            }
        }
    }

    private String getDeviceInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("" + Build.DISPLAY);
        return sb.toString();
    }

    public void destroyRes() {
        try {
            mActivity.stopService(new Intent(mActivity, MyService.class));
            if (receiver != null) {
                mActivity.unregisterReceiver(receiver);
            }
            if (msgReceiver != null) {
                mActivity.unregisterReceiver(msgReceiver);
            }
        } catch (Exception e) {
        }
        mActivity = null;
    }


}
