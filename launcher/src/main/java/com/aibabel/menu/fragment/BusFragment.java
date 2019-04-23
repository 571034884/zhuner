package com.aibabel.menu.fragment;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.menu.activity.MoreActivity;
import com.aibabel.menu.utils.AppStatusUtils;
import com.aibabel.menu.R;
import com.aibabel.menu.utils.Logs;

import butterknife.BindView;

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
                ((MoreActivity) getActivity()).addStatisticsEvent("map_click",null);
                launcherApp("com.aibabel.map");
                break;
            case R.id.more_huilv_app:
                ((MoreActivity) getActivity()).addStatisticsEvent("currency_click",null);
                launcherApp("com.aibabel.currencyconversion");
                break;
            case R.id.more_discount_app:
                ((MoreActivity) getActivity()).addStatisticsEvent("menu_coupon_click",null);
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
