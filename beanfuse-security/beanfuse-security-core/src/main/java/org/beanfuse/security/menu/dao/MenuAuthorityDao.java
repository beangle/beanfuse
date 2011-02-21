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

package org.beanfuse.security.menu.dao;

import java.util.List;

import org.beanfuse.security.Authority;
import org.beanfuse.security.Group;
import org.beanfuse.security.menu.Menu;
import org.beanfuse.security.menu.MenuAuthority;
import org.beanfuse.security.menu.MenuProfile;

/**
 * 权限数据存取接口
 * 
 * @author chaostone 2005-9-27
 */
public interface MenuAuthorityDao {
	/**
	 * 依据模块id和用户组id取得对应的权限
	 * 
	 * @param resourceId
	 * @param userId
	 * @return
	 */
	public MenuAuthority getMenuAuthority(Group group, Menu menu);

	/**
	 * 指定深度和父模块id获取用户组的权限
	 * 
	 * @param profile
	 *            TODO
	 * @param groupId
	 * @param depth
	 * @param ancestorCode
	 * 
	 * @return
	 */
	public List getMenuAuthorities(MenuProfile profile, Group group, int depth, String ancestorCode);

	/**
	 * 指定深度和父模块id获取用户组的模块
	 * 
	 * @param profile
	 *            TODO
	 * @param groupId
	 * @param depth
	 * @param ancestorCode
	 * 
	 * @return
	 */
	public List getMenus(MenuProfile profile, Group group, int depth, String ancestorCode);

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
