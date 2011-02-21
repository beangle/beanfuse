/*
 * Copyright c 2005-2009.
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-12-19            Created
 *  
 ********************************************************************************/
package org.beanfuse.query;

import java.util.Map;

import org.beanfuse.collection.page.PageLimit;

/**
 * 抽象查询
 * 
 * @author chaostone
 * 
 */
public abstract class AbstractQuery {
	/** query 查询语句 */
	protected String queryStr;

	/** count 计数语句 */
	protected String countStr;

	/** 分页 */
	protected PageLimit limit;

	/** 参数 */
	protected Map params;

	/** 缓存查询结果 */
	protected boolean cacheable = false;

	public PageLimit getLimit() {
		return limit;
	}

	public void setLimit(final PageLimit limit) {
		this.limit = limit;
	}

	public Map getParams() {
		return params;
	}

	public String getCountStr() {
		return countStr;
	}

	public void setCountStr(final String countStr) {
		this.countStr = countStr;
	}

	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(final String queryStr) {
		this.queryStr = queryStr;
	}

	public void setParams(final Map params) {
		this.params = params;
	}

	public abstract String toQueryString();

	public String toCountString() {
		return countStr;
	}

	public boolean isCacheable() {
		return cacheable;
	}

	public void setCacheable(final boolean cacheable) {
		this.cacheable = cacheable;
	}

}
