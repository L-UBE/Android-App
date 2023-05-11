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
import com.example.l3d_cube.Utility.MathUtils;
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
    private EditText zoffset;
    private Button sendButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMathBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        context = getContext();

        mathEquation = binding.mathEquation;
        resolution = binding.resolution;
        xoffset = binding.xoffset;
        yoffset = binding.yoffset;
        zoffset = binding.zoffset;
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

                String exp = mathEquation.getText().toString();
                int res = MathUtils.parseEditText(resolution);
                int xoff = MathUtils.parseEditText(xoffset);
                int yoff = MathUtils.parseEditText(yoffset);


                if (res > RESOLUTION_LIMIT) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            SystemUtils.systemErrorToast(context, "Invalid resolution");
                        }
                    });
                    return;
                }


                Argument varx = new Argument("x");
                Argument vary = new Argument("y");
                Expression bot = new Expression("solve( z^2-x, z, -7.5, 0 )", varx, vary);
                Expression top = new Expression("solve( z^2-x, z, 0, 7.5 )", varx, vary);
                boolean validExpression = bot.checkSyntax();

                if(!validExpression){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            SystemUtils.systemErrorToast(context, "Invalid expression");
                        }
                    });
                    return;
                }

                byte[] testData = new byte[4096];
                for (int x = 0; x < 16; x++) {
                    for(int y = 0; y < 16; y++) {
                        double scaledx = 1*x - 1*7.5;
                        double scaledy = 1*y - 1*7.5;
                        varx.setArgumentValue(scaledx);
                        vary.setArgumentValue(scaledy);
                        double test1 = top.calculate();
                        double test2 = bot.calculate();


                    }
                }

//                class HelloWorld {
//                    public static void main(String[] args) {
//
//                        double value = 0;
//
//                        for(int i = 0; i < 16; i++) {
//                            double temp = Math.abs((1*i - 1*7.5) - value);
//                            if(temp <= .5){
//                                System.out.println(temp);
//                                System.out.println(1*i - 1*7.5);
//                                return;
//                            }
//                        }
//
//                    }
//                }

                runOnUiThread(new Runnable() {
                    public void run() {

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
    }
}