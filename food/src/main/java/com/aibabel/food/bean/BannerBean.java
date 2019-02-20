package com.aibabel.food.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/13 14:41
 * 功能：
 * 版本：1.0
 */
public class BannerBean extends BaseBean {
    private List<BannersBean> banners;

    public static class BannersBean {
        /**
         * id : 123
         * url : 123
         * article : http://mini.eastday.com/a/18105819.html?qid=02157
         */

        private String id;
        private String imageUrl;
        private String articleUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getArticleUrl() {
            return articleUrl;
        }

        public void setArticleUrl(String articleUrl) {
            this.articleUrl = articleUrl;
        }
    }
}
