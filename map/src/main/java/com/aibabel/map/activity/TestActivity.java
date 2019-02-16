package com.aibabel.map.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.map.R;
import com.aibabel.map.adapter.CardPagerAdapter;
import com.aibabel.map.bean.BusinessBean;
import com.aibabel.map.bean.CardItem;
import com.aibabel.map.views.AutoNextLineLinearlayout;
import com.aibabel.map.views.ShadowTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.anl_label)
    AutoNextLineLinearlayout anlLabel;
    private WebView wv_web;


    private CardPagerAdapter mCardAdapter;
    private String url = "http://img3.imgtn.bdimg.com/it/u=3263276969,1648655876&fm=26&gp=0.jpg";
    private ShadowTransformer mCardShadowTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
//        wv_web = findViewById(R.id.wv_web);
        init();
    }

    private void init() {
        WebView webView = findViewById(R.id.wv_web);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);//主要是这句
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient());//这行最好不要丢掉
        // 该方法解决的问题是打开浏览器不调用系统浏览器，直接用webview打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl("http://destination.cdn.aibabel.com/play/H5/punchClock.html?sn=987654321000049&city=东京&country=日本&title=西瓜卡");
    }

}
