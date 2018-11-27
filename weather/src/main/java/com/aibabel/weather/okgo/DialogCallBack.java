package com.aibabel.weather.okgo;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;


/**
 * 作者：SunSH on 2018/6/14 20:32
 * 功能：
 * 版本：1.0
 */
public class DialogCallBack extends StringCallback {
    private String TAG = "DialogCallBack";

    LoadingDialog dialog;
    private Context context;
    private boolean isShowDialog;//是否显示dialog
    private boolean isCancel;//是否能返回键取消，默认能
    private Object tag;//用于取消网络连接

    /**
     * @param context
     * @param isShowDialog 是否显示dialog
     * @param tag          用于取消网络连接
     * @param isCancel     是否能返回键取消，默认能
     */
    public DialogCallBack(Context context, boolean isShowDialog, Object tag, boolean isCancel) {
        this.context = context;
        this.isShowDialog = isShowDialog;
        this.tag = tag;
        this.isCancel = isCancel;
    }

    public DialogCallBack(Context context, boolean isShowDialog, Object tag) {
        this.context = context;
        this.isShowDialog = isShowDialog;
        this.tag = tag;
        isCancel = true;
    }

    public DialogCallBack(Context context, Object tag) {
        this.context = context;
        this.tag = tag;
        isShowDialog = true;
        isCancel = true;
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
        if (isShowDialog) {

            dialog = LoadingDialog.showprogress(context, isCancel);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (tag != null) {
                        Log.e(TAG, "onCancel: 取消网络请求");
                        OkGo.getInstance().cancelTag(tag);
                    }
                }
            });
        }
    }

    @Override
    public void onSuccess(Response<String> response) {
        Log.e(TAG, "onSuccess: ");
    }

    @Override
    public void onFinish() {
        super.onFinish();
        Log.e(TAG, "onFinish: ");
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onError(Response<String> response) {
        super.onError(response);
        Log.e(TAG, "onError: " + response.getException().getMessage());
    }
}
