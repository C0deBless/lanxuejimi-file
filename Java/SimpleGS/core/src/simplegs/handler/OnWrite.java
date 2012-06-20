package simplegs.handler;

import java.nio.channels.CompletionHandler;

import simplegs.session.ClientSession;

public class OnWrite implements CompletionHandler<Integer, ClientSession> {

	@Override
	public void completed(Integer result, ClientSession attachment) {

	}

	@Override
	public void failed(Throwable exc, ClientSession attachment) {

	}

}
