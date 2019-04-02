package com.aibabel.alliedclock.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.aibabel.alliedclock.MainActivity;
import com.aibabel.alliedclock.R;
import com.aibabel.alliedclock.app.BaseActivity;
import com.aibabel.alliedclock.app.Constant;
import com.aibabel.alliedclock.bean.CityListBean;
import com.aibabel.alliedclock.custom.EditTextWithDel;
import com.aibabel.alliedclock.custom.SideBar;
import com.aibabel.alliedclock.custom.lianxiren.MySectionIndexer;
import com.aibabel.alliedclock.custom.lianxiren.PinnedHeaderListView;
import com.aibabel.alliedclock.custom.lianxiren.PinyinComparator;
import com.aibabel.alliedclock.custom.lianxiren.PinyinUtils;
import com.aibabel.alliedclock.custom.lianxiren.SortAdapter;
import com.aibabel.alliedclock.okgo.BaseBean;
import com.aibabel.alliedclock.okgo.BaseCallback;
import com.aibabel.alliedclock.okgo.OkGoUtil;
import com.aibabel.alliedclock.utils.CommonUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AddCityActivity extends BaseActivity implements BaseCallback {

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

    private List<CityListBean.DataBean> SourceDateList;

    private String[] sections;
    private static String ALL_CHARACTER = "";
    private int[] counts;
    private MySectionIndexer mIndexer;

    private SortAdapter adapter;

    private boolean isActive = false;

    @Override
    public int initLayout() {
        return R.layout.activity_add_city;
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
        OkGoUtil.<CityListBean>get(AddCityActivity.this, isActive, Constant.URL_CITYLIST_NEW, map, CityListBean.class, this);
//        GetRequest<String> getRequest = OkGo.<String>get(Constant.IP_PORT + Constant.URL_CITYLIST).tag(this);
//        getRequest.params("sn", CommonUtils.getSN());
//        getRequest.params("sl", CommonUtils.getLocal(AddCityActivity.this));
//        getRequest.params("no", CommonUtils.getRandom());
//        getRequest.execute(new StringCallback() {
//            @Override
//            public void onSuccess(Response<String> response) {
////                        CurrencyBean bean = new CurrencyBean();
////                        bean.setGroup(getResources().getString(R.string.common_used));
////                        List<CurrencyBean.ChildBean> childBeanList = new ArrayList<>();
////                        childBeanList.add(new CurrencyBean.ChildBean("CNY", "人民币", "", "", "", ""));
////                        childBeanList.add(new CurrencyBean.ChildBean("USA", "美元", "", "", "", ""));
////                        childBeanList.add(new CurrencyBean.ChildBean("JPY", "日元", "", "", "", ""));
////                        bean.setChild(childBeanList);
////                        currencyBeanList.add(bean);
//                List<CityBean> list = FastJsonUtil.changeJsonToList(response.body(), CityBean.class);
////        List<CurrencyBean> list = FastJsonUtil.changeJsonToList(aa, CurrencyBean.class);
//                for (int i = 0; i < list.size(); i++) {
//                    CityBean cityBean = list.get(i);
//                    if (cityBean.getChild().size() > 0) {
//                        //往右侧的sort排序控件上加子项
//                        indexString.add(cityBean.getGroup());
//                        cityBeanList.add(cityBean);
//                    }
//                }
//                sbSort.setIndexText(indexString);
//                adapter.updateData(cityBeanList);
//                //遍历所有group,将所有项设置成默认展开
//                for (int i = 0; i < cityBeanList.size(); i++) {
//                    rvCurrency.expandGroup(i);
//                }
//            }
//
//            @Override
//            public void onError(Response<String> response) {
//                super.onError(response);
//                Log.e("choosecurrency", "onError");
//            }
//        });
        sbSort.setTextView(tvDialog);
    }

//    public void initEvent() {
//        //设置右侧触摸监听
//        sbSort.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
//            @Override
//            public void onTouchingLetterChanged(String s) {
//                //该字母首次出现的位置
//                int position = adapter.getPositionOfGroupForSection(s.charAt(0));
//                if (position != -1) {
//                    //因为列表默认加上了常用的分组 所以位置需要再-1
//                    rvCurrency.setSelectedGroup(position + 1 - 1);
//                }
//            }
//        });
//        //根据输入框输入值的改变来过滤搜索
//        etSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
//                filterData(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//    }
//
//
//    public void initRecycleView() {
//        rvCurrency.setHeaderView(getLayoutInflater().inflate(R.layout.item_layout_for_group, null));
//        adapter = new MyExpandableListViewAdapter(AddCityActivity.this, cityBeanList);
//        rvCurrency.setAdapter(adapter);
////        rvCurrency.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
////            @Override
////            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
////                return true;
////            }
////        });
//        rvCurrency.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                Intent intent = new Intent();
//                intent.putExtra("cityCn", adapter.getData().get(groupPosition).getChild().get(childPosition).getCityCn());
//                intent.putExtra("cityEn", adapter.getData().get(groupPosition).getChild().get(childPosition).getCityEn());
//                intent.putExtra("timeZone", adapter.getData().get(groupPosition).getChild().get(childPosition).getTimeZone());
//                setResult(666, intent);
//                finish();
//                return false;
//            }
//        });
//    }
//
//    /**
//     * 根据输入框中的值来过滤数据并更新ListView
//     *
//     * @param filterStr
//     */
//    private void filterData(String filterStr) {
//        List<CityBean> mSortList = new ArrayList<>();
//        if (TextUtils.isEmpty(filterStr)) {
//            mSortList = cityBeanList;
//        } else {
//            mSortList.clear();
////            for (CurrencyBean sortModel : currencyBeanList) {
////                String name = sortModel.getGroup();
////                if (name.toUpperCase().indexOf(filterStr.toString().toUpperCase()) != -1 || PinyinUtils.getPingYin(name).toUpperCase().startsWith(filterStr.toString()
/// .toUpperCase())) {
////                    mSortList.add(sortModel);
////                }
////            }
//            for (int i = 0; i < cityBeanList.size(); i++) {
//                if (cityBeanList.get(i).getGroup().equals(filterStr.toString().toUpperCase())) {
//                    mSortList.add(cityBeanList.get(i));
//                }
//            }
//        }
//        // 根据a-z进行排序
////        Collections.sort(mSortList, new PinyinComparator());
//        adapter.updateData(mSortList);
//        for (int i = 0; i < mSortList.size(); i++) {
//            rvCurrency.expandGroup(i);
//        }
//    }

    @OnClick(R.id.iv_guanbi)
    public void onViewClicked() {
        ivGuanbi.setClickable(false);
        /**####  start-hjs-addStatisticsEvent   ##**/
        try {
            addStatisticsEvent("alliedclock_addCity1", null);
        }catch (Exception e){
            e.printStackTrace();
        }
        /**####  end-hjs-addStatisticsEvent  ##**/
        finish();
    }

    //当点击edittext意外位置时候 ，关闭软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            /**####  start-hjs-addStatisticsEvent   ##**/
            try {
                addStatisticsEvent("alliedclock_addCity2", null);
            }catch (Exception e){
                e.printStackTrace();
            }
            /**####  end-hjs-addStatisticsEvent  ##**/
            View v = getCurrentFocus();
            boolean hideInputResult = isShouldHideInput(v, ev);
            Log.v("hideInputResult", "zzz-->>" + hideInputResult);
            if (hideInputResult) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) AddCityActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
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
                    /**####  start-hjs-addStatisticsEvent   ##**/
                    try {
                        HashMap<String, Serializable> add_hp = new HashMap<>();
                        add_hp.put("alliedclock_num",position);
                        addStatisticsEvent("alliedclock_addCity7", add_hp);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    /**####  end-hjs-addStatisticsEvent  ##**/
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
                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("alliedclock_name",adapter.getData().get(position).getCityCn());
                    addStatisticsEvent("alliedclock_addCity4", add_hp);

                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                Intent intent = new Intent();
                intent.putExtra("cityCn", adapter.getData().get(position).getCityCn());
                intent.putExtra("cityEn", adapter.getData().get(position).getCityEn());
                intent.putExtra("timeZone", adapter.getData().get(position).getTimeZone());
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
                if (CommonUtils.isAvailable(AddCityActivity.this)&& SourceDateList != null && SourceDateList.size() > 0)
                    filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                if (name.toUpperCase().indexOf(filterStr.toString().toUpperCase()) != -1 || PinyinUtils.getPingYin(name).toUpperCase().startsWith(filterStr.toString()
                        .toUpperCase())) {
                    /**####  start-hjs-addStatisticsEvent   ##**/
                    try {
                        HashMap<String, Serializable> add_hp = new HashMap<>();
                        add_hp.put("alliedclock_search",name);
                        addStatisticsEvent("alliedclock_addCity3", add_hp);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    /**####  end-hjs-addStatisticsEvent  ##**/
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

}
