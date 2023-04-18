package com.example.l3d_cube.bluetooth.Client;

import android.app.Application;
import android.bluetooth.BluetoothDevice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.l3d_cube.bluetooth.BluetoothUtils;
import com.example.l3d_cube.bluetooth.Client.BluetoothDeviceManager;

import no.nordicsemi.android.ble.ConnectRequest;
import no.nordicsemi.android.ble.PhyRequest;

public class BluetoothViewModel extends AndroidViewModel {
    private final BluetoothDeviceManager bluetoothDeviceManager;
    @Nullable
    private ConnectRequest connectRequest;
    private BluetoothDevice device;


    public BluetoothViewModel(@NonNull Application application) {
        super(application);
        bluetoothDeviceManager = new BluetoothDeviceManager(getApplication());
    }

    public void connect(@NonNull final BluetoothDevice target) {
        device = target;
        connectRequest = bluetoothDeviceManager.connect(device)
                .useAutoConnect(false)
                .usePreferredPhy(PhyRequest.PHY_LE_2M_MASK)
                .then(d -> connectRequest = null)
                .done(d -> {
                    bluetoothDeviceManager.setIsDeviceConnected(true);
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
        } else if (bluetoothDeviceManager.isConnected()) {
            bluetoothDeviceManager.disconnect().enqueue();
        }
        bluetoothDeviceManager.setIsDeviceConnected(false);
    }

    public void write(byte[] data){
        bluetoothDeviceManager.write(data);
    }

    public LiveData<Boolean> isConnected() {
        return bluetoothDeviceManager.isDeviceConnected;
    }

}
