package com.example.l3d_cube.Model.Models;

import com.chaquo.python.PyObject;

public class ModelMath extends Model {

    private byte[] model;

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
    }

    @Override
    public byte[] rotate(String axis, int angle) {
        if(axis.equals("x")) {
            angle_x += angle;
        }

        else if(axis.equals("y")) {
            angle_y += angle;
        }

        else if(axis.equals("z")) {
            angle_z += angle;
        }
        return rotation_py.callAttr("rotate", PyObject.fromJava(model), axis, getAngleValue(axis)).toJava(byte[].class);
    }

    @Override
    public byte[] translate_x(int x) {
        this.xoff += x;
        model = equation_py.callAttr("compute", this.equation, this.scale, this.xoff, this.yoff, this.zoff, this.fillIn).toJava(byte[].class);
        return performPostRotation();
    }

    @Override
    public byte[] translate_y(int y) {
        this.yoff += y;
        model = equation_py.callAttr("compute", this.equation, this.scale, this.xoff, this.yoff, this.zoff, this.fillIn).toJava(byte[].class);
        return performPostRotation();
    }

    @Override
    public byte[] translate_z(int z) {
        this.zoff += z;
        model = equation_py.callAttr("compute", this.equation, this.scale, this.xoff, this.yoff, this.zoff, this.fillIn).toJava(byte[].class);
        return performPostRotation();
    }

    @Override
    public byte[] scale(double scale) {
        this.scale = scale;
        model = equation_py.callAttr("compute", this.equation, this.scale, this.xoff, this.yoff, this.zoff, this.fillIn).toJava(byte[].class);
        return performPostRotation();
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
        return model;
    }

    @Override
    public byte[] getModel() {
        return model;
    }

    private byte[] performPostRotation() {
        if(angle_x != 0) {
            return rotate("x", angle_x);
        }
        if(angle_y != 0) {
            return rotate("y", angle_y);
        }
        if(angle_z != 0) {
            return rotate("z", angle_z);
        }
        return model;
    }

    private int getAngleValue(String axis) {
        switch (axis) {
            case "x":
                return angle_x;
            case "y":
                return angle_y;
            default:
                return angle_z;
        }
    }
}
