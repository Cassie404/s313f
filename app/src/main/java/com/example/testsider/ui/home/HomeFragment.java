package com.example.testsider.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.testsider.Func.HomepageJsonHandlerThread;
import com.example.testsider.R;
import com.example.testsider.databinding.FragmentHomeBinding;

import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment{

    private FragmentHomeBinding binding;
    private LocationManager locationManager;
    private LocationListener locationListener;
    Button button_location;
    TextView textView_location;

    List<Address> addresses;
    String today, time;
    ImageView weather_icon;
    TextView instantTemp_up, instantTemp_down, instantTemp_avg, todaygust, todayhumidity, todayprecipitation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        //location
        textView_location = root.findViewById(R.id.current_location);
        button_location =  root.findViewById(R.id.button_location);
        //Runtime permissions
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }



//        button_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //create method
//                getLocation();
//            }
//        });


//        HomepageWeatherInfo, time&date
//        today = String.valueOf( root.findViewById(R.id.today));
//        time = String.valueOf( root.findViewById(R.id.time));
//
//        weather_icon =  root.findViewById(R.id.weathericon);
//        instantTemp_up =  root.findViewById(R.id.instant_temp_up);
//        instantTemp_down =  root.findViewById(R.id.instant_temp_down);
//        instantTemp_avg =  root.findViewById(R.id.instant_temp_avg);
//        todaygust =  root.findViewById(R.id.gust);
//        todayhumidity =  root.findViewById(R.id.humidity);
//        todayprecipitation =  root.findViewById(R.id.precipitation);
//
//        HomepageJsonHandlerThread jsonHandlerThread = new HomepageJsonHandlerThread();
//        getLocation(jsonHandlerThread);
//        jsonHandlerThread.start();


        return root;




    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}