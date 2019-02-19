package com.aibabel.speech.bean;

/**
 * Created by 冯凯 on 2017/12/15.
 */

public class ResultBean {
    /**
     * id : 1234
     * f : ch_ch
     * t : jpa
     * audio : spx16
     * speed : normal
     * gender : m
     * dev : IMSFICNNDDOUVNCNR
     */

    private String id;
    private String f;
    private String t;
    private String audio;
    private String speed;
    private String gender;
    private String dev;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }
}
