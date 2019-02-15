package com.aibabel.sos;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.aibabel.sos.activity.InformationItemActivity;
import com.aibabel.sos.activity.WorldCountryActivity;
import com.aibabel.sos.app.BaseActivity;
import com.aibabel.sos.app.Constans;
import com.aibabel.sos.utils.CommonUtils;
import com.aibabel.sos.utils.SosDbUtil;
import com.aibabel.sos.utils.WeizhiUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.taobao.sophix.SophixManager;

import org.json.JSONObject;

public class MainActivity extends BaseActivity {

    private static final Uri CONTENT_URI_WY = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");
    private static final Uri CONTENT_URI_ZF = Uri.parse("content://com.dommy.qrcode/aibabel_information");

    private String countryName = ""; //定位国家
    private String cityName = "";//定位城市
    private String countryName1 = "";//租赁国家
    private String cityName1 = "";//租赁城市

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        initData();
    }

    public void initData() {
        rexiufu();
        if (getIntent().getStringExtra("country") != null && getIntent().getStringExtra("country") != null) {
            countryName = getIntent().getStringExtra("country");
            cityName = getIntent().getStringExtra("city");
        } else {
            countryName = WeizhiUtil.getInfo(MainActivity.this, WeizhiUtil.CONTENT_URI_WY, "country");
            cityName = WeizhiUtil.getInfo(MainActivity.this, WeizhiUtil.CONTENT_URI_WY, "city");
            countryName1 = WeizhiUtil.getInfo(MainActivity.this, WeizhiUtil.CONTENT_URI_ZF, "Country");
        }
        if (cityName.length() > 2 && cityName.contains("市"))
            cityName = cityName.substring(0, cityName.lastIndexOf("市"));
        Constans.GUOJIA = countryName;
        Constans.CHENGSHI = cityName;
        Intent intent = new Intent();
        if (countryName1.equals("") || countryName1.equals(countryName)) {
            if (cityName.equals("")) {
                intent.setClass(MainActivity.this, WorldCountryActivity.class);
                intent.putExtra("fh", View.GONE);
            } else if (SosDbUtil.getLingshiguan(cityName).size() > 0) {
                intent.setClass(MainActivity.this, InformationItemActivity.class);
                intent.setAction("android.intent.action.InformationItemActivity");
                intent.putExtra("cs", cityName);
                intent.putExtra("gj", countryName);
            } else if (SosDbUtil.getLingshiguanInGuojia(countryName).size() > 0) {
                intent.setClass(MainActivity.this, InformationItemActivity.class);
                intent.putExtra("gj", countryName);
            } else {
                intent.setClass(MainActivity.this, WorldCountryActivity.class);
                intent.putExtra("fh", View.GONE);
            }
        } else {
            if (SosDbUtil.getLingshiguanInGuojia(countryName1).size() > 0) {
                intent.setClass(MainActivity.this, InformationItemActivity.class);
                intent.setAction("android.intent.action.InformationItemActivity");
                intent.putExtra("gj", countryName1);
            } else {
                intent.setClass(MainActivity.this, WorldCountryActivity.class);
                intent.putExtra("fh", View.GONE);
            }
        }
        if (getIntent().getStringExtra("from") != null && getIntent().getStringExtra("from").equals("menu"))
            intent.putExtra("fh", View.VISIBLE);
        startActivity(intent);
        finish();
    }

    public void rexiufu() {
//        String latitude = WeizhiUtil.getInfo(this, WeizhiUtil.CONTENT_URI_WY, "latitude");
//        String longitude = WeizhiUtil.getInfo(this, WeizhiUtil.CONTENT_URI_WY, "longitude");
        String latitude = "1111";
        String longitude = "1111";
        String url = "http://abroad.api.joner.aibabel.cn:7001/v1/jonersystem/GetAppNew?sn=" + CommonUtils.getSN() + "&no=" + CommonUtils.getRandom() + "&sl=" + CommonUtils.getLocalLanguage() + "&av=" + BuildConfig.VERSION_NAME + "&app=" + getPackageName() + "&sv=" + Build.DISPLAY + "&lat=" + latitude + "&lng=" + longitude;

        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("TAG", response.body().toString());
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

}
