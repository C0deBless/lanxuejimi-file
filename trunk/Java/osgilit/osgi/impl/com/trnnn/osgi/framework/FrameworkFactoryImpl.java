package com.trnnn.osgi.framework;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

public class FrameworkFactoryImpl implements FrameworkFactory {

	public static final String frameworkImplClass = "com.trnnn.osgi.framework.Framework";

	@Override
	public Framework newFramework(Map<String, String> configuration) {
		try {
			Class<?> clazz = Class.forName(frameworkImplClass);
			Constructor<?> cons = clazz.getConstructor(Map.class);
			Framework framework = (Framework) cons.newInstance(configuration);
			return framework;
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | NoSuchMethodException
				| SecurityException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
