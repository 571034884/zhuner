package com.aibabel.traveladvisory.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.traveladvisory.R;
import com.aibabel.traveladvisory.adapter.CommomRecyclerAdapter;
import com.aibabel.traveladvisory.adapter.CommonRecyclerViewHolder;
import com.aibabel.traveladvisory.app.BaseActivity;
import com.aibabel.traveladvisory.bean.HotBean;
import com.aibabel.traveladvisory.bean.WorldCountryBean;
import com.aibabel.traveladvisory.custom.MyRecyclerView;
import com.aibabel.traveladvisory.utils.CommonUtils;
import com.aibabel.traveladvisory.utils.FastJsonUtil;
import com.aibabel.traveladvisory.utils.MyGridLayoutManager;
import com.aibabel.traveladvisory.utils.MyScrollview;
import com.aibabel.traveladvisory.utils.OffLineUtil;
import com.aibabel.traveladvisory.utils.SharePrefUtil;
import com.aibabel.traveladvisory.utils.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jiangyy.easydialog.OtherDialog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WorldCountryActivity extends BaseActivity {

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
    public int getLayout(Bundle savedInstanceState){
        return R.layout.activity_world__city;
    }

    @Override
    public void init() {
        mContext = WorldCountryActivity.this;
        initTitle();
        initRecycleView();
        initData();
        hsvScroll.setFocusableInTouchMode(true);
        hsvScroll.requestFocus();
    }

    public void initTitle() {
        tvTitle.setVisibility(View.VISIBLE);
        CommonUtils.setMargins(tvTitle, 20, 0, 20, 0);
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

//        new UpdateDialog.Builder(this)
////                .setIcon(R.mipmap.ic_launcher)
//                .setTitle("离线数据包")
//                .setMessage("下载目的地离线包，使用起来会更流畅哦~", R.color.c_fe5100)
//                .setPositiveButton("立即下载", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                }).setNegativeButton("以后再说", null)
//                .setCancelable(false).show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStreamReader inputStreamReaderHot = new InputStreamReader(getAssets().open("country_hot.json"), "UTF-8");
                    BufferedReader bufferedReaderHot = new BufferedReader(
                            inputStreamReaderHot);
                    String lineHot;
                    StringBuilder stringBuilderHot = new StringBuilder();
                    while ((lineHot = bufferedReaderHot.readLine()) != null) {
                        stringBuilderHot.append(lineHot);
                    }
                    inputStreamReaderHot.close();
                    bufferedReaderHot.close();
                    hotCountryBean = FastJsonUtil.changeJsonToBean(stringBuilderHot.toString(), HotBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int j = 0; j < (hotCountryBean.getData().size() > 6 ? 6 : hotCountryBean.getData().size()); j++) {
                                hot_city_list.add(hotCountryBean.getData().get(j));
                            }
                            llHot.setVisibility(View.GONE);
                            hotAdapter.updateData(hot_city_list);
                        }
                    });
                    InputStreamReader inputStreamReader = new InputStreamReader(getAssets().open("country_state.json"), "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(
                            inputStreamReader);
                    String line;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    inputStreamReader.close();
                    bufferedReader.close();
                    worldCountryBean = FastJsonUtil.changeJsonToBean(stringBuilder.toString(), WorldCountryBean.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < worldCountryBean.getData().size(); i++) {
                                WorldCountryBean.DataBean bean = worldCountryBean.getData().get(i);
                                Log.e("run: ", bean.toString());
                                switch (bean.getStateId()) {
                                    case "1":
                                        for (int j = 0; j < (bean.getCountryState().size() > 6 ? 6 : bean.getCountryState().size()); j++) {
                                            asia_city_list.add(bean.getCountryState().get(j));
                                        }
                                        asiaAdapter.updateData(asia_city_list);
                                        if (bean.getCountryState().size() < 7)
                                            llAsia.setVisibility(View.GONE);
                                        break;
                                    case "2":
                                        for (int j = 0; j < (bean.getCountryState().size() > 6 ? 6 : bean.getCountryState().size()); j++) {
                                            northAmerica_city_list.add(bean.getCountryState().get(j));
                                        }
                                        northAmericaAdapter.updateData(northAmerica_city_list);
                                        if (bean.getCountryState().size() < 7)
                                            llNorthAmerica.setVisibility(View.GONE);
                                        break;
                                    case "3":
                                        for (int j = 0; j < (bean.getCountryState().size() > 6 ? 6 : bean.getCountryState().size()); j++) {
                                            southAmerica_city_list.add(bean.getCountryState().get(j));
                                        }
                                        southAmericaAdapter.updateData(southAmerica_city_list);
                                        if (bean.getCountryState().size() < 7)
                                            llSouthAmerica.setVisibility(View.GONE);
                                        break;
                                    case "4":
                                        for (int j = 0; j < (bean.getCountryState().size() > 6 ? 6 : bean.getCountryState().size()); j++) {
                                            oceania_city_list.add(bean.getCountryState().get(j));

                                        }
                                        oceaniaAdapter.updateData(oceania_city_list);
                                        if (bean.getCountryState().size() < 7)
                                            llOceania.setVisibility(View.GONE);
                                        break;
                                    case "5":
                                        for (int j = 0; j < (bean.getCountryState().size() > 6 ? 6 : bean.getCountryState().size()); j++) {
                                            europe_city_list.add(bean.getCountryState().get(j));
                                        }
                                        europeAdapter.updateData(europe_city_list);
                                        if (bean.getCountryState().size() < 7)
                                            llEurope.setVisibility(View.GONE);
                                        break;
                                    case "6":
                                        for (int j = 0; j < (bean.getCountryState().size() > 6 ? 6 : bean.getCountryState().size()); j++) {
                                            africa_city_list.add(bean.getCountryState().get(j));
                                        }
                                        africaAdapter.updateData(africa_city_list);
                                        if (bean.getCountryState().size() < 7)
                                            llAfrica.setVisibility(View.GONE);
                                        break;
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.e("run: ", e.getMessage());
                }
            }
        }).start();

    }

    private CommomRecyclerAdapter initView(RecyclerView lvList, int mLayId, int spanCount, final List city_list) {

        MyGridLayoutManager gridLayoutManager = new MyGridLayoutManager(this, spanCount);
        //设置布局管理器
        lvList.setLayoutManager(gridLayoutManager);
        CommomRecyclerAdapter adapter = new CommomRecyclerAdapter(WorldCountryActivity.this, city_list, mLayId, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
                WorldCountryBean.DataBean.CountryStateBean bean = (WorldCountryBean.DataBean.CountryStateBean) city_list.get(postion);
                Intent intent = new Intent(mContext, CountryActivity.class);
                intent.putExtra("countryId", bean.getCountryid());
                intent.putExtra("countryName", bean.getCountryname());
                intent.putExtra("countryNameEn", bean.getCountryEnName());
                if (OffLineUtil.offlineStatusMap.containsKey(bean.getCountryname())) {
                    switch (OffLineUtil.offlineStatusMap.get(bean.getCountryname())) {
                        case OffLineUtil.STATE_1:
                            intent.putExtra("isOfflineSupport", true);
                            startActivity(intent);
                            break;
                        case OffLineUtil.STATE_2:
                        case OffLineUtil.STATE_3:
                            if (!OffLineUtil.offlineTipMap.containsKey(bean.getCountryname())) {
                                showDialog(bean.getCountryname(), OffLineUtil.offlineStatusMap.get(bean.getCountryname()));
                            } else {
                                if (CommonUtils.isNetAvailable(WorldCountryActivity.this))
                                    startActivity(intent);
                                else
                                    ToastUtil.showShort(WorldCountryActivity.this, R.string.toast_wuwangluo);
                            }
                            break;
                    }
                } else {
                    if (CommonUtils.isNetAvailable(WorldCountryActivity.this))
                        startActivity(intent);
                    else ToastUtil.showShort(WorldCountryActivity.this, R.string.toast_wuwangluo);
                }
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {

                ImageView iv_item_icon = holder.getView(R.id.iv_item_icon);
                ImageView iv_offline = holder.getView(R.id.iv_offline);
                TextView tv_item = holder.getView(R.id.tv_item);
                WorldCountryBean.DataBean.CountryStateBean bean = (WorldCountryBean.DataBean.CountryStateBean) o;
                if (OffLineUtil.offlineStatusMap.containsKey(bean.getCountryname())) {
                    switch (OffLineUtil.offlineStatusMap.get(bean.getCountryname())) {
                        case OffLineUtil.STATE_1:
                        case OffLineUtil.STATE_2:
                            iv_offline.setVisibility(View.VISIBLE);
                            break;
                        case OffLineUtil.STATE_3:
                            iv_offline.setVisibility(View.GONE);
                            break;
                    }
                } else {
                    iv_offline.setVisibility(View.GONE);
                }
                RequestOptions options = new RequestOptions().placeholder(R.mipmap.no_tongyong3).error(R.mipmap.error_v);
                Glide.with(mContext)//图片@后面的截取操作拿到bean里面操作
                        .load(OffLineUtil.getInstance().getCountryImage(bean.getImageCountryUrl(164, 150))).apply(options)
                        //.placeholder(R.mipmap.no_tongyong3).error(R.mipmap.error_v)
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

        MyGridLayoutManager gridLayoutManager = new MyGridLayoutManager(this, spanCount);
        //设置布局管理器
        lvList.setLayoutManager(gridLayoutManager);
        CommomRecyclerAdapter adapter = new CommomRecyclerAdapter(WorldCountryActivity.this, city_list, mLayId, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
                HotBean.DataBean bean = (HotBean.DataBean) city_list.get(postion);
                Intent intent = new Intent(mContext, CountryActivity.class);
                intent.putExtra("countryId", bean.getCountryId());
                intent.putExtra("countryName", bean.getCountryName());
                intent.putExtra("countryNameEn", bean.getCountryEnName());
                if (OffLineUtil.offlineStatusMap.containsKey(bean.getCountryName())) {
                    switch (Integer.valueOf(OffLineUtil.offlineStatusMap.get(bean.getCountryName()))) {
                        case OffLineUtil.STATE_1:
                            intent.putExtra("isOfflineSupport", true);
                            startActivity(intent);
                            break;
                        case OffLineUtil.STATE_2:
                        case OffLineUtil.STATE_3:
                            if (!OffLineUtil.offlineTipMap.containsKey(bean.getCountryName())) {
                                showDialog(bean.getCountryName(), OffLineUtil.offlineStatusMap.get(bean.getCountryName()));
                            } else {
                                if (CommonUtils.isNetAvailable(WorldCountryActivity.this))
                                    startActivity(intent);
                                else
                                    ToastUtil.showShort(WorldCountryActivity.this, R.string.toast_wuwangluo);
                            }
                            break;
                    }
                } else {
                    if (CommonUtils.isNetAvailable(WorldCountryActivity.this))
                        startActivity(intent);
                    else ToastUtil.showShort(WorldCountryActivity.this, R.string.toast_wuwangluo);
                }
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {

                ImageView iv_item_icon = holder.getView(R.id.iv_item_icon);
                ImageView iv_offline = holder.getView(R.id.iv_offline);
                TextView tv_item = holder.getView(R.id.tv_item);
                HotBean.DataBean bean = (HotBean.DataBean) o;
                if (OffLineUtil.offlineStatusMap.containsKey(bean.getCountryName())) {
                    switch (OffLineUtil.offlineStatusMap.get(bean.getCountryName())) {
                        case OffLineUtil.STATE_1:
                        case OffLineUtil.STATE_2:
                            iv_offline.setVisibility(View.VISIBLE);
                            break;
                        case OffLineUtil.STATE_3:
                            iv_offline.setVisibility(View.GONE);
                            break;
                    }
                } else {
                    iv_offline.setVisibility(View.GONE);
                }

                RequestOptions options = new RequestOptions().placeholder(R.mipmap.no_tongyong3).error(R.mipmap.error_v);
                Glide.with(mContext)
                        .load(OffLineUtil.getInstance().getCountryImage(bean.getImageCountryUrl(240, 150))).apply(options)
//                        .placeholder(R.mipmap.no_tongyong3).error(R.mipmap.error_v)
//                        .load(bean.getImageCountryUrl(240, 150)).placeholder(R.mipmap.no_tongyong3)
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
                if (CommonUtils.isNetAvailable(this)) {
                    startActivity(new Intent(this, SearchPageActivity.class));
                } else {
                    ToastUtil.showShort(this, getResources().getString(R.string.toast_lixian));
                }
                break;
            case R.id.ll_hot:
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

    public void showDialog(String guojia, int status) {
        OffLineUtil.offlineTipMap.put(guojia, -status);
        SharePrefUtil.saveString(this, "offlineTip", OffLineUtil.transMapToString(OffLineUtil.offlineTipMap));
        new OtherDialog.Builder(WorldCountryActivity.this)
                .setGravity(Gravity.CENTER)
                .setContentView(R.layout.commondialog)
                .setText(R.id.tv_wenzi, status == OffLineUtil.STATE_2 ? getResources().getString(R.string.lixiantishi2) : getResources().getString(R.string.lixiantishi3))
                .setCancelable(false)
                .setOnClickListener(R.id.tv_xiazai, status == OffLineUtil.STATE_2 ? getResources().getString(R.string.install) : getResources().getString(R.string.download), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            String mPackageName = "com.aibabel.download.offline";
                            PackageManager packageManager = getPackageManager();
                            startActivity(packageManager.getLaunchIntentForPackage(mPackageName));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).setOnClickListener(R.id.tv_hulue, getResources().getString(R.string.ignore), null).show();
    }

}
