package common.processmonitor.handler;

import java.nio.channels.CompletionHandler;

import common.processmonitor.session.AioSession;

public class OnWrite implements CompletionHandler<Integer, AioSession> {

	@Override
	public void completed(Integer result, AioSession session) {
		session.isWriting(false);
		session.notifyWriteProcess();
	}

	@Override
	public void failed(Throwable exc, AioSession session) {
		session.close();
	}

}
