package com.aibabel.surfinternet.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * Created by fytworks on 2019/4/9.
 */

public class DetailPayBean extends BaseBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * sku_id : DP20190307000265
         * name : 乌拉圭
         * packageName:巴林岛-流量套餐
         * highFlowSize : 100
         * priceCny : 13.00
         * priceUsd :
         * quantity_min : 1
         * quantity_max : 30
         * quantity_step : 1
         * introduct :  *每天100M后降速 *覆盖国家: 乌拉圭
         * countryName : 乌拉圭
         * flowPic : http://destination.cdn.aibabel.com/netflow/flowPic1904091109/100.png
         */

        private String sku_id;
        private String name;
        private String highFlowSize;
        private String priceCny;
        private String priceUsd;
        private String quantity_min;
        private String quantity_max;
        private String quantity_step;
        private String introduct;
        private String countryName;
        private String flowPic;
        private String packageName;

        public String getSku_id() {
            return sku_id;
        }

        public void setSku_id(String sku_id) {
            this.sku_id = sku_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHighFlowSize() {
            return highFlowSize;
        }

        public void setHighFlowSize(String highFlowSize) {
            this.highFlowSize = highFlowSize;
        }

        public String getPriceCny() {
            return priceCny;
        }

        public void setPriceCny(String priceCny) {
            this.priceCny = priceCny;
        }

        public String getPriceUsd() {
            return priceUsd;
        }

        public void setPriceUsd(String priceUsd) {
            this.priceUsd = priceUsd;
        }

        public String getQuantity_min() {
            return quantity_min;
        }

        public void setQuantity_min(String quantity_min) {
            this.quantity_min = quantity_min;
        }

        public String getQuantity_max() {
            return quantity_max;
        }

        public void setQuantity_max(String quantity_max) {
            this.quantity_max = quantity_max;
        }

        public String getQuantity_step() {
            return quantity_step;
        }

        public void setQuantity_step(String quantity_step) {
            this.quantity_step = quantity_step;
        }

        public String getIntroduct() {
            return introduct;
        }

        public void setIntroduct(String introduct) {
            this.introduct = introduct;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getFlowPic() {
            return flowPic;
        }

        public void setFlowPic(String flowPic) {
            this.flowPic = flowPic;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }
    }
}
