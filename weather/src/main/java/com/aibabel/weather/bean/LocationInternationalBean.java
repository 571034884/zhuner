package com.aibabel.weather.bean;

import com.aibabel.weather.okgo.BaseBean;

/**
 * 作者：SunSH on 2018/11/8 10:20
 * 功能：
 * 版本：1.0
 */
public class LocationInternationalBean extends BaseBean {
    /**
     * data : {"cityCnName":"东京","CountryCnName":"日本"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cityCnName : 东京
         * CountryCnName : 日本
         */

        private String cityCnName;
        private String CountryCnName;

        public String getCityCnName() {
            return cityCnName;
        }

        public void setCityCnName(String cityCnName) {
            this.cityCnName = cityCnName;
        }

        public String getCountryCnName() {
            return CountryCnName;
        }

        public void setCountryCnName(String CountryCnName) {
            this.CountryCnName = CountryCnName;
        }
    }
}
