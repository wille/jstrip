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
			File outDir = null;
			String mainClass;

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
				String sOutDir = getArg(args, "-out");

				if (sOutDir != null) {
					outDir = new File(sOutDir);
				} else {
					throw new IllegalArgumentException("Output directory not specified");
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

			if (argsContains(args, "-main")) {
				mainClass = getArg(args, "-main");
			} else {
				throw new ClassNotFoundException("Main class not specified");
			}

			List<JarInputStream> jiss = new ArrayList<JarInputStream>();
			for (File file : libraries) {
				Main.log("Loading library " + file.getName());
				jiss.add(new JarInputStream(new FileInputStream(file)));
			}

			jiss.add(new JarInputStream(new FileInputStream(launchJar)));

			Scanner scanner = new Scanner(mainClass, jiss, args);
			scanner.run();

			for (File file : libraries) {
				Main.log("Stripping library " + file.getName());

				File out = new File(outDir, file.getName());

				ArchiveRewriter writer = new ArchiveRewriter(file, out, scanner.getLoadedClasses());
				writer.rewrite();

				Main.log("Stripped library " + file.getName() + ", Took " + writer.getTime() + " ms, Percent smaller " + writer.getSizeReduced() + "%, Old file size, " + DataUnits.getAsString(writer.getOldSize()) + ", New file size " + DataUnits.getAsString(writer.getNewSize()));
			}

			Main.log("Completed, stripped " + libraries.size() + " libraries");
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
