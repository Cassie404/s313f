package com.example.testsider.Func;


import android.util.Log;

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
import java.util.HashMap;

public class TravelpageJsonHandlerThread extends Thread{

    private static final String TAG = "JsonHandlerThread";
    private String languageCode = "en";
    static String travelUrl = "https://api.open-meteo.com/v1/forecast?latitude=22.2783&longitude=114.1747&current=temperature_2m,relative_humidity_2m,precipitation,wind_gusts_10m&daily=temperature_2m_max,temperature_2m_min&timezone=auto";
    public static String geo = "https://geocoding-api.open-meteo.com/v1/search?name=Hong Kong&count=1&language=en&format=json";

    public void getlocation(String city) {
        geo = "https://geocoding-api.open-meteo.com/v1/search?name=" + city + "&count=1&language=en&format=json";
        String json ="";

        try {
            URL url = new URL(geo);

            String lati="";
            String longti="";
            String line;

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = url.openStream();
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is));

            while ((line = bufferReader.readLine())!= null){
                json = json + line+"\n";
            }

            if(!json.isEmpty()){
                JSONObject obj = new JSONObject(json);
                JSONArray info = obj.getJSONArray("results");
                JSONObject result = info.getJSONObject(0);
                lati = result.getString("latitude");
                longti = result.getString("longtitude");

            }
            is.close();
            bufferReader.close();
            traveljsonUrl(lati,longti);
            System.out.println(lati+"+"+longti);

        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }



    public void traveljsonUrl(String latitude, String longitude) {
        travelUrl = "https://api.open-meteo.com/v1/forecast?latitude="+ latitude +"&longitude="
                + longitude + "&current=temperature_2m,relative_humidity_2m,is_day,precipitation,wind_gusts_10m&daily=temperature_2m_max,temperature_2m_min&timezone=Asia%2FSingapore&forecast_days=1"
                + "&current=temperature_2m,relative_humidity_2m,is_day,precipitation,wind_gusts_10m"
                + "&daily=temperature_2m_max,temperature_2m_min"
                + "&timezone=Asia%2FSingapore"
                + "&forecast_days=1"
                + "&lang=" + this.languageCode;
    }

    public static String makeRequest() {
        String json= null;
        try {
            URL url = new URL(travelUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = url.openStream();
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = bufferReader.readLine()) != null) {
                json = json + line + "\n";
            }

            return json;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String [] toArray(JSONArray json) throws JSONException {
        int i = 0;
        String[] array = new String[7];
        while(json.getString(i)!= null){
            array[i]= json.getString(i);
        }return array;
    }



    public void run() {
            String latitude;
            String longitude;

            // "contactStr" variable store the json file content
            String contactStr = makeRequest();
            Log.e(TAG, "Response from url: " + contactStr);

            if (contactStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(contactStr);

                    // Getting JSON Array node
                    JSONArray travelData = jsonObj.getJSONArray("travelData");

                    // looping through All Contacts
                    for (int i = 0; i < travelData.length(); i++) {
                        JSONObject c = travelData.getJSONObject(i);

                        JSONObject current = c.getJSONObject("current");
                        String date_time = current.getString("time");
                        String[] datetime = date_time.split("T");
                        String temp_avg = current.getString("temperature_2m");
                        String humidity = current.getString("relative_humidity_2m");
                        String wind_gusts_10m = current.getString("wind_gusts_10m");
                        String precipitation = current.getString("precipitation");

                        JSONObject daily = c.getJSONObject("daily");
                        String[] date = toArray(daily.getJSONArray("time"));
                        String[] t_up = toArray(daily.getJSONArray("temperature_2m_max"));
                        String[] t_down = toArray(daily.getJSONArray("temperature_2m_mix"));
                        String[] uv = toArray(daily.getJSONArray("uv_index_clear_sky_max"));

                        String temp_down = t_up[0];
                        String temp_up = t_down[0];



                        TravelpageWeatherInfo.addtravelData(datetime[0], datetime[1], temp_avg, humidity, wind_gusts_10m, precipitation, temp_up, temp_down,date,t_up,t_down,uv);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }


}
