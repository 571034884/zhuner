package com.aibabel.ocr.bean;

import java.util.List;

/**
 * =====================================================================
 * <p>
 * 作者 : 张文颖
 * <p>
 * 时间: 2018/4/2
 * <p>
 * 描述:
 * <p>
 * =====================================================================
 */

public class ObjectResponseBean {


    /**
     * error_code : 0
     * data : [{"score":0.943346,"root":"⾮非⾃自然图像-屏幕截图","keyword":"屏幕截图"},{"score":0.577355,"root":"⾮非⾃自然图像-⽂文字图","keyword":"⽂文字图⽚片"},{"score":0.385177,"root":"⾮非⾃自然图像-⽂文字图","keyword":"发票"},{"score":0.193164,"root":"商品-其他","keyword":"试题"},{"score":0.002261,"root":"⾮非⾃自然图像-⽂文字图","keyword":"准考证"}]
     */

    private int error_code;
    private List<DataBean> data;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * score : 0.943346
         * root : ⾮非⾃自然图像-屏幕截图
         * keyword : 屏幕截图
         */

        private double score;
        private String root;
        private String keyword;
        private String imgUrl;
        private String audioUrl;
        private String detail;
        private String pic;
        private String baike;
        private String text;


        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getRoot() {
            return root;
        }

        public void setRoot(String root) {
            this.root = root;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getAudioUrl() {
            return audioUrl;
        }

        public void setAudioUrl(String audioUrl) {
            this.audioUrl = audioUrl;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getBaike() {
            return baike;
        }

        public void setBaike(String baike) {
            this.baike = baike;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
