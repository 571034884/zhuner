package com.aibabel.translate.bean;

/**
 * Created by Wuqinghua on 2018/5/17 0017.
 */
public class ErrorResultBean {

    /**
     * id : 会话ID
     * ecode : 错误编码
     */

    private String id;
    private String ecode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }
}
