package com.aibabel.download.offline.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class LanPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private List<String> titleList;
    public LanPagerAdapter(FragmentManager fm,List<Fragment> list_frag,List<String> title_List) {
        super(fm);
        list=list_frag;
        titleList=title_List;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }



    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
