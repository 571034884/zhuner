package com.aibabel.download.offline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.aibabel.download.offline.app.MyApplication;
import com.aibabel.download.offline.util.L;
import com.aibabel.download.offline.util.ZipUtil;

public class TestActivity extends AppCompatActivity {

    private boolean isF=true;
    Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void kaishi(View view) {
/*        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                long start=System.currentTimeMillis();
                ZipUtil.upZipFile1("/sdcard/download_offline/mdd_europe_hot.zip","/sdcard/offline/mdd/");
                long end=System.currentTimeMillis();
                L.e("haoshi ===================="+(end-start));

//                for (int i = 0; i < 10000000; i++) {
//                    try {
//                        Thread.sleep(1000);
//                        L.e("====" + i);
//                    } catch (Exception e) {
//                        System.out.println("异常");
//                        break;
//                    }
//                }
            }
        });
        thread.start();*/


    }

    public void tingzhi(View view) {

//        thread.interrupt();

        MyApplication.isFile =false;
        L.e("==========================="+thread.isInterrupted());
    }
}
