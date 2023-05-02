package com.example.l3d_cube.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.l3d_cube.MainViewModel;
import com.example.l3d_cube.Model.LED.PresetShapes;
import com.example.l3d_cube.Model.PresetModel;
import com.example.l3d_cube.databinding.FragmentUploadBinding;

public class UploadFragment extends Fragment {

    private FragmentUploadBinding binding;
    private Context context;
    private MainViewModel mainViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUploadBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        context = getContext();

        setupPresets();

        return root;
    }

    private void setupPresets() {
        ImageButton preset1 = binding.preset1;
        preset1.setOnClickListener(v -> {
            byte[] testData = new byte[512];
            java.util.Arrays.fill(testData, 0, 512, (byte) 0x00);
            mainViewModel.handleIncomingBluetoothData(testData);
        });

        ImageButton preset2 = binding.preset2;
        preset2.setOnClickListener(v -> {
            byte[] testData = new byte[512];
            java.util.Arrays.fill(testData, 0, 512, (byte) 0x01);
            mainViewModel.handleIncomingBluetoothData(testData);
        });

        ImageButton preset3 = binding.preset3;
        preset3.setOnClickListener(v -> {
            byte[] testData = new byte[512];
            java.util.Arrays.fill(testData, 0, 512, (byte) 0x02);
            mainViewModel.handleIncomingBluetoothData(testData);
        });

        ImageButton preset4 = binding.preset4;
        preset4.setOnClickListener(v -> {
            byte[] testData = new byte[512];
            java.util.Arrays.fill(testData, 0, 512, (byte) 0x03);
            mainViewModel.handleIncomingBluetoothData(testData);
        });

        ImageButton preset5 = binding.preset5;
        preset5.setOnClickListener(v -> {
            byte[] cube = PresetShapes.cube;
            mainViewModel.handleIncomingBluetoothData(cube);
        });

        ImageButton preset6 = binding.preset6;
        preset6.setOnClickListener(v -> {
            byte[] sphere = PresetShapes.sphere;
            mainViewModel.handleIncomingBluetoothData(sphere);
        });

        ImageButton preset7 = binding.preset7;
        preset7.setOnClickListener(v -> {
            mainViewModel.rotate(-5);
        });

        ImageButton preset8 = binding.preset8;
        preset8.setOnClickListener(v -> {
            mainViewModel.rotate(5);
        });
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