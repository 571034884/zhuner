package com.aibabel.readme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.utils.DeviceUtils;
import com.aibabel.readme.R;
import com.aibabel.readme.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    private int[] shijiezhong = {R.mipmap.zhong1};
    private int[] tianqi = {R.mipmap.tianqi1, R.mipmap.tianqi2};
    private int[] wutishibie = {R.mipmap.wutishibie1, R.mipmap.wutishibie2};



    private int[] churujin_proL = {R.mipmap.churujin1, R.mipmap.churujin2, R.mipmap.churujin3};
    private int[] ditu_proL = {R.mipmap.ditu1, R.mipmap.ditu2, R.mipmap.ditu3, R.mipmap.ditu4, R.mipmap.ditu5, R.mipmap.ditu6, R.mipmap.ditu7, R.mipmap.ditu8};
    private int[] shezhi_proL = {R.mipmap.shezhi1, R.mipmap.shezhi2, R.mipmap.shezhi3, R.mipmap.shezhi4,R.mipmap.shezhi5,R.mipmap.shezhi6,R.mipmap.shezhi7};
    private int[] huilu_proL = {R.mipmap.huilv1};
    private int[] jinqu_proL = {R.mipmap.jinqu1, R.mipmap.jinqu2, R.mipmap.jinqu3, R.mipmap.jinqu4, R.mipmap.jinqu5, R.mipmap.jinqu6, R.mipmap.jinqu7, R.mipmap.jinqu8};
    private int[] lixian_proL = {R.mipmap.lixian1, R.mipmap.lixian2};
    private int[] sos_proL = {R.mipmap.sos1, R.mipmap.sos2};
//    private int[] paizhao_proL = {R.mipmap.p1, R.mipmap.p2,R.mipmap.p3};
    private int[] paizhao_proL = {R.mipmap.p1, R.mipmap.photo2};
    private int[] quanqiu_proL = {R.mipmap.quanqiu1, R.mipmap.quanqiu2, R.mipmap.quanqiu8, R.mipmap.quanqiu9, R.mipmap.quanqiu4, R.mipmap.quanqiu5, R.mipmap.quanqiu6, R.mipmap.quanqiu7};

    private int[] shijiezhong_proL = {R.mipmap.zhong1};
    private int[] caidan_proL = {R.mipmap.caidan1, R.mipmap.caidan2};
    private int[] tianqi_proL = {R.mipmap.tianqi1, R.mipmap.tianqi2};
    private int[] youhuiquan_proL = {R.mipmap.youhuiquan1, R.mipmap.youhuiquan2, R.mipmap.youhuiquan3};
//    private int[] yuyinfanyi_proL = {R.mipmap.yuyinfanyi1, R.mipmap.yuyinfanyi4, R.mipmap.yuyinfanyi5, R.mipmap.yuyinfanyi6, R.mipmap.yuyinfanyi3};
    private int[] yuyinfanyi_proL = {R.mipmap.yuyinfanyi1, R.mipmap.yuyinfanyi4, R.mipmap.yuyinfanyi7, R.mipmap.yuyinfanyi6, R.mipmap.yuyinfanyi3};
    private int[] yuyinmishu_proL = {R.mipmap.yuyinmishu1};
    private int[] wanle_proL = {R.mipmap.play1,R.mipmap.play2,R.mipmap.play3,R.mipmap.play4,R.mipmap.play5,R.mipmap.play6,R.mipmap.play7};
    private int[] meishi_proL = {R.mipmap.meishi1,R.mipmap.meishi2,R.mipmap.meishi3,R.mipmap.meishi4,R.mipmap.meishi5};

    private List<View> list = new ArrayList<>();
    private List<TextView> dots = new ArrayList<>();
    private int oldPosition = 0;
    private int currentPosition = 0;
    private LinearLayout.LayoutParams lp_dots_normal, lp_dots_focused;
    private int[] yuyinfanyi;
    private int[] paizhao;
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
        String proVersion = intent.getStringExtra("proVersion");
//        version = "PH";
//        proVersion = "S";
        switch (version) {
            case "PM":
                paizhao = new int[]{R.mipmap.photo1, R.mipmap.photo2};
                yuyinfanyi = new int[]{R.mipmap.yuyinfanyi_go1, R.mipmap.yuyinfanyi_go2, R.mipmap.yuyinfanyi3};
                yuyinmishu = new int[]{R.mipmap.yuyinmishu_go1};
                shezhi = new int[]{R.mipmap.shezhi_go1, R.mipmap.shezhi_go2};
                break;
            case "PL":
                paizhao = new int[]{R.mipmap.photo1, R.mipmap.photo2};
                yuyinfanyi = new int[]{R.mipmap.yuyinfanyi1, R.mipmap.yuyinfanyi2, R.mipmap.yuyinfanyi3};
                yuyinmishu = new int[]{R.mipmap.yuyinmishu1};
                shezhi = new int[]{R.mipmap.shezhi1, R.mipmap.shezhi2, R.mipmap.shezhi3, R.mipmap.shezhi4};
                break;
            case "PH":
                paizhao = new int[]{R.mipmap.p1, R.mipmap.photo2};
                yuyinfanyi = new int[]{R.mipmap.yuyinfanyi_fly1, R.mipmap.yuyinfanyi_fly2,  R.mipmap.yuyinfanyi6,R.mipmap.yuyinfanyi3};
                yuyinmishu = new int[]{R.mipmap.yuyinmishu_fly1};
                if (DeviceUtils.getSystem()==DeviceUtils.System.FLY_Mobile){
                    shezhi = new int[]{R.mipmap.shezhi1, R.mipmap.shezhi_fly2, R.mipmap.shezhi_fly_mobile3, R.mipmap.shezhi_fly_mobile4};
                }else{
                    shezhi = new int[]{R.mipmap.shezhi1, R.mipmap.shezhi_fly2, R.mipmap.shezhi3, R.mipmap.shezhi4};
                }

                break;
        }
        if (TextUtils.equals(proVersion, "S")) {
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
                    for (int i = 0; i < quanqiu_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(quanqiu_proL[i]);
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
                case 13:
                    for (int i = 0; i < lixian_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(lixian_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;
            }
        } else if (TextUtils.equals(proVersion, "L")) {

            /*  readmeList.add(new ReadmeItemBean(1,"菜单"));
                    readmeList.add(new ReadmeItemBean(2,"语音翻译"));
                    readmeList.add(new ReadmeItemBean(3,"拍照翻译"));
                    readmeList.add(new ReadmeItemBean(4,"当地玩乐"));
                    readmeList.add(new ReadmeItemBean(5,"地图"));
                    readmeList.add(new ReadmeItemBean(6,"时钟"));

                    readmeList.add(new ReadmeItemBean(7,"天气"));
                    readmeList.add(new ReadmeItemBean(8,"汇率"));
                    readmeList.add(new ReadmeItemBean(9,"美食"));

                    readmeList.add(new ReadmeItemBean(10,"景区导览"));
                    readmeList.add(new ReadmeItemBean(11,"出入境"));
                    readmeList.add(new ReadmeItemBean(12,"优惠券"));
                    readmeList.add(new ReadmeItemBean(13,"语音秘书"));
                    readmeList.add(new ReadmeItemBean(14,"全球上网"));
                    readmeList.add(new ReadmeItemBean(15,"离线管理"));
                    readmeList.add(new ReadmeItemBean(16,"设置"));
                    readmeList.add(new ReadmeItemBean(17,"sos"));*/
            switch (type) {
                case 1:
                    for (int i = 0; i < caidan_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(caidan_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;
                case 2:
                    for (int i = 0; i < yuyinfanyi_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(yuyinfanyi_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;
                case 3:
                    for (int i = 0; i < paizhao_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(paizhao_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;
                case 4:
                    for (int i = 0; i <wanle_proL .length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(wanle_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;
                case 5:
                    for (int i = 0; i < ditu_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(ditu_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;
                case 6:
                    for (int i = 0; i < shijiezhong_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(shijiezhong_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;
                case 7:
                    for (int i = 0; i < tianqi_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(tianqi_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;
                case 8:
                    for (int i = 0; i < huilu_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(huilu_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;
                case 9:
                    for (int i = 0; i < meishi_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(meishi_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;
                case 10:
                    for (int i = 0; i < jinqu_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(jinqu_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;
                case 11:
                    for (int i = 0; i < churujin_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(churujin_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;
                case 12:
                    for (int i = 0; i < youhuiquan_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(youhuiquan_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;

                case 13:
                    for (int i = 0; i < yuyinmishu_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(yuyinmishu_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;

                case 14:
                    for (int i = 0; i < quanqiu_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(quanqiu_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;
                case 15:
                    for (int i = 0; i < lixian_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(lixian_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;
                case 16:
                    for (int i = 0; i < shezhi_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(shezhi_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;
                case 17:
                    for (int i = 0; i < sos_proL.length; i++) {
                        ImageView imageView = new ImageView(this);
                        imageView.setImageResource(sos_proL[i]);
                        list.add(imageView);
                    }
                    pager.setAdapter(new ViewPagerAdapter(list));
                    initViewPager();
                    initPoint(list);
                    break;

            }
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
