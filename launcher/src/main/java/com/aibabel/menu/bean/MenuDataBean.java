package com.aibabel.menu.bean;

import com.aibabel.baselibrary.http.BaseBean;


public class MenuDataBean extends BaseBean {


    /**
     * data : {"timeZone":"GMT+8","currency100WaiBi":"100 CNY","currencyCny":"100.00 CNY","cityNameEn":"Beijing","countryNameEn":"China","cityNameCn":"北京市","countryNameCn":"中国","baiduProvinceName":"北京市","baiduStreetName":"知春路","cityNameFrom":"bd","cityId":"","countryId":"","addrPicUrl":"","addrPicColor":"","addrDetialPage":"http://destination.cdn.aibabel.com/menuH5/image/H5page/index1904011612.html","weatherNowData":{"titleNowWeather":"当前天气","WeatherAddr":"北京","local_time_rfc822":"Fri, 19 Apr 2019 15:20:35 +0800","weather":"阴","temperature_string":"17","relative_humidity":"19%","icon":"cloudy","wind_dir":"南风","wind_degrees":6,"precip_today_in":"0.00"},"fgAPageHasCheep":"1","fgBPageHasCheep":"0"}
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
         * timeZone : GMT+8
         * currency100WaiBi : 100 CNY
         * currencyCny : 100.00 CNY
         * cityNameEn : Beijing
         * countryNameEn : China
         * cityNameCn : 北京市
         * countryNameCn : 中国
         * baiduProvinceName : 北京市
         * baiduStreetName : 知春路
         * cityNameFrom : bd
         * cityId :
         * countryId :
         * addrPicUrl :
         * addrPicColor :
         * addrDetialPage : http://destination.cdn.aibabel.com/menuH5/image/H5page/index1904011612.html
         * weatherNowData : {"titleNowWeather":"当前天气","WeatherAddr":"北京","local_time_rfc822":"Fri, 19 Apr 2019 15:20:35 +0800","weather":"阴","temperature_string":"17","relative_humidity":"19%","icon":"cloudy","wind_dir":"南风","wind_degrees":6,"precip_today_in":"0.00"}
         * fgAPageHasCheep : 1
         * fgBPageHasCheep : 0
         */

        private String timeZone;
        private String currency100WaiBi;
        private String currencyCny;
        private String cityNameEn;
        private String countryNameEn;
        private String cityNameCn;
        private String countryNameCn;
        private String baiduProvinceName;
        private String baiduStreetName;
        private String cityNameFrom;
        private String cityId;
        private String countryId;
        private String addrPicUrl;
        private String addrPicColor;
        private String addrDetialPage;
        private WeatherNowDataBean weatherNowData;
        private String fgAPageHasCheep;
        private String fgBPageHasCheep;

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public String getCurrency100WaiBi() {
            return currency100WaiBi;
        }

        public void setCurrency100WaiBi(String currency100WaiBi) {
            this.currency100WaiBi = currency100WaiBi;
        }

        public String getCurrencyCny() {
            return currencyCny;
        }

        public void setCurrencyCny(String currencyCny) {
            this.currencyCny = currencyCny;
        }

        public String getCityNameEn() {
            return cityNameEn;
        }

        public void setCityNameEn(String cityNameEn) {
            this.cityNameEn = cityNameEn;
        }

        public String getCountryNameEn() {
            return countryNameEn;
        }

        public void setCountryNameEn(String countryNameEn) {
            this.countryNameEn = countryNameEn;
        }

        public String getCityNameCn() {
            return cityNameCn;
        }

        public void setCityNameCn(String cityNameCn) {
            this.cityNameCn = cityNameCn;
        }

        public String getCountryNameCn() {
            return countryNameCn;
        }

        public void setCountryNameCn(String countryNameCn) {
            this.countryNameCn = countryNameCn;
        }

        public String getBaiduProvinceName() {
            return baiduProvinceName;
        }

        public void setBaiduProvinceName(String baiduProvinceName) {
            this.baiduProvinceName = baiduProvinceName;
        }

        public String getBaiduStreetName() {
            return baiduStreetName;
        }

        public void setBaiduStreetName(String baiduStreetName) {
            this.baiduStreetName = baiduStreetName;
        }

        public String getCityNameFrom() {
            return cityNameFrom;
        }

        public void setCityNameFrom(String cityNameFrom) {
            this.cityNameFrom = cityNameFrom;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getAddrPicUrl() {
            return addrPicUrl;
        }

        public void setAddrPicUrl(String addrPicUrl) {
            this.addrPicUrl = addrPicUrl;
        }

        public String getAddrPicColor() {
            return addrPicColor;
        }

        public void setAddrPicColor(String addrPicColor) {
            this.addrPicColor = addrPicColor;
        }

        public String getAddrDetialPage() {
            return addrDetialPage;
        }

        public void setAddrDetialPage(String addrDetialPage) {
            this.addrDetialPage = addrDetialPage;
        }

        public WeatherNowDataBean getWeatherNowData() {
            return weatherNowData;
        }

        public void setWeatherNowData(WeatherNowDataBean weatherNowData) {
            this.weatherNowData = weatherNowData;
        }

        public String getFgAPageHasCheep() {
            return fgAPageHasCheep;
        }

        public void setFgAPageHasCheep(String fgAPageHasCheep) {
            this.fgAPageHasCheep = fgAPageHasCheep;
        }

        public String getFgBPageHasCheep() {
            return fgBPageHasCheep;
        }

        public void setFgBPageHasCheep(String fgBPageHasCheep) {
            this.fgBPageHasCheep = fgBPageHasCheep;
        }

        public static class WeatherNowDataBean {
            /**
             * titleNowWeather : 当前天气
             * WeatherAddr : 北京
             * local_time_rfc822 : Fri, 19 Apr 2019 15:20:35 +0800
             * weather : 阴
             * temperature_string : 17
             * relative_humidity : 19%
             * icon : cloudy
             * wind_dir : 南风
             * wind_degrees : 6
             * precip_today_in : 0.00
             */

            private String titleNowWeather;
            private String WeatherAddr;
            private String local_time_rfc822;
            private String weather;
            private String temperature_string;
            private String relative_humidity;
            private String icon;
            private String wind_dir;
            private int wind_degrees;
            private String precip_today_in;

            public String getTitleNowWeather() {
                return titleNowWeather;
            }

            public void setTitleNowWeather(String titleNowWeather) {
                this.titleNowWeather = titleNowWeather;
            }

            public String getWeatherAddr() {
                return WeatherAddr;
            }

            public void setWeatherAddr(String WeatherAddr) {
                this.WeatherAddr = WeatherAddr;
            }

            public String getLocal_time_rfc822() {
                return local_time_rfc822;
            }

            public void setLocal_time_rfc822(String local_time_rfc822) {
                this.local_time_rfc822 = local_time_rfc822;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getTemperature_string() {
                return temperature_string;
            }

            public void setTemperature_string(String temperature_string) {
                this.temperature_string = temperature_string;
            }

            public String getRelative_humidity() {
                return relative_humidity;
            }

            public void setRelative_humidity(String relative_humidity) {
                this.relative_humidity = relative_humidity;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public int getWind_degrees() {
                return wind_degrees;
            }

            public void setWind_degrees(int wind_degrees) {
                this.wind_degrees = wind_degrees;
            }

            public String getPrecip_today_in() {
                return precip_today_in;
            }

            public void setPrecip_today_in(String precip_today_in) {
                this.precip_today_in = precip_today_in;
            }
        }
    }
}
