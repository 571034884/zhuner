package com.aibabel.coupon.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.coupon.R;
import com.aibabel.coupon.bean.Constans;
import com.aibabel.coupon.fragment.CouponFragment;
import com.aibabel.coupon.fragment.DestinationFragment;
import com.aibabel.coupon.utils.ContentProviderUtil;
import com.aibabel.coupon.utils.MyRadioButton;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CouponActivity extends BaseActivity {

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
    private final Uri CONTENT_URI = Uri.parse("content://com.dommy.qrcode/aibabel_information");
    String dev_oid, dev_uid, dev_uname, dev_sn ,dev_d;
    private String pro_version;


    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_coupon;
    }

    @Override
    public void init() {
        initView();
    }

    private void initView() {


        Constans.HOST_XW = ContentProviderUtil.getHost(this);
        // 根据 ReceiveActivity 的 type  确定显示 哪个 Fragment
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        fragmentManager = getSupportFragmentManager();
        destinationFragment = new DestinationFragment();
        couponFragment = new CouponFragment();
        showFragment(type);
        setHotRepairEnable(true, 5);
        String display = Build.DISPLAY;
        Constans.PRO_VERSION = display.substring(9, 10);
        initDictionary();

    }


    private void initDictionary() {
        try {
            Cursor cursor = CouponActivity.this.getContentResolver().query(CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Constans.PRO_DEV_OID = cursor.getString(cursor.getColumnIndex("oid"));
                    Log.e("dev_oid", Constans.PRO_DEV_OID);
                    dev_uid = cursor.getString(cursor.getColumnIndex("uid"));
                    Log.e("dev_oid", dev_uid);
                    dev_uname = cursor.getString(cursor.getColumnIndex("uname"));
                    Log.e("dev_oid——", dev_uname);
                    dev_sn = cursor.getString(cursor.getColumnIndex("sn"));
                    Log.e("dev_oid————", dev_sn);
                    dev_d = cursor.getString(cursor.getColumnIndex("d"));
                    Log.e("dev_oid——————", dev_d);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("wzf", "tianxia=" + e.getMessage());
        }
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
                tvMudidi.setTextColor(Color.parseColor("#000000"));
                tvCoupon.setTextColor(Color.parseColor("#959595"));
//                tvCoupon.setCompoundDrawables(getResources().getDrawable(R.mipmap.my_coupon_no),null,null,null);

                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("coupon_Details7", null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                break;
            case 1://购物车
                fragmentTransaction.replace(R.id.fragment, couponFragment);
                currentFragment = 1;
                tvCoupon.setChecked(true);
                tvMudidi.setChecked(false);
//                tvMudidi.setCompoundDrawables(getResources().getDrawable(R.mipmap.mudidi_no),null,null,null);
                tvCoupon.setTextColor(Color.parseColor("#000000"));
                tvMudidi.setTextColor(Color.parseColor("#959595"));
//                tvCoupon.setCompoundDrawables(getResources().getDrawable(R.mipmap.my_coupon_yes),null,null,null);
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("coupon_Details8", null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                break;
        }

        fragmentTransaction.commit();
    }


    @OnClick({R.id.tv_mudidi, R.id.tv_coupon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_mudidi:
                showFragment(0);
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("coupon_main2", null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                break;
            case R.id.tv_coupon:
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("coupon_main3", null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                showFragment(1);
                break;
        }
    }
}
