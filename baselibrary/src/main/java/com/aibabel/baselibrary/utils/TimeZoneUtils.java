package com.aibabel.baselibrary.utils;

import android.app.AlarmManager;
import android.content.Context;
import android.text.TextUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;

public class TimeZoneUtils {
    public static boolean timeZoneChecked=false;
    /**
     *  获取国家编码
     * @param context
     * @return
     */
    public static String getCountyName(Context context) {
        String countryName = "";
        try {
            String address = "http://ip.taobao.com/service/getIpInfo2.php?ip=myip";
            URL url = new URL(address);

            //  URLConnection htpurl=url.openConnection();

            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.7 Safari/537.36"); //设置浏览器ua 保证不出现503

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();

                // 将流转化为字符串
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in));

                String tmpString = "";
                StringBuilder retJSON = new StringBuilder();
                while ((tmpString = reader.readLine()) != null) {
                    retJSON.append(tmpString + "\n");
                }

                JSONObject jsonObject = new JSONObject(retJSON.toString());
                String code = jsonObject.getString("code");


                if (code.equals("0")) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    countryName = data.getString("country");


                } else {
                    countryName = "";

                }
            } else {
                countryName = "";

            }
        } catch (Exception e) {
            countryName = "";

        }
        return countryName;
    }



    public static  void startChecksIPThread(Context context){
        if (timeZoneChecked) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ip=getCountyName(context);
                if (!TextUtils.isEmpty(ip)){
                       timeZoneChecked=true;
                    if (ip.contains("中国"))   {
                        String chinaTimeZoneId="Asia/Shanghai";
                        TimeZone timeZone = TimeZone.getDefault();
                        String id = timeZone.getID(); //获取时区id
                        if (!id.equals(chinaTimeZoneId)){
                            AlarmManager alarm = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                            alarm.setTimeZone(chinaTimeZoneId);//默认时区的id

                        }

                    }
                }



            }
        }).start();
    }



}
