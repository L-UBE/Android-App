package com.example.l3d_cube.Model.LED;

public class LedColor {
    private static final int res = 16;

    public static byte[] random(byte[] uncoloredLEDs) {
        byte[] coloredLEDs = new byte[res*res*res];
        for (int i = 0; i < uncoloredLEDs.length; i++) {
            byte color = (byte) (Math.random() * (15-1+1) + 1);
            coloredLEDs[i] = (byte) (uncoloredLEDs[i] * color);
        }
        return coloredLEDs;
    }

    public static byte[] setColor(byte[] uncoloredLEDs, byte color) {
        byte[] coloredLEDs = new byte[res*res*res];
        for (int i = 0; i < uncoloredLEDs.length; i++) {
            coloredLEDs[i] = (byte) (uncoloredLEDs[i] * color);
        }
        return coloredLEDs;
    }

    public static byte[] gradient_z(byte[] uncoloredLEDs) {
        byte[] coloredLEDs = new byte[res*res*res];
        for (int i = 0; i < res*res*res; i++) {
            if (i < res*res) {
                coloredLEDs[i] = uncoloredLEDs[i];
            } else if (i < 2*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x02);
            } else if (i < 3*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x03);
            } else if (i < 4*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x04);
            } else if (i < 5*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x05);
            } else if (i < 6*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x06);
            } else if (i < 7*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x07);
            } else if (i < 8*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x08);
            } else if (i < 9*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x08);
            } else if (i < 10*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x07);
            } else if (i < 11*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x06);
            } else if (i < 12*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x05);
            } else if (i < 13*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x04);
            } else if (i < 14*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x03);
            } else if (i < 15*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x02);
            } else {
                coloredLEDs[i] = uncoloredLEDs[i];
            }
        }
        return coloredLEDs;
    }

    public static byte[] gradient_y(byte[] uncoloredLEDs) {
        byte[] coloredLEDs = new byte[res * res * res];
        for (int i = 0; i < res * res * res; i++) {
            if (i % (res * res) < res) {
                coloredLEDs[i] = uncoloredLEDs[i];
            } else if (i % (res * res) < res * 2) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x02);
            } else if (i % (res * res) < res * 3) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x03);
            } else if (i % (res * res) < res * 4) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x04);
            } else if (i % (res * res) < res * 5) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x05);
            } else if (i % (res * res) < res * 6) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x06);
            } else if (i % (res * res) < res * 7) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x07);
            } else if (i % (res * res) < res * 8) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x08);
            } else if (i % (res * res) < res * 9) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x08);
            } else if (i % (res * res) < res * 10) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x07);
            } else if (i % (res * res) < res * 11) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x06);
            } else if (i % (res * res) < res * 12) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x05);
            } else if (i % (res * res) < res * 13) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x04);
            } else if (i % (res * res) < res * 14) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x03);
            } else if (i % (res * res) < res * 15) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x02);
            } else {
                coloredLEDs[i] = uncoloredLEDs[i];
            }
        }
        return coloredLEDs;
    }

    public static byte[] gradient_x(byte[] uncoloredLEDs) {
        byte[] coloredLEDs = new byte[res*res*res];
        for (int i = 0; i < res*res*res; i++) {
            if (i % res == 0) {
                coloredLEDs[i] = uncoloredLEDs[i];
            } else if (i % res == 1) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x02);
            } else if (i % res == 2) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x03);
            } else if (i % res == 3) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x04);
            } else if (i % res == 4) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x05);
            } else if (i % res == 5) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x06);
            } else if (i % res == 6) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x07);
            } else if (i % res == 7) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x08);
            } else if (i % res == 8) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x08);
            } else if (i % res == 9) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x07);
            } else if (i % res == 10) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x06);
            } else if (i % res == 11) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x05);
            } else if (i % res == 12) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x04);
            } else if (i % res == 13) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x03);
            } else if (i % res == 14) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x02);
            } else {
                coloredLEDs[i] = uncoloredLEDs[i];
            }
        }
        return coloredLEDs;
    }
}
