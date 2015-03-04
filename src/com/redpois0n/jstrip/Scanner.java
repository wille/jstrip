package com.redpois0n.jstrip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class Scanner extends ClassLoader {
	
	private final List<String> loaded = new ArrayList<String>();
	private final List<String> resources = new ArrayList<String>();
	private final Map<String, byte[]> classes = new HashMap<String, byte[]>();

	private String mainclazz;
	private String[] arguments;

	public Scanner(String mainclazz, List<JarInputStream> jiss, String[] arguments) {
		super(Main.class.getClassLoader());
		for (JarInputStream jis : jiss) {
			loadResources(jis);
		}
		this.mainclazz = mainclazz;
		this.arguments = arguments;
	}

	public void run() throws Exception {
		Main.log("Invoking main");
		
		Class<?> clazz = loadClass(mainclazz);
		Method main = clazz.getDeclaredMethod("main", String[].class);
		main.invoke(this, new Object[] { arguments });
	}
	
	public List<String> getLoadedClasses() {
		return loaded;
	}
	
	public List<String> getResources() {
		return resources;
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		Main.log("Getting resource as stream: " + name);
		return super.getResourceAsStream(name);
	}

	@Override
	public URL getResource(String name) {
		Main.log("Getting resource: " + name);
		resources.add(name);
		return super.getResource(name);
	}

	@Override
	protected Enumeration<URL> findResources(String name) throws IOException {
		Main.log("Looking for resources: " + name);
		return super.findResources(name);
	}

	public Class<?> findClass(String name) throws ClassNotFoundException {
		Main.log("Finding class: " + name);
		
		byte[] data = getClassData(name);
		
		if (data != null) {
			return defineClass(name, data, 0, data.length, Main.class.getProtectionDomain());
		} else {
			throw new ClassNotFoundException(name);
		}
	}

	@Override
	protected String findLibrary(String libname) {
		Main.log("Finding library: " + libname);
		return super.findLibrary(libname);
	}

	@Override
	protected URL findResource(String name) {
		Main.log("Looking for resource: " + name);
		return super.findResource(name);
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		return super.getResources(name);
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		Main.log("Loading class: " + name);
		loaded.add(name);
		return super.loadClass(name, resolve);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return loadClass(name, false);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Scanner) {
			return ((Scanner) o).getParent() == getParent();
		}
		return false;
	}
	
	public void loadResources(JarInputStream stream) {
		try {
			JarEntry entry = null;
			while ((entry = stream.getNextJarEntry()) != null) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();

				byte[] buffer = new byte[1024];

				int read;
				while ((read = stream.read(buffer)) != -1) {
					out.write(buffer, 0, read);
				}

				out.close();

				byte[] array = out.toByteArray();
				
				if (Utils.isClass(entry.getName())) {
					classes.put(Utils.getClassName(entry.getName()), array);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] getClassData(String name) {
		byte[] b = classes.remove(name);
		return b;
	}


}
