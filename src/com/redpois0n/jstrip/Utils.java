package com.redpois0n.jstrip;

public class Utils {
	
	public static String getClassName(String name) {
		String clazz;
		
		if (name.length() > ".class".length() && name.toLowerCase().endsWith(".class")) {
			clazz = name.substring(0, name.length() - 6).replace('/', '.');
		} else {
			clazz = name;
		}
		
		return clazz;
	}
	
	public static boolean isClass(String name) {
		return name.toLowerCase().endsWith(".class");
	}

}
