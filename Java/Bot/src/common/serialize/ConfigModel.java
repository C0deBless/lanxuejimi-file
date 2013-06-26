package common.serialize;

public class ConfigModel {

	public ConfigModel(String key, String type) {
		this.key = key;
		this.type = type;
	}

	private String key;
	private String type;
	private String genericType1;
	private String genericType2;

	public String getGenericType1() {
		return genericType1;
	}

	public void setGenericType1(String genericType1) {
		this.genericType1 = genericType1;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGenericType2() {
		return genericType2;
	}

	public void setGenericType2(String genericType2) {
		this.genericType2 = genericType2;
	}

}
