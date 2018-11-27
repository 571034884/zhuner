package com.aibabel.alliedclock.bean;

/**
 * 作者：SunSH on 2018/7/13 21:09
 * 功能：
 * 版本：1.0
 */
public class OneCityBean {
    /**
     * code : 1
     * msg : Success!
     * data : {"countryCn":"中国","countryEn":"China","cityCn":"北京","cityEn":"Beijing","timeZone":"GMT+8"}
     */

    private String code;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * countryCn : 中国
         * countryEn : China
         * cityCn : 北京
         * cityEn : Beijing
         * timeZone : GMT+8
         */

        private String countryCn;
        private String countryEn;
        private String cityCn;
        private String cityEn;
        private String timeZone;

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
    }
}
