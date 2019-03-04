package com.aibabel.map.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.map.R;
import com.aibabel.map.activity.TrafficDetailActivity;
import com.aibabel.map.adapter.MyGridAdapter;
import com.aibabel.map.bean.LocationBean;
import com.aibabel.map.bean.RouteBean;
import com.aibabel.map.bean.trafficen.EnBean;
import com.aibabel.map.bean.trafficen.RoutesEnBean;
import com.aibabel.map.bean.trafficzh.RoutesZhBean;
import com.aibabel.map.bean.trafficzh.ZhBean;
import com.aibabel.map.route.MyTrafficRouteOverlayEn;
import com.aibabel.map.route.MyTrafficRouteOverlayZh;
import com.aibabel.map.route.OverlayManager;
import com.aibabel.map.utils.BaiDuStatusUtil;
import com.aibabel.map.utils.BaiDuUtil;
import com.aibabel.map.utils.CommonUtils;
import com.aibabel.map.utils.MathUtil;
import com.aibabel.map.views.MyGridView;
import com.baidu.location.Address;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by fytworks on 2018/12/26.
 * 后期修改 使用GridView
 */

@SuppressLint("ValidFragment")
public class DriveFragment extends BaseFragment implements SensorEventListener ,View.OnClickListener{
    private Context mContext;
    RouteBean routeBean;

    @BindView(R.id.mv_map)
    TextureMapView mMapView;
    @BindView(R.id.error_txt)
    TextView mTextError;
    @BindView(R.id.ll_error)
    LinearLayout mLlError;
    @BindView(R.id.my_grid)
    MyGridView myGrid;
    @BindView(R.id.rl_view)
    RelativeLayout mRlView;
    @BindView(R.id.rl_less)
    RelativeLayout mRlOneLess;
    @BindView(R.id.tv_details)
    TextView mTvDetails;
    @BindView(R.id.tv_less_min)
    TextView mTvLessMin;
    @BindView(R.id.tv_less_km)
    TextView mTvLessKm;

    OverlayManager routeOverlay = null;
    BaiduMap mBaiduMap;
    SensorManager mSensorManager;
    BitmapDescriptor mCurrentMarker;
    LocationMode mCurrentMode;
    LocationClient mLocClient;
    MyLocationData locData;
    Double lastX = 0.0;
    int mCurrentDirection = 0;
    float mCurrentAccracy;
    double mCurrentLat = 0.0;
    double mCurrentLon = 0.0;
    boolean isFirstLoc = true; // 是否首次定位flag
    boolean IS_FRIST_LOCATION = true; //第一次进入页面 定位成功后，自动进行路线规划
    Address address;//定位成功的信息
    ZhBean driveZhBean = null;
    EnBean driveEnBean = null;
    private MyGridAdapter gridAdapter = null;
    private int index = 0;

    @SuppressLint("ValidFragment")
    public DriveFragment(Bundle bundle) {
        routeBean = bundle.getParcelable("routeBean");
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_drive;
    }

    @Override
    public void init(View view, Bundle bundle) {
        mContext = getContext();
        mLlError.setVisibility(View.GONE);
        mRlView.setVisibility(View.GONE);
        mTvDetails.setOnClickListener(this);
        //地图初始化
        initBaiDuOption();
        initListener();
    }

    private void initListener() {
        myGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!CommonUtils.isNetworkAvailable(mContext)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }


                mBaiduMap.clear();
                index = position;
                switch (routeBean.getLocationWhere()){
                    case 0://国外
                        routeLineTrafficEn(driveEnBean.getData().getResult().getRoutes().get(position), driveEnBean.getData().getResult().getRoutes().get(position).getOrigin(), driveEnBean.getData().getResult().getRoutes().get(position).getDestination());
                        break;
                    case 1://国内
                        routeLineTrafficZh(driveZhBean.getData().getResult().getRoutes().get(position), driveZhBean.getData().getResult().getOrigin(), driveZhBean.getData().getResult().getDestination());
                        break;
                }

                gridAdapter.setSeclection(position);
                gridAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initBaiDuOption() {
        mBaiduMap = mMapView.getMap();
        //去掉百度Log
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        //缩放控件
        mMapView.showZoomControls(false);
        //比例尺
        mMapView.showScaleControl(false);
        mSensorManager = (SensorManager)getActivity().getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //打开室内图，默认为关闭状态
        mBaiduMap.setIndoorEnable(false);
        //定位跟随  FOLLOWING//定位跟随态    NORMAL//默认为普通态     COMPASS//定位罗盘态
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        //自定义图标mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
        mCurrentMarker = null;
        //以下定位相关
        mLocClient = new LocationClient(getContext());
        mLocClient.registerLocationListener(new MyLocationListener());
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);//自动定位 定位间隔要大于1000ms   默认0只定位一次
        option.setIsNeedLocationDescribe(true);//需要位置信息
        option.setIsNeedAddress(true);//地址信息
        mLocClient.setLocOption(option);
        mLocClient.start();
    }
//-------------------------------实现-------------------------------
    @Override
    public void onSensorChanged(SensorEvent event) {
        double x = event.values[SensorManager.DATA_X];
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

    /**
     * 暴露方法
     * @param routes
     */
    public void changeFramgnet(RouteBean routes){
        mBaiduMap.clear();
        routeBean = routes;
        obtainApi();
    }


    private void obtainApi() {

        Map map = new HashMap();
        map.put("startLatLon",routeBean.getStartLoc().getLat()+","+routeBean.getStartLoc().getLng());
        map.put("startName",routeBean.getStartName());
        map.put("endLatLon",routeBean.getEndLoc().getLat()+","+routeBean.getEndLoc().getLng());
        map.put("endName",routeBean.getEndName());
        map.put("type",routeBean.getMode());
        StatisticsManager.getInstance(mContext).addEventAidl(1210,map);

        String url = BaiDuUtil.getUrl(routeBean.getLocationWhere(), routeBean.getIndex());
        Map param = new HashMap();
        param.put("origin", routeBean.getStartLoc().getLat()+ "," + routeBean.getStartLoc().getLng());
        param.put("destination", routeBean.getEndLoc().getLat()+ "," + routeBean.getEndLoc().getLng());
        param.put("mode", routeBean.getMode());
        param.put("coord_type", routeBean.getCoord_type());
        param.put("locationWhere", routeBean.getLocationWhere()+"");
        OkGoUtil.get(mContext,url, param, BaseBean.class, new BaseCallback() {
            @Override
            public void onSuccess(String s, BaseBean baseBean, String s1) {
                index = 0;
                trafficType(s1);
            }

            @Override
            public void onError(String s, String s1, String s2) {
                mLlError.setVisibility(View.VISIBLE);
                mRlView.setVisibility(View.GONE);
                iconShow(505050);
                showStartEnd();
            }

            @Override
            public void onFinsh(String s) {
            }
        });
    }

    /**
     * 使用GridView优化
     *     正常情况下 单个adapter
     *      ---- 0
     *              ----没有数据的情况下
     *      ---- 1
     *              ----单独布局
     *      ---- 2
     *              ----双布局
     *      ---- >2
     *              ----双布局(仅展示前2)
     *     异常情况下 单个adapter
     *      具体情况看code显示文案和icon
     *
     * @param body
     */
    private void trafficType(String body) {
        switch (routeBean.getLocationWhere()){
            case 0:
                driveEnBean = FastJsonUtil.changeJsonToBean(body, EnBean.class);
                if (driveEnBean.getData() != null) {
                    if (driveEnBean.getData().getStatus() == 0) {
                        mLlError.setVisibility(View.GONE);
                        mRlView.setVisibility(View.VISIBLE);
                        showTrafficSchemeEn(driveEnBean);
                    } else{
                        mLlError.setVisibility(View.VISIBLE);
                        mRlView.setVisibility(View.GONE);
                        iconShow(driveEnBean.getData().getStatus());
                        showStartEnd();
                    }
                }else{
                    mLlError.setVisibility(View.VISIBLE);
                    mRlView.setVisibility(View.GONE);
                    iconShow(101010);
                    showStartEnd();
                }
                break;
            case 1:
                //国内数据解析
                driveZhBean = FastJsonUtil.changeJsonToBean(body, ZhBean.class);
                if (driveZhBean.getData() != null) {
                    if (driveZhBean.getData().getStatus() == 0) {
                        mLlError.setVisibility(View.GONE);
                        mRlView.setVisibility(View.VISIBLE);
                        //数据分析
                        showTrafficSchemeZh(driveZhBean);
                    } else{
                        mLlError.setVisibility(View.VISIBLE);
                        mRlView.setVisibility(View.GONE);
                        iconShow(driveEnBean.getData().getStatus());
                        showStartEnd();
                    }
                }else{
                    mLlError.setVisibility(View.VISIBLE);
                    mRlView.setVisibility(View.GONE);
                    iconShow(101010);
                    showStartEnd();
                }
                break;
        }
    }


    private void showStartEnd(){
        List<OverlayOptions> overlayOptionses = new ArrayList<OverlayOptions>();
        overlayOptionses.add((new MarkerOptions())
                .position(new LatLng(routeBean.getStartLoc().getLat(),routeBean.getStartLoc().getLng()))
                .icon(BitmapDescriptorFactory
                                .fromAssetWithDpi("Icon_start.png")).zIndex(10));
        //终点
        overlayOptionses
                .add((new MarkerOptions())
                        .position(new LatLng(routeBean.getEndLoc().getLat(),routeBean.getEndLoc().getLng()))
                        .icon(BitmapDescriptorFactory
                                        .fromAssetWithDpi("Icon_end.png")).zIndex(10));
        mBaiduMap.addOverlays(overlayOptionses);
    }

    /**
     * 国外
     * 获取数据size
     *      1：横向显示
     *   >=2： gridview显示
     *
     * @param driveEnBean
     */
    private void showTrafficSchemeEn(EnBean driveEnBean) {
        int size = driveEnBean.getData().getResult().getRoutes().size();
        if (size == 1){
            RoutesEnBean routesEnBean = driveEnBean.getData().getResult().getRoutes().get(0);
            //单条
            Log.e("TAGS",size+"");
            myGrid.setVisibility(View.GONE);
            mRlOneLess.setVisibility(View.VISIBLE);
            mTvLessMin.setText(BaiDuUtil.getHoursMin(routesEnBean.getDuration()));
            mTvLessKm.setText(MathUtil.mathDivision(routesEnBean.getDistance(), 1000, 2) + "公里");
            mTvDetails.setTextColor(mContext.getResources().getColor(R.color.c_33));
        }else if (size >= 2){
            //多条
            Log.e("TAGS",size+"");
            myGrid.setVisibility(View.VISIBLE);
            mRlOneLess.setVisibility(View.GONE);
            //TODO 加载gridView
            gridAdapter = new MyGridAdapter(mContext,driveEnBean.getData().getResult().getRoutes(),routeBean.getLocationWhere());
            myGrid.setAdapter(gridAdapter);
        }
        //路线规划 默认第一个路线
        routeLineTrafficEn(driveEnBean.getData().getResult().getRoutes().get(0), driveEnBean.getData().getResult().getRoutes().get(0).getOrigin(), driveEnBean.getData().getResult().getRoutes().get(0).getDestination());

    }

    /**
     * 国内
     * 获取数据size
     *      1：横向显示
     *   >=2： gridview显示
     *
     * @param driveZhBean
     */
    private void showTrafficSchemeZh(ZhBean driveZhBean) {
        //判定有几条路线
        int size = driveZhBean.getData().getResult().getRoutes().size();
        if (size == 1){
            RoutesZhBean driveRoutesZhBean = driveZhBean.getData().getResult().getRoutes().get(0);
            //单条
            Log.e("TAGS",size+"");
            myGrid.setVisibility(View.GONE);
            mRlOneLess.setVisibility(View.VISIBLE);

            mTvLessMin.setText(BaiDuUtil.getHoursMin(driveRoutesZhBean.getDuration()));
            mTvLessKm.setText(MathUtil.mathDivision(driveRoutesZhBean.getDistance(), 1000, 2) + "公里");
            mTvDetails.setTextColor(mContext.getResources().getColor(R.color.c_33));
        }else if (size >= 2){
            //多条
            Log.e("TAGS",size+"");
            myGrid.setVisibility(View.VISIBLE);
            mRlOneLess.setVisibility(View.GONE);

            //TODO 加载gridView
            gridAdapter = new MyGridAdapter(mContext,driveZhBean.getData().getResult().getRoutes(),routeBean.getLocationWhere());
            myGrid.setAdapter(gridAdapter);

        }
        routeLineTrafficZh(driveZhBean.getData().getResult().getRoutes().get(0), driveZhBean.getData().getResult().getOrigin(), driveZhBean.getData().getResult().getDestination());
    }


    //------------------------------------------路线规划
    private void routeLineTrafficEn(RoutesEnBean driveRoutesEnBean, LocationBean origin, LocationBean destination) {
        MyTrafficRouteOverlayEn myDrivingRouteOverlayEn = new MyTrafficRouteOverlayEn(mBaiduMap);
        routeOverlay = myDrivingRouteOverlayEn;
        myDrivingRouteOverlayEn.setData(driveRoutesEnBean, origin.getLat() + "," + origin.getLng(), destination.getLat() + "," + destination.getLng(),routeBean.getIndex());
        myDrivingRouteOverlayEn.addToMap();
        myDrivingRouteOverlayEn.zoomToSpan();
    }
    private void routeLineTrafficZh(RoutesZhBean driveRoutesZhBean, LocationBean origin, LocationBean destination) {
        MyTrafficRouteOverlayZh myDrivingRouteOverlayZh = new MyTrafficRouteOverlayZh(mBaiduMap);
        routeOverlay = myDrivingRouteOverlayZh;
        myDrivingRouteOverlayZh.setData(driveRoutesZhBean, origin.getLat() + "," + origin.getLng(), destination.getLat() + "," + destination.getLng(),routeBean.getIndex());
        myDrivingRouteOverlayZh.addToMap();
        myDrivingRouteOverlayZh.zoomToSpan();
    }

    //----------------------实现--------------------------
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            address = location.getAddress();
            //定位成功后显示我的位置



            routeBean.setCoord_type(location.getCoorType());
            routeBean.setLocationWhere(location.getLocationWhere());
            //-------------------------------
            //TODO 测试
//            routeBean.setCoord_type("wgs84");
//            routeBean.setLocationWhere(0);
            //----------------------------------


            if (IS_FRIST_LOCATION) {
                IS_FRIST_LOCATION = false;
                //TODO 定位成功之后 才可以请求API
                //获取数据
                obtainApi();
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
            mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                    mCurrentMode, true, null, 0, 0));
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(15.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }

    //-------------------生命周期----------------------------
    @Override
    public void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        if (mMapView != null){
            mMapView.onDestroy();
            mMapView = null;
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();

        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

//--------------------------------------------------------

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_details:
                if (!CommonUtils.isNetworkAvailable(mContext)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }

                if (routeBean.getLocationWhere() ==1){
                    if (driveZhBean.getData().getResult().getRoutes().size() == 0){
                        ToastUtil.showShort(mContext,"路线过短，请修改起点或终点");
                        return;
                    }
                }else  if (routeBean.getLocationWhere() == 0){
                    if (driveEnBean.getData().getResult().getRoutes().size() == 0){
                        ToastUtil.showShort(mContext,"路线过短，请修改起点或终点");
                        return;
                    }
                }


                    Map map = new HashMap();
                map.put("type",routeBean.getMode());
                StatisticsManager.getInstance(mContext).addEventAidl(1211,map);

                Intent intent = new Intent(mContext, TrafficDetailActivity.class);
                if (routeBean.getLocationWhere() == 1) {
                    intent.putExtra("json", driveZhBean);//数据
                } else if (routeBean.getLocationWhere() == 0) {
                    intent.putExtra("json", driveEnBean);//数据
                }
                intent.putExtra("locationWhere", routeBean.getLocationWhere());
                intent.putExtra("index", index);//当前第几个方案
                intent.putExtra("startName", routeBean.getStartName());//起点
                intent.putExtra("endName", routeBean.getEndName());//终点
                startActivity(intent);
                break;
        }
    }


    /**
     * 具体的 体现
     * @param index
     */
    private void iconShow(int index){
        switch (index){
            case 0:
                //成功，不会出现，
                break;
            case 1:
            case 505050:
                mTextError.setText("准儿出错了");
                break;
            case 2:
                mTextError.setText("准儿出错了");
                break;
            case 7:
            case 1001:
            case 1002:
            case 2001:
            case 101010:
                mTextError.setText("准儿没找到信息");
                break;
            case 3001:
                mTextError.setText("暂不支持该出行方式");
                break;
        }
    }

}
