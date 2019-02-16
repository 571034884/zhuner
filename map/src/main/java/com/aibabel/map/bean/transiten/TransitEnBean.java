package com.aibabel.map.bean.transiten;

import com.aibabel.baselibrary.http.BaseBean;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/12/1.
 */

public class TransitEnBean extends BaseBean implements Serializable{

    private TransitDataEnBean data;

    public TransitDataEnBean getData() {
        return data;
    }

    public void setData(TransitDataEnBean data) {
        this.data = data;
    }
}
