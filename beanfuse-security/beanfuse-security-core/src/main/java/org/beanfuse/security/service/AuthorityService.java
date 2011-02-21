//$Id: AuthorityService.java,v 1.5 2007/01/13 07:06:51 chaostone Exp $
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
 * chaostone             2005-9-26          rename
 *  
 ********************************************************************************/

package org.beanfuse.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.beanfuse.security.Authority;
import org.beanfuse.security.Group;
import org.beanfuse.security.Resource;
import org.beanfuse.security.User;
import org.beanfuse.security.dao.AuthorityDao;

/**
 * 用户用户组权限管理服务接口. 权限实体@see <code>Authority</code> 系统资源实体@see
 * <code>Resource</code> 系统功能点实体@see <code>Action</code> 数据权限实体@see
 * <code>Restriction</code>
 * 
 * @author dell,chaostone 2005-9-27
 */
public interface AuthorityService {

	/**
	 * 按照资源名称查询单独的资源
	 * @param name
	 * @return
	 */
	public Resource getResource(String name);

	/**
	 * 查询用户的访问资源范围
	 * @param user
	 * @return
	 */
	public List getResources(User user);

	/**
	 * 用户组内对应的资源
	 * 
	 * @param group
	 * @return
	 */
	public List getResources(Group group);
	
	/**
	 * 用户组内对应的资源ID
	 * 
	 * @param group
	 * @return
	 */
	public Set getResourceIds(Group group);

	/**
	 * 依据默认深度（小于或等于）得到用户的所有权限
	 * 
	 * @param user
	 * @return
	 */
	public List getAuthorities(User user);

	/**
	 * 依据默认深度得到用户组拥有的权限
	 * 
	 * @param group
	 * @return
	 */
	public List getAuthorities(Group group);

	/**
	 * 依据资源和用户取得对应的权限
	 * @param user
	 * @param resource
	 * @return
	 */
	public Authority getAuthority(User user, Resource resource);

	/**
	 * 依据资源和用户组取得对应的权限
	 * 
	 * @param userId
	 * @param resource
	 * @return
	 */
	public Authority getAuthority(Group group, Resource resource);


	/**
	 * 保存新的权限设置
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

	/**
	 * 授权
	 * 
	 * @param ao
	 * @param resources
	 * @param authorityClass
	 */
	public void authorize(Group group, Set resources);

	/**
	 * 拷贝权限
	 * 
	 * @param fromGroup
	 * @param toGroups
	 */
	public void copyAuthority(Group fromGroup, Collection toGroups);

	public void setAuthorityDao(AuthorityDao authorityDao);

	public void setUserService(UserService userService);

}
