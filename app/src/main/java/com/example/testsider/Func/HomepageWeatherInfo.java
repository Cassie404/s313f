package com.example.testsider.Func;

import java.util.ArrayList;
import java.util.HashMap;

// A utility class for creating contact list
public class HomepageWeatherInfo {
    public static String DATE = "date";
    public static String TIME = "time";
    public static String TEMP_AVG = "temp_avg";
    public static String HUMIDITY = "humidity";
    public static String GUST = "gust";
    public static String PRECIPITATION = "precipitation";
    public static String TEMP_UP = "temp_up";
    public static String TEMP_DOWN = "temp_down";

    public static ArrayList<HashMap<String, String>> dataList = new ArrayList<>();


    public static void addhomeData(String adate, String atime, String temp_avg,String humidity, String gust, String precipitation, String temp_up, String temp_down) {
        // Create contact
        HashMap<String, String> homedata = new HashMap<>();
        homedata.put(DATE, adate);
        homedata.put(TIME, atime);
        homedata.put(TEMP_AVG, temp_avg);
        homedata.put(HUMIDITY, humidity);
        homedata.put(GUST, gust);
        homedata.put(PRECIPITATION, precipitation);
        homedata.put(TEMP_UP, temp_up);
        homedata.put(TEMP_DOWN, temp_down);

        // Add contact to contact list
        dataList.add(homedata);
    }
}
