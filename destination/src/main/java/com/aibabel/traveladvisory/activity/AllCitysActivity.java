package com.aibabel.traveladvisory.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.traveladvisory.R;
import com.aibabel.traveladvisory.adapter.CommomRecyclerAdapter;
import com.aibabel.traveladvisory.adapter.CommonItemDecoration;
import com.aibabel.traveladvisory.adapter.CommonRecyclerViewHolder;
import com.aibabel.traveladvisory.app.BaseActivity;
import com.aibabel.traveladvisory.app.Constans;
import com.aibabel.traveladvisory.bean.CountryCitysBean;
import com.aibabel.traveladvisory.okgo.BaseBean;
import com.aibabel.traveladvisory.okgo.BaseCallback;
import com.aibabel.traveladvisory.okgo.OkGoUtil;
import com.aibabel.traveladvisory.utils.CommonUtils;
import com.aibabel.traveladvisory.utils.OffLineUtil;
import com.aibabel.traveladvisory.utils.ToastUtil;
import com.aibabel.traveladvisory.utils.TravelAdDbUtil;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AllCitysActivity extends BaseActivity implements BaseCallback {

    @BindView(R.id.iv_fanhui)
    ImageView ivFanhui;
    @BindView(R.id.tv_palce)
    TextView tvPalce;
    @BindView(R.id.iv_chazhao)
    ImageView ivChazhao;
    @BindView(R.id.rv_citys)
    RecyclerView rvCitys;

    List<CountryCitysBean.DataBean> countryCityList = new ArrayList<>();
    CommomRecyclerAdapter<CountryCitysBean.DataBean> adapter;
    private String countryName; //当前城市
    private CountryCitysBean countryCitysBean;

    private int page = 1;
    private int pageSize = 30;
    private boolean canLoadMore = true;
    private boolean isOfflineSupport;

    @Override
    public int initLayout() {
        return R.layout.activity_all_citys;
    }

    @Override
    public void init() {

        getIntentData();

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        rvCitys.setLayoutManager(gridLayoutManager);
        adapter = new CommomRecyclerAdapter(AllCitysActivity.this, countryCityList, R.layout.item_layout_for_citys, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
                CountryCitysBean.DataBean bean = countryCityList.get(postion);
                Intent intent = new Intent(AllCitysActivity.this, CityActivity.class);
                intent.putExtra("cityId", bean.getId());
                intent.putExtra("cityName", bean.getCnName());
                intent.putExtra("cityNameEn", bean.getEnName());
                intent.putExtra("countryName", countryName);
                intent.putExtra("isOfflineSupport", isOfflineSupport);
                startActivity(intent);
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {
                TextView tv_city_name = holder.getView(R.id.tv_city_name);
                TextView tv_city_attractions = holder.getView(R.id.tv_city_attractions);
                ImageView iv_city_pic = holder.getView(R.id.iv_city_pic);
//                CitysBean.DataBean.ListBean bean = (CitysBean.DataBean.ListBean) o;
                tv_city_name.setText(((CountryCitysBean.DataBean) o).getCnName());
                tv_city_attractions.setText(((CountryCitysBean.DataBean) o).getTravelTime().getBusy().getMonth() + ((CountryCitysBean.DataBean) o).getTravelTime().getBusy().getDesc() + "\n"
                        + ((CountryCitysBean.DataBean) o).getTravelTime().getCommon().getMonth() + ((CountryCitysBean.DataBean) o).getTravelTime().getCommon().getDesc() + "\n"
                        + ((CountryCitysBean.DataBean) o).getTravelTime().getSlack().getMonth() + ((CountryCitysBean.DataBean) o).getTravelTime().getSlack().getDesc() + "\n");
                if (!isOfflineSupport) {
                    Glide.with(mContext).load(((CountryCitysBean.DataBean) o).getImageUrl(false, 144, 164)).placeholder(R.mipmap.no_tongyong3).error(R.mipmap.error_v).into(iv_city_pic);
                } else {
                    String imgPath = ((CountryCitysBean.DataBean) o).getImageUrl(true, 1, 1);
                    String filePath = OffLineUtil.offlinePath + OffLineUtil.offlineSupportMap.get(countryName) + "/" + countryName + "/";
                    Glide.with(mContext).load(new File(filePath + ((CountryCitysBean.DataBean) o).getCnName() + "/" + imgPath)).placeholder(R.mipmap.no_tongyong3).error(R.mipmap.error_v).into(iv_city_pic);
                }
            }
        };
        rvCitys.addItemDecoration(new CommonItemDecoration(getDrawable(R.drawable.h_line_d7)));
        //设置Adapter
        rvCitys.setAdapter(adapter);
        initData();

        rvCitys.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0) {
                    //加载更多
                    if (canLoadMore)
                        initData();
                }
            }
        });

    }

    public void getIntentData() {
        countryName = getIntent().getStringExtra("countryName");
        isOfflineSupport = getIntent().getBooleanExtra("isOfflineSupport", false);
        tvPalce.setText(countryName);
    }

    public void initData() {
//        if (isOfflineSupport) {
        countryCityList = TravelAdDbUtil.getAllChengshi(countryName);
        adapter.updateData(countryCityList);
//        } else {
//            Map<String, String> map = new HashMap<>();
//            map.put("countryName", countryName);
//            map.put("page", page + "");
//
//            OkGoUtil.<CountryCitysBean>get(this, Constans.METHOD_ALLCITY_IN_COUNTRY, map, CountryCitysBean.class, this);
//        }
    }


    @OnClick({R.id.iv_fanhui, R.id.iv_chazhao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_fanhui:
                finish();
                break;
            case R.id.iv_chazhao:
                if (CommonUtils.isNetAvailable(this)) {
                    startActivity(new Intent(AllCitysActivity.this, SearchPageActivity.class));
                } else {
                    ToastUtil.showShort(this, getResources().getString(R.string.toast_lixian));
                }
                break;
        }
    }


    @Override
    public void onSuccess(String method, BaseBean model) {
        switch (method) {
            case Constans.METHOD_ALLCITY_IN_COUNTRY:
                countryCitysBean = (CountryCitysBean) model;
                countryCityList = countryCitysBean.getData();
                if (countryCityList.size() > 0) {
                    canLoadMore = true;
                    page++;
                    countryCityList.addAll(countryCityList);
                } else {
                    canLoadMore = false;
                }
                adapter.updateData(countryCityList);
                break;
        }
    }

    @Override
    public void onError(String method, String message) {
        ToastUtil.show(this, getResources().getString(R.string.toast_wangluocuowu), Toast.LENGTH_SHORT);
    }
}
