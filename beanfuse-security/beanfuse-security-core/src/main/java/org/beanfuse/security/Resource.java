// $Id: Resource.java,v 1.2 2008/08/28 03:22:36 chaostone Exp $
/*
 * 
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 */
/*******************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date            Description 
 * ============ ============ ============ 
 * chaostone     2008-7-28   created
 * 
 ******************************************************************************/
package org.beanfuse.security;

import java.util.Set;

import org.beanfuse.model.LongIdEntity;

/**
 * 
 * 系统资源.<br>
 * 
 * @author chaostone 2008-7-28
 * 
 */
public interface Resource extends LongIdEntity {
	
	/** 不受保护的公共资源 */
	public static final int PUBLIC = 0;

	/** 受保护的公有资源 */
	public static final int PROTECTED = 1;

	/** 受保护的私有资源 */
	public static final int PRIVATE = 2;

	/**
	 * 资源名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 资源名称
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * 资源标题
	 * 
	 * @return
	 */
	public String getTitle();

	/**
	 * 资源标题
	 * 
	 * @param title
	 */
	public void setTitle(String title);

	/**
	 * 返回菜单描述
	 * 
	 * @return
	 */
	public String getDescription();

	/**
	 * 资源状态
	 * 
	 * @return
	 */
	public boolean isEnabled();

	/**
	 * 设置资源状态
	 * 
	 * @param IsActive
	 * @return
	 */
	public void setEnabled(boolean isEnabled);

	/**
	 * 适用的用户类别
	 * 
	 * @return
	 */
	public Set getCategories();

	/**
	 * 查询适用的用户类别
	 * 
	 * @param categories
	 */
	public void setCategories(Set categories);

	/**
	 * 限制模式
	 * 
	 * @return
	 */
	public Set getPatterns();

	/**
	 * 设置限制模式
	 * 
	 * @param patterns
	 */
	public void setPatterns(Set patterns);

	/**
	 * 资源访问范围
	 * 
	 * @return
	 */
	public int getScope();

	/**
	 * 设置资源访问范围
	 * 
	 * @param scope
	 */
	public void setScope(int scope);
}