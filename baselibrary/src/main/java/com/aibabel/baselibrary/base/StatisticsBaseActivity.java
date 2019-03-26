package com.aibabel.baselibrary.base;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.aibabel.baselibrary.impl.IStatistics;
import com.aibabel.baselibrary.mode.StatisticsManager;
import com.aibabel.baselibrary.utils.DeviceUtils;
import com.xuexiang.xipc.XIPC;
import com.xuexiang.xipc.core.channel.IPCListener;
import com.xuexiang.xipc.core.channel.IPCService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据统计基础类
 */
public class StatisticsBaseActivity extends AppCompatActivity {
    public static final String NOTIFY_ID="notiytId";
    public static final String HARDWARE_BUTTON="HardwareButton";
    protected static String notifyId;   //当前路径下通知的id
    private JSONArray eventsArray=null;
    private JSONObject pageObject=null;
    protected boolean isOpenFromHardwareButton=true; //是否通过物理按键唤起
    private JSONObject pageParameters;
    protected  static String appName, appVersion;
    public  IStatistics statisticsManager;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         connectXIPC();
        notifyId=getIntent().getStringExtra(NOTIFY_ID);

        isOpenFromHardwareButton=getIntent().getBooleanExtra(HARDWARE_BUTTON,true);

        getIntent().putExtra("HardwareButton",false);
        if (DeviceUtils.getSystem()== DeviceUtils.System.PRO_LEASE){
            try {
                if (appName==null){
                    appName=getPackageName().split("\\.")[2];
                }
                if (appVersion==null){
                    PackageInfo info=getPackageManager().getPackageInfo(getPackageName(),0);
                    appVersion=info.versionName;
                }
                pageObject=new JSONObject();
                pageObject.put("pn",getClass().getSimpleName());

                if (!TextUtils.isEmpty(notifyId)){
                    pageObject.put("notify",notifyId);
                }
                if (isOpenFromHardwareButton){
                    pageObject.put("h",true);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }



    }
    private void connectXIPC(){
        if (statisticsManager!=null)
            return;
        if (!getPackageName().equals("com.aibabel.menu")&&DeviceUtils.getSystem()== DeviceUtils.System.PRO_LEASE){
            XIPC.setIPCListener(new IPCListener() {
                @Override
                public void onIPCConnected(Class<? extends IPCService> service) {
                    statisticsManager=XIPC.getInstance(IStatistics.class);

//                    Toast.makeText(getApplicationContext(),"IPC服务已绑定！",Toast.LENGTH_LONG).show();
                }
            });
            XIPC.connectApp(this,"com.aibabel.menu");

        }
        else {
            statisticsManager= StatisticsManager.getInstance();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (DeviceUtils.getSystem()== DeviceUtils.System.PRO_LEASE){
            try {
                pageObject.remove("it");
                pageObject.put("it",System.currentTimeMillis());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }


    /**
     *  添加页面参数
     * @param key  参数key
     * @param value  参数值
     */

    public void  addPageParameters(String key ,Serializable value){

        if (DeviceUtils.getSystem()== DeviceUtils.System.PRO_LEASE){
            if (pageParameters==null){
                pageParameters=new JSONObject();
            }
            try {
                pageParameters.put(key,value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



    }
    /**
     * 跨进程添加统计数据
     */
    private void addPathToStatisticsManager(){
        if (DeviceUtils.getSystem()== DeviceUtils.System.PRO_LEASE&&statisticsManager!=null){

            try {



                pageObject.put("ot", System.currentTimeMillis());
                pageObject.put("p",pageParameters);

                if (eventsArray!=null&&eventsArray.length()>0)
                    pageObject.put("e",eventsArray);

                statisticsManager.addPath(appName,appVersion,pageObject);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (System.currentTimeMillis() - pageObject.optLong("it") > 500) {
                addPathToStatisticsManager();
                if (pageObject.has("h")) {
                    pageObject.remove("h");
                }
            }
        }catch (Exception e){

        }


    }

    /**
     * 添加自定义事件
     * @param eventId   自定义事件id
     * @param parameters  自定义事件参数
     */
    public void addStatisticsEvent(String eventId, HashMap<String, Serializable> parameters){
        if (DeviceUtils.getSystem()== DeviceUtils.System.PRO_LEASE){
            JSONObject eventObject=new JSONObject();
            try {
                eventObject.put("eid",eventId);
                eventObject.put("et",System.currentTimeMillis());
                if (parameters!=null&&parameters.size()>0){
                    JSONObject parametersJson=new JSONObject();
                    for (Map.Entry<String, Serializable> entry : parameters.entrySet()){

                        parametersJson.put(entry.getKey(),entry.getValue());
                    }
                    eventObject.put("p",parametersJson.toString());

                }
                if (eventsArray==null){
                    eventsArray=new JSONArray();
                }
                eventsArray.put(eventObject);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



    }

    /**
     *  添加独立的、不依赖于任何界面的事件统计、通常用于统计Service中的事件
     * @param eventId
     * @param parameters
     */
    public void addIndependentEvent(String eventId, HashMap<String, Serializable> parameters){


    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (!TextUtils.isEmpty("")){
            intent.putExtra(NOTIFY_ID,notifyId);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (!TextUtils.isEmpty("")){
            intent.putExtra(NOTIFY_ID,notifyId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!getPackageName().equals("com.aibabel.menu")&&DeviceUtils.getSystem()== DeviceUtils.System.PRO_LEASE){
            XIPC.disconnect(this);
        }

    }
}
