// $Id: Module.java,v 1.2 2006/11/28 03:22:36 chaostone Exp $
/*
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 */
/*******************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name          Date         Description
 *  ============ ============ ============
 * dell          2005-8-23    Created
 * chaostone     2005-9-26    modified/rename
 * 
 ******************************************************************************/
package org.beanfuse.security.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.beanfuse.model.pojo.LongIdObject;

/**
 * 
 * 系统模块，代表一组系统功能点的集合.<br>
 * <p>
 * 系统模块之间存在基于代码表示上的父子级联关系.<br>
 * 上下级关系是通过模块代码的包含关系体现的。<br>
 * 系统模块作为权限分配的基本单位.
 * <p>
 * 
 * 
 * @author dell,chaostone 2005-9-26
 * 
 */
public class Resource extends LongIdObject implements org.beanfuse.security.Resource {
	private static final long serialVersionUID = -8285208615351119572L;

	/** 模块名字 */
	private String name;

	/** 模块标题 */
	private String title;

	/** 简单描述 */
	private String description;

	/** 模块是否可用 */
	private boolean enabled;

	/**资源访问范围*/
	private int scope;
	
	/** 资源限制模型 */
	private Set patterns = new HashSet();

	private Set categories = new HashSet();

	public String toString() {
		return new ToStringBuilder(this).append("name", this.name).append("id", this.id).append(
				"description", this.description).toString();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set getPatterns() {
		return patterns;
	}

	public void setPatterns(Set patterns) {
		this.patterns = patterns;
	}

	public Set getCategories() {
		return categories;
	}

	public void setCategories(Set categories) {
		this.categories = categories;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

}