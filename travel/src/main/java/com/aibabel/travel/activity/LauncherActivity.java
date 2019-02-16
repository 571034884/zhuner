package com.aibabel.travel.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.travel.BuildConfig;
import com.aibabel.travel.R;
import com.aibabel.travel.app.UrlConfig;
import com.aibabel.travel.bean.BaseModel;
import com.aibabel.travel.bean.Country_City;
import com.aibabel.travel.http.ResponseCallback;
import com.aibabel.travel.service.LocationService;
import com.aibabel.travel.utils.CommonUtils;
import com.aibabel.travel.utils.Constant;
import com.aibabel.travel.utils.ContentProviderUtil;
import com.aibabel.travel.utils.WeizhiUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.taobao.sophix.SophixManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ==========================================================================================
 *
 * @Author：CreateBy 张文颖
 * @Date：2018/5/31
 * @Desc：启动页 ==========================================================================================
 */
public class LauncherActivity extends BaseActivity implements ResponseCallback {


    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.ll)
    LinearLayout ll;
    private String version;
    private String proVersion;

    @Override
    public int initLayout() {
        return R.layout.activity_launcher;
    }

    @Override
    public void init() {
        String display = Build.DISPLAY;
        proVersion = display.substring(9, 10);
        Log.e("version", "==" + proVersion);
        version = display.substring(0, 2);
        Log.e("version", "==" + version);
        if (TextUtils.equals(version, "PL") && TextUtils.equals(proVersion, "L")) {
            tv1.setVisibility(View.GONE);
            tv2.setVisibility(View.VISIBLE);
            ll.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.GONE);
            ll.setVisibility(Color.parseColor("#fe5000"));
        }
        initData();
        rexiufu();
    }
    public void rexiufu() {
        String latitude = WeizhiUtil.getInfo(this, WeizhiUtil.CONTENT_URI_WY, "latitude");
        String longitude = WeizhiUtil.getInfo(this, WeizhiUtil.CONTENT_URI_WY, "longitude");
        String url =   Constant.RXF_URL+ "/v1/jonersystem/GetAppNew?sn=" + CommonUtils.getSN() + "&no=" + CommonUtils.getRandom() + "&sl=" + CommonUtils.getLocalLanguage() + "&av=" + BuildConfig.VERSION_NAME + "&app=" + getPackageName() + "&sv=" + Build.DISPLAY + "&lat=" + latitude + "&lng=" + longitude;
        Log.e("热修复",url);
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

    public void initData() {
        String version = CommonUtils.getDeviceInfo();
        Log.e("version", version);
        if (!CommonUtils.isAvailable()) {
            toWorld();
        } else {
            String cityName = ContentProviderUtil.getCity(this);
            String countryName = ContentProviderUtil.getCountry(this);
            Map<String, String> map = new HashMap<String, String>();

            map.put("cityName", cityName);
            map.put("CountryName", countryName);
//        map.put("cityName","北京");
//        map.put("CountryName","欧洲");
            get1(this, this, Country_City.class, map, UrlConfig.CMD_COUNTRY_CITY);


            startService(new Intent(this, LocationService.class));
        }

    }


    private void toWorld() {
        Intent intent = new Intent();
        intent.setClass(this, WorldActivity.class);
        intent.putExtra("first", 1);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onSuccess(String method, BaseModel result) {
        Country_City bean = (Country_City) result;
        if (null != bean) {
            int type = bean.getData().getType();
            String name = bean.getData().getName();
            String path = bean.getData().getCover();
            int id = bean.getData().getId();
            toDetail(id + "", name, path, type);
        } else {
            toWorld();
        }


    }

    @Override
    public void onError() {
        Toast.makeText(this, R.string.try_again, Toast.LENGTH_SHORT).show();
    }


    private void toDetail(String id, String name, String path, int type) {
        Intent intent = new Intent();
        switch (type) {
//            case 0:
//                Toast.makeText(this, "服务器出现异常，请稍后重试！", Toast.LENGTH_SHORT).show();
//                break;
            case 1:
                intent.setClass(this, CountryActivity.class);
                break;
            case 2:
                intent.setClass(this, CityActivity.class);
                break;
            default:
                intent.setClass(this, WorldActivity.class);
                break;
        }
        if (TextUtils.isEmpty(name)) {
            intent.setClass(this, WorldActivity.class);
        }

        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("url", path);
        intent.putExtra("first", 1);
        startActivity(intent);
        finish();
    }


}
