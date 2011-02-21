//$Id: HibernateQuerySupport.java,v 1.1 2007-2-9 下午10:29:28 chaostone Exp $
/*
 * Copyright c 2005-2009
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-2-9         Created
 *  
 ********************************************************************************/

package org.beanfuse.query.hibernate;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.collection.page.PageLimit;
import org.beanfuse.collection.page.SinglePage;
import org.beanfuse.query.AbstractQuery;
import org.beanfuse.query.Condition;
import org.beanfuse.query.EntityQuery;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * 与hibernate的接口支持
 * 
 * @author chaostone
 * 
 */
public final class HibernateQuerySupport {

	private HibernateQuerySupport() {
		super();
	}

	/**
	 * 针对查询条件绑定查询的值
	 * 
	 * @param query
	 * @param conditions
	 */
	public static void bindValues(final Query query, final List conditions) {
		int position = 0;
		boolean hasInterrogation = false; // 含有问号
		for (final Iterator iter = conditions.iterator(); iter.hasNext();) {
			final Condition condition = (Condition) iter.next();
			if (StringUtils.contains(condition.getContent(), "?")) {
				hasInterrogation = true;
			}
			if (hasInterrogation) {
				for (final Iterator iterator = condition.getValues().iterator(); iterator.hasNext();) {
					query.setParameter(position++, iterator.next());
				}
			} else {
				final List paramNames = condition.getNamedParams();
				for (int i = 0; i < paramNames.size(); i++) {
					final String name = (String) paramNames.get(i);
					final Object value = condition.getValues().get(i);

					if (value.getClass().isArray()) {
						query.setParameterList(name, (Object[]) value);
					} else if (value instanceof Collection) {
						query.setParameterList(name, (Collection) value);
					} else {
						query.setParameter(name, value);
					}
				}
			}
		}
	}

	/**
	 * 统计该查询的记录数
	 * 
	 * @param query
	 * @param hibernateSession
	 * @return
	 */
	public static int count(final AbstractQuery query, final Session hibernateSession) {
		final String countQueryStr = query.toCountString();
		if (StringUtils.isEmpty(countQueryStr)) {
			Query hibernateQuery = null;
			if (query instanceof EntityQuery) {
				hibernateQuery = hibernateSession.createQuery(query.toQueryString());
			} else {
				hibernateQuery = hibernateSession.createSQLQuery(query.toQueryString());
			}
			if (query.isCacheable()) {
				hibernateQuery.setCacheable(query.isCacheable());
			}
			setParameter(hibernateQuery, query.getParams());
			return hibernateQuery.list().size();
		} else {
			Query countQuery = null;
			if (query instanceof EntityQuery) {
				countQuery = hibernateSession.createQuery(countQueryStr);
			} else {
				countQuery = hibernateSession.createSQLQuery(countQueryStr);
			}
			if (query.isCacheable()) {
				countQuery.setCacheable(query.isCacheable());
			}
			setParameter(countQuery, query.getParams());
			final Number count = (Number) (countQuery.uniqueResult());
			if (null == count) {
				return 0;
			} else {
				return count.intValue();
			}
		}
	}

	/**
	 * 查询结果集
	 * 
	 * @param query
	 * @param hibernateSession
	 * @return
	 */
	public static List find(final AbstractQuery query, final Session hibernateSession) {
		Query hibernateQuery = null;
		if (query instanceof EntityQuery) {
			hibernateQuery = hibernateSession.createQuery(query.toQueryString());
		} else {
			hibernateQuery = hibernateSession.createSQLQuery(query.toQueryString());
		}
		if (query.isCacheable()) {
			hibernateQuery.setCacheable(query.isCacheable());
		}
		setParameter(hibernateQuery, query.getParams());
		if (null == query.getLimit()) {
			return hibernateQuery.list();
		} else {
			final PageLimit limit = query.getLimit();
			hibernateQuery.setFirstResult((limit.getPageNo() - 1) * limit.getPageSize())
					.setMaxResults(limit.getPageSize());
			return hibernateQuery.list();
		}
	}

	/**
	 * 查询(如果有分页则返回Pagination)
	 * 
	 * @param query
	 * @param hibernateSession
	 * @return
	 */
	public static List search(final AbstractQuery query, final Session hibernateSession) {
		if (null == query.getLimit()) {
			return find(query, hibernateSession);
		} else {
			return new SinglePage(query.getLimit().getPageNo(), query.getLimit().getPageSize(),
					count(query, hibernateSession), find(query, hibernateSession));
		}
	}

	/**
	 * 查询结果中唯一的对象
	 * 
	 * @param query
	 * @param hibernateSession
	 * @return
	 * @throws HibernateException
	 */
	public static Object uniqueResult(final AbstractQuery query, final Session hibernateSession) {
		return uniqueElement(find(query, hibernateSession));
	}

	static Object uniqueElement(final List list) {
		final int size = list.size();
		if (size == 0) {
			return null;
		}
		final Object first = list.get(0);
		for (int i = 1; i < size; i++) {
			if (list.get(i) != first) {
				throw new NonUniqueResultException(list.size());
			}
		}
		return first;
	}

	/**
	 * 为query设置参数
	 * 
	 * @param query
	 * @param argument
	 * @return
	 */
	public static Query setParameter(final Query query, final Object[] argument) {
		if (argument != null && argument.length > 0) {
			for (int i = 0; i < argument.length; i++) {
				query.setParameter(i, argument[i]);
			}
		}
		return query;
	}

	/**
	 * 为query设置参数
	 * 
	 * @param query
	 * @param argument
	 * @return
	 */
	public static Query setParameter(final Query query, final Map parameterMap) {
		if (parameterMap != null && !parameterMap.isEmpty()) {
			final Set parameterNameSet = parameterMap.keySet();
			for (final Iterator ite = parameterNameSet.iterator(); ite.hasNext();) {
				final String parameterName = (String) ite.next();
				if (null == parameterName) {
					break;
				}
				final Object parameterValue = parameterMap.get(parameterName);
				if (null == parameterValue) {
					query.setParameter(parameterName, (Object) null);
				} else if (parameterValue.getClass().isArray()) {
					query.setParameterList(parameterName, (Object[]) parameterValue);
				} else if (parameterValue instanceof Collection) {
					query.setParameterList(parameterName, (Collection) parameterValue);
				} else {
					query.setParameter(parameterName, parameterValue);
				}
			}
		}
		return query;
	}
}
