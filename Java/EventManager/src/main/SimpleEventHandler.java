package main;

import java.util.ArrayList;
import java.util.List;

import com.lmax.disruptor.EventHandler;

public class SimpleEventHandler implements EventHandler<SimpleEvent> {

	private List<String> valuesSeen = new ArrayList<>();

	@Override
	public void onEvent(SimpleEvent event, long sequence, boolean endOfBatch)
			throws Exception {
		valuesSeen.add(event.getValue());
	}
}
