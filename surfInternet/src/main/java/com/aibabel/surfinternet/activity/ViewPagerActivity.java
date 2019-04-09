package com.aibabel.surfinternet.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.surfinternet.R;
import com.aibabel.surfinternet.adapter.ViewPagerAdapter;
import com.aibabel.surfinternet.base.BaseNetActivity;
import com.aibabel.surfinternet.net.Api;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerActivity extends BaseNetActivity {


    @BindView(R.id.fragmentviewpager)
    ViewPager pager;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.ll_point)
    LinearLayout llPoint;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    private boolean next = false;
    private int oldPosition = 0;
    private int currentPosition = 0;
    private LinearLayout.LayoutParams lp_dots_normal, lp_dots_focused;
    private List<View> list;
    private List<TextView> dots = new ArrayList<>();
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;

    @Override
    public int getLayouts(Bundle var1) {
        return R.layout.activity_view_pager;
    }

    @Override
    public void initView() {
        initViewPager();

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (next) {
                    finish();
                } else {
                    pager.setCurrentItem(1);
                }
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }
    //初始化ViewPager的方法
    public void initViewPager() {

        if (Api.PHONE_COUNTRY.equals("CN")) {
            iv1 = new ImageView(this);
            iv1.setImageResource(R.mipmap.help1);
            iv2 = new ImageView(this);
            iv2.setImageResource(R.mipmap.help2);
            iv3 = new ImageView(this);
            iv3.setImageResource(R.mipmap.help3);
        } else if (Api.PHONE_COUNTRY.equals("TW")) {
            iv1 = new ImageView(this);
            iv1.setImageResource(R.mipmap.help11);
            iv2 = new ImageView(this);
            iv2.setImageResource(R.mipmap.help12);
            iv3 = new ImageView(this);
            iv3.setImageResource(R.mipmap.help13);
        }else if (Api.PHONE_LANGUAGE.equals("en")){
            iv1 = new ImageView(this);
            iv1.setImageResource(R.mipmap.help1_en);
            iv2 = new ImageView(this);
            iv2.setImageResource(R.mipmap.help2_en);
            iv3 = new ImageView(this);
            iv3.setImageResource(R.mipmap.help3_en);
        }

        list = new ArrayList<View>();
        list.add(iv1);
        list.add(iv2);
        list.add(iv3);
        pager.setAdapter(new ViewPagerAdapter(list));

        if (list.size() > 1) llPoint.setVisibility(View.VISIBLE);
        else llPoint.setVisibility(View.GONE);
        for (int i = 0; i < (list.size() > 5 ? 5 : list.size()); i++) {
            TextView v_dot = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_for_ocr_viewpager_point, null);
            LinearLayout.LayoutParams docParams;
            if (i == 0) {
                docParams = new LinearLayout.LayoutParams(20, 20);
                v_dot.setBackgroundResource(R.drawable.dot_focused);
                v_dot.setTextSize(10);
//                v_dot.setText("1");
            } else {
                docParams = new LinearLayout.LayoutParams(15, 15);
                v_dot.setBackgroundResource(R.drawable.dot_normal);
            }
            docParams.setMargins(10, 0, 10, 0);
            v_dot.setLayoutParams(docParams);
            llPoint.addView(v_dot);
            dots.add(v_dot);
        }

        //监听ViewPager滑动效果
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                //当识别的部分超过5个时候，只用5个点显示
                if (list.size() > 5) {
                    if (arg0 < 2) {
                        currentPosition = arg0;
                    } else if (list.size() - arg0 > 2) {
                        currentPosition = 2;
                    } else {
                        currentPosition = 5 - (list.size() - arg0);
                    }
                } else {
                    currentPosition = arg0 % list.size();
                }

                //将对应的位置背景变为focus形式
                lp_dots_normal = (LinearLayout.LayoutParams) (dots.get(oldPosition))
                        .getLayoutParams();
                lp_dots_focused = (LinearLayout.LayoutParams) (dots.get(currentPosition))
                        .getLayoutParams();

                lp_dots_normal.width = 15;
                lp_dots_normal.height = 15;
                lp_dots_focused.width = 20;
                lp_dots_focused.height = 20;

                dots.get(oldPosition).setText("");
//                dots.get(currentPosition).setText(arg0 + 1 + "");
                dots.get(currentPosition).setTextSize(10);
                dots.get(oldPosition).setLayoutParams(lp_dots_normal);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                dots.get(currentPosition).setLayoutParams(lp_dots_focused);
                dots.get(currentPosition).setBackgroundResource(R.drawable.dot_focused);
                oldPosition = currentPosition;

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }


}
