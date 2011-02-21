//$Id:QueryHelper.java 2009-1-20 下午06:53:48 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.struts2.action.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beanfuse.collection.page.Page;
import org.beanfuse.collection.page.PageLimit;
import org.beanfuse.entity.Model;
import org.beanfuse.entity.types.EntityType;
import org.beanfuse.query.Condition;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.utils.web.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryHelper extends ParamHelper {

	protected static final Logger logger = LoggerFactory.getLogger(QueryHelper.class);

	public static final String PAGENO = "pageNo";

	public static final String PAGESIZE = "pageSize";

	public static boolean RESERVED_NULL = true;

	public static void populateConditions(EntityQuery entityQuery) {
		entityQuery.add(extractConditions(entityQuery.getEntityClass(), entityQuery.getAlias(),
				null));
	}

	/**
	 * 把entity alias的别名的参数转换成条件.<br>
	 * 
	 * @param entityQuery
	 * @param exclusiveAttrNames
	 *            以entityQuery中alias开头的属性串
	 */
	public static void populateConditions(EntityQuery entityQuery, String exclusiveAttrNames) {
		entityQuery.add(extractConditions(entityQuery.getEntityClass(), entityQuery.getAlias(),
				exclusiveAttrNames));
	}

	/**
	 * 提取中的条件
	 * 
	 * @param clazz
	 * @param prefix
	 * @param exclusiveAttrNames
	 * @return
	 */
	public static List extractConditions(Class clazz, String prefix, String exclusiveAttrNames) {
		Object entity = null;
		try {
			if (clazz.isInterface()) {
				EntityType entityType = Model.getEntityType(clazz.getName());
				clazz = entityType.getEntityClass();
			}
			entity = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("[RequestUtil.extractConditions]: error in in initialize "
					+ clazz);
		}
		List conditions = new ArrayList();
		HttpServletRequest request = ServletActionContext.getRequest();
		Map params = ParamHelper.getParams( prefix, exclusiveAttrNames);
		for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
			String attr = (String) iter.next();
			String strValue = ((String) params.get(attr)).trim();
			// 过滤空属性
			if (StringUtils.isNotEmpty(strValue)) {
				try {
					if (RESERVED_NULL && "null".equals(strValue)) {
						conditions.add(new Condition(prefix + "." + attr + " is null"));
					} else {
						Model.getPopulator().populateValue(attr, strValue, entity);
						Object settedValue = PropertyUtils.getProperty(entity, attr);
						if (null == settedValue)
							continue;
						if (settedValue instanceof String) {
							conditions.add(new Condition(prefix + "." + attr + " like :"
									+ attr.replace('.', '_'), "%" + (String) settedValue + "%"));
						} else {
							conditions.add(new Condition(prefix + "." + attr + "=:"
									+ attr.replace('.', '_'), settedValue));
						}
					}
				} catch (Exception e) {
					logger.debug("[populateFromParams]:error in populate entity " + prefix
							+ "'s attribute " + attr);
				}
			}
		}
		return conditions;
	}

	/**
	 * 从的参数或者cookie中(参数优先)取得分页信息
	 * 
	 * @return
	 */
	public static PageLimit getPageLimit() {
		PageLimit limit = new PageLimit();
		limit.setPageNo(getPageNo());
		limit.setPageSize(getPageSize());
		return limit;
	}

	/**
	 * 获得请求中的页码
	 * 
	 * @return
	 */
	public static int getPageNo() {
		String pageNo = get(PAGENO);
		if (StringUtils.isNotEmpty(pageNo)) {
			return Integer.valueOf(pageNo).intValue();
		} else {
			return Page.DEFAULT_PAGE_NUM;
		}
	}

	/**
	 * 获得请求中的页长
	 * 
	 * @return
	 */
	public static int getPageSize() {
		String pageSize = get(PAGESIZE);
		if (StringUtils.isNotEmpty(pageSize)) {
			return Integer.valueOf(pageSize).intValue();
		} else {
			HttpServletRequest request = ServletActionContext.getRequest();
			pageSize = CookieUtils.getCookieValue(request, PAGESIZE);
			if (StringUtils.isNotEmpty(pageSize)) {
				return Integer.valueOf(pageSize).intValue();
			} else
				return Page.DEFAULT_PAGE_SIZE;
		}
	}

	/**
	 * 向中注册分页信息
	 * 
	 * @param objs
	 */
	public static void addPage(Page objs) {
		Page page = (Page) objs;
		put(PAGENO, new Integer(page.getPageNo()));
		put(PAGESIZE, new Integer(page.getPageSize()));
		put("previousPageNo", new Integer(page.getPreviousPageNo()));
		put("nextPageNo", new Integer(page.getNextPageNo()));
		put("maxPageNo", new Integer(page.getMaxPageNo()));
		put("thisPageSize", new Integer(page.size()));
		put("totalSize", new Integer(page.getTotal()));
	}

	public static void addDateIntervalCondition(EntityQuery query, String attr, String beginOn,
			String endOn) {
		addDateIntervalCondition(query, query.getAlias(), attr, beginOn, endOn);
	}

	/**
	 * 增加日期区间查询条件
	 * 
	 * @param
	 * @param query
	 * @param alias
	 * @param attr
	 *            时间限制属性
	 * @param beginOn
	 *            开始的属性名字(全名)
	 * @param endOn
	 *            结束的属性名字(全名)
	 * @throws ParseException
	 */
	public static void addDateIntervalCondition(EntityQuery query, String alias, String attr,
			String beginOn, String endOn) {
		String stime = get(beginOn);
		String etime = get(endOn);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date sdate = null, edate = null;
		if (StringUtils.isNotBlank(stime)) {
			try {
				sdate = df.parse(stime);
			} catch (Exception e) {
				logger.debug("wrong date format:" + stime);
			}

		}
		// 截至日期增加一天
		if (StringUtils.isNotBlank(etime)) {
			try {
				edate = df.parse(etime);
			} catch (Exception e) {
				logger.debug("wrong date format:" + etime);
			}
			Calendar gc = new GregorianCalendar();
			gc.setTime(edate);
			gc.set(Calendar.DAY_OF_YEAR, gc.get(Calendar.DAY_OF_YEAR) + 1);
			edate = gc.getTime();
		}
		String objAttr = ((null == alias) ? query.getAlias() : alias) + "." + attr;
		if (null != sdate && null == edate) {
			query.add(new Condition(objAttr + " >=:sdate", sdate));
		} else if (null != sdate && null != edate) {
			query
					.add(new Condition(objAttr + " >=:sdate and " + objAttr + " <:edate", sdate,
							edate));
		} else if (null == sdate && null != edate) {
			query.add(new Condition(objAttr + " <:edate", edate));
		}
	}
}
