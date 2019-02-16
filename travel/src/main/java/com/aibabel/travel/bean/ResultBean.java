package com.aibabel.travel.bean;

import java.util.List;

public class ResultBean {

    /**
     * code : 1
     * msg : Success!
     * data : [{"audios":"https://mjtt.gowithtommy.com/å¤§æ´\u008bæ´²/æ¾³å¤§å\u0088©äº\u009a/é»\u0084é\u0087\u0091æµ·å²¸/æ¾³å¤§å\u0088©äº\u009a-é»\u0084é\u0087\u0091æµ·å²¸-å\u0086²æµªè\u0080\u0085å¤©å \u0082.mp3","count":"0","distance":0,"imgUrl":"https://music.gowithtommy.com/mjtt_backend_server/prod/upload/136b788bcbc8af17db40ef8fcb5c3706.jpg","lat":-28.003259,"lng":153.428088,"poi_id":1251,"poi_name_cn":"冲浪者天堂","poi_name_en":"","poi_name_local":""}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * audios : https://mjtt.gowithtommy.com/å¤§æ´æ´²/æ¾³å¤§å©äº/é»éæµ·å²¸/æ¾³å¤§å©äº-é»éæµ·å²¸-å²æµªèå¤©å .mp3
         * count : 0
         * distance : 0
         * imgUrl : https://music.gowithtommy.com/mjtt_backend_server/prod/upload/136b788bcbc8af17db40ef8fcb5c3706.jpg
         * lat : -28.003259
         * lng : 153.428088
         * poi_id : 1251
         * poi_name_cn : 冲浪者天堂
         * poi_name_en :
         * poi_name_local :
         */

        private String audios;
        private String count;
        private int distance;
        private String imgUrl;
        private double lat;
        private double lng;
        private int poi_id;
        private String poi_name_cn;
        private String poi_name_en;
        private String poi_name_local;

        public String getAudios() {
            return audios;
        }

        public void setAudios(String audios) {
            this.audios = audios;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public int getPoi_id() {
            return poi_id;
        }

        public void setPoi_id(int poi_id) {
            this.poi_id = poi_id;
        }

        public String getPoi_name_cn() {
            return poi_name_cn;
        }

        public void setPoi_name_cn(String poi_name_cn) {
            this.poi_name_cn = poi_name_cn;
        }

        public String getPoi_name_en() {
            return poi_name_en;
        }

        public void setPoi_name_en(String poi_name_en) {
            this.poi_name_en = poi_name_en;
        }

        public String getPoi_name_local() {
            return poi_name_local;
        }

        public void setPoi_name_local(String poi_name_local) {
            this.poi_name_local = poi_name_local;
        }


    }
}

