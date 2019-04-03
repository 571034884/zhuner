package com.aibabel.scenic.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.scenic.R;
import com.aibabel.scenic.activity.CityLocaActivity;
import com.aibabel.scenic.adapter.CountryCityAdapter;
import com.aibabel.scenic.base.ScenicBaseApplication;
import com.aibabel.scenic.bean.AddressBean;
import com.aibabel.scenic.okgo.ApiConstant;
import com.aibabel.scenic.utils.Logs;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 南美洲
 * Created by fytworks on 2019/3/26.
 */

public class SouthAmericaFragment extends BaseFragment {

    @BindView(R.id.test_recy)
    ListView mRecy;

    private Context mContext;
    @Override
    public int getLayout() {
        return R.layout.fragment_test;
    }

    @Override
    public void init(View view, Bundle savedInstanceState) {
        mContext = getContext();
        showListView(ScenicBaseApplication.addressBeanSouth.getData());
    }

    private void showListView(List<AddressBean.DataBean> data) {
        CountryCityAdapter adapter = new CountryCityAdapter(mContext,data);
        mRecy.setAdapter(adapter);

        adapter.setOnItemClickListener(new CountryCityAdapter.onClickListener() {
            @Override
            public void onItemClick(String cityName) {
                try{
                    HashMap<String, Serializable> map = new HashMap<>();
                    map.put("scenic_loc_city_city",cityName);
                    ((CityLocaActivity)getActivity()).addStatisticsEvent("scenic_loc_city",map);

                }catch (Exception e){}
                CityLocaActivity act = (CityLocaActivity) getActivity();
                Intent intent = new Intent();
                intent.putExtra("city",cityName);
                act.setResult(1002, intent);
                act.finish();
            }
        });
    }
}
