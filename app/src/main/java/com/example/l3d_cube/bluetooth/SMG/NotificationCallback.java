package com.example.l3d_cube.bluetooth.SMG;

import android.bluetooth.BluetoothDevice;
import no.nordicsemi.android.ble.data.Data;

import androidx.annotation.NonNull;

public interface NotificationCallback {
    void gesture(@NonNull final BluetoothDevice device, Data data);
}
