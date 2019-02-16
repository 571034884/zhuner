package com.aibabel.map.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.map.R;
import com.aibabel.map.bean.RouteBean;

/**
 * Created by fytworks on 2018/12/26.
 */

@SuppressLint("ValidFragment")
public class WalkFragment extends BaseFragment{

    private Context mContext;
    RouteBean routeBean;

    @SuppressLint("ValidFragment")
    public WalkFragment(Bundle bundle) {
        routeBean = bundle.getParcelable("routeBean");
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_walk;
    }

    @Override
    public void init(View view, Bundle bundle) {
        mContext = getContext();
    }
}
