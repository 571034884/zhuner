package com.aibabel.translate.bean;

import org.litepal.crud.DataSupport;

public class MessageBean extends DataSupport {

    /**
     * code : 0
     * from : zh
     * to : en
     * trans_text : 你好很高兴认识你欢迎来到中国
     * trans_result : Hello. Nice to meet you. Welcome to China.
     */
    private int id;
    private int code;
    private String from;
    private String to;
    private String trans_text;
    private String trans_result;
    private boolean isChecked;
    private long time;

    public MessageBean() {
    }

    public MessageBean(int code, String from, String to, String trans_text, String trans_result, boolean isChecked, long time) {
        this.code = code;
        this.from = from;
        this.to = to;
        this.trans_text = trans_text;
        this.trans_result = trans_result;
        this.isChecked = isChecked;
        this.time = time;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTrans_text() {
        return trans_text;
    }

    public void setTrans_text(String trans_text) {
        this.trans_text = trans_text;
    }

    public String getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(String trans_result) {
        this.trans_result = trans_result;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
