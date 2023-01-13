package com.example.l3d_cube.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.l3d_cube.ArrayUtils;
import com.example.l3d_cube.SystemUtils;
import com.example.l3d_cube.ui.FragmentDataTransfer;
import com.example.l3d_cube.databinding.FragmentHomeBinding;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private final int RESOLUTION_LIMIT = 100;

    private EditText mathEquation;
    private EditText resolution;
    private EditText xoffset;
    private EditText yoffset;
    private Button sendButton;

    FragmentDataTransfer fragmentDataTransfer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Context context = getContext();

        mathEquation = binding.mathEquation;
        resolution = binding.resolution;
        xoffset = binding.xoffset;
        yoffset = binding.yoffset;
        sendButton = binding.button;

        sendButton.setOnClickListener(view -> {
            long startTimeMills = System.currentTimeMillis();


            String exp = mathEquation.getText().toString();
            int res = parseEditText(resolution);
            int xoff = parseEditText(xoffset);
            int yoff = parseEditText(yoffset);


            if (res > RESOLUTION_LIMIT) {
                SystemUtils.systemErrorToast(context, "Invalid resolution");
                return;
            }

            Argument varX = new Argument("x");
            Argument varY = new Argument("y");
            Expression e = new Expression(exp, varX ,varY);

            boolean validExpression = e.checkSyntax();
            if(!validExpression){
                SystemUtils.systemErrorToast(context, "Invalid expression");
                return;
            }

            int[][] z = new int[100][100];
            for (int x = 0; x < 100; x++) {
                for(int y = 0; y < 100; y++) {
                    varX.setArgumentValue(x);
                    varY.setArgumentValue(y);
                    z[x][y] = (int) e.calculate();
                }
            }

            int[] flatArray = ArrayUtils.flatten(z);

            sendToBluetooth(ArrayUtils.intArrayToByteArray(flatArray));

            long endTimeMills = System.currentTimeMillis();
            SystemUtils.systemInfoToast(context, "Elapsed Time: " + (endTimeMills - startTimeMills));
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
        fragmentDataTransfer = (FragmentDataTransfer) context;
    }

    private int parseEditText(EditText editText) {
        String text = editText.getText().toString();
        if(!text.isEmpty()) {
            return Integer.parseInt(text);
        }
        return 0;
    }

    public void sendToBluetooth(byte[] data) {
        fragmentDataTransfer.fragmentToBluetooth(data);
    }

    public void sendToBluetooth(String data) {
        fragmentDataTransfer.fragmentToBluetooth(data);
    }
}