package com.example.l3d_cube.ui.notifications;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.l3d_cube.ArrayUtils;
import com.example.l3d_cube.databinding.FragmentNotificationsBinding;
import com.example.l3d_cube.ui.FragmentDataTransfer;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    FragmentDataTransfer fragmentDataTransfer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Context context = getContext();

        int[][][] map = new int[100][100][100];
        for (int x = 0; x < 100; x++) {
            for(int y = 0; y < 100; y++) {
                for(int z = 0; z < 100; z++) {
                    map[x][y][z] = 1;
                }
            }
        }

        int[] flatArray = ArrayUtils.flatten(map);

        sendToBluetooth(ArrayUtils.intArrayToByteArray(flatArray));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentDataTransfer = (FragmentDataTransfer) context;
    }

    public void sendToBluetooth(byte[] data) {
        fragmentDataTransfer.fragmentToBluetooth(data);
    }

    public void sendToBluetooth(String data) {
        fragmentDataTransfer.fragmentToBluetooth(data);
    }
}