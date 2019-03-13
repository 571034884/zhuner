package com.aibabel.surfinternet.okgo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.aibabel.surfinternet.bean.Constans;
import com.aibabel.surfinternet.bean.PaymentBean;
import com.aibabel.surfinternet.utils.CommonUtils;
import com.aibabel.surfinternet.utils.FastJsonUtil;
import com.aibabel.surfinternet.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：SunSH on 2018/6/12 15:26
 * 功能：
 * 版本：1.0
 */
public class OkGoUtil {

    public static String TAG = "OkGoUtil";
    public static int SUCCESS = 1;

    /**
     * @param <T>
     * @param context   dialog中用到
     * @param method    方法名
     * @param param     参数
     * @param cls       结果要解析的对象
     * @param mCallback 回调
     */
    public static <T> void get(final Context context, final String method, Map<String, String> param, final Class<T> cls, final BaseCallback mCallback) {

        GetRequest<String> getRequest = OkGo.<String>get(Constans.HOST_XW + Constans.METHOD_GROUP_XW + method).tag(method);

        for (Map.Entry<String, String> entry : param.entrySet()) {
            getRequest.params(entry.getKey(), entry.getValue());
        }
        Log.e("LK---001","url = "+Constans.HOST_XW + Constans.METHOD_GROUP_XW + method);
        getRequest.params("sn", CommonUtils.getSN());
        getRequest.params("sl", CommonUtils.getLocal(context));
        getRequest.params("no", CommonUtils.getRandom());

        getRequest.execute(new DialogCallBack(context,true, method) {

            @Override
            public void onSuccess(Response<String> response) {
                BaseBean baseBean = (BaseBean) FastJsonUtil.changeJsonToBean(response.body(), cls);
                if (baseBean.getCode() == SUCCESS) {
                    mCallback.onSuccess(method, baseBean);
                } else {
                    ToastUtil.show(context, baseBean.getMsg(), Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
//                response.getException()

                mCallback.onError(method, response);
                Log.e("onError",response.message().toString()+"");

            }
        });

    }

    public static <T> void post(final Context context, final String method, JSONObject json, final Class<PaymentBean> cls, final BaseCallback mCallback) {

        PostRequest<String> postRequest = OkGo.<String>post(Constans.HOST_XS + Constans.METHOD_GROUP_XS + method).tag(method);
//          postRequest.get



        Log.e("posturl===============",postRequest.getUrl());
        Log.e("postjson============",json.toString());



        postRequest.upJson(json);

        postRequest.execute(new DialogCallBack(context, method) {

            @Override
            public void onSuccess(Response<String> response) {
                BaseBean baseBean = (BaseBean) FastJsonUtil.changeJsonToBean(response.body(), cls);
                if (baseBean.getCode() == SUCCESS) {
                    mCallback.onSuccess(method, baseBean);
                } else {
                    ToastUtil.show(context, baseBean.getMsg(), Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Log.e(TAG, "onError: " + response.getException().getMessage());
            }
        });

    }
}
