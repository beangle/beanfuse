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
 * dell                 2005-9-15           Created
 * chaostone            2005-9-26           add mngUsers
 *  
 ********************************************************************************/

package org.beanfuse.security.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.beanfuse.model.pojo.LongIdTimeObject;
import org.beanfuse.security.UserCategory;
import org.beanfuse.security.restriction.RestrictionHolder;

/**
 * 系统中所有用户的账号、权限、状态信息.
 * 
 * @author dell,chaostone 2005-9-26
 */
public class User extends LongIdTimeObject implements org.beanfuse.security.User, RestrictionHolder {
	private static final long serialVersionUID = -3625902334772342380L;

	/** 名称 */
	protected String name;

	/** 用户姓名 */
	private String fullname;

	/** 用户密文 */
	private String password;

	/** 用户联系email */
	private String mail;

	/** 是否管理员 */
	private boolean admin;

	/** 对应用户组 */
	private Set groups = new HashSet();

	/** 创建人 */
	private org.beanfuse.security.User creator;

	/** 向下级授权,所管理的用户组 */
	private Set mngGroups = new HashSet();

	/** 向下级授权,所管理的用户 */
	private Set mngUsers = new HashSet();

	/** 种类 */
	protected Set categories = new HashSet();

	/** 缺省类别 */
	private UserCategory defaultCategory;

	/** 状态 */
	protected int status;

	/** 访问限制 */
	protected Set restrictions = new HashSet();

	/** 备注 */
	protected String remark;

	public User() {
		super();
	}

	public User(Long id) {
		setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(Set restrictions) {
		this.restrictions = restrictions;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("password", this.password)
				.append("name", this.getName()).toString();
	}

	public boolean isCategory(Long categoryId) {
		for (Iterator iter = categories.iterator(); iter.hasNext();) {
			UserCategory category = (UserCategory) iter.next();
			if (category.getId().equals(categoryId))
				return true;
		}
		return false;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Set getMngGroups() {
		return mngGroups;
	}

	public void setMngGroups(Set mngGroups) {
		this.mngGroups = mngGroups;
	}

	public Set getMngUsers() {
		return mngUsers;
	}

	public void setMngUsers(Set mngUsers) {
		this.mngUsers = mngUsers;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set getGroups() {
		return groups;
	}

	public Set getAllGroups() {
		Set allGroups = new HashSet();
		for (Iterator iter = groups.iterator(); iter.hasNext();) {
			org.beanfuse.security.Group group = (Group) iter.next();
			while (null != group) {
				allGroups.add(group);
				group = group.getParent();
			}
		}
		return allGroups;
	}

	public void setGroups(Set groups) {
		this.groups = groups;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public org.beanfuse.security.User getCreator() {
		return creator;
	}

	public void setCreator(org.beanfuse.security.User creator) {
		this.creator = creator;
	}

	public UserCategory getDefaultCategory() {
		return defaultCategory;
	}

	public void setDefaultCategory(UserCategory defaultCategory) {
		this.defaultCategory = defaultCategory;
	}

	public Set getCategories() {
		return categories;
	}

	public void setCategories(Set categories) {
		this.categories = categories;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * 1 激活
	 */
	public boolean isEnabled() {
		return status == 1;
	}

}
