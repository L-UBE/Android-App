package com.example.l3d_cube;

import android.bluetooth.BluetoothDevice;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.example.l3d_cube.bluetooth.SMG.BluetoothSMGViewModel;
import com.example.l3d_cube.bluetooth.Utility.BluetoothConnectionUtils;
import com.example.l3d_cube.bluetooth.Utility.BluetoothSystemUtils;
import com.example.l3d_cube.bluetooth.MCU.BluetoothMCUViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.example.l3d_cube.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;
    private BluetoothMCUViewModel bluetoothMCUViewModel;
    private BluetoothSMGViewModel bluetoothSMGViewModel;

    // Animation Variables
    private LottieAnimationView animationView;

    // Toolbar
    private AppBarLayout appBarLayout;
    private Menu menu;

    // Bluetooth Variables
    private final String BTAG = "Bluetooth: ";

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
                R.id.navigation_math, R.id.navigation_gesture, R.id.navigation_upload, R.id.navigation_display_settings,R.id.navigation_app_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.refresh.observe(this, outgoingData -> {
            bluetoothMCUViewModel.write(mainViewModel.outgoingModel);
        });

        bluetoothMCUViewModel = new ViewModelProvider(this).get(BluetoothMCUViewModel.class);
        bluetoothMCUViewModel.connectionStatus().observe(this, connectionStatus -> {
            String MCUBluetoothState = BluetoothSystemUtils.getBluetoothState(connectionStatus);
            BluetoothSystemUtils.bluetoothConnectToast(this, connectionStatus);
            menu.findItem(R.id.MCUConnectionStatus).setTitle(MCUBluetoothState);
        });

        bluetoothSMGViewModel = new ViewModelProvider(this).get(BluetoothSMGViewModel.class);
        bluetoothSMGViewModel.incomingData().observe(this, incomingData -> {
            mainViewModel.handleIncomingBluetoothData(incomingData);
        });
        bluetoothSMGViewModel.connectionStatus().observe(this, connectionStatus -> {
            String SMGBluetoothState = BluetoothSystemUtils.getBluetoothState(connectionStatus);
            BluetoothSystemUtils.bluetoothConnectToast(this, connectionStatus);
            menu.findItem(R.id.SMGConnectionStatus).setTitle(SMGBluetoothState);
        });

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

        String MCUBluetoothState = BluetoothSystemUtils.getBluetoothState(bluetoothMCUViewModel.isConnected());
        menu.findItem(R.id.MCUConnectionStatus).setTitle(MCUBluetoothState);

        String SMGBluetoothState = BluetoothSystemUtils.getBluetoothState(bluetoothSMGViewModel.isConnected());
        menu.findItem(R.id.SMGConnectionStatus).setTitle(SMGBluetoothState);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.MCUConnect) {
            Log.i(BTAG, "button pressed");
            BluetoothConnectionUtils.connect(this, sharedPreferences, bluetoothMCUViewModel);
        }
        else if (item.getItemId() == R.id.SMGConnect) {
            Log.i(BTAG, "button pressed");
            BluetoothConnectionUtils.connect(this, sharedPreferences, bluetoothSMGViewModel);
        }
        return true;
    }

    public void registerBTActivity(){
        BluetoothSystemUtils.btActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
//                if (result.getResultCode() == Activity.RESULT_OK) {
//                    BluetoothConnectionUtils.connect(this, sharedPreferences, bluetoothMCUViewModel);
//                }
            });
    }

    public void registerBTPermissionRequest(){
        BluetoothSystemUtils.btRequestPermission = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
                result -> {
//                    if (result) {
//                        BluetoothConnectionUtils.connect(this, sharedPreferences, bluetoothMCUViewModel);
//                    }
                });
    }

    private void autoConnect() {
        boolean shouldAutoConnect = sharedPreferences.getBoolean("autoConnect", false);
        if(shouldAutoConnect && BluetoothSystemUtils.isBluetoothConnectPermissionGranted(this)){
            boolean hardCodedConnection = sharedPreferences.getBoolean("hardCodedConnection", false);
            if(hardCodedConnection) {
                BluetoothConnectionUtils.connect(this, sharedPreferences, bluetoothMCUViewModel);
                BluetoothConnectionUtils.connect(this, sharedPreferences, bluetoothSMGViewModel);
                return;
            }

            // TODO: FIX
            String autoConnectDevice = sharedPreferences.getString("preferredDevice", null);
            if(autoConnectDevice != null) {
                BluetoothDevice selectedDevice = BluetoothSystemUtils.findBluetoothDevice(autoConnectDevice);
                if(selectedDevice != null) {
                    bluetoothMCUViewModel.connect(selectedDevice);
                    return;
                }
            }
            BluetoothSystemUtils.autoConnectFailedToast(this);
        }
    }
}