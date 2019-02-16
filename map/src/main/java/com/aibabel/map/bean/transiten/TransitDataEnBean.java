package com.aibabel.map.bean.transiten;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/12/1.
 */

public class TransitDataEnBean implements Serializable{
    private String message;
    private TransitResultEnBean result;
    private int status;
    private int type;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TransitResultEnBean getResult() {
        return result;
    }

    public void setResult(TransitResultEnBean result) {
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
