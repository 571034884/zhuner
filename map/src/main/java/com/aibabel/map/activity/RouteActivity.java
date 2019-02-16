package com.aibabel.map.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.map.R;
import com.aibabel.map.adapter.TransitAdapter;
import com.aibabel.map.bean.LocationBean;
import com.aibabel.map.bean.RouteBean;
import com.aibabel.map.bean.search.AddressResult;
import com.aibabel.map.bean.trafficen.EnBean;
import com.aibabel.map.bean.trafficen.RoutesEnBean;
import com.aibabel.map.bean.trafficzh.RoutesZhBean;
import com.aibabel.map.bean.trafficzh.ZhBean;
import com.aibabel.map.bean.transiten.TransitEnBean;
import com.aibabel.map.bean.transiten.TransitResultEnBean;
import com.aibabel.map.bean.transitzh.TransitResultZhBean;
import com.aibabel.map.bean.transitzh.TransitZhBean;
import com.aibabel.map.route.MyTrafficRouteOverlayEn;
import com.aibabel.map.route.MyTrafficRouteOverlayZh;
import com.aibabel.map.route.OverlayManager;
import com.aibabel.map.utils.ActivityConstant;
import com.aibabel.map.utils.BaiDuConstant;
import com.aibabel.map.utils.BaiDuStatusUtil;
import com.aibabel.map.utils.BaiDuUtil;
import com.aibabel.map.utils.MathUtil;
import com.aibabel.map.utils.TimerDataUtil;
import com.aibabel.map.wheel.WheelView;
import com.aibabel.map.wheel.adapter.ArrayWheelAdapter;
import com.aibabel.map.wheel.listener.OnItemSelectedListener;
import com.baidu.location.Address;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
/**
 * 弃用2019年1月3日10:37:12
 */
public class RouteActivity extends BaseActivity implements SensorEventListener {

    @BindView(R.id.iv_return)
    ImageView ivRetrun;
    @BindView(R.id.iv_change)
    ImageView ivChange;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    @BindView(R.id.tv_device)
    TextView tvDevice;
    @BindView(R.id.tv_transit)
    TextView tvTransit;
    @BindView(R.id.tv_walk)
    TextView tvWalk;

    @BindView(R.id.rl_devicewalk)
    RelativeLayout rlDeviceWalk;
    @BindView(R.id.rl_transit)
    RelativeLayout rlTransit;
    @BindView(R.id.mv_map)
    MapView mMapView;
    @BindView(R.id.route_view)
    View routeView;
    @BindView(R.id.iv_inout)
    ImageView ivInOut;
    @BindView(R.id.tv_timer_new)
    TextView tvTimerNew;

    BaiduMap mBaiduMap;
    SensorManager mSensorManager;
    BitmapDescriptor mCurrentMarker;
    LocationMode mCurrentMode;
    LocationClient mLocClient;
    MyLocationData locData;
    OverlayManager routeOverlay = null;
    Double lastX = 0.0;
    int mCurrentDirection = 0;
    float mCurrentAccracy;
    double mCurrentLat = 0.0;
    double mCurrentLon = 0.0;
    boolean isFirstLoc = true; // 是否首次定位flag
    boolean IS_FRIST_LOCATION = true; //第一次进入页面 定位成功后，自动进行路线规划
    Address address;//定位成功的信息

    String startName = "";
    LatLng startLatLon = null;
    String endName = "";
    LatLng endLatLon = null;
    //0 驾车   1 公交   2 步行
    int ROUTE_MODE = 1;
    //判断国内bd09ll    国外wgs84
    String coorType = "bd09ll";
    int locationWhere = 2;

    String startMonth = "";
    String startTimer = "";




    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_route;
    }

    @Override
    public void init() {
        //获取上页面数据
        initDataBean();
        //初始化百度
        initBaiDuOption();
        //默认隐藏
        initDefaultView();
        //监听
        initListener();
        //时间选择器获取当前之后15天的数据
        initDataTimer();
        //时间选择器
        isShowHideView();
    }

    private void initListener() {
        lvTransit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, TransitRouteActivity.class);
                switch (locationWhere) {
                    case 1://国内
                        //JSON
                        TransitResultZhBean zhRoute = tZhBean.getData().getResult();
                        intent.putExtra("route", zhRoute);
                        break;
                    case 0://国外
                        TransitResultEnBean enRoute = tEnBean.getData().getResult();
                        intent.putExtra("route", enRoute);
                        break;
                }
                intent.putExtra("locationWhere", locationWhere);
                intent.putExtra("index", position);
                intent.putExtra("startName", tvStart.getText().toString());
                intent.putExtra("endName", tvEnd.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void initDefaultView() {
        //初始隐藏底部方案View
        rlBottom.setVisibility(View.GONE);
        rlBottomError.setVisibility(View.GONE);
        routeView.setVisibility(View.GONE);
        rlTransit.setVisibility(View.GONE);
    }

    private void initDataBean() {
        //跳转过来的路线规划，必须传递实体类。上一页请判空
        RouteBean routeBean = getIntent().getExtras().getParcelable("routes");

        //TODO 我的位置 传递过来
        startName = routeBean.getStartName();
        startLatLon = new LatLng(routeBean.getStartLoc().getLat(), routeBean.getStartLoc().getLng());

        //TODO 上个页面传递过来
        endName = routeBean.getEndName();
        endLatLon = new LatLng(routeBean.getEndLoc().getLat(), routeBean.getEndLoc().getLng());

        ROUTE_MODE = routeBean.getIndex();
        //----------------------------------------------
        //TODO 模拟测试
//        startName = "筑底市场";
//        startLatLon = new LatLng(35.664848,139.766895);
//
//        endName = "正宗日本料理餐厅";
//        endLatLon = new LatLng(35.693577,139.744352);
//
//        ROUTE_MODE = 1;
        //----------------------------------------------
        tvStart.setText(startName);
        tvEnd.setText(endName);
    }

    private List<String> monthListValues;
    private List<String> monthList;
    private List<String> hoursList;
    private List<String> minList;
    int indexMonth = 0;
    int indexHours = 0;
    int indexMin = 0;
    private void initDataTimer() {
        monthListValues = TimerDataUtil.getMonthValues(15);
        monthList = TimerDataUtil.getMonth(15);
        hoursList = TimerDataUtil.getHours();
        minList = TimerDataUtil.getMin();

        startMonth = TimerDataUtil.getStartMonth();
        startTimer = TimerDataUtil.getStartTimer();

        ////获取角标以及设置当前时间
        getDataTimer();
    }

    private void getDataTimer() {
        indexMonth = TimerDataUtil.getIndexMonth(monthList,"今天");
        indexHours = TimerDataUtil.getIndexHours(hoursList,TimerDataUtil.getCurrentHours());
        indexMin = TimerDataUtil.getIndexMin(minList,TimerDataUtil.getCurrentMin());
        tvTimerNew.setText(monthList.get(indexMonth)+" "+hoursList.get(indexHours)+":"+minList.get(indexMin)+" 出发");
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
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //打开室内图，默认为关闭状态
        mBaiduMap.setIndoorEnable(false);
        //定位跟随  FOLLOWING//定位跟随态    NORMAL//默认为普通态     COMPASS//定位罗盘态
        mCurrentMode = LocationMode.NORMAL;
        //自定义图标mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
        mCurrentMarker = null;
        //以下定位相关
        mLocClient = new LocationClient(this);
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
    //选择的方案
    int index = 0;
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                this.finish();
                break;
            case R.id.iv_change:
                startEndChange();
                break;
            case R.id.tv_start:
                skipSearchAty(tvStart.getText().toString(), "0");
                break;
            case R.id.tv_end:
                skipSearchAty(tvEnd.getText().toString(), "1");
                break;
            case R.id.tv_device:
                defaultSetting(BaiDuConstant.DRIVING_MODE);
                break;
            case R.id.tv_transit:
                defaultSetting(BaiDuConstant.TRANSIT_MODE);
                break;
            case R.id.tv_walk:
                defaultSetting(BaiDuConstant.WALKING_MODE);
                break;
            case R.id.rl_scheme_one:
                defaultView(0);
                addLineShow(0);
                index = 0;
                break;
            case R.id.rl_scheme_two:
                defaultView(1);
                addLineShow(1);
                index = 1;
                break;
            case R.id.rl_scheme_three:
                defaultView(2);
                addLineShow(2);
                index = 2;
                break;
            case R.id.tv_details:
                Intent intent = new Intent(mContext, TrafficDetailActivity.class);
//                String json = "";
                if (locationWhere == 1) {
//                    json = FastJsonUtil.changObjectToString(driveZhBean);
                    intent.putExtra("json", driveZhBean);//数据
                } else if (locationWhere == 0) {
//                    json = FastJsonUtil.changObjectToString(driveEnBean);
                    intent.putExtra("json", driveEnBean);//数据
                }
                intent.putExtra("locationWhere", locationWhere);
                intent.putExtra("index", index);//当前第几个方案
                intent.putExtra("startName", tvStart.getText().toString());//起点
                intent.putExtra("endName", tvEnd.getText().toString());//终点
                startActivity(intent);
                break;
            case R.id.rl_timer:
                if (!popWnd.isShowing()){
                    ivInOut.setImageResource(R.mipmap.ic_close_popu);
                    popWnd.showAsDropDown(view);
                    Animation animation = new AlphaAnimation(0.0f, 1.0f);
                    animation.setDuration(500);
                    routeView.setVisibility(View.VISIBLE);
                    routeView.setAnimation(animation);
                }
                break;
        }
    }

    PopupWindow popWnd = null;
    WheelView month;
    WheelView hours;
    WheelView min;

    private void isShowHideView() {

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_popuwindow, null);
        popWnd = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popWnd.setContentView(contentView);
        popWnd.setAnimationStyle(R.style.TopPopAnim);
        popWnd.setOutsideTouchable(false);
        popWnd.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivInOut.setImageResource(R.mipmap.ic_open_popu);
                Animation animation = new AlphaAnimation(1.0f,0.0f);
                animation.setDuration(500);
                routeView.setAnimation(animation);
                routeView.setVisibility(View.GONE);
            }
        });

        month = contentView.findViewById(R.id.options1);
        hours = contentView.findViewById(R.id.options2);
        min = contentView.findViewById(R.id.options3);
        TextView tvOk = contentView.findViewById(R.id.tv_ok);
        //适配
        month.setAdapter(new ArrayWheelAdapter(monthList));
        hours.setAdapter(new ArrayWheelAdapter(hoursList));
        min.setAdapter(new ArrayWheelAdapter(minList));

        //当前选中
        month.setCurrentItem(indexMonth);
        hours.setCurrentItem(indexHours);
        min.setCurrentItem(indexMin);

        month.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                //当前日期和之后的日期 不会有之前的日期所以直接获取角标
                if (monthList.get(index).equals("今天")){
                    indexMonth = TimerDataUtil.getIndexMonth(monthList,"今天");
                    indexHours = TimerDataUtil.getIndexHours(hoursList,TimerDataUtil.getCurrentHours());
                    indexMin = TimerDataUtil.getIndexMin(minList,TimerDataUtil.getCurrentMin());
                    month.setCurrentItem(indexMonth);
                    hours.setCurrentItem(indexHours);
                    min.setCurrentItem(indexMin);
                }else{
                    indexMonth = index;
                }
            }
        });
        hours.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                /**
                 * 用户滑动小时
                 *
                 * 首先判断日期
                 *  今天
                 *      以当前时间为轴
                 *          > 当前时间轴    正常(当前小时 之后的小时)
                 *              返回用户滑动小时 index
                 *          < 当前时间轴    异常(当前小时 之前的小时)
                 *              返回到当前时间轴 index
                 *  以后
                 *      无所谓
                 */
                int type = TimerDataUtil.getIndexHoursType(hoursList,monthList.get(indexMonth),hoursList.get(index));
                indexHours = type;
                hours.setCurrentItem(indexHours);

            }
        });
        min.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                //同小时一样
                int type = TimerDataUtil.getIndexMinType(minList,minList.get(index),monthList.get(indexMonth));
                indexMin = type;
                min.setCurrentItem(indexMin);
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
                if (monthList.get(indexMonth).equals("今天")){

                    /**
                     * 用户选择小时 >= 当前小时
                     *      return 用户选择小时
                     * 用户选择小时 <  当前小时
                     *      return 当前小时
                     */
                    indexHours = TimerDataUtil.getHoursConFirm(hoursList,hoursList.get(indexHours));

                    /**
                     * 用户选择分钟 >= 当前分钟
                     *      return 用户选择分钟
                     * 用户选择分钟 <  当前分钟
                     *      return 当前分钟
                     */
                    indexMin = TimerDataUtil.getMinConFirm(minList,minList.get(indexMin));

                    //设置到当前
                    month.setCurrentItem(indexMonth);
                    hours.setCurrentItem(indexHours);
                    min.setCurrentItem(indexMin);

                }
                tvTimerNew.setText(monthList.get(indexMonth)+" "+hoursList.get(indexHours)+":"+minList.get(indexMin)+" 出发");

                //TODO 逻辑处理 请求API
                startMonth = monthListValues.get(indexMonth);
                startTimer = hoursList.get(indexHours)+":"+minList.get(indexMin);
                judgeDataMode(ROUTE_MODE);
            }
        });
    }

    private void skipSearchAty(String name, String searchType) {
        if (address == null) {
            ToastUtil.showShort(mContext, "定位失败");
            return;
        }
        Intent intent = new Intent(RouteActivity.this, SearchActivity.class);
        intent.putExtra("value", name);
        intent.putExtra("type", searchType);
        intent.putExtra("city", address.city);
        intent.putExtra("coorType", coorType);
        intent.putExtra("locationWhere", locationWhere);
        startActivityForResult(intent, ActivityConstant.REQUSET_OK);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);

    }

    /**
     * 选择方案
     */
    private void addLineShow(int index) {
        mBaiduMap.clear();
        if (locationWhere == 1) {
            routeLineTrafficZh(driveZhBean.getData().getResult().getRoutes().get(index), driveZhBean.getData().getResult().getOrigin(), driveZhBean.getData().getResult().getDestination());
        } else if (locationWhere == 0) {
            routeLineTrafficEn(driveEnBean.getData().getResult().getRoutes().get(index), driveEnBean.getData().getResult().getRoutes().get(index).getOrigin(), driveEnBean.getData().getResult().getRoutes().get(index).getDestination());
        }
    }

    private void defaultSetting(int type) {
        Resources res = getResources();
        tvDevice.setTextColor(res.getColor(R.color.c_33));
        tvTransit.setTextColor(res.getColor(R.color.c_33));
        tvWalk.setTextColor(res.getColor(R.color.c_33));
        tvDevice.setBackgroundResource(R.drawable.tv_routetype_default_bg);
        tvTransit.setBackgroundResource(R.drawable.tv_routetype_default_bg);
        tvWalk.setBackgroundResource(R.drawable.tv_routetype_default_bg);
        tvDevice.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        tvTransit.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        tvWalk.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        switch (type) {
            case BaiDuConstant.DRIVING_MODE:
                tvDevice.setTextColor(res.getColor(R.color.c_ff));
                tvDevice.setCompoundDrawablesRelativeWithIntrinsicBounds(res.getDrawable(R.mipmap.ic_drive), null, null, null);
                tvDevice.setBackgroundResource(R.drawable.tv_routetype_bg);
                //隐藏or显示
                rlDeviceWalk.setVisibility(View.VISIBLE);
                rlTransit.setVisibility(View.GONE);

                break;
            case BaiDuConstant.TRANSIT_MODE:
                tvTransit.setTextColor(res.getColor(R.color.c_ff));
                tvTransit.setCompoundDrawablesRelativeWithIntrinsicBounds(res.getDrawable(R.mipmap.ic_transit), null, null, null);
                tvTransit.setBackgroundResource(R.drawable.tv_routetype_bg);
                rlDeviceWalk.setVisibility(View.GONE);
                rlTransit.setVisibility(View.VISIBLE);
                break;
            case BaiDuConstant.WALKING_MODE:
                tvWalk.setTextColor(res.getColor(R.color.c_ff));
                tvWalk.setCompoundDrawablesRelativeWithIntrinsicBounds(res.getDrawable(R.mipmap.ic_walk), null, null, null);
                tvWalk.setBackgroundResource(R.drawable.tv_routetype_bg);
                rlDeviceWalk.setVisibility(View.VISIBLE);
                rlTransit.setVisibility(View.GONE);
                break;
        }

        ROUTE_MODE = type;
        //同一接口判断 起点 终点 路线选择
        judgeDataMode(type);

    }

    private void judgeDataMode(int type) {
        //可以切换 但是不请求API
        if (address == null) {
            ToastUtil.showShort(mContext, "定位失败");
            return;
        }

        startName = tvStart.getText().toString();
        endName = tvEnd.getText().toString();
        if (TextUtils.isEmpty(startName) || TextUtils.isEmpty(endName) || endName.equals("选择终点位置")) {
            return;
        }
        //获取url，获取路线类型
        routeOkGo(BaiDuUtil.getUrl(locationWhere, type), type);
    }

    private void routeOkGo(String url, int type) {
        Map param = new HashMap();
        param.put("origin", startLatLon.latitude + "," + startLatLon.longitude);
        param.put("destination", endLatLon.latitude + "," + endLatLon.longitude);
        param.put("mode", BaiDuUtil.getModeType(type));
        param.put("coord_type", coorType + "");
        param.put("locationWhere", locationWhere + "");
        param.put("departure_date", startMonth+ "");
        param.put("departure_time", startTimer + "");
        OkGoUtil.get(mContext, url, param, BaseBean.class, new BaseCallback() {
            @Override
            public void onSuccess(String s, BaseBean baseBean, String s1) {
                mBaiduMap.clear();
                switch (type) {
                    case BaiDuConstant.DRIVING_MODE:
                    case BaiDuConstant.WALKING_MODE:
                        trafficType(locationWhere, s1);
                        break;
                    case BaiDuConstant.TRANSIT_MODE:
                        transitType(locationWhere, s1);
                        break;
                }
            }

            @Override
            public void onError(String s, String s1, String s2) {
                rlBottom.setVisibility(View.GONE);
                mBaiduMap.clear();
                ToastUtil.showShort(mContext, s1);
                switch (type){
                    case BaiDuConstant.DRIVING_MODE:
                    case BaiDuConstant.WALKING_MODE:
                        rlBottomError.setVisibility(View.VISIBLE);
                        iconShow(505050);
                        break;
                    case BaiDuConstant.TRANSIT_MODE:
                        llError.setVisibility(View.VISIBLE);
                        lvTransit.setVisibility(View.GONE);
                        ToastUtil.showShort(mContext,"服务器出现问题");
                        break;
                }
            }

            @Override
            public void onFinsh(String s) {

            }
        });
    }


    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.rl_bottom_error)
    RelativeLayout rlBottomError;
    @BindView(R.id.error_txt)
    TextView tvError;
    @BindView(R.id.rl_scheme_one)
    RelativeLayout rlSchemeOne;
    @BindView(R.id.rl_scheme_two)
    RelativeLayout rlSchemeTwo;
    @BindView(R.id.rl_scheme_three)
    RelativeLayout rlSchemeThree;
    @BindView(R.id.tv_route_name_one)
    TextView tvRouteNameOne;
    @BindView(R.id.tv_route_name_two)
    TextView tvRouteNameTwo;
    @BindView(R.id.tv_route_name_three)
    TextView tvRouteNameThree;
    @BindView(R.id.tv_route_min_one)
    TextView tvRouteMinOne;
    @BindView(R.id.tv_route_min_two)
    TextView tvRouteMinTwo;
    @BindView(R.id.tv_route_min_three)
    TextView tvRouteMinThree;
    @BindView(R.id.tv_route_km_one)
    TextView tvRouteKmOne;
    @BindView(R.id.tv_route_km_two)
    TextView tvRouteKmTwo;
    @BindView(R.id.tv_route_km_three)
    TextView tvRouteKmThree;
    @BindView(R.id.tv_route_light_one)
    ImageView tvRouteLightOne;
    @BindView(R.id.tv_route_light_two)
    ImageView tvRouteLightTwo;
    @BindView(R.id.tv_route_light_three)
    ImageView tvRouteLightThree;
    @BindView(R.id.tv_route_lightnum_one)
    TextView tvRouteLightNumOne;
    @BindView(R.id.tv_route_lightnum_two)
    TextView tvRouteLightNumTwo;
    @BindView(R.id.tv_route_lightnum_three)
    TextView tvRouteLightNumThree;
    @BindView(R.id.v_line_one)
    View vLineOne;
    @BindView(R.id.v_line_two)
    View vLineTwo;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.lv_transit)
    ListView lvTransit;
    @BindView(R.id.rl_less)
    RelativeLayout rlLess;
    @BindView(R.id.ll_much)
    LinearLayout llMuch;
    @BindView(R.id.tv_less_min)
    TextView tvLessMin;
    @BindView(R.id.tv_less_km)
    TextView tvLessKm;
    @BindView(R.id.tv_route_light)
    ImageView tvRouteLight;
    @BindView(R.id.tv_route_lightnum)
    TextView tvRouteLightNum;
    @BindView(R.id.ll_error)
    LinearLayout llError;

    private void defaultView(int index) {
        Resources res = getResources();
        tvRouteNameOne.setTextColor(res.getColor(R.color.c_99));
        tvRouteNameTwo.setTextColor(res.getColor(R.color.c_99));
        tvRouteNameThree.setTextColor(res.getColor(R.color.c_99));
        tvRouteMinOne.setTextColor(res.getColor(R.color.c_33));
        tvRouteMinTwo.setTextColor(res.getColor(R.color.c_33));
        tvRouteMinThree.setTextColor(res.getColor(R.color.c_33));
        tvRouteKmOne.setTextColor(res.getColor(R.color.c_33));
        tvRouteKmTwo.setTextColor(res.getColor(R.color.c_33));
        tvRouteKmThree.setTextColor(res.getColor(R.color.c_33));
        tvRouteLightOne.setImageResource(R.mipmap.ic_light_default);
        tvRouteLightTwo.setImageResource(R.mipmap.ic_light_default);
        tvRouteLightThree.setImageResource(R.mipmap.ic_light_default);
        tvRouteLightNumOne.setTextColor(res.getColor(R.color.c_33));
        tvRouteLightNumTwo.setTextColor(res.getColor(R.color.c_33));
        tvRouteLightNumThree.setTextColor(res.getColor(R.color.c_33));

        switch (index) {
            case 0:
                tvRouteNameOne.setTextColor(res.getColor(R.color.c_red));
                tvRouteMinOne.setTextColor(res.getColor(R.color.c_red));
                tvRouteKmOne.setTextColor(res.getColor(R.color.c_red));
                tvRouteLightOne.setImageResource(R.mipmap.ic_light_select);
                tvRouteLightNumOne.setTextColor(res.getColor(R.color.c_red));
                break;
            case 1:
                tvRouteNameTwo.setTextColor(res.getColor(R.color.c_red));
                tvRouteMinTwo.setTextColor(res.getColor(R.color.c_red));
                tvRouteKmTwo.setTextColor(res.getColor(R.color.c_red));
                tvRouteLightTwo.setImageResource(R.mipmap.ic_light_select);
                tvRouteLightNumTwo.setTextColor(res.getColor(R.color.c_red));
                break;
            case 2:
                tvRouteNameThree.setTextColor(res.getColor(R.color.c_red));
                tvRouteMinThree.setTextColor(res.getColor(R.color.c_red));
                tvRouteKmThree.setTextColor(res.getColor(R.color.c_red));
                tvRouteLightThree.setImageResource(R.mipmap.ic_light_select);
                tvRouteLightNumThree.setTextColor(res.getColor(R.color.c_red));
                break;
        }

    }


    ZhBean driveZhBean = null;
    EnBean driveEnBean = null;

    /**
     * 包含国内驾车 国内步行
     * 包含国外驾车 国外步行
     *
     * @param locationWhere 国家类型 == 0 国内   1 国外
     * @param body          数据
     */
    private void trafficType(int locationWhere, String body) {
        if (locationWhere == 1) {
            //国内隐藏红绿灯和数量
            tvRouteLightOne.setVisibility(View.GONE);
            tvRouteLightNumOne.setVisibility(View.GONE);
            tvRouteLightTwo.setVisibility(View.GONE);
            tvRouteLightNumTwo.setVisibility(View.GONE);
            tvRouteLightThree.setVisibility(View.GONE);
            tvRouteLightNumThree.setVisibility(View.GONE);
            tvRouteLight.setVisibility(View.GONE);
            tvRouteLightNum.setVisibility(View.GONE);
            //国内数据解析
            driveZhBean = FastJsonUtil.changeJsonToBean(body, ZhBean.class);
            if (driveZhBean.getData() != null) {
                if (driveZhBean.getData().getStatus() == 0) {
                    index = 0;
                    //显示View
                    rlBottom.setVisibility(View.VISIBLE);
                    rlBottomError.setVisibility(View.GONE);
                    selectLineZh(driveZhBean);
                } else{
                    rlBottom.setVisibility(View.GONE);
                    rlBottomError.setVisibility(View.VISIBLE);
                    BaiDuStatusUtil.getBaiDuStatusZh(mContext,driveZhBean.getData().getStatus());
                    iconShow(driveZhBean.getData().getStatus());
                }
            }else{
                rlBottom.setVisibility(View.GONE);
                rlBottomError.setVisibility(View.VISIBLE);
                ToastUtil.showShort(mContext, "没有数据");
                iconShow(101010);
            }
        } else if (locationWhere == 0) {
            tvRouteLightOne.setVisibility(View.VISIBLE);
            tvRouteLightNumOne.setVisibility(View.VISIBLE);
            tvRouteLightTwo.setVisibility(View.VISIBLE);
            tvRouteLightNumTwo.setVisibility(View.VISIBLE);
            tvRouteLightThree.setVisibility(View.VISIBLE);
            tvRouteLightNumThree.setVisibility(View.VISIBLE);
            tvRouteLight.setVisibility(View.VISIBLE);
            tvRouteLightNum.setVisibility(View.VISIBLE);
            //国外数据解析
            driveEnBean = FastJsonUtil.changeJsonToBean(body, EnBean.class);
            if (driveEnBean.getData() != null) {
                if (driveEnBean.getData().getStatus() == 0) {
                    index = 0;
                    //显示View
                    rlBottom.setVisibility(View.VISIBLE);
                    rlBottomError.setVisibility(View.GONE);
                    selectLineEn(driveEnBean);
                } else{
                    rlBottom.setVisibility(View.GONE);
                    rlBottomError.setVisibility(View.VISIBLE);
                    BaiDuStatusUtil.getBaiDuStatusEn(mContext,driveEnBean.getData().getStatus());
                    iconShow(driveEnBean.getData().getStatus());
                }
            }else{
                rlBottom.setVisibility(View.GONE);
                rlBottomError.setVisibility(View.VISIBLE);
                ToastUtil.showShort(mContext, "没有数据");
                iconShow(101010);
            }
        }
    }

    private void selectLineEn(EnBean driveEnBean) {
        //判定有几条路线
        int size = driveEnBean.getData().getResult().getRoutes().size();
        if (size == 1) {//只有一条
            index = 0;
            rlLess.setVisibility(View.VISIBLE);//显示一条展示
            llMuch.setVisibility(View.GONE);//隐藏多条展示
            //数据展示
            showLessViewEn(driveEnBean.getData().getResult().getRoutes());
        } else {//有多条
            rlLess.setVisibility(View.GONE);//显示一条展示
            llMuch.setVisibility(View.VISIBLE);//隐藏多条展示
            showMuchViewEn(driveEnBean.getData().getResult().getRoutes());
        }
        //路线规划 默认第一个路线
        routeLineTrafficEn(driveEnBean.getData().getResult().getRoutes().get(0), driveEnBean.getData().getResult().getRoutes().get(0).getOrigin(), driveEnBean.getData().getResult().getRoutes().get(0).getDestination());
    }

    private void selectLineZh(ZhBean driveZhBean) {
        //判定有几条路线
        int size = driveZhBean.getData().getResult().getRoutes().size();
        if (size == 1) {//只有一条
            index = 0;
            rlLess.setVisibility(View.VISIBLE);//显示一条展示
            llMuch.setVisibility(View.GONE);//隐藏多条展示
            //数据展示
            showLessViewZh(driveZhBean.getData().getResult().getRoutes());
        } else {//有多条
            rlLess.setVisibility(View.GONE);//显示一条展示
            llMuch.setVisibility(View.VISIBLE);//隐藏多条展示
            showMuchViewZh(driveZhBean.getData().getResult().getRoutes());
        }
        //路线规划 默认第一个路线
        routeLineTrafficZh(driveZhBean.getData().getResult().getRoutes().get(0), driveZhBean.getData().getResult().getOrigin(), driveZhBean.getData().getResult().getDestination());
    }

    TransitZhBean tZhBean = null;
    TransitEnBean tEnBean = null;
    TransitAdapter transitAdapter = null;

    private void transitType(int locationWhere, String body) {
        if (locationWhere == 1) {
            tZhBean = FastJsonUtil.changeJsonToBean(body, TransitZhBean.class);
            if (tZhBean.getData() != null){
                if (tZhBean.getData().getStatus() == 0) {
                    llError.setVisibility(View.GONE);
                    lvTransit.setVisibility(View.VISIBLE);
                    transitAdapter = new TransitAdapter(mContext, tZhBean.getData().getResult().getRoutes(), 0);
                    lvTransit.setAdapter(transitAdapter);
                } else{
                    llError.setVisibility(View.VISIBLE);
                    lvTransit.setVisibility(View.GONE);
                    BaiDuStatusUtil.getBaiDuStatusZh(mContext,tZhBean.getData().getStatus());
                }
            }else{
                llError.setVisibility(View.VISIBLE);
                lvTransit.setVisibility(View.GONE);
                ToastUtil.showShort(mContext, "没有数据");
            }
        } else if (locationWhere == 0) {
            tEnBean = FastJsonUtil.changeJsonToBean(body, TransitEnBean.class);
            if (tEnBean.getData() != null){
                if (tEnBean.getData().getStatus() == 0) {
                    llError.setVisibility(View.GONE);
                    lvTransit.setVisibility(View.VISIBLE);
                    transitAdapter = new TransitAdapter(mContext, tEnBean.getData().getResult().getRoutes(), 1);
                    lvTransit.setAdapter(transitAdapter);
                } else{
                    llError.setVisibility(View.VISIBLE);
                    lvTransit.setVisibility(View.GONE);
                    BaiDuStatusUtil.getBaiDuStatusEn(mContext,tEnBean.getData().getStatus());
                }
            }else{
                llError.setVisibility(View.VISIBLE);
                lvTransit.setVisibility(View.GONE);
                ToastUtil.showShort(mContext, "没有数据");
            }

        }
    }

    /**
     * 切换 起点终点
     */
    private void startEndChange() {
        String start = tvStart.getText().toString();
        String end = tvEnd.getText().toString();
        tvStart.setText(end);
        tvEnd.setText(start);
        LatLng startLatLon = this.startLatLon;
        LatLng endLatLon = this.endLatLon;
        this.startLatLon = endLatLon;
        this.endLatLon = startLatLon;

        judgeDataMode(ROUTE_MODE);
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
                tvError.setText("服务器异常,稍后再试");
                break;
            case 2:
                tvError.setText("参数无效");
                break;
            case 7:
            case 1001:
            case 1002:
            case 2001:
            case 101010:
                tvError.setText("没有返回结果");
                break;
            case 3001:
                tvError.setText("暂不支持该出行方式");
                break;
        }
    }


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
            coorType = location.getCoorType();
            locationWhere = location.getLocationWhere();

            //----------------------------------
            //TODO 模拟测试
//            coorType = "wgs84";
//            locationWhere = 0;
            //----------------------------------


            if (IS_FRIST_LOCATION) {
                IS_FRIST_LOCATION = false;
                //默认驾车
                defaultSetting(ROUTE_MODE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityConstant.REQUSET_OK && resultCode == ActivityConstant.RESULT_OK) {
            String type = data.getExtras().getString("type");
            AddressResult address = data.getExtras().getParcelable("address");
            if (type.equals("0")) {
                startName = address.getName();
                startLatLon = new LatLng(address.getLocation().getLat(), address.getLocation().getLng());
                tvStart.setText(startName);
            } else if (type.equals("1")) {
                endName = address.getName();
                endLatLon = new LatLng(address.getLocation().getLat(), address.getLocation().getLng());
                tvEnd.setText(endName);
            }
            judgeDataMode(ROUTE_MODE);
        }
    }
    //==================================以下路线规划=======================================

    /**
     * 只有一条的显示方案    en
     *
     * @param routes
     */
    private void showLessViewEn(List<RoutesEnBean> routes) {
        RoutesEnBean routesEnBean = routes.get(0);
        tvLessMin.setText(BaiDuUtil.getHoursMin(routesEnBean.getDuration()));
        tvLessKm.setText(MathUtil.mathDivision(routesEnBean.getDistance(), 1000, 2) + "公里");
        tvDetails.setTextColor(getResources().getColor(R.color.c_33));
        tvRouteLightNum.setText(routesEnBean.getLight_number() + "");
    }

    /**
     * 只有一条的显示方案    zh
     *
     * @param routes
     */
    private void showLessViewZh(List<RoutesZhBean> routes) {
        RoutesZhBean driveRoutesZhBean = routes.get(0);
        tvLessMin.setText(BaiDuUtil.getHoursMin(driveRoutesZhBean.getDuration()));
        tvLessKm.setText(MathUtil.mathDivision(driveRoutesZhBean.getDistance(), 1000, 2) + "公里");
        tvDetails.setTextColor(getResources().getColor(R.color.c_33));
    }

    /**
     * 多条的显示方案 en
     *
     * @param routes
     */
    private void showMuchViewEn(List<RoutesEnBean> routes) {
        //默认显示第一条方案
        defaultView(0);
        //判定方案是>=2or3
        showViewTraffic(routes.size());
        for (int i = 0; i < routes.size(); i++) {
            RoutesEnBean driveRoutesEnBean = routes.get(i);
            if (i == 0) {
                tvRouteNameOne.setText("方案一");
                tvRouteMinOne.setText(BaiDuUtil.getHoursMin(driveRoutesEnBean.getDuration()));
                tvRouteKmOne.setText(MathUtil.mathDivision(driveRoutesEnBean.getDistance(), 1000, 2) + "公里");
                tvRouteLightNumOne.setText(driveRoutesEnBean.getLight_number() + "");
            } else if (i == 1) {
                tvRouteNameTwo.setText("方案二");
                tvRouteMinTwo.setText(BaiDuUtil.getHoursMin(driveRoutesEnBean.getDuration()));
                tvRouteKmTwo.setText(MathUtil.mathDivision(driveRoutesEnBean.getDistance(), 1000, 2) + "公里");
                tvRouteLightNumTwo.setText(driveRoutesEnBean.getLight_number() + "");
            } else if (i == 2) {
                tvRouteNameThree.setText("方案三");
                tvRouteMinThree.setText(BaiDuUtil.getHoursMin(driveRoutesEnBean.getDuration()));
                tvRouteKmThree.setText(MathUtil.mathDivision(driveRoutesEnBean.getDistance(), 1000, 2) + "公里");
                tvRouteLightNumThree.setText(driveRoutesEnBean.getLight_number() + "");
            }
        }
    }

    /**
     * 多条的显示方案 zh
     *
     * @param routes
     */
    private void showMuchViewZh(List<RoutesZhBean> routes) {
        //默认显示第一条方案
        defaultView(0);
        //判定方案是>=2or3
        showViewTraffic(routes.size());
        for (int i = 0; i < routes.size(); i++) {
            RoutesZhBean driveRoutesZhBean = routes.get(i);
            if (i == 0) {
                tvRouteNameOne.setText("方案一");
                tvRouteMinOne.setText(BaiDuUtil.getHoursMin(driveRoutesZhBean.getDuration()));
                tvRouteKmOne.setText(MathUtil.mathDivision(driveRoutesZhBean.getDistance(), 1000, 2) + "公里");
            } else if (i == 1) {
                tvRouteNameTwo.setText("方案二");
                tvRouteMinTwo.setText(BaiDuUtil.getHoursMin(driveRoutesZhBean.getDuration()));
                tvRouteKmTwo.setText(MathUtil.mathDivision(driveRoutesZhBean.getDistance(), 1000, 2) + "公里");
            } else if (i == 2) {
                tvRouteNameThree.setText("方案三");
                tvRouteMinThree.setText(BaiDuUtil.getHoursMin(driveRoutesZhBean.getDuration()));
                tvRouteKmThree.setText(MathUtil.mathDivision(driveRoutesZhBean.getDistance(), 1000, 2) + "公里");
            }
        }
    }

    /**
     * 多条公共显示方案
     *
     * @param size
     */
    private void showViewTraffic(int size) {
        switch (size) {
            case 1:
                rlSchemeOne.setVisibility(View.VISIBLE);
                rlSchemeTwo.setVisibility(View.GONE);
                rlSchemeThree.setVisibility(View.GONE);
                vLineOne.setVisibility(View.GONE);
                vLineTwo.setVisibility(View.GONE);
                break;
            case 2:
                rlSchemeOne.setVisibility(View.VISIBLE);
                rlSchemeTwo.setVisibility(View.VISIBLE);
                rlSchemeThree.setVisibility(View.GONE);
                vLineOne.setVisibility(View.VISIBLE);
                vLineTwo.setVisibility(View.GONE);
                break;
            case 3:
                rlSchemeOne.setVisibility(View.VISIBLE);
                rlSchemeTwo.setVisibility(View.VISIBLE);
                rlSchemeThree.setVisibility(View.VISIBLE);
                vLineOne.setVisibility(View.VISIBLE);
                vLineTwo.setVisibility(View.VISIBLE);
                break;
        }
        tvDetails.setTextColor(getResources().getColor(R.color.c_red));
    }

    private void routeLineTrafficEn(RoutesEnBean driveRoutesEnBean, LocationBean origin, LocationBean destination) {
        MyTrafficRouteOverlayEn myDrivingRouteOverlayEn = new MyTrafficRouteOverlayEn(mBaiduMap);
        routeOverlay = myDrivingRouteOverlayEn;
        myDrivingRouteOverlayEn.setData(driveRoutesEnBean, origin.getLat() + "," + origin.getLng(), destination.getLat() + "," + destination.getLng(),ROUTE_MODE);
        myDrivingRouteOverlayEn.addToMap();
        myDrivingRouteOverlayEn.zoomToSpan();
    }

    private void routeLineTrafficZh(RoutesZhBean driveRoutesZhBean, LocationBean origin, LocationBean destination) {
        MyTrafficRouteOverlayZh myDrivingRouteOverlayZh = new MyTrafficRouteOverlayZh(mBaiduMap);
        routeOverlay = myDrivingRouteOverlayZh;
        myDrivingRouteOverlayZh.setData(driveRoutesZhBean, origin.getLat() + "," + origin.getLng(), destination.getLat() + "," + destination.getLng(),ROUTE_MODE);
        myDrivingRouteOverlayZh.addToMap();
        myDrivingRouteOverlayZh.zoomToSpan();
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
