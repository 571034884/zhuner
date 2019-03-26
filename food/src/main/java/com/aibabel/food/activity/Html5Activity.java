package com.aibabel.food.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.base.BaseApplication;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.baselibrary.utils.ProviderUtils;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.food.R;
import com.aibabel.food.base.Constant;
import com.aibabel.food.bean.Html5UrlBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class Html5Activity extends BaseActivity implements BaseCallback<Html5UrlBean> {

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.iTurn)
    View iTurn;
    @BindView(R.id.tvTurnTranslate)
    TextView tvTurnTranslate;
    @BindView(R.id.tvTurnCamera)
    TextView tvTurnCamera;
    @BindView(R.id.tvTurnHuilv)
    TextView tvTurnHuilv;
    private String where;
    private String shopId;
    private String bannerId;
    private String shopName;
    private String bannerName;
    private View mErrorView;

    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_html5;
    }

    public void getIntentDate() {
        where = getIntent().getStringExtra("where");
        shopId = getIntent().getStringExtra("shopId");
        bannerId = getIntent().getStringExtra("bannerId");
        shopName = getIntent().getStringExtra("shopName");
        bannerName = getIntent().getStringExtra("bannerName");

        if (where.equals("filter") || where.equals("search"))
            iTurn.setVisibility(View.VISIBLE);
        else iTurn.setVisibility(View.GONE);
    }

    @Override
    public void init() {
        getIntentDate();
        initErrorPage();
        if (where.equals("filter") || where.equals("search"))
            setPathParams("p1" + shopId);//加载url
        else
            setPathParams("p1" + bannerName);//加载url

        webView.addJavascriptInterface(this, "android");//添加js监听 这样html就能调用客户端
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js

        /**
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.

        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

        //不显示webview缩放按钮
//        webSettings.setDisplayZoomControls(false);

        getHtmlData();
    }


    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            //6.0以下执行
            Log.i(TAG, "onReceivedError: ------->errorCode" + errorCode + ":" + description);
            //网络未连接
            showErrorPage();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.i(TAG, "onReceivedError: ");
            showErrorPage();//显示错误页面
        }


        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("ansen", "拦截url:" + url);
            if (url.equals("http://www.google.com/")) {
                Toast.makeText(Html5Activity.this, "国内不能访问google,拦截该url", Toast.LENGTH_LONG).show();
                return true;//表示我已经处理过了
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

    };

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient = new WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("确定", null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.i("ansen", "网页标题:" + title);
            if (title.contains("404")) {
                showErrorPage();
            }
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("ansen", "是否有上一个页面:" + webView.canGoBack());
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {//点击返回按钮的时候判断有没有上一页
            webView.goBack(); // goBack()表示返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * JS调用android的方法
     *
     * @return
     */
    @JavascriptInterface //仍然必不可少
    public void close() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (webView.canGoBack()) {
                    webView.goBack();//返回上个页面
                } else {
                    finish();
                }
            }
        });
    }

    @JavascriptInterface //仍然必不可少
    public void turnToDaohang(String endName, String endLoc) {
        ToastUtil.showShort(this, "跳转导航");
        Intent intent = getAppOpenIntentByPackageName(mContext, "com.aibabel.map");
        intent.setClassName("com.aibabel.map", "com.aibabel.map.activity.RouteLineActivity");
        intent.putExtra("from", "food");
        intent.putExtra("endName", endName);
        intent.putExtra("endLoc", endLoc);
        startActivity(intent);
        finish();
    }

    @JavascriptInterface //仍然必不可少
    public void statisticsOpenCount(String foodName) {
        Map<String, String> map = new HashMap<>();
        map.put("p1", foodName);
        StatisticsManager.getInstance(this).addEventAidl(1015, map);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //释放资源
        if (webView != null) webView.destroy();
        webView = null;
    }

    public void getHtmlData() {
        Map<String, String> map = new HashMap<>();
        OkGoUtil.<Html5UrlBean>get(Constant.METHOD_HTML_URL, map, Html5UrlBean.class, this);
    }

    @Override
    public void onSuccess(String s, Html5UrlBean html5UrlBean, String s1) {
        if (where.equals("filter") || where.equals("search"))
            webView.loadUrl(html5UrlBean.getData().getPageCateDetail() + "?sn=" + CommonUtils.getSN() + "&shopId=" + shopId + "&countryName=" + ProviderUtils.getInfo(ProviderUtils.COLUMN_COUNTRY));//加载url
        else
            webView.loadUrl(html5UrlBean.getData().getPagefoodSoft() + "?sn=" + CommonUtils.getSN() + "&banner_id=" + bannerId + "&countryName=" + ProviderUtils.getInfo(ProviderUtils.COLUMN_COUNTRY));//加载url

    }

    @Override
    public void onError(String s, String s1, String s2) {
        showErrorPage();
    }

    @Override
    public void onFinsh(String s) {

    }

//    语音翻译按钮	点击	food_html5		food_html5_def:味道不错
//    菜单翻译按钮	点击	food_html6		food_html6_def:菜单翻译
//    账单换算按钮	点击	food_html7		food_html7_def:账单翻译


    @OnClick({R.id.tvTurnTranslate, R.id.tvTurnCamera, R.id.tvTurnHuilv})
    public void onViewClicked(View view) {
        Intent intent = null;
        Map<String, String> map = new HashMap<>();
        map.put("p1", shopId);
        switch (view.getId()) {
            case R.id.tvTurnTranslate:
                StatisticsManager.getInstance(this).addEventAidl(1011, map);
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("food_html5_def", shopId);
                    addStatisticsEvent("food_html5", add_hp);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/


                intent = getAppOpenIntentByPackageName(mContext, "com.aibabel.translate");
                intent.setClassName("com.aibabel.translate", "com.aibabel.translate.MainActivity");
//                intent.setAction(Intent.ACTION_VIEW);
                break;
            case R.id.tvTurnCamera:
                StatisticsManager.getInstance(this).addEventAidl(1012, map);
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("food_html6_def", shopId);
                    addStatisticsEvent("food_html6", add_hp);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/

                intent = getAppOpenIntentByPackageName(mContext, "com.aibabel.ocr");
                intent.setClassName("com.aibabel.ocr", "com.aibabel.ocr.activity.LauncherActivity");
                break;
            case R.id.tvTurnHuilv:
                StatisticsManager.getInstance(this).addEventAidl(1013, map);
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("food_html7_def", shopId);
                    addStatisticsEvent("food_html7", add_hp);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                intent = getAppOpenIntentByPackageName(mContext, "com.aibabel.currencyconversion");
                intent.setClassName("com.aibabel.currencyconversion", "com.aibabel" +
                        ".currencyconversion.MainActivity");
                break;
        }
        if (intent != null) {
            intent.putExtra("from", "food");
            startActivity(intent);
        }
    }

    /**
     * 调起应用  没有启动直接启动   启动过直接调起
     *
     * @param context
     * @param packageName
     * @return
     */
    public static Intent getAppOpenIntentByPackageName(Context context, String packageName) {
        String mainAct = null;
        PackageManager pkgMag = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);

        @SuppressLint("WrongConstant") List<ResolveInfo> list = pkgMag.queryIntentActivities(intent,
                PackageManager.GET_ACTIVITIES);
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo info = list.get(i);
            if (info.activityInfo.packageName.equals(packageName)) {
                mainAct = info.activityInfo.name;
                break;
            }
        }
        if (TextUtils.isEmpty(mainAct)) {
            return null;
        }
        intent.setComponent(new ComponentName(packageName, mainAct));
        return intent;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case 133:
                    BaseApplication.exit();
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 显示自定义错误提示页面，用一个View覆盖在WebView
     */
    private void showErrorPage() {
        if (webView != null) {
            webView.removeAllViews(); //移除加载网页错误时，默认的提示信息
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout
                    .LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            webView.addView(mErrorView, 0, layoutParams); //添加自定义的错误提示的View
        }
    }

    /***
     * 显示加载失败时自定义的网页
     */
    private void initErrorPage() {
        if (mErrorView == null) {
            mErrorView = View.inflate(this, R.layout.layout_webview_error, null);
            mErrorView.findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }
}
