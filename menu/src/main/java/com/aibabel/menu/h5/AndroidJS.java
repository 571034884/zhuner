package com.aibabel.menu.h5;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.menu.MainActivity;
import com.aibabel.menu.SearchActivity;
import com.aibabel.menu.app.MyApplication;
import com.aibabel.menu.util.AppStatusUtils;
import com.aibabel.menu.util.CommonUtils;
import com.aibabel.menu.util.L;
import com.aibabel.menu.util.LocationUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.json.JSONException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public class AndroidJS {

    private MainActivity mContext;

    public AndroidJS(MainActivity context) {
        mContext = context;
    }
//
//    @JavascriptInterface
//    public void jumpSos(String cityId) {
//        L.e("webview>>>>>>>>>>>=================", cityId);
//        //调起SOS
//        mContext.startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext,"com.aibabel.sos"));
//    }
//    @JavascriptInterface
//    public void jumpSos() {
//        L.e("webview>>>>>>>>>>>=================wc1111111111111111");
//        //调起SOS
//        mContext.startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext,"com.aibabel.sos"));
//    }

    @JavascriptInterface
    public void jumpSos(String countryName, String cityName) {
        L.e("webview>>>>>>>>>>>=================country:" + countryName + "==========city:" + cityName);
        //调起SOS
        mContext.startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, "com.aibabel.sos").putExtra("country", countryName).putExtra("city", cityName).putExtra("from","menu"));

    }

    @JavascriptInterface
    public void jumpSearch() {

        //调起搜索页面
        ((MainActivity) mContext).startActivityForResult(new Intent(mContext, SearchActivity.class), 100);

    }


    public void getJsonCity(String cityId) {
        //返回当前城市的json

    }

    @JavascriptInterface
    public String getCCId() {
        //返回当前国家的json
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("cityId", MyApplication.city_id);
        jsonObject.put("countryId", MyApplication.country_id);


        jsonObject.put("sn", CommonUtils.getSN());
        jsonObject.put("sv", Build.DISPLAY);
        String loc = LocationUtils.getLocation(mContext);
        if (!TextUtils.isEmpty(loc)) {
            try {
                String[] arr = loc.split(",");
                jsonObject.put("lat", arr[0]);
                jsonObject.put("lng", arr[1]);
            } catch (Exception e) {
                jsonObject.put("lat", "-");
                jsonObject.put("lng", "-");
            }

        } else {
            jsonObject.put("lat", "-");
            jsonObject.put("lng", "-");
        }


        L.e("getCCId=============" + jsonObject.toJSONString());

        return jsonObject.toJSONString();

    }

    @JavascriptInterface
    public void tiShi() {
      /*  CustomDialog.Builder builderChangeCity = new CustomDialog.Builder((MainActivity) mContext, R.activity_rent_locked.dialog_tishi_no_source);
        builderChangeCity.setTvListener(R.id.dialog_tishi_sure, "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderChangeCity.dismiss();
            }
        });
        builderChangeCity.setCanceledOnTouchOutside(false);
        builderChangeCity.show();*/

//        mContext.startActivity(new Intent(mContext, SearchActivity.class));
    }

    /**
     *没有城市内容提示
     */
    @JavascriptInterface
    public void noCity() {
        if (MainActivity.upListener != null) {
            MainActivity.upListener.noCity();
        }
    }

    /**
     * 统计H5
     * @param json
     */

    @JavascriptInterface
    public void countH5(String json) {
        L.e("tongji h5:"+json);

    }
    @JavascriptInterface
    public void addJsEvent(String eventId, String parameters) throws JSONException {
        HashMap<String, Serializable> parametersMap=null;

        if (!TextUtils.isEmpty(parameters)){
            org.json.JSONObject object=new org.json.JSONObject(parameters);
            parametersMap=new HashMap<>();
            Iterator<String> keys=object.keys();
            while (keys.hasNext()){
                String key=keys.next();
                String value= (String) object.get(key);
                parametersMap.put(key,value);
            }
        }

       mContext.addStatisticsEvent(eventId, parametersMap);
        try {

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void addPageParameters(String key, Serializable value) {
        mContext.addPageParameters(key, value);
    }

}
