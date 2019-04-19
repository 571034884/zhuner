package com.aibabel.launcher.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.launcher.R;
import com.aibabel.launcher.base.LaunBaseActivity;
import com.aibabel.launcher.mode.CityListBean;
import com.aibabel.launcher.net.Api;
import com.aibabel.launcher.utils.DBUtils;
import com.aibabel.launcher.utils.Logs;
import com.aibabel.launcher.utils.NetWorkUtil;
import com.aibabel.launcher.utils.SPUtils;
import com.aibabel.launcher.view.sousuo.MySectionIndexer;
import com.aibabel.launcher.view.sousuo.PinnedHeaderListView;
import com.aibabel.launcher.view.sousuo.PinyinComparator;
import com.aibabel.launcher.view.sousuo.SideBar;
import com.aibabel.launcher.view.sousuo.SortAdapter;
import com.aibabel.message.utiles.L;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class SearchActivity extends LaunBaseActivity implements View.OnClickListener {

    @BindView(R.id.search_guanbi)
    ImageView searchGuanbi;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.search_autonext)
    TagFlowLayout searchAutonext;
    @BindView(R.id.search_remen_ll)
    LinearLayout searchRemenLl;
    @BindView(R.id.search_city)
    PinnedHeaderListView searchCity;
    @BindView(R.id.tv_dialog)
    TextView tvDialog;
    @BindView(R.id.search_sort)
    SideBar searchSort;
    @BindView(R.id.search_curr_location_tv)
    TextView searchCurrLocationTv;
    @BindView(R.id.search_curr_location_ll)
    LinearLayout searchCurrLocationLl;

    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_search;
    }

    @Override
    public void init() {
    }
    String showCity = "";

    protected void initView() {

        setPathParams("进入目的地列表");
        addStatisticsEvent("menu_search_open",null);


        //判断是否显示  当前定位
        if (!NetWorkUtil.isAvailable(mContext)) {
            searchCurrLocationLl.setVisibility(View.GONE);
        } else {
            showCity= SPUtils.get(mContext,"showCityName","").toString();
            if (showCity != null && !TextUtils.isEmpty(showCity)) {
                searchCurrLocationLl.setVisibility(View.VISIBLE);
                searchCurrLocationTv.setText(showCity);
            } else {
                searchCurrLocationLl.setVisibility(View.GONE);
            }

        }

        initData();
    }


    public void initData() {
        searchGuanbi.setOnClickListener(this);

        //ListView的点击事件
        searchCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                finish();
            }
        });

        searchCurrLocationLl.setOnClickListener(this);

        //获取尘世列表
        bindCityList();

        initEvents();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.search_guanbi:
                addStatisticsEvent("menu_search_close",null);
                closeAct();
                break;
            case R.id.search_curr_location_ll:
                HashMap<String, Serializable> map = new HashMap<>();
                map.put("menu_search_location_name",showCity+"");
                addStatisticsEvent("menu_search_location",map);
                Intent intent1 = new Intent();
                intent1.putExtra("cmd","search");
                intent1.setAction("com.aibabel.menu.MENULOCATION");
                sendBroadcast(intent1);
                finish();
                break;

        }

    }
    CityListBean cityListBeans;

    /**
     * 请求城市列表
     */
    public void bindCityList() {

        if (NetWorkUtil.isAvailable(mContext)) {

            Map<String, String> mapPram = new HashMap<>();
            OkGoUtil.get(mContext, Api.GET_CITYLIST, mapPram, CityListBean.class, new BaseCallback<CityListBean>() {
                @Override
                public void onSuccess(String s, CityListBean cityListBean, String s1) {
                    Logs.e("bindMenuData  onsuccess================" + cityListBean.toString());
                    cityListBeans = cityListBean;
                    Collections.sort(cityListBean.getData().getPopularAddr(), new PinyinComparator());
                    View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_group_item, searchCity, false);

                    searchCity.setPinnedHeaderView(view);
                    getIndexerAndCounts(cityListBean.getData().getPopularAddr());
                    bindReMen(cityListBean.getData().getHotAddr());

                }

                @Override
                public void onError(String s, String s1, String s2) {
                    Logs.e("bindMenuData  onError ================" + s);

                }

                @Override
                public void onFinsh(String s) {

                }

            });
        } else {
            try {
                SQLiteDatabase db = DBUtils.openDatabase("/sdcard/offline/menu/menu_mdd.db");
                if (db == null) return;
                Cursor cursor = db.query("city_list", null, null, null, null, null, null);
                List<CityListBean.DataBean.PopularAddrBean> popList = new ArrayList<>();
                List<CityListBean.DataBean.HotAddrBean> hotList = new ArrayList<>();

                try {

                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            CityListBean.DataBean.PopularAddrBean pop = new CityListBean.DataBean.PopularAddrBean();
                            pop.setCityChj(cursor.getString(cursor.getColumnIndex("city_name_chj")));
                            pop.setCityId(cursor.getString(cursor.getColumnIndex("city_id")));
                            pop.setCountryId(cursor.getString(cursor.getColumnIndex("country_id")));
                            pop.setCountryChj(cursor.getString(cursor.getColumnIndex("country_name_chj")));
                            pop.setGroupBy(cursor.getString(cursor.getColumnIndex("groupBy")));

                            if (cursor.getInt(cursor.getColumnIndex("isHot")) == 1) {
                                CityListBean.DataBean.HotAddrBean hot = new CityListBean.DataBean.HotAddrBean();
                                hot.setCityChj(cursor.getString(cursor.getColumnIndex("city_name_chj")));
                                hot.setCountryChj(cursor.getString(cursor.getColumnIndex("country_name_chj")));
                                hot.setCityId(cursor.getString(cursor.getColumnIndex("city_id")));
                                hot.setCountryId(cursor.getString(cursor.getColumnIndex("country_id")));
                                hot.setGroupBy(cursor.getString(cursor.getColumnIndex("groupBy")));
                                hotList.add(hot);
                            }

                            popList.add(pop);
                        } while (cursor.moveToNext());


                    }


                    Collections.sort(popList, new PinyinComparator());
                    View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_group_item, searchCity, false);

                    searchCity.setPinnedHeaderView(view);
                    getIndexerAndCounts(popList);
                    bindReMen(hotList);
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }

                }
            }catch (Exception e){

            }

        }
    }


    private String[] sections;
    private static String ALL_CHARACTER = "";
    private int[] counts;
    private MySectionIndexer mIndexer;
    private SortAdapter adapter;

    /**
     * 数据排序列表
     *
     * @param list
     */
    public void getIndexerAndCounts(final List<CityListBean.DataBean.PopularAddrBean> list) {
        ALL_CHARACTER = "";
        List<String> sectionlist = new ArrayList<>();
        for (CityListBean.DataBean.PopularAddrBean city : list) {    //计算全部城市
            String firstCharacter = city.getGroupBy();
            if (!sectionlist.contains(firstCharacter.toUpperCase())) {
                sectionlist.add(firstCharacter.toUpperCase());
            }
        }
        sections = sectionlist.toArray(new String[sectionlist.size()]);
        //初始化每个字母有多少个item
        counts = new int[sections.length];
        for (String s : sectionlist) {
            ALL_CHARACTER = ALL_CHARACTER + s;
        }
        for (CityListBean.DataBean.PopularAddrBean city : list) {    //计算全部城市
            String firstCharacter = city.getGroupBy();
            int index = ALL_CHARACTER.indexOf(firstCharacter);
            counts[index]++;
        }
        mIndexer = new MySectionIndexer(sections, counts);
        adapter = new SortAdapter(this, list, mIndexer);
        searchCity.setAdapter(adapter);
        searchCity.setOnScrollListener(adapter);
        searchCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try{
                    HashMap<String, Serializable> map = new HashMap<>();
                    map.put("popular_list_click_id",list.get(i).getCityChj()+"");
                    addStatisticsEvent("popular_list_c",map);
                }catch (Exception e){}
                Intent intent = new Intent();
                intent.putExtra("city_id", list.get(i).getCityId());
                intent.putExtra("country_id", list.get(i).getCountryId());
                intent.putExtra("city_name", list.get(i).getCityChj());
                intent.putExtra("country_name", list.get(i).getCountryChj());
                setResult(200, intent);
                finish();
            }
        });
    }

    //设置右侧触摸监听
    private void initEvents() {
        searchSort.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                if (mIndexer != null) {
                    try{
                        HashMap<String, Serializable> map = new HashMap<>();
                        map.put("popular_list_click_id",s+"");
                        addStatisticsEvent("index_slide",map);
                    }catch (Exception e){}


                    //该字母首次出现的位置
                    int position = mIndexer.getPositionForSection(ALL_CHARACTER.indexOf(s));
//                int position = adapter.getPositionForSection(s.charAt(0));
                    if (position != -1) {
                        searchCity.setSelection(position);
                    }
                }
            }
        });
        searchSort.setTextView(tvDialog);

    }

    /**
     * 绑定热门标签
     *
     * @param list
     */
    public void bindReMen(final List<CityListBean.DataBean.HotAddrBean> list) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_remen, null);
        final LayoutInflater mInflater = LayoutInflater.from(this);

        searchAutonext.setAdapter(new TagAdapter<CityListBean.DataBean.HotAddrBean>(list) {

            @Override
            public View getView(FlowLayout parent, int position, CityListBean.DataBean.HotAddrBean bean) {
//                将tv.xml文件填充到标签内.
                TextView tv = (TextView) mInflater.inflate(R.layout.item_remen,
                        searchAutonext, false);
//               为标签设置对应的内容
                tv.setText(bean.getCityChj());
                return tv;
            }

            //             为标签设置预点击内容(就是一开始就处于点击状态的标签)
            @Override
            public boolean setSelected(int position, CityListBean.DataBean.HotAddrBean bean) {
                return bean.getCityChj().equals("Android");
            }
        });
//          为点击标签设置点击事件.
        searchAutonext.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Logs.e("onTagClick========" + (String) view.getTag());

                try{
                    HashMap<String, Serializable> map = new HashMap<>();
                    map.put("popular_click_id",list.get(position).getCityChj()+"");
                    addStatisticsEvent("popular_click",map);
                }catch (Exception e){}


                Intent intent = new Intent();
//                intent.putExtra("url",list.get(position).getMenuAddrId());
                intent.putExtra("city_id", list.get(position).getCityId());
                intent.putExtra("country_id", list.get(position).getCountryId());
                intent.putExtra("city_name", list.get(position).getCityChj());
                intent.putExtra("country_name", list.get(position).getCountryChj());
                setResult(200, intent);
                finish();
                return true;
            }
        });

    }
}
