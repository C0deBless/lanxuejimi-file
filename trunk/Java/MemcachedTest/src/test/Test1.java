package test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

public class Test1 {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException,
			InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		// Get a memcached client connected to several servers
		MemcachedClient c = new MemcachedClient(
				AddrUtil.getAddresses("192.168.10.1:11211"));

		// Try to get a value, for up to 5 seconds, and cancel if it doesn't
		// return
		Object myObj = null;
		Future<Object> f = c.asyncGet("someKey");
		try {
			myObj = f.get(5, TimeUnit.SECONDS);
			System.out.println(myObj);
		} catch (TimeoutException e) {
			// Since we don't need this, go ahead and cancel the operation. This
			// is not strictly necessary, but it'll save some work on the
			// server.
			f.cancel(false);
			// Do other timeout related stuff
		}
	}

}
