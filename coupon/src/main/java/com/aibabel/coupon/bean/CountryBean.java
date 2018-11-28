package com.aibabel.coupon.bean;

/**
 * 作者：wuqinghua_fyt on 2018/9/5 10:35
 * 功能：
 * 版本：1.0
 *
 *  目的地 首页
 *  参数 国家名 国家英文名 国家图片
 */
public class CountryBean {
    private String Country_name ;
    private String Country_name_english ;
    private int Country_img ;

    public CountryBean(String country_name, String country_name_english, int country_img) {
        Country_name = country_name;
        Country_name_english = country_name_english;
        Country_img = country_img;
    }

    public String getCountry_name() {
        return Country_name;
    }

    public void setCountry_name(String country_name) {
        Country_name = country_name;
    }

    public String getCountry_name_english() {
        return Country_name_english;
    }

    public void setCountry_name_english(String country_name_english) {
        Country_name_english = country_name_english;
    }

    public int getCountry_img() {
        return Country_img;
    }

    public void setCountry_img(int country_img) {
        Country_img = country_img;
    }
}
