package tech.model;

public class Account {

	public static final String ROLE_NORMAL = "normal";
	public static final String ROLE_SUPER_ADMIN = "superadmin";
	public static final String ROLE_BLOG_ADMIN = "blogadmin";
	public static final String ROLE_SITE_ADMIN = "siteadmin";

	private int userId;
	private String nickName;
	private String email;
	private String locale;
	private String role;
	private String pwd;
	private String sig;

	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
