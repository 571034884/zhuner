package com.aibabel.menu.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.ProviderUtils;
import com.aibabel.menu.MainActivity;
import com.aibabel.menu.app.MyApplication;
import com.aibabel.menu.bean.CityListBean;
import com.aibabel.menu.bean.MenuDataBean;
import com.aibabel.menu.util.AppStatusUtils;
import com.aibabel.menu.util.DBUtils;
import com.aibabel.menu.util.L;
import com.aibabel.menu.util.LocationUtils;
import com.aibabel.menu.util.SPUtils;
import com.aibabel.menu.util.UrlConstants;
import com.aibabel.menu.view.sousuo.PinyinComparator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MenuLocationReceiver extends BroadcastReceiver {


    boolean isUpdate = true;
    long oldTime = 0;


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        oldTime = System.currentTimeMillis();
        String cmd = intent.getStringExtra("cmd");
        if (TextUtils.equals(cmd, "search")) {
            L.e("search======================");
            bindMenuData(context, "update", "2");
            SPUtils.put(context, "isChange", "idChange");
            return;

        }

        isUpdate = true;
        Map<String, String> map = LocationUtils.getLocMap(context);
        L.e("loc==========spCity:" + (String) SPUtils.get(context, "cityName", "") + "=====street:" + map.get("street") + "=====city:" + map.get("city") + "=====province:" + map.get("province") + "=====country:" + map.get("country") + "============lat:" + LocationUtils.getLocation(context));
        String country = map.get("country");
//        String province=map.get("province");
//        String city=map.get("city");

        if (TextUtils.equals(SPUtils.get(context, "isChange", "").toString(), "true")) {
            String showCity = "";

            if (!TextUtils.isEmpty(country)) {

                if (TextUtils.equals(country, "中国")) {
                    showCity = map.get("city");
//           if (!TextUtils.equals(SPUtils.get(context,"menuShowkey","").toString(),country+"_"+showCity+"_"+map.get("street"))) {
                    if (!TextUtils.equals(SPUtils.get(context, "menuShowkey", "").toString(), country + "_" + showCity)) {

                        bindMenuData(context, "change", "2");
                    }

                } else {
                    //日本  泰国
                    SQLiteDatabase db = null;
                    switch (country) {
                        case "日本":
                            db = DBUtils.DBManager(context, "menu_city_jp.db");
                            break;
                        case "泰国":
                            db = DBUtils.DBManager(context, "menu_city_th.db");
                            break;
                    }
                    if (db != null) {
                        Cursor cursor = db.query("tb", null, "country=? and province_baidu=? and city_baidu=?", new String[]{map.get("country"), map.get("province"), map.get("city")}, null, null, null);

                        try {
                            if (cursor != null && cursor.moveToFirst()) {
                                do {
                                    showCity = cursor.getString(cursor.getColumnIndex("mddH5"));
                                    if (TextUtils.equals(showCity, "默认")) {
                                        showCity = cursor.getString(cursor.getColumnIndex("province_show"));
                                    }
                                } while (cursor.moveToNext());
                            }

                        } finally {
                            if (cursor != null) {
                                cursor.close();
                            }

                        }
//               if (!TextUtils.equals(SPUtils.get(context,"menuShowkey","").toString(),country+"_"+showCity+"_"+map.get("street"))) {
                        if (!TextUtils.equals(SPUtils.get(context, "menuShowkey", "").toString(), country + "_" + showCity)) {

                            bindMenuData(context, "change", "2");
                        }
                    } else {
                        //出日泰意外  国家
                        showCity = map.get("province");
//               if (!TextUtils.equals(SPUtils.get(context,"menuShowkey","").toString(),country+"_"+showCity+"_"+map.get("street"))) {
                        if (!TextUtils.equals(SPUtils.get(context, "menuShowkey", "").toString(), country + "_" + showCity)) {

                            bindMenuData(context, "change", "2");
                        }
                    }

                }
                isUpdate = false;
                SPUtils.put(context, "isChange", "false");
            }
        }


        String addr = ProviderUtils.getInfo("addr");
        if (!TextUtils.isEmpty(addr)) {
            if (MainActivity.upListener != null) {
                MainActivity.upListener.updateAddr(addr);
            }
        }




        if (!TextUtils.isEmpty(country)) {
            //中国   存城市级别
            if (TextUtils.equals(country, "中国")) {
                if (!TextUtils.isEmpty(map.get("city"))) {
                    SPUtils.put(context, "cityName", map.get("city"));
                    SPUtils.put(context, "showCityName", map.get("city"));
                }
            } else {
                if (!TextUtils.isEmpty(map.get("province"))) {
                    SPUtils.put(context, "cityName", map.get("province"));
                    String showCity = "";
                    //日本  泰国
                    SQLiteDatabase db = null;
                    switch (country) {
                        case "日本":
                            db = DBUtils.DBManager(context, "menu_city_jp.db");
                            break;
                        case "泰国":
                            db = DBUtils.DBManager(context, "menu_city_th.db");
                            break;
                    }
                    if (db != null) {
                        Cursor cursor = db.query("tb", null, "country=? and province_baidu=? and city_baidu=?", new String[]{map.get("country"), map.get("province"), map.get("city")}, null, null, null);

                        try {
                            if (cursor != null && cursor.moveToFirst()) {
                                do {
                                    showCity = cursor.getString(cursor.getColumnIndex("mddH5"));
                                    if (TextUtils.equals(showCity, "默认")) {
                                        showCity = cursor.getString(cursor.getColumnIndex("province_show"));
                                    }
                                } while (cursor.moveToNext());
                            } else {
                                showCity = map.get("province");
                            }

                        } finally {
                            if (cursor != null) {
                                cursor.close();
                            }

                        }

                    } else {
                        //出日泰意外  国家
                        showCity = map.get("province");

                    }
                    SPUtils.put(context, "showCityName", showCity);
                }
            }
        }



        //三分钟 并且未因城市切换而刚刚更新
        if (isUpdate) {
            L.e("three min update========================");

//
//            if (oldTime != 0 && (System.currentTimeMillis() - oldTime) < 5 * 1000) {
//                L.e("5秒发了两次以上广播==============================");
//                return;
//            }

            if (TextUtils.equals(SPUtils.get(context, "isChange", "").toString(), "false")) {
                if (!TextUtils.isEmpty(MyApplication.country_id) && !TextUtils.isEmpty(MyApplication.city_id)) {
                    bindMenuData(context, "update", "1");

                    if (AppStatusUtils.isApkInDebug(context)) {
                        if (!AppStatusUtils.isApkInDebug(context)) {
                            if (MainActivity.upListener != null) {
                                MainActivity.upListener.onError("根据国家id走了一次定位刷新!");
                            }
                        }
                    }
                } else {
                    //根据定位 刷新
                    if (!TextUtils.isEmpty(LocationUtils.getLocation(context))) {


                        bindMenuData(context, "update", "2");
                        SPUtils.put(context, "isChange", "idChange");
                        if (AppStatusUtils.isApkInDebug(context)) {
                            if (MainActivity.upListener != null) {
                                MainActivity.upListener.onError("根据经纬度》》走了一次定位刷新!");
                            }
                        }

                    }

                }


            } else {
                //根据定位 刷新
                if (!TextUtils.isEmpty(LocationUtils.getLocation(context))) {

                    bindMenuData(context, "update", "2");
                    SPUtils.put(context, "isChange", "idChange");
                    if (AppStatusUtils.isApkInDebug(context)) {
                        if (MainActivity.upListener != null) {
                            MainActivity.upListener.onError("根据经纬度》》走了一次定位刷新!");
                        }
                    }
                }
            }


        }



    }


    /**
     * 切换城市逻辑
     *
     * @param context
     * @param cityName
     * @return flase 需要切换  调出循环
     */
    private void changeCity(Context context, String cityName) {
        if (SPUtils.contains(context, "cityName")) {
            String city = (String) SPUtils.get(context, "cityName", "");
            L.e("cityName=========" + cityName + "city==========" + city);
            if (!TextUtils.equals(cityName, city)) {
                isUpdate = false;
                //判断本地和定位的城市
                L.e("城市发生改变==========================");
                bindMenuData(context, "change", "2");
            }

        }

        SPUtils.put(context, "cityName", cityName);


    }


    public void bindMenuData(Context mContext, String type, String getType) {
        Map<String, String> mapPram = new HashMap<>();
        mapPram.put("location", LocationUtils.getLocation(mContext));
        mapPram.put("cityId", MyApplication.city_id);
        mapPram.put("countryId", MyApplication.country_id);
        mapPram.put("type", getType);

        LocationUtils.printUrl(mContext, UrlConstants.GET_MENU, mapPram);

        OkGoUtil.get(false, UrlConstants.GET_MENU, mapPram, MenuDataBean.class, new BaseCallback<MenuDataBean>() {
            @Override
            public void onSuccess(String s, MenuDataBean menuDataBean, String s1) {
                if (MainActivity.upListener != null) {
                    L.e("bindMenuData  onsuccess================" + menuDataBean.toString());

                    if (TextUtils.equals(type, "change")) {
                        MainActivity.upListener.changeCity(menuDataBean, s1, type);
                    } else {
                        MainActivity.upListener.updateUI(menuDataBean);
                    }


                }
            }

            @Override
            public void onError(String s, String s1, String s2) {
                L.e("bindMenuData  onError ================" + s);
                if (MainActivity.upListener != null) {
//                    MainActivity.upListener.onError("您的网络走丢了!");
                }

            }

            @Override
            public void onFinsh(String s) {

            }


        });
    }
}
