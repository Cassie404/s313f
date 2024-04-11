package com.example.testsider.Func;

import com.example.testsider.ui.slideshow.TravelFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TravelpageWeatherInfo {

  private static String DATE ,TIME,TEMP_AVG,HUMIDITY,GUST,CLOUD,PRECIPITATION,TEMP_UP,TEMP_DOWN;

  private static List<String> date = new ArrayList<>();
  private static  List<String> t_up = new ArrayList<>();
  private static  List<String>  t_down= new ArrayList<>();
  private static  List<String>  sky= new ArrayList<>();
  private static List<String>  rainSum = new ArrayList<>();

  public static List<String> getRainSum() {
    return rainSum;
  }

  public static void setRainSum(List<String> rainSum) {
    TravelpageWeatherInfo.rainSum = rainSum;
  }

  public static void setDATE(String DATE) {
    TravelpageWeatherInfo.DATE = DATE;
  }

  public static void setTIME(String TIME) {
    TravelpageWeatherInfo.TIME = TIME;
  }

  public static String getCLOUD() {
    return CLOUD;
  }

  public static void setCLOUD(String CLOUD) {
    TravelpageWeatherInfo.CLOUD = CLOUD;
  }

  public static void setTempAvg(String tempAvg) {
    TEMP_AVG = tempAvg;
  }

  public static void setHUMIDITY(String HUMIDITY) {
    TravelpageWeatherInfo.HUMIDITY = HUMIDITY;
  }

  public static void setGUST(String GUST) {
    TravelpageWeatherInfo.GUST = GUST;
  }

  public static void setPRECIPITATION(String PRECIPITATION) {
    TravelpageWeatherInfo.PRECIPITATION = PRECIPITATION;
  }

  public static void setTempUp(String tempUp) {
    TEMP_UP = tempUp;
  }

  public static void setTempDown(String tempDown) {
    TEMP_DOWN = tempDown;
  }

  public static String getDATE() {
    return DATE;
  }

  public static String getTIME() {
    return TIME;
  }

  public static String getTempAvg() {
    return TEMP_AVG;
  }

  public static String getHUMIDITY() {
    return HUMIDITY;
  }

  public static String getGUST() {
    return GUST;
  }

  public static String getPRECIPITATION() {
    return PRECIPITATION;
  }

  public static String getTempUp() {
    return TEMP_UP;
  }

  public static String getTempDown() {
    return TEMP_DOWN;
  }


  public static List<String> getDate() {
    return date;
  }

  public static void setDate(List<String> date) {
    TravelpageWeatherInfo.date = date;
  }

  public static List<String> getT_up() {
    return t_up;
  }

  public static void setT_up(List<String> t_up) {
    TravelpageWeatherInfo.t_up = t_up;
  }

  public static List<String> getT_down() {
    return t_down;
  }

  public static void setT_down(List<String> t_down) {
    TravelpageWeatherInfo.t_down = t_down;
  }

  public static List<String> getSky() {
    return sky;
  }

  public static void setSky(List<String> sky) {
    TravelpageWeatherInfo.sky = sky;
  }

  public static void addtravelData(String adate, String atime, String temp_avg, String humidity, String gust, String precipitation) {

    // Create contact

    DATE = adate;
    TIME = atime;
    HUMIDITY =humidity;
    TEMP_AVG=temp_avg;
    GUST=gust;
    PRECIPITATION = precipitation;

    System.out.println("finished adding all attribute");
    TravelFragment frag = new TravelFragment();
    while(TravelpageWeatherInfo.getTempAvg()!=null){frag.setAttributes();}
  }


}


