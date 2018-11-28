package com.aibabel.readme.bean;

/**
 * 作者：wuqinghua_fyt on 2018/8/30 10:32
 * 功能：
 * 版本：1.0
 */
public class ReadmeItemBean {
    private int type ;
    private String item_name ;

    public ReadmeItemBean(int type, String item_name) {
        this.type = type;
        this.item_name = item_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

}
