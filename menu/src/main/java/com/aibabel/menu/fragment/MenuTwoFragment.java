package com.aibabel.menu.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.menu.MenuActivity;
import com.aibabel.menu.R;
import com.aibabel.menu.util.AppStatusUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * create an instance of this fragment.
 */
public class MenuTwoFragment extends BaseFragment implements View.OnClickListener {


    Unbinder unbinder;
    @BindView(R.id.two_frag_1)
    LinearLayout twoFrag1;
    @BindView(R.id.two_frag_2)
    LinearLayout twoFrag2;
    @BindView(R.id.two_frag_3)
    LinearLayout twoFrag3;
    @BindView(R.id.two_frag_4)
    LinearLayout twoFrag4;
    @BindView(R.id.two_frag_5)
    LinearLayout twoFrag5;

    public MenuTwoFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_menu_two;
    }

    @Override
    public void init(View view, Bundle bundle) {
        setListener();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void setListener() {
        twoFrag1.setOnClickListener(this);
        twoFrag2.setOnClickListener(this);
        twoFrag3.setOnClickListener(this);
        twoFrag4.setOnClickListener(this);
        twoFrag5.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.two_frag_1:
                    //调起地图
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(getContext(),"com.aibabel.map"));
                    try{
                        ((MenuActivity) getActivity()).addStatisticsEvent("menu_map_click",null);
                    }catch (Exception e){}
                    break;
                case R.id.two_frag_2:
                    //调起 订单
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    ComponentName cn = new ComponentName("com.aibabel.fyt_play", "com.aibabel.fyt_play.activity.MyWebActivity");
                    intent.setComponent(cn);
                    intent.putExtra("from","menu");
                    startActivity(intent);
                    try{
                        ((MenuActivity) getActivity()).addStatisticsEvent("menu_order_click",null);
                    }catch (Exception e){}
                    break;
                case R.id.two_frag_3:
                    //调起搜索

                     intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                     cn = new ComponentName("com.google.android.googlequicksearchbox", "com.google.android.apps.gsa.queryentry.QueryEntryActivity");
                    intent.setComponent(cn);
                    startActivity(intent);
                    try{
                        ((MenuActivity) getActivity()).addStatisticsEvent("menu_search_click",null);
                    }catch (Exception e){}
                    break;
                case R.id.two_frag_4:
                    //调起吐槽
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(getContext(),"com.aibabel.tucao"));

                    try{
                        ((MenuActivity) getActivity()).addStatisticsEvent("menu_complaints_click",null);
                    }catch (Exception e){}
                    break;
                case R.id.two_frag_5:
                    //调起设置
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(getContext(), "com.zhuner.administrator.settings"));
                    try{
                        ((MenuActivity) getActivity()).addStatisticsEvent("menu_settings_click",null);
                    }catch (Exception e){}
                    break;


            }

        } catch (Exception e) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
