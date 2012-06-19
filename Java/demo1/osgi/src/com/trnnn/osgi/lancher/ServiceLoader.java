package com.trnnn.osgi.lancher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ServiceLoader {
	public final static <E> Class<E> load(Class<E> clazz) throws IOException,
			ClassNotFoundException {
		return load(clazz, Thread.currentThread().getContextClassLoader());
	}

	@SuppressWarnings("unchecked")
	public final static <E> Class<E> load(Class<E> clazz,
			ClassLoader classLoader) throws IOException, ClassNotFoundException {
		String resource = " META-INF/services/ " + clazz.getName();
		InputStream in = classLoader.getResourceAsStream(resource);
		if (in == null)
			return null;

		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String serviceClassName = reader.readLine();
			return (Class<E>) classLoader.loadClass(serviceClassName);
		} finally {
			in.close();
		}
	}
}
