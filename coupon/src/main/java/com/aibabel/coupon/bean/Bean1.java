package com.aibabel.coupon.bean;

/**
 * 作者：wuqinghua_fyt on 2018/12/13 17:31
 * 功能：
 * 版本：1.0
 */
public class Bean1 {
    private String Countryname ;
    private String Countryengname;
    private int Countryimage;

    public Bean1() {
        super();
    }

    public Bean1(String countryname, String countryengname, int countryimage) {
        Countryname = countryname;
        Countryengname = countryengname;
        Countryimage = countryimage;
    }

    public String getCountryname() {
        return Countryname;
    }

    public void setCountryname(String countryname) {
        Countryname = countryname;
    }

    public String getCountryengname() {
        return Countryengname;
    }

    public void setCountryengname(String countryengname) {
        Countryengname = countryengname;
    }

    public int getCountryimage() {
        return Countryimage;
    }

    public void setCountryimage(int countryimage) {
        Countryimage = countryimage;
    }
}
