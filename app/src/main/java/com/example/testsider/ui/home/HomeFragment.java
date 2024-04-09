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

public class HomeFragment extends Fragment implements LocationListener {

    private FragmentHomeBinding binding;

    Button button_location;
    TextView textView_location;
    LocationManager locationManager;
    List<Address> addresses;
    String today, time;
    ImageView weather_icon;
    TextView instantTemp_up, instantTemp_down, instantTemp_avg, todaygust, todayhumidity, todayprecipitation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//
//
//        //location
//        textView_location = root.findViewById(R.id.current_location);
//        button_location =  root.findViewById(R.id.button_location);
//        //Runtime permissions
//        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(requireActivity(),new String[]{
//                    Manifest.permission.ACCESS_FINE_LOCATION
//            },100);
//        }
//        button_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //create method
//                getLocation();
//            }
//        });
//
////        HomepageWeatherInfo, time&date
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
//        jsonHandlerThread.homejsonUrl((String)addresses.get(0).toString() ,(String)addresses.get(1).toString());
//        jsonHandlerThread.start();

        return root;




    }
    @SuppressLint("MissingPermission")
    private void getLocation(){
        try {
            locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, HomeFragment.this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(getContext(), ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(requireActivity(), Locale.getDefault());
            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);
            String[] buff = address.split(",");
            textView_location.setText(buff[buff.length-3] + ',' + buff[buff.length-1]);
        }catch (Exception e){
            e.printStackTrace();
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}