package com.example.l3d_cube.Model.LED;

public class LedColor {
    private static final int res = 16;

    private static byte[] gradient1 = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x0F};
    private static byte[] gradient2 = {0x0F, 0x0E, 0x0D, 0x0C, 0x0B, 0x0A, 0x09, 0x08, 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x01};
    private static byte[] gradient3 = {0x0F, 0x0E, 0x0D, 0x0C, 0x0B, 0x0A, 0x09, 0x08, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F};

    public static byte[] random(byte[] uncoloredLEDs) {
        byte[] coloredLEDs = new byte[res*res*res];
        for (int i = 0; i < uncoloredLEDs.length; i++) {
            byte color = (byte) (Math.random() * (15) + 1);
            coloredLEDs[i] = (byte) (uncoloredLEDs[i] * color);
        }
        return coloredLEDs;
    }

    private static byte[] setStaticColor(byte[] uncoloredLEDs, byte color) {
        byte[] coloredLEDs = new byte[res*res*res];
        for (int i = 0; i < uncoloredLEDs.length; i++) {
            coloredLEDs[i] = (byte) (uncoloredLEDs[i] * color);
        }
        return coloredLEDs;
    }

    private static byte[] gradient_onion(byte[] uncoloredLEDs, boolean option) {
        byte[] coloredLEDs = new byte[res*res*res];

        for (int index = 0; index < uncoloredLEDs.length; index++) {
            int i = index / (res * res);
            int temp = index % (res * res);
            int j = temp / res;
            int k = temp % res;

            int minDistance = Math.min(i, Math.min(j, Math.min(res - 1 - i, Math.min(res - 1 - j, Math.min(k, res - 1 - k)))));

            if(option){
                coloredLEDs[index] = (byte) (uncoloredLEDs[index] * (15 - minDistance * 2));
            } else {
                coloredLEDs[index] = (byte) (uncoloredLEDs[index] * (minDistance * 2 + 1));
            }
        }

        return coloredLEDs;
    }

    private static byte[] gradient_x(byte[] uncoloredLEDs, byte[] gradient) {
        byte[] coloredLEDs = new byte[res*res*res];
        for (int i = 0; i < res*res*res; i++) {
            if (i < res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[0]);
            } else if (i < 2*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[1]);
            } else if (i < 3*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[2]);
            } else if (i < 4*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[3]);
            } else if (i < 5*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[4]);
            } else if (i < 6*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[5]);
            } else if (i < 7*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[6]);
            } else if (i < 8*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[7]);
            } else if (i < 9*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[8]);
            } else if (i < 10*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[9]);
            } else if (i < 11*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[10]);
            } else if (i < 12*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[11]);
            } else if (i < 13*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[12]);
            } else if (i < 14*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[13]);
            } else if (i < 15*res*res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[14]);
            } else {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[15]);
            }
        }
        return coloredLEDs;
    }

    private static byte[] gradient_y(byte[] uncoloredLEDs, byte[] gradient) {
        byte[] coloredLEDs = new byte[res * res * res];
        for (int i = 0; i < res * res * res; i++) {
            if (i % (res * res) < res) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[0]);
            } else if (i % (res * res) < res * 2) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[1]);
            } else if (i % (res * res) < res * 3) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[2]);
            } else if (i % (res * res) < res * 4) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[3]);
            } else if (i % (res * res) < res * 5) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[4]);
            } else if (i % (res * res) < res * 6) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[5]);
            } else if (i % (res * res) < res * 7) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[6]);
            } else if (i % (res * res) < res * 8) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[7]);
            } else if (i % (res * res) < res * 9) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[8]);
            } else if (i % (res * res) < res * 10) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[9]);
            } else if (i % (res * res) < res * 11) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[10]);
            } else if (i % (res * res) < res * 12) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[11]);
            } else if (i % (res * res) < res * 13) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[12]);
            } else if (i % (res * res) < res * 14) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[13]);
            } else if (i % (res * res) < res * 15) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[14]);
            } else {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[15]);
            }
        }
        return coloredLEDs;
    }

    private static byte[] gradient_z(byte[] uncoloredLEDs, byte[] gradient) {
        byte[] coloredLEDs = new byte[res*res*res];
        for (int i = 0; i < res*res*res; i++) {
            if (i % res == 0) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[0]);
            } else if (i % res == 1) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[1]);
            } else if (i % res == 2) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[2]);
            } else if (i % res == 3) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[3]);
            } else if (i % res == 4) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[4]);
            } else if (i % res == 5) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[5]);
            } else if (i % res == 6) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[6]);
            } else if (i % res == 7) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[7]);
            } else if (i % res == 8) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[8]);
            } else if (i % res == 9) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[9]);
            } else if (i % res == 10) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[10]);
            } else if (i % res == 11) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[11]);
            } else if (i % res == 12) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[12]);
            } else if (i % res == 13) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[13]);
            } else if (i % res == 14) {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[14]);
            } else {
                coloredLEDs[i] = (byte) (uncoloredLEDs[i] * gradient[15]);
            }
        }
        return coloredLEDs;
    }

    public static byte[] color(byte[] uncoloredLEDs, String color) {
        byte[] coloredLEDs;
        switch (color) {
            case "off":
                coloredLEDs = setStaticColor(uncoloredLEDs, (byte) 0x00);
                break;
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
                coloredLEDs = gradient_x(uncoloredLEDs, gradient1);
                break;
            case "gx2":
                coloredLEDs = gradient_x(uncoloredLEDs, gradient2);
                break;
            case "gx3":
                coloredLEDs = gradient_x(uncoloredLEDs, gradient3);
                break;

            case "gy1":
                coloredLEDs = gradient_y(uncoloredLEDs, gradient1);
                break;
            case "gy2":
                coloredLEDs = gradient_y(uncoloredLEDs, gradient2);
                break;
            case "gy3":
                coloredLEDs = gradient_y(uncoloredLEDs, gradient3);
                break;

            case "gz1":
                coloredLEDs = gradient_z(uncoloredLEDs, gradient1);
                break;
            case "gz2":
                coloredLEDs = gradient_z(uncoloredLEDs, gradient2);
                break;
            case "gz3":
                coloredLEDs = gradient_z(uncoloredLEDs, gradient3);
                break;

            case "gl1":
                coloredLEDs = gradient_onion(uncoloredLEDs, true);
                break;
            case "gl2":
                coloredLEDs = gradient_onion(uncoloredLEDs, false);
                break;

            default:
                coloredLEDs = gradient_onion(uncoloredLEDs, true);
                break;
        }
        return coloredLEDs;
    }
}
