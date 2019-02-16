package com.aibabel.map.bean.transitzh;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/11/30.
 */

public class TransitVehicleZhBean implements Serializable{

    private String direct_text;
    private String end_name;
    private String end_time;
    private String name;
    private String start_name;
    private String start_time;
    private int stop_num;
    private String total_price;
    private int type;
    private String zone_price;

    public String getDirect_text() {
        return direct_text;
    }

    public void setDirect_text(String direct_text) {
        this.direct_text = direct_text;
    }

    public String getEnd_name() {
        return end_name;
    }

    public void setEnd_name(String end_name) {
        this.end_name = end_name;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_name() {
        return start_name;
    }

    public void setStart_name(String start_name) {
        this.start_name = start_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public int getStop_num() {
        return stop_num;
    }

    public void setStop_num(int stop_num) {
        this.stop_num = stop_num;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getZone_price() {
        return zone_price;
    }

    public void setZone_price(String zone_price) {
        this.zone_price = zone_price;
    }
}
