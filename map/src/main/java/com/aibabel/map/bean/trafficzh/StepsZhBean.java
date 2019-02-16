package com.aibabel.map.bean.trafficzh;

import com.aibabel.map.bean.LocationBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fytworks on 2018/11/29.
 */

public class StepsZhBean implements Serializable{
    private int direction;
    private double distance;
    private int duration;
    private LocationBean end_location;
    private String instruction;
    private int leg_index;
    private String path;
    private LocationBean start_location;
    private List<TrafficConditionZhBean> traffic_condition;
    private int turn;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
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

    public LocationBean getEnd_location() {
        return end_location;
    }

    public void setEnd_location(LocationBean end_location) {
        this.end_location = end_location;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getLeg_index() {
        return leg_index;
    }

    public void setLeg_index(int leg_index) {
        this.leg_index = leg_index;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocationBean getStart_location() {
        return start_location;
    }

    public void setStart_location(LocationBean start_location) {
        this.start_location = start_location;
    }

    public List<TrafficConditionZhBean> getTraffic_condition() {
        return traffic_condition;
    }

    public void setTraffic_condition(List<TrafficConditionZhBean> traffic_condition) {
        this.traffic_condition = traffic_condition;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
