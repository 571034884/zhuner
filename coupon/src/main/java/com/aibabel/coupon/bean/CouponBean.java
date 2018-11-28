package com.aibabel.coupon.bean;

/**
 * 作者：wuqinghua_fyt on 2018/9/5 17:03
 * 功能：
 * 版本：1.0
 *  我的优惠券 页  和  目的地领券页
 *  参数 ： 角标名字  店图片  店名字  店商品价格   使用条件  是否领券
 */
public class CouponBean {

    private String tv_jiaobiao_name ;
    private String iv_shop_img ;
    private String tv_shop_name ;
    private String tv_shop_price ;
    private String tv_shop_details ;
    private String tv_receive ;
//    https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536148533790&di=6b6f44e5602faa55606b7918ba7e00f1&imgtype=0&src=http%3A%2F%2Fi2.w.hjfile.cn%2Fnews%2F201509%2F201509101221406593.jpg

    public CouponBean(String tv_jiaobiao_name, String iv_shop_img, String tv_shop_name, String tv_shop_price, String tv_shop_details, String tv_receive) {
        this.tv_jiaobiao_name = tv_jiaobiao_name;
        this.iv_shop_img = iv_shop_img;
        this.tv_shop_name = tv_shop_name;
        this.tv_shop_price = tv_shop_price;
        this.tv_shop_details = tv_shop_details;
        this.tv_receive = tv_receive;
    }

    public String getTv_jiaobiao_name() {
        return tv_jiaobiao_name;
    }

    public void setTv_jiaobiao_name(String tv_jiaobiao_name) {
        this.tv_jiaobiao_name = tv_jiaobiao_name;
    }

    public String getIv_shop_img() {
        return iv_shop_img;
    }

    public void setIv_shop_img(String iv_shop_img) {
        this.iv_shop_img = iv_shop_img;
    }

    public String getTv_shop_name() {
        return tv_shop_name;
    }

    public void setTv_shop_name(String tv_shop_name) {
        this.tv_shop_name = tv_shop_name;
    }

    public String getTv_shop_price() {
        return tv_shop_price;
    }

    public void setTv_shop_price(String tv_shop_price) {
        this.tv_shop_price = tv_shop_price;
    }

    public String getTv_shop_details() {
        return tv_shop_details;
    }

    public void setTv_shop_details(String tv_shop_details) {
        this.tv_shop_details = tv_shop_details;
    }

    public String getTv_receive() {
        return tv_receive;
    }

    public void setTv_receive(String tv_receive) {
        this.tv_receive = tv_receive;
    }
}
