package com.aibabel.speech.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.speech.BuildConfig;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.taobao.sophix.SophixManager;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.Locale;

public class CommonUtils {


    /**
     * 判断网络是否可用
     *
     * @return true: 可用 false: 不可用
     */
    public static boolean isAvailable(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }



    /**
     * 获取本机SN 设备识别码
     * @return
     */
    /**
     * 获取本机SN 设备识别码
     *
     * @return
     */
    public static String getSN() {
        String sn="0000000000000000";
        try {
            Class clz = Class.forName("android.os.SystemProperties");
            Method method = clz.getMethod("get", String.class,String.class);
            sn = (String) method.invoke(clz,"gsm.serial", "0000000000000000");
            sn.trim();
            if (sn.indexOf(" ") != -1) {
                sn = sn.substring(0, sn.indexOf(" "));
            }
            Log.e("CommonUtils","sn="+sn);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CommonUtils",e.getMessage());
        }

        return sn;
    }


    public static int getRandom(){
        int random =  (int)((Math.random()*9+1)*1000);
        return random;
    }

    public static String getLocal(Context context){

        String country = Locale.getDefault().getCountry();
        String language = Locale.getDefault().getLanguage();
        String sl = "";

        switch (language){
            case "zh":
                sl = language+"_"+country;
                break;
            case "en":
            case "ja":
            case "ko":
                sl = language;
                break;
            default:
                sl="en";
                break;
        }
        return sl;
    }




    public static void rexiufu(Context mContext) {
        String baseUrl= HostUtil.getHotFixHost(mContext);

        String location=HostUtil.getLocation(mContext);
        String[] arr=location.split(",");
        String url = baseUrl+ "/v1/jonersystem/GetAppNew?sn=" + CommonUtils.getSN() + "&no=" + CommonUtils.getRandom() + "&sl=" + getLocal(mContext) + "&av=" + BuildConfig.VERSION_NAME + "&app=" + mContext.getPackageName() + "&sv=" + Build.DISPLAY + "&lat=" + arr[0] + "&lng=" +arr[1];

        OkGo.<String>get(url)
                .tag(mContext)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        L.e("热修复", response.body().toString());
                        if (!TextUtils.isEmpty(response.body().toString())) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                boolean isNew = (Boolean) ((JSONObject) jsonObject.get("data")).get("isNew");
                                if (isNew) {
                                    SophixManager.getInstance().queryAndLoadNewPatch();
                                    L.e("success:", "=================" + isNew + "=================");
                                } else {
                                    L.e("failed:", "=================" + isNew + "=================");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                L.e("Exception:", "==========" + e.getMessage() + "===========");
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                    }
                });
    }


}
