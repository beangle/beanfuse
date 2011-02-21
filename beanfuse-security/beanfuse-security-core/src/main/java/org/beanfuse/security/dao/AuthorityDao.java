//$Id: AuthorityDao.java,v 1.3 2007/01/13 07:06:51 chaostone Exp $
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
 * chaostone             2005-9-27         Created
 *  
 ********************************************************************************/

package org.beanfuse.security.dao;

import java.util.List;
import java.util.Set;

import org.beanfuse.security.Authority;
import org.beanfuse.security.Group;
import org.beanfuse.security.Resource;

/**
 * 权限数据存取接口
 * 
 * @author chaostone 2005-9-27
 */
public interface AuthorityDao {

	/**
	 * 依据模块id和用户组id取得对应的权限
	 * 
	 * @param userId
	 * @param resource
	 * @return
	 */
	public Authority getAuthority(Group group, Resource resource);

	/**
	 * 指定深度和父模块id获取用户组的权限
	 * 
	 * @param group
	 * @param depth
	 * @param ancestorCode
	 * @return
	 */
	public List getAuthorities(Group group);

	/**
	 * 获取用户组的资源集合
	 * 
	 * @param group
	 * @return
	 */
	public List getResources(Group group);

	/**
	 * 获取用户组的资源id集合
	 * 
	 * @param group
	 * @return
	 */
	public Set getResourceIds(Group group);

	/**
	 * 保存或更新权限设置
	 * 
	 * @param authority
	 */
	public void saveOrUpdate(Authority authority);

	/**
	 * 删除权限的信息设置
	 * 
	 * @param authority
	 */
	public void remove(Authority authority);

}
