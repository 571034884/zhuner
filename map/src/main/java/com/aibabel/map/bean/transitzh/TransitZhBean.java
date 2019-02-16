package com.aibabel.map.bean.transitzh;

import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.map.bean.trafficzh.DataZhBean;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/11/29.
 */

public class TransitZhBean extends BaseBean implements Serializable{
    private TransitDataZhBean data;

    public TransitDataZhBean getData() {
        return data;
    }

    public void setData(TransitDataZhBean data) {
        this.data = data;
    }
}
