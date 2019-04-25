package com.aibabel.menu.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.mode.DataManager;
import com.aibabel.baselibrary.mode.StatisticsManager;
import com.aibabel.baselibrary.sphelper.SPHelper;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.baselibrary.utils.ProviderUtils;
import com.aibabel.baselibrary.utils.SharePrefUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.menu.base.LaunBaseActivity;
import com.aibabel.menu.bean.MenuDataBean;
import com.aibabel.menu.bean.PushMessageBean;
import com.aibabel.menu.bean.SyncOrder;
import com.aibabel.menu.net.Api;
import com.aibabel.menu.receiver.LauncherBcastReceiver;
import com.aibabel.menu.rent.RentDialogActivity;
import com.aibabel.menu.rent.RentKeepUseActivity;
import com.aibabel.menu.rent.RentLockedActivity;
import com.aibabel.menu.rent.SimDetectActivity;
import com.aibabel.menu.utils.AppStatusUtils;
import com.aibabel.menu.utils.CalenderUtil;
import com.aibabel.menu.utils.DetectUtil;
import com.aibabel.menu.utils.I18NUtils;
import com.aibabel.menu.utils.LocationUtils;
import com.aibabel.menu.utils.LogUtil;
import com.aibabel.menu.utils.Logs;
import com.aibabel.menu.view.MaterialBadgeTextView;
import com.aibabel.menu.view.MyDialog;
import com.aibabel.menu.R;
import com.aibabel.message.receiver.MessageListener;
import com.aibabel.message.receiver.NetBroadcastReceiver;
import com.aibabel.message.service.MessageService;
import com.aibabel.message.sqlite.SqlUtils;
import com.aibabel.message.utiles.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hyphenate.chat.EMClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends LaunBaseActivity implements NetBroadcastReceiver.NetListener, LauncherBcastReceiver.LauncherListener,MessageListener {

    @BindView(R.id.main_location_app)
    TextView mMainLocation;
    @BindView(R.id.home_badge_icon)
    ImageView homeBadgeIcon;
    @BindView(R.id.home_badge)
    MaterialBadgeTextView homeBadge;
    @BindView(R.id.main_notice_app)
    FrameLayout flNotice;
    @BindView(R.id.main_layout_one)
    LinearLayout mainLayoutOne;
    @BindView(R.id.rl_mask)
    RelativeLayout mRlMask;
    @BindView(R.id.main_timer_city)
    TextView mMainTimerCity;
    @BindView(R.id.main_weather_c)
    TextView mMainWeatherC;
    @BindView(R.id.main_weather_des)
    TextView mMainWeatherDes;
    @BindView(R.id.main_huilv_cny)
    TextView mMainHuiLvCny;
    @BindView(R.id.main_huilv_chg)
    TextView mMainHuiLvChg;
    @BindView(R.id.main_timer_clock)
    TextClock mMainTimerClock;
    @BindView(R.id.main_timer_data)
    TextClock mMainTimerData;
    @BindView(R.id.main_five_rl)
    RelativeLayout mMainFiveRl;
    @BindView(R.id.iv_main_five)
    ImageView mMainFiveIv;
    @BindView(R.id.tv_main_five)
    TextView mMainFiveTv;
    @BindView(R.id.main_address_pic)
    ImageView mMainAddressPic;
    @BindView(R.id.main_wifi_app)
    TextView mMainWifi;
    @BindView(R.id.main_one_app_one)
    RelativeLayout mMainOneAppOne;
    @BindView(R.id.main_one_app_two)
    LinearLayout mMainOneAppTwo;
    @BindView(R.id.main_one_app_two_url)
    ImageView mMainOneAppTwoUrl;
    @BindView(R.id.main_one_app_two_name)
    TextView mMainOneAppTwoName;
    @BindView(R.id.main_one_app_two_title)
    TextView mMainOneAppTwoTitle;
    @BindView(R.id.main_one_app_two_on)
    ImageView mMainOneAppTwoOn;
    @BindView(R.id.iv_native)
    ImageView mIvNativeMask;


    //环信交互handler
    Handler handler = new MyHandler(MainActivity.this);

    private String locationCity = "";//城市
    private String locationCountry = "";//国家
    private String locationLatLng = "";//经纬度
    private boolean flagApi = false;//判断请求
    private String oldCity = "";
    private int hxMessage = 0;


    /**
     * 跳转到制定的消息fragment中
     */
    private int fragment_index;
    private NetBroadcastReceiver broadcastReceiver;
    private LauncherBcastReceiver launcherBcastReceiver;
    ScreenOffReceiver screenrecive;

    @Override
    public int getLayout(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        boolean mainMasking = mmkv.decodeBool("mainMasking", false);
        if (mainMasking) {
            mRlMask.setVisibility(View.GONE);
        } else {
            mRlMask.setVisibility(View.VISIBLE);
        }
        homeBadge = findViewById(R.id.home_badge);
        registerNet();
        registerLauncher();
        startMessageService();
        mMainLocation.setText("—·—");
        mMainTimerCity.setText("—·—");
        mMainWeatherC.setText("—·—");
        mMainWeatherDes.setText("—·—");
        mMainHuiLvCny.setText("—·—");
        mMainHuiLvChg.setText("—·—");
        mMainLocation.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        loopHandler = new LooptempHandler(this);
        requestNetwork();
        init_neveruser();
        

        //息屏广播数据上传
        try {

            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);

            screenrecive = new ScreenOffReceiver();
            mContext.registerReceiver(screenrecive, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private class ScreenOffReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_SCREEN_OFF)) {
                mMainLocation.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        StatisticsManager.getInstance().uplaodData(getApplicationContext(), SharePrefUtil.getString(mContext, "order_oid", ""));

                    }


                }, 2000);
            }

        }
    }


    //false 当前是未播状态   true  当前是播放状态         默认false
    private boolean flagScenic = false;
    @Override
    public void init() {
        super.init();
        JPushInterface.setAlias(this, 1, CommonUtils.getSN());
        LogUtil.e("getSN:" + CommonUtils.getSN());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_mask://空， 拦截透传事件
                break;
            case R.id.tv_mask:
                mmkv.encode("mainMasking", true);
                mRlMask.setVisibility(View.GONE);
                break;
            case R.id.main_notice_app://跳转到消息中心
                HashMap<String, Serializable> map = new HashMap<>();
                map.put("menu_notice_click_id", "打开");
                addStatisticsEvent("menu_notice_click", map);
                startMessageActivity();
                break;
            case R.id.main_location_app://跳转到目的地
                HashMap<String, Serializable> map1 = new HashMap<>();
                map1.put("mddname", oldCity + "");
                addStatisticsEvent("mdd_click", map1);
                startActResult(SearchActivity.class, 100);
                break;
            case R.id.main_wifi_app://跳转到wifi
                addStatisticsEvent("wifi_click", null);
                launcherApp("com.zhuner.administrator.settings");
                break;
            case R.id.main_timer_app://跳转到世界钟
                addStatisticsEvent("time_click", null);
                launcherApp("com.aibabel.alliedclock");
                break;
            case R.id.main_weather_app://天气
                addStatisticsEvent("weather_click", null);
                launcherApp("com.aibabel.weather");
                break;
            case R.id.main_huilv_app://汇率
                addStatisticsEvent("currency_click", null);
                launcherApp("com.aibabel.currencyconversion");
                break;
            case R.id.main_one_app_one://定位1
                addStatisticsEvent("scenic_click", null);
                launcherApp("com.aibabel.scenic");
                break;
            case R.id.main_one_app_two://定位1
                addStatisticsEvent("scenic_click", null);
                launcherApp("com.aibabel.scenic");
                break;
            case R.id.main_one_app_left:
                if (CommonUtils.isFastClick()) {
                    addStatisticsEvent("main_scenic_pre",null);
                    changeScenic(2);
                }
                break;
            case R.id.main_one_app_two_on:
                if (flagScenic){
                    flagScenic = false;
                    if (CommonUtils.isFastClick()) {
                        addStatisticsEvent("main_scenic_off",null);
                        changeScenic(1);
                    }
                }else{
                    flagScenic = true;
                    if (CommonUtils.isFastClick()) {
                        addStatisticsEvent("main_scenic_on",null);
                        changeScenic(0);
                    }
                }
                break;
            case R.id.main_one_app_right:
                if (CommonUtils.isFastClick()) {
                    addStatisticsEvent("main_scenic_next",null);
                    changeScenic(3);
                }
                break;
            case R.id.main_two_app://定位2
                addStatisticsEvent("photo_click", null);
                launcherApp("com.aibabel.ocr");
                break;
            case R.id.main_three_app://定位3
                addStatisticsEvent("voice_click", null);
                launcherApp("com.aibabel.translate");
                break;
            case R.id.main_four_app://定位4
                addStatisticsEvent("map_click", null);
                launcherApp("com.aibabel.map");
                break;
            case R.id.main_five_app://定位点5
                switch (mmkv.decodeString("mainFive", "1")) {
                    case "1":
                        addStatisticsEvent("menu_fun_click",null);
                        launcherApp("com.aibabel.fyt_play");
                        break;
                    case "2":
                        //TODO 跳转到H5
                        addStatisticsEvent("menu_shop_click",null);
                        Intent intent = new Intent(mContext, H5Activity.class);
                        intent.putExtra("url","http://www.baidu.com");
                        startActivity(intent);
                        break;
                }
                break;
            case R.id.main_six_app://定位点6
                addStatisticsEvent("more_click", null);
                startAct(MoreActivity.class);
                break;
            case R.id.iv_native:
                if (indexNativeMask == 1){
                    if (hxMessage>0){
                        indexNativeMask = 2;
                        mIvNativeMask.setImageResource(R.mipmap.ic_native_keybord_bg);
                    }else{
                        indexNativeMask = 2;
                        mIvNativeMask.setImageResource(R.mipmap.ic_native_im_bg);
                    }
                }else if (indexNativeMask == 2){
                    if (hxMessage>0){
                        indexNativeMask = 3;
                        mIvNativeMask.setImageResource(R.mipmap.ic_native_xi_bg);
                    }else{
                        indexNativeMask = 3;
                        mIvNativeMask.setImageResource(R.mipmap.ic_native_keybord_bg);
                    }
                }else if (indexNativeMask == 3){
                    startMessage();
                }
                break;
        }
    }

    public void launcherApp(String packageStr) {
        try {
            startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, packageStr));
        } catch (Exception e) {
            Logs.e(packageStr + ":" + e.toString());
        }
    }


    /**
     * 播放状态
     * @param type  类型
     *  0.开始播放
     *  1.关闭播放
     *  2.上一首
     *  3.下一首
     *  4.暂停播放
     */
    private void changeScenic(int type){
        switch (type){
            case 0:
                mMainOneAppTwoOn.setImageResource(R.mipmap.ic_scenic_pause);
                sendBroastCast("com.aibabel.travel.play");
                break;
            case 1:
                mMainOneAppTwoOn.setImageResource(R.mipmap.ic_scenic_play);
                sendBroastCast("com.aibabel.travel.pause");
                break;
            case 2:
                sendBroastCast("com.aibabel.travel.prv");
                break;
            case 3:
                sendBroastCast("com.aibabel.travel.next");
                break;
        }
    }

    private void sendBroastCast(String action){
        Intent intent = new Intent();
        intent.setAction(action);
        sendBroadcast(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            switch (resultCode) {
                case 200://目的地回调
                    String type = data.getStringExtra("type");
                    String cityName = data.getStringExtra("city_name");
                    if (TextUtils.isEmpty(type)) {
                        return;
                    }
                    if (type.equals("1")) {
                        //定位
                        if (!cityName.equals(oldCity)) {
                            oldCity = cityName;
                            changeView();
                        }
                    } else if (type.equals("0")) {
                        //选择
                        String cityID = data.getStringExtra("city_id");
                        String countryID = data.getStringExtra("country_id");
                        if (!TextUtils.isEmpty(cityID) && !TextUtils.isEmpty(countryID) && !cityName.equals(oldCity)) {
                            oldCity = cityName;
                            isNetWorkCity(cityID, countryID, "");
                            boolean isDialog = mmkv.decodeBool("isDialogShow", false);
                            Logs.e("是否显示Dialog：" + isDialog);
//                            isDialog = false;
                            if (!TextUtils.isEmpty(locationLatLng) && !TextUtils.isEmpty(locationCity) && !isDialog) {
                                //判断有定位
                                showDialogView(locationCity);
                            }
                        }
                    }
                    break;
            }
        }
    }

    private MyDialog builder;

    private void showDialogView(String locationCity) {
        View view = getLayoutInflater().inflate(R.layout.dialog_layout_city, null);
        builder = new MyDialog(mContext, 0, 0, view, R.style.dialog);
        builder.setCancelable(false);

        //初始化控件
        TextView btnLeft = view.findViewById(R.id.dialog_left);
        TextView btnRight = view.findViewById(R.id.dialog_right);
        TextView titleCity = view.findViewById(R.id.dialog_citys);
        titleCity.setText(locationCity + "");

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mmkv.encode("isDialogShow", true);
                builder.dismiss();
            }
        });
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mmkv.encode("isDialogShow", false);
                changeView();
                builder.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int count = EMClient.getInstance().chatManager().getUnreadMessageCount();
    }

    private void registerNet() {
        //网络变化广播监听
        broadcastReceiver = new NetBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, intentFilter);
        broadcastReceiver.setListener(this);
    }

    private void registerLauncher() {
        //后台定位广播接收器
        launcherBcastReceiver = new LauncherBcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.aibabel.menu.MENULOCATION");//定位广播监听
        intentFilter.addAction("com.aibabel.launcher.MUSIC");//音乐广播监听
        registerReceiver(launcherBcastReceiver, intentFilter);
        launcherBcastReceiver.setListener(this);
    }


    @Override
    public void netState(boolean isAvailable) {
        /**
         * 获取首页数据，
         * 第一次必须走网络监听广播。
         * 第N次监听到广播不做处理
         */
        if (isAvailable) {
            if (!flagApi) {
                Logs.e("netState:首页获取数据一次");
                flagApi = true;
                //获取当前定位信息并改变UI状态
                changeView();
            }
        }
    }

    @Override
    public void netState(String nameWifi) {
        mMainWifi.setText(nameWifi);
    }

    @Override
    public void launcherReceiver(String city) {
        if (TextUtils.isEmpty(oldCity)) {
            Logs.e("oldCity - null - 了");
            changeView();
        }
    }

    public RequestOptions options = new RequestOptions().placeholder(R.mipmap.placeholder_h).error(R.mipmap.error_h);
    @Override
    public void launcherMusic(String poiName,String urlPic,String name,int type) {
        mMainOneAppOne.setVisibility(View.GONE);
        mMainOneAppTwo.setVisibility(View.VISIBLE);
        switch (type){
            case 0://开始播放
                flagScenic = true;
                Glide.with(mContext).load(urlPic).apply(options).into(mMainOneAppTwoUrl);
                mMainOneAppTwoName.setText(name);
                mMainOneAppTwoTitle.setText(poiName);
                mMainOneAppTwoOn.setImageResource(R.mipmap.ic_scenic_pause);
                break;
            case 1://暂停
                flagScenic = false;
                mMainOneAppTwoOn.setImageResource(R.mipmap.ic_scenic_play);
                break;
            case 2://再次开始播放
                flagScenic = true;
                mMainOneAppTwoOn.setImageResource(R.mipmap.ic_scenic_pause);
                break;
            case 3://关闭播放器
                flagScenic = false;
                mMainOneAppOne.setVisibility(View.VISIBLE);
                mMainOneAppTwo.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 1.监听到有网络
     * 2.开始获取定位信息
     * 3.判断是否定位成功
     * 3.1 定位成功，修改UI，请求API获取首页数据
     * 3.2 定位失败，修改UI。不做处理
     * 等待后台定位成功的广播以及切换目的地
     */
    private void changeView() {
        locationCity = ProviderUtils.getInfo(ProviderUtils.COLUMN_CITY);
        locationCountry = ProviderUtils.getInfo(ProviderUtils.COLUMN_COUNTRY);
        locationLatLng = ProviderUtils.getInfo(ProviderUtils.COLUMN_LATITUDE) + "," + ProviderUtils.getInfo(ProviderUtils.COLUMN_LONGITUDE);
        oldCity = locationCity;
        if (!TextUtils.isEmpty(locationCity) && !TextUtils.isEmpty(locationCountry) && !TextUtils.isEmpty(locationLatLng)) {
            isNetWorkCity(locationCity, locationCountry, locationLatLng);
        } else {
            Logs.e("changeViewType:" + "定位失败");
        }
    }

    /**
     * 请求接口进行数据查询
     * 之所以加网络判断是用于第N次切换目的地
     * 请悉知
     */
    public void isNetWorkCity(String cityID, String countryID, String latlng) {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            Logs.e("isNetWorkCity:有网," + countryID + "," + cityID);
            getOkGo(cityID, countryID, latlng);
        } else {
            Logs.e("isNetWorkCity:无网," + countryID + "," + cityID);
            ToastUtil.showShort(mContext, "当前没有网络");
        }
    }

    private void getOkGo(String cityID, String countryID, String latlng) {
        Map<String, String> mapPram = new HashMap<>();
        if (!TextUtils.isEmpty(latlng)) {
            mapPram.put("location", latlng);
        } else {
            mapPram.put("countryId", countryID);
            mapPram.put("cityId", cityID);
        }

        OkGoUtil.get(false, Api.GET_MENU, mapPram, MenuDataBean.class, new BaseCallback<MenuDataBean>() {

            @Override
            public void onSuccess(String method, MenuDataBean model, String resoureJson) {
                Logs.e(Api.GET_MENU + "," + resoureJson);
                showViewData(model);
            }

            @Override
            public void onError(String method, String message, String resoureJson) {
                Logs.e(Api.GET_MENU + message);
            }

            @Override
            public void onFinsh(String method) {

            }
        });
    }

    private void showViewData(MenuDataBean bean) {
        if (bean.getData() == null) {
            return;
        }
        MenuDataBean.DataBean menu = bean.getData();
        mmkv.encode("moreThree", menu.getFgBPageHasCheep());
        mmkv.encode("mainFive", menu.getFgAPageHasCheep());

        if (locationCity.equals(menu.getCityNameCn())) {
            mMainLocation.setText(menu.getCityNameCn());
            mMainLocation.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.mipmap.main_top_location_icn), null, null, null);
        } else {
            mMainLocation.setText(menu.getCityNameCn());
            mMainLocation.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }


        mMainTimerCity.setText(menu.getCityNameCn());
        try {
            mMainTimerClock.setTimeZone(!TextUtils.equals(bean.getCode(), "-10000") ? !TextUtils.isEmpty(bean.getData().getTimeZone()) ? bean.getData().getTimeZone() : I18NUtils.getCurrentTimeZone() : I18NUtils.getCurrentTimeZone());
            mMainTimerData.setTimeZone(!TextUtils.equals(bean.getCode(), "-10000") ? !TextUtils.isEmpty(bean.getData().getTimeZone()) ? bean.getData().getTimeZone() : I18NUtils.getCurrentTimeZone() : I18NUtils.getCurrentTimeZone());
        } catch (Exception e) {
            mMainTimerClock.setTimeZone(I18NUtils.getCurrentTimeZone());
            mMainTimerData.setTimeZone(I18NUtils.getCurrentTimeZone());
        }

        if (!TextUtils.isEmpty(menu.getWeatherNowData().getTemperature_string())) {
            mMainWeatherC.setText(menu.getWeatherNowData().getTemperature_string());
        } else {
            mMainWeatherC.setText("—·—");
        }
        if (!TextUtils.isEmpty(menu.getWeatherNowData().getWeather())) {
            mMainWeatherDes.setText(menu.getWeatherNowData().getWeather());
        } else {
            mMainWeatherDes.setText("—·—");
        }
        if (!TextUtils.isEmpty(menu.getCurrency100WaiBi())) {
            mMainHuiLvCny.setText(menu.getCurrency100WaiBi());
        } else {
            mMainHuiLvCny.setText("—·—");
        }
        if (!TextUtils.isEmpty(menu.getCurrencyCny())) {
            mMainHuiLvChg.setText(menu.getCurrencyCny());
        } else {
            mMainHuiLvChg.setText("—·—");
        }

        if (!TextUtils.isEmpty(menu.getAddrPicUrl())) {
            mmkv.encode("addressUrl", menu.getAddrPicUrl());
            Glide.with(mContext).load(menu.getAddrPicUrl()).into(mMainAddressPic);
        } else {
            mmkv.encode("addressUrl", "null");
            mMainAddressPic.setImageResource(R.mipmap.ic_top_default);
        }


        //根据后台参数配置 默认1
        switch (mmkv.decodeString("mainFive", "1")) {
            case "1"://当地玩乐
                mMainFiveRl.setBackgroundResource(R.mipmap.ic_game_bg);
                mMainFiveIv.setImageResource(R.mipmap.ic_game_img);
                mMainFiveTv.setText("当地玩乐");
                break;
            case "2"://特价商品
                mMainFiveRl.setBackgroundResource(R.mipmap.ic_shop_bg);
                mMainFiveIv.setImageResource(R.mipmap.ic_shop_img);
                mMainFiveTv.setText("特价商品");
                break;
        }
    }

    private int indexNativeMask = 0;
    private void startMessageActivity() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        String nativeDate = simpleDateFormat.format(date);
        String nativeMask = mmkv.decodeString("nativeMask","");
        String nativeMaskIndex = mmkv.decodeString("nativeMaskIndex","");
        if (TextUtils.isEmpty(nativeMask) && TextUtils.isEmpty(nativeMaskIndex)){
            //1.第一天第一次打开
            indexNativeMask = 1;
            mIvNativeMask.setVisibility(View.VISIBLE);
            mmkv.encode("nativeMask",nativeDate);
            mmkv.encode("nativeMaskIndex","1");
        }else if (nativeMaskIndex.equals("1")){
            if (nativeMask.equals(nativeDate)){
                indexNativeMask = 3;
                //2.第一天第N次打开
                mIvNativeMask.setVisibility(View.GONE);
                startMessage();
            }else{
                //3.第二天第一次打开
                indexNativeMask = 1;
                mIvNativeMask.setVisibility(View.VISIBLE);
                mmkv.encode("nativeMask",nativeDate);
                mmkv.encode("nativeMaskIndex","2");
            }
        }else if (nativeMaskIndex.equals("2")){
            indexNativeMask = 3;
            //4.第二天第N次打开
            mIvNativeMask.setVisibility(View.GONE);
            startMessage();
        }
        if (hxMessage > 0) {
            mIvNativeMask.setImageResource(R.mipmap.ic_native_im_bg);
        }else{
            mIvNativeMask.setImageResource(R.mipmap.ic_native_xi_bg);
        }

    }

    public void startMessage(){
        mIvNativeMask.setVisibility(View.GONE);
        if (hxMessage > 0) {
            fragment_index = 1;
        }
        Intent intent = new Intent(this, com.aibabel.message.MsgMainActivity.class);
        intent.putExtra("count", hxMessage);
        intent.putExtra("fragment_index", fragment_index);
        startActivity(intent);
        homeBadge.setVisibility(View.GONE);
        makeRead();
    }


    //=================================================环信消息开始===================================





    /**
     * 开始环信服务并传输数据
     */
    private void startMessageService() {
        Intent messageService = new Intent();
        messageService.setClass(getApplicationContext(), MessageService.class);
        messageService.putExtra("messenger", new Messenger(handler));
        startService(messageService);
    }

    private void getHxUserInfo(){

        Intent intent = new Intent(Constant.ACTION_HX_USERINFO);
        sendBroadcast(intent);
    }



    /**
     * 清除已读标记
     */
    private void makeRead() {
        hxMessage = 0;
        fragment_index = 0;
        Intent intent = new Intent(Constant.ACTION_MAKE_READED);
        sendBroadcast(intent);
    }

    @Override
    public void makeMessageAsRead() {
        hxMessage = 0;
        fragment_index = 0;
        Intent intent = new Intent(Constant.ACTION_MAKE_READED);
        sendBroadcast(intent);
    }


    /**
     * 声明静态内部类不会持有外部类的隐式引用
     */
    private class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == Constant.MSG_RECEIVER) {
                    hxMessage = msg.arg1;
                    if (hxMessage + set_BadgeCount > 0) {
                        homeBadge.setBadgeCount(hxMessage + set_BadgeCount);
                        homeBadge.setVisibility(View.VISIBLE);
                    } else {
                        homeBadge.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
    }

//===================================环信结束==============================================

    public static MaterialBadgeTextView home_badge;
    public static int set_BadgeCount = 0;
    /********锁机流程********/
    public static final int toast_rent_Time = 16;
    public static final String bunder_iszhuner = "zhuner";
    public static final String bunder_qudao = "kefu";
    public static final String order_channelName = "order_channelName";
    public static final String order_oid = "order_oid";
    public static final String order_uid = "order_uid";
    public static final String order_uname = "order_uname";
    public static final String order_sn = "order_sn";
    public static final String order_from = "order_from_time";
    public static final String order_endttime = "orderendttime";
    public static final String order_islock = "order_islock";
    public static final String order_lockattime = "order_at";
    public static final String order_isZhuner = "order_isZhuner";

    private static final String neverUseNet_start = "never_user_start_time";
    private static final String neverUseNet_end = "never_user_end_time";
    private static final String neverUseNetflag = "never_user_flag";
    public static LooptempHandler loopHandler;
    private static boolean locknetsync = true;
    /**
     * 如果锁机了，再次同步一次
     */
    private static boolean iflocksyncAgain = true;


    /***
     * 这是一个静态,loop轮询机制
     */
    public class LooptempHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public LooptempHandler(Activity activity) {
            mWeakReference = new WeakReference<Activity>(activity);
        }

        @TargetApi(Build.VERSION_CODES.O)
        @Override
        public void handleMessage(Message msg) {
            try {

                LogUtil.e("msg.what = " + msg.what);

                final Activity activity = mWeakReference.get();
                if (activity != null) {

                    switch (msg.what) {
                        case 100:
                            try {
                                boolean RentLocked_fore = DetectUtil.isForeground(activity, RentLockedActivity.class);
                                LogUtil.e("RentLocked_fore " + RentLocked_fore);
                                SqlUtils.deleteDataAll();
                                if (RentLocked_fore) return;
                                Bundle getbundle = msg.getData();
                                String zhuner_str = getbundle.getString(bunder_iszhuner);
                                String qudao_str = getbundle.getString(bunder_qudao);
                                LogUtil.e("RentLockedActivity start" + zhuner_str + qudao_str);
                                if (TextUtils.isEmpty(zhuner_str)) {
                                    int isZhuner = SharePrefUtil.getInt(mContext, order_isZhuner, -1);
                                    if ((isZhuner == 1)) {
                                        zhuner_str = "zhuner";
                                    }
                                }
                                if (TextUtils.isEmpty(qudao_str)) {
                                    qudao_str = SharePrefUtil.getString(mContext, order_channelName, "");
                                }
                                Intent keepuse = new Intent(activity, RentLockedActivity.class); //已经到期
                                keepuse.putExtra(bunder_iszhuner, zhuner_str);
                                keepuse.putExtra(bunder_qudao, qudao_str);
                                startActivity(keepuse);
                                if (iflocksyncAgain) {
                                    iflocksyncAgain = false;
                                    loopHandler.sendEmptyMessageDelayed(130, 1000 * 5);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                        case 110:

                            boolean isForeground = DetectUtil.isForeground(activity, SimDetectActivity.class);
                            LogUtil.e("isForeground:" + isForeground);
                            if (!isForeground) {
                                Intent simdetect = new Intent(activity, SimDetectActivity.class);
                                startActivity(simdetect);
                            }
                            break;
                        case 120:
                            boolean Foreground = DetectUtil.isForeground(activity, RentDialogActivity.class);
                            if (!Foreground) {
                                Intent RentLocked = new Intent(activity, RentDialogActivity.class); //即将到期
                                startActivity(RentLocked);
                            }
                            break;
                        case 130: ////同步订单
                            if (isNetworkConnected()) {
                                LogUtil.e("isNetworkConnected = true");
                                SharePrefUtil.saveString(mContext, neverUseNetflag, "net_ok");
                                syncOrder(activity);
                            } else {
                                ///没有网络情况下
                                lock90day();
                            }
                            break;
                        case 200:
                            unlock_ok_clear();  //清除flag等
                            if (loopHandler != null)
                                loopHandler.sendEmptyMessageDelayed(130, 1000 * 60 * 5);
                            break;
                        case 300:
                            //无网络的话更新时间
                            if (!isNetworkConnected()) {
                                updatetime(CalenderUtil.getyyyyMMddHHmmss());
                            }
                            break;
                        case 301:
                            set_BadgeCount += 1;
                            home_badge.setBadgeCount(set_BadgeCount + hxMessage);
                            fragment_index = 0;
                            home_badge.setBadgeCount(set_BadgeCount);
                            //消息里面显示红点

                            try {
                                PushMessageBean bean = (PushMessageBean) msg.obj;
                                bean.setBadge(true);
                                SqlUtils.updateBadgeBean(bean);
                                LogUtil.e("++++++++++++new ");
                            } catch (Exception e) {
                            }
                            break;
                        case 302:
                            set_BadgeCount -= 1;
                            if (set_BadgeCount > 0) home_badge.setBadgeCount(set_BadgeCount);
                            else {
                                set_BadgeCount = 0;
                                home_badge.setBadgeCount(set_BadgeCount);
                            }
                            break;
                        case 310:
                            /**####  start-hjs-addStatisticsEvent   ##**/
                            try {
                                HashMap<String, Serializable> add_hp = (HashMap<String, Serializable>) msg.obj;
                                addStatisticsEvent("push_notification", add_hp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            /**####  end-hjs-addStatisticsEvent  ##**/
                            break;
                        case 320:
                            try {
                                HashMap<String, Serializable> add_hp = (HashMap<String, Serializable>) msg.obj;
                                addStatisticsEvent("push_notification1", add_hp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 330:
                            try {
                                HashMap<String, Serializable> add_hp = (HashMap<String, Serializable>) msg.obj;
                                addStatisticsEvent("push_notification2", add_hp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 340:
                            try {
                                HashMap<String, Serializable> add_hp = (HashMap<String, Serializable>) msg.obj;
                                addStatisticsEvent("push_notification5", add_hp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        case 400:
                            HashMap<String, Serializable> add_hp = (HashMap<String, Serializable>) msg.obj;
                            LogUtil.e("hjs" + add_hp.get("key"));
                            break;
                        case 800:
                           getHxUserInfo();
                            break;
                        default:
                            break;

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 跳转 100 handler
     */
    private void lockloopmsg(String order_end) {
//        String order_end = SharePrefUtil.getString(mContext, order_endttime, "");
        String channelName = SharePrefUtil.getString(mContext, order_channelName, "");
        LogUtil.e("channelName=" + (channelName));
        int isZhuner = SharePrefUtil.getInt(mContext, order_isZhuner, -1);
        if (TextUtils.isEmpty(order_end)) {
            return;
        }

        try {
            LogUtil.e("lockloopmsg=" + (order_end));
            //负数的话为已经过期{
            if ((!TextUtils.isEmpty(order_end)) && (CalenderUtil.compaeTimeWithAfter24(order_end) <= 0)) {
                if (isNetworkConnected()) {
                    if (locknetsync) {
                        if (isNetworkConnected()) syncOrder(getApplication());
                        locknetsync = false;
                    }
                } else {
                    Message message = new Message();
                    message.what = 100;
                    Bundle bun = new Bundle();

                    if ((isZhuner == 1)) {
                        bun.putString(bunder_iszhuner, "zhuner");
                    } else if (isZhuner == 0) {
                        bun.putString(bunder_qudao, channelName);
                    } else {
                        if (TextUtils.isEmpty(channelName)) {
                            bun.putString(bunder_iszhuner, "zhuner");
                            bun.putString(bunder_qudao, "");
                        } else {
                            bun.putString(bunder_iszhuner, "zz");
                            bun.putString(bunder_qudao, channelName);
                        }
                    }
                    message.setData(bun);
                    if (loopHandler != null) loopHandler.sendMessage(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isnetok = true;

    private void requestNetwork() {
        final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo network = connectivityManager.getActiveNetworkInfo();
        int type = ConnectivityManager.TYPE_DUMMY;
        if (network != null) {
            type = network.getType();
        }

        if (type == ConnectivityManager.TYPE_MOBILE) {
            LogUtil.v(TAG, " mobile data is connected: " + network.isConnected());

        } else if (type == ConnectivityManager.TYPE_WIFI) {
            LogUtil.v(TAG, "wifi is connected: " + network.isConnected());
        }

        connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);

                LogUtil.v("void onAvailable(Network network:");
                NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();
                int type = ConnectivityManager.TYPE_DUMMY;
                if (networkinfo != null) {
                    type = networkinfo.getType();
                }

                if (type == ConnectivityManager.TYPE_MOBILE) {
                    LogUtil.v("onAvailable mobile data is connected: " + networkinfo.isConnected());

                } else if (type == ConnectivityManager.TYPE_WIFI) {
                    LogUtil.v("onAvailable wifi is connected: " + networkinfo.isConnected());
                }

                Log.e("SERVICE_FUWU", "监听到当前有网");
                // TODO: 2019/4/17 b博博业务逻辑
                // getInternetService();
                if (isnetok) {
                    if (loopHandler != null) loopHandler.sendEmptyMessageDelayed(130, 10000);
                    isnetok = false;
                }
            }

            @Override
            public void onLost(Network network) {
                super.onLost(network);
                LogUtil.v("onLost(Network network)  ");

                NetworkInfo networkinfo = connectivityManager.getActiveNetworkInfo();

                int type = ConnectivityManager.TYPE_DUMMY;
                if (networkinfo != null) {
                    type = networkinfo.getType();
                }

                if (type == ConnectivityManager.TYPE_MOBILE) {
                    LogUtil.v("onLost mobile data is lost: " + networkinfo.isConnected());

                } else if (type == ConnectivityManager.TYPE_WIFI) {
                    LogUtil.v("onLost wifi is lost: " + networkinfo.isConnected());
                }

            }
        });

    }

    /*
    * 是否连接
    * */
    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public int boot_start_lock() {
        int lockflag = SharePrefUtil.getInt(mContext, order_islock, 0);

        if (lockflag == 1) {
            Log.e("hjs", "上次锁机，开机继续锁机");
            if (loopHandler != null) loopHandler.sendEmptyMessageDelayed(100, 1000);
            return 1;
        }
        return -1;
    }

    /**
     * 测试IP
     */
    //private static final String url_sync_order = "https://wx.aibabel.com:3002/common/api/machine/syncOrder";
    private static final String url_sync_order = "https://api.web.aibabel.cn:7001/common/api/machine/syncOrder";

    /**
     * 同步订单， hjs
     *
     * @param context
     */
    private void syncOrder(final Context context) {
        try {
            LogUtil.e("sn = " + CommonUtils.getSN());
            OkGo.<String>post(url_sync_order)
                    .tag(this)
                    .params("sn", CommonUtils.getSN())
                    .params("isInChina", LocationUtils.locationWhere(context))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            if ((response != null) && (response.body() != null)) {
                                String reposStr = response.body().toString();
                                LogUtil.e("sync = " + reposStr);
                                try {
                                    SyncOrder syncOrderObj = FastJsonUtil.changeJsonToBean(reposStr, SyncOrder.class);
                                    if ((syncOrderObj != null)) {
                                        LogUtil.e("synorder != null");
                                        Locklogic(context, syncOrderObj);
                                        //SharePrefUtil.saveString();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();

                                }
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            LogUtil.e("reposStr onError = ");
                            ////okgerror();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 90天锁机逻辑,或者
     */
    public void lock90day() {
        ///没有网络情况下
        try {
            if (!isNetworkConnected()) {
                int lock_type = boot_start_lock();
                LogUtil.e("lock_type =" + lock_type);
                if (lock_type == 1) return;

                String endtime = SharePrefUtil.getString(mContext, neverUseNet_end, "");//90天未使用
                if (!TextUtils.isEmpty(endtime)) {
                    int comparetime = CalenderUtil.compaeTimeWithNow(endtime);
                    LogUtil.e("lock90day " + comparetime);
                    if (((comparetime <= toast_rent_Time) && (comparetime >= 11))) {
                        if (loopHandler != null) loopHandler.sendEmptyMessage(120);
                        return;
                    }
                }


                String spnettemp = SharePrefUtil.getString(mContext, neverUseNetflag, "");
                LogUtil.e("neverUseNetflag =" + spnettemp);
                LogUtil.e(" neverUseNet_end =" + endtime);
                if (!TextUtils.isEmpty(endtime)) {
                    if (CalenderUtil.compaeTimeWithAfter24(endtime) <= 0) {
                        LogUtil.e(" compaeTimeWithAfter24  lockloopmsg()  <=0");
                        lockloopmsg(endtime);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 更新时间
     *
     * @param partime
     */
    private void updatetime(String partime) {
        if (partime == null) return;
        try {
            String end90time = CalenderUtil.calculateTimeDifferenceadd90(partime);
            SharePrefUtil.put(mContext, neverUseNet_start, partime);
            SharePrefUtil.put(mContext, neverUseNet_end, end90time);
            Log.e("hjs", "updatetime ok" + end90time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean synctimefore = true;

    /**
     * 锁机逻辑，hjs
     */
    public void Locklogic(Context context, SyncOrder synorder) {
        SyncOrder.Body OrderBody = synorder.getBody();
        if (OrderBody != null) {
            String chanelname = OrderBody.getChannelName();
            String oid = OrderBody.getOid();
            String uid = OrderBody.getUid();
            String uname = OrderBody.getUname();
            String sn = OrderBody.getSn();
            String from_time = OrderBody.getF();
            String end_time = OrderBody.getT();
            int islock = OrderBody.getIsLock();
            int attime = OrderBody.getAt();
            int isZhuner = OrderBody.getIsZhuner();

            if (!TextUtils.isEmpty(oid)) {
                String order_id = SharePrefUtil.getString(context, order_oid, "");
                if ((!TextUtils.isEmpty(order_id)) && (!order_id.equalsIgnoreCase(oid))) {
                    if (loopHandler != null) loopHandler.sendEmptyMessage(800);
                }
                if(TextUtils.isEmpty(order_id)){
                    if (loopHandler != null) loopHandler.sendEmptyMessage(800);
                }
            }


            SharePrefUtil.saveString(context, order_channelName, chanelname);
            SharePrefUtil.saveString(context, order_oid, oid);
            SPHelper.save(order_oid, oid);
            DataManager.getInstance().setSaveString(order_oid, oid);
            SharePrefUtil.saveString(context, order_uid, uid);
            SharePrefUtil.saveString(context, order_uname, uname);

            SharePrefUtil.saveString(context, order_endttime, end_time);
            SharePrefUtil.saveString(context, order_sn, sn);
            SharePrefUtil.saveString(context, order_from, from_time);

            if (!TextUtils.isEmpty(end_time)) {
                SharePrefUtil.saveString(context, end_time, end_time);
                updatetime(end_time);
            } else {
                try {
                    String[] startstr = CalenderUtil.calculateTimeDifferenceByDuration();
                    if (startstr != null) {
                        String starttime_str = startstr[0];
                        String end_time_str = startstr[1];
                        LogUtil.e(" = order starttime= " + starttime_str);
                        LogUtil.e(" =order  end_time= " + end_time_str);
                        SharePrefUtil.saveString(mContext, neverUseNet_start, starttime_str);
                        SharePrefUtil.saveString(mContext, neverUseNet_end, end_time_str);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if (islock >= 0) {
                SharePrefUtil.saveInt(context, order_islock, islock);
                try {
                    boolean RentLocked_fore = DetectUtil.isForeground(this, RentLockedActivity.class);
                    boolean RentKeepUse = DetectUtil.isForeground(this, RentKeepUseActivity.class);
                    if ((RentKeepUse || RentLocked_fore) && islock == 0) {
                        LogUtil.e("RentLocked_fore = " + RentLocked_fore);
                        try {
                            RentLockedActivity.finsRentlock();
                            RentKeepUseActivity.finsRentlock();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //if (loopHandler != null) loopHandler.sendEmptyMessage(200);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            SharePrefUtil.saveInt(context, order_lockattime, attime);
            SharePrefUtil.saveInt(context, order_isZhuner, isZhuner);


            try {
                ///服务器锁机
                if (islock == 1) {
                    LogUtil.e("服务器锁机 ");
                    Message message = new Message();
                    message.what = 100;
                    Bundle bun = new Bundle();
                    if ((isZhuner == 1)) {
                        bun.putString(bunder_iszhuner, "zhuner");
                    } else if (isZhuner == 0) {
                        bun.putString(bunder_qudao, chanelname);
                    } else {
                        if (TextUtils.isEmpty(chanelname)) {
                            bun.putString(bunder_iszhuner, "zhuner");
                            bun.putString(bunder_qudao, "");
                        } else {
                            bun.putString(bunder_iszhuner, "zz");
                            bun.putString(bunder_qudao, chanelname);
                        }
                    }
                    message.setData(bun);
                    if (loopHandler != null) loopHandler.sendMessage(message);
                    return;
                }

                int comparetime = CalenderUtil.compaeTimeWithNow(end_time);
                LogUtil.e("提醒续租 ");
                //提醒续租
                if (((comparetime <= toast_rent_Time) && (comparetime >= 11))) {
                    if (loopHandler != null) loopHandler.sendEmptyMessage(120);
                    return;
                }
                LogUtil.e("到期后24小时内锁机 _fail ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            String order_id = SharePrefUtil.getString(context, order_oid, "");
            ///国外 并且无订单
            if ((LocationUtils.locationWhere(context) == 0) && (TextUtils.isEmpty(order_id))) {
                LogUtil.e("国外 并且无订单  ");
            }
        }
    }

    /**
     * 扫码解锁后 清除标志位、清楚90天未联网日期、关闭lock按钮界面
     */
    private void unlock_ok_clear() {
        try {
            boolean RentLocked_fore = DetectUtil.isForeground(this, RentLockedActivity.class);
            LogUtil.e("RentLocked_fore = " + RentLocked_fore);
            try {
                if (RentLocked_fore) {
                    RentLockedActivity.finsRentlock();
                }
                RentKeepUseActivity.finsRentlock();
                RentLockedActivity.finsRentlock();
            } catch (Exception e) {

            }

            clearALlsharePreutil();

            init_neveruser();
            //TODO 清除服务器域名
            LogUtil.e("onRestart = RentLocked_fore");

            locknetsync = true;
            isnetok = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除所有sharep 订单信息
     */
    public void clearALlsharePreutil() {
        SqlUtils.deleteDataAll();
        SharePrefUtil.put(mContext, neverUseNetflag, "");
        SharePrefUtil.put(mContext, neverUseNet_start, "");
        SharePrefUtil.put(mContext, neverUseNet_end, "");
        SharePrefUtil.put(mContext, order_channelName, "");
        SharePrefUtil.put(mContext, order_oid, "");
        SharePrefUtil.put(mContext, order_uid, "");
        SharePrefUtil.put(mContext, order_uname, "");
        SharePrefUtil.put(mContext, order_sn, "");
        SharePrefUtil.put(mContext, order_from, "");
        SharePrefUtil.put(mContext, order_endttime, "");
        SharePrefUtil.put(mContext, order_islock, 0);
        SharePrefUtil.put(mContext, order_lockattime, 0);
        SharePrefUtil.put(mContext, order_isZhuner, 0);
        int lockflag = SharePrefUtil.getInt(mContext, order_islock, 0);
        String get_starttime = SharePrefUtil.getString(mContext, neverUseNet_start, "");
        String get_endtime = SharePrefUtil.getString(mContext, neverUseNet_end, "");

        LogUtil.e("get_starttime==" + get_starttime);
        LogUtil.e("get_endtime==" + get_endtime);

    }

    /**
     * 初始化从来未使用的函数
     */
    private void init_neveruser() {
        try {
            String get_starttime = "";
            String get_endtime = "";
            try {
                get_starttime = SharePrefUtil.getString(mContext, neverUseNet_start, "");
                get_endtime = SharePrefUtil.getString(mContext, neverUseNet_end, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            LogUtil.e("start__= " + get_starttime + " = init_neveruser = " + get_endtime);
            if (TextUtils.isEmpty(get_starttime) || (TextUtils.isEmpty(get_endtime))) {
                String[] startstr = CalenderUtil.calculateTimeDifferenceByDuration();
                if (startstr != null) {
                    String starttime = startstr[0];
                    String end_time = startstr[1];

                    LogUtil.e(" = init_neveruser starttime= " + starttime);
                    LogUtil.e(" =  end_time= " + end_time);

                    SharePrefUtil.saveString(mContext, neverUseNet_start, starttime);
                    SharePrefUtil.saveString(mContext, neverUseNet_end, end_time);

                    get_starttime = SharePrefUtil.getString(mContext, neverUseNet_start, "");
                    LogUtil.e("  starttime= " + get_starttime);

                    get_endtime = SharePrefUtil.getString(mContext, neverUseNet_end, "");
                    LogUtil.e("  get_endtime= " + get_endtime);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            if (screenrecive != null) {
                unregisterReceiver(screenrecive);
            }

            if (null != broadcastReceiver)
                unregisterReceiver(broadcastReceiver);
        }catch (Exception e){
            Logs.e("launcher："+e.toString());
        }
//        stopService();
    }
}
