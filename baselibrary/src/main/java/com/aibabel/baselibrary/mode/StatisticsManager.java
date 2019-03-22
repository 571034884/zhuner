package com.aibabel.baselibrary.mode;

import com.aibabel.baselibrary.impl.IStatistics;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class StatisticsManager implements IStatistics {
    List<String>  Statistics=new ArrayList();
    JSONArray allReadyUploadData=new JSONArray();
    private ArrayList<StatisticsModle> pathNodes=new ArrayList<>();

    public static StatisticsManager getInstance(){
        return StatisticsManagerHolder.manager;
    }

    public void addPath(String appName,String appVersion,JSONObject info){
        JSONObject pageUnitData=new JSONObject();


        int size=pathNodes.size();
        StatisticsModle modle=null;

        if (size==0){
            modle=new StatisticsModle();
            modle.an=appName;
            modle.av=appVersion;
            modle.c.put(info);
            
            pathNodes.add(modle);

        }else {
            modle=pathNodes.get(size-1);
            if (modle.an.equals(appName)){
                modle.c.put(info);
            }else{
                modle=new StatisticsModle();
                modle.an=appName;
                modle.av=appVersion;
                modle.c.put(info);
                pathNodes.add(modle);
            }

        }

    }
    public String createUploadData(String order_id){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("leaseID",order_id);
            jsonObject.put("version","3");

            jsonObject.put("localTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
            JSONArray array=new JSONArray();
            for (StatisticsModle modle:pathNodes){
                JSONObject object=new JSONObject();
                object.put("av",modle.av);
                object.put("an",modle.an);
                object.put("c",modle.c);
                array.put(object);

            }
            jsonObject.put("path",array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();

    }

//    private String createUploadData() throws JSONException {
//      if (jsonArraysList.size()>0){
//          for (StatisticsModle modle:jsonArraysList){
//              JSONObject jsonObject=new JSONObject();
//              jsonObject.put("leaseID","");
//              jsonObject.put("version","3");
////              jsonObject.put("localTime", TimeZone.getDefault().get);
//
//          }
//      }
//    }

    private static class  StatisticsManagerHolder {
        public  static  final  StatisticsManager manager=new StatisticsManager();

    }

}
