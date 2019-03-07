package com.aibabel.translate.bean;

public class MessageBean {

    /**
     * code : 0
     * from : zh
     * to : en
     * trans_text : 你好很高兴认识你欢迎来到中国
     * trans_result : Hello. Nice to meet you. Welcome to China.
     */

    private int code;
    private String from;
    private String to;
    private String trans_text;
    private String trans_result;
    private boolean isChecked;

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
}
