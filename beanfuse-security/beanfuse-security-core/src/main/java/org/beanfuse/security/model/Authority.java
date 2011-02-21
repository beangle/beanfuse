//$Id: Authority.java,v 1.2 2006/10/19 09:21:32 chaostone Exp $
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
 * chaostone            2005-9-26           rename/refactor
 *  
 ********************************************************************************/

package org.beanfuse.security.model;

import java.util.Set;

import org.beanfuse.model.pojo.LongIdObject;
import org.beanfuse.security.Group;
import org.beanfuse.security.Resource;
import org.beanfuse.security.restriction.RestrictionHolder;

/**
 * 权限实体，模块及其操作的数据范围规定
 * 
 * @author dell,chaostone 2005-9-26
 */
public class Authority extends LongIdObject implements RestrictionHolder,
		org.beanfuse.security.Authority {

	private static final long serialVersionUID = -8956079356245507990L;

	/** 用户组 */
	protected Group group;

	/** 权限实体中的模块 */
	protected Resource resource;

	/** 该模块对应的数据操作范围 */
	protected Set restrictions;

	public Authority() {
		super();
	}

	public Authority(Long id) {
		super(id);
	}

	public Authority(Group group, Resource resource) {
		super();
		this.group = group;
		this.resource = resource;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = (Resource) resource;
	}

	public Set getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(Set restrictions) {
		this.restrictions = restrictions;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Object clone() {
		Authority groupAuthority = new Authority();
		groupAuthority.setResource(resource);
		groupAuthority.setGroup(group);
		return groupAuthority;
	}

	public void merge(org.beanfuse.security.Authority other) {
		// TODO Auto-generated method stub
	}
}
