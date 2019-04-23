package com.aibabel.menu.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.aibabel.menu.activity.MainActivity;
import com.aibabel.menu.utils.LogUtil;
import java.util.concurrent.ScheduledExecutorService;

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

//    private static WorkerThread worker = new WorkerThread();

    public MyService() {

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e("hjs=", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    private static boolean issleeep4runing = false;
    MyThread thread2 = new MyThread();
    private Object object = new Object();

    class MyThread extends Thread {
        @Override
        public void run() {
            if (MainActivity.loopHandler != null)
                MainActivity.loopHandler.sendEmptyMessage(130);


        }
    }


    private boolean tag = false;

    static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getBaseContext();
        Log.e("====================", "com.example.root.testhuaping.service.MyService");
        Log.e("hjs", "MyServic=onCreate-");
//        startLoopRent();
//        SharePrefUtil.saveString(getApplicationContext(),"","");
        if (!issleeep4runing) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    while (!issleeep4runing) {
                        issleeep4runing = true;
                        try {
                            LogUtil.d("  Thread.sleep(4h) start= " + issleeep4runing);
                            Thread.sleep(1000 * 60 * 60 * 4);
                             //Thread.sleep(1000 * 30);
                            thread2.run();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally {
                            issleeep4runing = false;
                            LogUtil.d("  Thread.sleep(4h) end= " + issleeep4runing);
                        }
                    }
                }
            }).start();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopLoop();
        tag = true;
        Log.v("MyService", "on destroy");
    }

//
//    private static int startloop = 0;
//    public static class WorkerThread implements Runnable {
//        public WorkerThread() {
//            startloop = 0;
//        }
//
//        @Override
//        public void run() {
//            try {
//
//                LogUtil.d(Thread.currentThread().getName() + " Start. Time = ");
//                LogUtil.d(" startloop =" + startloop);
//                if (startloop >= 24) {
//                    processCommand();
//                    startloop = 0;
//                    LogUtil.d("发送handle");
//                }
//                startloop++;
//                LogUtil.d(Thread.currentThread().getName() + " End. Time = ");
//            } catch (Exception e) {
//                e.printStackTrace();
//                LogUtil.d("");
//            }
//        }
//
//        private void processCommand() {
//            try {
//                if (MainActivity.loopHandler != null)
//                    MainActivity.loopHandler.sendEmptyMessage(130);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    /***
//     * 这是一个静态,loop轮询机制
//     */
//   /* private static Handler LooptempHandler = new Handler(msg -> {
//        if (msg.what == LOOPER_CODE) {
//            LogUtil.d("LOOPER_CODE_start");
//        }
//        return false;
//    });*/
//
//    /**
//     * 循环获取轮询
//     */
//    public void startLoopRent() {
//
//        LogUtil.d("startLoopRent");
//        if (scheduledExecutor == null) {
//            scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
//        } else {
//            return;
//        }
//
//        WorkerThread worker = new WorkerThread();
//        scheduledExecutor.scheduleWithFixedDelay((worker), LoopTimeRate, LoopTimeRate, TimeUnit.MILLISECONDS);//.HOURS
//
//
//    }
//
//    /**
//     * 关闭循环遍历数据
//     */
//    public void stopLoop() {
//        LogUtil.d("stopLoop stopLoop");
//        if (scheduledExecutor != null) {
//            scheduledExecutor.shutdown();
//        }
//        scheduledExecutor = null;
//    }






//
//    /********锁机流程********/
//    public static final int toast_rent_Time = 16;
//    public static final String bunder_iszhuner = "zhuner";
//    public static final String bunder_qudao = "kefu";
//    public static final String order_channelName = "order_channelName";
//    public static final String order_oid = "order_oid";
//    public static final String order_uid = "order_uid";
//    public static final String order_uname = "order_uname";
//    public static final String order_sn = "order_sn";
//    public static final String order_from = "order_from_time";
//    public static final String order_endttime = "orderendttime";
//    public static final String order_islock = "order_islock";
//    public static final String order_lockattime = "order_at";
//    public static final String order_isZhuner = "order_isZhuner";
//
//    private static final String neverUseNet_start = "never_user_start_time";
//    private static final String neverUseNet_end = "never_user_end_time";
//    private static final String neverUseNetflag = "never_user_flag";
//    private static boolean locknetsync = true;
//    /**
//     * 如果锁机了，再次同步一次
//     */
//    private static boolean iflocksyncAgain = true;
//
//
//
//
//
//
//    /**
//     * 跳转 100 handler
//     */
//    private void lockloopmsg(String order_end) {
////        String order_end = SharePrefUtil.getString(mContext, order_endttime, "");
//        String channelName = SharePrefUtil.getString(mContext, order_channelName, "");
//        LogUtil.e("channelName=" + (channelName));
//        int isZhuner = SharePrefUtil.getInt(mContext, order_isZhuner, -1);
//        if (TextUtils.isEmpty(order_end)) {
//            return;
//        }
//
//        try {
//            LogUtil.e("lockloopmsg=" + (order_end));
//            //负数的话为已经过期{
//            if ((!TextUtils.isEmpty(order_end)) && (CalenderUtil.compaeTimeWithAfter24(order_end) <= 0)) {
//                if (isNetworkConnected()) {
//                    if (locknetsync) {
//                        if (isNetworkConnected()) syncOrder(getApplication());
//                        locknetsync = false;
//                    }
//                } else {
//                    Message message = new Message();
//                    message.what = 100;
//                    Bundle bun = new Bundle();
//
//                    if ((isZhuner == 1)) {
//                        bun.putString(bunder_iszhuner, "zhuner");
//                    } else if (isZhuner == 0) {
//                        bun.putString(bunder_qudao, channelName);
//                    } else {
//                        if (TextUtils.isEmpty(channelName)) {
//                            bun.putString(bunder_iszhuner, "zhuner");
//                            bun.putString(bunder_qudao, "");
//                        } else {
//                            bun.putString(bunder_iszhuner, "zz");
//                            bun.putString(bunder_qudao, channelName);
//                        }
//                    }
//                    message.setData(bun);
//                    if (MainActivity.loopHandler != null) MainActivity.loopHandler.sendMessage(message);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static boolean isnetok = true;
//
//
//
//    /*
//    * 是否连接
//    * */
//    private boolean isNetworkConnected() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        return (networkInfo != null && networkInfo.isConnected());
//    }
//
//    public int boot_start_lock() {
//        int lockflag = SharePrefUtil.getInt(mContext, order_islock, 0);
//
//        if (lockflag == 1) {
//            Log.e("hjs", "上次锁机，开机继续锁机");
//            if (MainActivity.loopHandler != null) MainActivity.loopHandler.sendEmptyMessageDelayed(100, 1000);
//            return 1;
//        }
//        return -1;
//    }
//
//    /**
//     * 测试IP
//     */
//    //private static final String url_sync_order = "https://wx.aibabel.com:3002/common/api/machine/syncOrder";
//    private static final String url_sync_order = "https://api.web.aibabel.cn:7001/common/api/machine/syncOrder";
//
//    /**
//     * 同步订单， hjs
//     *
//     * @param context
//     */
//    public static void syncOrder(final Context context) {
//        try {
//            LogUtil.e("sn = " + CommonUtils.getSN());
//            OkGo.<String>post(url_sync_order)
//                    .tag(context)
//                    .params("sn", CommonUtils.getSN())
//                    .params("isInChina", LocationUtils.locationWhere(context))
//                    .execute(new StringCallback() {
//                        @Override
//                        public void onSuccess(Response<String> response) {
//                            if ((response != null) && (response.body() != null)) {
//                                String reposStr = response.body().toString();
//                                LogUtil.e("sync = " + reposStr);
//                                try {
//                                    SyncOrder syncOrderObj = FastJsonUtil.changeJsonToBean(reposStr, SyncOrder.class);
//                                    if ((syncOrderObj != null)) {
//                                        LogUtil.e("synorder != null");
//                                        Locklogic(context, syncOrderObj);
//                                        //SharePrefUtil.saveString();
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onError(Response<String> response) {
//                            super.onError(response);
//                            LogUtil.e("reposStr onError = ");
//                            ////okgerror();
//                        }
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 90天锁机逻辑,或者
//     */
//    public void lock90day() {
//        ///没有网络情况下
//        try {
//            if (!isNetworkConnected()) {
//                int lock_type = boot_start_lock();
//                LogUtil.e("lock_type =" + lock_type);
//                if (lock_type == 1) return;
//
//                String endtime = SharePrefUtil.getString(mContext, neverUseNet_end, "");//90天未使用
//                if (!TextUtils.isEmpty(endtime)) {
//                    int comparetime = CalenderUtil.compaeTimeWithNow(endtime);
//                    LogUtil.e("lock90day " + comparetime);
//                    if (((comparetime <= toast_rent_Time) && (comparetime >= 11))) {
//                        if (MainActivity.loopHandler != null) MainActivity.loopHandler.sendEmptyMessage(120);
//                        return;
//                    }
//                }
//
//
//                String spnettemp = SharePrefUtil.getString(mContext, neverUseNetflag, "");
//                LogUtil.e("neverUseNetflag =" + spnettemp);
//                LogUtil.e(" neverUseNet_end =" + endtime);
//                if (!TextUtils.isEmpty(endtime)) {
//                    if (CalenderUtil.compaeTimeWithAfter24(endtime) <= 0) {
//                        LogUtil.e(" compaeTimeWithAfter24  lockloopmsg()  <=0");
//                        lockloopmsg(endtime);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//
//    /**
//     * 更新时间
//     *
//     * @param partime
//     */
//    private static void updatetime(String partime) {
//        if (partime == null) return;
//        try {
//            String end90time = CalenderUtil.calculateTimeDifferenceadd90(partime);
//            SharePrefUtil.put(mContext, neverUseNet_start, partime);
//            SharePrefUtil.put(mContext, neverUseNet_end, end90time);
//            Log.e("hjs", "updatetime ok" + end90time);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static boolean synctimefore = true;
//
//    /**
//     * 锁机逻辑，hjs
//     */
//    public static void Locklogic(Context context, SyncOrder synorder) {
//        SyncOrder.Body OrderBody = synorder.getBody();
//        if (OrderBody != null) {
//            String chanelname = OrderBody.getChannelName();
//            String oid = OrderBody.getOid();
//            String uid = OrderBody.getUid();
//            String uname = OrderBody.getUname();
//            String sn = OrderBody.getSn();
//            String from_time = OrderBody.getF();
//            String end_time = OrderBody.getT();
//            int islock = OrderBody.getIsLock();
//            int attime = OrderBody.getAt();
//            int isZhuner = OrderBody.getIsZhuner();
//
//            SharePrefUtil.saveString(context, order_channelName, chanelname);
//            SharePrefUtil.saveString(context, order_oid, oid);
//            SPHelper.save(order_oid, oid);
//            DataManager.getInstance().setSaveString(order_oid, oid);
//            SharePrefUtil.saveString(context, order_uid, uid);
//            SharePrefUtil.saveString(context, order_uname, uname);
//
//            SharePrefUtil.saveString(context, order_endttime, end_time);
//            SharePrefUtil.saveString(context, order_sn, sn);
//            SharePrefUtil.saveString(context, order_from, from_time);
//
//            if (!TextUtils.isEmpty(end_time)) {
//                SharePrefUtil.saveString(context, end_time, end_time);
//                updatetime(end_time);
//            } else {
//                try {
//                    String[] startstr = CalenderUtil.calculateTimeDifferenceByDuration();
//                    if (startstr != null) {
//                        String starttime_str = startstr[0];
//                        String end_time_str = startstr[1];
//                        LogUtil.e(" = order starttime= " + starttime_str);
//                        LogUtil.e(" =order  end_time= " + end_time_str);
//                        SharePrefUtil.saveString(mContext, neverUseNet_start, starttime_str);
//                        SharePrefUtil.saveString(mContext, neverUseNet_end, end_time_str);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//            if (islock >= 0) {
//                SharePrefUtil.saveInt(context, order_islock, islock);
//                try {
//                    boolean RentLocked_fore = DetectUtil.isForeground(context, RentLockedActivity.class);
//                    boolean RentKeepUse = DetectUtil.isForeground(context, RentKeepUseActivity.class);
//                    if ((RentKeepUse || RentLocked_fore) && islock == 0) {
//                        LogUtil.e("RentLocked_fore = " + RentLocked_fore);
//                        try {
//                            RentLockedActivity.finsRentlock();
//                            RentKeepUseActivity.finsRentlock();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        //if (loopHandler != null) loopHandler.sendEmptyMessage(200);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            SharePrefUtil.saveInt(context, order_lockattime, attime);
//            SharePrefUtil.saveInt(context, order_isZhuner, isZhuner);
//
//
//            try {
//                ///服务器锁机
//                if (islock == 1) {
//                    LogUtil.e("服务器锁机 ");
//                    Message message = new Message();
//                    message.what = 100;
//                    Bundle bun = new Bundle();
//                    if ((isZhuner == 1)) {
//                        bun.putString(bunder_iszhuner, "zhuner");
//                    } else if (isZhuner == 0) {
//                        bun.putString(bunder_qudao, chanelname);
//                    } else {
//                        if (TextUtils.isEmpty(chanelname)) {
//                            bun.putString(bunder_iszhuner, "zhuner");
//                            bun.putString(bunder_qudao, "");
//                        } else {
//                            bun.putString(bunder_iszhuner, "zz");
//                            bun.putString(bunder_qudao, chanelname);
//                        }
//                    }
//                    message.setData(bun);
//                    if (MainActivity.loopHandler != null) MainActivity.loopHandler.sendMessage(message);
//                    return;
//                }
//
//                int comparetime = CalenderUtil.compaeTimeWithNow(end_time);
//                LogUtil.e("提醒续租 ");
//                //提醒续租
//                if (((comparetime <= toast_rent_Time) && (comparetime >= 11))) {
//                    if (MainActivity.loopHandler != null) MainActivity.loopHandler.sendEmptyMessage(120);
//                    return;
//                }
//                LogUtil.e("到期后24小时内锁机 _fail ");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        } else {
//            String order_id = SharePrefUtil.getString(context, order_oid, "");
//            ///国外 并且无订单
//            if ((LocationUtils.locationWhere(context) == 0) && (TextUtils.isEmpty(order_id))) {
//                LogUtil.e("国外 并且无订单  ");
//            }
//        }
//    }
//
//    /**
//     * 扫码解锁后 清除标志位、清楚90天未联网日期、关闭lock按钮界面
//     */
//    private void unlock_ok_clear() {
//        try {
//            boolean RentLocked_fore = DetectUtil.isForeground(this, RentLockedActivity.class);
//            LogUtil.e("RentLocked_fore = " + RentLocked_fore);
//            try {
//                if (RentLocked_fore) {
//                    RentLockedActivity.finsRentlock();
//                }
//                RentKeepUseActivity.finsRentlock();
//                RentLockedActivity.finsRentlock();
//            } catch (Exception e) {
//
//            }
//
//            clearALlsharePreutil();
//
//            init_neveruser();
//            //TODO 清除服务器域名
//            LogUtil.e("onRestart = RentLocked_fore");
//
//            locknetsync = true;
//            isnetok = true;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 清除所有sharep 订单信息
//     */
//    public void clearALlsharePreutil() {
//        SqlUtils.deleteDataAll();
//        SharePrefUtil.put(mContext, neverUseNetflag, "");
//        SharePrefUtil.put(mContext, neverUseNet_start, "");
//        SharePrefUtil.put(mContext, neverUseNet_end, "");
//        SharePrefUtil.put(mContext, order_channelName, "");
//        SharePrefUtil.put(mContext, order_oid, "");
//        SharePrefUtil.put(mContext, order_uid, "");
//        SharePrefUtil.put(mContext, order_uname, "");
//        SharePrefUtil.put(mContext, order_sn, "");
//        SharePrefUtil.put(mContext, order_from, "");
//        SharePrefUtil.put(mContext, order_endttime, "");
//        SharePrefUtil.put(mContext, order_islock, 0);
//        SharePrefUtil.put(mContext, order_lockattime, 0);
//        SharePrefUtil.put(mContext, order_isZhuner, 0);
//        int lockflag = SharePrefUtil.getInt(mContext, order_islock, 0);
//        String get_starttime = SharePrefUtil.getString(mContext, neverUseNet_start, "");
//        String get_endtime = SharePrefUtil.getString(mContext, neverUseNet_end, "");
//
//        LogUtil.e("get_starttime==" + get_starttime);
//        LogUtil.e("get_endtime==" + get_endtime);
//
//    }
//
//    /**
//     * 初始化从来未使用的函数
//     */
//    private void init_neveruser() {
//        try {
//            String get_starttime = "";
//            String get_endtime = "";
//            try {
//                get_starttime = SharePrefUtil.getString(mContext, neverUseNet_start, "");
//                get_endtime = SharePrefUtil.getString(mContext, neverUseNet_end, "");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            LogUtil.e("start__= " + get_starttime + " = init_neveruser = " + get_endtime);
//            if (TextUtils.isEmpty(get_starttime) || (TextUtils.isEmpty(get_endtime))) {
//                String[] startstr = CalenderUtil.calculateTimeDifferenceByDuration();
//                if (startstr != null) {
//                    String starttime = startstr[0];
//                    String end_time = startstr[1];
//
//                    LogUtil.e(" = init_neveruser starttime= " + starttime);
//                    LogUtil.e(" =  end_time= " + end_time);
//
//                    SharePrefUtil.saveString(mContext, neverUseNet_start, starttime);
//                    SharePrefUtil.saveString(mContext, neverUseNet_end, end_time);
//
//                    get_starttime = SharePrefUtil.getString(mContext, neverUseNet_start, "");
//                    LogUtil.e("  starttime= " + get_starttime);
//
//                    get_endtime = SharePrefUtil.getString(mContext, neverUseNet_end, "");
//                    LogUtil.e("  get_endtime= " + get_endtime);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
