package org.beanfuse.security.concurrent.category;

import org.beanfuse.security.online.CategoryProfile;

public class OnlineProfile {

	private static final long serialVersionUID = 1999239598984221565L;

	private CategoryProfile categoryProfile;

	private transient int online;

	public OnlineProfile() {
		super();
	}

	public OnlineProfile(CategoryProfile profile) {
		super();
		this.categoryProfile = profile;
	}

	synchronized public boolean hasCapacity() {
		return online < categoryProfile.getCapacity();
	}

	public boolean isFull() {
		return !hasCapacity();
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	synchronized public void increase() {
		online++;
	}

	synchronized public void decrease() {
		online--;
	}

	public CategoryProfile getCategoryProfile() {
		return categoryProfile;
	}

	public void setCategoryProfile(CategoryProfile categoryProfile) {
		this.categoryProfile = categoryProfile;
	}

}
