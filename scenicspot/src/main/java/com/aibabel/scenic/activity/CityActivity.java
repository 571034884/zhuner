package com.aibabel.scenic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.sphelper.SPHelper;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.baselibrary.utils.ProviderUtils;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.scenic.R;
import com.aibabel.scenic.adapter.CardHomeAdapter;
import com.aibabel.scenic.adapter.CityAdapter;
import com.aibabel.scenic.adapter.GridAdapter;
import com.aibabel.scenic.adapter.HistoryAdapter;
import com.aibabel.scenic.base.BaseScenicActivity;
import com.aibabel.scenic.base.ScenicBaseApplication;
import com.aibabel.scenic.bean.PoiDetailsBean;
import com.aibabel.scenic.bean.ScenicBean;
import com.aibabel.scenic.okgo.ApiConstant;
import com.aibabel.scenic.utils.Logs;
import com.aibabel.scenic.view.CustomGridView;
import com.aibabel.scenic.view.EmptyLayout;
import com.aibabel.scenic.view.ShadowTransformer;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class CityActivity extends BaseScenicActivity {
    @BindView(R.id.vp_info)
    ViewPager vpInfo;
    @BindView(R.id.tv_hot)
    TextView tvHot;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.v_hot)
    View vHot;
    @BindView(R.id.v_about)
    View vAbout;
    @BindView(R.id.v_collect)
    View vCollect;
    @BindView(R.id.c_grid)
    CustomGridView mGrid;
    @BindView(R.id.rlv_history)
    RecyclerView mRecyclerHistory;
    @BindView(R.id.sl_match)
    ScrollView mScroll;
    @BindView(R.id.item_null)
    RelativeLayout mRelaNull;
    @BindView(R.id.iv_null_view)
    ImageView mNullIv;
    @BindView(R.id.tv_null_view)
    TextView mNullTv;
    @BindView(R.id.tv_history)
    TextView hideHistory;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.empty)
    EmptyLayout mEmpty;


    private ShadowTransformer mCardTransformer;
    private CardHomeAdapter mCardAdapter;
    private HistoryAdapter mHistoryAdapter;
    private GridAdapter mGridAdapter;
    //0 热门   1附近   2收藏
    private int flagIndex = 0;
    private String city;
    @Override
    public int getLayouts(Bundle var1) {
        return R.layout.activity_city;
    }

    @Override
    public void initView() {
        city = getIntent().getStringExtra("city");
        tvTitle.setText(city);
        isNetWork();
        mEmpty.setOnBtnClickListener(new EmptyLayout.onClickListener() {
            @Override
            public void onBtnClick() {
                isNetWork();
            }
        });
    }

    private void isNetWork() {
        if (CommonUtils.isNetworkAvailable(mContext)) {
            mScroll.setVisibility(View.VISIBLE);
            getDataValue(city);
        } else {
            mScroll.setVisibility(View.GONE);
            mEmpty.setErrorType(EmptyLayout.NETWORK_EMPTY);
        }
    }

    @Override
    public void initData() {

    }

    /**
     * 网络请求
     *
     * @param city
     */
    private void getDataValue(String city) {
        Map<String, String> map = new HashMap<>();
        map.put("cityFrom", "cityName");
        map.put("cityName", city);
        map.put("lat", ProviderUtils.getInfo(ProviderUtils.COLUMN_LATITUDE));
        map.put("lng", ProviderUtils.getInfo(ProviderUtils.COLUMN_LONGITUDE));
        map.put("leaseId", SPHelper.getString("order_oid",""));

        OkGoUtil.get(mContext, ApiConstant.GET_HOME_SCENIC, map, ScenicBean.class, new BaseCallback<ScenicBean>() {
            @Override
            public void onSuccess(String method, ScenicBean model, String resoureJson) {
                Logs.e(ApiConstant.GET_HOME_SCENIC + "：" + resoureJson);
                mEmpty.setErrorType(EmptyLayout.SUCCESS_EMPTY);
                showDetails(model.data);
            }

            @Override
            public void onError(String method, String message, String resoureJson) {
                mEmpty.setErrorType(EmptyLayout.ERROR_EMPTY);
                Logs.e(ApiConstant.GET_HOME_SCENIC + "：" + message);
            }

            @Override
            public void onFinsh(String method) {

            }
        });
    }

    ScenicBean.Datas data = null;
    List<PoiDetailsBean> poiTop = null;
    List<PoiDetailsBean> poiMsgHot = null;
    List<PoiDetailsBean> poiCountry = null;

    private void showDetails(ScenicBean.Datas data) {
        this.data = data;
        //TOP数据
        if (data.poiTop == null) {
            poiTop = new ArrayList<>();
        } else {
            poiTop = data.poiTop;
        }
        mCardAdapter = new CardHomeAdapter(mContext, poiTop);
        mCardTransformer = new ShadowTransformer(vpInfo, mCardAdapter);
        vpInfo.setAdapter(mCardAdapter);
        mCardTransformer.enableScaling(true);
        mCardAdapter.setOnItemClickListener(new CardHomeAdapter.onClickListener() {
            @Override
            public void onItemClick(PoiDetailsBean bean, View view) {
                Intent intent = new Intent();
                intent.setClass(CityActivity.this, SpotsActivity.class);
                intent.putExtra("poiId", bean.idstring);
                startActivity(intent);
            }
        });
        //热门
        getShowView(0);
        poiMsgHot = data.poiMsgHot;
        mGridAdapter = new GridAdapter(mContext, poiMsgHot);
        mGrid.setAdapter(mGridAdapter);
        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoiDetailsBean bean = poiMsgHot.get(position);
                Intent intent = new Intent();
                intent.setClass(CityActivity.this, SpotsActivity.class);
                intent.putExtra("poiId", bean.idstring);
                startActivity(intent);
            }
        });


        //国家历史
        poiCountry = data.countryHistoryCustom;
        if (poiCountry.size() == 0) {
            hideHistory.setVisibility(View.GONE);
        } else {
            hideHistory.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerHistory.setLayoutManager(ms);
        mHistoryAdapter = new HistoryAdapter(R.layout.item_history, poiCountry);
        mRecyclerHistory.setAdapter(mHistoryAdapter);
        mHistoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(CityActivity.this, HistoryActivity.class);
                intent.putExtra("json", FastJsonUtil.changListToString(poiCountry));
                Logs.e(FastJsonUtil.changListToString(poiCountry));
                intent.putExtra("position", position);
                intent.putExtra("menuCountryId", data.menuCountryId+"");
                intent.putExtra("historyPage", data.historyPage+"");
                intent.putExtra("imageCountry", data.imageCountry+"");
                Logs.e("menuCountryId:"+data.menuCountryId+",historyPage:"+data.historyPage+",imageCountry:"+data.imageCountry+"");
                startActivity(intent);
            }
        });
    }


    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_hot://热门
                getShowView(0);
                break;
            case R.id.ll_about://附近
                getShowView(1);
                break;
            case R.id.ll_collect://收藏
                getShowView(2);
                break;
            case R.id.ll_more://更多
                toScenic();
                break;
            case R.id.tv_left:
                finish();
                break;
        }
    }

    /**
     * 跳转更多
     * @param
     */
    private void toScenic() {
        Intent intent = new Intent(this, ScenicActivity.class);
        intent.putExtra("index", flagIndex);
        intent.putExtra("cityName", city);
        startActivity(intent);
    }
    private void getShowView(int index) {
        if (flagIndex == index) {
            return;
        }
        flagIndex = index;
        defaultViewType(index);
    }


    /**
     * 重置状态
     *
     * @param index
     */
    private void defaultViewType(int index) {
        tvHot.setTextSize(15);
        tvHot.setTextColor(getResources().getColor(R.color.c_666666));
        tvHot.getPaint().setFakeBoldText(false);

        tvAbout.setTextSize(15);
        tvAbout.setTextColor(getResources().getColor(R.color.c_666666));
        tvAbout.getPaint().setFakeBoldText(false);

        tvCollect.setTextSize(15);
        tvCollect.setTextColor(getResources().getColor(R.color.c_666666));
        tvCollect.getPaint().setFakeBoldText(false);

        vHot.setVisibility(View.INVISIBLE);
        vAbout.setVisibility(View.INVISIBLE);
        vCollect.setVisibility(View.INVISIBLE);

        switch (index) {
            case 0:
                tvHot.setTextSize(17);
                tvHot.setTextColor(getResources().getColor(R.color.c_ff781d));
                tvHot.getPaint().setFakeBoldText(true);
                vHot.setVisibility(View.VISIBLE);

                if (data.poiMsgHot != null && data.poiMsgHot.size() != 0) {
                    mRelaNull.setVisibility(View.GONE);
                    mGrid.setVisibility(View.VISIBLE);
                    poiMsgHot = data.poiMsgHot;
                    mGridAdapter = new GridAdapter(mContext, poiMsgHot);
                    mGrid.setAdapter(mGridAdapter);
                } else {
                    mRelaNull.setVisibility(View.VISIBLE);
                    mGrid.setVisibility(View.GONE);
                    mNullTv.setText("暂无数据");
                }

                break;
            case 1:
                tvAbout.setTextSize(17);
                tvAbout.setTextColor(getResources().getColor(R.color.c_ff781d));
                tvAbout.getPaint().setFakeBoldText(true);
                vAbout.setVisibility(View.VISIBLE);

                if (data.poiMsgNearby != null && data.poiMsgNearby.size() != 0) {
                    mRelaNull.setVisibility(View.GONE);
                    mGrid.setVisibility(View.VISIBLE);
                    poiMsgHot = data.poiMsgNearby;
                    mGridAdapter = new GridAdapter(mContext, poiMsgHot);
                    mGrid.setAdapter(mGridAdapter);

                } else {
                    mRelaNull.setVisibility(View.VISIBLE);
                    mGrid.setVisibility(View.GONE);
                    mNullTv.setText("定位失败，请查看热门景点");
                }
                break;
            case 2:
                tvCollect.setTextSize(17);
                tvCollect.setTextColor(getResources().getColor(R.color.c_ff781d));
                tvCollect.getPaint().setFakeBoldText(true);
                vCollect.setVisibility(View.VISIBLE);

                if (data.poiMsgMy != null && data.poiMsgMy.size() != 0) {
                    mRelaNull.setVisibility(View.GONE);
                    mGrid.setVisibility(View.VISIBLE);
                    poiMsgHot = data.poiMsgMy;
                    mGridAdapter = new GridAdapter(mContext, poiMsgHot);
                    mGrid.setAdapter(mGridAdapter);
                } else {
                    mRelaNull.setVisibility(View.VISIBLE);
                    mGrid.setVisibility(View.GONE);
                    mNullTv.setText("收藏夹为空");
                }
                break;
        }

    }
}
