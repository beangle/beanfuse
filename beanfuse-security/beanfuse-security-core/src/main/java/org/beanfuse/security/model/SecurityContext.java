package org.beanfuse.security.model;

import java.util.ArrayList;
import java.util.List;

import org.beanfuse.security.Resource;
import org.beanfuse.security.User;

public class SecurityContext implements org.beanfuse.security.SecurityContext {

	User user;

	Resource resource;

	List restrictions = new ArrayList(0);

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public List getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List restrictions) {
		this.restrictions = restrictions;
	}

}
