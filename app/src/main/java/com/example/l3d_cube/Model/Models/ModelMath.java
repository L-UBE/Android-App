package com.example.l3d_cube.Model.Models;

import com.chaquo.python.PyObject;

public class ModelMath extends Model {

    private byte[] model;
    private byte[] rotated_model;

    private PyObject rotation_py;
    private PyObject equation_py;

    private String equation = "";
    private double scale = 1;
    private int xoff = 0;
    private int yoff = 0;
    private int zoff = 0;

    private int angle_x = 0;
    private int angle_y = 0;
    private int angle_z = 0;

    private boolean fillIn;


    public ModelMath(PyObject equation_py, PyObject rotation_py, String equation, double scale, int xoff, int yoff, int zoff, boolean fillIn) {
        this.equation_py = equation_py;
        this.rotation_py = rotation_py;

        this.equation = equation;
        this.scale = scale;
        this.xoff = xoff;
        this.yoff = yoff;
        this.zoff = zoff;
        this.fillIn = fillIn;

        model = equation_py.callAttr("compute", this.equation, this.scale, this.xoff, this.yoff, this.zoff, this.fillIn).toJava(byte[].class);
        rotated_model = model;
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

        rotated_model = rotate();
        return rotated_model;
    }

    @Override
    public byte[] translate_x(int x) {
        this.xoff += x;
        model = transform();
        rotated_model = rotate();
        return rotated_model;
    }

    @Override
    public byte[] translate_y(int y) {
        this.yoff += y;
        model = transform();
        rotated_model = rotate();
        return rotated_model;
    }

    @Override
    public byte[] translate_z(int z) {
        this.zoff += z;
        model = transform();
        rotated_model = rotate();
        return rotated_model;
    }

    @Override
    public byte[] scale(int scale) {
        if(scale == 2) {
            this.scale /= 2;
        }
        else if(scale == -2) {
            this.scale *= 2;
        }

        model = transform();
        rotated_model = rotate();
        return rotated_model;
    }

    @Override
    public byte[] reset() {
        angle_x = 0;
        angle_y = 0;
        angle_z = 0;

        xoff = 0;
        yoff = 0;
        zoff = 0;

        scale = 1;

        model = equation_py.callAttr("compute", equation, scale, xoff, yoff, zoff, fillIn).toJava(byte[].class);
        rotated_model = model;
        return rotated_model;
    }

    @Override
    public byte[] getModel() {
        return rotated_model;
    }

    private byte[] rotate() {
        byte[] rotatedModel = model;

        if(angle_x != 0) {
            rotatedModel = rotation_py.callAttr("rotate", PyObject.fromJava(rotatedModel), "x", angle_x).toJava(byte[].class);
        }

        if(angle_y != 0) {
            rotatedModel = rotation_py.callAttr("rotate", PyObject.fromJava(rotatedModel), "y", angle_y).toJava(byte[].class);
        }

        if(angle_z != 0) {
            rotatedModel = rotation_py.callAttr("rotate", PyObject.fromJava(rotatedModel), "z", angle_z).toJava(byte[].class);
        }

        return rotatedModel;
    }

    private byte[] transform() {
        return equation_py.callAttr("compute", this.equation, this.scale, this.xoff, this.yoff, this.zoff, this.fillIn).toJava(byte[].class);
    }
}
