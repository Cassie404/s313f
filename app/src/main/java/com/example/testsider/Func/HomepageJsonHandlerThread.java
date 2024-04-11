package com.example.testsider.Func;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.example.testsider.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomepageJsonHandlerThread extends Thread {
    private static final String TAG = "HomepageJsonHandlerThread";
    // URL to get contacts JSON file

    private String languageCode = "en";
//    private String jsonUrlIn24 ="https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&hourly=temperature_2m&forecast_days=1";

    private String jsonUrl = "https://api.open-meteo.com/v1/forecast?latitude=22.28056&longitude=114.17222&current=temperature_2m,rain,apparent_temperature,cloud_cover,visibility,pressure_msl,uv_index,relative_humidity_2m,is_day,precipitation_probability,precipitation,wind_gusts_10m&daily=temperature_2m_max,temperature_2m_min&timezone=Asia%2FSingapore&forecast_days=1";
//private String jsonUrl ="";
    private String jsonUrlIn24 ="https://api.open-meteo.com/v1/forecast?latitude=22.28056&longitude=114.17222&hourly=temperature_2m&forecast_days=1";
//    private String jsonUrl,jsonUrlIn24;
    private String latitude , longitude;


    public  HomepageJsonHandlerThread(){};

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

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public void homejsonUrl(double latitude, double longitude) {
         jsonUrl = "https://api.open-meteo.com/v1/forecast?latitude="+latitude+"&longitude="+longitude+"&current=temperature_2m,rain,apparent_temperature,cloud_cover,visibility,pressure_msl,uv_index,relative_humidity_2m,is_day,precipitation_probability,precipitation,wind_gusts_10m&daily=temperature_2m_max,temperature_2m_min&timezone=Asia%2FSingapore&forecast_days=1";

        jsonUrlIn24 ="https://api.open-meteo.com/v1/forecast?latitude="+latitude+"&longitude="+
                longitude+"&hourly=temperature_2m&forecast_days=1";
    }




    public static int changeIcon( double cloud ,double rain ){

        if(rain  > 0){
            if(rain <=2.5 )
                return R.drawable.pic62;
            if(rain >2.5)
                return R.drawable.pic63;

        }else if (cloud  > 0){
            if(cloud  <= 25)
                return R.drawable.pic50;
            if(cloud  > 25 && cloud <= 50 )
                return R.drawable.pic60;
            if(cloud > 50)
                return R.drawable.pic61;
        }
        return R.drawable.pic50;
    }

    public static int changeIcon(){
        double cloud = Double.parseDouble(HomepageWeatherInfo.getCloud_cover()) ;
        double rain = Double.parseDouble(HomepageWeatherInfo.getRain());
        if(rain  > 0){
            if(rain <=2.5 )
                return R.drawable.pic62;
            if(rain >2.5)
                return R.drawable.pic63;
        }else if (cloud  > 0){
            if(cloud  <= 25)
                return R.drawable.pic50;
            if(cloud  > 25 && cloud <= 50 )
                return R.drawable.pic60;
            if(cloud > 50)
                return R.drawable.pic61;
        }
        return R.drawable.pic50;
    }

    public static String makeRequest(String jsonurl) {

        String response = null;
        try {
            URL url = new URL(jsonurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = inputStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }












    private static String inputStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            }
        }
        return sb.toString();
    }


    public void run() {
        String contactStr = this.makeRequest(jsonUrl);
        String contactStr24 = this.makeRequest(jsonUrlIn24);
        Log.e(TAG, "Response from url: " + contactStr);

        if (contactStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(contactStr);
                // Getting JSON Object for "current"
                JSONObject current = jsonObj.getJSONObject("current");
                HomepageWeatherInfo.setTime(current.getString("time"));
                HomepageWeatherInfo.setInterval(current.getString("interval"));
                HomepageWeatherInfo.setTemperature_2m(current.getString("temperature_2m"));
                HomepageWeatherInfo.setRelative_humidity_2m(current.getString("relative_humidity_2m"));
                HomepageWeatherInfo.setIs_day(current.getString("is_day"));
                HomepageWeatherInfo.setPrecipitation(current.getString("precipitation"));
                HomepageWeatherInfo.setWind_gusts_10m(current.getString("wind_gusts_10m"));
                HomepageWeatherInfo.setPrecipitation_probability(current.getString("precipitation_probability"));
                HomepageWeatherInfo.setUv_index(current.getString("uv_index"));
                HomepageWeatherInfo.setCloud_cover(current.getString("cloud_cover"));
                HomepageWeatherInfo.setApparent_temperature(current.getString("apparent_temperature"));
                HomepageWeatherInfo.setVisibility(current.getString("visibility"));
                HomepageWeatherInfo.setPressure_msl(current.getString("pressure_msl"));
                HomepageWeatherInfo.setRain(current.getString("rain"));
                // Getting JSON Object for "daily"
                JSONObject daily = jsonObj.getJSONObject("daily");
                JSONArray date = daily.getJSONArray("time");
                JSONArray temperature_2m_max = daily.getJSONArray("temperature_2m_max");
                JSONArray temperature_2m_min = daily.getJSONArray("temperature_2m_min");



                // Loop through the arrays
                for (int i = 0; i < date.length(); i++) {
                    HomepageWeatherInfo.getDate().add(date.getString(i));
                    HomepageWeatherInfo.getTemperature_2m_min().add(temperature_2m_max.getString(i));
                    HomepageWeatherInfo.getTemperature_2m_max().add(temperature_2m_min.getString(i));
                }

                JSONObject json = new JSONObject(contactStr24);
                JSONObject hourly = json.getJSONObject("hourly");
                JSONArray timeArray = hourly.getJSONArray("time");
                JSONArray temperatureArray = hourly.getJSONArray("temperature_2m");

                List<Pair<String, String>> temperatureList = new ArrayList<>();
                for (int i = 0; i < timeArray.length(); i++) {
                    String timeString = timeArray.getString(i);
                    LocalDateTime dateTime = LocalDateTime.parse(timeString, DateTimeFormatter.ISO_DATE_TIME);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                    String time = dateTime.format(formatter);
                    String temperature = temperatureArray.getDouble(i) + "";

                    Pair<String, String> pair = new Pair<>(time, temperature);
                    temperatureList.add(pair);

                }
                HomepageWeatherInfo.setTemperature_2m_24(temperatureList);



            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }
}