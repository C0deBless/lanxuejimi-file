package com.trnnn.osgi.admin.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFactory {

	private Properties properties;

	public PropertiesFactory(String propertiesURL) {
		this.properties = new Properties();
		InputStream inStream = this.getClass().getClassLoader()
				.getResourceAsStream(propertiesURL);
		try {
			this.properties.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String key){
		return this.properties.get(key).toString();
	}
	
	public void addProperty(String key,Object value){
		this.properties.put(key, value); 
	}
	
	public void removeProperty(String key){
		this.properties.remove(key);
	}
}
