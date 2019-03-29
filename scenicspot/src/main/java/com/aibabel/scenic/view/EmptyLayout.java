package com.aibabel.scenic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.scenic.R;


/**
 * Created by fytworks on 2019/1/9.
 */
public class EmptyLayout extends LinearLayout{

    //正常显示的情况下
    public static final int SUCCESS_EMPTY = 0;
    //没有数据的情况下
    public static final int NORMAL_EMPTY = 1;
    //报错情况下
    public static final int ERROR_EMPTY = 2;
    //没有网络的情况下
    public static final int NETWORK_EMPTY = 3;


    private final Context context;

    private ImageView empty_img;
    private TextView empty_txt;
    private LinearLayout empty_ll;
    private TextView empty_btn;


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
        empty_btn = view.findViewById(R.id.empty_btn);
        addView(view);
        empty_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBtnClick();
            }
        });
    }

    private onClickListener listener;

    public void setOnBtnClickListener(onClickListener listener) {
        this.listener = listener;
    }

    public interface onClickListener {
        void onBtnClick();
    }

    public void setErrorType(int error){
        setVisibility(View.VISIBLE);
        empty_btn.setVisibility(GONE);
        switch (error){
            case SUCCESS_EMPTY:
                setVisibility(View.GONE);
                break;
            case NORMAL_EMPTY:
                empty_img.setImageResource(R.mipmap.ic_null);
                empty_txt.setText("准儿没有找到信息");
                break;
            case ERROR_EMPTY:
                empty_img.setImageResource(R.mipmap.icon_transit_error);
                empty_txt.setText("准儿出错了");
                empty_btn.setVisibility(VISIBLE);
                empty_btn.setText("重新加载");
                break;
            case NETWORK_EMPTY:
                empty_img.setImageResource(R.mipmap.ic_network_error);
                empty_txt.setText("网络开了小差，请连接网络");
                empty_btn.setVisibility(VISIBLE);
                empty_btn.setText("重新加载");
                break;
        }
    }
}
