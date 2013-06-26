package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import common.serialize.Serializable;
import common.tools.StringUtil;

public class RevengeHelpModel implements Serializable {

	public static final int STATUS_WAITING = 0;
	public static final int STATUS_ACCEPTED = 1;
	public static final int STATUS_REVENGED = 2;

	private int revSrl;
	private long helpCSN;
	private String helpName;
	private int helpLevel;
	private int status;

	@Override
	public void serialize(ByteBuffer bb) {
		bb.putInt(revSrl);
		bb.putDouble(helpCSN);
		bb.putInt(status);
		StringUtil.putString(bb, this.helpName);
		bb.putInt(helpLevel);
	}

	public static RevengeHelpModel deserialize(ByteBuffer bb) {
		int revSrl = bb.getInt();
		long helpCSN = (long) bb.getDouble();
		int status = bb.getInt();
		String helpName = StringUtil.getString(bb);
		int helpLevel = bb.getInt();
		RevengeHelpModel data = new RevengeHelpModel();
		data.revSrl = revSrl;
		data.helpCSN = helpCSN;
		data.status = status;
		data.helpName = helpName;
		data.helpLevel = helpLevel;
		return data;
	}

	public static List<RevengeHelpModel> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<RevengeHelpModel> list = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			list.add(RevengeHelpModel.deserialize(bb));
		}
		return list;
	}

	public int getRevSrl() {
		return revSrl;
	}

	public void setRevSrl(int revSrl) {
		this.revSrl = revSrl;
	}

	public long getHelpCSN() {
		return helpCSN;
	}

	public void setHelpCSN(long helpCSN) {
		this.helpCSN = helpCSN;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHelpName() {
		return helpName;
	}

	public void setHelpName(String helpName) {
		this.helpName = helpName;
	}

	public int getHelpLevel() {
		return helpLevel;
	}

	public void setHelpLevel(int helpLevel) {
		this.helpLevel = helpLevel;
	}

}
