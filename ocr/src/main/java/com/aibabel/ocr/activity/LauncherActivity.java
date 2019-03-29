package com.aibabel.ocr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.aibabel.ocr.R;
import com.aibabel.ocr.utils.ContentProviderUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ==========================================================================================
 *
 * @Author：CreateBy 张文颖
 * @Date：2018/5/31
 * @Desc：启动页
 * @==========================================================================================
 */
public class LauncherActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launcher);
    }


    private void toPhoto() {

        Intent intent = new Intent();
        intent.setClass(this, TakePhoteActivity.class);
        startActivity(intent.putExtra("from", getIntent().getStringExtra("from")));
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO: 2019/3/29 临时操作，按语音翻译键调起的时候，停止播放景区导览
        Intent intent1 = new Intent("com.aibabel.scenic.stop");
        sendBroadcast(intent1);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                toPhoto();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 160);
    }
}
