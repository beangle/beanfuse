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
 * dell                 2005-9-15           Created
 * chaostone            2005-9-26           refactor
 *  
 ********************************************************************************/

package org.beanfuse.security.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.beanfuse.model.pojo.LongIdTimeObject;
import org.beanfuse.security.User;
import org.beanfuse.security.UserCategory;

/**
 * 系统中用户组的基本信息和账号信息.
 * 
 * @author dell,chaostone 2005-9-26
 */
public class Group extends LongIdTimeObject implements org.beanfuse.security.Group {

	private static final long serialVersionUID = -3404181949500894284L;

	/** 名称 */
	private String name;

	/** 上级组 */
	private org.beanfuse.security.Group parent;

	/** 关联的用户 */
	private Set users = new HashSet();

	/** 对应的用户类别 */
	private UserCategory category;

	/** 访问限制 */
	protected Set restrictions = new HashSet();

	/** 权限 */
	protected Set authorities = new HashSet();

	/** 菜单权限 */
	private Set menuAuthorities = new HashSet();

	/** 下级组 */
	private Set children = new HashSet();

	/** 管理者 */
	protected Set managers = new HashSet();

	/** 创建人 */
	private User creator;
	
	/** 备注 */
	protected String remark;

	/** 是否启用 */
	public boolean enabled;
	
	public Group() {
		super();
	}

	public Group(Long id) {
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

	public Set getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set authorities) {
		this.authorities = authorities;
	}

	public Set getMenuAuthorities() {
		return menuAuthorities;
	}

	public void setMenuAuthorities(Set menuAuthorities) {
		this.menuAuthorities = menuAuthorities;
	}

	public Set getManagers() {
		return managers;
	}

	public void setManagers(Set managers) {
		this.managers = managers;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set getUsers() {
		return users;
	}

	public void setUsers(Set users) {
		this.users = users;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public UserCategory getCategory() {
		return category;
	}

	public void setCategory(UserCategory userCategory) {
		this.category = userCategory;
	}

	public org.beanfuse.security.Group getParent() {
		return parent;
	}

	public void setParent(org.beanfuse.security.Group parent) {
		this.parent = parent;
	}

	public Set getChildren() {
		return children;
	}

	public void setChildren(Set children) {
		this.children = children;
	}

	public String toString() {
		return new ToStringBuilder(this).append("name", this.getName()).append("id", this.id)
				.toString();
	}

}
