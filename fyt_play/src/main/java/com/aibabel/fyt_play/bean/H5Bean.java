package com.aibabel.fyt_play.bean;

import com.aibabel.baselibrary.http.BaseBean;

/**
 * 作者：wuqinghua_fyt on 2019/1/15 15:00
 * 功能：
 * 版本：1.0
 */
public class H5Bean extends BaseBean {


    /**
     * data : {"pageIndex":"http://destination.cdn.aibabel.com/play/H5/punchClock.html","js":"http://destination.cdn.aibabel.com/play/H5/jquery-1.4.4.min.js","css":"http://destination.cdn.aibabel.com/play/H5/reset.css","orderDetials":"http://destination.cdn.aibabel.com/play/H5/orderDetails.html"}
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
         * pageIndex : http://destination.cdn.aibabel.com/play/H5/punchClock.html
         * js : http://destination.cdn.aibabel.com/play/H5/jquery-1.4.4.min.js
         * css : http://destination.cdn.aibabel.com/play/H5/reset.css
         * orderDetials : http://destination.cdn.aibabel.com/play/H5/orderDetails.html
         */

        private String pageIndex;
        private String js;
        private String css;
        private String orderDetials;

        public String getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(String pageIndex) {
            this.pageIndex = pageIndex;
        }

        public String getJs() {
            return js;
        }

        public void setJs(String js) {
            this.js = js;
        }

        public String getCss() {
            return css;
        }

        public void setCss(String css) {
            this.css = css;
        }

        public String getOrderDetials() {
            return orderDetials;
        }

        public void setOrderDetials(String orderDetials) {
            this.orderDetials = orderDetials;
        }
    }
}
