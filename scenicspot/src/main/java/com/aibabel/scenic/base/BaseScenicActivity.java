package com.aibabel.scenic.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.scenic.utils.Logs;

/**
 * Created by fytworks on 2019/3/23.
 */

public abstract class BaseScenicActivity extends BaseActivity implements OnClickListener{


    private NetworkChangeListener mNetworkListener;
    private IntentFilter intentFilter;

    @Override
    public int getLayout(Bundle savedInstanceState) {
        return getLayouts(savedInstanceState);
    }

    @Override
    public void init() {
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
//
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        mNetworkListener = new NetworkChangeListener();
//        registerReceiver(mNetworkListener, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNetworkListener != null) {
            unregisterReceiver(mNetworkListener);
        }
    }

    /**
     * 网络改变监控广播
     * <p>
     * 监听网络的改变状态,只有在用户操作网络连接开关(wifi,mobile)的时候接受广播,
     * 然后对相应的界面进行相应的操作，并将 状态 保存在我们的APP里面
     */
    class NetworkChangeListener extends BroadcastReceiver {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Logs.e("当前网络可用");
//                BASENETWORK = true;
            } else {
                Logs.e("当前网络不可用");
//                BASENETWORK = false;
            }
        }
    }

    public abstract int getLayouts(Bundle var1);
    public abstract void initView();
    public abstract void initData();

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 133:
            case 134:
                Intent intent = new Intent("com.aibabel.scenic.stop");
                sendBroadcast(intent);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
