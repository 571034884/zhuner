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
import android.widget.TextView;

/**
 * Created by Administrator on 2018/5/23.
 */

public class one2Activity extends FragmentActivity implements View.OnClickListener {

    private TextView tv06,tv07;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //设置去除ActionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_one2);

        tv07=(TextView)findViewById(R.id.jinru3);
        tv07.setOnClickListener(this);
        tv06=(TextView) findViewById(R.id.fanhui2);
        tv06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent=new Intent();
        ComponentName cmpName=null;
        switch (v.getId()) {
            case R.id.jinru3:{
                cmpName=new ComponentName("com.aibabel.ocrobject","com.aibabel.ocrobject.activity.TakePhoteActivity");
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
