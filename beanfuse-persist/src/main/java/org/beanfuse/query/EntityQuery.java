/*
 *
 * Copyright c 2005-2009.
 * 
 * Licensed under GNU  LESSER General Public License, Version 3.  
 * http://www.gnu.org/licenses
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2006-10-15            Created
 *  
 ********************************************************************************/
package org.beanfuse.query;

import org.apache.commons.lang.StringUtils;
import org.beanfuse.entity.Model;
import org.beanfuse.entity.types.EntityType;

/**
 * 实体类查询
 * 
 * @author chaostone
 * 
 */
public class EntityQuery extends SqlQuery {
	/** 查询实体类 */
	protected Class entityClass;

	/** 实体类别名 */
	protected String alias;

	public EntityQuery() {
		super();
	}

	public EntityQuery(final String hql) {
		super();
		this.queryStr = hql;
	}

	public EntityQuery(final String entityName, final String alias) {
		EntityType type = Model.getEntityType(entityName);
		this.entityClass = type.getEntityClass();
		this.alias = alias;
		select = "select " + alias + " ";
		from = "from " + entityName + " " + alias;
	}

	public EntityQuery(final Class entityClass, final String alias) {
		super();
		EntityType type = Model.getEntityType(entityClass.getName());
		if (null == type) {
			type = Model.getEntityType(entityClass);
		}
		this.entityClass = type.getEntityClass();
		this.alias = alias;
		select = "select " + alias + " ";
		from = "from " + type.getEntityName() + " " + alias;
	}

	public EntityQuery join(final String path, final String alias) {
		from += " join " + path + " " + alias;
		return this;
	}

	public EntityQuery join(final String joinMode, final String path, final String alias) {
		from += " " + joinMode + " join " + path + " " + alias;
		return this;
	}

	/**
	 * 形成计数查询语句，如果不能形成，则返回""
	 */
	public String toCountString() {
		if (StringUtils.isNotEmpty(countStr)) {
			return countStr;
		}
		StringBuilder countString = new StringBuilder("select count(*) ");
		// 原始查询语句
		final String genQueryStr = genQueryString(false);
		if (StringUtils.isEmpty(genQueryStr)) {
			return "";
		}
		final String lowerCaseQueryStr = genQueryStr.toLowerCase();

		if (StringUtils.contains(lowerCaseQueryStr, " group ")) {
			return "";
		}
		if (StringUtils.contains(lowerCaseQueryStr, " union ")) {
			return "";
		}

		final int indexOfFrom = lowerCaseQueryStr.indexOf("from");
		final String selectWhat = lowerCaseQueryStr.substring(0, indexOfFrom);
		final int indexOfDistinct = selectWhat.indexOf("distinct");
		// select distinct a from table;
		if (-1 != indexOfDistinct) {
			if (StringUtils.contains(selectWhat, ",")) {
				return null;
			} else {
				countString = new StringBuilder("select count(");
				countString.append(genQueryStr.substring(indexOfDistinct, indexOfFrom)).append(')');
			}
		}
		countString.append(genQueryStr.substring(indexOfFrom));
		return countString.toString();
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(final String alias) {
		this.alias = alias;
	}

	public Class getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(final Class entityClass) {
		this.entityClass = entityClass;
	}

}
