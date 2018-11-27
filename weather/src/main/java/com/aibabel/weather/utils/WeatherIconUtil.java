package com.aibabel.weather.utils;


import com.aibabel.weather.R;

/**
 * 作者：SunSH on 2018/6/1 21:56
 * 功能：
 * 版本：1.0
 */
public class WeatherIconUtil {

    /**
     * 随便给的第二个参数， 表示未来4天的白天天气图标
     *
     * @param icon
     * @return
     */
    public static int getIcon(String icon) {
        return getIcon(icon, "12");
    }

    public static int getIcon(String icon, String hour) {
        int image = 0;
        Integer time = Integer.valueOf(hour);
        if (time > 5 && time < 19) {
            switch (icon) {
                case "chanceflurries":
                case "Chanceflurries":
                    image = R.mipmap.chanceflurries;
                    break;
                case "chancerain":
                case "Chancerain":
                    image = R.mipmap.chancerain;
                    break;
                case "chancesleet":
                case "Chancesleet":
                    image = R.mipmap.chancesleet;
                    break;
                case "chancesnow":
                case "Chancesnow":
                    image = R.mipmap.chancesnow;
                    break;
                case "chancetstorms":
                case "Chancetstorms":
                    image = R.mipmap.chancetstorms;
                    break;
                case "clear":
                case "Clear":
                    image = R.mipmap.clear;
                    break;
                case "cloudy":
                case "Cloudy":
                    image = R.mipmap.cloudy;
                    break;
                case "flurries":
                case "Flurries":
                    image = R.mipmap.flurries;
                    break;
                case "fog":
                case "Fog":
                    image = R.mipmap.fog;
                    break;
                case "hazy":
                case "Hazy":
                    image = R.mipmap.hazy;
                    break;
                case "mostlycloudy":
                case "Mostlycloudy":
                    image = R.mipmap.mostlycloudy;
                    break;
                case "mostlysunny":
                case "Mostlysunny":
                case "Mostly Cloudy":
                    image = R.mipmap.mostlysunny;
                    break;
                case "partlycloudy":
                case "Partlycloudy":
                case "Partly Cloudy":
                    image = R.mipmap.partlycloudy;
                    break;
                case "partlysunny":
                case "Partlysunny":
                    image = R.mipmap.partlysunny;
                    break;
                case "rain":
                case "Rain":
                    image = R.mipmap.rain1;
                    break;
                case "sleet":
                case "Sleet":
                    image = R.mipmap.sleet;
                    break;
                case "snow":
                case "Snow":
                    image = R.mipmap.snow;
                    break;
                case "sunny":
                case "Sunny":
                    image = R.mipmap.sunny;
                    break;
                case "tstorms":
                case "Tstorms":
                    image = R.mipmap.tstorms;
                    break;
                case "thunderstorm":
                case "Thunderstorm":
                    image = R.mipmap.thunderstorm;
                    break;
                case "unknown":
                case "Unknown":
                    image = R.mipmap.unknown;
                    break;
            }
        } else {
            switch (icon) {
                case "chanceflurries":
                case "Chanceflurries":
                    image = R.mipmap.chanceflurries_nt;
                    break;
                case "chancerain":
                case "Chancerain":
                    image = R.mipmap.chancerain_nt;
                    break;
                case "chancesleet":
                case "Chancesleet":
                    image = R.mipmap.chancesleet_nt;
                    break;
                case "chancesnow":
                case "Chancesnow":
                    image = R.mipmap.chancesnow_nt;
                    break;
                case "chancetstorms":
                case "Chancetstorms":
                    image = R.mipmap.chancetstorms_nt;
                    break;
                case "clear":
                case "Clear":
                    image = R.mipmap.clear_nt;
                    break;
                case "cloudy":
                case "Cloudy":
                    image = R.mipmap.cloudy_nt;
                    break;
                case "flurries":
                case "Flurries":
                    image = R.mipmap.flurries_nt;
                    break;
                case "fog":
                case "Fog":
                    image = R.mipmap.fog_nt;
                    break;
                case "hazy":
                case "Hazy":
                    image = R.mipmap.hazy_nt;
                    break;
                case "mostlycloudy":
                case "Mostlycloudy":
                    image = R.mipmap.mostlycloudy_nt;
                    break;
                case "mostlysunny":
                case "Mostlysunny":
                    image = R.mipmap.mostlysunny_nt;
                    break;
                case "partlycloudy":
                case "Partlycloudy":
                    image = R.mipmap.partlycloudy_nt;
                    break;
                case "partlysunny":
                case "Partlysunny":
                    image = R.mipmap.partlysunny_nt;
                    break;
                case "rain":
                case "Rain":
                    image = R.mipmap.rain_nt1;
                    break;
                case "sleet":
                case "Sleet":
                    image = R.mipmap.sleet_nt;
                    break;
                case "snow":
                case "Snow":
                    image = R.mipmap.snow_nt;
                    break;
                case "sunny":
                case "Sunny":
                    image = R.mipmap.sunny_nt;
                    break;
                case "tstorms":
                case "Tstorms":
                    image = R.mipmap.tstorms_nt;
                    break;
                case "Thunderstorm":
                case "thunderstorm":
                    image = R.mipmap.thunderstorm_nt;
                    break;
                case "unknown":
                case "Unknown":
                    image = R.mipmap.unknown_nt;
                    break;
            }
        }
        return image;
    }

    public static int getIconBg(String icon) {
        return getIcon(icon, "12");
    }

    public static int getIconBg(String icon, String hour) {
        int image = 0;
        Integer time = Integer.valueOf(hour);
        if (time > 5 && time < 19) {
            switch (icon) {
                case "chanceflurries":
                case "Chanceflurries":
                    image = R.mipmap.mostlysunny_bg;
                    break;
                case "chancerain":
                case "Chancerain":
                    image = R.mipmap.cloudy_bg;
                    break;
                case "chancesleet":
                case "Chancesleet":
                    image = R.mipmap.cloudy_bg;
                    break;
                case "chancesnow":
                case "Chancesnow":
                    image = R.mipmap.cloudy_bg;
                    break;
                case "chancetstorms":
                case "Chancetstorms":
                    image = R.mipmap.cloudy_bg;
                    break;
                case "clear":
                case "Clear":
                    image = R.mipmap.clear_bg;
                    break;
                case "cloudy":
                case "Cloudy":
                    image = R.mipmap.cloudy_bg;
                    break;
                case "flurries":
                case "Flurries":
                    image = R.mipmap.flurries_bg;
                    break;
                case "fog":
                case "Fog":
                    image = R.mipmap.fog_bg;
                    break;
                case "hazy":
                case "Hazy":
                    image = R.mipmap.hazy_bg;
                    break;
                case "mostlycloudy":
                case "Mostlycloudy":
                    image = R.mipmap.mostlycloudy_bg;
                    break;
                case "mostlysunny":
                case "Mostlysunny":
                case "Mostly Cloudy":
                    image = R.mipmap.mostlysunny_bg;
                    break;
                case "partlycloudy":
                case "Partlycloudy":
                case "Partly Cloudy":
                    image = R.mipmap.mostlycloudy_bg;
                    break;
                case "partlysunny":
                case "Partlysunny":
                    image = R.mipmap.mostlycloudy_bg;
                    break;
                case "rain":
                case "Rain":
                    image = R.mipmap.rain_bg;
                    break;
                case "sleet":
                case "Sleet":
                    image = R.mipmap.sleet_bg;
                    break;
                case "snow":
                case "Snow":
                    image = R.mipmap.snow_bg;
                    break;
                case "sunny":
                case "Sunny":
                    image = R.mipmap.clear_bg;
                    break;
                case "tstorms":
                case "Tstorms":
                    image = R.mipmap.unknown_bg;
                    break;
                case "thunderstorm":
                case "Thunderstorm":
                    image = R.mipmap.unknown_bg;
                    break;
                case "unknown":
                case "Unknown":
                    image = R.mipmap.unknown_bg;
                    break;
            }
        } else {
            switch (icon) {
                case "chanceflurries":
                case "Chanceflurries":
                    image = R.mipmap.mostlycloudy_nt_bg;
                    break;
                case "chancerain":
                case "Chancerain":
                    image = R.mipmap.cloudy_nt_bg;
                    break;
                case "chancesleet":
                case "Chancesleet":
                    image = R.mipmap.cloudy_nt_bg;
                    break;
                case "chancesnow":
                case "Chancesnow":
                    image = R.mipmap.cloudy_nt_bg;
                    break;
                case "chancetstorms":
                case "Chancetstorms":
                    image = R.mipmap.cloudy_nt_bg;
                    break;
                case "clear":
                case "Clear":
                    image = R.mipmap.clear_nt_bg;
                    break;
                case "cloudy":
                case "Cloudy":
                    image = R.mipmap.cloudy_nt_bg;
                    break;
                case "flurries":
                case "Flurries":
                    image = R.mipmap.flurries_nt_bg;
                    break;
                case "fog":
                case "Fog":
                    image = R.mipmap.fog_nt_bg;
                    break;
                case "hazy":
                case "Hazy":
                    image = R.mipmap.hazy_nt_bg;
                    break;
                case "mostlycloudy":
                case "Mostlycloudy":
                    image = R.mipmap.mostlycloudy_nt_bg;
                    break;
                case "mostlysunny":
                case "Mostlysunny":
                    image = R.mipmap.mostlycloudy_nt_bg;
                    break;
                case "partlycloudy":
                case "Partlycloudy":
                    image = R.mipmap.mostlycloudy_nt_bg;
                    break;
                case "partlysunny":
                case "Partlysunny":
                    image = R.mipmap.mostlycloudy_nt_bg;
                    break;
                case "rain":
                case "Rain":
                    image = R.mipmap.rain_nt_bg;
                    break;
                case "sleet":
                case "Sleet":
                    image = R.mipmap.sleet_nt_bg;
                    break;
                case "snow":
                case "Snow":
                    image = R.mipmap.snow_nt_bg;
                    break;
                case "sunny":
                case "Sunny":
                    image = R.mipmap.clear_nt_bg;
                    break;
                case "tstorms":
                case "Tstorms":
                    image = R.mipmap.unknown_nt_bg;
                    break;
                case "Thunderstorm":
                case "thunderstorm":
                    image = R.mipmap.unknown_nt_bg;
                    break;
                case "unknown":
                case "Unknown":
                    image = R.mipmap.unknown_nt_bg;
                    break;
            }
        }
        return image;
    }
}
