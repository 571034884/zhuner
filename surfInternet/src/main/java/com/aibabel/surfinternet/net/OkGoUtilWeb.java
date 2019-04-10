package com.aibabel.surfinternet.net;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.DialogCallBack;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.surfinternet.bean.PaymentBean;
import com.aibabel.surfinternet.utils.FastJsonUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONObject;


/**
 * Created by fytworks on 2019/4/8.
 */

public class OkGoUtilWeb {

    public static String TAG = "OkGoUtil";
    public static String SUCCESS = "1";

    public static <T> void post(final Context context, final String method, JSONObject json, final Class<PaymentBean> cls,BaseCallback mCallback) {

        PostRequest<String> postRequest = OkGo.<String>post(Api.HOST_WEB + Api.METHOD_GROUP_WEB + method).tag(method);

        postRequest.upJson(json);

        postRequest.execute(new DialogCallBack(context, method) {

            @Override
            public void onSuccess(Response<String> response) {
                BaseBean baseBean = (BaseBean) FastJsonUtil.changeJsonToBean(response.body(), cls);
                if (baseBean.getCode().equals(SUCCESS)) {
                    mCallback.onSuccess(method, baseBean,response.body());
                } else {
                    mCallback.onError(method, baseBean.getCode() + " " + baseBean.getMsg(), response.body());
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                try{
                    mCallback.onError(method, response.getException().getMessage(), "");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }


}
