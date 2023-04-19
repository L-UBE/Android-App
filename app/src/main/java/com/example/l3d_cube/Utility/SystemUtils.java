package com.example.l3d_cube.Utility;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.example.l3d_cube.R;

import es.dmoral.toasty.Toasty;

public class SystemUtils {
    public static void restartApp(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(context.getPackageName());
        Intent mainIntent = Intent.makeRestartActivityTask(intent.getComponent());
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }

    public static void systemInfoToast(Context context, String message) {
        Toasty.custom(context,
                        message,
                        R.drawable.settings,
                        es.dmoral.toasty.R.color.infoColor ,
                        Toast.LENGTH_LONG,
                        true,
                        true)
                .show();
    }

    public static void systemErrorToast(Context context, String message) {
        Toasty.error(context, message).show();
    }
}
