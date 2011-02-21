package org.beanfuse.security;

public class UserDetails {

	/** userId */
	private Long userid;

	/** 用户真实姓名 */
	private String fullname;

	/** 用户类别 */
	private UserCategory category;

	public UserCategory getCategory() {
		return category;
	}

	public void setCategory(UserCategory category) {
		this.category = category;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long id) {
		this.userid = id;
	}

	public String toString() {
		return userid + ":" + fullname;
	}

}
