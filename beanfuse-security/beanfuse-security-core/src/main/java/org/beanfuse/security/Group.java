//$Id: Group.java,v 1.1 2006/10/12 14:40:10 chaostone Exp $
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
 * chaostone            2009/6/1            Created
 *  
 ********************************************************************************/

package org.beanfuse.security;

import java.util.Set;

import org.beanfuse.model.LongIdTimeEntity;
import org.beanfuse.security.restriction.RestrictionHolder;

/**
 * 系统用户组的基本信息
 * 
 * @author chaostone 2005-9-26
 */
public interface Group extends LongIdTimeEntity, RestrictionHolder {

	/**
	 * 名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 设置名称
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * 上级组
	 * 
	 * @return
	 */
	public Group getParent();

	/**
	 * 设置上级组
	 * 
	 * @param parent
	 */
	public void setParent(Group parent);

	/**
	 * 下级组
	 * 
	 * @return
	 */
	public Set getChildren();

	/**
	 * 设置下级组
	 * 
	 * @param children
	 */
	public void setChildren(Set children);

	/**
	 * 查询权限
	 * 
	 * @return
	 */
	public Set getAuthorities();

	/**
	 * 设置权限
	 * 
	 * @param authorities
	 */
	public void setAuthorities(Set authorities);

	/**
	 * 菜单权限
	 * 
	 * @return
	 */
	public Set getMenuAuthorities();

	/**
	 * 设置菜单权限
	 * 
	 * @param menuAuthorities
	 */
	public void setMenuAuthorities(Set menuAuthorities);

	/**
	 * 关联的系统用户
	 * 
	 * @return
	 */
	public Set getUsers();

	/**
	 * 关联的系统用户
	 * 
	 * @param users
	 */
	public void setUsers(Set users);

	/**
	 * 管理者
	 * 
	 * @return
	 */
	public Set getManagers();

	/**
	 * 管理者
	 * 
	 * @param managers
	 */
	public void setManagers(Set managers);

	/**
	 * 创建者
	 * 
	 * @return
	 */
	public User getCreator();

	/**
	 * 设置创建者
	 * 
	 * @param creator
	 */
	public void setCreator(User creator);

	/**
	 * 用户组对应的类别.
	 * 
	 * @return
	 */
	public UserCategory getCategory();

	/**
	 * 设置用户组对应的类别.
	 * 
	 * @param categories
	 */
	public void setCategory(UserCategory userCategory);

	/**
	 * 状态
	 * 
	 * @return
	 */
	public boolean isEnabled();

	/**
	 * 设置状态
	 * 
	 * @param isEnabled
	 */
	public void setEnabled(boolean isEnabled);

	/**
	 * 备注
	 * 
	 * @return
	 */
	public String getRemark();

	/**
	 * 设置备注
	 * 
	 * @param remark
	 */
	public void setRemark(String remark);

}
