//$Id: PasswordCtrl.java,v 1.1 2008-8-19 下午03:39:39 鄂州蚊子 Exp $
/*
 *
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 */
/********************************************************************************
 * @author 鄂州蚊子
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * 鄂州蚊子                 2008-8-19             Created
 *  
 ********************************************************************************/
package org.beanfuse.webapp.security.action;

import java.util.HashMap;
import java.util.Map;

import org.beanfuse.model.predicates.ValidEntityKeyPredicate;
import org.beanfuse.security.User;
import org.beanfuse.security.service.UserService;

public class PasswordAction extends SecurityAction {

	private UserService userService;

	/**
	 * 显示修改用户帐户界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String editUser() {
		put("user", userService.get(getLong("user.id")));
		return forward();
	}

	/**
	 * 更新其他用户帐户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String updateUser() {
		Long userId = getLong("user.id");
		if (ValidEntityKeyPredicate.INSTANCE.evaluate(userId)) {
			User user = userService.get(userId);
			User manager = getUser();
			if (userService.isManagedBy(manager, user)) {
				return updateAccount(userId);
			} else {
				return null;
			}
		} else {
			addError("error.parameters.needed");
			return (ERROR);
		}
	}



	/**
	 * 更新指定帐户的密码和邮箱
	 * 
	 * @param mapping
	 * @param request
	 * @param userId
	 * @return
	 */
	private String updateAccount(Long userId) {
		String email = get("mail");
		String pwd = get("password");
		Map valueMap = new HashMap(2);
		valueMap.put("password", pwd);
		valueMap.put("mail", email);
		entityService.update(User.class, "id", new Object[] { userId }, valueMap);
		addMessage("ok.passwordChanged");
		return "actionResult";
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
