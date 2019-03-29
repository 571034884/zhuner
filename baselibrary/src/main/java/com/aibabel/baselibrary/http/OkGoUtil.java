package com.aibabel.baselibrary.http;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.aibabel.baselibrary.R;
import com.aibabel.baselibrary.base.BaseApplication;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.baselibrary.utils.ProviderUtils;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作者：SunSH on 2018/6/12 15:26
 * 功能：
 * 版本：1.0
 */
public class OkGoUtil {

    public static String appVersionName = "1.0.0";
    public static String SUCCESS = "1";

    /**
     * 服务器地址
     */
    public static String defaultServerUrl;

    public static String getServerUrl() {
        return defaultServerUrl;
    }

    public static void setDefualtServerUrl(String defaultServerUrl) {
        OkGoUtil.defaultServerUrl = defaultServerUrl;
    }

    /**
     * 默认方法组
     */
    public static String defaultInterfaceGroup;

    public static String getDefaultInterfaceGroup() {
        return defaultInterfaceGroup;
    }

    public static void setDefaultInterfaceGroup(String defaultInterfaceGroup) {
        OkGoUtil.defaultInterfaceGroup = defaultInterfaceGroup;
    }

    /**
     * 其他接口组
     */
    public static List<String> otherInterfaceGroup = new ArrayList<>();

    public static void addOtherInterfaceGroup(String interfaceGroup) {
        OkGoUtil.otherInterfaceGroup.add(interfaceGroup);
    }

    /**
     * 显示对话框,提示无网络，使用默认接口组
     */
    public static <T extends BaseBean> void get(Context context, String interfaceName, Map<String, String> param, Class<T> cls, BaseCallback mCallback) {
        get(context, true, getDefaultInterfaceGroup(), interfaceName, param, cls, mCallback);
    }

    /**
     * 显示对话框，使用默认接口组
     */
    public static <T extends BaseBean> void get(Context context, boolean isNotice, String interfaceName, Map<String, String> param, Class<T> cls, BaseCallback
            mCallback) {
        get(context, isNotice, getDefaultInterfaceGroup(), interfaceName, param, cls, mCallback);
    }

    /**
     * 不显示对话框,提示无网络，使用默认接口组
     */
    public static <T extends BaseBean> void get(String interfaceName, Map<String, String> param, Class<T> cls, BaseCallback mCallback) {
        get(null, true, getDefaultInterfaceGroup(), interfaceName, param, cls, mCallback);
    }

    /**
     * 不显示对话框,使用默认接口组
     */
    public static <T extends BaseBean> void get(boolean isNotice, String interfaceName, Map<String, String> param, Class<T> cls, BaseCallback mCallback) {
        get(null, isNotice, getDefaultInterfaceGroup(), interfaceName, param, cls, mCallback);
    }

    /**
     * 不显示对话框,提示无网络
     */
    public static <T extends BaseBean> void get(String interfaceGroup, String interfaceName, Map<String, String> param, Class<T> cls, BaseCallback mCallback) {
        get(null, true, interfaceGroup, interfaceName, param, cls, mCallback);
    }

    /**
     * 不显示对话框
     */
    public static <T extends BaseBean> void get(boolean isNotice, String interfaceGroup, String interfaceName, Map<String, String> param, Class<T> cls,
                                                BaseCallback mCallback) {
        get(null, isNotice, interfaceGroup, interfaceName, param, cls, mCallback);
    }

    /**
     * 默认提示对话框，提示无网络信息
     */
    public static <T extends BaseBean> void get(Context context, String interfaceGroup, String interfaceName, Map<String, String> param, Class<T> cls, BaseCallback
            mCallback) {
        get(context, true, interfaceGroup, interfaceName, param, cls, mCallback);
    }

    /**
     * 可控显示对话框
     *
     * @param context        dialog中用到 控制对话框显示与否 null或不传 默认不不显示，反之
     * @param isNotice       无网是否提示 不传 默认显示
     * @param interfaceGroup 接口组
     * @param interfaceName  接口名
     * @param param          参数
     * @param cls            结果解析对象
     * @param mCallback      回调
     * @param <T>            泛型
     */
    public static <T extends BaseBean> void get(final Context context, boolean isNotice, final String interfaceGroup, final String interfaceName, Map<String,
            String> param, final Class<T> cls, final BaseCallback<T> mCallback) {
        if (!CommonUtils.isNetworkAvailable(BaseApplication.mApplication)) {
            if (isNotice) {
                ToastUtil.showShort(BaseApplication.mApplication, BaseApplication.mApplication.getResources().getString(R.string.toast_wuwangluo));
            }
            mCallback.onError(interfaceName, BaseApplication.mApplication.getResources().getString(R.string.toast_wuwangluo), "");
            return;
        }
//        GetRequest<String> getRequest = OkGo.<String>get(getServerUrl() + interfaceGroup + interfaceName).tag(interfaceName);
        GetRequest<String> getRequest = OkGo.<String>get(checkIps(BaseApplication.mApplication.getAppPackageName()) + interfaceGroup + interfaceName).tag
                (interfaceName);

        for (Map.Entry<String, String> entry : getCommonRequsetParam().entrySet()) {
            getRequest.params(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, String> entry : param.entrySet()) {
            getRequest.params(entry.getKey(), entry.getValue());
        }
//        //公共参数
//        getRequest.params("sv", Build.DISPLAY);
//        getRequest.params("sn", CommonUtils.getSN());
//        getRequest.params("sl", CommonUtils.getLocalLanguage());
//        getRequest.params("av", appVersionName);
//        getRequest.params("no", CommonUtils.getRandom());
////        getRequest.params("lat", ProviderUtils.getInfo(ProviderUtils.COLUMN_LATITUDE));
////        getRequest.params("lng", ProviderUtils.getInfo(ProviderUtils.COLUMN_LONGITUDE));
//        getRequest.params("lat", "1111");
//        getRequest.params("lng", "11");

        getRequest.execute(new DialogCallBack(context, interfaceName) {

            @Override
            public void onSuccess(Response<String> response) {
                try {
                    T baseBean = FastJsonUtil.changeJsonToBean(response.body(), cls);
                    if (baseBean.getCode().equals(SUCCESS)) {
                        mCallback.onSuccess(interfaceName, baseBean, response.body());
                    } else {
                        mCallback.onError(interfaceName, baseBean.getCode() + " " + baseBean.getMsg(), response.body());
//                        ToastUtil.showShort(context, baseBean.getMsg());
                    }
                } catch (Exception e) {
//                    Logger.e(e.getMessage().toString());
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                mCallback.onError(interfaceName, response.getException().getMessage(), response.body());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mCallback.onFinsh(interfaceName);
            }
        });
    }

    public static Map<String, String> getCommonRequsetParam() {
        Map<String, String> map = new HashMap<>();
        map.put("sv", Build.DISPLAY);
        map.put("sn", CommonUtils.getSN());
        map.put("sl", CommonUtils.getLocalLanguage());
        map.put("av", appVersionName);
        map.put("no", CommonUtils.getRandom() + "");
        map.put("lat", ProviderUtils.getInfo(ProviderUtils.COLUMN_LATITUDE));
        map.put("lng", ProviderUtils.getInfo(ProviderUtils.COLUMN_LONGITUDE));
//        map.put("lat", "1111");
//        map.put("lng", "11");
        return map;
    }

    /**
     * 通过当前程序包名 获取服务器地址
     *
     * @param packageName 包名
     * @return
     */
    public static String checkIps(String packageName) {
        String countryName = ProviderUtils.getInfo(ProviderUtils.COLUMN_COUNTRY);
        String ips = ProviderUtils.getInfo(ProviderUtils.COLUMN_IP);
        String key;
        try {
            if (countryName.equals("中国")) {
                key = "中国_" + packageName + "_joner";
            } else {
                key = "default_" + packageName + "_joner";
            }
            JSONObject jsonObject = new JSONObject(ips);
            JSONArray jsonArray = new JSONArray(jsonObject.getString(key));
            return jsonArray.getJSONObject(0).get("domain").toString();
        } catch (Exception e) {
            Log.e("init: ", "ip信息错误");
            return defaultServerUrl;
        }
    }

}
