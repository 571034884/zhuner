package com.aibabel.map.bean.transiten;

import com.aibabel.map.bean.LocationBean;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/12/1.
 */

public class TransitCityEnBean implements Serializable{
    private int city_id;
    private String city_name;
    private LocationBean location;

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }
}
