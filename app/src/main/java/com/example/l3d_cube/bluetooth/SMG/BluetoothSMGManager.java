package com.example.l3d_cube.bluetooth.SMG;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.l3d_cube.bluetooth.BluetoothManager;
import com.example.l3d_cube.bluetooth.Utility.BluetoothSystemUtils;

import no.nordicsemi.android.ble.callback.profile.ProfileDataCallback;

public class BluetoothSMGManager extends BluetoothManager {

    private BluetoothGattCharacteristic notifyCharacteristic;

    public final MutableLiveData<byte[]> incomingData = new MutableLiveData<>();

    private final ProfileDataCallback incomingBluetoothData = (device, data) -> this.incomingData.setValue(data.getValue());

    public void setIsDeviceConnected(boolean isDeviceConnected) {
        this.isDeviceConnected.setValue(isDeviceConnected);
    }

    public BluetoothSMGManager(@NonNull final Context context) { super(context); }

    @Override
    protected void initialization() {
        setNotificationCallback(notifyCharacteristic).with(incomingBluetoothData);
        enableNotifications(notifyCharacteristic).enqueue();
    }

    @Override
    protected void invalidateServices() {
        notifyCharacteristic = null;
    }

    @Override
    protected boolean isSupported(@NonNull BluetoothGattService service) {
        notifyCharacteristic = service.getCharacteristic(BluetoothSystemUtils.getUuidNotify());
        return notifyCharacteristic != null;
    }
}
