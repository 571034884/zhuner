package com.aibabel.travel.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.aibabel.travel.R;

public class NoticeDialog extends AlertDialog {


    private TextView tv_name;
    private TextView tv_title;
    private TextView tv_content;
    private ImageView iv_close;
    private LinearLayout ll_dialog;

    private Window window;
    public NoticeDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public NoticeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected NoticeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    private void init(Context context) {
        setContentView(R.layout.notice_dialog);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv_content);
        iv_close = (ImageView)findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        window = getWindow();
        // 按返回键是否取消
        setCancelable(false);
        // 监听返回键处理
        setOnCancelListener(null);
        // 设置居中
        window.getAttributes().gravity = Gravity.TOP;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.0f;
        window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);


    }


    /**
     * 给Dialog设置提示信息
     *
     * @param message
     */
    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            tv_content.setText(message);
            tv_content.invalidate();
        }
    }
    /**
     * 给Dialog设置提示信息
     *
     * @param title
     */
    public void setTitle(CharSequence title) {
        if (title != null && title.length() > 0) {
            tv_title.setText(title);
            tv_title.invalidate();
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
