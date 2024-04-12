    package com.example.testsider.ui.home;

    import android.Manifest;
    import android.annotation.SuppressLint;
    import android.content.Context;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.content.res.Configuration;
    import android.content.res.Resources;
    import android.location.Address;
    import android.location.Geocoder;
    import android.location.Location;
    import android.location.LocationListener;
    import android.location.LocationManager;
    import android.os.Bundle;
    import android.util.DisplayMetrics;
    import android.util.Log;
    import android.util.Pair;
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
    import androidx.recyclerview.widget.DividerItemDecoration;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.testsider.Func.HomepageJsonHandlerThread;
    import com.example.testsider.Func.HomepageWeatherInfo;
    import com.example.testsider.Func.RecyclerViewAdapter;
    import com.example.testsider.MainActivity;
    import com.example.testsider.R;
    import com.example.testsider.databinding.FragmentHomeBinding;

    import java.text.SimpleDateFormat;
    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.Date;
    import java.util.List;
    import java.util.Locale;
    import java.util.concurrent.CountDownLatch;

    public class HomeFragment extends Fragment{

        private FragmentHomeBinding binding;
        private LocationManager locationManager;
        private LocationListener locationListener;
        Button button_location;
        TextView textView_location;
        TextView forecast_test;
        private String TAG = "HomeFragment";
        List<Address> addresses;
        RecyclerView recyclerView;
        TextView today, time;
        ImageView weather_icon;
        TextView instantTemp_up, instantTemp_down, instantTemp_currnet, todaygust, todayhumidity, todayprecipitation,precipitation_probability,uv_index;
        double latitude;
        double longitude;
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {

            binding = FragmentHomeBinding.inflate(inflater, container, false);
            View root = binding.getRoot();



            //location
//            textView_location = root.findViewById(R.id.current_location);
//            button_location =  root.findViewById(R.id.button_location);
            //Runtime permissions
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(),new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },100);
            }

            //        uv_index = findViewById(R.id.uv_index);
            recyclerView = root.findViewById(R.id.recyclerView);
            today = root.findViewById(R.id.today);
            time = root.findViewById(R.id.time);
            precipitation_probability= root.findViewById(R.id.precipitation_probability);
            weather_icon = root.findViewById(R.id.weathericon);
            instantTemp_up = root.findViewById(R.id.instant_temp_up);
            instantTemp_down = root.findViewById(R.id.instant_temp_down);
            instantTemp_currnet = root.findViewById(R.id.instant_temp_current);
            todaygust = root.findViewById(R.id.gust);
            todayhumidity = root.findViewById(R.id.humidity);
    //        todayprecipitation = findViewById(R.id.precipitation);
//            button_location= root.findViewById(R.id.button_location);




            HomepageJsonHandlerThread jsonHandlerThread = new HomepageJsonHandlerThread();
            this.getLocation(jsonHandlerThread);
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
//                String dateTimeStr = HomepageWeatherInfo.getTime();
//                LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//                String timeStr = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
//                time.setText(timeStr);

                // 獲取當前時間
                Date currentTime = new Date();
                // 設置日期格式
                SimpleDateFormat dateFormat = new SimpleDateFormat( "HH:mm");
                // 格式化時間
                String formattedTime = dateFormat.format(currentTime);
                time.setText(formattedTime);
                weather_icon.setImageResource(HomepageJsonHandlerThread.changeIcon());
                today.setText(HomepageWeatherInfo.getDate().get(0));
//                double temp = Double.parseDouble(HomepageWeatherInfo.getTemperature_2m());
//                double precip = Double.parseDouble(HomepageWeatherInfo.getRelative_humidity_2m());


                for(Pair<String,String>a :  HomepageWeatherInfo.getTemperature_2m_24()){
                    System.out.println("this is a:"+a.first +" and :"+a.second);
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(HomepageWeatherInfo.getTemperature_2m_24());
                recyclerView.setAdapter(adapter);

            }catch (InterruptedException e) {
                Log.e(TAG, "InterruptedException: " + e.getMessage());
            }


            return root;
        }


        private void getLocation(HomepageJsonHandlerThread jsonHandlerThread ) {
            try {
                locationManager = (LocationManager)   getActivity().getSystemService(Context.LOCATION_SERVICE);
                locationListener = new LocationListener() {

                    @Override
                    public void onLocationChanged(Location location) {

                         latitude = location.getLatitude();
                         longitude = location.getLongitude();
                        jsonHandlerThread.homejsonUrl(latitude,latitude);
                        System.out.println("this is :"+latitude+" and :"+longitude);

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


        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
    }