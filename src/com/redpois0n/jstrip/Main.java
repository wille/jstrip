package com.redpois0n.jstrip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws Exception {
		try {
			File launchJar;
			List<File> libraries = new ArrayList<File>();
			File outJar;
			
			if (argsContains(args, "-in")) {
				String sLaunchJar = getArg(args, "-in");
				if (sLaunchJar != null) {
					launchJar = new File(sLaunchJar);
				} else {
					throw new IllegalArgumentException("Input file needs to be specified");
				}
			} else {
				throw new IllegalArgumentException("Input file needs to be specified");
			}
			
			if (argsContains(args, "-l")) {
				String slib = getArg(args, "-l");
				
				if (slib != null) {
					String[] libs = slib.split(";");
					
					for (String s : libs) {
						libraries.add(new File(s));
					}
				} else {
					throw new IllegalArgumentException("Library not specified");
				}
			}
			
			if (argsContains(args, "-out")) {
				String sOutFile = getArg(args, "-out");
				
				if (sOutFile != null) {
					outJar = new File(sOutFile);
				} else {
					throw new IllegalArgumentException("Output not specified");
				}
			}
			
			for (File file : libraries) {
				ClasspathHack.addFile(file);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			printUsage();
		}
	}
	
	public static void printUsage() {
		System.out.println("Usage: java -jar jstrip.jar -in input.jar -l library1.jar;library2.jar -o output.jar");
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
