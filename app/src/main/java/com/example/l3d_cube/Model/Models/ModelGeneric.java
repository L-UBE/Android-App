package com.example.l3d_cube.Model.Models;

import com.chaquo.python.PyObject;

public class ModelGeneric extends Model{

    private byte[] original_model;
    private byte[] model;
    private byte[] rotatedModel;

    private PyObject rotation_py;
    private PyObject shape_py;

    private int xoff = 0;
    private int yoff = 0;
    private int zoff = 0;

    private int angle_x = 0;
    private int angle_y = 0;
    private int angle_z = 0;

    public ModelGeneric(PyObject rotation_py, PyObject shape_py, byte[] model) {
        original_model = model;
        this.model = original_model;
        rotatedModel = this.model;

        this.rotation_py = rotation_py;
        this.shape_py = shape_py;
    }

    @Override
    public byte[] rotate(String axis, int angle) {
        switch (axis) {
            case "x":
                angle_x += angle;
                angle_x %= 360;
                break;
            case "y":
                angle_y += angle;
                angle_y %= 360;
                break;
            case "z":
                angle_z += angle;
                angle_z %= 360;
                break;
        }

        return rotate();
    }

    private byte[] rotate() {
        rotatedModel = original_model;

        if(angle_x != 0) {
            rotatedModel = rotation_py.callAttr("rotate", PyObject.fromJava(rotatedModel), "x", angle_x).toJava(byte[].class);
        }

        if(angle_y != 0) {
            rotatedModel = rotation_py.callAttr("rotate", PyObject.fromJava(rotatedModel), "y", angle_y).toJava(byte[].class);
        }

        if(angle_z != 0) {
            rotatedModel = rotation_py.callAttr("rotate", PyObject.fromJava(rotatedModel), "z", angle_z).toJava(byte[].class);
        }

        model = transform();
        return model;
    }

    @Override
    public byte[] translate_x(int x) {
        this.xoff += x;
        model = transform();
        return model;
    }

    @Override
    public byte[] translate_y(int y) {
        this.yoff += y;
        model = transform();
        return model;
    }

    @Override
    public byte[] translate_z(int z) {
        this.zoff += z;
        model = transform();
        return model;
    }

    @Override
    public byte[] scale(int scale) {
        return model;
    }

    @Override
    public byte[] reset() {
        angle_x = 0;
        angle_y = 0;
        angle_z = 0;

        xoff = 0;
        yoff = 0;
        zoff = 0;

        model = original_model;
        rotatedModel = original_model;

        return model;
    }

    private byte[] transform() {
        return shape_py.callAttr("translateShape", rotatedModel, xoff, yoff, zoff).toJava(byte[].class);
    }

    @Override
    public byte[] getModel() {
        return model;
    }
}
