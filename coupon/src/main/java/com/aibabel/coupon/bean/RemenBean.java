package com.aibabel.coupon.bean;

/**
 * 作者：wuqinghua_fyt on 2018/9/7 10:39
 * 功能：
 * 版本：1.0
 *
 * 优惠券使用详情里
 * 参数 图 文字  特点 1  特点 2
 */
public class RemenBean {

    private String remen_img ;
    private String remen_name ;
    private String remen_tedian1;
    private String remen_tedian2;

    public RemenBean(String remen_img, String remen_name, String remen_tedian1, String remen_tedian2) {
        this.remen_img = remen_img;
        this.remen_name = remen_name;
        this.remen_tedian1 = remen_tedian1;
        this.remen_tedian2 = remen_tedian2;
    }

    public String getRemen_img() {
        return remen_img;
    }

    public void setRemen_img(String remen_img) {
        this.remen_img = remen_img;
    }

    public String getRemen_name() {
        return remen_name;
    }

    public void setRemen_name(String remen_name) {
        this.remen_name = remen_name;
    }

    public String getRemen_tedian1() {
        return remen_tedian1;
    }

    public void setRemen_tedian1(String remen_tedian1) {
        this.remen_tedian1 = remen_tedian1;
    }

    public String getRemen_tedian2() {
        return remen_tedian2;
    }

    public void setRemen_tedian2(String remen_tedian2) {
        this.remen_tedian2 = remen_tedian2;
    }
}
