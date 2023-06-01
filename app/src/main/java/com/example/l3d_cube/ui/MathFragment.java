package com.example.l3d_cube.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.l3d_cube.MainViewModel;
import com.example.l3d_cube.Utility.MathUtils;
import com.example.l3d_cube.databinding.FragmentMathBinding;

import java.util.HashMap;
import java.util.Map;


public class MathFragment extends Fragment {

    private FragmentMathBinding binding;
    private MainViewModel mainViewModel;

    private TextView equation;
    private String userInput = "";
    private int scale;
    private int xoffset;
    private int yoffset;
    private int zoffset;
    private ToggleButton fillin;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMathBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        equation = binding.mathEquation;
        fillin = binding.fillInToggleButton;

        binding.enter.setOnClickListener(view -> {
            scale = MathUtils.parseEditText(binding.scale);
            if(scale == 0) {
                scale = 1;
            }
            xoffset = MathUtils.parseEditText(binding.xoffset);
            yoffset = MathUtils.parseEditText(binding.yoffset);
            zoffset = MathUtils.parseEditText(binding.zoffset);
            String eq = equation.getText().toString();
            mainViewModel.computeMathEquation(eq, scale, xoffset, yoffset, zoffset);
        });

        fillin.setOnClickListener(view -> {
            mainViewModel.setFillIn(fillin.isChecked());
        });

        setupKeys();

        return root;
    }

    private void setupKeys() {
        Map<View, String> buttonMap = new HashMap<>();
        buttonMap.put(binding.nine, "9");
        buttonMap.put(binding.eight, "8");
        buttonMap.put(binding.seven, "7");
        buttonMap.put(binding.six, "6");
        buttonMap.put(binding.five, "5");
        buttonMap.put(binding.four, "4");
        buttonMap.put(binding.three, "3");
        buttonMap.put(binding.two, "2");
        buttonMap.put(binding.one, "1");
        buttonMap.put(binding.zero, "0");
        buttonMap.put(binding.decimal, ".");
        buttonMap.put(binding.sin, "sin(");
        buttonMap.put(binding.cos, "cos(");
        buttonMap.put(binding.tan, "tan(");
        buttonMap.put(binding.log, "log(");
        buttonMap.put(binding.ln, "ln(");
        buttonMap.put(binding.x, "x");
        buttonMap.put(binding.y, "y");
        buttonMap.put(binding.z, "z");
        buttonMap.put(binding.e, "E");
        buttonMap.put(binding.pi, "pi");
        buttonMap.put(binding.par1, "(");
        buttonMap.put(binding.par2, ")");
        buttonMap.put(binding.power, "^");
        buttonMap.put(binding.divide, "/");
        buttonMap.put(binding.multiply, "*");
        buttonMap.put(binding.subtract, "-");
        buttonMap.put(binding.add, "+");

        for (Map.Entry<View, String> entry : buttonMap.entrySet()) {
            entry.getKey().setOnClickListener(view -> {
                userInput += entry.getValue();
                equation.setText(userInput);
            });
        }

        binding.back.setOnClickListener(view -> {
            if(userInput.length() != 0) {
                userInput = userInput.substring(0, userInput.length() - 1);
                equation.setText(userInput);
            }
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