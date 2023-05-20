package com.example.l3d_cube.bluetooth.MCU;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.l3d_cube.bluetooth.BluetoothManager;
import com.example.l3d_cube.bluetooth.Utility.BluetoothSystemUtils;

import no.nordicsemi.android.ble.callback.profile.ProfileDataCallback;

public class BluetoothMCUManager extends BluetoothManager {
    private BluetoothGattCharacteristic writeCharacteristic;
    private BluetoothGattCharacteristic notifyCharacteristic;

    public final MutableLiveData<byte[]> incomingData = new MutableLiveData<>();

    private final ProfileDataCallback incomingBluetoothData = (device, data) -> this.incomingData.setValue(data.getValue());

    public BluetoothMCUManager(@NonNull final Context context) {
        super(context);
    }

    @Override
    protected void initialization() {
        setNotificationCallback(notifyCharacteristic).with(incomingBluetoothData);
        enableNotifications(notifyCharacteristic).enqueue();
    }

    @Override
    protected void invalidateServices() {
        writeCharacteristic = null;
        notifyCharacteristic = null;
    }

    @Override
    protected boolean isSupported(@NonNull BluetoothGattService service) {
        writeCharacteristic = service.getCharacteristic(BluetoothSystemUtils.getUuidWrite());
        notifyCharacteristic = service.getCharacteristic(BluetoothSystemUtils.getUuidNotify());
        return (writeCharacteristic != null && notifyCharacteristic != null);
    }

    public void write(byte[] data){
        if(writeCharacteristic == null){
            return;
        }
        writeCharacteristic(
                writeCharacteristic,
                data,
                BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
        ).split().enqueue();
    }
}
