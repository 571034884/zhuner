package com.aibabel.traveladvisory.activity;

import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.aibabel.traveladvisory.R;
import com.aibabel.traveladvisory.app.BaseActivity;
import com.aibabel.traveladvisory.app.Constans;
import com.aibabel.traveladvisory.app.MyApplication;
import com.aibabel.traveladvisory.bean.GaiLanBean;
import com.aibabel.traveladvisory.bean.GuojiagailanBean;
import com.aibabel.traveladvisory.bean.HotCityBean;
import com.aibabel.traveladvisory.custom.ProgressWebView;
import com.aibabel.traveladvisory.okgo.BaseBean;
import com.aibabel.traveladvisory.okgo.BaseCallback;
import com.aibabel.traveladvisory.okgo.OkGoUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class GailanActivity extends BaseActivity implements BaseCallback {

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.iv_fanhui)
    ImageView ivFanhui;

    @Override
    public int getLayout(Bundle savedInstanceState){
        return R.layout.activity_gailan;
    }

    private WebSettings webSettings;

    @Override
    public void init() {
        String id = getIntent().getStringExtra("placeId");
        webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBuiltInZoomControls(true);//support zoom
//        webSettings.setPluginsEnabled(true);//support flash
        //加载需要显示的网页
        Log.e("onViewClicked: ", getIntent().getStringExtra("placeId"));
//        webView.loadUrl("http://m.youpu.cn/index/impressInfo?placeId=" + id);//加载url

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });


        Map<String, String> map = new HashMap<>();
        map.put("Id", id);
        OkGoUtil.<GaiLanBean>getInHtml(GailanActivity.this, Constans.METHOD_GET_GJGL, map, GaiLanBean.class, this);
    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e( "onKeyDown: " ,"aaaaaaa");
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.e( "onKeyUp: " ,"aaaaaaa");
        return super.onKeyUp(keyCode, event);
    }
    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode){
//            case 133:
//                MyApplication.exit();
//                break;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    @OnClick(R.id.iv_fanhui)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onSuccess(String method, BaseBean model) {
        switch (method) {
            case Constans.METHOD_GET_GJGL:
                GaiLanBean gaiLanBean = (GaiLanBean) model;
                if (gaiLanBean != null && !gaiLanBean.getData().equals(""))
                    webView.loadData(gaiLanBean.getData(), "text/html;charset=utf-8", null);
//                    webView.loadDataWithBaseURL(null, gaiLanBean.getData(), "text/html", "utf-8", null);
                break;
        }
    }

    @Override
    public void onError(String method, String message) {

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction()==KeyEvent.ACTION_DOWN){
            switch (event.getKeyCode()){
                case 133:
                    MyApplication.exit();
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }

}
