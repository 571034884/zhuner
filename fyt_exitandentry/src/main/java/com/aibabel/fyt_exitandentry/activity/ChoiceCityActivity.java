package com.aibabel.fyt_exitandentry.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.fyt_exitandentry.R;
import com.aibabel.fyt_exitandentry.adapter.CommomRecyclerAdapter;
import com.aibabel.fyt_exitandentry.adapter.CommonRecyclerViewHolder;
import com.aibabel.fyt_exitandentry.adapter.SortAdapter;
import com.aibabel.fyt_exitandentry.bean.CityAirplaneBean;
import com.aibabel.fyt_exitandentry.bean.CityListBean;
import com.aibabel.fyt_exitandentry.bean.Constans;
import com.aibabel.fyt_exitandentry.bean.HotCityBean;
import com.aibabel.fyt_exitandentry.utils.MySectionIndexer;
import com.aibabel.fyt_exitandentry.utils.PinnedHeaderListView;
import com.aibabel.fyt_exitandentry.utils.PinyinComparator;
import com.aibabel.fyt_exitandentry.utils.PinyinUtils;
import com.aibabel.fyt_exitandentry.utils.SideBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.okgo.model.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ChoiceCityActivity extends BaseActivity implements BaseCallback<BaseBean> {

    @BindView(R.id.iv_guanbi)
    ImageView ivGuanbi;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.rv_currency)
    PinnedHeaderListView sortListView;
    @BindView(R.id.tv_dialog)
    TextView tvDialog;
    @BindView(R.id.sb_sort)
    SideBar sbSort;
    @BindView(R.id.rv)
    RecyclerView rv;
    private List<CityListBean.DataBean> SourceDateList;

    private String[] sections;
    private static String ALL_CHARACTER = "";
    private int[] counts;
    private MySectionIndexer mIndexer;

    private SortAdapter adapter;

    private boolean isActive = false;
    private List<HotCityBean> stringList = new ArrayList<>();
    private CommomRecyclerAdapter adapter1;
    private CityAirplaneBean cityAirplaneBean;
    private List<CityAirplaneBean.DataBean> airplaneBeanData;
    private int isFirst;

    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_choice_city;
    }

    @Override
    public void init() {
//        initRecycleView();
//        stringList.add("京都");
//        stringList.add("大阪");
//        stringList.add("北海道");
//        stringList.add("熊本");
//        stringList.add("吉普岛");
//        stringList.add("济州岛");
        Intent intent = getIntent();
        isFirst = intent.getIntExtra("isFirst", 0);
        initData();
        initEvents();
        initAdapter();
        setPathParams("目的地名称");
    }

    private void initAdapter() {


//        LinearLayoutManager layoutManager = new LinearLayoutManager(ChoiceCityActivity.this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        //设置布局管理器
        rv.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter1 = new CommomRecyclerAdapter(ChoiceCityActivity.this, stringList, R.layout.recy_country, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
                //热门城市点击
                Map map = new HashMap();
                map.put("p1",stringList.get(postion).getHotCity() );
                StatisticsManager.getInstance(mContext).addEventAidl(1812,map);

                Intent intent = new Intent(ChoiceCityActivity.this, MainActivity.class);
                intent.putExtra("country", stringList.get(postion).getHotCountry());
                intent.putExtra("city", stringList.get(postion).getHotCity());
                Constans.CITY = stringList.get(postion).getHotCity();
                Constans.COUNTRY = stringList.get(postion).getHotCountry();
                if (isFirst == 1) {
                    isFirst=0;
                    startActivity(intent);
                } else {
                    setResult(RESULT_OK, intent);
                }
                    finish();
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {
                TextView tv_country_name = holder.getView(R.id.tv);
                tv_country_name.setText(stringList.get(position).getHotCity());
            }
        };
        rv.setAdapter(adapter1);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActive = false;
    }

    /**
     * 初始化MainBean的数据列表
     */
    public void initData() {


        Map<String, String> map = new HashMap<>();
        OkGoUtil.<CityListBean>get(ChoiceCityActivity.this, Constans.METHOD_GETCITYLIST, map, CityListBean.class, this);
//        OkGoUtil.<CityListBean>get(ChoiceCityActivity.this, isActive, Constans.METHOD_GETCITYNOGROUP, map, CityListBean.class, this);
        sbSort.setTextView(tvDialog);
    }


    @OnClick(R.id.iv_guanbi)
    public void onViewClicked() {
        ivGuanbi.setClickable(false);
        finish();
    }

    //当点击edittext意外位置时候 ，关闭软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            boolean hideInputResult = isShouldHideInput(v, ev);
            Log.v("hideInputResult", "zzz-->>" + hideInputResult);
            if (hideInputResult) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) ChoiceCityActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                if (v != null) {
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            //之前一直不成功的原因是,getX获取的是相对父视图的坐标,getRawX获取的才是相对屏幕原点的坐标！！！
            Log.v("leftTop[]", "zz--left:" + left + "--top:" + top + "--bottom:" + bottom + "--right:" + right);
            Log.v("event", "zz--getX():" + event.getRawX() + "--getY():" + event.getRawY());
            if (event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    private void setAdapter() {
        Collections.sort(SourceDateList, new PinyinComparator());
        getIndexerAndCounts(SourceDateList);
//        adapter = new SortAdapter(this, SourceDateList, mIndexer);
//        sortListView.setAdapter(adapter);
//        sortListView.setOnScrollListener(adapter);
        //設置頂部固定頭部
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_group_item, sortListView, false);

        sortListView.setPinnedHeaderView(view);
    }

    public void getIndexerAndCounts(List<CityListBean.DataBean> list) {
        ALL_CHARACTER = "";
        List<String> sectionlist = new ArrayList<>();
        for (CityListBean.DataBean city : list) {    //计算全部城市
            String firstCharacter = city.getGroupByChj();
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
            String firstCharacter = city.getGroupByChj();
            int index = ALL_CHARACTER.indexOf(firstCharacter);
            counts[index]++;
        }
        mIndexer = new MySectionIndexer(sections, counts);
        adapter = new SortAdapter(this, list, mIndexer);
        sortListView.setAdapter(adapter);
        sortListView.setOnScrollListener(adapter);
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
                        sortListView.setSelection(position);
                    }
                }
            }
        });

        //ListView的点击事件
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Map map = new HashMap();
                map.put("p1",adapter.getData().get(position).getCityChj() );
                StatisticsManager.getInstance(mContext).addEventAidl(1813,map);

                Intent intent = new Intent(ChoiceCityActivity.this, MainActivity.class);
                intent.putExtra("country", adapter.getData().get(position).getCountryChj());
                intent.putExtra("city", adapter.getData().get(position).getCityChj());
                Constans.CITY = adapter.getData().get(position).getCityChj();
                Constans.COUNTRY = adapter.getData().get(position).getCountryChj();
                if (isFirst == 1) {
                    isFirst=0;
                    startActivity(intent);
                } else {
                    setResult(RESULT_OK, intent);
                }
                finish();
//                itemRequest(adapter.getData().get(position).getCountryChj(), adapter.getData().get(position).getCityChj());

            }
        });

        //根据输入框输入值的改变来过滤搜索
//        etSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
//                if (CommonUtils.isAvailable(ChoiceCityActivity.this))
//                    filterData(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }

    private void itemRequest(String countryChj, String cityChj) {


        Map<String, String> map = new HashMap<>();
        map.put("countryChj", countryChj);
        map.put("cityChj", cityChj);
        OkGoUtil.<CityAirplaneBean>get(ChoiceCityActivity.this, Constans.METHOD_GETCITYAIRPLANE, map, CityAirplaneBean.class, this);
    }


    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<CityListBean.DataBean> mSortList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            mSortList = SourceDateList;
        } else {
            mSortList.clear();
            for (CityListBean.DataBean sortModel : SourceDateList) {
                String name = sortModel.getCityChj();
                if (name.toUpperCase().indexOf(filterStr.toString().toUpperCase()) != -1 || PinyinUtils.getPingYin(name).toUpperCase().startsWith(filterStr.toString().toUpperCase())) {
                    mSortList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(mSortList, new PinyinComparator());
        if (mSortList != null)
            getIndexerAndCounts(mSortList);
//        adapter.updateListView(mSortList, mIndexer);
    }


    @Override
    public void onSuccess(String method, BaseBean baseBean, String s1) {
        switch (method) {
            case Constans.METHOD_GETCITYLIST:
                SourceDateList = ((CityListBean) baseBean).getData();
                setAdapter();
                for (int i = 0; i < SourceDateList.size(); i++) {
                    if (TextUtils.equals(SourceDateList.get(i).getIsHot(), "1")) {
                        stringList.add(new HotCityBean(SourceDateList.get(i).getCountryChj(), SourceDateList.get(i).getCityChj()));
                    }
                }
                adapter1.updateData(stringList);
                break;

            case Constans.METHOD_GETCITYAIRPLANE:
                cityAirplaneBean = (CityAirplaneBean) baseBean;
                airplaneBeanData = cityAirplaneBean.getData();
                Intent intent = new Intent(ChoiceCityActivity.this, MainActivity.class);
                intent.putExtra("list", (Serializable) airplaneBeanData);
                startActivity(intent);
                finish();
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
