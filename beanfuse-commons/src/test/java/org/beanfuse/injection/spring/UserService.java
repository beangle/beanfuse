package org.beanfuse.injection.spring;

import java.util.List;
import java.util.Map;

public class UserService {

	private UserProvider provider;

	private Map someMap;
	
	private List someList;
	
	public UserProvider getProvider() {
		return provider;
	}

	public void setProvider(UserProvider provider) {
		this.provider = provider;
	}

	public Map getSomeMap() {
		return someMap;
	}

	public void setSomeMap(Map someMap) {
		this.someMap = someMap;
	}

	public List getSomeList() {
		return someList;
	}

	public void setSomeList(List someList) {
		this.someList = someList;
	}
	
}
