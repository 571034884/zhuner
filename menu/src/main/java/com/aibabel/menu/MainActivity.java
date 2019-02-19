package com.aibabel.menu;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.ProviderUtils;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.menu.app.MyApplication;
import com.aibabel.menu.base.BaseActivity;
import com.aibabel.menu.bean.MenuDataBean;
import com.aibabel.menu.bitmap.MyTransformtion;
import com.aibabel.menu.broadcast.NetBroadcastReceiver;
import com.aibabel.menu.dialog.CustomDialog;
import com.aibabel.menu.h5.AndroidJS;
import com.aibabel.menu.inf.UpdateMenu;
import com.aibabel.menu.util.AppStatusUtils;
import com.aibabel.menu.util.CommonUtils;
import com.aibabel.menu.util.DBUtils;
import com.aibabel.menu.util.DoubleClickUtil;
import com.aibabel.menu.util.FileUtil;
import com.aibabel.menu.util.GlideCacheUtil;
import com.aibabel.menu.util.I18NUtils;
import com.aibabel.menu.util.L;
import com.aibabel.menu.util.LocationUtils;
import com.aibabel.menu.util.NetUtil;
import com.aibabel.menu.util.RenUtils;
import com.aibabel.menu.util.SPUtils;
import com.aibabel.menu.util.UrlConstants;
import com.aibabel.menu.view.MagicTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.mian_webview)
    WebView mianWebview;
    @BindView(R.id.main_return_img)
    ImageView mainReturnImg;
    @BindView(R.id.main_top_ctiy)
    MagicTextView mainTopCtiy;
    @BindView(R.id.main_topPanel)
    RelativeLayout mainTopPanel;
    @BindView(R.id.main_middle_time)
    TextClock mainMiddleTime;
    @BindView(R.id.main_middle_tianqi)
    TextView mainMiddleTianqi;
    @BindView(R.id.main_middle_tianqi_du)
    TextView mainMiddleTianqiDu;
    @BindView(R.id.main_middle_huilv)
    TextView mainMiddleHuilv;
    //    @BindView(R.id.main_middle_huilv1)
//    SingleLineZoomTextView mainMiddleHuilv1;
    @BindView(R.id.main_middle_ll)
    LinearLayout mainMiddleLl;
    @BindView(R.id.main_map_local_tv)
    TextView mainMapLocalTv;
    @BindView(R.id.main_map_jinru_img)
    ImageView mainMapJinruImg;
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
    @BindView(R.id.main_shanghua_img)
    ImageView mainShanghuaImg;
    @BindView(R.id.main_middle_tianqi_du1)
    TextView mainMiddleTianqiDu1;


    @BindView(R.id.main_middle_huilv_top1)
    TextView mainMiddleHuilvTop1;
    @BindView(R.id.main_middle_huilv_top2)
    TextView mainMiddleHuilvTop2;
    @BindView(R.id.main_middle_date)
    TextClock mainMiddleDate;
    @BindView(R.id.main_top_ctiy_ll)
    LinearLayout mainTopCtiyLl;
    @BindView(R.id.main_top_ctiy_img)
    ImageView mainTopCtiyImg;
    @BindView(R.id.main_return_img_ll)
    LinearLayout mainReturnImgLl;


    private LinearLayout bottom_menu_gd;
    private LinearLayout bottom_ll;
    private RelativeLayout top_ll;
    private WebView webView;
    WebSettings webSettings = null;
    int num;
    Rect rect;
    //上滑动画
    public static AnimationDrawable frameAnim;
    public static UpdateMenu upListener; //更新界面接口
    CustomDialog.Builder builderChangeCity;//切换城市dialog
    CustomDialog.Builder builderNoCity;//切换城市dialog

    NetBroadcastReceiver netBroadcastReceiver;

    //泽峰  租赁逻辑类
    RenUtils renUtils;

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.e("MainActivity  onCreate========================");

//        DBUtils.copyAssetsToSd(mContext,"index.html");

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
                mainMapLocalTv.setText(addr);

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

                ToastUtil.show(mContext, err, 500);
            }

            @Override
            public void noCity() {
                L.e("没有城市内容==============================================");
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

        L.e("path=====================" + mContext.getFilesDir().getAbsolutePath());


    }

    @Override
    public int getLayout(Bundle bundle) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        return R.layout.activity_main;
    }

    @Override
    public void init() {


    }


    @Override
    protected void assignView() {
        top_ll = findViewById(R.id.main_topPanel);
        bottom_ll = findViewById(R.id.main_bottomPanel);
        bottom_menu_gd = findViewById(R.id.bttom_menu_ll_gd);
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


    }

/*    @Override
    protected int getLayoutId() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        getWindow().setExitTransition(new Slide().setDuration(400));
//        getWindow().setReenterTransition(new Slide().setDuration(400));
        return R.layout.activity_main;
    }*/

    @Override
    protected void initData() {

        netBroadcastReceiver = new NetBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(netBroadcastReceiver, intentFilter);
        //上滑引导的帧动画
        frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.amin_list_shanghua);
        mainShanghuaImg.setBackgroundDrawable(frameAnim);
        frameAnim.start();

        webviewSetting();


        getMenuData(mContext, "location", "2");

        renUtils = new RenUtils(this);
        renUtils.startZl(); //启动租赁
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
//                startActivity(intent,ActivityOptions.makeCustomAnimation(mContext,R.transition.tran_menu_in,R.transition.tran_menu_out).toBundle());
                    StatisticsManager.getInstance(mContext).addEventAidl( "点击菜单首页更多", new HashMap<>());
                    break;
                case R.id.main_middle_time_ll:
                    //调起时钟
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, "com.aibabel.alliedclock"));
                    StatisticsManager.getInstance(mContext).addEventAidl( "点击菜单首页时间", new HashMap<>());

                    break;
                case R.id.main_middle_tianqi_ll:
                    //调起天气
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, "com.aibabel.weather"));
                    StatisticsManager.getInstance(mContext).addEventAidl( "点击菜单首页天气", new HashMap<>());

                    break;
                case R.id.main_middle_huilv_ll:
                    //调起汇率
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, "com.aibabel.currencyconversion"));
                    StatisticsManager.getInstance(mContext).addEventAidl( "点击菜单首页汇率", new HashMap<>());

                    break;
                case R.id.main_map_rl:
                    //调起地图
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, "com.aibabel.map"));
                    StatisticsManager.getInstance(mContext).addEventAidl( "点击菜单首页地图", new HashMap<>());


                    break;
                case R.id.bttom_menu_ll_yyfy:
                    //调起语音翻译
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, "com.aibabel.translate"));
                    StatisticsManager.getInstance(mContext).addEventAidl( "点击菜单首页语音翻译", new HashMap<>());

//                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, "com.aibabel.translate").putExtra("from","food"));

//                    startActivity(new Intent().setPackage("com.aibabel.translate").putExtra("from", "food"));


                    break;
                case R.id.bttom_menu_ll_pzfy:
                    //调起拍照翻译
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, "com.aibabel.ocr"));
                    StatisticsManager.getInstance(mContext).addEventAidl( "点击菜单首页拍照翻译", new HashMap<>());

                    break;
                case R.id.bttom_menu_ll_ddwl:
                    //调起当地玩乐
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, "com.aibabel.fyt_play"));
                    StatisticsManager.getInstance(mContext).addEventAidl( "点击菜单首页当地玩乐", new HashMap<>());

                    break;
                case R.id.main_top_ctiy_ll:

//                    Intent intent1 = new Intent();
////                    intent1.setAction("com.aibabel.menu.MENULOCATION");
////                    sendBroadcast(intent1);
                    //调起搜索页面
                    ((MainActivity) mContext).startActivityForResult(new Intent(mContext, SearchActivity.class), 100);
                    L.e("1111111111111111111111111111111");
                    StatisticsManager.getInstance(mContext).addEventAidl( "点击菜单首页进入选择目的地", new HashMap<>());


                    break;

                case R.id.main_return_img_ll:
                    L.e("fanhuidonghua===============================");
//     top_ll.getGlobalVisibleRect(new Rect());
                    showPanel();
                    StatisticsManager.getInstance(mContext).addEventAidl("点击h5关闭面板h5不可见", new HashMap());
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
                // TODO Auto-generated method stub
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        frameAnim.stop();
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

                        frameAnim.start();

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
        StatisticsManager.getInstance(mContext).addEventAidl( "滑动进入目的地", new HashMap<>());


    }

    /**
     * 上下面板打开动画
     */
    public void hidePanel1() {
        ObjectAnimator.ofFloat(top_ll, "translationY", -700).setDuration(10).start();
        ObjectAnimator.ofFloat(top_ll, "alpha", 1f, 0.5f).setDuration(10).start();
        ObjectAnimator.ofFloat(bottom_ll, "translationY", 500).setDuration(10).start();
        ObjectAnimator.ofFloat(bottom_ll, "alpha", 1f, 0.4f).setDuration(10).start();
        StatisticsManager.getInstance(mContext).addEventAidl( "滑动不过度动画进入目的地", new HashMap<>());

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


        webView.addJavascriptInterface(new AndroidJS(mContext), "menuApp");
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
                view.loadUrl("file:///sdcard/offline/menu/moren_error.html");
                L.e("1111111111111111111" + errorCode + "--------" + description);
                isError = true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (request.isForMainFrame()) { // 或者： if(request.getUrl().toString() .equals(getUrl()))
                    // 在这里显示自定义错误页
                    view.loadUrl("file:///sdcard/offline/menu/moren_error.html");
                }
                L.e("1111111111111111111" + error + "--------");
                isError = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (isError) {
                    view.loadUrl("file:///sdcard/offline/menu/moren_error.html");
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
        MyApplication.city_id = bean.getData().getCityId();
        MyApplication.country_id = bean.getData().getCountryId();
        if (!TextUtils.equals(oldCity, bean.getData().getCityNameCn())) {
            if (TextUtils.equals("-10000", bean.getCode())) {
                StatisticsManager.getInstance(mContext).addEventAidl( "切换目的地", new HashMap() {{
                    put("type", "离线");
                    put("city", bean.getData().getCityNameCn());

                }});
            } else {
                StatisticsManager.getInstance(mContext).addEventAidl( "切换目的地", new HashMap() {{
                    put("type", "在线");
                    put("city", bean.getData().getCityNameCn());

                }});
            }
        }

        try {
//            mainTopCtiy.setTextColor(TextUtils.equals(bean.getData().getAddrPicColor(), "light") ? getResources().getColor(R.color.clr_333) : getResources().getColor(R.color.clr_fff));
//            mainTopCtiy.setTextColor(getResources().getColor(R.color.clr_fff));

            if (TextUtils.equals(bean.getData().getAddrPicColor(), "light")) {
                frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.amin_list_shanghua_an);
                mainShanghuaImg.setBackgroundDrawable(frameAnim);
                frameAnim.start();
            } else {
                frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.amin_list_shanghua);
                mainShanghuaImg.setBackgroundDrawable(frameAnim);
                frameAnim.start();
            }
        } catch (Exception e) {

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
            mainMiddleHuilvTop1.setText(!TextUtils.equals(bean.getCode(), "-10000") ? bean.getData().getCurrencyData().substring(0, bean.getData().getCurrencyData().indexOf(".")) : "--·--");
            mainMiddleHuilvTop2.setText(!TextUtils.equals(bean.getCode(), "-10000") ? bean.getData().getCurrencyData().substring(bean.getData().getCurrencyData().indexOf(".")) : "");

        } catch (Exception e) {
            mainMiddleHuilvTop1.setText("--·--");
            mainMiddleHuilvTop2.setText("");


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

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);


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
        int top_y=Math.abs((int)top_ll.getY()) ;
        L.e("top_y==========================="+top_y);
        if (top_ll.getGlobalVisibleRect(rect)&&top_y>0&&top_y<700) {
            L.e("onResume==========================");
            hidePanel1();

        }
    }

    @Override
    protected void onPause() {


        super.onPause();
    }
}
