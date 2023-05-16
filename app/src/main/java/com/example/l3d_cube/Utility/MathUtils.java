package com.example.l3d_cube.Utility;

import android.widget.EditText;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

public class MathUtils {

    public static int parseEditText(EditText editText) {
        String text = editText.getText().toString();
        if(!text.isEmpty()) {
            return Integer.parseInt(text);
        }
        return 0;
    }

    public static boolean validateEquation(String equation) {
        Argument varX = new Argument("x");
        Argument varY = new Argument("y");
        Argument varZ = new Argument("z");
        return new Expression(equation, varX, varY, varZ).checkSyntax();
    }

}
