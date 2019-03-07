package com.aibabel.translate;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aibabel.translate.activity.BaseActivity;
import com.aibabel.translate.adapter.LeftSetAdapter;
import com.aibabel.translate.app.BaseApplication;
import com.aibabel.translate.broadcast.NetBroadcastReceiver;
import com.aibabel.translate.broadcast.ScreenBroadcastReceiver;
import com.aibabel.translate.fragment.AiFragment;
import com.aibabel.translate.fragment.BaseFragment;
import com.aibabel.translate.fragment.IpsilateralFragment;
import com.aibabel.translate.fragment.OppositeFragment;
import com.aibabel.translate.offline.ChangeOffline;
import com.aibabel.translate.utils.CommonUtils;
import com.aibabel.translate.utils.Constant;
import com.aibabel.translate.utils.L;
import com.aibabel.translate.utils.MyDialog;
import com.aibabel.translate.utils.SharePrefUtil;
import com.aibabel.translate.view.DragLayout;
import com.aibabel.translate.view.MyLinearLayout;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements NetBroadcastReceiver.NetListener, ScreenBroadcastReceiver.ScreenListener {

    @BindView(R.id.fl_translate)
    FrameLayout flTranslate;
    @BindView(R.id.mian_close_img)
    ImageView mianCloseImg;
    @BindView(R.id.lv_left)
    ListView mLeftList;
//    @BindView(R.id.ml_layout)
//    MyLinearLayout mLinearLayout;
    @BindView(R.id.tv_menu)
    TextView tvMenu;
    @BindView(R.id.dl)
    DragLayout mDragLayout;


    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private IpsilateralFragment ipsiFragment;
    private OppositeFragment oppoFragment;
    private AiFragment aiFragment;
    private BaseFragment currentFragment;
    /**
     * 当前fragment标记，默认为0，语音翻译同侧模式
     */
    private int currentFragmentIndex = 0;
    /**
     * 网络变化广播监听
     */
    public NetBroadcastReceiver broadcastReceiver;
    /**
     * 屏幕变化广播
     */
    private ScreenBroadcastReceiver mScreenReceiver;
    /**
     * 判定是否为长安物理键调起
     */
    private String isTranslateStart;
    /**
     * 调起的code
     */
    private int isTranslateKeyCode;
    /**
     * 是否处于息屏状态
     */
    private boolean isSleep = false;

    private LeftSetAdapter adapter = null;
    private Integer menuTitle[] = new Integer[]{R.string.m_translate, R.string.m_translate_ai, R.string.m_history};
    private int lastIndex = 0;


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
        aiFragment = new AiFragment();
//        showFragment(0);
        initData();
    }


    /**
     * 数据的填充
     */
    public void initData() {

        //左面板侧拉，内部数据填充
        adapter = new LeftSetAdapter(Arrays.asList(menuTitle), mContext);
        mLeftList.setAdapter(adapter);

        mLeftList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        showFragment(lastIndex);
                        break;
                    case 1:
                        if (currentFragmentIndex == 1 || currentFragmentIndex == 0)
                            lastIndex = currentFragmentIndex;
                        showFragment(2);
                        SharePrefUtil.saveBoolean(mContext, "type", true);
                        break;
                    case 2:
                        if (currentFragmentIndex == 1 || currentFragmentIndex == 0)
                            lastIndex = currentFragmentIndex;
                        if (currentFragmentIndex == 0) {
                            ipsiFragment.toRecord();
                        } else if (currentFragmentIndex == 1) {
                            oppoFragment.toRecord();
                        }
                        break;
                }
                mDragLayout.close();
            }
        });

        //首页图标的动效
//        mLinearLayout.setDraglayout(mDragLayout);

        mDragLayout.setDragStatusListener(new DragLayout.OnDragStatusChangeListener() {
            @Override
            public void onClose() {
                switchIcon(currentFragmentIndex,false);
            }

            @Override
            public void onOpen() {
                switchIcon(currentFragmentIndex,true);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onDraging(float percent) {

            }
        });

        showFragment(SharePrefUtil.getInt(this, "translateType", 0));
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
                showFragment(currentFragmentIndex);
//                L.e("当前显示 FRAG======================onWindowFocusChanged");
                if (currentFragmentIndex == 0) {
                    ipsiFragment.onKeyDown(131, null);
                } else if (currentFragmentIndex == 1) {
                    oppoFragment.onKeyDown(131, null);
                } else if (currentFragmentIndex == 2) {
                    aiFragment.onKeyDown(131, null);
                }

            } else if (isTranslateKeyCode == 132) {
                showFragment(currentFragmentIndex);
                if (currentFragmentIndex == 0) {
                    ipsiFragment.onKeyDown(132, null);
                } else if (currentFragmentIndex == 1) {
                    oppoFragment.onKeyDown(132, null);
                } else if (currentFragmentIndex == 2) {
                    aiFragment.onKeyDown(132, null);
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
                currentFragmentIndex = 0;
                currentFragment = ipsiFragment;
                break;
            case 1://异侧
                fragmentTransaction.replace(R.id.fl_translate, oppoFragment);
                currentFragmentIndex = 1;
                currentFragment = oppoFragment;
                break;
            case 2://智能翻译
                fragmentTransaction.replace(R.id.fl_translate, aiFragment);
                currentFragmentIndex = 2;
                currentFragment = aiFragment;
                break;
        }
        save(currentFragmentIndex);
        mDragLayout.close();
        fragmentTransaction.commit();
    }


    /**
     * 保存当前翻译类型
     *
     * @param type
     */
    private void save(int type) {
        SharePrefUtil.saveInt(this, "translateType", type);
    }


    @Override
    protected void onResume() {

        if (TextUtils.equals(CommonUtils.getDeviceInfo(), "PM") && !Constant.IS_NEED_SHOW && CommonUtils.isAvailable()) {
            MyDialog.dismiss();
        }
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
        return currentFragmentIndex;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        switch (keyCode) {
            case 24:
            case 25:
                if (currentFragmentIndex == 0) {
                    if (null != ipsiFragment && ipsiFragment.getIsRecording()) {
                        return true;
                    }
                }
                if (currentFragmentIndex == 1) {
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

        switch (currentFragmentIndex) {
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

        switch (currentFragmentIndex) {
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
        switch (currentFragmentIndex) {
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

    /**
     * 打开或关闭侧滑
     */
    public void drag() {
        if (mDragLayout.getStatus().equals(DragLayout.Status.Close)) {
            mDragLayout.open();
        } else {
            mDragLayout.close();
        }
    }


    public void switchIcon(int type,boolean isOpen){
        switch (type){
            case 0:
                ipsiFragment.switchMenuIcon(isOpen);
                break;
            case 1:
                oppoFragment.switchMenuIcon(isOpen);
                break;
            case 2:
                aiFragment.switchMenuIcon(isOpen);
                break;
        }
    }


}
