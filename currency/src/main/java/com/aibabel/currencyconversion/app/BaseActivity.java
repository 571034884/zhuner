package com.aibabel.currencyconversion.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.aibabel.statisticalserver.SimpleStatisticsActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：SunSH on 2018/8/15 15:16
 * 功能：
 * 版本：1.0
 */
//public abstract class BaseActivity extends SimpleStatisticsActivity {

public abstract class BaseActivity extends com.aibabel.baselibrary.base.BaseActivity {

    private Unbinder mUnbinder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setNavigationBarVisibility(false);

        setContentView(initLayout());
        mUnbinder = ButterKnife.bind(this);
        init();
    }


    public  int getLayout(Bundle savedInstanceState){
        return -1;
    }

//    /**
//     * 初始化其他内容
//     */
//    public  void init();

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int initLayout();

    public abstract void init();
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if(mUnbinder!=null)mUnbinder.unbind();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 设置导航栏显示状态
     *
     * @param visible
     */
    private void setNavigationBarVisibility(boolean visible) {
        int flag = 0;
        if (!visible) {
            flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        getWindow().getDecorView().setSystemUiVisibility(flag);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode==133||keyCode==134){
//
//            onStop();
//
////            getWindow().getDecorView().postDelayed(() -> MyApplication.exit(),500);
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
}
