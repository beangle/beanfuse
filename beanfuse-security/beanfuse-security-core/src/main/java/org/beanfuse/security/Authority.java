//$Id: Authority.java,v 1.2 2006/10/19 09:21:32 chaostone Exp $
/*
 *
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone            2005-9-26           Created
 *  
 ********************************************************************************/

package org.beanfuse.security;

import org.beanfuse.model.LongIdEntity;

/**
 * 权限
 * 
 * @author chaostone 2005-9-26
 */
public interface Authority extends LongIdEntity, Cloneable {
	/**
	 * 系统资源
	 * 
	 * @return
	 */
	public Resource getResource();

	/**
	 * 设置资源
	 * 
	 * @param resource
	 */
	public void setResource(Resource resource);

	/**
	 * 设置授权对象
	 * 
	 * @param group
	 */
	public void setGroup(Group group);

	/**
	 * 获得授权对象
	 * 
	 * @param ao
	 */
	public Group getGroup();

	public void merge(Authority other);

	public Object clone();
}
