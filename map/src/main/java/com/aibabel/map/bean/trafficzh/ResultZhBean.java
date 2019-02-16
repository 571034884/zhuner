package com.aibabel.map.bean.trafficzh;

import com.aibabel.map.bean.LocationBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fytworks on 2018/11/29.
 */

public class ResultZhBean implements Serializable{
    private LocationBean destination;
    private LocationBean origin;
    private List<RoutesZhBean> routes;//一共有几条路线

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

    public List<RoutesZhBean> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RoutesZhBean> routes) {
        this.routes = routes;
    }
}
