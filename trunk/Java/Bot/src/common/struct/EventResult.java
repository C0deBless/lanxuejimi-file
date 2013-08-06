package common.struct;

public class EventResult {
	public static final int DESTROY = 0;
	public static final int INVISIBLE = 1;

	private EventResultObjectAction success;
	private EventResultObjectAction failure;

	public EventResult(int success, int failure) {
		if( DESTROY == success ) {
			this.success = EventResultObjectAction.DESTROY;
		}
		else {
			this.success = EventResultObjectAction.INVISIBLE;
		}
		
		if( DESTROY == failure ) {
			this.failure = EventResultObjectAction.DESTROY;
		}
		else {
			this.failure = EventResultObjectAction.INVISIBLE;
		}
	}

	public EventResult(EventResultObjectAction success, EventResultObjectAction failure) {
		this.success = success;
		this.failure = failure;
	}

	public EventResultObjectAction getSuccess() {
		return success;
	}

	public EventResultObjectAction getFailure() {
		return failure;
	}

	public EventResult clon() {
		EventResult obj = new EventResult(this.success, this.failure);
		return obj;
	}
	
	public static int eventResultActionToResultVaue( EventResultObjectAction action ) {
		if(EventResultObjectAction.DESTROY == action ) {
			return DESTROY;
		}
		else if(EventResultObjectAction.INVISIBLE == action ) {
			return INVISIBLE;
		}
		return -1;
	}
}
