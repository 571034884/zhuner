package com.aibabel.map.bean.transitzh;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fytworks on 2018/11/30.
 */

public class TransitRoutesZhBean implements Serializable{

    private double distance;
    private int duration;
    private List<TransitLineZhPriceBean> line_price;
    private double price;
    private int traffic_condition;
    public List<List<TransitStepsZhBean>> steps;

    public List<List<TransitStepsZhBean>> getSteps() {
        return steps;
    }

    public void setSteps(List<List<TransitStepsZhBean>> steps) {
        this.steps = steps;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTraffic_condition() {
        return traffic_condition;
    }

    public void setTraffic_condition(int traffic_condition) {
        this.traffic_condition = traffic_condition;
    }

    public List<TransitLineZhPriceBean> getLine_price() {
        return line_price;
    }

    public void setLine_price(List<TransitLineZhPriceBean> line_price) {
        this.line_price = line_price;
    }
}
