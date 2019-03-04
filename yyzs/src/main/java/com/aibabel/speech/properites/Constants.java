package com.aibabel.speech.properites;

public class Constants {
    /**
     * RequestParams rp = new RequestParams("http://123.56.15.63:3389");
     * // RequestParams rp = new RequestParams("http://192.168.5.242:3389");
     */

    private static Constants instance;

    public static Constants getInstance() {
        if (instance == null) {
            instance = new Constants();
            return instance;
        }
        return instance;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        this.intentionUrl = baseUrl;
        this.getImgUrl = baseUrl + "/img";
    }

    public String getIntentionUrl() {
        return intentionUrl;
    }

    public void setIntentionUrl(String intentionUrl) {
        this.intentionUrl = intentionUrl;
    }

    public String getGetImgUrl() {
        return getImgUrl;
    }

    public void setGetImgUrl(String getImgUrl) {
        this.getImgUrl = getImgUrl;
    }

    private String baseUrl = "http://abroad.api.pa.aibabel.cn:6021";
//    String baseUrl="http://192.168.5.242:3389";


    private String intentionUrl = getBaseUrl();
    private String getImgUrl = getBaseUrl() + "/img";
}
