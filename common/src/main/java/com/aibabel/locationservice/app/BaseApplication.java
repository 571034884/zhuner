package com.aibabel.locationservice.app;

import android.app.Application;
import android.util.Log;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.utils.DeviceUtils;
import com.aibabel.locationservice.MainActivity;
import com.aibabel.locationservice.utils.CommonUtils;
import com.lzy.okgo.OkGo;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;


public class BaseApplication extends com.aibabel.baselibrary.base.BaseApplication {

    public static Application CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this;
        configOKGo();
        if(DeviceUtils.getSystem()!=DeviceUtils.System.FLY_INTERNATIONAL){
            configJPush();
        }
        StatisticsManager.getInstance(this);

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

    private void configOKGo(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(30*1000, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(30*1000, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(30*1000, TimeUnit.MILLISECONDS);
        OkGo.getInstance().init(this).setRetryCount(0).setOkHttpClient(builder.build()); //必须调用初始化
    }

    /**
     * 配置极光推送
     */
    public void configJPush() {
        Log.e("BaseApplication","配置极光推送");
        JPushInterface.setDebugMode (true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init (this);
        JPushInterface.setAlias(this, 1, CommonUtils.getSN());
        Set<String> hashSet = new HashSet<>();
        hashSet.add(CommonUtils.getDevice());
        try {
            hashSet.add(CommonUtils.getVerName(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JPushInterface.setTags(this, 2, hashSet);
        //设置通知只显示20条
        JPushInterface.setLatestNotificationNumber(this, 20);

    }



}
