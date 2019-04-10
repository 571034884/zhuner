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

import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.impl.IDataManager;
import com.aibabel.baselibrary.sphelper.SPHelper;
import com.aibabel.baselibrary.utils.FilesUtil;
import com.aibabel.baselibrary.utils.XIPCUtils;
import com.aibabel.surfinternet.activity.OrderActivity;
import com.aibabel.surfinternet.activity.TrandActivity;
import com.aibabel.surfinternet.activity.ViewPagerActivity;
import com.aibabel.surfinternet.base.BaseNetActivity;
import com.aibabel.surfinternet.net.Api;
import com.aibabel.surfinternet.bean.OrderitemBean;
import com.aibabel.surfinternet.utils.CommonUtils;
import com.aibabel.surfinternet.utils.Logs;
import com.aibabel.surfinternet.utils.NetUtil;
import com.aibabel.surfinternet.utils.WeizhiUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.taobao.sophix.SophixManager;
import com.tencent.mmkv.MMKV;
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

import butterknife.BindView;

import static com.xuexiang.xipc.XIPC.getContext;

public class MainActivity extends BaseNetActivity{
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_quanqiu)
    TextView tvQuanqiu;
    @BindView(R.id.tv_net_help)
    TextView tvNetHelp;
    @BindView(R.id.ll)
    RelativeLayout ll;
    @BindView(R.id.iv_error)
    ImageView ivError;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.ll_isnet)
    LinearLayout llIsnet;
    @BindView(R.id.cl)
    ConstraintLayout cl;

    private List<OrderitemBean.DataBean> datalist = new ArrayList<>();
    private Intent intent;
    private boolean is_onclick = true;
    private boolean isFind = false;

    @Override
    public int getLayouts(Bundle var1) {
        return R.layout.activity_main;
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initView() {
        initCountryLanguage();
        getVersion();

        //读取LK标识--- 桌面进行存储
        Api.Lk_CARDTYPE = FilesUtil.readToFile();
        Log.e("LK---001","LK标识符："+ Api.Lk_CARDTYPE);
        if (NetUtil.isNetworkAvailable(MainActivity.this)) {
            initDatas();
        } else {
            ivClose.setVisibility(View.GONE);
            ll.setVisibility(View.VISIBLE);
            llIsnet.setVisibility(View.VISIBLE);
        }

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

    @Override
    public void initData() {

    }

    //获取系统语言
    private void initCountryLanguage() {
        //获取当前手机的语言
        Api.PHONE_COUNTRY = Locale.getDefault().getCountry();
        Api.PHONE_LANGUAGE = getResources().getConfiguration().locale.getLanguage();
        //读取语言选择的 json 文件
        Api.SETCOUNTRYlANGUAGE = com.aibabel.baselibrary.utils.CommonUtils.getLocalLanguage();
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
            Api.PRO_VERSION_NUMBER = display.substring(9, 10);
//            Api.PRO_VERSION_NUMBER = "S";

            Api.COUNTRY_VERSION_NUMBER = display.substring(7, 8);
//            Api.COUNTRY_VERSION_NUMBER = "5";

            Api.PHONE_MOBILE_NUMBER = display.substring(0, 2);
//            Api.PHONE_MOBILE_NUMBER = "PH";

            MMKV mmkv = MMKV.defaultMMKV();
            mmkv.encode("deviceType",Api.PRO_VERSION_NUMBER);
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

                    Api.PHONE_ICCID = list.get(i).getIccId();
//                 LK---001   Api.PHONE_ICCID = "89860012018051816514";
                    Log.e("LK---001","硬卡ICCID："+ Api.PHONE_ICCID);
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
        Api.PHONE_ICCID = telephonyManager.getDeviceId(1);
        Log.e("LK---001","LK卡IMEI获取成功："+ Api.PHONE_ICCID);
//        Log.e("imei_2", telephonyManager.getDeviceId(1));
        //Toast.makeText(MainActivity.this, "slot=" + slot, Toast.LENGTH_LONG).show();

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initDatas() {
        if (Api.Lk_CARDTYPE) {
            Log.e("LK---001","LK卡是否启动："+ Api.Lk_CARDTYPE+";开始获取imei");
            init_imei();
        } else {
            Log.e("LK---001","LK卡是否启动："+ Api.Lk_CARDTYPE+";开始获取iccid");
            initMtkDoubleSim();
        }
        if (!TextUtils.isEmpty(Api.PHONE_ICCID)) {
            Map<String, String> map = new HashMap<>();
            map.put("iccid", Api.PHONE_ICCID);
            if (Api.Lk_CARDTYPE) {
                map.put("cardType", "lksc");
                Log.e("LK---001","请求数据："+ Api.PHONE_ICCID+"---lksc");
            }
            Log.e("LK---001","请求数据："+ Api.PHONE_ICCID);
            MMKV mmkv = MMKV.defaultMMKV();
            mmkv.encode("iccid_intent",Api.PHONE_ICCID);
            Logs.e(map.toString());
            OkGoUtil.get(mContext, Api.GET_COUNTRY_ORDER, map, OrderitemBean.class, new BaseCallback<OrderitemBean>() {
                @Override
                public void onSuccess(String method, OrderitemBean model, String resoureJson) {
                    Logs.e(Api.GET_COUNTRY_ORDER+":"+resoureJson);
                    if (model.getData() != null){
                        showDatas(model);
                    }
                }

                @Override
                public void onError(String method, String message, String resoureJson) {
                    Log.e("LK---001","请求失败:"+ Api.Lk_CARDTYPE);
                    Logs.e(Api.GET_COUNTRY_ORDER+":"+message.toString());
                    ivClose.setVisibility(View.GONE);
                    llIsnet.setVisibility(View.VISIBLE);
                    ll.setVisibility(View.VISIBLE);
                    tvError.setText(getResources().getString(R.string.zoudiule));
                }

                @Override
                public void onFinsh(String method) {}
            });
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void showDatas(OrderitemBean orderitemBean) {

        Log.e("LK---001","请求成功，是否LK："+ Api.Lk_CARDTYPE);
        datalist = orderitemBean.getData();
        if (datalist != null) {
            //如果 在订单存在的情况下 订单长度为0  则跳转 国家页
            if (datalist.size() == 0) {

                Log.e("LK---001","有订单,但订单长度为0:"+ Api.Lk_CARDTYPE);
                intent = new Intent(MainActivity.this, TrandActivity.class);
                intent.putExtra("first", 1);
            }
            // 否则 跳转 订单页
            else {

                Log.e("LK---001","有订单:"+ Api.Lk_CARDTYPE);
                intent = new Intent(MainActivity.this, OrderActivity.class);
                intent.putExtra("orderitemBean", orderitemBean);
                intent.putExtra("first", 1);
            }

            //如果 在订单不存在的情况下 则跳转 国家页
        } else {
            Log.e("LK---001","没有订单:"+ Api.Lk_CARDTYPE);
            intent = new Intent(MainActivity.this, TrandActivity.class);
            intent.putExtra("first", 1);
        }

        if (NetUtil.isNetworkAvailable(MainActivity.this) && Api.PHONE_ICCID != null && !TextUtils.equals(Api.PHONE_ICCID, "")) {
            startActivity(intent);
            finish();
        } else {
            ivClose.setVisibility(View.GONE);
            llIsnet.setVisibility(View.VISIBLE);
            ll.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        is_onclick = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
