package main;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class DisruptorDemo {

	private static class SimpleEventFactory implements
			EventFactory<SimpleEvent> {

		@Override
		public SimpleEvent newInstance() {
			return new SimpleEvent();
		}

	}

	private static final EventFactory<SimpleEvent> EVENT_FACTORY = new SimpleEventFactory();

	private static final int RING_BUFFER_SIZE = 16;

	public static void main(String[] args) {

		Executor executor = Executors.newSingleThreadExecutor();
		Disruptor<SimpleEvent> disruptor = new Disruptor<>(EVENT_FACTORY,
				RING_BUFFER_SIZE, executor, ProducerType.SINGLE,
				new SleepingWaitStrategy());

		disruptor.handleEventsWith(new SimpleEventHandler());

		RingBuffer<SimpleEvent> ringBuffer = disruptor.start();

		disruptor.publishEvent(new EventTranslator<SimpleEvent>() {

			@Override
			public void translateTo(SimpleEvent event, long sequence) {
				event.setValue("test event 1");
			}
		});

	}

}
