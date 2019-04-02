package com.aibabel.map.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.map.R;
import com.aibabel.map.adapter.SearchAddressAdapter;
import com.aibabel.map.base.MapBaseActivity;
import com.aibabel.map.bean.CollectStarBean;
import com.aibabel.map.bean.LocationBean;
import com.aibabel.map.bean.PoiAddressResult;
import com.aibabel.map.bean.RouteBean;
import com.aibabel.map.bean.search.AddressBean;
import com.aibabel.map.bean.search.AddressResult;
import com.aibabel.map.utils.ApiConstant;
import com.aibabel.map.utils.BaiDuConstant;
import com.aibabel.map.utils.BaiDuUtil;
import com.aibabel.map.utils.CommonUtils;
import com.aibabel.map.utils.DBUtil;
import com.aibabel.map.utils.KeyBords;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.lzy.okgo.OkGo;

import org.litepal.LitePal;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

/**
 * 首页搜索页面
 * Created by fytworks on 2018/12/6.
 */

public class SearchAddressActivity extends MapBaseActivity implements SensorEventListener, BaiduMap.OnMapClickListener, BaiduMap.OnMarkerClickListener {

    @BindView(R.id.ll_view)
    LinearLayout llView;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.iv_search)
    TextView ivSearch;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.mv_map)
    MapView mMapView;
    @BindView(R.id.rl_details)
    LinearLayout rlDetails;
    @BindView(R.id.lv_item_des)
    ListView lvItemDes;
    @BindView(R.id.rl_route)
    RelativeLayout rlRoute;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_maker)
    TextView tvMaker;
    @BindView(R.id.iv_goroute)
    ImageView ivGoRoute;
    @BindView(R.id.view_tran)
    View vTran;
    @BindView(R.id.iv_clear_et)
    ImageView ivClearEt;

    BaiduMap mBaiduMap;
    MyLocationData locData;
    LocationMode mCurrentMode;
    SensorManager mSensorManager;
    boolean isFirstLoc = true; // 是否首次定位
    List<AddressResult> resultList = null;
    SearchAddressAdapter addressAdapter = null;
    CollectStarBean collectStarBean = null;
    BDLocation mLocation;
    private String lat,lon;

    @Override
    public int getLayoutMap(Bundle bundle) {
        return R.layout.activity_searchaddress;
    }

    @Override
    public void initMap() {
        initBaiDuOption();
        //监听EditText
        newAdapterEt();
        //ListView监听
        viewItemOnClick();
        //默认隐藏
        initShowHide();
        //读取数据库操作
        initLatPal();
    }

    @Override
    public void receiveLocation(BDLocation location) {
        if (mMapView == null) {
            return;
        }
        mLocation = location;
//        Log.e("SearchAddressActivity", mLocation.getLatitude() + "," + mLocation.getLongitude());
        setLocData();
        if (isFirstLoc) {
            isFirstLoc = false;
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(14.5f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
    }

    private void initLatPal() {
        //数据库操作
        resultList = LitePal.findAll(AddressResult.class, true);
        if (resultList.size() == 0) {
            rlDetails.setVisibility(View.GONE);
            vTran.setVisibility(View.GONE);
        } else {
            vTran.setVisibility(View.VISIBLE);
            rlDetails.setVisibility(View.VISIBLE);
            Collections.reverse(resultList);
            showViewData();
        }

        List<CollectStarBean> allCollect = LitePal.findAll(CollectStarBean.class);
        if (allCollect.size() != 0) {
            collectStarBean = allCollect.get(0);
            showCollectMaker(collectStarBean);
        }
    }

    private void initShowHide() {
        rlRoute.setVisibility(View.GONE);
        ivClearEt.setVisibility(View.INVISIBLE);
    }

    AddressResult addressResult = null;
    private boolean flagSearch = false;

    private void viewItemOnClick() {
        lvItemDes.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        lvItemDes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                KeyBords.closeKeybord(etSearch, mContext);
                etSearch.setFocusable(false);

                if (!CommonUtils.isNetworkAvailable(SearchAddressActivity.this)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }

                if (resultList == null || resultList.get(position) == null){
                    return;
                }

                addressResult = resultList.get(position);
                if (DBUtil.isSaveALL(addressResult.getUid(), addressResult.getName())) {
                    addressResult.save();
                    addressResult.getLocation().save();
                }
                showMarkerPoi(addressResult.getLocation().getLat(), addressResult.getLocation().getLng());

                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("map_search_letter1", addressResult.getName());
                    addStatisticsEvent("map_search5", add_hp);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/

                tvName.setText(addressResult.getName());
                flagSearch = true;
                tvAddress.setText(addressResult.getAddr());
                ivClearEt.setVisibility(View.VISIBLE);
                etSearch.setText(addressResult.getName());
                if (addressResult.getTag().equals("酒店")) {
                    tvMaker.setVisibility(View.VISIBLE);
                } else {
                    tvMaker.setVisibility(View.GONE);
                }
            }
        });


//        etSearch.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
//                    //其次再做相应操作
//                    String content = etSearch.getText().toString();
//                    if (!TextUtils.isEmpty(content)) {
//                        ivClearEt.setVisibility(View.VISIBLE);
//                        //请求API
//                        rlRoute.setVisibility(View.GONE);
//                        rlDetails.setVisibility(View.VISIBLE);
//                        vTran.setVisibility(View.VISIBLE);
//                        searchOkGo(content);
//                    }
//                }
//
//                return false;
//            }
//        });

    }


    OverlayOptions option = null;
    Marker markerPoi = null;

    private void showMarkerPoi(double latitude, double longitude) {
        if (option != null) {
            markerPoi.remove();
        }
        //隐藏搜索结果

        vTran.setVisibility(View.GONE);
        rlDetails.setVisibility(View.GONE);
        rlRoute.setVisibility(View.VISIBLE);

        LatLng point = new LatLng(latitude, longitude);
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_poi_marker);//marker标记

        Bundle mBundle = new Bundle();
        mBundle.putInt("id", 0);

        option = new MarkerOptions()
                .position(point)
                .extraInfo(mBundle)
                .icon(bitmap);
        markerPoi = (Marker) mBaiduMap.addOverlay(option);

        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(point);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        if (DBUtil.isSaveCollect(addressResult.getUid(), addressResult.getName())) {
            tvMaker.setText("标记为我的酒店");
            tvMaker.setTextColor(getResources().getColor(R.color.c_red));
        } else {
            tvMaker.setText("已标记");
            tvMaker.setTextColor(getResources().getColor(R.color.c_33));
        }
    }


    private void searchOkGo(String loca) {
        OkGo.cancelAll(OkGo.getInstance().getOkHttpClient());
        Map param = new HashMap();
        param.put("query", loca.trim());
        param.put("city_limit", "true");
        if(mLocation!=null){
        param.put("region", mLocation.getAddress().city);
        param.put("coord_type", mLocation.getCoorType());
        param.put("locationWhere", mLocation.getLocationWhere() + "");}
        param.put("language", "zh-CN");
        OkGoUtil.get(false, ApiConstant.API_SEARCH_CITY, param, AddressBean.class, new BaseCallback<AddressBean>() {

            @Override
            public void onSuccess(String s, AddressBean addressBean, String s1) {
                rlDetails.setVisibility(View.VISIBLE);
                vTran.setVisibility(View.VISIBLE);
                rlRoute.setVisibility(View.GONE);
                if (addressBean.getData().getStatus() == 0) {
                    resultList = addressBean.getData().getResult();
                    if (resultList == null || resultList.size() == 0) {
                        ToastUtil.showShort(mContext, "没有搜索到结果");
                        return;
                    }
                    showViewData();
                } else {
                    ToastUtil.showShort(mContext,"准儿没有找到信息");
                }
            }

            @Override
            public void onError(String s, String s1, String s2) {
                ToastUtil.showShort(mContext, s1);
            }

            @Override
            public void onFinsh(String s) {

            }
        });
    }

    private void showViewData() {
        addressAdapter = new SearchAddressAdapter(mContext, resultList);
        lvItemDes.setAdapter(addressAdapter);
        addressAdapter.notifyDataSetChanged();
    }

    private void newAdapterEt() {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*+=|{}':;',\\[\\].<>/?~！@#￥%……&*——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        etSearch.setFilters(new InputFilter[]{filter});
        /* 当输入关键字变化时，动态更新建议列表 */
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (cs.length() <= 0) {
                    rlDetails.setVisibility(View.GONE);
                    rlRoute.setVisibility(View.GONE);
                    vTran.setVisibility(View.GONE);
                    ivClearEt.setVisibility(View.INVISIBLE);
                    //数据库操作
                    resultList = LitePal.findAll(AddressResult.class, true);
                    if (resultList.size() == 0) {
                        rlDetails.setVisibility(View.GONE);
                        vTran.setVisibility(View.GONE);
                    } else {
                        rlDetails.setVisibility(View.VISIBLE);
                        vTran.setVisibility(View.VISIBLE);
                        Collections.reverse(resultList);
                        showViewData();
                    }
                    return;
                }

                if (flagSearch) {
                    flagSearch = false;
                    return;
                }
                ivClearEt.setVisibility(View.VISIBLE);
                //请求API
                rlRoute.setVisibility(View.GONE);
                rlDetails.setVisibility(View.VISIBLE);
                vTran.setVisibility(View.VISIBLE);
                searchOkGo(cs.toString());
            }
        });
    }


    private void initBaiDuOption() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMapClickListener(this);
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:

                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("map_search1", null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/

                this.finish();
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                KeyBords.closeKeybord(etSearch, mContext);
                break;
            case R.id.iv_search:
                String loca = etSearch.getText().toString();
                if (TextUtils.isEmpty(loca)) {
                    ToastUtil.showShort(mContext, "请输入关键字");
                    return;
                }
                KeyBords.closeKeybord(etSearch, mContext);
                searchOkGo(loca);


                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("map_search3_key", loca);
                    addStatisticsEvent("map_search3", add_hp);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/

                break;
            case R.id.et_search:
                if (!CommonUtils.isNetworkAvailable(SearchAddressActivity.this)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }
                etSearch.setFocusable(true);
                etSearch.setFocusableInTouchMode(true);
                etSearch.requestFocus();
                break;
            case R.id.iv_goroute:
                if (!CommonUtils.isNetworkAvailable(SearchAddressActivity.this)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }
                if (mLocation == null){
                    ToastUtil.showShort(mContext,"定位失败");
                    return;
                }
                Intent intent = new Intent(mContext, RouteLineActivity.class);
                RouteBean routeBean = new RouteBean();
                routeBean.setStartName("我的位置");
                routeBean.setStartLoc(new LocationBean(mLocation.getLatitude(), mLocation.getLongitude()));
                routeBean.setEndName(addressResult.getName());
                routeBean.setEndLoc(addressResult.getLocation());
                if (TextUtils.isEmpty(addressResult.getCity())){
                    routeBean.setCity(mLocation.getCity());
                }else{
                    routeBean.setCity(addressResult.getCity());
                }
                routeBean.setIndex(BaiDuConstant.DRIVING_MODE);
                routeBean.setMode(BaiDuUtil.getModeType(BaiDuConstant.TRANSIT_MODE));
                routeBean.setCoord_type(mLocation.getCoorType());
                routeBean.setLocationWhere(mLocation.getLocationWhere());
                intent.putExtra("routes", routeBean);
                startActivity(intent);

                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("map_search_letter2", addressResult.getName());
                    addStatisticsEvent("map_search6", add_hp);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/

//                finish();
                break;
            case R.id.tv_maker:
                if (!CommonUtils.isNetworkAvailable(SearchAddressActivity.this)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }
                if (DBUtil.isSaveCollect(addressResult.getUid(), addressResult.getName())) {
                    //没有此条数据(只添加一个) 删除之前的数据，添加当前
                    LitePal.deleteAll(CollectStarBean.class);
                    CollectStarBean collect = new CollectStarBean();
                    collect.setName(addressResult.getName());
                    collect.setUid(addressResult.getUid());
                    collect.setLocation(addressResult.getLocation().getLat() + "," + addressResult.getLocation().getLng());
                    collect.setAddr(addressResult.getAddr());
                    collect.save();
                    //展示maker
                    showCollectMaker(collect);
                }
                break;
            case R.id.iv_clear_et:

                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("map_search4", null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/

                etSearch.setText("");
                //数据库操作
                resultList = LitePal.findAll(AddressResult.class, true);
                if (resultList.size() == 0) {
                    rlDetails.setVisibility(View.GONE);
                    vTran.setVisibility(View.GONE);
                } else {
                    rlDetails.setVisibility(View.VISIBLE);
                    vTran.setVisibility(View.VISIBLE);
                    Collections.reverse(resultList);
                    showViewData();
                }

                etSearch.setFocusable(true);
                etSearch.setFocusableInTouchMode(true);
                etSearch.requestFocus();
                break;
        }
    }

    OverlayOptions optionCollect = null;
    Marker markerCollect = null;

    private void showCollectMaker(CollectStarBean collect) {
        if (optionCollect != null) {
            mBaiduMap.clear();
        }
        //隐藏搜索结果
        String[] loc = collect.getLocation().split(",");
        LatLng point = new LatLng(Double.parseDouble(loc[0]), Double.parseDouble(loc[1]));
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_star_collect);//marker标记


        Bundle mBundle = new Bundle();
        mBundle.putInt("id", 1);

        optionCollect = new MarkerOptions()
                .position(point)
                .extraInfo(mBundle)
                .icon(bitmap);
        markerCollect = (Marker) mBaiduMap.addOverlay(optionCollect);

        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(point);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        tvMaker.setText("已标记");
        tvMaker.setTextColor(getResources().getColor(R.color.c_33));
    }

    //-----------------------------------maker点----------------------------------------------
    @Override
    public void onMapClick(LatLng latLng) {
        KeyBords.closeKeybord(etSearch, mContext);
//        mBaiduMap.clear();
//        rlRoute.setVisibility(View.GONE);
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        KeyBords.closeKeybord(etSearch, mContext);
        if (mLocation != null) {
            if (!mapPoi.getUid().equals("")) {
                poiSearchOkGo(mapPoi.getUid());
            }
        }
        return false;
    }

    private void poiSearchOkGo(String uid) {
        Map map = new HashMap();
        map.put("uid", uid);
        map.put("coord_type", mLocation.getCoorType());
        map.put("locationWhere", mLocation.getLocationWhere() + "");
        OkGoUtil.get(false, ApiConstant.API_SEARCH_POT, map, BaseBean.class, new BaseCallback() {

            @Override
            public void onSuccess(String s, BaseBean baseBean, String s1) {
                PoiAddressResult poiAddressResult = FastJsonUtil.changeJsonToBean(s1, PoiAddressResult.class);
                if (poiAddressResult.getData() != null) {
                    addressResult = poiAddressResult.getData();
                    showMarkerPoi(addressResult.getLocation().getLat(), addressResult.getLocation().getLng());
                    tvName.setText(addressResult.getName());
                    tvAddress.setText(addressResult.getAddr());
                    //是否是酒店
                    if (addressResult.getTag().equals("酒店")) {
                        tvMaker.setVisibility(View.VISIBLE);
                    } else {
                        tvMaker.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onError(String s, String s1, String s2) {
                ToastUtil.showShort(mContext, "准儿没有找到信息");
                rlRoute.setVisibility(View.GONE);
            }

            @Override
            public void onFinsh(String s) {
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    //------------------------------------定位功能---------------------------------------------
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;

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
    private void setLocData() {
        if (mLocation == null) {
            return;
        }
        locData = new MyLocationData.Builder()
                //精度圈
                .accuracy(BaiDuConstant.accuracyCircleSize)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(mCurrentDirection)
                //经纬度
                .latitude(mLocation.getLatitude()).longitude(mLocation.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, null, BaiDuConstant.accuracyCircleFillColor, BaiDuConstant.accuracyCircleStrokeColor));
    }


    //--------------------------生命周期------------------------------------
    @Override
    protected void onDestroy() {
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        KeyBords.closeKeybord(etSearch,mContext);
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

        KeyBords.closeKeybord(etSearch,mContext);
    }

    @Override
    protected void onStop() {
        //取消注册传感器监听

        KeyBords.closeKeybord(etSearch,mContext);
        mSensorManager.unregisterListener(this);
        super.onStop();
    }
}
