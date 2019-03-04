package com.aibabel.locationservice.bean;

/**
 * 作者：wuqinghua_fyt on 2018/12/28 9:26
 * 功能：
 * 版本：1.0
 */
public class PushBean {
    private String message;
    private String type;
    private String title ;
    private String mesId;
    private String content;

    public PushBean() {
        super();
    }


    public String getMesId() {
        return mesId;
    }

    public void setMesId(String mesId) {
        this.mesId = mesId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PushBean(String message, String type, String title, String mesId, String content) {
        this.message = message;
        this.type = type;
        this.title = title;
        this.mesId = mesId;
        this.content = content;
    }
}
