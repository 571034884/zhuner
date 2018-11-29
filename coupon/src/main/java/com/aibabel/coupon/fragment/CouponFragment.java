package com.aibabel.coupon.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.coupon.R;
import com.aibabel.coupon.activity.DetailsActivity;
import com.aibabel.coupon.adapter.CommomRecyclerAdapter;
import com.aibabel.coupon.adapter.CommonRecyclerViewHolder;
import com.aibabel.coupon.bean.CouponBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 * 我的优惠券页面
 */
public class CouponFragment extends Fragment {


    @BindView(R.id.tv_mudidi1)
    TextView tvMudidi1;
    @BindView(R.id.rv_coupon)
    RecyclerView rvCoupon;
    Unbinder unbinder;
    private CommomRecyclerAdapter adapter;
    private List<CouponBean> couponList = new ArrayList<>();
    private String img_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536148533790&di=6b6f44e5602faa55606b7918ba7e00f1&imgtype=0&src=http%3A%2F%2Fi2.w.hjfile.cn%2Fnews%2F201509%2F201509101221406593.jpg";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_coupon, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        initAdapter();
        initData();
        return inflate;
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            couponList.add(new CouponBean("", img_url, "松本清", "立享95折+8%免税", "消费满30000日元使用", "去使用"));
        }
        adapter.updateData(couponList);
    }

    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        rvCoupon.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new CommomRecyclerAdapter(getActivity(), couponList, R.layout.recy_coupon, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, final int postion) {
                startActivity(new Intent(getActivity(), DetailsActivity.class));
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {
                TextView tv_jiaobiao_name = holder.getView(R.id.tv_jiaobiao_name);
                TextView tv_shop_name = holder.getView(R.id.tv_shop_name);
                TextView tv_shop_price = holder.getView(R.id.tv_shop_price);
                TextView tv_shop_details = holder.getView(R.id.tv_shop_details);
                TextView tv_receive = holder.getView(R.id.tv_receive);
                ImageView iv_shop_img = holder.getView(R.id.iv_shop_img);
                tv_jiaobiao_name.setText(((CouponBean) o).getTv_jiaobiao_name());
                tv_shop_name.setText(((CouponBean) o).getTv_shop_name());
                tv_shop_price.setText(((CouponBean) o).getTv_shop_price());
                tv_shop_details.setText(((CouponBean) o).getTv_shop_details());
                tv_receive.setText(((CouponBean) o).getTv_receive());
                tv_jiaobiao_name.setText(((CouponBean) o).getTv_jiaobiao_name());

                Glide.with(getActivity())
                        .load(((CouponBean) o).getIv_shop_img())
//                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into(iv_shop_img);
            }
        };
        rvCoupon.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        null.unbind();
//    }
}
