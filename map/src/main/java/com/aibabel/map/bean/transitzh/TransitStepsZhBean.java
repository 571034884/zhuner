package com.aibabel.map.bean.transitzh;

import com.aibabel.map.bean.LocationBean;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/11/30.
 */

public class TransitStepsZhBean implements Serializable{
    private int duration;
    private double distance;
    private LocationBean end_location;
    private String instruction;
    private String path;
    private LocationBean start_location;
    private int type;
    private TransitVehicleZhBean vehicle;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public TransitVehicleZhBean getVehicle() {
        return vehicle;
    }

    public void setVehicle(TransitVehicleZhBean vehicle) {
        this.vehicle = vehicle;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}