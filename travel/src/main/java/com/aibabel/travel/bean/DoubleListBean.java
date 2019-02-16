package com.aibabel.travel.bean;

import android.support.annotation.NonNull;

/**
 * 作者：wuqinghua_fyt on 2018/11/27 18:09
 * 功能：
 * 版本：1.0
 */
public class DoubleListBean implements  Comparable<DoubleListBean>{
    private double distance;
    private String name;

    public DoubleListBean(double distance, String name) {
        this.distance = distance;
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull DoubleListBean o) {
        double i = this.getDistance() - o.getDistance();//先按照年龄排序

        return (int) i;
    }
}
