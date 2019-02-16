package com.aibabel.map.bean.transiten;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/12/1.
 */

public class TransitDetailEnBean implements Serializable{
    private String first_time;
    private String last_time;
    private String name;
    private String off_station;
    private String on_station;
    private int stop_num;
    private int type;

    public String getFirst_time() {
        return first_time;
    }

    public void setFirst_time(String first_time) {
        this.first_time = first_time;
    }

    public String getLast_time() {
        return last_time;
    }

    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOff_station() {
        return off_station;
    }

    public void setOff_station(String off_station) {
        this.off_station = off_station;
    }

    public String getOn_station() {
        return on_station;
    }

    public void setOn_station(String on_station) {
        this.on_station = on_station;
    }

    public int getStop_num() {
        return stop_num;
    }

    public void setStop_num(int stop_num) {
        this.stop_num = stop_num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
