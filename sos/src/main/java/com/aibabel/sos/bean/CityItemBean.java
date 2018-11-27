package com.aibabel.sos.bean;

/**
 * Created by Wuqinghua on 2018/6/5 0005.
 */
public class CityItemBean {
    public String City_Name;

    public int City_Img;

    public CityItemBean(String city_Name, int city_Img) {
        City_Name = city_Name;

        City_Img = city_Img;
    }

    @Override
    public String toString() {
        return "CityBean{" +
                "City_Name='" + City_Name + '\'' +
                ", City_Img=" + City_Img +
                '}';
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

    public int getCity_Img() {
        return City_Img;
    }

    public void setCity_Img(int city_Img) {
        City_Img = city_Img;
    }
}
