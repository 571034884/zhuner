package com.aibabel.launcher.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.launcher.R;
import com.aibabel.launcher.utils.Logs;

/**
 * Created by fytworks on 2019/4/18.
 */

public class TravelFragment extends BaseFragment{

    @Override
    public int getLayout() {
        return R.layout.fragment_more_travel;
    }

    @Override
    public void init(View view, Bundle savedInstanceState) {

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.more_travel_app:
                launcherApp("com.aibabel.fyt_play");
                break;
            case R.id.more_scenic_app:
                launcherApp("com.aibabel.scenic");
                break;
            case R.id.more_country_app:
                launcherApp("com.aibabel.scenic");
                break;
            case R.id.more_food_app:
                launcherApp("com.aibabel.food");
                break;
        }

    }

    public void launcherApp(String packageStr) {
        try {
            Intent LaunchIntent = mContext.getPackageManager().getLaunchIntentForPackage(packageStr);
            startActivity(LaunchIntent);
        } catch (Exception e) {
            Logs.e(packageStr + ":" + e.toString());
        }
    }

}
