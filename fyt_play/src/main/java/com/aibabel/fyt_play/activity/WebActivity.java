package com.aibabel.fyt_play.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.base.BaseApplication;
import com.aibabel.baselibrary.mode.DataManager;
import com.aibabel.fyt_play.R;
import com.aibabel.fyt_play.js.AdvancedWebView;
import com.aibabel.fyt_play.utils.CommonUtils;
import com.aibabel.fyt_play.utils.EmptyLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity {

    @BindView(R.id.wb)
    AdvancedWebView wb;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.el_error)
    EmptyLayout elError;

    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_web;
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        String country = intent.getStringExtra("country");
        String title = intent.getStringExtra("title");
        String url = intent.getStringExtra("url");
        String leaseNo = intent.getStringExtra("leaseNo");
        String sn = CommonUtils.getSN();
        elError.setErrorType(EmptyLayout.EMPTY_NORMAL_DISPLAY,"");
//        leaseNo = "14011105947001";

        // 设置WebView的客户端
//        wb.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return false;// 返回false
//            }
//        });

        WebSettings webSettings = wb.getSettings();
        webSettings.setDomStorageEnabled(true);//主要是这句
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);

        wb.addJavascriptInterface(this, "Android");
        wb.setWebChromeClient(new WebChromeClient());//这行最好不要丢掉
        // 该方法解决的问题是打开浏览器不调用系统浏览器，直接用webview打开
        wb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            // 断网或者网络连接超时
                elError.setErrorType(EmptyLayout.EMPTY_ERROR_DATAS,"加载失败");
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
//        leaseNo = "14011000739301";
//        url = " http://wx.aibabel.com:8005/wanle/html/punchClock.html";
//        wb.loadUrl("http://destination.cdn.aibabel.com/play/H5/punchClock.html?sn="+sn+"&city="+city+"&country="+country+"&title="+title+"&leaseNo"+leaseNo);

//        url = "http://wx.aibabel.com:8005/wanle/html/punchClock.html";
//        leaseNo = "14012505188301";
//         url=" http://192.168.50.224:8080/wanle/html/punchClock.html";
        leaseNo = DataManager.getInstance().getString("order_oid");
        wb.loadUrl(url+"?sn=" + sn + "&city=" + city + "&country=" + country + "&title=" + title + "&leaseNo=" + leaseNo);
//        wb.loadUrl("http://wx.aibabel.com:8005/meishi/html/punchClock.html?sn="+sn+"&city="+city+"&country="+country+"&title="+title+"&leaseNo"+leaseNo);
        Log.e("url", url+"?sn=" + sn + "&city=" + city + "&country=" + country + "&title=" + title + "&leaseNo=" + leaseNo);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        wb.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //如果想要屏蔽只需要返回ture 即可
                return true;
            }
        });


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
                String value=  object.get(key).toString();
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
     * JS调用android的方法
     *
     * @return
     */
    @JavascriptInterface //仍然必不可少
    public void close() {
//        Log.e("aas11","====");
        finish();
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


}
