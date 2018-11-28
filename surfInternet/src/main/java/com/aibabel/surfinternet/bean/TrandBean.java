package com.aibabel.surfinternet.bean;

import com.aibabel.surfinternet.okgo.BaseBean;

import java.util.List;

/**
 * Created by Wuqinghua on 2018/6/16 0016.
 *
 * 全球上网  获取支持的国家的列表
 */
public class TrandBean extends BaseBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * country : 丹麦
         * price : 29.9
         * impage : http://p.flow.billionconnect.com/module/country/danmai.png
         * skuid : 1532093169957594
         * days : 1
         * priceYidian : 14.0000
         */

        private String country;
        private String price;
        private String impage;
        private String skuid;
        private String days;
        private String priceYidian;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImpage() {
            return impage;
        }

        public void setImpage(String impage) {
            this.impage = impage;
        }

        public String getSkuid() {
            return skuid;
        }

        public void setSkuid(String skuid) {
            this.skuid = skuid;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getPriceYidian() {
            return priceYidian;
        }

        public void setPriceYidian(String priceYidian) {
            this.priceYidian = priceYidian;
        }
    }
}
