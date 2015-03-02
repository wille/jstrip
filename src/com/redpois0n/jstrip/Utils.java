package com.redpois0n.jstrip;

public class Utils {
	
	public static String getClassName(String name) {
		return name.substring(0, name.length() - 6).replace('/', '.');
	}
	
	public static boolean isClass(String name) {
		return name.toLowerCase().endsWith(".class");
	}

}
