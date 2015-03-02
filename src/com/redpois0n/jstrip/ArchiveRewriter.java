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
	
	public ArchiveRewriter(File file, List<String> allowedClasses) {
		this.input = file;
		this.output = file;
		this.allowedClasses = allowedClasses;
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
			
			if (shouldWrite(entry)) {
				InputStream is = zip.getInputStream(entry);
				zos.putNextEntry(entry);
				
				byte[] buffer = new byte[1024];
				int read;
				
				while ((read = is.read(buffer)) != -1) {
					zos.write(buffer, 0, read);
				}
				
				is.close();
				zos.closeEntry();
			}		
		}
		
		zip.close();
		zos.close();
	}
	
	public boolean shouldWrite(ZipEntry entry) {
		String className = entry.getName().replace("/", ".");
		if (className.startsWith(".")) {
			className = className.substring(1, className.length());
		}
		
		return allowedClasses.contains(className);
	}

}
