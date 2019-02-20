package com.aibabel.traveladvisory.bean;

/**
 * Created by Wuqinghua on 2018/6/14 0014.
 */
public class Interest_pointBean {
    private String url_img ;
    private String tv_name;
    private String tv_english;
    private String tv_praise;
    private String tv_paiming;
    private String tv_star;
    private String tv_evaluate;
    private String tv_introduce;

    public Interest_pointBean() {
        super();
    }

    @Override
    public String toString() {
        return "Interest_pointBean{" +
                "url_img='" + url_img + '\'' +
                ", tv_name='" + tv_name + '\'' +
                ", tv_english='" + tv_english + '\'' +
                ", tv_praise='" + tv_praise + '\'' +
                ", tv_paiming='" + tv_paiming + '\'' +
                ", tv_star='" + tv_star + '\'' +
                ", tv_evaluate='" + tv_evaluate + '\'' +
                ", tv_introduce='" + tv_introduce + '\'' +
                '}';
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_english() {
        return tv_english;
    }

    public void setTv_english(String tv_english) {
        this.tv_english = tv_english;
    }

    public String getTv_praise() {
        return tv_praise;
    }

    public void setTv_praise(String tv_praise) {
        this.tv_praise = tv_praise;
    }

    public String getTv_paiming() {
        return tv_paiming;
    }

    public void setTv_paiming(String tv_paiming) {
        this.tv_paiming = tv_paiming;
    }

    public String getTv_star() {
        return tv_star;
    }

    public void setTv_star(String tv_star) {
        this.tv_star = tv_star;
    }

    public String getTv_evaluate() {
        return tv_evaluate;
    }

    public void setTv_evaluate(String tv_evaluate) {
        this.tv_evaluate = tv_evaluate;
    }

    public String getTv_introduce() {
        return tv_introduce;
    }

    public void setTv_introduce(String tv_introduce) {
        this.tv_introduce = tv_introduce;
    }

    public Interest_pointBean(String url_img, String tv_name, String tv_english, String tv_praise, String tv_paiming, String tv_star, String tv_evaluate, String tv_introduce) {
        this.url_img = url_img;
        this.tv_name = tv_name;
        this.tv_english = tv_english;
        this.tv_praise = tv_praise;
        this.tv_paiming = tv_paiming;
        this.tv_star = tv_star;
        this.tv_evaluate = tv_evaluate;
        this.tv_introduce = tv_introduce;
    }
}
