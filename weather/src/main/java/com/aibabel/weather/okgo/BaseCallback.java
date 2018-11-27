package com.aibabel.weather.okgo;

/**
 * 作者：SunSH on 2018/6/12 15:31
 * 功能：
 * 版本：1.0
 */
public interface BaseCallback {

    void onSuccess(String method, BaseBean model);

    void onError(String method, String message);
}
