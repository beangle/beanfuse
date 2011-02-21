package org.beanfuse.security;

import java.util.List;

public interface SecurityContext {

	public User getUser();

	public void setUser(User user);

	public Resource getResource();

	public void setResource(Resource resource);

	public List getRestrictions();

	public void setRestrictions(List restrictions);

}