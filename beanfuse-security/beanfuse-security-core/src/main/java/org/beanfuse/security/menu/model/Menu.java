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
package org.beanfuse.security.menu.model;

import java.util.HashSet;
import java.util.Set;

import org.beanfuse.model.pojo.LongIdObject;
import org.beanfuse.security.menu.MenuProfile;

public class Menu extends LongIdObject implements org.beanfuse.security.menu.Menu {

	private static final long serialVersionUID = 3864556621041443066L;

	private String code;

	private String title;

	private String engTitle;

	private String entry;

	private String description;

	private Set resources = new HashSet(0);

	private boolean enabled = true;

	private MenuProfile profile;

	public boolean isLeaf() {
		return null != entry;
	}

	/**
	 * 不同级的菜单按照他们固有的级联顺序排序.
	 * 
	 * @see java.lang.Comparable#compareTo(Object)
	 * 
	 */
	public int compareTo(Object object) {
		Menu other = (Menu) object;
		return getCode().compareTo(other.getCode());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEngTitle() {
		return engTitle;
	}

	public void setEngTitle(String engTitle) {
		this.engTitle = engTitle;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set getResources() {
		return resources;
	}

	public void setResources(Set resources) {
		this.resources = resources;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public MenuProfile getProfile() {
		return profile;
	}

	public void setProfile(MenuProfile profile) {
		this.profile = profile;
	}

}
