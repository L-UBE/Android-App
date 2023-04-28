package com.example.l3d_cube.Model;

import java.util.Random;

public class PresetModel {
    public static byte[] randomModel() {
        byte[] random = new byte[512];
        new Random().nextBytes(random);
        return random;
    }
}
