package com.aibabel.baselibrary.mode;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;

import com.aibabel.baselibrary.impl.IStatistics;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.baselibrary.utils.SharePrefUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;
import com.xuexiang.xipc.annotation.ClassName;
import com.xuexiang.xipc.annotation.MethodName;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

@ClassName("IStatistics")
public class StatisticsManager implements IStatistics {
    private static final Uri CITY_URI = Uri.parse("content://com.aibabel.locationservice.provider.AibabelProvider/aibabel_location");
    private double latitude;
    private double longitude;
    private long locationTime = System.currentTimeMillis();
    List<String> Statistics = new ArrayList();
    JSONArray allReadyUploadData = new JSONArray();
    private ArrayList<StatisticsModle> pathNodes = new ArrayList<>();
    private JSONArray independentEventArray = new JSONArray();

    public static StatisticsManager getInstance() {
        return StatisticsManagerHolder.manager;
    }

    @MethodName("addPath")
    public void addPath(String appName, String appVersion, String info) {
        Log.e("addPath", appName + "  " + appVersion + " " + info);
//
        try {
            JSONObject infoObject = new JSONObject(info);
            int size = pathNodes.size();
            StatisticsModle modle = null;

            if (size == 0) {
                modle = new StatisticsModle();
                modle.an = appName;
                modle.av = appVersion;
                modle.c.put(infoObject);
                pathNodes.add(modle);
            } else {
                modle = pathNodes.get(size - 1);
                if (modle.an.equals(appName)) {
                    modle.c.put(infoObject);
                } else {
                    modle = new StatisticsModle();
                    modle.an = appName;
                    modle.av = appVersion;
                    modle.c.put(infoObject);
                    pathNodes.add(modle);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 添加独立的、不依赖于任何界面的事件统计、通常用于统计Service中的事件
     *
     * @param id         事件id
     * @param parameters 事件参数
     */
    @Override
    @MethodName("addIndependentEvent")
    public void addIndependentEvent(String id, HashMap<String, Serializable> parameters) {


        JSONObject eventObject = new JSONObject();
        try {
            eventObject.put("eid", id);
            eventObject.put("et", System.currentTimeMillis());
            if (parameters != null && parameters.size() > 0) {
                JSONObject parametersJson = new JSONObject();
                for (Map.Entry<String, Serializable> entry : parameters.entrySet()) {

                    parametersJson.put(entry.getKey(), entry.getValue());
                }
                eventObject.put("p", parametersJson.toString());
            }
            independentEventArray.put(eventObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /**
     * 获取当前城市
     *
     * @param context
     * @return
     */
    public void location(Context context) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - locationTime < 15 * 60 * 100) {
            return;
        }
        locationTime = currentTime;
        Cursor cursor = context.getContentResolver().query(CITY_URI, null, null, null, null);

        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int latitudeIndex = cursor.getColumnIndex("latitude");
                int longitudeIndex = cursor.getColumnIndex("longitude");
                latitude = cursor.getDouble(latitudeIndex);
                longitude = cursor.getDouble(longitudeIndex);


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor)
                cursor.close();
        }

    }


    @MethodName("createUploadData")
    public String createUploadData(String order_id) {
        if (pathNodes.size()==0){
            return null;
        }
        if (pathNodes.size()==1&& pathNodes.get(0).c.length()==0){
          return null;
        }
        String resultStr = null;
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

            if (independentEventArray != null && independentEventArray.length() > 0) {
                jsonObject.put("events", independentEventArray);
            }


            resultStr = StringEscapeUtils.unescapeJava(jsonObject.toString());
            Log.e("createUploadData", resultStr);
            independentEventArray = new JSONArray();
            pathNodes.clear();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultStr;

    }


    public void uplaodData(Context context, String order_id) {
        String newData = createUploadData(order_id);
        if (TextUtils.isEmpty(newData)){
            return;
        }
        location(context);


        final String allData = getData(context, newData);
//        if (allData.length()<200){
//            try {
//                JSONArray array=new JSONArray(newData);
//                if (array.length()==1&&array.getJSONObject(0).optJSONArray("path").length()==0){
//                    return;
//                }
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }


        Log.e("allPath", allData);
        String url = "http://39.107.238.111:7001";
//        String url=CommonUtils.getTimerType()==0?"http://abroad.api.joner.aibabel.cn:7001":"http://api.joner.aibabel.cn:7001";
        PostRequest<String> postRequest = OkGo.<String>post(url + "/v2/ddot/JonerLogPush").tag("JonerLogPush");

        postRequest.params("sv", Build.DISPLAY);
        postRequest.params("sn", CommonUtils.getSN());
        postRequest.params("sl", CommonUtils.getLocalLanguage());
        postRequest.params("av", CommonUtils.getDeviceInfo());
        postRequest.params("no", CommonUtils.getRandom() + "");
        postRequest.params("lat", latitude + "");
        postRequest.params("lng", longitude + "");
        postRequest.params("data", allData);
        postRequest.execute(new StringCallback() {
            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);

            }

            @Override
            public void onSuccess(Response<String> response) {

                Log.e("onSuccess", response.body());

                try {
                    JSONObject object = new JSONObject(response.body());
                    if (!object.has("code") || !object.getString("code").equals("1")) {
                        saveData(context, allData);
                    } else {
                        saveData(context, "");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                saveData(context, allData);

            }

            @Override
            public void onError(Response<String> response) {
                Log.e("onError", response.code() + "");
                saveData(context, allData);
            }

            @Override
            public void onFinish() {
                super.onFinish();

            }
        });


    }

    private void saveData(Context context, String data) {
        new FileUtil(context).save(data);
    }


    private String getData(Context context, String data) {

        FileUtil fileUtil = new FileUtil(context);
        String savedData = fileUtil.load();
        JSONArray array = null;
        try {
            if (!TextUtils.isEmpty(savedData)){
                array=new JSONArray(savedData);
            }else{
                array=new JSONArray();
            }
            array.put(new JSONObject(data));
        }catch (Exception e){
            e.printStackTrace();
        }
        if (array!=null){
            return StringEscapeUtils.unescapeJava(array.toString());
        }else{
            return "";
        }

//        if (!TextUtils.isEmpty(savedData)) {
//            try {
//
//                if (savedData.startsWith("[")) {
//                    array = new JSONArray(savedData);
//                } else {
//                    array = new JSONArray();
//                    array.put(new JSONObject(savedData));
//                }
//
//                array.put(new JSONObject(data));
//                String result=StringEscapeUtils.unescapeJava(array.toString());
//
//                return result;
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//                return data;
//            }
//        } else {
//            return data;
//        }

    }

    /**
     * @param input 需要压缩的字符串
     * @return 压缩后的字符串
     * @throws IOException IO
     */
    public static String compress(String input) throws IOException {
        if (input == null || input.length() == 0) {
            return input;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzipOs = new GZIPOutputStream(out);
        gzipOs.write(input.getBytes());
        gzipOs.close();
        return out.toString("ISO-8859-1");
    }
    private static class StatisticsManagerHolder {
        public static final StatisticsManager manager = new StatisticsManager();

    }


    public static class FileUtil {
        private Context context = null;

        public FileUtil(Context context) {
            this.context = context;
        }

        public void save(String data) {

            FileOutputStream out = null;
            BufferedWriter writer = null;
            try {
                //设置文件名称，以及存储方式
                out = context.openFileOutput("data", Context.MODE_PRIVATE);
                //创建一个OutputStreamWriter对象，传入BufferedWriter的构造器中
                writer = new BufferedWriter(new OutputStreamWriter(out));
                //向文件中写入数据
                writer.write(data);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public String load() {
            FileInputStream in = null;
            BufferedReader reader = null;
            StringBuilder content = new StringBuilder();
            try {
                //设置将要打开的存储文件名称
                in = context.openFileInput("data");
                //FileInputStream -> InputStreamReader ->BufferedReader
                reader = new BufferedReader(new InputStreamReader(in));
                String line = new String();
                //读取每一行数据，并追加到StringBuilder对象中，直到结束
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return content.toString();
        }
    }

}
