package com.aibabel.launcher.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.launcher.R;
import com.aibabel.launcher.base.LaunBaseActivity;

import butterknife.BindView;

public class MainActivity extends LaunBaseActivity {

    @Override
    public int getLayout(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_more:
                ToastUtil.showShort(mContext,"跳转页面");
                break;
            case R.id.fl_notice:
                ToastUtil.showShort(mContext,"跳转消息");
                break;
        }
    }


}
