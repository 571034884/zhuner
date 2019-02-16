package com.aibabel.map.bean.trafficen;

import com.aibabel.baselibrary.http.BaseBean;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/11/30.
 */

public class EnBean extends BaseBean implements Serializable{
    private DataEnBean data;

    public DataEnBean getData() {
        return data;
    }

    public void setData(DataEnBean data) {
        this.data = data;
    }
}
