//$Id: GroupService.java,v 1.1 2006/10/12 14:40:16 chaostone Exp $
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
 * dell                 2005-5-13           Created
 * chaostone            2005-9-26           modified
 *  
 ********************************************************************************/

package org.beanfuse.security.service;

import java.util.List;

import org.beanfuse.model.EntityExistException;
import org.beanfuse.security.Group;
import org.beanfuse.security.dao.GroupDao;

/**
 * 用户组信息服务接口
 * 
 * @author dell,chaostone 2005-9-26
 */
public interface GroupService {

	/**
	 * 查找指定id的用户组信息，查找不到返回null
	 * 
	 * @param groupId
	 * @return
	 */
	public Group get(Long groupId);

	/**
	 * 返回用户组id数组中的用户组,若数组为空,直接返回,没有对应id的用户组将被忽略.
	 * 
	 * @param groupIds
	 * @return
	 */
	public List get(Long groupIds[]);

	/**
	 * 保存新建的group信息，group存在时抛出异常
	 * 
	 * @param groupForm
	 * @param userId
	 */
	public void saveOrUpdate(Group group) throws EntityExistException;

	/**
	 * 设置用户组信息存取对象
	 * 
	 * @param groupDao
	 */
	public void setGroupDao(GroupDao groupDao);
}
