package com.aibabel.weather.app;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.aibabel.statisticalserver.SimpleStatisticsActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：SunSH on 2018/5/26 17:35
 * 功能：
 * 版本：1.0
 */
public abstract class BaseActivity extends com.aibabel.baselibrary.base.BaseActivity {

//    private Unbinder mUnbinder;
//    public Context mContext;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
//        setNavigationBarVisibility(false);
//        //锁定竖屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//        setContentView(initLayout());
//        mUnbinder = ButterKnife.bind(this);
//        mContext = this;
//        init();
//    }
//
//    /**
//     * 设置布局
//     *
//     * @return
//     */
//    public abstract int initLayout();
//
//    public abstract void init();
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
//    protected void onDestroy() {
//        super.onDestroy();
//        mUnbinder.unbind();
//    }
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
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case 133:
//                MyApplication.exit();
//                break;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

}
