package com.example.l3d_cube.Model.Models;

import com.chaquo.python.PyObject;

public class ModelShape extends Model {

    private byte[] model;
    private byte[] rotatedModel;

    private PyObject rotation_py;
    private PyObject transformation_shape_py;

    private int xoff = 0;
    private int yoff = 0;
    private int zoff = 0;

    private int angle_x = 0;
    private int angle_y = 0;
    private int angle_z = 0;

    public ModelShape(PyObject rotation_py, PyObject transformation_shape_py, byte[] model) {
        this.model = model;
        rotatedModel = this.model;

        this.rotation_py = rotation_py;
        this.transformation_shape_py = transformation_shape_py;
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

        rotatedModel = model;

        if(angle_x != 0) {
            rotatedModel = rotation_py.callAttr("rotate", PyObject.fromJava(rotatedModel), "x", angle_x).toJava(byte[].class);
        }

        if(angle_y != 0) {
            rotatedModel = rotation_py.callAttr("rotate", PyObject.fromJava(rotatedModel), "y", angle_y).toJava(byte[].class);
        }

        if(angle_z != 0) {
            rotatedModel = rotation_py.callAttr("rotate", PyObject.fromJava(rotatedModel), "z", angle_z).toJava(byte[].class);
        }

        return transform();
    }

    @Override
    public byte[] translate_x(int x) {
        this.xoff += x;
        return transform();
    }

    @Override
    public byte[] translate_y(int y) {
        this.yoff += y;
        return transform();
    }

    @Override
    public byte[] translate_z(int z) {
        this.zoff += z;
        return transform();
    }

    @Override
    public byte[] scale(double scale) {
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

        rotatedModel = model;

        return model;
    }

    private byte[] transform() {
        return transformation_shape_py.callAttr("translateShape", rotatedModel, xoff, yoff, zoff).toJava(byte[].class);
    }


    @Override
    public byte[] getModel() {
        return model;
    }
}
