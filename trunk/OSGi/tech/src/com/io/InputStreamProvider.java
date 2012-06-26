package com.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class InputStreamProvider {
	public static InputStream getPlainTextInputStream(String source){
		return new ByteArrayInputStream(source.getBytes(),0,source.getBytes().length);
	}
}
