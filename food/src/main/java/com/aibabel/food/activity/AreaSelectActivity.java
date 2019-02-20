package com.aibabel.food.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.food.R;
import com.aibabel.food.base.Constant;
import com.aibabel.food.custom.sticklist.CityListBean;
import com.aibabel.food.custom.sticklist.MySectionIndexer;
import com.aibabel.food.custom.sticklist.PinnedHeaderListView;
import com.aibabel.food.custom.sticklist.PinyinComparator;
import com.aibabel.food.custom.sticklist.SideBar;
import com.aibabel.food.custom.sticklist.SortAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AreaSelectActivity extends BaseActivity implements BaseCallback<CityListBean> {

    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.lvCity)
    PinnedHeaderListView lvCity;
    @BindView(R.id.tv_dialog)
    TextView tvDialog;
    @BindView(R.id.sbSort)
    SideBar sbSort;

    private List<CityListBean.DataBean> SourceDateList = new ArrayList<>();

    private String[] sections;
    private static String ALL_CHARACTER = "";
    private int[] counts;
    private MySectionIndexer mIndexer;

    private SortAdapter adapter;

    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_area_select;
    }

    @Override
    public void init() {
        if (getIntent().getStringExtra("from") != null && getIntent().getStringExtra("from").equals("main")) {
            ivClose.setVisibility(View.GONE);
//            setPhysicalButtonsExit();
        } else {
            ivClose.setVisibility(View.VISIBLE);
        }
        initData();
        initEvents();
    }


    @OnClick(R.id.ivClose)
    public void onViewClicked() {
        finish();
    }

    /**
     * 初始化MainBean的数据列表
     */
    public void initData() {
        Map<String, String> map = new HashMap<>();
        OkGoUtil.<CityListBean>get(this, Constant.METHOD_AREA_CHANGE, map, CityListBean.class, this);
        sbSort.setTextView(tvDialog);
    }

    private void setAdapter() {
        Collections.sort(SourceDateList, new PinyinComparator());
        getIndexerAndCounts(SourceDateList);
        //設置頂部固定頭部
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_group_item, lvCity, false);

        lvCity.setPinnedHeaderView(view);
    }

    public void getIndexerAndCounts(List<CityListBean.DataBean> list) {
        ALL_CHARACTER = "";
        List<String> sectionlist = new ArrayList<>();
        for (CityListBean.DataBean city : list) {    //计算全部城市
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
        for (CityListBean.DataBean city : list) {    //计算全部城市
            String firstCharacter = city.getGroupBy();
            int index = ALL_CHARACTER.indexOf(firstCharacter);
            counts[index]++;
        }
        mIndexer = new MySectionIndexer(sections, counts);
        adapter = new SortAdapter(this, list, mIndexer);
        lvCity.setAdapter(adapter);
        lvCity.setOnScrollListener(adapter);
    }

    private void initEvents() {
        //设置右侧触摸监听
        sbSort.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                if (mIndexer != null) {
                    //该字母首次出现的位置
                    int position = mIndexer.getPositionForSection(ALL_CHARACTER.indexOf(s));
//                int position = adapter.getPositionForSection(s.charAt(0));
                    if (position != -1) {
                        lvCity.setSelection(position);
                    }
                }
            }
        });
        //ListView的点击事件
        lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Constant.CURRENT_CITY = adapter.getData().get(position).getName_cn();
                Intent intent = new Intent();
                intent.putExtra("cityId", adapter.getData().get(position).getCity_id());
                intent.putExtra("cityName", adapter.getData().get(position).getName_cn());
                setResult(Constant.RESULT_CODE_AREA_SELECT, intent);
                finish();
            }
        });
    }

    @Override
    public void onSuccess(String s, CityListBean cityListBean, String s1) {
        switch (s) {
            case Constant.METHOD_AREA_CHANGE:
                SourceDateList = cityListBean.getData();
                setAdapter();
                break;
        }
    }

    @Override
    public void onError(String s, String s1, String s2) {

    }

    @Override
    public void onFinsh(String s) {

    }
}
