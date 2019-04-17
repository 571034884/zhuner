package com.aibabel.launcher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.launcher.R;
import com.aibabel.launcher.base.LaunBaseActivity;

/**
 * Created by fytworks on 2019/4/17.
 */

public class MoreActivity extends LaunBaseActivity{

    @Override
    public int getLayout(Bundle savedInstanceState) {
        return R.layout.activity_more;
    }

    @Override
    public void init() {

    }

    @Override
    protected void initView() {

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_close:
                closeAct();
                break;
        }
    }

}
