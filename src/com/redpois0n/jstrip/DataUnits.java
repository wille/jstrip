package com.redpois0n.jstrip;


public class DataUnits {

	public static String getAsString(long bytes) {
		String s = bytes + " B";
		String[] units = new String[] { "B", "K", "M", "G", "T" };

		for (int i = 5; i > 0; i--) {
			double step = Math.pow(1024, i);
			if (bytes > step) {
				double absolute = Math.abs(bytes / step);
				
				s = String.format("%.2f", absolute) + " " + units[i] + "B";
				break;
			}
		}

		return s;
	}

}
