package com.aibabel.weather.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.weather.R;
import com.aibabel.weather.app.BaseActivity;
import com.aibabel.weather.app.Constant;
import com.aibabel.weather.bean.CityBean;
import com.aibabel.weather.bean.CityListBean;
import com.aibabel.weather.custom.EditTextWithDel;
import com.aibabel.weather.custom.MySectionIndexer;
import com.aibabel.weather.custom.PinnedHeaderListView;
import com.aibabel.weather.custom.PinyinComparator;
import com.aibabel.weather.custom.PinyinUtils;
import com.aibabel.weather.custom.SideBar;
import com.aibabel.weather.custom.SortAdapter;
import com.aibabel.weather.okgo.BaseBean;
import com.aibabel.weather.okgo.BaseCallback;
import com.aibabel.weather.okgo.OkGoUtil;
import com.aibabel.weather.utils.CommonUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AddCityActivity extends BaseActivity implements BaseCallback , SortAdapter.OnstateListener {

    @BindView(R.id.iv_guanbi)
    ImageView ivGuanbi;
    @BindView(R.id.rv_currency)
    PinnedHeaderListView sortListView;
    @BindView(R.id.sb_sort)
    SideBar sbSort;
    @BindView(R.id.tv_dialog)
    TextView tvDialog;
    @BindView(R.id.et_search)
    EditTextWithDel etSearch;

    private List<CityListBean.DataBean> SourceDateList = new ArrayList<>();

    private String[] sections;
    private static String ALL_CHARACTER = "";
    private int[] counts;
    private MySectionIndexer mIndexer;

    private SortAdapter adapter;

    //排序控件数据集合
    ArrayList<String> indexString = new ArrayList<>();
    //二级列表数据集合
    List<CityBean> cityBeanList = new ArrayList<>();


    public int initLayout() {
        return R.layout.activity_add_city;
    }
    @Override
    public int getLayout(Bundle savedInstanceState) {
        return initLayout();
    }


    @Override
    public void init() {
//        initRecycleView();
        initData();
        initEvents();

        switch (CommonUtils.getLocalLanguage()) {
            case "zh_CN":
            case "zh_TW":
            case "en":
                sbSort.setVisibility(View.VISIBLE);
                break;
            case "ja":
            case "ko":
                sbSort.setVisibility(View.GONE);
                break;
            default:
                sbSort.setVisibility(View.GONE);
                break;
        }


    }

    /**
     * 初始化MainBean的数据列表
     */
    public void initData() {
        Map<String, String> map = new HashMap<>();
        OkGoUtil.<CityListBean>get(AddCityActivity.this, Constant.URL_CITYLIST_NEW, map,
                CityListBean.class, this);
        sbSort.setTextView(tvDialog);
    }

    @OnClick(R.id.iv_guanbi)
    public void onViewClicked() {
        addStatisticsEvent("weather_addCity1",null);
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
                InputMethodManager imm = (InputMethodManager) AddCityActivity.this
                        .getSystemService(Activity.INPUT_METHOD_SERVICE);
                if (v != null) {
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
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
            Log.v("leftTop[]", "zz--left:" + left + "--top:" + top + "--bottom:" + bottom +
                    "--right:" + right);
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

        //設置頂部固定頭部
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout
                .list_group_item, sortListView, false);

        sortListView.setPinnedHeaderView(view);
    }

    public void getIndexerAndCounts(List<CityListBean.DataBean> list) {
        ALL_CHARACTER = "";
        List<String> sectionlist = new ArrayList<>();
        for (CityListBean.DataBean city : list) {    //计算全部城市
            String firstCharacter = city.getGroup();
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
            String firstCharacter = city.getGroup();
            int index = ALL_CHARACTER.indexOf(firstCharacter);
            counts[index]++;
        }
        mIndexer = new MySectionIndexer(sections, counts);
        adapter = new SortAdapter(this, list, mIndexer);
        adapter.onstateListener=this;
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
                        HashMap<String,Serializable> map=new HashMap<>();
                        map.put("weather_addCity7_num", s);
                        addStatisticsEvent("weather_addCity7",map);

                    }
                }
            }
        });

        //ListView的点击事件
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                HashMap<String, Serializable> map = new HashMap<>();
                map.put("weather_main1_country",adapter.getData().get(position).getCountryCn());
                map.put("weather_main1_city",adapter.getData().get(position).getCityCn());

                addStatisticsEvent("weather_addCity4",map);
                Intent intent = new Intent();
                intent.putExtra("countryCn", adapter.getData().get(position).getCountryCn());
                intent.putExtra("countryEn", adapter.getData().get(position).getCountryEn());
                intent.putExtra("cityCn", adapter.getData().get(position).getCityCn());
                intent.putExtra("cityEn", adapter.getData().get(position).getCityEn());
                setResult(666, intent);
                finish();
            }
        });

        //根据输入框输入值的改变来过滤搜索
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                if (CommonUtils.isNetAvailable(AddCityActivity.this) && SourceDateList != null && SourceDateList.size() > 0){
                    filterData(s.toString());

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    addStatisticsEvent("weather_addCity2",null);
                }
            }
        });

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        if (SourceDateList == null) return;
        List<CityListBean.DataBean> mSortList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            mSortList = SourceDateList;
        } else {
            mSortList.clear();
            for (CityListBean.DataBean sortModel : SourceDateList) {
                String name = sortModel.getCityCn();
                if (name.toUpperCase().indexOf(filterStr.toString().toUpperCase()) != -1 ||
                        PinyinUtils.getPingYin(name).toUpperCase().startsWith(filterStr.toString()
                        .toUpperCase())) {
                    mSortList.add(sortModel);
                }

            }
        }
        HashMap<String ,Serializable> map=new HashMap<>();
        map.put("weather_addCity3_search",filterStr);
        addStatisticsEvent("weather_addCity3",map);
        // 根据a-z进行排序
        Collections.sort(mSortList, new PinyinComparator());
        getIndexerAndCounts(mSortList);

//        adapter.updateListView(mSortList, mIndexer);
    }

    @Override
    public void onSuccess(String method, BaseBean model) {
        switch (method) {
            case Constant.URL_CITYLIST_NEW:
                SourceDateList = ((CityListBean) model).getData();
                setAdapter();
                break;
        }
    }

    @Override
    public void onError(String method, String message) {

    }
    int firstVisibleItem=0;
    @Override
    public void onStateChanged(int state) {
        if (state==1){
            firstVisibleItem=sortListView.getFirstVisiblePosition();
        }
        if (state==0){
            if (sortListView.getFirstVisiblePosition()-firstVisibleItem>0){
                addStatisticsEvent("weather_addCity5",null);
            }else{
                addStatisticsEvent("weather_addCity6",null);
            }

        }
    }
}
