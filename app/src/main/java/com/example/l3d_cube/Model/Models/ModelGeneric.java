package com.example.l3d_cube.Model.Models;

public class ModelGeneric extends Model{

    private byte[] model;

    public ModelGeneric(byte[] model) {
        this.model = model;
    }

    @Override
    public byte[] rotate(String axis, int angle) {
        return model;
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
    public byte[] scale(int scale) {
        return model;
    }

    @Override
    public byte[] reset() {
        return model;
    }

    @Override
    public byte[] getModel() {
        return model;
    }
}
