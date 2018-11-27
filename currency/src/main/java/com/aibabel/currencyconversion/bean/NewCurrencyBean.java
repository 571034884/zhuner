package com.aibabel.currencyconversion.bean;

import com.aibabel.currencyconversion.utils.FastJsonUtil;

import java.io.Serializable;

/**
 * 作者：SunSH on 2018/9/3 9:34
 * 功能：
 * 版本：1.0
 */
public class NewCurrencyBean implements Serializable{
    /**
     * groupEn : A
     * group : A
     * key : AED
     * zh_ch : 阿联酋迪拉姆
     * zh_tw : 阿聯迪拉姆
     * en : UAE dirham
     * jp : ＵＡＥディルハム
     * ko : 아랍에미리트 디르함
     */

    private String groupEn;
    private String group;
    private String key;
    private String zh_ch;
    private String zh_tw;
    private String en;
    private String jp;
    private String ko;

    public NewCurrencyBean() {
    }

    public NewCurrencyBean(String key, String zh_ch, String zh_tw, String en, String jp, String ko) {
        this.key = key;
        this.zh_ch = zh_ch;
        this.zh_tw = zh_tw;
        this.en = en;
        this.jp = jp;
        this.ko = ko;
    }

    public NewCurrencyBean(String json) {
        NewCurrencyBean bean = FastJsonUtil.changeJsonToBean(json, NewCurrencyBean.class);
        this.key = bean.getKey();
        this.zh_ch = bean.getZh_ch();
        this.zh_tw = bean.getZh_tw();
        this.en = bean.getEn();
        this.jp = bean.getJp();
        this.ko = bean.getKo();
    }

    public String getGroupEn() {
        return groupEn;
    }

    public void setGroupEn(String groupEn) {
        this.groupEn = groupEn;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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
