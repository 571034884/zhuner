package com.aibabel.alliedclock.okgo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.aibabel.alliedclock.R;
import com.aibabel.alliedclock.app.Constant;
import com.aibabel.alliedclock.utils.CommonUtils;
import com.aibabel.alliedclock.utils.FastJsonUtil;
import com.aibabel.alliedclock.utils.NetUtil;
import com.aibabel.alliedclock.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

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
    public static <T> void get(final Context context, final boolean isActive, final String method, Map<String, String> param, final Class<T> cls, final BaseCallback mCallback) {
        if (NetUtil.isNetworkAvailable(context)) {
            GetRequest<String> getRequest = OkGo.<String>get(Constant.IP_PORT + Constant.HOST_URL + method).tag(method);

            for (Map.Entry<String, String> entry : param.entrySet()) {
                getRequest.params(entry.getKey(), entry.getValue());
            }

            getRequest.params("sn", CommonUtils.getSN());
            getRequest.params("sl", CommonUtils.getLocalLanguage());
            getRequest.params("no", CommonUtils.getRandom());
            getRequest.params("sysLanguage", Constant.SYSTEM_LANGUAGE);

            getRequest.execute(new DialogCallBack(context, method) {

                @Override
                public void onSuccess(Response<String> response) {
                    try {
                        BaseBean baseBean = (BaseBean) FastJsonUtil.changeJsonToBean(response.body(), cls);
                        if (baseBean.getCode() == SUCCESS) {
                            mCallback.onSuccess(method, baseBean);
                        } else {
                            ToastUtil.show(context, baseBean.getMsg(), Toast.LENGTH_SHORT);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onSuccess: " + e.getMessage().toString());
                    }
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    mCallback.onError(method, response.getException().getMessage());
                }
            });
        } else {
            ToastUtil.show(context, context.getResources().getString(R.string.toast_wuwangluo), 1000);
        }


    }

    /**
     * 为html页面写的
     *
     * @param <T>
     * @param context   dialog中用到
     * @param method    方法名
     * @param param     参数
     * @param cls       结果要解析的对象
     * @param mCallback 回调
     */
    public static <T> void getInHtml(final Context context, final String method, Map<String, String> param, final Class<T> cls, final BaseCallback mCallback) {
        if (NetUtil.isNetworkAvailable(context)) {
            GetRequest<String> getRequest = OkGo.<String>get(Constant.IP_PORT + Constant.HOST_URL + method).tag(method);

            for (Map.Entry<String, String> entry : param.entrySet()) {
                getRequest.params(entry.getKey(), entry.getValue());
            }

            getRequest.params("sn", CommonUtils.getSN());
            getRequest.params("sl", CommonUtils.getLocalLanguage());
            getRequest.params("no", CommonUtils.getRandom());

            getRequest.execute(new StringCallback() {

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
                    mCallback.onError(method, response.getException().getMessage());
                }
            });
        } else {
            ToastUtil.show(context, context.getResources().getString(R.string.toast_wuwangluo), 1000);
        }


    }

    public static <T> void post(final Context context, final String method, String[] params, final Class<T> cls, final BaseCallback mCallback) {

        PostRequest<String> postRequest = OkGo.<String>post(Constant.IP_PORT + Constant.HOST_URL + method).tag(method);

        for (int i = 0; i < params.length; i++) {
            postRequest.upString(params[i]);
        }

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
