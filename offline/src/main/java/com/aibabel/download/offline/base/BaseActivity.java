package com.aibabel.download.offline.base;

/**
 * Created by Administrator on 2017/10/19.
 */


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.aibabel.statisticalserver.SimpleStatisticsActivity;


public abstract class BaseActivity extends SimpleStatisticsActivity {
    protected Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置去除ActionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayoutId());
        setContext(this);
        assignView();
        initView();
        initListener();
        initData();
    }

    protected abstract void assignView();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected void setContext(Context c) {
        mContext=c;

    }

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

    @Override
    protected void onStop() {
        super.onStop();

    }

}
