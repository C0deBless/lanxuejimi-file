package common.serialize;

import java.nio.ByteBuffer;

import common.tools.ErrorCode;
import common.tools.StringUtil;

public abstract class ResponseBase {
	protected int res = ErrorCode.RES_FAILED;
	protected int code = Integer.MIN_VALUE;
	protected String msg = "";

	public int getRes() {
		return res;
	}

	public void setRes(int res) {
		this.res = res;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public abstract void serialize(ByteBuffer bb);

	static class DefaultResposneBase extends ResponseBase {
		@Override
		public void serialize(ByteBuffer bb) {
			bb.putInt(res);
			bb.putInt(code);
			StringUtil.putString(bb, msg);
			bb.flip();
		}
	};

	public static ResponseBase getDefaultResponseData(int res, int code) {
		DefaultResposneBase base = new DefaultResposneBase();
		base.res = res;
		base.code = code;
		return base;
	}

	public static ResponseBase getDefaultResponseData(int res, int code,
			String msg) {
		DefaultResposneBase base = new DefaultResposneBase();
		base.res = res;
		base.code = code;
		base.msg = msg;
		return base;
	}

	public static ResponseBase deserialize(ByteBuffer buffer) {
		int res = buffer.getInt();
		int code = buffer.getInt();
		String msg = StringUtil.getString(buffer);
		return getDefaultResponseData(res, code, msg);
	}
}
