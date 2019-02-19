package com.aibabel.translate.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.translate.R;

public class CustomProgress extends Dialog {



    private TextView tv_msg;
    private ImageView iv_loading;

    public CustomProgress(Context context) {
        super(context);
    }

    public CustomProgress(Context context, int theme) {
        super(context, theme);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }


    private void init(Context context) {


        setTitle("");
        setContentView(R.layout.progress_custom);
        tv_msg = findViewById(R.id.message);
        tv_msg.setText(context.getString(R.string.app_name));

        // 按返回键是否取消
        setCancelable(false);
        // 监听返回键处理
        setOnCancelListener(null);
        // 设置居中
        getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.0f;
        getWindow().setAttributes(lp);


    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = findViewById(R.id.spinnerImageView);
        // 获取ImageView上的动画背景
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        // 开始动画
        spinner.start();
    }

    /**
     * 给Dialog设置提示信息
     *
     * @param message
     */
    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            tv_msg.setText(message);
            tv_msg.invalidate();
        }
    }



    public void show() {
        super.show();
    }


    public void cancle() {
        super.cancel();
    }

    public void dismiss() {
        super.dismiss();
    }


}
