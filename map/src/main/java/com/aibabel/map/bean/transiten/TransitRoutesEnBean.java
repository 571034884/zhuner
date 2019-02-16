package com.aibabel.map.bean.transiten;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fytworks on 2018/12/1.
 */

public class TransitRoutesEnBean implements Serializable{
    private String arrive_time;
    private double distance;
    private int duration;
    private double price;
    private List<TransitStepsEnBean> steps;

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
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

    public List<TransitStepsEnBean> getSteps() {
        return steps;
    }

    public void setSteps(List<TransitStepsEnBean> steps) {
        this.steps = steps;
    }
}
