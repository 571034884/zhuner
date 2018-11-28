package com.aibabel.coupon.bean;

/**
 * 作者：wuqinghua_fyt on 2018/9/7 10:37
 * 功能：
 * 版本：1.0
 *
 * 优惠券使用详情中
 * 点名 地址 营业时间
 */
public class ShopBean {
    private String shop_name;
    private String shop_address;
    private String shop_time ;


    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getShop_time() {
        return shop_time;
    }

    public void setShop_time(String shop_time) {
        this.shop_time = shop_time;
    }

    public ShopBean(String shop_name, String shop_address, String shop_time) {
        this.shop_name = shop_name;
        this.shop_address = shop_address;
        this.shop_time = shop_time;
    }
}
