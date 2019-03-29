package com.aibabel.scenic.bean;

import com.aibabel.baselibrary.http.BaseBean;

import java.util.List;

public class SpotsBean extends BaseBean {


    /**
     * data : {"poiMsg":{"idstring":"CurrentScenicId446","name":"东京塔","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fupload%2Fe429e39f04a539e61ba05f161cc2193d.jpg","type":3,"cityname":"东京","countryname":"日本","latitude":"35.658576","longitude":"139.745450","audiosUrl":"https://mjtt.gowithtommy.com/%E4%BA%9A%E6%B4%B2/%E6%97%A5%E6%9C%AC/%E4%B8%9C%E4%BA%AC/%E4%B8%9C%E4%BA%AC%E5%A1%94.mp3","subcount":24,"desc":"虽然在晴空塔skytree建立以后，东京塔似乎风头没有那么强劲，但其实在很多日本人心目中，1958年建成的东京铁塔才是真正的东京地标，现在每年亦有超过300万游客到访，也是众多日本电影电视剧的取景地，夜晚点亮的东京塔更是格外的美丽。\r\n\r\n东京塔高333米，现在是仅次于晴空塔的日本第二高建筑物。150米高度的位置设有大型展望室（设有商店和咖啡座）［目前东京塔特别展望台在维修，预计到17年夏天开放］，250米的高度设有规模较小的特别展望室。\r\n\r\n塔下有小型的水族馆、纪念品店等设施,塔内4-5层也会举行期间限定的各类展会，比如曾有藤子不二雄展，以及海贼王展。（过生日的人可以享受折扣价登上150米高的主观景台，一览东京的美景，还可以在Café La Tour咖啡店享受免费的蛋糕）\r\n\r\n如果是白天到达，身体强壮的还可以尝试徒步爬楼梯到展望厅！仅限周末和节假日的11点～16点才开放通往大眺望厅的\u201c上行楼梯\u201d！ 东京塔的\u201c上行楼梯\u201d是外围楼梯，从Foot Town屋顶至大眺望厅（150米）约有600阶台阶。可以边眺望景色边吹吹风。这是登上东京塔的特别路线。\r\n\r\n"},"subpoiMsg":[{"idstring":"CurrentSubscenicId30160","name":"高度333米","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F069d1aebcbf8cb350545f97e2c57753558cc21a3.jpg","type":4,"cityname":"东京","countryname":"日本","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/145d4dac6b53b8dc649a0c53ff62af9a27edc5a4.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId30161","name":"彩灯","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fbf8af528a113ab9d43bccbfb6de7c25c15f3dd6f.jpg","type":4,"cityname":"东京","countryname":"日本","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/3016497ab8d55e764f25013e69d41544db8ad491.mp3","subcount":0,"desc":""}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * poiMsg : {"idstring":"CurrentScenicId446","name":"东京塔","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fupload%2Fe429e39f04a539e61ba05f161cc2193d.jpg","type":3,"cityname":"东京","countryname":"日本","latitude":"35.658576","longitude":"139.745450","audiosUrl":"https://mjtt.gowithtommy.com/%E4%BA%9A%E6%B4%B2/%E6%97%A5%E6%9C%AC/%E4%B8%9C%E4%BA%AC/%E4%B8%9C%E4%BA%AC%E5%A1%94.mp3","subcount":24,"desc":"虽然在晴空塔skytree建立以后，东京塔似乎风头没有那么强劲，但其实在很多日本人心目中，1958年建成的东京铁塔才是真正的东京地标，现在每年亦有超过300万游客到访，也是众多日本电影电视剧的取景地，夜晚点亮的东京塔更是格外的美丽。\r\n\r\n东京塔高333米，现在是仅次于晴空塔的日本第二高建筑物。150米高度的位置设有大型展望室（设有商店和咖啡座）［目前东京塔特别展望台在维修，预计到17年夏天开放］，250米的高度设有规模较小的特别展望室。\r\n\r\n塔下有小型的水族馆、纪念品店等设施,塔内4-5层也会举行期间限定的各类展会，比如曾有藤子不二雄展，以及海贼王展。（过生日的人可以享受折扣价登上150米高的主观景台，一览东京的美景，还可以在Café La Tour咖啡店享受免费的蛋糕）\r\n\r\n如果是白天到达，身体强壮的还可以尝试徒步爬楼梯到展望厅！仅限周末和节假日的11点～16点才开放通往大眺望厅的\u201c上行楼梯\u201d！ 东京塔的\u201c上行楼梯\u201d是外围楼梯，从Foot Town屋顶至大眺望厅（150米）约有600阶台阶。可以边眺望景色边吹吹风。这是登上东京塔的特别路线。\r\n\r\n"}
         * subpoiMsg : [{"idstring":"CurrentSubscenicId30160","name":"高度333米","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F069d1aebcbf8cb350545f97e2c57753558cc21a3.jpg","type":4,"cityname":"东京","countryname":"日本","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/145d4dac6b53b8dc649a0c53ff62af9a27edc5a4.mp3","subcount":0,"desc":""},{"idstring":"CurrentSubscenicId30161","name":"彩灯","cover":"https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2Fbf8af528a113ab9d43bccbfb6de7c25c15f3dd6f.jpg","type":4,"cityname":"东京","countryname":"日本","latitude":"0.000000","longitude":"0.000000","audiosUrl":"https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/3016497ab8d55e764f25013e69d41544db8ad491.mp3","subcount":0,"desc":""}]
         */

        private PoiMsgBean poiMsg;
        private List<SubpoiMsgBean> subpoiMsg;

        public PoiMsgBean getPoiMsg() {
            return poiMsg;
        }

        public void setPoiMsg(PoiMsgBean poiMsg) {
            this.poiMsg = poiMsg;
        }

        public List<SubpoiMsgBean> getSubpoiMsg() {
            return subpoiMsg;
        }

        public void setSubpoiMsg(List<SubpoiMsgBean> subpoiMsg) {
            this.subpoiMsg = subpoiMsg;
        }

        public static class PoiMsgBean {
            /**
             * idstring : CurrentScenicId446
             * name : 东京塔
             * cover : https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fupload%2Fe429e39f04a539e61ba05f161cc2193d.jpg
             * type : 3
             * cityname : 东京
             * countryname : 日本
             * latitude : 35.658576
             * longitude : 139.745450
             * audiosUrl : https://mjtt.gowithtommy.com/%E4%BA%9A%E6%B4%B2/%E6%97%A5%E6%9C%AC/%E4%B8%9C%E4%BA%AC/%E4%B8%9C%E4%BA%AC%E5%A1%94.mp3
             * subcount : 24
             * desc : 虽然在晴空塔skytree建立以后，东京塔似乎风头没有那么强劲，但其实在很多日本人心目中，1958年建成的东京铁塔才是真正的东京地标，现在每年亦有超过300万游客到访，也是众多日本电影电视剧的取景地，夜晚点亮的东京塔更是格外的美丽。

             东京塔高333米，现在是仅次于晴空塔的日本第二高建筑物。150米高度的位置设有大型展望室（设有商店和咖啡座）［目前东京塔特别展望台在维修，预计到17年夏天开放］，250米的高度设有规模较小的特别展望室。

             塔下有小型的水族馆、纪念品店等设施,塔内4-5层也会举行期间限定的各类展会，比如曾有藤子不二雄展，以及海贼王展。（过生日的人可以享受折扣价登上150米高的主观景台，一览东京的美景，还可以在Café La Tour咖啡店享受免费的蛋糕）

             如果是白天到达，身体强壮的还可以尝试徒步爬楼梯到展望厅！仅限周末和节假日的11点～16点才开放通往大眺望厅的“上行楼梯”！ 东京塔的“上行楼梯”是外围楼梯，从Foot Town屋顶至大眺望厅（150米）约有600阶台阶。可以边眺望景色边吹吹风。这是登上东京塔的特别路线。


             */

            private String idstring;
            private String name;
            private String cover;
            private int type;
            private String cityname;
            private String countryname;
            private String latitude;
            private String longitude;
            private String audiosUrl;
            private int subcount;
            private String desc;

            public String getIdstring() {
                return idstring;
            }

            public void setIdstring(String idstring) {
                this.idstring = idstring;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }

            public String getCountryname() {
                return countryname;
            }

            public void setCountryname(String countryname) {
                this.countryname = countryname;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getAudiosUrl() {
                return audiosUrl;
            }

            public void setAudiosUrl(String audiosUrl) {
                this.audiosUrl = audiosUrl;
            }

            public int getSubcount() {
                return subcount;
            }

            public void setSubcount(int subcount) {
                this.subcount = subcount;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class SubpoiMsgBean {
            /**
             * idstring : CurrentSubscenicId30160
             * name : 高度333米
             * cover : https://music.gowithtommy.com/mjtt_backend_server%2Fprod%2Fdata%2F069d1aebcbf8cb350545f97e2c57753558cc21a3.jpg
             * type : 4
             * cityname : 东京
             * countryname : 日本
             * latitude : 0.000000
             * longitude : 0.000000
             * audiosUrl : https://mjtt.gowithtommy.com/mjtt_backend_server/prod/data/145d4dac6b53b8dc649a0c53ff62af9a27edc5a4.mp3
             * subcount : 0
             * desc :
             */

            private String idstring;
            private String name;
            private String cover;
            private int type;
            private String cityname;
            private String countryname;
            private String latitude;
            private String longitude;
            private String audiosUrl;
            private int subcount;
            private String desc;

            public String getIdstring() {
                return idstring;
            }

            public void setIdstring(String idstring) {
                this.idstring = idstring;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }

            public String getCountryname() {
                return countryname;
            }

            public void setCountryname(String countryname) {
                this.countryname = countryname;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getAudiosUrl() {
                return audiosUrl;
            }

            public void setAudiosUrl(String audiosUrl) {
                this.audiosUrl = audiosUrl;
            }

            public int getSubcount() {
                return subcount;
            }

            public void setSubcount(int subcount) {
                this.subcount = subcount;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }
}
