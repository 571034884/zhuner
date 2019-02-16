package com.aibabel.map.bean.trafficen;

import com.aibabel.map.bean.LocationBean;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/11/30.
 */

public class StepsEnBean implements Serializable{
    private int direction;
    private double distance;
    private LocationBean end_location;
    private String instructions;
    private String path;
    private String road_name;
    private LocationBean start_location;
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

    public LocationBean getEnd_location() {
        return end_location;
    }

    public void setEnd_location(LocationBean end_location) {
        this.end_location = end_location;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRoad_name() {
        return road_name;
    }

    public void setRoad_name(String road_name) {
        this.road_name = road_name;
    }

    public LocationBean getStart_location() {
        return start_location;
    }

    public void setStart_location(LocationBean start_location) {
        this.start_location = start_location;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
