package com.aibabel.ocr.widgets;


public class CardItem {

    private String mTextResource;
    private String mTitleResource;
    private String mUrlResource;
    private String mAudioUrl;

    public CardItem(String title, String text, String url, String audioUrl) {
        mTitleResource = title;
        mTextResource = text;
        mUrlResource = url;
        mAudioUrl = audioUrl;
    }

    public String getmTextResource() {
        return mTextResource;
    }

    public void setmTextResource(String mTextResource) {
        this.mTextResource = mTextResource;
    }

    public String getmTitleResource() {
        return mTitleResource;
    }

    public void setmTitleResource(String mTitleResource) {
        this.mTitleResource = mTitleResource;
    }

    public void setmUrlResource(String mUrlResource) {
        this.mUrlResource = mUrlResource;
    }

    public String getText() {
        return mTextResource;
    }

    public String getTitle() {
        return mTitleResource;
    }

    public String getmUrlResource() {
        return mUrlResource;
    }

    public String getmAudioUrl() {
        return mAudioUrl;
    }

    public void setmAudioUrl(String mAudioUrl) {
        this.mAudioUrl = mAudioUrl;
    }
}
