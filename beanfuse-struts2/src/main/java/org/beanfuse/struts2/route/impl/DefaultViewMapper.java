//$Id:DefaultResultPageMapper.java 2009-1-19 上午12:14:07 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.struts2.route.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.struts2.route.Convention;
import org.beanfuse.struts2.route.Profile;
import org.beanfuse.struts2.route.ProfileService;
import org.beanfuse.struts2.route.ViewMapper;
import org.beanfuse.lang.StringUtil;

import com.opensymphony.xwork2.inject.Inject;

public class DefaultViewMapper implements ViewMapper {

	private Map<String, String> methodViews = new HashMap<String, String>();

	private ProfileService profileServie;

	public DefaultViewMapper() {
		super();
		methodViews.put("search", "list");
		methodViews.put("query", "list");
		methodViews.put("edit", "form");
		methodViews.put("home", "index");
		methodViews.put("execute", "index");
		methodViews.put("add", "new");
	}

	/**
	 * 查询control对应的view的名字(没有后缀)
	 * 
	 * @param request
	 * @param controllerClass
	 * @param relativePath
	 * @return
	 */
	public String getViewPath(String className, String methodName, String viewName) {
		if (StringUtils.isNotEmpty(viewName)) {
			if (viewName.charAt(0) == Convention.separator) {
				return viewName;
			}
		}
		Profile profile = profileServie.getProfile(className);
		if (null == profile) {
			throw new RuntimeException("no convention profile for " + className);
		}
		StringBuilder buf = new StringBuilder();
		if (profile.getPathStyle().equals(FULL)) {
			buf.append(Convention.separator);
			buf.append(profile.getSimpleName(className));
		} else if (profile.getPathStyle().equals(SIMPLE)) {
			buf.append(profile.getViewPath());
			// 添加中缀路径
			buf.append(profile.getInfix(className));
		} else if (profile.getPathStyle().equals(SEO)) {
			buf.append(profile.getViewPath());
			buf.append(StringUtil.unCamel(profile.getInfix(className)));
		} else {
			throw new RuntimeException(profile.getPathStyle() + " was not supported");
		}
		// add method mapping path
		buf.append(Convention.separator);
		if (StringUtils.isEmpty(viewName) || viewName.equals("success")) {
			viewName = methodName;
		}

		if (null == methodViews.get(viewName)) {
			buf.append(viewName);
		} else {
			buf.append(methodViews.get(viewName));
		}
		return buf.toString();
	}

	@Inject
	public void setProfileServie(ProfileService profileServie) {
		this.profileServie = profileServie;
	}
}
