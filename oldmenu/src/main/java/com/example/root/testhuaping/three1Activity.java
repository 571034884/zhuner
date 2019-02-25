package com.example.root.testhuaping;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/5/23.
 */

public class three1Activity extends FragmentActivity implements View.OnClickListener {

    private ImageView tv7;
    private Button tv32;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //设置去除ActionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_three1);

        tv7=(ImageView) findViewById(R.id.fanhui32);
        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv32=(Button)findViewById(R.id.jinru32);
        tv32.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent=new Intent();
        ComponentName cmpName=null;
        switch (v.getId()) {
            case R.id.jinru32:{
                cmpName=new ComponentName("com.aibabel.currencyconversion","com.aibabel.currencyconversion.MainActivity");
                //cmpName=new ComponentName("com.aibabel.travel","com.aibabel.travel.activity.LauncherActivity");
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
