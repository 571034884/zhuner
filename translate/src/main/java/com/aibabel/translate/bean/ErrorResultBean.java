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

    //===================上面的为socket使用，下面的为websocket使用=====================
    /**
     * sessionId :
     * info : You recommend a small dish. I have a strange taste.
     * code : 200
     */

    private String sessionId;
    private String info;
    private int code;




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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
