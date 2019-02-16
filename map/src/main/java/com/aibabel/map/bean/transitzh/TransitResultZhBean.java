package com.aibabel.map.bean.transitzh;

import com.aibabel.map.bean.LocationBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fytworks on 2018/11/30.
 */

public class TransitResultZhBean implements Serializable{
    private LocationBean destination;
    private LocationBean origin;
    private List<TransitRoutesZhBean> routes;//一共有几条路线

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

    public List<TransitRoutesZhBean> getRoutes() {
        return routes;
    }

    public void setRoutes(List<TransitRoutesZhBean> routes) {
        this.routes = routes;
    }
}
