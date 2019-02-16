package com.aibabel.travel.http;

import com.aibabel.travel.bean.BaseModel;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * 作者：SunSH on 2018/6/12 15:31
 * 功能：
 * 版本：1.0
 */
//public interface ResponseCallback<T> {
//    void onStar(String method);
//
//    void onFinish();
//
//    void onSuccess(String method, T model);
//
//    void onSuccess(String method, List<T> list);
//
//    void onError(String method, Response<String> response);
//}
public interface ResponseCallback {
//    void onStar();
//
//    void onFinish();

    void onSuccess(String method, BaseModel result);

//    void onSuccess(String method, List<BaseModel> list);
//      void onSuccess(int actionId, String result);
//    void onSuccess(Response<List<BaseModel>> response);

    void onError();
}
