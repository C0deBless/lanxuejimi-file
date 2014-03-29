package tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class Server {
	public static final int PORT = 7000;
	
	
	public static void main(String[] args) throws IOException, InterruptedException{
		final AsynchronousServerSocketChannel server=AsynchronousServerSocketChannel.open();
		final ByteBuffer readBuffer = ByteBuffer.allocate(128);
		server.bind(new InetSocketAddress(PORT));
		server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
			public void completed(AsynchronousSocketChannel channel, Object attachment) {
				System.out.println("client connected");
				server.accept(null, this);
				channel.read(readBuffer, channel, new CompletionHandler<Integer, AsynchronousSocketChannel>() {

					@Override
					public void completed(Integer result, AsynchronousSocketChannel channel) {
						if(result<=0){
							System.out.println("client disconnect");
							return;
						}
						readBuffer.clear();
						int length = readBuffer.getInt();
						byte[] data= new byte[length];
						readBuffer.get(data, 0, length);
						
						readBuffer.clear();
						System.out.println(length + "," + new String(data));
						channel.read(readBuffer, channel, this);
						ByteBuffer writeBuffer = ByteBuffer.allocate(128);
						String str ="received message"+data;
						byte[] data2 = str.getBytes();
						writeBuffer.putInt(data2.length);
						writeBuffer.put(data2);
						writeBuffer.flip();
						
						
						channel.write(writeBuffer,null, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
							@Override
							public void completed(Integer result,
									AsynchronousSocketChannel channel) {
								System.out.println("complete");
							}
							@Override
							public void failed(Throwable exc,
									AsynchronousSocketChannel channel) {
								exc.printStackTrace();
							}
						});
					
					}

					@Override
					public void failed(Throwable exc, AsynchronousSocketChannel channel) {
						exc.printStackTrace();
					}
				});
			}
			@Override
			public void failed(Throwable exc, Object attachment) {
				exc.printStackTrace();
			}
		});
		Thread.currentThread().join();
	}
}
