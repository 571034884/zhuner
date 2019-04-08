package com.aibabel.surfinternet;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.aibabel.baselibrary.impl.IDataManager;
import com.aibabel.baselibrary.utils.FilesUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.baselibrary.utils.XIPCUtils;
import com.aibabel.surfinternet.activity.BaseActivity;
import com.aibabel.surfinternet.activity.OrderActivity;
import com.aibabel.surfinternet.activity.TrandActivity;
import com.aibabel.surfinternet.activity.ViewPagerActivity;
import com.aibabel.surfinternet.bean.Constans;
import com.aibabel.surfinternet.bean.OrderitemBean;
import com.aibabel.surfinternet.okgo.BaseBean;
import com.aibabel.surfinternet.okgo.BaseCallback;
import com.aibabel.surfinternet.okgo.OkGoUtil;
import com.aibabel.surfinternet.utils.CommonUtils;
import com.aibabel.surfinternet.utils.NetUtil;
import com.aibabel.surfinternet.utils.WeizhiUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.taobao.sophix.SophixManager;
import com.umeng.analytics.MobclickAgent;
import com.xuexiang.xipc.XIPC;
import com.xuexiang.xipc.core.channel.IPCListener;
import com.xuexiang.xipc.core.channel.IPCService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.xuexiang.xipc.XIPC.getContext;

public class MainActivity extends BaseActivity implements BaseCallback {


    private static final String TODO = "";

    ImageView ivClose;
    TextView tvQuanqiu;
    TextView tvNetHelp;
    RelativeLayout ll;
    ImageView ivError;
    TextView tvError;
    LinearLayout llIsnet;
    ConstraintLayout cl;

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
    private boolean isFind = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("surfinterner=====","onCreate");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_main);
        initView();

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
                } else {
                    key_xw = "default_" + getPackageName() + "_joner";
                    key_xs = "default_" + getPackageName() + "_pay";
                }
                JSONObject jsonObject = new JSONObject(ips);
                JSONArray jsonArray_xw = new JSONArray(jsonObject.getString(key_xw));
                Constans.HOST_XW = jsonArray_xw.getJSONObject(0).get("domain").toString();
//                Constans.HOST_XW = "http://39.107.238.111:7001";
                Log.e("LK---001", Constans.HOST_XW + "---");
                JSONArray jsonArray_xs = new JSONArray(jsonObject.getString(key_xs));
                Constans.HOST_XS = jsonArray_xs.getJSONObject(0).get("domain").toString();
//                Constans.HOST_XS = "https://wx.aibabel.com:3002";
                Log.e("LK---001", Constans.HOST_XS + "---");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        rexiufu();
        initCountryLanguage();
//        init_imei();
        getVersion();

        //读取LK标识--- 桌面进行存储
        Constans.Lk_CARDTYPE = FilesUtil.readToFile();
        Log.e("LK---001","LK标识符："+Constans.Lk_CARDTYPE);
        if (NetUtil.isNetworkAvailable(MainActivity.this)) {
            initData();
        } else {
            ivClose.setVisibility(View.GONE);
            ll.setVisibility(View.VISIBLE);
            llIsnet.setVisibility(View.VISIBLE);
        }



        XIPC.connectApp(getContext(), XIPCUtils.XIPC_MENU_NEW);
        XIPC.setIPCListener(new IPCListener() {
            @Override
            public void onIPCConnected(Class<? extends IPCService> service) {
//                Toast.makeText(MainActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                IDataManager dm = XIPC.getInstance(IDataManager.class);
                String softType = dm.getString("softSimType");
                Log.e("LK---001","LK标识符："+Constans.Lk_CARDTYPE+"---LK当前卡状态："+softType);
            }
        });

        //关闭
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //帮助
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

    //---------领科卡启动过程-------------------------------------------------------------------------------
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        is_onclick = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        XIPC.disconnect(getContext());
    }

    //----------------------------------------------------------------------------------------
    private void initView() {
        ivClose = findViewById(R.id.iv_close);
        ivError = findViewById(R.id.iv_error);
        tvQuanqiu = findViewById(R.id.tv_quanqiu);
        llIsnet = findViewById(R.id.ll_isnet);
        cl = findViewById(R.id.cl);
        tvNetHelp = findViewById(R.id.tv_net_help);
        tvError = findViewById(R.id.tv_error);
        ll = findViewById(R.id.ll);
    }

    public void rexiufu() {
        String latitude = WeizhiUtil.getInfo(this, WeizhiUtil.CONTENT_URI_WY, "latitude");
        String longitude = WeizhiUtil.getInfo(this, WeizhiUtil.CONTENT_URI_WY, "longitude");
        String url = Constans.HOST_XW + "/v1/jonersystem/GetAppNew?sn=" + CommonUtils.getSN() + "&no=" + CommonUtils.getRandom() + "&sl=" + CommonUtils.getLocalLanguage() + "&av=" + BuildConfig.VERSION_NAME + "&app=" + getPackageName() + "&sv=" + Build.DISPLAY + "&lat=" + latitude + "&lng=" + longitude;
        Log.e("热修复", url);
        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("热修复", response.body().toString());
                        if (!TextUtils.isEmpty(response.body().toString())) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                boolean isNew = (Boolean) ((JSONObject) jsonObject.get("data")).get("isNew");
                                if (isNew) {
                                    SophixManager.getInstance().queryAndLoadNewPatch();
                                    Log.e("success:", "=================" + isNew + "=================");
                                } else {
                                    Log.e("failed:", "=================" + isNew + "=================");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("Exception:", "==========" + e.getMessage() + "===========");
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                    }
                });


    }

    //获取系统语言
    private void initCountryLanguage() {
        //获取当前手机的语言
        Constans.PHONE_COUNTRY = Locale.getDefault().getCountry();
        Constans.PHONE_LANGUAGE = getResources().getConfiguration().locale.getLanguage();
        //读取语言选择的 json 文件
        Log.e("lan_country", Constans.PHONE_COUNTRY + "========lan_language----------" + Constans.PHONE_LANGUAGE + "--------");
        if (Constans.PHONE_LANGUAGE.equals("zh")) {
            if (Constans.PHONE_COUNTRY.equals("CN")) {
                Constans.SETCOUNTRYlANGUAGE = "Chj";

            } else if (Constans.PHONE_COUNTRY.equals("TW")) {
                Constans.SETCOUNTRYlANGUAGE = "Chf";
            } else {
                Constans.SETCOUNTRYlANGUAGE = "Chj";
            }
        } else if (Constans.PHONE_LANGUAGE.equals("en")) {
            Constans.SETCOUNTRYlANGUAGE = "En";
        } else if (Constans.PHONE_LANGUAGE.equals("ja")) {
            Constans.SETCOUNTRYlANGUAGE = "Jpa";
        } else if (Constans.PHONE_LANGUAGE.equals("ko")) {
            Constans.SETCOUNTRYlANGUAGE = "Kor";
        } else {
            Constans.SETCOUNTRYlANGUAGE = "Chj";
        }

    }

    //是否可点击
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

    private void getVersion() {
        try {
            String display = Build.DISPLAY;
            Constans.PRO_VERSION_NUMBER = display.substring(9, 10);
//            Constans.PRO_VERSION_NUMBER = "S";

            Constans.COUNTRY_VERSION_NUMBER = display.substring(7, 8);
//            Constans.COUNTRY_VERSION_NUMBER = "5";

            Constans.PHONE_MOBILE_NUMBER = display.substring(0, 2);
//            Constans.PHONE_MOBILE_NUMBER = "PH";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取ICCID
    @SuppressLint({"MissingPermission", "NewApi"})
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void initMtkDoubleSim() {
        String ss = CommonUtils.getSN();
        Log.e("LK---001","SN:"+ss);
        try {
            List<SubscriptionInfo> list = SubscriptionManager.from(this).getActiveSubscriptionInfoList();
            //如果没有取到
            if (null == list || list.size() == 0) {
                Log.e("LK---001","硬卡ICCID：null");
                ivClose.setVisibility(View.GONE);
                ll.setVisibility(View.VISIBLE);
                llIsnet.setVisibility(View.VISIBLE);
                ivError.setImageResource(R.mipmap.iccid);
                tvError.setText(getResources().getString(R.string.iccid));
            }
            //获取内置卡的Iccid
            for (int i = 0; i < list.size(); i++) {
                Log.e("Q_M", "ICCID-->" + list.get(i).getIccId());
                Log.e("Q_M", "sim_id-->" + list.get(i).getSimSlotIndex());
                if (list.get(i).getSimSlotIndex() == 1) {

                    Constans.PHONE_ICCID = list.get(i).getIccId();
//                 LK---001   Constans.PHONE_ICCID = "89860012018051816514";
                    Log.e("LK---001","硬卡ICCID："+Constans.PHONE_ICCID);
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

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //获取IMEI
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init_imei() {


        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

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
        Constans.PHONE_ICCID = telephonyManager.getDeviceId(0);
        Log.e("LK---001","LK卡IMEI获取成功："+Constans.PHONE_ICCID);
//        Log.e("imei_2", telephonyManager.getDeviceId(1));
        //Toast.makeText(MainActivity.this, "slot=" + slot, Toast.LENGTH_LONG).show();

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initData() {
        if (Constans.Lk_CARDTYPE) {
            Log.e("LK---001","LK卡是否启动："+Constans.Lk_CARDTYPE+";开始获取imei");
            init_imei();
        } else {
            Log.e("LK---001","LK卡是否启动："+Constans.Lk_CARDTYPE+";开始获取iccid");
            initMtkDoubleSim();
        }

//         iccid = "89852022018041802362";
//         Constans.PHONE_ICCID = "89860012017300216438";
        if (!TextUtils.isEmpty(Constans.PHONE_ICCID)) {
            Map<String, String> map = new HashMap<>();
            map.put("iccid", Constans.PHONE_ICCID);
            if (Constans.Lk_CARDTYPE) {
                map.put("cardType", "lksc");
                Log.e("LK---001","请求数据："+Constans.PHONE_ICCID+"---lksc");
            }
            Log.e("LK---001","请求数据："+Constans.PHONE_ICCID);
            OkGoUtil.<OrderitemBean>get(MainActivity.this, Constans.METHOD_KADINGDANXIANGQING, map, OrderitemBean.class, this);
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onSuccess(String method, BaseBean model) {

        Log.e("LK---001","请求成功，是否LK："+Constans.Lk_CARDTYPE);
        orderitemBean = (OrderitemBean) model;
        datalist = orderitemBean.getData();
        if (datalist != null) {
            //如果 在订单存在的情况下 订单长度为0  则跳转 国家页
            if (datalist.size() == 0) {

                Log.e("LK---001","有订单,但订单长度为0:"+Constans.Lk_CARDTYPE);
                intent = new Intent(MainActivity.this, TrandActivity.class);
                intent.putExtra("first", 1);
            }
            // 否则 跳转 订单页
            else {

                Log.e("LK---001","有订单:"+Constans.Lk_CARDTYPE);
                intent = new Intent(MainActivity.this, OrderActivity.class);
                intent.putExtra("orderitemBean", orderitemBean);
                intent.putExtra("first", 1);
            }

            //如果 在订单不存在的情况下 则跳转 国家页
        } else {
            Log.e("LK---001","没有订单:"+Constans.Lk_CARDTYPE);
            intent = new Intent(MainActivity.this, TrandActivity.class);
            intent.putExtra("first", 1);
        }


        if (NetUtil.isNetworkAvailable(MainActivity.this) && Constans.PHONE_ICCID != null && !TextUtils.equals(Constans.PHONE_ICCID, "")) {
            startActivity(intent);
            finish();
        } else {
            ivClose.setVisibility(View.GONE);
            llIsnet.setVisibility(View.VISIBLE);
            ll.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onError(String method, Response<String> response) {
        Log.e("LK---001","请求失败:"+Constans.Lk_CARDTYPE);
        ivClose.setVisibility(View.GONE);
        llIsnet.setVisibility(View.VISIBLE);
        ll.setVisibility(View.VISIBLE);
        tvError.setText(getResources().getString(R.string.zoudiule));
    }
}
