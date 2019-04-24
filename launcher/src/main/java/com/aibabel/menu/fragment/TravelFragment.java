package com.aibabel.menu.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
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

public class TravelFragment extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.more_travel_app)
    CardView moreTravelApp;
    @BindView(R.id.more_scenic_app)
    CardView moreScenicApp;
    @BindView(R.id.more_country_app)
    CardView moreCountryApp;
    @BindView(R.id.more_food_app)
    CardView moreFoodApp;
    @BindView(R.id.more_poortravel_app)
    CardView morePoortravelApp;

    @Override
    public int getLayout() {
        return R.layout.fragment_more_travel;
    }

    @Override
    public void init(View view, Bundle savedInstanceState) {
        moreTravelApp.setOnClickListener(this);
        moreScenicApp.setOnClickListener(this);
        moreCountryApp.setOnClickListener(this);
        moreFoodApp.setOnClickListener(this);
        morePoortravelApp.setOnClickListener(this);
    }
    public void launcherApp(String packageStr) {
        try {
            startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, packageStr));
        } catch (Exception e) {
            Logs.e(packageStr + ":" + e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_travel_app:
                ((MoreActivity) getActivity()).addStatisticsEvent("menu_fun_click",null);
                launcherApp("com.aibabel.fyt_play");
                break;
            case R.id.more_scenic_app:
                ((MoreActivity) getActivity()).addStatisticsEvent("scenic_click",null);
                launcherApp("com.aibabel.scenic");
                break;
            case R.id.more_country_app:
                ((MoreActivity) getActivity()).addStatisticsEvent("scenic_click",null);
                launcherApp("com.aibabel.scenic");
                break;
            case R.id.more_food_app:
                ((MoreActivity) getActivity()).addStatisticsEvent("menu_areaselect_click",null);
                launcherApp("com.aibabel.food");
                break;
            case R.id.more_poortravel_app:
                ((MoreActivity) getActivity()).addStatisticsEvent("poortravel_click",null);
                launcherApp("com.qyer.android.plan");
                break;
        }
    }
}
