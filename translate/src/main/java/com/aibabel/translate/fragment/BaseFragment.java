package com.aibabel.translate.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.aibabel.translate.MainActivity;
import com.aibabel.translate.utils.L;


public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    //按键keycode
    protected final static int UP_KEY = 132;
    protected final static int DOWN_KEY = 131;
    //这个activity就是MainActivity
    public MainActivity activity;

    public static boolean isOpen = false;

    // Fragment被创建
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();// 获取所在的activity对象
        L.e("BaseFragment onCreate===================");
    }


    // activity创建结束
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.e("BaseFragment onActivityCreated===================");
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        L.e("basefragment onViewCreated============================");
        initView();
        initData();
    }

    /**
     * 初始化数据, 子类可以不实现
     */
    public void initView() {

    }


    /**
     * 初始化数据, 子类可以不实现
     */
    public void initData() {

    }

    @Override
    public void onClick(View v) {
//        if (SystemClock.uptimeMillis() - clickTime <= 1500) {
//            //如果两次的时间差＜1s，就不执行操作
//
//        } else {
//            //当前系统时间的毫秒值
//            clickTime = SystemClock.uptimeMillis();
//            Toast.makeText(MainUI.this, "再次点击退出", Toast.LENGTH_SHORT).show();
//            return false;
//        }
    }





}
