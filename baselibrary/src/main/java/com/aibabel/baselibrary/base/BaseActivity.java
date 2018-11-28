package com.aibabel.baselibrary.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：SunSH on 2018/11/13 17:26
 * 功能：
 * 版本：1.0
 */
public abstract class BaseActivity extends AppCompatActivity {

    public Unbinder mUnbinder;
    public Context mContext;
    public boolean isExit = false;

    private FragmentListener fragmentListener;

    public static final int ADD = 1;
    public static final int REPLACE = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setNavigationBarVisibility(false);

        setContentView(getLayout(savedInstanceState));
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        init();
    }

    /**
     * 忽略物理按键退出
     */
    public void ignorePhysicalButtonsExit() {
        isExit = false;
    }

    /**
     * 设置物理键关闭程序
     *
     * @return boolean
     */
    public void setPhysicalButtonsExit() {
        isExit = true;
    }

    /**
     * 获取布局
     *
     * @param savedInstanceState
     * @return R.layout.xxxxLayout
     */
    public abstract int getLayout(Bundle savedInstanceState);

    /**
     * 初始化其他内容
     */
    public abstract void init();

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    /**
     * 设置导航栏显示状态
     *
     * @param visible
     */
    private void setNavigationBarVisibility(boolean visible) {
        int flag = 0;
        if (!visible) {
            flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        getWindow().getDecorView().setSystemUiVisibility(flag);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    public void startActivity(Class cls) {
        startActivity(new Intent(mContext, cls));
    }

    public void startActivityForResult(Class cls, int requestCode) {
        startActivityForResult(new Intent(mContext, cls), requestCode);
    }

    public void setFragmentListener(FragmentListener listener) {
        this.fragmentListener = listener;
    }

    /**
     * 布局中Fragment的ID，没有Fragment可以不做更改
     */
    public interface FragmentListener {
        int getFragmentViewId();

    }

    /**
     * 初始化fragment
     */
    protected void initFragment(BaseFragment fragment, int way) {
        if (fragmentListener == null) {
            Logger.e("未设置fragmentListener");
            return;
        }
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (way == ADD) {
                transaction.add(fragmentListener.getFragmentViewId(), fragment, fragment.getClass().getSimpleName());
            } else {
                transaction.replace(fragmentListener.getFragmentViewId(), fragment, fragment.getClass().getSimpleName())
                        .addToBackStack(fragment.getClass().getSimpleName());//添加回退栈
            }
            transaction.commitAllowingStateLoss();
        }
    }

    /**
     * 初始化fragment:add方式
     */
    protected void addFragment(BaseFragment fragment) {
        initFragment(fragment, ADD);
    }

    /**
     * 初始化fragment:replace方式
     */
    protected void replaceFragment(BaseFragment fragment) {
        initFragment(fragment, REPLACE);
    }

    /**
     * 移除fragment,集合replace方式，返回使用
     */
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    /**
     * 展示目标fragment
     *
     * @param fragment
     */
    public void showFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction().show(fragment).commitAllowingStateLoss();
    }

    /**
     * 隐藏目标fragment
     *
     * @param fragment
     */
    public void hideFragment(BaseFragment fragment) {
        if (fragment != null)
            getSupportFragmentManager().beginTransaction().hide(fragment).commitAllowingStateLoss();
    }

    /**
     * 获取已经存在的fragment
     *
     * @param fragment
     * @return
     */
    public BaseFragment getExistingFragment(BaseFragment fragment) {
        return (BaseFragment) getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isExit) {
            switch (keyCode) {
                case 133:
                case 134:
                    BaseApplication.exit();
                    break;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
