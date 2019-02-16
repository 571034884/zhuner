package com.aibabel.coupon.bean;


import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * 作者：wuqinghua_fyt on 2018/11/29 18:33
 * 功能：
 * 版本：1.0
 */
public class CouponQueryBean extends BaseBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * CouponId : 2
         * countryname : 泰国
         * countryengname : Thailand
         * countryimage : http://destination.cdn.aibabel.com/coupon/pic/Thailand.png
         * bannerimage : http://destination.cdn.aibabel.com/coupon/pic/Thailand-banner.png
         * image : http://destination.cdn.aibabel.com/coupon/pic/kingpower.png
         * title : 王权免税店（清迈机场店）
         * youhui : 600泰铢
         * tiaojian : 购物满7000泰铢
         * couponType :
         * context : 泰国王权免税店隶属于泰国王权国际集团旗下，汇聚众多世界时尚热销品牌，购物空间优雅舒适，拥有王权曼谷市中心店、王权曼谷素万那普国际机场店、王权曼谷史万利店、王权曼谷廊曼机场店、芭提雅市区店、普吉国际机场店，合艾机场店，清迈国际机场店等八大分店。
         * qrimage : http://destination.cdn.aibabel.com/coupon/pic/泰国免税店王权（清迈机场店）(1).png
         * basicimage : http://destination.cdn.aibabel.com/coupon/pic/泰国免税店王权（清迈机场店）.jpg
         * time : 2019/3/31
         * tiaojianshort : 部分产品不享受，详询店员
         */

        private int CouponId;
        private String countryname;
        private String countryengname;
        private String countryimage;
        private String bannerimage;
        private String image;
        private String title;
        private String youhui;
        private String tiaojian;
        private String couponType;
        private String context;
        private String qrimage;
        private String basicimage;
        private String time;
        private String tiaojianshort;

        public int getCouponId() {
            return CouponId;
        }

        public void setCouponId(int CouponId) {
            this.CouponId = CouponId;
        }

        public String getCountryname() {
            return countryname;
        }

        public void setCountryname(String countryname) {
            this.countryname = countryname;
        }

        public String getCountryengname() {
            return countryengname;
        }

        public void setCountryengname(String countryengname) {
            this.countryengname = countryengname;
        }

        public String getCountryimage() {
            return countryimage;
        }

        public void setCountryimage(String countryimage) {
            this.countryimage = countryimage;
        }

        public String getBannerimage() {
            return bannerimage;
        }

        public void setBannerimage(String bannerimage) {
            this.bannerimage = bannerimage;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getYouhui() {
            return youhui;
        }

        public void setYouhui(String youhui) {
            this.youhui = youhui;
        }

        public String getTiaojian() {
            return tiaojian;
        }

        public void setTiaojian(String tiaojian) {
            this.tiaojian = tiaojian;
        }

        public String getCouponType() {
            return couponType;
        }

        public void setCouponType(String couponType) {
            this.couponType = couponType;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getQrimage() {
            return qrimage;
        }

        public void setQrimage(String qrimage) {
            this.qrimage = qrimage;
        }

        public String getBasicimage() {
            return basicimage;
        }

        public void setBasicimage(String basicimage) {
            this.basicimage = basicimage;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTiaojianshort() {
            return tiaojianshort;
        }

        public void setTiaojianshort(String tiaojianshort) {
            this.tiaojianshort = tiaojianshort;
        }
    }
}
