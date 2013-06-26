package common.struct;

import java.nio.ByteBuffer;

import common.serialize.Serializable;
import common.tools.StringUtil;

public class MailSendParameter implements Serializable {
	public static final int SEND_SUCCESS = 0;
	public static final int SEND_FAILED_NOT_FOUND_TO_USER = 1;
	
	private static final MailSendParameter DUMMY = new MailSendParameter(0, "", "", "", "", 0, 0, 0, 0, new Item(), 0 );
	long fromUserId;
	String fromName;
	String toName;
	String title;
	String memo;
	final int itemId;
	final int itemCount;
	int gold;
	final int type;
	Item remainedItem;
	final int guildSRL;
	
	public MailSendParameter(long fromUserId, String fromName, String toName, String title,
			String memo, int itemId, int itemCount, int gold, int type, int guildSRL) {
		this.fromUserId = fromUserId;
		this.fromName = fromName;
		this.toName = toName;
		this.title = title;
		this.memo = memo;
		this.itemId = itemId;
		this.itemCount = itemCount;
		this.gold = gold;
		this.type = type;
		this.remainedItem = new Item();
		this.guildSRL = guildSRL;
	}

	public MailSendParameter(long fromUserId, String fromName, String toName, String title,
			String memo, int itemId, int itemCount, int gold, int type, Item remainedItem, int guildSRL) {
		this( fromUserId, fromName, toName, title, memo, itemId, itemCount, gold, type, guildSRL);
		this.remainedItem = remainedItem;
	}

	public int getType() {
		return type;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public String getTitle() {
		return title;
	}

	public String getMemo() {
		return memo;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getItemId() {
		return itemId;
	}

	public int getItemCount() {
		return itemCount;
	}

	public int getGold() {
		return this.gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public Item getRemainedItem() {
		return remainedItem;
	}
	
	public int getRemainedItemId() {
		if( null == remainedItem ) {
			return Integer.MIN_VALUE;
		}
		return remainedItem.getId();
	}

	public void setRemainedItem(Item remainedItem) {
		this.remainedItem = remainedItem;
	}

	public boolean equals(MailSendParameter mail) {
		if (!this.fromName.equalsIgnoreCase(mail.getFromName())
				|| !this.toName.equalsIgnoreCase(mail.getToName())
				|| !this.title.equalsIgnoreCase(mail.getTitle())
				|| !this.memo.equalsIgnoreCase(mail.getMemo())
				|| (this.itemId != mail.getItemId())
				|| (this.itemCount != mail.getItemCount())
				|| (this.gold != mail.getGold())) {
			return false;
		}
		return true;
	}

	@Override
	public void serialize(ByteBuffer bb) {
		bb.putLong(this.fromUserId);
		StringUtil.putString(bb, fromName);
		StringUtil.putString(bb, toName);
		StringUtil.putString(bb, title);
		StringUtil.putString(bb, memo);
		bb.putInt(itemId);
		bb.putInt(itemCount);
		bb.putInt(gold);
		bb.putInt(type);
		bb.putInt(guildSRL);
		this.remainedItem.serialize(bb);
		
	}

	public static MailSendParameter deserialize(ByteBuffer bb) {
		long fromUserId = bb.getLong();
		String fromName = StringUtil.getString(bb);
		String toName = StringUtil.getString(bb);
		String title = StringUtil.getString(bb);
		String memo = StringUtil.getString(bb);
		int itemId = bb.getInt();
		int count = bb.getInt();
		int gold = bb.getInt();
		int type = bb.getInt();
		int guildSRL = bb.getInt();
		Item remainedItem = Item.deserialize(bb);
		return new MailSendParameter(fromUserId, fromName, toName, title, memo, itemId,
				count, gold, type, remainedItem, guildSRL);
	}
	
	public static MailSendParameter dummy() {
		return DUMMY;
	}
	
	public void URLEncoding() {
		this.memo = StringUtil.urlencode(this.memo);
		this.title = StringUtil.urlencode(this.title);
	}

	public long getFromUserId() {
		return fromUserId;
	}
	
	public int getGuildSRL() {
		return this.guildSRL;
	}
	
}