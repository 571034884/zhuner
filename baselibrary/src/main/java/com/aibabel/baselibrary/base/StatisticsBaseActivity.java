package com.aibabel.baselibrary.base;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.baselibrary.utils.DeviceUtils;
import com.xuexiang.xipc.XIPC;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据统计基础类
 */
public class StatisticsBaseActivity extends AppCompatActivity {
    public static final String NOTIFY_ID="notiytId";
    public static final String HARDWARE_BUTTON="HardwareButton";
    protected  String notifyId;   //当前路径下通知的id
    private JSONArray eventsArray=null;
    private JSONObject pageObject=null;
    protected boolean isOpenFromHardwareButton=true; //是否通过物理按键唤起
    private JSONObject pageParameters;
    protected  static String appName, appVersion;

    private boolean callStop=false;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


         notifyId=getIntent().getStringExtra(NOTIFY_ID);
         Log.e("notiytId Intent",""+notifyId);

        isOpenFromHardwareButton=getIntent().getBooleanExtra(HARDWARE_BUTTON,true);


        if (DeviceUtils.getSystem()== DeviceUtils.System.PRO_LEASE){
            try {
                if (appName==null){
                    appName=getPackageName().split("\\.")[2];
                }
                if (appVersion==null){
                    PackageInfo info=getPackageManager().getPackageInfo(getPackageName(),0);
                    appVersion=info.versionName;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        initPageObject();


    }

    private void initPageObject(){
        try {
            pageObject=new JSONObject();
            pageObject.put("pn",getClass().getSimpleName());
            pageObject.put("it",System.currentTimeMillis());
            if (!TextUtils.isEmpty(notifyId)){
                pageObject.put("notify",notifyId);
            }
//            if (isOpenFromHardwareButton&&(appName.equals("translate")||appName.equals("ocr")||appName.equals("speech"))){
//                pageObject.put("h",true);
//            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();


        if (callStop&&DeviceUtils.getSystem()== DeviceUtils.System.PRO_LEASE){
           initPageObject();

        }

        callStop=false;
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

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (pageObject.has("ot"))
            pageObject.remove("ot");
            pageObject.put("ot",System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onStop() {
        try {
            if (pageObject.optLong("ot") - pageObject.optLong("it") > 500){
                pageObject.put("p",pageParameters);

                if (eventsArray!=null&&eventsArray.length()>0){
                    pageObject.put("e",eventsArray);

                }

                eventsArray=new JSONArray();
                Intent intent = new Intent();
                intent.putExtra("av",appVersion);
                intent.putExtra("an",appName);
                intent.putExtra("pageInfo",pageObject.toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction("com.aibabel.statistics");
                sendBroadcast(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        callStop=true;

        super.onStop();
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
                    eventObject.put("p",parametersJson);


                }
                Log.e("addStatisticsEvent", eventObject.toString());
                if (eventsArray==null){
                    eventsArray=new JSONArray();
                }
                eventsArray.put(eventObject);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



    }



    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (!TextUtils.isEmpty(notifyId)){
            intent.putExtra(NOTIFY_ID,notifyId);
        }
        super.startActivityForResult(intent, requestCode);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!getPackageName().equals("com.aibabel.menu")&&DeviceUtils.getSystem()== DeviceUtils.System.PRO_LEASE){
            XIPC.disconnect(this);
        }

    }
}
