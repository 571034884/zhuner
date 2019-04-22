package com.aibabel.launcher.rent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.aibabel.menu.R;
import com.aibabel.launcher.utils.LogUtil;


public class RentLockedActivity extends Activity {
    String qudao;
    String kefutel;

    private  static  Activity myrentlock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rent_locked);
        LinearLayout rent_user_unlock =(LinearLayout)findViewById(R.id.rent_user_unlock);
        LinearLayout rent_manger_unlock =(LinearLayout)findViewById(R.id.rent_manger_unlock);

        Intent intent = getIntent();
        if (intent == null) {
            qudao = "";
            kefutel = "";
        } else {
             qudao = intent.getStringExtra("zhuner");
             kefutel = intent.getStringExtra("kefu");
        }

        myrentlock = this;

        rent_user_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keepuse =   new Intent(RentLockedActivity.this, RentKeepUseActivity.class);
                keepuse.putExtra("zhuner",qudao);
                keepuse.putExtra("kefu",kefutel);
                startActivity(keepuse);
            }
        });
        rent_manger_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.dommy.qrcode");
                LaunchIntent.putExtra("erweima", "daoqi");
                startActivity(LaunchIntent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
       // LogUtil.e("rentlock = onrestart");
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
        myrentlock = null;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtil.e("hjs","keyCode="+keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_HOME:
                return true;
            case KeyEvent.KEYCODE_MENU:
                return true;
            case KeyEvent.KEYCODE_BACK:
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                return true;
            /*case KeyEvent.KEYCODE_CALL:
                return true;
            case KeyEvent.KEYCODE_SYM:
                return true;
            case KeyEvent.KEYCODE_STAR:
                return true;*/
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
            case 136:
                return true;

        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onResume() {
        super.onResume();
//        this.startLockTask();
       // LogUtil.e("rentlock = onResume");
    }

    }
