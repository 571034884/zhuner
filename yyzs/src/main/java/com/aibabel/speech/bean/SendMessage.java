package com.aibabel.speech.bean;

/**
 * Created by zhenzi on 2017/7/23.
 */

public class SendMessage {

    String cmd;
    String id;
    String dev;
    String lan;
    String info;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "SendMessage [cmd=" + cmd + ", id=" + id + ", dev=" + dev + ", lan=" + lan + ", info=" + info + "]";
    }
}
