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
import com.example.testsider.R;
import com.example.testsider.databinding.FragmentTravelBinding;

import java.util.HashMap;

public class TravelFragment extends Fragment {

    private FragmentTravelBinding binding;

    protected String place;

    public String url= "https://geocoding-api.open-meteo.com/v1/search?name=Berlin&count=1&language=en&format=json";

    TextView temp;

    TextView date;
    ImageView icon;
    static TextView gust;
    static TextView humidity;
    static TextView precipitation;
    static TextView maxtemp;
    static TextView mintemp;
    EditText location;
    Button button;
    TextView date1, date2, date3;

    TextView high1,high2,high3;

    TextView low1, low2,low3;

    ImageView icon1,icon2,icon3;



    public void findlocation(){
        String city = String.valueOf(location.getText());
        TravelpageJsonHandlerThread travel = new TravelpageJsonHandlerThread(city);
        travel.start();
        try {
            travel.join();
            setAttributes();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);}
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
        date3 = binding.date3;
        high1 = binding.high1;
        high2 = binding.high2;
        high3 = binding.high3;
        low1 = binding.low1;
        low2 = binding.low2;
        low3 = binding.low3;
        icon1 = binding.pic1;
        icon2 = binding.pic2;
        icon3 = binding.pic3;

        TravelpageJsonHandlerThread travel = new TravelpageJsonHandlerThread("Hong Kong");
        travel.start();
        try {
            travel.join();
            setAttributes();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);}
        TravelpageWeatherInfo info = new TravelpageWeatherInfo();
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

    public int changeIcon(int day){
        double sun = Double.parseDouble(TravelpageWeatherInfo.getSky().get(day)) ;
        double rain = Double.parseDouble(TravelpageWeatherInfo.getRainSum().get(day));
        if(rain  > 0){
            if(rain <=2.5 )
                return R.drawable.pic62;
            if(rain >2.5)
                return R.drawable.pic63;
        }else if (sun < 7.5){
            if(sun  <= 6)
                return R.drawable.pic50;
            if(sun  < 5 && sun >= 3 )
                return R.drawable.pic60;
            if(sun > 3)
                return R.drawable.pic61;
        }
        return R.drawable.pic50;
    }

    @SuppressLint("SetTextI18n")
    public void setAttributes() {

        date.setText(TravelpageWeatherInfo.getDATE());
        icon.setImageResource(changeIcon(0));
        temp.setText(TravelpageWeatherInfo.getTempAvg()+ "°C");
        maxtemp.setText(TravelpageWeatherInfo.getT_up().get(0) + "°C");
        mintemp.setText(TravelpageWeatherInfo.getT_down().get(0) + "°C");
        gust.setText(TravelpageWeatherInfo.getGUST()+ "°C");
        humidity.setText(TravelpageWeatherInfo.getHUMIDITY() + "°C");
        precipitation.setText(TravelpageWeatherInfo.getPRECIPITATION()+ "mm");
        date1.setText(TravelpageWeatherInfo.getDate().get(1));
        date2.setText(TravelpageWeatherInfo.getDate().get(2));
        date3.setText(TravelpageWeatherInfo.getDate().get(3));
        icon1.setImageResource(changeIcon(1));
        icon2.setImageResource(changeIcon(2));
        icon3.setImageResource(changeIcon(3));
        high1.setText(TravelpageWeatherInfo.getT_up().get(1)+ "°C");
        high2.setText(TravelpageWeatherInfo.getT_up().get(2)+ "°C");
        high3.setText(TravelpageWeatherInfo.getT_up().get(3)+ "°C");
        low1.setText(TravelpageWeatherInfo.getT_down().get(1)+ "°C");
        low2.setText(TravelpageWeatherInfo.getT_down().get(2)+ "°C");
        low3.setText(TravelpageWeatherInfo.getT_down().get(3)+ "°C");

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}