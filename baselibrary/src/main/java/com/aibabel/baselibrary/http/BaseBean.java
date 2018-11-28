package com.aibabel.baselibrary.http;

/**
 * 作者：SunSH on 2018/6/15 15:14
 * 功能：
 * 版本：1.0
 */
public class BaseBean {
    /**
     * code : 0
     * msg : success
     * data : {}
     */

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
