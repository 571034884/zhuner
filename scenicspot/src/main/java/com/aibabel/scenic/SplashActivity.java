package com.aibabel.scenic;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.aibabel.baselibrary.utils.ProviderUtils;
import com.aibabel.scenic.activity.MainActivity;
import com.aibabel.scenic.activity.CityLocaActivity;
import com.aibabel.scenic.base.BaseScenicActivity;

import butterknife.BindView;


/**
 * 启动页
 * Created by HP on 2018/6/14.
 */

public class SplashActivity extends BaseScenicActivity implements View.OnClickListener {


    @BindView(R.id.tv_skip)
    TextView mCountDownTextView;
    MyCountDownTimer mCountDownTimer;

    @Override
    public int getLayouts(Bundle bundle) {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        mCountDownTextView.setOnClickListener(this);
        mCountDownTextView.setVisibility(View.GONE);
        mCountDownTextView.setText("3s 跳过");
        mCountDownTimer = new MyCountDownTimer(500, 200);
        mCountDownTimer.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_skip:
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                }
                onSwitchView();
                break;
        }
    }

    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以「 毫秒 」为单位倒计时的总数
         *                          例如 millisInFuture = 1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick()
         *                          例如: countDownInterval = 1000 ; 表示每 1000 毫秒调用一次 onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void onFinish() {
            mCountDownTextView.setText(R.string.splash_zero);
            onSwitchView();
        }

        public void onTick(long millisUntilFinished) {
            mCountDownTextView.setText(millisUntilFinished / 1000 +" 跳过");
        }
    }

    private void onSwitchView() {
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        this.finish();
    }


    @Override
    protected void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        super.onDestroy();
    }
}
