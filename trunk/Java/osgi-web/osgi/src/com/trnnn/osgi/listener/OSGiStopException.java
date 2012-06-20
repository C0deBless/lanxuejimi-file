package com.trnnn.osgi.listener;

public class OSGiStopException extends RuntimeException {

	public OSGiStopException(String string, Exception e) {
		super(string, e);
	}

	private static final long serialVersionUID = 7357386966765043837L;

}
