package com.aibabel.weather.okgo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.aibabel.weather.R;
import com.aibabel.weather.app.Constant;
import com.aibabel.weather.utils.CommonUtils;
import com.aibabel.weather.utils.FastJsonUtil;
import com.aibabel.weather.utils.NetUtil;
import com.aibabel.weather.utils.ToastUtil;
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

    public static <T> void get(final Context context, final String method, Map<String, String> param, final Class<T> cls, final BaseCallback mCallback) {
        get(context, Constant.DEFAULT_METHOD_GROUP, method, param, cls, mCallback);
    }

    /**
     * @param <T>
     * @param context   dialog中用到
     * @param method    方法名
     * @param param     参数
     * @param cls       结果要解析的对象
     * @param mCallback 回调
     */
    public static <T> void get(final Context context, final String methodGroup, final String method, Map<String, String> param, final Class<T> cls, final BaseCallback mCallback) {
        if (NetUtil.isNetworkAvailable(context)) {
            GetRequest<String> getRequest = OkGo.<String>get(Constant.IP_PORT + methodGroup + method).tag(method);

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
}
