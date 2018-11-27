package com.aibabel.weather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * 作者：SunSH on 2018/5/25 13:58
 * 功能：
 * 版本：1.0
 */
public class WeatherAdapter extends BaseFragmentPagerAdapter {

    List<Fragment> list;

    public WeatherAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void notifyDataSetChanged() {
        tags.clear();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        Fragment fragment = (Fragment) object;
        //如果Item对应的Tag存在，则不进行刷新
        if (tags.indexOfValue(fragment.getTag()) > -1) {
            return super.getItemPosition(object);
        }
        return POSITION_NONE;
    }
}
//public class WeatherAdapter extends FragmentPagerAdapter {
//
//    List<Fragment> list;
//    private int mChildCount = 0;
//
//    public WeatherAdapter(FragmentManager fm) {
//        super(fm);
//    }
//
//    public WeatherAdapter(FragmentManager fm, List<Fragment> list) {
//        super(fm);
//        this.list = list;
//    }//写构造方法，方便赋值调用
//
//    @Override
//    public Fragment getItem(int arg0) {
//        return list.get(arg0);
//    }//根据Item的位置返回对应位置的Fragment，绑定item和Fragment
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }//设置Item的数量
//
//    /**
//     * 该方法是强制重绘页面
//     */
//    @Override
//    public void notifyDataSetChanged() {
////        mChildCount = getCount();
//        super.notifyDataSetChanged();
//    }
//
//    /**
//     * 该方法是强制重绘页面
//     */
//    @Override
//    public int getItemPosition(Object object) {
////        if (mChildCount > 0) {
////            mChildCount--;
////            return POSITION_NONE;
////        }
//        return super.getItemPosition(object);
//    }
//
//}
