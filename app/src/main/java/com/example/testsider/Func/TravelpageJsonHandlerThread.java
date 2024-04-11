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

    private static final String TAG = "TraveljsonHandlerThread";
    private double lati = 22.2783;
    private double longti = 114.1747;
    private String city = "";
    private String languageCode = "en";
    String travelUrl = "https://api.open-meteo.com/v1/forecast?latitude=22.2783&longitude=114.1747&current=temperature_2m,cloud_cover,relative_humidity_2m,precipitation,wind_gusts_10m&daily=temperature_2m_max,temperature_2m_min&timezone=auto&forecast_days=3";
    public String geo = "https://geocoding-api.open-meteo.com/v1/search?name=Hong%20Kong&count=1&language=en&format=json";

    public TravelpageJsonHandlerThread(String city) {
        this.city = city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void updatelocation() {
        this.city = this.city.replaceAll(" ","%20");
        geo = "https://geocoding-api.open-meteo.com/v1/search?name=" + this.city+ "&count=1&language=en&format=json";
        System.out.println(geo);
        String json ="";

        try {
            URL url = new URL(geo);

            String line;

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = url.openStream();

            InputStreamReader a = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(a);
            conn.setRequestMethod("GET");

            // Read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            json = inputStreamToString(in);

            //extract information
            JSONObject jsonObj = new JSONObject(json);
            if(!json.isEmpty()){
                JSONObject obj = new JSONObject(json);
                JSONArray info = obj.getJSONArray("results");
                JSONObject result = info.getJSONObject(0);
                this.lati = result.getDouble("latitude");
                this.longti = result.getDouble("longitude");
                traveljsonUrl(this.lati, this.longti);
                System.out.println(this.lati+"and"+this.longti);

            }
            is.close();
            bufferReader.close();
            System.out.println(this.lati+"+"+this.longti);

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



    public void traveljsonUrl(double latitude, double longitude) {
        travelUrl = "https://api.open-meteo.com/v1/forecast?latitude="+ latitude +"&longitude="
                + longitude
                + "&current=temperature_2m,cloud_cover,relative_humidity_2m,is_day,precipitation,wind_gusts_10m"
                + "&daily=temperature_2m_max,rain_sum,temperature_2m_min,uv_index_clear_sky_max"
                + "&timezone=auto"
                + "&forecast_days=7"
                + "&lang=" + this.languageCode;
    }

    public String makeRequest() {
        String response = null;
        try {
            URL url = new URL(this.travelUrl);
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

    public static String [] toArray(JSONArray json) throws JSONException {
        int i = 0;
        String[] array = new String[7];
        while(json.getString(i)!= null){
            array[i]= json.getString(i);
        }return array;
    }



    public void run() {
        this.updatelocation();
        // "contactStr" variable store the json file content
        String contactStr = makeRequest();
        Log.e(TAG, "Response from url: " + contactStr);

        if (contactStr != null) {
            try {
                JSONObject c = new JSONObject(contactStr);

                // Getting JSON Array node
                //JSONArray travelData = jsonObj.getJSONArray("");

                // looping through All Contacts
                //for (int i = 0; i < travelData.length(); i++) {
                // JSONObject c = travelData.getJSONObject(i);


                JSONObject current = c.getJSONObject("current");
                TravelpageWeatherInfo.setDATE( current.getString("time").split("T")[0]);
                TravelpageWeatherInfo.setTIME(current.getString("time").split("T")[1]);
                TravelpageWeatherInfo.setTempAvg(Double.toString(current.getDouble("temperature_2m")));
                TravelpageWeatherInfo.setHUMIDITY(Double.toString(current.getDouble("relative_humidity_2m")));
                TravelpageWeatherInfo.setGUST(Double.toString(current.getDouble("wind_gusts_10m")));
                TravelpageWeatherInfo.setPRECIPITATION(Double.toString(current.getDouble("precipitation")));
                TravelpageWeatherInfo.setCLOUD(Integer.toString(current.getInt("cloud_cover")));

                JSONObject daily = c.getJSONObject("daily");
                JSONArray  date = daily.getJSONArray("time");


                for (int i = 0; i < date.length(); i++) {
                    TravelpageWeatherInfo.getDate().add(daily.getJSONArray("time").getString(i));
                    TravelpageWeatherInfo.getT_up().add(Double.toString(daily.getJSONArray("temperature_2m_max").getDouble(i)));
                    TravelpageWeatherInfo.getT_down().add(Double.toString(daily.getJSONArray("temperature_2m_min").getDouble(i)));
                    TravelpageWeatherInfo.getSky().add(Double.toString(daily.getJSONArray("uv_index_clear_sky_max").getDouble(i)));
                    TravelpageWeatherInfo.getRainSum().add(Double.toString(daily.getJSONArray("rain_sum").getDouble(i)));
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }


    }

}
