import java.io.*;
import java.net.*;

public class SubHttpProxy extends HttpProxy {
	static private boolean first = true;

	public SubHttpProxy(Socket s) {
		super(s);
	}

	public void writeLog(int c, boolean browser) throws IOException {
		if (first)
			log.write('*');
		first = false;
		log.write(c);
		if (c == '\n')
			log.write('*');
	}

	public String processHostName(String url, String host, int port, Socket sock) {

		return host;
	}

	static public void main(String args[]) throws InterruptedException {
		int port = 9999;
		System.out.println("proxy server running at " + port);
		HttpProxy.log = System.out;
		HttpProxy.logging = true;
		HttpProxy.startProxy(port, SubHttpProxy.class);

		Thread.currentThread().join();
	}

}
