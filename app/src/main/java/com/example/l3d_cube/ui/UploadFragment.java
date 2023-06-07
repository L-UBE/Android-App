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
import com.example.l3d_cube.databinding.FragmentUploadBinding;

public class UploadFragment extends Fragment {

    private FragmentUploadBinding binding;
    private MainViewModel mainViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUploadBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        setupPresets();

        return root;
    }

    private void setupPresets() {
        binding.preset1.setOnClickListener(v -> {
            mainViewModel.setShape("cube");
        });

        binding.preset2.setOnClickListener(v -> {
            mainViewModel.setShape("sphere");
        });

        binding.preset3.setOnClickListener(v -> {
            mainViewModel.setShape("cylinder");
        });

        binding.preset4.setOnClickListener(v -> {
            mainViewModel.setShape("rhomboid");
        });

        binding.preset5.setOnClickListener(v -> {
            byte[] testData = new byte[4096];
            java.util.Arrays.fill(testData, 0, 4096, (byte) 0x01);
            mainViewModel.setGenericModel(testData);
        });

        binding.preset6.setOnClickListener(v -> {

        });

        binding.preset7.setOnClickListener(v -> {

        });

        binding.preset8.setOnClickListener(v -> {

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