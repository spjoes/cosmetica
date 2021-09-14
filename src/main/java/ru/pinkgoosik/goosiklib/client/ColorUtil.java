package ru.pinkgoosik.goosiklib.client;

public class ColorUtil {
	private static final float[] FLOAT_BUFFER = new float[4];
	private static final int ALPHA = 255 << 24;

	public static int color(int r, int g, int b) {
		return ALPHA | (r << 16) | (g << 8) | b;
	}

	public static int color(String hex) {
		int r = Integer.parseInt(hex.substring(0, 2), 16);
		int g = Integer.parseInt(hex.substring(2, 4), 16);
		int b = Integer.parseInt(hex.substring(4, 6), 16);
		return color(r, g, b);
	}

	public static float[] toFloatArray(int color) {
		FLOAT_BUFFER[0] = ((color >> 16 & 255) / 255.0F);
		FLOAT_BUFFER[1] = ((color >> 8 & 255) / 255.0F);
		FLOAT_BUFFER[2] = ((color & 255) / 255.0F);
		FLOAT_BUFFER[3] = ((color >> 24 & 255) / 255.0F);

		return FLOAT_BUFFER;
	}
}