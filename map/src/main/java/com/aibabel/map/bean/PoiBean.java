package com.aibabel.map.bean;


import com.aibabel.baselibrary.http.BaseBean;

/**
 * 作者：SunSH on 2018/6/20 14:51
 * 功能：
 * 版本：1.0
 */
public class PoiBean extends BaseBean {

    /**
     * data : {"PoiId":125760,"parentId":0,"ranking":1,"hotranking":999999,"positive":"60%","PoiType":10,"PoiSubType":17,"Level":3,"placeId":365,"areaId":0,"continentId":5,"countryId":148,"CnCityName":"里昂","EnCityName":"Lyon","CnName":"圣让大教堂","EnName":"Cathédrale St-Jean","LocalName":"","Address":"place St-Jean, 5e","localaddress":"","Traffic":"地铁D线在Saint-Jean下车。","Ticket":"","Website":"","openTime":"周一-五 08:00-12:00 14:00-19:30 ；周末 14:00-17:00","Phone":"(33-4)78422825","picCount":0,"Spot":"兼具罗曼和哥特式风格的主教大教堂。","tips":"教堂内一座15世纪的天文钟是标志，12点、 14点、15点和16点会报时，钟内小人会有表演。\r\n礼拜期间不允许游客参观。\r\n每逢灯节人会很多。","description":"圣让大教堂位于索恩河畔，历史悠久，教皇约翰二十二世加冕典礼和法王亨利四世与王后玛丽·德·美第奇的婚典都在此举行。教堂整体的建筑风格偏向罗马式风格，然而教堂正面属于哥特式风格，其中引人注目的是由280块石徽装饰的窗户。\r\n教堂位于老城区，旁边的步行街也值得参观。","Score":0,"star":0,"stiming":0.5,"etiming":1,"busySeasonStart":1,"busySeasonEnd":12,"paytype":"","featured":"","tagids":"12,","lng":4.827401340247,"lat":45.7607488099177,"firstchoice":0,"glat":"45.7608008","glng":"4.8272903","PyName":"","recommend":0,"landmark":0,"poi_areaid":708,"poi_districtid":142,"poi_image":"","countryName":"法国","poiTypeName":"景点","tagCnName":"建筑|"}
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
         * PoiId : 125760
         * parentId : 0
         * ranking : 1
         * hotranking : 999999
         * positive : 60%
         * PoiType : 10
         * PoiSubType : 17
         * Level : 3
         * placeId : 365
         * areaId : 0
         * continentId : 5
         * countryId : 148
         * CnCityName : 里昂
         * EnCityName : Lyon
         * CnName : 圣让大教堂
         * EnName : Cathédrale St-Jean
         * LocalName :
         * Address : place St-Jean, 5e
         * localaddress :
         * Traffic : 地铁D线在Saint-Jean下车。
         * Ticket :
         * Website :
         * openTime : 周一-五 08:00-12:00 14:00-19:30 ；周末 14:00-17:00
         * Phone : (33-4)78422825
         * picCount : 0
         * Spot : 兼具罗曼和哥特式风格的主教大教堂。
         * tips : 教堂内一座15世纪的天文钟是标志，12点、 14点、15点和16点会报时，钟内小人会有表演。
         礼拜期间不允许游客参观。
         每逢灯节人会很多。
         * description : 圣让大教堂位于索恩河畔，历史悠久，教皇约翰二十二世加冕典礼和法王亨利四世与王后玛丽·德·美第奇的婚典都在此举行。教堂整体的建筑风格偏向罗马式风格，然而教堂正面属于哥特式风格，其中引人注目的是由280块石徽装饰的窗户。
         教堂位于老城区，旁边的步行街也值得参观。
         * Score : 0
         * star : 0
         * stiming : 0.5
         * etiming : 1
         * busySeasonStart : 1
         * busySeasonEnd : 12
         * paytype :
         * featured :
         * tagids : 12,
         * lng : 4.827401340247
         * lat : 45.7607488099177
         * firstchoice : 0
         * glat : 45.7608008
         * glng : 4.8272903
         * PyName :
         * recommend : 0
         * landmark : 0
         * poi_areaid : 708
         * poi_districtid : 142
         * poi_image :
         * countryName : 法国
         * poiTypeName : 景点
         * tagCnName : 建筑|
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

        public String getPoi_image() {
            return poi_image;
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
