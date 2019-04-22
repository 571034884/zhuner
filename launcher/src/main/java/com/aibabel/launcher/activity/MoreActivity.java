package com.aibabel.launcher.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.menu.R;
import com.aibabel.launcher.base.LaunBaseActivity;
import com.aibabel.launcher.fragment.AboutFragment;
import com.aibabel.launcher.fragment.BusFragment;
import com.aibabel.launcher.fragment.MyFragment;
import com.aibabel.launcher.fragment.ShopFragment;
import com.aibabel.launcher.fragment.TourFragment;
import com.aibabel.launcher.fragment.TravelFragment;
import com.aibabel.launcher.utils.Logs;
import com.aibabel.launcher.view.BlurTransformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;

/**
 * Created by fytworks on 2019/4/17.
 */

public class MoreActivity extends LaunBaseActivity {

    @BindView(R.id.iv_travel)
    ImageView ivTravel;
    @BindView(R.id.tv_travel)
    TextView tvTravel;
    @BindView(R.id.rl_travel)
    RelativeLayout rlTravel;
    @BindView(R.id.iv_bus)
    ImageView ivBus;
    @BindView(R.id.tv_bus)
    TextView tvBus;
    @BindView(R.id.rl_bus)
    RelativeLayout rlBus;
    @BindView(R.id.iv_shop)
    ImageView ivShop;
    @BindView(R.id.tv_shop)
    TextView tvShop;
    @BindView(R.id.rl_shop)
    RelativeLayout rlShop;
    @BindView(R.id.iv_tour)
    ImageView ivTour;
    @BindView(R.id.tv_tour)
    TextView tvTour;
    @BindView(R.id.rl_tour)
    RelativeLayout rlTour;
    @BindView(R.id.iv_my)
    ImageView ivMy;
    @BindView(R.id.tv_my)
    TextView tvMy;
    @BindView(R.id.rl_my)
    RelativeLayout rlMy;
    @BindView(R.id.iv_about)
    ImageView ivAbout;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    @BindView(R.id.iv_more_gs)
    ImageView ivMoreGS;

    private FragmentManager fragmentManager;
    private AboutFragment aboutFragment;
    private BusFragment busFragment;
    private MyFragment myFragment;
    private ShopFragment shopFragment;
    private TourFragment tourFragment;
    private TravelFragment travelFragment;
    private Fragment fragment;

    @Override
    public int getLayout(Bundle savedInstanceState) {
        return R.layout.activity_more;
    }

    @Override
    protected void initView() {
        String addressUrl = mmkv.decodeString("addressUrl","null");
        RequestOptions options = new RequestOptions().bitmapTransform(new BlurTransformation(this, 14, 3));
        if (addressUrl.equals("null")){
            int topPic = R.mipmap.ic_top_default;
            Glide.with(mContext).load(topPic).apply(options).into(ivMoreGS);
        }else{
            Glide.with(mContext).load(addressUrl).apply(options).into(ivMoreGS);
        }

        switch (mmkv.decodeString("moreThree","0")){
            case "0":
                rlShop.setVisibility(View.GONE);
                break;
            case "1":
                rlShop.setVisibility(View.VISIBLE);
                break;
        }

        fragmentManager = getSupportFragmentManager();
        switchSelect(rlTravel,ivTravel,tvTravel);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_close:
                closeAct();
                break;
            case R.id.rl_travel:
                defaultType();
                switchSelect(rlTravel,ivTravel,tvTravel);
                break;
            case R.id.rl_bus:
                defaultType();
                switchSelect(rlBus,ivBus,tvBus);
                break;
            case R.id.rl_shop:
                defaultType();
                switchSelect(rlShop,ivShop,tvShop);
                break;
            case R.id.rl_tour:
                defaultType();
                switchSelect(rlTour,ivTour,tvTour);
                break;
            case R.id.rl_my:
                defaultType();
                switchSelect(rlMy,ivMy,tvMy);
                break;
            case R.id.rl_about:
                defaultType();
                switchSelect(rlAbout,ivAbout,tvAbout);
                break;

        }
    }

    public void defaultType(){
        rlTravel.setBackgroundResource(R.drawable.more_default_bg);
        rlBus.setBackgroundResource(R.drawable.more_default_bg);
        rlShop.setBackgroundResource(R.drawable.more_default_bg);
        rlTour.setBackgroundResource(R.drawable.more_default_bg);
        rlMy.setBackgroundResource(R.drawable.more_default_bg);
        rlAbout.setBackgroundResource(R.drawable.more_default_bg);

        ivTravel.setImageResource(R.mipmap.ic_travel_select);
        ivBus.setImageResource(R.mipmap.ic_bus_select);
        ivShop.setImageResource(R.mipmap.ic_shop_select);
        ivTour.setImageResource(R.mipmap.ic_tour_select);
        ivMy.setImageResource(R.mipmap.ic_my_select);
        ivAbout.setImageResource(R.mipmap.ic_about_select);

        tvTravel.setTextColor(getResources().getColor(R.color.color_ff8b01));
        tvBus.setTextColor(getResources().getColor(R.color.color_ff8b01));
        tvShop.setTextColor(getResources().getColor(R.color.color_ff8b01));
        tvTour.setTextColor(getResources().getColor(R.color.color_ff8b01));
        tvMy.setTextColor(getResources().getColor(R.color.color_ff8b01));
        tvAbout.setTextColor(getResources().getColor(R.color.color_ff8b01));
    }

    public void switchSelect(RelativeLayout rl,ImageView iv,TextView tv){
        rl.setBackgroundResource(R.drawable.more_select_bg);
        tv.setTextColor(getResources().getColor(R.color.color_ffffff));
        int draw = 0;
        switch (iv.getId()){
            case R.id.iv_travel:
                draw = R.mipmap.ic_travel_default;
                break;
            case R.id.iv_bus:
                draw = R.mipmap.ic_bus_default;
                break;
            case R.id.iv_shop:
                draw = R.mipmap.ic_shop_default;
                break;
            case R.id.iv_tour:
                draw = R.mipmap.ic_tour_default;
                break;
            case R.id.iv_my:
                draw = R.mipmap.ic_my_default;
                break;
            case R.id.iv_about:
                draw = R.mipmap.ic_about_default;
                break;
        }
        if (draw != 0){
            iv.setImageResource(draw);
            switchFragment(rl);
        }
    }

    private void switchFragment(RelativeLayout index) {
        switch (index.getId()){
            case R.id.rl_travel:
                fragment = new TravelFragment();
                break;
            case R.id.rl_bus:
                fragment = new BusFragment();
                break;
            case R.id.rl_shop:
                fragment = new ShopFragment();
                break;
            case R.id.rl_tour:
                fragment = new TourFragment();
                break;
            case R.id.rl_my:
                fragment = new MyFragment();
                break;
            case R.id.rl_about:
                fragment = new AboutFragment();
                break;
        }
        switchChange(fragment);
    }

    private void switchChange(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.more_scheme, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 133:
                Logs.e("走了onKeyDown");
                closeAct();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
