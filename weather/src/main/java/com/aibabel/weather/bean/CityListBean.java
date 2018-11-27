package com.aibabel.weather.bean;

import com.aibabel.weather.okgo.BaseBean;

import java.util.List;

/**
 * 作者：SunSH on 2018/7/26 16:50
 * 功能：
 * 版本：1.0
 */
public class CityListBean extends BaseBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * countryCn : 阿联酋
         * countryEn : The United Arab Emirates
         * cityCn : 阿布扎比
         * cityEn : Abu Dhabi
         * timeZone : GMT+4
         * group : A
         */

        private String countryCn;
        private String countryEn;
        private String cityCn;
        private String cityEn;
        private String timeZone;
        private String group;

        public String getCountryCn() {
            return countryCn;
        }

        public void setCountryCn(String countryCn) {
            this.countryCn = countryCn;
        }

        public String getCountryEn() {
            return countryEn;
        }

        public void setCountryEn(String countryEn) {
            this.countryEn = countryEn;
        }

        public String getCityCn() {
            return cityCn;
        }

        public void setCityCn(String cityCn) {
            this.cityCn = cityCn;
        }

        public String getCityEn() {
            return cityEn;
        }

        public void setCityEn(String cityEn) {
            this.cityEn = cityEn;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }
    }
}
