package com.example.testsider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testsider.Func.HomepageJsonHandlerThread;
import com.example.testsider.Func.HomepageWeatherInfo;
import com.example.testsider.ui.Language.LanguageFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testsider.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private SharedPreferences preferences;
    private String languageCode;
    private LocationManager locationManager;
    private LocationListener locationListener;

//    private String km = "km/h";
//    private String mm = "mm";
//    private String oc = "°C";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private String TAG = "MainActivity";
    Button button_location;
    TextView textView_location;
    List<Address> addresses;
    TextView today, time;
    ImageView weather_icon;
    TextView instantTemp_up, instantTemp_down, instantTemp_currnet, todaygust, todayhumidity, todayprecipitation,precipitation_probability,uv_index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);



        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_additional)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


//        uv_index = findViewById(R.id.uv_index);
        today = findViewById(R.id.today);
        time = findViewById(R.id.time);
        precipitation_probability= findViewById(R.id.precipitation_probability);
        weather_icon = findViewById(R.id.weathericon);
        instantTemp_up = findViewById(R.id.instant_temp_up);
        instantTemp_down = findViewById(R.id.instant_temp_down);
        instantTemp_currnet = findViewById(R.id.instant_temp_current);
        todaygust = findViewById(R.id.gust);
        todayhumidity = findViewById(R.id.humidity);
//        todayprecipitation = findViewById(R.id.precipitation);
        button_location= findViewById(R.id.button_location);

        HomepageJsonHandlerThread jsonHandlerThread = new HomepageJsonHandlerThread();
        getLocation(jsonHandlerThread);
        jsonHandlerThread.start();
        try {
            jsonHandlerThread.join();
            todaygust.setText(HomepageWeatherInfo.getWind_gusts_10m());
//            todayprecipitation.setText(HomepageWeatherInfo.getPrecipitation());
            todayhumidity.setText(HomepageWeatherInfo.getRelative_humidity_2m()+"%");
            instantTemp_up.setText(HomepageWeatherInfo.getTemperature_2m_min().get(0)+"°C");
            instantTemp_down.setText(HomepageWeatherInfo.getTemperature_2m_max().get(0)+"°C");
//            double temperatureMax = Double.parseDouble(HomepageWeatherInfo.getTemperature_2m_max().get(0));
//            double temperatureMin = Double.parseDouble(HomepageWeatherInfo.getTemperature_2m_min().get(0));
//            double result =(temperatureMax + temperatureMin) / 2;
            instantTemp_currnet.setText(HomepageWeatherInfo.getTemperature_2m()+"°C");
            precipitation_probability.setText(HomepageWeatherInfo.getPrecipitation_probability()+"%");
//            uv_index.setText(HomepageWeatherInfo.getUv_index());
            String dateTimeStr = HomepageWeatherInfo.getTime();
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            String timeStr = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            time.setText(timeStr);

//            // 獲取當前時間
//            Date currentTime = new Date();
//            // 設置日期格式
//            SimpleDateFormat dateFormat = new SimpleDateFormat( "HH:mm");
//            // 格式化時間
//            String formattedTime = dateFormat.format(currentTime);
//            time.setText(formattedTime);

            today.setText(HomepageWeatherInfo.getDate().get(0));
            double temp = Double.parseDouble(HomepageWeatherInfo.getTemperature_2m());
            double precip = Double.parseDouble(HomepageWeatherInfo.getRelative_humidity_2m());
            if(  temp >20 && precip <=0.0){
                weather_icon.setImageResource(R.drawable.pic50);
            }


        }catch (InterruptedException e) {
            Log.e(TAG, "InterruptedException: " + e.getMessage());
        }

        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent refresh = new Intent(view.getContext(), MainActivity.class);
                finish();
                startActivity(refresh);
            }
        });


    }
    private void getLocation(HomepageJsonHandlerThread jsonHandlerThread) {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();


                    jsonHandlerThread.homejsonUrl(String.valueOf(latitude), String.valueOf(longitude));


                    locationManager.removeUpdates(locationListener);
                }

                @Override
                public void onLocationChanged(@NonNull List<Location> locations) {
                    LocationListener.super.onLocationChanged(locations);
                }
                @Override
                public void onFlushComplete(int requestCode) {
                    LocationListener.super.onFlushComplete(requestCode);
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    LocationListener.super.onStatusChanged(provider, status, extras);
                }
                @Override
                public void onProviderEnabled(@NonNull String provider) {
                    LocationListener.super.onProviderEnabled(provider);
                }
                @Override
                public void onProviderDisabled(@NonNull String provider) {
                    LocationListener.super.onProviderDisabled(provider);
                }
            };


            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }




    public void changeLanCh(View view){
       setLocale("zh");
       Intent refresh = new Intent(this, MainActivity.class);
       finish();
       startActivity(refresh);
   }

    public void changeLanEn(View view){
        //改UI 語言
        setLocale("en");
        Intent refresh = new Intent(this, MainActivity.class);
        finish();
        startActivity(refresh);

        //改url語言
//        HomepageJsonHandlerThread jsonHandlerThread = new HomepageJsonHandlerThread();
//        jsonHandlerThread.setLanguageCode(languageCode);
//        jsonHandlerThread.start();
    }

    private void setLocale(String lang){
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(lang);
        resources.updateConfiguration(configuration,metrics);
        onConfigurationChanged(configuration);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}