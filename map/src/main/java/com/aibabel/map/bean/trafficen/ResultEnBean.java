package com.aibabel.map.bean.trafficen;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fytworks on 2018/11/30.
 */

public class ResultEnBean implements Serializable{


    private List<RoutesEnBean> routes;//一共有几条路线
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RoutesEnBean> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RoutesEnBean> routes) {
        this.routes = routes;
    }
}
