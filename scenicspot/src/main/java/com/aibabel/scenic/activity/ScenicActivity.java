package com.aibabel.scenic.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.scenic.R;
import com.aibabel.scenic.base.BaseScenicActivity;
import com.aibabel.scenic.fragment.ScenicCollectFragment;
import com.aibabel.scenic.fragment.ScenicHotFragment;
import com.aibabel.scenic.fragment.ScenicNearFragment;

import butterknife.BindView;

/**
 * ==========================================================================================
 *
 * @Author： 张文颖
 * @Date：2019/3/25
 * @Desc：景区列表
 * ==========================================================================================
 */
public class ScenicActivity extends BaseScenicActivity {

    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_hot)
    TextView tvHot;
    @BindView(R.id.v_hot)
    View vHot;
    @BindView(R.id.ll_hot)
    LinearLayout llHot;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.v_about)
    View vAbout;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.v_collect)
    View vCollect;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
    @BindView(R.id.fl_fragment)
    FrameLayout flFragment;
    //0 热门   1附近   2收藏
    private int flagIndex = -1;
    private String cityName;
    private int index;


    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private ScenicCollectFragment scenicCollectFragment;
    private ScenicHotFragment scenicHotFragment;
    private ScenicNearFragment scenicNearFragment;
    private int currentFragment;

    @Override
    public int getLayouts(Bundle var1) {
        return R.layout.activity_scenic;
    }

    @Override
    public void initView() {
        cityName = getIntent().getStringExtra("cityName");
        index = getIntent().getIntExtra("index", 0);
        llHot.setOnClickListener(this);
        llAbout.setOnClickListener(this);
        llCollect.setOnClickListener(this);
        tvLeft.setOnClickListener(this);
        tvTitle.setText(R.string.title_scenics);
        fragmentManager = getSupportFragmentManager();
        scenicCollectFragment = new ScenicCollectFragment(cityName);
        scenicHotFragment = new ScenicHotFragment(cityName);
        scenicNearFragment = new ScenicNearFragment(cityName);
        getShowView(index);
    }


    @Override
    public void initData() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                this.finish();
                break;
            case R.id.ll_hot://热门
                getShowView(0);
                break;
            case R.id.ll_about://附近
                getShowView(1);
                break;
            case R.id.ll_collect://收藏
                getShowView(2);
                break;
        }
    }


    private void getShowView(int index) {
        if (flagIndex == index) {
            return;
        }
        flagIndex = index;
        defaultViewType(index);
        //显示fragment
        showFragment(index);
//        ToastUtil.showShort(mContext, "展示当前选中：" + flagIndex);
    }

    /**
     * 展示不同的Fragment
     *
     * @param type
     */
    public void showFragment(int type) {

        fragmentTransaction = fragmentManager.beginTransaction();

        switch (type) {
            case 0://热门
                fragmentTransaction.replace(R.id.fl_fragment, scenicHotFragment);
                currentFragment = 0;
                break;
            case 1://附近
                fragmentTransaction.replace(R.id.fl_fragment, scenicNearFragment);
                currentFragment = 1;
                break;
            case 2://收藏
                fragmentTransaction.replace(R.id.fl_fragment, scenicCollectFragment);
                currentFragment = 2;
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 重置状态
     *
     * @param index
     */
    private void defaultViewType(int index) {
        tvHot.setTextSize(15);
        tvHot.setTextColor(getResources().getColor(R.color.c_666666));
        tvHot.getPaint().setFakeBoldText(false);

        tvAbout.setTextSize(15);
        tvAbout.setTextColor(getResources().getColor(R.color.c_666666));
        tvAbout.getPaint().setFakeBoldText(false);

        tvCollect.setTextSize(15);
        tvCollect.setTextColor(getResources().getColor(R.color.c_666666));
        tvCollect.getPaint().setFakeBoldText(false);

        vHot.setVisibility(View.INVISIBLE);
        vAbout.setVisibility(View.INVISIBLE);
        vCollect.setVisibility(View.INVISIBLE);

        switch (index) {
            case 0:
                tvHot.setTextSize(17);
                tvHot.setTextColor(getResources().getColor(R.color.c_ff781d));
                tvHot.getPaint().setFakeBoldText(true);
                vHot.setVisibility(View.VISIBLE);
                break;
            case 1:
                tvAbout.setTextSize(17);
                tvAbout.setTextColor(getResources().getColor(R.color.c_ff781d));
                tvAbout.getPaint().setFakeBoldText(true);
                vAbout.setVisibility(View.VISIBLE);
                break;
            case 2:
                tvCollect.setTextSize(17);
                tvCollect.setTextColor(getResources().getColor(R.color.c_ff781d));
                tvCollect.getPaint().setFakeBoldText(true);
                vCollect.setVisibility(View.VISIBLE);
                break;
        }

    }

}
