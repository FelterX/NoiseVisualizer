package fr.felterx.noisevisualizer;

import java.util.Random;

/*
* By FelterX 15 oct. 2022 14:27:32
*/

public class MathsUtil {

	public static float clamp(float value, float min, float max) {
		if (value < min)
			return min;
		else if (value > max)
			return max;

		return value;
	}

	public static int getRandom(int from, int to) {
		return getRandom(from, to, new Random());
	}

	public static int getRandom(int from, int to, Random random) {
		if (from < to)
			return from + random.nextInt(Math.abs(to - from));
		return from - random.nextInt(Math.abs(to - from));
	}
}
