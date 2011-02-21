//$Id: MenuProfile.java 2008-7-28 下午04:30:17 chaostone Exp $
/*
 *
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============   ============  ============
 * chaostone      2008-7-28  Created
 *  
 ********************************************************************************/
package org.beanfuse.security.menu.model;

import java.util.ArrayList;
import java.util.List;

import org.beanfuse.model.pojo.LongIdObject;
import org.beanfuse.security.UserCategory;

public class MenuProfile extends LongIdObject implements org.beanfuse.security.menu.MenuProfile {

	private static final long serialVersionUID = 9147563981118270960L;

	private String name;

	private List menus = new ArrayList(0);

	/** 对应的用户类别 */
	private UserCategory category;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List getMenus() {
		return menus;
	}

	public void setMenus(List menus) {
		this.menus = menus;
	}

	public UserCategory getCategory() {
		return category;
	}

	public void setCategory(UserCategory category) {
		this.category = category;
	}
}
