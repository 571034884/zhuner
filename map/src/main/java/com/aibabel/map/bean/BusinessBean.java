package com.aibabel.map.bean;


import com.aibabel.baselibrary.http.BaseBean;

import java.io.Serializable;
import java.util.List;

public class BusinessBean extends BaseBean implements Serializable {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * address : 地铁10号线
         * tag : 地铁站
         * tagType : metro
         * lng : 116.360484
         * lat : 39.982026
         * distance : 488
         * baiduUID : 2d180dc935fbae831c5800de
         * tourguideId : 0
         * tourguideType : 0
         * audioUrl :
         * detinationId : 0
         * desc :
         * hotranking : 0
         * positive :
         * PoiType : 0
         * openTime :
         * localName :
         * phoneNum :
         * recommend : 0
         * tagMy :
         * image :
         * nameCh : 西土城
         * nameEn :
         */

        private String address;
        private String tag;
        private String tagType;
        private double lng;
        private double lat;
        private int distance;
        private String baiduUID;
        private int tourguideId;
        private int tourguideType;
        private String audioUrl;
        private int detinationId;
        private String desc;
        private int hotranking;
        private String positive;
        private int PoiType;
        private String openTime;
        private String localName;
        private String phoneNum;
        private String recommend;
        private String tagMy;
        private String image;
        private String nameCh;
        private String tourguidePidStr;
        private String nameEn;
        private int couponId;
        private int cateId;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTagType() {
            return tagType;
        }

        public void setTagType(String tagType) {
            this.tagType = tagType;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getBaiduUID() {
            return baiduUID;
        }

        public void setBaiduUID(String baiduUID) {
            this.baiduUID = baiduUID;
        }

        public int getTourguideId() {
            return tourguideId;
        }

        public void setTourguideId(int tourguideId) {
            this.tourguideId = tourguideId;
        }

        public int getTourguideType() {
            return tourguideType;
        }

        public void setTourguideType(int tourguideType) {
            this.tourguideType = tourguideType;
        }

        public String getAudioUrl() {
            return audioUrl;
        }

        public void setAudioUrl(String audioUrl) {
            this.audioUrl = audioUrl;
        }

        public int getDetinationId() {
            return detinationId;
        }

        public void setDetinationId(int detinationId) {
            this.detinationId = detinationId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getHotranking() {
            return hotranking;
        }

        public void setHotranking(int hotranking) {
            this.hotranking = hotranking;
        }

        public String getPositive() {
            return positive;
        }

        public void setPositive(String positive) {
            this.positive = positive;
        }

        public int getPoiType() {
            return PoiType;
        }

        public void setPoiType(int PoiType) {
            this.PoiType = PoiType;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public String getLocalName() {
            return localName;
        }

        public void setLocalName(String localName) {
            this.localName = localName;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getRecommend() {
            return recommend;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        public String getTagMy() {
            return tagMy;
        }

        public void setTagMy(String tagMy) {
            this.tagMy = tagMy;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNameCh() {
            return nameCh;
        }

        public void setNameCh(String nameCh) {
            this.nameCh = nameCh;
        }

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }


        public int getCouponId() {
            return couponId;
        }

        public void setCouponId(int couponId) {
            this.couponId = couponId;
        }

        public int getCateId() {
            return cateId;
        }

        public void setCateId(int cateId) {
            this.cateId = cateId;
        }

        public String getTourguidePidStr() {
            return tourguidePidStr;
        }

        public void setTourguidePidStr(String tourguidePidStr) {
            this.tourguidePidStr = tourguidePidStr;
        }
    }
}
