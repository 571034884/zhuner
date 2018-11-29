package com.aibabel.coupon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.coupon.R;
import com.aibabel.coupon.adapter.CommomRecyclerAdapter;
import com.aibabel.coupon.adapter.CommonRecyclerViewHolder;
import com.aibabel.coupon.adapter.MyGridLayoutManager;
import com.aibabel.coupon.bean.CountryBean;
import com.aibabel.coupon.bean.CouponBean;
import com.aibabel.coupon.bean.RemenBean;
import com.aibabel.coupon.bean.ShopBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

// 优惠券详情页
public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_img)
    CircleImageView ivImg;
    @BindView(R.id.tv_zhekou)
    TextView tvZhekou;
    @BindView(R.id.tv_xiangqing)
    TextView tvXiangqing;
    @BindView(R.id.tv_xiangzhi)
    TextView tvXiangzhi;
    @BindView(R.id.iv_tiaoxinma)
    ImageView ivTiaoxinma;
    @BindView(R.id.tv_youhui_time)
    TextView tvYouhuiTime;
    @BindView(R.id.tv_youhui_zhengce)
    TextView tvYouhuiZhengce;
    @BindView(R.id.rv_dianpu)
    RecyclerView rvDianpu;
    @BindView(R.id.rv_remen)
    RecyclerView rvRemen;

    private List<ShopBean> shopList = new ArrayList<>();
    private List<RemenBean> remenList = new ArrayList<>();
    private String img_url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536148533790&di=6b6f44e5602faa55606b7918ba7e00f1&imgtype=0&src=http%3A%2F%2Fi2.w.hjfile.cn%2Fnews%2F201509%2F201509101221406593.jpg";
    private CommomRecyclerAdapter adapter;
    private CommomRecyclerAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        initAdapter();

        initData();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initShopAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        rvDianpu.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new CommomRecyclerAdapter(this, shopList, R.layout.recy_dianpu, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {
                TextView tv_dianpu_name = holder.getView(R.id.tv_dianpu_name);
                TextView tv_dianpu_address = holder.getView(R.id.tv_dianpu_address);
                TextView tv_dianpu_time = holder.getView(R.id.tv_dianpu_time);
                tv_dianpu_name.setText(((ShopBean) o).getShop_name());
                tv_dianpu_address.setText(((ShopBean) o).getShop_address());
                tv_dianpu_time.setText(((ShopBean) o).getShop_time());

            }
        };
        rvDianpu.setAdapter(adapter);


    }
    private void initRememAdapter() {
         MyGridLayoutManager myGridLayoutManager = new MyGridLayoutManager(this,2);
        //设置布局管理器
        rvRemen.setLayoutManager(myGridLayoutManager);
        //设置为垂直布局，这也是默认的

        adapter1 = new CommomRecyclerAdapter(this, remenList, R.layout.recy_remen, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
            }
        }, null) {


            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {
                TextView tv_remen_name = holder.getView(R.id.tv_remen_name);
                TextView tv_xingjiabi1 = holder.getView(R.id.tv_xingjiabi1);
                TextView tv_xingjiabi2 = holder.getView(R.id.tv_xingjiabi2);
                ImageView iv_remen_img = holder.getView(R.id.iv_remen_img);
                tv_remen_name.setText(((RemenBean) o).getRemen_name());
                tv_xingjiabi1.setText(((RemenBean) o).getRemen_tedian1());
                tv_xingjiabi2.setText(((RemenBean) o).getRemen_tedian2());
                Glide.with(DetailsActivity.this)
                        .load(((RemenBean) o).getRemen_img())
                        .into(iv_remen_img);

            }
        };
        rvRemen.setAdapter(adapter1);
    }

    private void initData() {
        for (int i = 0; i < 4; i++) {
            shopList.add(new ShopBean("松本清（银座5th店）", "地址：中英区银座5-5-1", "营业时间:10:00-22:00"));
            remenList.add(new RemenBean(img_url, "安耐晒", "防水", "性价比高"));
        }
        adapter.updateData(shopList);
        adapter1.updateData(remenList);
    }

    private void initAdapter() {

        initShopAdapter();
        initRememAdapter();


    }


}
