package com.aibabel.food.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/15 11:34
 * 功能：
 * 版本：1.0
 */
public class KindBean extends BaseBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 11110003
         * name_cn : 烤串
         * image_url : https://ypimg1.youpu.cn/mealPic/201606/24/cb7992c8cd5f60ac038dfac7a86bef2b.jpg
         */

        private String id;
        private String name_cn;
        private String image_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName_cn() {
            return name_cn;
        }

        public void setName_cn(String name_cn) {
            this.name_cn = name_cn;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }
}
