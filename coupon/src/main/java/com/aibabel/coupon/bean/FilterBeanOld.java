package com.aibabel.coupon.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.time.temporal.ValueRange;
import java.util.List;

/**
 * 作者：SunSH on 2018/12/6 10:45
 * 功能：
 * 版本：1.0
 */
public class FilterBeanOld extends BaseBean {

    public List<FilterItemBean> getFilterItemBeanList() {
        return filterItemBeanList;
    }

    public void setFilterItemBeanList(List<FilterItemBean> filterItemBeanList) {
        this.filterItemBeanList = filterItemBeanList;
    }

    List<FilterItemBean> filterItemBeanList;

    public static class FilterItemBean{
        String url;
        String name;
        float score;
        String manPay;
        String tip;

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

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public String getManPay() {
            return manPay;
        }

        public void setManPay(String manPay) {
            this.manPay = manPay;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }
    }
}
