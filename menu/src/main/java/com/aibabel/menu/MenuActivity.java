package com.aibabel.menu;

import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aibabel.baselibrary.base.BaseApplication;
import com.aibabel.baselibrary.bean.RepaireBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.SharePrefUtil;
import com.aibabel.menu.base.BaseActivity;
import com.aibabel.menu.fragment.MenuOneFragment;
import com.aibabel.menu.fragment.MenuThreeFragment;
import com.aibabel.menu.fragment.MenuTwoFragment;
import com.aibabel.menu.util.AppStatusUtils;
import com.aibabel.menu.util.GlideCacheUtil;
import com.aibabel.menu.util.L;
import com.aibabel.menu.view.BannerIndicator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.taobao.sophix.SophixManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MenuActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.menu_bottom_yyfy_ll)
    LinearLayout menuBottomYyfyLl;


    @BindView(R.id.menu_top_rl)
    RelativeLayout menuTopRl;
    @BindView(R.id.menu_img)
    ImageView menuImg;
    @BindView(R.id.menu_viewpager)
    ViewPager menuViewpager;
    @BindView(R.id.menu_bannerindicator)
    BannerIndicator menuBannerindicator;
    @BindView(R.id.menu_bottom_pzfy_ll)
    LinearLayout menuBottomPzfyLl;
    @BindView(R.id.menu_bottom_ddwl_ll)
    LinearLayout menuBottomDdwlLl;
    @BindView(R.id.menu_bttom_gd_ll)
    LinearLayout menuBttomGdLl;


    @BindView(R.id.menu_bttom_gd_img)
    ImageView menuBttomGdImg;



    //退出icon动画
    public static AnimationDrawable frameAnim;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        L.e("=============================");
        super.onCreate(savedInstanceState);


    }

    @Override
    public int getLayout(Bundle bundle) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        getWindow().setEnterTransition(new Explode().setDuration(500));
//        TransitionSet transitionSet = new TransitionSet();
//        transitionSet.addTransition(new Slide(Gravity.RIGHT).setDuration(600));
//        transitionSet.addTransition(new Explode());

        getWindow().setEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.tran_menu_in));
        getWindow().setReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.tran_menu_out));
        return R.layout.activity_menu;
    }

    @Override
    public void init() {
        setHotRepairEnable(true);

        L.e("请求是否有热修复=============================");
    }

    @Override
    protected void assignView() {
        frameAnim = (AnimationDrawable) getResources().getDrawable(R.drawable.amin_list_tuichu);
        menuBttomGdImg.setBackgroundDrawable(frameAnim);



//        L.e("=================" + mContext.getFilesDir().getAbsolutePath() + "/mohuImg.jpg");

        GlideCacheUtil.getInstance().clearImageAllCache(mContext);
        File file=new File(mContext.getFilesDir().getAbsolutePath() + "/mohuImg.jpg");
        if (!file.exists()) {
            Glide.with(mContext).load(R.mipmap.mohuimg_moren).into(menuImg);
        } else {
            Glide.with(mContext).load(mContext.getFilesDir().getAbsolutePath() + "/mohuImg.jpg").into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                    menuImg.setImageDrawable(resource);
//                menuTopRl.setBackgroundTintMode(Mode);
//                BitmapDrawable bd = (BitmapDrawable) resource;
//                saveImage(bd.getBitmap());

                }
            });
        }


    }

    @Override
    protected void onResume() {
//        frameAnim.start();


        index++;
        if (index==1) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
            frameAnim.start();
//


        }
        super.onResume();
    }



    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        menuBttomGdLl.setOnClickListener(this);
        menuBottomYyfyLl.setOnClickListener(this);
        menuBottomPzfyLl.setOnClickListener(this);
        menuBottomDdwlLl.setOnClickListener(this);


    }

//    @Override
//    protected int getLayoutId() {
//
//
//
//
//        return R.layout.activity_menu;
//    }

    @Override
    protected void initData() {

//        RequestOptions options = new RequestOptions().error(R.mipmap.icon_jqdl).bitmapTransform(new BlurTransformation(this, 14, 3));


        final List<Fragment> datas = new ArrayList<>();
        datas.add(new MenuOneFragment());
        datas.add(new MenuTwoFragment());
        datas.add(new MenuThreeFragment());

        menuViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return datas.get(position);
            }

            @Override
            public int getCount() {
                return datas.size();
            }
        });
        menuBannerindicator.setUpWidthViewPager(menuViewpager);


    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.menu_bttom_gd_ll:
                    finishAfterTransition();
//                finish();
                    break;
                case R.id.menu_bottom_yyfy_ll:
                    //调起语音翻译
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, "com.aibabel.translate"));
                    break;
                case R.id.menu_bottom_pzfy_ll:
                    //调起拍照翻译
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext, "com.aibabel.ocr"));
                    break;
                case R.id.menu_bottom_ddwl_ll:
                    //调起当地玩乐

                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(mContext,"com.aibabel.fyt_play"));
                    break;
            }
        } catch (Exception e) {

        }


    }

    int index=0;
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
     /*   index++;
        if (index==1) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
                    frameAnim.start();
//


        }*/


        super.onWindowFocusChanged(hasFocus);
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


}
