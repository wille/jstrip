package com.redpois0n.jstrip;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Scanner extends ClassLoader {
	
	private final List<String> loaded = new ArrayList<String>();

	private String mainclazz;
	private String[] arguments;

	public Scanner(String mainclazz, String[] arguments) {
		super(Scanner.class.getClassLoader());
		this.mainclazz = mainclazz;
		this.arguments = arguments;
	}

	public void run() throws Exception {
		Main.log("Invoking main");
		
		Class<?> clazz = loadClass(mainclazz);
		Method main = clazz.getDeclaredMethod("main", String[].class);
		main.invoke(clazz.newInstance(), new Object[] { arguments });
	}
	
	public List<String> getLoadedClasses() {
		return loaded;
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		Main.log("Getting resource as stream: " + name);
		return super.getResourceAsStream(name);
	}

	@Override
	public URL getResource(String name) {
		Main.log("Getting resource: " + name);
		return super.getResource(name);
	}

	@Override
	protected Enumeration<URL> findResources(String name) throws IOException {
		Main.log("Looking for resources: " + name);
		return super.findResources(name);
	}

	public Class<?> findClass(String name) throws ClassNotFoundException {
		Main.log("Finding class: " + name);
		return super.findClass(name);
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

}
