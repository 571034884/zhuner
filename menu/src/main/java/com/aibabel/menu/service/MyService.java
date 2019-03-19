package com.aibabel.menu.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.aibabel.menu.MainActivity;
import com.aibabel.menu.util.LogUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/***
 * 租赁主service
 */
public class MyService extends Service {
    /**
     * 轮询机制
     */
    public ScheduledExecutorService scheduledExecutor;
    private final static int LOOPER_CODE = 0x00100;
    public static final int LoopTimeRate = 10;

    private  static WorkerThread worker   = new WorkerThread();
    public MyService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Log.e("====================","com.example.root.testhuaping.service.MyService");
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    Intent intent = new Intent();
                    intent.setAction("com.example.root.testhuaping.service.MyService");
                    sendBroadcast(intent);


                    try {
                        Thread.sleep(1000 * 60 * 10);
                        //Thread.sleep(1000 * 5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(worker!=null){
                        worker.run();
                    }else{
                        worker = new WorkerThread();
                    }
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    private boolean tag = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("====================", "com.example.root.testhuaping.service.MyService");
        Log.e("hjs", "MyServic=onCreate-");
//        startLoopRent();
//        SharePrefUtil.saveString(getApplicationContext(),"","");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLoop();
        tag = true;
        Log.v("MyService", "on destroy");
    }


    private static int startloop=0;
    public static class WorkerThread implements Runnable {
        public WorkerThread() {
            startloop = 0;
        }

        @Override
        public void run() {
            try {

                LogUtil.d(Thread.currentThread().getName() + " Start. Time = ");
                LogUtil.d(" startloop ="+startloop);
                if(startloop>=24) {
                    processCommand();
                    startloop = 0;
                    LogUtil.d("发送handle");
                }
                startloop++;
                LogUtil.d(Thread.currentThread().getName() + " End. Time = ");
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.d("");
            }
        }

        private void processCommand() {
            try {
                if(MainActivity.loopHandler!=null)MainActivity.loopHandler.sendEmptyMessage(130);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /***
     * 这是一个静态,loop轮询机制
     */
   /* private static Handler LooptempHandler = new Handler(msg -> {
        if (msg.what == LOOPER_CODE) {
            LogUtil.d("LOOPER_CODE_start");
        }
        return false;
    });*/

    /**
     * 循环获取轮询
     */
    public void startLoopRent() {

        LogUtil.d("startLoopRent");
        if (scheduledExecutor == null) {
            scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        } else {
            return;
        }

        WorkerThread worker = new WorkerThread();
        scheduledExecutor.scheduleWithFixedDelay((worker), LoopTimeRate, LoopTimeRate, TimeUnit.MILLISECONDS);//.HOURS


    }

    /**
     * 关闭循环遍历数据
     */
    public void stopLoop() {
        LogUtil.d("stopLoop stopLoop");
        if (scheduledExecutor != null) {
            scheduledExecutor.shutdown();
        }
        scheduledExecutor = null;
    }


}
