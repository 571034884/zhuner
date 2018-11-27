package com.aibabel.weather.bean;

import android.text.TextUtils;
import android.util.Log;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 作者：SunSH on 2018/5/31 16:21
 * 功能：
 * 版本：1.0
 */
public class WeatherBean implements Serializable {
    /**
     * title : 天气预报
     * weatherNowData : {"titleNowWeather":"当前天气","WeatherAddr":"Beijing, China","local_time_rfc822":"Fri, 13 Jul 2018 19:31:46 +0800","weather":"Clear","temperature_string":"81 F (27 C)","relative_humidity":"89%","icon":"clear","wind_dir":"SE","wind_degrees":130,"precip_today_in":"0.00"}
     * weatherHoursData : [{"FCTTIME":"20","temp":"26","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"21","temp":"25","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"22","temp":"25","condition":"Partly Cloudy","icon":"partlycloudy"},{"FCTTIME":"23","temp":"25","condition":"Overcast","icon":"cloudy"},{"FCTTIME":"0","temp":"25","condition":"Overcast","icon":"cloudy"},{"FCTTIME":"1","temp":"25","condition":"Overcast","icon":"cloudy"},{"FCTTIME":"2","temp":"25","condition":"Overcast","icon":"cloudy"},{"FCTTIME":"3","temp":"25","condition":"Overcast","icon":"cloudy"},{"FCTTIME":"4","temp":"24","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"5","temp":"24","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"6","temp":"25","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"7","temp":"25","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"8","temp":"26","condition":"Thunderstorm","icon":"tstorms"},{"FCTTIME":"9","temp":"27","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"10","temp":"28","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"11","temp":"28","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"12","temp":"29","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"13","temp":"29","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"14","temp":"30","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"15","temp":"30","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"16","temp":"30","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"17","temp":"29","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"18","temp":"29","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"19","temp":"28","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"20","temp":"27","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"21","temp":"26","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"22","temp":"26","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"23","temp":"26","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"0","temp":"26","condition":"Chance of Rain","icon":"chancerain"},{"FCTTIME":"1","temp":"25","condition":"Thunderstorm","icon":"tstorms"},{"FCTTIME":"2","temp":"25","condition":"Thunderstorm","icon":"tstorms"},{"FCTTIME":"3","temp":"25","condition":"Thunderstorm","icon":"tstorms"},{"FCTTIME":"4","temp":"25","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"5","temp":"25","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"6","temp":"25","condition":"Chance of a Thunderstorm","icon":"chancetstorms"},{"FCTTIME":"7","temp":"26","condition":"Chance of a Thunderstorm","icon":"chancetstorms"}]
     * weatherobj : [{"data":"2018-7-13","condition":"Chance of a Thunderstorm","Temphigh":"32","Templow":"23","icon":"chancetstorms"},{"data":"2018-7-14","condition":"Thunderstorm","Temphigh":"30","Templow":"24","icon":"tstorms"},{"data":"2018-7-15","condition":"Thunderstorm","Temphigh":"32","Templow":"25","icon":"tstorms"},{"data":"2018-7-16","condition":"Chance of a Thunderstorm","Temphigh":"33","Templow":"25","icon":"chancetstorms"}]
     */

    private String title;
    private WeatherNowDataBean weatherNowData;
    private List<WeatherHoursDataBean> weatherHoursData;
    private List<WeatherobjBean> weatherobj;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public WeatherNowDataBean getWeatherNowData() {
        return weatherNowData;
    }

    public void setWeatherNowData(WeatherNowDataBean weatherNowData) {
        this.weatherNowData = weatherNowData;
    }

    public List<WeatherHoursDataBean> getWeatherHoursData() {
        return weatherHoursData;
    }

    public void setWeatherHoursData(List<WeatherHoursDataBean> weatherHoursData) {
        this.weatherHoursData = weatherHoursData;
    }

    public List<WeatherobjBean> getWeatherobj() {
        return weatherobj;
    }

    public void setWeatherobj(List<WeatherobjBean> weatherobj) {
        this.weatherobj = weatherobj;
    }

    public static class WeatherNowDataBean implements Serializable {
        /**
         * titleNowWeather : 当前天气
         * WeatherAddr : Beijing, China
         * local_time_rfc822 : Fri, 13 Jul 2018 19:31:46 +0800
         * weather : Clear
         * temperature_string : 81 F (27 C)
         * relative_humidity : 89%
         * icon : clear
         * wind_dir : SE
         * wind_degrees : 130
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
            if (TextUtils.isEmpty(temperature_string)) {
                return "--";
            } else if (temperature_string.split("\\(").length > 1) {
                return temperature_string.split("\\(")[1].split(" ")[0];
            } else {
                return temperature_string;
            }
//            return temperature_string;
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

    public static class WeatherHoursDataBean implements Serializable {
        /**
         * FCTTIME : 20
         * temp : 26
         * condition : Chance of a Thunderstorm
         * icon : chancetstorms
         */

        private String FCTTIME;
        private String temp;
        private String condition;
        private String icon;

        public String getFCTTIME() {
            return FCTTIME;
        }

        public void setFCTTIME(String FCTTIME) {
            this.FCTTIME = FCTTIME;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public static class WeatherobjBean implements Serializable {
        /**
         * data : 2018-7-13
         * condition : Chance of a Thunderstorm
         * Temphigh : 32
         * Templow : 23
         * icon : chancetstorms
         */

        private String data;
        private String condition;
        private String Temphigh;
        private String Templow;
        private String icon;

        public String getData() {
            Log.e( "getData: ", data);
            if (TextUtils.isEmpty(data)) {
                return "--";
            } else {
                try {
                    DateFormat df_from = new SimpleDateFormat("yyyy-M-d");
                    Date date2 = df_from.parse(data);
                    DateFormat df_to = new SimpleDateFormat("EEEE");
                    String s2 = df_to.format(date2);
                    return s2;
                } catch (Exception e) {
                    e.printStackTrace();
                    return data;
                }
            }
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getTemphigh() {
            return Temphigh;
        }

        public void setTemphigh(String Temphigh) {
            this.Temphigh = Temphigh;
        }

        public String getTemplow() {
            return Templow;
        }

        public void setTemplow(String Templow) {
            this.Templow = Templow;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
