package com.aibabel.traveladvisory.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.traveladvisory.R;
import com.aibabel.traveladvisory.adapter.CommomRecyclerAdapter;
import com.aibabel.traveladvisory.adapter.CommonRecyclerViewHolder;
import com.aibabel.traveladvisory.app.BaseActivity;
import com.aibabel.traveladvisory.app.Constans;
import com.aibabel.traveladvisory.bean.InterestPointBean;
import com.aibabel.traveladvisory.custom.BackgroundDarkPopupWindow;
import com.aibabel.traveladvisory.custom.MyRecyclerView;
import com.aibabel.traveladvisory.okgo.BaseBean;
import com.aibabel.traveladvisory.okgo.BaseCallback;
import com.aibabel.traveladvisory.okgo.OkGoUtil;
import com.aibabel.traveladvisory.utils.CommonUtils;
import com.aibabel.traveladvisory.utils.FastJsonUtil;
import com.aibabel.traveladvisory.utils.OffLineUtil;
import com.aibabel.traveladvisory.utils.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：SunSH on 2018/6/15 11:53
 * 功能：
 * 版本：1.0
 */
public class InterestPointActivity extends BaseActivity implements BaseCallback {
    String TAG = "InterestPointActivity";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.xiala_type)
    ImageView xialaType;
    @BindView(R.id.ranking)
    TextView ranking;
    @BindView(R.id.xiala_ranking)
    ImageView xialaRanking;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.rv_interest_point)
    MyRecyclerView rvInterestPoint;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.ll_ranking)
    LinearLayout llRanking;
    @BindView(R.id.rl_rv)
    RelativeLayout rlRv;
    RadioGroup rgType;
    RadioGroup rgRanking;
    @BindView(R.id.iv_chaozhao)
    ImageView ivChaozhao;
    @BindView(R.id.tv_no)
    TextView tvNo;
    private View popu_type;
    private View popu_ranking;

    private BackgroundDarkPopupWindow popupWindow_type;
    private BackgroundDarkPopupWindow popupWindow_ranking;

    private String cityName;//城市名
    private String type; //类型名字
    private int whichType;
    private int whichOrder;
    private String order;

    private CommomRecyclerAdapter adapter;
    private List<InterestPointBean.DataBean> toursimDataBeanList = new ArrayList();
    private int page = 1;
    private int pageSize = 50;
    private boolean isOfflineSupport;
    private String cityId;
    private String countryName;
    String imgPath;

    @Override
    public int initLayout() {
        return R.layout.activity_interest_point;
    }

    @Override
    public void init() {
        mContext = InterestPointActivity.this;
        getIntentData();
        tvType.setText(type);
        if (TextUtils.equals(order, "positive"))
            ranking.setText("好评优先");
        else
            ranking.setText("排名优先");
        initRecycler();
        initPopupWindow();
        if (isOfflineSupport) {
            imgPath = OffLineUtil.offlinePath + OffLineUtil.offlineSupportMap.get(countryName) + "/" + countryName + "/" + cityName + "/" + type + "positive" + "/";
            initOfflineData(countryName, cityName, type, order, page);
        } else
            initData(type, cityName, page, pageSize, order);
    }

    public void getIntentData() {
        isOfflineSupport = getIntent().getBooleanExtra("isOfflineSupport", false);
        countryName = getIntent().getStringExtra("countryName");
        cityId = getIntent().getStringExtra("cityId");
        cityName = getIntent().getStringExtra("cityName");
        whichType = getIntent().getIntExtra("whichType", 0);
        type = getIntent().getStringExtra("type");
        whichOrder = getIntent().getIntExtra("whichOrder", 0);
        order = getIntent().getStringExtra("order");
    }

    public void initPopupWindow() {
        popu_type = View.inflate(InterestPointActivity.this, R.layout.popu_type, null);
        rgType = popu_type.findViewById(R.id.rg_type);
        //第一次初始化的时候通过view的id 判断默认选中指定的view，
        rgType.check(rgType.getChildAt(whichType).getId());
        popupWindow_type = new BackgroundDarkPopupWindow(popu_type, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow_type.setBackgroundDrawable(new BitmapDrawable());
        popupWindow_type.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow_type.setFocusable(true);//点击空白处关闭
        popupWindow_type.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                xialaType.setImageResource(R.mipmap.moreicon);
            }
        });

        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvType.setText(((RadioButton) group.findViewById(checkedId)).getText());
                type = ((RadioButton) group.findViewById(checkedId)).getText().toString();
                page = 1;
                if (isOfflineSupport) {
                    imgPath = OffLineUtil.offlinePath + OffLineUtil.offlineSupportMap.get(countryName) + "/" + countryName + "/" + cityName + "/" + type + "positive" + "/";
                    initOfflineData(countryName, cityName, type, order, page);
                } else
                    initData(type, cityName, page, pageSize, order);
                popupWindow_type.dismiss();
            }
        });


        popu_ranking = View.inflate(InterestPointActivity.this, R.layout.popu_ranking, null);
        rgRanking = popu_ranking.findViewById(R.id.rg_ranking);
        //第一次初始化的时候通过view的id 判断默认选中指定的view，
        rgRanking.check(rgRanking.getChildAt(whichOrder).getId());
        popupWindow_ranking = new BackgroundDarkPopupWindow(popu_ranking, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow_ranking.setBackgroundDrawable(new BitmapDrawable());
        popupWindow_ranking.setAnimationStyle(android.R.style.Animation_Dialog);
        popupWindow_ranking.setFocusable(true);//点击空白处关闭
        popupWindow_ranking.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                xialaRanking.setImageResource(R.mipmap.moreicon);
            }
        });
        rgRanking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ranking.setText(((RadioButton) group.findViewById(checkedId)).getText());
                page = 1;
                switch (checkedId) {
                    case R.id.rb_haoping:
                        order = "positive";
                        break;
                    case R.id.rb_paiming:
                        order = "ranking";
                        break;
                }
                if (isOfflineSupport) {
                    imgPath = OffLineUtil.offlinePath + OffLineUtil.offlineSupportMap.get(countryName) + "/" + countryName + "/" + cityName + "/" + type + "positive" + "/";
                    initOfflineData(countryName, cityName, type, order, page);
                } else
                    initData(type, cityName, page, pageSize, order);
                popupWindow_ranking.dismiss();
            }
        });

    }

    /**
     * 底部弹出popupWindow
     */
    private void popupWindowShow_type() {
        xialaType.setImageResource(R.mipmap.moreicon_top);
        popupWindow_type.setDarkStyle(-1);
        popupWindow_type.setDarkColor(Color.parseColor("#a0000000"));
        popupWindow_type.resetDarkPosition();
        popupWindow_type.darkBelow(ll2);
        popupWindow_type.showAsDropDown(ll2, ll2.getRight() / 2, 0);
    }

    private void popupWindowShow_ranking() {
        xialaRanking.setImageResource(R.mipmap.moreicon_top);
        popupWindow_ranking.setDarkStyle(-1);
        popupWindow_ranking.setDarkColor(Color.parseColor("#a0000000"));
        popupWindow_ranking.resetDarkPosition();
        popupWindow_ranking.darkBelow(ll2);
        popupWindow_ranking.showAsDropDown(ll2, ll2.getRight() / 2, 0);
    }

    private void initData(String type, String cityName, int page, int pageSize, String order) {
//        //在线数据
        Map<String, String> map = new HashMap<>();
        map.put("tag", type);
        map.put("cityName", cityName);
        map.put("page", page + "");
        map.put("pageSize", pageSize + "");
        map.put("turnBy", order);
//        map.put("tag", "餐饮");
//        map.put("Name", "沙巴");
//        map.put("page", page + "");
//        map.put("pageSize", pageSize + "");
        OkGoUtil.<InterestPointBean>get(this, Constans.METHOD_GET_INTEREST_POINT_MSG, map, InterestPointBean.class, this);

    }

    private void initOfflineData(final String countryName, final String cityName, final String type, final String order, final int page) {
        String filePath = OffLineUtil.offlinePath + OffLineUtil.offlineSupportMap.get(countryName) + "/" + countryName + "/" + cityName + "/" + type + order + "/" + page + ".txt";
        OffLineUtil.getInstance().getJsonData(InterestPointActivity.this, filePath, new OffLineUtil.OnOfflineLister() {
            @Override
            public void complete(String json) {
                toursimDataBeanList = (FastJsonUtil.changeJsonToList(json, InterestPointBean.DataBean.class));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateData(toursimDataBeanList);
                    }
                });
            }

            @Override
            public void error() {
                toursimDataBeanList.clear();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateData(toursimDataBeanList);
                    }
                });
            }
        });
    }

    private void initRecycler() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(InterestPointActivity.this, 1);
        rvInterestPoint.setLayoutManager(gridLayoutManager);
        adapter = new CommomRecyclerAdapter(InterestPointActivity.this, toursimDataBeanList, R.layout.rv_interest_point_item, new CommomRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
                Intent intent = new Intent(InterestPointActivity.this, CityDetailsActivity.class);
                intent.putExtra("detail", toursimDataBeanList.get(postion));
                intent.putExtra("isOfflineSupport", isOfflineSupport);
                intent.putExtra("imgPath", imgPath);
                startActivity(intent);
            }

        }, null) {

            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {
                long time = 0;
                ImageView iv_img = holder.getView(R.id.iv_img);
                TextView tv_name = holder.getView(R.id.tv_name);
                TextView tv_english = holder.getView(R.id.tv_english);
                TextView tv_evaluate = holder.getView(R.id.tv_evaluate);
                TextView tv_introduce = holder.getView(R.id.tv_introduce);
                TextView tv_paiming = holder.getView(R.id.tv_paiming);
                TextView tv_star = holder.getView(R.id.tv_star);
                TextView tv_praise = holder.getView(R.id.tv_praise);

                InterestPointBean.DataBean bean = (InterestPointBean.DataBean) o;

                tv_name.setText(bean.getCnName());
                tv_english.setText(bean.getEnName());
                tv_evaluate.setText(bean.getTagCnName());
                tv_introduce.setText(bean.getDescription());
                tv_star.setText(bean.getStar() == 0 ? "" : bean.getStar() + "星级");
                if (bean.getRanking() != 999999) {
                    tv_paiming.setText("排名第" + bean.getRanking() + "的" + bean.getPoiTypeName());
                } else {
                    tv_paiming.setText("");
                }
                if (bean.getPositive().equals("") || bean.getPositive().equals("0")) {
                    tv_praise.setText("");
                } else {
                    tv_praise.setText("好评率" + bean.getPositive());
                }
//                tv_praise.setText(bean.getPositive().equals("") ? "" : "好评率" + bean.getPositive());
                if (isOfflineSupport) {
                    Glide.with(InterestPointActivity.this)
                            .load(new File(imgPath + bean.getPoi_image(true, 1, 1)))
                            .placeholder(R.mipmap.no_tongyong3).error(R.mipmap.error_v)
                            .listener(new RequestListener<File, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, File model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    Log.e(TAG, "onException: " + e);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, File model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    return false;
                                }
                            })
                            .into(iv_img);
                } else {
                    Glide.with(InterestPointActivity.this)
                            .load(bean.getPoi_image(false, 145, 145))
                            .placeholder(R.mipmap.no_tongyong3).error(R.mipmap.error_v)
                            .into(iv_img);
                }
            }
        };
        rvInterestPoint.setEmptyView(tvNo);
        rvInterestPoint.setAdapter(adapter);
    }

    @OnClick({R.id.iv_back, R.id.iv_chaozhao, R.id.ll_type, R.id.ll_ranking})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_chaozhao:
                if (CommonUtils.isNetAvailable(this)) {
                    Intent intent = new Intent(mContext, SearchPageActivity.class);
                    intent.putExtra("cnCityName", cityName);
                    startActivity(intent);
                } else {
                    ToastUtil.showShort(this, getResources().getString(R.string.toast_lixian));
                }
                break;
            case R.id.ll_type:
                if (popupWindow_ranking.isShowing())
                    popupWindow_ranking.dismiss();
                if (popupWindow_type.isShowing()) {
                    popupWindow_type.dismiss();
                } else {
                    popupWindowShow_type();
                }
                break;
            case R.id.ll_ranking:
                if (popupWindow_type.isShowing())
                    popupWindow_type.dismiss();
                if (popupWindow_ranking.isShowing()) {
                    popupWindow_ranking.dismiss();
                } else {
                    popupWindowShow_ranking();
                }
                break;
        }
    }


    @Override
    public void onSuccess(String method, BaseBean model) {
        switch (method) {
            case Constans.METHOD_GET_INTEREST_POINT_MSG:
                toursimDataBeanList = ((InterestPointBean) model).getData();
                adapter.updateData(toursimDataBeanList);
                rvInterestPoint.scrollToPosition(0);
//                interestPointBean = (InterestPointBean) model;
                break;
        }
    }


    @Override
    public void onError(String method, String message) {

    }

}
