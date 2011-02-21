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
package org.beanfuse.security.menu;

import java.util.List;

import org.beanfuse.model.LongIdEntity;
import org.beanfuse.security.UserCategory;

public interface MenuProfile extends LongIdEntity {

	public String getName();

	public void setName(String name);

	public List getMenus();

	public void setMenus(List menus);

	public UserCategory getCategory();

	public void setCategory(UserCategory category);
}
