package com.aibabel.menu.rent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.aibabel.menu.R;
import com.aibabel.menu.utils.LogUtil;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RentKeepUseActivity extends Activity {
    private Unbinder mUnbinder;


//    @BindView(R.id.ku_layout_show)
//    LinearLayout ku_layout_show;

    @BindView(R.id.keepuser_textView)
    TextView keepuser_tv;
    @BindView(R.id.hotline_textView)
    TextView hotline;

    @BindView(R.id.hotline_qrcode)
    ImageView hotline_qrcode;
    @BindView(R.id.rent_keepuser_close)
    LinearLayout keepuser_close;


    @BindString(R.string.qinlianxi_str)
    String qinlianxi;

    @BindString(R.string.kefu_keepuse)
    String keepuse;


    private  static  Activity myrentlock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rent_keep_use);
        mUnbinder = ButterKnife.bind(this);
        myrentlock = this;
        Intent intent = getIntent();
        if (intent == null) {
        } else {
            String qudao = intent.getStringExtra("zhuner");
            String kefutel = intent.getStringExtra("kefu");

            if ((qudao != null) && (qudao.equalsIgnoreCase("zhuner"))) {
                if (keepuser_tv != null) keepuser_tv.setText(R.string.zhunerkefu);
                if (hotline != null) {
                    hotline.setVisibility(View.VISIBLE);
                    hotline.setText(R.string.hotline_tel);
                }
                if (hotline_qrcode != null) {
                    hotline_qrcode.setVisibility(View.VISIBLE);
                    hotline_qrcode.setImageResource(R.mipmap.zhunerwchat);
                }
            } else {

                try {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
                    layoutParams.height = 200;
                    layoutParams.gravity = Gravity.CENTER;
                    keepuser_tv.setLayoutParams(layoutParams);
                    if (!TextUtils.isEmpty(kefutel)) {
                        if (keepuser_tv != null)
                            keepuser_tv.setText(qinlianxi + "" + kefutel + keepuse);
                        if (hotline != null) hotline.setVisibility(View.GONE);
                        if (hotline_qrcode != null) hotline_qrcode.setVisibility(View.GONE);
                    } else {
                        if (keepuser_tv != null) keepuser_tv.setText(qinlianxi + keepuse);
                        if (hotline_qrcode != null) hotline_qrcode.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    if (keepuser_tv != null) keepuser_tv.setText(qinlianxi + keepuse);
                    if (hotline_qrcode != null) hotline_qrcode.setVisibility(View.GONE);
                    e.printStackTrace();
                }


            }
        }
        if (keepuser_close != null) {
            keepuser_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RentKeepUseActivity.this.finish();
                }
            });
        }


    }

    public static void finsRentlock(){
        try {
            if (myrentlock != null) {
                LogUtil.e("  myrentlock.finish();");
                myrentlock.finish();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            myrentlock = null;
            if(mUnbinder!=null)mUnbinder.unbind();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
