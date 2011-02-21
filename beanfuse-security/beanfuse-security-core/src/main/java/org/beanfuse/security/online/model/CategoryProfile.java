package org.beanfuse.security.online.model;

import org.beanfuse.model.pojo.LongIdObject;
import org.beanfuse.security.UserCategory;
import org.beanfuse.security.online.SessionProfile;

public class CategoryProfile extends LongIdObject implements org.beanfuse.security.online.CategoryProfile {

	private static final long serialVersionUID = 1999239598984221565L;

	protected SessionProfile sessionProfile;

	protected UserCategory category;

	protected int capacity;

	protected int userMaxSessions;

	/** minutes */
	protected int inactiveInterval;

	public CategoryProfile() {
		super();
	}

	public CategoryProfile(UserCategory category, int max, int inactiveInterval) {
		super();
		this.category = category;
		this.capacity = max;
		this.inactiveInterval = inactiveInterval;
	}

	public SessionProfile getSessionProfile() {
		return sessionProfile;
	}

	public void setSessionProfile(SessionProfile sessionProfile) {
		this.sessionProfile = sessionProfile;
	}

	public UserCategory getCategory() {
		return category;
	}

	public void setCategory(UserCategory category) {
		this.category = category;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int max) {
		this.capacity = max;
	}

	public int getInactiveInterval() {
		return inactiveInterval;
	}

	public void setInactiveInterval(int inactiveInterval) {
		this.inactiveInterval = inactiveInterval;
	}

	public int getUserMaxSessions() {
		return userMaxSessions;
	}

	public void setUserMaxSessions(int maxSessions) {
		this.userMaxSessions = maxSessions;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(category.getName());
		sb.append(":{max=").append(capacity).append(',');
		sb.append("maxSessions=").append(userMaxSessions).append(',');
		sb.append("inactiveInterval=").append(inactiveInterval).append('}');
		return sb.toString();
	}

}
