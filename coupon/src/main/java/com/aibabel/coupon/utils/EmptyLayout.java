package com.aibabel.coupon.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.coupon.R;


/**
 * Created by fytworks on 2019/1/9.
 */
public class EmptyLayout extends LinearLayout{

    //正常显示的情况下
    public static final int EMPTY_NORMAL_DISPLAY = 0;
    //没有数据的情况下
    public static final int EMPTY_NORMAL_DATAS = 1;
    //报错情况下
    public static final int EMPTY_ERROR_DATAS = 2;
    //没有网络的情况下
    public static final int EMPTY_NETWORK_DATAS = 3;


    private final Context context;

    private ImageView empty_img;
    private TextView empty_txt;
    private LinearLayout empty_ll;


    public EmptyLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }
    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.view_error, null);
        empty_img = view.findViewById(R.id.empty_img);
        empty_txt = view.findViewById(R.id.empty_txt);
        empty_ll =  view.findViewById(R.id.empty_ll);
        addView(view);
    }

    public void setErrorType(int error,String text){
        setVisibility(View.VISIBLE);
        switch (error){
            case EMPTY_NORMAL_DISPLAY:
                setVisibility(View.GONE);
                break;
            case EMPTY_NORMAL_DATAS:
                empty_img.setImageResource(R.mipmap.icon_transit_error);
                empty_txt.setText(text);
                break;
            case EMPTY_ERROR_DATAS:
                empty_img.setImageResource(R.mipmap.icon_transit_error);
                empty_txt.setText(text);
                break;
            case EMPTY_NETWORK_DATAS:
                empty_img.setImageResource(R.mipmap.icon_transit_error);
                empty_txt.setText(text);
                break;
        }
    }

    public void setOnButtonClick(OnClickListener listener) {
        empty_img.setOnClickListener(listener);
    }
}
