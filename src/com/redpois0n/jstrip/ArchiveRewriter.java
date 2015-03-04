package com.redpois0n.jstrip;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ArchiveRewriter {

	private File input;
	private File output;
	private List<String> allowedClasses;
	private long start;
	private long end;
	private long startSize;
	private long endSize;

	public ArchiveRewriter(File input, File output, List<String> allowedClasses) {
		this.input = input;
		this.output = output;
		this.allowedClasses = allowedClasses;
		this.start = System.currentTimeMillis();
		this.startSize = input.length();
	}

	public boolean isSameFile() {
		return input == output;
	}

	public void rewrite() throws Exception {
		OutputStream os;

		if (isSameFile()) {
			os = new ByteArrayOutputStream();
		} else {
			os = new FileOutputStream(output);
		}

		ZipOutputStream zos = new ZipOutputStream(os);
		ZipFile zip = new ZipFile(input);

		Enumeration<? extends ZipEntry> entries = zip.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();

			boolean isClass = Utils.isClass(entry.getName());

			if (shouldWrite(entry)) {
				InputStream is = zip.getInputStream(entry);
				zos.putNextEntry(entry);

				if (isClass) {
					Main.log("Writing class " + entry.getName());
				} else {
					Main.log("Writing " + entry.getName());
				}
				byte[] buffer = new byte[1024];
				int read;

				while ((read = is.read(buffer)) != -1) {
					zos.write(buffer, 0, read);
				}

				is.close();
				zos.closeEntry();
			} else if (isClass) {
				Main.log("Skipping class " + entry.getName() + " (class never loaded)");
			} else {
				Main.log("Skipping " + entry.getName());
			}
		}

		zip.close();
		zos.close();
		
		end = System.currentTimeMillis();
		
		if (isSameFile()) {
			FileOutputStream fos = new FileOutputStream(output);
			fos.write(((ByteArrayOutputStream) os).toByteArray());
			fos.close();
		}
		
		this.endSize = output.length();
	}
	
	public long getTime() {
		return end - start;
	}
	
	public int getSizeReduced() {
		double percent = (double) endSize / (double) startSize;
		return (int) (percent * 100);
	}
	
	public long getOldSize() {
		return startSize;
	}
	
	public long getNewSize() {
		return endSize;
	}

	public boolean shouldWrite(ZipEntry entry) {
		String className = Utils.getClassName(entry.getName());

		return Utils.isClass(entry.getName()) && allowedClasses.contains(className) || !Utils.isClass(entry.getName());
	}

}
