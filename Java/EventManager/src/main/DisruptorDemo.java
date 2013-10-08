package main;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.lmax.disruptor.EventFactory;
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

	private static final int RING_BUFFER_SIZE = 10;

	public static void main(String[] args) {
		// final RingBuffer<SimpleEvent> ringBuffer = new
		// PreallocatedRingBuffer<>(
		// EVENT_FACTORY, new MultiProducerSequencer(RING_BUFFER_SIZE,
		// new BlockingWaitStrategy()));
		//
		// final SimpleEventHandler eventHandler = new SimpleEventHandler();
		// final BatchEventProcessor<SimpleEvent> eventProcessor = new
		// BatchEventProcessor<>(
		// ringBuffer, ringBuffer.newBarrier(), eventHandler);
		//
		// ringBuffer.setGatingSequences(eventProcessor.getSequence());

		Executor executor = Executors.newSingleThreadExecutor();
		Disruptor<SimpleEvent> disrunptor = new Disruptor<>(EVENT_FACTORY,
				RING_BUFFER_SIZE, executor, ProducerType.SINGLE,
				new SleepingWaitStrategy());
	}

}
