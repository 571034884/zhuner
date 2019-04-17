package com.aibabel.launcher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.launcher.R;
import com.aibabel.launcher.base.LaunBaseActivity;
import com.aibabel.launcher.utils.Logs;

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 133:
                Logs.e("走了onKeyDown");
                closeAct();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
