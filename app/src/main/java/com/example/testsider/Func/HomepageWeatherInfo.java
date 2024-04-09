package com.example.testsider.Func;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// A utility class for creating contact list
public class HomepageWeatherInfo {
    private static String time;
    private static String interval;
    private static String temperature_2m;
    private static String relative_humidity_2m;
    private static String is_day;
    private static String precipitation;
    private static String wind_gusts_10m;

    private static  List<String> date = new ArrayList<>();
    private static  List<String> temperature_2m_max  = new ArrayList<>();
    private static  List<String> temperature_2m_min  = new ArrayList<>();


    public HomepageWeatherInfo() {
    }

    public static String getTime() {
        return time;
    }

    public static void setTime(String time) {
        HomepageWeatherInfo.time = time;
    }

    public static String getInterval() {
        return interval;
    }

    public static void setInterval(String interval) {
        HomepageWeatherInfo.interval = interval;
    }

    public static String getTemperature_2m() {
        return temperature_2m;
    }

    public static void setTemperature_2m(String temperature_2m) {
        HomepageWeatherInfo.temperature_2m = temperature_2m;
    }

    public static String getRelative_humidity_2m() {
        return relative_humidity_2m;
    }

    public static void setRelative_humidity_2m(String relative_humidity_2m) {
        HomepageWeatherInfo.relative_humidity_2m = relative_humidity_2m;
    }

    public static String getIs_day() {
        return is_day;
    }

    public static void setIs_day(String is_day) {
        HomepageWeatherInfo.is_day = is_day;
    }

    public static String getPrecipitation() {
        return precipitation;
    }

    public static void setPrecipitation(String precipitation) {
        HomepageWeatherInfo.precipitation = precipitation;
    }

    public static String getWind_gusts_10m() {
        return wind_gusts_10m;
    }

    public static void setWind_gusts_10m(String wind_gusts_10m) {
        HomepageWeatherInfo.wind_gusts_10m = wind_gusts_10m;
    }

    public static List<String> getDate() {
        return date;
    }

    public static void setDate(List<String> date) {
        HomepageWeatherInfo.date = date;
    }

    public static  List<String> getTemperature_2m_max() {
        return temperature_2m_max;
    }

    public static  void setTemperature_2m_max(List<String> temperature_2m_max) {
        HomepageWeatherInfo.temperature_2m_max = temperature_2m_max;
    }

    public static List<String> getTemperature_2m_min() {
        return temperature_2m_min;
    }

    public static void setTemperature_2m_min(List<String> temperature_2m_min) {
        HomepageWeatherInfo.temperature_2m_min = temperature_2m_min;
    }
}
