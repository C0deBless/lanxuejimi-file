package simple.cache.map;

import java.util.LinkedHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheHashMap<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = 7499570981407621907L;

	public CacheHashMap(int maxCapacity){
		this(maxCapacity,false);
	}
	
	public CacheHashMap(int maxCapacity, boolean isLRU) {
		super(maxCapacity, DEFAULT_LOAD_FACTOR, true);
		this.maxCapacity = maxCapacity;
		this.isLRU = isLRU;
	}

	private static final float DEFAULT_LOAD_FACTOR = 0.75f;
	private final int maxCapacity;
	private final boolean isLRU;
	private final ReadWriteLock rwlock = new ReentrantReadWriteLock();
	private final Lock readLock = rwlock.readLock();
	private final Lock writeLock = rwlock.writeLock();


	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		if (isLRU)
			return size() > maxCapacity;
		else
			return false;
	}

	@Override
	public boolean containsKey(Object key) {
		try {
			readLock.lock();
			return super.containsKey(key);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public V get(Object key) {
		try {
			readLock.lock();
			return super.get(key);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public V put(K key, V value) {
		try {
			writeLock.lock();
			return super.put(key, value);
		} finally {
			writeLock.unlock();
		}
	}

	public int size() {
		try {
			readLock.lock();
			return super.size();
		} finally {
			readLock.unlock();
		}
	}

	public void clear() {
		try {
			writeLock.lock();
			super.clear();
		} finally {
			writeLock.unlock();
		}
	}
}