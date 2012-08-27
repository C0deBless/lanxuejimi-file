package common.tools;


public class TestCommander extends Serializable {

	private int user_id;
	private String name;
	private String pos;
//	private List<Integer> skills;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	// public List<Integer> getSkills() {
	// return skills;
	// }
	//
	// public void setSkills(List<Integer> skills) {
	// this.skills = skills;
	// }

}
