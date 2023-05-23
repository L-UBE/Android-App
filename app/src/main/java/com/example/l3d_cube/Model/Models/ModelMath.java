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

    private int angle = 0;

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
    public byte[] rotate(int angle) {
        this.angle += angle;
        return rotation_py.callAttr("rotate", PyObject.fromJava(model), this.angle).toJava(byte[].class);
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
        angle = 0;
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
        if(angle != 0) {
            return rotate(0);
        }
        return model;
    }
}
