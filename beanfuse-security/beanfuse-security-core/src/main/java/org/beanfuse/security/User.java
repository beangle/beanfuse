//$Id: User.java,v 1.7 2007/01/05 03:48:05 chaostone Exp $
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
 * chaostone            2005-9-26           create
 *  
 ********************************************************************************/

package org.beanfuse.security;

import java.util.Set;

import org.beanfuse.model.LongIdTimeEntity;

/**
 * 系统中所有用户的账号、权限、状态信息.
 * 
 * @author dell,chaostone 2005-9-26
 */
public interface User extends LongIdTimeEntity {

	// 新建用户的缺省密码
	public static final String DEFAULT_PASSWORD = "1";

	// 冻结
	public static final int FREEZE = 0;

	// 激活
	public static final int ACTIVE = 1;

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
	 * 用户真实姓名
	 * 
	 * @return
	 */
	public String getFullname();

	/**
	 * 设置用户真实姓名
	 * 
	 * @param fullname
	 */
	public void setFullname(String fullname);

	/**
	 * 用户密码(不限制是明码还是密文)
	 * 
	 * @return
	 */
	public String getPassword();

	/**
	 * 设置密码
	 * 
	 * @param password
	 */
	public void setPassword(String password);

	/**
	 * 用户邮件
	 * 
	 * @return
	 */
	public String getMail();

	/**
	 * 用户邮件
	 * 
	 * @return
	 */
	public void setMail(String mail);

	/**
	 * 对应用户组
	 * 
	 * @return
	 */
	public Set getGroups();

	/**
	 * 设置对应用户组
	 * 
	 * @param groups
	 */
	public void setGroups(Set groups);

	/**
	 * 查询对应用户组及其上级组
	 * 
	 * @return
	 */
	public Set getAllGroups();

	/**
	 * 状态
	 * 
	 * @return
	 */
	public int getStatus();

	/**
	 * 设置状态
	 * 
	 * @param status
	 */
	public void setStatus(int status);

	/**
	 * 类别.
	 * 
	 * @return
	 */
	public Set getCategories();

	/**
	 * 设置类别.
	 * 
	 * @param categories
	 */
	public void setCategories(Set HasSet);

	/**
	 * 缺省类别
	 * 
	 * @return
	 */
	public UserCategory getDefaultCategory();

	/**
	 * 设置缺省类别
	 * 
	 * @param userCategory
	 */
	public void setDefaultCategory(UserCategory userCategory);

	public boolean isCategory(Long categoryId);

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
	 * 该用户管理的用户组
	 * 
	 * @return
	 */
	public Set getMngGroups();

	/**
	 * 设置管理用户组
	 * 
	 * @param Groups
	 */
	public void setMngGroups(Set Groups);
	/**
	 * 是否启用
	 * 
	 * @return
	 */
	public boolean isEnabled();
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

	public boolean isAdmin();

	public void setAdmin(boolean admin);
}
