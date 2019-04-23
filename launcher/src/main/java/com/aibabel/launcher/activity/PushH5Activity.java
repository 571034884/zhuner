package com.aibabel.launcher.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.launcher.base.LaunBaseActivity;
import com.aibabel.launcher.utils.Logs;
import com.aibabel.menu.R;
import com.baidu.location.BDLocation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;

/**
 * 打卡页面 H5
 *
 * Created by fytworks on 2018/12/19.
 */

public class PushH5Activity extends LaunBaseActivity{

    @BindView(R.id.bar_closes)
    ImageView barClose;
    @BindView(R.id.wv_web)
    WebView webView;
    @BindView(R.id.ll_error)
    LinearLayout llError;

    String locationWhere = "";
    BDLocation mLocation;
    private String url = "";

    @Override
    public int getLayout(Bundle savedInstanceState) {
        return R.layout.activity_h5;
    }

    @Override
    protected void initView() {
        url = getIntent().getStringExtra("url");
        llError.setVisibility(View.GONE);
        initOptionWeb();
    }

    @SuppressLint("JavascriptInterface")
    private void initOptionWeb() {
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        // 启用javascript
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");

        String ua = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(ua + "aibabel_map");
        ua = webView.getSettings().getUserAgentString();
        Log.i("uasssssss",ua);
        // 添加js交互接口类，并起别名 imagelistner
        webView.addJavascriptInterface(this, "android");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                webView.setVisibility(View.GONE);
                llError.setVisibility(View.VISIBLE);
            }

        });
        webView.loadUrl(url);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bar_closes:
                finish();
                break;
            case R.id.ll_error:
                if (CommonUtils.isNetworkAvailable(this)){
                    webView.setVisibility(View.VISIBLE);
                    llError.setVisibility(View.GONE);
                    webView.loadUrl(url);
                }else{
                    ToastUtil.showShort(mContext,"请检查网络连接");
                }
                break;
        }


    }

    @JavascriptInterface
    public void addJsEvent(String eventId, String parameters) throws JSONException {
        HashMap<String, Serializable> parametersMap=null;

        if (!TextUtils.isEmpty(parameters)){
            JSONObject object=new JSONObject(parameters);
            parametersMap=new HashMap<>();
            Iterator<String> keys=object.keys();
            while (keys.hasNext()){
                String key=keys.next();
                String value= (String) object.get(key);
                parametersMap.put(key,value);
            }
        }

        addStatisticsEvent(eventId, parametersMap);
    }

    @JavascriptInterface
    @Override
    public void addPageParameters(String key, Serializable value) {
        super.addPageParameters(key, value);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 133:
                Logs.e("走了onKeyDown");
                closeAct();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 接收数据
     * @param name
     */
    @JavascriptInterface
    public void aiBabelMap(String name){
        Log.i("aiBabelMap",name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearCacheWebView();
    }
    private void clearCacheWebView() {
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookies(null);
            cookieManager.removeAllCookie();
            cookieManager.flush();
        } else {
            cookieManager.removeSessionCookies(null);
            cookieManager.removeAllCookie();
            CookieSyncManager.getInstance().sync();
        }
        WebStorage.getInstance().deleteAllData();
    }


}
