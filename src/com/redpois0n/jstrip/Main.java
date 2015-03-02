package com.redpois0n.jstrip;

public class Main {

	public static void main(String[] args) {

	}
	
	public static boolean argsContains(String[] args, String arg) {
		for (String s : args) {
			if (arg.equals(s)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static String getArg(String[] args, String arg) {
		for (int i = 0; i < args.length; i++) {
			if (arg.equals(args[i])) {
				return args[i + 1];
			}
		}
		
		return null;
	}

}
