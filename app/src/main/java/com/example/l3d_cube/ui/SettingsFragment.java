package com.example.l3d_cube.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.example.l3d_cube.R;
import com.example.l3d_cube.Utility.SystemUtils;
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
            SwitchPreference hardCodedConnection = findPreference("hardCodedConnection");
            SwitchPreference autoConnect = findPreference("autoConnect");
            EditTextPreference hardCodedDeviceMCU = findPreference("hardCodedDeviceMCU");
            EditTextPreference hardCodedDeviceSMG = findPreference("hardCodedDeviceSMG");
            ListPreference preferredDeviceMCU = findPreference("preferredDeviceMCU");
            ListPreference preferredDeviceSMG = findPreference("preferredDeviceSMG");

            hardCodedConnection.setVisible(true);
            autoConnect.setVisible(true);

            hardCodedDeviceMCU.setVisible(sharedPreferences.getBoolean("hardCodedConnection", false));
            hardCodedDeviceSMG.setVisible(sharedPreferences.getBoolean("hardCodedConnection", false));

            hardCodedConnection.setOnPreferenceChangeListener((preference, value) -> {
                hardCodedDeviceMCU.setVisible((boolean) value);
                hardCodedDeviceSMG.setVisible((boolean) value);
                return true;
            });

            List<String> bondedDevices = BluetoothUtils.getBluetoothNames(BluetoothUtils.getBondedDevices());
            CharSequence[] devices = bondedDevices.toArray(new CharSequence[bondedDevices.size()]);

            preferredDeviceMCU.setEntries(devices);
            preferredDeviceMCU.setEntryValues(devices);

            preferredDeviceMCU.setVisible(sharedPreferences.getBoolean("autoConnect", false));

            preferredDeviceSMG.setEntries(devices);
            preferredDeviceSMG.setEntryValues(devices);

            preferredDeviceSMG.setVisible(sharedPreferences.getBoolean("autoConnect", false));

            autoConnect.setOnPreferenceChangeListener((preference, value) -> {
                preferredDeviceMCU.setVisible((boolean) value);
                preferredDeviceSMG.setVisible((boolean) value);
                return true;
            });
        }
    }
}


