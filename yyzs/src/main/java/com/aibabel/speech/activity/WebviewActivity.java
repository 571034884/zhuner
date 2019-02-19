package com.aibabel.speech.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import android.widget.TextView;

import com.aibabel.speech.R;
import com.aibabel.speech.util.L;
import com.aibabel.statisticalserver.SimpleStatisticsActivity;

public class WebviewActivity extends SimpleStatisticsActivity {

    private WebView webView;
    private ImageView img_return;
    private TextView tvLading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_webview);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        String url=getIntent().getStringExtra("url");
        L.e(url+"");


        webView=findViewById(R.id.item_webview_wb);
        tvLading=findViewById(R.id.item_webview_loading);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {

                Log.e("-------", "onPageFinished: "+url );
                tvLading.setVisibility(View.GONE);
                img_return.setVisibility(View.VISIBLE);

            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }


        });
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSavePassword(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        // 自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        img_return=findViewById(R.id.item_webview_return);
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (webView!=null) {
            webView.removeAllViews();
            webView=null;

        }
        super.onDestroy();
    }
}
