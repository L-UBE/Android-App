package com.example.l3d_cube.bluetooth.MCU;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.l3d_cube.bluetooth.BluetoothManager;
import com.example.l3d_cube.bluetooth.Utility.BluetoothSystemUtils;

public class BluetoothMCUManager extends BluetoothManager {
    private BluetoothGattCharacteristic writeCharacteristic;

    public BluetoothMCUManager(@NonNull final Context context) {
        super(context);
    }

    @Override
    protected void initialization() {
    }

    @Override
    protected void invalidateServices() {
        writeCharacteristic = null;
    }

    @Override
    protected boolean isSupported(@NonNull BluetoothGattService service) {
        writeCharacteristic = service.getCharacteristic(BluetoothSystemUtils.getUuidWrite());
        return writeCharacteristic != null;
    }

    public void write(byte[] data){
        if(writeCharacteristic == null){
            return;
        }
        writeCharacteristic(
                writeCharacteristic,
                data,
                BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE
        ).split().enqueue();
    }
}
