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

import com.example.l3d_cube.Utility.ArrayUtils;
import com.example.l3d_cube.Utility.SystemUtils;
import com.example.l3d_cube.databinding.FragmentMathBinding;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;


public class MathFragment extends Fragment {

    private FragmentMathBinding binding;
    private Context context;

    private final int RESOLUTION_LIMIT = 1000;

    private EditText mathEquation;
    private EditText resolution;
    private EditText xoffset;
    private EditText yoffset;
    private Button sendButton;

    private ProgressBar progressBar;

    FragmentDataTransfer fragmentDataTransfer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMathBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        context = getContext();

        progressBar = binding.progressBar;

        mathEquation = binding.mathEquation;
        resolution = binding.resolution;
        xoffset = binding.xoffset;
        yoffset = binding.yoffset;
        sendButton = binding.button;

        sendButton.setOnClickListener(view -> {
            computeMathEquation();
        });
        return root;
    }

    private void computeMathEquation(){
        new Thread(new Runnable() {
            @Override public void run() {
                long startTimeMills = System.currentTimeMillis();

                progressBar.setProgress(0);
                String exp = mathEquation.getText().toString();
                int res = parseEditText(resolution);
                int xoff = parseEditText(xoffset);
                int yoff = parseEditText(yoffset);


                if (res > RESOLUTION_LIMIT) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            SystemUtils.systemErrorToast(context, "Invalid resolution");
                        }
                    });
                    return;
                }

                Argument varX = new Argument("x");
                Argument varY = new Argument("y");
                Expression e = new Expression(exp, varX ,varY);

                boolean validExpression = e.checkSyntax();
                if(!validExpression){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            SystemUtils.systemErrorToast(context, "Invalid expression");
                        }
                    });
                    return;
                }

                int[][] z = new int[res][res];
                for (int x = 0; x < res; x++) {
                    for(int y = 0; y < res; y++) {
                        varX.setArgumentValue(x);
                        varY.setArgumentValue(y);
                        z[x][y] = (int) e.calculate();
                    }
                }

                int[] flatArray = ArrayUtils.flatten(z);

                runOnUiThread(new Runnable() {
                    public void run() {
                        sendToBluetooth(ArrayUtils.intArrayToByteArray(flatArray));
                    }
                });

                long endTimeMills = System.currentTimeMillis();
                runOnUiThread(new Runnable() {
                    public void run() {
                        SystemUtils.systemInfoToast(context, "Compution complete, elapsed time: " + (endTimeMills - startTimeMills) + " ms");
                    }
                });
            }
        }).start();
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