package com.aibabel.traveladvisory.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.traveladvisory.R;
import com.aibabel.traveladvisory.adapter.CommomRecyclerAdapter;
import com.aibabel.traveladvisory.adapter.CommonRecyclerViewHolder;
import com.aibabel.traveladvisory.app.BaseActivity;
import com.aibabel.traveladvisory.app.Constans;
import com.aibabel.traveladvisory.bean.HotBean;
import com.aibabel.traveladvisory.bean.WorldCountryBean;
import com.aibabel.traveladvisory.custom.MyRecyclerView;
import com.aibabel.traveladvisory.okgo.BaseBean;
import com.aibabel.traveladvisory.okgo.BaseCallback;
import com.aibabel.traveladvisory.okgo.OkGoUtil;
import com.aibabel.traveladvisory.utils.MyGridLayoutManager;
import com.aibabel.traveladvisory.utils.MyScrollview;
import com.aibabel.traveladvisory.utils.OffLineUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class WorldCountryActivityOnline extends BaseActivity implements BaseCallback {

    @BindView(R.id.rv_hot_citys)
    RecyclerView rvHotCitys;
    @BindView(R.id.ll_hot)
    LinearLayout llHot;
    @BindView(R.id.ll_hot_citys)
    LinearLayout llHotCitys;
    @BindView(R.id.rv_asia_citys)
    MyRecyclerView rvAsiaCitys;
    @BindView(R.id.ll_asia)
    LinearLayout llAsia;
    @BindView(R.id.ll_asia_citys)
    LinearLayout llAsiaCitys;
    @BindView(R.id.rv_europe_citys)
    RecyclerView rvEuropeCitys;
    @BindView(R.id.ll_europe)
    LinearLayout llEurope;
    @BindView(R.id.ll_europe_citys)
    LinearLayout llEuropeCitys;
    @BindView(R.id.rv_africa_citys)
    RecyclerView rvAfricaCitys;
    @BindView(R.id.ll_africa)
    LinearLayout llAfrica;
    @BindView(R.id.ll_africa_citys)
    LinearLayout llAfricaCitys;
    @BindView(R.id.rv_NorthAmerica_citys)
    RecyclerView rvNorthAmericaCitys;
    @BindView(R.id.ll_NorthAmerica)
    LinearLayout llNorthAmerica;
    @BindView(R.id.ll_NorthAmerica_citys)
    LinearLayout llNorthAmericaCitys;
    @BindView(R.id.rv_SouthAmerica_citys)
    RecyclerView rvSouthAmericaCitys;
    @BindView(R.id.ll_SouthAmerica)
    LinearLayout llSouthAmerica;
    @BindView(R.id.ll_SouthAmerica_citys)
    LinearLayout llSouthAmericaCitys;
    @BindView(R.id.rv_Oceania_citys)
    RecyclerView rvOceaniaCitys;
    @BindView(R.id.ll_Oceania)
    LinearLayout llOceania;
    @BindView(R.id.ll_Oceania_citys)
    LinearLayout llOceaniaCitys;
    @BindView(R.id.hsvScroll)
    MyScrollview hsvScroll;
    //    @BindView(R.id.tv_search)
//    TextView tvSearch;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private HotBean hotCountryBean;
    private WorldCountryBean worldCountryBean;
    private List<HotBean.DataBean> hot_city_list = new ArrayList();
    private List<WorldCountryBean.DataBean.CountryStateBean> asia_city_list = new ArrayList();
    private List<WorldCountryBean.DataBean.CountryStateBean> europe_city_list = new ArrayList();
    private List<WorldCountryBean.DataBean.CountryStateBean> africa_city_list = new ArrayList();
    private List<WorldCountryBean.DataBean.CountryStateBean> northAmerica_city_list = new ArrayList();
    private List<WorldCountryBean.DataBean.CountryStateBean> southAmerica_city_list = new ArrayList();
    private List<WorldCountryBean.DataBean.CountryStateBean> oceania_city_list = new ArrayList();

    private CommomRecyclerAdapter hotAdapter;
    private CommomRecyclerAdapter asiaAdapter;
    private CommomRecyclerAdapter europeAdapter;
    private CommomRecyclerAdapter africaAdapter;
    private CommomRecyclerAdapter northAmericaAdapter;
    private CommomRecyclerAdapter southAmericaAdapter;
    private CommomRecyclerAdapter oceaniaAdapter;

    @Override
    public int initLayout() {
        return R.layout.activity_world__city;
    }

    @Override
    public void init() {
        mContext = WorldCountryActivityOnline.this;
        initTitle();
        initRecycleView();
        initData();
        hsvScroll.setFocusableInTouchMode(true);
        hsvScroll.requestFocus();
    }

    public void initTitle() {
        tvTitle.setVisibility(View.VISIBLE);
    }

    public void initRecycleView() {
        hotAdapter = initHotView(rvHotCitys, R.layout.hot_city, 2, hot_city_list);
        asiaAdapter = initView(rvAsiaCitys, R.layout.continent_city, 3, asia_city_list);
        europeAdapter = initView(rvEuropeCitys, R.layout.continent_city, 3, europe_city_list);
        africaAdapter = initView(rvAfricaCitys, R.layout.continent_city, 3, africa_city_list);
        northAmericaAdapter = initView(rvNorthAmericaCitys, R.layout.continent_city, 3, northAmerica_city_list);
        southAmericaAdapter = initView(rvSouthAmericaCitys, R.layout.continent_city, 3, southAmerica_city_list);
        oceaniaAdapter = initView(rvOceaniaCitys, R.layout.continent_city, 3, oceania_city_list);
    }


    private void initData() {
        Map<String, String> map = new HashMap<>();
        OkGoUtil.<WorldCountryBean>get(this, Constans.METHOD_GET_WORLD_COUNTRY, map, WorldCountryBean.class, this);
        OkGoUtil.<HotBean>get(this, Constans.METHOD_GET_HOT_COUNTRY, map, HotBean.class, this);


    }

    private CommomRecyclerAdapter initView(RecyclerView lvList, int mLayId, int spanCount, final List city_list) {

//        hsvScroll.setCurrentScrollableContainer(this);

        MyGridLayoutManager gridLayoutManager = new MyGridLayoutManager(this, spanCount);
        //设置布局管理器
        lvList.setLayoutManager(gridLayoutManager);
        CommomRecyclerAdapter adapter = new CommomRecyclerAdapter(WorldCountryActivityOnline.this, city_list, mLayId, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
                WorldCountryBean.DataBean.CountryStateBean bean = (WorldCountryBean.DataBean.CountryStateBean) city_list.get(postion);
                Intent intent = new Intent(mContext, CountryActivity.class);
                intent.putExtra("countryId", bean.getCountryid());
                intent.putExtra("countryName", bean.getCountryname());
                intent.putExtra("countryNameEn", bean.getCountryEnName());

                startActivity(intent);
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {

                ImageView iv_item_icon = holder.getView(R.id.iv_item_icon);
                TextView tv_item = holder.getView(R.id.tv_item);
                WorldCountryBean.DataBean.CountryStateBean bean = (WorldCountryBean.DataBean.CountryStateBean) o;
                Glide.with(mContext)//图片@后面的截取操作拿到bean里面操作
                        .load(OffLineUtil.getInstance().getCountryImage(bean.getImageCountryUrl(164, 150))).placeholder(R.mipmap.no_tongyong3)
//                        .load(bean.getImageCountryUrl(164, 150)).placeholder(R.mipmap.no_tongyong3)
                        .into(iv_item_icon);
                tv_item.setText(bean.getCountryname());
            }
        };
        //设置Adapter
        lvList.setAdapter(adapter);

        return adapter;
    }

    private CommomRecyclerAdapter initHotView(RecyclerView lvList, int mLayId, int spanCount, final List city_list) {

//        hsvScroll.setCurrentScrollableContainer(this);

        MyGridLayoutManager gridLayoutManager = new MyGridLayoutManager(this, spanCount);
        //设置布局管理器
        lvList.setLayoutManager(gridLayoutManager);
        CommomRecyclerAdapter adapter = new CommomRecyclerAdapter(WorldCountryActivityOnline.this, city_list, mLayId, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
                HotBean.DataBean bean = (HotBean.DataBean) city_list.get(postion);
                Intent intent = new Intent(mContext, CountryActivity.class);
                intent.putExtra("countryId", bean.getCountryId());
                intent.putExtra("countryName", bean.getCountryName());
                intent.putExtra("countryNameEn", bean.getCountryEnName());

                startActivity(intent);
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {

                ImageView iv_item_icon = holder.getView(R.id.iv_item_icon);
                TextView tv_item = holder.getView(R.id.tv_item);
                HotBean.DataBean bean = (HotBean.DataBean) o;
                Glide.with(mContext)
                        .load(bean.getImageCountryUrl(240, 150)).placeholder(R.mipmap.no_tongyong3)
                        .into(iv_item_icon);
//                if ((Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry()).contains("en-") || (Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry()).equals("US")) {
//                    tv_item.setText(bean.getCountryEnName());
//                } else {
                tv_item.setText(bean.getCountryName());
//                }
            }
        };
        //设置Adapter
        lvList.setAdapter(adapter);

        return adapter;
    }

    @OnClick({R.id.tv_title, R.id.ll_hot, R.id.ll_asia, R.id.ll_europe, R.id.ll_africa, R.id.ll_NorthAmerica, R.id.ll_SouthAmerica, R.id.ll_Oceania})
//    @OnClick({R.id.tv_search, R.id.ll_hot, R.id.ll_asia, R.id.ll_europe, R.id.ll_africa, R.id.ll_NorthAmerica, R.id.ll_SouthAmerica, R.id.ll_Oceania})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_title:
                startActivity(new Intent(this, SearchPageActivity.class));
                break;
            case R.id.ll_hot:
//                for (int i = 0; i < 2; i++) {
////                    hot_city_list.add(new CityItemBean("泰国", R.mipmap.city_img));
////                    initView(rvHotCitys, R.layout.hot_city, 2, hot_city_list);
//                    llHot.setVisibility(View.GONE);
//                }
                break;
            case R.id.ll_asia:
                if (worldCountryBean != null) {
                    for (int i = 6; i < worldCountryBean.getData().get(5).getCountryState().size(); i++) {
                        asia_city_list.add(worldCountryBean.getData().get(5).getCountryState().get(i));
                    }
                    asiaAdapter.updateData(asia_city_list);
                    llAsia.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_europe:
                if (worldCountryBean != null) {
                    for (int i = 6; i < worldCountryBean.getData().get(1).getCountryState().size(); i++) {
                        europe_city_list.add(worldCountryBean.getData().get(1).getCountryState().get(i));
                    }
                    europeAdapter.updateData(europe_city_list);
                    llEurope.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_africa:
                if (worldCountryBean != null) {
                    for (int i = 6; i < worldCountryBean.getData().get(0).getCountryState().size(); i++) {
                        africa_city_list.add(worldCountryBean.getData().get(0).getCountryState().get(i));
                    }
                    africaAdapter.updateData(africa_city_list);
                    llAfrica.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_NorthAmerica:
                if (worldCountryBean != null) {
                    for (int i = 6; i < worldCountryBean.getData().get(4).getCountryState().size(); i++) {
                        northAmerica_city_list.add(worldCountryBean.getData().get(4).getCountryState().get(i));
                    }
                    northAmericaAdapter.updateData(northAmerica_city_list);
                    llNorthAmerica.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_SouthAmerica:
                if (worldCountryBean != null) {
                    for (int i = 6; i < worldCountryBean.getData().get(3).getCountryState().size(); i++) {
                        southAmerica_city_list.add(worldCountryBean.getData().get(3).getCountryState().get(i));
                    }
                    southAmericaAdapter.updateData(southAmerica_city_list);
                    llSouthAmerica.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_Oceania:
                if (worldCountryBean != null) {
                    for (int i = 6; i < worldCountryBean.getData().get(2).getCountryState().size(); i++) {
                        oceania_city_list.add(worldCountryBean.getData().get(2).getCountryState().get(i));
                    }
                    oceaniaAdapter.updateData(oceania_city_list);
                    llOceania.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onSuccess(String method, BaseBean model) {
        switch (method) {
            case Constans.METHOD_GET_HOT_COUNTRY:
                hotCountryBean = (HotBean) model;
                for (int j = 0; j < (hotCountryBean.getData().size() > 6 ? 6 : hotCountryBean.getData().size()); j++) {
                    hot_city_list.add(hotCountryBean.getData().get(j));
                }
                llHot.setVisibility(View.GONE);
                hotAdapter.updateData(hot_city_list);
                break;
            case Constans.METHOD_GET_WORLD_COUNTRY:
                worldCountryBean = (WorldCountryBean) model;//4 1 5 2 6 3
                for (int i = 0; i < worldCountryBean.getData().size(); i++) {
                    WorldCountryBean.DataBean bean = worldCountryBean.getData().get(i);
                    switch (bean.getStateId()) {
                        case "1":
                            for (int j = 0; j < (bean.getCountryState().size() > 6 ? 6 : bean.getCountryState().size()); j++) {
                                asia_city_list.add(bean.getCountryState().get(j));
                            }
                            asiaAdapter.updateData(asia_city_list);
                            if (bean.getCountryState().size() < 7) llAsia.setVisibility(View.GONE);
//                                asia_city_list.addAll(worldCountryBean.getData().get(i).getCountryState());
//                                asiaAdapter.updateData(asia_city_list);
//                            }
                            break;
                        case "2":
                            for (int j = 0; j < (bean.getCountryState().size() > 6 ? 6 : bean.getCountryState().size()); j++) {
                                northAmerica_city_list.add(bean.getCountryState().get(j));
                            }
                            northAmericaAdapter.updateData(northAmerica_city_list);
                            if (bean.getCountryState().size() < 7)
                                llNorthAmerica.setVisibility(View.GONE);
//                                northAmerica_city_list.addAll(worldCountryBean.getData().get(i).getCountryState());
//                                northAmericaAdapter.updateData(northAmerica_city_list);
//                            }
                            break;
                        case "3":
                            for (int j = 0; j < (bean.getCountryState().size() > 6 ? 6 : bean.getCountryState().size()); j++) {
                                southAmerica_city_list.add(bean.getCountryState().get(j));
                            }
                            southAmericaAdapter.updateData(southAmerica_city_list);
                            if (bean.getCountryState().size() < 7)
                                llSouthAmerica.setVisibility(View.GONE);
//                                southAmerica_city_list.addAll(worldCountryBean.getData().get(i).getCountryState());
//                                southAmericaAdapter.updateData(southAmerica_city_list);
//                            }
                            break;
                        case "4":
                            for (int j = 0; j < (bean.getCountryState().size() > 6 ? 6 : bean.getCountryState().size()); j++) {
                                oceania_city_list.add(bean.getCountryState().get(j));

                            }
                            oceaniaAdapter.updateData(oceania_city_list);
                            if (bean.getCountryState().size() < 7)
                                llOceania.setVisibility(View.GONE);
//                                oceania_city_list.addAll(worldCountryBean.getData().get(i).getCountryState());
//                                oceaniaAdapter.updateData(oceania_city_list);
//                            }
                            break;
                        case "5":
                            for (int j = 0; j < (bean.getCountryState().size() > 6 ? 6 : bean.getCountryState().size()); j++) {
                                europe_city_list.add(bean.getCountryState().get(j));
                            }
                            europeAdapter.updateData(europe_city_list);
                            if (bean.getCountryState().size() < 7)
                                llEurope.setVisibility(View.GONE);
//                                europe_city_list.addAll(worldCountryBean.getData().get(i).getCountryState());
//                                europeAdapter.updateData(europe_city_list);
//                            }
                            break;
                        case "6":
                            for (int j = 0; j < (bean.getCountryState().size() > 6 ? 6 : bean.getCountryState().size()); j++) {
                                africa_city_list.add(bean.getCountryState().get(j));
                            }
                            africaAdapter.updateData(africa_city_list);
                            if (bean.getCountryState().size() < 7)
                                llAfrica.setVisibility(View.GONE);
//                                africa_city_list.addAll(worldCountryBean.getData().get(i).getCountryState());
//                                africaAdapter.updateData(africa_city_list);
//                            }
                            break;
                    }
                }
        }

    }

    @Override
    public void onError(String method, String message) {

    }

}
