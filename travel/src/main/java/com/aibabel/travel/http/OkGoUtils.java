package com.aibabel.travel.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.aibabel.travel.R;
import com.aibabel.travel.app.UrlConfig;
import com.aibabel.travel.bean.BaseModel;
import com.aibabel.travel.utils.CommonUtils;
import com.aibabel.travel.utils.Constant;
import com.aibabel.travel.utils.ContentProviderUtil;
import com.aibabel.travel.utils.FastJsonUtil;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.base.Request;

import java.util.Map;

/**
 * 作者：SunSH on 2018/6/12 15:26
 * 功能：
 * 版本：1.0
 */
public class OkGoUtils {


    public final static int SUCCESS = 1;


    /**
     * @param <T>
     * @param context    dialog中用到
     * @param method     方法名
     * @param param      参数
     * @param cls        结果要解析的对象
     * @param mCallback  回调
     */
    public static <T> void get(final Context context, final String method, Map<String, String> param, final Class<T> cls, final ResponseCallback mCallback) {

        GetRequest<String> getRequest = OkGo.<String>get(ContentProviderUtil.getHost(context)+method).tag(method);
//        GetRequest<String> getRequest = OkGo.<String>get("http://192.168.50.7:7001/v1/tourguide/"+method).tag(method);
        getRequest.headers("Connection","close");
        getRequest.headers("charset","utf-8");
        getRequest.headers("Content-Type","text/plain");
//        getRequest.params("cmd", method);
        for (Map.Entry<String, String> entry : param.entrySet()) {
            getRequest.params(entry.getKey(), entry.getValue());
        }

        getRequest.params("sn", CommonUtils.getSN());
        getRequest.params("sl",CommonUtils.getLocal(context));
        getRequest.params("no",CommonUtils.getRandom());

        Log.d("OkGoUtils:",getRequest.getUrl());
        getRequest.execute(new DialogCallBack(context, true, method) {

            @Override
            public void onSuccess(Response<String> response) {
                Log.d("response",response.body().toString());
                BaseModel baseModel = (BaseModel) FastJsonUtil.changeJsonToBean(response.body(), cls);
                if(null==baseModel){
                    Toast.makeText(context, context.getResources().getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(null!=baseModel&&baseModel.getCode()==SUCCESS){
                    mCallback.onSuccess(method,baseModel);
                }else{
                    Toast.makeText(context, baseModel.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                mCallback.onError();
            }

        });

    }
    public static <T> void get1(final Context context, final String method, Map<String, String> param, final Class<T> cls, final ResponseCallback mCallback) {

        GetRequest<String> getRequest = OkGo.<String>get(ContentProviderUtil.getHost(context)+method).tag(method);
//        GetRequest<String> getRequest = OkGo.<String>get("http://192.168.50.7:7001/v1/tourguide/"+method).tag(method);
        getRequest.headers("Connection","close");
        getRequest.headers("charset","utf-8");
        getRequest.headers("Content-Type","text/plain");
//        getRequest.params("cmd", method);
        for (Map.Entry<String, String> entry : param.entrySet()) {
            getRequest.params(entry.getKey(), entry.getValue());
        }

        getRequest.params("sn", CommonUtils.getSN());
        getRequest.params("sl",CommonUtils.getLocal(context));
        getRequest.params("no",CommonUtils.getRandom());

        Log.d("OkGoUtils:",getRequest.getUrl());
        getRequest.execute(new DialogCallBack(context, false, method) {

            @Override
            public void onSuccess(Response<String> response) {
                Log.d("response",response.body().toString());
                BaseModel baseModel = (BaseModel) FastJsonUtil.changeJsonToBean(response.body(), cls);
                if(null==baseModel){
                    Toast.makeText(context, context.getResources().getString(R.string.try_again), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(null!=baseModel&&baseModel.getCode()==SUCCESS){
                    mCallback.onSuccess(method,baseModel);
                }else{
                    Toast.makeText(context, baseModel.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                mCallback.onError();
            }

        });

    }

}
