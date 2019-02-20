package com.aibabel.traveladvisory.bean;

import android.util.Log;

import com.aibabel.traveladvisory.app.Constans;
import com.aibabel.traveladvisory.okgo.BaseBean;

import java.util.List;

/**
 * 作者：SunSH on 2018/6/19 20:35
 * 功能：
 * 版本：1.0
 */
public class HotBean extends BaseBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * countryName : 日本
         * countryId :
         * countryEnName : Japan
         * imageCountryUrl : https://ypimg1.youpu.cn/album/Japan/20140730/53d8c9cc0b95e.jpg@640w_1000h_1c_1e_1o
         */

        private String countryName;
        private String countryId;
        private String countryEnName;
        private String imageCountryUrl;

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getCountryEnName() {
            return countryEnName;
        }

        public void setCountryEnName(String countryEnName) {
            this.countryEnName = countryEnName;
        }

        public String getImageCountryUrl(int width, int height) {
            if (imageCountryUrl == null||imageCountryUrl.equals("")||imageCountryUrl.contains("default.png"))
                return "";
            int begin = imageCountryUrl.indexOf("/", 8);
            int end = imageCountryUrl.indexOf("@");
            begin = begin == -1 ? 0 : begin;
            end = end == -1 ? imageCountryUrl.length() : end;
            String string = imageCountryUrl.substring(begin, end);
            //在线返回
//            return Constans.PIC_HOST +
//                    string + "?imageMogr2/thumbnail/!" + width + "x" + height + "r/gravity/Center/crop/" + width + "x" + height + "/quality/50";
            //离线返回
            String offlineString = imageCountryUrl.substring(begin, end);
            int offlineBegin = offlineString.lastIndexOf("/");
            Log.e("getImageCountryUrl: ", offlineString.substring(offlineBegin + 1, offlineString.length()) + width + "x" + height);
            return offlineString.substring(offlineBegin + 1, offlineString.length() - 4) + width + "x" + height;
        }

        public void setImageCountryUrl(String imageCountryUrl) {
            this.imageCountryUrl = imageCountryUrl;
        }
    }
}
