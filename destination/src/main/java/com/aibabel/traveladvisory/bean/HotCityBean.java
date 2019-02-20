package com.aibabel.traveladvisory.bean;

import com.aibabel.traveladvisory.app.Constans;
import com.aibabel.traveladvisory.okgo.BaseBean;

import java.util.List;

/**
 * 作者：SunSH on 2018/6/20 9:21
 * 功能：
 * 版本：1.0
 */
public class HotCityBean extends BaseBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * imageCityUrl :
         * type : City
         * Id :
         * EnName : Paris
         * Name : 巴黎
         */

        private String imageCityUrl;
        private String type;
        private String Id;
        private String EnName;
        private String Name;

        public String getImageCityUrl(boolean offline, int width, int height) {
            if (imageCityUrl == null||imageCityUrl.equals("")||imageCityUrl.contains("default.png"))
                return "";
            if (!offline) {
                int begin = imageCityUrl.indexOf("/", 8);
                int end = imageCityUrl.indexOf("@");
                begin = begin == -1 ? 0 : begin;
                end = end == -1 ? imageCityUrl.length() : end;
                String string = imageCityUrl.substring(begin, end);
                return Constans.PIC_HOST +
                        string + "?imageMogr2/thumbnail/!" + width + "x" + height + "r/gravity/Center/crop/" + width + "x" + height + "/quality/50";
            } else {
                int begin = imageCityUrl.lastIndexOf("/");
                if (imageCityUrl.contains("@")) {
                    return imageCityUrl.substring(begin + 1, imageCityUrl.indexOf("@"));
                } else {
                    return imageCityUrl.substring(begin + 1, imageCityUrl.length());
                }
            }
        }

        public void setImageCityUrl(String imageCityUrl) {
            this.imageCityUrl = imageCityUrl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getEnName() {
            return EnName;
        }

        public void setEnName(String EnName) {
            this.EnName = EnName;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }
}
