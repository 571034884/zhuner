package com.aibabel.launcher.base;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.launcher.R;

import butterknife.ButterKnife;

/**
 * Created by fytworks on 2019/4/16.
 */

public abstract class LaunBaseActivity extends BaseActivity{


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStateFlag(true);
        super.onCreate(savedInstanceState);
        int getlayout = getLayout(savedInstanceState);
        if(getlayout>0) {
            setContentView(getlayout);
            mUnbinder = ButterKnife.bind(this);
        }
        init();
        initView();
        setKilledToBackground(false);
    }

    protected abstract void initView();


    public void startAct(Class act){
        startActivity(new Intent(mContext,act));
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void closeAct(){
        finish();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
