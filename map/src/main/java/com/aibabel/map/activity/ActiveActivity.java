package com.aibabel.map.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.base.StatisticsBaseActivity;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.map.R;
import com.aibabel.map.base.MapBaseActivity;
import com.aibabel.map.utils.CommonUtils;
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

public class ActiveActivity extends MapBaseActivity{

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
    public int getLayoutMap(Bundle bundle) {
        return R.layout.activity_activ;
    }

    @Override
    public void initMap() {
        initDataBean();
        initOptionWeb();
    }

    @Override
    public void receiveLocation(BDLocation location) {
        mLocation = location;
    }

    private void initDataBean() {
        locationWhere = getIntent().getStringExtra("locationWhere");
        String sn = CommonUtils.getSN();
        url = "http://destination.cdn.aibabel.com/clock/dongjing-1.html?sn="+sn+"&leaseId="+CommonUtils.getOrderID(mContext)+"&locationWhere="+locationWhere;
//        url = "http://192.168.50.224:8080/dongjing-1.html?sn=999900000000009&leaseld=123456";
        llError.setVisibility(View.GONE);
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
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("path_plan_route5", null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
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

//    @Override
//    @JavascriptInterface
//    public void addStatisticsEvent(String eventId, HashMap<String, Serializable> parameters) {
//        super.addStatisticsEvent(eventId, parameters);
//    }

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



    /**
     * 接收数据
     * @param name
     */
    @JavascriptInterface
    public void aiBabelMap(String name){
        Log.i("aiBabelMap",name);
    }

    /**
     * 让JS调用Android方法获取数据
     * @return
     */
    @JavascriptInterface
    public String getLatLon(){
        if (mLocation == null){
            ToastUtil.showShort(mContext,"获取经纬度失败");
            return "0,0";
        }
        return mLocation.getLatitude()+","+mLocation.getLongitude();
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
