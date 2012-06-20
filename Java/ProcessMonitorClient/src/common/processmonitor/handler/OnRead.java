package common.processmonitor.handler;

import java.nio.channels.CompletionHandler;

import common.processmonitor.session.AioSession;

public class OnRead implements CompletionHandler<Integer, AioSession> {

	@Override
	public void completed(Integer result, AioSession session) {
		if (result <= 0) {
			session.close();
		} else {
			session.pendingRead();
		}
	}

	@Override
	public void failed(Throwable exc, AioSession session) {
		session.close();
	}
}
