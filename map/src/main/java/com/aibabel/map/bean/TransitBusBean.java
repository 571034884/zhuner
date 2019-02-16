package com.aibabel.map.bean;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/11/30.
 * 用于存储公交数据
 *
 *
 */

public class TransitBusBean implements Serializable{

    private String name;
    private int type;

    public TransitBusBean() {
    }

    public TransitBusBean(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
