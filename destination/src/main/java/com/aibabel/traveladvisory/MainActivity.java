package com.aibabel.traveladvisory;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.aibabel.traveladvisory.activity.CityActivity;
import com.aibabel.traveladvisory.activity.CountryActivity;
import com.aibabel.traveladvisory.activity.WorldCountryActivity;
import com.aibabel.traveladvisory.app.BaseActivity;
import com.aibabel.traveladvisory.app.Constans;
import com.aibabel.traveladvisory.bean.GuojiagailanBean;
import com.aibabel.traveladvisory.bean.HotCityBean;
import com.aibabel.traveladvisory.bean.ShouyeBean;
import com.aibabel.traveladvisory.okgo.BaseBean;
import com.aibabel.traveladvisory.okgo.BaseCallback;
import com.aibabel.traveladvisory.okgo.OkGoUtil;
import com.aibabel.traveladvisory.utils.NetUtil;
import com.aibabel.traveladvisory.utils.ToastUtil;
import com.aibabel.traveladvisory.utils.WeizhiUtil;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.ColorFilterTransformation;


public class MainActivity extends BaseActivity implements BaseCallback {

    private String cityName;
    private String countryName = "";
    private String countryName1 = "";//租赁城市名称
    private Context mContext;

    private String ips;
    private String key;

    ShouyeBean shouyeBean;

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {

        mContext = this;
        if (NetUtil.isNetworkAvailable(MainActivity.this)) {
            countryName = WeizhiUtil.getInfo(MainActivity.this, WeizhiUtil.CONTENT_URI_WY, "country");
            cityName = WeizhiUtil.getInfo(MainActivity.this, WeizhiUtil.CONTENT_URI_WY, "city");
            ips = WeizhiUtil.getInfo(MainActivity.this, WeizhiUtil.CONTENT_URI_WY, "ips");
            countryName1 = WeizhiUtil.getInfo(MainActivity.this, WeizhiUtil.CONTENT_URI_ZF, "Country");

            if (cityName.length() > 2 && cityName.contains("市"))
                cityName = cityName.substring(0, cityName.lastIndexOf("市"));
            try {
                if (countryName.equals("中国")) {
                    key = "中国_" + getPackageName() + "_joner";
                } else {
                    key = "default_" + getPackageName() + "_joner";
                }
                JSONObject jsonObject = new JSONObject(ips);
                JSONArray jsonArray = new JSONArray(jsonObject.getString(key));
//                Constans.IP_PORT = "http://192.168.50.13:7001";
                Constans.IP_PORT = jsonArray.getJSONObject(0).get("domain").toString();
            } catch (Exception e) {
                Log.e("init: ", "ip信息错误");
            }

            Map<String, String> map = new HashMap<>();
            map.put("DestinationCountryName", countryName1);
            map.put("currentCountryName", countryName);
            map.put("currentCityName", cityName);
            OkGoUtil.<ShouyeBean>get(mContext, false, Constans.METHOD_GET_SHOUYE, map, ShouyeBean.class, this);

        } else {
            if (!BuildConfig.AUTO_TYPE.equals("go")) {
                //添加离线后
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, WorldCountryActivity.class);
                startActivity(intent);
                finish();
            } else {
                ToastUtil.show(MainActivity.this, getResources().getString(R.string.toast_wuwangluo), 1000);
            }
        }
    }

    @Override
    public void onSuccess(String method, BaseBean model) {
        switch (method) {
            case Constans.METHOD_GET_SHOUYE:
                shouyeBean = (ShouyeBean) model;
                Intent intent = new Intent();
                if (shouyeBean.getData().getType().equals("Country")) {
                    if (shouyeBean.getData().getName().equals("亚洲") ||
                            shouyeBean.getData().getName().equals("非洲") ||
                            shouyeBean.getData().getName().equals("北美洲") ||
                            shouyeBean.getData().getName().equals("南美洲") ||
                            shouyeBean.getData().getName().equals("欧洲") ||
                            shouyeBean.getData().getName().equals("大洋洲")) {
                        intent.setClass(MainActivity.this, WorldCountryActivity.class);
                    } else {
                        intent.setClass(MainActivity.this, CountryActivity.class);
                        intent.putExtra("isback", View.GONE);
                        intent.putExtra("countryName", shouyeBean.getData().getName());
                        intent.putExtra("countryId", shouyeBean.getData().getId());
                    }
                } else if (shouyeBean.getData().getType().equals("City")) {
                    intent.setClass(MainActivity.this, CityActivity.class);
                    intent.putExtra("isback", View.GONE);
                    intent.putExtra("cityName", shouyeBean.getData().getName());
                    intent.putExtra("cityId", shouyeBean.getData().getId());
                } else if (shouyeBean.getData().getType().equals("World")) {
                    intent.setClass(MainActivity.this, WorldCountryActivity.class);
                }
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onError(String method, String message) {
        ToastUtil.show(MainActivity.this, getResources().getString(R.string.toast_wuwangluo), 1000);
    }
}
