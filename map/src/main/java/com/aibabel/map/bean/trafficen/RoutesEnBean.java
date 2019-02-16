package com.aibabel.map.bean.trafficen;

import com.aibabel.map.bean.LocationBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fytworks on 2018/11/30.
 */

public class RoutesEnBean implements Serializable{
    private LocationBean destination;
    private LocationBean origin;
    private double distance;
    private int duration;
    private int light_number;
    private int tag;
    private List<StepsEnBean> steps;//某一条路线内部 具体的路程经过

    public LocationBean getDestination() {
        return destination;
    }

    public void setDestination(LocationBean destination) {
        this.destination = destination;
    }

    public LocationBean getOrigin() {
        return origin;
    }

    public void setOrigin(LocationBean origin) {
        this.origin = origin;
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

    public int getLight_number() {
        return light_number;
    }

    public void setLight_number(int light_number) {
        this.light_number = light_number;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public List<StepsEnBean> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsEnBean> steps) {
        this.steps = steps;
    }
}
