package com.aibabel.food.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/5 9:51
 * 功能：
 * 版本：1.0
 */
public class HomePageBean extends BaseBean {

    List<PartBean> partBeanList;

    public List<PartBean> getPartBeanList() {
        return partBeanList;
    }

    public void setPartBeanList(List<PartBean> partBeanList) {
        this.partBeanList = partBeanList;
    }

    public static class PartBean {
        String partName;
        int partType;
        List<PartItemBean> partItemBeanList;

        public String getPartName() {
            return partName;
        }

        public void setPartName(String partName) {
            this.partName = partName;
        }

        public int getPartType() {
            return partType;
        }

        public void setPartType(int partType) {
            this.partType = partType;
        }

        public List<PartItemBean> getPartItemBeanList() {
            return partItemBeanList;
        }

        public void setPartItemBeanList(List<PartItemBean> partItemBeanList) {
            this.partItemBeanList = partItemBeanList;
        }

        public static class PartItemBean {
            String name;
            String iconUrl;
            String tip;
            String manUrl;
            String manSay;
            String manPay;
            float score;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getTip() {
                return tip;
            }

            public void setTip(String tip) {
                this.tip = tip;
            }

            public String getManUrl() {
                return manUrl;
            }

            public void setManUrl(String manUrl) {
                this.manUrl = manUrl;
            }

            public String getManSay() {
                return manSay;
            }

            public void setManSay(String manSay) {
                this.manSay = manSay;
            }

            public String getManPay() {
                return manPay;
            }

            public void setManPay(String manPay) {
                this.manPay = manPay;
            }

            public float getScore() {
                return score;
            }

            public void setScore(float score) {
                this.score = score;
            }
        }
    }

}
