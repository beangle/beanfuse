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

package org.beanfuse.security.menu.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.beanfuse.security.Group;
import org.beanfuse.security.User;
import org.beanfuse.security.menu.Menu;
import org.beanfuse.security.menu.MenuAuthority;
import org.beanfuse.security.menu.MenuProfile;
import org.beanfuse.security.menu.dao.MenuAuthorityDao;
import org.beanfuse.security.service.UserService;

/**
 * 用户用户组权限管理服务接口. 权限实体@see <code>Authority</code> 系统资源实体@see
 * <code>Resource</code> 系统功能点实体@see <code>Action</code> 数据权限实体@see
 * <code>DataRealm</code>
 * 
 * @author dell,chaostone 2005-9-27
 */
public interface MenuAuthorityService {

	/**
	 * 用户的所有权限
	 * 
	 * @param userId
	 * @return
	 */
	public List getMenuAuthorities(MenuProfile profile, User user);

	/**
	 * 查询用户指定深度和祖先代码的权限
	 * 
	 * @param userId
	 * @param depth
	 * @param ancestorCode
	 * @return
	 */
	public List getMenuAuthorities(MenuProfile profile, User user, int depth, String ancestorCode);

	/**
	 * 依据资源id和用户组id取得对应的权限
	 * 
	 * @param userId
	 * @param menuId
	 * 
	 * @return
	 */
	public MenuAuthority getMenuAuthority(Group group, Menu menu);

	/**
	 * 依据（小于或等于）深度和父系资源得到用户组拥有的权限
	 * 
	 * @param groupId
	 * @param depth
	 * @param ancestorCode
	 * @return
	 */
	public List getMenuAuthorities(MenuProfile profile, Group group, int depth, String ancestorCode);

	/**
	 * 依据（小于或等于）深度得到用户组拥有的权限
	 * 
	 * @param groupId
	 * @param depth
	 * @return
	 */
	public List getMenuAuthorities(MenuProfile profile, Group group, int depth);

	/**
	 * 依据默认深度得到用户组拥有的权限
	 * 
	 * @param groupId
	 * @return
	 */
	public List getMenuAuthorities(MenuProfile profile, Group group);

	/**
	 * 获取用户的直接权限范围内的资源和所具有用户组的资源.
	 * 
	 * @param userId
	 * @return
	 */
	public List getMenus(MenuProfile profile, User user);

	/**
	 * 返回小于或等于深度、父系资源id的用户权限范围内的所有子资源
	 * 
	 * @param userId
	 * @param depth
	 * @param ancestorCode
	 * @return
	 */
	public List getMenus(MenuProfile profile, User user, int depth, String ancestorCode);

	/**
	 * 用户组内对应的资源
	 * 
	 * @param groupId
	 * @return
	 */
	public List getMenus(MenuProfile profile, Group group);

	/**
	 * 保存新的权限设置
	 * 
	 * @param authority
	 */
	public void saveOrUpdate(MenuAuthority authority);

	/**
	 * 授权
	 * 
	 * @param resources
	 * @param ao
	 */
	public void authorize(Group group, Set resources);

	/**
	 * 拷贝权限
	 * 
	 * @param fromGroup
	 * @param toGroups
	 */
	public void copyAuthority(MenuProfile profile, Group fromGroup, Collection toGroups);

	public void setMenuAuthorityDao(MenuAuthorityDao authorityDao);

	public void setUserService(UserService userService);

}
