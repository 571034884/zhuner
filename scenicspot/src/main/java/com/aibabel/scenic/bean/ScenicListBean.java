package com.aibabel.scenic.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;
/**
 *==========================================================================================
 * @Author： 张文颖
 *
 * @Date：2019/3/27
 *
 * @Desc：
 *==========================================================================================
 */
public class ScenicListBean extends BaseBean{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * idstring : CurrentScenicId1467
         * name : 卢浮宫
         * cover : https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F4163e459baf54d708ea7128821f22484baf8f39a.jpg
         * type : 3
         * cityname : 巴黎
         * countryname : 法国
         * latitude : 48.860611
         * longitude : 2.337633
         * audiosUrl : https://mjtt.gowithtommy.com/%E6%AC%A7%E6%B4%B2/%E6%B3%95%E5%9B%BD/%E5%8D%A2%E6%B5%AE%E5%AE%AB/%E6%B3%95%E5%9B%BD-%E5%B7%B4%E9%BB%8E-%E5%8D%A2%E6%B5%AE%E5%AE%AB.mp3
         * subcount : 400
         * desc :
         * isMy : 0
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
        private String isMy;

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

        public String getIsMy() {
            return isMy;
        }

        public void setIsMy(String isMy) {
            this.isMy = isMy;
        }
    }
}
