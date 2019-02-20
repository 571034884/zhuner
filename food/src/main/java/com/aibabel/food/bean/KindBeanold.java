package com.aibabel.food.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/5 19:17
 * 功能：
 * 版本：1.0
 */
public class KindBeanold extends BaseBean {

    List<ItemKindBean> itemKindList;

    public List<ItemKindBean> getItemKindList() {
        return itemKindList;
    }

    public void setItemKindList(List<ItemKindBean> itemKindList) {
        this.itemKindList = itemKindList;
    }

    public static class ItemKindBean {
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
