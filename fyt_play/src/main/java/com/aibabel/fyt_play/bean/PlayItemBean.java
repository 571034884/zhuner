package com.aibabel.fyt_play.bean;

/**
 * 作者：wuqinghua_fyt on 2018/12/11 16:40
 * 功能：
 * 版本：1.0
 */
public class PlayItemBean {
    private int item_img ;
    private String item_title ;
    private String item_context ;

    public PlayItemBean(int item_img, String item_title, String item_context) {
        this.item_img = item_img;
        this.item_title = item_title;
        this.item_context = item_context;
    }

    public PlayItemBean() {
        super();
    }

    public int getItem_img() {
        return item_img;
    }

    public void setItem_img(int item_img) {
        this.item_img = item_img;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_context() {
        return item_context;
    }

    public void setItem_context(String item_context) {
        this.item_context = item_context;
    }
}
