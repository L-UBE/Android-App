package com.example.l3d_cube.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattService;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.l3d_cube.bluetooth.Utility.BluetoothSystemUtils;

import no.nordicsemi.android.ble.livedata.ObservableBleManager;

public abstract class BluetoothManager extends ObservableBleManager {

    public final MutableLiveData<Boolean> isDeviceConnected = new MutableLiveData<>();

    public void setIsDeviceConnected(boolean isDeviceConnected) {
        this.isDeviceConnected.setValue(isDeviceConnected);
    }

    protected BluetoothManager(@NonNull final Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected BleManagerGattCallback getGattCallback() {
        return new BluetoothManager.BluetoothDeviceManagerGattCallback();
    }

    private class BluetoothDeviceManagerGattCallback extends BleManagerGattCallback {
        @Override
        protected void initialize() {
            requestMtu(250).enqueue();
            initialization();
        }

        @Override
        protected boolean isRequiredServiceSupported(@NonNull BluetoothGatt gatt) {
            final BluetoothGattService service = gatt.getService(BluetoothSystemUtils.getUuidUart());
            return isSupported(service);
        }

        @Override
        protected void onServicesInvalidated() {
            invalidateServices();
            if(Boolean.TRUE.equals(isDeviceConnected.getValue())){
                isDeviceConnected.setValue(false);
            }
        }
    }
    protected abstract void initialization();

    protected abstract void invalidateServices();

    protected abstract boolean isSupported(@NonNull BluetoothGattService service);
}
