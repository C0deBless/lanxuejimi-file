package tech.model;

public class Tag {
	private int id;

	private String name;
	private String value;
	private int parent;

	public Tag(int id, String name, String value, int parent) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
		this.parent = parent;
	}

	public Tag() {
	}

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

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

}
