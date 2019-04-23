package com.aibabel.launcher.fragment;

import android.content.ComponentName;
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

public class TourFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.more_outin_app)
    CardView moreOutinApp;
    @BindView(R.id.more_weather_app)
    CardView moreWeatherApp;
    @BindView(R.id.more_timer_app)
    CardView moreTimerApp;
    @BindView(R.id.more_search_app)
    CardView moreSearchApp;
    @BindView(R.id.more_translate_app)
    CardView moreTranslateApp;
    @BindView(R.id.more_menu_app)
    CardView moreMenuApp;
    @BindView(R.id.more_essay_app)
    CardView moreEssayApp;
    @BindView(R.id.more_body_app)
    CardView moreBodyApp;

    @Override
    public int getLayout() {
        return R.layout.fragment_more_tour;
    }

    @Override
    public void init(View view, Bundle savedInstanceState) {
        moreOutinApp.setOnClickListener(this);
        moreWeatherApp.setOnClickListener(this);
        moreTimerApp.setOnClickListener(this);
        moreSearchApp.setOnClickListener(this);
        moreTranslateApp.setOnClickListener(this);
        moreMenuApp.setOnClickListener(this);
        moreEssayApp.setOnClickListener(this);
        moreBodyApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more_outin_app:
                ((MoreActivity) getActivity()).addStatisticsEvent("menu_choice_click",null);
                launcherApp("com.aibabel.fyt_exitandentry");
                break;
            case R.id.more_weather_app:
                ((MoreActivity) getActivity()).addStatisticsEvent("weather_click",null);
                launcherApp("com.aibabel.weather");
                break;
            case R.id.more_timer_app:
                ((MoreActivity) getActivity()).addStatisticsEvent("time_click",null);
                launcherApp("com.aibabel.alliedclock");
                break;
            case R.id.more_search_app:
                ((MoreActivity) getActivity()).addStatisticsEvent("menu_search_click",null);
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName("com.google.android.googlequicksearchbox", "com.google.android.apps.gsa.queryentry.QueryEntryActivity");
                intent.setComponent(cn);
                startActivity(intent);
                break;
            case R.id.more_translate_app:
                ((MoreActivity) getActivity()).addStatisticsEvent("voice_click",null);
                launcherApp("com.aibabel.translate");
                break;
            case R.id.more_menu_app:
                ((MoreActivity) getActivity()).addStatisticsEvent("photo_click",null);
                launcherApp("com.aibabel.ocr");
                break;
            case R.id.more_essay_app:
                ((MoreActivity) getActivity()).addStatisticsEvent("photo_click",null);
                launcherApp("com.aibabel.ocr");
                break;
            case R.id.more_body_app:
                ((MoreActivity) getActivity()).addStatisticsEvent("photo_click",null);
                launcherApp("com.aibabel.ocr");
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
