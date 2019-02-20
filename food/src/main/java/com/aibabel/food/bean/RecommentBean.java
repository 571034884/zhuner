package com.aibabel.food.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/6 16:50
 * 功能：
 * 版本：1.0
 */
public class RecommentBean extends BaseBean {

    List<RecommentItemBean> itemRecommentBeanList;

    public List<RecommentItemBean> getItemRecommentBeanList() {
        return itemRecommentBeanList;
    }

    public void setItemRecommentBeanList(List<RecommentItemBean> itemRecommentBeanList) {
        this.itemRecommentBeanList = itemRecommentBeanList;
    }

    public static class RecommentItemBean {
        String url;
        String name;
        String manPay;
        String introduce;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getManPay() {
            return manPay;
        }

        public void setManPay(String manPay) {
            this.manPay = manPay;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }
    }
}
