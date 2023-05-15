package com.example.l3d_cube.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.l3d_cube.MainViewModel;
import com.example.l3d_cube.databinding.FragmentDisplaySettingsBinding;
import com.example.l3d_cube.databinding.FragmentMathBinding;

public class DisplaySettingsFragment extends Fragment {

    private FragmentDisplaySettingsBinding binding;
    private Context context;
    private MainViewModel mainViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDisplaySettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        context = getContext();

        return root;
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
