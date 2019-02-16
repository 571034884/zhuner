package com.aibabel.map.bean.transiten;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/12/1.
 */

public class TransitVehicleEnBean implements Serializable{
    private TransitDetailEnBean detail;
    private int type;

    public TransitDetailEnBean getDetail() {
        return detail;
    }

    public void setDetail(TransitDetailEnBean detail) {
        this.detail = detail;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
