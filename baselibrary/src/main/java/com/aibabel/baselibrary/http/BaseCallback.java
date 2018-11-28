package com.aibabel.baselibrary.http;

/**
 * 作者：SunSH on 2018/6/12 15:31
 * 功能：
 * 版本：1.0
 */
public interface BaseCallback<T extends BaseBean> {

    void onSuccess(String method, T model,String resoureJson);

    void onError(String method, String message,String resoureJson);
}
