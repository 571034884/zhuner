package com.aibabel.map.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.aibabel.map.R;
import com.aibabel.map.views.MapRipple;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fytworks on 2018/11/15.
 * 测试类
 */

public class LocationActivity extends Activity implements SensorEventListener {

    private static final String TAG = "LocationActivity";
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LatLng latLng;
    private MyLocationData locData;
    private LocationMode mCurrentMode;
    private SensorManager mSensorManager;
    BitmapDescriptor mCurrentMarker;

    //测试maker
    private FrameLayout content_main;
    private ImageView iv6;
    private ImageView iv5;
    private ImageView iv4;
    private ImageView iv3;
    private ImageView iv2;
    private ImageView iv1;
    private FrameLayout fl0;
    private List<ImageView> imageViews = new ArrayList<>();
    private final int radius1 = 150;
    boolean isFirstLoc = true; // 是否首次定位
    private MapRipple mapRipple;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//屏蔽系统自带标题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_location);
        mMapView = findViewById(R.id.mmap);
        mBaiduMap = mMapView.getMap();


//        iv7= (ImageView) findViewById(R.id.iv7);
//        imageViews.add(iv7);
        iv6 = (ImageView) findViewById(R.id.iv_cate);
        imageViews.add(iv6);
        iv5 = (ImageView) findViewById(R.id.iv_metro);
        imageViews.add(iv5);
        iv4 = (ImageView) findViewById(R.id.iv_shop);
        imageViews.add(iv4);
        iv3 = (ImageView) findViewById(R.id.iv_scenic);
        imageViews.add(iv3);
        iv2 = (ImageView) findViewById(R.id.iv_all);
        imageViews.add(iv2);
        iv1 = (ImageView) findViewById(R.id.iv1);
        fl0= (FrameLayout) findViewById(R.id.fl0);

        //定位
        initLocationOption();
    }

    /**
     * 初始化定位参数配置
     */
    LocationClient mLocClient;

    private void initLocationOption() {

        //去掉百度Log
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //打开室内图，默认为关闭状态
        mBaiduMap.setIndoorEnable(true);
        //不显示缩放控件
        mMapView.showZoomControls(false);
        //定位跟随  FOLLOWING//定位跟随态    NORMAL//默认为普通态     COMPASS//定位罗盘态
        mCurrentMode = LocationMode.NORMAL;
        //自定义图标mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
        mCurrentMarker = null;


        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(new MyLocationListener());
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);//自动定位 定位间隔要大于1000ms   默认0只定位一次
        mLocClient.setLocOption(option);
        mLocClient.start();

    }

    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private float mCurrentAccracy;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_cate:
                chageIcon(R.mipmap.ic_cate);
                break;
            case R.id.iv_metro:
                chageIcon(R.mipmap.ic_metro);
                break;
            case R.id.iv_scenic:
                chageIcon(R.mipmap.ic_scenic);
                break;
            case R.id.iv_shop:
                chageIcon(R.mipmap.ic_shop);
                break;
            case R.id.iv_all:
                chageIcon(R.mipmap.ic_add);
                break;

        }
    }

    private void chageIcon(int res){
        iv1.setTag(false);
        mapRipple.startRippleMapAnimation();
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(res);
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker, 0, 0));
        closeCircleMenu();
    }
        /**
         * 显示圆形菜单
         */

        private void showCircleMenu() {
            for (int i = 0; i < imageViews.size(); i++) {
                PointF point = new PointF();
                int avgAngle = (360 / (imageViews.size()));
                int angle = avgAngle * i;
                Log.d(TAG, "angle=" + angle);
                point.x = (float) Math.cos(angle * (Math.PI / 180)) * radius1;
                point.y = (float) Math.sin(angle * (Math.PI / 180)) * radius1;
                Log.d(TAG, point.toString());
                ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imageViews.get(i), "translationX", 0, point.x);
                ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageViews.get(i), "translationY", 0, point.y);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(500);
                animatorSet.play(objectAnimatorX).with(objectAnimatorY);
                animatorSet.start();
            }
        }

        /**
         * 关闭圆形菜单
         */

        private void closeCircleMenu() {
            for (int i = 0; i < imageViews.size(); i++) {
                PointF point = new PointF();
                int avgAngle = (360 / (imageViews.size()));
                int angle = avgAngle * i;
                Log.d(TAG, "angle=" + angle);
                point.x = (float) Math.cos(angle * (Math.PI / 180)) * radius1;
                point.y = (float) Math.sin(angle * (Math.PI / 180)) * radius1;

                Log.d(TAG, point.toString());
                ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imageViews.get(i), "translationX", point.x, 0);
                ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageViews.get(i), "translationY", point.y, 0);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(500);
                animatorSet.play(objectAnimatorX).with(objectAnimatorY);
                animatorSet.start();
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        fl0.setVisibility(View.GONE);
                        UiSettings settings=mBaiduMap.getUiSettings();
                        settings.setAllGesturesEnabled(true);   //一切手势操作
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }


        }


    /**
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {



        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.ic_add);

//            BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromView(markView);
            mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker, 0, 0));

            mBaiduMap.setOnMyLocationClickListener(new BaiduMap.OnMyLocationClickListener() {
                @Override
                public boolean onMyLocationClick() {

                    Boolean isShowing = (Boolean) iv1.getTag();
                    if (null == isShowing || isShowing == false) {
                        latLng = new LatLng(mCurrentLat, mCurrentLon);
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.target(latLng).zoom(18.0f);
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                        mapRipple.stopRippleMapAnimation();
//                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv1, "rotation", 0, 45);
//                        objectAnimator.setDuration(500);
//                        objectAnimator.start();
                        iv1.setTag(true);
                        fl0.setVisibility(View.VISIBLE);
                        UiSettings settings=mBaiduMap.getUiSettings();
                        settings.setAllGesturesEnabled(false);   //关闭一切手势操作
                        showCircleMenu();

                    } else {
                        iv1.setTag(false);
//                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv1, "rotation", 45, 0);
//                        objectAnimator.setDuration(500);
//                        objectAnimator.start();
                        closeCircleMenu();
                        mapRipple.startRippleMapAnimation();

                    }
//                    Toast.makeText(LocationActivity.this, "=============gogogo===========", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
            LatLng ll = new LatLng(0, 0);
            if (isFirstLoc) {
                isFirstLoc = false;
                ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }


            mapRipple = new MapRipple(mBaiduMap, ll, LocationActivity.this)
                    .withNumberOfRipples(2)
                    .withFillColor(Color.parseColor("#ff0000"))
                    .withStrokeColor(Color.BLACK)
                    .withStrokeWidth(1)
                    .withDistance(500)      // 2000 metres radius
                    .withRippleDuration(6000)    //12000ms
                    .withTransparency(0.18f);



            mapRipple.startRippleMapAnimation();


        }
    }





    //===============================以下生命周期==============================

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();

        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }
}
