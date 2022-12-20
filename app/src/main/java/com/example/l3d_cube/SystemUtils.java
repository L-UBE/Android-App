package com.example.l3d_cube;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

public class SystemUtils {
    public static void restartApp(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(context.getPackageName());
        Intent mainIntent = Intent.makeRestartActivityTask(intent.getComponent());
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }
}
