package com.example.l3d_cube.ui;

import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.l3d_cube.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey);

        SwitchPreference autoConnect = findPreference("autoConnect");
        ListPreference preferredDevice = findPreference("preferredDevice");

        autoConnect.setOnPreferenceClickListener(preference -> {
            if(true){
                preferredDevice.setVisible(true);
            } else {
                preferredDevice.setVisible(false);
            }
            return true;
        });
    }

}
