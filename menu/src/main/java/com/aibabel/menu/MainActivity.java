package com.aibabel.menu;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.mode.DataManager;
import com.aibabel.baselibrary.mode.ServerManager;
import com.aibabel.baselibrary.sphelper.SPHelper;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.baselibrary.utils.ProviderUtils;
import com.aibabel.baselibrary.utils.ServerKeyUtils;
import com.aibabel.baselibrary.utils.SharePrefUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.menu.app.MyApplication;
import com.aibabel.menu.base.BaseActivity;
import com.aibabel.menu.bean.Domain;
import com.aibabel.menu.bean.MenuDataBean;
import com.aibabel.menu.bean.PublicBean;
import com.aibabel.menu.bean.PushMessageBean;
import com.aibabel.menu.bean.ReletSn;
import com.aibabel.menu.bean.ServerBean;
import com.aibabel.menu.bean.SyncOrder;
import com.aibabel.menu.bitmap.MyTransformtion;
import com.aibabel.menu.broadcast.NetBroadcastReceiver;
import com.aibabel.menu.broadcast.NotificationClickReceiver;
import com.aibabel.menu.broadcast.ResidentNotificationHelper;
import com.aibabel.menu.dialog.CustomDialog;
import com.aibabel.menu.h5.AndroidJS;
import com.aibabel.menu.inf.UpdateMenu;
import com.aibabel.menu.rent.RentDialogActivity;
import com.aibabel.menu.rent.RentLockedActivity;
import com.aibabel.menu.util.AppStatusUtils;
import com.aibabel.menu.util.CalenderUtil;
import com.aibabel.menu.util.CommonUtils;
import com.aibabel.menu.util.Constans;
import com.aibabel.menu.util.DetectUtil;
import com.aibabel.menu.util.DoubleClickUtil;
import com.aibabel.menu.util.FileUtil;
import com.aibabel.menu.util.GlideCacheUtil;
import com.aibabel.menu.util.I18NUtils;
import com.aibabel.menu.util.L;
import com.aibabel.menu.util.LocationUtils;
import com.aibabel.menu.util.LogUtil;
import com.aibabel.menu.util.NetUtil;
import com.aibabel.menu.util.RenUtils;
import com.aibabel.menu.util.SPUtils;
import com.aibabel.menu.util.ServerUtils;
import com.aibabel.menu.util.UrlConstants;
import com.aibabel.menu.view.MagicTextView;
import com.aibabel.messagemanage.JiGuangActivity;
import com.aibabel.messagemanage.sqlite.SqlUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.matrixxun.starry.badgetextview.MaterialBadgeTextView;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.mian_webview)
    WebView mianWebview;
    @BindView(R.id.main_return_img)
    ImageView mainReturnImg;
    @BindView(R.id.main_top_ctiy)
    TextView mainTopCtiy;
    @BindView(R.id.main_topPanel)
    RelativeLayout mainTopPanel;
    @BindView(R.id.main_middle_time)
    TextClock mainMiddleTime;
    @BindView(R.id.main_middle_tianqi)
    TextView mainMiddleTianqi;
    @BindView(R.id.main_middle_tianqi_du)
    TextView mainMiddleTianqiDu;
    @BindView(R.id.main_middle_ll)
    LinearLayout mainMiddleLl;
    @BindView(R.id.main_map_local_tv)
    TextView mainMapLocalTv;
    @BindView(R.id.main_map_jinru_tv)
    TextView mainMapJinruTv;
    @BindView(R.id.bttom_menu_ll_gd)
    LinearLayout bttomMenuLlGd;
    @BindView(R.id.main_bottomPanel)
    LinearLayout mainBottomPanel;
    @BindView(R.id.main_middle_time_ll)
    RelativeLayout mainMiddleTimeLl;
    @BindView(R.id.main_middle_tianqi_ll)
    RelativeLayout mainMiddleTianqiLl;
    @BindView(R.id.main_middle_huilv_ll)
    RelativeLayout mainMiddleHuilvLl;
    @BindView(R.id.main_map_rl)
    RelativeLayout mainMapRl;
    @BindView(R.id.bttom_menu_ll_yyfy)
    LinearLayout bttomMenuLlYyfy;
    @BindView(R.id.bttom_menu_ll_pzfy)
    LinearLayout bttomMenuLlPzfy;
    @BindView(R.id.bttom_menu_ll_ddwl)
    LinearLayout bttomMenuLlDdwl;
//    @BindView(R.id.main_shanghua_img)
//    ImageView mainShanghuaImg;
    @BindView(R.id.main_middle_tianqi_du1)
    TextView mainMiddleTianqiDu1;

    @BindView(R.id.main_middle_date)
    TextClock mainMiddleDate;
    @BindView(R.id.main_top_ctiy_ll)
    LinearLayout mainTopCtiyLl;
    @BindView(R.id.main_top_ctiy_img)
    ImageView mainTopCtiyImg;
    @BindView(R.id.main_return_img_ll)
    LinearLayout mainReturnImgLl;
    @BindView(R.id.tv_huilvone)
    TextView tvHuilvOne;
    @BindView(R.id.tv_huilvtwo)
    TextView tvHuilvTwo;
    @BindView(R.id.text_view_address_city)
    TextView tvAddressCity;

    private LinearLayout bottom_menu_gd;
    private LinearLayout bottom_ll;
    private RelativeLayout top_ll;
    private FrameLayout notice_start_activity;

    private WebView webView;
    WebSettings webSettings = null;
    int num;
    Rect rect;
    //上滑动画
//    public static AnimationDrawable frameAnim;
    public static UpdateMenu upListener; //更新界面接口
    CustomDialog.Builder builderChangeCity;//切换城市dialog
    CustomDialog.Builder builderNoCity;//切换城市dialog

    NetBroadcastReceiver netBroadcastReceiver;

    //泽峰  租赁逻辑类
    RenUtils renUtils;

    private final String moren_error = "file:///sdcard/offline/menu/moren_error.html";


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


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.e("MainActivity  onCreate========================");
        LogUtil.e("MainActivity_onCreate");
//        DBUtils.copyAssetsToSd(mContext,"index.html");
        //根据时区选择服务器
        initService();
        //城市切换后 更新界面
        upListener = new UpdateMenu() {

            @Override
            public void changeCity(MenuDataBean bean, String dataJson, String type) {
                L.e("changeCity============================" + dataJson);

                String content = "准儿定位到您在";
                Drawable d = mContext.getResources().getDrawable(R.mipmap.dingwei_icon);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                ImageSpan img = new ImageSpan(d, ImageSpan.ALIGN_BASELINE); // 注意这行
                SpannableStringBuilder style = new SpannableStringBuilder(content + "》" + bean.getData().getCityNameCn());
                style.setSpan(img, content.length(), content.length() + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);


                if (builderChangeCity == null) {
                    builderChangeCity = new CustomDialog.Builder(MainActivity.this, R.layout.dialog_change_city)
                            .setTv(R.id.dialog_change_content, style)
                            .setTvListener(R.id.dialog_change_btm_cancle, "", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    builderChangeCity.dismiss();
                                }
                            })
                            .setTvListener(R.id.dialog_change_btm_sure, "", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    bindMenuData(bean);
                                    builderChangeCity.dismiss();
                                }
                            }).setCanceledOnTouchOutside(false);
                } else {
                    builderChangeCity.setTvListener(R.id.dialog_change_btm_sure, "", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bindMenuData(bean);
                            builderChangeCity.dismiss();
                        }
                    });

                }
                builderChangeCity.show();
            }

            @Override
            public void updateUI(MenuDataBean bean) {
                L.e("updateUI======================" + bean.toString());
                bindMenuData(bean);
            }

            @Override
            public void updateAddr(String addr) {
                if (mainMapLocalTv != null) mainMapLocalTv.setText(addr);

            }

            @Override
            public void getDdata() {
//                getMenuData(mContext,"location","2");
            }

            @Override
            public void closePlayer() {
                webView.loadUrl("javascript:AudioControl('js-video',2)");
            }

            @Override
            public void onError(String err) {

//                ToastUtil.show(mContext, err, 500);
            }

            @Override
            public void noCity() {

                if (builderNoCity == null) {
                    builderNoCity = new CustomDialog.Builder(MainActivity.this, R.layout.dialog_tishi_no_source)
                            .setTvListener(R.id.dialog_tishi_sure, "", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    builderNoCity.dismiss();
                                }
                            }).setCanceledOnTouchOutside(false);
                }
                builderNoCity.show();

            }
        };

        try {

            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            screenrecive = new ScreenOffReceiver();
            mContext.registerReceiver(screenrecive, filter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    ScreenOffReceiver screenrecive ;
    private class ScreenOffReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_SCREEN_OFF)) {
                mainReturnImg.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        com.aibabel.baselibrary.mode.StatisticsManager.getInstance().uplaodData(getApplicationContext(), SharePrefUtil.getString(context, "order_oid", ""));

                    }
                },2000);
            }

        }
    }

    @Override
    public int getLayout(Bundle bundle) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        loopHandler = new LooptempHandler(this);
        addStatisticsEvent("menu_main_open",null);
    }

    public static MaterialBadgeTextView home_badge;
    public static int set_BadgeCount = 0;

    @Override
    protected void assignView() {
        top_ll = findViewById(R.id.main_topPanel);
        bottom_ll = findViewById(R.id.main_bottomPanel);
        bottom_menu_gd = findViewById(R.id.bttom_menu_ll_gd);
        home_badge = findViewById(R.id.home_badge);
        home_badge.setBadgeCount(0);
        notice_start_activity = findViewById(R.id.notice_start_activity);


    }

    @Override
    protected void initView() {
        rect = new Rect();
    }

    @Override
    protected void initListener() {

        setGestureListener();
        bottom_menu_gd.setOnClickListener(this);
        bttomMenuLlPzfy.setOnClickListener(this);
        bttomMenuLlYyfy.setOnClickListener(this);
        bttomMenuLlDdwl.setOnClickListener(this);
        mainMiddleTimeLl.setOnClickListener(this);
        mainMiddleTianqiLl.setOnClickListener(this);
        mainMiddleHuilvLl.setOnClickListener(this);

        mainMapRl.setOnClickListener(this);

        mainTopCtiyLl.setOnClickListener(this);
        mainReturnImgLl.setOnClickListener(this);
        notice_start_activity.setOnClickListener(this);

    }

/*    @Override
    protected int getLayoutId() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        getWindow().setExitTransition(new Slide().setDuration(400));
//        getWindow().setReenterTransition(new Slide().setDuration(400));
        return R.activity_rent_locked.activity_main;
    }*/

    @Override
    protected void initData() {

        netBroadcastReceiver = new NetBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(netBroadcastReceiver, intentFilter);
        //上滑引导的帧动画
//        frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.amin_list_shanghua);
//        mainShanghuaImg.setBackgroundDrawable(frameAnim);
//        frameAnim.start();

        webviewSetting();


        getMenuData(mContext, "location", "2");

        renUtils = new RenUtils(this);
        renUtils.startZl(); //启动租赁


        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    init_neveruser();
                    Log.e("SERVICE_FUWU", "开启网络监听");
                    requestNetwork();
                    // boot_start_lock();
                    lock90day();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }


        JPushInterface.setAlias(this, 1, CommonUtils.getSN());
        LogUtil.e("getSN:" + CommonUtils.getSN());



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
    private void syncOrder(Context context) {
        try {
            LogUtil.e("sn = " + CommonUtils.getSN());
            OkGo.<String>post(url_sync_order)
                    .tag(this)
                    .params("sn", CommonUtils.getSN())
                    .params("isInChina", LocationUtils.locationWhere(context))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(com.lzy.okgo.model.Response<String> response) {
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

            int lock_type = boot_start_lock();
            LogUtil.e("lock_type =" + lock_type);
            if (lock_type == 1) return;

            String spnettemp = SharePrefUtil.getString(mContext, neverUseNetflag, "");
            LogUtil.e("neverUseNetflag =" + spnettemp);
            if (TextUtils.isEmpty(spnettemp)) {
                String endtime = SharePrefUtil.getString(mContext, neverUseNet_end, "");//90天未使用
                LogUtil.e(" neverUseNet_end =" + endtime);
                if (!TextUtils.isEmpty(endtime)) {
                    if (CalenderUtil.compaeTimeWithAfter24(endtime) <= 0) {
                        LogUtil.e(" compaeTimeWithAfter24  lockloopmsg()  <=0");
                        lockloopmsg(endtime);
                    }
                } else {
                    LogUtil.e("neverUseNet_end=endtime = null");
                }
            } else if (spnettemp.equalsIgnoreCase("net_ok")) {
                String endtime = SharePrefUtil.getString(mContext, neverUseNet_end, "");//90天未使用
                lockloopmsg(endtime);//以前连接过网络，快到期断网，4个小时检测一次
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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

            if (!TextUtils.isEmpty(chanelname)) {
                SharePrefUtil.saveString(context, order_channelName, chanelname);
            } else LogUtil.e("chanelname empty");

////张月测试用
//            ((Activity)mContext).runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    ToastUtil.show(context,"oid="+oid,Toast.LENGTH_LONG);
//                }
//            });

            if (!TextUtils.isEmpty(oid)) {
                SharePrefUtil.saveString(context, order_oid, oid);
                SPHelper.save(order_oid, oid);
                DataManager.getInstance().setSaveString(order_oid, oid);
            } else LogUtil.e("oid empty");
            if (!TextUtils.isEmpty(uid)) {
                SharePrefUtil.saveString(context, order_uid, uid);
            } else LogUtil.e("uid = null");
            if (!TextUtils.isEmpty(uname)) {
                SharePrefUtil.saveString(context, order_uname, uname);
            } else LogUtil.e("uname = null");

            if (!TextUtils.isEmpty(from_time)) {
                SharePrefUtil.saveString(context, order_from, from_time);
            } else LogUtil.e("from_time = null");
            if (!TextUtils.isEmpty(end_time)) {
                SharePrefUtil.saveString(context, order_endttime, end_time);
            } else LogUtil.e("end_time = null");
            if (!TextUtils.isEmpty(sn)) {
                SharePrefUtil.saveString(context, order_sn, sn);
            } else LogUtil.e("sn = null");
            if (islock >= 0) {
                SharePrefUtil.saveInt(context, order_islock, islock);
                try {
                    boolean RentLocked_fore = DetectUtil.isForeground(this, RentLockedActivity.class);
                    if (RentLocked_fore&&islock==0) {
                        LogUtil.e("RentLocked_fore = " + RentLocked_fore);
                        if (loopHandler != null) loopHandler.sendEmptyMessage(200);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else LogUtil.e("order_islock = null");
            if (attime >= 0) {
                SharePrefUtil.saveInt(context, order_lockattime, attime);
            } else LogUtil.e("order_lockattime == null;");

            if (isZhuner >= 0) {
                SharePrefUtil.saveInt(context, order_isZhuner, isZhuner);
            } else LogUtil.e("order_isZhuner == null;");

            try {
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


                int comparetime = CalenderUtil.compaeTimeWithNow(end_time);

                LogUtil.e("服务器锁机 ");
                ///服务器锁机
                if (islock == 1) {
                    if (loopHandler != null) loopHandler.sendMessage(message);
                    return;
                }

                LogUtil.e("提醒续租 ");
                //提醒续租
                if (((comparetime <= toast_rent_Time) && (comparetime >= 11))) {
                    if (loopHandler != null) loopHandler.sendEmptyMessage(120);
                    return;
                }
                LogUtil.e("到期后24小时内锁机 ");
                ////到期后24小时内锁机
//                if ( CalenderUtil.compaeTimeWithAfter24(end_time)<=0) {
//                    if (loopHandler != null) loopHandler.sendMessage(message);
//                    return;
//                }

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


    public void getMenuData(Context mContext, String type, String getType) {
        Map<String, String> mapPram = new HashMap<>();
        mapPram.put("location", LocationUtils.getLocation(mContext));
        mapPram.put("cityId", MyApplication.city_id);
        mapPram.put("countryId", MyApplication.country_id);
        mapPram.put("type", getType);

        LocationUtils.printUrl(mContext, UrlConstants.GET_MENU, mapPram);

        OkGoUtil.get(false, UrlConstants.GET_MENU, mapPram, MenuDataBean.class, new BaseCallback<MenuDataBean>() {
            @Override
            public void onSuccess(String s, MenuDataBean menuDataBean, String s1) {
                Log.e("XINMENU",s1);
                bindMenuData(menuDataBean);
            }

            @Override
            public void onError(String s, String s1, String s2) {
                L.e("bindMenuData  onError ================" + s);

            }

            @Override
            public void onFinsh(String s) {

            }


        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.bttom_menu_ll_gd:
                    ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(this);
                    startActivity(new Intent(mContext, MenuActivity.class), compat.toBundle());
                    addStatisticsEvent("more_click",null);
                    break;
                case R.id.main_middle_time_ll:
                    //调起时钟
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, "com.aibabel.alliedclock"));
                    addStatisticsEvent("time_click",null);

                    break;
                case R.id.main_middle_tianqi_ll:
                    //调起天气
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, "com.aibabel.weather"));
                    addStatisticsEvent("weather_click",null);
                    break;
                case R.id.main_middle_huilv_ll:
                    //调起汇率
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, "com.aibabel.currencyconversion"));
                    addStatisticsEvent("currency_click",null);

                    break;
                case R.id.main_map_rl:
                    //调起地图
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, "com.aibabel.map"));
                    addStatisticsEvent("map_click",null);
                    break;
                case R.id.bttom_menu_ll_yyfy:
                    //调起语音翻译
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, "com.aibabel.translate"));
                    addStatisticsEvent("voice_click",null);
                    break;
                case R.id.bttom_menu_ll_pzfy:
                    //调起拍照翻译
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, "com.aibabel.ocr"));
                    addStatisticsEvent("photo_click",null);
                    break;
                case R.id.bttom_menu_ll_ddwl:
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext,"com.aibabel.scenic"));

                    break;
                case R.id.main_top_ctiy_ll:

//                    Intent intent1 = new Intent();
////                    intent1.setAction("com.aibabel.menu.MENULOCATION");
////                    sendBroadcast(intent1);
                    //调起搜索页面
                    ((MainActivity) mContext).startActivityForResult(new Intent(mContext, SearchActivity.class), 100);

                    try{
                        HashMap<String, Serializable> map = new HashMap<>();
                        map.put("mddname",oldCity+"");
                        addStatisticsEvent("mdd_click",map);
                    }catch (Exception e){}
                    break;

                case R.id.main_return_img_ll:
                    L.e("fanhuidonghua===============================");
//     top_ll.getGlobalVisibleRect(new Rect());
                    showPanel();
                    StatisticsManager.getInstance(mContext).addEventAidl(1133, new HashMap());
                    break;
                case R.id.notice_start_activity:
                    startActivity(new Intent(this, com.aibabel.messagemanage.MainActivity.class));
                    set_BadgeCount = 0;
                    home_badge.setBadgeCount(set_BadgeCount);
                    break;

            }
        } catch (Exception e) {

        }


    }


    private float mPosX, mPosY, mCurPosX, mCurPosY, last;
    private int index = 0;

    /**
     * 设置上下滑动作监听器
     *
     * @author jczmdeveloper
     */
    private void setGestureListener() {
        top_ll.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
//                        frameAnim.stop();
                        mPosX = event.getX();
                        mPosY = event.getRawY();

                        index = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = event.getX();
                        mCurPosY = event.getRawY();

                        if (mCurPosY - mPosY < 0 && (Math.abs(mCurPosY - mPosY) > 60) && (Math.abs(mCurPosX - mPosX) < 150)) {
                            top_ll.setTranslationY((mCurPosY - mPosY));
                            bottom_ll.setTranslationY(-((mCurPosY - mPosY) / 2));
                        }

                        index++;
                        if (index % 5 == 0) {
                            last = event.getRawY();
                        }


                        break;
                    case MotionEvent.ACTION_UP:


                        mCurPosY = event.getRawY();


                        L.e("offsetsByY==================" + (mCurPosY - last));
                        L.e("mCurPosY - mPosY============" + (mCurPosY - mPosY) + "======== (Math.abs(mCurPosY - mPosY) > 60)===" + (Math.abs(mCurPosY - mPosY)) + "====(Math.abs(mCurPosX - mPosX) <60)=====" + (Math.abs(mCurPosX - mPosX)));
                        if ((mCurPosY - last) >= 2) {
                            L.e("111111111111111");
                            showPanel1();


                        } else if (mCurPosY - mPosY < 0 && (Math.abs(mCurPosY - mPosY) > 60) && (Math.abs(mCurPosX - mPosX) < 300)) {

                            if (DoubleClickUtil.isFastClick()) {
                                hidePanel();
                                L.e("222222222222222");
                            }
                        } else {
                            L.e("3333333333333333");
                            showPanel1();
                        }

//                        frameAnim.start();

                        break;
                }
                return true;
            }

        });


    }


    /**
     * 上下面板打开动画
     */
    public void hidePanel() {
        ObjectAnimator.ofFloat(top_ll, "translationY", -700).setDuration(300).start();
        ObjectAnimator.ofFloat(top_ll, "alpha", 1f, 0.5f).setDuration(300).start();
        ObjectAnimator.ofFloat(bottom_ll, "translationY", 500).setDuration(300).start();
        ObjectAnimator.ofFloat(bottom_ll, "alpha", 1f, 0.4f).setDuration(300).start();
        StatisticsManager.getInstance(mContext).addEventAidl(1140, new HashMap<>());


    }

    /**
     * 上下面板打开动画
     */
    public void hidePanel1() {
        ObjectAnimator.ofFloat(top_ll, "translationY", -700).setDuration(10).start();
        ObjectAnimator.ofFloat(top_ll, "alpha", 1f, 0.5f).setDuration(10).start();
        ObjectAnimator.ofFloat(bottom_ll, "translationY", 500).setDuration(10).start();
        ObjectAnimator.ofFloat(bottom_ll, "alpha", 1f, 0.4f).setDuration(10).start();
        StatisticsManager.getInstance(mContext).addEventAidl(1141, new HashMap<>());

    }

    /**
     * 显示面板打开动画
     */
    public void showPanel() {
        ObjectAnimator.ofFloat(top_ll, "translationY", 0).setDuration(300).start();
        ObjectAnimator.ofFloat(top_ll, "alpha", 0.5f, 1f).setDuration(300).start();
        ObjectAnimator.ofFloat(bottom_ll, "translationY", 0).setDuration(300).start();
        ObjectAnimator.ofFloat(bottom_ll, "alpha", 0.5f, 1f).setDuration(300).start();
        L.e("菜单画面合并 showPanel=======================");
    }

    /**
     * 不要alpha过渡显示面板打开动画
     */
    public void showPanel1() {

        webView.loadUrl("javascript:AudioControl('js-video',2)");

        ObjectAnimator.ofFloat(top_ll, "translationY", 0).setDuration(300).start();
        ObjectAnimator.ofFloat(top_ll, "alpha", 1f, 1f).setDuration(300).start();
        ObjectAnimator.ofFloat(bottom_ll, "translationY", 0).setDuration(300).start();
        ObjectAnimator.ofFloat(bottom_ll, "alpha", 1f, 1f).setDuration(300).start();
        L.e("菜单画面合并 showPanel1=======================");

    }


    /**
     * 设置webview  缓存 注入js对象
     */
    boolean isError = false;

    public void webviewSetting() {
        webView = findViewById(R.id.mian_webview);
        webView.requestFocus();
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBlockNetworkImage(false);  //图片显示问题
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        //文件权限
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowContentAccess(true);
        //缓存相关
        if (NetUtil.isNetworkConnected(MainActivity.this)) {
            //有网络，则加载网络地址
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存模式LOAD_CACHE_ELSE_NETWORK
        } else {
            //无网络，则加载缓存路径
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 1024 * 2);
        webSettings.setDomStorageEnabled(true);//开启DOM storage API功能
        webSettings.setDatabaseEnabled(true);//开启database storeage API功能
        String cacheDirPath = MainActivity.this.getFilesDir().getAbsolutePath() + "/webcache";//缓存路径
        Log.e("==============", cacheDirPath);
        webSettings.setDatabasePath(cacheDirPath);//设置数据库缓存路径
        webSettings.setAppCachePath(cacheDirPath);//设置AppCaches缓存路径
        webSettings.setAppCacheEnabled(true);//开启AppCaches功能
        webSettings.setDisplayZoomControls(false);//关闭缩放
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);

        webView.addJavascriptInterface(new AndroidJS(this), "menuApp");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });


        if (TextUtils.isEmpty(MyApplication.city_id)) {
            webView.loadUrl("file:///sdcard/offline/menu/moren_no_data.html");

        } else {

//            if (CommonUtils.isAvailable(mContext)) {
//                //在线h5
//                webView.loadUrl("http://destination.cdn.aibabel.com/menuH5/image/H5page/index1812110952.html");
//
//            } else {
//                webView.loadUrl("file:///sdcard/offline/menu/index.html");
//            }
        }

        webView.setWebViewClient(new WebViewClient() {

            //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isError = false;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                /**
                 * 防止死循环
                 */
                //if(!TextUtils.isEmpty(description)){
                //   if((description).contains("ERR")){
                if (view == null) return;
                if ((view != null) && (view.getUrl() != null)) {
                    if ((view.getUrl().equalsIgnoreCase(moren_error))) {
                        return;
                    }
                }
                // }
                //}
                view.loadUrl(moren_error);
                L.e("1111111111111111111" + errorCode + "--------" + description);
                isError = true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                /**
                 * 防止死循环
                 */

                if (view == null) return;
                if ((view != null) && (view.getUrl() != null)) {
                    if ((view.getUrl().equalsIgnoreCase(moren_error))) {
                        return;
                    }
                }


                if (request.isForMainFrame()) { // 或者： if(request.getUrl().toString() .equals(getUrl()))
                    // 在这里显示自定义错误页
                    view.loadUrl(moren_error);
                }
                L.e("webViewo nReceivedError(view, request, error);" + error + "--------");
                isError = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (isError) {
                    view.loadUrl(moren_error);
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

        });
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

    }


    /**
     * 将请求绑定在界面上   并处理图片  和webview缓存
     * light
     *
     * @param bean
     */
    String oldCity = ""; //城市相同  部分内容不更新

    public void bindMenuData(MenuDataBean bean) {
        if (bean == null) return;
        if (mContext == null) return;
        if (StatisticsManager.getInstance(mContext) == null) return;

        try {
            MyApplication.city_id = bean.getData().getCityId();
            MyApplication.country_id = bean.getData().getCountryId();
            if (!TextUtils.equals(oldCity, bean.getData().getCityNameCn())) {
                if (TextUtils.equals("-10000", bean.getCode())) {
                    StatisticsManager.getInstance(mContext).addEventAidl(1142, new HashMap() {{
                        put("type", "离线");
                        put("city", bean.getData().getCityNameCn());

                    }});
                } else {
                    StatisticsManager.getInstance(mContext).addEventAidl(1142, new HashMap() {{
                        put("type", "在线");
                        put("city", bean.getData().getCityNameCn());

                    }});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (TextUtils.equals(SPUtils.get(mContext, "showCityName", "").toString(), bean.getData().getCityNameCn())) {
                //显示的桌面  就是当前城市
                mainTopCtiyImg.setVisibility(View.VISIBLE);
            } else {
                mainTopCtiyImg.setVisibility(View.GONE);
            }

//            SPUtils.put(mContext,"menuShowkey",bean.getData().getCountryNameCn()+"_"+bean.getData().getCityNameCn()+"_"+bean.getData().getBaiduStreetName());
            SPUtils.put(mContext, "menuShowkey", bean.getData().getCountryNameCn() + "_" + bean.getData().getCityNameCn());

            mainTopCtiy.setText(!TextUtils.equals(bean.getCode(), "-10000") ? TextUtils.isEmpty(bean.getData().getCityNameCn()) ? "--·--" : bean.getData().getCityNameCn().trim() + "·" + bean.getData().getCountryNameCn().trim() : bean.getData().getCityNameCn().trim() + "·" + bean.getData().getCountryNameCn().trim());
            if (mainTopCtiy.getText().toString().equals("--·--")) {
                mainTopCtiyImg.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            mainTopCtiyImg.setVisibility(View.GONE);
            mainTopCtiy.setText("--·--");

        }

        L.e("picUrl================" + bean.getData().getAddrPicUrl());
        GlideCacheUtil.getInstance().clearImageAllCache(mContext);
        Glide.with(mContext).load(TextUtils.isEmpty(bean.getData().getAddrPicUrl()) ? R.mipmap.morentu1 : bean.getData().getAddrPicUrl()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                top_ll.setBackground(resource);
                BitmapDrawable bd = (BitmapDrawable) resource;
                FileUtil.saveImage(mContext, bd.getBitmap(), "cityImg");

            }
        });
        Glide.with(mContext).load(TextUtils.isEmpty(bean.getData().getAddrPicUrl()) ? R.mipmap.morentu1 : bean.getData().getAddrPicUrl()).apply(new RequestOptions().bitmapTransform(new MyTransformtion(mContext))).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {

                BitmapDrawable bd = (BitmapDrawable) resource;
                FileUtil.saveImage(mContext, bd.getBitmap(), "mohuImg");

            }
        });

        //中间仪表盘   时间
        try {
            mainMiddleTime.setTimeZone(!TextUtils.equals(bean.getCode(), "-10000") ? !TextUtils.isEmpty(bean.getData().getTimeZone()) ? bean.getData().getTimeZone() : I18NUtils.getCurrentTimeZone() : I18NUtils.getCurrentTimeZone());
            mainMiddleDate.setTimeZone(!TextUtils.equals(bean.getCode(), "-10000") ? !TextUtils.isEmpty(bean.getData().getTimeZone()) ? bean.getData().getTimeZone() : I18NUtils.getCurrentTimeZone() : I18NUtils.getCurrentTimeZone());

        } catch (Exception e) {
            mainMiddleTime.setTimeZone(I18NUtils.getCurrentTimeZone());
            mainMiddleDate.setTimeZone(I18NUtils.getCurrentTimeZone());

        }


        //天气
        try {
            mainMiddleTianqi.setText(!TextUtils.equals(bean.getCode(), "-10000") ? TextUtils.isEmpty(bean.getData().getWeatherNowData().getWeather()) ? "天气" : bean.getData().getWeatherNowData().getWeather() : "天气");
            mainMiddleTianqiDu.setText(!TextUtils.equals(bean.getCode(), "-10000") ? bean.getData().getWeatherNowData().getTemperature_string() : "--·--");
            mainMiddleTianqiDu1.setText(!TextUtils.equals(bean.getCode(), "-10000") ? "℃" : "");
        } catch (Exception e) {
            mainMiddleTianqi.setText("天气");
            mainMiddleTianqiDu.setText("--·--");
            mainMiddleTianqiDu1.setText("");
        }

        // 汇率
        try {
            if (!TextUtils.isEmpty(bean.getData().getCurrency100WaiBi())){
                tvHuilvOne.setText(bean.getData().getCurrency100WaiBi()+"");
            }else{
                tvHuilvOne.setText("--·--");
            }

            if (!TextUtils.isEmpty(bean.getData().getCurrencyCny())){
                tvHuilvTwo.setText(bean.getData().getCurrencyCny()+"");
            }else{
                tvHuilvTwo.setText("--·--");
            }
        }catch (Exception e){
            tvHuilvOne.setText("--·--");
            tvHuilvTwo.setText("--·--");
        }

        try {
            if (!TextUtils.isEmpty(bean.getData().getCityNameCn())){
                tvAddressCity.setText(bean.getData().getCityNameCn()+"");
            }else{
                tvAddressCity.setText("--·--");
            }
        }catch (Exception e){
            tvAddressCity.setText("--·--");
        }


        String addr = ProviderUtils.getInfo("addr");
        L.e("dw======================" + addr);
        if (!TextUtils.isEmpty(addr)) {
            mainMapLocalTv.setText(addr);
        } else {
            mainMapLocalTv.setText("--·--");
        }


        //缓存相关  城市相同不更新
        if (!TextUtils.equals(oldCity, bean.getData().getCityNameCn())) {
            if (TextUtils.equals(bean.getData().getCityNameFrom(), "bd")) {
                webView.loadUrl("file:///sdcard/offline/menu/moren_no_data.html");

            } else {

                if (CommonUtils.isAvailable(MainActivity.this)) {
                    //有网络，则加载网络地址
                    webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存模式LOAD_CACHE_ELSE_NETWORK
//                    StringBuffer sb = new StringBuffer("http://192.168.50.224:8080/mudidi/index.html");
                    StringBuffer sb = new StringBuffer(bean.getData().getAddrDetialPage());
                    sb.append("?");
                    sb.append("sn=" + CommonUtils.getSN());
                    sb.append("&");
                    sb.append("sv=" + Build.DISPLAY);
                    sb.append("&");
                    if (TextUtils.isEmpty(MyApplication.city_id)) {
                        sb.append("cityId=" + " ");
                    } else {
                        sb.append("cityId=" + MyApplication.city_id);
                    }

                    sb.append("&");
                    sb.append("countryId=" + MyApplication.country_id);
                    sb.append("&");
                    sb.append("cityName=" + bean.getData().getCityNameCn());
                    webView.loadUrl(sb.toString());
                    L.e("webview  loadurl==============" + sb.toString());

                } else {
                    //无网络，
                    webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                    webView.loadUrl("file:///sdcard/offline/menu/index.html");
                }
            }
        }

        oldCity = bean.getData().getCityNameCn();
        addStatisticsEvent("mdd_up",null);
    }


    /**
     * 内嵌了  webview用disp监听物理按键
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {

            switch (event.getKeyCode()) {
                case 131:
                    L.e("131===================");

                    if (!top_ll.getGlobalVisibleRect(rect)) {
                        showPanel1();
                    }
                    break;
                case 132:
                    if (!top_ll.getGlobalVisibleRect(rect)) {
                        showPanel1();
                    }
                    break;
                case 133:
                    L.e("133===================");
                    if (!top_ll.getGlobalVisibleRect(rect)) {
                        showPanel1();
                    }
                    break;
                case 134:
                    L.e("134===================");
                    if (!top_ll.getGlobalVisibleRect(rect)) {
                        showPanel1();
                    }
                    break;
            }

        } else if (event.getAction() == KeyEvent.ACTION_UP) {

            switch (event.getKeyCode()) {
                case 131:
//                    L.e("131===================");
//
//                    if (!top_ll.getGlobalVisibleRect(rect)) {
//                        showPanel1();
//                    }
                    break;
                case 132:
//                    if (!top_ll.getGlobalVisibleRect(rect)) {
//                        showPanel1();
//                    }
                    break;
                case 133:
//                    L.e("133===================");
//                    if (!top_ll.getGlobalVisibleRect(rect)) {
//                        showPanel1();
//                    }
                    break;
                case 134:
                    L.e("134===================");
                    if (!top_ll.getGlobalVisibleRect(rect)) {
                        showPanel1();
                    }
                    break;

            }
        }

        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {
            //提示

            SPUtils.put(getApplicationContext(), "isChange", "true");
            L.e("onActivityResult=================" + data.getStringExtra("city_id"));
            MyApplication.city_id = data.getStringExtra("city_id");
            MyApplication.country_id = data.getStringExtra("country_id");

            //在线h51111111111111111111111111
            if (CommonUtils.isAvailable(mContext)) {
                L.e("onActivityResult 在线==========================" + MyApplication.city_id);
                getMenuData(mContext, "location", "1");

            } else {
                webView.loadUrl("file:///sdcard/offline/menu/index.html");
                MenuDataBean bean = new MenuDataBean();
                MenuDataBean.DataBean child = new MenuDataBean.DataBean();
                child.setCityId(MyApplication.city_id);
                child.setCountryId(MyApplication.country_id);
                child.setCityNameCn(data.getStringExtra("city_name"));
                child.setCountryNameCn(data.getStringExtra("country_name"));
                child.setAddrPicUrl("");
                bean.setCode("-10000");
                bean.setData(child);

                bindMenuData(bean);
            }

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e("Main Activity onStart");
    }

    /**
     * 扫码解锁后 清除标志位、清楚90天未联网日期、关闭lock按钮界面
     */
    private void unlock_ok_clear() {
        try {
            boolean RentLocked_fore = DetectUtil.isForeground(this, RentLockedActivity.class);
            LogUtil.e("RentLocked_fore = " + RentLocked_fore);
            if (RentLocked_fore) {
                RentLockedActivity.finsRentlock();
            }
            clearALlsharePreutil();

            init_neveruser();
            //TODO 清除服务器域名
            LogUtil.e("onRestart = RentLocked_fore");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.e("MainAcitivyt = onRestart");

    }

    public static LooptempHandler loopHandler;

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
                                loopHandler.sendEmptyMessageDelayed(130, 10000);
                            break;
                        case 300:
                            //接受到文颖的消息
                            if (msg != null) {
                                Bundle getdata = msg.getData();

                            }
                            break;
                        case 301:
                            set_BadgeCount += 1;
                            home_badge.setBadgeCount(set_BadgeCount);
                            try {
                                PushMessageBean bean = (PushMessageBean)msg.obj;
                                bean.setBadge(true);
                                SqlUtils.updateBadgeBean(bean);
                                LogUtil.e("++++++++++++new ");
                            }catch (Exception e){
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
                                HashMap<String, Serializable> add_hp = ( HashMap<String, Serializable>)msg.obj;
                                addStatisticsEvent("push_notification", add_hp);
                            }catch (Exception e){
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
                            LogUtil.e("hjs"+add_hp.get("key"));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

        L.e("MainActivity  onResume========================");
//        if(loopHandler!=null)loopHandler.sendEmptyMessageDelayed(100,4000);
    }

    @Override
    protected void onDestroy() {
        //注销  租赁逻辑里面的广播和资源
        renUtils.destroyRes();
        if (netBroadcastReceiver != null) {
            unregisterReceiver(netBroadcastReceiver);
        }
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //        L.e("rect==================="+top_ll.getGlobalVisibleRect(rect)+"========top_ll.getY()==========="+top_ll.getY()+"========top_ll.getY()111==========="+top_ll.getY()+"======(int)top_ll.getY()>0===="+((int)top_ll.getY()>0)+"=====(int)top_ll.getY()<700====="+((int)top_ll.getY()<700));
        int top_y = Math.abs((int) top_ll.getY());
        L.e("top_y===========================" + top_y);
        if (top_ll.getGlobalVisibleRect(rect) && top_y > 0 && top_y < 700) {
            L.e("onResume==========================");
            hidePanel1();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



    private void requestNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

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
                getInternetService();
                if (loopHandler != null) loopHandler.sendEmptyMessageDelayed(130, 10000);
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


    /**
     * 判断时区 进行服务器筛选
     */
    private void initService() {
        String timerID = TimeZone.getDefault().getID();
        if (timerID.equals("Asia/Shanghai")) {
            Constans.HOST_SERVER = Constans.HOST_SERVER_CH;
        } else {
            Constans.HOST_SERVER = Constans.HOST_SERVER_EN;
        }
        Log.e("SERVICE_FUWU", "时区：" + timerID + "----选择服务器:" + Constans.HOST_SERVER);
    }
    private int servers = 0;

    /**
     * 1.获取服务器域名
     * 2.存储
     * 3.开启定时轮询
     * 1.判断容错是否达到3次
     * 1.达到三次
     * 判断时区，切换域名   容错清零
     * 2.未达到三次
     * 判断时区，域名不做变动     超时容错清零
     */
    private void getInternetService() {
        Log.e("SERVICE_FUWU", "开始请求域名接口");
        if (!CommonUtils.isAvailable(this)) {
            Log.e("SERVICE_FUWU", "当前网络不可用");
            return;
        }
        Log.e("SERVICE_FUWU", "URL:" + Constans.GET_HOST_SERVER);
        Log.e("SERVICE_FUWU", "sn:" + CommonUtils.getSN());
        Log.e("SERVICE_FUWU", "no:" + CommonUtils.getRandom());
        Log.e("SERVICE_FUWU", "sl:" + CommonUtils.getLocal(mContext));

        OkGo.<String>get(Constans.GET_HOST_SERVER)
                .tag(this)
                .params("sn", CommonUtils.getSN())
                .params("no", CommonUtils.getRandom())
                .params("sl", CommonUtils.getLocal(mContext))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (!TextUtils.isEmpty(response.body().toString())) {
                            Log.e("SERVICE_FUWU", "onSuccess:" + response.body().toString());
                            saveService(response.body().toString());
                        } else {
                            //TODO 获取服务列表空
                            Log.e("SERVICE_FUWU", "onSuccess:数据空");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        switch (servers){
                            case 0:
                                servers = 1;
                                getInternetService();
                                break;
                            case 1:
                                ToastUtil.showShort(mContext, "服务器出错，请重启设备");
                                break;
                        }

                        Log.e("SERVICE_FUWU", "onError:" + response.getException().toString());
                    }
                });

    }




    private void saveService(String response) {
        try {
            PublicBean bean = FastJsonUtil.changeJsonToBean(response, PublicBean.class);
            //备份所有服务器数据，使用,分割
            ServerUtils.saveAllServer(bean.data.server);
//            ServerManager.getInstance().setPingServerError(ServerKeyUtils.serverKeyChatJoner);
            //TODO 任务
        } catch (Exception e) {
            ToastUtil.showShort(mContext, "解析服务器列表出错，请重启设备");
        }

    }

    /*
    * 是否连接
    * */
    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /*
    * 外网是否可以访问
    * */
    public boolean isNetworkOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("ping -c 1 47.93.177.251");
            int exitValue = ipProcess.waitFor();

            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
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


}
