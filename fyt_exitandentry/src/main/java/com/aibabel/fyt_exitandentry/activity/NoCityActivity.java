package com.aibabel.fyt_exitandentry.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.fyt_exitandentry.R;
import com.aibabel.fyt_exitandentry.adapter.CommomRecyclerAdapter;
import com.aibabel.fyt_exitandentry.adapter.CommonRecyclerViewHolder;
import com.aibabel.fyt_exitandentry.bean.CityAirplaneBean;
import com.aibabel.fyt_exitandentry.bean.CityListBean;
import com.aibabel.fyt_exitandentry.bean.Constans;
import com.aibabel.fyt_exitandentry.bean.NoCityBean;
import com.aibabel.fyt_exitandentry.utils.ContentProviderUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lzy.okgo.model.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoCityActivity extends BaseActivity implements BaseCallback<BaseBean> {

    @BindView(R.id.tv_country_name)
    TextView tvCountryName;
    @BindView(R.id.tv_city_name)
    TextView tvCityName;
    @BindView(R.id.choice_city_ll_layout)
    LinearLayout choiceCityLlLayout;
    @BindView(R.id.country_img_cl_layout)
    RelativeLayout countryImgClLayout;
    @BindView(R.id.rv_city)
    RecyclerView rvCity;
    private CommomRecyclerAdapter adapter;
    private NoCityBean noCityBean;
    private List<NoCityBean.DataBean> noCityBeanData = new ArrayList<>();
    private int isFirst;


    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_no_city;
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        isFirst = intent.getIntExtra("isFirst", 0);
        initAdapter();
        initData();
        countryImgClLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        Constans.COUNTRY = ContentProviderUtil.getCountry(this);
//        Constans.COUNTRY = "日本";
        Map<String, String> map = new HashMap<>();
        map.put("countryName",Constans.COUNTRY);
        OkGoUtil.<NoCityBean>get(NoCityActivity.this, Constans.METHOD_GETCITYLIST, map, NoCityBean.class, this);

    }


    private void initAdapter() {


//        LinearLayoutManager layoutManager = new LinearLayoutManager(ChoiceCityActivity.this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        //设置布局管理器
        rvCity.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new CommomRecyclerAdapter(NoCityActivity.this, noCityBeanData, R.layout.recy_country1, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
                Intent intent = new Intent(NoCityActivity.this,MainActivity.class);
                intent.putExtra("country",noCityBeanData.get(postion).getCountryChj());
                intent.putExtra("city",noCityBeanData.get(postion).getCityChj());
                if (isFirst==1){
                    isFirst =0;
                    startActivity(intent);
                }else {

                    setResult(RESULT_OK,intent);
                }
                finish();
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {
                TextView tv_country_name = holder.getView(R.id.tv);
                tv_country_name.setText(noCityBeanData.get(position).getCityChj());
            }
        };
        rvCity.setAdapter(adapter);
    }



    @Override
    public void onSuccess(String method, BaseBean baseBean, String s1) {
        switch (method){
            case Constans.METHOD_GETCITYLIST:
                noCityBean = (NoCityBean) baseBean;
                noCityBeanData = noCityBean.getData();
                adapter.updateData(noCityBeanData);



                SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        countryImgClLayout.setBackground(resource);
                    }
                };
                RequestOptions options = new RequestOptions().placeholder(R.mipmap.jiazaizhong1).error(R.mipmap.jiazaishibai1);

                Glide.with(NoCityActivity.this).load(noCityBeanData.get(0).getImageUrl()).apply(options).into(simpleTarget);
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
