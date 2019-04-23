package com.aibabel.menu.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.menu.R;
import com.tencent.mmkv.MMKV;

import butterknife.ButterKnife;

/**
 * Created by fytworks on 2019/4/16.
 */

public abstract class LaunBaseActivity extends BaseActivity{
    //统一处理 数据机制
    public MMKV mmkv = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStateFlag(true);
        super.onCreate(savedInstanceState);
        int getlayout = getLayout(savedInstanceState);
        if(getlayout>0) {
            setContentView(getlayout);
            mUnbinder = ButterKnife.bind(this);
        }
        mmkv = MMKV.defaultMMKV();
        initView();
        setKilledToBackground(false);
    }

    protected abstract void initView();

    @Override
    public void init() {

    }

    public void startActResult(Class act, int results){
        startActivityForResult(new Intent(mContext, act), results);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void startAct(Class act){
        startActivity(new Intent(mContext,act));
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void closeAct(){
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
