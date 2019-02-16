package com.aibabel.fyt_exitandentry.bean;



import com.aibabel.baselibrary.http.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：SunSH on 2018/7/26 16:50
 * 功能：
 * 版本：1.0
 */
public class CityListBean extends BaseBean implements Serializable{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * AddrId : 1
         * countryChj : 日本
         * cityChj : 东京
         * groupByChj : D
         * isHot : 1
         */

        private String AddrId;
        private String countryChj;
        private String cityChj;
        private String groupByChj;
        private String isHot;

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
    }
}
