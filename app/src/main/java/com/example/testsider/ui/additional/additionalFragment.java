package com.example.testsider.ui.additional;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testsider.Func.HomepageWeatherInfo;
import com.example.testsider.R;
import com.example.testsider.databinding.FragmentAdditionalBinding;
import com.example.testsider.databinding.FragmentHomeBinding;


public class additionalFragment extends Fragment {
    private FragmentAdditionalBinding binding;
    TextView uv,cloud,visibility,sea,atemp;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAdditionalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        uv = root.findViewById(R.id.uv_index);
        cloud= root.findViewById(R.id.cloud);
        visibility= root.findViewById(R.id.visibility);
        sea= root.findViewById(R.id.sea_level);
        atemp = root.findViewById(R.id.a_temp);

        uv.setText(HomepageWeatherInfo.getUv_index());
        cloud.setText(HomepageWeatherInfo.getCloud_cover()+"%");
        visibility.setText(HomepageWeatherInfo.getVisibility()+"m");
        sea.setText(HomepageWeatherInfo.getPressure_msl()+"hPa");
        atemp.setText(HomepageWeatherInfo.getApparent_temperature()+"°C");
        return root;


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}



