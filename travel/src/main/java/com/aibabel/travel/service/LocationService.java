package com.aibabel.travel.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.travel.bean.DetailBean;

import com.aibabel.travel.bean.DoubleListBean;
import com.aibabel.travel.bean.ResultBean;
import com.aibabel.travel.map.MapUtils;
import com.aibabel.travel.map.ReLocationManager;
import com.aibabel.travel.utils.CommonUtils;
import com.aibabel.travel.utils.Constant;
import com.aibabel.travel.utils.ContentProviderUtil;
import com.aibabel.travel.utils.FastJsonUtil;


import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.DistanceUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class LocationService extends Service {
    private String TAG = LocationService.class.getSimpleName().toString();
    private Handler handler_poi = new Handler();
    private Handler handler_server = new Handler();
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private Runnable runnable_poi;
    private Runnable runnable_server;


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
    //位置提醒
    private MyLocationListener listener;
    private NotifyLister mNotifyLister;
    private String travelJson;

    private DetailBean detailBean;
    private static final double EARTH_RADIUS = 6378137;
    private ResultBean bean;

    //存储服务器返回的景点到当前定位的距离
    private List<DoubleListBean> doubleList = new ArrayList<>();
    //存储位置提醒过的景点的经纬度
    private List<String> lng_lat_nameList = new ArrayList<>();
    private double latitude_notice;
    private double longitude_notice;
    private String notice_name;
    //是否已经推送发送过广播（发过一次之后就不再发送）
    private boolean isSendBroadcast;
    private String audios;
    private String imgUrl;
    private List<ResultBean.DataBean> data;
    private int position;

    private boolean firstRequest = false;
    private Vibrator mVibrator;
    private double latitude_baidu;
    private double longitude_baidu;

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
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isFirst = true;
//        LatLng latLng123 = zhungHuan_BD(39.938148, 116.403198);
//        Log.e("123",latLng123.toString());
//        String time = intent.getStringExtra("time");
//        if (time==null){
//            Constant.LOCATION_MILLIS=1500;
//        }else {
//            Constant.LOCATION_MILLIS = Integer.valueOf(time).intValue();
//        }


        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        //百度定位
        //声明、配置LocationClient类
        mLocationClient = MapUtils.getLocationClient(getApplicationContext(), Constant.LOCATION_MILLIS);
//        mLocationClient = MapUtils.getLocationClient(getApplicationContext(),6*1000);
        // //注册监听函数
        mLocationClient.registerLocationListener(myListener);


        mNotifyLister = new NotifyLister();
        mLocationClient.registerNotify(mNotifyLister);


        //调用LocationClient的start()方法，便可发起定位请求
        mLocationClient.start();

        reLocationManager = new ReLocationManager(mLocationClient);
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
                handler_poi.postDelayed(this, Constant.POI_MILLIS);
            }
        };
        handler_poi.postDelayed(runnable_poi, 0);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 注册监听
     */

    //定位
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
//            Toast.makeText(LocationService.this, "latitude："+latitude+" longitude:"+longitude, Toast.LENGTH_SHORT).show();

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            errorCode = location.getLocType();


            if (latitude != 0 && longitude != 0) {
                if (!firstRequest) {
                    getInformation(LocationService.this);
                    firstRequest = true;
                }
            }
            if (data != null) {
                if (doubleList != null) {
                    doubleList.clear();
                }
                for (int i = 0; i < data.size(); i++) {

                    LatLng latLng_g = zhungHuan_BD(data.get(i).getLat(), data.get(i).getLng());
                    LatLng latLng_b = new LatLng(latitude, longitude);
                    double distance = DistanceUtil.getDistance(latLng_b, latLng_g);
                    doubleList.add(new DoubleListBean(distance, data.get(i).getPoi_name_cn()));
                }
                Collections.sort(doubleList);
                for (int i = 0; i < doubleList.size(); i++) {
                    Log.e("景点排名", doubleList.get(i).getName() + "==" + doubleList.get(i).getDistance());
                }
                for (int i = 0; i < data.size(); i++) {
                    if (TextUtils.equals(doubleList.get(0).getName(), data.get(i).getPoi_name_cn())) {
                        if (data.get(i).getLat() != 0 && data.get(i).getLng() != 0) {
                            latitude_notice = data.get(i).getLat();
                            longitude_notice = data.get(i).getLng();
                            notice_name = data.get(i).getPoi_name_cn();
                            audios = data.get(i).getAudios();
                            imgUrl = data.get(i).getImgUrl();
                            notifyHandler.sendEmptyMessage(0);
                            try {
                                LatLng latLng_g = zhungHuan_BD(latitude_notice, longitude_notice);
                                LatLng latLng_b = new LatLng(latitude, longitude);
                                double distance = DistanceUtil.getDistance(latLng_b, latLng_g);
                                Log.e("距离", distance + "===" + notice_name + "===" + latLng_g.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            if (doubleList != null && doubleList.size() != 0) {

                if (doubleList.get(0).getDistance() <= Constant.REMINDER_DISTANCE) {
                    if (lng_lat_nameList != null) {
                        if (!lng_lat_nameList.contains(notice_name)) {
                            if (latitude_notice != 0 && longitude_notice != 0) {
//                            mNotifyLister.SetNotifyLocation(latitude_baidu, longitude_baidu, 30, mLocationClient.getLocOption().getCoorType());
                                sendBroadcast();
                            }
                            for (int i = 0; i < lng_lat_nameList.size(); i++) {
                                Log.e("notice", lng_lat_nameList.size() + "==" + lng_lat_nameList.get(i));
                            }
                            isSendBroadcast = false;
                        }
                    }
                }
            }
            Log.e("定位-景区导览", "errorCode：" + errorCode + " latitude：" + latitude + " longitude:" + longitude + " 国家：" + country + "，省：" + province + "，市：" + city + "，区：" + district + "，街道：" + street + "详细地址：" + addr);
        }


    }






    //请求服务器获取附近景点
    private void getInformation(final Context context) {
        if (!CommonUtils.isAvailable1(context)) {
            return;
        }
//        latitude = 35.698432;
//        longitude = 139.771639;
        String HOST = ContentProviderUtil.getHost(context);

//        String HOST = "http://api.joner.aibabel.cn:7001";
        String url = HOST+"/v3/poi/region?lat=" + latitude + "&lng=" + longitude + "&r=" + Constant.RADIUS + "&sn=" + CommonUtils.getSN() + "&no=" + CommonUtils.getRandom() + "&sl=" + CommonUtils.getLocal(context) + "&errorCode=" + errorCode + "&sv=" + CommonUtils.getDeviceInfo() + "&av=" + CommonUtils.getVerName(this);
//        String url = "http://api.joner.aibabel.cn:7001/v3/poi/region?lat=" + latitude + "&lng=" + longitude + "&r=" + Constant.RADIUS + "&sn=" + CommonUtils.getSN() + "&no=" + CommonUtils.getRandom() + "&sl=" + CommonUtils.getLocal(context) + "&errorCode=" + errorCode + "&sv=" + CommonUtils.getDeviceInfo() + "&av=" + CommonUtils.getVerName(this);

        // Log.e(TAG, url);
//        Log.e("url", url);
        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
//                        Log.e(TAG, response.body());
                        doubleList.clear();
                        // 服务器发来的 需要提醒的景点位置信息 （景点名 景点录音 景点图片）
                        if (!TextUtils.isEmpty(response.body().toString())) {
                            try {
                                ResultBean resultBean = FastJsonUtil.changeJsonToBean(response.body().toString(), ResultBean.class);
                                if (TextUtils.equals("1", resultBean.getCode())) {
                                    data = resultBean.getData();
                                    if (data != null) {
                                        for (int i = 0; i < data.size(); i++) {
                                            LatLng latLng_g = zhungHuan_BD(data.get(i).getLat(), data.get(i).getLng());
                                            LatLng latLng_b = new LatLng(latitude, longitude);
                                            double distance = DistanceUtil.getDistance(latLng_b, latLng_g);
                                            doubleList.add(new DoubleListBean(distance, data.get(i).getPoi_name_cn()));
                                        }
                                        Collections.sort(doubleList);
                                        for (int i = 0; i < data.size(); i++) {
                                            if (TextUtils.equals(doubleList.get(0).getName(), data.get(i).getPoi_name_cn())) {
                                                if (data.get(i).getLat() != 0 && data.get(i).getLng() != 0) {
                                                    position = i;
                                                    latitude_notice = data.get(i).getLat();
                                                    longitude_notice = data.get(i).getLng();
                                                    LatLng latLng_notice = zhungHuan_BD(latitude_notice, longitude_notice);
                                                    latitude_baidu = latLng_notice.latitude;
                                                    longitude_baidu = latLng_notice.longitude;
                                                    Log.e("latLng_notice", latLng_notice.toString());
                                                    notice_name = data.get(i).getPoi_name_cn();
                                                    audios = data.get(i).getAudios();
                                                    imgUrl = data.get(i).getImgUrl();
                                                    notifyHandler.sendEmptyMessage(0);
//                                                    Log.e("list_title", "latitude" + latitude_notice + "==longitude" + longitude_notice + "==name" + notice_name + "===" + doubleList.get(0).getDistance());
                                                }
                                            }
                                        }
                                    }
                                }
//                                detailBean = FastJsonUtil.changeJsonToBean(response.body().toString(), DetailBean.class);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        travelJson = response.body().toString();
                    }

                    @Override
                    public void onError(Response<String> response) {

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
//                        mLocationClient.stop();
//                        mLocationClient.unRegisterLocationListener(myListener);
                    }
                });
    }

    // 设置位置提醒的条件
    private Handler notifyHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (lng_lat_nameList != null) {
                if (!lng_lat_nameList.contains(notice_name)) {
                    if (latitude_notice != 0 && longitude_notice != 0) {
//                        mNotifyLister.SetNotifyLocation(latitude_baidu, longitude_baidu, 30, mLocationClient.getLocOption().getCoorType());
                        Log.e("aaa4", latitude_baidu + "aaaa3" + longitude_baidu);
                    }
                    for (int i = 0; i < lng_lat_nameList.size(); i++) {
                        Log.e("notice", lng_lat_nameList.size() + "==" + lng_lat_nameList.get(i));
                    }
//                    isSendBroadcast = false;
                }
            }
        }

    };

    public void sendBroadcast() {
        mVibrator.vibrate(1000);//振动提醒已到设定位置附近
        Log.e("定位", "定位在附近");
        // 发送广播给 景区导览 apk
        final Intent intent = new Intent("com.aibabel.broadcast.noticelocation");
        if (!isSendBroadcast) {
            lng_lat_nameList.add(notice_name);
            intent.putExtra("audioUrl", audios);
            intent.putExtra("imgUrl", imgUrl);
            intent.putExtra("name", notice_name);
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

            sendBroadcast(intent);
            isSendBroadcast = true;
        }
    }

    //位置提醒
    public class NotifyLister extends BDNotifyListener {
        public void onNotify(BDLocation mlocation, float distance) {
            mVibrator.vibrate(1000);//振动提醒已到设定位置附近
            Log.e("定位", "定位在附近");
            // 发送广播给 景区导览 apk
            final Intent intent = new Intent("com.aibabel.broadcast.noticelocation");
            if (!isSendBroadcast) {
                lng_lat_nameList.add(notice_name);
                intent.putExtra("audioUrl", audios);
                intent.putExtra("imgUrl", imgUrl);
                intent.putExtra("name", notice_name);
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

                if (TextUtils.equals((CommonUtils.getDeviceInfo().substring(0, 2)), "PL")){
                    sendBroadcast(intent);
                }
//                isSendBroadcast = true;
            }
        }
    }

    @Override
    public boolean stopService(Intent name) {
        //注销屏幕监听广播
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
        stopLoaction();//停止定位服务
        stopRequst();//停止网络请求

        super.onDestroy();
    }

    public LatLng zhungHuan_BD(double lat_z, double lng_z) {
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.COMMON);

        // sourceLatLng待转换坐标
        LatLng sourceLatLng = new LatLng(lat_z, lng_z);
        converter.coord(sourceLatLng);
        LatLng desLatLng = converter.convert();


        return desLatLng;
    }

//    public LatLng zhungHuan_GPS(double lat_jps, double lng_jps) {
//        // 将GPS设备采集的原始GPS坐标转换成百度坐标
//
//        CoordinateConverter converter = new CoordinateConverter();
//        converter.from(CoordinateConverter.CoordType.GPS);
//
//        // sourceLatLng待转换坐标
//        LatLng sourceLatLng = new LatLng(lat_jps, lng_jps);
//        converter.coord(sourceLatLng);
//        LatLng desLatLng = converter.convert();
//        return desLatLng;
//    }
}