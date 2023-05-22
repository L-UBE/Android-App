package com.example.l3d_cube;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.example.l3d_cube.Model.LED.LedMapping;
import com.example.l3d_cube.Model.PresetShapes.Cube;

public class MainViewModel extends AndroidViewModel {
    private Python py;
    private PyObject rotation_py;
    private PyObject equation_py;

    private boolean handshake = false;

    private boolean fillIn = true;

    private String equation = "";
    private int angle = 0;
    private int xoff = 0;
    private int yoff = 0;
    private int zoff = 0;
    private double scale = 1;

    private byte[] model = Cube.cube;
    public byte[] outgoingModel;
    public final MutableLiveData<Boolean> refresh = new MutableLiveData<>();

    private String color = "gl1";
    private byte brightness = 0x04;

    public MainViewModel(@NonNull Application application) {
        super(application);

        py = Python.getInstance();
        rotation_py = py.getModule("rotation");
        equation_py = py.getModule("equation");
    }

    public void handleIncomingBluetoothData(byte[] incomingData) {
        if(incomingData[0] == 0x00) {
            this.xoff -= incomingData[1];
            new Thread(this::transformation).start();
        }
        else if(incomingData[0] == 0x01) {
            this.xoff += incomingData[1];
            new Thread(this::transformation).start();
        }
        else if(incomingData[0] == 0x02) {
            this.zoff += incomingData[1];
            new Thread(this::transformation).start();
        }
        else if(incomingData[0] == 0x03) {
            this.zoff -= incomingData[1];
            new Thread(this::transformation).start();
        }
        else if(incomingData[0] == 0x04) {
            this.yoff += incomingData[1];
            new Thread(this::transformation).start();
        }
        else if(incomingData[0] == 0x05) {
            this.yoff -= incomingData[1];
            new Thread(this::transformation).start();
        }
        else if(incomingData[0] == 0x06) {
            return;
        }
        else if(incomingData[0] == 0x07) {
            return;
        }
        else if(incomingData[0] == 0x08) {
            this.angle = incomingData[1]*10;
            new Thread(() -> {
                outgoingModel = LedMapping.mapLEDs(rotation_py.callAttr("rotate", PyObject.fromJava(model), this.angle).toJava(byte[].class), brightness);
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
            refresh(this.model);
        }).start();
    }

    public void computeMathEquation(String equation) {
        this.equation = equation;
        new Thread(() -> {
            model = equation_py.callAttr("compute", equation, 1, 0, 0, 0, fillIn).toJava(byte[].class);
            refresh(model);
        }).start();
    }

    public void rotate(int angle) {
        new Thread(() -> {
            this.angle += angle;
            refresh(rotation_py.callAttr("rotate", PyObject.fromJava(model), this.angle).toJava(byte[].class));
        }).start();
    }

    private void transformation() {
        model = equation_py.callAttr("compute", equation, scale, xoff, yoff, zoff, fillIn).toJava(byte[].class);
        if(angle != 0){
            refresh(rotation_py.callAttr("rotate", PyObject.fromJava(model), this.angle).toJava(byte[].class));
        } else {
            refresh(model);
        }
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

    public void setBrightness(int value) {
        brightness = (byte) value;
        refresh(model);
    }

    private void refresh(byte[] data) {
        if(isHandshakeComplete()) {
            outgoingModel = LedMapping.mapLEDs(data, brightness);
            refresh.postValue(Boolean.FALSE.equals(refresh.getValue()));
        }
    }

    public void completeHandshake() {
        handshake = true;
        refresh(model);
    }

    private boolean isHandshakeComplete() {
        return handshake;
    }

    public void setFillIn(boolean fillIn) {
        this.fillIn = fillIn;
        new Thread(this::transformation).start();
    }

    public void setColor(String color) {
        this.color = color;
        refresh(model);
    }
}
