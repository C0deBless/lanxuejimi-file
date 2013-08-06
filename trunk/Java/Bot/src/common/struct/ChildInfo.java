package common.struct;

import java.util.ArrayList;
import java.util.List;

public class ChildInfo {
	int id;
	List<Integer> create;
	List<Integer> remove;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Integer> getCreate() {
		return create;
	}
	public void setCreate(List<Integer> create) {
		this.create = create;
	}	
	public List<Integer> getRemove() {
		return remove;
	}
	public void setRemove(List<Integer> remove) {
		this.remove = remove;
	}
	
	
	public void initCreate(String data) {
		this.create = init( data);
	}
	
	public void initRemove(String data) {
		this.remove = init( data);
	}
	
	private List<Integer> init( String data ) {
		if( (null == data) || (data.equalsIgnoreCase("")) ) {
			return null;
		}
		
		String [] arr = data.split(",");
		List<Integer> list = new ArrayList<>();
		for( String s : arr ) {
			list.add( Integer.parseInt(s) );
		}
		return list;
	}
	
	public boolean isEmptyCreate() {
		if( null == this.create ) {
			return true;
		}
		return this.create.isEmpty();
	}
	
	public boolean isEmptyRemove() {
		if( null == this.remove ) {
			return true;
		}
		return this.remove.isEmpty();
	}
	
	
	public ChildInfo clon() {
		ChildInfo obj = new ChildInfo();
		obj.id = this.id;
		
		if( null != this.create ) {
			obj.create = new ArrayList<>();
			for( int value : this.create ) {
				obj.create.add(value);
			}
		}
		
		if( null != this.remove ) {
			obj.remove = new ArrayList<>();
			for( int value : this.remove ) {
				obj.remove.add(value);
			}
		}
		return obj;
	}
	

	
}
