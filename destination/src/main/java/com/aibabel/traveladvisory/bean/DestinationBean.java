package com.aibabel.traveladvisory.bean;

/**
 * Created by Wuqinghua on 2018/6/14 0014.
 */
public class DestinationBean {
    private String Destination_name;
    private String Destination_title;
    private String Destination_branch;
    private String Destination_img_url;

    public DestinationBean() {
        super();
    }

    public String getDestination_name() {
        return Destination_name;
    }

    public void setDestination_name(String destination_name) {
        Destination_name = destination_name;
    }

    public String getDestination_title() {
        return Destination_title;
    }

    public void setDestination_title(String destination_title) {
        Destination_title = destination_title;
    }

    public String getDestination_branch() {
        return Destination_branch;
    }

    public void setDestination_branch(String destination_branch) {
        Destination_branch = destination_branch;
    }

    public String getDestination_img_url() {
        return Destination_img_url;
    }

    public void setDestination_img_url(String destination_img_url) {
        Destination_img_url = destination_img_url;
    }

    @Override
    public String toString() {
        return "DestinationBean{" +
                "Destination_name='" + Destination_name + '\'' +
                ", Destination_title='" + Destination_title + '\'' +
                ", Destination_branch='" + Destination_branch + '\'' +
                ", Destination_img_url='" + Destination_img_url + '\'' +
                '}';
    }

    public DestinationBean(String destination_name, String destination_title, String destination_branch, String destination_img_url) {
        Destination_name = destination_name;
        Destination_title = destination_title;
        Destination_branch = destination_branch;
        Destination_img_url = destination_img_url;
    }
}
