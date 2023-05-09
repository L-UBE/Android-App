package com.example.l3d_cube.bluetooth.Utility;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AlertDialog;

import com.example.l3d_cube.bluetooth.BluetoothViewModel;

import java.util.ArrayList;
import java.util.List;

public class BluetoothConnectionUtils {
    public static void connect(Context context, SharedPreferences sharedPreferences, BluetoothViewModel bluetoothViewModel) {
        if (bluetoothViewModel.isConnected()) {
            bluetoothViewModel.disconnect();
            return;
        }

        if (!BluetoothSystemUtils.checkBluetoothGeneralRequirements(context)) {
            return;
        }

        if (!BluetoothSystemUtils.checkBluetoothConnectionRequirements(context)) {
            return;
        }

        boolean shouldUseHardCodedConnection = sharedPreferences.getBoolean("hardCodedConnection", false);
        if (shouldUseHardCodedConnection) {
            handleHardCodedConnection(context, sharedPreferences, bluetoothViewModel);
        } else {
            if (!BluetoothSystemUtils.checkBluetoothScanRequirements(context)) {
                return;
            }
            scan(context, bluetoothViewModel);
        }
    }

    private static void handleHardCodedConnection(Context context, SharedPreferences sharedPreferences, BluetoothViewModel bluetoothViewModel) {
        String hardCodedAddress = BluetoothSystemUtils.stringToAddress(sharedPreferences.getString("hardCodedDevice" + bluetoothViewModel.getDeviceName(), ""));
        boolean validBluetoothAddress = BluetoothAdapter.checkBluetoothAddress(hardCodedAddress);
        if (validBluetoothAddress) {
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

    @SuppressLint("MissingPermission")
    private static void scan(Context context, BluetoothViewModel bluetoothViewModel) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        List<BluetoothDevice> devices = new ArrayList<>();

        List<String> deviceNameAddress = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context,
                android.R.layout.select_dialog_singlechoice,
                deviceNameAddress);

        BluetoothSystemUtils.getDefaultBluetoothAdapter().startDiscovery();
        BroadcastReceiver receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    devices.add(device);
                    adapter.add(device.getName() + "\n" + device.getAddress());
                }
            }
        };

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(receiver, filter);

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
