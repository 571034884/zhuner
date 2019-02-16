package com.aibabel.travel.bean;

/**
 * Created by Wuqinghua on 2018/6/5 0005.
 */
public class CityItemBean {
    private int id;
    private String name;
    private String cover;
    private int cover_path;
    private double latitude;
    private double longitude;
    private int subCount;
    private String state;
    private String hotCountry;
    public String City_Name;
    public String City_Img;
    public String offline;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
    public int getCover_path() {
        return cover_path;
    }

    public void setCover_path(int cover_path) {
        this.cover_path = cover_path;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getSubCount() {
        return subCount;
    }

    public void setSubCount(int subCount) {
        this.subCount = subCount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHotCountry() {
        return hotCountry;
    }

    public void setHotCountry(String hotCountry) {
        this.hotCountry = hotCountry;
    }

    public CityItemBean() {
        super();
    }

    public String getCity_Name() {
        return City_Name;
    }

    public void setCity_Name(String city_Name) {
        City_Name = city_Name;
    }

    public String getCity_Img() {
        return City_Img;
    }

    public void setCity_Img(String city_Img) {
        City_Img = city_Img;
    }

    public String getOffline() {
        return offline;
    }

    public void setOffline(String offline) {
        this.offline = offline;
    }

    @Override
    public String toString() {
        return "CityItemBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", cover_path=" + cover_path +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", subCount=" + subCount +
                ", state='" + state + '\'' +
                ", hotCountry='" + hotCountry + '\'' +
                ", City_Name='" + City_Name + '\'' +
                ", City_Img='" + City_Img + '\'' +
                ", offline='" + offline + '\'' +
                '}';
    }
}
