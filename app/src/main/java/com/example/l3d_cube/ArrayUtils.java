package com.example.l3d_cube;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

public class ArrayUtils {
    public static byte[] intArrayToByteArray(int[] data) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(data.length * 4);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(data);

        return byteBuffer.array();
    }

    public static int[] flatten(int[][] data) {
        return Arrays.stream(data)
                .flatMapToInt(Arrays::stream)
                .toArray();
    }

    public static int[] flatten(int[][][] data) {
        return Arrays.stream(data)
                .flatMap(Arrays::stream)
                .flatMapToInt(Arrays::stream)
                .toArray();
    }
}
