package com.redpois0n.jstrip;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

public class ArchiveRewriter {
	
	private File input;
	private File output;
	
	public ArchiveRewriter(File file) {
		this.input = file;
		this.output = file;
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
	
		
	
		zos.close();
	}

}
