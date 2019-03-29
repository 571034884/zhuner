package com.aibabel.menu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.baselibrary.mode.StatisticsManager;
import com.aibabel.menu.MenuActivity;
import com.aibabel.menu.R;
import com.aibabel.menu.util.AppStatusUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * nt.
 */
public class MenuOneFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.one_frag_1)
    LinearLayout oneFrag1;
    @BindView(R.id.one_frag_2)
    LinearLayout oneFrag2;
    @BindView(R.id.one_frag_3)
    LinearLayout oneFrag3;
    @BindView(R.id.one_frag_4)
    LinearLayout oneFrag4;
    @BindView(R.id.one_frag_5)
    LinearLayout oneFrag5;
    Unbinder unbinder;

    public MenuOneFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_menu_one;
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
        oneFrag1.setOnClickListener(this);
        oneFrag2.setOnClickListener(this);
        oneFrag3.setOnClickListener(this);
        oneFrag4.setOnClickListener(this);
        oneFrag5.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        try {
            switch (view.getId()) {
                case R.id.one_frag_1:

                    //调起打折券
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(getContext(), "com.aibabel.coupon"));
                    try{
                        ((MenuActivity) getActivity()).addStatisticsEvent("menu_coupon_click",null);
                    }catch (Exception e){}
                    break;
                case R.id.one_frag_2:
                    //调起美食
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(getContext(),"com.aibabel.food"));
                    try{
                        ((MenuActivity) getActivity()).addStatisticsEvent("menu_areaselect_click",null);
                    }catch (Exception e){}
                    break;
                case R.id.one_frag_3:
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(getContext(), "com.aibabel.fyt_play"));
                    try{
                        ((MenuActivity) getActivity()).addStatisticsEvent("menu_fun_click",null);
                    }catch (Exception e){}
                    break;
                case R.id.one_frag_4:
                    //调起出入境
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(getContext(),"com.aibabel.fyt_exitandentry"));
                    try{
                        ((MenuActivity) getActivity()).addStatisticsEvent("menu_choice_click",null);
                    }catch (Exception e){}
                    break;
                case R.id.one_frag_5:
                    //调起应用说明书
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(getContext(),"com.aibabel.readme"));
                    try{
                        ((MenuActivity) getActivity()).addStatisticsEvent("menu_readme_click",null);
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
