package com.example.l3d_cube.bluetooth.SMG;

import android.app.Application;
import android.bluetooth.BluetoothDevice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.l3d_cube.bluetooth.BluetoothUtils;
import com.example.l3d_cube.bluetooth.SMG.BluetoothSMGManager;

import no.nordicsemi.android.ble.ConnectRequest;
import no.nordicsemi.android.ble.PhyRequest;
import no.nordicsemi.android.ble.callback.profile.ProfileDataCallback;
import no.nordicsemi.android.ble.data.Data;

public class BluetoothSMGViewModel extends AndroidViewModel {
    private final BluetoothSMGManager bluetoothSMGManager;

    @Nullable
    private ConnectRequest connectRequest;

    private BluetoothDevice device;


    public BluetoothSMGViewModel(@NonNull Application application) {
        super(application);
        bluetoothSMGManager = new BluetoothSMGManager(application);

    }

    public void connect(@NonNull final BluetoothDevice target) {
        device = target;
        connectRequest = bluetoothSMGManager.connect(device)
                .useAutoConnect(false)
                .usePreferredPhy(PhyRequest.PHY_LE_2M_MASK)
                .then(d -> connectRequest = null)
                .done(d -> {
                    bluetoothSMGManager.setIsDeviceConnected(true);
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
        } else if (bluetoothSMGManager.isConnected()) {
            bluetoothSMGManager.disconnect().enqueue();
        }
        bluetoothSMGManager.setIsDeviceConnected(false);
    }

    public void write(byte[] data){
        bluetoothSMGManager.write(data);
    }

    public Boolean isConnected() {
        return Boolean.TRUE.equals(bluetoothSMGManager.isDeviceConnected.getValue());
    }

    public MutableLiveData<Boolean> connectionStatus() {
        return bluetoothSMGManager.isDeviceConnected;
    }

}
