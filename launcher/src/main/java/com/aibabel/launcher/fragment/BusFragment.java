package com.aibabel.launcher.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.launcher.utils.AppStatusUtils;
import com.aibabel.menu.R;
import com.aibabel.launcher.utils.Logs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fytworks on 2019/4/18.
 */

public class BusFragment extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.more_map_app)
    CardView moreMapApp;
    @BindView(R.id.more_huilv_app)
    CardView moreHuilvApp;
    @BindView(R.id.more_discount_app)
    CardView moreDiscountApp;

    @Override
    public int getLayout() {
        return R.layout.fragment_more_bus;
    }

    @Override
    public void init(View view, Bundle savedInstanceState) {
        moreMapApp.setOnClickListener(this);
        moreHuilvApp.setOnClickListener(this);
        moreDiscountApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more_map_app:
                launcherApp("com.aibabel.map");
                break;
            case R.id.more_huilv_app:
                launcherApp("com.aibabel.currencyconversion");
                break;
            case R.id.more_discount_app:
                launcherApp("com.aibabel.coupon");
                break;
        }
    }

    public void launcherApp(String packageStr) {
        try {
            startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, packageStr));
        } catch (Exception e) {
            Logs.e(packageStr + ":" + e.toString());
        }
    }
}
