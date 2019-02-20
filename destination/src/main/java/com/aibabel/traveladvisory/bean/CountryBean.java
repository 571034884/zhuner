package com.aibabel.traveladvisory.bean;

/**
 * Created by Wuqinghua on 2018/6/9 0009.
 */
public class CountryBean {
    private  String name;
    private  String imgUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private  String id;

    public CountryBean(String name, String imgUrl, String id) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.id = id;
    }

    public CountryBean() {
        super();
    }

    @Override
    public String toString() {
        return "CountryBean{" +
                "name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
