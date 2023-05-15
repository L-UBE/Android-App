package com.example.l3d_cube.ui;

import static com.scichart.core.utility.Dispatcher.runOnUiThread;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.l3d_cube.MainViewModel;
import com.example.l3d_cube.Utility.ArrayUtils;
import com.example.l3d_cube.Utility.MathUtils;
import com.example.l3d_cube.Utility.SystemUtils;
import com.example.l3d_cube.databinding.FragmentMathBinding;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;


public class MathFragment extends Fragment {

    private FragmentMathBinding binding;
    private Context context;
    private MainViewModel mainViewModel;

    private final int RESOLUTION_LIMIT = 1000;

    private EditText mathEquation;
    private EditText resolution;
    private EditText xoffset;
    private EditText yoffset;
    private EditText zoffset;
    private Button sendButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMathBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        context = getContext();

        mathEquation = binding.mathEquation;
        resolution = binding.resolution;
        xoffset = binding.xoffset;
        yoffset = binding.yoffset;
        zoffset = binding.zoffset;
        sendButton = binding.button;

        sendButton.setOnClickListener(view -> {
            mainViewModel.computeMathEquation(mathEquation.getText().toString());
        });

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