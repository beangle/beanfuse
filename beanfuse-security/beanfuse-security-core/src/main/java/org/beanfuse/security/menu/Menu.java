//$Id: Menu.java 2008-7-28 下午03:36:07 chaostone Exp $
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

import java.util.Set;

import org.beanfuse.model.LongIdEntity;

public interface Menu extends LongIdEntity, Comparable {

	public boolean isLeaf();

	public String getCode();

	public void setCode(String code);

	public String getTitle();

	public void setTitle(String title);

	public String getEngTitle();

	public void setEngTitle(String engTitle);

	public String getEntry();

	public void setEntry(String entry);

	public String getDescription();

	public void setDescription(String description);

	public Set getResources();

	public void setResources(Set resources);

	public boolean isEnabled();

	public void setEnabled(boolean enabled);

	public MenuProfile getProfile();

	public void setProfile(MenuProfile profile);
}
