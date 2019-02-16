package com.aibabel.map.bean.trafficzh;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fytworks on 2018/11/29.
 */

public class RoutesZhBean implements Serializable{
    private double distance;
    private int duration;
    private int traffic_condition;
    private List<StepsZhBean> steps;//某一条路线内部 具体的路程经过

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

    public int getTraffic_condition() {
        return traffic_condition;
    }

    public void setTraffic_condition(int traffic_condition) {
        this.traffic_condition = traffic_condition;
    }

    public List<StepsZhBean> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsZhBean> steps) {
        this.steps = steps;
    }
}
