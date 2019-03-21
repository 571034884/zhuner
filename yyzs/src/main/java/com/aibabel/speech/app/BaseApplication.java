package com.aibabel.speech.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.WindowManager;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.speech.BuildConfig;
import com.aibabel.speech.properites.Constants;
import com.aibabel.speech.tcp.SocketManger;
import com.aibabel.speech.util.CommonUtils;
import com.aibabel.speech.util.HostUtil;
import com.aibabel.speech.util.L;
import com.aibabel.speech.util.SystemUtil;

import com.baidu.mapapi.SDKInitializer;
import com.aibabel.speech.util.L;
import com.aibabel.speech.util.SystemUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.xuhao.android.libsocket.sdk.OkSocket;

import org.xutils.x;

import java.util.LinkedList;

import core.mate.Core;

/**
 * Created by SunSH on 2018/3/14.
 * TTS
 * <p>
 * LILtE6ZfdeC0GROf3S431SWm
 */

public class BaseApplication extends com.aibabel.baselibrary.base.BaseApplication {


    /**
     * 存储程序中所创建的activity
     */
//    private static LinkedList<Activity> activityLinkedList;
    public static String bot_session = "";
    public static String is_dialog_end = "";
    public static String user_id = "0000000000000000";


    public static int tishi_num = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        StatisticsManager.getInstance(this).setConfig(getPackageName(), BuildConfig.VERSION_NAME);
        L.e("BaseApplication    onCreate===================");


        x.Ext.init(this);
        x.Ext.setDebug(true);
        initPlugin();
        //初始化Vitamio框架
        tishi_num++;

        SDKInitializer.initialize(this);
        //动画初始化
        Core.getInstance().init(this);
        Core.getInstance().setDevModeEnable();

        try {

            user_id = CommonUtils.getSN();
            //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
            UMConfigure.init(this, "5b51a062f43e485bea00004b", user_id, UMConfigure.DEVICE_TYPE_PHONE,
                    null);
            MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
            L.e("串号---------------" + user_id);
            if (user_id == null || user_id.equals("")) {
                user_id = "0000000000000000";
            }


        } catch (Exception e) {

        }


    }

    @Override
    public String getAppVersionName() {
        return null;
    }

    @Override
    public String getAppPackageName() {
        return null;
    }

    @Override
    public void setServerUrlAndInterfaceGroup() {

    }

    @Override
    public String setUmengKey() {
        return null;
    }


    /**
     * 初始化第三方框架
     */
    private void initPlugin() {
        /**
         * OKGO初始化
         */
        OkSocket.initialize(this, true);

    }

    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {

        super.registerActivityLifecycleCallbacks(callback);
    }

    @Override
    public void onTrimMemory(int level) {
        changeUrl();
        super.onTrimMemory(level);
    }

    //去定位获取一次   语音识别和意图识别的  服务器地址
    public void changeUrl() {
        String url = HostUtil.getServerHost("pa");
        if (!url.equals("")) {
            Constants.getInstance().setBaseUrl(url);
            L.e("----------------url:" + Constants.getInstance().getBaseUrl());
        }

        String shibieUrl = HostUtil.getServerHost("function");
        if (!shibieUrl.equals("")) {
            try {
                String[] arr = shibieUrl.split(":");
                SocketManger.getInstance().setIP(arr[0]);
                SocketManger.getInstance().setPORT(Integer.valueOf(arr[1]));
                L.e("MyApp onTrimMemory==============" + arr[0] + "=====" + arr[1]);
                if (!arr[0].equals(SocketManger.getInstance().getIP())) {
                    L.e("MyApp onTrimMemory==============切换");
                    SocketManger.getInstance().reInitConfig(arr[0], Integer.valueOf(arr[1]));
                }
            } catch (Exception e) {

            }
        }
    }
}
