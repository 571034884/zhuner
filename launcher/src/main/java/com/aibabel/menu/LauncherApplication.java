package com.aibabel.menu;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aibabel.baselibrary.base.BaseApplication;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.menu.service.MyService;
import com.aibabel.menu.utils.Logs;
import com.aibabel.message.helper.DemoHelper;
import com.aibabel.message.utiles.L;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.tencent.mmkv.MMKV;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by fytworks on 2019/4/16.
 */

public class LauncherApplication extends BaseApplication {

    private static LauncherApplication instance;
    public static Context applicationContext;
    // 记录是否已经初始化
    private boolean isInit = false;



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        applicationContext = this;
        //init helper
        try{
            MMKV.initialize(this);
        }catch (Exception e){
            Logs.e(e.toString());
        }
        DemoHelper.getInstance().init(applicationContext);
        initEasemob();
        configJPush();
        LitePal.initialize(this);
        initSQLite();
        startService(new Intent(this, MyService.class));

    }

    private void initSQLite() {
        //创建表,如果存在就不会在创建了
        try {
            SQLiteDatabase db = Connector.getDatabase();
        }catch (Exception e){
            e.printStackTrace();
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
        String HOST = "http://abroad.api.function.aibabel.cn:7001";
        switch (CommonUtils.getTimerType()) {
            case 0://国外
                HOST = "http://abroad.api.function.aibabel.cn:7001";
                break;
            case 1://国内
                HOST = "http://api.function.aibabel.cn:7001";
                break;
        }
        Logs.e(HOST);
        //设置服务器地址
        OkGoUtil.setDefualtServerUrl(HOST);
//        //如果需要其他接口组
        OkGoUtil.setDefaultInterfaceGroup("/v1/deviceMenu/");

    }

    @Override
    public String setUmengKey() {
              return "5bfe125db465f545ae0000eb";
    }

    public static LauncherApplication getInstance() {
        return instance;
    }

    /**
     * 初始化环信
     */
    private void initEasemob() {



        // 获取当前进程 id 并取得进程名
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        /**
         * 如果app启用了远程的service，此application:onCreate会被调用2次
         * 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
         * 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
         */
        if (processAppName == null || !processAppName.equalsIgnoreCase(applicationContext.getPackageName())) {
            // 则此application的onCreate 是被service 调用的，直接返回
            return;
        }
        if (isInit) {
            return;
        }
        /**
         * SDK初始化的一些配置
         * 关于 EMOptions 可以参考官方的 API 文档
         * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1chat_1_1_e_m_options.html
         */
        EMOptions options = new EMOptions();
        // 设置Appkey，如果配置文件已经配置，这里可以不用设置
        // options.setAppKey("lzan13#hxsdkdemo");
        // 设置自动登录
        options.setAutoLogin(true);
        // 设置是否需要发送已读回执
        options.setRequireAck(true);
        // 设置是否需要发送回执，TODO 这个暂时有bug，上层收不到发送回执
        options.setRequireDeliveryAck(true);
        // 设置是否需要服务器收到消息确认
//        options.setRequireServerAck(true);
        // 收到好友申请是否自动同意，如果是自动同意就不会收到好友请求的回调，因为sdk会自动处理，默认为true
        options.setAcceptInvitationAlways(false);
        // 设置是否自动接收加群邀请，如果设置了当收到群邀请会自动同意加入
        options.setAutoAcceptGroupInvitation(false);
        // 设置（主动或被动）退出群组时，是否删除群聊聊天记录
        options.setDeleteMessagesAsExitGroup(false);
        // 设置是否允许聊天室的Owner 离开并删除聊天室的会话
        options.allowChatroomOwnerLeave(true);
        // 设置google GCM推送id，国内可以不用设置
        // options.setGCMNumber(MLConstants.ML_GCM_NUMBER);
        // 设置集成小米推送的appid和appkey
        // options.setMipushConfig(MLConstants.ML_MI_APP_ID, MLConstants.ML_MI_APP_KEY);

        // 调用初始化方法初始化sdk
        EMClient.getInstance().init(applicationContext, options);

        // 设置开启debug模式
        EMClient.getInstance().setDebugMode(true);

        // 设置初始化已经完成
        isInit = true;
    }

    /**
     * 根据Pid获取当前进程的名字，一般就是当前app的包名
     *
     * @param pid 进程的id
     * @return 返回进程的名字
     */
    private String getAppName(int pid) {
        String processName = null;
        ActivityManager activityManager = (ActivityManager) applicationContext.getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator i = list.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    // 根据进程的信息获取当前进程的名字
                    processName = info.processName;
                    // 返回当前进程名
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 没有匹配的项，返回为null
        return null;
    }




    /**
     * 配置极光推送
     */
    public void configJPush() {
        Log.e("BaseApplication","LauncherApplication配置极光推送");
        JPushInterface.setDebugMode (false);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init (this);
        L.e("SN:"+CommonUtils.getSN());
        JPushInterface.setAlias(this, 1, CommonUtils.getSN());
        JPushInterface.getAlias(this,1);
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
