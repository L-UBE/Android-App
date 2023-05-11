package com.example.l3d_cube;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.example.l3d_cube.Model.LED.LedMapping;
import com.example.l3d_cube.bluetooth.Utility.BluetoothDataUtils;

public class MainViewModel extends AndroidViewModel {
    private Python py;
    private PyObject transformation;
    private int angle = 0;

    public final MutableLiveData<Boolean> refresh = new MutableLiveData<>();
    private byte[] model;
    public byte[] outgoingModel;

    private int delay = 300;

    public MainViewModel(@NonNull Application application) {
        super(application);

        py = Python.getInstance();
        transformation = py.getModule("rotation");

        Thread main = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    refresh.postValue(Boolean.FALSE.equals(refresh.getValue()));
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        throw new RuntimeException("Refresh Thread Failed!");
                    }
                }
            }
        });
        main.start();
    }

    public void handleIncomingBluetoothData(byte[] incomingData) {
        if(incomingData[0] == 0x08 && incomingData.length == 2) {
            int angle = incomingData[1];
            this.angle = angle*10;
            outgoingModel = LedMapping.mapLEDs(transformation.callAttr("rotate", PyObject.fromJava(model), this.angle).toJava(byte[].class));
        }
    }

    public void setModel(byte[] model) {
        this.model = model;
        outgoingModel = LedMapping.mapLEDs(model);
    }

    public void rotate(int angle) {
        this.angle += angle;
        outgoingModel = LedMapping.mapLEDs(transformation.callAttr("rotate", PyObject.fromJava(model), this.angle).toJava(byte[].class));
    }

    private void setDelay(int delay) {
        this.delay = delay;
    }
}
