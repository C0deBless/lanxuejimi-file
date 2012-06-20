package com.trnnn.osgi.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.osgi.framework.BundleContext;

public class Console implements Runnable {

	protected InputStream in = System.in;
	protected PrintStream out = System.out;
	protected ConsoleCommand command;
	protected BundleContext bundleContext;
	private boolean isRunning = true;

	public Console(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
		build();
	}

	private void build() {
		this.command = new ConsoleCommand(this.bundleContext);
	}

	public void start() throws IOException {
		isRunning = true;
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		while (isRunning) {
			String input;
			try {
				input = reader.readLine();
				command.parseAndExecute(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void close() {
		this.isRunning = false;
	}

	public void print(Object o) {
		this.out.print(o);
	}

	public void println(Object o) {
		this.out.println(o);
	}
}
