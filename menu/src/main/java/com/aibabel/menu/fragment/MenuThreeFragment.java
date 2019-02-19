package com.aibabel.menu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.menu.R;
import com.aibabel.menu.util.AppStatusUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * create an instance of this fragment.
 */
public class MenuThreeFragment extends BaseFragment implements View.OnClickListener {


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

    public MenuThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_menu_three;
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
                    //调起   全球上网
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(getContext(),"com.aibabel.surfinternet"));
                    break;
                case R.id.two_frag_2:
                    //调起汇率
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(getContext(), "com.aibabel.currencyconversion"));
                    break;
                case R.id.two_frag_3:
                    //调起天气
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(getContext(),"com.aibabel.weather"));

                    break;
                case R.id.two_frag_4:
                    //调起世界钟
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(getContext(),"com.aibabel.alliedclock"));


                    break;
                case R.id.two_frag_5:
                    //调起离线管理
                    startActivity(AppStatusUtils.getAppOpenIntentByPackageName(getContext(), "com.aibabel.download.offline"));

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
