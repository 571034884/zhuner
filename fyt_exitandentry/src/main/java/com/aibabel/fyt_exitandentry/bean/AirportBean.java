package com.aibabel.fyt_exitandentry.bean;

/**
 * 作者：wuqinghua_fyt on 2018/12/28 11:21
 * 功能：
 * 版本：1.0
 */
public class AirportBean {
    private String airName;
    private String airImg;


    public AirportBean() {
        super();
    }

    public String getAirName() {
        return airName;
    }

    public void setAirName(String airName) {
        this.airName = airName;
    }

    public String getAirImg() {
        return airImg;
    }

    public void setAirImg(String airImg) {
        this.airImg = airImg;
    }

    public AirportBean(String airName, String airImg) {
        this.airName = airName;
        this.airImg = airImg;
    }
}
