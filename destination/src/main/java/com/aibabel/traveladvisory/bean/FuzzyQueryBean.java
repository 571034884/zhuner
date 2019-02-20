package com.aibabel.traveladvisory.bean;

import com.aibabel.traveladvisory.app.Constans;
import com.aibabel.traveladvisory.okgo.BaseBean;

import java.util.List;

/**
 * 作者：SunSH on 2018/6/16 19:23
 * 功能：
 * 版本：1.0
 */
public class FuzzyQueryBean extends BaseBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * CnName : 日本
         * placeId : 135
         * poi_image :
         * CnCityName :
         * poiTypeName :
         * ranking : 0
         * explain :
         * type : 国家概览
         */

        private String CnName;
        private int placeId;
        private String poi_image;
        private String CnCityName;
        private String poiTypeName;
        private int ranking;
        private String explain;
        private String type;

        public String getCnName() {
            return CnName;
        }

        public void setCnName(String CnName) {
            this.CnName = CnName;
        }

        public int getPlaceId() {
            return placeId;
        }

        public void setPlaceId(int placeId) {
            this.placeId = placeId;
        }

        public String getPoi_image(int width, int height) {
            if (poi_image == null||poi_image.equals("")||poi_image.contains("default.png"))
                return "";
            int begin = poi_image.indexOf("/", 8);
            int end = poi_image.indexOf("@");
            begin = begin == -1 ? 0 : begin;
            end = end == -1 ? poi_image.length() : end;
            String string = poi_image.substring(begin, end);
            return Constans.PIC_HOST +
                    string + "?imageMogr2/thumbnail/!" + width + "x" + height + "r/gravity/Center/crop/" + width + "x" + height + "/quality/50";

        }

        public void setPoi_image(String poi_image) {
            this.poi_image = poi_image;
        }

        public String getCnCityName() {
            return CnCityName;
        }

        public void setCnCityName(String CnCityName) {
            this.CnCityName = CnCityName;
        }

        public String getPoiTypeName() {
            return poiTypeName;
        }

        public void setPoiTypeName(String poiTypeName) {
            this.poiTypeName = poiTypeName;
        }

        public int getRanking() {
            return ranking;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
