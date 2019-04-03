package com.aibabel.scenic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.CommonUtils;
import com.aibabel.baselibrary.utils.SharePrefUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.scenic.R;
import com.aibabel.scenic.adapter.SearchAdapter;
import com.aibabel.scenic.adapter.SearchWordAdapter;
import com.aibabel.scenic.base.BaseScenicActivity;
import com.aibabel.scenic.bean.SearchBean;
import com.aibabel.scenic.bean.SearchWordBean;
import com.aibabel.scenic.okgo.ApiConstant;
import com.aibabel.scenic.utils.KeyBords;
import com.aibabel.scenic.utils.Logs;
import com.aibabel.scenic.view.EmptyLayout;

import java.io.Serializable;
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

public class CitySearchActivity extends BaseScenicActivity{



    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_clear_et)
    ImageView etClear;
    @BindView(R.id.lv_search)
    ListView lvSearch;
    @BindView(R.id.empty)
    EmptyLayout mEmpty;
    @BindView(R.id.tv_history_city_one)
    TextView tvCityOne;
    @BindView(R.id.tv_history_city_two)
    TextView tvCityTwo;
    @BindView(R.id.tv_history_city_three)
    TextView tvCityThree;


    @Override
    public int getLayouts(Bundle var1) {
        return R.layout.ativity_citysearch;
    }

    @Override
    public void initView() {

        addStatisticsEvent("scenic_search_open",null);

        etSearch.setHint("搜搜您想要的");
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

        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStatisticsEvent("scenic_search_click",null);
            }
        });

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

                    try{
                        HashMap<String, Serializable> map = new HashMap<>();
                        map.put("scenic_search_search_key",data+"");
                        addStatisticsEvent("scenic_search_search",map);
                    }catch (Exception e){}

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
        mEmpty.setOnBtnClickListener(new EmptyLayout.onClickListener() {
            @Override
            public void onBtnClick() {
                getDataViewSearch(etSearch.getText().toString().trim());
            }
        });
        showHistory();
    }

    private void showHistory() {
        String oneHistory = SharePrefUtil.getString(mContext,"citySearchNameOne","");
        if (!TextUtils.isEmpty(oneHistory)){
            tvCityOne.setText(oneHistory.split(",")[0]);
        }
        String twoHistory = SharePrefUtil.getString(mContext,"citySearchNameTwo","");
        if (!TextUtils.isEmpty(twoHistory)){
            tvCityTwo.setText(twoHistory.split(",")[0]);
        }
        String threeHistory = SharePrefUtil.getString(mContext,"citySearchNameThree","");
        if (!TextUtils.isEmpty(threeHistory)){
            tvCityThree.setText(threeHistory.split(",")[0]);
        }
    }

    private void getDataViewSearch(String data) {
        if (!CommonUtils.isNetworkAvailable(mContext)){
            mEmpty.setErrorType(EmptyLayout.NETWORK_EMPTY);
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("keyword",data);
        OkGoUtil.get(mContext, ApiConstant.GET_WORD_SEARCH, map, SearchWordBean.class, new BaseCallback<SearchWordBean>() {
            @Override
            public void onSuccess(String method, SearchWordBean model, String resoureJson) {
                Logs.e(ApiConstant.GET_CITY_SEARCH+"："+resoureJson);
                if (model.getData() != null){
                    if (model.getData().getCityList() == null && model.getData().getPoiList() == null && model.getData().getSubPoiList() == null){
                        mEmpty.setErrorType(EmptyLayout.NORMAL_EMPTY);
                        lvSearch.setVisibility(View.GONE);
                    }else{
                        showSearchData(model.getData());
                        mEmpty.setErrorType(EmptyLayout.SUCCESS_EMPTY);
                        lvSearch.setVisibility(View.VISIBLE);
                        addStatisticsEvent("scenic_search_resoult_open",null);


                    }
                }else{
                    mEmpty.setErrorType(EmptyLayout.NORMAL_EMPTY);
                    lvSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(String method, String message, String resoureJson) {
                Logs.e(ApiConstant.GET_CITY_SEARCH+"："+message);
                mEmpty.setErrorType(EmptyLayout.ERROR_EMPTY);
            }

            @Override
            public void onFinsh(String method) {

            }
        });
    }

    private SearchWordAdapter searchAdapter = null;
    private List<SearchWordBean.DataBean.CityListBean> listBean = null;
    private void showSearchData(SearchWordBean.DataBean data1) {
        listBean = new ArrayList<>();
        if (data1.getCityList() != null && data1.getCityList().size() != 0){
            listBean.addAll(data1.getCityList());
        }
        if (data1.getPoiList() != null && data1.getPoiList().size() != 0){
            listBean.addAll(data1.getPoiList());
        }
        if (data1.getSubPoiList() != null && data1.getSubPoiList().size() != 0){
            listBean.addAll(data1.getSubPoiList());
        }

        searchAdapter = new SearchWordAdapter(mContext,listBean);
        lvSearch.setAdapter(searchAdapter);
        searchAdapter.setOnItemClickListener(new SearchWordAdapter.onClickListener() {
            @Override
            public void onItemClick(SearchWordBean.DataBean.CityListBean bean) {
                try{
                    HashMap<String, Serializable> map = new HashMap<>();
                    map.put("scenic_search_resoult_click_name",bean.getName());
                    addStatisticsEvent("scenic_search_resoult_click",map);
                }catch (Exception e){}

                Intent intent;
                switch (bean.getType()){
                    case 2:
                        //回到首页
                        saveDataHistory(bean.getName(),bean.getType(),bean.getIdstring());
                        intent = new Intent();
                        intent.putExtra("city",bean.getName());
                        Logs.e("状态：2，"+bean.getName());
                        CitySearchActivity.this.setResult(1002, intent);
                        CitySearchActivity.this.finish();
                        break;
                    case 3:
                        saveDataHistory(bean.getName(),bean.getType(),bean.getIdstring());
                        intent = new Intent();
                        intent.setClass(CitySearchActivity.this, SpotsActivity.class);
                        intent.putExtra("poiId", bean.getIdstring());
                        Logs.e("状态：3，"+bean.getIdstring());
                        startActivity(intent);
                        break;
                    case 4:
                        saveDataHistory(bean.getName(),bean.getType(),bean.getPidStr());
                        intent = new Intent();
                        intent.setClass(CitySearchActivity.this, SpotsActivity.class);
                        intent.putExtra("poiId", bean.getPidStr());
                        Logs.e("状态：4，"+bean.getPidStr());
                        startActivity(intent);
                        break;
                }
            }
        });

    }

    private void saveDataHistory(String name, int type, String ids) {
        String strOne = SharePrefUtil.getString(mContext,"citySearchNameOne","");
        String strTwo = SharePrefUtil.getString(mContext,"citySearchNameTwo","");
        String strThree = SharePrefUtil.getString(mContext,"citySearchNameThree","");
        if (TextUtils.isEmpty(strOne)){
            SharePrefUtil.saveString(mContext,"citySearchNameOne",name+","+type+","+ids);
        }else{
            if (TextUtils.isEmpty(strTwo)){
                SharePrefUtil.saveString(mContext,"citySearchNameTwo",name+","+type+","+ids);
            }else{
                if (TextUtils.isEmpty(strThree)){
                    SharePrefUtil.saveString(mContext,"citySearchNameThree",name+","+type+","+ids);
                }else{
                    addStatisticsEvent("scenic_search_clear_history",null);
                    SharePrefUtil.saveString(mContext,"citySearchNameThree",strTwo);
                    SharePrefUtil.saveString(mContext,"citySearchNameTwo",strOne);
                    SharePrefUtil.saveString(mContext,"citySearchNameOne",name+","+type+","+ids);
                }
            }
        }
        showHistory();
    }


    @Override
    public void initData() {

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                addStatisticsEvent("scenic_search_resoult_close",null);
                addStatisticsEvent("scenic_search_close",null);
                this.finish();
                break;
            case R.id.iv_clear_et:
                etSearch.setText("");
                KeyBords.closeKeybord(etSearch, mContext);
                lvSearch.setVisibility(View.GONE);
                break;
            case R.id.tv_history_city_one:
                getCityHistory("citySearchNameOne");
                break;
            case R.id.tv_history_city_two:
                getCityHistory("citySearchNameTwo");
                break;
            case R.id.tv_history_city_three:
                getCityHistory("citySearchNameThree");
                break;
        }
    }

    private void getCityHistory(String key) {
        String str = SharePrefUtil.getString(mContext,key,"");
        String[] sr = str.split(",");
        String sr1 = sr[0];
        String sr2 = sr[1];
        String sr3 = sr[2];
        Logs.e(str);
        try{
            HashMap<String, Serializable> map = new HashMap<>();
            map.put("scenic_search_near_name",sr1);
            addStatisticsEvent("scenic_search_near",map);
        }catch (Exception e){}
        Intent intent;
        switch (sr2){
            case "2":
                intent = new Intent();
                intent.putExtra("city",sr1);
                CitySearchActivity.this.setResult(1002, intent);
                CitySearchActivity.this.finish();
                break;
            case "3":
                intent = new Intent();
                intent.setClass(CitySearchActivity.this, SpotsActivity.class);
                intent.putExtra("poiId", sr3);
                startActivity(intent);
                break;
            case "4":
                intent = new Intent();
                intent.setClass(CitySearchActivity.this, SpotsActivity.class);
                intent.putExtra("poiId", sr3);
                startActivity(intent);
                break;
        }

    }
}
