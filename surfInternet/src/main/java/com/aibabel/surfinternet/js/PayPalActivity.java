package com.aibabel.surfinternet.js;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.surfinternet.MainActivity;
import com.aibabel.surfinternet.R;
import com.aibabel.surfinternet.activity.BaseActivity;
import com.aibabel.surfinternet.utils.NetUtil;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayPalActivity extends BaseActivity implements OnJSClickListener {


    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_tishi)
    ImageView ivTishi;
    @BindView(R.id.iv_tishi1)
    ImageView ivTishi1;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.ll_isnet)
    LinearLayout llIsnet;
    @BindView(R.id.rl_web)
    RelativeLayout rlWeb;
    private AdvancedWebView webView;
    private String time = "0";
    Uri uri = Uri.parse("content://icc/adn");
    private String skuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);
        ButterKnife.bind(this);
        if (NetUtil.isNetworkAvailable(PayPalActivity.this)) {
            initView();
            initData();
        } else {
            rl.setVisibility(View.GONE);
            llIsnet.setVisibility(View.VISIBLE);
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        webView = findViewById(R.id.wbv_paypal);
        // 允许 使用js

        webView.getSettings().setJavaScriptEnabled(true);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;

            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                return true;
            }
        });
        webView.setJSClickListener(this);


    }

    public void initData() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Log.e("url_pay", url);
        String payType = intent.getStringExtra("payType");
        Log.e("payType_pay", payType);
        time = intent.getStringExtra("time");
        String subOrderNo = intent.getStringExtra("subOrderNo");
        Log.e("subOrderNo_pay", subOrderNo);
        skuid = intent.getStringExtra("SKUID");

        Map map1 = new HashMap();
        map1.put("p1", skuid);
        StatisticsManager.getInstance(PayPalActivity.this).addEventAidl(1730, map1);

        webView.loadUrl(url);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, final int progress) {
                Log.e("progress", progress + "----");
            }
        });
    }

    public void clearCache() {
        try {
            this.deleteDatabase("webview.db");
            this.deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
        }
        return false;
    }

    private void back() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 下单成功
     */
    private void requestData() throws ParseException {
        Log.e("下单", "下单成功");
        //续租代码
        /*if (TextUtils.equals(version,"L")){
            String time_end = SharePrefUtil.getString(CustomWebViewActivity.this, "time_end", "0");
            // 你给我的时间
            Log.e("time_end",time_end);
            long time_end1 = stringToLong(time_end, "yyyyMMddHHmmss");
            //这是 转化为long 类型
            Log.e("time_end1",time_end1+"");
            Integer time1 = Integer.valueOf(time);
            //这是 我要延期的时间 int
            Log.e("time_end2",time1+"");
            String dateDelay = getDateDelay(time_end1, time1);
            Log.e("dateDelay", "=====" + dateDelay);
            Intent intent = new Intent();
            intent.setAction("com.android.zhuner.time");
            intent.putExtra("Zhuner_Time", dateDelay);
            Log.e("time", "====" + dateDelay);
            CustomWebViewActivity.this.sendBroadcast(intent);
            SharePrefUtil.saveString(CustomWebViewActivity.this, "time_end", dateDelay);
        }*/
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                tvTishi.setVisibility(View.VISIBLE);
//                tvTishi.setImageResource(R.mipmap.succes);
//                tvTishi.setScaleType(ImageView.ScaleType.FIT_XY);
                ivTishi.setImageResource(R.mipmap.success1);
                ivTishi1.setVisibility(View.VISIBLE);
                Glide.with(PayPalActivity.this)
                        .load(R.mipmap.successs2).into(ivTishi1);
                rl.setVisibility(View.GONE);
                tvError.setText(getResources().getString(R.string.success));
                llIsnet.setVisibility(View.VISIBLE);
//                insertContact("softsim","00001");

                Map map1 = new HashMap();
                map1.put("p2", skuid);
                StatisticsManager.getInstance(PayPalActivity.this).addEventAidl(1732, map1);
                //TODO 下单成功  判断标识 是否重启设备
            }
        });


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(PayPalActivity.this, MainActivity.class));
            }
        }, 3000);
    }
    public void insertContact(String name, String phoneNumber) {
        ContentValues values = new ContentValues();
        values.put("tag", name);
        values.put("number", phoneNumber);
        Uri insertInfo = getContentResolver().insert(uri, values);
        Log.e("1023",">>>>>>" + "new sim contact uri, "
                + insertInfo.toString());
    }
    @Override
    public void onJSClick(final JsClickInfo jsInfo) {
        Log.e("js", "回结果");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (jsInfo == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rlWeb.setBackgroundResource(R.mipmap.net2);
                        }
                    });
                }
            }
        }, 60000);

        if (jsInfo.isPay_success()) {
            timer.cancel();
            try {
                requestData();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if (jsInfo.isPay_fail()) {
            timer.cancel();
            FailData();
        }

    }

    /**
     * 下单失败
     */
    private void FailData() {

        Log.e("下单", "下单失败");

        Intent intent = new Intent();
        intent.setAction("com.android.zhuner.time");
        Integer time1 = Integer.valueOf(time);
        intent.putExtra("Zhuner_Time", time1);
        Log.e("time", "====" + time1);
        PayPalActivity.this.sendBroadcast(intent);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                tvTishi.setVisibility(View.VISIBLE);
//                tvTishi.setText("下单失败");
                rl.setVisibility(View.GONE);
                ivTishi.setImageResource(R.mipmap.fail1);
                Glide.with(PayPalActivity.this)
                        .load(R.mipmap.fail2).into(ivTishi1);
                tvError.setText(getResources().getString(R.string.fail));

                llIsnet.setVisibility(View.VISIBLE);
//                Toast.makeText(CustomWebViewActivity.this, "下单失败", Toast.LENGTH_SHORT).show();
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        clearCache();
        super.onDestroy();
    }

}
