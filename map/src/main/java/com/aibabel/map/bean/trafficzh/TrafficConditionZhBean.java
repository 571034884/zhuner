package com.aibabel.map.bean.trafficzh;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/11/29.
 */

public class TrafficConditionZhBean implements Serializable{
    private int geo_cnt;
    private int status;

    public int getGeo_cnt() {
        return geo_cnt;
    }

    public void setGeo_cnt(int geo_cnt) {
        this.geo_cnt = geo_cnt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
