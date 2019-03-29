package com.aibabel.fyt_exitandentry.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.baselibrary.base.StatisticsBaseActivity;
import com.aibabel.fyt_exitandentry.R;
import com.aibabel.fyt_exitandentry.activity.PhotoViewActivity;
import com.aibabel.fyt_exitandentry.activity.PhotoViewActivity2;
import com.aibabel.fyt_exitandentry.adapter.CommomRecyclerAdapter;
import com.aibabel.fyt_exitandentry.adapter.CommonRecyclerViewHolder;
import com.aibabel.fyt_exitandentry.bean.AirportBean;
import com.aibabel.fyt_exitandentry.bean.CityAirplaneBean;
import com.aibabel.fyt_exitandentry.utils.WrapContentHeightViewPager;
import com.alibaba.mtl.appmonitor.Transaction;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class AirportFragment extends BaseFragment {

    @BindView(R.id.tv_rujingka)
    TextView tvRujingka;
    @BindView(R.id.iv_rujingka)
    ImageView ivRujingka;
    @BindView(R.id.rujinka_ll)
    LinearLayout rujinkaLl;
    @BindView(R.id.tv_airport)
    TextView tvAirport;
    @BindView(R.id.lv_airport)
    RecyclerView lvAirport;
    @BindView(R.id.tv_traffic)
    TextView tvTraffic;
    @BindView(R.id.tv_traffic_title)
    TextView tvTrafficTitle;

    @BindView(R.id.ll_traffic)
    LinearLayout llTraffic;
    @BindView(R.id.ll_airport)
    LinearLayout llAirport;
    private CommomRecyclerAdapter adapter;
    private List<AirportBean> airportImgList = null;
    private CityAirplaneBean.DataBean dataBean;
    private Bitmap bitmap;
    private SimpleTarget<Bitmap> simpleTarget;
    private ImageView ivAirportItem;
    private String str1;
    private String str2;

    @SuppressLint("ValidFragment")
    public AirportFragment(CityAirplaneBean.DataBean dataBean) {
        this.dataBean = dataBean;

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_airport;

    }

    @Override
    public void init(View view, Bundle bundle) {

        initData(dataBean);
        initOnClickListeren();
    }


    private void initOnClickListeren() {
        ivRujingka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
                intent.putExtra("img", str1);
                ivRujingka.post(new Runnable() {
                    @Override
                    public void run() {
                        int width = ivRujingka.getWidth();
                        int height = ivRujingka.getHeight();
                        if (width > height) {
                            startActivity(intent);
                        } else {
                            Intent intent2 = new Intent(getActivity(), PhotoViewActivity2.class);
                            intent2.putExtra("img", str1);
                            startActivity(intent2);
                        }
                    }
                });
                startActivity(intent);

            }
        });
    }


    private void initData(CityAirplaneBean.DataBean dataBean) {

//        Picasso.with(getActivity()).load(dataBean.getEntryCard()).placeholder(R.mipmap.jichangjiazaizhong).error(R.mipmap.jichangjiazaishibai).into(ivRujingka);
        String entryCard_str = dataBean.getEntryCard();
        if (entryCard_str.contains("&&&")) {
            str1 = entryCard_str.substring(0, entryCard_str.indexOf("&&&"));
//            Picasso.with(getActivity()).load(str1).placeholder(R.mipmap.jichangjiazaizhong).error(R.mipmap.jichangjiazaishibai).into(ivRujingka);
            RequestOptions options = new RequestOptions().placeholder(R.mipmap.jichangjiazaizhong).error(R.mipmap.jichangjiazaishibai);
//            Glide.with(getActivity()).load(str1).apply(options).into(ivRujingka);
            Picasso.with(getActivity()).load(str1).config(Bitmap.Config.RGB_565).placeholder(R.mipmap.jichangjiazaizhong).error(R.mipmap.jichangjiazaishibai).into(ivRujingka);
            str2 = entryCard_str.substring(entryCard_str.indexOf("&&&") + 3, entryCard_str.length());

            ImageView imageView3 = new ImageView(getContext());
            imageView3.setAdjustViewBounds(true);
            imageView3.setMaxHeight(959);
            imageView3.setMaxWidth(500);
            Picasso.with(getActivity()).load(str2).config(Bitmap.Config.RGB_565).placeholder(R.mipmap.jichangjiazaizhong).error(R.mipmap.jichangjiazaishibai).into(imageView3);
//            Glide.with(getActivity()).load(str2).apply(options).into(imageView3);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,20,0,10);//4个参数按顺序分别是左
            imageView3.setLayoutParams(params);

            rujinkaLl.addView(imageView3);


            imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
                    intent.putExtra("img", str2);
                    addPhotoEventClick(str1);
                    if (str2 != null && !TextUtils.equals(str2, "")) {
                        imageView3.post(new Runnable() {
                            @Override
                            public void run() {
                                int width = imageView3.getWidth();
                                int height = imageView3.getHeight();
                                if (width > height) {
                                    startActivity(intent);
                                } else {
                                    Intent intent2 = new Intent(getActivity(), PhotoViewActivity2.class);
                                    intent2.putExtra("img", str2);
                                    startActivity(intent2);
                                }
                            }
                        });

                    }
                }
            });
        } else {
//            Picasso.with(getActivity()).load(dataBean.getEntryCard()).placeholder(R.mipmap.jichangjiazaizhong).error(R.mipmap.jichangjiazaishibai).into(ivRujingka);
            RequestOptions options = new RequestOptions().placeholder(R.mipmap.jichangjiazaizhong).error(R.mipmap.jichangjiazaishibai);

            Glide.with(getActivity()).load(dataBean.getEntryCard()).apply(options).into(ivRujingka);
        }


        List<String> airportMapArr = dataBean.getAirportMapArr();
        for (int i = 0; i < airportMapArr.size(); i++) {
            airportImgList = new ArrayList<>();
            if (airportMapArr.get(i).contains("标题&&&")) {
                String airportName = airportMapArr.get(i).replace("标题&&&", "");
                TextView textView1 = new TextView(getContext());
                textView1.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24);
                textView1.setTextColor(getResources().getColorStateList(R.color.textView_color));
                textView1.setLineSpacing(18, 1);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(20,50,0,10);//4个参数按顺序分别是左
                textView1.setLayoutParams(params);
                textView1.setGravity(Gravity.CENTER_VERTICAL);
                llAirport.addView(textView1);
                textView1.setText(airportName);

            }

            if (airportMapArr.get(i).contains("内容###")) {
                String airportName = airportMapArr.get(i).replace("内容###", "");
                TextView textView1 = new TextView(getContext());
                textView1.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24);
                textView1.setTextColor(getResources().getColorStateList(R.color.textView_color));
                textView1.setLineSpacing(18, 1);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,20,0,30);//4个参数按顺序分别是左
                textView1.setLayoutParams(params);
                textView1.setGravity(Gravity.CENTER);
                llAirport.addView(textView1);
                textView1.setText(airportName);

            }

            if (airportMapArr.get(i).contains("图片&&&")) {
                String airportImg = airportMapArr.get(i).replace("图片&&&", "");

                ImageView imageView2 = new ImageView(getContext());
                imageView2.setAdjustViewBounds(true);
                imageView2.setMaxHeight(959);
                imageView2.setMaxWidth(500);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(20,0,20,10);//4个参数按顺序分别是左
                imageView2.setLayoutParams(params);
                llAirport.addView(imageView2);
                RequestOptions options = new RequestOptions().placeholder(R.mipmap.jichangjiazaizhong).error(R.mipmap.jichangjiazaishibai);
                Picasso.with(getActivity()).load(airportImg).config(Bitmap.Config.RGB_565).placeholder(R.mipmap.jichangjiazaizhong).error(R.mipmap.jichangjiazaishibai).into(imageView2);
//                Glide.with(getActivity()).load(airportImg).apply(options).into(imageView2);
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
                        intent.putExtra("img", airportImg);
                        addPhotoEventClick(airportImg);
                        if (airportImg != null && !TextUtils.equals(airportImg, "")) {
                            imageView2.post(new Runnable() {
                                @Override
                                public void run() {
                                    int width = imageView2.getWidth();
                                    int height = imageView2.getHeight();
                                    if (width > height) {
                                        startActivity(intent);
                                    } else {
                                        Intent intent2 = new Intent(getActivity(), PhotoViewActivity2.class);
                                        intent2.putExtra("img", airportImg);
                                        startActivity(intent2);
                                    }
                                }
                            });
                        }
                    }
                });

            }
        }


        List<String> airportTrafficArr = dataBean.getAirportTrafficArr();
        for (int i = 0; i < airportTrafficArr.size(); i++) {
            if (airportTrafficArr.get(i).contains("标题&&&")) {
                String airportName = airportTrafficArr.get(i).replace("标题&&&", "");
                TextView textView1 = new TextView(getContext());
                textView1.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24);
                textView1.setTextColor(getResources().getColorStateList(R.color.textView_color));
                textView1.setLineSpacing(14, 1);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,50,10,30);//4个参数按顺序分别是左
                textView1.setLayoutParams(params);
//                textView1.setGravity(Gravity.);
//                textView1.setGravity(Gravity.CENTER_VERTICAL);
                llTraffic.addView(textView1);
                textView1.setText(airportName);
            }

            if (airportTrafficArr.get(i).contains("内容###")) {
                String airportName = airportTrafficArr.get(i).replace("内容###", "");
                TextView textView2 = new TextView(getContext());
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24);
                textView2.setTextColor(getResources().getColorStateList(R.color.textView_color));
                textView2.setLineSpacing(18, 1);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,20,0,30);//4个参数按顺序分别是左
                textView2.setLayoutParams(params);
                textView2.setGravity(Gravity.CENTER);
                llTraffic.addView(textView2);
                textView2.setText(airportName);

            }
            if (airportTrafficArr.get(i).contains("图片&&&")) {
                String airportImg = airportTrafficArr.get(i).replace("图片&&&", "");
                ImageView imageView1 = new ImageView(getContext());
                imageView1.setAdjustViewBounds(true);
                imageView1.setMaxHeight(959);
                imageView1.setMaxWidth(500);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,20,0,10);//4个参数按顺序分别是左
                imageView1.setLayoutParams(params);
                llTraffic.addView(imageView1);
                RequestOptions options = new RequestOptions().placeholder(R.mipmap.jichangjiazaizhong).error(R.mipmap.jichangjiazaishibai);
                Picasso.with(getActivity()).load(airportImg).config(Bitmap.Config.RGB_565).placeholder(R.mipmap.jichangjiazaizhong).error(R.mipmap.jichangjiazaishibai).into(imageView1);
//                Glide.with(getActivity()).load(airportImg).apply(options).into(imageView1);
                imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
                        intent.putExtra("img", airportImg);
                        addPhotoEventClick(airportImg);
                        if (airportImg != null && !TextUtils.equals(airportImg, "")) {
                            imageView1.post(new Runnable() {
                                @Override
                                public void run() {
                                    int width = imageView1.getWidth();
                                    int height = imageView1.getHeight();
                                    if (width > height) {
                                        startActivity(intent);
                                    } else {
                                        Intent intent2 = new Intent(getActivity(), PhotoViewActivity2.class);
                                        intent2.putExtra("img", airportImg);
                                        startActivity(intent2);
                                    }
                                }
                            });
                        }
                    }
                });
            }
            if (!airportTrafficArr.get(i).contains("标题&&&") && (!airportTrafficArr.get(i).contains("图片&&&")) && (!airportTrafficArr.get(i).contains("内容###"))) {
                TextView textView1 = new TextView(getContext());
                textView1.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24);
                textView1.setLineSpacing(12, 1);
                textView1.setTextColor(getResources().getColorStateList(R.color.textView_color));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,30,0,30);//4个参数按顺序分别是左
                textView1.setLayoutParams(params);
                llTraffic.addView(textView1);
                textView1.setText(airportTrafficArr.get(i));
            }
        }
//        airportImgList.add(R.mipmap.jichang1);
//        airportImgList.add(R.mipmap.jichang2);
//        adapter.updateData(airportImgList);


    }
    private void addPhotoEventClick(String name){

        StatisticsBaseActivity statisticsBaseActivity= (StatisticsBaseActivity) getActivity();
        if (statisticsBaseActivity!=null){
            HashMap<String, Serializable> map=new HashMap<>();
            map.put("fyt_exitandentry_main4_def",name);
            statisticsBaseActivity.addStatisticsEvent("fyt_exitandentry_main4",map);
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
