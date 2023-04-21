package com.example.l3d_cube.bluetooth.SMG;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.l3d_cube.bluetooth.BluetoothManager;
import com.example.l3d_cube.bluetooth.BluetoothViewModel;

public class BluetoothSMGViewModel extends BluetoothViewModel {

    public BluetoothSMGViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected BluetoothManager initializeBluetoothManager(@NonNull Application application) {
        return new BluetoothSMGManager(application);
    }

    @Override
    public String getDeviceName() {
        return "SMG";
    }

    public MutableLiveData<byte[]> incomingData() {
        return ((BluetoothSMGManager)bluetoothManager).incomingData;
    }
}
