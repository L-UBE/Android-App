package com.example.l3d_cube.bluetooth.SMG;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.l3d_cube.bluetooth.BluetoothUtils;
import com.example.l3d_cube.ui.FragmentDataTransfer;

import no.nordicsemi.android.ble.callback.profile.ProfileDataCallback;
import no.nordicsemi.android.ble.data.Data;
import no.nordicsemi.android.ble.livedata.ObservableBleManager;

public class BluetoothSMGManager extends ObservableBleManager {

    private BluetoothGattCharacteristic writeCharacteristic;
    private BluetoothGattCharacteristic notifyCharacteristic;

    private boolean isSupported;

    public final MutableLiveData<Boolean> isDeviceConnected = new MutableLiveData<>();

    FragmentDataTransfer fragmentDataTransfer;
    ProfileDataCallback notifcationCallback;

    public void setIsDeviceConnected(boolean isDeviceConnected) {
        this.isDeviceConnected.setValue(isDeviceConnected);
    }

    public BluetoothSMGManager(@NonNull final Context context) { super(context); }

    @NonNull
    @Override
    protected BleManagerGattCallback getGattCallback() {
        return new BluetoothDeviceManagerGattCallback();
    }

    private class BluetoothDeviceManagerGattCallback extends BleManagerGattCallback {
        @Override
        protected void initialize() {
            requestMtu(517).enqueue();
            setNotificationCallback(notifyCharacteristic).with(notifcationCallback);
            enableNotifications(notifyCharacteristic).enqueue();
        }

        @Override
        protected boolean isRequiredServiceSupported(@NonNull BluetoothGatt gatt) {
            final BluetoothGattService service = gatt.getService(BluetoothUtils.getUuidUart());
            if (service != null) {
                writeCharacteristic = service.getCharacteristic(BluetoothUtils.getUuidWrite());
                notifyCharacteristic = service.getCharacteristic(BluetoothUtils.getUuidNotify());
            }
            isSupported = writeCharacteristic != null && notifyCharacteristic != null;
            return isSupported;
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
