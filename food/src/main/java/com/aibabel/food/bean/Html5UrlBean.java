package com.aibabel.food.bean;

import com.aibabel.baselibrary.http.BaseBean;

/**
 * 作者：SunSH on 2018/12/19 17:09
 * 功能：
 * 版本：1.0
 */
public class Html5UrlBean extends BaseBean {
    /**
     * data : {"pageCateDetail":"http://destination.cdn.aibabel.com/food/H5/cateDetail1812190921.html","pagefoodSoft":"http://destination.cdn.aibabel.com/food/H5/foodSoft1812191700.html"}
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
         * pageCateDetail : http://destination.cdn.aibabel.com/food/H5/cateDetail1812190921.html
         * pagefoodSoft : http://destination.cdn.aibabel.com/food/H5/foodSoft1812191700.html
         */

        private String pageCateDetail;
        private String pagefoodSoft;

        public String getPageCateDetail() {
            return pageCateDetail;
        }

        public void setPageCateDetail(String pageCateDetail) {
            this.pageCateDetail = pageCateDetail;
        }

        public String getPagefoodSoft() {
            return pagefoodSoft;
        }

        public void setPagefoodSoft(String pagefoodSoft) {
            this.pagefoodSoft = pagefoodSoft;
        }
    }
}
