package com.aibabel.traveladvisory.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.traveladvisory.R;
import com.aibabel.traveladvisory.adapter.CommomRecyclerAdapter;
import com.aibabel.traveladvisory.adapter.CommonRecyclerViewHolder;
import com.aibabel.traveladvisory.app.BaseActivity;
import com.aibabel.traveladvisory.app.Constans;
import com.aibabel.traveladvisory.bean.GuojiagailanBean;
import com.aibabel.traveladvisory.bean.HotCityBean;
import com.aibabel.traveladvisory.okgo.BaseBean;
import com.aibabel.traveladvisory.okgo.BaseCallback;
import com.aibabel.traveladvisory.okgo.OkGoUtil;
import com.aibabel.traveladvisory.utils.CommonUtils;
import com.aibabel.traveladvisory.utils.FastJsonUtil;
import com.aibabel.traveladvisory.utils.MyScrollview;
import com.aibabel.traveladvisory.utils.OffLineUtil;
import com.aibabel.traveladvisory.utils.ToastUtil;
import com.aibabel.traveladvisory.utils.TravelAdDbUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.OkGo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

public class CountryActivity extends BaseActivity implements BaseCallback {

    @BindView(R.id.iv_jingqu)
    ImageView ivJingqu;
    @BindView(R.id.tv_guojia)
    TextView tvGuojia;
    @BindView(R.id.tv_guojia_en)
    TextView tvGuojiaEn;
    @BindView(R.id.cl)
    ConstraintLayout cl;
    @BindView(R.id.ll_gailan)
    LinearLayout llGailan;
    @BindView(R.id.ll_rujing)
    LinearLayout llRujing;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.v1)
    View v1;
    @BindView(R.id.ll_fengsu)
    LinearLayout llFengsu;
    @BindView(R.id.ll_anquan)
    LinearLayout llAnquan;
    @BindView(R.id.ll_jiankang)
    LinearLayout llJiankang;
    @BindView(R.id.ll_jieqing)
    LinearLayout llJieqing;
    @BindView(R.id.ll3)
    LinearLayout ll3;
    @BindView(R.id.v2)
    View v2;
    @BindView(R.id.tv_hot_city)
    TextView tvHotCity;
    @BindView(R.id.iv_citys)
    ImageView ivCitys;
    @BindView(R.id.rv_hot_citys)
    RecyclerView rvHotCitys;
    @BindView(R.id.ll_Oceania)
    LinearLayout llOceania;
    @BindView(R.id.hsv_scroll)
    MyScrollview hsvScroll;
    @BindView(R.id.tv_gailan)
    TextView tvGailan;
    @BindView(R.id.iv_left1)
    ImageView ivLeft1;
    @BindView(R.id.iv_right1)
    ImageView ivRight1;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private int isBack;
    private Context mContext;
    private String countryName;//当前国家
    private String countryId;//国家id
    private String countryNameEn;//国家英文

    private List<HotCityBean.DataBean> hotCityBeanList = new ArrayList<>();
    private GuojiagailanBean guojiagailanBean;
    private GuojiagailanBean.DataBean dataBean;
    private CommomRecyclerAdapter adapter;
    private boolean isOfflineSupport;

    @Override
    public int getLayout(Bundle savedInstanceState){
        return R.layout.activity_country;
    }

    @Override
    public void init() {
        mContext = this;
        initTitle();
        getIntentData();
        initRecycleView();
        initData();
    }

    public void initTitle() {
        ivLeft1.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        ivRight1.setVisibility(View.VISIBLE);
        ivLeft1.setVisibility(getIntent().getIntExtra("isback", View.VISIBLE));
        CommonUtils.setMargins(tvTitle, ivLeft1.getVisibility() == View.GONE ? 20 : 0, 0, ivRight1.getVisibility() == View.GONE ? 20 : 0, 0);

        ivLeft1.setImageResource(R.mipmap.fanhui_bai);
        tvTitle.setHint(getResources().getString(R.string.morenxianshi));
        tvTitle.setHintTextColor(getResources().getColor(R.color.gray66));
        tvTitle.setBackgroundResource(R.drawable.bg_search_80while);
        ivRight1.setImageResource(R.mipmap.shijie);
    }

    RequestOptions options_no_tongyong1 = new RequestOptions().placeholder(R.mipmap.no_tongyong1).error(R.mipmap.error_h);
    RequestOptions options_no_tongyong3 = new RequestOptions().placeholder(R.mipmap.no_tongyong3).error(R.mipmap.error_h);
    public void initData() {
        hotCityBeanList = TravelAdDbUtil.getReMen(countryName);
        adapter.updateData(hotCityBeanList);
        if (isOfflineSupport) {
            String filePath = OffLineUtil.offlinePath + OffLineUtil.offlineSupportMap.get(countryName) + "/" + countryName + "/" + countryName + "ID" + countryId + ".txt";
            OffLineUtil.getInstance().getJsonData(this, filePath, new OffLineUtil.OnOfflineLister() {
                @Override
                public void complete(String json) {
                    dataBean = FastJsonUtil.changeJsonToBean(json, GuojiagailanBean.DataBean.class);
                    String filePath = OffLineUtil.offlinePath + OffLineUtil.offlineSupportMap.get(countryName) + "/" + countryName + "/";
                    String imgPath = dataBean.getPlace_picture(true, 1, 1);
                    Glide.with(mContext).load(new File(filePath + imgPath))
                            .apply(options_no_tongyong1)
                            .apply(RequestOptions.bitmapTransform(new ColorFilterTransformation(CountryActivity.this, 0x4d000000)))
                            .into(ivJingqu);

                //.placeholder(R.mipmap.no_tongyong1).error(R.mipmap.error_h).bitmapTransform(new ColorFilterTransformation(CountryActivity.this, 0x4d000000)).into(ivJingqu);
                }
                @Override
                public void error() {
                }
            });
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("countryName", countryName);
            OkGoUtil.<GuojiagailanBean>get(mContext, Constans.METHOD_GUOJIAGAILAN, map, GuojiagailanBean.class, this);
        }
//        OkGoUtil.<HotCityBean>get(mContext, Constans.METHOD_GET_HOT_CITY, map, HotCityBean.class, this);
    }


    public void initRecycleView() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
//        gridLayoutManager.setScrollEnabled(false);
        rvHotCitys.setLayoutManager(gridLayoutManager);
//        rvCity.setEmptyView(tv_main_empty); //设置空布局
        adapter = new CommomRecyclerAdapter(mContext, hotCityBeanList, R.layout.rv_hot_citys, new CommomRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
                Intent intent = null;
                HotCityBean.DataBean bean = hotCityBeanList.get(postion);
                switch (bean.getType()) {
                    case "City":
                        intent = new Intent(mContext, CityActivity.class);
                        intent.putExtra("isOfflineSupport", isOfflineSupport);
                        intent.putExtra("cityName", bean.getName());
//                intent.putExtra("cityName", "巴黎");
                        intent.putExtra("cityId", bean.getId());
                        intent.putExtra("cityNameEn", bean.getEnName());
                        intent.putExtra("countryName", countryName);
                        startActivity(intent);
                        break;
                    case "countryName":
                        intent = new Intent(mContext, CountryActivity.class);
                        intent.putExtra("countryName", bean.getName());
//                intent.putExtra("cityName", "巴黎");
                        intent.putExtra("countryId", bean.getId());
                        intent.putExtra("countryNameEn", bean.getEnName());
                        startActivity(intent);
                    case "PoiId":
                        if (CommonUtils.isNetAvailable(CountryActivity.this)){
                            intent = new Intent(mContext, CityDetailsActivity.class);
                            intent.putExtra("poiId", bean.getId());
                            startActivity(intent);
                        }else {
                            ToastUtil.showShort(CountryActivity.this,R.string.toast_lianwang);
                        }
                        break;
                }
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {
                ImageView iv_city = holder.getView(R.id.iv_hot_city);
                TextView tv_city = holder.getView(R.id.tv_hot_city_name);
                HotCityBean.DataBean bean = (HotCityBean.DataBean) o;
//                if ((Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry()).contains("en-") || (Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry()).equals("US")) {
//                    tv_city.setText(bean.getEnName());
//                } else {
                tv_city.setText(bean.getName());
//                }
                 if (!isOfflineSupport) {
                    Glide.with(mContext).load(bean.getImageCityUrl(false, 162, 150))
                            .apply(options_no_tongyong3)
//                            .placeholder(R.mipmap.no_tongyong3).error(R.mipmap.error_v)
                            .into(iv_city);
                } else {
                    String imgPath = bean.getImageCityUrl(true, 1, 1);
                    String filePath = OffLineUtil.offlinePath + OffLineUtil.offlineSupportMap.get(countryName) + "/" + countryName + "/";
                    Glide.with(mContext).load(new File(filePath + bean.getName() + "/" + imgPath))
                            .apply(options_no_tongyong3)
//                            .placeholder(R.mipmap.no_tongyong3).error(R.mipmap.error_v)
                            .into(iv_city);
                }
            }


        };
        rvHotCitys.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(Constans.METHOD_GUOJIAGAILAN);
        OkGo.getInstance().cancelTag(Constans.METHOD_GET_HOT_CITY);
    }

    /**
     * 获取上个页面跳转的参数
     */
    public void getIntentData() {
//        isBack = getIntent().getIntExtra("isback", 0);
        isOfflineSupport = getIntent().getBooleanExtra("isOfflineSupport", false);
        countryName = getIntent().getStringExtra("countryName");
        countryId = getIntent().getStringExtra("countryId");
        countryNameEn = getIntent().getStringExtra("countryNameEn");

//        ivFanhui.setVisibility(isBack);
        tvGuojia.setText(countryName);
        if (countryName.contains("香港") || countryName.contains("澳门") || countryName.contains("台湾")) {
            tvGailan.setText(getResources().getString(R.string.ganlan));
        } else {
            tvGailan.setText(getResources().getString(R.string.guojiaganlan));
        }
        tvGuojiaEn.setText(countryNameEn);
//        if (countryName.equals("中国")) {
//            llOceania.setVisibility(View.GONE);
//        } else {
        llOceania.setVisibility(View.VISIBLE);
//        }
    }

    @OnClick({R.id.iv_left1, R.id.iv_right1, R.id.tv_title, R.id.ll_gailan, R.id.ll_rujing, R.id.ll_fengsu, R.id.ll_anquan, R.id.ll_jiankang, R.id.ll_jieqing, R.id.iv_citys, R.id.ll_Oceania})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.iv_fanhui:
            case R.id.iv_left1:
                finish();
                break;
            case R.id.iv_right1:
//            case R.id.iv_diqiu:
                startActivity(new Intent(mContext, WorldCountryActivity.class));
                break;
            case R.id.tv_title:
//            case R.id.iv_chazhao:
                if (CommonUtils.isNetAvailable(this)){
                    startActivity(new Intent(mContext, SearchPageActivity.class));
                }else {
                    ToastUtil.showShort(this,getResources().getString(R.string.toast_lixian));
                }
                break;
            case R.id.ll_gailan:
                if (dataBean != null) {
                    Intent gailanIntent = new Intent(mContext, GailanActivity.class);
                    gailanIntent.putExtra("placeId", dataBean.getOverview_info().getContent().getPlaceId());
                    startActivity(gailanIntent);
                } else {
                    ToastUtil.show(mContext, getResources().getString(R.string.toast_zanwuxinxi), Toast.LENGTH_SHORT);
                }
                break;
            case R.id.ll_rujing:
                if (dataBean != null && dataBean.getVisa_info() != null && !dataBean.getVisa_info().getContent().equals("")) {
                    Intent rujingkaIntent = new Intent(mContext, RujingkaActivity.class);
                    rujingkaIntent.putExtra("imgUrl", dataBean.getVisa_info().getContent());
                    rujingkaIntent.putExtra("countryName", countryName);
                    rujingkaIntent.putExtra("isOfflineSupport", isOfflineSupport);
                    rujingkaIntent.putExtra("title", getResources().getString(R.string.rujingkazhinan));
                    startActivity(rujingkaIntent);
                } else {
                    ToastUtil.show(mContext, getResources().getString(R.string.toast_zanwuxinxi), Toast.LENGTH_SHORT);
                }
                break;
            case R.id.ll_fengsu:
                if (dataBean != null)
                    gotoHtmlActivity(dataBean.getImpress_overview().getCustoms().getContent(), getResources().getString(R.string.fengsu));
                break;
            case R.id.ll_anquan:
                if (dataBean != null) {
                    gotoHtmlActivity(dataBean.getImpress_overview().getSecurity().getContent(), getResources().getString(R.string.anquan));
                } else {
                    ToastUtil.show(mContext, getResources().getString(R.string.toast_zanwuxinxi), Toast.LENGTH_SHORT);
                }
                break;
            case R.id.ll_jiankang:
                if (dataBean != null) {
                    gotoHtmlActivity(dataBean.getImpress_overview().getHealthy().getContent(), getResources().getString(R.string.jiankang));
                } else {
                    ToastUtil.show(mContext, getResources().getString(R.string.toast_zanwuxinxi), Toast.LENGTH_SHORT);
                }
                break;
            case R.id.ll_jieqing:
                if (dataBean != null) {
                    gotoHtmlActivity(dataBean.getImpress_overview().getFestival().getContent(), getResources().getString(R.string.jieqing));
                } else {
                    ToastUtil.show(mContext, getResources().getString(R.string.toast_zanwuxinxi), Toast.LENGTH_SHORT);
                }
                break;
            case R.id.iv_citys:
                break;
            case R.id.ll_Oceania:
                Intent intent = new Intent(mContext, AllCitysActivity.class);
                intent.putExtra("countryName", countryName);
                intent.putExtra("isOfflineSupport", isOfflineSupport);
                startActivity(intent);
                break;
        }
    }

    public void gotoHtmlActivity(String html, String which) {
        if (TextUtils.equals("", html)) {
            ToastUtil.show(mContext, getResources().getString(R.string.toast_zanwuxinxi), Toast.LENGTH_SHORT);
        } else {
            Intent intent = new Intent(mContext, HtmlActivity.class);
            intent.putExtra("place", which);
//            intent.putExtra("place", dataBean.getOverview_info().getContent().getPlaceCnName());
            intent.putExtra("html", html);
            startActivity(intent);
        }
    }


    @Override
    public void onSuccess(String method, BaseBean model) {
        switch (method) {
            case Constans.METHOD_GUOJIAGAILAN:
                guojiagailanBean = (GuojiagailanBean) model;
                dataBean = guojiagailanBean.getData().get(0);
                Log.e( "onSuccess: " ,dataBean.getPlace_picture(false, 540, 425));
                Glide.with(mContext).load(dataBean.getPlace_picture(false, 540, 425))
                        .apply(options_no_tongyong1)
                        .apply(RequestOptions.bitmapTransform(new ColorFilterTransformation(this, 0x4d000000)))
                        .into(ivJingqu);

//                        .placeholder(R.mipmap.no_tongyong1).error(R.mipmap.error_h)
//                        .bitmapTransform(new ColorFilterTransformation(this, 0x4d000000)).into(ivJingqu);
                break;
            case Constans.METHOD_GET_HOT_CITY:
                hotCityBeanList = ((HotCityBean) model).getData();
                adapter.updateData(hotCityBeanList);
                break;
        }
    }


    @Override
    public void onError(String method, String message) {
//        ToastUtil.show(mContext, message, Toast.LENGTH_SHORT);
    }

}
