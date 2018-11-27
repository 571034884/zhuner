package com.aibabel.ocr.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Wuqinghua on 2018/3/29 0029.
 */

public class LanguageBean {
    //是否 单语言  1 单 2 多
    private int tag ;
//    // 麦克 图标
//    private Drawable img_Mike;
    //喇叭图标
    private Drawable img_horn;
    //选择的语言
    private String language;
    // 语言的分类
    private String language_classification;
    //语言标识
    private String name_str;
    //语言trans
    private String trans;
    // 选择的对勾图标
    private Drawable Choice;
    // 二级语言按钮
    private Drawable elv;

    public LanguageBean() {
        super();
    }

    @Override
    public String toString() {
        return "NewLanguageBean{" +
                "tag=" + tag +
                ", img_horn=" + img_horn +
                ", language='" + language + '\'' +
                ", language_classification='" + language_classification + '\'' +
                ", name_str='" + name_str + '\'' +
                ", trans='" + trans + '\'' +
                ", Choice=" + Choice +
                ", elv=" + elv +
                '}';
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public Drawable getImg_horn() {
        return img_horn;
    }

    public void setImg_horn(Drawable img_horn) {
        this.img_horn = img_horn;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage_classification() {
        return language_classification;
    }

    public void setLanguage_classification(String language_classification) {
        this.language_classification = language_classification;
    }

    public String getName_str() {
        return name_str;
    }

    public void setName_str(String name_str) {
        this.name_str = name_str;
    }

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public Drawable getChoice() {
        return Choice;
    }

    public void setChoice(Drawable choice) {
        Choice = choice;
    }

    public Drawable getElv() {
        return elv;
    }

    public void setElv(Drawable elv) {
        this.elv = elv;
    }

    public LanguageBean(int tag, Drawable img_horn, String language, String language_classification, String name_str, String trans, Drawable choice, Drawable elv) {
        this.tag = tag;
        this.img_horn = img_horn;
        this.language = language;
        this.language_classification = language_classification;
        this.name_str = name_str;
        this.trans = trans;
        Choice = choice;
        this.elv = elv;
    }
}
