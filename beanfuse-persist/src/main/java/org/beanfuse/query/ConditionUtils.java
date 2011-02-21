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
 * chaostone             2006-10-11            Created
 *  
 ********************************************************************************/
package org.beanfuse.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.beanfuse.model.Component;
import org.beanfuse.model.Entity;
import org.beanfuse.model.predicates.ValidEntityKeyPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 条件提取辅助类
 * 
 * @author chaostone
 * 
 */
public final class ConditionUtils {
	private static final Logger logger = LoggerFactory.getLogger(ConditionUtils.class);

	private ConditionUtils() {
		super();
	}

	public static String toQueryString(final List conditions) {
		if (null == conditions || conditions.isEmpty()) {
			return "";
		}
		final StringBuilder buf = new StringBuilder("");
		for (final Iterator iter = conditions.iterator(); iter.hasNext();) {
			final Condition conditioin = (Condition) iter.next();
			buf.append('(').append(conditioin.getContent()).append(')');
			if (iter.hasNext()) {
				buf.append(" and ");
			}
		}
		return buf.toString();
	}

	/**
	 * 提取对象中的条件<br>
	 * 提取的属性仅限"平面"属性(允许包括component)<br>
	 * 过滤掉属性:null,或者空Collection
	 * 
	 * @param alias
	 * @param entity
	 * @param mode
	 * @return
	 */
	public static List extractConditions(String alias, final Entity entity) {
		if (null == entity) {
			return Collections.EMPTY_LIST;
		}
		final List conditions = new ArrayList();

		StringBuilder aliasBuilder = new StringBuilder(alias == null ? "" : alias);
		if (aliasBuilder.length() > 0 && !alias.endsWith(".")) {
			aliasBuilder.append(".");
		}
		String attr = "";
		try {
			final Map beanMap = PropertyUtils.describe(entity);
			for (final Iterator iter = beanMap.keySet().iterator(); iter.hasNext();) {
				attr = (String) iter.next();
				// 条件描述的应该是属性
				if (!PropertyUtils.isWriteable(entity, attr)) {
					continue;
				}
				final Object value = PropertyUtils.getProperty(entity, attr);
				if (null == value) {
					continue;
				}
				if (!(value instanceof Collection)) {
					addAttrCondition(conditions, alias + attr, value);
				}
			}
		} catch (Exception e) {
			logger.debug("error occur in extractConditions for  bean {} with attr named {}",
					entity, attr);
		}
		return conditions;
	}

	/**
	 * 获得条件的绑定参数映射
	 * 
	 * @param query
	 * @param conditions
	 */
	public static Map getParamMap(final List conditions) {
		final Map params = new HashMap();
		for (final Iterator iter = conditions.iterator(); iter.hasNext();) {
			final Condition condition = (Condition) iter.next();
			params.putAll(getParamMap(condition));
		}
		return params;
	}

	/**
	 * 获得条件的绑定参数映射
	 * 
	 * @param query
	 * @param conditions
	 */
	public static Map getParamMap(final Condition condition) {
		final Map params = new HashMap();
		if (!StringUtils.contains(condition.getContent(), "?")) {
			final List paramNames = condition.getNamedParams();
			if (paramNames.size() > condition.getValues().size()) {
				throw new RuntimeException("condition params not setted [" + condition.getContent()
						+ "] with value:" + condition.getValues());
			}
			for (int i = 0; i < paramNames.size(); i++) {
				params.put((String) paramNames.get(i), condition.getValues().get(i));
			}
		}
		return params;
	}

	/**
	 * 为extractConditions使用的私有方法<br>
	 * 
	 * @param conditions
	 * @param name
	 * @param value
	 * @param mode
	 */
	private static void addAttrCondition(final List conditions, final String name, Object value) {
		if (value instanceof String) {
			if (StringUtils.isBlank((String) value)) {
				return;
			}
			StringBuilder content = new StringBuilder(name);
			content.append(" like :").append(name.replace('.', '_'));
			conditions.add(new Condition(content.toString(), "%" + value + "%"));
		} else if (value instanceof Component) {
			conditions.addAll(extractComponent(name, (Component) value));
			return;
		} else if (value instanceof Entity) {
			try {
				final String key = ((Entity) value).key();
				Object property = PropertyUtils.getProperty(value, key);
				if (ValidEntityKeyPredicate.getInstance().evaluate(property)) {
					StringBuilder content = new StringBuilder(name);
					content.append('.').append(key).append(" = :").append(name.replace('.', '_'))
							.append('_').append(key);
					conditions.add(new Condition(content.toString(), property));
				}
			} catch (Exception e) {
				logger.warn("getProperty " + value + "error", e);
			}
		} else {
			conditions.add(new Condition(name + " = :" + name.replace('.', '_'), value));
		}
	}

	private static List extractComponent(final String prefix, final Component component) {
		if (null == component) {
			return Collections.EMPTY_LIST;
		}
		final List conditions = new ArrayList();
		String attr = "";
		try {
			final Map beanMap = PropertyUtils.describe(component);
			for (final Iterator iter = beanMap.keySet().iterator(); iter.hasNext();) {
				attr = (String) iter.next();
				if ("class".equals(attr)) {
					continue;
				}
				if (!PropertyUtils.isWriteable(component, attr)) {
					continue;
				}
				final Object value = PropertyUtils.getProperty(component, attr);
				if (value == null) {
					continue;
				} else if (value instanceof Collection) {
					if (((Collection) value).isEmpty()) {
						continue;
					}
				} else {
					addAttrCondition(conditions, prefix + "." + attr, value);
				}
			}

		} catch (Exception e) {
			logger.warn("error occur in extractComponent of component:" + component
					+ "with attr named :" + attr);
		}
		return conditions;
	}

}
