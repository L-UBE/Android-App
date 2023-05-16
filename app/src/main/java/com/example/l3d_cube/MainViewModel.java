package com.example.l3d_cube;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.example.l3d_cube.Model.LED.LedMapping;

public class MainViewModel extends AndroidViewModel {
    private Python py;
    private PyObject rotation_py;
    private int angle = 0;

    private PyObject equation_py;
    private boolean fill_in = true;

    public final MutableLiveData<Boolean> refresh = new MutableLiveData<>();
    private byte[] model;
    public byte[] outgoingModel;

    private int delay = 300;

    public MainViewModel(@NonNull Application application) {
        super(application);

        py = Python.getInstance();
        rotation_py = py.getModule("rotation");
        equation_py = py.getModule("equation");

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
//        main.start();
    }

    public void handleIncomingBluetoothData(byte[] incomingData) {
        if(incomingData[0] == 0x08) {
            int angle = incomingData[1];
            new Thread(() -> {
                this.angle = angle*10;
                outgoingModel = LedMapping.mapLEDs(rotation_py.callAttr("rotate", PyObject.fromJava(model), this.angle).toJava(byte[].class));
            }).start();
        }
    }

    public void setModel(byte[] model) {
        new Thread(() -> {
            this.model = model;
            outgoingModel = LedMapping.mapLEDs(model);
            refresh.postValue(Boolean.FALSE.equals(refresh.getValue()));
        }).start();
    }

    public void computeMathEquation(String equation) {
        new Thread(() -> {
            model = equation_py.callAttr("compute", equation, 1, fill_in).toJava(byte[].class);
            outgoingModel = LedMapping.mapLEDs(model);
            refresh.postValue(Boolean.FALSE.equals(refresh.getValue()));
        }).start();
    }

    public void rotate(int angle) {
        new Thread(() -> {
            this.angle += angle;
            outgoingModel = LedMapping.mapLEDs(rotation_py.callAttr("rotate", PyObject.fromJava(model), this.angle).toJava(byte[].class));
            refresh.postValue(Boolean.FALSE.equals(refresh.getValue()));
        }).start();
    }

    private void setDelay(int delay) {
        this.delay = delay;
    }
}
