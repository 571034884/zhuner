package com.aibabel.surfinternet.bean;


import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * Created by Wuqinghua on 2018/6/16 0016.
 *
 * 全球上网  获取支持的国家的列表
 */
public class TrandBean extends BaseBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * countryName : 乌克兰
         * imageUrl :
         */

        private String countryName;
        private String imageUrl;

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
