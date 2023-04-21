package com.example.l3d_cube.bluetooth.Utility;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AlertDialog;

import com.example.l3d_cube.bluetooth.BluetoothViewModel;

import java.util.List;

public class BluetoothConnectionUtils {
    public static void connect(Context context, SharedPreferences sharedPreferences, BluetoothViewModel bluetoothViewModel) {
        if(bluetoothViewModel.isConnected()){
            bluetoothViewModel.disconnect();
            return;
        }

        if (!BluetoothSystemUtils.checkBluetoothConnectionRequirements(context)) {
            return;
        }

        boolean shouldUseHardCodedConnection = sharedPreferences.getBoolean("hardCodedConnection", false);
        if(shouldUseHardCodedConnection){
            handleHardCodedConnection(context, sharedPreferences, bluetoothViewModel);
        } else {
            handleConnectionSelection(context, bluetoothViewModel);
        }
    }

    private static void handleHardCodedConnection(Context context, SharedPreferences sharedPreferences, BluetoothViewModel bluetoothViewModel){
        String hardCodedAddress = sharedPreferences.getString("hardCodedDevice" + bluetoothViewModel.getDeviceName(), "");
        boolean validBluetoothAddress = BluetoothAdapter.checkBluetoothAddress(hardCodedAddress);
        if(validBluetoothAddress){
            BluetoothDevice hardCodedDevice = BluetoothSystemUtils.getDefaultBluetoothAdapter().getRemoteDevice(hardCodedAddress);
            bluetoothViewModel.connect(hardCodedDevice);
        } else {
            BluetoothSystemUtils.invalidBluetoothAddressToast(context);
        }
    }

    private static void handleConnectionSelection(Context context, BluetoothViewModel bluetoothViewModel) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        List<BluetoothDevice> devices = BluetoothSystemUtils.getBondedDevices();
        List<String> deviceNameAddress = BluetoothSystemUtils.getBluetoothNamesAndAddresses(devices);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context,
                android.R.layout.select_dialog_singlechoice,
                deviceNameAddress);

        alertDialog.setSingleChoiceItems(adapter, -1,
                (dialog, which) -> {
                    dialog.dismiss();
                    int position = ((AlertDialog) dialog)
                            .getListView()
                            .getCheckedItemPosition();
                    BluetoothDevice selectedDevice = devices.get(position);
                    bluetoothViewModel.connect(selectedDevice);
                });
        alertDialog.setTitle("Select a Bluetooth Module");
        alertDialog.show();
    }
}
