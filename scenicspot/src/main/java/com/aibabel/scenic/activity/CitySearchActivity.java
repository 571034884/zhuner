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

    @Override
    public int getLayouts(Bundle var1) {
        return R.layout.ativity_citysearch;
    }

    @Override
    public void initView() {
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
        mEmpty.setOnBtnClickListener(new EmptyLayout.onClickListener() {
            @Override
            public void onBtnClick() {
                getDataViewSearch(etSearch.getText().toString().trim());
            }
        });
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
                Intent intent;
                switch (bean.getType()){
                    case 2:
                        //回到首页
                        intent = new Intent();
                        intent.putExtra("city",bean.getName());
                        Logs.e("状态：2，"+bean.getName());
                        CitySearchActivity.this.setResult(1002, intent);
                        CitySearchActivity.this.finish();
                        break;
                    case 3:
                        intent = new Intent();
                        intent.setClass(CitySearchActivity.this, SpotsActivity.class);
                        intent.putExtra("poiId", bean.getIdstring());
                        Logs.e("状态：3，"+bean.getIdstring());
                        startActivity(intent);
                        break;
                    case 4:
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



    @Override
    public void initData() {

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                this.finish();
                break;
            case R.id.iv_clear_et:
                etSearch.setText("");
                KeyBords.closeKeybord(etSearch, mContext);
                lvSearch.setVisibility(View.GONE);
                break;
        }
    }

}
