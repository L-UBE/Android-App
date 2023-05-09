package com.example.l3d_cube.ui;

import static com.example.l3d_cube.Model.PresetShapes.Cube.cube;
import static com.example.l3d_cube.Model.PresetShapes.Cylinder.cylinder;
import static com.example.l3d_cube.Model.PresetShapes.Rhomboid.rhomboid;
import static com.example.l3d_cube.Model.PresetShapes.Sphere.sphere;

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
            byte[] testData = new byte[4096];
            java.util.Arrays.fill(testData, 0, 4096, (byte) 0x00);
            mainViewModel.handleIncomingBluetoothData(testData);
        });

        ImageButton preset2 = binding.preset2;
        preset2.setOnClickListener(v -> {
            byte[] testData = new byte[4096];
            java.util.Arrays.fill(testData, 0, 2, (byte) 0x01);
            mainViewModel.handleIncomingBluetoothData(testData);
        });

        ImageButton preset3 = binding.preset3;
        preset3.setOnClickListener(v -> {
            byte[] testData = new byte[4096];
            java.util.Arrays.fill(testData, 0, 4096, (byte) 0x02);
            mainViewModel.handleIncomingBluetoothData(testData);
        });

        ImageButton preset4 = binding.preset4;
        preset4.setOnClickListener(v -> {
            byte[] testData = new byte[4096];
            java.util.Arrays.fill(testData, 0, 4096, (byte) 0x03);
            mainViewModel.handleIncomingBluetoothData(testData);
        });

        ImageButton preset5 = binding.preset5;
        preset5.setOnClickListener(v -> {
            mainViewModel.handleIncomingBluetoothData(cube);
        });

        ImageButton preset6 = binding.preset6;
        preset6.setOnClickListener(v -> {
            mainViewModel.handleIncomingBluetoothData(sphere);
        });

        ImageButton preset7 = binding.preset7;
        preset7.setOnClickListener(v -> {
            mainViewModel.handleIncomingBluetoothData(cylinder);
        });

        ImageButton preset8 = binding.preset8;
        preset8.setOnClickListener(v -> {
            mainViewModel.handleIncomingBluetoothData(rhomboid);
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