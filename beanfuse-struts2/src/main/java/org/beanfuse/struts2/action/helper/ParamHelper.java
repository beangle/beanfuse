//$Id:ParamHelper.java 2009-1-20 下午06:56:19 chaostone Exp $
/*
 * Copyright c 2005-2009.
 * 
 * Licensed under the GPL License, Version 2.0 (the "License")
 * http://www.gnu.org/licenses/gpl-2.0.html
 * 
 */
package org.beanfuse.struts2.action.helper;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.lang.StringUtils;
import org.beanfuse.bean.converters.DateConverter;
import org.beanfuse.bean.converters.SqlDateConverter;

import com.opensymphony.xwork2.ActionContext;

public class ParamHelper {

	static {
		registerConverter();
	}

	public static void registerConverter() {
		ConvertUtils.register(new SqlDateConverter(), java.sql.Date.class);
		ConvertUtils.register(new DateConverter(), java.util.Date.class);
		ConvertUtils.register(new BooleanConverter(null), Boolean.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new LongConverter(null), Long.class);
		ConvertUtils.register(new FloatConverter(null), Float.class);
		ConvertUtils.register(new DoubleConverter(null), Double.class);
	}
	
	public static void put(String key, Object value) {
		ActionContext.getContext().getContextMap().put(key, value);
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
	public static Map getParams(String prefix, String exclusiveAttrNames) {
		return getParamsMap(prefix, exclusiveAttrNames, true);
	}

	public static Map getParams(String prefix) {
		return getParamsMap(prefix, null, true);
	}

	public static String[] getValues(String attr) {
		return (String[]) ActionContext.getContext().getParameters().get(attr);
	}

	/**
	 * get parameter named attr
	 * 
	 * @param attr
	 * @return single value or multivalue joined with comma
	 */
	public static String get(String attr) {
		String[] values = (String[]) ActionContext.getContext().getParameters().get(attr);
		if (null != values) {
			if (values.length == 1) {
				return values[0];
			} else {
				return StringUtils.join(values, ',');
			}
		} else {
			return null;
		}
	}

	public static Object get(Class clazz, String name) {
		String strValue = get(name);
		if (StringUtils.isNotBlank(strValue)) {
			return ConvertUtils.convert(strValue, clazz);
		} else {
			return null;
		}
	}

	public static Boolean getBoolean(String name) {
		return (Boolean) get(Boolean.class, name);
	}

	public static boolean getBool(String name) {
		String strValue = get(name);
		if (StringUtils.isEmpty(strValue))
			return false;
		else
			return ((Boolean) ConvertUtils.convert(strValue, Boolean.class)).booleanValue();
	}

	public static java.sql.Date getDate(String name) {
		return (java.sql.Date) get(java.sql.Date.class, name);
	}

	public static Date getTime(String name) {
		return (Date) get(Date.class, name);
	}

	public static Float getFloat(String name) {
		return (Float) get(Float.class, name);
	}

	public static Integer getInteger(String name) {
		return (Integer) get(Integer.class, name);
	}

	public static Long getLong(String name) {
		return (Long) get(Long.class, name);
	}

	public static Map getParamsMap(String prefix, String exclusiveAttrNames, boolean stripPrefix) {
		HashSet excludes = new HashSet();
		if (StringUtils.isNotEmpty(exclusiveAttrNames)) {
			String[] exclusiveAttrs = StringUtils.split(exclusiveAttrNames, ",");
			for (int i = 0; i < exclusiveAttrs.length; i++) {
				excludes.add(exclusiveAttrs[i]);
			}
		}
		Map params = new HashMap();
		Map parameters = ActionContext.getContext().getParameters();
		for (Iterator iterator = parameters.keySet().iterator(); iterator.hasNext();) {
			String attr = (String) iterator.next();
			if ((attr.indexOf(prefix + ".") == 0) && (!excludes.contains(attr))) {
				String[] val = (String[]) parameters.get(attr);
				if (null != val) {
					if (1 == val.length) {
						params.put((stripPrefix ? attr.substring(prefix.length() + 1) : attr),
								val[0].trim());
					} else {
						params.put((stripPrefix ? attr.substring(prefix.length() + 1) : attr), val);
					}
				}
			}
		}
		return params;
	}
}