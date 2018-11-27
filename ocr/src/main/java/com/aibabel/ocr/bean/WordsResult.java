package com.aibabel.ocr.bean;

import java.util.List;

/**
 * Created by zuoliangzhu on 17/3/23.
 */

public class WordsResult {

    //location
    private int left;
    private int top;
    private int width;
    private int height;
    //words
    private String words;

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public int getHeight() {

        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {

        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getTop() {

        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getLeft() {

        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

}
