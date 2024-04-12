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


    }




    public void changeLanCh(View view){
       setLocale("zh");
       HomepageWeatherInfo.setKm("公里/小時");
       Intent refresh = new Intent(this, MainActivity.class);
       finish();
       startActivity(refresh);
   }

    public void changeLanEn(View view){
        //改UI 語言
        setLocale("en");
        HomepageWeatherInfo.setKm("km/h");
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