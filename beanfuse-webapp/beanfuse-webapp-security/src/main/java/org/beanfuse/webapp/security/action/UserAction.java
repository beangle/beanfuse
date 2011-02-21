//$Id: UserAction.java,v 1.19 2007/01/24 01:11:32 chaostone Exp $
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.struts2.route.Action;
import org.beanfuse.collection.Order;
import org.beanfuse.lang.SeqStringUtil;
import org.beanfuse.model.Entity;
import org.beanfuse.query.Condition;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.security.User;
import org.beanfuse.security.Group;
import org.beanfuse.security.UserCategory;
import org.beanfuse.security.codec.EncryptUtil;
import org.beanfuse.security.model.UserPropertyExtractor;
import org.beanfuse.security.service.GroupService;
import org.beanfuse.security.service.UserService;
import org.beanfuse.transfer.exporter.PropertyExtractor;

/**
 * 用户管理响应处理类
 * 
 * @author chaostone 2005-9-29
 */
public class UserAction extends SecurityAction {

	private UserService userService;

	private GroupService groupService;

	protected void indexSetting() {
		put("categories", entityService.loadAll(UserCategory.class));
	}

	protected EntityQuery buildQuery() {
		User manager = getUser();
		EntityQuery entityQuery = new EntityQuery(entityName, "user");
		// 查询用户组
		StringBuilder sb = new StringBuilder("exists(from user.groups ug where ");
		List params =new ArrayList();
		if (!manager.isAdmin()) {
			sb.append("ug in(:groups) ");
			params.add(manager.getGroups());
		}
		String groupName = get("groupName");
		if (StringUtils.isNotEmpty(groupName)) {
			if (params.size() > 0) {
				sb.append(" and ");
			}
			sb.append("ug.name like :groupName ");
			params.add("%" + groupName + "%");
		}
		if (!params.isEmpty()) {
			sb.append(')');
			Condition groupCondition = new Condition(sb.toString());
			groupCondition.setValues(params);
			entityQuery.add(groupCondition);
		}
		Long categoryId = getLong("categoryId");
		if (null != categoryId) {
			entityQuery.join("user.categories", "category");
			entityQuery.add(new Condition("category.id=:categoryId", categoryId));
		}
		populateConditions(entityQuery);
		entityQuery.setLimit(getPageLimit());
		entityQuery.addOrder(Order.parse(get(Order.ORDER_STR)));
		return entityQuery;
	}

	protected PropertyExtractor getPropertyExtractor() {
		return new UserPropertyExtractor();
	}

	/**
	 * 保存用户信息
	 */
	protected String saveAndForward(Entity entity) {
		User user = (User) entity;
		String errorMsg = "";
		user.getCategories().clear();
		// // 收集用户身份
		String[] categories = StringUtils.split(get("categoryIds"), ",");
		for (int i = 0; i < categories.length; i++) {
			errorMsg = checkCategory(user, Long.valueOf(categories[i]));
			if (StringUtils.isNotEmpty(errorMsg)) {
				return forward(new Action("edit"), errorMsg);
			}
		}
		// 检验用户合法性
		errorMsg = checkUser(user);
		if (StringUtils.isNotEmpty(errorMsg)) {
			return forward(new Action("edit"), errorMsg);
		}
		try {
			String groupIdSeq = get("groupIds");
			String mngGroupIdSeq = get("mngGroupIds");
			List groups = groupService.get(SeqStringUtil.transformToLong(groupIdSeq));
			List mngGroups = groupService.get(SeqStringUtil.transformToLong(mngGroupIdSeq));
			user.getGroups().clear();
			user.getGroups().addAll(groups);
			user.getMngGroups().clear();
			user.getMngGroups().addAll(mngGroups);
			if (user.isVO()) {
				User creator = userService.get(getUserId());
				user.setPassword(getDefaultPassword(user));
				userService.createUser(creator, user);
			} else {
				userService.saveOrUpdate(user);
			}
		} catch (Exception e) {
			// FIXME
			return forward(ERROR);
		}
		return redirect("search", "info.save.success");
	}

	protected void editSetting(Entity entity) {
		User user = (User) entity;
		User manager = getUser();
		Collection allGroups=manager.getMngGroups();
		if(manager.isAdmin()){
			allGroups=entityService.loadAll(Group.class);
		}
		Set userGroups = new HashSet(allGroups);
		userGroups.removeAll(user.getGroups());
		Set mngGroups = new HashSet(allGroups);
		mngGroups.removeAll(user.getMngGroups());
		put("userGroups", userGroups);
		put("mngGroups", mngGroups);
		put("categories", entityService.loadAll(UserCategory.class));
	}

	/**
	 * 删除一个或多个用户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String remove() {
		String userIdSeq = get("userIds");
		Long[] userIds = SeqStringUtil.transformToLong(userIdSeq);
		User creator = userService.get(getUserId());
		List toBeRemoved = userService.getUsers(userIds);
		try {
			for (Iterator it = toBeRemoved.iterator(); it.hasNext();) {
				User one = (User) it.next();
				// 不能删除自己
				if (!one.getId().equals(getUserId())) {
					userService.removeUser(creator, one);
				}
			}
		} catch (Exception e) {
			return redirect("search", "info.delete.failure");
		}
		return redirect("search", "info.delete.success");
	}

	/**
	 * 禁用或激活一个或多个用户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String activate() {
		String userIdSeq = get("userIds");
		Long[] userIds = SeqStringUtil.transformToLong(userIdSeq);
		String isActivate = get("isActivate");
		try {
			if (StringUtils.isNotEmpty(isActivate) && "false".equals(isActivate)) {
				// logHelper.info(request, "UnActivate userIds:" + userIds);
				userService.updateState(userIds, User.FREEZE);
			} else {
				// logHelper.info(request, "Activate userIds:" + userIds);
				userService.updateState(userIds, User.ACTIVE);
			}
		} catch (Exception e) {
			// logHelper.info(request, "Faliure Activate alert for userIds:"
			// + userIds, e);
			return forward(ERROR, "error.occurred");
		}
		String msg = "ok.activate";
		if (StringUtils.isNotEmpty(isActivate) && "false".equals(isActivate))
			msg = "info.unactivate.success";

		return redirect("search", msg);
	}

	/**
	 * 核实用户身份
	 * 
	 * @param user
	 * @param category
	 * @return
	 */
	protected String checkCategory(User user, Long categoryId) {
		user.getCategories().add(entityService.get(UserCategory.class, categoryId));
		return "";
	}

	protected String checkUser(User user) {
		if (user.isVO() && entityService.exist(entityName, "name", user.getName())) {
			return "error.model.existed";
		}
		return "";
	}

	public String info() throws Exception {
		String loginName = get("loginName");
		if (StringUtils.isNotBlank(loginName)) {
			User user = userService.get(loginName);
			if (null != user) {
				put("user", user);
				return forward();
			} else {
				return null;
			}
		} else {
			return super.info();
		}
	}

	protected String getDefaultPassword(User user) {
		return EncryptUtil.encode(User.DEFAULT_PASSWORD);
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

}
