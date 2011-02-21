package org.beanfuse.persist.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.collection.page.Page;
import org.beanfuse.collection.page.PageLimit;
import org.beanfuse.entity.Model;
import org.beanfuse.entity.ModelBuilder;
import org.beanfuse.model.Entity;
import org.beanfuse.model.LongIdEntity;
import org.beanfuse.persist.EntityDao;
import org.beanfuse.persist.EntityService;
import org.beanfuse.query.AbstractQuery;
import org.hibernate.Criteria;
import org.hibernate.Query;

public class EntityServiceImpl implements EntityService {

	protected EntityDao entityDao;

	protected ModelBuilder modelBuilder;

	public Entity load(Class clazz, Serializable id) {
		return entityDao.load(clazz, id);
	}

	public List loadAll(Class entity) {
		return entityDao.loadAll(entity);
	}

	public Entity get(Class clazz, Serializable id) {
		return entityDao.get(clazz, id);
	}

	public Entity get(String entityName, Serializable id) {
		return entityDao.get(entityName, id);
	}

	public List load(Class entity, String attr, Collection values) {
		return entityDao.load(entity, attr, values.toArray());
	}

	public List load(Class entity, String attr, Object value) {
		return entityDao.load(entity, attr, new Object[] { value });
	}

	public List load(Class entity, String attr, Object[] values) {
		return entityDao.load(entity, attr, values);
	}

	public List load(String entityName, String attr, Object[] values) {
		return entityDao.load(entityName, attr, values);
	}

	public List load(Class entity, String[] attrs, Object[] values) {
		Map params = new HashMap();
		for (int i = 0; i < attrs.length; i++) {
			params.put(attrs[i], values[i]);
		}
		return load(entity, params);
	}

	/**
	 * @param entity
	 * @param parameterMap
	 * @return
	 */
	public List load(Class entity, final Map parameterMap) {
		if (entity == null || parameterMap == null || parameterMap.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		String entityName = entity.getName();
		StringBuilder hql = new StringBuilder();
		hql.append("select entity from ").append(entityName).append(" as entity ")
				.append(" where ");

		Set keySet = parameterMap.keySet();
		Map m = new HashMap(keySet.size());
		// 变量编号
		int i = 0;
		for (Iterator ite = keySet.iterator(); ite.hasNext();) {
			String keyName = (String) ite.next();
			if (StringUtils.isEmpty(keyName)) {
				return null;
			}
			i++;
			Object keyValue = parameterMap.get(keyName);

			String[] tempName = StringUtils.split(keyName, "\\.");
			String name = tempName[tempName.length - 1] + i;
			m.put(name, keyValue);

			if (keyValue != null
					&& (keyValue.getClass().isArray() || keyValue instanceof Collection)) {
				hql.append("entity.").append(keyName).append(" in (:").append(name)
						.append(") and ");
			} else {
				hql.append("entity.").append(keyName).append(" = :").append(name).append(" and ");
			}
		}
		hql.append(" (1=1) ");
		return entityDao.searchHQLQuery(hql.toString(), m);
	}

	public List search(AbstractQuery query) {
		return entityDao.search(query);
	}

	public Page paginateCriteria(Criteria criteria, PageLimit limit) {
		return entityDao.paginateCriteria(criteria, limit);
	}

	public Page paginateHQLQuery(String hql, Map params, PageLimit limit) {
		return entityDao.paginateHQLQuery(hql, params, limit);
	}

	public Page paginateNamedQuery(String queryName, Map params, PageLimit limit) {
		return entityDao.paginateNamedQuery(queryName, params, limit);
	}

	public Page paginateQuery(Query query, Map params, PageLimit limit) {
		return entityDao.paginateQuery(query, params, limit);
	}

	public List searchHQLQuery(String hql, Map params, boolean cacheable) {
		return entityDao.searchHQLQuery(hql, params, cacheable);
	}

	public List searchHQLQuery(String hql, Map params) {
		return entityDao.searchHQLQuery(hql, params);
	}

	public List searchHQLQuery(String hql, Object[] params) {
		return entityDao.searchHQLQuery(hql, params);
	}

	public List searchHQLQuery(String hql) {
		return entityDao.searchHQLQuery(hql);
	}

	public List searchNamedQuery(String queryName, Map params, boolean cacheable) {
		return entityDao.searchNamedQuery(queryName, params, cacheable);
	}

	public List searchNamedQuery(String queryName, Map params) {
		return entityDao.searchNamedQuery(queryName, params);
	}

	public List searchNamedQuery(String queryName, Object[] params) {
		return entityDao.searchNamedQuery(queryName, params);
	}

	public int update(Class entityClass, String attr, Object[] values, String[] argumentName,
			Object[] argumentValue) {
		if (null == values || values.length == 0) {
			return 0;
		}
		Map updateParams = new HashMap();
		for (int i = 0; i < argumentValue.length; i++) {
			updateParams.put(argumentName[i], argumentValue[i]);
		}
		return update(entityClass, attr, values, updateParams);

	}

	public int update(Class entityClass, String attr, Object[] values, Map updateParams) {
		if (null == values || values.length == 0 || updateParams.isEmpty()) {
			return 0;
		}
		String entityName = entityClass.getName();
		StringBuilder hql = new StringBuilder();
		hql.append("update ").append(entityName).append(" set ");
		Set parameterNameSet = updateParams.keySet();
		for (Iterator ite = parameterNameSet.iterator(); ite.hasNext();) {
			String parameterName = (String) ite.next();
			if (null == parameterName) {
				continue;
			}
			hql.append(parameterName).append(" = ").append(":").append(
					StringUtils.replaceChars(parameterName, '.', '_')).append(",");
		}
		hql.deleteCharAt(hql.length() - 1);

		hql.append(" where ").append(attr).append(" in (:ids)");
		updateParams.put("ids", values);
		return entityDao.executeUpdateHql(hql.toString(), updateParams);
	}

	public void remove(Object entity) {
		entityDao.remove(entity);
	}

	public void remove(Collection entities) {
		if (null != entities && !entities.isEmpty()) {
			entityDao.remove(entities);
		}
	}

	public boolean remove(Class entityClass, Map parameterMap) {
		return entityDao.remove(entityClass, parameterMap);
	}

	public boolean remove(Class entityClass, String attr, Collection values) {
		return entityDao.remove(entityClass, attr, values);
	}

	public boolean remove(Class entityClass, String attr, Object[] values) {
		return entityDao.remove(entityClass, attr, values);
	}

	public void saveOrUpdate(Collection entities) {
		if (null != entities && !entities.isEmpty()) {
			entityDao.saveOrUpdate(entities);
		}
	}

	public void saveOrUpdate(Object entity) {
		if (entity != null) {
			entityDao.saveOrUpdate(entity);
		}
	}

	public void saveOrUpdate(String entityName, Collection entities) {
		entityDao.saveOrUpdate(entityName, entities);
	}

	public void saveOrUpdate(String entityName, Object entity) {
		entityDao.saveOrUpdate(entityName, entity);
	}

	public int count(String entityName, String keyName, Object value) {
		String hql = "select count(*) from " + entityName + " where " + keyName + "=:value";
		Map params = new HashMap();
		params.put("value", value);
		List rs = entityDao.searchHQLQuery(hql, params);
		if (rs.isEmpty()) {
			return 0;
		} else {
			return ((Number) rs.get(0)).intValue();
		}
	}

	public int count(Class entityClass, String keyName, Object value) {
		return count(entityClass.getName(), keyName, value);
	}

	public int count(Class entityClass, String[] attrs, Object[] values, String countAttr) {
		String entityName = entityClass.getName();
		StringBuilder hql = new StringBuilder();
		if (StringUtils.isNotEmpty(countAttr)) {
			hql.append("select count(distinct ").append(countAttr).append(") from ");
		} else {
			hql.append("select count(*) from ");
		}
		hql.append(entityName).append(" as entity where ");
		Map params = new HashMap();
		for (int i = 0; i < attrs.length; i++) {
			String keyName = (String) attrs[i];
			if (StringUtils.isEmpty(keyName)) {
				continue;
			}
			Object keyValue = values[i];
			params.put(keyName, keyValue);
			String[] tempName = StringUtils.split(attrs[i], "\\.");
			attrs[i] = tempName[tempName.length - 1] + i;
			if (keyValue != null
					&& (keyValue.getClass().isArray() || keyValue instanceof Collection)) {
				hql.append("entity.").append(keyName).append(" in (:").append(attrs[i]).append(
						") and ");
			} else {
				hql.append("entity.").append(keyName).append(" = :").append(attrs[i]).append(
						" and ");
			}
		}
		hql.append(" (1=1) ");
		return ((Number) entityDao.searchHQLQuery(hql.toString(), params).get(0)).intValue();
	}

	public boolean exist(Class entityClass, String attr, Object value) {
		return count(entityClass, attr, value) > 0;
	}

	public boolean exist(String entityName, String attr, Object value) {
		return count(entityName, attr, value) > 0;
	}

	public boolean exist(Class entity, String[] attrs, Object[] values) {
		return (count(entity, attrs, values, null) > 0);
	}

	/**
	 * 检查持久化对象是否存在
	 * 
	 * @param entityName
	 * @param keyName
	 * @param id
	 * @return boolean(是否存在) 如果entityId为空或者有不一样的entity存在则认为存在。
	 */
	public boolean duplicate(Class clazz, Long id, String codeName, Object codeValue) {
		if (null != codeValue && StringUtils.isNotEmpty(codeValue.toString())) {
			List list = entityDao.load(clazz, codeName, new Object[] { codeValue });
			if (list != null && !list.isEmpty()) {
				if (id == null) {
					return true;
				} else {
					for (Iterator it = list.iterator(); it.hasNext();) {
						Entity info = (Entity) it.next();
						if (!info.getEntityId().equals(id)) {
							return true;
						}
					}
					return false;
				}

			}
		}
		return false;
	}

	public void initialize(Object entity) {
		entityDao.initialize(entity);
	}

	public void refresh(Object entity) {
		entityDao.refresh(entity);
	}

	public void evict(Object entity) {
		entityDao.evict(entity);
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public boolean duplicate(String entityName, Long id, Map params) {
		StringBuilder b = new StringBuilder("from ");
		b.append(entityName).append(" where (1=1)");
		Map paramsMap = new HashMap();
		int i = 0;
		for (Iterator iterator = params.keySet().iterator(); iterator.hasNext(); i++) {
			String key = (String) iterator.next();
			b.append(" and ").append(key).append('=').append(":param" + i);
			paramsMap.put("param" + i, params.get(key));
		}
		List list = entityDao.searchHQLQuery(b.toString(), paramsMap);
		if (!list.isEmpty()) {
			if (null == id) {
				return false;
			} else {
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					LongIdEntity one = (LongIdEntity) iter.next();
					if (!one.getId().equals(id)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	protected Model getModel() {
		return modelBuilder.getModel();
	}

	protected ModelBuilder getModelBuilder() {
		return modelBuilder;
	}

	public void setModelBuilder(ModelBuilder modelBuilder) {
		this.modelBuilder = modelBuilder;
	}

}
