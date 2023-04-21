package com.example.l3d_cube.bluetooth.Utility;

import no.nordicsemi.android.ble.callback.profile.ProfileDataCallback;

public class BluetoothDataUtils {
    public static byte[] parseIncomingBluetoothData(byte[] data)  {
        byte[] testData = new byte[2048];
        java.util.Arrays.fill(testData, 0, 2048, (byte) 0x00);
        return testData;
    }
}
