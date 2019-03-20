package com.aibabel.map;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.base.BaseApplication;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.impl.IDataManager;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.baselibrary.utils.XIPCUtils;
import com.aibabel.map.activity.ActiveActivity;
import com.aibabel.map.activity.DialogActivity;
import com.aibabel.map.activity.RouteLineActivity;
import com.aibabel.map.activity.SearchAddressActivity;
import com.aibabel.map.adapter.CardPagerAdapter;
import com.aibabel.map.base.MapBaseActivity;
import com.aibabel.map.bean.BusinessBean;
import com.aibabel.map.bean.LocationBean;
import com.aibabel.map.bean.RouteBean;
import com.aibabel.map.bean.search.AddressResult;
import com.aibabel.map.utils.ApiConstant;
import com.aibabel.map.utils.BaiDuConstant;
import com.aibabel.map.utils.BaiDuUtil;
import com.aibabel.map.utils.CommonUtils;
import com.aibabel.map.views.CheckRadioButton;
import com.aibabel.map.views.MapRipple;
import com.aibabel.map.views.ShadowTransformer;
import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.orhanobut.logger.Logger;
import com.xuexiang.xipc.XIPC;
import com.xuexiang.xipc.core.channel.IPCListener;
import com.xuexiang.xipc.core.channel.IPCService;

import org.litepal.LitePal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.xuexiang.xipc.XIPC.getContext;

public class MainActivity extends MapBaseActivity implements SensorEventListener, BaiduMap.OnMarkerClickListener, CardPagerAdapter.onClickListener {

    private String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.vp_info)
    ViewPager vpInfo;
    @BindView(R.id.rv_info)
    RecyclerView rvInfo;
    @BindView(R.id.tv_location)
    CheckBox tvLocation;
    @BindView(R.id.tv_activity)
    ImageView tvActivity;
    @BindView(R.id.mv_map)
    MapView mMapView;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.cb_cate)
    CheckRadioButton mCate;
    @BindView(R.id.cb_scenic)
    CheckRadioButton mScenic;
    @BindView(R.id.cb_shop)
    CheckRadioButton mShop;
    @BindView(R.id.cb_metro)
    CheckRadioButton mMetro;
    @BindView(R.id.main_close_app)
    ImageView mCloseApp;

    BaiduMap mBaiduMap;

    MyLocationData locData;
    MyLocationConfiguration.LocationMode mCurrentMode;
    int mCurrentDirection = 0;
    boolean isFirstLoc = true; // 是否首次定位
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private Double lastX = 0.0;
    private BitmapDescriptor mMarker;
    private String tag;
    private List<BusinessBean.DataBean> list = new ArrayList<>();
    private List<Marker> markers;
    private int currentId;
    private int preId;
    private List<OverlayOptions> options;
    SensorManager mSensorManager;
    BDLocation mLocation;

    @Override
    public int getLayoutMap(Bundle bundle) {
        return R.layout.activity_main;
    }

    @Override
    public void initMap() {
        //热修复
        setHotRepairEnable(true);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        initViewPage();
        initLocationOption();
        AnimationDrawable animDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.home_gif_anim);
        tvActivity.setImageDrawable(animDrawable);
        animDrawable.start();

        initListener();
    }

    private void initListener() {
        mCate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!CommonUtils.isFastClick()){
                    mCate.setChecked(false);
                    return;
                }
                if (!CommonUtils.isNetworkAvailable(mContext)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    mCate.setChecked(false);
                    return;
                }

                if (mLocation == null){
                    ToastUtil.showShort(mContext,"定位失败");
                    mCate.setChecked(false);
                    return;
                }
                Map map = new HashMap();
                map.put("type","cate");
                if (isChecked){
                    StatisticsManager.getInstance(mContext).addEventAidl(1203, map);
                    tag = "cate";
                    mMetro.setChecked(false);
                    mScenic.setChecked(false);
                    mShop.setChecked(false);
                    getPoi(mCate);
                }else{
                    StatisticsManager.getInstance(mContext).addEventAidl(1204, map);
                    clear();
                }
            }
        });

        mMetro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!CommonUtils.isNetworkAvailable(mContext)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    mMetro.setChecked(false);
                    return;
                }

                if (mLocation == null){
                    ToastUtil.showShort(mContext,"定位失败");
                    mMetro.setChecked(false);
                    return;
                }
                Map map = new HashMap();
                map.put("type","metro");
                if (isChecked){
                    StatisticsManager.getInstance(mContext).addEventAidl(1203, map);
                    tag = "metro";
                    mCate.setChecked(false);
                    mScenic.setChecked(false);
                    mShop.setChecked(false);
                    getPoi(mMetro);
                }else{
                    StatisticsManager.getInstance(mContext).addEventAidl(1204, map);
                    clear();
                }
            }
        });

        mScenic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!CommonUtils.isNetworkAvailable(mContext)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    mScenic.setChecked(false);
                    return;
                }

                if (mLocation == null){
                    ToastUtil.showShort(mContext,"定位失败");
                    mScenic.setChecked(false);
                    return;
                }
                Map map = new HashMap();
                map.put("type","scenic");
                if (isChecked){
                    StatisticsManager.getInstance(mContext).addEventAidl(1203, map);
                    tag = "scenic";
                    mCate.setChecked(false);
                    mMetro.setChecked(false);
                    mShop.setChecked(false);
                    getPoi(mScenic);
                }else{
                    StatisticsManager.getInstance(mContext).addEventAidl(1204, map);
                    clear();
                }
            }
        });

        mShop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!CommonUtils.isNetworkAvailable(mContext)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    mShop.setChecked(false);
                    return;
                }

                if (mLocation == null){
                    ToastUtil.showShort(mContext,"定位失败");
                    mShop.setChecked(false);
                    return;
                }
                Map map = new HashMap();
                map.put("type","shop");
                if (isChecked){
                    StatisticsManager.getInstance(mContext).addEventAidl(1203, map);
                    tag = "shop";
                    mCate.setChecked(false);
                    mMetro.setChecked(false);
                    mScenic.setChecked(false);
                    getPoi(mShop);
                }else{
                    StatisticsManager.getInstance(mContext).addEventAidl(1204, map);
                    clear();
                }
            }
        });
        mCloseApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseApplication.exit();
            }
        });
    }

    @Override
    public void receiveLocation(BDLocation location) {
        if (mMapView == null) {
            return;
        }
        mLocation = location;
//        Log.e("MainActivity-LatLon", mLocation.getLatitude() + "," + mLocation.getLongitude());
        setLocData();
        if (isFirstLoc) {
            mScenic.setChecked(true);
            isFirstLoc = false;
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
//            LatLng ll = new LatLng(35.714764, 139.796665);
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(14.5f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
    }

    /**
     * viewpage 设置滑动监听
     */
    private void initViewPage() {
        vpInfo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                preId = currentId;
                currentId = i;
                drawMarker(preId, currentId);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    private void drawMarker(int preId, int currentId) {

        String category = (String) (markers.get(currentId).getExtraInfo().get("type"));
        // TODO: 2018/12/13 修改当前marker
        View curView = LayoutInflater.from(MainActivity.this).inflate(R.layout.marker, null);
        TextView tv_current = curView.findViewById(R.id.tv_index);
        ImageView iv_current = curView.findViewById(R.id.iv_marker);
        // TODO: 2018/12/13 修改上一个marker
        View preView = LayoutInflater.from(MainActivity.this).inflate(R.layout.marker, null);
        TextView tv_pre = preView.findViewById(R.id.tv_index);
        ImageView iv_pre = preView.findViewById(R.id.iv_marker);

        switch (category) {
            case "cate":
                tv_current.setTextColor(Color.parseColor("#ff7e00"));
                iv_current.setImageResource(R.mipmap.marker_cate_select);
                iv_pre.setImageResource(R.mipmap.marker_cate_normal);
                break;
            case "metro":
                tv_current.setTextColor(Color.parseColor("#0095d3"));
                iv_current.setImageResource(R.mipmap.marker_metro_select);
                iv_pre.setImageResource(R.mipmap.marker_metro_normal);
                break;
            case "scenic":
                iv_current.setImageResource(R.mipmap.marker_scenic_select);
                tv_current.setTextColor(Color.parseColor("#1ed200"));
                iv_pre.setImageResource(R.mipmap.marker_scenic_normal);
                break;
            case "shop":
                tv_current.setTextColor(Color.parseColor("#0bb7ff"));
                iv_current.setImageResource(R.mipmap.marker_shop_select);
                iv_pre.setImageResource(R.mipmap.marker_shop_normal);
                break;
        }

        double lat = markers.get(currentId).getPosition().latitude;
        double lng = markers.get(currentId).getPosition().longitude;
        changeZoom(lat, lng);
        if(currentId+1>10){
            tv_current.setText("");
        }else{
            tv_current.setText((currentId + 1) + "");
        }
        BitmapDescriptor curMarker = BitmapDescriptorFactory.fromView(curView);
        markers.get(currentId).setIcon(curMarker);
        if(preId+1>10){
            tv_pre.setText("");
        }else{
            tv_pre.setText((preId + 1) + "");
        }
        BitmapDescriptor preMarker = BitmapDescriptorFactory.fromView(preView);
        markers.get(preId).setIcon(preMarker);
        //在地图上批量添加
    }


    /**
     * 初始化定位参数配置
     */
    public void initLocationOption() {
        mBaiduMap = mMapView.getMap();
        //去掉百度Log
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //打开室内图，默认为关闭状态
        mBaiduMap.setIndoorEnable(false);
        //设置marker监听
        mBaiduMap.setOnMarkerClickListener(this);
        //不显示比例尺
        mMapView.showScaleControl(false);
        //不显示缩放控件
        mMapView.showZoomControls(false);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    }

    /**
     * 点击事件
     *
     * @param view
     */
    public void onClick(View view) {
        Map map = new HashMap();
        switch (view.getId()) {
            case R.id.tv_location:
                if (!CommonUtils.isNetworkAvailable(this)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }
                StatisticsManager.getInstance(mContext).addEventAidl( 1205, map);

                updateLocation();
                break;
            case R.id.tv_search:
                if (!CommonUtils.isNetworkAvailable(this)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }
                if (mLocation == null){
                    ToastUtil.showShort(mContext,"定位失败");
                    return;
                }

                StatisticsManager.getInstance(mContext).addEventAidl( 1206, map);
                Intent intent = new Intent(mContext, SearchAddressActivity.class);
                //TODO 存储当前位置
                List<AddressResult> resultList = LitePal.findAll(AddressResult.class, true);
                if (resultList.size() == 0){
                    AddressResult result = new AddressResult();
                    result.setAddr(mLocation.getAddrStr());
                    result.setCity(mLocation.getCity());
                    result.setCityid(mLocation.getCityCode());
                    result.setDistrict(mLocation.getDistrict());
                    result.setName("我的位置");
                    result.setProvince(mLocation.getProvince());
                    result.setLocation(new LocationBean(mLocation.getLatitude(),mLocation.getLongitude()));
                    result.setUid("");
                    result.setTag("");
                    result.save();
                    result.getLocation().save();
                }
                startActivity(intent);
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                break;
            case R.id.tv_activity:

                if (!CommonUtils.isNetworkAvailable(this)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }

                if (mLocation != null) {
                    StatisticsManager.getInstance(mContext).addEventAidl(1201, new HashMap());

                    Intent intents = new Intent(mContext, ActiveActivity.class);
                    intents.putExtra("locationWhere",mLocation.getLocationWhere()+"");
                    startActivity(intents);
                } else {
                    ToastUtil.showShort(mContext, "定位失败,请重试");
                }
                break;
        }

    }

    /**
     * 更新定位
     */
    private void updateLocation() {
        if (mLocation == null){
            ToastUtil.showShort(mContext,"定位中...");
            return;
        }

        LatLng ll = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(14.5f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        //以下是定位类型
//        switch (mCurrentMode) {
//            case NORMAL:
//                mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
//                ToastUtil.showShort(mContext,"进入罗盘模式");
//                Log.e("mCurrentMode","NORMAL---->"+"COMPASS");
//
//                mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
//                        mCurrentMode, true, null));
//                builder.target(ll).zoom(18f).overlook(0);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//
//                break;
//            case COMPASS:
//                mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
//                ToastUtil.showShort(mContext,"进入跟随模式");
//                Log.e("mCurrentMode","COMPASS---->"+"FOLLOWING");
//
//                mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
//                        mCurrentMode, true, null));
//                builder.target(ll).zoom(18f).overlook(0);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                break;
//            case FOLLOWING:
//                mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
//                ToastUtil.showShort(mContext,"进入普通模式");
//                Log.e("mCurrentMode","FOLLOWING---->"+"NORMAL");
//
//                mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
//                        mCurrentMode, true, null));
//                builder.target(ll).zoom(14f).overlook(0);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                break;
//        }
    }

    /**
     * 查找poi
     * @param cb
     */
    private void getPoi(CheckRadioButton cb) {
        Map<String, String> map = new HashMap<>();
        map.put("tag", tag);
        map.put("radius", BaiDuConstant.accuracyCircleSize+"");
        map.put("location", mLocation.getLatitude() + "," + mLocation.getLongitude());
        map.put("coord_type", mLocation.getCoorType());
        map.put("locationWhere", mLocation.getLocationWhere() + "");

//        map.put("location", "35.714764,139.796665");
//        map.put("coord_type", "wgs84");
//        map.put("locationWhere", "0");

        OkGoUtil.get(mContext, ApiConstant.API_POI, map, BusinessBean.class, new BaseCallback<BusinessBean>() {
            @Override
            public void onSuccess(String s, BusinessBean businessBean, String s1) {
                Logger.e(s1);
                if (null != businessBean) {
                    if (null != businessBean.getData() && businessBean.getData().size() > 0) {
                        clear();
                        list = businessBean.getData();
                        // TODO: 向页面上画标记
                        drawAllMarker(list, 0);
                        // TODO: 显示返回结果
                        mCardAdapter = new CardPagerAdapter(businessBean.getData(), MainActivity.this);
                        mCardShadowTransformer = new ShadowTransformer(vpInfo, mCardAdapter);
                        vpInfo.setAdapter(mCardAdapter);
                        mCardAdapter.setOnItemClickListener(MainActivity.this);
                        mCardShadowTransformer.enableScaling(true);
                        vpInfo.setVisibility(View.VISIBLE);
                        //更新位置到当前
                        updateLocation();
                    }
                } else {
                    ToastUtil.showShort(MainActivity.this, "抱歉当前没有数据！");
                    clear();
                    vpInfo.setVisibility(View.GONE);
                    cb.setChecked(false);
                }


            }

            @Override
            public void onError(String s, String s1, String s2) {
                clear();
                cb.setChecked(false);
                vpInfo.setVisibility(View.GONE);
                ToastUtil.showShort(MainActivity.this, "抱歉当前没有数据！");
                // TODO: 请求失败后，动画停止
            }

            @Override
            public void onFinsh(String s) {

            }
        });

    }


    /**
     * 清除地图上的marker，并清空list数据
     */
    private void clear() {
        if (null != list)
            list.clear();
        if (null != markers)
            markers.clear();
        if (null != options)
            options.clear();
        vpInfo.setVisibility(View.GONE);
        mBaiduMap.clear();
    }

    /**
     * 在地图绘制多个点
     *
     * @param list
     */
    private void drawAllMarker(List<BusinessBean.DataBean> list, int index) {
        mBaiduMap.clear();
        currentId = 0;
        preId = 0;
        if (null != list && list.size() > 0) {
            options = new ArrayList<OverlayOptions>();
            markers = new ArrayList<>();
            //设置坐标点
            for (int i = 0; i < list.size(); i++) {
                BusinessBean.DataBean bean = list.get(i);
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.marker, null);
                TextView tv_index = view.findViewById(R.id.tv_index);
                ImageView iv_marker = view.findViewById(R.id.iv_marker);
                if (i < 10){
                    tv_index.setText((i + 1) + "");
                }
                switch (bean.getTagType()) {
                    case "cate":
                        iv_marker.setImageResource(R.mipmap.marker_cate_normal);
                        if (i == index) {
                            tv_index.setTextColor(Color.parseColor("#ff7e00"));
                            iv_marker.setImageResource(R.mipmap.marker_cate_select);
                        }
                        mMarker = BitmapDescriptorFactory.fromView(view);
                        break;
                    case "metro":
                        iv_marker.setImageResource(R.mipmap.marker_metro_normal);
                        if (i == index) {
                            tv_index.setTextColor(Color.parseColor("#0095d3"));
                            iv_marker.setImageResource(R.mipmap.marker_metro_select);
                        }
                        mMarker = BitmapDescriptorFactory.fromView(view);
                        break;
                    case "scenic":
                        iv_marker.setImageResource(R.mipmap.marker_scenic_normal);
                        if (i == index) {
                            tv_index.setTextColor(Color.parseColor("#1ed200"));
                            iv_marker.setImageResource(R.mipmap.marker_scenic_select);
                        }
                        mMarker = BitmapDescriptorFactory.fromView(view);
                        break;
                    case "shop":
                        iv_marker.setImageResource(R.mipmap.marker_shop_normal);
                        if (i == index) {
                            tv_index.setTextColor(Color.parseColor("#0bb7ff"));
                            iv_marker.setImageResource(R.mipmap.marker_shop_select);
                        }
                        mMarker = BitmapDescriptorFactory.fromView(view);
                        break;
                }
                Bundle mBundle = new Bundle();
                mBundle.putInt("id", i);
                mBundle.putString("type", bean.getTagType());
                LatLng point = new LatLng(bean.getLat(), bean.getLng());
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .extraInfo(mBundle)
                        .icon(mMarker);

                options.add(option);
                //在地图上批量添加
                if (null != mBaiduMap) {
                    Marker marker = (Marker) mBaiduMap.addOverlay(option);

                    markers.add(marker);

                }

            }


        }

    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        Logger.e(marker.toString());
        Bundle bundle = marker.getExtraInfo();
        int id = bundle.getInt("id");
        double lat = marker.getPosition().latitude;
        double lng = marker.getPosition().longitude;
        changeZoom(lat, lng);

        vpInfo.setCurrentItem(id);


        return false;
    }

    /**
     * 修改定位缩放级别
     *
     * @param latitude
     * @param longitude
     */
    private void changeZoom(Double latitude, Double longitude) {
        LatLng ll = new LatLng(latitude, longitude);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(17);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
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


    @Override
    protected void onDestroy() {
        // 退出时销毁定位
//        locService.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }


    @Override
    public void onItemClick(BusinessBean.DataBean item, View view) {

        if(!CommonUtils.isFastClick())return;
        if (TextUtils.equals(item.getTagType(), "metro")) {
            // TODO: 2018/12/10
            Intent intent = new Intent(this, RouteLineActivity.class);
            RouteBean bean = new RouteBean();
            /**
             * DRIVING_MODE 驾车
             * TRANSIT_MODE 公交
             * WALKING_MODE 步行
             */
            bean.setIndex(BaiDuConstant.TRANSIT_MODE);
            bean.setStartName("我的位置");//我的位置
            bean.setStartLoc(new LocationBean(mLocation.getLatitude(), mLocation.getLongitude()));
            bean.setEndName(item.getNameCh());
            bean.setEndLoc(new LocationBean(item.getLat(), item.getLng()));
            bean.setMode(BaiDuUtil.getModeType(BaiDuConstant.TRANSIT_MODE));
            bean.setCoord_type(mLocation.getCoorType());
            bean.setLocationWhere(mLocation.getLocationWhere());
            bean.setCity(mLocation.getAddress().city);
            intent.putExtra("routes", bean);
            startActivity(intent);
        } else {

            Map map = new HashMap();
            map.put("type", item.getTagType());
            map.put("scenicName", item.getNameCh());
            map.put("scenicAddr", item.getAddress());
            StatisticsManager.getInstance(mContext).addEventAidl(1213, map);

            Intent intent = new Intent(this, DialogActivity.class);
            intent.putExtra("data", item);
            intent.putExtra("mCurrentLat", mLocation.getLatitude());
            intent.putExtra("mCurrentLon", mLocation.getLongitude());
            intent.putExtra("coord_type", mLocation.getCoorType());
            intent.putExtra("locationWhere", mLocation.getLocationWhere());
            intent.putExtra("city", mLocation.getAddress().city);
            startActivity(intent);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            setLocData();
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 配置经纬度
     * 精度圈大小
     * 精度圈颜色
     */
    private void setLocData(){
        if (mLocation == null){
            return;
        }
        locData = new MyLocationData.Builder()
                //精度圈
                .accuracy(BaiDuConstant.accuracyCircleSize)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(mCurrentDirection)
                //经纬度
                .latitude(mLocation.getLatitude()).longitude(mLocation.getLongitude()).build();
//                .latitude(35.68107).longitude(139.766967).build();
        mBaiduMap.setMyLocationData(locData);
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, null, BaiDuConstant.accuracyCircleFillColor, BaiDuConstant.accuracyCircleStrokeColor));
    }

}
