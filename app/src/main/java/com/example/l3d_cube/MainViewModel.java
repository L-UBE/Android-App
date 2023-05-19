package com.example.l3d_cube;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.example.l3d_cube.Model.LED.LedMapping;
import com.example.l3d_cube.bluetooth.Utility.BluetoothConnectionUtils;
import com.example.l3d_cube.bluetooth.Utility.BluetoothSystemUtils;

public class MainViewModel extends AndroidViewModel {
    private Python py;
    private PyObject rotation_py;

    private String equation = "";
    private int angle = 0;
    private int xoff = 0;
    private int yoff = 0;
    private int zoff = 0;
    private double scale = 1;

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
        if(incomingData[0] == 0x00) {
            this.xoff -= incomingData[1];
            new Thread(this::transformation).start();
        }
        else if(incomingData[0] == 0x01) {
            this.xoff -= incomingData[1];
            new Thread(this::transformation).start();
        }
        else if(incomingData[0] == 0x02) {
            this.xoff -= incomingData[1];
            new Thread(this::transformation).start();
        }
        else if(incomingData[0] == 0x03) {
            this.xoff -= incomingData[1];
            new Thread(this::transformation).start();
        }
        else if(incomingData[0] == 0x04) {
            this.xoff -= incomingData[1];
            new Thread(this::transformation).start();
        }
        else if(incomingData[0] == 0x05) {
            this.xoff -= incomingData[1];
            new Thread(this::transformation).start();
        }
        else if(incomingData[0] == 0x06) {
            return;
        }
        else if(incomingData[0] == 0x07) {
            return;
        }
        else if(incomingData[0] == 0x08) {
            int angle = incomingData[1];
            this.angle = angle*10;
            new Thread(() -> {
                outgoingModel = LedMapping.mapLEDs(rotation_py.callAttr("rotate", PyObject.fromJava(model), this.angle).toJava(byte[].class));
            }).start();
        }
        else if(incomingData[0] == 0x09) {
            scale = getScale(incomingData[1]);
            new Thread(this::transformation).start();
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
        this.equation = equation;
        new Thread(() -> {
            model = equation_py.callAttr("compute", equation, 1, 0, 0, 0, fill_in).toJava(byte[].class);
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

    private void transformation() {
        model = equation_py.callAttr("compute", equation, scale, xoff, yoff, zoff, fill_in).toJava(byte[].class);
        if(angle != 0){
            outgoingModel = LedMapping.mapLEDs(rotation_py.callAttr("rotate", PyObject.fromJava(model), this.angle).toJava(byte[].class));
        } else {
            outgoingModel = LedMapping.mapLEDs(model);
        }
        refresh.postValue(Boolean.FALSE.equals(refresh.getValue()));
    }

    private double getScale(int zoom){
        if(zoom == 2) {
            return .5;
        }
        else if(zoom == 3) {
            return .25;
        }
        else if(zoom == 5) {
            return 2;
        }
        else if(zoom == 6) {
            return 4;
        }
        else {
            return 1;
        }
    }

    public void reset() {
        angle = 0;
        xoff = 0;
        yoff = 0;
        zoff = 0;
        scale = 1;
    }


    private void setDelay(int delay) {
        this.delay = delay;
    }
}
