package com.aibabel.fyt_exitandentry.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.fyt_exitandentry.R;
import com.aibabel.fyt_exitandentry.bean.CityAirplaneBean;
import com.aibabel.fyt_exitandentry.bean.CityListBean;
import com.aibabel.fyt_exitandentry.bean.Constans;

import com.aibabel.fyt_exitandentry.utils.ContentProviderUtil;
import com.aibabel.fyt_exitandentry.utils.NetUtil;
import com.aibabel.fyt_exitandentry.utils.ToastUtil;
import com.lzy.okgo.model.Response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirstActivity extends BaseActivity implements BaseCallback<BaseBean> {

    private CityAirplaneBean cityAirplaneBean;
    private List<CityAirplaneBean.DataBean> airplaneBeanData;
    private List<CityListBean.DataBean> cityListBeanData;

    private final Uri CONTENT_URI = Uri.parse("content://com.dommy.qrcode/aibabel_information");
    String dev_oid, dev_uid, dev_uname, dev_sn;
    private String dev_d;

    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_first;
    }

    @Override
    public void init() {
        Constans.HOST_XW = ContentProviderUtil.getHost(this);
        Constans.CITY = ContentProviderUtil.getCity(this);
        Constans.COUNTRY = ContentProviderUtil.getCountry(this);
        setPathParams("目的地名称");

        Map map = new HashMap();
        map.put("目的地名称", Constans.CITY);
        StatisticsManager.getInstance(mContext).addEventAidl( "进入页面", map);
//        Constans.CITY = "东京";
//        Constans.COUNTRY = "日本";
        if (NetUtil.isNetworkAvailable(this)) {
            initData();
        } else {
            ToastUtil.showShort(this, "当前无网络");
            finish();
        }
        setHotRepairEnable(true, 5);
    }

    private void initDictionary() {
        try {
            Cursor cursor = FirstActivity.this.getContentResolver().query(CONTENT_URI, null, null, null, null);
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

    private void initData() {
        if (TextUtils.equals(Constans.CITY, "")|| TextUtils.equals(Constans.COUNTRY, "")||(!TextUtils.equals(Constans.COUNTRY, "日本") )) {
            Intent intent = new Intent(FirstActivity.this, ChoiceCityActivity.class);
            intent.putExtra("isFirst", 1);
            startActivity(intent);
            finish();
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("countryChj", Constans.COUNTRY);
            map.put("cityChj", Constans.CITY);
            OkGoUtil.<CityAirplaneBean>get(FirstActivity.this, Constans.METHOD_GETCITYAIRPLANE, map, CityAirplaneBean.class, this);
        }
    }
    @Override
    public void onSuccess(String method, BaseBean baseBean, String s1) {
        switch (method) {
            case Constans.METHOD_GETCITYAIRPLANE:
                cityAirplaneBean = (CityAirplaneBean) baseBean;
                airplaneBeanData = cityAirplaneBean.getData();
                if (airplaneBeanData == null) {
                    Intent intent = new Intent(FirstActivity.this, NoCityActivity.class);
                    intent.putExtra("isFirst", 1);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
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
