package com.aibabel.scenic.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

public class HistoryBean extends BaseBean {


 /*   *//**
     * data : {"dataTourguide":[{"idstring":"CurrentCityId109","name":"日本文化","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fupload%2F5f49aaead39b2408af6adbba181615ae.jpg","type":2,"cityname":"","countryname":"日本","latitude":"0.000000","longitude":"0.000000","audiosUrl":"","subcount":7,"desc":""},{"idstring":"CurrentCityId111","name":"日本简介","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fd401aa09b46ee9e4cb2e3d5d79a740386adb0413.jpg","type":2,"cityname":"","countryname":"日本","latitude":"0.000000","longitude":"0.000000","audiosUrl":"","subcount":6,"desc":""}],"menuCountryId":"800000001","historyPage":"http://destination.cdn.aibabel.com/tourguideH5/index.html"}
     *//*

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        *//**
         * dataTourguide : [{"idstring":"CurrentCityId109","name":"日本文化","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fupload%2F5f49aaead39b2408af6adbba181615ae.jpg","type":2,"cityname":"","countryname":"日本","latitude":"0.000000","longitude":"0.000000","audiosUrl":"","subcount":7,"desc":""},{"idstring":"CurrentCityId111","name":"日本简介","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fd401aa09b46ee9e4cb2e3d5d79a740386adb0413.jpg","type":2,"cityname":"","countryname":"日本","latitude":"0.000000","longitude":"0.000000","audiosUrl":"","subcount":6,"desc":""}]
         * menuCountryId : 800000001
         * historyPage : http://destination.cdn.aibabel.com/tourguideH5/index.html
         *//*

        private String menuCountryId;
        private String historyPage;
        private String imageCountry;
        private List<DataTourguideBean> dataTourguide;

        public String getMenuCountryId() {
            return menuCountryId;
        }

        public void setMenuCountryId(String menuCountryId) {
            this.menuCountryId = menuCountryId;
        }

        public String getHistoryPage() {
            return historyPage;
        }

        public void setHistoryPage(String historyPage) {
            this.historyPage = historyPage;
        }

        public List<DataTourguideBean> getDataTourguide() {
            return dataTourguide;
        }

        public void setDataTourguide(List<DataTourguideBean> dataTourguide) {
            this.dataTourguide = dataTourguide;
        }

        public String getImageCountry() {
            return imageCountry;
        }

        public void setImageCountry(String imageCountry) {
            this.imageCountry = imageCountry;
        }

        public static class DataTourguideBean {
            *//**
             * idstring : CurrentCityId109
             * name : 日本文化
             * cover : https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fupload%2F5f49aaead39b2408af6adbba181615ae.jpg
             * type : 2
             * cityname :
             * countryname : 日本
             * latitude : 0.000000
             * longitude : 0.000000
             * audiosUrl :
             * subcount : 7
             * desc :
             *//*

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
    }*/

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
