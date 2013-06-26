package common.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Constants;
import common.serialize.Serializable;
import common.tools.StringUtil;

public class MailBase implements Comparable<MailBase>, Serializable {
	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_ALLY_REQUEST = 1;
	public static final int TYPE_GUILD_JOIN_CONFIRM = 3;
	public static final int TYPE_GUILD_MEMBER_EXILE = 4;
	public static final int TYPE_GUILD_MAIL = 5;
	public static final int TYPE_GUILD_INVITE = 6;
	public static final int TYPE_GUILD_INVITE_CONFIRM = 7;
	public static final int TYPE_INVEN_ITEM_MAIL = 8;
	public static final int TYPE_RAID_RESULT = 9;
	public static final int TYPE_BASEPVP_RESULT = 10;

	int mail_id;
	String fromUser;
	String title;
	String memo;
	char read;
	long sendTime;
	int itemId;
	int itemCount;
	int type;
	int guildSRL;
	String guildName;

	public MailBase(int mail_id, String fromUser, String title, String memo,
			char read, long sendTime, int itemId, int itemCount, int type,
			int guildSRL, String guildName) {
		super();
		this.mail_id = mail_id;
		this.fromUser = fromUser;
		this.title = title;
		this.memo = memo;
		this.read = read;
		this.sendTime = sendTime;
		this.itemId = itemId;
		this.itemCount = itemCount;
		this.type = type;
		this.guildSRL = guildSRL;
		this.guildName = guildName;
		if (null == this.guildName) {
			this.guildName = "";
		}
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public char getRead() {
		return read;
	}

	public void setRead(char read) {
		this.read = read;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public int getMail_id() {
		return mail_id;
	}

	public String getFromUser() {
		return fromUser;
	}

	public String getTitle() {
		return title;
	}

	public String getMemo() {
		return memo;
	}

	public long getSendTime() {
		return sendTime;
	}

	public int getGuildSRL() {
		return guildSRL;
	}

	public String getGuildName() {
		return guildName;
	}
	
	public void setGuildName(String guildName) {
		this.guildName = guildName;
	}

	public Map<String, Object> prepareList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.MAIL_ID, this.mail_id);
		map.put(Constants.FROM, this.fromUser);
		map.put(Constants.TITLE, this.title);
		map.put(Constants.SEND_TIME, this.sendTime);
		map.put(Constants.ITEM_ID, this.itemId);
		map.put(Constants.COUNT, this.itemCount);
		map.put(Constants.READ, this.read);
		map.put(Constants.TYPE, this.type);
		map.put(Constants.GUILD_SRL, this.guildSRL);
		map.put(Constants.GUILD_NAME, this.guildName);
		return map;
	}

	public void serialize(ByteBuffer bb) {
		bb.putInt(mail_id);
		StringUtil.putString(bb, fromUser);
		StringUtil.putString(bb, title);
		StringUtil.putString(bb, memo);
		bb.putChar(read);
		bb.putLong(sendTime);
		bb.putInt(itemId);
		bb.putInt(itemCount);
		bb.putInt(type);
		bb.putInt(guildSRL);
		StringUtil.putString(bb, guildName);
	}
	
	public void serializeForMailList(ByteBuffer bb) {
		bb.putInt(mail_id);
		StringUtil.putString(bb, fromUser);
		StringUtil.putString(bb, title);
		StringUtil.putString(bb, read+"");		
		StringUtil.putString(bb, sendTime+"");
		bb.putInt(itemId);
		bb.putInt(itemCount);
		bb.putInt(type);
		bb.putInt(guildSRL);
		StringUtil.putString(bb, guildName);
	}

	public static MailBase deserialize(ByteBuffer bb) {
		int mail_id = bb.getInt();
		String fromUser = StringUtil.getString(bb);
		String title = StringUtil.getString(bb);
		String memo = StringUtil.getString(bb);
		char read = bb.getChar();
		long sendTime = bb.getLong();
		int itemId = bb.getInt();
		int itemCount = bb.getInt();
		int type = bb.getInt();
		int guildSRL = bb.getInt();
		String guildName = StringUtil.getString(bb);

		return new MailBase(mail_id, fromUser, title, memo, read, sendTime,
				itemId, itemCount, type, guildSRL, guildName);
	}

	public static List<MailBase> deserializeList(ByteBuffer bb) {
		int size = bb.getInt();
		List<MailBase> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			MailBase b = MailBase.deserialize(bb);
			list.add(b);
		}
		return list;
	}

	@Override
	public int compareTo(MailBase o) {
		if (null == o) {
			return 0;
		}
		
		if (this == o) {
			return 0;
		}
		
		if (this.getMail_id() > o.getMail_id()) {
			return -1;
		}
		else if (this.getMail_id() < o.getMail_id()) {
			return 1;
		}
		else {
			return 0;
		}		
	}
	
	public static boolean isNotUpdateGold( final int mailType ) {
		if( (MailBase.TYPE_ALLY_REQUEST == mailType) ||
				(MailBase.TYPE_GUILD_JOIN_CONFIRM == mailType) ||
				(MailBase.TYPE_GUILD_MEMBER_EXILE == mailType) ||
				(MailBase.TYPE_GUILD_INVITE_CONFIRM == mailType) ||
				(MailBase.TYPE_INVEN_ITEM_MAIL == mailType) ||
				(MailBase.TYPE_BASEPVP_RESULT == mailType) ) {
			return true;
		}
		return false;		 
	}
	
	public void setTite( String title ) {
		this.title = title;
		if( null == this.title ) {
			this.title = "empty";
		}
	}
}
