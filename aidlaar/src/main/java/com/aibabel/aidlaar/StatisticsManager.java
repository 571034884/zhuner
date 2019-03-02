package com.aibabel.aidlaar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：SunSH on 2019/1/4 16:21
 * 功能：数据统计统一管理者类
 * 版本：1.0
 */
public class StatisticsManager {

    private static String TAG = "StatisticsManager";
    //    private static final String PACKAGE_SERVICE = "com.aibabel.statisticalserver";
    private static final String PACKAGE_SERVICE = "com.aibabel.locationservice";

    private StatisticsDataController controller;
    private static StatisticsManager instance;
    private Context mContext;
    private String mAppName;
    private String mAppVersion;
    private IBinder binder;

    public static synchronized StatisticsManager getInstance(Context context) {
        if (instance == null) {
            instance = new StatisticsManager(context);
        }
        return instance;
    }

    public StatisticsManager setConfig(String appName, String appVersion) {
        mAppName = appName;
        mAppVersion = appVersion;
        if (instance == null) {
            instance = new StatisticsManager(mContext);
        }
        return instance;
    }

    private StatisticsManager(Context context) {
        mContext = context.getApplicationContext();
        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.e(TAG, "onServiceConnected: " + PACKAGE_SERVICE + "成功");
                binder = service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent();
        intent.setAction("com.aibabel.statisticalserver.AidlService");
        intent.setPackage(PACKAGE_SERVICE);
        mContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private StatisticsDataController getService() {
        if (controller != null) {
            return controller;
        }
        controller = StatisticsDataController.Stub.asInterface(binder);
        return controller;
    }

    /**
     * 用户路径统计
     *
     * @param pageName     页面名
     * @param entryTime    进入时间
     * @param exitTime     退出时间
     * @param interactions 交互次数
     * @param keyWordm     相关参数
     */
    public void addUserPathAidl(String pageName, long entryTime, long exitTime, int interactions, String keyWordm) {
        Log.e(TAG, "addUserPath: " + pageName + entryTime + exitTime + interactions + keyWordm);
        try {
            getService().addPath(mAppName, mAppVersion, pageName, entryTime, exitTime, interactions, keyWordm);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 交互事件统计
     *
     * @param eventId 事件id
     * @param keyWord 相关参数
     */
    public void addEventAidl(int eventId, Map keyWord) {
        Log.e(TAG, "addEvent: " + eventId + keyWord);
        try {
            getService().addEvent(eventId, System.currentTimeMillis(), JSONObject.toJSON(keyWord).toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public void addEventAidl(String eventName, Map keyWord) {
//        Log.e(TAG, "addEvent: " + eventName + keyWord);
//        try {
//            getService().addEvent(mAppName, mAppVersion, eventName, System.currentTimeMillis(), "", JSONObject.toJSON(keyWord).toString());
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void addEventAidl(int eventId) {
        addEventAidl(eventId, new HashMap());
    }
//    public void addEventAidl(String eventName) {
//        addEventAidl(eventName, new HashMap());
//    }

    /**
     * 通知统计
     *
     * @param type  类型
     * @param param 参数
     * @param time  时间戳
     */
    public void addNotifyAidl(int notifyId, int type, long time, Map param) {
        Log.e(TAG, "addNotifyAidl: " + type + time + param);
        try {
            getService().addNotify(notifyId, time, type, JSONObject.toJSON(param).toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public void addNotifyAidl(int type, long time, String scope, String descirbe) {
//        Log.e(TAG, "addNotifyAidl: " + type + time + scope + descirbe);
//        try {
//            getService().addNotify(mAppName, mAppVersion, type, time, scope, descirbe);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void getAllData() {
        Log.e(TAG, "getAllData: ");
        try {
            getService().getAllData();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 修改通知阅读状态
//     *
//     * @param type 类型
//     * @param time 通知时间
//     */
//    public void setConsultedStatus(int type, long time) {
//        Log.e(TAG, "setConsultedStatus: ");
//        try {
//            getService().setConsultedStatus(mAppName, type, time);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 数据发送
     */
    public void sendDataAidl(String url, Map<String, String> postMap) {
        try {
            getService().sendDataToHost(url, postMap);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveSharePreference(String key, String value) {
        try {
            getService().saveSharePreference(key, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStringSP(String key, String defaultValue) {
        try {
            return getService().getStringSP(key, defaultValue);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public boolean getBooleanSP(String key, boolean defaultValue) {
        try {
            return getService().getBooleanSP(key, defaultValue);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public int getIntSP(String key, int defaultValue) {
        try {
            return getService().getIntSP(key, defaultValue);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public float getFloatSP(String key, float defaultValue) {
        try {
            return getService().getFloatSP(key, defaultValue);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public long getLongSP(String key, long defaultValue) {
        try {
            return getService().getLongSP(key, defaultValue);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}
