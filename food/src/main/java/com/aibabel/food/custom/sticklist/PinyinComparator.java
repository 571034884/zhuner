package com.aibabel.food.custom.sticklist;


import java.util.Comparator;

/**
 * 用来对ListView中的数据根据A-Z进行排序，前面两个if判断主要是将不是以汉字开头的数据放在后面
 */
public class PinyinComparator implements Comparator<CityListBean.DataBean> {

    public int compare(CityListBean.DataBean o1, CityListBean.DataBean o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o1.getGroupBy() == null || o2.getGroupBy() == null) {
            return 1;
        } else if (o1.getGroupBy().equals("@") || o2.getGroupBy().equals("#")) {
            return -1;
        } else if (o1.getGroupBy().equals("#") || o2.getGroupBy().equals("@")) {
            return 1;
        } else {
            return o1.getGroupBy().compareTo(o2.getGroupBy());
        }
    }
}
