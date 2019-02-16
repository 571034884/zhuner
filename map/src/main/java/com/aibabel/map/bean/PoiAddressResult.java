package com.aibabel.map.bean;

import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.map.bean.search.AddressResult;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/12/10.
 */

public class PoiAddressResult extends BaseBean implements Serializable{
    private AddressResult data;

    public AddressResult getData() {
        return data;
    }

    public void setData(AddressResult data) {
        this.data = data;
    }
}
