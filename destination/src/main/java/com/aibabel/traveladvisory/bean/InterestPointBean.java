package com.aibabel.traveladvisory.bean;

import com.aibabel.traveladvisory.app.Constans;
import com.aibabel.traveladvisory.okgo.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：SunSH on 2018/6/18 11:30
 * 功能：
 * 版本：1.0
 */
public class InterestPointBean extends BaseBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * PoiId : 125422
         * parentId : 0
         * ranking : 999999
         * hotranking : 999999
         * positive : 0
         * PoiType : 68
         * PoiSubType : 98
         * Level : 3
         * placeId : 251
         * areaId : 0
         * continentId : 5
         * countryId : 148
         * CnCityName : 巴黎
         * EnCityName : Paris
         * CnName : Moulin Radet
         * EnName : Moulin Radet
         * LocalName :
         * Address : 83 Rue Lepic, 75018 Paris
         * localaddress :
         * Traffic : Metro:Abbesses or Lamarck-Caulaincourt
         * Ticket :
         * Website : www.lemoulindelagalette.fr
         * openTime : 周二到周六：12:00–14:30，19:00–22:30
         * Phone : +33 1 46 06 84 77
         * picCount : 1
         * Spot : 个性的风车样子的餐厅外观，新鲜的时令食材给你最真实的美味感觉。
         * tips :
         * description : 远远的你就可以看到一个大大的风车，好奇心会驱使你到那里。开始你会以为那是一个知名的建筑，或者是个有趣的房子，但你走进它，你会发现这竟然是一家餐厅。除了那个大风车，餐厅的美味菜肴也是吸引很多人前来品尝。精心挑选供应商，只使用新鲜的时令食材，保证了对顾客最真实的口味感受。这里的肉用木炭烧制，搭配天然食材，就成就了真正的美味。还有这里的甜品糕点，更是冲击你的味蕾，让你完全沉浸在其中。要是再来一杯上好的葡萄酒，那就更加完美了。
         * <p>
         * Score : 0
         * star : 0
         * stiming : 0.5
         * etiming : 1
         * busySeasonStart : 1
         * busySeasonEnd : 12
         * paytype :
         * featured :
         * tagids : 143,144,10,
         * lng : 2.3404811
         * lat : 48.8867782
         * firstchoice : 0
         * glat : 48.8876592
         * glng : 2.3363486
         * PyName :
         * recommend : 0
         * landmark : 0
         * poi_areaid : 0
         * poi_districtid : 0
         * poi_image : https://ypimg1.youpu.cn/yp/201603/16/b8f24577d36f4968a2e7389060ff6a3e.jpg@1o
         * countryName : 法国
         * poiTypeName : 餐饮
         * tagCnName : 晚餐|
         */

        private int PoiId;
        private int parentId;
        private int ranking;
        private int hotranking;
        private String positive;
        private int PoiType;
        private int PoiSubType;
        private int Level;
        private int placeId;
        private int areaId;
        private int continentId;
        private int countryId;
        private String CnCityName;
        private String EnCityName;
        private String CnName;
        private String EnName;
        private String LocalName;
        private String Address;
        private String localaddress;
        private String Traffic;
        private String Ticket;
        private String Website;
        private String openTime;
        private String Phone;
        private int picCount;
        private String Spot;
        private String tips;
        private String description;
        private int Score;
        private int star;
        private double stiming;
        private int etiming;
        private int busySeasonStart;
        private int busySeasonEnd;
        private String paytype;
        private String featured;
        private String tagids;
        private double lng;
        private double lat;
        private int firstchoice;
        private String glat;
        private String glng;
        private String PyName;
        private int recommend;
        private int landmark;
        private int poi_areaid;
        private int poi_districtid;
        private String poi_image;
        private String countryName;
        private String poiTypeName;
        private String tagCnName;

        public int getPoiId() {
            return PoiId;
        }

        public void setPoiId(int PoiId) {
            this.PoiId = PoiId;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getRanking() {
            return ranking;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
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

        public int getPoiSubType() {
            return PoiSubType;
        }

        public void setPoiSubType(int PoiSubType) {
            this.PoiSubType = PoiSubType;
        }

        public int getLevel() {
            return Level;
        }

        public void setLevel(int Level) {
            this.Level = Level;
        }

        public int getPlaceId() {
            return placeId;
        }

        public void setPlaceId(int placeId) {
            this.placeId = placeId;
        }

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public int getContinentId() {
            return continentId;
        }

        public void setContinentId(int continentId) {
            this.continentId = continentId;
        }

        public int getCountryId() {
            return countryId;
        }

        public void setCountryId(int countryId) {
            this.countryId = countryId;
        }

        public String getCnCityName() {
            return CnCityName;
        }

        public void setCnCityName(String CnCityName) {
            this.CnCityName = CnCityName;
        }

        public String getEnCityName() {
            return EnCityName;
        }

        public void setEnCityName(String EnCityName) {
            this.EnCityName = EnCityName;
        }

        public String getCnName() {
            return CnName;
        }

        public void setCnName(String CnName) {
            this.CnName = CnName;
        }

        public String getEnName() {
            return EnName;
        }

        public void setEnName(String EnName) {
            this.EnName = EnName;
        }

        public String getLocalName() {
            return LocalName;
        }

        public void setLocalName(String LocalName) {
            this.LocalName = LocalName;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getLocaladdress() {
            return localaddress;
        }

        public void setLocaladdress(String localaddress) {
            this.localaddress = localaddress;
        }

        public String getTraffic() {
            return Traffic;
        }

        public void setTraffic(String Traffic) {
            this.Traffic = Traffic;
        }

        public String getTicket() {
            return Ticket;
        }

        public void setTicket(String Ticket) {
            this.Ticket = Ticket;
        }

        public String getWebsite() {
            return Website;
        }

        public void setWebsite(String Website) {
            this.Website = Website;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public int getPicCount() {
            return picCount;
        }

        public void setPicCount(int picCount) {
            this.picCount = picCount;
        }

        public String getSpot() {
            return Spot;
        }

        public void setSpot(String Spot) {
            this.Spot = Spot;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getScore() {
            return Score;
        }

        public void setScore(int Score) {
            this.Score = Score;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public double getStiming() {
            return stiming;
        }

        public void setStiming(double stiming) {
            this.stiming = stiming;
        }

        public int getEtiming() {
            return etiming;
        }

        public void setEtiming(int etiming) {
            this.etiming = etiming;
        }

        public int getBusySeasonStart() {
            return busySeasonStart;
        }

        public void setBusySeasonStart(int busySeasonStart) {
            this.busySeasonStart = busySeasonStart;
        }

        public int getBusySeasonEnd() {
            return busySeasonEnd;
        }

        public void setBusySeasonEnd(int busySeasonEnd) {
            this.busySeasonEnd = busySeasonEnd;
        }

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public String getFeatured() {
            return featured;
        }

        public void setFeatured(String featured) {
            this.featured = featured;
        }

        public String getTagids() {
            return tagids;
        }

        public void setTagids(String tagids) {
            this.tagids = tagids;
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

        public int getFirstchoice() {
            return firstchoice;
        }

        public void setFirstchoice(int firstchoice) {
            this.firstchoice = firstchoice;
        }

        public String getGlat() {
            return glat;
        }

        public void setGlat(String glat) {
            this.glat = glat;
        }

        public String getGlng() {
            return glng;
        }

        public void setGlng(String glng) {
            this.glng = glng;
        }

        public String getPyName() {
            return PyName;
        }

        public void setPyName(String PyName) {
            this.PyName = PyName;
        }

        public int getRecommend() {
            return recommend;
        }

        public void setRecommend(int recommend) {
            this.recommend = recommend;
        }

        public int getLandmark() {
            return landmark;
        }

        public void setLandmark(int landmark) {
            this.landmark = landmark;
        }

        public int getPoi_areaid() {
            return poi_areaid;
        }

        public void setPoi_areaid(int poi_areaid) {
            this.poi_areaid = poi_areaid;
        }

        public int getPoi_districtid() {
            return poi_districtid;
        }

        public void setPoi_districtid(int poi_districtid) {
            this.poi_districtid = poi_districtid;
        }

        public String getPoi_image(boolean offline, int width, int height) {
            if (poi_image == null || poi_image.equals("") || poi_image.contains("default.png"))
                return "";
            if (!offline) {
                int begin = poi_image.indexOf("/", 8);
                int end = poi_image.indexOf("@");
                begin = begin == -1 ? 0 : begin;
                end = end == -1 ? poi_image.length() : end;
                String string = poi_image.substring(begin, end);
                return Constans.PIC_HOST +
                        string + "?imageMogr2/thumbnail/!" + width + "x" + height + "r/gravity/Center/crop/" + width + "x" + height + "/quality/50";
            } else {
                int begin = poi_image.lastIndexOf("/");
                int end = poi_image.indexOf("@");
                return poi_image.substring(begin + 1, end);
            }
        }

        public void setPoi_image(String poi_image) {
            this.poi_image = poi_image;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getPoiTypeName() {
            return poiTypeName;
        }

        public void setPoiTypeName(String poiTypeName) {
            this.poiTypeName = poiTypeName;
        }

        public String getTagCnName() {
            return tagCnName;
        }

        public void setTagCnName(String tagCnName) {
            this.tagCnName = tagCnName;
        }
    }
}
