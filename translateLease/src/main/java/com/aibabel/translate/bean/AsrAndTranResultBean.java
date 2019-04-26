package com.aibabel.translate.bean;

/**
 * Created by SunSH on 2017/12/15.
 */

public class AsrAndTranResultBean {
    /**
     * id : 会话ID
     * status : asr
     * info : 识别文本
     */

    private String id;
    private String status;
    private String info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
