package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import common.serialize.Serializable;
import common.serialize.SerializationHelper;
import common.tools.BooleanUtil;
import common.tools.StringUtil;

public class RevengeModel implements Serializable {

	private int srl;
	private long attackUID;
	private String attackName;
	private int lootedGold;
	private String lootedGoods;
	private boolean isRevenged;
	private long createTime;

	private long pTime;
	private int basepvpWin;
	private int basepvpLose;
	private int level;
	private boolean isProtected;

	private boolean isHelpModel = false;
	private long ownerUID;
	private String ownerName;
	private int ownerLevel;

	private List<RevengeHelpModel> helpList = new ArrayList<>();

	public void addHelpModel(RevengeHelpModel model) {
		if (this.helpList == null) {
			this.helpList = new ArrayList<>();
		}
		this.helpList.add(model);
	}

	@Override
	public void serialize(ByteBuffer bb) {
		bb.putInt(srl);
		bb.putDouble(attackUID);
		StringUtil.putString(bb, attackName);
		bb.putInt(lootedGold);
		StringUtil.putString(bb, lootedGoods);
		BooleanUtil.putBoolean(bb, isRevenged);
		bb.putDouble(createTime);
		BooleanUtil.putBoolean(bb, isProtected);
		bb.putDouble(pTime);
		bb.putInt(basepvpWin);
		bb.putInt(basepvpLose);
		bb.putInt(level);
		BooleanUtil.putBoolean(bb, isHelpModel);
		bb.putDouble(this.ownerUID);
		StringUtil.putString(bb, ownerName);
		bb.putInt(this.ownerLevel);
		SerializationHelper.serializeList(helpList, bb);
	}

	public static RevengeModel deserialize(ByteBuffer bb) {
		int srl = bb.getInt();
		long attackUID = (long) bb.getDouble();
		String attackName = StringUtil.getString(bb);
		int lootedGold = bb.getInt();
		String lootedGoods = StringUtil.getString(bb);
		boolean isRevenged = BooleanUtil.getBoolean(bb);
		long createTime = (long) bb.getDouble();
		boolean isProtected = BooleanUtil.getBoolean(bb);
		long pTime = (long) bb.getDouble();
		int basepvpWin = bb.getInt();
		int basepvpLose = bb.getInt();
		int level = bb.getInt();
		boolean isHelpModel = BooleanUtil.getBoolean(bb);
		long ownerUID = (long) bb.getDouble();
		String ownerName = StringUtil.getString(bb);
		int ownerLevel = bb.getInt();

		List<RevengeHelpModel> helpModels = RevengeHelpModel
				.deserializeList(bb);

		RevengeModel data = new RevengeModel();
		data.srl = srl;
		data.attackUID = attackUID;
		data.attackName = attackName;
		data.lootedGold = lootedGold;
		data.lootedGoods = lootedGoods;
		data.isRevenged = isRevenged;
		data.createTime = createTime;
		data.isProtected = isProtected;
		data.pTime = pTime;
		data.basepvpWin = basepvpWin;
		data.basepvpLose = basepvpLose;
		data.level = level;
		data.isHelpModel = isHelpModel;
		data.helpList = helpModels;
		data.ownerLevel = ownerLevel;
		data.ownerName = ownerName;
		data.ownerUID = ownerUID;
		return data;
	}

	public static List<RevengeModel> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<RevengeModel> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			RevengeModel b = RevengeModel.deserialize(bb);
			list.add(b);
		}
		return list;
	}

	public int getSrl() {
		return srl;
	}

	public void setSrl(int srl) {
		this.srl = srl;
	}

	public long getAttackUID() {
		return attackUID;
	}

	public void setAttackUID(long attackUID) {
		this.attackUID = attackUID;
	}

	public String getAttackName() {
		return attackName;
	}

	public void setAttackName(String attackName) {
		this.attackName = attackName;
	}

	public int getLootedGold() {
		return lootedGold;
	}

	public void setLootedGold(int lootedGold) {
		this.lootedGold = lootedGold;
	}

	public String getLootedGoods() {
		return lootedGoods;
	}

	public void setLootedGoods(String lootedGoods) {
		this.lootedGoods = lootedGoods;
	}

	public boolean isRevenged() {
		return isRevenged;
	}

	public void setRevenged(boolean isRevenged) {
		this.isRevenged = isRevenged;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getpTime() {
		return pTime;
	}

	public void setpTime(long pTime) {
		this.pTime = pTime;
	}

	public int getBasepvpWin() {
		return basepvpWin;
	}

	public void setBasepvpWin(int basepvpWin) {
		this.basepvpWin = basepvpWin;
	}

	public int getBasepvpLose() {
		return basepvpLose;
	}

	public void setBasepvpLose(int basepvpLose) {
		this.basepvpLose = basepvpLose;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isProtected() {
		return isProtected;
	}

	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}

	public List<RevengeHelpModel> getHelpList() {
		return helpList;
	}

	public void setHelpList(List<RevengeHelpModel> helpList) {
		this.helpList = helpList;
	}

	public boolean isHelpModel() {
		return isHelpModel;
	}

	public void setHelpModel(boolean isHelpModel) {
		this.isHelpModel = isHelpModel;
	}

	public long getOwnerUID() {
		return ownerUID;
	}

	public void setOwnerUID(long ownerUID) {
		this.ownerUID = ownerUID;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public int getOwnerLevel() {
		return ownerLevel;
	}

	public void setOwnerLevel(int ownerLevel) {
		this.ownerLevel = ownerLevel;
	}

}
