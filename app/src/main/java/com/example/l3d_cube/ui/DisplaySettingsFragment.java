package com.example.l3d_cube.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.l3d_cube.MainViewModel;
import com.example.l3d_cube.databinding.FragmentDisplaySettingsBinding;

import java.util.HashMap;
import java.util.Map;

public class DisplaySettingsFragment extends Fragment {

    private FragmentDisplaySettingsBinding binding;
    private Context context;
    private MainViewModel mainViewModel;

    private SeekBar brightness;

    private ToggleButton power;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDisplaySettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        context = getContext();

        brightness = binding.brightnessSeekBar;
        brightness.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mainViewModel.setBrightness(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        power = binding.powerToggleButton;
        power.setOnClickListener(view -> {
            mainViewModel.setPower(power.isChecked());
        });

        setupColorPresets();

        return root;
    }

    private void setupColorPresets() {
        Map<View, String> colorMap = new HashMap<>();
        colorMap.put(binding.static1,"s1");
        colorMap.put(binding.static2,"s2");
        colorMap.put(binding.static3,"s3");
        colorMap.put(binding.static4,"s4");
        colorMap.put(binding.static5,"s5");
        colorMap.put(binding.static6,"s6");
        colorMap.put(binding.static7,"s7");
        colorMap.put(binding.static8,"s8");
        colorMap.put(binding.static9,"s9");
        colorMap.put(binding.static10,"s10");
        colorMap.put(binding.static11,"s11");
        colorMap.put(binding.static12,"s12");
        colorMap.put(binding.static13,"s13");
        colorMap.put(binding.static14,"s14");
        colorMap.put(binding.static15,"s15");

        colorMap.put(binding.gradientX1,"gx1");
        colorMap.put(binding.gradientX2,"gx2");
        colorMap.put(binding.gradientX3,"gx3");

        colorMap.put(binding.gradientY1,"gy1");
        colorMap.put(binding.gradientY2,"gy2");
        colorMap.put(binding.gradientY3,"gy3");

        colorMap.put(binding.gradientZ1,"gz1");
        colorMap.put(binding.gradientZ2,"gz2");
        colorMap.put(binding.gradientZ3,"gz3");

        colorMap.put(binding.gradientL1,"gl1");
        colorMap.put(binding.gradientL2,"gl2");

        for (Map.Entry<View, String> entry : colorMap.entrySet()) {
            entry.getKey().setOnClickListener(view -> {
                mainViewModel.setColor(entry.getValue());
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


}
