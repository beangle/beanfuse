//$Id: UtilDAO.java,v 1.8 2006/12/19 05:10:06 chaostone Exp $
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

package org.beanfuse.persist;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.beanfuse.collection.page.Page;
import org.beanfuse.collection.page.PageLimit;
import org.beanfuse.model.Entity;
import org.beanfuse.query.AbstractQuery;
import org.hibernate.Criteria;
import org.hibernate.Query;

/**
 * hibernate dao 查询辅助类
 * 
 * @author chaostone
 * 
 */
public interface EntityDao {
	/**
	 * 列举给定实体的所有实例
	 * 
	 * @param entity
	 * @return
	 */
	public List loadAll(Class entity);

	/**
	 * 根据属性列举实体
	 * 
	 * @param entityClass
	 * @param keyName
	 * @param values
	 * @return
	 */
	public List load(Class entityClass, String keyName, Object[] values);

	/**
	 * 根据属性列举实体
	 * 
	 * @param entityClass
	 * @param keyName
	 * @param values
	 * @return
	 */
	public List load(String entityName, String keyName, Object[] values);

	/**
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Entity load(Class clazz, Serializable id);

	/**
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Entity get(Class clazz, Serializable id);

	/**
	 * 依据实体类的全名或简名加载对象
	 * 
	 * @param entityName
	 * @param id
	 * @return
	 */
	public Entity get(String entityName, Serializable id);

	/**
	 * 执行查询
	 * 
	 * @param query
	 * @return
	 */
	public List search(AbstractQuery query);

	/**
	 * 命名查询
	 * 
	 * @param queryName
	 * @param params
	 * @param cacheable
	 * @return
	 */
	public List searchNamedQuery(final String queryName, final Map params);

	/**
	 * 命名查询
	 * 
	 * @param queryName
	 * @param params
	 * @param cacheable
	 * @return
	 */
	public List searchNamedQuery(final String queryName, final Object[] params);

	/**
	 * 支持缓存的命名查询
	 * 
	 * @param queryName
	 * @param params
	 * @param cacheable
	 * @return
	 */
	public List searchNamedQuery(String queryName, Map params, boolean cacheable);

	/**
	 * 直接查询
	 * 
	 * @param hql
	 * @return
	 */
	public List searchHQLQuery(final String hql);

	/**
	 * HQL查询
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public List searchHQLQuery(final String hql, final Map params);

	/**
	 * HQL查询
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public List searchHQLQuery(final String hql, final Object[] params);

	/**
	 * 支持缓存的HQL查询
	 * 
	 * @param queryName
	 * @param params
	 * @param cacheable
	 * @return
	 */
	public List searchHQLQuery(String hql, final Map params, boolean cacheable);

	/**
	 * 分页命名查询
	 * 
	 * @param queryName
	 * @param params
	 * @param limit
	 * @return
	 */
	public Page paginateNamedQuery(final String queryName, final Map params, PageLimit limit);

	/**
	 * 分页HQL查询
	 * 
	 * @param hql
	 * @param params
	 * @param limit
	 * @return
	 */
	public Page paginateHQLQuery(final String hql, final Map params, PageLimit limit);

	/**
	 * 对hibernate Criteria的分页支持
	 * 
	 * @param criteria
	 * @param limit
	 * @return
	 */
	public Page paginateCriteria(Criteria criteria, PageLimit limit);

	/**
	 * 对hibernate query的分页支持
	 * 
	 * @param query
	 * @param params
	 * @param limit
	 * @return
	 */
	public Page paginateQuery(Query query, Map params, PageLimit limit);

	/**
	 * 执行HQL 进行更新或者删除
	 * 
	 * @param queryStr
	 * @param argument
	 * @return
	 */
	int executeUpdateHql(String hql, Object[] argument);

	/**
	 * 执行HQL 进行更新或者删除
	 * 
	 * @param queryStr
	 * @param argument
	 * @return
	 */
	int executeUpdateHql(String hql, Map parameterMap);

	/**
	 * 执行命名语句进行更新或者删除
	 * 
	 * @param queryStr
	 * @param argument
	 * @return
	 */
	int executeUpdateNamedQuery(String queryName, Map parameterMap);

	/**
	 * 执行命名语句进行更新或者删除
	 * 
	 * @param queryStr
	 * @param argument
	 * @return
	 */
	int executeUpdateNamedQuery(String queryName, Object[] argument);

	/**
	 * 保存单个或多个实体.
	 * 
	 */
	public void saveOrUpdate(Object obj);

	/**
	 * 按照实体名称，保存单个或多个实体.
	 * 
	 * @param entityName
	 * @param obj
	 */
	public void saveOrUpdate(String entityName, Object obj);

	/**
	 * 删除单个对象
	 * 
	 * @param entity
	 */
	public void remove(Object entity);

	/**
	 * 删除集合内的所有对象
	 * 
	 * @param entities
	 */
	public void remove(Collection entities);

	/**
	 * 批量删除对象
	 * 
	 * @param entityClass
	 *            (对象对应的类)
	 * @param keyName
	 *            (得到对象的key)
	 * @param values
	 *            (要修改的values的值集合)
	 * @return 是否删除成功
	 */
	public boolean remove(Class entityClass, String attr, Object[] values);

	/**
	 * 批量删除对象
	 * 
	 * @param entityClass
	 *            (对象对应的类)
	 * @param attr
	 *            (得到对象的key)
	 * @param values
	 *            (要修改的ids的值集合)
	 * @return 是否删除成功
	 */
	public boolean remove(Class entityClass, String attr, Collection values);

	/**
	 * 批量删除对象
	 * 
	 * @param entityClass
	 * @param parameterMap
	 *            (取得对象的key的name和value对应的Map)
	 * @return 是否删除成功
	 */
	public boolean remove(Class entityClass, Map parameterMap);

	// Blob and Clob
	public Blob createBlob(InputStream inputStream, int length);

	public Blob createBlob(InputStream inputStream);

	public Clob createClob(String str);

	// 容器相关
	public void evict(Object entity);

	void initialize(Object entity);

	void refresh(Object entity);

}
