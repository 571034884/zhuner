package com.aibabel.alliedclock.bean;

import java.util.List;

/**
 * 作者：SunSH on 2018/5/31 15:36
 * 功能：
 * 版本：1.0
 */
public class CityBean {

    /**
     * group : A
     * child : [{"countryCn":"阿联酋","countryEn":"The United Arab Emirates","cityCn":"阿布扎比","cityEn":"Abu Dhabi","timeZone":"GMT+4"},{"countryCn":"澳大利亚","countryEn":"Australia","cityCn":"阿德莱德","cityEn":"Adelaide","timeZone":"GMT+9.5"},{"countryCn":"印度","countryEn":"India","cityCn":"阿格拉","cityEn":"Agra","timeZone":"GMT+5.5"},{"countryCn":"荷兰","countryEn":"Netherlands","cityCn":"阿姆斯特丹","cityEn":"Amsterdam","timeZone":"GMT+1"},{"countryCn":"法国","countryEn":"France","cityCn":"艾克斯","cityEn":"Aix En Prevence","timeZone":"GMT+1"},{"countryCn":"英国","countryEn":"Britain","cityCn":"爱丁堡","cityEn":"Edinburgh","timeZone":"GMT+0"},{"countryCn":"土耳其","countryEn":"Turkey","cityCn":"安卡拉","cityEn":"Ankara","timeZone":"GMT+2"},{"countryCn":"土耳其","countryEn":"Turkey","cityCn":"安塔利亚","cityEn":"Antalya","timeZone":"GMT+2"},{"countryCn":"比利时","countryEn":"Belgium","cityCn":"安特卫普","cityEn":"Antwerp","timeZone":"GMT+1"},{"countryCn":"丹麦","countryEn":"Denmark","cityCn":"奥胡斯","cityEn":"Aarhus","timeZone":"GMT+1"},{"countryCn":"新西兰","countryEn":"New Zealand","cityCn":"奥克兰","cityEn":"Auckland","timeZone":"GMT+12"},{"countryCn":"美国","countryEn":"U.S.A","cityCn":"奥兰多","cityEn":"Orlando","timeZone":"GMT-5"},{"countryCn":"希腊","countryEn":"Greece","cityCn":"奥林匹亚","cityEn":"Olympia","timeZone":"GMT-8"},{"countryCn":"挪威","countryEn":"Norway","cityCn":"奥斯陆","cityEn":"Oslo","timeZone":"GMT+1"},{"countryCn":"中国","countryEn":"China","cityCn":"澳门","cityEn":"Macau","timeZone":"GMT+8"}]
     */

    private String group;
    private List<ChildBean> child;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    public static class ChildBean {
        /**
         * countryCn : 阿联酋
         * countryEn : The United Arab Emirates
         * cityCn : 阿布扎比
         * cityEn : Abu Dhabi
         * timeZone : GMT+4
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
