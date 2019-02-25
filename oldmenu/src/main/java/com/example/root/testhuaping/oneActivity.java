package com.example.root.testhuaping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/5/23.
 */

public class oneActivity extends Activity{

    private TextView tv7;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //设置去除ActionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_one);

        tv7=(TextView) findViewById(R.id.jinru1);
        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
                startActivity(LaunchIntent);
            }
        });
    }


}
