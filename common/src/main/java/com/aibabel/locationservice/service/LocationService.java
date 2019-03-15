package com.aibabel.locationservice.service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.locationservice.alarm.ScreenListener;
import com.aibabel.locationservice.bean.LocationBean;
import com.aibabel.locationservice.bean.ResultBean;
import com.aibabel.locationservice.map.MapUtils;
import com.aibabel.locationservice.map.ReLocationManager;
import com.aibabel.locationservice.provider.LocationModel;
import com.aibabel.locationservice.receiver.BootBroadcastReceiver;
import com.aibabel.locationservice.receiver.CardBroadcastReceiver;
import com.aibabel.locationservice.receiver.ScreenBroadcastReceiver;
import com.aibabel.locationservice.utils.CommonUtils;
import com.aibabel.locationservice.utils.Constants;
import com.aibabel.locationservice.utils.ContentProviderUtil;
import com.aibabel.locationservice.utils.FastJsonUtil;
import com.aibabel.locationservice.utils.FlowUtil;
import com.aibabel.locationservice.utils.SharePrefUtil;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.taobao.sophix.SophixManager;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;


public class LocationService extends Service implements ScreenListener, CardBroadcastReceiver.Switch_Card {
    private String TAG = LocationService.class.getSimpleName().toString();
    private Handler handler_poi = new Handler();
    private Handler handler_server = new Handler();
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private Runnable runnable_poi;
    private Runnable runnable_server;
    private LocationBean locationBean = new LocationBean();
    private ScreenBroadcastReceiver mScreenReceiver;


    //业务参数
    private double latitude;
    private double longitude;
    private String addr;
    private String country;
    private String province;
    private String city;
    private String district;
    private String street;
    private String locationDescribe;
    private String ips;
    private ReLocationManager reLocationManager;
    private TimerTask timerTask;
    private Timer timer;
    private boolean isFirst;
    private int errorCode;
    private String coorType;
    private int locationWhere;

    //========================流量统计=========================
    private long now_flow;
    private long zero_now_flow;
    private long today_flow;
    private long last_zero_now_flow;
    private int TIME = 30000;
    private long now_flow_num;
    private long now_flow_last;
    private String cur_card = "-1";
    private Timer flowTimer;
    private String str_date;
    // 外置卡的 流量
    public static long cardType_0 = 0;
    // 内置卡的 流量
    public static long cardType_1 = 0;
    // 区间流量
    public static long sectionFlow = 0;

    public static long lastNumFlow = 0;
    public static long sectionNumFlow = 0;
    //====================流量统计结束===========================


    /**
     * 绑定服务时才会调用
     * 必须要实现的方法
     *
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        super.onCreate();

    }

    /**
     * 每次通过startService()方法启动Service时都会被回调。
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isFirst = true;
        //注册监听屏幕变化广播
        if (mScreenReceiver == null) {
            mScreenReceiver = new ScreenBroadcastReceiver();
            mScreenReceiver.registerScreenBroadcastReceiver(this);
            mScreenReceiver.setAlarmListener(this);
        }


        //获取默认IP地址
        ips = ContentProviderUtil.ips;
        contentProvide();
        //百度定位
        //声明、配置LocationClient类
        if(TextUtils.equals(CommonUtils.getProType(),"L")){//判定Pro是否为销售版
            mLocationClient = MapUtils.getLocationClient(getApplicationContext(), Constants.LOCATION_MILLIS);
        }else{
            mLocationClient = MapUtils.getLocationClient(getApplicationContext(), Constants.LOCATION_MILLIS_S);
        }
        // //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        //调用LocationClient的start()方法，便可发起定位请求
        mLocationClient.start();

        reLocationManager = new ReLocationManager(mLocationClient);


        //循环请求后台获取新的IP地址
        runnable_server = new Runnable() {
            @Override
            public void run() {
                getHotFix();
                getServerIP();
                contentProvide();
                handler_server.postDelayed(this, Constants.SERVER_MILLIS);
            }
        };
        handler_server.postDelayed(runnable_server, 0);


        //循环请求后台获取新POI数据
        runnable_poi = new Runnable() {
            @Override
            public void run() {
                if (!isFirst) {//判定是否为第一次启动，第一次定位不请求（百度默认有第一次定位）
                    getInformation(LocationService.this);
                } else {
                    Log.e(TAG, "第一次没有请求!");
                    isFirst = false;
                }
                if(TextUtils.equals(CommonUtils.getProType(),"L")){//判定Pro是否为销售版
                    handler_poi.postDelayed(this, Constants.POI_MILLIS);
                }else{
                    handler_poi.postDelayed(this, Constants.POI_MILLIS_S);
                }

            }
        };
        handler_poi.postDelayed(runnable_poi, 0);

        //注册切换sim卡广播
        CardBroadcastReceiver cardBroadcastReceiver = new CardBroadcastReceiver();
        cardBroadcastReceiver.setSwitch_card(this);
        FlowUtil.initMtkDoubleSim(LocationService.this);
        if (FlowUtil.isMobileEnabled(this)) {
            FlowUtil.getDefaultDataSubId(this);
        }
        //上传统计流量
        uploadTraffic();
        //向语音翻译发送广播
        sendTranslation();
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 获取是否需要热修复
     */
    private void getHotFix() {
        if (!CommonUtils.isAvailable(this)) {
//            handler_server.sendEmptyMessageDelayed(MESSAGE_SERVER,SERVER_MILLIS);
            return;
        }
        String HOST = ContentProviderUtil.getHost(this);
        String url = HOST + "/v1/jonersystem/GetAppNew?sn=" + CommonUtils.getSN() + "&no=" + CommonUtils.getRandom() + "&sl=" + CommonUtils.getLocal() + "&av=" + CommonUtils.getVerName(this) + "&app=" + getPackageName() + "&sv=" + CommonUtils.getSystemFlag() + "&lat=" + latitude + "&lng=" + longitude;
        Log.e(TAG, url);
//        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(TAG, response.body().toString());
                        if (!TextUtils.isEmpty(response.body().toString())) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                boolean isNew = (Boolean) ((JSONObject) jsonObject.get("data")).get("isNew");
                                if (isNew) {
                                    SophixManager.getInstance().queryAndLoadNewPatch();
                                    Log.e("success:", "=================" + isNew + "=================");
                                } else {
                                    Log.e("failed:", "=================" + isNew + "=================");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("Exception:", "==========" + e.getMessage() + "===========");
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                    }
                });


    }

    /**
     * 定时给语音翻译发送广播，用于判定是否释放离线模型
     */
    private void sendTranslation() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Constants.ACTION_TRANSLATE);
                sendBroadcast(intent);
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, new Date(), Constants.PERIOD);

    }


    @Override
    public void wake() {//屏幕亮起后执行任务
        if (!mLocationClient.isStarted())
            mLocationClient.start();

    }

    @Override
    public void sleep() {//屏幕熄灭后调用执行任务
        String ip = "http://abroad.api.joner.aibabel.cn:7001";
        if (locationWhere == 0) {
            ip = "http://abroad.api.joner.aibabel.cn:7001";
        } else {
            ip = "http://api.joner.aibabel.cn:7001";
        }
        Map<String, String> map = new HashMap<>();
        map.put("sv", Build.DISPLAY);
        map.put("sn", CommonUtils.getSN());
        map.put("sl", CommonUtils.getLocal());
        map.put("av", CommonUtils.getVerName(this));
        map.put("no", CommonUtils.getRandom() + "");
        map.put("lat", latitude + "");
        map.put("lng", longitude + "");
        StatisticsManager.getInstance(this).sendDataAidl(ip + "/v1/ddot/JonerLogPush", map);
        Log.e("sleep", ip);
//        isScreenOn = false;
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                if (mLocationClient.isStarted())
//                    mLocationClient.stop();
////                    handler.removeCallbacks(runnable);
//            }
//        };
//        Timer timer = new Timer();
//        timer.schedule(task, Constants.FIVE_MILLIS);
    }


    /**
     * 注册监听
     */
    public class MyLocationListener extends BDAbstractLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度和地址相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            //获取纬度信息
            latitude = location.getLatitude();
            //获取经度信息
            longitude = location.getLongitude();
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            //获取详细地址信息
            addr = location.getAddrStr();
            //获取国家
            country = location.getCountry();
            //获取省份
            province = location.getProvince();
            //获取城市
            city = location.getCity();
            //获取区县
            district = location.getDistrict();
            //获取街道信息
            street = location.getStreet();
            //获取位置描述信息
            locationDescribe = location.getLocationDescribe();
            coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            locationWhere = location.getLocationWhere();


            locationBean.setAddr(addr);
            locationBean.setCountry(country);
            locationBean.setDistrict(district);
            locationBean.setLatitude(latitude);
            locationBean.setLocationDescribe(locationDescribe);
            locationBean.setLongitude(longitude);
            locationBean.setCity(city);
            locationBean.setStreet(street);
            locationBean.setProvince(province);

            //判断定位是否成功，直到定位成功获取到国家
            if (latitude == 4.9E-324 || longitude == 4.9E-324 || latitude == 0.0 || longitude == 0.0) {
                reLocationManager.onLocationFailed();
            } else {
                reLocationManager.onLocationSuccess();
            }

            contentProvide();
            /**
             * 给菜单发送广播
             */
            Intent intent = new Intent("com.aibabel.menu.MENULOCATION");
            intent.putExtra("city", city);
            sendBroadcast(intent);

            Log.e(TAG, "errorCode：" + errorCode + " latitude：" + latitude + " longitude:" + longitude + " 国家：" + country + "，省：" + province + "，市：" + city + "，区：" + district + "，街道：" + street + "详细地址：" + addr);
        }


    }

//    private void sendLocationBroadcast(LocationBean bean) {
//        Intent broadcastIntent = new Intent(Constants.ACTION_LOCATION);
//        Bundle bundle = new Bundle();
//        bundle.putString("location", FastJsonUtil.changObjectToString(bean));
//        broadcastIntent.putExtras(bundle);
//        sendBroadcast(broadcastIntent);
//    }
//
//    /**
//     * 根据apk类型选择广播
//     * @param apk
//     * @return
//     */
//    private String selectBroadcastByType(String apk) {
//        String action = "";
//        if (TextUtils.equals(apk, "weather")) {
//            action = Constants.ACTION_WEATHER;
//        } else if (TextUtils.equals(apk, Constants.TRAVEL)) {
//            action = Constants.ACTION_TRAVEL;
//        } else if (TextUtils.equals(apk, "location")) {
//            action = Constants.ACTION_LOCATION;
//        } else if (TextUtils.equals(apk, "advisory")) {
//            action = Constants.ACTION_TRAVEL_ADVISORY;
//        }
//        return action;
//    }


    @Override
    public boolean stopService(Intent name) {
        //注销屏幕监听广播
        mScreenReceiver.unRegisterScreenBroadcastReceiver(this);
        stopLoaction();//停止定位服务
        stopRequst();//停止网络请求
        return super.stopService(name);
    }

    /**
     * 停止定位
     */
    public void stopLoaction() {
        mLocationClient.stop();
    }

    /**
     * 停止handler
     */
    public void stopRequst() {
        if (null != handler_server) {
            handler_server.removeCallbacks(runnable_server);
        }
        if (null != handler_poi) {
            handler_poi.removeCallbacks(runnable_poi);
        }
    }


    /**
     * 服务销毁时的回调
     */
    @Override
    public void onDestroy() {
        //注销网络变化监听
//        if (null != networkRecciver)
//            unregisterReceiver(networkRecciver);
        //注销屏幕监听广播
        mScreenReceiver.unRegisterScreenBroadcastReceiver(this);
        stopLoaction();//停止定位服务
        stopRequst();//停止网络请求
        super.onDestroy();
    }


    /**
     * 获取推送通知
     */
    public void getInformation(final Context context) {
        if (!CommonUtils.isAvailable(context)) {
            return;
        }
//        latitude = 35.698432;
//        longitude = 139.771639;
//        latitude = 0.0;
//        longitude = 0.0;
        //String HOST = "http://api.joner.aibabel.cn:7001";
        String HOST = ContentProviderUtil.getHost(context);
        String url = HOST + "/v3/poi?lat=" + latitude + "&lng=" + longitude + "&r=" + Constants.RADIUS + "&sn=" + CommonUtils.getSN() + "&no=" + CommonUtils.getRandom() + "&sl=" + CommonUtils.getLocal() + "&errorCode=" + errorCode + "&sv=" + CommonUtils.getSystemFlag() + "&av=" + CommonUtils.getVerName(this);
        Log.e(TAG, url);
        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(TAG, response.body());
                        try {
                            ResultBean bean = FastJsonUtil.changeJsonToBean(response.body().toString(), ResultBean.class);
                            if (null != bean && TextUtils.equals(bean.getData().getApk(), Constants.TRAVEL)) {
                                //发送景区导览通知广播
                                Intent broadcastIntent = new Intent(Constants.ACTION_TRAVEL);
                                Bundle bundle = new Bundle();
                                bundle.putString("data", FastJsonUtil.changListToString(bean.getData().getInfo()));
                                broadcastIntent.putExtras(bundle);
                                context.sendBroadcast(broadcastIntent);
                            } else if (null != bean && TextUtils.equals(bean.getData().getApk(), Constants.NOTICE)) {
                                //发送普通通知广播
                                Intent broadcastIntent = new Intent(Constants.ACTION_NOTICE);
                                Bundle bundle = new Bundle();
                                bundle.putString("msg", bean.getData().getMsg());
                                broadcastIntent.putExtras(bundle);
                                context.sendBroadcast(broadcastIntent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }


    /**
     * 获取服务器地址列表
     */
    private void getServerIP() {
        if (!CommonUtils.isAvailable(this)) {
//            handler_server.sendEmptyMessageDelayed(MESSAGE_SERVER,SERVER_MILLIS);
            return;
        }
        String HOST = ContentProviderUtil.getHost(this);
        String url = HOST + "/v1/jonerconfig/getConfig?sn=" + CommonUtils.getSN() + "&no=" + CommonUtils.getRandom() + "&sl=" + CommonUtils.getLocal();
        Log.e(TAG, url);
//        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(TAG, response.body().toString());
                        if (!TextUtils.isEmpty(response.body().toString())) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());

                                ips = ((JSONObject) jsonObject.get("data")).get("server").toString();
                                if (TextUtils.isEmpty(ips)) {
                                    ips = ContentProviderUtil.ips;
                                }
//
                                contentProvide();
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                    }
                });

    }


    /**
     * 将定位信息和接口信息存入xontentprovider中
     */
    private void contentProvide() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LocationModel.LocationEntry._ID, 1 + "");
        if (latitude != 4.9E-324 && latitude != 0.0)
            contentValues.put(LocationModel.LocationEntry.COLUMN_LATITUDE, latitude);
        if (longitude != 4.9E-324 && longitude != 0.0)
            contentValues.put(LocationModel.LocationEntry.COLUMN_LONGITUDE, longitude);
        contentValues.put(LocationModel.LocationEntry.COLUMN_COUNTRY, country);
        contentValues.put(LocationModel.LocationEntry.COLUMN_PROVINCE, province);
        contentValues.put(LocationModel.LocationEntry.COLUMN_CITY, city);
        if (!TextUtils.isEmpty(addr))
            contentValues.put(LocationModel.LocationEntry.COLUMN_ADDR, addr);
        contentValues.put(LocationModel.LocationEntry.COLUMN_IP, ips);
        contentValues.put(LocationModel.LocationEntry.COLUMN_COOR, coorType);
        contentValues.put(LocationModel.LocationEntry.COLUMN_WHERE, locationWhere + "");
        getContentResolver().insert(LocationModel.LocationEntry.CONTENT_URI, contentValues);
        Cursor cursor = getContentResolver().query(LocationModel.LocationEntry.CONTENT_URI, null, null, null, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                cursor.getString(4);
            } else {
                Log.e("TAG", TAG + "：无数据");
            }
        } finally {
            if (null != cursor)
                cursor.close();
        }

    }
//=======================================以下是获取流量方法===================================

    /**
     * 计算一段时间总流量
     *
     * @param context
     * @return
     */
    public long get_flow_num(Context context) {
        //获取关机前的流量
        now_flow_last = SharePrefUtil.getLong(context, "now_flow", 0);
        //开机到现在的流量（重启之后）
        now_flow = flow();
        now_flow_num = now_flow + now_flow_last;
        return now_flow_num;
    }

    /**
     * 获取今天使用流量
     *
     * @param context
     */
    public void get_flow(Context context) {
        //上次的 流量(关机之前)
        String date_old = SharePrefUtil.getString(context, "date", "");
        SimpleDateFormat sdf_date = new SimpleDateFormat("yy-MM-dd");
        Date date_new = new Date();
        str_date = sdf_date.format(date_new);
        now_flow_num = get_flow_num(context);
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//        Date data = new Date();
//        str_time = sdf.format(data);

        if (TextUtils.equals(date_old, str_date)) {
            last_zero_now_flow = SharePrefUtil.getLong(context, "zero_now_flow", 0);
            today_flow = now_flow_num - last_zero_now_flow;
        } else {
            last_zero_now_flow = SharePrefUtil.getLong(context, "now_flow", 0);
            SharePrefUtil.saveLong(context, "zero_now_flow", last_zero_now_flow);
            today_flow = now_flow_num - last_zero_now_flow;
        }


    }

    /**
     * 当前一段时间使用流量（从开机到现在）
     *
     * @return
     */
    public long flow() {
        long mobileRxBytes = TrafficStats.getMobileRxBytes();
        long mobileTxBytes = TrafficStats.getMobileTxBytes();
        //开机到现在一共的流量
        long now_flow = mobileRxBytes + mobileTxBytes;
        return now_flow;
    }


    @Override
    public void switch_Card() {
        get_flow(LocationService.this);
        lastNumFlow = SharePrefUtil.getLong(LocationService.this, "last_num_flow", 0);
        sectionNumFlow = now_flow_num - lastNumFlow;
        Log.e("sectionNumFlow", "总流量==" + now_flow_num + "===5分钟前的总流量==" + lastNumFlow + "===5分钟使用的流量==" + sectionNumFlow + "");
        SharePrefUtil.saveLong(LocationService.this, "last_num_flow", now_flow_num);
        if (FlowUtil.isMobileEnabled(LocationService.this)) {
            BootBroadcastReceiver.CARD_TYPE = FlowUtil.getDefaultDataSubId(LocationService.this);
            if (BootBroadcastReceiver.CARD_TYPE == 0) {
                // TODO: 2019/2/25 切换card0发送流量统计到后台
                send_sectionFlow(Constants.CARD_0, sectionNumFlow, BootBroadcastReceiver.CARD_TYPE);
                Log.e("上传", "iccid ==" + Constants.CARD_0 + "====" + sectionNumFlow);
            } else if (BootBroadcastReceiver.CARD_TYPE == 1) {
                // TODO: 2019/2/25 切换card1发送流量统计到后台
                send_sectionFlow(Constants.CARD_1, sectionNumFlow, BootBroadcastReceiver.CARD_TYPE);
                Log.e("上传", "iccid ==" + Constants.CARD_1 + "====" + sectionNumFlow);
            }
        }


        //区间流量
        cardType_1 = SharePrefUtil.getLong(LocationService.this, "card1", 0);
        cardType_0 = SharePrefUtil.getLong(LocationService.this, "card0", 0);
        sectionFlow = today_flow - (cardType_0 + cardType_1);

        if (BootBroadcastReceiver.CARD_TYPE == 0) {
            cardType_1 = cardType_1 + sectionFlow;
            SharePrefUtil.saveLong(LocationService.this, "card1", cardType_1);
        } else if (BootBroadcastReceiver.CARD_TYPE == 1) {
            cardType_0 = cardType_0 + sectionFlow;
            SharePrefUtil.saveLong(LocationService.this, "card0", cardType_0);
        }

    }


    /**
     * 上传流量到服务器
     */
    private void uploadTraffic() {
        Timer timer_date = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        get_flow(LocationService.this);
                        if (CommonUtils.isAvailable(LocationService.this)) {

                            lastNumFlow = SharePrefUtil.getLong(LocationService.this, "", 0);
                            lastNumFlow = SharePrefUtil.getLong(LocationService.this, "last_num_flow", 0);
                            sectionNumFlow = now_flow_num - lastNumFlow;
                            Log.e("sectionNumFlow", "总流量：" + now_flow_num + "===5分钟前的总流量：" + lastNumFlow + "===5分钟使用的流量：" + sectionNumFlow + "");
                            SharePrefUtil.saveLong(LocationService.this, "last_num_flow", now_flow_num);
                            if (FlowUtil.isMobileEnabled(LocationService.this)) {
                                BootBroadcastReceiver.CARD_TYPE = FlowUtil.getDefaultDataSubId(LocationService.this);
                                if (BootBroadcastReceiver.CARD_TYPE == 0) {
                                    send_sectionFlow(Constants.CARD_0, sectionNumFlow, BootBroadcastReceiver.CARD_TYPE);
                                    cur_card = Constants.CARD_0;
                                } else if (BootBroadcastReceiver.CARD_TYPE == 1) {
                                    send_sectionFlow(Constants.CARD_1, sectionNumFlow, BootBroadcastReceiver.CARD_TYPE);
                                    cur_card = Constants.CARD_1;
                                }
                                Log.e("上传", "iccid ==" + cur_card + "====" + sectionNumFlow);
                            }
                        }

                    }
                });

            }
        };
//        timer_date.schedule(task, 0, 60 * 1000);
        timer_date.schedule(task, 0, 60 * 1000 * 5);

    }


    /**
     * 发送流量到服务器
     *
     * @param iccid
     * @param sectionFlow
     */
    private void send_sectionFlow(String iccid, long sectionFlow, int cardType) {

        if (sectionFlow <= 0) {
            Log.e(TAG, "=============当前未使用流量不上传统计处理==============");
            return;
        }

        if (!CommonUtils.isAvailable(this)) {
            Log.e(TAG, "=============当前网络断开了不上流量传统计数据==============");
            return;
        }

        String url = "https://wx.aibabel.com:3002/common/api/flow/count";

        String orderNo = CommonUtils.getOrderNo();
        Log.e(TAG, "============="+orderNo+"==============");
        OkGo.<String>post(url)
                .tag(this)
                .params("orderNo", orderNo)
                .params("sn", CommonUtils.getSN())
                .params("iccid", iccid)
                .params("cardType", cardType+1)
                .params("count", sectionFlow)
                .params("unit", "b")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(TAG+"流量统计onSuccess：",response.body().toString());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Log.e(TAG+"流量统计onError：",response.body().toString());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }

}
