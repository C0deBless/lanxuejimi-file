package org.runtimemonitoring.tracing;

import java.util.Date;
import java.util.Properties;

import org.runtimemonitoring.tracing.threads.ThreadInfoCapture;

public class JMXTracer implements ITracer {

	public JMXTracer(Properties p) {

	}

	@Override
	public void trace(int value, String... name) {
		echo(value, name);
	}

	@Override
	public void trace(long value, String... name) {
		echo(value, name);
	}

	@Override
	public void trace(String value, String... name) {
		echo(value, name);
	}

	@Override
	public void trace(Date value, String... name) {
		echo(value, name);
	}

	@Override
	public void traceSticky(int value, String... name) {
		echo(value, name);
	}

	@Override
	public void traceSticky(long value, String... name) {
		echo(value, name);
	}

	@Override
	public void traceDelta(int value, String... name) {
		echo(value, name);
	}

	@Override
	public void traceDelta(long value, String... name) {
		echo(value, name);
	}

	@Override
	public void traceDeltaSticky(int value, String... name) {
		echo(value, name);
	}

	@Override
	public void traceDeltaSticky(long value, String... name) {
		echo(value, name);
	}

	@Override
	public void traceIncident(String... name) {
		echo(null, name);
	}

	@Override
	public void traceIncident(int value, String... name) {
		echo(value, name);
	}

	@Override
	public void trace(String value, String type, String... name) {
		echo(value, name);
	}

	@Override
	public void startThreadInfoCapture(int options) {

	}

	@Override
	public void startThreadInfoCapture(int options, boolean nanoTime) {

	}

	@Override
	public ThreadInfoCapture endThreadInfoCapture(String... name) {
		return null;
	}

	@Override
	public String lookupRange(String rangeName, long value) {
		return null;
	}

	@Override
	public long sizeOf(Object obj) {
		return 0;
	}

	private void echo(Object obj, String[] values) {
		System.out.print('-');
		if (obj != null)
			System.out.print(obj.toString());
		if (values != null && values.length > 0) {
			for (String string : values) {
				System.out.print(", ");
				System.out.print(string);
			}
		}
		System.out.print("\r\n");
	}
}
