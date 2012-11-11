package tech.model;

import java.util.ArrayList;
import java.util.List;

public class Tag {
	private int id;
	private String name;
	private String value;
	private List<Tag> sub = new ArrayList<>();;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Tag> getSub() {
		return sub;
	}

	public void addSub(Tag tag) {
		this.sub.add(tag);
	}

}
