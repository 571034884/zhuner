package com.aibabel.food.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/15 10:29
 * 功能：
 * 版本：1.0
 */
public class FilterBean extends BaseBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * shopName : 小犬纯二郎
         * shopId : aaabbccdd
         * score : 2
         * perperson : 11
         * tagJson : 火锅，海鲜
         * picUrl :
         */

        private String shopName;
        private String shopId;
        private String score;
        private String perperson;
        private String tagJson;
        private String picUrl;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getPerperson() {
            return perperson;
        }

        public void setPerperson(String perperson) {
            this.perperson = perperson;
        }

        public String getTagJson() {
            return tagJson;
        }

        public void setTagJson(String tagJson) {
            this.tagJson = tagJson;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }
    }
}
