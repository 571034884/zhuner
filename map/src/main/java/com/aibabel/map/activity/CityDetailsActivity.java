package com.aibabel.map.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.map.R;
import com.aibabel.map.bean.InterestPointBean;
import com.aibabel.map.bean.PoiBean;
import com.aibabel.map.bean.RouteBean;
import com.aibabel.map.utils.ApiConstant;
import com.aibabel.map.utils.CommonUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CityDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_name_en)
    TextView tvNameEn;
    @BindView(R.id.tv_paiming)
    TextView tvPaiming;
    @BindView(R.id.tv_haoping)
    TextView tvHaoping;
    @BindView(R.id.tv_jieshao)
    TextView tvJieshao;
    @BindView(R.id.tv_traffic)
    TextView tvTraffic;
    @BindView(R.id.cl_traffic)
    ConstraintLayout clTraffic;
    @BindView(R.id.tv_ticket)
    TextView tvTicket;
    @BindView(R.id.cl_ticket)
    ConstraintLayout clTicket;
    @BindView(R.id.tv_opentime)
    TextView tvOpentime;
    @BindView(R.id.cl_opentime)
    ConstraintLayout clOpentime;
    @BindView(R.id.tv_paytype)
    TextView tvPaytype;
    @BindView(R.id.cl_paytype)
    ConstraintLayout clPaytype;
    @BindView(R.id.iv_fanhui)
    ImageView ivFanhui;
    @BindView(R.id.cl)
    ConstraintLayout cl;
    @BindView(R.id.iv_traffic)
    ImageView ivTraffic;
    @BindView(R.id.iv_ticket)
    ImageView ivTicket;
    @BindView(R.id.iv_opentime)
    ImageView ivOpentime;
    @BindView(R.id.iv_paytype)
    ImageView ivPaytype;
    @BindView(R.id.iv_wangzhi)
    ImageView ivWangzhi;
    @BindView(R.id.tv_wangzhi)
    TextView tvWangzhi;
    @BindView(R.id.cl_wangzhi)
    ConstraintLayout clWangzhi;
    @BindView(R.id.iv_dianhua)
    ImageView ivDianhua;
    @BindView(R.id.tv_dianhua)
    TextView tvDianhua;
    @BindView(R.id.cl_dianhua)
    ConstraintLayout clDianhua;
    @BindView(R.id.ll_daohang)
    LinearLayout llDaohang;
    @BindView(R.id.ll_wenluka)
    LinearLayout llWenluka;
    @BindView(R.id.tv_guanbi)
    ImageView tvGuanbi;
    @BindView(R.id.cl_wenluka)
    ConstraintLayout clWenluka;
    @BindView(R.id.tv_didian_zhong)
    TextView tvDidianZhong;
    @BindView(R.id.tv_didian_wai)
    TextView tvDidianWai;
    @BindView(R.id.tv_dizhi_wai)
    TextView tvDizhiWai;
    @BindView(R.id.tv_dizhi_zhong)
    TextView tvDizhiZhong;
    @BindView(R.id.tv_didian_bendi)
    TextView tvDidianBendi;
    @BindView(R.id.v)
    ConstraintLayout v;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.rl_error)
    RelativeLayout rlError;
    @BindView(R.id.ll_error)
    LinearLayout llError;
    @BindView(R.id.sv_content)
    ScrollView svContent;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    private InterestPointBean.DataBean detailBean;
    private String poiId;
    private PoiBean.DataBean dataBean;

    //具体参数集合体
    private RouteBean routeBean = null;

    @Override
    public int getLayout(Bundle var1) {
        return R.layout.activity_city_details;
    }

    @Override
    public void init() {
        getIntentData();
//        if (TextUtils.equals(poiId, "") || poiId == null) {
//            initData();
//        } else {
//
//
//        }
        getPoi();
    }



    private void getPoi(){
        Map<String, String> map = new HashMap<>();
        map.put("poiId", poiId);
        OkGoUtil.get(this, ApiConstant.API_POIMSG, map, PoiBean.class, new BaseCallback<PoiBean>() {
            @Override
            public void onSuccess(String s, PoiBean bean, String s1) {
                Logger.e(s1);
                if (null != bean) {
                    rlError.setVisibility(View.GONE);
                    svContent.setVisibility(View.VISIBLE);
                    dataBean = bean.getData();
                    initPoiData();
                } else {
                    rlError.setVisibility(View.GONE);
                    svContent.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String s, String s1, String s2) {
                ToastUtil.showShort(CityDetailsActivity.this, "抱歉当前没有数据！");
                rlError.setVisibility(View.VISIBLE);
                clWenluka.setVisibility(View.GONE);
                svContent.setVisibility(View.GONE);
            }

            @Override
            public void onFinsh(String s) {

            }
        });
    }


    public void initData() {

        RequestOptions options = new RequestOptions().placeholder(R.mipmap.icon_scenic_img_small).error(R.mipmap.icon_scenic_img_small);
        Glide.with(CityDetailsActivity.this).load(detailBean.getPoi_image(false, 540, 320)).apply(options).into(ivImg);
        tvName.setText(detailBean.getCnName());
        tvNameEn.setText(detailBean.getEnName());

        if (detailBean.getRanking() != 999999) {
            tvPaiming.setText(detailBean.getCountryName() + detailBean.getCnCityName() + "排名第" + detailBean.getRanking() + "的" + detailBean.getPoiTypeName());
        } else {
            tvPaiming.setText(detailBean.getCountryName() + detailBean.getCnCityName());
        }
        if (detailBean.getPositive().equals("") || detailBean.getPositive().equals("0")) {
            tvHaoping.setVisibility(View.GONE);
        } else {
            tvHaoping.setVisibility(View.VISIBLE);
            tvHaoping.setText("好评率" + detailBean.getPositive());
        }
        tvJieshao.setText(detailBean.getDescription());
        if (TextUtils.equals("", detailBean.getTraffic())) clTraffic.setVisibility(View.GONE);
        if (TextUtils.equals("", detailBean.getTicket())) clTicket.setVisibility(View.GONE);
        if (TextUtils.equals("", detailBean.getOpenTime())) clOpentime.setVisibility(View.GONE);
        if (TextUtils.equals("", detailBean.getPaytype())) clPaytype.setVisibility(View.GONE);
        if (TextUtils.equals("", detailBean.getWebsite())) clWangzhi.setVisibility(View.GONE);
        if (TextUtils.equals("", detailBean.getPhone())) clDianhua.setVisibility(View.GONE);
        tvTraffic.setText(detailBean.getTraffic());
        tvTicket.setText(detailBean.getTicket());
        tvOpentime.setText(detailBean.getOpenTime());
        tvPaytype.setText(detailBean.getPaytype());
        tvWangzhi.setText(detailBean.getWebsite());
        tvDianhua.setText(detailBean.getPhone());

        if (TextUtils.equals("", detailBean.getLocalName())) tvDidianBendi.setVisibility(View.GONE);
        if (TextUtils.equals("", detailBean.getEnName())) tvDidianWai.setVisibility(View.GONE);
        if (TextUtils.equals("", detailBean.getCnName())) tvDidianZhong.setVisibility(View.GONE);
        if (TextUtils.equals("", detailBean.getLocaladdress())) tvDizhiWai.setVisibility(View.GONE);
        if (TextUtils.equals("", detailBean.getAddress())) tvDizhiZhong.setVisibility(View.GONE);
        tvDidianBendi.setText(detailBean.getLocalName());
        tvDidianWai.setText(detailBean.getEnName());
        tvDidianZhong.setText(detailBean.getCnName());
        tvDizhiWai.setText(detailBean.getLocaladdress());
        tvDizhiZhong.setText(detailBean.getAddress());
    }

    public void initPoiData() {
        RequestOptions options = new RequestOptions().placeholder(R.mipmap.icon_scenic_img_small).error(R.mipmap.icon_scenic_img_small);
        Glide.with(CityDetailsActivity.this).load(dataBean.getPoi_image()).apply(options).into(ivImg);
        tvName.setText(dataBean.getCnName());
        tvNameEn.setText(dataBean.getEnName());
        if (dataBean.getRanking() != 999999) {
            tvPaiming.setText(dataBean.getCountryName() + dataBean.getCnCityName() + "排名第" + dataBean.getRanking() + "的" + dataBean.getPoiTypeName());
        } else {
            tvPaiming.setText(dataBean.getCountryName() + dataBean.getCnCityName());
        }
        if (dataBean.getPositive().equals("") || dataBean.getPositive().equals("0")) {
            tvHaoping.setText("");
        } else {
            tvHaoping.setText("好评率" + dataBean.getPositive());
        }
        tvJieshao.setText(dataBean.getDescription());
        if (TextUtils.equals("", dataBean.getTraffic())) clTraffic.setVisibility(View.GONE);
        if (TextUtils.equals("", dataBean.getTicket())) clTicket.setVisibility(View.GONE);
        if (TextUtils.equals("", dataBean.getOpenTime())) clOpentime.setVisibility(View.GONE);
        if (TextUtils.equals("", dataBean.getPaytype())) clPaytype.setVisibility(View.GONE);
        if (TextUtils.equals("", dataBean.getWebsite())) clWangzhi.setVisibility(View.GONE);
        if (TextUtils.equals("", dataBean.getPhone())) clDianhua.setVisibility(View.GONE);
        tvTraffic.setText(dataBean.getTraffic());
        tvTicket.setText(dataBean.getTicket());
        tvOpentime.setText(dataBean.getOpenTime());
        tvPaytype.setText(dataBean.getPaytype());
        tvWangzhi.setText(dataBean.getWebsite());
        tvDianhua.setText(dataBean.getPhone());

        if (TextUtils.equals("", dataBean.getLocalName())) tvDidianBendi.setVisibility(View.GONE);
        if (TextUtils.equals("", dataBean.getEnName())) tvDidianWai.setVisibility(View.GONE);
        if (TextUtils.equals("", dataBean.getCnName())) tvDidianZhong.setVisibility(View.GONE);
        if (TextUtils.equals("", dataBean.getLocaladdress())) tvDizhiWai.setVisibility(View.GONE);
        if (TextUtils.equals("", dataBean.getAddress())) tvDizhiZhong.setVisibility(View.GONE);
        tvDidianBendi.setText(dataBean.getLocalName());
        tvDidianWai.setText(dataBean.getEnName());
        tvDidianZhong.setText(dataBean.getCnName());
        tvDizhiWai.setText(dataBean.getLocaladdress());
        tvDizhiZhong.setText(dataBean.getAddress());
    }

    public void getIntentData() {
        detailBean = (InterestPointBean.DataBean) getIntent().getSerializableExtra("detail");
        poiId = getIntent().getStringExtra("poiId");
        //TODO 跳转过来的路线规划，必须传递实体类
        routeBean = getIntent().getExtras().getParcelable("routes");
    }

    @OnClick({R.id.iv_fanhui, R.id.ll_daohang, R.id.tv_guanbi, R.id.ll_wenluka,R.id.iv_back,R.id.ll_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_fanhui:
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_daohang:
                if (!CommonUtils.isNetworkAvailable(this)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }
                turnToGoogleMap();
                break;
            case R.id.ll_error:
                if (!CommonUtils.isNetworkAvailable(this)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }
                getPoi();
                break;
            case R.id.tv_guanbi:

                AnimatorSet set_guan = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.wenluka_guanbi);
                set_guan.setTarget(clWenluka);
                set_guan.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        clWenluka.setVisibility(View.GONE);
                        llDaohang.setClickable(true);
                        llWenluka.setClickable(true);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                set_guan.start();
                break;
            case R.id.ll_wenluka:

                if (!CommonUtils.isNetworkAvailable(this)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }
                clWenluka.setVisibility(View.VISIBLE);
                AnimatorSet set_kai = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.wenluka_dakai);
                set_kai.setTarget(clWenluka);
                set_kai.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        llDaohang.setClickable(false);
                        llWenluka.setClickable(false);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                set_kai.start();
                break;
        }
    }

    /*
     * 跳转到路线规划中
     */
    public void turnToGoogleMap() {
        //TODO 跳转到路线规划中
        Intent intent = new Intent(this, RouteLineActivity.class);
        intent.putExtra("routes", routeBean);
        startActivity(intent);
    }



//    /*
//     * 检查手机上是否安装了指定的软件
//     * @param context
//     * @param packageName：应用包名
//     * @return
//     */
//    public static boolean isAvilible(Context context, String packageName) {
//        // 获取packagemanager
//        final PackageManager packageManager = context.getPackageManager();
//        // 获取所有已安装程序的包信息
//        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
//        // 用于存储所有已安装程序的包名
//        List<String> packageNames = new ArrayList<String>();
//        // 从pinfo中将包名字逐一取出，压入pName list中
//        if (packageInfos != null) {
//            for (int i = 0; i < packageInfos.size(); i++) {
//                String packName = packageInfos.get(i).packageName;
//                packageNames.add(packName);
//            }
//        }
//        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
//        return packageNames.contains(packageName);
//    }

}
