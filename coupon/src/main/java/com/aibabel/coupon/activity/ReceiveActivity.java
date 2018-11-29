package com.aibabel.coupon.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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

import com.aibabel.coupon.R;
import com.aibabel.coupon.adapter.CommomRecyclerAdapter;
import com.aibabel.coupon.adapter.CommonRecyclerViewHolder;
import com.aibabel.coupon.bean.CouponBean;
import com.aibabel.coupon.fragment.CouponFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


//   目的地 领券页
public class ReceiveActivity extends BaseActivity {


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
    private CommomRecyclerAdapter adapter;
    private List<CouponBean> couponList = new ArrayList<>();
    private String img_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536148533790&di=6b6f44e5602faa55606b7918ba7e00f1&imgtype=0&src=http%3A%2F%2Fi2.w.hjfile.cn%2Fnews%2F201509%2F201509101221406593.jpg";
    private RelativeLayout rl;
    private TextView tv_receive;

    private List<String> imaglist = new ArrayList<>();

    private View popu1;
    private TextView tv_title;
    private ImageView iv_img;
    private ImageView iv_close;
    private PopupWindow popupWindow;
    private ViewStub vsTest;

    @Override
    public int initLayout() {
        return R.layout.searchview;

    }

    @Override
    public void init() {

        vsTest = findViewById(R.id.vs_test);
        vsTest.setLayoutResource(R.layout.stub_coupon);
        View iv_vsContent = vsTest.inflate();
        rvCoupon = iv_vsContent.findViewById(R.id.rv_coupon);
        tvMudidi = iv_vsContent.findViewById(R.id.tv_mudidi);
        tvCoupon = iv_vsContent.findViewById(R.id.tv_coupon);
        initTitle();
//        initIntent();
        initAdapter();
        initData();
        initBanner();
        initPopupwindow();
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
                    ivLeft.setImageResource(R.mipmap.ic_backb);
                    ivRight.setImageResource(R.mipmap.my_coupon_yes);
                } else {
                    clRoot.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
                    ivLeft.setImageResource(R.mipmap.ic_backb);
                    ivRight.setImageResource(R.mipmap.my_coupon_yes);
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
                Intent intent = new Intent(ReceiveActivity.this, CouponActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceiveActivity.this, CouponActivity.class);
                intent.putExtra("type",0);
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
    private void initBanner() {
        imaglist.add("http://pic26.photophoto.cn/20130218/0017030092066924_b.jpg");
        imaglist.add("http://pic33.nipic.com/20130927/13469348_101538361365_2.jpg");
        imaglist.add("http://i.ledanji.com/up/2017/b/ba4f96592ce11dda");
        imaglist.add("https://i02picsos.sogoucdn.com/167ca5fc543fd1a5");
        banner.setImages(imaglist)//添加图片集合或图片url集合
                .setDelayTime(1500)//设置轮播时间
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new GlideImage())//加载图片
                .setIndicatorGravity(BannerConfig.CENTER)//设置指示器位置
                .start();
    }


    private void initData() {
        for (int i = 0; i < 5; i++) {

            couponList.add(new CouponBean("", img_url, "松本清", "立享95折+8%免税", "消费满30000日元使用", "免费领"));
        }
        adapter.updateData(couponList);
    }

    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        rvCoupon.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new CommomRecyclerAdapter(this, couponList, R.layout.recy_coupon, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, final int postion) {

                popupWindowShow();

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


                ImageView iv_shop_img = holder.getView(R.id.iv_shop_img);
                tv_jiaobiao_name.setText(((CouponBean) o).getTv_jiaobiao_name());
                tv_shop_name.setText(((CouponBean) o).getTv_shop_name());
                tv_shop_price.setText(((CouponBean) o).getTv_shop_price());
                tv_shop_details.setText(((CouponBean) o).getTv_shop_details());
                tv_receive.setText(((CouponBean) o).getTv_receive());
                tv_jiaobiao_name.setText(((CouponBean) o).getTv_jiaobiao_name());

                Glide.with(ReceiveActivity.this)
                        .load(((CouponBean) o).getIv_shop_img())
                        .into(iv_shop_img);

                tv_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.equals(couponList.get(position).getTv_receive(), "免费领")) {
                            couponList.get(position).setTv_receive("去使用");
                            adapter.updateData(couponList);
                        } else {
                            startActivity(new Intent(ReceiveActivity.this, DetailsActivity.class));
                        }

                    }
                });
                if (TextUtils.equals(couponList.get(position).getTv_receive(), "免费领")) {
                    tv_receive.setBackgroundResource(R.drawable.shape_background);
                    tv_receive.setTextColor(Color.parseColor("#ffffff"));
                } else {
                    tv_receive.setBackgroundResource(R.drawable.shape_background2);
                    tv_receive.setTextColor(Color.parseColor("#3e3e3e"));
                }

            }
        };

        rvCoupon.setAdapter(adapter);

    }


    /**
     * 底部弹出popupWindow
     */
    private void popupWindowShow() {
//        cl.setBackgroundColor(Color.parseColor("#30000000"));
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
                popupWindow.dismiss();
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
        String country_name = intent.getStringExtra("country_name");
        String country_name_english = intent.getStringExtra("country_name_english");
        int country_img = intent.getIntExtra("country_img", 0);

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
