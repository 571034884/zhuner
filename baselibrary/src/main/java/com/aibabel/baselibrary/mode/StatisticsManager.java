package com.aibabel.baselibrary.mode;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.aibabel.baselibrary.impl.IStatistics;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.baselibrary.utils.SharePrefUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;
import com.xuexiang.xipc.annotation.ClassName;
import com.xuexiang.xipc.annotation.MethodName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ClassName("IStatistics")
public class StatisticsManager implements IStatistics {
    private static final Uri CITY_URI = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");
    private double latitude;
    private double longitude;
    private  long locationTime=System.currentTimeMillis();
    List<String> Statistics = new ArrayList();
    JSONArray allReadyUploadData = new JSONArray();
    private ArrayList<StatisticsModle> pathNodes = new ArrayList<>();

    public static StatisticsManager getInstance() {
        return StatisticsManagerHolder.manager;
    }

    @MethodName("addPath")
    public void addPath(String appName, String appVersion, JSONObject info) {

        int size = pathNodes.size();
        StatisticsModle modle = null;

        if (size == 0) {
            modle = new StatisticsModle();
            modle.an = appName;
            modle.av = appVersion;
            modle.c.put(info);

            pathNodes.add(modle);

        } else {
            modle = pathNodes.get(size - 1);
            if (modle.an.equals(appName)) {
                modle.c.put(info);
            } else {
                modle = new StatisticsModle();
                modle.an = appName;
                modle.av = appVersion;
                modle.c.put(info);
                pathNodes.add(modle);
            }

        }


    }

    /**
     *  通知栏点击事件统计
     * @param id
     * @param parameters
     */
    @Override
    @MethodName("addNotify")
    public void addNotify(String id, HashMap<String, Serializable> parameters) {
         JSONObject pageObject=new JSONObject();
         JSONObject eventObject=new JSONObject();
        try {
            eventObject.put("eid",id);
            eventObject.put("et",System.currentTimeMillis());
            if (parameters!=null&&parameters.size()>0){
                JSONObject parametersJson=new JSONObject();
                for (Map.Entry<String, Serializable> entry : parameters.entrySet()){

                    parametersJson.put(entry.getKey(),entry.getValue());
                }
                eventObject.put("p",parametersJson.toString());
            }
           pageObject.put("e",new JSONArray().put(eventObject));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        addPath("notify","1.0",pageObject);


    }

    /**
     * 获取当前城市
     *
     * @param context
     * @return
     */
    public  void location(Context context) {
        long currentTime=System.currentTimeMillis();
        if (currentTime-locationTime<15*60*100){
            return;
        }
        locationTime=currentTime;
        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);

        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int latitudeIndex = cursor.getColumnIndex("latitude");
                int longitudeIndex = cursor.getColumnIndex("longitude");
                latitude = cursor.getDouble(latitudeIndex);
                longitude = cursor.getDouble(longitudeIndex);


            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if (null != cursor)
                cursor.close();
        }

    }


    @MethodName("createUploadData")
    public String createUploadData(String order_id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("leaseID", order_id);
            jsonObject.put("version", "3");

            jsonObject.put("localTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
            JSONArray array = new JSONArray();
            for (StatisticsModle modle : pathNodes) {
                JSONObject object = new JSONObject();
                object.put("av", modle.av);
                object.put("an", modle.an);
                object.put("c", modle.c);
                array.put(object);

            }
            jsonObject.put("path", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();

    }





    public void uplaodData( Context context,String order_id) {
        location(context);
        String newData=createUploadData(order_id);
        final String allData=getData(context,newData);

        String url="http://39.107.238.111:7001";
//        String url=CommonUtils.getTimerType()==0?"http://abroad.api.joner.aibabel.cn:7001":"http://api.joner.aibabel.cn:7001";
        PostRequest<String> postRequest = OkGo.<String>post(url+"/v2/ddot/JonerLogPush").tag("JonerLogPush");

        postRequest.params("sv", Build.DISPLAY);
        postRequest.params("sn", CommonUtils.getSN());
        postRequest.params("sl", CommonUtils.getLocalLanguage());
        postRequest.params("av", CommonUtils.getDeviceInfo());
        postRequest.params("no", CommonUtils.getRandom() + "");
        postRequest.params("lat", latitude + "");
        postRequest.params("lng", longitude + "");
        postRequest.params("data",allData);
        postRequest.execute(new StringCallback() {
            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);

            }

            @Override
            public void onSuccess(Response<String> response) {

                Log.e("onSuccess",response.body());
                try {
                    JSONObject object=new JSONObject(response.body());
                    if (!object.has("code")||!object.getString("code").equals("1")){
                         saveData(context,allData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Response<String> response) {
                Log.e("onError",response.code()+"");
                saveData(context,allData);
            }

            @Override
            public void onFinish() {
                super.onFinish();

            }
        });


    }
    private void saveData(Context context,String data)  {
        SharePrefUtil.put(context,"statisticsData",data);
    }


    private String getData(Context context,String data)  {
       String savedData= SharePrefUtil.getString(context,"statisticsData","");
        JSONArray jsonArray=null;
        try {
            if (!TextUtils.isEmpty(savedData)){
                jsonArray= new JSONArray(savedData);
            }else{
                jsonArray= new JSONArray();
            }
            jsonArray.put(data);


        }catch (Exception e){
            e.printStackTrace();
        }




       return jsonArray.toString();
    }

    private static class StatisticsManagerHolder {
        public static final StatisticsManager manager = new StatisticsManager();

    }

}
