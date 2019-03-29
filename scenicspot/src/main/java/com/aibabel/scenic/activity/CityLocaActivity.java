package com.aibabel.scenic.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.ProviderUtils;
import com.aibabel.baselibrary.utils.SharePrefUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.scenic.R;
import com.aibabel.scenic.adapter.FragmentAdapter;
import com.aibabel.scenic.adapter.SearchAdapter;
import com.aibabel.scenic.base.BaseScenicActivity;
import com.aibabel.scenic.bean.SearchBean;
import com.aibabel.scenic.fragment.AfricaFragment;
import com.aibabel.scenic.fragment.AsiaFragment;
import com.aibabel.scenic.fragment.EuiopeFragment;
import com.aibabel.scenic.fragment.NorthAmericaFragment;
import com.aibabel.scenic.fragment.OceaniaFragment;
import com.aibabel.scenic.fragment.SouthAmericaFragment;
import com.aibabel.scenic.okgo.ApiConstant;
import com.aibabel.scenic.utils.KeyBords;
import com.aibabel.scenic.utils.Logs;
import com.aibabel.scenic.view.SimpleViewpagerIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

/**
 * Created by fytworks on 2019/3/25.
 */

public class CityLocaActivity extends BaseScenicActivity{

    @BindView(R.id.indicator)
    SimpleViewpagerIndicator mIndicator;
    @BindView(R.id.viewpager)
    ViewPager mPager;
    @BindView(R.id.tv_location_name)
    TextView mLocationName;
    @BindView(R.id.tv_history_city_one)
    TextView mHistoryOne;
    @BindView(R.id.tv_history_city_two)
    TextView mHistoryTwo;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_clear_et)
    ImageView etClear;
    @BindView(R.id.lv_search)
    ListView lvSearch;
    @BindView(R.id.iv_close)
    ImageView ivClose;


    private String city = "";
    private List<Fragment> fragList = null;
    private List<String> strList = null;
    @Override
    public int getLayouts(Bundle var1) {
        return R.layout.activity_cityloca;
    }

    @Override
    public void initView() {

        String type = getIntent().getStringExtra("type");
        if (type.equals("0")){
            ivClose.setVisibility(View.GONE);
        }else if (type.equals("1")){
            ivClose.setVisibility(View.VISIBLE);
        }

        city = ProviderUtils.getInfo(ProviderUtils.COLUMN_CITY);
        if (!TextUtils.isEmpty(city)){
            mLocationName.setText(city);
            mLocationName.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.location_ok),
                    null, null, null);
        }else{
            mLocationName.setText("重新定位");
            mLocationName.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.location_error),
                    null, null, null);
        }
        mHistoryOne.setText(SharePrefUtil.getString(mContext,"locationNameOne",""));
        mHistoryTwo.setText(SharePrefUtil.getString(mContext,"locationNameTwo",""));


        initIndicator();
        mPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),fragList,strList));
        mIndicator.setViewPager(mPager);

        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void initIndicator() {
        mIndicator.setExpand(false)//设置tab宽度为包裹内容还是平分父控件剩余空间，默认值：false,包裹内容
                .setIndicatorColor(Color.parseColor("#FF8B01"))//indicator颜色
                .setIndicatorHeight(2)//indicator高度
                .setTabTextSize(17)//文字大小
                .setTabTextColor(Color.parseColor("#666666"))//文字颜色
                .setTabTypeface(null)//字体
                .setTabTypefaceStyle(Typeface.NORMAL)//字体样式：粗体、斜体等
                .setTabBackgroundResId(0)//设置tab的背景
                .setTabPadding(15)//设置tab的左右padding
                .setSelectedTabTextSize(20)//被选中的文字大小
                .setSelectedTabTextColor(Color.parseColor("#FF8805"))//被选中的文字颜色
                .setSelectedTabTypeface(null)
                .setSelectedTabTypefaceStyle(Typeface.BOLD)
                .setScrollOffset(120);//滚动偏移量

        fragList = new ArrayList<>();
        fragList.add(new AsiaFragment());
        fragList.add(new EuiopeFragment());
        fragList.add(new AfricaFragment());
        fragList.add(new NorthAmericaFragment());
        fragList.add(new SouthAmericaFragment());
        fragList.add(new OceaniaFragment());
        strList = new ArrayList<>();
        strList.add("亚洲");
        strList.add("欧洲");
        strList.add("非洲");
        strList.add("北美洲");
        strList.add("南美洲");
        strList.add("大洋洲");
    }

    @Override
    public void initData() {
        lvSearch.setVisibility(View.GONE);
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*+=|{}':;',\\[\\].<>/?~！@#￥%……&*——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        etSearch.setFilters(new InputFilter[]{filter});
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (cs.length() <= 0) {
                    etClear.setVisibility(View.INVISIBLE);
                }else {
                    etClear.setVisibility(View.VISIBLE);
                }
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    String data = etSearch.getText().toString().trim();
                    if (!TextUtils.isEmpty(data)){
                        Logs.e("搜索关键字："+data);
                        KeyBords.closeKeybord(etSearch,mContext);
                        getDataViewSearch(data);
                    }else{
                        ToastUtil.showShort(mContext,"请输入关键字");
                        return true;
                    }
                }
                return false;
            }
        });

    }

    private void getDataViewSearch(String data) {
        Map<String, String> map = new HashMap<>();
        map.put("keyword",data);
        OkGoUtil.get(mContext, ApiConstant.GET_CITY_SEARCH, map, SearchBean.class, new BaseCallback<SearchBean>() {
            @Override
            public void onSuccess(String method, SearchBean model, String resoureJson) {
                Logs.e(ApiConstant.GET_CITY_SEARCH+"："+resoureJson);
                if (model.getData().size() > 0){
                    lvSearch.setVisibility(View.VISIBLE);
                    showSearchData(data,model.getData());
                }else{
                    ToastUtil.showShort(mContext,"准儿没有找到信息");
                }
            }

            @Override
            public void onError(String method, String message, String resoureJson) {
                Logs.e(ApiConstant.GET_CITY_SEARCH+"："+message);
                ToastUtil.showShort(mContext,"准儿出错了");
                lvSearch.setVisibility(View.GONE);
            }

            @Override
            public void onFinsh(String method) {

            }
        });
    }

    private SearchAdapter searchAdapter = null;
    private void showSearchData(String data, List<SearchBean.DataBean> data1) {
        searchAdapter = new SearchAdapter(mContext,data1);
        lvSearch.setAdapter(searchAdapter);
        searchAdapter.setOnItemClickListener(new SearchAdapter.onClickListener() {
            @Override
            public void onItemClick(SearchBean.DataBean bean) {
                //数据存储
                saveLocationCity(bean.getName());
                //回到首页
                Intent intent = new Intent();
                intent.putExtra("city",bean.getName());
                CityLocaActivity.this.setResult(1002, intent);
                CityLocaActivity.this.finish();
            }
        });

    }

    private void saveLocationCity(String cityName) {
        String locHistoryOne = SharePrefUtil.getString(mContext,"locationNameOne","");
        String locHistoryTwo = SharePrefUtil.getString(mContext,"locationNameTwo","");
        if (TextUtils.isEmpty(locHistoryOne)){
            //第一没有值
            SharePrefUtil.saveString(mContext,"locationNameOne",cityName);
        }else{
            if (TextUtils.isEmpty(locHistoryTwo)){
                //第二没有值
                SharePrefUtil.saveString(mContext,"locationNameTwo",cityName);
            }else{
                //第一第二都有值
                /**
                 * 1.把第一个值存入第二个中
                 * 2.把当前值存入第一个中
                 */
                SharePrefUtil.saveString(mContext,"locationNameTwo",locHistoryOne);
                SharePrefUtil.saveString(mContext,"locationNameOne",cityName);

            }
        }
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_close:
                this.finish();
                break;
            case R.id.tv_location_name:
                if (!TextUtils.isEmpty(city)){
                    Intent intent = new Intent();
                    intent.putExtra("city",city);
                    setResult(1002, intent);
                    finish();
                }else{
                    city = ProviderUtils.getInfo(ProviderUtils.COLUMN_CITY);
                    if (!TextUtils.isEmpty(city)){
                        mLocationName.setText(city);
                        mLocationName.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.location_ok),
                                null, null, null);
                    }else{
                        mLocationName.setText("定位失败");
                        mLocationName.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.location_error),
                                null, null, null);
                    }
                }
                break;
            case R.id.iv_clear_et:
                etSearch.setText("");
                KeyBords.closeKeybord(etSearch, mContext);
                lvSearch.setVisibility(View.GONE);
                break;
        }
    }
}
