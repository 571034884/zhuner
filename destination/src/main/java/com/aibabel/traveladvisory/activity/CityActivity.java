package com.aibabel.traveladvisory.activity;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.traveladvisory.R;
import com.aibabel.traveladvisory.app.BaseActivity;
import com.aibabel.traveladvisory.app.Constans;
import com.aibabel.traveladvisory.bean.ChengshigailanBean;
import com.aibabel.traveladvisory.okgo.BaseBean;
import com.aibabel.traveladvisory.okgo.BaseCallback;
import com.aibabel.traveladvisory.okgo.OkGoUtil;
import com.aibabel.traveladvisory.utils.CommonUtils;
import com.aibabel.traveladvisory.utils.FastJsonUtil;
import com.aibabel.traveladvisory.utils.OffLineUtil;
import com.aibabel.traveladvisory.utils.ToastUtil;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

public class CityActivity extends BaseActivity implements BaseCallback {

    @BindView(R.id.iv_jingqu)
    ImageView ivJingqu;
    @BindView(R.id.tv_chengshi)
    TextView tvChengshi;
    @BindView(R.id.tv_chengshi_en)
    TextView tvChengshiEn;
    @BindView(R.id.tv_wendu)
    TextView tvWendu;
    @BindView(R.id.tv_tianqi)
    TextView tvTianqi;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tv_shijian)
    TextView tvShijian;
    @BindView(R.id.cl)
    ConstraintLayout cl;
    @BindView(R.id.gl_horizontal)
    Guideline glHorizontal;
    @BindView(R.id.gl_vertical)
    Guideline glVertical;
    @BindView(R.id.ll_jingdian)
    LinearLayout llJingdian;
    @BindView(R.id.ll_canyin)
    LinearLayout llCanyin;
    @BindView(R.id.ll_gouwu)
    LinearLayout llGouwu;
    @BindView(R.id.ll_zhusu)
    LinearLayout llZhusu;
    @BindView(R.id.ll_gailan)
    LinearLayout ll_gailan;
    @BindView(R.id.ll_jiaotong)
    LinearLayout ll_jiaotong;
    @BindView(R.id.iv_left1)
    ImageView ivLeft1;
    @BindView(R.id.iv_right1)
    ImageView ivRight1;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private int isBack;
    private String cityId;
    private String cityName;
    private String cityNameEn;
    private Context mContext;
    private ChengshigailanBean chengshigailanBean;
    private ChengshigailanBean.DataBean dataBean;
    private boolean isOfflineSupport;
    private String countryName;

    @Override
    public int initLayout() {
        return R.layout.activity_city;
    }

    @Override
    public void init() {
        mContext = this;

        initTitle();
        getIntentData();
        initData();
    }


    @Override
    protected void onStart() {
        super.onStart();
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

    /**
     * 获取上个页面跳转的参数
     */
    public void getIntentData() {

//        isBack = getIntent().getIntExtra("isback", 0);
        isOfflineSupport = getIntent().getBooleanExtra("isOfflineSupport", false);
        cityId = getIntent().getStringExtra("cityId");
        cityName = getIntent().getStringExtra("cityName");
        cityNameEn = getIntent().getStringExtra("cityNameEn");
        countryName = getIntent().getStringExtra("countryName");

//        ivFanhui.setVisibility(isBack);
        tvChengshi.setText(cityName);
        tvChengshiEn.setText(cityNameEn);

    }

    public void initData() {
        if (isOfflineSupport) {
            String filePath = OffLineUtil.offlinePath + OffLineUtil.offlineSupportMap.get(countryName) + "/" + countryName + "/" + cityName + "/" + cityName + "ID" + cityId + ".txt";
            OffLineUtil.getInstance().getJsonData(this, filePath, new OffLineUtil.OnOfflineLister() {
                @Override
                public void complete(String json) {
                    dataBean = FastJsonUtil.changeJsonToBean(json, ChengshigailanBean.DataBean.class);
                    String filePath = OffLineUtil.offlinePath + OffLineUtil.offlineSupportMap.get(countryName) + "/" + countryName + "/"+ cityName + "/";
                    String imgPath = dataBean.getPlace_picture(true, 1, 1);
                    Glide.with(mContext).load(new File(filePath + imgPath)).placeholder(R.mipmap.no_tongyong1).error(R.mipmap.error_h)
                            .bitmapTransform(new ColorFilterTransformation(CityActivity.this, 0x4d000000)).into(ivJingqu);
                }
                @Override
                public void error() {
                }
            });
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("cityId", cityId);
            OkGoUtil.<ChengshigailanBean>get(mContext, Constans.METHOD_CHEGNSHIGAILAN_ID, map, ChengshigailanBean.class, this);
        }
    }

    @OnClick({R.id.iv_left1, R.id.iv_right1, R.id.tv_title, R.id.ll_gailan, R.id.ll_jiaotong, R.id.ll_jingdian, R.id.ll_canyin, R.id.ll_gouwu, R.id.ll_zhusu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.iv_fanhui:
            case R.id.iv_left1:
                finish();
                break;
            case R.id.ll_gailan:
//                Log.e("onViewClicked: ", cityId);
                if (!TextUtils.equals(cityId, "") && !TextUtils.equals(cityId, null)) {
                    Intent gailanIntent = new Intent(mContext, GailanActivity.class);
                    gailanIntent.putExtra("placeId", cityId);
                    startActivity(gailanIntent);
                } else {
                    ToastUtil.show(CityActivity.this, getResources().getString(R.string.toast_zanwuxinxi), 1000);
                }
                break;
            case R.id.ll_jiaotong:
                if (dataBean != null && dataBean.getStrateg_info() != null) {
                    gotoHtmlActivity(dataBean.getStrateg_info().getContent(), getResources().getString(R.string.bendijiaotong));
                } else {
                    ToastUtil.show(CityActivity.this, getResources().getString(R.string.toast_zanwuxinxi), 1000);
                }
                break;
            case R.id.iv_right1:
//            case R.id.iv_diqiu:
                startActivity(new Intent(mContext, WorldCountryActivity.class));
                break;
            case R.id.tv_title:
                if (CommonUtils.isNetAvailable(this)){
                    Intent intent = new Intent(mContext, SearchPageActivity.class);
                    intent.putExtra("cnCityName", cityName);
                    startActivity(intent);
                }else {
                    ToastUtil.showShort(this,getResources().getString(R.string.toast_lixian));
                }
                break;
            case R.id.ll_jingdian:
                gotoInterstPoint(0, "景点", 1, "positive");
                break;
            case R.id.ll_canyin:
                gotoInterstPoint(1, "餐饮", 1, "positive");
                break;
            case R.id.ll_gouwu:
                gotoInterstPoint(2, "购物", 1, "positive");
                break;
            case R.id.ll_zhusu:
                gotoInterstPoint(3, "住宿", 1, "positive");
                break;
        }
    }

    public void gotoInterstPoint(int whichType, String type, int whichOrder, String order) {
        Intent intent = new Intent(mContext, InterestPointActivity.class);
        intent.putExtra("isOfflineSupport", isOfflineSupport);//城市id
        intent.putExtra("cityId", cityId);//城市id
        intent.putExtra("cityName", cityName);//城市名字
        intent.putExtra("countryName", countryName);//城市名字
        intent.putExtra("whichType", whichType);//点的是哪一个
        intent.putExtra("whichOrder", whichOrder);//点的是哪一个
        intent.putExtra("type", type);//点的位置类型名字
        intent.putExtra("order", order);//点的位置类型名字
        startActivity(intent);
    }

    public void gotoHtmlActivity(String html, String which) {
        if (TextUtils.equals("", html)) {
            ToastUtil.show(mContext, getResources().getString(R.string.toast_zanwuxinxi), Toast.LENGTH_SHORT);
        } else {
            Intent intent = new Intent(mContext, HtmlActivity.class);
            intent.putExtra("place", which);
//            intent.putExtra("place", guojiagailanBean.getData().get(0).getOverview_info().getContent().getPlaceCnName());
            intent.putExtra("html", html);
            startActivity(intent);
        }
    }

    @Override
    public void onSuccess(String method, BaseBean model) {
        switch (method) {
            case Constans.METHOD_CHEGNSHIGAILAN_ID:
                chengshigailanBean = (ChengshigailanBean) model;
                dataBean = chengshigailanBean.getData().get(0);
                Glide.with(mContext).load(dataBean.getPlace_picture(false, 540, 425)).placeholder(R.mipmap.no_tongyong1).error(R.mipmap.error_h)
                        .bitmapTransform(new ColorFilterTransformation(this, 0x4d000000)).into(ivJingqu);
                break;
        }
    }

    @Override
    public void onError(String method, String message) {

    }
}
