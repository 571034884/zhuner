package com.aibabel.map.bean.trafficzh;

import android.os.Parcelable;

import com.aibabel.baselibrary.http.BaseBean;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/11/29.
 */

public class ZhBean extends BaseBean implements Serializable{
    private DataZhBean data;

    public DataZhBean getData() {
        return data;
    }

    public void setData(DataZhBean data) {
        this.data = data;
    }
}
