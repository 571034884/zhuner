package com.aibabel.coupon.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.baselibrary.base.StatisticsBaseActivity;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.coupon.R;
import com.aibabel.coupon.activity.CouponActivity;
import com.aibabel.coupon.activity.DetailsActivity;
import com.aibabel.coupon.activity.ReceiveActivity;
import com.aibabel.coupon.adapter.CommomRecyclerAdapter;
import com.aibabel.coupon.adapter.CommonRecyclerViewHolder;
import com.aibabel.coupon.bean.Constans;
import com.aibabel.coupon.bean.CouponQueryBean;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * 我的优惠券页面
 */
public class CouponFragment extends BaseFragment implements BaseCallback<BaseBean> {


    @BindView(R.id.tv_mudidi1)
    TextView tvMudidi1;
    @BindView(R.id.rv_coupon)
    RecyclerView rvCoupon;
    @BindView(R.id.ll_nodate)
    LinearLayout llNodate;
    Unbinder unbinder;
    private CommomRecyclerAdapter adapter;
    private CouponQueryBean couponQueryBean;
    private List<CouponQueryBean.DataBean> couponQueryBeanData;
    private int couponId;
    private final int requestCode = 150;
    private CouponActivity activity;

    @Override
    public int getLayout() {
        return R.layout.fragment_coupon;
    }

    @Override
    public void init(View view, Bundle bundle) {
        initAdapter();
        initData();
    }


    private void initData() {
     /*   for (int i = 0; i < 5; i++) {
            couponList.add(new CouponBean("", img_url, "松本清", "立享95折+8%免税", "消费满30000日元使用", "去使用"));
        }
        adapter.updateData(couponList);*/
        activity = (CouponActivity) getActivity();
        Map<String, String> map = new HashMap<>();
        if (TextUtils.equals(Constans.PRO_VERSION,"L")){
            map.put("leaseId",Constans.PRO_DEV_OID);
        }
        OkGoUtil.<CouponQueryBean>get(getActivity(), Constans.METHOD_GETMYCOUPON, map, CouponQueryBean.class, this);
    }

    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        rvCoupon.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new CommomRecyclerAdapter(getActivity(), couponQueryBeanData, R.layout.recy_coupon, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, final int postion) {
                couponId = couponQueryBeanData.get(postion).getCouponId();
                Log.e("couponId", couponId + "");

                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    HashMap<String, Serializable> add_hp = new HashMap<>();
                    add_hp.put("coupon_Details6_def", couponId);
                    ((StatisticsBaseActivity)getActivity()).addStatisticsEvent("coupon_Details6", add_hp);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/

                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("couponId", couponId);
                startActivity(intent);

            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {
                TextView tv_jiaobiao_name = holder.getView(R.id.tv_jiaobiao_name);
                TextView tv_shop_name = holder.getView(R.id.tv_shop_name);
                TextView tv_shop_price = holder.getView(R.id.tv_shop_price);
                TextView tv_shop_details = holder.getView(R.id.tv_shop_details);
                TextView tv_receive = holder.getView(R.id.tv_receive);
                tv_receive.setVisibility(View.VISIBLE);
                ImageView iv_shop_img = holder.getView(R.id.iv_shop_img);
                tv_shop_name.setText(((CouponQueryBean.DataBean) o).getTitle());
                tv_shop_price.setText(((CouponQueryBean.DataBean) o).getYouhui());
                tv_shop_details.setText(((CouponQueryBean.DataBean) o).getTiaojianshort());

                Glide.with(getActivity())
                        .load(((CouponQueryBean.DataBean) o).getImage())
                        .into(iv_shop_img);


                tv_receive.setText("去使用");
                tv_receive.setTextColor(Color.parseColor("#ffffff"));
            }
        };
        rvCoupon.setAdapter(adapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

//        if(requestCode==100&&resultCode==200){
//
//        }

        int type = data.getIntExtra("type", 0);
        activity.showFragment(type);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onSuccess(String method, BaseBean baseBean, String s1) {
        switch (method) {
            case Constans.METHOD_GETMYCOUPON:
                couponQueryBean = (CouponQueryBean) baseBean;
                couponQueryBeanData = couponQueryBean.getData();
                if (couponQueryBeanData.size() == 0 || couponQueryBeanData == null) {
                    llNodate.setVisibility(View.VISIBLE);
                    rvCoupon.setVisibility(View.GONE);
                } else {
                    llNodate.setVisibility(View.GONE);
                    rvCoupon.setVisibility(View.VISIBLE);
                    for (int i = 0; i < couponQueryBeanData.size(); i++) {
                        Log.e("couponQueryBeanData", couponQueryBeanData.size() + "==" + i + "===" + couponQueryBeanData.get(i).getTiaojianshort());
                    }
                    adapter.updateData(couponQueryBeanData);
                }



                break;
        }
    }

    @Override
    public void onError(String s, String s1, String s2) {

    }

    @Override
    public void onFinsh(String s) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        null.unbind();
//    }
}
