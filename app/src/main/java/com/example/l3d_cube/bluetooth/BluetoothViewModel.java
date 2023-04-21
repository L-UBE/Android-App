package com.example.l3d_cube.bluetooth;

import android.app.Application;
import android.bluetooth.BluetoothDevice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.l3d_cube.bluetooth.Utility.BluetoothSystemUtils;

import no.nordicsemi.android.ble.ConnectRequest;
import no.nordicsemi.android.ble.PhyRequest;

public abstract class BluetoothViewModel extends AndroidViewModel {
    protected final BluetoothManager bluetoothManager;

    @Nullable
    private ConnectRequest connectRequest;

    private BluetoothDevice device;

    protected BluetoothViewModel(@NonNull Application application) {
        super(application);
        bluetoothManager = initializeBluetoothManager(application);
    }

    public void connect(@NonNull final BluetoothDevice target) {
        device = target;
        connectRequest = bluetoothManager.connect(device)
                .useAutoConnect(false)
                .usePreferredPhy(PhyRequest.PHY_LE_2M_MASK)
                .then(d -> connectRequest = null)
                .done(d -> {
                    bluetoothManager.setIsDeviceConnected(true);
                })
                .fail((d, s) -> {
                    BluetoothSystemUtils.bluetoothConnectToastFail(getApplication());
                });
        connectRequest.enqueue();
    }

    public void disconnect() {
        device = null;
        if (connectRequest != null) {
            connectRequest.cancelPendingConnection();
        } else if (bluetoothManager.isConnected()) {
            bluetoothManager.disconnect().enqueue();
        }
        bluetoothManager.setIsDeviceConnected(false);
    }

    public MutableLiveData<Boolean> connectionStatus() {
        return bluetoothManager.isDeviceConnected;
    }

    public Boolean isConnected() {
        return Boolean.TRUE.equals(bluetoothManager.isDeviceConnected.getValue());
    }

    protected abstract BluetoothManager initializeBluetoothManager(@NonNull Application application);

    public abstract String getDeviceName();
}
