package com.aibabel.download.offline.dailog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.download.offline.R;

public class TishiDialog  extends Dialog{
//    private Dialog dialog;
    private TextView tv_title;
    private TextView tv_desc;
    private TextView tv_cancel;
    private TextView tv_sure;


    public TextView getTv_title() {
        return tv_title;
    }

    public TextView getTv_desc() {
        return tv_desc;
    }



    public TextView getTv_cancel() {
        return tv_cancel;
    }

    public TextView getTv_sure() {
        return tv_sure;
    }

    private Context mContext;

    public TishiDialog(Context context) {
        super(context);
        mContext=context;

//        dialog=new Dialog(context,R.style.CustomDialog);

//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//需要在设置内容之前定义
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View view= LinearLayout.inflate(mContext, R.layout.dialog_tishi,null);
        tv_title=view.findViewById(R.id.dialog_title);
        tv_desc=view.findViewById(R.id.dialog_desc);
        tv_cancel=view.findViewById(R.id.dialog_tv_cancel);
        tv_sure=view.findViewById(R.id.dialog_tv_sure);

        setContentView(view);
        setCancelable(false);

    }









}
