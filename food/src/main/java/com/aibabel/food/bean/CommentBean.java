package com.aibabel.food.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/6 16:50
 * 功能：
 * 版本：1.0
 */
public class CommentBean extends BaseBean {

    List<CommentItemBean> itemCommentBeanList;

    public List<CommentItemBean> getItemCommentBeanList() {
        return itemCommentBeanList;
    }

    public void setItemRecommentBeanList(List<CommentItemBean> itemCommentBeanList) {
        this.itemCommentBeanList = itemCommentBeanList;
    }

    public static class CommentItemBean {
        String manUrl;
        String manName;
        float score;
        String manSay;
        String date;
        List<String> urlList;

        public String getManUrl() {
            return manUrl;
        }

        public void setManUrl(String manUrl) {
            this.manUrl = manUrl;
        }

        public String getManName() {
            return manName;
        }

        public void setManName(String manName) {
            this.manName = manName;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public String getManSay() {
            return manSay;
        }

        public void setManSay(String manSay) {
            this.manSay = manSay;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<String> getUrlList() {
            return urlList;
        }

        public void setUrlList(List<String> urlList) {
            this.urlList = urlList;
        }
    }
}
