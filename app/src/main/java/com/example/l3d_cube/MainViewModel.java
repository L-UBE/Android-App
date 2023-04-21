package com.example.l3d_cube;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.l3d_cube.bluetooth.Utility.BluetoothDataUtils;

public class MainViewModel extends AndroidViewModel {
    public final MutableLiveData<byte[]> outgoingData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void handleIncomingBluetoothData(byte[] incomingData) {
        byte[] parsedData = BluetoothDataUtils.parseIncomingBluetoothData(incomingData);
        outgoingData.setValue(parsedData);
    }
}
