package com.example.l3d_cube.bluetooth.Client;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.l3d_cube.bluetooth.BluetoothUtils;

import no.nordicsemi.android.ble.livedata.ObservableBleManager;

public class BluetoothDeviceManager extends ObservableBleManager {

    private BluetoothGattCharacteristic writeCharacteristic, notifyCharacteristic;
    private boolean supported;

    public final MutableLiveData<Boolean> isDeviceConnected = new MutableLiveData<>();

    public void setIsDeviceConnected(boolean isDeviceConnected) {
        this.isDeviceConnected.setValue(isDeviceConnected);
    }


    public BluetoothDeviceManager(@NonNull final Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected BleManagerGattCallback getGattCallback() {
        return new BluetoothDeviceManagerGattCallback();
    }

    private class BluetoothDeviceManagerGattCallback extends BleManagerGattCallback {
        @Override
        protected void initialize() {
            // Initialize your device.
            // This means e.g. enabling notifications, setting notification callbacks,
            // sometimes writing something to some Control Point.
            // Kotlin projects should not use suspend methods here, which require a scope.
            requestMtu(517)
                    .enqueue();
        }

        @Override
        protected boolean isRequiredServiceSupported(@NonNull BluetoothGatt gatt) {
            final BluetoothGattService service = gatt.getService(BluetoothUtils.getUuidUart());
            if (service != null) {
                writeCharacteristic = service.getCharacteristic(BluetoothUtils.getUuidWrite());
                notifyCharacteristic = service.getCharacteristic(BluetoothUtils.getUuidNotify());
            }
            supported = writeCharacteristic != null && notifyCharacteristic != null;
            return supported;
        }

        @Override
        protected void onServicesInvalidated() {
            writeCharacteristic = null;
            notifyCharacteristic = null;
            if(Boolean.TRUE.equals(isDeviceConnected.getValue())){
                isDeviceConnected.setValue(false);
            }
        }
    }

    public void write(byte[] data){

        if(writeCharacteristic == null){
            return;
        }

        writeCharacteristic(
                writeCharacteristic,
                data,
                BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE
        ).enqueue();
    }
}
