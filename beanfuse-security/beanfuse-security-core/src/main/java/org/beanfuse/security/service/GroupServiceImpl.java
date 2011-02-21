//$Id: GroupServiceImpl.java,v 1.1 2006/10/12 14:40:22 chaostone Exp $
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
 * dell                                     Created
 * chaostone             2005-9-26          refactor
 *  
 ********************************************************************************/

package org.beanfuse.security.service;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import org.beanfuse.model.EntityExistException;
import org.beanfuse.persist.impl.BaseServiceImpl;
import org.beanfuse.security.Group;
import org.beanfuse.security.dao.GroupDao;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 * 用户组信息服务的实现类
 * 
 * @author dell, chaostone 2005-9-26
 */
public class GroupServiceImpl extends BaseServiceImpl implements GroupService {

	private GroupDao groupDao;

	public Group get(Long groupId) {
		try {
			return groupDao.get(groupId);
		} catch (ObjectRetrievalFailureException e) {
			return null;
		}
	}

	public List get(Long[] groupIds) {
		if (null == groupIds || groupIds.length < 1)
			return Collections.EMPTY_LIST;
		else
			return groupDao.get(groupIds);
	}

	public void saveOrUpdate(Group group) throws EntityExistException {
		group.setUpdatedAt(new Date(System.currentTimeMillis()));
		if (group.isVO()) {
			group.setCreatedAt(new Date(System.currentTimeMillis()));
		}
		groupDao.saveOrUpdate(group);
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

}
