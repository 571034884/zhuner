package com.aibabel.map.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/12/10.
 */

public class CollectStarBean extends LitePalSupport implements Serializable{

    private String name;
    private String location;
    private String uid;
    private String addr;

    public CollectStarBean(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
