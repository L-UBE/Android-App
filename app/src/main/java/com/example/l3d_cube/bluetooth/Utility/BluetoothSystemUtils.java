package com.example.l3d_cube.bluetooth.Utility;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.l3d_cube.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class BluetoothSystemUtils {

    public static ActivityResultLauncher<Intent> btActivityResultLauncher;
    public static ActivityResultLauncher<String> btRequestPermission;

    private final static UUID UUID_UART = UUID.fromString("0199fe27-ba28-43d9-a553-f501be4940fd");
    private final static UUID UUID_WRITE = UUID.fromString("6ab4f1d6-ee33-4f6f-a5d2-6b0d48a58b71");
    private final static UUID UUID_NOTIFY = UUID.fromString("36e87c98-abc8-4d87-9f62-057968bc53e7");

    private final static BluetoothAdapter defaultBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public static boolean isBluetoothConnectPermissionGranted(@NonNull final Context context) {
        if (isSorAbove())
            return ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT)
                    == PackageManager.PERMISSION_GRANTED;
        else{
            return ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH)
                    == PackageManager.PERMISSION_GRANTED;
        }
    }

    public static boolean isBluetoothScanPermissionGranted(Context context) {
        if (isSorAbove()){
            return ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN)
                    == PackageManager.PERMISSION_GRANTED;
        }
        else{
            return ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH)
                    == PackageManager.PERMISSION_GRANTED;
        }
    }

    public static boolean isBluetoothLocationPermissionGranted(Context context) {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED;

    }

    @SuppressLint("MissingPermission")
    public static List<BluetoothDevice> getBondedDevices() {
        return new ArrayList<>(defaultBluetoothAdapter.getBondedDevices());
    }

    @SuppressLint("MissingPermission")
    public static List<String> getBluetoothNamesAndAddresses(List<BluetoothDevice> bluetoothDevices){
        List<String> deviceNamesAddresses = new ArrayList<>();
        for (BluetoothDevice device : bluetoothDevices) {
            String deviceName = device.getName();
            String deviceAddress = device.getAddress();
            deviceNamesAddresses.add(deviceName + "\n" + deviceAddress);
        }
        return deviceNamesAddresses;
    }

    @SuppressLint("MissingPermission")
    public static List<String> getBluetoothNames(List<BluetoothDevice> bluetoothDevices){
        List<String> deviceNames = new ArrayList<>();
        for (BluetoothDevice device : bluetoothDevices) {
            deviceNames.add(device.getName());
        }
        return deviceNames;
    }

    @SuppressLint("MissingPermission")
    public static BluetoothDevice findBluetoothDevice(String deviceName) {
        List<BluetoothDevice> bluetoothDevices = getBondedDevices();
        for (BluetoothDevice device : bluetoothDevices) {
            if(device.getName().equals(deviceName)){
                return device;
            }
        }
        return null;
    }

    public static boolean checkBluetoothGeneralRequirements(Context context) {
        if (getDefaultBluetoothAdapter() == null) {
            return false;
        }

        if (!getDefaultBluetoothAdapter().isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            btActivityResultLauncher.launch(enableBtIntent);
            return false;
        }
        return true;
    }

    public static boolean checkBluetoothConnectionRequirements(Context context) {
        if(!isBluetoothConnectPermissionGranted(context)){
            if(isSorAbove()) {
                btRequestPermission.launch(Manifest.permission.BLUETOOTH_CONNECT);
            }
            else{
                btRequestPermission.launch(Manifest.permission.BLUETOOTH);
            }
            return false;
        }
        return true;
    }

    public static boolean checkBluetoothScanRequirements(Context context) {
        if(!isBluetoothScanPermissionGranted(context)){
            if(isSorAbove()) {
                btRequestPermission.launch(Manifest.permission.BLUETOOTH_SCAN);
            }
            else{
                btRequestPermission.launch(Manifest.permission.BLUETOOTH);
            }
            return false;
        }

        if(!isBluetoothLocationPermissionGranted(context)){
            btRequestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            return false;
        }
        return true;
    }

    public static boolean isSorAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S;
    }

    public static UUID getUuidUart() {
        return UUID_UART;
    }

    public static UUID getUuidNotify() {
        return UUID_NOTIFY;
    }

    public static UUID getUuidWrite() {
        return UUID_WRITE;
    }

    public static BluetoothAdapter getDefaultBluetoothAdapter() {
        return defaultBluetoothAdapter;
    }

    public static String stringToAddress(String address) {
        return address.substring(0, 2) + ":" + address.substring(2, 4) + ":" + address.substring(4, 6) + ":" + address.substring(6, 8) + ":" + address.substring(8, 10) + ":" + address.substring(10, 12);
    }

    public static String getBluetoothState(boolean isConnected) {
        return isConnected ? "Connected" : "Disconnected";
    }

    public static void bluetoothConnectToast(Context context, boolean isConnected) {
        String connectionMsg = (isConnected ? "Connected to" : "Disconnected from") + " Bluetooth Module";
        bluetoothInfoToast(context, connectionMsg);
    }

    public static void bluetoothConnectToastFail(Context context){
        String failMsg = "Failed to connect to Bluetooth Device";
        bluetoothErrorToast(context, failMsg);
    }

    public static void noBluetoothDeviceConnectedToast(Context context){
        String failMsg = "No Bluetooth Device connected";
        bluetoothErrorToast(context, failMsg);
    }

    public static void invalidBluetoothAddressToast(Context context){
        String failMsg = "Hard-Coded Bluetooth Device is invalid";
        bluetoothErrorToast(context, failMsg);
    }

    public static void autoConnectFailedToast(Context context){
        String failMsg = "Auto-Connect failed";
        bluetoothErrorToast(context, failMsg);
    }

    private static void bluetoothInfoToast(Context context, String message) {
        Toasty.custom(context,
                        message,
                        R.drawable.bluetooth_settings,
                        es.dmoral.toasty.R.color.infoColor ,
                        Toast.LENGTH_LONG,
                        true,
                        true)
                .show();
    }

    private static void bluetoothErrorToast(Context context, String message) {
        Toasty.error(context, message).show();
    }

}
