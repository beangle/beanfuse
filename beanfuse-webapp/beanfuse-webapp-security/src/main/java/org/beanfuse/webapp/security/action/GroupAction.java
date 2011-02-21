//$Id: GroupAction.java,v 1.8 2006/12/30 01:29:02 chaostone Exp $
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
 * chaostone            2005-09-29          refactor
 * zq                   2007-09-18          修改或替换了下面所有的info()方法
 *  
 ********************************************************************************/

package org.beanfuse.webapp.security.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.beanfuse.collection.Order;
import org.beanfuse.lang.SeqStringUtil;
import org.beanfuse.model.Entity;
import org.beanfuse.query.Condition;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.Group;
import org.beanfuse.security.User;
import org.beanfuse.security.UserCategory;
import org.beanfuse.security.model.GroupPropertyExtractor;
import org.beanfuse.security.service.AuthorityService;
import org.beanfuse.security.service.GroupService;
import org.beanfuse.security.service.UserService;
import org.beanfuse.transfer.exporter.PropertyExtractor;

/**
 * 用户组信息维护响应类
 * 
 * @author chaostone 2005-9-29
 */
public class GroupAction extends SecurityAction {

	private GroupService groupService;

	private UserService userService;

	protected void indexSetting() {
		put("categories", entityService.loadAll(UserCategory.class));
	}

	protected void editSetting(Entity entity) {
		put("categories", entityService.loadAll(UserCategory.class));
	}

	protected EntityQuery buildQuery() {
		User manager = getUser();
		EntityQuery entityQuery = new EntityQuery(entityName, "userGroup");
		if (!manager.isAdmin()) {
			entityQuery.join("userGroup.managers", "manager");
			entityQuery.add(new Condition("manager.id=:managerId", manager.getId()));
		}
		populateConditions(entityQuery);
		entityQuery.setLimit(getPageLimit());
		entityQuery.addOrder(Order.parse(get("orderBy")));
		return entityQuery;
	}

	protected PropertyExtractor getPropertyExtractor(HttpServletRequest request) {
		return new GroupPropertyExtractor();
	}

	protected String saveAndForward(Entity entity) {
		Group group = (Group) entity;
		if (null != group) {
			List list = this.entityService.load(Group.class, "name", group.getName());
			int isUniqueFlag = 0;
			if (null != group.getId()) {
				isUniqueFlag = 1;
			}
			if (null != list && list.size() > isUniqueFlag) {
				return redirect("edit", "error.notUnique");
			}
		}

		if (null == group.getId()) {
			User creator = userService.get(getUserId());
			userService.createGroup(creator, group);
		} else {
			groupService.saveOrUpdate(group);
		}
		return redirect("search", "info.save.success");
	}

	/**
	 * 删除一个或多个用户组
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String remove() {
		String groupIdSeq = get("groupIds");
		User curUser = userService.get(getUserId());
		List toBeRemoved = groupService.get(SeqStringUtil.transformToLong(groupIdSeq));
		userService.removeGroup(curUser, toBeRemoved);
		return redirect("search", "info.delete.success");
	}

	/**
	 * 设置拷贝权限的起始用户组和目标用户组
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String copyAuthSetting() {
		Long fromGroupId = getLong("groupId");
		Group fromGroup = groupService.get(fromGroupId);
		put("fromGroup", fromGroup);
		put("toGroups", getUser().getMngGroups());
		return forward();
	}

	/**
	 * 拷贝权限
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String copyAuth() {
		Long fromGroupId = getLong("fromGroupId");
		Long[] toGroupIds = SeqStringUtil.transformToLong(get("toGroupIds"));
		Group fromGroup = groupService.get(fromGroupId);
		List toGroups = groupService.get(toGroupIds);
		authorityService.copyAuthority(fromGroup, toGroups);
		return redirect("search", "info.set.success");
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
