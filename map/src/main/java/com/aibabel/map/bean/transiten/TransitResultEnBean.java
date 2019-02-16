package com.aibabel.map.bean.transiten;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fytworks on 2018/12/1.
 */

public class TransitResultEnBean implements Serializable{
    private TransitCityEnBean destination;
    private TransitCityEnBean origin;
    private int total;
    private List<TransitRoutesEnBean> routes;

    public TransitCityEnBean getDestination() {
        return destination;
    }

    public void setDestination(TransitCityEnBean destination) {
        this.destination = destination;
    }

    public TransitCityEnBean getOrigin() {
        return origin;
    }

    public void setOrigin(TransitCityEnBean origin) {
        this.origin = origin;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TransitRoutesEnBean> getRoutes() {
        return routes;
    }

    public void setRoutes(List<TransitRoutesEnBean> routes) {
        this.routes = routes;
    }
}
