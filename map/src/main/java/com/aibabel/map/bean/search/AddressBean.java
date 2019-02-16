package com.aibabel.map.bean.search;

import com.aibabel.baselibrary.http.BaseBean;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/11/27.
 */

public class AddressBean extends BaseBean implements Serializable{

    private AddressData data;

    public AddressData getData() {
        return data;
    }

    public void setData(AddressData data) {
        this.data = data;
    }
}
