package simplegs.handler;

import java.io.IOException;
import java.nio.channels.CompletionHandler;

import simplegs.session.ClientSession;

public class OnRead implements CompletionHandler<Integer, ClientSession> {

	@Override
	public void completed(Integer result, ClientSession session) {
		if (result > 0) {
			session.pendingRead();
		} else {
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void failed(Throwable exc, ClientSession session) {
		exc.printStackTrace();
	}

}
