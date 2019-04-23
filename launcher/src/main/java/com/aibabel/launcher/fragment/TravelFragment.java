package com.aibabel.launcher.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.launcher.activity.MoreActivity;
import com.aibabel.launcher.utils.AppStatusUtils;
import com.aibabel.menu.R;
import com.aibabel.launcher.utils.Logs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
                try {
                    ((MoreActivity) getActivity()).addStatisticsEvent("poortravel_click",null);
                    Intent LaunchIntent = mContext.getPackageManager().getLaunchIntentForPackage("com.qyer.android.plan");
                    startActivity(LaunchIntent);
                } catch (Exception e) {
                    Logs.e(  "com.qyer.android.plan:" + e.toString());
                }

                break;
        }
    }
}
