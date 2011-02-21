//$Id: UtilDAOImpl.java,v 1.10 2007/01/17 02:48:11 chaostone Exp $
/*
 *
 * Copyright c 2005-2009.
 * 
 * Licensed under GNU  LESSER General Public License, Version 3.  
 * http://www.gnu.org/licenses
 * 
 */
/********************************************************************************
 * @author pippo
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * pippo             2005-11-15         Created
 *  
 ********************************************************************************/

package org.beanfuse.persist.hibernate;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.beanfuse.collection.page.Page;
import org.beanfuse.collection.page.PageLimit;
import org.beanfuse.collection.page.SinglePage;
import org.beanfuse.entity.Model;
import org.beanfuse.model.Entity;
import org.beanfuse.persist.EntityDao;
import org.beanfuse.query.AbstractQuery;
import org.beanfuse.query.EntityQuery;
import org.beanfuse.query.hibernate.HibernateQuerySupport;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author chaostone
 */
public class EntityDaoHibernate extends HibernateDaoSupport implements EntityDao {

	public List loadAll(Class clazz) {
		String hql = "from " + Model.getEntityType(clazz).getEntityName();
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	public List load(Class entityClass, String keyName, Object[] values) {
		if (entityClass == null || StringUtils.isEmpty(keyName) || values == null
				|| values.length == 0) {
			return Collections.EMPTY_LIST;
		}

		String entityName = Model.getEntityType(entityClass).getEntityName();
		return load(entityName, keyName, values);
	}

	public List load(String entityName, String keyName, Object[] values) {
		StringBuilder hql = new StringBuilder();
		hql.append("select entity from ").append(entityName).append(" as entity where entity.")
				.append(keyName).append(" in (:keyName)");
		Map parameterMap = new HashMap(1);
		if (values.length < 500) {
			parameterMap.put("keyName", values);
			EntityQuery query = new EntityQuery(hql.toString());
			query.setParams(parameterMap);
			return (List) search(query);
		} else {
			EntityQuery query = new EntityQuery(hql.toString());
			query.setParams(parameterMap);
			List rs = new ArrayList();
			int i = 0;
			while (i < values.length) {
				int end = i + 500;
				if (end > values.length) {
					end = values.length;
				}
				parameterMap.put("keyName", ArrayUtils.subarray(values, i, end));
				rs.addAll(search(query));
				i += 500;
			}
			return rs;
		}
	}

	public Entity get(Class clazz, Serializable id) {
		return get(Model.getEntityType(clazz).getEntityName(), id);
	}

	public Entity get(String entityName, Serializable id) {
		if (StringUtils.contains(entityName, '.')) {
			return (Entity) getHibernateTemplate().get(entityName, id);
		} else {
			String hql = "from " + entityName + " where id =:id";
			Query query = getSession().createQuery(hql);
			query.setParameter("id", id);
			List rs = query.list();
			if (rs.isEmpty()) {
				return null;
			} else {
				return (Entity) rs.get(0);
			}
		}
	}

	public Entity load(Class clazz, Serializable id) {
		return (Entity) getHibernateTemplate().load(Model.getEntityType(clazz).getEntityName(), id);
	}

	/**
	 * 依据自构造的查询语句进行查询
	 * 
	 * @see #buildCountQueryStr(Query)
	 * @see org.beanfuse.query.limit.Pagination
	 */
	public List search(AbstractQuery query) {
		return HibernateQuerySupport.search(query, getSession());
	}

	public List searchNamedQuery(final String queryName, final Map params) {
		Query query = this.getSession().getNamedQuery(queryName);
		return HibernateQuerySupport.setParameter(query, params).list();
	}

	public List searchNamedQuery(final String queryName, final Map params, boolean cacheable) {
		Query query = this.getSession().getNamedQuery(queryName);
		query.setCacheable(cacheable);
		return HibernateQuerySupport.setParameter(query, params).list();
	}

	public List searchNamedQuery(String queryName, Object[] params) {
		Query query = this.getSession().getNamedQuery(queryName);
		return HibernateQuerySupport.setParameter(query, params).list();
	}

	public List searchHQLQuery(String hql) {
		return getSession().createQuery(hql).list();
	}

	public List searchHQLQuery(String hql, Map params) {
		Query query = this.getSession().createQuery(hql);
		return HibernateQuerySupport.setParameter(query, params).list();
	}

	public List searchHQLQuery(String hql, Object[] params) {
		Query query = this.getSession().createQuery(hql);
		return HibernateQuerySupport.setParameter(query, params).list();
	}

	public List searchHQLQuery(String hql, final Map params, boolean cacheable) {
		Query query = this.getSession().createQuery(hql);
		query.setCacheable(cacheable);
		return HibernateQuerySupport.setParameter(query, params).list();
	}

	public Page paginateNamedQuery(String queryName, Map params, PageLimit limit) {
		Query query = this.getSession().getNamedQuery(queryName);
		return paginateQuery(query, params, limit);
	}

	public Page paginateHQLQuery(String hql, Map params, PageLimit limit) {
		Query query = this.getSession().createQuery(hql);
		return paginateQuery(query, params, limit);
	}

	public Page paginateCriteria(Criteria criteria, PageLimit limit) {
		CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;
		int totalCount = 0;
		List targetList = null;
		if (null == criteriaImpl.getProjection()) {
			criteria.setFirstResult((limit.getPageNo() - 1) * limit.getPageSize()).setMaxResults(
					limit.getPageSize());
			targetList = criteria.list();
			Projection projection = null;
			criteria.setFirstResult(0).setMaxResults(1);
			projection = Projections.rowCount();
			totalCount = ((Integer) criteria.setProjection(projection).uniqueResult()).intValue();
		} else {
			List list = criteria.list();
			totalCount = list.size();
			criteria.setFirstResult((limit.getPageNo() - 1) * limit.getPageSize()).setMaxResults(
					limit.getPageSize());
			targetList = criteria.list();
		}
		// 返回结果
		return new SinglePage(limit.getPageNo(), limit.getPageSize(), totalCount, targetList);
	}

	public void evict(Object entity) {
		getSession().evict(entity);
	}

	public int executeUpdateHql(final String queryStr, final Object[] argument) {
		Query query = getSession().createQuery(queryStr);
		return HibernateQuerySupport.setParameter(query, argument).executeUpdate();
	}

	public int executeUpdateHql(final String queryStr, final Map parameterMap) {
		Query query = getSession().createQuery(queryStr);
		return HibernateQuerySupport.setParameter(query, parameterMap).executeUpdate();
	}

	public int executeUpdateNamedQuery(final String queryName, final Map parameterMap) {
		Query query = getSession().getNamedQuery(queryName);
		return HibernateQuerySupport.setParameter(query, parameterMap).executeUpdate();
	}

	public int executeUpdateNamedQuery(final String queryName, final Object[] argument) {
		Query query = getSession().getNamedQuery(queryName);
		return HibernateQuerySupport.setParameter(query, argument).executeUpdate();
	}

	public Blob createBlob(InputStream inputStream, int length) {
		BufferedInputStream imageInput = new java.io.BufferedInputStream(inputStream);
		return Hibernate.createBlob(imageInput, length);
	}

	public Blob createBlob(InputStream inputStream) {
		BufferedInputStream imageInput = new BufferedInputStream(inputStream);
		try {
			return Hibernate.createBlob(imageInput);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public Clob createClob(String str) {
		return Hibernate.createClob(str);
	}

	public void refresh(Object entity) {
		getSession().refresh(entity);
	}

	public void initialize(Object entity) {
		getHibernateTemplate().initialize(entity);
	}

	/**
	 * 
	 * @param query
	 * @param names
	 * @param values
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page paginateQuery(Query query, Map params, PageLimit limit) {
		HibernateQuerySupport.setParameter(query, params);
		query.setFirstResult((limit.getPageNo() - 1) * limit.getPageSize()).setMaxResults(
				limit.getPageSize());
		List targetList = query.list();

		String queryStr = buildCountQueryStr(query);
		Query countQuery = null;
		if (query instanceof SQLQuery) {
			countQuery = getSession().createSQLQuery(queryStr);
		} else {
			countQuery = getSession().createQuery(queryStr);
		}
		HibernateQuerySupport.setParameter(countQuery, params);
		// 返回结果
		return new SinglePage(limit.getPageNo(), limit.getPageSize(), ((Number) (countQuery
				.uniqueResult())).intValue(), targetList);
	}

	public void saveOrUpdate(Object entity) {
		if (entity instanceof Collection) {
			Collection entities = (Collection) entity;
			for (Iterator iter = entities.iterator(); iter.hasNext();) {
				Object obj = iter.next();
				if (obj instanceof HibernateProxy) {
					getHibernateTemplate().saveOrUpdate(obj);
				} else {
					getHibernateTemplate().saveOrUpdate(
							Model.getEntityType(obj.getClass()).getEntityName(), obj);
				}
			}
		} else {
			if (entity instanceof HibernateProxy) {
				getHibernateTemplate().saveOrUpdate(entity);
			} else {
				getHibernateTemplate().saveOrUpdate(
						Model.getEntityType(entity.getClass()).getEntityName(), entity);
			}
		}
	}

	public void saveOrUpdate(String entityName, Object entity) {
		if (entity instanceof Collection) {
			Collection entities = (Collection) entity;
			for (Iterator iter = entities.iterator(); iter.hasNext();) {
				getHibernateTemplate().saveOrUpdate(entityName, (Object) iter.next());
			}
		} else {
			getHibernateTemplate().saveOrUpdate(entityName, entity);
		}
	}

	public void remove(Collection entities) {
		for (Iterator iter = entities.iterator(); iter.hasNext();) {
			getHibernateTemplate().delete((Object) iter.next());
		}
	}

	public void remove(Object entity) {
		getHibernateTemplate().delete(entity);
	}

	public boolean remove(Class clazz, String attr, Object[] values) {
		if (clazz == null || StringUtils.isEmpty(attr) || values == null || values.length == 0) {
			return false;
		}
		String entityName = Model.getEntityType(clazz).getEntityName();
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ").append(entityName).append(" where ").append(attr).append(
				" in (:ids)");
		Map parameterMap = new HashMap(1);
		parameterMap.put("ids", values);
		return executeUpdateHql(hql.toString(), parameterMap) > 0;
	}

	public boolean remove(Class entityClass, String attr, Collection values) {
		return remove(entityClass, attr, values.toArray());
	}

	public boolean remove(Class clazz, Map keyMap) {
		if (clazz == null || keyMap == null || keyMap.isEmpty()) {
			return false;
		}
		String entityName = Model.getEntityType(clazz).getEntityName();
		StringBuilder hql = new StringBuilder();
		hql.append("delete from ").append(entityName).append(" where ");
		Set keySet = keyMap.keySet();
		Map params = new HashMap();
		for (Iterator ite = keySet.iterator(); ite.hasNext();) {
			String keyName = ite.next().toString();
			Object keyValue = keyMap.get(keyName);
			String paramName = keyName.replace('.', '_');
			params.put(paramName, keyValue);
			if (keyValue.getClass().isArray() || keyValue instanceof Collection) {
				hql.append(keyName).append(" in (:").append(paramName).append(") and ");
			} else {
				hql.append(keyName).append(" = :").append(paramName).append(" and ");
			}
		}
		hql.append(" (1=1) ");
		return (executeUpdateHql(hql.toString(), params) > 0);
	}

	/**
	 * 构造查询记录数目的查询字符串
	 * 
	 * @param query
	 * @return
	 */
	private String buildCountQueryStr(Query query) {
		String queryStr = "select count(*) ";
		if (query instanceof SQLQuery) {
			queryStr += "from (" + query.getQueryString() + ")";
		} else {
			String lowerCaseQueryStr = query.getQueryString().toLowerCase();
			String selectWhich = lowerCaseQueryStr.substring(0, query.getQueryString().indexOf(
					"from"));
			int indexOfDistinct = selectWhich.indexOf("distinct");
			int indexOfFrom = lowerCaseQueryStr.indexOf("from");
			// 如果含有distinct
			if (-1 != indexOfDistinct) {
				if (StringUtils.contains(selectWhich, ",")) {
					queryStr = "select count("
							+ query.getQueryString().substring(indexOfDistinct,
									query.getQueryString().indexOf(",")) + ")";

				} else {
					queryStr = "select count("
							+ query.getQueryString().substring(indexOfDistinct, indexOfFrom) + ")";
				}
			}
			queryStr += query.getQueryString().substring(indexOfFrom);
		}
		return queryStr;
	}

}
