package com.aibabel.menu.base;

/**
 * Created by Administrator on 2017/10/19.
 */


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;



public abstract class BaseActivity extends com.aibabel.baselibrary.base.BaseActivity {
//    protected Context mContext;
//    public Unbinder mUnbinder;
    @Override
    public void onCreate(Bundle savedInstanceState) {


//        Window window = this.getWindow();
//        View decorView = window.getDecorView();
//        // 两个标志位要结合使用，表示让应用的主体内容占用系统状态栏的空间
//        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        decorView.setSystemUiVisibility(option);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        this.getWindow().setStatusBarColor(Color.TRANSPARENT); // 底部导航栏颜色也可以由系统设置

        setStateFlag(true);
        //沉浸式状态栏
        super.onCreate(savedInstanceState);


//        setContentView(getLayout(savedInstanceState));
//        setContext(this);
//        mUnbinder = ButterKnife.bind(this);
        assignView();
        initView();
        initListener();
        initData();
    }

    protected abstract void assignView();

    protected abstract void initView();

    protected abstract void initListener();

//    protected abstract int getLayoutId();




    protected abstract void initData();

//    protected void setContext(Context c) {
//        mContext=c;
//
//    }

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
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
