package com.example.root.testhuaping;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Method;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

/**
 * Created by Administrator on 2018/5/23.
 */

public class one5Activity extends FragmentActivity implements View.OnClickListener {

    private ImageView tv7;
    private Button tv18,tv19;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置去除ActionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_one5);

        tv7 = (ImageView) findViewById(R.id.fanhui6);
        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv18 = (Button) findViewById(R.id.rediankai);;
        tv18.setOnClickListener(this);

    };

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.aibabel.wifi.app.status");
        registerReceiver(mDrawRightEntryHomeReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mDrawRightEntryHomeReceiver);
    }

    BroadcastReceiver mDrawRightEntryHomeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String tty = intent.getStringExtra("msg");
            String uid = intent.getStringExtra("wifiApName");
            String uname = intent.getStringExtra("wifiApPassword");
            Log.d("ffff","tty="+tty);
            if (tty.equals("OFF")) {
                Intent intent1 = new Intent();
                intent1.putExtra("wifiApName",uid);
                intent1.putExtra("wifiApPassword",uname);
                intent1.setClass(one5Activity.this, three6Activity.class);
                startActivity(intent1);
            } else {
                Intent intent2 = new Intent();
                intent2.putExtra("wifiApName",uid);
                intent2.putExtra("wifiApPassword",uname);
                intent2.setClass(one5Activity.this, three7Activity.class);
                startActivity(intent2);
            }

        }

    };
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent=new Intent();
        ComponentName cmpName=null;
        switch (v.getId()) {
            case R.id.rediankai:{
                intent.setAction("com.aibabel.wifi.app.get.status");
                sendBroadcast(intent);
                Log.e("fffffffff","ggggggggggg");
                break;
            }
            default:
                break;
        }
        if(cmpName!=null){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmpName);
            try{
                startActivity(intent);
            }catch(ActivityNotFoundException ex){


            }
        }

    }

}
