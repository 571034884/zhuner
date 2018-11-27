package com.aibabel.currencyconversion.bean;

import com.aibabel.currencyconversion.utils.FastJsonUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：SunSH on 2018/5/17 21:35
 * 功能：币种jsonbean
 * 版本：1.0
 */
public class CurrencyBean {

    /**
     * group : A
     * child : [{"key":"AED","zh_ch":"迪拉姆","zh_tw":"","en":"","jp":"","ko":""},{"key":"ARS","zh_ch":"阿根廷比索","zh_tw":"","en":"","jp":"","ko":""},{"key":"AUD","zh_ch":"澳元","zh_tw":"","en":"","jp":"","ko":""}]
     */

    private String group;
    private List<ChildBean> child;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    public static class ChildBean implements Serializable{
        /**
         * key : AED
         * zh_ch : 迪拉姆
         * zh_tw :
         * en :
         * jp :
         * ko :
         */

        private String key;
        private String zh_ch;
        private String zh_tw;
        private String en;
        private String jp;
        private String ko;

        public ChildBean() {
        }

        public ChildBean(String key, String zh_ch, String zh_tw, String en, String jp, String ko) {
            this.key = key;
            this.zh_ch = zh_ch;
            this.zh_tw = zh_tw;
            this.en = en;
            this.jp = jp;
            this.ko = ko;
        }

        public ChildBean(String json) {
            ChildBean bean = FastJsonUtil.changeJsonToBean(json, ChildBean.class);
            this.key = bean.getKey();
            this.zh_ch = bean.getZh_ch();
            this.zh_tw = bean.getZh_tw();
            this.en = bean.getEn();
            this.jp = bean.getJp();
            this.ko = bean.getKo();
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getZh_ch() {
            return zh_ch;
        }

        public void setZh_ch(String zh_ch) {
            this.zh_ch = zh_ch;
        }

        public String getZh_tw() {
            return zh_tw;
        }

        public void setZh_tw(String zh_tw) {
            this.zh_tw = zh_tw;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getJp() {
            return jp;
        }

        public void setJp(String jp) {
            this.jp = jp;
        }

        public String getKo() {
            return ko;
        }

        public void setKo(String ko) {
            this.ko = ko;
        }

    }
}
