package com.tech.bll.models;

public class SigninInfoModel {
	private String userName;
	private String userNickName;
	private String userCompetence;
	private String userLogonName;
	private String userId;
	private String userAvatarPath;
	public String getUserAvatarPath() {
		return userAvatarPath;
	}
	public void setUserAvatarPath(String userAvatarPath) {
		this.userAvatarPath = userAvatarPath;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserCompetence(String userCompetence) {
		this.userCompetence = userCompetence;
	}
	public String getUserCompetence() {
		return userCompetence;
	}
	public void setUserLogonName(String userLogonName) {
		this.userLogonName = userLogonName;
	}
	public String getUserLogonName() {
		return userLogonName;
	}
	/*public static void main(String[] args) throws MapperException{
		SigninInfoModel model=new SigninInfoModel();
		model.setUserCompetence("competence");
		model.setUserLogonName("logonname");
		model.setUserName("username");
		model.setUserNickName("usernickname");
		Vector<SigninInfoModel> list=new Vector<SigninInfoModel>();
		list.add(model);
		list.add(model);
		JSONValue json=JSONMapper.toJSON(list);
		String str1=json.render(true);
		System.out.println(str1);
	}*/
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
}
