package com.aibabel.fyt_play.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.adapter.BaseRecyclercAdapter;
import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.fyt_play.R;
import com.aibabel.fyt_play.adapter.CommomRecyclerAdapter;
import com.aibabel.fyt_play.adapter.CommonRecyclerViewHolder;
import com.aibabel.fyt_play.bean.CityBean;
import com.aibabel.fyt_play.bean.Constans;
import com.aibabel.fyt_play.bean.PlayBean;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChoiceCityActivity extends BaseActivity implements BaseCallback<BaseBean>{

    @BindView(R.id.iv_guanbi)
    ImageView ivGuanbi;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.rv)
    RecyclerView rv;
    private List<CityBean.DataBean> cityBeanList = new ArrayList<>();
    private TextView tvCity;
    private CommomRecyclerAdapter adapter;

    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_choice_city;
    }

    @Override
    public void init() {
        initAdapter();
        initDate();
        ivGuanbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void initAdapter() {
        GridLayoutManager layoutManager = new GridLayoutManager(ChoiceCityActivity.this,3);
        //设置布局管理器
        rv.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new CommomRecyclerAdapter(ChoiceCityActivity.this, cityBeanList, R.layout.city_item, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, final int postion) {

                Map map = new HashMap();
                map.put("p1",cityBeanList.get(postion));
                StatisticsManager.getInstance(mContext).addEventAidl(1511,map);


                Intent intent = new Intent(ChoiceCityActivity.this,MainActivity.class);
                intent.putExtra("city",cityBeanList.get(postion).getCity());
                intent.putExtra("country",cityBeanList.get(postion).getCountry());
                setResult(RESULT_OK,intent);
                finish();
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, final int position) {
                tvCity = holder.getView(R.id.tv_city);
                tvCity.setText(cityBeanList.get(position).getCity());
            }
        };

        rv.setAdapter(adapter);


    }
    private void initDate() {
        Map<String, String> map = new HashMap<>();
        if (Constans.ISCOUNTRY){
            map.put("country",Constans.COUNTRY);
        }
        OkGoUtil.<CityBean>get(this, Constans.METHOD_GETPLAYADDRLIST, map, CityBean.class, this);
//        OkGoUtil.<CityListBean>get(ChoiceCityActivity.this, isActive, Constans.METHOD_GETCITYNOGROUP, map, CityListBean.class, this);
    }



    @Override
    public void onSuccess(String method, BaseBean baseBean, String s1) {
        Log.e("s1",s1);
        switch (method) {
            case Constans.METHOD_GETPLAYADDRLIST:
                CityBean baseBean1 = (CityBean) baseBean;
                cityBeanList = baseBean1.getData();
                adapter.updateData(cityBeanList);
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
