package com.aibabel.download.offline.bean;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DownViewBean
{
    private String key;

    public String getDown_url() {
        return down_url;
    }

    public void setDown_url(String down_url) {
        this.down_url = down_url;
    }

    private String down_url;

    public String getStatus() {
        if (status==null) {
            return "";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
    private String name;
    private RelativeLayout rl;
    private TextView tv_title;
    private TextView tv_desc;

    private  TextView tv_id;



    /**
     *
     * @param key
     * @param down_url
     * @param status
     * @param name
     * @param rl
     * @param tv_title
     * @param tv_desc

     * @param tv_id
     * @param img
     * @param tv_status
     */
    public DownViewBean(String key, String down_url, String status, String name, RelativeLayout rl, TextView tv_title, TextView tv_desc, TextView tv_id, ImageView img, TextView tv_status) {
        this.key = key;
        this.down_url = down_url;
        this.status = status;
        this.name = name;
        this.rl = rl;
        this.tv_title = tv_title;
        this.tv_desc = tv_desc;

        this.tv_id = tv_id;
        this.img = img;
        this.tv_status = tv_status;
    }



    private ImageView img;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public TextView getTv_id() {
        return tv_id;
    }

    public void setTv_id(TextView tv_id) {
        this.tv_id = tv_id;
    }



    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }






    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public RelativeLayout getRl() {
        return rl;
    }

    public void setRl(RelativeLayout rl) {
        this.rl = rl;
    }

    public TextView getTv_title() {
        return tv_title;
    }

    public void setTv_title(TextView tv_title) {
        this.tv_title = tv_title;
    }

    public TextView getTv_desc() {
        return tv_desc;
    }

    public void setTv_desc(TextView tv_desc) {
        this.tv_desc = tv_desc;
    }



    public TextView getTv_status() {
        return tv_status;
    }

    public void setTv_status(TextView tv_status) {
        this.tv_status = tv_status;
    }

    private TextView tv_status;


}
