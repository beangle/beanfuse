//$Id:BaseAction.java 2009-1-19 下午08:04:49 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.struts2.action;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;
import org.apache.struts2.ServletActionContext;
import org.beanfuse.struts2.action.helper.ParamHelper;
import org.beanfuse.struts2.action.helper.PopulateHelper;
import org.beanfuse.struts2.action.helper.QueryHelper;
import org.beanfuse.collection.page.Page;
import org.beanfuse.collection.page.PageLimit;
import org.beanfuse.entity.Model;
import org.beanfuse.model.Entity;
import org.beanfuse.persist.EntityService;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.text.TextResource;
import org.beanfuse.utils.web.CookieUtils;
import org.beanfuse.utils.web.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;

public class BaseAction extends DispatchAction {

	protected static Logger logger = LoggerFactory.getLogger(BaseAction.class);

	protected EntityService entityService;

	protected String getRemoteAddr() {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (null == request)
			return null;
		return RequestUtils.getIpAddr(request);
	}

	protected String getURI() {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (null == request)
			return null;
		String actionName = request.getServletPath();
		if (actionName.startsWith("/")) {
			actionName = actionName.substring(1);
		}
		return actionName;
	}

	protected TextResource getTextResource() {
		return new ActionTextResource(this);
	}

	protected void put(String key, Object value) {
		if (value instanceof Page) {
			QueryHelper.addPage((Page) value);
		}
		ParamHelper.put(key, value);
	}

	/**
	 * 
	 * @param request
	 * @param prefix
	 * @return
	 */
	protected Map getParams(String prefix) {
		return ParamHelper.getParams(prefix, null);
	}

	protected Map getParams() {
		return ActionContext.getContext().getParameters();
	}

	/**
	 * 返回request中以prefix.开头的参数
	 * 
	 * @param request
	 * @param prefix
	 * @param exclusiveAttrNames
	 *            要排除的属性串
	 * @return
	 */
	protected Map getParams(String prefix, String exclusiveAttrNames) {
		return ParamHelper.getParams(prefix, exclusiveAttrNames);
	}

	protected String[] getValues(String paramName) {
		return ParamHelper.getValues(paramName);
	}

	protected String get(String paramName) {
		return ParamHelper.get(paramName);
	}

	protected Object getAttribute(String name) {
		return ActionContext.getContext().getContextMap().get(name);
	}

	protected Object get(Class clazz, String name) {
		return ParamHelper.get(clazz, name);
	}

	protected Boolean getBoolean(String name) {
		return ParamHelper.getBoolean(name);
	}

	protected boolean getBool(String name) {
		return ParamHelper.getBool(name);
	}

	protected java.sql.Date getDate(String name) {
		return ParamHelper.getDate(name);
	}

	protected Date getTime(String name) {
		return ParamHelper.getTime(name);
	}

	protected Float getFloat(String name) {
		return ParamHelper.getFloat(name);
	}

	protected Integer getInteger(String name) {
		return ParamHelper.getInteger(name);
	}

	protected Long getLong(String name) {
		return ParamHelper.getLong(name);
	}

	// populate------------------------------------------------------------------

	/**
	 * 将request中的参数设置到clazz对应的bean。
	 * 
	 * @param request
	 * @param clazz
	 * @param name
	 * @return
	 */
	protected Object populate(Class clazz, String shortName) {
		return PopulateHelper.populate(clazz, shortName);
	}

	protected void populate(Object obj, String shortName) {
		Map params = getParams(shortName);
		Model.populate(params, obj);
	}

	protected Object populate(String entityName) {
		return PopulateHelper.populate(entityName);
	}

	protected Object populate(Class clazz) {
		return PopulateHelper.populate(clazz);
	}

	protected Object populate(String entityName, String shortName) {
		return PopulateHelper.populate(entityName, shortName);
	}

	protected Object populate(Object obj, String entityName, String shortName) {
		return PopulateHelper.populate(obj, entityName, shortName);
	}

	protected void populate(Map params, Entity entity, String entityName) {
		Validate.notNull(entity, "Cannot populate to null.");
		Model.getPopulator().populate(params, entity, entityName);
	}

	protected void populate(Map params, Entity entity) {
		Validate.notNull(entity, "Cannot populate to null.");
		Model.populate(params, entity);
	}

	// query------------------------------------------------------
	protected int getPageNo() {
		return QueryHelper.getPageNo();
	}

	protected int getPageSize() {
		return QueryHelper.getPageSize();
	}

	/**
	 * 从request的参数或者cookie中(参数优先)取得分页信息
	 * 
	 * @param request
	 * @return
	 */
	protected PageLimit getPageLimit() {
		return QueryHelper.getPageLimit();
	}

	protected void populateConditions(EntityQuery entityQuery) {
		QueryHelper.populateConditions(entityQuery);
	}

	protected void populateConditions(EntityQuery entityQuery, String exclusiveAttrNames) {
		QueryHelper.populateConditions(entityQuery, exclusiveAttrNames);
	}

	// CURD----------------------------------------
	protected void remove(Collection list) {
		entityService.remove(list);
	}

	protected void remove(Object obj) {
		entityService.remove(obj);
	}

	protected void saveOrUpdate(Collection list) {
		entityService.saveOrUpdate(list);
	}

	protected void saveOrUpdate(Object obj) {
		entityService.saveOrUpdate(obj);
	}

	protected Collection search(EntityQuery query) {
		return entityService.search(query);
	}

	protected EntityService getEntityService() {
		return entityService;
	}

	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	protected String getCookieValue(String cookieName) {
		return CookieUtils.getCookieValue(ServletActionContext.getRequest(), cookieName);
	}

	protected void setCookie(String name, String value, String path, int age) {
		try {
			CookieUtils.setCookie(ServletActionContext.getRequest(), ServletActionContext
					.getResponse(), name, value, path, age);
		} catch (Exception e) {
			logger.error("setCookie error", e);
		}
	}

	protected void setCookie(String name, String value, int age) {
		try {
			CookieUtils.setCookie(ServletActionContext.getRequest(), ServletActionContext
					.getResponse(), name, value, age);
		} catch (Exception e) {
			logger.error("setCookie error", e);
		}
	}

	protected void deleteCookie(String name) {
		CookieUtils.deleteCookieByName(ServletActionContext.getRequest(), ServletActionContext
				.getResponse(), name);
	}
}
