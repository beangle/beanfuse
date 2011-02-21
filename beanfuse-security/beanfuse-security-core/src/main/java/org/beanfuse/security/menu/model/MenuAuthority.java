//$Id: MenuItem.java 2008-7-28 下午04:29:29 chaostone Exp $
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

import org.beanfuse.model.pojo.LongIdObject;
import org.beanfuse.security.Group;
import org.beanfuse.security.menu.Menu;

public class MenuAuthority extends LongIdObject implements org.beanfuse.security.menu.MenuAuthority {

	private static final long serialVersionUID = 4018685623671974644L;

	private Menu menu;

	private Group group;
	
	
	public MenuAuthority() {
		super();
	}

	public MenuAuthority(Long id) {
		super(id);
	}

	public MenuAuthority(Group group, Menu menu) {
		super();
		this.group = group;
		this.menu = menu;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
