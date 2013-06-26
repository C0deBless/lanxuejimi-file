package common.struct;

public class MoveItemResult {
	private boolean isSuccess;
	private String msg;
	private Item item;
	
	public MoveItemResult(boolean isSuccess, String msg, Item item) {
		super();
		this.isSuccess = isSuccess;
		this.msg = msg;
		this.item = item;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public String getMsg() {
		return msg;
	}

	public Item getItem() {
		return item;
	}
	
	
}
