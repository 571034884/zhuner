package com.aibabel.map.bean.trafficen;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/11/30.
 */

public  class DataEnBean implements Serializable{
    private String message;
    private ResultEnBean result;
    private int status;
    private int type;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultEnBean getResult() {
        return result;
    }

    public void setResult(ResultEnBean result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
