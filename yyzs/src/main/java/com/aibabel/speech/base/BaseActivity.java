package com.aibabel.speech.base;

/**
 * Created by Administrator on 2017/10/19.
 */


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aibabel.statisticalserver.SimpleStatisticsActivity;
import com.umeng.analytics.MobclickAgent;


public abstract class BaseActivity extends  com.aibabel.baselibrary.base.BaseActivity {
//public abstract class BaseActivity extends SimpleStatisticsActivity {
    protected Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public int getLayout(Bundle savedInstanceState){
        return  -1;
    }

    /**
     * 初始化其他内容
     */
    public  void init(){

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
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//    }

}
