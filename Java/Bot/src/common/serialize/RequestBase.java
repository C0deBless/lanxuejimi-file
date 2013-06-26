package common.serialize;

import java.nio.ByteBuffer;

public abstract class RequestBase {
	protected long userId;

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public abstract void serialize(ByteBuffer bb);

	public static RequestBase getDefaultModel(long userId) {
		RequestBase base = new RequestBase() {
			@Override
			public void serialize(ByteBuffer bb) {
				bb.putLong(userId);
				bb.flip();
			}
		};
		base.userId = userId;
		return base;
	}

	public static RequestBase deserialize(ByteBuffer buffer) {
		long userId = buffer.getLong();
		return getDefaultModel(userId);
	}
}
