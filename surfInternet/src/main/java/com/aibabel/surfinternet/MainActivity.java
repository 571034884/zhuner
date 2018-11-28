package com.aibabel.surfinternet;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.surfinternet.activity.BaseActivity;
import com.aibabel.surfinternet.activity.OrderActivity;
import com.aibabel.surfinternet.activity.TrandActivity;
import com.aibabel.surfinternet.activity.ViewPagerActivity;
import com.aibabel.surfinternet.bean.Constans;
import com.aibabel.surfinternet.bean.OrderitemBean;
import com.aibabel.surfinternet.okgo.BaseBean;
import com.aibabel.surfinternet.okgo.BaseCallback;
import com.aibabel.surfinternet.okgo.OkGoUtil;
import com.aibabel.surfinternet.utils.NetUtil;
import com.lzy.okgo.model.Response;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements BaseCallback {


    @BindView(R.id.tv_quanqiu)
    TextView tvQuanqiu;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.ll)
    RelativeLayout ll;
    @BindView(R.id.cl)
    ConstraintLayout cl;
    @BindView(R.id.ll_isnet)
    LinearLayout llIsnet;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.tv_net_help)
    TextView tvNetHelp;
    @BindView(R.id.iv_error)
    ImageView ivError;
    private OrderitemBean orderitemBean;
    private List<OrderitemBean.DataBean> datalist = new ArrayList<>();
    private Intent intent;
    private boolean isMtkDoubleSim;
    private static final Uri CONTENT_URI = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");

    private int onclick = 0;
    private String countryNameCN;
    private String ips;
    private String key_xw;
    private String key_xs;
    private boolean is_onclick = true;
    private String iccid;
    private boolean isFind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        sendBroadcast(new Intent("com.android.zhuner.wqhtime"));
        try {
            Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
            if (null != cursor) {
                cursor.moveToFirst();
                countryNameCN = cursor.getString(2);
                ips = cursor.getString(cursor.getColumnIndex("ips"));
                Log.e("ips", ips);
                if (countryNameCN.equals("中国")) {
                    key_xw = "中国_" + getPackageName() + "_joner";
                    key_xs = "中国_" + getPackageName() + "_pay";
//                key_xs_payment = "中国_"+"com.aibabel.surfinternet"+"_pay";
                } else {
                    key_xw = "default_" + getPackageName() + "_joner";
                    key_xs = "default_" + getPackageName() + "_pay";
//                key_xs_payment = "default_"+"com.aibabel.surfinternet"+"_pay";
                }
                JSONObject jsonObject = new JSONObject(ips);
                JSONArray jsonArray_xw = new JSONArray(jsonObject.getString(key_xw));
                Constans.HOST_XW = jsonArray_xw.getJSONObject(0).get("domain").toString();
//                Constans.HOST_XW = "http://39.107.238.111:7001";

//                Constans.HOST_XW="http://192.168.50.8:7001";
                JSONArray jsonArray_xs = new JSONArray(jsonObject.getString(key_xs));
                Constans.HOST_XS = jsonArray_xs.getJSONObject(0).get("domain").toString();
//                Constans.HOST_XS = "http://api.web.aibabel.cn:7000";
//                Constans.HOST_XS = "https://wx.aibabel.com:3002";
//                Constans.HOST_XS = "http://192.168.1.107:3001";

                Log.e("HOST_XS", Constans.HOST_XS + "---");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        initCountryLanguage();
        if (NetUtil.isNetworkAvailable(MainActivity.this)) {
            initData();
        } else {
//            initData();
//            cl.setBackgroundResource(R.mipmap.net1);
            ivClose.setVisibility(View.GONE);
            ll.setVisibility(View.VISIBLE);
            llIsnet.setVisibility(View.VISIBLE);
//            Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    finish();
//                }
//            }, 5000);
        }

     /*   llIsnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetUtil.isNetworkAvailable(MainActivity.this)){
                    initData();


                }
            }
        });*/

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvNetHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_onclick) {
                    onClickable(tvNetHelp, is_onclick);
                    startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));
                    is_onclick = false;
                }
            }
        });
    }

    private void initCountryLanguage() {
        //获取当前手机的语言
        String country = Locale.getDefault().getCountry();
        String language = getResources().getConfiguration().locale.getLanguage();
        //读取语言选择的 json 文件
        InputStreamReader inputStreamReader = null;
        Log.e("lan_country", country + "========lan_language----------" + language + "--------");
        if (language.equals("zh")) {
            if (country.equals("CN")) {
                Constans.SETCOUNTRYlANGUAGE = "Chj";

            } else if (country.equals("TW")) {
                Constans.SETCOUNTRYlANGUAGE = "Chf";
            } else {
                Constans.SETCOUNTRYlANGUAGE = "Chj";
            }
        } else if (language.equals("en")) {
            Constans.SETCOUNTRYlANGUAGE = "En";
        } else if (language.equals("ja")) {
            Constans.SETCOUNTRYlANGUAGE = "Jpa";
        } else if (language.equals("ko")) {
            Constans.SETCOUNTRYlANGUAGE = "Kor";
        } else {
            Constans.SETCOUNTRYlANGUAGE = "Chj";
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * @param view
     * @param b    是否可点击
     */
    private void onClickable(View view, boolean b) {
        if (b) {
            if (null != view) {
                view.setClickable(true);
            }
        } else {

            if (null != view) {
                view.setClickable(false);
            }

        }
    }

    //        try {
//            TelephonyManager tm = (TelephonyManager) MainActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
//            Class<?> c = Class.forName("android.telephony.TelephonyManager");
//
//            Method m = TelephonyManager.class.getDeclaredMethod("getSimSerialNumber", int.class);
//            iccid = (String) m.invoke(tm, 1);
//            Log.e("iccid_number", iccid + "===");
//
//            if (null == iccid) {
//                initMtkDoubleSim1();
////                    ivClose.setVisibility(View.GONE);
////                    ll.setVisibility(View.VISIBLE);
////                    llIsnet.setVisibility(View.VISIBLE);
////                    ivError.setImageResource(R.mipmap.iccid);
////                    tvError.setText(getResources().getString(R.string.iccid));
//
//
//            }
//        } catch (Exception e) {
//            isMtkDoubleSim = false;
//            return;
//        }
//        isMtkDoubleSim = true;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void initMtkDoubleSim() {

        try {
            List<SubscriptionInfo> list = SubscriptionManager.from(this).getActiveSubscriptionInfoList();
            if (null == list || list.size() == 0) {
                ivClose.setVisibility(View.GONE);
                ll.setVisibility(View.VISIBLE);
                llIsnet.setVisibility(View.VISIBLE);
                ivError.setImageResource(R.mipmap.iccid);
                tvError.setText(getResources().getString(R.string.iccid));
            }


            for (int i = 0; i < list.size(); i++) {
                Log.e("Q_M", "ICCID-->" + list.get(i).getIccId());
                Log.e("Q_M", "sim_id-->" + list.get(i).getSimSlotIndex());
                if (list.get(i).getSimSlotIndex() == 1) {
                    Log.e("iccid123", list.get(i).getIccId());
                    iccid = list.get(i).getIccId();
                    isFind = true;
                    return;
                }
            }
            if (!isFind) {
                ivClose.setVisibility(View.GONE);
                ll.setVisibility(View.VISIBLE);
                llIsnet.setVisibility(View.VISIBLE);
                ivError.setImageResource(R.mipmap.iccid);
                tvError.setText(getResources().getString(R.string.iccid));

            }
//            if (list.get(i).getSimSlotIndex()==1){
//                Log.e("iccid123",list.get(i).getIccId());
//                iccid = list.get(i).getIccId();
//                if (null == iccid) {
//                    ivClose.setVisibility(View.GONE);
//                    ll.setVisibility(View.VISIBLE);
//                    llIsnet.setVisibility(View.VISIBLE);
//                    ivError.setImageResource(R.mipmap.iccid);
//                    tvError.setText(getResources().getString(R.string.iccid));
//                }
//            }else {
//                iccid = list.get(i).getIccId();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void initMtkDoubleSim1() {
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(TELEPHONY_SERVICE);// 取得相关系统服务
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        iccid = telephonyManager.getSimSerialNumber();  //取出 ICCID
        if (null == iccid) {
            ivClose.setVisibility(View.GONE);
            ll.setVisibility(View.VISIBLE);
            llIsnet.setVisibility(View.VISIBLE);
            ivError.setImageResource(R.mipmap.iccid);
            tvError.setText(getResources().getString(R.string.iccid));
        }
        Log.e("iccid", iccid);
    }

    private void initData() {
        initMtkDoubleSim();

        Log.e("iccid_dindan", iccid + "===");
//         iccid = "89860012017300216438";
        if (iccid == null) {
            initMtkDoubleSim();
        } else {
            Map<String, String> map = new HashMap<>();

            map.put("iccid", iccid);
//            map.put("iccid", "89860012017300216438");
            Log.e("iccid_dindan", iccid + "===");
//            ToastUtil.showLong(MainActivity.this,iccid);
            OkGoUtil.<OrderitemBean>get(MainActivity.this, Constans.METHOD_KADINGDANXIANGQING, map, OrderitemBean.class, this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        is_onclick = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onSuccess(String method, BaseBean model) {
        orderitemBean = (OrderitemBean) model;
        datalist = orderitemBean.getData();
//        ivClose.setVisibility(View.VISIBLE);
//        llIsnet.setVisibility(View.GONE);
//        ll.setVisibility(View.GONE);
        if (datalist != null) {
            if (datalist.size() == 0) {
                intent = new Intent(MainActivity.this, TrandActivity.class);
                intent.putExtra("first", 1);

            } else {
                intent = new Intent(MainActivity.this, OrderActivity.class);
                intent.putExtra("orderitemBean", orderitemBean);
                intent.putExtra("first", 1);
            }
        } else {
            intent = new Intent(MainActivity.this, TrandActivity.class);
            intent.putExtra("first", 1);
        }


        if (NetUtil.isNetworkAvailable(MainActivity.this)) {
            startActivity(intent);
            finish();
        } else {
//            ToastUtil.showShort(MainActivity.this, "当前无网络，请联网操作");
//            cl.setBackgroundResource(R.mipmap.net1);
            ivClose.setVisibility(View.GONE);
            llIsnet.setVisibility(View.VISIBLE);
            ll.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onError(String method, Response<String> response) {
        ivClose.setVisibility(View.GONE);
        llIsnet.setVisibility(View.VISIBLE);
        ll.setVisibility(View.VISIBLE);
        tvError.setText(getResources().getString(R.string.zoudiule));
    }
}
