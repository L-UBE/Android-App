package com.example.l3d_cube.Utility;

import android.widget.EditText;

public class MathUtils {

    public static int parseEditText(EditText editText) {
        String text = editText.getText().toString();
        if(!text.isEmpty()) {
            return Integer.parseInt(text);
        }
        return 0;
    }

}
