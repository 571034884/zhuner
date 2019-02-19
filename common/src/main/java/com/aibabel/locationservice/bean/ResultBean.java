package com.aibabel.locationservice.bean;

import java.util.List;

public class ResultBean {


    /**
     * code : 1
     * msg : Success!
     * data : {"apk":"travel","info":[{"lng":139.769738,"lat":35.698146,"poi_id":47499,"poi_name_cn":"秋叶原电器城","poi_name_en":"Akihabara Electric Town","poi_name_local":"秋葉原電気街"},{"lng":139.771639,"lat":35.698432,"poi_id":47500,"poi_name_cn":"秋叶原无线电中心","poi_name_en":"Akihabara Radio Center","poi_name_local":"秋葉原ラジオセンター"},{"lng":139.7546932,"lat":35.6905432,"poi_id":48006,"poi_name_cn":"东京国立近代美术馆","poi_name_en":"Tokyo National Museum of Modern Art (MOMAT)","poi_name_local":"東京国立近代美術館"},{"lng":139.77273,"lat":35.70007,"poi_id":48249,"poi_name_cn":"东京动画秋叶原中心","poi_name_en":"Tokyo Anime Center Akiba Info","poi_name_local":"東京アニメセンター Akiba Info"},{"lng":139.7517859,"lat":35.70553084,"poi_id":48253,"poi_name_cn":"东京巨蛋城 ","poi_name_en":"Tokyo Dome City","poi_name_local":"東京ドームシティ"},{"lng":139.75192,"lat":35.705315,"poi_id":49802,"poi_name_cn":"Tokyo Dome","poi_name_en":"Tokyo Dome","poi_name_local":"東京ドーム"},{"lng":139.768143,"lat":35.704319,"poi_id":314319,"poi_name_cn":"神田明神","poi_name_en":"Kanda Myōjin","poi_name_local":"神田明神"},{"lng":139.7753269,"lat":35.7020691,"poi_id":314413,"poi_name_cn":"秋叶原","poi_name_en":"Akihabara","poi_name_local":" 秋葉原（あきはばら）"},{"lng":139.767792,"lat":35.695789,"poi_id":314427,"poi_name_cn":"松屋百货银座总店","poi_name_en":"Matsuya","poi_name_local":"松屋銀座"},{"lng":139.7674306,"lat":35.6911273,"poi_id":314468,"poi_name_cn":"神田西口商业街","poi_name_en":"Kanda Nishiguchi Commercial Street","poi_name_local":"神田駅西口商店街"}]}
     */

    private String code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * apk : travel
         * info : [{"lng":139.769738,"lat":35.698146,"poi_id":47499,"poi_name_cn":"秋叶原电器城","poi_name_en":"Akihabara Electric Town","poi_name_local":"秋葉原電気街"},{"lng":139.771639,"lat":35.698432,"poi_id":47500,"poi_name_cn":"秋叶原无线电中心","poi_name_en":"Akihabara Radio Center","poi_name_local":"秋葉原ラジオセンター"},{"lng":139.7546932,"lat":35.6905432,"poi_id":48006,"poi_name_cn":"东京国立近代美术馆","poi_name_en":"Tokyo National Museum of Modern Art (MOMAT)","poi_name_local":"東京国立近代美術館"},{"lng":139.77273,"lat":35.70007,"poi_id":48249,"poi_name_cn":"东京动画秋叶原中心","poi_name_en":"Tokyo Anime Center Akiba Info","poi_name_local":"東京アニメセンター Akiba Info"},{"lng":139.7517859,"lat":35.70553084,"poi_id":48253,"poi_name_cn":"东京巨蛋城 ","poi_name_en":"Tokyo Dome City","poi_name_local":"東京ドームシティ"},{"lng":139.75192,"lat":35.705315,"poi_id":49802,"poi_name_cn":"Tokyo Dome","poi_name_en":"Tokyo Dome","poi_name_local":"東京ドーム"},{"lng":139.768143,"lat":35.704319,"poi_id":314319,"poi_name_cn":"神田明神","poi_name_en":"Kanda Myōjin","poi_name_local":"神田明神"},{"lng":139.7753269,"lat":35.7020691,"poi_id":314413,"poi_name_cn":"秋叶原","poi_name_en":"Akihabara","poi_name_local":" 秋葉原（あきはばら）"},{"lng":139.767792,"lat":35.695789,"poi_id":314427,"poi_name_cn":"松屋百货银座总店","poi_name_en":"Matsuya","poi_name_local":"松屋銀座"},{"lng":139.7674306,"lat":35.6911273,"poi_id":314468,"poi_name_cn":"神田西口商业街","poi_name_en":"Kanda Nishiguchi Commercial Street","poi_name_local":"神田駅西口商店街"}]
         */

        private String apk;
        private List<InfoBean> info;
        private String msg;



        public String getApk() {
            return apk;
        }

        public void setApk(String apk) {
            this.apk = apk;
        }

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
        public static class InfoBean {
            /**
             * lng : 139.769738
             * lat : 35.698146
             * poi_id : 47499
             * poi_name_cn : 秋叶原电器城
             * poi_name_en : Akihabara Electric Town
             * poi_name_local : 秋葉原電気街
             */

            private double lng;
            private double lat;
            private int poi_id;
            private String poi_name_cn;
            private String poi_name_en;
            private String poi_name_local;
            private int count;
            private String distance;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getAudios() {
                return audios;
            }

            public void setAudios(String audios) {
                this.audios = audios;
            }

            private String imgUrl;
            private String audios;

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
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
}
