package com.example.root.testhuaping;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by root on 18-6-8.
 */

public class Service1 extends Service {
    private String TAG = "service";

    public String startTime,endTime,date;
    public SimpleDateFormat sdf;
        @Override
    public IBinder onBind(Intent intent)
    {
        // TODO Auto-generated method stub
        return null;
        }

        //当启动Service的时候会调用这个方法
        @Override
        public void onCreate()
        {
        Log.i(TAG, "onCreate");
        super.onCreate();
        }

        //当系统被销毁的时候会调用这个方法
        @Override
        public void onDestroy()
        {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        }

        //当启动Service的时候会调用这个方法
        @Override
        public int onStartCommand(Intent intent, int flags, int startId)
        {
            mHandler.postDelayed(r, 1000);
            Log.i(TAG, "onStartCommand");
            return super.onStartCommand(intent, flags, startId);

        }

    Handler mHandler = new Handler();
    Runnable r = new Runnable() {

        @Override
        public void run() {
            //do something
            //每隔1s循环执行run方法
            sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            IsToday();
            // 注册receiver
            mHandler.postDelayed(this, 1000*60*10);
        }
    };
    public  void IsToday(){
        //String date ="20180620";
        date = sdf.format(new java.util.Date().getTime());
        Log.e("wzf>>>>>>>>>>>>","date="+date);
        try {
            if(DateUtils.isDateOneBigger(date,startTime,endTime)){
                Log.e(">>>>>>>>>>>>>>>>>","date="+date);
                Log.e(">>>>>>>>>>>>>>>>>","startTime="+startTime);
                Log.e(">>>>>>>>>>>>>>>>>","endTime="+endTime);
                Toast.makeText(this, "可以用", Toast.LENGTH_SHORT).show();
            }else{
                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.dommy.qrcode");
                startActivity(LaunchIntent);
                Toast.makeText(this, "未在租赁使用期内", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){

            Log.e(">>>>>>>>>>>>>>>>>",e.getMessage());
        }

    }
}
