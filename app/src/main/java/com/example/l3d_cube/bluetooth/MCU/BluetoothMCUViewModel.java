package com.example.l3d_cube.bluetooth.MCU;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.l3d_cube.bluetooth.BluetoothManager;
import com.example.l3d_cube.bluetooth.BluetoothViewModel;
import com.example.l3d_cube.bluetooth.SMG.BluetoothSMGManager;

public class BluetoothMCUViewModel extends BluetoothViewModel {

    public BluetoothMCUViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected BluetoothManager initializeBluetoothManager(@NonNull Application application) {
        return new BluetoothMCUManager(application);
    }

    @Override
    public String getDeviceName() {
        return "MCU";
    }

    public void write(byte[] data){
        ((BluetoothMCUManager)bluetoothManager).write(data);
    }

    public MutableLiveData<byte[]> incomingData() {
        return ((BluetoothMCUManager)bluetoothManager).incomingData;
    }
}
