package simplegs.event;

import java.util.EventListener;

public interface SessionListener extends EventListener {
	public void onOpen(Object handback);

	public void onClose(Object handback);

	public void onRead(Object handback);

	public void onWrite(Object handback);

	public void onError(Object handback);
}
