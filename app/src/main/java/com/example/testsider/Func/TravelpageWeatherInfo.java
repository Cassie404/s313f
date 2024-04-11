package com.example.testsider.Func;

import java.util.ArrayList;
import java.util.HashMap;

public class TravelpageWeatherInfo {
  public static String DATE = "date";
  public static String TIME = "time";
  public static String TEMP_AVG = "temp_avg";
  public static String HUMIDITY = "humidity";
  public static String GUST = "gust";
  public static String PRECIPITATION = "precipitation";
  public static String TEMP_UP = "temp_up";
  public static String TEMP_DOWN = "temp_down";

  public static String [] date = null;
  public static String [] t_up= null;
  public static String [] t_down= null;
  public static String [] sky= null;



  public static ArrayList<HashMap<String, String>> dataList = new ArrayList<>();

  public static void addtravelData(String adate, String atime, String temp_avg,String humidity, String gust, String precipitation, String temp_up, String temp_down,String[] dateArray,String[] maxt,String[] mint,String[]uv) {

    // Create contact
    HashMap<String, String> traveldata = new HashMap<>();
    HashMap<String[],String[]> travelArray= new HashMap<>();
    traveldata.put(DATE, adate);
    traveldata.put(TIME, atime);
    traveldata.put(TEMP_AVG, temp_avg);
    traveldata.put(HUMIDITY, humidity);
    traveldata.put(GUST, gust);
    traveldata.put(PRECIPITATION, precipitation);
    date = dateArray;
    t_up =maxt;
    t_down = mint;


    // Add contact to contact list
    dataList.add(traveldata);

  }


}


