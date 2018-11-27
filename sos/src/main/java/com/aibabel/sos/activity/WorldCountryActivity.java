package com.aibabel.sos.activity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.sos.R;
import com.aibabel.sos.adapter.CommomRecyclerAdapter;
import com.aibabel.sos.adapter.CommonRecyclerViewHolder;
import com.aibabel.sos.app.BaseActivity;
import com.aibabel.sos.bean.SosBean;
import com.aibabel.sos.custom.MyGridLayoutManager;
import com.aibabel.sos.custom.MyRecyclerView;
import com.aibabel.sos.custom.MyScrollview;
import com.aibabel.sos.utils.CheckFlag;
import com.aibabel.sos.utils.SosDbUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorldCountryActivity extends BaseActivity {

    @BindView(R.id.tv_recently_viewed)
    TextView tvRecentlyViewed;
    @BindView(R.id.tv_city1)
    TextView tvCity1;
    @BindView(R.id.tv_city2)
    TextView tvCity2;
    @BindView(R.id.tv_city3)
    TextView tvCity3;
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
    //    @BindView(R.id.tv_sousuo)
//    TextView tvSousuo;
//    @BindView(R.id.iv_fanhui)
//    ImageView ivFanhui;
    @BindView(R.id.tv_Asia)
    TextView tvAsia;
    @BindView(R.id.iv_left1)
    ImageView ivLeft1;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private CommomRecyclerAdapter asiaAdapter;
    private CommomRecyclerAdapter europeAdapter;
    private CommomRecyclerAdapter africaAdapter;
    private CommomRecyclerAdapter northAmericaAdapter;
    private CommomRecyclerAdapter southAmericaAdapter;
    private CommomRecyclerAdapter oceaniaAdapter;
    private ArrayList<SosBean> asia_city_list = new ArrayList();
    private ArrayList<SosBean> europe_city_list = new ArrayList();
    private ArrayList<SosBean> africa_city_list = new ArrayList();
    private ArrayList<SosBean> northAmerica_city_list = new ArrayList();
    private ArrayList<SosBean> southAmerica_city_list = new ArrayList();
    private ArrayList<SosBean> oceania_city_list = new ArrayList();

    private SQLiteDatabase db;
    private ArrayList<SosBean> list1;
    private ArrayList<SosBean> list2;
    private ArrayList<SosBean> list3;
    private ArrayList<SosBean> list4;
    private ArrayList<SosBean> list5;
    private ArrayList<SosBean> list6;
    private int onclick = 0;

    @Override
    public int initLayout() {
        return R.layout.activity_world__city;
    }

    @Override
    public void init() {
        mContext = WorldCountryActivity.this;
//        initSearch();
        initTitle();
//        ivFanhui.setVisibility(getIntent().getIntExtra("fh", View.VISIBLE));

        initData();
        initRecycleView();
        hsvScroll.setFocusableInTouchMode(true);
        hsvScroll.requestFocus();
//        svSearch.setIconifiedByDefault(false);
//        svSearch.setFocusable(false);

        tvAsia.setOnClickListener(new View.OnClickListener() {

            private Long time1;

            @Override
            public void onClick(View v) {
                onclick++;
                if (onclick == 1) {
                    time1 = System.currentTimeMillis();
                }
                if (System.currentTimeMillis() - time1 > 10000) {
                    onclick = 0;
                } else {
                    if (onclick >= 10) {
                        Intent intent = new Intent();
                        ComponentName cmpName = new ComponentName("com.example.android.home", "com.example.android.home.Home");
                        if (cmpName != null) {
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setComponent(cmpName);
                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException ex) {

                            }
                        }
                    }
                }


            }
        });

    }

    public void initTitle() {
        ivLeft1.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);

        ivLeft1.setVisibility(getIntent().getIntExtra("fh", View.VISIBLE));

        ivLeft1.setImageResource(R.mipmap.fanhui_bai_yuan);
        tvTitle.setHint(getResources().getString(R.string.morenxianshi));
        tvTitle.setHintTextColor(getResources().getColor(R.color.gray66));
    }

    public void initRecycleView() {
        asiaAdapter = initView(rvAsiaCitys, R.layout.continent_city, 3, list1);
        africaAdapter = initView(rvAfricaCitys, R.layout.continent_city, 3, list2);
        europeAdapter = initView(rvEuropeCitys, R.layout.continent_city, 3, list3);
        northAmericaAdapter = initView(rvNorthAmericaCitys, R.layout.continent_city, 3, list4);
        southAmericaAdapter = initView(rvSouthAmericaCitys, R.layout.continent_city, 3, list5);
        oceaniaAdapter = initView(rvOceaniaCitys, R.layout.continent_city, 3, list6);
    }

//    private void initSearch() {
//        //更改文字颜色，光标样色
//        try {
//            Class cls = Class.forName("android.support.v7.widget.SearchView");
//            Field field = cls.getDeclaredField("mSearchSrcTextView");
//            field.setAccessible(true);
//            TextView tv = (TextView) field.get(svSearch);
//
//            Class[] clses = cls.getDeclaredClasses();
//            for (Class cls_ : clses) {
//                Log.e("TAG", cls_.toString());
//                if (cls_.toString().endsWith("android.support.v7.widget.SearchView$SearchAutoComplete")) {
//                    Class targetCls = cls_.getSuperclass().getSuperclass().getSuperclass().getSuperclass();
//                    Field cuosorIconField = targetCls.getDeclaredField("mCursorDrawableRes");
//                    cuosorIconField.setAccessible(true);
//                    cuosorIconField.set(tv, R.drawable.v_line_00);
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("TAG", "ERROR setCursorIcon = " + e.toString());
//        }
//
//    }


    private void initData() {

        asia_city_list = (ArrayList<SosBean>) SosDbUtil.getDazhou(getResources().getString(R.string.yazhou));
        if (asia_city_list.size() < 7) llAsia.setVisibility(View.GONE);
        list1 = new ArrayList<>();
        for (int i = 0; i < (asia_city_list.size() > 6 ? 6 : asia_city_list.size()); i++) {
            list1.add(asia_city_list.get(i));
        }

        africa_city_list = (ArrayList<SosBean>) SosDbUtil.getDazhou(getResources().getString(R.string.feizhou));
        if (africa_city_list.size() < 7) llAfrica.setVisibility(View.GONE);
        list2 = new ArrayList<>();
        for (int i = 0; i < (africa_city_list.size() > 6 ? 6 : africa_city_list.size()); i++) {
            list2.add(africa_city_list.get(i));
        }

        europe_city_list = (ArrayList<SosBean>) SosDbUtil.getDazhou(getResources().getString(R.string.ouzhou));
        if (europe_city_list.size() < 7) llEurope.setVisibility(View.GONE);
        list3 = new ArrayList<>();
        for (int i = 0; i < (europe_city_list.size() > 6 ? 6 : europe_city_list.size()); i++) {
            list3.add(europe_city_list.get(i));
        }

        northAmerica_city_list = (ArrayList<SosBean>) SosDbUtil.getDazhou(getResources().getString(R.string.beimeizhou));
        if (northAmerica_city_list.size() < 7) llNorthAmerica.setVisibility(View.GONE);
        list4 = new ArrayList<>();
        for (int i = 0; i < (northAmerica_city_list.size() > 6 ? 6 : northAmerica_city_list.size()); i++) {
            list4.add(northAmerica_city_list.get(i));
        }

        southAmerica_city_list = (ArrayList<SosBean>) SosDbUtil.getDazhou(getResources().getString(R.string.nanmeizhou));
        if (southAmerica_city_list.size() < 7) llSouthAmerica.setVisibility(View.GONE);
        list5 = new ArrayList<>();
        for (int i = 0; i < (southAmerica_city_list.size() > 6 ? 6 : southAmerica_city_list.size()); i++) {
            list5.add(southAmerica_city_list.get(i));
        }

        oceania_city_list = (ArrayList<SosBean>) SosDbUtil.getDazhou(getResources().getString(R.string.dayangzhou));
        if (oceania_city_list.size() < 7) llOceania.setVisibility(View.GONE);
        list6 = new ArrayList<>();
        for (int i = 0; i < (oceania_city_list.size() > 6 ? 6 : oceania_city_list.size()); i++) {
            list6.add(oceania_city_list.get(i));
        }
    }

    private CommomRecyclerAdapter initView(RecyclerView lvList, int mLayId, int spanCount, final List city_list) {

//        hsvScroll.setCurrentScrollableContainer(this);

        MyGridLayoutManager gridLayoutManager = new MyGridLayoutManager(this, spanCount);
        //设置布局管理器
        lvList.setLayoutManager(gridLayoutManager);
        CommomRecyclerAdapter adapter = new CommomRecyclerAdapter(WorldCountryActivity.this, city_list, mLayId, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
                SosBean bean = (SosBean) city_list.get(postion);
                Intent intent = new Intent(mContext, InformationItemActivity.class);
                intent.putExtra("gj", bean.getGj());

                startActivity(intent);
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {
                SosBean bean = (SosBean) o;
//                ImageView iv_item_icon = holder.getView(R.id.iv_item_icon);
                TextView tv_item = holder.getView(R.id.tv_item);
                ImageView iv_item_icon = holder.getView(R.id.iv_item_icon);
//                WorldCountryBean.DataBean.CountryStateBean bean = (WorldCountryBean.DataBean.CountryStateBean) o;
//                Glide.with(mContext)
//                        .load(bean.getImageCountryUrl()).placeholder(R.mipmap.ic_launcher)
//                        .into(iv_item_icon);
                tv_item.setText(bean.getGj());
                iv_item_icon.setImageResource(CheckFlag.getFlag(bean.getGq()));
            }
        };
        //设置Adapter
        lvList.setAdapter(adapter);

        return adapter;
    }

    @OnClick({R.id.iv_left1, R.id.tv_title, R.id.ll_hot, R.id.ll_asia, R.id.ll_europe, R.id.ll_africa, R.id.ll_NorthAmerica, R.id.ll_SouthAmerica, R.id.ll_Oceania})
//    @OnClick({R.id.iv_fanhui, R.id.tv_sousuo, R.id.ll_hot, R.id.ll_asia, R.id.ll_europe, R.id.ll_africa, R.id.ll_NorthAmerica, R.id.ll_SouthAmerica, R.id.ll_Oceania})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left1:
                finish();
                break;
            case R.id.tv_title:
                Intent intent = new Intent(WorldCountryActivity.this, SousuoActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_hot:
                break;
            case R.id.ll_asia:
                for (int i = 6; i < asia_city_list.size(); i++) {
                    list1.add(asia_city_list.get(i));
                }
                asiaAdapter.updateData(list1);
                llAsia.setVisibility(View.GONE);
                break;
            case R.id.ll_europe:
                for (int i = 6; i < europe_city_list.size(); i++) {
                    list3.add(europe_city_list.get(i));
                }
                europeAdapter.updateData(list3);
                llEurope.setVisibility(View.GONE);
                break;
            case R.id.ll_africa:
                for (int i = 6; i < africa_city_list.size(); i++) {
                    list2.add(africa_city_list.get(i));
                }
                africaAdapter.updateData(list2);
                llAfrica.setVisibility(View.GONE);
                break;
            case R.id.ll_NorthAmerica:
                for (int i = 6; i < northAmerica_city_list.size(); i++) {
                    list4.add(northAmerica_city_list.get(i));
                }
                northAmericaAdapter.updateData(list4);
                llNorthAmerica.setVisibility(View.GONE);
                break;
            case R.id.ll_SouthAmerica:
                for (int i = 6; i < southAmerica_city_list.size(); i++) {
                    list5.add(southAmerica_city_list.get(i));
                }
                southAmericaAdapter.updateData(list5);
                llSouthAmerica.setVisibility(View.GONE);
                break;
            case R.id.ll_Oceania:
                for (int i = 6; i < oceania_city_list.size(); i++) {
                    list6.add(oceania_city_list.get(i));
                }
                oceaniaAdapter.updateData(list6);
                llOceania.setVisibility(View.GONE);
                break;
        }
    }

}
