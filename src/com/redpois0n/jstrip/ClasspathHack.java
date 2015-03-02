package com.redpois0n.jstrip;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ClasspathHack {

	public static void addURL(URL u) throws Exception {
		URLClassLoader cl = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class<?> clazz = URLClassLoader.class;

		Method method = clazz.getDeclaredMethod("addURL", new Class[] { URL.class });
		method.setAccessible(true);
		method.invoke(cl, u);
	}

}
