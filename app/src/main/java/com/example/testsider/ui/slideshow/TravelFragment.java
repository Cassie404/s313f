package com.example.testsider.ui.slideshow;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.testsider.Func.TravelpageJsonHandlerThread;
import com.example.testsider.Func.TravelpageWeatherInfo;
import com.example.testsider.databinding.FragmentTravelBinding;

import java.util.HashMap;

public class TravelFragment extends Fragment {

    private FragmentTravelBinding binding;

    protected String place;

    public String url= "https://geocoding-api.open-meteo.com/v1/search?name=Berlin&count=1&language=en&format=json";

    TextView city;
    TextView temp;
    TextView date;
    ImageView icon;
    TextView gust;
    TextView humidity;
    TextView precipitation;
    TextView maxtemp;
    TextView mintemp;
    EditText location;
    Button button;
    TextView date1;
    TextView date2;
    TextView high1;
    TextView high2;
    TextView low1;
    TextView low2;




    public void findlocation(){
        String city = String.valueOf(location.getText());
        TravelpageJsonHandlerThread travel = new TravelpageJsonHandlerThread();
        travel.getlocation(city);

    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentTravelBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        temp = binding.avgtemp;
        date = binding.today;
        icon = binding.weathericon;
        gust = binding.gust;
        humidity = binding.humidity;
        precipitation = binding.precipitation;
        maxtemp = binding.instantTempUp;
        mintemp = binding.instantTempDown;
        location = binding.search;
        button = binding.button;
        date1 = binding.date1;
        date2 = binding.date2;
        high1 = binding.high1;
        high2 = binding.high2;
        low1 = binding.low1;
        low2 = binding.low2;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findlocation();
            }
        });

        return root;
    }

    @SuppressLint("MissingPermission")
    private void getLocation(){
        try {


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setAttributes(){
        if(!TravelpageWeatherInfo.dataList.isEmpty()){
            HashMap<String,String> info = TravelpageWeatherInfo.dataList.get(0);
            maxtemp.setText(info.get(TravelpageWeatherInfo.TEMP_UP)+"°C");
            mintemp.setText(info.get(TravelpageWeatherInfo.TEMP_DOWN)+"°C");
            temp.setText(info.get(TravelpageWeatherInfo.TEMP_AVG)+"°C");
            gust.setText(info.get(TravelpageWeatherInfo.GUST)+"km/h");
            humidity.setText(info.get(TravelpageWeatherInfo.HUMIDITY)+"%");
            precipitation.setText(info.get(TravelpageWeatherInfo.PRECIPITATION)+"mm");
            date1.setText(TravelpageWeatherInfo.date[0]);
            date2.setText(TravelpageWeatherInfo.date[1]);
            high1.setText(TravelpageWeatherInfo.t_up[0]);
            high2.setText(TravelpageWeatherInfo.t_up[1]);
            low1.setText(TravelpageWeatherInfo.t_down[0]);
            low2.setText(TravelpageWeatherInfo.t_down[1]);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}