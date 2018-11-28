package com.aibabel.baselibrary.dialog;

import android.view.View;

import com.aibabel.baselibrary.R;

import kale.ui.view.dialog.BaseCustomDialog;

/**
 * 作者：SunSH on 2018/11/12 14:56
 * 功能：设置自定义布局对话框
 * 版本：1.0
 */
public class MyLoadingDialog extends BaseCustomDialog {
    @Override
    protected int getLayoutResId() {
        return R.layout.loading_layout;
    }

    @Override
    protected void bindViews(View root) {

    }

    @Override
    protected void setViews() {

    }
}
