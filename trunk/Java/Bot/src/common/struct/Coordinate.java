package common.struct;

import java.nio.ByteBuffer;

import common.Constants;
import common.serialize.Serializable;
import common.tools.StringUtil;

public class Coordinate implements Serializable {
	int portId;
	String whr = "unknown";
	String ns = "unknown";
	String ew = "unknown";
	String pos = "-1,-1";

	public Coordinate(String whr, int portId, String ns, String ew, String pos) {
		super();
		this.whr = whr;
		this.portId = portId;
		this.ns = ns;
		this.ew = ew;
		this.pos = pos;
	}

	public String getWhr() {
		return whr;
	}

	public void setWhr(String whr) {
		this.whr = whr;
	}

	public int getPortId() {
		return portId;
	}

	public void setPortId(int portId) {
		this.portId = portId;
	}

	public String getNs() {
		return ns;
	}

	public void setNs(String ns) {
		this.ns = ns;
	}

	public String getEw() {
		return ew;
	}

	public void setEw(String ew) {
		this.ew = ew;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public void setData(Coordinate position) {
		this.whr = position.whr;
		this.portId = position.portId;
		this.ns = position.ns;
		this.ew = position.ew;
		this.pos = position.pos;
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(portId);
		StringUtil.putString(bb, whr);
		StringUtil.putString(bb, ns);
		StringUtil.putString(bb, ew);
		StringUtil.putString(bb, pos);
	}

	public static Coordinate deserialize(ByteBuffer bb) {
		int portId = bb.getInt();
		String whr = StringUtil.getString(bb);
		String ns = StringUtil.getString(bb);
		String ew = StringUtil.getString(bb);
		String pos = StringUtil.getString(bb);
		return new Coordinate(whr, portId, ns, ew, pos);
	}
	
	public boolean isSea() {
		if( null == whr ) {
			return false;
		}
		
		if( whr.equalsIgnoreCase(Constants.SEA) && (0 == this.portId) ) {
			return true;
		}
		return false;
	}
}
