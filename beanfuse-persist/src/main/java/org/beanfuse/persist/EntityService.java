//$Id: EntityService.java,v 1.6 2006/12/19 05:10:06 chaostone Exp $
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

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.beanfuse.collection.page.Page;
import org.beanfuse.collection.page.PageLimit;
import org.beanfuse.entity.ModelBuilder;
import org.beanfuse.model.Entity;
import org.beanfuse.query.AbstractQuery;
import org.hibernate.Criteria;
import org.hibernate.Query;

/**
 * 服务工具类
 * 
 * @author chaostone
 * 
 */
public interface EntityService {
	/**
	 * 加载
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Entity load(Class clazz, Serializable id);

	/**
	 * 得到某一属性的值在集合范围中的对象
	 * 
	 * @param entityClass
	 * @param attr
	 * @param values
	 * @return List
	 */
	public List load(Class entityClass, String attr, Collection values);

	/**
	 * 得到某一属性的值在集合范围中的对象
	 * 
	 * @param entityClass
	 * @param attr
	 * @param values
	 * @return List
	 */
	public List load(Class entityClass, String attr, Object[] values);

	/**
	 * 得到某一属性的值在集合范围中的对象
	 * 
	 * @param entityName
	 * @param attr
	 * @param values
	 * @return List
	 */
	public List load(String entityName, String attr, Object[] values);

	/**
	 * 根据属性单个值查询对象
	 * 
	 * @param entity
	 * @param attr
	 * @param value
	 * @return
	 */
	public List load(Class entity, String attr, Object value);

	/**
	 * 根据多个属性进行加载
	 * 
	 * @param entity
	 * @param attrs
	 * @param values
	 * @return
	 */
	public List load(Class entity, String[] attrs, Object[] values);

	/**
	 * 根据给定属性读取对象
	 * 
	 * @param entity
	 * @return List
	 */
	public List load(Class entity, Map parameterMap);

	/**
	 * 加载
	 * 
	 * @param entity
	 * @return
	 */
	public List loadAll(Class entity);

	/**
	 * 加载并实例化
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public Entity get(Class clazz, Serializable id);

	/**
	 * 加载并实例化
	 * 
	 * @param entityName
	 * @param id
	 * @return
	 */
	public Entity get(String entityName, Serializable id);

	/**
	 * 搜索
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
	 * 删除
	 * 
	 * @param entity
	 */
	public void remove(Object entity);

	/**
	 * 批量删除
	 * 
	 * @param enities
	 */
	public void remove(Collection enities);

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

	/**
	 * 批量更新对象
	 * 
	 * @param entityClass
	 * @param attr
	 * @param values
	 * @param updateParams
	 * @return
	 */
	public int update(Class entityClass, String attr, Object[] values, Map updateParams);

	/**
	 * 批量更新对象
	 * 
	 * @param entityClass
	 * @param attr
	 * @param values
	 * @param argumentName
	 * @param argumentValue
	 * @return
	 */
	int update(Class entityClass, String attr, Object[] values, String[] argumentName,
			Object[] argumentValue);

	/**
	 * 保存或批量保存.
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(Object obj);

	/**
	 * 指定实体名称,保存或批量保存.
	 * 
	 * @param entityName
	 * @param entity
	 */
	public void saveOrUpdate(String entityName, Object obj);

	/**
	 * 在当前会话中清除现有对象
	 * 
	 * @param entity
	 */
	public void evict(Object entity);

	/**
	 * 刷新对象
	 * 
	 * @param entity
	 */
	public void refresh(Object entity);

	/**
	 * 强制初始化
	 * 
	 * @param entity
	 */
	public void initialize(Object entity);

	/**
	 * 检查是否存在
	 * 
	 * @param entity
	 * @param keyName
	 * @param value
	 * @return
	 */
	public boolean exist(Class entity, String keyName, Object value);

	/**
	 * 检查是否存在
	 * 
	 * @param entityName
	 * @param attr
	 * @param value
	 * @return
	 */
	public boolean exist(String entityName, String attr, Object value);

	/**
	 * 检查是否存在
	 * 
	 * @param entity
	 * @param attrs
	 * @param values
	 * @return
	 */
	public boolean exist(Class entity, String[] attrs, Object[] values);

	/**
	 * 计数
	 * 
	 * @param entity
	 * @param keyName
	 * @param value
	 * @return
	 */
	public int count(Class entity, String keyName, Object value);

	/**
	 * 计数
	 * 
	 * @param entityName
	 * @param keyName
	 * @param value
	 * @return
	 */
	public int count(String entityName, String keyName, Object value);

	/**
	 * 计数
	 * 
	 * @param entityClass
	 * @param attrs
	 * @param values
	 * @param countAttr
	 * @return
	 */
	public int count(Class entityClass, String[] attrs, Object[] values, String countAttr);

	/**
	 * 是否重复
	 * 
	 * @param clazz
	 * @param id
	 * @param codeName
	 * @param codeValue
	 * @return
	 */
	boolean duplicate(Class clazz, Long id, String codeName, Object codeValue);

	/**
	 * 是否id重复
	 * 
	 * @param entityName
	 * @param params
	 * @return
	 */
	boolean duplicate(String entityName, Long id, Map params);

	public void setEntityDao(EntityDao entityDao);

	public EntityDao getEntityDao();

	/**
	 * 设置模型构建者
	 * 
	 * @param builder
	 */
	public void setModelBuilder(ModelBuilder builder);
}