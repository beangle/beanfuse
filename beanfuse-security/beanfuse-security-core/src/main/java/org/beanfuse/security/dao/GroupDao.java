//$Id: GroupDao.java,v 1.1 2006/10/12 14:39:51 chaostone Exp $
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
 * dell                 2005-9-7            Created
 * chaostone            2005-9-26           move,add
 *  
 ********************************************************************************/

package org.beanfuse.security.dao;

import java.util.List;

import org.beanfuse.security.Group;

/**
 * 用户组信息存取接口
 * 
 * @author dell,chaostone 2005-9-26
 */
public interface GroupDao {

	/**
	 * 查找指定id的用户组信息
	 * 
	 * @param groupId
	 * @return
	 */
	public Group get(Long groupId);

	/**
	 * 根据ids查询用户组
	 * 
	 * @param groupIds
	 * @return
	 */
	public List get(Long[] groupIds);

	/**
	 * 添加新建的用户组信息，重复时将抛出异常
	 * 
	 * @param group
	 */
	public void saveOrUpdate(Group group);

	/**
	 * 删除用户组
	 * 
	 * @param group
	 */
	public void remove(Group group);

	/**
	 * 通过主键删除用户组
	 * 
	 * @param groupId
	 */
	public void remove(Long groupId);

}
