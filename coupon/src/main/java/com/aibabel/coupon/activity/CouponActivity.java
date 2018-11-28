package com.aibabel.coupon.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.aibabel.coupon.R;
import com.aibabel.coupon.fragment.CouponFragment;
import com.aibabel.coupon.fragment.DestinationFragment;
import com.aibabel.coupon.utils.MyRadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CouponActivity extends AppCompatActivity {

    @BindView(R.id.fragment)
    FrameLayout fragment;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.tv_mudidi)
    MyRadioButton tvMudidi;
    @BindView(R.id.tv_coupon)
    MyRadioButton tvCoupon;
    @BindView(R.id.rg_number1)
    RadioGroup rgNumber1;
    private FragmentManager fragmentManager;
    private DestinationFragment destinationFragment;
    private CouponFragment couponFragment;
    private FragmentTransaction fragmentTransaction;
    private int currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        // 根据 ReceiveActivity 的 type  确定显示 哪个 Fragment
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        fragmentManager = getSupportFragmentManager();
        destinationFragment = new DestinationFragment();
        couponFragment = new CouponFragment();
        showFragment(type);

    }

    /**
     * 展示不同的Fragment
     *
     * @param type
     */
    public void showFragment(int type) {

        fragmentTransaction = fragmentManager.beginTransaction();


        switch (type) {
            case 0://目的地
                fragmentTransaction.replace(R.id.fragment, destinationFragment);
                currentFragment = 0;
                tvMudidi.setChecked(true);
                tvCoupon.setChecked(false);
//                tvMudidi.setCompoundDrawables(getResources().getDrawable(R.mipmap.mudidi_yes),null,null,null);
                tvMudidi.setTextColor(Color.parseColor("#5d5d5d"));
                tvCoupon.setTextColor(Color.parseColor("#c2c2c2"));
//                tvCoupon.setCompoundDrawables(getResources().getDrawable(R.mipmap.my_coupon_no),null,null,null);
                break;
            case 1://购物车
                fragmentTransaction.replace(R.id.fragment, couponFragment);
                currentFragment = 1;
                tvCoupon.setChecked(true);
                tvMudidi.setChecked(false);
//                tvMudidi.setCompoundDrawables(getResources().getDrawable(R.mipmap.mudidi_no),null,null,null);
                tvCoupon.setTextColor(Color.parseColor("#5d5d5d"));
                tvMudidi.setTextColor(Color.parseColor("#c2c2c2"));
//                tvCoupon.setCompoundDrawables(getResources().getDrawable(R.mipmap.my_coupon_yes),null,null,null);

                break;
        }

        fragmentTransaction.commit();
    }




    @OnClick({R.id.tv_mudidi, R.id.tv_coupon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_mudidi:
                showFragment(0);
                break;
            case R.id.tv_coupon:
                showFragment(1);
                break;
        }
    }
}
