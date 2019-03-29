package com.aibabel.fyt_play.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.base.BaseApplication;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.fyt_play.R;
import com.aibabel.fyt_play.bean.Constans;
import com.aibabel.fyt_play.bean.H5Bean;
import com.aibabel.fyt_play.bean.OrderBean;
import com.aibabel.fyt_play.js.AdvancedWebView;
import com.aibabel.fyt_play.js.JsClickInfo;
import com.aibabel.fyt_play.js.OnJSClickListener;
import com.aibabel.fyt_play.utils.CommonUtils;
import com.aibabel.fyt_play.utils.EmptyLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWebActivity extends BaseActivity implements OnJSClickListener , BaseCallback<BaseBean> {

    @BindView(R.id.wb)
    AdvancedWebView wb;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.el_error)
    EmptyLayout elError;

    private final Uri CONTENT_URI = Uri.parse("content://com.dommy.qrcode/aibabel_information");
    String dev_oid, dev_uid, dev_uname, dev_sn;
    private String dev_d;
    private String leaseNo;
    private String url;
    private String orderDetials;
    private String sn;

    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_my_web;
    }

    @Override
    public void init() {
        Intent intent1 = getIntent();
        String from = intent1.getStringExtra("from");
        if (TextUtils.equals(from,"menu")&&from!=null){
            initDictionary();
            leaseNo = dev_oid;

            Map<String, String> map1 = new HashMap<>();
//            dev_oid = "14012505188301";
            map1.put("leaseId", dev_oid);
            map1.put("page", "1");
            map1.put("pageSize", "1");
            OkGoUtil.<OrderBean>get(MyWebActivity.this, Constans.METHOD_GETPLAYORDERMSG, map1, OrderBean.class, this);

            Map<String, String> map2 = new HashMap<>();
            OkGoUtil.<H5Bean>get(this, Constans.METHOD_GETPLAYH5FORMAT, map2, H5Bean.class, this);
        }else {
            Intent intent = getIntent();
            leaseNo = intent.getStringExtra("leaseNo");
            url = intent.getStringExtra("url");
        }

        sn = CommonUtils.getSN();
        elError.setErrorType(EmptyLayout.EMPTY_NORMAL_DISPLAY, "");
        WebSettings webSettings = wb.getSettings();
        webSettings.setDomStorageEnabled(true);//主要是这句
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.supportMultipleWindows();
        webSettings.setAllowContentAccess(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);

        wb.setWebChromeClient(new WebChromeClient());//这行最好不要丢掉

        webSettings.setBlockNetworkImage(false);//解决图片不显示
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);

        wb.addJavascriptInterface(this, "Android");

        wb.setWebChromeClient(new WebChromeClient());//这行最好不要丢掉
        // 该方法解决的问题是打开浏览器不调用系统浏览器，直接用webview打开
        wb.setWebViewClient(new WebViewClient() {
            //            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return true;
//            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // 断网或者网络连接超时
                ivClose.setVisibility(View.VISIBLE);
                elError.setErrorType(EmptyLayout.EMPTY_ERROR_DATAS, "加载失败");
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

        wb.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                return true;
            }
        });
        wb.setJSClickListener(this);
//        leaseNo = "14011105947001";
//        leaseNo = "14011000739301";

//        url = "http://wx.aibabel.com:8005/wanle/html/orderDetails.html";
//        leaseNo = "14012505188301";
        if (url!=null&&!TextUtils.equals(url,"")){
            wb.loadUrl(url+"?sn=" + sn + "&leaseId=" + leaseNo);
        }
//        wb.loadUrl("http://wx.aibabel.com:8005/wanle/html/punchClock.html?sn=987654321000060&city=东京&country=日本&title=西瓜卡111&leaseNo=14122004882901" );
        Log.e("url11", url+"?sn=" + sn + "&leaseId=" + leaseNo);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * JS调用android的方法
     *
     * @return
     */
    @JavascriptInterface //仍然必不可少
    public void close() {

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        Log.e("aas","====");
        finish();
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

    @Override
    public void onJSClick(JsClickInfo jsc) {
        }

    private void initDictionary() {
        try {
            Cursor cursor = MyWebActivity.this.getContentResolver().query(CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    dev_oid = cursor.getString(cursor.getColumnIndex("oid"));
                    Log.e("dev_oid", dev_oid);
                    dev_uid = cursor.getString(cursor.getColumnIndex("uid"));
                    dev_uname = cursor.getString(cursor.getColumnIndex("uname"));
                    dev_sn = cursor.getString(cursor.getColumnIndex("sn"));
                    dev_d = cursor.getString(cursor.getColumnIndex("d"));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("wzf", "tianxia=" + e.getMessage());
        }
    }

    @Override
    public void onSuccess(String method, BaseBean baseBean, String s1) {
        switch (method){
            case Constans.METHOD_GETPLAYORDERMSG:
                OrderBean orderBean = (OrderBean) baseBean;
                List<OrderBean.DataBean> data = orderBean.getData();
                if (data.size() == 0) {
                    wb.setVisibility(View.GONE);
                    elError.setErrorType(EmptyLayout.EMPTY_ERROR_DATAS, "暂无订单");
                    ivClose.setVisibility(View.VISIBLE);
                } else {
                    ivClose.setVisibility(View.GONE);
                    wb.setVisibility(View.VISIBLE);
                }
                break;

            case Constans.METHOD_GETPLAYH5FORMAT:
                H5Bean h5Bean = (H5Bean) baseBean;
                H5Bean.DataBean h5BeanData = h5Bean.getData();
                url = h5BeanData.getOrderDetials();
//                url = "http://wx.aibabel.com:8005/wanle/html/orderDetails.html";
//                leaseNo = "14012505188301";
                if (url!=null&&!TextUtils.equals(url,"")){
                    wb.loadUrl(url+"?sn=" + sn + "&leaseId=" + leaseNo);
                }
                break;
        }
    }

    @Override
    public void onError(String s, String s1, String s2) {

    }

    @Override
    public void onFinsh(String s) {

    }
}
