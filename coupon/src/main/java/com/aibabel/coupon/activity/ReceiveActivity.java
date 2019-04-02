package com.aibabel.coupon.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.coupon.R;
import com.aibabel.coupon.adapter.CommomRecyclerAdapter;
import com.aibabel.coupon.adapter.CommonRecyclerViewHolder;
import com.aibabel.coupon.bean.Constans;
import com.aibabel.coupon.bean.CouponBean;
import com.aibabel.coupon.utils.CommonUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


//   目的地 领券页
public class ReceiveActivity extends BaseActivity implements BaseCallback<BaseBean> {


    RecyclerView rvCoupon;
    TextView tvMudidi;
    TextView tvCoupon;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.cl_root)
    ConstraintLayout clRoot;

    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.ll_root)
    CoordinatorLayout cl;
    @BindView(R.id.iv_banner)
    ImageView ivBanner;
    private CommomRecyclerAdapter adapter;
    private RelativeLayout rl;
    private TextView tv_receive;

    private List<String> imaglist = new ArrayList<>();

    private View popu1;
    private TextView tv_title;
    private ImageView iv_img;
    private ImageView iv_close;
    private PopupWindow popupWindow;
    private ViewStub vsTest;
    private String country_name;
    private String country_name_english;
    private String country_img;
    private String country_banner_img;
    private CouponBean couponBean;
    private List<CouponBean.DataBean> couponBeanData;
    private int couponId;


    @Override
    public void init() {

        vsTest = findViewById(R.id.vs_test);
        vsTest.setLayoutResource(R.layout.stub_coupon);
        View iv_vsContent = vsTest.inflate();
        rvCoupon = iv_vsContent.findViewById(R.id.rv_coupon);
        tvMudidi = iv_vsContent.findViewById(R.id.tv_mudidi);
        tvCoupon = iv_vsContent.findViewById(R.id.tv_coupon);


        initIntent();
        initTitle();
        initPopupwindow();
        initAdapter();
        initData();


        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
                //第一种
                int toolbarHeight = appBarLayout.getTotalScrollRange();
                int dy = Math.abs(verticalOffset);
                if (dy < toolbarHeight - clRoot.getHeight()) {
                    float scale = (float) dy / toolbarHeight;
                    float alpha = scale * 255;
                    clRoot.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    ivLeft.setImageResource(R.mipmap.lyzx_fanhuibaise);

                    ivRight.setImageResource(R.mipmap.my_coupon_white);
                } else {
                    clRoot.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
                    ivLeft.setImageResource(R.mipmap.lyzx_fanhuishense);
                    ivRight.setImageResource(R.mipmap.my_coupon_red);
                }
            }
        });

    }


    public void initTitle() {
        ivLeft.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        ivRight.setVisibility(View.VISIBLE);

        ivLeft.setVisibility(getIntent().getIntExtra("isback", View.VISIBLE));

        ivLeft.setImageResource(R.mipmap.ic_backb);
        tvTitle.setHint(getResources().getString(R.string.search_hint_shop));
        tvTitle.setHintTextColor(getResources().getColor(R.color.color_99));
        tvTitle.setBackgroundResource(R.drawable.bg_search_80while);
        ivRight.setImageResource(R.mipmap.ic_home_w);
        clRoot.setAlpha(1);

        // 根据type 判断跳转 目的地 还是我的优惠券
        // type ： 1 目的地 2 我的优惠券
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ReceiveActivity.this, CouponActivity.class);
//                intent.putExtra("type", 1);
//                startActivity(intent);
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("coupon_receive6", null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/

                Intent intent = new Intent();
                intent.putExtra("type", 1);
                setResult(200,intent);
                finish();
            }
        });

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("coupon_receive1", null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/

                Intent intent = new Intent();
                intent.putExtra("type", 0);
                setResult(200,intent);
                finish();
//                startActivity(intent);
            }
        });

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("coupon_receive2", null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/

                Intent intent = new Intent(ReceiveActivity.this, SearchActivity.class);
                intent.putExtra("country",country_name);
                startActivity(intent);
            }
        });
    }

    // 中部 弹 框
    private void initPopupwindow() {
        popu1 = View.inflate(ReceiveActivity.this, R.layout.coupan_popu, null);
        tv_title = popu1.findViewById(R.id.tv_title);
        iv_img = popu1.findViewById(R.id.iv_img);
        iv_close = popu1.findViewById(R.id.iv_close);
    }

    // Banner 的轮播图
    private void initBanner(List<CouponBean.DataBean> couponBeanData) {
        for (int i = 0; i < couponBeanData.size(); i++) {
            imaglist.add(couponBeanData.get(i).getCouponData().getBannerimage());
        }
        banner.setImages(imaglist)//添加图片集合或图片url集合
                .setDelayTime(1500)//设置轮播时间
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new GlideImage())//加载图片
                .setIndicatorGravity(BannerConfig.CENTER)//设置指示器位置
                .start();
    }

    private void userGetOneCoupon(int couponId) {


        Map<String, String> map = new HashMap<>();
        if (TextUtils.equals(Constans.PRO_VERSION,"L")){
            map.put("leaseId",Constans.PRO_DEV_OID);
        }
        map.put("CouponId", couponId + "");
        map.put("countryName", country_name);
        OkGoUtil.<BaseBean>get(ReceiveActivity.this, Constans.METHOD_USERGETONECOUPON, map, BaseBean.class, this);

    }

    private void initData() {

    /*    for (int i = 0; i < 5; i++) {

            couponList.add(new CouponBean("", img_url, "松本清", "立享95折+8%免税", "消费满30000日元使用", "免费领"));
        }
        adapter.updateData(couponList);*/
        Map<String, String> map = new HashMap<>();
        if (TextUtils.equals(Constans.PRO_VERSION,"L")){
            map.put("leaseId",Constans.PRO_DEV_OID);
        }
        map.put("countryName", country_name);
        OkGoUtil.<CouponBean>get(ReceiveActivity.this, Constans.METHOD_GETCOUPONMSG, map, CouponBean.class, this);
    }

    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        rvCoupon.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new CommomRecyclerAdapter(this, couponBeanData, R.layout.recy_coupon, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, final int postion) {

//                popupWindowShow(postion);

            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, final int position) {
                TextView tv_jiaobiao_name = holder.getView(R.id.tv_jiaobiao_name);
                rl = holder.getView(R.id.rl);
                TextView tv_shop_name = holder.getView(R.id.tv_shop_name);
                TextView tv_shop_price = holder.getView(R.id.tv_shop_price);
                TextView tv_shop_details = holder.getView(R.id.tv_shop_details);
                tv_receive = holder.getView(R.id.tv_receive);
                RelativeLayout rlPopu = holder.getView(R.id.rl_popu);
                RelativeLayout rlReceive = holder.getView(R.id.rl_receive);
                ImageView iv_shop_img = holder.getView(R.id.iv_shop_img);
                tv_shop_name.setText(((couponBeanData).get(position).getCouponData().getTitle()));
                tv_shop_price.setText(((couponBeanData).get(position).getCouponData().getYouhui()));
                tv_shop_details.setText(((couponBeanData).get(position).getCouponData().getTiaojianshort()));

                if (TextUtils.equals((couponBeanData).get(position).getUserHasThisCoupon(), "true")) {
                    tv_receive.setText("去使用");
                } else {
                    tv_receive.setText("免费领");
                }

                rlPopu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        /**####  start-hjs-addStatisticsEvent   ##**/
                        try {
                            HashMap<String, Serializable> add_hp = new HashMap<>();
                            add_hp.put("coupon_receive3_def", "优惠券详情");
                            addStatisticsEvent("coupon_receive3", add_hp);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        /**####  end-hjs-addStatisticsEvent  ##**/

                        try {
                            popupWindowShow(position);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });
                Glide.with(ReceiveActivity.this)
                        .load(((couponBeanData).get(position).getCouponData().getImage()))
                        .into(iv_shop_img);

                rlReceive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        couponId = couponBeanData.get(position).getCouponData().getCouponId();
                        Log.e("couponId", couponId + "");
                        if (TextUtils.equals(couponBeanData.get(position).getUserHasThisCoupon(), "false")) {
                            /**####  start-hjs-addStatisticsEvent   ##**/
                            try {
                                HashMap<String, Serializable> add_hp = new HashMap<>();
                                add_hp.put("coupon_receive4_def", couponBeanData.get(position).getCouponData().getTitle());
                                addStatisticsEvent("coupon_receive4", add_hp);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            /**####  end-hjs-addStatisticsEvent  ##**/

                            userGetOneCoupon(couponId);
                            couponBeanData.get(position).setUserHasThisCoupon("true");
                            adapter.updateData(couponBeanData);

                        } else {
                            /**####  start-hjs-addStatisticsEvent   ##**/
                            try {
                                HashMap<String, Serializable> add_hp = new HashMap<>();
                                add_hp.put("coupon_receive5_def", couponBeanData.get(position).getCouponData().getTitle());
                                addStatisticsEvent("coupon_receive5", add_hp);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            /**####  end-hjs-addStatisticsEvent  ##**/

                            Intent intent = new Intent(ReceiveActivity.this, DetailsActivity.class);

                            intent.putExtra("couponId", couponId);
                            startActivity(intent);
                        }

                    }
                });
                if (TextUtils.equals((couponBeanData).get(position).getUserHasThisCoupon(), "false")) {
//                    tv_receive.setBackgroundResource(R.drawable.shape_background);

                    tv_receive.setTextColor(Color.parseColor("#ffffff"));
                } else {
//                    tv_receive.setBackgroundResource(R.drawable.shape_background2);

                    tv_receive.setTextColor(Color.parseColor("#ffffff"));
                }

            }
        };
        rvCoupon.setAdapter(adapter);
    }


    /**
     * 底部弹出popupWindow
     */
    private void popupWindowShow(int position) {
//        cl.setBackgroundColor(Color.parseColor("#30000000"));

        tv_title.setText(couponBeanData.get(position).getCouponData().getContext());
        Glide.with(ReceiveActivity.this)
                .load(((couponBeanData).get(position).getCouponData().getImage()))
                .into(iv_img);
        popupWindow = new PopupWindow(popu1, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundAlpha(0.25f);//设置屏幕透明
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(cl, Gravity.CENTER, 0, 0);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow!=null){


                    /**####  start-hjs-addStatisticsEvent   ##**/
                    try {
                        addStatisticsEvent("coupon_Details1", null);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    /**####  end-hjs-addStatisticsEvent  ##**/
                    popupWindow.dismiss();
                }
            }
        });
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    private void initIntent() {
        Intent intent = getIntent();
        country_name = intent.getStringExtra("country_name");
        country_name_english = intent.getStringExtra("country_name_english");
        country_img = intent.getStringExtra("country_img");
        country_banner_img = intent.getStringExtra("country_banner_img");
        setPathParams("国家名称");
    }





    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.searchview;
    }

    @Override
    public void onSuccess(String method, BaseBean baseBean, String s1) {
        switch (method) {
            case Constans.METHOD_GETCOUPONMSG:
                couponBean = (CouponBean) baseBean;
                couponBeanData = couponBean.getData();

                adapter.updateData(couponBeanData);
                initBanner(couponBeanData);
                Glide.with(ReceiveActivity.this)
                        .load(((couponBeanData).get(0).getCouponData().getBannerimage()))
                        .into(ivBanner);
                break;
        }
    }

    @Override
    public void onError(String s, String s1, String s2) {

    }

    @Override
    public void onFinsh(String s) {

    }

    /*@OnClick({R.id.tv_mudidi, R.id.tv_coupon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_mudidi:
                startActivity(new Intent(ReceiveActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.tv_coupon:
                startActivity(new Intent(ReceiveActivity.this, MyCouponActivity.class));
                finish();
                break;
        }
    }*/


    public class GlideImage extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext()).load(path).into(imageView);
        }
    }
}
