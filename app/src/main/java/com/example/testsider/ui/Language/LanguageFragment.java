package com.example.testsider.ui.Language;

import static androidx.core.app.ActivityCompat.recreate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

//import com.example.testsider.databinding.FragmentGalleryBinding;
import com.example.testsider.MainActivity;
import com.example.testsider.R;
import com.example.testsider.databinding.FragmentLanguageBinding;

import java.util.Locale;
import java.util.prefs.PreferenceChangeEvent;

public class LanguageFragment extends Fragment {

    private FragmentLanguageBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentLanguageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }







    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}