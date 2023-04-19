package com.example.l3d_cube.bluetooth.MCU;

import android.app.Application;
import android.bluetooth.BluetoothDevice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.l3d_cube.bluetooth.BluetoothUtils;
import com.example.l3d_cube.bluetooth.MCU.BluetoothMCUManager;

import no.nordicsemi.android.ble.ConnectRequest;
import no.nordicsemi.android.ble.PhyRequest;

public class BluetoothMCUViewModel extends AndroidViewModel {
    private final BluetoothMCUManager bluetoothMCUManager;

    @Nullable
    private ConnectRequest connectRequest;

    private BluetoothDevice device;


    public BluetoothMCUViewModel(@NonNull Application application) {
        super(application);
        bluetoothMCUManager = new BluetoothMCUManager(application);
    }

    public void connect(@NonNull final BluetoothDevice target) {
        device = target;
        connectRequest = bluetoothMCUManager.connect(device)
                .useAutoConnect(false)
                .usePreferredPhy(PhyRequest.PHY_LE_2M_MASK)
                .then(d -> connectRequest = null)
                .done(d -> {
                    bluetoothMCUManager.setIsDeviceConnected(true);
                })
                .fail((d, s) -> {
                    BluetoothUtils.bluetoothConnectToastFail(getApplication());
                });
        connectRequest.enqueue();
    }

    public void disconnect() {
        device = null;
        if (connectRequest != null) {
            connectRequest.cancelPendingConnection();
        } else if (bluetoothMCUManager.isConnected()) {
            bluetoothMCUManager.disconnect().enqueue();
        }
        bluetoothMCUManager.setIsDeviceConnected(false);
    }

    public void write(byte[] data){
        bluetoothMCUManager.write(data);
    }

    public MutableLiveData<Boolean> connectionStatus() {
        return bluetoothMCUManager.isDeviceConnected;
    }

    public Boolean isConnected() {
        return Boolean.TRUE.equals(bluetoothMCUManager.isDeviceConnected.getValue());
    }
}
