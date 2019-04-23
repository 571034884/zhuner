package com.aibabel.menu.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

public class CityListBean extends BaseBean {


    /**
     * data : {"uageUrl":"http://destination.cdn.aibabel.com/menuH5/image/H5page/index.html","hotAddr":[{"countryId":"800000001","cityId":"100000001","countryChj":"日本","countryEn":"Japan","countryKor":"일본","countryJpa":"日本","countryChf":"日本","cityChj":"东京","cityEn":"Fukuoka","cityKor":"후쿠오카","cityJpa":"福岡","cityChf":"福岡","timeZone":"GMT+9","groupBy":"F","groupByChj":"F","groupByEn":"F","groupByKor":"ㅎ","groupByJpa":"福","groupByChf":"F","destinationCity_id":"","isHot":"1"},{"countryId":"800000001","cityId":"100000006","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"九州","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"J","groupByChj":"J","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"1"},{"countryId":"800000001","cityId":"100000003","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"京都","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"J","groupByChj":"J","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"1"},{"countryId":"800000001","cityId":"100000004","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"奈良","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"N","groupByChj":"N","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"1"}],"popularAddr":[{"countryId":"800000001","cityId":"100000001","countryChj":"日本","countryEn":"Japan","countryKor":"일본","countryJpa":"日本","countryChf":"日本","cityChj":"东京","cityEn":"Fukuoka","cityKor":"후쿠오카","cityJpa":"福岡","cityChf":"福岡","timeZone":"GMT+9","groupBy":"F","groupByChj":"F","groupByEn":"F","groupByKor":"ㅎ","groupByJpa":"福","groupByChf":"F","destinationCity_id":"","isHot":"1"},{"countryId":"800000001","cityId":"100000006","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"九州","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"J","groupByChj":"J","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"1"},{"countryId":"800000001","cityId":"100000003","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"京都","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"J","groupByChj":"J","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"1"},{"countryId":"800000001","cityId":"100000007","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"冲绳","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"C","groupByChj":"C","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"0"},{"countryId":"800000001","cityId":"100000005","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"北海道","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"B","groupByChj":"B","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"0"},{"countryId":"800000001","cityId":"100000002","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"大阪","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"D","groupByChj":"D","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"0"},{"countryId":"800000001","cityId":"100000004","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"奈良","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"N","groupByChj":"N","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"1"}]}
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
         * uageUrl : http://destination.cdn.aibabel.com/menuH5/image/H5page/index.html
         * hotAddr : [{"countryId":"800000001","cityId":"100000001","countryChj":"日本","countryEn":"Japan","countryKor":"일본","countryJpa":"日本","countryChf":"日本","cityChj":"东京","cityEn":"Fukuoka","cityKor":"후쿠오카","cityJpa":"福岡","cityChf":"福岡","timeZone":"GMT+9","groupBy":"F","groupByChj":"F","groupByEn":"F","groupByKor":"ㅎ","groupByJpa":"福","groupByChf":"F","destinationCity_id":"","isHot":"1"},{"countryId":"800000001","cityId":"100000006","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"九州","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"J","groupByChj":"J","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"1"},{"countryId":"800000001","cityId":"100000003","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"京都","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"J","groupByChj":"J","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"1"},{"countryId":"800000001","cityId":"100000004","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"奈良","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"N","groupByChj":"N","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"1"}]
         * popularAddr : [{"countryId":"800000001","cityId":"100000001","countryChj":"日本","countryEn":"Japan","countryKor":"일본","countryJpa":"日本","countryChf":"日本","cityChj":"东京","cityEn":"Fukuoka","cityKor":"후쿠오카","cityJpa":"福岡","cityChf":"福岡","timeZone":"GMT+9","groupBy":"F","groupByChj":"F","groupByEn":"F","groupByKor":"ㅎ","groupByJpa":"福","groupByChf":"F","destinationCity_id":"","isHot":"1"},{"countryId":"800000001","cityId":"100000006","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"九州","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"J","groupByChj":"J","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"1"},{"countryId":"800000001","cityId":"100000003","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"京都","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"J","groupByChj":"J","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"1"},{"countryId":"800000001","cityId":"100000007","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"冲绳","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"C","groupByChj":"C","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"0"},{"countryId":"800000001","cityId":"100000005","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"北海道","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"B","groupByChj":"B","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"0"},{"countryId":"800000001","cityId":"100000002","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"大阪","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"D","groupByChj":"D","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"0"},{"countryId":"800000001","cityId":"100000004","countryChj":"日本","countryEn":"","countryKor":"","countryJpa":"","countryChf":"","cityChj":"奈良","cityEn":"","cityKor":"","cityJpa":"","cityChf":"","timeZone":"","groupBy":"N","groupByChj":"N","groupByEn":"","groupByKor":"","groupByJpa":"","groupByChf":"","destinationCity_id":"","isHot":"1"}]
         */

        private String uageUrl;
        private List<HotAddrBean> hotAddr;
        private List<PopularAddrBean> popularAddr;

        public String getUageUrl() {
            return uageUrl;
        }

        public void setUageUrl(String uageUrl) {
            this.uageUrl = uageUrl;
        }

        public List<HotAddrBean> getHotAddr() {
            return hotAddr;
        }

        public void setHotAddr(List<HotAddrBean> hotAddr) {
            this.hotAddr = hotAddr;
        }

        public List<PopularAddrBean> getPopularAddr() {
            return popularAddr;
        }

        public void setPopularAddr(List<PopularAddrBean> popularAddr) {
            this.popularAddr = popularAddr;
        }

        public static class HotAddrBean {
            /**
             * countryId : 800000001
             * cityId : 100000001
             * countryChj : 日本
             * countryEn : Japan
             * countryKor : 일본
             * countryJpa : 日本
             * countryChf : 日本
             * cityChj : 东京
             * cityEn : Fukuoka
             * cityKor : 후쿠오카
             * cityJpa : 福岡
             * cityChf : 福岡
             * timeZone : GMT+9
             * groupBy : F
             * groupByChj : F
             * groupByEn : F
             * groupByKor : ㅎ
             * groupByJpa : 福
             * groupByChf : F
             * destinationCity_id :
             * isHot : 1
             */

            private String countryId;
            private String cityId;
            private String countryChj;
            private String countryEn;
            private String countryKor;
            private String countryJpa;
            private String countryChf;
            private String cityChj;
            private String cityEn;
            private String cityKor;
            private String cityJpa;
            private String cityChf;
            private String timeZone;
            private String groupBy;
            private String groupByChj;
            private String groupByEn;
            private String groupByKor;
            private String groupByJpa;
            private String groupByChf;
            private String destinationCity_id;
            private String isHot;

            public String getCountryId() {
                return countryId;
            }

            public void setCountryId(String countryId) {
                this.countryId = countryId;
            }

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public String getCountryChj() {
                return countryChj;
            }

            public void setCountryChj(String countryChj) {
                this.countryChj = countryChj;
            }

            public String getCountryEn() {
                return countryEn;
            }

            public void setCountryEn(String countryEn) {
                this.countryEn = countryEn;
            }

            public String getCountryKor() {
                return countryKor;
            }

            public void setCountryKor(String countryKor) {
                this.countryKor = countryKor;
            }

            public String getCountryJpa() {
                return countryJpa;
            }

            public void setCountryJpa(String countryJpa) {
                this.countryJpa = countryJpa;
            }

            public String getCountryChf() {
                return countryChf;
            }

            public void setCountryChf(String countryChf) {
                this.countryChf = countryChf;
            }

            public String getCityChj() {
                return cityChj;
            }

            public void setCityChj(String cityChj) {
                this.cityChj = cityChj;
            }

            public String getCityEn() {
                return cityEn;
            }

            public void setCityEn(String cityEn) {
                this.cityEn = cityEn;
            }

            public String getCityKor() {
                return cityKor;
            }

            public void setCityKor(String cityKor) {
                this.cityKor = cityKor;
            }

            public String getCityJpa() {
                return cityJpa;
            }

            public void setCityJpa(String cityJpa) {
                this.cityJpa = cityJpa;
            }

            public String getCityChf() {
                return cityChf;
            }

            public void setCityChf(String cityChf) {
                this.cityChf = cityChf;
            }

            public String getTimeZone() {
                return timeZone;
            }

            public void setTimeZone(String timeZone) {
                this.timeZone = timeZone;
            }

            public String getGroupBy() {
                return groupBy;
            }

            public void setGroupBy(String groupBy) {
                this.groupBy = groupBy;
            }

            public String getGroupByChj() {
                return groupByChj;
            }

            public void setGroupByChj(String groupByChj) {
                this.groupByChj = groupByChj;
            }

            public String getGroupByEn() {
                return groupByEn;
            }

            public void setGroupByEn(String groupByEn) {
                this.groupByEn = groupByEn;
            }

            public String getGroupByKor() {
                return groupByKor;
            }

            public void setGroupByKor(String groupByKor) {
                this.groupByKor = groupByKor;
            }

            public String getGroupByJpa() {
                return groupByJpa;
            }

            public void setGroupByJpa(String groupByJpa) {
                this.groupByJpa = groupByJpa;
            }

            public String getGroupByChf() {
                return groupByChf;
            }

            public void setGroupByChf(String groupByChf) {
                this.groupByChf = groupByChf;
            }

            public String getDestinationCity_id() {
                return destinationCity_id;
            }

            public void setDestinationCity_id(String destinationCity_id) {
                this.destinationCity_id = destinationCity_id;
            }

            public String getIsHot() {
                return isHot;
            }

            public void setIsHot(String isHot) {
                this.isHot = isHot;
            }
        }

        public static class PopularAddrBean {
            /**
             * countryId : 800000001
             * cityId : 100000001
             * countryChj : 日本
             * countryEn : Japan
             * countryKor : 일본
             * countryJpa : 日本
             * countryChf : 日本
             * cityChj : 东京
             * cityEn : Fukuoka
             * cityKor : 후쿠오카
             * cityJpa : 福岡
             * cityChf : 福岡
             * timeZone : GMT+9
             * groupBy : F
             * groupByChj : F
             * groupByEn : F
             * groupByKor : ㅎ
             * groupByJpa : 福
             * groupByChf : F
             * destinationCity_id :
             * isHot : 1
             */

            private String countryId;
            private String cityId;
            private String countryChj;
            private String countryEn;
            private String countryKor;
            private String countryJpa;
            private String countryChf;
            private String cityChj;
            private String cityEn;
            private String cityKor;
            private String cityJpa;
            private String cityChf;
            private String timeZone;
            private String groupBy;
            private String groupByChj;
            private String groupByEn;
            private String groupByKor;
            private String groupByJpa;
            private String groupByChf;
            private String destinationCity_id;
            private String isHot;

            public String getCountryId() {
                return countryId;
            }

            public void setCountryId(String countryId) {
                this.countryId = countryId;
            }

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public String getCountryChj() {
                return countryChj;
            }

            public void setCountryChj(String countryChj) {
                this.countryChj = countryChj;
            }

            public String getCountryEn() {
                return countryEn;
            }

            public void setCountryEn(String countryEn) {
                this.countryEn = countryEn;
            }

            public String getCountryKor() {
                return countryKor;
            }

            public void setCountryKor(String countryKor) {
                this.countryKor = countryKor;
            }

            public String getCountryJpa() {
                return countryJpa;
            }

            public void setCountryJpa(String countryJpa) {
                this.countryJpa = countryJpa;
            }

            public String getCountryChf() {
                return countryChf;
            }

            public void setCountryChf(String countryChf) {
                this.countryChf = countryChf;
            }

            public String getCityChj() {
                return cityChj;
            }

            public void setCityChj(String cityChj) {
                this.cityChj = cityChj;
            }

            public String getCityEn() {
                return cityEn;
            }

            public void setCityEn(String cityEn) {
                this.cityEn = cityEn;
            }

            public String getCityKor() {
                return cityKor;
            }

            public void setCityKor(String cityKor) {
                this.cityKor = cityKor;
            }

            public String getCityJpa() {
                return cityJpa;
            }

            public void setCityJpa(String cityJpa) {
                this.cityJpa = cityJpa;
            }

            public String getCityChf() {
                return cityChf;
            }

            public void setCityChf(String cityChf) {
                this.cityChf = cityChf;
            }

            public String getTimeZone() {
                return timeZone;
            }

            public void setTimeZone(String timeZone) {
                this.timeZone = timeZone;
            }

            public String getGroupBy() {
                return groupBy;
            }

            public void setGroupBy(String groupBy) {
                this.groupBy = groupBy;
            }

            public String getGroupByChj() {
                return groupByChj;
            }

            public void setGroupByChj(String groupByChj) {
                this.groupByChj = groupByChj;
            }

            public String getGroupByEn() {
                return groupByEn;
            }

            public void setGroupByEn(String groupByEn) {
                this.groupByEn = groupByEn;
            }

            public String getGroupByKor() {
                return groupByKor;
            }

            public void setGroupByKor(String groupByKor) {
                this.groupByKor = groupByKor;
            }

            public String getGroupByJpa() {
                return groupByJpa;
            }

            public void setGroupByJpa(String groupByJpa) {
                this.groupByJpa = groupByJpa;
            }

            public String getGroupByChf() {
                return groupByChf;
            }

            public void setGroupByChf(String groupByChf) {
                this.groupByChf = groupByChf;
            }

            public String getDestinationCity_id() {
                return destinationCity_id;
            }

            public void setDestinationCity_id(String destinationCity_id) {
                this.destinationCity_id = destinationCity_id;
            }

            public String getIsHot() {
                return isHot;
            }

            public void setIsHot(String isHot) {
                this.isHot = isHot;
            }
        }
    }
}
