package com.aibabel.map.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fytworks on 2018/12/3.
 */

public class TransitDetailsBean implements Serializable{
    private List<TransitBusBean> lineName;
    private String timer;
    private String subWay;
    private String startName;

    public List<TransitBusBean> getLineName() {
        return lineName;
    }

    public void setLineName(List<TransitBusBean> lineName) {
        this.lineName = lineName;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getSubWay() {
        return subWay;
    }

    public void setSubWay(String subWay) {
        this.subWay = subWay;
    }

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }
}
