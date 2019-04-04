package com.yumu.hexie.common.util;

public class DistanceUtil {
	private static final double PI = Math.PI / 180.0;
	private static final double EARTH_RADIUS = 6378.137;

	public static double distanceBetween(double r1, double r2, double t1,
			double t2) {
		double a = (t1 - t2) * PI;
		double b = (r1 - r2) * PI;
		double s = 2
				* EARTH_RADIUS
				* Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
						+ Math.cos(t1 * PI) * Math.cos(t2 * PI)
						* Math.pow(Math.sin(b / 2), 2)));
		return Math.round(s * 1000);
	}

}
