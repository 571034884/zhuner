package com.aibabel.weather.bean;

import java.io.Serializable;

/**
 * 作者：SunSH on 2018/5/31 20:22
 * 功能：
 * 版本：1.0
 */
public class WeatherUrlBean implements Serializable {

    String city;
    String country;
    String cityCN;
    String countryCN;

    public String getCityCN() {
        return cityCN;
    }

    public void setCityCN(String cityCN) {
        this.cityCN = cityCN;
    }

    public String getCountryCN() {
        return countryCN;
    }

    public void setCountryCN(String countryCN) {
        this.countryCN = countryCN;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
//        return "LANG=" + "CN" + "&COUNTRY=" + country + "&CITY=" + city + ".json";
        return "&ADDR=" + country + "/" + city;
    }
}
