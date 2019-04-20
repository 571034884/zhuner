package com.aibabel.launcher.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.launcher.R;
import com.aibabel.launcher.activity.H5Activity;
import com.aibabel.launcher.utils.Logs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fytworks on 2019/4/18.
 */

public class ShopFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.more_car_app)
    CardView moreCarApp;
    @BindView(R.id.more_coupon_app)
    CardView moreCouponApp;
    @BindView(R.id.more_foodshop_app)
    CardView moreFoodshopApp;
    @BindView(R.id.more_insure_app)
    CardView moreInsureApp;

    @Override
    public int getLayout() {
        return R.layout.fragment_more_shop;
    }

    @Override
    public void init(View view, Bundle savedInstanceState) {
        moreCarApp.setOnClickListener(this);
        moreCouponApp.setOnClickListener(this);
        moreFoodshopApp.setOnClickListener(this);
        moreInsureApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more_car_app:
                launcherApp("http://www.baidu.com");
                break;
            case R.id.more_coupon_app:
                launcherApp("http://www.baidu.com");
                break;
            case R.id.more_foodshop_app:
                launcherApp("http://www.baidu.com");
                break;
            case R.id.more_insure_app:
                launcherApp("http://www.baidu.com");
                break;
        }
    }
    public void launcherApp(String packageStr) {
        Intent intent = new Intent(mContext, H5Activity.class);
        intent.putExtra("url",packageStr);
        startActivity(intent);
    }

}
