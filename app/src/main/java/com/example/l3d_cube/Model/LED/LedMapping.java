package com.example.l3d_cube.Model.LED;

public class LedMapping {
    private static final int resolution = 16;

	public static byte[] mapLEDs(byte[] unmappedLEDs, String color, byte brightness) {
//		byte[] coloredLEDs = LedColor.color(unmappedLEDs, "s15");
//		return coloredLEDs;
		byte[] coloredLEDs = LedColor.color(unmappedLEDs, color);
		byte[] mappedLEDs = new byte[resolution*resolution*resolution];

		for (int i = 0; i < unmappedLEDs.length; i++) {
			short hash = LedHash.hash[i];
			int index = getMappedIndex(hash);
			mappedLEDs[index] = coloredLEDs[i];
		}

		return compress(mappedLEDs, brightness);
	}

	private static int getMappedIndex(short hash) {
		byte stage = (byte) ((hash >> 12) - 1);
		byte channel = (byte) (((hash >> 8) & 0x0F) - 1);
		byte led = (byte) (hash & 0xFF);

		return Byte.toUnsignedInt(channel) * resolution * resolution
				+ Byte.toUnsignedInt(stage) * resolution * resolution * 4
				+ Byte.toUnsignedInt(led);
	}

	private static byte[] compress(byte[] uncompressed, byte brightness) {
		byte[] compressed = new byte[(resolution*resolution*resolution / 2) + 1];

		for (int i = 0; i < compressed.length - 1; i++) {
			compressed[i] = (byte) (uncompressed[2*i] << 4 | uncompressed[2*i + 1]);
		}

		compressed[compressed.length - 1] = brightness;

		return compressed;
	}
}
