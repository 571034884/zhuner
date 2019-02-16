package com.aibabel.fyt_exitandentry.bean;


import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * 作者：wuqinghua_fyt on 2018/12/13 10:25
 * 功能：
 * 版本：1.0
 */
public class NoCityBean extends BaseBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * AddrId : 01
         * countryChj : 日本
         * cityChj : 东京
         * groupByChj : D
         * isHot : 1
         * imageUrl : https://ypimg1.youpu.cn/shine/201504/5c11746d1a46c1531fee164622f59e9e.jpg
         */

        private String AddrId;
        private String countryChj;
        private String cityChj;
        private String groupByChj;
        private String isHot;
        private String imageUrl;

        public String getAddrId() {
            return AddrId;
        }

        public void setAddrId(String AddrId) {
            this.AddrId = AddrId;
        }

        public String getCountryChj() {
            return countryChj;
        }

        public void setCountryChj(String countryChj) {
            this.countryChj = countryChj;
        }

        public String getCityChj() {
            return cityChj;
        }

        public void setCityChj(String cityChj) {
            this.cityChj = cityChj;
        }

        public String getGroupByChj() {
            return groupByChj;
        }

        public void setGroupByChj(String groupByChj) {
            this.groupByChj = groupByChj;
        }

        public String getIsHot() {
            return isHot;
        }

        public void setIsHot(String isHot) {
            this.isHot = isHot;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
