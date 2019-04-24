package com.aibabel.menu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.menu.R;

import java.util.Locale;

/**
 * Created by Administrator on 2018/5/23.
 */

public class three7Activity extends Activity {

    private ImageView tv38;
    private TextView uid1, uname1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //设置去除ActionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_three7);

        tv38=(ImageView) findViewById(R.id.fanhui38);
        tv38.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String uid = getIntent().getStringExtra("wifiApName");
        String uname = getIntent().getStringExtra("wifiApPassword");

        uid1=(TextView)findViewById(R.id.uid);
        uname1=(TextView)findViewById(R.id.uname);
        uid1.setText("AP:"+uid);
        if (getCountryZipCode(this).equals("CN")){
            uname1.setText("密码:"+ uname);
        }else if(getCountryZipCode(this).equals("TW")){
            uname1.setText("密碼:" + uname);
        }else if(getlanguage(this).equals("en")){
            uname1.setText("password:" + uname);
        }else if(getlanguage(this).equals("ja")){
            uname1.setText("パスワード:" + uname);
        }else if(getlanguage(this).equals("ko")) {
            uname1.setText("비밀번호:" + uname);
        }else {
            uname1.setText(R.string.mima + uname);
        }

    }

    public static String getCountryZipCode(Context context)
    {
        String CountryZipCode = "";

        Locale locale = context.getResources().getConfiguration().locale;
        CountryZipCode = locale.getCountry();
        Log.e("wzf","CountryZipCode="+CountryZipCode);

        return CountryZipCode;
    }
    public static String getlanguage(Context context)
    {

        String language = "";
        Locale locale = context.getResources().getConfiguration().locale;
        language=Locale.getDefault().getLanguage();
        Log.e("wzf","language="+language);

        return language;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

