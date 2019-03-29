package com.aibabel.scenic.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * Created by fytworks on 2019/3/26.
 */

public class SearchBean extends BaseBean{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * idstring : CurrentCityId21
         * name : 印度教天神
         * cover : https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fupload%2F8ca991d3f11af5d16fabd62e36597099.jpeg
         * type : 2
         * cityname :
         * countryname : 尼泊尔
         * latitude : 0.000000
         * longitude : 0.000000
         * audiosUrl :
         * subcount : 8
         * desc :
         */

        private String idstring;
        private String name;
        private String cover;
        private int type;
        private String cityname;
        private String countryname;
        private String latitude;
        private String longitude;
        private String audiosUrl;
        private int subcount;
        private String desc;

        public String getIdstring() {
            return idstring;
        }

        public void setIdstring(String idstring) {
            this.idstring = idstring;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getCountryname() {
            return countryname;
        }

        public void setCountryname(String countryname) {
            this.countryname = countryname;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getAudiosUrl() {
            return audiosUrl;
        }

        public void setAudiosUrl(String audiosUrl) {
            this.audiosUrl = audiosUrl;
        }

        public int getSubcount() {
            return subcount;
        }

        public void setSubcount(int subcount) {
            this.subcount = subcount;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
