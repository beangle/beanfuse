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
 * chaostone             2006-5-24            Created
 *  
 ********************************************************************************/
package org.beanfuse.webapp.security.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beanfuse.bean.comparators.PropertyComparator;
import org.beanfuse.collection.page.PagedList;
import org.beanfuse.security.access.CachedResourceAccessor;
import org.beanfuse.security.concurrent.category.CategorySessionController;
import org.beanfuse.security.online.CategoryProfile;

import com.opensymphony.xwork2.ActionContext;

/**
 * 系统在线用户管理
 * 
 * @author chaostone
 * 
 */
public class OnlineUserAction extends SecurityAction {

	private CategorySessionController sessionController;

	protected void indexSetting() {
		String orderBy = get("orderBy");
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = "principal asc";
		}
		List onlineActivities = sessionController.getOnlineActivities();
		Collections.sort(onlineActivities, new PropertyComparator(orderBy));
		put("onlineActivities", new PagedList(onlineActivities, getPageLimit()));
		put("onlineProfiles", sessionController.getOnlineProfiles());
	}

	/**
	 * 保存设置
	 * 
	 * @param mapping
	 * @param form
	 * @param
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String save() {
		List categories = entityService.loadAll(CategoryProfile.class);
		for (Iterator iterator = categories.iterator(); iterator.hasNext();) {
			CategoryProfile profile = (CategoryProfile) iterator.next();
			int max = getInteger("max_" + profile.getId()).intValue();
			int maxSessions = getInteger("maxSessions_" + profile.getId()).intValue();
			int inactiveInterval = getInteger("inactiveInterval_" + profile.getId()).intValue();
			profile.setCapacity(max);
			profile.setUserMaxSessions(maxSessions);
			profile.setInactiveInterval(inactiveInterval);
			sessionController.setCategoryProfile(profile.getCategory(), profile);
		}
		entityService.saveOrUpdate(categories);
		return redirect("index", "info.save.success");
	}

	/**
	 * 保存设置
	 * 
	 * @param mapping
	 * @param form
	 * @param
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String invalidate() {
		String[] sessionIds = StringUtils.split(get("sessionIds"), ",");
		String mySessionId = ServletActionContext.getRequest().getSession().getId();
		if (null != sessionIds) {
			for (int i = 0; i < sessionIds.length; i++) {
				if (mySessionId.equals(sessionIds[i]))
					continue;
				sessionController.removeAuthentication(sessionIds[i]);
			}
		}
		return redirect("index", "info.save.success");
	}

	/**
	 * 访问记录
	 * 
	 * @param mapping
	 * @param form
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String accessLog() {
		CachedResourceAccessor ra = (CachedResourceAccessor) ActionContext.getContext()
				.getApplication().get("ResourceAccessor");
		List accessLogs = null;
		if (null == ra) {
			accessLogs = Collections.EMPTY_LIST;
		} else {
			accessLogs = new ArrayList(ra.getAccessLogs());
		}
		String orderBy = get("orderBy");
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = "duration desc";
		}
		Collections.sort(accessLogs, new PropertyComparator(orderBy));
		put("accessLogs", accessLogs);
		return forward();
	}

	public void setSessionController(CategorySessionController sessionController) {
		this.sessionController = sessionController;
	}

}
