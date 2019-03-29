package com.aibabel.scenic.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fytworks on 2019/3/26.
 */

public class FragmentAdapter extends FragmentPagerAdapter{

    private FragmentManager fragmentManager;
    private List<Fragment> list;
    private List<String> title;

    public FragmentAdapter(FragmentManager fm,List<Fragment> list,List<String> title) {
        super(fm);
        this.fragmentManager = fm;
        this.list = list;
        this.title = title;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
