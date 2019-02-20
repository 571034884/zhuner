package com.aibabel.traveladvisory.bean;

import android.util.Log;

import com.aibabel.traveladvisory.app.Constans;
import com.aibabel.traveladvisory.okgo.BaseBean;

import java.util.List;

/**
 * 作者：SunSH on 2018/6/18 17:09
 * 功能：
 * 版本：1.0
 */
public class WorldCountryBean extends BaseBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * stateId : 4
         * countryState : [{"countryname":"斐济","countryid":"127","state":"4"},{"countryname":"新西兰","countryid":"134","state":"4"},{"countryname":"帕劳","countryid":"112","state":"4"}]
         */

        private String stateId;
        private List<CountryStateBean> countryState;

        public String getStateId() {
            return stateId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }

        public List<CountryStateBean> getCountryState() {
            return countryState;
        }

        public void setCountryState(List<CountryStateBean> countryState) {
            this.countryState = countryState;
        }

        public static class CountryStateBean {
            /**
             * countryname : 斐济
             * countryid : 127
             * state : 4
             * countryEnName :
             * imageCountryUrl :
             */

            private String countryname;
            private String countryid;
            private String state;
            private String countryEnName;
            private String imageCountryUrl;

            public String getCountryname() {
                return countryname;
            }

            public void setCountryname(String countryname) {
                this.countryname = countryname;
            }

            public String getCountryid() {
                return countryid;
            }

            public void setCountryid(String countryid) {
                this.countryid = countryid;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getCountryEnName() {
                return countryEnName;
            }

            public void setCountryEnName(String countryEnName) {
                this.countryEnName = countryEnName;
            }

            public String getImageCountryUrl(int width, int height) {
                if (imageCountryUrl == null ||imageCountryUrl.equals("")||imageCountryUrl.contains("default.png"))
                    return "";
                int begin = imageCountryUrl.indexOf("/", 8);
                int end = imageCountryUrl.indexOf("@");
                begin = begin == -1 ? 0 : begin;
                end = end == -1 ? imageCountryUrl.length() : end;
                String string = imageCountryUrl.substring(begin, end);
                //在线返回
//                return Constans.PIC_HOST +
//                        string + "?imageMogr2/thumbnail/!" + width + "x" + height + "r/gravity/Center/crop/" + width + "x" + height + "/quality/50";
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
}
