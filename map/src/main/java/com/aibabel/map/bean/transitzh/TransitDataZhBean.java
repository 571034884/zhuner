package com.aibabel.map.bean.transitzh;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/11/30.
 */

public class TransitDataZhBean implements Serializable{

    private String message;
    private int status;
    private TransitResultZhBean result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TransitResultZhBean getResult() {
        return result;
    }

    public void setResult(TransitResultZhBean result) {
        this.result = result;
    }
}
