package common.processmonitor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import common.processmonitor.gui.GUILoader;
import common.processmonitor.handler.MessageHandler;
import common.processmonitor.handler.OnRead;
import common.processmonitor.handler.OnWrite;
import common.processmonitor.session.ClientSession;

public class MonitorClient {

	private final int port = 1500;
	GUILoader guiLoader;

	public void run() throws IOException {
		guiLoader = new GUILoader("Monitor Client " + port);
		guiLoader.displayGUI();
		AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
		InetSocketAddress addr = new InetSocketAddress("www.easymode.com", port);
		ClientSession session = new ClientSession(channel, new OnRead(),
				new OnWrite(), new MessageHandler());
		channel.connect(addr, session,
				new CompletionHandler<Void, ClientSession>() {

					@Override
					public void completed(Void result, ClientSession session) {
						// TODO Auto-generated method stub
						System.out.println("DispatchServer connected...");
						session.pendingRead();
					}

					@Override
					public void failed(Throwable exc, ClientSession session) {
						// TODO Auto-generated method stub
						session.close();
					}
				});
	}

}
