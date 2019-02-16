package com.aibabel.fyt_exitandentry.bean;

/**
 * 作者：wuqinghua_fyt on 2018/12/13 14:12
 * 功能：
 * 版本：1.0
 */
public class HotCityBean {
    private String hotCountry;
    private String hotCity;

    public HotCityBean() {
        super();
    }

    public HotCityBean(String hotCountry, String hotCity) {
        this.hotCountry = hotCountry;
        this.hotCity = hotCity;
    }

    public String getHotCountry() {
        return hotCountry;
    }

    public void setHotCountry(String hotCountry) {
        this.hotCountry = hotCountry;
    }

    public String getHotCity() {
        return hotCity;
    }

    public void setHotCity(String hotCity) {
        this.hotCity = hotCity;
    }
}
