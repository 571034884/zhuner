package com.aibabel.travel.bean;

public class DetailBean {

    private String audioUrl;
    private String imageUrl;
    private String name;

    private boolean selector ;
    private boolean islast;

    public boolean isIslast() {
        return islast;
    }

    public void setIslast(boolean islast) {
        this.islast = islast;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isSelector() {
        return selector;
    }

    public void setSelector(boolean selector) {
        this.selector = selector;
    }

    public DetailBean(String audioUrl, String imageUrl, String name, boolean selector, boolean islast) {
        this.audioUrl = audioUrl;
        this.imageUrl = imageUrl;
        this.name = name;
        this.selector = selector;
        this.islast = islast;
    }

    public DetailBean() {
        super();
    }
}
