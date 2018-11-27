package com.aibabel.sos;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.aibabel.sos.activity.InformationItemActivity;
import com.aibabel.sos.activity.WorldCountryActivity;
import com.aibabel.sos.app.BaseActivity;
import com.aibabel.sos.app.Constans;
import com.aibabel.sos.utils.SosDbUtil;
import com.aibabel.sos.utils.WeizhiUtil;

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
        countryName = WeizhiUtil.getInfo(MainActivity.this, WeizhiUtil.CONTENT_URI_WY, "country");
        cityName = WeizhiUtil.getInfo(MainActivity.this, WeizhiUtil.CONTENT_URI_WY, "city");
        countryName1 = WeizhiUtil.getInfo(MainActivity.this, WeizhiUtil.CONTENT_URI_ZF, "Country");
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
        startActivity(intent);
        finish();
//        try {
//            Cursor cursor = getContentResolver().query(CONTENT_URI_WY, null, null, null, null);
//            cursor.moveToFirst();
//            countryName = cursor.getString(2);
//            cityName = cursor.getString(3);
//            Constans.GUOJIA = countryName;
//            Constans.CHENGSHI = cityName;
//            cursor.close();
//            Cursor cursor1 = getContentResolver().query(CONTENT_URI_ZF, null, null, null, null);
//            cursor1.moveToFirst();
//            countryName1 = cursor1.getString(1);
//            cityName1 = cursor1.getString(3);
//            cursor1.close();
//            Log.e("initData: ", "接收到定位服务的cursor" + countryName + "    " + countryName1);
//        } catch (Exception e) {
//            Log.e("initData: ", "没有接收到定位服务的cursor");
//            e.printStackTrace();
//        }
//        cityName = "大阪";
//        cityName1 = "纽约";
//        countryName = "中国";
//        countryName1 = "美国";
//        if (!TextUtils.equals(cityName1, "") && TextUtils.equals(cityName, cityName1) && SosDbUtil.getLingshiguan(cityName).size() > 0) {
//            Intent intent = new Intent(MainActivity.this, InformationItemActivity.class);
//            intent.putExtra("cs", cityName);
//            intent.putExtra("gj", countryName);
//            startActivity(intent);
//            finish();
//        } else if (!TextUtils.equals(countryName1, "中国") && !TextUtils.equals(countryName1, "")) {
//            Intent intent = new Intent(MainActivity.this, InformationItemActivity.class);
//            intent.putExtra("gj", countryName1);
//            startActivity(intent);
//            finish();
//        } else {
//            Intent intent = new Intent(MainActivity.this, WorldCountryActivity.class);
//            intent.putExtra("fh", View.GONE);
//            startActivity(intent);
//            finish();
//        }
    }
}
