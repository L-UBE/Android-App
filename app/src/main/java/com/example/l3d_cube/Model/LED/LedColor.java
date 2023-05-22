package com.example.l3d_cube.Model.LED;

public class LedColor {
    private static final int res = 16;

    public static byte[] random(byte[] uncoloredLEDs) {
        byte[] coloredLEDs = new byte[res*res*res];
        for (int i = 0; i < uncoloredLEDs.length; i++) {
            byte color = (byte) (Math.random() * (15) + 1);
            coloredLEDs[i] = (byte) (uncoloredLEDs[i] * color);
        }
        return coloredLEDs;
    }

    public static byte[] setStaticColor(byte[] uncoloredLEDs, byte color) {
        byte[] coloredLEDs = new byte[res*res*res];
        for (int i = 0; i < uncoloredLEDs.length; i++) {
            coloredLEDs[i] = (byte) (uncoloredLEDs[i] * color);
        }
        return coloredLEDs;
    }

    public static byte[] gradient_onion(byte[] uncoloredLEDs) {
        byte[] coloredLEDs = new byte[res*res*res];
        int value = 1;

        for (int index = 0; index < uncoloredLEDs.length; index++) {
            int i = index / (res * res);
            int temp = index % (res * res);
            int j = temp / res;
            int k = temp % res;

            int minDistance = Math.min(i, Math.min(j, Math.min(res - 1 - i, Math.min(res - 1 - j, Math.min(k, res - 1 - k)))));

            coloredLEDs[index] = (byte) (uncoloredLEDs[index] * (minDistance * value));
        }

        return coloredLEDs;
    }

    public static byte[] gradient_x(byte[] uncoloredLEDs) {
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
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x09);
            } else if (i < 10*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0A);
            } else if (i < 11*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0B);
            } else if (i < 12*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0C);
            } else if (i < 13*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0D);
            } else if (i < 14*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0E);
            } else if (i < 15*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0F);
            } else {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0F);
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
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x09);
            } else if (i % (res * res) < res * 10) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0A);
            } else if (i % (res * res) < res * 11) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0B);
            } else if (i % (res * res) < res * 12) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0C);
            } else if (i % (res * res) < res * 13) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0D);
            } else if (i % (res * res) < res * 14) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0E);
            } else if (i % (res * res) < res * 15) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0F);
            } else {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0F);
            }
        }
        return coloredLEDs;
    }

    public static byte[] gradient_z(byte[] uncoloredLEDs) {
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
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x09);
            } else if (i % res == 9) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0A);
            } else if (i % res == 10) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0B);
            } else if (i % res == 11) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0C);
            } else if (i % res == 12) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0D);
            } else if (i % res == 13) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0E);
            } else if (i % res == 14) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0F);
            } else {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * 0x0F);
            }
        }
        return coloredLEDs;
    }

    public static byte[] color(byte[] uncoloredLEDs, String color) {
        byte[] coloredLEDs;
        switch (color) {
            case "s1":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x01);
                break;
            case "s2":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x02);
                break;
            case "s3":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x03);
                break;
            case "s4":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x04);
                break;
            case "s5":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x05);
                break;
            case "s6":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x06);
                break;
            case "s7":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x07);
                break;
            case "s8":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x08);
                break;
            case "s9":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x09);
                break;
            case "s10":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x0A);
                break;
            case "s11":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x0B);
                break;
            case "s12":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x0C);
                break;
            case "s13":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x0D);
                break;
            case "s14":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x0E);
                break;
            case "s15":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x0F);
                break;

            case "gx1":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x0D);
                break;
            case "gx2":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x0E);
                break;
            case "gx3":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x0F);
                break;

            case "gy1":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x0D);
                break;
            case "gy2":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x0E);
                break;
            case "gy3":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x0F);
                break;

            case "gz1":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x0D);
                break;
            case "gz2":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x0E);
                break;
            case "gz3":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x0F);
                break;

            case "gl1":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x0D);
                break;
            case "gl2":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x0E);
                break;

            default:
                coloredLEDs = gradient_onion(uncoloredLEDs);
                break;
        }
        return coloredLEDs;
    }
}
