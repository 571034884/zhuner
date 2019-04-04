package com.aibabel.coupon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.coupon.R;
import com.aibabel.coupon.adapter.CommomRecyclerAdapter;
import com.aibabel.coupon.adapter.CommonRecyclerViewHolder;
import com.aibabel.coupon.adapter.MyGridLayoutManager;
import com.aibabel.coupon.bean.Constans;
import com.aibabel.coupon.bean.RemenBean;
import com.aibabel.coupon.bean.ShopBean;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;
import com.lzy.okgo.model.Response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

// 优惠券详情页
public class DetailsActivity extends BaseActivity implements BaseCallback<BaseBean> {

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
    @BindView(R.id.iv_yuantu)
    ImageView ivYuantu;


    private List<RemenBean> remenList = new ArrayList<>();
    private CommomRecyclerAdapter adapter;
    private CommomRecyclerAdapter adapter1;
    private ShopBean shopBean;
    private ShopBean.DataBean shopBeanData;

    @Override
    public int getLayout(Bundle bundle) {
        return  R.layout.activity_details;
    }

    @Override
    public void init() {
        initData();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**####  start-hjs-addStatisticsEvent   ##**/
                try {
                    addStatisticsEvent("coupon_Details3", null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                /**####  end-hjs-addStatisticsEvent  ##**/
                finish();
            }
        });
    }

    private void initShopAdapter() {
      /*  LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        rvDianpu.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new CommomRecyclerAdapter(this, shopBeanData, R.layout.recy_dianpu, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {
                TextView tv_dianpu_name = holder.getView(R.id.tv_dianpu_name);
                TextView tv_dianpu_address = holder.getView(R.id.tv_dianpu_address);
                TextView tv_dianpu_time = holder.getView(R.id.tv_dianpu_time);
//                tv_dianpu_name.setText(((ShopBean) o).getShop_name());
//                tv_dianpu_address.setText(((ShopBean) o).getShop_address());
//                tv_dianpu_time.setText(((ShopBean) o).getShop_time());

            }
        };
        rvDianpu.setAdapter(adapter);
*/

    }

    private void initRememAdapter() {
        MyGridLayoutManager myGridLayoutManager = new MyGridLayoutManager(this, 2);
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
        Intent intent = getIntent();
        int couponId = intent.getIntExtra("couponId", -1);
        Log.e("couponId",couponId+"==");
      /*  for (int i = 0; i < 4; i++) {
            shopList.add(new ShopBean("松本清（银座5th店）", "地址：中英区银座5-5-1", "营业时间:10:00-22:00"));
            remenList.add(new RemenBean(img_url, "安耐晒", "防水", "性价比高"));
        }
        adapter.updateData(shopList);
        adapter1.updateData(remenList);*/

        Map<String, String> map = new HashMap<>();
        if (TextUtils.equals(Constans.PRO_VERSION,"L")){
            map.put("leaseId",Constans.PRO_DEV_OID);
        }
        map.put("CouponId", couponId + "");
        OkGoUtil.<ShopBean>get(DetailsActivity.this, Constans.METHOD_GETONECOUPONDETIAL, map, ShopBean.class, this);
    }

    private void initAdapter() {

//        initShopAdapter();
//        initRememAdapter();


    }




    @Override
    public void onSuccess(String method, BaseBean baseBean, String s1) {
        switch (method) {
            case Constans.METHOD_GETONECOUPONDETIAL:
                shopBean = (ShopBean) baseBean;
                shopBeanData = shopBean.getData();
                tvZhekou.setText(shopBeanData.getTitle()+shopBeanData.getYouhui());


                Glide.with(DetailsActivity.this)
                        .load((shopBeanData).getImage())
                        .into(ivImg);
//                tvZhekou.setText(shopBeanData.getYouhui());
//                tvXiangqing.setText(shopBeanData.getTiaojianshort());
                Glide.with(DetailsActivity.this)
                        .load((shopBeanData).getQrimage())
                        .into(ivTiaoxinma);
                tvYouhuiTime.setText((shopBeanData).getTime());
                tvYouhuiZhengce.setText((shopBeanData).getTiaojian());

                Glide.with(DetailsActivity.this)
                        .load((shopBeanData).getBasicimage())
                        .into(ivYuantu);
                Log.e("imgurl",(shopBeanData).getBasicimage());

                ivYuantu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /**####  start-hjs-addStatisticsEvent   ##**/
                        try {
                            HashMap<String,Serializable> map = new HashMap<>();
                            map.put("title",shopBeanData.getTitle());
                            addStatisticsEvent("coupon_Details4", map);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        /**####  end-hjs-addStatisticsEvent  ##**/

                        Intent intent = new Intent(DetailsActivity.this,PhotoViewActivity.class);
                        intent.putExtra("photo_img",shopBeanData.getBasicimage());
                        startActivity(intent);
                    }
                });
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
