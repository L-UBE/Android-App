package com.example.l3d_cube.Model.Models;

import com.chaquo.python.PyObject;

public class ModelShape extends Model {

    private byte[] model;

    private PyObject rotation_py;
    private PyObject transformation_shape_py;

    private int angle = 0;
    private int xoff = 0;
    private int yoff = 0;
    private int zoff = 0;

    public ModelShape(PyObject rotation_py, PyObject transformation_shape_py, byte[] model) {
        this.model = model;

        this.rotation_py = rotation_py;
        this.transformation_shape_py = transformation_shape_py;
    }

    @Override
    public byte[] rotate(int angle) {
        this.angle += angle;
        return rotation_py.callAttr("rotate", PyObject.fromJava(model), this.angle).toJava(byte[].class);
    }

    @Override
    public byte[] translate_x(int x) {
        this.xoff += x;
        model = transformation_shape_py.callAttr("translateShape", model, xoff, yoff, zoff).toJava(byte[].class);
        return performPostRotation();
    }

    @Override
    public byte[] translate_y(int y) {
        this.yoff += y;
        model = transformation_shape_py.callAttr("translateShape", model, xoff, yoff, zoff).toJava(byte[].class);
        return performPostRotation();
    }

    @Override
    public byte[] translate_z(int z) {
        this.zoff += z;
        model = transformation_shape_py.callAttr("translateShape", model, xoff, yoff, zoff).toJava(byte[].class);
        return performPostRotation();
    }

    @Override
    public byte[] scale(double scale) {
        return model;
    }

    @Override
    public byte[] reset() {
        angle = 0;
        xoff = 0;
        yoff = 0;
        zoff = 0;
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
