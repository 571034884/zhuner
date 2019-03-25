package com.aibabel.baselibrary.base;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aibabel.baselibrary.impl.IStatistics;
import com.aibabel.baselibrary.utils.DeviceUtils;
import com.xuexiang.xipc.XIPC;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class StatisticsBaseActivity extends AppCompatActivity {
    protected static String notifyId;   //当前路径下通知的id
    private boolean isFromNotify; //是否有通知打开
    private JSONArray eventsArray=null;
    private JSONObject pageObject=null;
    protected boolean isOpenFromHardwareButton=true; //是否通过物理按键唤起
    private JSONObject pageParameters;
    protected  static String appName, appVersion;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

            } catch (Exception e) {
                e.printStackTrace();
            }
            XIPC.connectApp(this,"com.aibabel.menu");
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
        if (DeviceUtils.getSystem()== DeviceUtils.System.PRO_LEASE){

            try {
                IStatistics statisticsManager=XIPC.getInstance(IStatistics.class);
                pageObject.put("ot", System.currentTimeMillis());
                pageObject.put("p",pageParameters);

                if (eventsArray!=null&&eventsArray.length()>0)
                    pageObject.put("e",eventsArray);
                String order=statisticsManager.createUploadData("iiiidd");
                statisticsManager.addPath(appName,appVersion,pageObject);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (System.currentTimeMillis()-pageObject.optLong("it")>500){
            addPathToStatisticsManager();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (DeviceUtils.getSystem()== DeviceUtils.System.PRO_LEASE){
          XIPC.disconnect(this);
        }
    }
}
