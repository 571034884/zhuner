package com.aibabel.fyt_exitandentry.bean;



import com.aibabel.baselibrary.http.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：wuqinghua_fyt on 2018/12/6 14:56
 * 功能：
 * 版本：1.0
 */
public class CityAirplaneBean extends BaseBean implements Serializable {


    /**
     * code : 1
     * data : [{"AddrId":"01","countryChj":"日本","cityChj":"东京","airportName":"成田机场","entryCard":"https://ypimg1.youpu.cn/album/Japan/20140730/53d8c9cc0b95e.jpg","airportTraffic":"","airportTrafficArr":["标题&&&成田机场攻略：","东京往来成田机场的交通选择很多，对于初次到日本的游客选择交通方式会比较困难。不妨先看一看下面的攻略，再选择适合自己的交通方式吧！\r\n1. 一下车就要到酒店门口，可以参考利用机场利木津巴士是否有到酒店门口的路线；\r\n2. 住宿地点距离东京车站、银座不远的话，可以达成京成巴士或者平和交通巴士；\r\n3. 住宿地点在东京车站、品川、涩谷、新宿、池袋、大宫、横滨、大船JR车站附近的，建议搭乘成田特快N'EX；\r\n4. 住宿地点在日暮里、上野站附近，建议达成Skylinner；\r\n5. 住宿地点不在上述范围内，想要快一点到达的话，住宿地在东京站以南就搭乘特快N'EX，在东京车站以北就搭乘Skylinner。","\r\n标题&&&机场巴士：","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&选择1：东京利木津巴士","前往的目的地有日本桥、东京站、池袋、涩谷、银座、新宿、六本木、浅草、东京迪士尼度假区等，线路多，且直达许多知名饭店门口。","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","官网：https://www.limousinebus.co.jp/ch2/\r\n票价：2500~3100日元左右，儿童（6-12岁）收半价，小于6岁儿童不占座不需购买票。\r\n乘车时间：1~1.5小时","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&选择2：平和交通The Access Narita","前往目的地有东京站，银座站。\r\n官网：http://accessnarita.jp/cn2/home/\r\n票价：成人1000日元，儿童半价500日元，无需提前购票，车内直接支付即可。\r\n乘车时间：1小时20分","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&选择3：京成巴士","前往的目的地有东京站、银座站、、临海副都心等。这辆巴士算是所有机场巴士中最便宜的了。\r\n官网：http://www.keiseibus.co.jp/inbound/tokyoshuttle/zh/\r\n票价：成人单程1000日元\r\n乘车时间：1小时~1.5小时","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&机场铁路/电车：","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&机场铁路/电车：","标题&&&选择1：成田特快N'EX（属JR东日本）","时间：从成田机场至东京车站约1小时，每30分钟发一班 \r\n停靠车站：东京车站，品川，涩谷，新宿，池袋，大宫，武藏小杉，横滨，大船。\r\n票价：可以购买外国人限定N'EX东京来回优惠车票，无论从以上那个车站，优惠来回成人（12周岁以上）都是4000日元（原价6040日元），儿童（6-11岁）2000日元（原价3020日元以上），并且购买外国人优惠票还能免费转乘JR普通列车一次。","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&选择2：快速Airport成田（属JR东日本）","JR东日本的普通列车，路线与N'EX差不多，停靠的车站变多了，大约需要1小时20分才可以抵达东京站，不过票价便宜许多，成田机场到东京车站约1300日元，大约30分钟一班车，购票可以直接购票机器购买，无优惠票，不需提前预约。","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&选择3：Skylinner（属京成电铁）","时间：从成田机场直达日暮里只要36分钟，上野41分钟，是所有交通工具中最快进入东京市区的。 \r\n票价：单程票价为2470日元，可以购买优惠票【Skyliner Discount Ticket】及【Keisei Skyliner＆Tokyo Subway Ticket】。","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&选择4：Access特急（属京成电铁）","时间：与Skylinner走相同路线，不过中间停留的站点较多，从成田机场至上野最快要55分钟，成田至浅草50分钟。 \r\n票价：成田--上野 成人1240日元，儿童620日元；成田--浅草 成人1290日元，儿童650日元；直接车站机器购买，无需预约，无优惠票。","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&选择5：京成本线特急（属京成电铁）","时间：走另一条路线，停留的站点更多，从成田机场至上野最快要75分钟，成田至浅草70分钟。 \r\n票价：成田--上野 成人1030日元，儿童520日元；成田--浅草 成人1100日元，儿童550日元；直接车站机器购买，无需预约，无优惠票。","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg"],"airportMap":"","airportMapArr":["标题&&&T1：","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&T2：","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg"],"emergency":"","cityimageurl":"https://ypimg1.youpu.cn/shine/201504/5c11746d1a46c1531fee164622f59e9e.jpg"},{"AddrId":"02","countryChj":"日本","cityChj":"东京","airportName":"羽田机场","entryCard":"https://ypimg1.youpu.cn/album/Tokyo/20140730/53d8c6dab0889.jpg","airportTraffic":"","airportTrafficArr":["标题&&&机场巴士：","羽田机场至东京市区及周边各地的大巴由京急巴士（京滨急行）和利木津巴士两家公司运行。\r\n京急巴士：http://cn.hnd-bus.com/\r\n利木津巴士：https://www.limousinebus.co.jp/ch1/","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&机场铁路/电车：","从羽田机场前往东京市区有京急电铁（京滨急行）和东京单轨电车两种方式可以选择。","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg"],"airportMap":"","airportMapArr":["标题&&&T1:","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&T2：","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg"],"emergency":"","cityimageurl":"https://ypimg1.youpu.cn/shine/201504/5c11746d1a46c1531fee164622f59e9e.jpg"}]
     */

    private String codeX;
    private List<DataBean> data;

    public String getCodeX() {
        return codeX;
    }

    public void setCodeX(String codeX) {
        this.codeX = codeX;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * AddrId : 01
         * countryChj : 日本
         * cityChj : 东京
         * airportName : 成田机场
         * entryCard : https://ypimg1.youpu.cn/album/Japan/20140730/53d8c9cc0b95e.jpg
         * airportTraffic :
         * airportTrafficArr : ["标题&&&成田机场攻略：","东京往来成田机场的交通选择很多，对于初次到日本的游客选择交通方式会比较困难。不妨先看一看下面的攻略，再选择适合自己的交通方式吧！\r\n1. 一下车就要到酒店门口，可以参考利用机场利木津巴士是否有到酒店门口的路线；\r\n2. 住宿地点距离东京车站、银座不远的话，可以达成京成巴士或者平和交通巴士；\r\n3. 住宿地点在东京车站、品川、涩谷、新宿、池袋、大宫、横滨、大船JR车站附近的，建议搭乘成田特快N'EX；\r\n4. 住宿地点在日暮里、上野站附近，建议达成Skylinner；\r\n5. 住宿地点不在上述范围内，想要快一点到达的话，住宿地在东京站以南就搭乘特快N'EX，在东京车站以北就搭乘Skylinner。","\r\n标题&&&机场巴士：","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&选择1：东京利木津巴士","前往的目的地有日本桥、东京站、池袋、涩谷、银座、新宿、六本木、浅草、东京迪士尼度假区等，线路多，且直达许多知名饭店门口。","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","官网：https://www.limousinebus.co.jp/ch2/\r\n票价：2500~3100日元左右，儿童（6-12岁）收半价，小于6岁儿童不占座不需购买票。\r\n乘车时间：1~1.5小时","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&选择2：平和交通The Access Narita","前往目的地有东京站，银座站。\r\n官网：http://accessnarita.jp/cn2/home/\r\n票价：成人1000日元，儿童半价500日元，无需提前购票，车内直接支付即可。\r\n乘车时间：1小时20分","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&选择3：京成巴士","前往的目的地有东京站、银座站、、临海副都心等。这辆巴士算是所有机场巴士中最便宜的了。\r\n官网：http://www.keiseibus.co.jp/inbound/tokyoshuttle/zh/\r\n票价：成人单程1000日元\r\n乘车时间：1小时~1.5小时","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&机场铁路/电车：","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&机场铁路/电车：","标题&&&选择1：成田特快N'EX（属JR东日本）","时间：从成田机场至东京车站约1小时，每30分钟发一班 \r\n停靠车站：东京车站，品川，涩谷，新宿，池袋，大宫，武藏小杉，横滨，大船。\r\n票价：可以购买外国人限定N'EX东京来回优惠车票，无论从以上那个车站，优惠来回成人（12周岁以上）都是4000日元（原价6040日元），儿童（6-11岁）2000日元（原价3020日元以上），并且购买外国人优惠票还能免费转乘JR普通列车一次。","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&选择2：快速Airport成田（属JR东日本）","JR东日本的普通列车，路线与N'EX差不多，停靠的车站变多了，大约需要1小时20分才可以抵达东京站，不过票价便宜许多，成田机场到东京车站约1300日元，大约30分钟一班车，购票可以直接购票机器购买，无优惠票，不需提前预约。","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&选择3：Skylinner（属京成电铁）","时间：从成田机场直达日暮里只要36分钟，上野41分钟，是所有交通工具中最快进入东京市区的。 \r\n票价：单程票价为2470日元，可以购买优惠票【Skyliner Discount Ticket】及【Keisei Skyliner＆Tokyo Subway Ticket】。","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&选择4：Access特急（属京成电铁）","时间：与Skylinner走相同路线，不过中间停留的站点较多，从成田机场至上野最快要55分钟，成田至浅草50分钟。 \r\n票价：成田--上野 成人1240日元，儿童620日元；成田--浅草 成人1290日元，儿童650日元；直接车站机器购买，无需预约，无优惠票。","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&选择5：京成本线特急（属京成电铁）","时间：走另一条路线，停留的站点更多，从成田机场至上野最快要75分钟，成田至浅草70分钟。 \r\n票价：成田--上野 成人1030日元，儿童520日元；成田--浅草 成人1100日元，儿童550日元；直接车站机器购买，无需预约，无优惠票。","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg"]
         * airportMap :
         * airportMapArr : ["标题&&&T1：","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg","标题&&&T2：","图片&&&https://ypimg1.youpu.cn/shine/201611/509c518c7b990ee4091b2e81733b82f1.jpg"]
         * emergency :
         * cityimageurl : https://ypimg1.youpu.cn/shine/201504/5c11746d1a46c1531fee164622f59e9e.jpg
         */

        private String AddrId;
        private String countryChj;
        private String cityChj;
        private String airportName;
        private String entryCard;
        private String airportTraffic;
        private String airportMap;
        private String emergency;
        private String cityimageurl;
        private List<String> airportTrafficArr;
        private List<String> airportMapArr;

        public String getAddrId() {
            return AddrId;
        }

        public void setAddrId(String AddrId) {
            this.AddrId = AddrId;
        }

        public String getCountryChj() {
            return countryChj;
        }

        public void setCountryChj(String countryChj) {
            this.countryChj = countryChj;
        }

        public String getCityChj() {
            return cityChj;
        }

        public void setCityChj(String cityChj) {
            this.cityChj = cityChj;
        }

        public String getAirportName() {
            return airportName;
        }

        public void setAirportName(String airportName) {
            this.airportName = airportName;
        }

        public String getEntryCard() {
            return entryCard;
        }

        public void setEntryCard(String entryCard) {
            this.entryCard = entryCard;
        }

        public String getAirportTraffic() {
            return airportTraffic;
        }

        public void setAirportTraffic(String airportTraffic) {
            this.airportTraffic = airportTraffic;
        }

        public String getAirportMap() {
            return airportMap;
        }

        public void setAirportMap(String airportMap) {
            this.airportMap = airportMap;
        }

        public String getEmergency() {
            return emergency;
        }

        public void setEmergency(String emergency) {
            this.emergency = emergency;
        }

        public String getCityimageurl() {
            return cityimageurl;
        }

        public void setCityimageurl(String cityimageurl) {
            this.cityimageurl = cityimageurl;
        }

        public List<String> getAirportTrafficArr() {
            return airportTrafficArr;
        }

        public void setAirportTrafficArr(List<String> airportTrafficArr) {
            this.airportTrafficArr = airportTrafficArr;
        }

        public List<String> getAirportMapArr() {
            return airportMapArr;
        }

        public void setAirportMapArr(List<String> airportMapArr) {
            this.airportMapArr = airportMapArr;
        }
    }
}
