package com.aibabel.surfinternet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.aibabel.surfinternet.BaseApplication;
import com.aibabel.surfinternet.utils.SharePrefUtil;
import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends com.aibabel.baselibrary.base.BaseActivity {
//public class BaseActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
//        setNavigationBarVisibility(false);
//        super.onCreate(savedInstanceState);
//    }

    @Override
    public int getLayout(Bundle savedInstanceState) {
        return -1;
    }

    @Override
    public void init() {

    }
//
//    /**
//     * 设置导航栏显示状态
//     *
//     * @param visible
//     */
//    private void setNavigationBarVisibility(boolean visible) {
//        int flag = 0;
//        if (!visible) {
//            flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//        }
//        getWindow().getDecorView().setSystemUiVisibility(flag);
//        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        MobclickAgent.onResume(this);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        MobclickAgent.onPause(this);
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode){
//            case 133:
//            case 134:
//
//                BaseApplication.exit();
//
//                break;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
