package com.aibabel.translate;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aibabel.translate.activity.BaseActivity;
import com.aibabel.translate.app.BaseApplication;
import com.aibabel.translate.broadcast.NetBroadcastReceiver;
import com.aibabel.translate.broadcast.ScreenBroadcastReceiver;
import com.aibabel.translate.fragment.IpsilateralFragment;
import com.aibabel.translate.fragment.OppositeFragment;
import com.aibabel.translate.offline.ChangeOffline;
import com.aibabel.translate.utils.CommonUtils;
import com.aibabel.translate.utils.Constant;
import com.aibabel.translate.utils.L;
import com.aibabel.translate.utils.MyDialog;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements NetBroadcastReceiver.NetListener, ScreenBroadcastReceiver.ScreenListener {

    @BindView(R.id.fl_translate)
    FrameLayout flTranslate;
    @BindView(R.id.mian_close_img)
    ImageView mianCloseImg;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private IpsilateralFragment ipsiFragment;
    private OppositeFragment oppoFragment;
    private int currentFragment = 0;
    public NetBroadcastReceiver broadcastReceiver;
    private ScreenBroadcastReceiver mScreenReceiver;
    private String isTranslateStart;
    private int isTranslateKeyCode;
    private boolean isSleep = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initBroadcast();
        longPressKey("MainActivty");

        mianCloseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        try {

            String from = getIntent().getStringExtra("from");
            L.e("MainActivity   onCreate=======================Intent:" + from);
            if (TextUtils.equals(from, "food")) {
                mianCloseImg.setVisibility(View.VISIBLE);
            } else {
                mianCloseImg.setVisibility(View.GONE);
            }


        } catch (Exception e) {
            mianCloseImg.setVisibility(View.GONE);

        }


    }


    private void initView() {
        fragmentManager = getSupportFragmentManager();
        ipsiFragment = new IpsilateralFragment();
        oppoFragment = new OppositeFragment();
        showFragment(0);
        // TODO: 2019/3/29 临时操作，按语音翻译键调起的时候，停止播放景区导览
        Intent intent1 = new Intent("com.aibabel.scenic.stop");
        sendBroadcast(intent1);
    }

    /**
     * 设置网络监听广播
     */
    private void initBroadcast() {
        broadcastReceiver = new NetBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, intentFilter);
        broadcastReceiver.setListener(this);

        mScreenReceiver = new ScreenBroadcastReceiver();
        mScreenReceiver.registerScreenBroadcastReceiver(this);
        mScreenReceiver.setScreenListener(this);
    }


    @Override
    protected void onStart() {
        ChangeOffline.getInstance().getOfflineList();
        super.onStart();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        try {

            String from = intent.getStringExtra("from");
            L.e("MainActivity   onNewIntent=======================Intent:" + from);
            if (TextUtils.equals(from, "food")) {
                mianCloseImg.setVisibility(View.VISIBLE);
            } else {
                mianCloseImg.setVisibility(View.GONE);
            }
            // TODO: 2019/3/29 临时操作，按语音翻译键调起的时候，停止播放景区导览
            Intent intent1 = new Intent("com.aibabel.scenic.stop");
            sendBroadcast(intent1);


        } catch (Exception e) {

            mianCloseImg.setVisibility(View.GONE);
        }
        try {
            //判定是否又长按调起的
            isTranslateStart = intent.getStringExtra("isTranslateStart");
            intent.putExtra("isTranslateStart", "null");
            //按键是上或者下（131或者132）
            isTranslateKeyCode = intent.getIntExtra("isTranslateKeyCode", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onNewIntent(intent);
    }


    /**
     * 长按调起应用
     */
    private void longPressKey(String fun) {

        try {
            Intent intent1 = getIntent();
            //判定是否又长按调起的
            isTranslateStart = intent1.getStringExtra("isTranslateStart");
            getIntent().putExtra("isTranslateStart", "null");
            //按键是上或者下（131或者132）
            isTranslateKeyCode = intent1.getIntExtra("isTranslateKeyCode", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);


        L.e("onWindowFocusChanged isTranslateStart==============" + isTranslateStart);

        if (TextUtils.equals(isTranslateStart, "isTranslateStart")) {
            if (isSleep) {
                return;
            }
            isTranslateStart = "";
            if (TextUtils.equals(CommonUtils.getDeviceInfo(), "PM") && Constant.IS_NEED_SHOW && !CommonUtils.isAvailable()) {
                MyDialog.showDialog(this);
                Constant.IS_NEED_SHOW = false;
                return;
            }
            if (!CommonUtils.isAvailable()) {
                ChangeOffline.getInstance().getOfflineList();
                ChangeOffline.getInstance().createOrChange();

            }

            if (isTranslateKeyCode == 131) {
                showFragment(currentFragment);
//                L.e("当前显示 FRAG======================onWindowFocusChanged");
                if (currentFragment == 0) {
                    ipsiFragment.onKeyDown(131, null);
                } else {
                    oppoFragment.onKeyDown(131, null);
                }

            } else if (isTranslateKeyCode == 132) {
                showFragment(currentFragment);
                if (currentFragment == 0) {
                    ipsiFragment.onKeyDown(132, null);
                } else {
                    oppoFragment.onKeyDown(132, null);
                }
//                L.e("当前显示 FRAG======================onWindowFocusChanged dui");
            }


        } else {
            if (isSleep) {
                return;
            }
            if (!CommonUtils.isAvailable() && hasFocus == true) {
                try {

                    String isF = getIntent().getStringExtra("isTranslateKeyCode");
                    if (isF == null || isF.equals("null")) {
                        getIntent().putExtra("isTranslateKeyCode", "isF");
                        ChangeOffline.getInstance().getOfflineList();

                        ChangeOffline.getInstance().createOrChange();
                        if (TextUtils.equals(CommonUtils.getDeviceInfo(), "PM") && Constant.IS_NEED_SHOW && !CommonUtils.isAvailable()) {
                            MyDialog.showDialog(this);
                            Constant.IS_NEED_SHOW = false;
                            return;
                        }

                    }

                } catch (Exception e) {
                    getIntent().putExtra("isTranslateKeyCode", "isF");
                    ChangeOffline.getInstance().getOfflineList();
                    ChangeOffline.getInstance().createOrChange();

                }
            }

        }


    }

    /**
     * 展示不同的Fragment
     *
     * @param type
     */
    public void showFragment(int type) {

        fragmentTransaction = fragmentManager.beginTransaction();


//        L.e("当前显示 FRAG======================"+type);
        switch (type) {
            case 0://同侧
                fragmentTransaction.replace(R.id.fl_translate, ipsiFragment);
                currentFragment = 0;
                break;
            case 1://异侧
                fragmentTransaction.replace(R.id.fl_translate, oppoFragment);
                currentFragment = 1;
                break;
        }


        fragmentTransaction.commit();
    }


    AlertDialog dialog;

    @Override
    protected void onResume() {

        if (TextUtils.equals(CommonUtils.getDeviceInfo(), "PM") && !Constant.IS_NEED_SHOW && CommonUtils.isAvailable()) {
            MyDialog.dismiss();
        }
//        Constant.IS_NEED_SHOW = true;


//        try {
//           String res= SharePrefUtil.getString(this,"isAgree","");
//            if (!res.equals("true")) {
//                if (dialog==null) {
//                    dialog = new AlertDialog.Builder(this)
//
//                            .setTitle("服务条款")//设置对话框的标题
//                            .setMessage("尊敬的用户，欢迎使用准儿翻译机，在使用前请您仔细阅读以下免责条款，" +
//                                    "如您对本协议中的任何条款表示异议，您可以选择不使用准儿翻译机；但如您使用准儿翻译机，" +
//                                    "您的使用行为将被视为对本协议全部内容的认可，本协议对用户和北京分音塔科技有限公司均具有法律效力。\n" +
//                                    "1. 准儿翻译机—语音翻译以非人工方式提供翻译结果，北京分音塔科技有限公司对翻译结果的正确性、准确性、完整性和合法性不做任何形式的保证，亦不承担任何法律责任。\n" +
//                                    "2. 在您使用语音翻译的时候，可能会获取您部分基本信息，以及网络权限(数据连接或WiFi连接)等等。")//设置对话框的内容
//                            //设置对话框的按钮
//                            .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    System.exit(0);
//                                    dialog.dismiss();
//
//                                }
//                            })
//                            .setPositiveButton("接受", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                    SharePrefUtil.saveString(MainActivity.this, "isAgree", "true");
//
//                                    dialog.dismiss();
//                                }
//                            }).create();
//                }
//                dialog.show();
//            }
//
//        } catch (Exception e) {
//
//        }
        super.onResume();
    }

    /**
     * 隐藏所有的Fragment
     *
     * @param transaction
     */
    private void hideAllFragment(FragmentTransaction transaction) {
        if (ipsiFragment != null) {
            transaction.hide(ipsiFragment);
        }
        if (oppoFragment != null) {
            transaction.hide(oppoFragment);
        }
    }

    /**
     * 获取当前的fragment
     *
     * @return
     */
    public int getCurrentFragment() {
        return currentFragment;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        switch (keyCode) {
            case 24:
            case 25:
                if (currentFragment == 0) {
                    if (null != ipsiFragment && ipsiFragment.getIsRecording()) {
                        return true;
                    }
                }
                if (currentFragment == 1) {
                    if (null != oppoFragment && oppoFragment.getIsRecording()) {
                        return true;
                    }
                }
                break;
            case 133:
//                L.e("133=======================");
                BaseApplication.isIpsil = "";
//                getIntent().putExtra("isTranslateKeyCode","null");
                break;
            case 134:
//                L.e("134=======================");
//                getIntent().putExtra("isTranslateKeyCode","null");
                BaseApplication.isIpsil = "";
                break;
        }

        switch (currentFragment) {
            case 0:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    event.startTracking();
                    if (event.getRepeatCount() == 0) {
                        ipsiFragment.onKeyDown(keyCode, event);
                    }
                }
                break;
            case 1:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    event.startTracking();
                    if (event.getRepeatCount() == 0) {
                        oppoFragment.onKeyDown(keyCode, event);
                    }
                }
                break;

        }


        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch (currentFragment) {
            case 0:
                ipsiFragment.onKeyUp(keyCode, event);
                break;
            case 1:
                oppoFragment.onKeyUp(keyCode, event);
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void netChanged() {
        switch (currentFragment) {
            case 0:
                ipsiFragment.netChanged();
                break;
            case 1:
                oppoFragment.netChanged();
                break;
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
//        L.e("MianActivity  onstop========================================");
//        Constant.IS_NEED_SHOW = true;
        BaseApplication.isIpsil = "";

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void wake() {
        isSleep = false;

    }

    @Override
    public void sleep() {
        isSleep = true;
        BaseApplication.isTran = true;
    }
}
