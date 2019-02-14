package com.aibabel.baselibrary.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：SunSH on 2018/11/15 11:33
 * 功能：
 * 版本：1.0
 */
public abstract class BaseFragment extends Fragment {

    Unbinder unbinder;
    //构造方法参数的key
    public static final String PARAM = "param";
    public Context mContext;

    public BaseFragment() {

    }

    public BaseFragment(String param) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PARAM, param);
        setArguments(bundle);
    }

    public BaseFragment(Bundle bundle) {
        setArguments(bundle);
    }

    /**
     * 获取Fragment布局
     *
     * @return
     */
    public abstract int getLayout();

    /**
     * 逻辑代码
     *
     * @param view
     * @param savedInstanceState
     */
    public abstract void init(View view, Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        init(view, savedInstanceState);
        mContext = getContext();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void startActivity(Class cls) {
        startActivity(new Intent(mContext, cls));
    }

    public void startActivityForResult(Class cls, int requestCode) {
        startActivityForResult(new Intent(mContext, cls), requestCode);
    }
}
