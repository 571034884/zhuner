package com.aibabel.readme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.readme.R;
import com.aibabel.readme.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerActivity extends BaseActivity {

    @BindView(R.id.fragmentviewpager)
    ViewPager pager;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.ll_point)
    LinearLayout llPoint;

    private int[] sos = {R.mipmap.sos1, R.mipmap.sos2};
    private int[] huilu = {R.mipmap.huilv1};
    private int[] jinqu = {R.mipmap.jinqu1, R.mipmap.jinqu2, R.mipmap.jinqu3, R.mipmap.jinqu4, R.mipmap.jinqu5, R.mipmap.jinqu6, R.mipmap.jinqu7, R.mipmap.jinqu8};
    private int[] quanqiu = {R.mipmap.quanqiu1, R.mipmap.quanqiu2, R.mipmap.quanqiu3, R.mipmap.quanqiu4, R.mipmap.quanqiu5, R.mipmap.quanqiu6, R.mipmap.quanqiu7};
    private int[] mudidi = {R.mipmap.mudidi1, R.mipmap.mudidi2, R.mipmap.mudidi3};
    private int[] paizhao = {R.mipmap.photo1, R.mipmap.photo2};

    private int[] shijiezhong = {R.mipmap.zhong1};
    private int[] tianqi = {R.mipmap.tianqi1, R.mipmap.tianqi2};
    private int[] wutishibie = {R.mipmap.wutishibie1, R.mipmap.wutishibie2};

    private List<View> list = new ArrayList<>();
    private List<TextView> dots = new ArrayList<>();
    private int oldPosition = 0;
    private int currentPosition = 0;
    private LinearLayout.LayoutParams lp_dots_normal, lp_dots_focused;
    private int[] yuyinfanyi;
    private int[] yuyinmishu;
    private int[] shezhi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ButterKnife.bind(this);
        initData();

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        String version = intent.getStringExtra("version");
        switch (version) {
            case "PM":
                yuyinfanyi = new int[]{R.mipmap.yuyinfanyi_go1, R.mipmap.yuyinfanyi_go2,R.mipmap.yuyinfanyi3};
                yuyinmishu = new int[]{R.mipmap.yuyinmishu_go1};
                shezhi = new int[]{R.mipmap.shezhi_go1, R.mipmap.shezhi_go2};
                break;
            case "PL":
                yuyinfanyi = new int[]{R.mipmap.yuyinfanyi1, R.mipmap.yuyinfanyi2, R.mipmap.yuyinfanyi3};
                yuyinmishu = new int[]{R.mipmap.yuyinmishu1};
                shezhi = new int[]{R.mipmap.shezhi1, R.mipmap.shezhi2, R.mipmap.shezhi3, R.mipmap.shezhi4};
                break;
            case "PH":
                yuyinfanyi = new int[]{R.mipmap.yuyinfanyi_fly1, R.mipmap.yuyinfanyi_fly2,R.mipmap.yuyinfanyi3};
                yuyinmishu = new int[]{R.mipmap.yuyinmishu_fly1};
                shezhi = new int[]{R.mipmap.shezhi1, R.mipmap.shezhi_fly2,R.mipmap.shezhi3, R.mipmap.shezhi4};
                break;
        }
        switch (type) {
            case 1:

                for (int i = 0; i < yuyinfanyi.length; i++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(yuyinfanyi[i]);
                    list.add(imageView);
                }
                pager.setAdapter(new ViewPagerAdapter(list));
                initViewPager();
                initPoint(list);


                break;
            case 2:
                for (int i = 0; i < paizhao.length; i++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(paizhao[i]);
                    list.add(imageView);
                }
                pager.setAdapter(new ViewPagerAdapter(list));
                initViewPager();
                initPoint(list);
                break;
            case 3:
                for (int i = 0; i < jinqu.length; i++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(jinqu[i]);
                    list.add(imageView);
                }
                pager.setAdapter(new ViewPagerAdapter(list));
                initViewPager();
                initPoint(list);
                break;
            case 4:
                for (int i = 0; i < mudidi.length; i++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(mudidi[i]);
                    list.add(imageView);
                }
                pager.setAdapter(new ViewPagerAdapter(list));
                initViewPager();
                initPoint(list);
                break;
            case 5:
                for (int i = 0; i < shezhi.length; i++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(shezhi[i]);
                    list.add(imageView);
                }
                pager.setAdapter(new ViewPagerAdapter(list));
                initViewPager();
                initPoint(list);
                break;
            case 6:
                for (int i = 0; i < wutishibie.length; i++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(wutishibie[i]);
                    list.add(imageView);
                }
                pager.setAdapter(new ViewPagerAdapter(list));
                initViewPager();
                initPoint(list);
                break;
            case 7:
                for (int i = 0; i < quanqiu.length; i++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(quanqiu[i]);
                    list.add(imageView);
                }
                pager.setAdapter(new ViewPagerAdapter(list));
                initViewPager();
                initPoint(list);
                break;
            case 8:
                for (int i = 0; i < yuyinmishu.length; i++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(yuyinmishu[i]);
                    list.add(imageView);
                }
                pager.setAdapter(new ViewPagerAdapter(list));
                initViewPager();
                initPoint(list);
                break;
            case 9:
                for (int i = 0; i < huilu.length; i++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(huilu[i]);
                    list.add(imageView);
                }
                pager.setAdapter(new ViewPagerAdapter(list));
                initViewPager();
                initPoint(list);
                break;
            case 10:
                for (int i = 0; i < tianqi.length; i++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(tianqi[i]);
                    list.add(imageView);
                }
                pager.setAdapter(new ViewPagerAdapter(list));
                initViewPager();
                initPoint(list);
                break;
            case 11:
                for (int i = 0; i < shijiezhong.length; i++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(shijiezhong[i]);
                    list.add(imageView);
                }
                pager.setAdapter(new ViewPagerAdapter(list));
                initViewPager();

                break;
            case 12:
                for (int i = 0; i < sos.length; i++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setImageResource(sos[i]);
                    list.add(imageView);
                }
                pager.setAdapter(new ViewPagerAdapter(list));
                initViewPager();
                initPoint(list);
                break;
        }
    }

    private void initPoint(List<View> imgList) {
        if (imgList.size() > 1) llPoint.setVisibility(View.VISIBLE);
        else llPoint.setVisibility(View.GONE);
        for (int i = 0; i < (imgList.size() > 5 ? 5 : imgList.size()); i++) {
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
    }

    //初始化ViewPager的方法
    public void initViewPager() {

        pager.setAdapter(new ViewPagerAdapter(list));
        //监听ViewPager滑动效果
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

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
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
