package com.example.l3d_cube;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.example.l3d_cube.bluetooth.SMG.BluetoothSMGViewModel;
import com.example.l3d_cube.bluetooth.BluetoothUtils;
import com.example.l3d_cube.bluetooth.MCU.BluetoothMCUViewModel;
import com.example.l3d_cube.ui.FragmentDataTransfer;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.example.l3d_cube.databinding.ActivityMainBinding;

import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements FragmentDataTransfer {

    private ActivityMainBinding binding;
    private BluetoothMCUViewModel bluetoothMCUViewModel;
    private BluetoothSMGViewModel bluetoothSMGViewModel;

    // Animation Variables
    private LottieAnimationView animationView;

    // Toolbar
    private AppBarLayout appBarLayout;
    private Menu menu;

    // Bluetooth Variables
    private final String BTAG = "Bluetooth: ";
    private ActivityResultLauncher<Intent> btActivityResultLauncher;
    private ActivityResultLauncher<String> btRequestPermission;

    // Settings
    private SharedPreferences sharedPreferences;
    private boolean shouldDisableAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        animationView = binding.animationView;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        shouldDisableAnimation = sharedPreferences.getBoolean("disableAnimation", false);
        startAsyncAnimation();

        appBarLayout = binding.appbarLayout;
        setSupportActionBar(binding.toolbar);

        BottomNavigationView navView = binding.bottomNavMenu;
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_math, R.id.navigation_gesture, R.id.navigation_upload, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        bluetoothMCUViewModel = new ViewModelProvider(this).get(BluetoothMCUViewModel.class);

        bluetoothMCUViewModel.connectionStatus().observe(this, connectionStatus -> {
            String bluetoothState = BluetoothUtils.getBluetoothState(connectionStatus);
            BluetoothUtils.bluetoothConnectToast(this, connectionStatus);
            menu.findItem(R.id.bluetoothStatus).setTitle(bluetoothState);
        });

        bluetoothSMGViewModel = new ViewModelProvider(this).get(BluetoothSMGViewModel.class);

        registerBTActivity();
        registerBTPermissionRequest();

        autoConnect();
    }

    public void startAsyncAnimation() {
        int animationDelay = shouldDisableAnimation ? 0 : 3300;
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            appBarLayout.setVisibility(View.VISIBLE);
            binding.bottomNavMenu.setVisibility(View.VISIBLE);

            animationView.cancelAnimation();
            animationView.setVisibility(View.GONE);
        }, animationDelay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        String bluetoothState = BluetoothUtils.getBluetoothState(bluetoothMCUViewModel.isConnected());
        menu.findItem(R.id.bluetoothStatus).setTitle(bluetoothState);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.bluetoothSettings) {
            Log.i(BTAG, "button pressed");
            connectToBluetooth();
        }
        return true;
    }

    public void registerBTActivity(){
        btActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    connectToBluetooth();
                }
            });
    }

    public void registerBTPermissionRequest(){
        btRequestPermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
                result -> {
                    if (result) {
                        connectToBluetooth();
                    }
                });
    }

    @SuppressLint("MissingPermission")
    public void connectToBluetooth() {
        if(bluetoothMCUViewModel.isConnected()){
            bluetoothMCUViewModel.disconnect();
            bluetoothSMGViewModel.disconnect();
            return;
        }

        if (BluetoothUtils.getDefaultBluetoothAdapter() == null) {
            Log.e(BTAG, "bluetooth adapter not available");
            return;
        }

        if (!BluetoothUtils.getDefaultBluetoothAdapter().isEnabled()) {
            Log.e(BTAG, "bluetooth adapter not enabled");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            btActivityResultLauncher.launch(enableBtIntent);
            return;
        }

        if(!BluetoothUtils.isBluetoothPermissionGranted(this)){
            Log.e(BTAG, "bluetooth permissions not satisfied");
            if(BluetoothUtils.isSorAbove()) {
                btRequestPermission.launch(Manifest.permission.BLUETOOTH_CONNECT);
            }
            else{
                btRequestPermission.launch(Manifest.permission.BLUETOOTH);
            }
            return;
        }

        boolean shouldUseHardCodedConnection = sharedPreferences.getBoolean("hardCodedConnection", false);
        if(shouldUseHardCodedConnection){
            String hardCodedMCUAddress = sharedPreferences.getString("hardCodedDeviceMCU", "");
            boolean validMCUBluetoothAddress = BluetoothAdapter.checkBluetoothAddress(hardCodedMCUAddress);
            if(validMCUBluetoothAddress){
                BluetoothDevice hardCodedDeviceMCU = BluetoothUtils.getDefaultBluetoothAdapter().getRemoteDevice(hardCodedMCUAddress);
                bluetoothMCUViewModel.connect(hardCodedDeviceMCU);
            } else {
                BluetoothUtils.invalidBluetoothAddressToast(this);
            }

            String hardCodedSMGAddress = sharedPreferences.getString("hardCodedDeviceSMG", "");
            boolean validSMGBluetoothAddress = BluetoothAdapter.checkBluetoothAddress(hardCodedSMGAddress);
            if(validSMGBluetoothAddress){
                BluetoothDevice hardCodedDeviceSMG = BluetoothUtils.getDefaultBluetoothAdapter().getRemoteDevice(hardCodedSMGAddress);
                bluetoothSMGViewModel.connect(hardCodedDeviceSMG);
            } else {
                BluetoothUtils.invalidBluetoothAddressToast(this);
            }
        } else {
            showSelectionDialog(BluetoothUtils.getDefaultBluetoothAdapter().getBondedDevices());
        }
    }

    private void showSelectionDialog(Set<BluetoothDevice> pairedDevices) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        List<BluetoothDevice> devices = BluetoothUtils.getBondedDevices();
        List<String> deviceNameAddress = BluetoothUtils.getBluetoothNamesAndAddresses(devices);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.select_dialog_singlechoice,
                deviceNameAddress);

        alertDialog.setSingleChoiceItems(adapter, -1,
                (dialog, which) -> {
                    dialog.dismiss();
                    int position = ((AlertDialog) dialog)
                            .getListView()
                            .getCheckedItemPosition();
                    BluetoothDevice selectedDevice = devices.get(position);
                    bluetoothMCUViewModel.connect(selectedDevice);
                });
        alertDialog.setTitle("Select a Bluetooth Module");
        alertDialog.show();
    }

    private void autoConnect() {
        boolean shouldAutoConnect = sharedPreferences.getBoolean("autoConnect", false);
        if(shouldAutoConnect && BluetoothUtils.isBluetoothPermissionGranted(this)){
            boolean hardCodedConnection = sharedPreferences.getBoolean("hardCodedConnection", false);
            if(hardCodedConnection) {
                connectToBluetooth();
                return;
            }

            String autoConnectDevice = sharedPreferences.getString("preferredDevice", null);
            if(autoConnectDevice != null) {
                BluetoothDevice selectedDevice = BluetoothUtils.findBluetoothDevice(autoConnectDevice);
                if(selectedDevice != null) {
                    bluetoothMCUViewModel.connect(selectedDevice);
                    return;
                }
            }
            BluetoothUtils.autoConnectFailedToast(this);
        }
    }

    public BluetoothMCUViewModel getBluetoothViewModel(){
        return bluetoothMCUViewModel;
    }

    @Override
    public void fragmentToBluetooth(String data) {
        if(bluetoothMCUViewModel.isConnected()){
            bluetoothMCUViewModel.write(data.getBytes());
        } else {
            BluetoothUtils.noBluetoothDeviceConnectedToast(this);
        }
    }

    @Override
    public void fragmentToBluetooth(byte[] data) {
        if(bluetoothMCUViewModel.isConnected()){
            bluetoothMCUViewModel.write(data);
        } else {
            BluetoothUtils.noBluetoothDeviceConnectedToast(this);
        }
    }
}