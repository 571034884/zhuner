package com.aibabel.weather.utils;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：SunSH on 2018/6/1 19:04
 * 功能：
 * 版本：1.0
 */
public class MyOkgoUtil {

    private static String TAG = "MyOkgoUtil";

    public static List<Integer> list = new ArrayList<>();

    public interface OnListener {
        void onSuccess(Response<String> response, int time, boolean refresh);

        void onError(Response<String> response, int time);
    }

    /**
     * @param url
     * @param time 请求次数
     */
    public static void getWeather(String url, final int time, final OnListener listener) {
        list.add(time);
        OkGo.<String>get(url).tag(time)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e(TAG, response.body());
                        list.remove(new Integer(time));
                        listener.onSuccess(response, time, list.size() > 0 ? false : true);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e(TAG, "onError");
                        list.remove(new Integer(time));
                        listener.onError(response, time);
                    }
                });
    }
}
