package com.aibabel.map.bean.transitzh;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/11/30.
 */

public class TransitLineZhPriceBean implements Serializable{
    private int line_type;
    private double line_price;

    public int getLine_type() {
        return line_type;
    }

    public void setLine_type(int line_type) {
        this.line_type = line_type;
    }

    public double getLine_price() {
        return line_price;
    }

    public void setLine_price(double line_price) {
        this.line_price = line_price;
    }
}
