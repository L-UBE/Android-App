package com.example.l3d_cube.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.example.l3d_cube.R;
import com.example.l3d_cube.SystemUtils;
import com.example.l3d_cube.bluetooth.BluetoothUtils;

import java.util.List;

@SuppressLint("MissingPermission")
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey);

        Context context = getContext();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        Preference restart = findPreference("restart");
        restart.setOnPreferenceClickListener((preference) -> {
            SystemUtils.restartApp(context);
            return true;
        });

        boolean isBluetoothPermissionGranted = BluetoothUtils.isBluetoothPermissionGranted(context);
        if(isBluetoothPermissionGranted) {
            SwitchPreference autoConnect = findPreference("autoConnect");
            ListPreference preferredDevice = findPreference("preferredDevice");

            autoConnect.setVisible(true);

            List<String> bondedDevices = BluetoothUtils.getBluetoothNames(BluetoothUtils.getBondedDevices());
            CharSequence[] devices = bondedDevices.toArray(new CharSequence[bondedDevices.size()]);
            preferredDevice.setEntries(devices);
            preferredDevice.setEntryValues(devices);

            preferredDevice.setVisible(sharedPreferences.getBoolean("autoConnect", false));

            autoConnect.setOnPreferenceChangeListener((preference, value) -> {
                preferredDevice.setVisible((boolean) value);
                return true;
            });
        }
    }
}


