package common.struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChildObject {
	ChildInfo generated;
	Map<Integer,ChildInfo> eventSuccess;
	Map<Integer,ChildInfo> eventFailure;

	public ChildInfo getGenerated() {
		return generated;
	}
	public void setGenerated(ChildInfo generated) {
		this.generated = generated;
	}
	
	public boolean isEmptyGeneratedCreate() {
		if( null == this.generated ) {
			return true;
		}
		return this.generated.isEmptyCreate();
	}
	
	public boolean isEmptyGeneratedRemove() {
		if( null == this.generated ) {
			return true;
		}
		return this.generated.isEmptyRemove();
	}
	
	public Map<Integer, ChildInfo> getEventSuccess() {
		return eventSuccess;
	}
	public ChildInfo getEventSuccess( int selectId ) {
		return eventSuccess.get(selectId);
	}
	
	public void setEventSuccess(Map<Integer, ChildInfo> eventSuccess) {
		this.eventSuccess = eventSuccess;
	}
	public Map<Integer, ChildInfo> getEventFailure() {
		return eventFailure;
	}
	public ChildInfo getEventFailure( int selectId ) {
		return eventFailure.get(selectId);
	}
	
	public void setEventFailure(Map<Integer, ChildInfo> eventFailure) {
		this.eventFailure = eventFailure;
	}
	
	public ChildObject clon() { 
		ChildObject obj = new ChildObject();
		
		if( null != this.generated ) {
			obj.generated = this.generated.clon();
		}		
		
		if( null != eventSuccess ) {
			obj.eventSuccess = new HashMap<Integer, ChildInfo>();
			for( int key : eventSuccess.keySet() ) {
				ChildInfo src = eventSuccess.get(key);				
				eventSuccess.put( key, src.clon());
			}
		}
		
		if( null != eventFailure ) {
			obj.eventFailure = new HashMap<Integer, ChildInfo>();
			for( int key : eventFailure.keySet() ) {
				ChildInfo src = eventFailure.get(key);				
				eventFailure.put( key, src.clon());
			}
		}
		
		return obj;
		
	}
	
	public static ChildObject makeUseAdminShadowPVP() {
		ChildObject obj = new ChildObject();
		
		ChildInfo childInfo = new ChildInfo();
		childInfo.id = 0;
		childInfo.remove = new ArrayList<>();
		childInfo.create = childInfo.remove; 
		obj.generated = childInfo;
		
		obj.eventFailure = new HashMap<>();
		obj.eventSuccess = obj.eventFailure;
		
		return obj;
	}
}

