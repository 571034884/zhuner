package com.aibabel.messagemanage;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.base.StatisticsBaseActivity;
import com.aibabel.menu.R;
import com.aibabel.menu.base.BaseActivity;

import java.io.Serializable;
import java.util.HashMap;


public class JiGuangActivity extends StatisticsBaseActivity {


    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvConfirm;
    private TextView tvConfirm2;
    private TextView tvCancel;
    private TextView tvConfirm4;
    private LinearLayout ll1;
    private LinearLayout ll2;
    private LinearLayout ll3;
    private String pkg;
    private String path;
    private String couponId;

    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ji_guang);
        String msg = getIntent().getStringExtra("msg");
        title =getIntent().getStringExtra("title");
        pkg = getIntent().getStringExtra("package");
        path = getIntent().getStringExtra("path");
        couponId = getIntent().getStringExtra("couponId");

        String type = getIntent().getStringExtra("type");

        tvConfirm = findViewById(R.id.tv_confirm);
        tvConfirm2 = findViewById(R.id.tv_confirm2);
        tvCancel = findViewById(R.id.tv_cancel);
        tvConfirm4 = findViewById(R.id.tv_confirm4);
        ll1 = findViewById(R.id.ll1);
        ll2 = findViewById(R.id.ll2);
        ll3 = findViewById(R.id.ll3);

        tvContent = findViewById(R.id.tv_content);
        tvTitle = findViewById(R.id.tv_title);

        tvTitle.setText(title+"");
        tvContent.setText(msg+"");

        switch (type){
            case "1":
                ll1.setVisibility(View.VISIBLE);
                break;
            case "2":
                ll2.setVisibility(View.VISIBLE);
                break;
            case "3":
                ll3.setVisibility(View.VISIBLE);
                break;
        }
//        push_notification3		push_notification3_def:界面&&push_notification3_id:景区导览
//        push_notification4		push_notification4_def:确定&&push_notification4_id:景区导览
//        push_notification5		push_notification5_def:取消&&push_notification5_id:景区导览

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("push_notification5_def", title);
                    add_hp.put("push_notification5_id",MessageUtil.numid );
                    addStatisticsEvent("push_notification5", add_hp);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                JiGuangActivity.this.finish();
            }
        });
        tvConfirm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("push_notification5_def", title);
                    add_hp.put("push_notification5_id",MessageUtil.numid );
                    addStatisticsEvent("push_notification5", add_hp);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                JiGuangActivity.this.finish();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("push_notification5_def", title);
                    add_hp.put("push_notification5_id",MessageUtil.numid );
                    addStatisticsEvent("push_notification5", add_hp);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                JiGuangActivity.this.finish();
            }
        });
        tvConfirm4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCoupon();
            }
        });

    }



    /**
     * 跳转到购物
     */
    private void toCoupon() {
        try{
            /**####  start-hjs-addStatisticsEvent   ##**/
            try {
                HashMap<String, Serializable> add_hp = new HashMap<>();
                add_hp.put("push_notification4_def", title);
                add_hp.put("push_notification4_id",MessageUtil.numid );
                addStatisticsEvent("push_notification4", add_hp);
            }catch (Exception e){
                e.printStackTrace();
            }
            /**####  end-hjs-addStatisticsEvent  ##**/
            int id = Integer.parseInt(couponId);
            Intent intent = new Intent();
            ComponentName componentName = new ComponentName(pkg, path);
            intent.setComponent(componentName);
            intent.putExtra("from", "location");
            intent.putExtra("couponId", id);
            startActivity(intent);
            this.finish();
        }catch(Exception e){
            e.printStackTrace();
        }



    }

}
