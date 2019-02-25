package com.example.root.testhuaping.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;




public class MyService extends Service {
    public MyService() {
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // Log.e("====================","com.example.root.testhuaping.service.MyService");
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    Intent intent=new Intent();
                    intent.setAction("com.example.root.testhuaping.service.MyService");
                    sendBroadcast(intent);
                    try {
                        Thread.sleep(1000*6*2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }



                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);


    }



    private boolean tag=false;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("====================","com.example.root.testhuaping.service.MyService");



    }




    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        tag = true;
        Log.v("MyService", "on destroy");
    }




}
