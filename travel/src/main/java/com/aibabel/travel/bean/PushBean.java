package com.aibabel.travel.bean;

import java.util.List;

public class PushBean extends BaseModel {

    /**
     * lng : 139.778111
     * lat : 35.689446
     * poi_id : 3939934
     * poi_name_cn :
     * poi_name_en : YAITAROKA
     * poi_name_local : 焼樽家
     */

    private double lng;
    private double lat;
    private int poi_id;
    private String poi_name_cn;
    private String poi_name_en;
    private String poi_name_local;
    private int count;
    private String distance;
    private String imgUrl;
    private String audios;

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public int getPoi_id() {
        return poi_id;
    }

    public void setPoi_id(int poi_id) {
        this.poi_id = poi_id;
    }

    public String getPoi_name_cn() {
        return poi_name_cn;
    }

    public void setPoi_name_cn(String poi_name_cn) {
        this.poi_name_cn = poi_name_cn;
    }

    public String getPoi_name_en() {
        return poi_name_en;
    }

    public void setPoi_name_en(String poi_name_en) {
        this.poi_name_en = poi_name_en;
    }

    public String getPoi_name_local() {
        return poi_name_local;
    }

    public void setPoi_name_local(String poi_name_local) {
        this.poi_name_local = poi_name_local;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAudios() {
        return audios;
    }

    public void setAudios(String audios) {
        this.audios = audios;
    }

}
