package com.aibabel.food.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.adapter.BaseRecyclercAdapter;
import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.food.R;
import com.aibabel.food.adapter.CateKindAdapter;
import com.aibabel.food.adapter.PopularAreaAdapter;
import com.aibabel.food.adapter.SpaceItemDecoration;
import com.aibabel.food.base.Constant;
import com.aibabel.food.bean.AreaBean;
import com.aibabel.food.bean.HomePageAllBean;
import com.aibabel.food.bean.HomePageBean;
import com.aibabel.food.bean.KindBean;
import com.orhanobut.logger.Logger;
import com.zhouyou.recyclerview.XRecyclerView;
import com.zhouyou.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperStateRecyclerViewAdapter;
import com.zhouyou.recyclerview.manager.StateGridLayoutManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AllOffThingsActivity extends BaseActivity implements BaseCallback<BaseBean> {

    public final static String ACTIVITY_TAG = "ActivityTag";
    public final static int TAG_DEFAULT = 0;
    public final static int TAG_AREA = 1; //区域页
    public final static int TAG_KIND = 2; //类别页
    public int activityTag;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvAlloff)
    TextView tvAlloff;
    @BindView(R.id.rvAlloff)
    XRecyclerView rvAlloff;

    BaseRecyclercAdapter adapter = null;
    StateGridLayoutManager layoutManager = null;
    private HomePageBean homePageBean;

    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_all_off_string;
    }

    @Override
    public void init() {
//        initDate();
        setPathParams(Constant.CURRENT_CITY + " " + activityTag);
        getIntentData();
        if (activityTag == TAG_AREA) {
            tvAlloff.setText(R.string.quanbuquyu);
            layoutManager = new StateGridLayoutManager(this, 4);
            adapter = new PopularAreaAdapter(this);
            getKindData(Constant.METHOD_AREA_LIST, AreaBean.class);
        } else if (activityTag == TAG_KIND) {
            tvAlloff.setText(R.string.meishifenlei);
            layoutManager = new StateGridLayoutManager(this, 2);
            adapter = new CateKindAdapter(this);
//            rvAlloff.addItemDecoration(new SpaceItemDecoration(20, 2));
            getKindData(Constant.METHOD_KIND_LIST, KindBean.class);
        } else {
            Logger.e("没有设置页面类型");
            return;
        }
        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object item, int position) {
                Map<String, String> map = new HashMap<>();
                switch (activityTag) {
                    case TAG_AREA:
                        map.put("p1", ((AreaBean.DataBean) item).getName_cn());
                        StatisticsManager.getInstance(mContext).addEventAidl(1021, map);

                        /**####  start-hjs-addStatisticsEvent   ##**/
                        try {
                            HashMap<String, Serializable> add_hp = new HashMap<>();
                            add_hp.put("food_allOff5_def", ((AreaBean.DataBean) item).getName_cn());
                            addStatisticsEvent("food_allOff5", add_hp);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        /**####  end-hjs-addStatisticsEvent  ##**/
                        startActivity(new Intent(mContext, FilterActivity.class).putExtra(FilterActivity.FILTER_TAG1, ((AreaBean.DataBean) item)
                                .getName_cn()));
                        break;
                    case TAG_KIND:
                        /**####  start-hjs-addStatisticsEvent   ##**/
                        try {
                            HashMap<String, Serializable> add_hp = new HashMap<>();
                            add_hp.put("food_allOff5_def", ((AreaBean.DataBean) item).getName_cn());
                            addStatisticsEvent("food_allOff5", add_hp);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        /**####  end-hjs-addStatisticsEvent  ##**/
                        map.put("p1", ((KindBean.DataBean) item).getName_cn());
                        StatisticsManager.getInstance(mContext).addEventAidl( 1032, map);
                        startActivity(new Intent(mContext, FilterActivity.class).putExtra(FilterActivity.FILTER_TAG2, ((KindBean.DataBean) item)
                                .getName_cn()));
                        break;
                }
            }
        });
        rvAlloff.setPullRefreshEnabled(false);
        rvAlloff.setLoadingMoreEnabled(false);
        rvAlloff.setLayoutManager(layoutManager);
        rvAlloff.setNestedScrollingEnabled(false);
        rvAlloff.setAdapter(adapter);

        adapter.setState(HelperStateRecyclerViewAdapter.STATE_LOADING);//模拟加载中
    }

    @OnClick({R.id.ivBack, R.id.tvAlloff})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:

                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("food_allOff1", null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                finish();
                break;
        }
    }

    public void getIntentData() {
        activityTag = getIntent().getIntExtra(ACTIVITY_TAG, TAG_DEFAULT);
    }

    public void initDate() {
        List<HomePageBean.PartBean.PartItemBean> beanList3 = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            HomePageBean.PartBean.PartItemBean bean3 = new HomePageBean.PartBean.PartItemBean();
            bean3.setIconUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543986255501&di=7d47c59d2f45068bc068ab80444f0fb6" +
                    "&imgtype=0&src=http%3A%2F%2Fimg18.3lian.com%2Fd%2Ffile%2F201710%2F13%2F338b9c076dda5bc17e9239af07c79b62.jpg");
            bean3.setName("日食记");
            bean3.setManUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543986367013&di=c15f2cb1df37da591f46e3e428eaddc7" +
                    "&imgtype=0&src=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170504%2F0RQ11233-7.jpg");
            bean3.setManSay("好吃到停不下来，首选");
            bean3.setManPay("$123/人");
            bean3.setScore(4.0f);
            bean3.setTip("寿司");
            beanList3.add(bean3);
        }
        HomePageBean.PartBean partBean3 = new HomePageBean.PartBean();
        partBean3.setPartName("本地推荐");
        partBean3.setPartType(3);
        partBean3.setPartItemBeanList(beanList3);

        List<HomePageBean.PartBean.PartItemBean> beanList2 = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            HomePageBean.PartBean.PartItemBean bean2 = new HomePageBean.PartBean.PartItemBean();
            bean2.setIconUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543986930813&di=498af964fa1efd21cfae72ef12851fe9" +
                    "&imgtype=0&src=http%3A%2F%2Fww1.sinaimg.cn%2Flarge%2F705e3514jw1eoq79d5kobj20b306yaa2.jpg");
            bean2.setName("海鲜");
            beanList2.add(bean2);
        }
        HomePageBean.PartBean partBean2 = new HomePageBean.PartBean();
        partBean2.setPartName("美食分类");
        partBean2.setPartType(2);
        partBean2.setPartItemBeanList(beanList2);

        List<HomePageBean.PartBean.PartItemBean> beanList1 = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            HomePageBean.PartBean.PartItemBean bean1 = new HomePageBean.PartBean.PartItemBean();
            bean1.setIconUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543986930813&di=498af964fa1efd21cfae72ef12851fe9" +
                    "&imgtype=0&src=http%3A%2F%2Fww1.sinaimg.cn%2Flarge%2F705e3514jw1eoq79d5kobj20b306yaa2.jpg");
            bean1.setName("新宿");
            beanList1.add(bean1);
        }
        HomePageBean.PartBean partBean1 = new HomePageBean.PartBean();
        partBean1.setPartName("人气区域");
        partBean1.setPartType(1);
        partBean1.setPartItemBeanList(beanList1);

        List<HomePageBean.PartBean> partBeanList = new ArrayList<>();
        partBeanList.add(partBean1);
        partBeanList.add(partBean2);
        partBeanList.add(partBean3);
        homePageBean = new HomePageBean();
        homePageBean.setPartBeanList(partBeanList);
        homePageBean.setCode("1");
        homePageBean.setMsg("Success");

//        Log.e(this.getClass().getSimpleName(), "initDate: " + FastJsonUtil.changObjectToString(homePageBean));
    }


    public void getKindData(String method, Class clazz) {
        Map<String, String> map = new HashMap<>();
        map.put("city", Constant.CURRENT_CITY);
        OkGoUtil.get(false, method, map, clazz, this);
    }

    @Override
    public void onSuccess(String s, BaseBean baseBean, String s1) {
        switch (s) {
            case Constant.METHOD_AREA_LIST:
                adapter.setListAll(((AreaBean) baseBean).getData());
                break;
            case Constant.METHOD_KIND_LIST:
                adapter.setListAll(((KindBean) baseBean).getData());
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
