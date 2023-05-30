package com.example.l3d_cube;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.example.l3d_cube.Model.LED.LedMapping;
import com.example.l3d_cube.Model.Models.Model;
import com.example.l3d_cube.Model.Models.ModelGeneric;
import com.example.l3d_cube.Model.Models.ModelMath;
import com.example.l3d_cube.Model.Models.ModelShape;
import com.example.l3d_cube.Model.PresetShapes.Cube;
import com.example.l3d_cube.Utility.MathUtils;

public class MainViewModel extends AndroidViewModel {
    private Python py;
    private PyObject rotation_py;
    private PyObject equation_py;
    private PyObject shape_py;

    private boolean handshake = false;

    private Model model;
    public byte[] outgoingModel;
    public final MutableLiveData<Boolean> refresh = new MutableLiveData<>();

    private boolean power = true;
    private String color = "gl1";
    private byte brightness = 0x04;
    private boolean fillIn = true;

    public MainViewModel(@NonNull Application application) {
        super(application);

        py = Python.getInstance();
        rotation_py = py.getModule("rotation");
        equation_py = py.getModule("equation");
        shape_py = py.getModule("shape");

        model = new ModelShape(rotation_py, shape_py, "cube");
    }

    public void handleIncomingBluetoothData(byte[] incomingData) {
        // LEFT
        if(incomingData[0] == 0x00) {
            translate_x(-1 * incomingData[1]);
        }

        // RIGHT
        else if(incomingData[0] == 0x01) {
            translate_x(incomingData[1]);
        }

        // UP
        else if(incomingData[0] == 0x02) {
            translate_z(incomingData[1]);
        }

        // DOWN
        else if(incomingData[0] == 0x03) {
            translate_z(-1 * incomingData[1]);
        }

        // FORWARD
        else if(incomingData[0] == 0x04) {
            translate_y(incomingData[1]);
        }

        // BACKWARD
        else if(incomingData[0] == 0x05) {
            translate_y(-1 * incomingData[1]);
        }

        // ROTATE X
        else if(incomingData[0] == 0x06) {
            new Thread(() -> {
                rotate("x", incomingData[1]*10);
            }).start();
        }

        // ROTATE Y
        else if(incomingData[0] == 0x07) {
            new Thread(() -> {
                rotate("y", incomingData[1]*10);
            }).start();
        }

        // ROTATE Z
        else if(incomingData[0] == 0x08) {
            new Thread(() -> {
                rotate("z", incomingData[1]*10);
            }).start();
        }

        else if(incomingData[0] == 0x09) {
            new Thread(() -> {
                scale(MathUtils.getScale(incomingData[1]));
            }).start();
        }

        else if(incomingData[0] == 0x10) {
            new Thread(() -> {
                reset();
            }).start();
        }
    }

    public void setShape(String shape) {
        if(!checkPreconditions()){
            return;
        }

        new Thread(() -> {
            model = new ModelShape(rotation_py, shape_py, shape);
            refresh(model.getModel());
        }).start();
    }

    public void setGenericModel(byte[] genericModel) {
        if(!checkPreconditions()){
            return;
        }

        new Thread(() -> {
            model = new ModelGeneric(genericModel);
            refresh(model.getModel());
        }).start();
    }

    public void computeMathEquation(String equation, int scale, int xoff, int yoff, int zoff) {
        if(!checkPreconditions()){
            return;
        }

        new Thread(() -> {
            model = new ModelMath(equation_py,rotation_py, equation, scale, xoff, yoff, zoff, fillIn);
            refresh(model.getModel());
        }).start();
    }

    public void rotate(String axis, int angle) {
        if(!checkPreconditions()){
            return;
        }

        new Thread(() -> {
            refresh(model.rotate(axis, angle));
        }).start();
    }

    public void translate_x(int x){
        if(!checkPreconditions()){
            return;
        }

        new Thread(() -> {
            refresh(model.translate_x(x));
        }).start();
    }

    public void translate_y(int y){
        if(!checkPreconditions()){
            return;
        }

        new Thread(() -> {
            refresh(model.translate_y(y));
        }).start();
    }

    public void translate_z(int z){
        if(!checkPreconditions()){
            return;
        }

        new Thread(() -> {
            refresh(model.translate_z(z));
        }).start();
    }

    public void scale(double scale) {
        if(!checkPreconditions()){
            return;
        }

        new Thread(() -> {
            refresh(model.scale(scale));
        }).start();
    }

    public void reset() {
        if(!checkPreconditions()){
            return;
        }

        new Thread(() -> {
            refresh(model.reset());
        }).start();
    }

    private void refresh(byte[] data) {
        outgoingModel = LedMapping.mapLEDs(data, color, brightness);
        refresh.postValue(Boolean.FALSE.equals(refresh.getValue()));
    }

    public void completeHandshake() {
        handshake = true;
    }

    private boolean isHandshakeComplete() {
        return handshake;
    }

    public void setColor(String color) {
        this.color = color;

        if(!checkPreconditions()){
            return;
        }

        new Thread(() -> {
            refresh(model.getModel());
        }).start();
    }

    public void setBrightness(int value) {
        brightness = (byte) value;

        if(!checkPreconditions()){
            return;
        }

        new Thread(() -> {
            refresh(model.getModel());
        }).start();
    }

    public void setPower(boolean power) {
        if(this.power && !power) {
            color = "off";
            new Thread(() -> {
                refresh(model.getModel());
                this.power = false;
            }).start();
        }
        else if(!this.power && power) {
            if(color.equals("off")) {
                color = "gl1";
            }
            new Thread(() -> {
                refresh(model.getModel());
                this.power = true;
            }).start();
        }
    }

    private boolean checkPreconditions() {
        return power && handshake;
    }

    //TODO:
    public void setFillIn(boolean fillIn) {
        this.fillIn = fillIn;
    }
}
