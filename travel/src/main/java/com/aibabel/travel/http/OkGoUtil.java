package com.aibabel.travel.http;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.base.Request;

import java.util.Map;
import java.util.Set;

/**
 * 作者：SunSH on 2018/6/12 15:26
 * 功能：
 * 版本：1.0
 */
public class OkGoUtil{

    public static <T> void get(final String url, final ResponseCallback mCallback, final Class<T> cls, Map<String,String> params, final int actionId) {

        GetRequest<T> getRequest = OkGo.<T>get(url).tag(actionId);
        Set<String> keys = params.keySet();
        for(String key:keys){
            getRequest.params(key, params.get(key));
        }


        getRequest.execute(new JsonCallback<T>(cls) {
            @Override
            public void onStart(Request<T, ? extends Request> request) {
                super.onStart(request);
//                mCallback.onStar();
            }

            @Override
            public void onFinish() {
                super.onFinish();
//                mCallback.onFinish();
            }

            @Override
            public void onSuccess(Response<T> response) {
//                mCallback.onSuccess(actionId, (BaseModel)response.body());
            }

            @Override
            public void onError(Response<T> response) {
                super.onError(response);
                mCallback.onError();
            }
        });
    }

//    public static <T> void get(final String url, final ResponseCallback mCallback,final int actionId) {
//
//        Type type = new TypeToken<List<T>>() {
//        }.getType();
//
//        OkGo.<List<T>>get(url).params("", "").execute(new JsonCallback<List<T>>(type) {
//
//            @Override
//            public void onStart(Request<List<T>, ? extends Request> request) {
//                super.onStart(request);
//                mCallback.onStar();
//            }
//
//            @Override
//            public void onSuccess(Response<List<T>> response) {
//                mCallback.onSuccess(actionId, (BaseModel)response.body());
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                mCallback.onFinish();
//            }
//
//            @Override
//            public void onError(Response<List<T>> response) {
//                super.onError(response);
//                mCallback.onError();
//            }
//
//        });
//    }


//        public static <T> void get(final String url, final ResponseCallback mCallback, final Class<T> cls, final int actionId) {
//
//        OkGo.<String>get(url).execute(new StringCallback() {
//
//            @Override
//            public void onStart(Request<String, ? extends Request> request) {
//                super.onStart(request);
//                mCallback.onStar();
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                mCallback.onFinish();
//            }
//
//            @Override
//            public void onSuccess(Response<String> response) {
//                  mCallback.onSuccess(actionId,response);
//            }
//
//            @Override
//            public void onError(Response<String> response) {
//                super.onError(response);
//                mCallback.onError();
//            }
//        });
//    }

}
