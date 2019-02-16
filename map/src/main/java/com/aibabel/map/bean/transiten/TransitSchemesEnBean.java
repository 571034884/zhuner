package com.aibabel.map.bean.transiten;

import com.aibabel.map.bean.LocationBean;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/12/1.
 */

public class TransitSchemesEnBean implements Serializable{
    private double distance;
    private int duration;
    private LocationBean end_location;
    private String instructions;
    private String path;
    private LocationBean start_location;
    private TransitVehicleEnBean vehicle_info;

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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
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

    public TransitVehicleEnBean getVehicle_info() {
        return vehicle_info;
    }

    public void setVehicle_info(TransitVehicleEnBean vehicle_info) {
        this.vehicle_info = vehicle_info;
    }
}
