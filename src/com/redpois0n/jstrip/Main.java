package com.redpois0n.jstrip;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarInputStream;

public class Main {

	public static void main(String[] args) throws Exception {
		try {
			File launchJar;
			List<File> libraries = new ArrayList<File>();
			File outJar;

			if (argsContains(args, "-io")) {
				String sjar = getArg(args, "-io");
				if (sjar != null) {
					launchJar = new File(sjar);
					outJar = new File(sjar);
				} else {
					throw new IllegalArgumentException("No file specified");
				}
			} else {
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
				
				if (argsContains(args, "-out")) {
					String sOutFile = getArg(args, "-out");

					if (sOutFile != null) {
						outJar = new File(sOutFile);
					} else {
						throw new IllegalArgumentException("Output not specified");
					}
				}
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

			for (File file : libraries) {
				log("Loading library " + file.getName());
				Classpath.addFile(file);
			}
			
			Classpath.addFile(launchJar);
			
			List<JarInputStream> jiss = new ArrayList<JarInputStream>();
			for (File file : libraries) {
				jiss.add(new JarInputStream(new FileInputStream(file)));
			}
			
			jiss.add(new JarInputStream(new FileInputStream(launchJar)));
			
			Scanner scanner = new Scanner("ssl.Main", jiss, args);
			scanner.run();
		} catch (Exception ex) {
			ex.printStackTrace();
			printUsage();
		}
	}
	
	public static void log(String s) {
		System.out.println(s);
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
