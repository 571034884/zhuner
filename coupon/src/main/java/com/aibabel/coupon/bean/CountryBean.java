package com.aibabel.coupon.bean;





import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * 作者：wuqinghua_fyt on 2018/9/5 10:35
 * 功能：
 * 版本：1.0
 *
 *  目的地 首页
 *  参数 国家名 国家英文名 国家图片
 */
public class CountryBean extends BaseBean {

    /**
     * code : 1
     * data : [{"countryname":"日本","countryengname":"Japan","countryimage":"http://destination.cdn.aibabel.com/coupon/pic/Japan.png","bannerimage":"http://destination.cdn.aibabel.com/coupon/pic/Japan-banner.png"},{"countryname":"泰国","countryengname":"Thailand","countryimage":"http://destination.cdn.aibabel.com/coupon/pic/Thailand.png","bannerimage":"http://destination.cdn.aibabel.com/coupon/pic/Thailand-banner.png"}]
     */

    private String codeX;
    private List<DataBean> data;

    public String getCodeX() {
        return codeX;
    }

    public void setCodeX(String codeX) {
        this.codeX = codeX;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * countryname : 日本
         * countryengname : Japan
         * countryimage : http://destination.cdn.aibabel.com/coupon/pic/Japan.png
         * bannerimage : http://destination.cdn.aibabel.com/coupon/pic/Japan-banner.png
         */

        private String countryname;
        private String countryengname;
        private String countryimage;
        private String bannerimage;

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
    }
}
