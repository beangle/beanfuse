package org.beanfuse.struts2.route;

public interface ProfileService {

	public Profile getProfile(String className);

	public Profile getProfile(Class clazz);
}
