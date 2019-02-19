package com.aibabel.locationservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aibabel.locationservice.activity.JiGuangActivity;
import com.aibabel.locationservice.service.LocationService;
import com.aibabel.locationservice.utils.CommonUtils;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView startBtn;
    private TextView stopBtn;
    private TextView tvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = findViewById(R.id.startService);
        stopBtn = findViewById(R.id.stopService);
        tvHello = findViewById(R.id.tv_hello);
        startBtn.setOnClickListener(this);
        assert stopBtn != null;
        stopBtn.setOnClickListener(this);
        tvHello.setOnClickListener(this);

        /**
         * 配置极光推送
         */
        JPushInterface.setAlias(this, 1, CommonUtils.getSN());
        Set<String> hashSet = new HashSet<>();
        hashSet.add(CommonUtils.getDevice());
        try {
            hashSet.add(CommonUtils.getVerName(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JPushInterface.setTags(this, 2, hashSet);


    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent(this, LocationService.class);
        switch (v.getId()) {
            case R.id.startService:
                if (!CommonUtils.isServiceWork(this, "com.aibabel.locationservice.service.LocationService")) {
                    Log.e("locationservice：", "新开启定位！");
                    startService(it);
                } else {
                    Log.e("locationservice：", "先停止再开启定位！");
                    stopService(it);
                    startService(it);
                }

                break;
            case R.id.stopService:
                stopService(it);
                break;
            case R.id.tv_hello:
//                Intent intent = new Intent();
//                intent.setClass(this, JiGuangActivity.class);
//                intent.putExtra("type","1");
//                startActivity(intent);
//                ResidentNotificationHelper.sendResidentNotice(this,"","重要通知",new Intent());
                break;
        }
    }



}
