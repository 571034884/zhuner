package com.aibabel.map.bean;


import java.io.Serializable;

public class CardItem implements Serializable{

    private String url;
    private String nameCh;
    private String nameEn;
    private String category;
    private String distance;
    private String isHot;
    private String content;
    private String address;
    private String openTime;
//    private String ;


    public CardItem() {
    }

    public CardItem(String url, String nameCh, String nameEn, String category, String distance, String isHot) {
        this.url = url;
        this.nameCh = nameCh;
        this.nameEn = nameEn;
        this.category = category;
        this.distance = distance;
        this.isHot = isHot;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNameCh() {
        return nameCh;
    }

    public void setNameCh(String nameCh) {
        this.nameCh = nameCh;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getIsHot() {
        return isHot;
    }

    public void setIsHot(String isHot) {
        this.isHot = isHot;
    }
}
