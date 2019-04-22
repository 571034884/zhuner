package com.aibabel.launcher.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.launcher.R;
import com.aibabel.launcher.utils.Logs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fytworks on 2019/4/18.
 */

public class AboutFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.more_readme_app)
    CardView moreReadmeApp;
    @BindView(R.id.more_download_app)
    CardView moreDownloadApp;
    @BindView(R.id.more_customer_app)
    CardView moreCustomerApp;
    @BindView(R.id.more_setting_food)
    CardView moreSettingFood;

    @Override
    public int getLayout() {
        return R.layout.fragment_more_about;
    }

    @Override
    public void init(View view, Bundle savedInstanceState) {
        moreReadmeApp.setOnClickListener(this);
        moreDownloadApp.setOnClickListener(this);
        moreCustomerApp.setOnClickListener(this);
        moreSettingFood.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more_readme_app:
                launcherApp("com.aibabel.readme");
                break;
            case R.id.more_download_app:
                launcherApp("com.aibabel.download.offline");
                break;
            case R.id.more_customer_app:
                launcherApp("com.aibabel.tucao");
                break;
            case R.id.more_setting_food:
                launcherApp("com.zhuner.administrator.settings");
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
