package com.aibabel.speech.activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.statisticalserver.SimpleStatisticsActivity;
import com.alibaba.fastjson.JSON;
import com.aibabel.speech.R;
import com.aibabel.speech.bean.ViewspotBean;

public class ViewSpotDetailActivity extends SimpleStatisticsActivity {

    private TextView tv_name,tv_address,tv_star,tv_desc,tv_tishi,tv_featured;
    private ImageButton img_return;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_spot_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tv_name=findViewById(R.id.activity_viewstop_detail_name);
        tv_address=findViewById(R.id.activity_viewstop_detail_address);
        tv_star=findViewById(R.id.activity_viewstop_detail_star);
        tv_desc=findViewById(R.id.activity_viewstop_detail_desc);
        tv_tishi=findViewById(R.id.activity_viewstop_detail_tishi);
        tv_featured=findViewById(R.id.activity_viewstop_detail_featured);

        ViewspotBean bean=  JSON.parseObject(getIntent().getStringExtra("bean"),ViewspotBean.class);

        tv_name.setText(Html.fromHtml("<b>"+bean.getName()+"</b>"+"&nbsp;&nbsp;&nbsp;&nbsp;("+bean.getRegion()+")"));




        tv_address.setText(Html.fromHtml("<b>地址&nbsp;:</b>&nbsp;&nbsp;"+bean.getAddress()));
        tv_star.setText(Html.fromHtml("<b>亮点&nbsp;:</b>&nbsp;&nbsp;"+bean.getSpot()));
        tv_featured.setText(Html.fromHtml("<b>特色&nbsp;:</b>&nbsp;&nbsp;"+bean.getFeatured()));

        tv_desc.setText(Html.fromHtml("<b>简介&nbsp;:</b>&nbsp;&nbsp;"+bean.getDescription()));

        if (bean.getTips() != null) {
            tv_tishi.setText(Html.fromHtml("<b>小提示&nbsp;:</b>&nbsp;&nbsp;" + bean.getTips()));
        } else {
            tv_tishi.setVisibility(View.GONE);
        }


        img_return=findViewById(R.id.activity_viewstop_detail_left_return);
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
}
