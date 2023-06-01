package com.example.l3d_cube.Model.Models;

public abstract class Model {
    public abstract byte[] rotate(String axis, int angle);
    public abstract byte[] translate_x(int x);
    public abstract byte[] translate_y(int y);
    public abstract byte[] translate_z(int z);
    public abstract byte[] scale(int scale);
    public abstract byte[] reset();
    public abstract byte[] getModel();
}
