package com.aibabel.fyt_play.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.fyt_play.R;
import com.aibabel.fyt_play.adapter.CommomRecyclerAdapter;
import com.aibabel.fyt_play.adapter.CommonRecyclerViewHolder;
import com.aibabel.fyt_play.bean.Constans;
import com.aibabel.fyt_play.bean.H5Bean;
import com.aibabel.fyt_play.bean.OrderBean;
import com.aibabel.fyt_play.bean.PlayBean;
import com.aibabel.fyt_play.bean.PlayItemBean;
import com.aibabel.fyt_play.utils.ContentProviderUtil;
import com.aibabel.fyt_play.utils.CornerTransform;
import com.aibabel.fyt_play.utils.CustomRoundAngleImageView;
import com.aibabel.fyt_play.utils.NetUtil;
import com.aibabel.fyt_play.utils.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zhouyou.recyclerview.XRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener, BaseCallback<BaseBean> {

    @BindView(R.id.tv_country_name)
    TextView tvCountryName;
    @BindView(R.id.tv_city_name)
    TextView tvCityName;
    @BindView(R.id.choice_city_ll_layout)
    LinearLayout choiceCityLlLayout;
    @BindView(R.id.country_img_cl_layout)
    RelativeLayout countryImgClLayout;
    @BindView(R.id.rv_play)
    XRecyclerView rvPlay;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.btn_my_order)
    Button btnMyOrder;


    private List<PlayBean> playBeanList = new ArrayList<>();
    private List<PlayItemBean> playItemBeanList = new ArrayList<>();
    private TextView tvTitle;
    private RecyclerView rvItem;
    private TextView tvItemContext;
    private TextView tvItemTitle;
    private ImageView iv_item;
    /**
     * 展开状态下toolbar显示的内容
     */
    private View toolbarOpen;
    /**
     * 收缩状态下toolbar显示的内容
     */
    private View toolbarClose;
    private String country;
    private String city;
    private PlayBean playBean;
    private PlayBean.DataBean playBeanData;
    private List<PlayBean.DataBean.HomePageMsgBean> playBeanDataHomePageMsg = new ArrayList<>();
    private ImageView ivIcon;
    private CommomRecyclerAdapter adapter1;

    private CommomRecyclerAdapter adapter;

    private TextView tvCityName1;
    private TextView tvCountryName1;
    private LinearLayout choiceCityLlLayout1;
    private final String DATABASE_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/dictionary";
    private final String DATABASE_FILENAME = "dictionary.db3";
    private SQLiteDatabase database;
    private String dev_d;
    private String pageIndex;
    private String orderDetials;
    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_home_page;
    }

    private final Uri CONTENT_URI = Uri.parse("content://com.dommy.qrcode/aibabel_information");
    String dev_oid, dev_uid, dev_uname, dev_sn;

    @Override
    public void init() {
        Constans.HOST_XW = ContentProviderUtil.getHost(this);
        Constans.CITY = ContentProviderUtil.getCity(this);
        Constans.COUNTRY = ContentProviderUtil.getCountry(this);


        /**####  start-hjs-addStatisticsEvent   ##**/
        try {
            HashMap<String, Serializable> add_hp = new HashMap<>();
            add_hp.put("fyt_play_main1_def",  Constans.CITY);
            addStatisticsEvent("fyt_play_main1", add_hp);
        }catch (Exception e){
            e.printStackTrace();
        }
        /**####  end-hjs-addStatisticsEvent  ##**/

        toolbarOpen = findViewById(R.id.include_toolbar_open);
        toolbarClose = findViewById(R.id.include_toolbar_close);
        choiceCityLlLayout1 = toolbarClose.findViewById(R.id.choice_city_ll_layout);
        tvCityName1 = toolbarClose.findViewById(R.id.tv_city_name);
        tvCountryName1 = toolbarClose.findViewById(R.id.tv_country_name);
        appBar.addOnOffsetChangedListener(this);

        setPathParams("目的地");

        initDictionary();
        if (NetUtil.isNetworkAvailable(this)) {
            initData();
        } else {
            ToastUtil.showShort(this, "当前无网络");
            finish();
        }
//        initData();
//        initDictionary();
        initAdapterPlay();
        choiceCityLlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChoiceCityActivity.class);
                startActivityForResult(intent, 10);
            }
        });
        choiceCityLlLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChoiceCityActivity.class);
                startActivityForResult(intent, 10);
            }
        });

        rvPlay.setPullRefreshEnabled(false);
        setHotRepairEnable(true );
        btnMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, MyWebActivity.class);
                intent.putExtra("leaseNo", dev_oid);
                intent.putExtra("url", orderDetials);
                startActivityForResult(intent, 20);
            }
        });


    }

    private void initDictionary() {
        try {
            Cursor cursor = MainActivity.this.getContentResolver().query(CONTENT_URI, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    dev_oid = cursor.getString(cursor.getColumnIndex("oid"));
                    Log.e("dev_oid", dev_oid);
                    dev_uid = cursor.getString(cursor.getColumnIndex("uid"));
                    dev_uname = cursor.getString(cursor.getColumnIndex("uname"));
                    dev_sn = cursor.getString(cursor.getColumnIndex("sn"));
                    dev_d = cursor.getString(cursor.getColumnIndex("d"));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("wzf", "tianxia=" + e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            Constans.CITY = data.getStringExtra("city");
            Constans.COUNTRY = data.getStringExtra("country");


            tvCityName.setText(Constans.CITY );
            Map<String, String> map = new HashMap<>();
            map.put("country", Constans.COUNTRY);
            map.put("city", Constans.CITY );
            OkGoUtil.<PlayBean>get(this, Constans.METHOD_GETPLAYHOMEPAGE, map, PlayBean.class, this);
        }
        if (requestCode == 20 && resultCode == RESULT_OK) {
//            dev_oid = "14011105947001";
//            dev_oid = "14011000739301";
            Map<String, String> map1 = new HashMap<>();
            map1.put("leaseId", dev_oid);
            map1.put("page", "1");
            map1.put("pageSize", "1");
            OkGoUtil.<OrderBean>get(this, Constans.METHOD_GETPLAYORDERMSG, map1, OrderBean.class, this);
        }

    }


    private void initData() {
        if (TextUtils.equals(Constans.CITY,"")&&TextUtils.equals(Constans.COUNTRY,"")){
            Constans.ISCOUNTRY = false ;
            Constans.CITY = "东京";
            Constans.COUNTRY = "日本 ";
        }
        if (TextUtils.equals(Constans.COUNTRY,"日本")){
            Constans.ISCOUNTRY = true ;
            Constans.CITY = "东京";
        }else if (TextUtils.equals(Constans.COUNTRY,"泰国")){
            Constans.ISCOUNTRY = true ;
            Constans.CITY = "曼谷";
        }else {
            Constans.ISCOUNTRY = false ;
            Constans.CITY = "东京";
            Constans.COUNTRY = "日本 ";
        }
//        city = "东京";
        Map<String, String> map = new HashMap<>();
        map.put("country", Constans.COUNTRY );
        map.put("city", Constans.CITY);
        OkGoUtil.<PlayBean>get(this, Constans.METHOD_GETPLAYHOMEPAGE, map, PlayBean.class, this);

//         dev_oid = "14011105947001";
//         dev_oid = "14011000739301";

        Map<String, String> map1 = new HashMap<>();
//        dev_oid = "14012505188301";
        map1.put("leaseId", dev_oid);
        map1.put("page", "1");
        map1.put("pageSize", "1");
        OkGoUtil.<OrderBean>get(this, Constans.METHOD_GETPLAYORDERMSG, map1, OrderBean.class, this);


        Map<String, String> map2 = new HashMap<>();
        OkGoUtil.<H5Bean>get(this, Constans.METHOD_GETPLAYH5FORMAT, map2, H5Bean.class, this);
    }

    private void initAdapterPlayItem(List<PlayBean.DataBean.HomePageMsgBean.DetialBean> playItemBeanList,CommomRecyclerAdapter adapter1,int type) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        //设置布局管理器
        rvItem.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);

            adapter1 = new CommomRecyclerAdapter(MainActivity.this, playItemBeanList, R.layout.rv_play_item_item, new CommomRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(CommonRecyclerViewHolder holder, final int postion) {
                    /**####  start-hjs-addStatisticsEvent   ##**/
                    try {
                        HashMap<String, Serializable> add_hp = new HashMap<>();
                        String titel = tvTitle.getText().toString();
                        Log.e("hjs", "type==" + type);
                        if (type==0) {
                            add_hp.put("fyt_play_main2_def", playItemBeanList.get(postion).getTitle());
                            addStatisticsEvent("fyt_play_main2", add_hp);
                        } else if (type==1) {
                            add_hp.put("fyt_play_main4_def", playItemBeanList.get(postion).getTitle());
                            addStatisticsEvent("fyt_play_main4", add_hp);
                        }
                        if (type==2) {
                            add_hp.put("fyt_play_main6_def", playItemBeanList.get(postion).getTitle());
                            addStatisticsEvent("fyt_play_main6", add_hp);
                        }
                    } catch (Exception e) {
                    }
                    /**####  end-hjs-addStatisticsEvent  ##**/

                    Intent intent = new Intent(MainActivity.this, WebActivity.class);
                    intent.putExtra("city", tvCityName.getText().toString());
                    intent.putExtra("country", tvCountryName.getText().toString());
                    intent.putExtra("title", playItemBeanList.get(postion).getTitle());
                    intent.putExtra("url", pageIndex);
                    intent.putExtra("leaseNo", dev_oid);
                    startActivity(intent);
                }
            }, null) {
                @Override
                public void convert(CommonRecyclerViewHolder viewHolder, Object o, final int position) {
                    tvItemTitle = viewHolder.getView(R.id.tv_item_title);
                    tvItemContext = viewHolder.getView(R.id.tv_item_context);
                    iv_item = viewHolder.getView(R.id.iv_item);
                    View view1 = viewHolder.getView(R.id.view1);
                    View view2 = viewHolder.getView(R.id.view2);

                    tvItemTitle.setText(playItemBeanList.get(position).getTitle());
//                tvItemTitle.setBackgroundColor(Color.parseColor("#fff"));  //背景透明度
                    tvItemContext.setText(playItemBeanList.get(position).getContext());
                    if (position == playItemBeanList.size() - 1) {
                        view1.setVisibility(View.VISIBLE);
                    }
                    if (position == 0) {
                        view2.setVisibility(View.VISIBLE);
                    } else {
                        view2.setVisibility(View.GONE);
                    }
                    CornerTransform transformation = new CornerTransform(MainActivity.this, 30);
                    //只是绘制左上角和右上角圆角
                    transformation.setExceptCorner(false, false, true, true);
                    RequestOptions options = new RequestOptions().transform(transformation).placeholder(R.mipmap.jiazaizhong1).error(R.mipmap.jiazai1);
                    Glide.with(MainActivity.this)
                            .load(playItemBeanList.get(position).getImage())
                            .apply(options)
                            .thumbnail(loadTransform(MainActivity.this, R.mipmap.jiazaizhong1, 30))
                            .thumbnail(loadTransform(MainActivity.this, R.mipmap.jiazai1, 30))
                            .into(iv_item);

                }
            };

        rvItem.setAdapter(adapter1);
    }




    private static RequestBuilder<Drawable> loadTransform(Context context, @DrawableRes int placeholderId, float radius) {

        return Glide.with(context)
                .load(placeholderId)
                .apply(new RequestOptions().centerCrop().transform(new CornerTransform(context, radius)));
    }

    private void initAdapterPlay() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        //设置布局管理器
        rvPlay.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        adapter = new CommomRecyclerAdapter(MainActivity.this, playBeanDataHomePageMsg, R.layout.rv_play_item, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, final int postion) {
//                Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
//                intent.putExtra("img", airportImgList.get(postion));
//                startActivity(intent);

            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder viewHolder, Object o, final int position) {
                tvTitle = viewHolder.getView(R.id.tv_title);
                rvItem = viewHolder.getView(R.id.rv_item);
                ivIcon = viewHolder.getView(R.id.iv_icon);
                View view3 = viewHolder.getView(R.id.view3);

                rvItem.setNestedScrollingEnabled(false);
                if (position == 0) {
                    Glide.with(MainActivity.this).load(R.mipmap.icon_biwan).into(ivIcon);
                    view3.setVisibility(View.VISIBLE);
                    tvTitle.setText("必玩必备");
                    CommomRecyclerAdapter adapter0 =null;
                    initAdapterPlayItem(playBeanDataHomePageMsg.get(0).getDetial(),adapter0,0);
                } else if (position == 1) {
                    Glide.with(MainActivity.this).load(R.mipmap.icon_youxiantiyan).into(ivIcon);
                    tvTitle.setText("优选体验");
                    CommomRecyclerAdapter adapter1 = null;
                    initAdapterPlayItem(playBeanDataHomePageMsg.get(2).getDetial(),    adapter1,1);
                } else if (position == 2) {
                    Glide.with(MainActivity.this).load(R.mipmap.icon_zhoubiantuijian).into(ivIcon);
                    tvTitle.setText("周边推荐");
                    CommomRecyclerAdapter adapter2 = null;
                    initAdapterPlayItem(playBeanDataHomePageMsg.get(1).getDetial(),adapter2,2);
                }
            }
        };
        rvPlay.setAdapter(adapter);
    }



    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int scrollRange = appBarLayout.getTotalScrollRange();
        if (offset <= scrollRange / 2) {//当滑动没超过一半，展开状态下toolbar显示内容，根据收缩位置，改变透明值
            toolbarOpen.setVisibility(View.VISIBLE);
            toolbarClose.setVisibility(View.GONE);
            //根据偏移百分比 计算透明值
            float scale2 = (float) offset / (scrollRange / 2);
            int alpha2 = (int) (255 * scale2);
            toolbarOpen.setBackgroundColor(Color.argb(alpha2, 255, 255, 255));
        } else {//当滑动超过一半，收缩状态下toolbar显示内容，根据收缩位置，改变透明值
            toolbarClose.setVisibility(View.VISIBLE);
            toolbarOpen.setVisibility(View.GONE);
            float scale3 = (float) (scrollRange - offset) / (scrollRange / 2);
            int alpha3 = (int) (255 * scale3);
            toolbarClose.setBackgroundColor(Color.rgb(255, 255, 255));
        }
    }

    @Override
    public void onSuccess(String method, BaseBean baseBean, String s1) {
        Log.e("s1", s1);
        switch (method) {
            case Constans.METHOD_GETPLAYHOMEPAGE:
                playBean = (PlayBean) baseBean;
                //包含国家 城市 图片
                playBeanData = playBean.getData();
                // 包含 type icon
                playBeanDataHomePageMsg = playBeanData.getHomePageMsg();
                adapter.updateData(playBeanDataHomePageMsg);

                tvCityName.setText(playBeanData.getArea());
                tvCountryName.setText(playBeanData.getCountry());
                tvCountryName1.setText(playBeanData.getCountry());
                tvCityName1.setText(playBeanData.getArea());
                SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        countryImgClLayout.setBackground(resource);
                    }
                };
                RequestOptions requestOptions = new RequestOptions().placeholder(R.mipmap.wanleshouye_jiazhaizhong).error(R.mipmap.wanleshouye_jiazaishibai);
                Glide.with(MainActivity.this).load(playBeanData.getCitybanner()).apply(requestOptions).into(simpleTarget);

                rvPlay.scrollToPosition(0);
//                CoordinatorLayout.Behavior behavior =
//                        ((CoordinatorLayout.LayoutParams) appBar.getLayoutParams()).getBehavior();
//                if (behavior instanceof AppBarLayout.Behavior) {
//                    AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
//                    int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
//                    if (topAndBottomOffset != 0) {
//                        appBarLayoutBehavior.setTopAndBottomOffset(0);
//                    }
//                }
                break;
            case Constans.METHOD_GETPLAYORDERMSG:
                OrderBean orderBean = (OrderBean) baseBean;
                List<OrderBean.DataBean> data = orderBean.getData();
                if (data.size() == 0) {
                    btnMyOrder.setVisibility(View.GONE);
                } else {
                    btnMyOrder.setVisibility(View.VISIBLE);
                }
                break;

            case Constans.METHOD_GETPLAYH5FORMAT:
                H5Bean h5Bean = (H5Bean) baseBean;
                H5Bean.DataBean h5BeanData = h5Bean.getData();
                pageIndex = h5BeanData.getPageIndex();
                orderDetials = h5BeanData.getOrderDetials();
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
