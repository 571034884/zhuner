/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aibabel.baselibrary.mode;

import android.util.Log;

import com.aibabel.baselibrary.impl.IDataManager;
import com.aibabel.baselibrary.impl.IServerManager;
import com.aibabel.baselibrary.utils.SharePrefUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.tencent.mmkv.MMKV;
import com.xuexiang.xipc.annotation.ClassName;
import com.xuexiang.xipc.annotation.MethodName;
import com.xuexiang.xipc.annotation.Singleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static com.xuexiang.xipc.XIPC.getContext;

/**
 * 单例类。进行存储 获取
 */
@ClassName("ServerManager")
public class ServerManager implements IServerManager {

    private static volatile ServerManager sInstance = null;

    private String s;
    private boolean b;
    private int i;
    private long l;
    private float f;


    private ServerManager() {
        s = "";
        b = false;
        i = 0;
        l = 0;
        f = 0;
    }

    @Singleton
    public static ServerManager getInstance() {
        if (sInstance == null) {
            synchronized (ServerManager.class) {
                if (sInstance == null) {
                    sInstance = new ServerManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 流程思维
     * 1.利用传递过来的key进行 get，获取数据
     * 1.有数据
     * 获取数据转换成集合
     * 集合中元素 = 1
     * 1.timer - get(0) <= XXX（限定值）
     * timer合并到集合中，进行存储
     * 2.timer - get(0) >= XXX（限定值）
     * 首先清空集合，timer存入集合，进行存储
     * 集合中元素 = 2
     * 1.timer - get(0) <= XXX（限定值）
     * 集合清空，进行存储，判断时区，置换服务器域名
     * 2.timer - get(0) >= XXX（限定值）
     * 首先清空集合，timer存入集合，进行存储
     * 2.无数据
     * list集合形式{long}.toString();
     * 进行sp存储。
     *
     * @param key   键
     * @param timer 时间戳
     */
    @MethodName("setSaveServerError")
    @Override
    public void setSaveServerError(String key, long timer) {


    }

    /**
     * 根据ping差值 进行换算。
     * <p>
     * <p>
     * <p>
     * 1.获取所有服务器域名
     * 当前key
     * default_com_aibabel_alliedclock_joner
     * key去掉default
     * _com_aibabel_alliedclock_joner
     * <p>
     * 2.利用    _com_aibabel_alliedclock_joner   进行获取所有服务器域名
     * 3.利用,分割成字符串
     * [0]国外
     * [1]国内
     * [2]国内备用
     * 4.ping服务器，进行平均值判断
     *
     * @param key
     */
    @MethodName("setPingServerError")
    @Override
    public void setPingServerError(String key) {
        String allKey = key.replaceAll("default", "");//获取当前APP key
        String keyServers = SharePrefUtil.getString(getContext(), allKey, "");//获取当前APP所有域名
        String[] allServer = keyServers.split(",");//切割成字符串数组
        int type = getTimerType();//获取国内or国外
        switch (type) {
            case 1:
                //国内的情况下
                Log.e("SERVICE_FUWU", "国内有备用服务器:" + allServer[1] + "," + allServer[2]);
                //判断国内是否有备用服务器
                if (allServer.length == 3) {
                    //国内有备用服务器[1] [2]
                    String[] oneServer = allServer[1].split(":");
                    String[] twoServer = allServer[2].split(":");
                    boolean s = pingServer(oneServer[0], twoServer[0]);
                    if (s){
                        MMKV mmkv = MMKV.defaultMMKV();
                        Log.e("SERVICE_FUWU", "当前服务器11222233333："+mmkv.decodeString(key));
                        mmkv.encode(key,allServer[1]);
                        Log.e("SERVICE_FUWU", "当前服务器："+mmkv.decodeString(key));
                    }else{
                        MMKV mmkv = MMKV.defaultMMKV();
                        Log.e("SERVICE_FUWU", "当前服务器44445555666："+mmkv.decodeString(key));
                        mmkv.encode(key,allServer[2]);
                        Log.e("SERVICE_FUWU", "当前服务器11222233333："+mmkv.decodeString(key));
                    }
                } else if (allServer.length == 2) {
                    Log.e("SERVICE_FUWU", "国内没有备用服务器");
                }
                break;
            case 0:
                Log.e("SERVICE_FUWU", "国外没有备用服务器");
                break;
        }
    }

    /**
     * 获取当前时区
     *
     * @return 1国内服务器，0国外服务器
     */
    private int getTimerType() {
        try {
            String timerID = TimeZone.getDefault().getID();

            if (timerID.equals("Asia/Shanghai")) {
                Log.e("SERVICE_FUWU", "时区:" + 1);
                return 1;
            } else {
                Log.e("SERVICE_FUWU", "时区:" + 0);
                return 0;
            }
        } catch (Exception e) {
            Log.e("SERVICE_FUWU", "获取时区报错");
        }
        return 0;
    }

    /**
     * 进行ping 开始服务器域名
     *
     * @param oneServer 国内服务器
     * @param twoServer 国内备用服务器
     */
    private boolean pingServer(String oneServer, String twoServer) {
        Log.e("SERVICE_FUWU", "正式：" + oneServer + ",备用：" + twoServer);
        int ones = pingTimer(oneServer);
        int twos = pingTimer(twoServer);
        Log.e("SERVICE_FUWU", "正式：" + ones + ",备用：" + twos);
        if (ones != twos){
            if (ones > twos){
                Log.e("SERVICE_FUWU", "false");
                return false;//切换到备用服务器
            }else{
                Log.e("SERVICE_FUWU", "true");
                return true;//切换到正式服务器
            }
        }
        return false;
    }

    /**
     * 开启ping
     *
     * @param ips 域名
     * @return 1s 成功0    超时1
     */
    private int ping(String ips) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("ping -w 3 " + ips);
            return ipProcess.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private int pingTimer(String ips) {
        int s = 0;
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("/system/bin/ping -w 2 " + ips);
            BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = new String();
            while ((str = buf.readLine()) != null) {
                if (str.contains("avg")) {
                    int i = str.indexOf("/", 20);
                    int j = str.indexOf(".", i);
                    s = Integer.parseInt(str.substring(i + 1, j));
                }
            }
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
