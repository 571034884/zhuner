package com.aibabel.alliedclock.bean;

import java.io.Serializable;

/**
 * 作者：SunSH on 2018/5/31 20:22
 * 功能：
 * 版本：1.0
 */
public class ClockBean implements Serializable {

    String city;
    String cityCN;
    String tiemZone;
    String date;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCN() {
        return cityCN;
    }

    public void setCityCN(String cityCN) {
        this.cityCN = cityCN;
    }

    public String getTiemZone() {
        return tiemZone;
    }

    public void setTiemZone(String tiemZone) {
        this.tiemZone = tiemZone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
