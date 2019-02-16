package com.aibabel.map.bean.trafficzh;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/11/29.
 */

public class DataZhBean implements Serializable{
    private String message;
    private ResultZhBean result;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultZhBean getResult() {
        return result;
    }

    public void setResult(ResultZhBean result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
