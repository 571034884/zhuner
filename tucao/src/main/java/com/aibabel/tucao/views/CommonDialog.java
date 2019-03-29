package com.aibabel.tucao.views;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.aibabel.tucao.R;

public class CommonDialog extends AlertDialog {


    //    private TextView tv_cancel;//确定按钮
    private TextView tv_confirm;//取消按钮
    private TextView tv_title;//消息标题文本
    private TextView tv_message;//消息提示文本
    private String titleStr;//从外界设置的title文本
    private String messageStr;//从外界设置的消息文本
    private String cancelStr;//取消文本的显示内容
    private String confirmStr = "";//确定文本的显示内容
    private Context context;
    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private OnBtnClickListener onBtnClickListener;


    public CommonDialog(Context context) {
        super(context);
        this.context = context;
    }

    public CommonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    public CommonDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commondialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBtnClickListener != null) {
                    onBtnClickListener.onConfirmClick();
                }
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
//        //设置取消按钮被点击后，向外界提供监听
//        tv_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onBtnClickListener != null) {
//                    onBtnClickListener.onCancelClick();
//                }
//                if (noOnclickListener != null) {
//                    noOnclickListener.onNoClick();
//                }
//            }
//        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
//        cancelStr = context.getString(R.string.cancle);
//        confirmStr = context.getString(R.string.confirm);

        //如果用户自定了title和message
        if (titleStr != null) {
            tv_title.setText(titleStr);
        }
        if (messageStr != null) {
            tv_message.setText(messageStr);
        }
        //如果设置按钮的文字
        if (!TextUtils.isEmpty(confirmStr)) {
            tv_confirm.setText(confirmStr);
        }
//        if (!TextUtils.isEmpty(cancelStr)) {
//            tv_cancel.setText(cancelStr);
//        }
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        tv_confirm = findViewById(R.id.tv_confirm);
//        tv_cancel = findViewById(R.id.tv_cancel);
//        tv_title =  findViewById(R.id.title);
//        tv_message = findViewById(R.id.tv_dialog_content);
    }

    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title
     */
    public CommonDialog setTitle(String title) {
        titleStr = title;
        return this;
    }

    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message
     */
    public CommonDialog setMessage(String message) {
        messageStr = message;
        return this;
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }

    public interface OnBtnClickListener {
        public void onCancelClick();

        public void onConfirmClick();
    }


    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param str
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            cancelStr = str;
        }
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的监听
     *
     * @param
     * @param
     */
    public void setOnBtnclickListener(onYesOnclickListener onYesOnclickListener, onNoOnclickListener onNoOnclickListener) {

        this.yesOnclickListener = onYesOnclickListener;
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置按钮的显示内容和监听
     *
     * @param
     * @param
     */
    public void setOnBtnclickListener(String confirm, String cancel, onYesOnclickListener onYesOnclickListener, onNoOnclickListener onNoOnclickListener) {
        if (!TextUtils.isEmpty(confirm)) {
            confirmStr = confirm;
        }
        if (!TextUtils.isEmpty(cancel)) {
            cancelStr = cancel;
        }
        this.yesOnclickListener = onYesOnclickListener;
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的监听
     *
     * @param listener
     */
    public void setOnBtnClickListener(OnBtnClickListener listener) {

        this.onBtnClickListener = listener;
    }


}
