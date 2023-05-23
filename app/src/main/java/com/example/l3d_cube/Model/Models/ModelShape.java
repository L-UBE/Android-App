package com.example.l3d_cube.Model.Models;

import com.chaquo.python.PyObject;

public class ModelShape extends Model {

    private byte[] model;

    private PyObject rotation_py;

    private int angle = 0;

    public ModelShape(PyObject rotation_py, byte[] model) {
        this.model = model;

        this.rotation_py = rotation_py;
    }

    @Override
    public byte[] rotate(int angle) {
        this.angle += angle;
        return rotation_py.callAttr("rotate", PyObject.fromJava(model), this.angle).toJava(byte[].class);
    }

    @Override
    public byte[] translate_x(int x) {
        return model;
    }

    @Override
    public byte[] translate_y(int y) {
        return model;
    }

    @Override
    public byte[] translate_z(int z) {
        return model;
    }

    @Override
    public byte[] scale(double scale) {
        return model;
    }

    @Override
    public byte[] reset() {
        angle = 0;
        return model;
    }

    @Override
    public byte[] getModel() {
        return model;
    }
}
