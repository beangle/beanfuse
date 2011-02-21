//$Id: Restriction.java,v 1.1 2007-10-13 下午01:45:19 chaostone Exp $
/*
 *
 * Copyright c 2005-2009
 * Licensed under the Apache License, Version 2.0 (the "License")
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * 
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone              2007-10-13         Created
 *  
 ********************************************************************************/

package org.beanfuse.security.restriction.model;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.beanfuse.lang.SeqStringUtil;
import org.beanfuse.model.pojo.LongIdObject;
import org.beanfuse.security.restriction.Param;
import org.beanfuse.security.restriction.ParamGroup;
import org.beanfuse.security.restriction.RestrictionHolder;

/**
 * 资源访问限制
 * 
 * @author chaostone
 * 
 */
public class Restriction extends LongIdObject implements
		org.beanfuse.security.restriction.Restriction {
	private static final long serialVersionUID = -1157873272781525823L;

	private RestrictionHolder holder;

	private ParamGroup paramGroup;

	private boolean enabled = true;

	private Map items = new HashMap();

	public RestrictionHolder getHolder() {
		return holder;
	}

	public void setHolder(RestrictionHolder holder) {
		this.holder = holder;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public ParamGroup getParamGroup() {
		return paramGroup;
	}

	public Map getItems() {
		return items;
	}

	public void setParamGroup(ParamGroup paramGroup) {
		this.paramGroup = paramGroup;
	}

	public void setItems(Map items) {
		this.items = items;
	}

	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getItem(String paramName) {
		Param param = getParamGroup().getParam(paramName);
		if (null == param) {
			return null;
		} else {
			return getItem(param);
		}
	}

	public String getItem(Param param) {
		if (null == items || items.isEmpty()) {
			return null;
		} else {
			return (String) items.get(param.getId());
		}
	}

	public void setItem(Param param, String text) {
		items.put(param.getId(), text);
	}

	public Object getValue(Param param) {
		String value = getItem(param);
		if (null == value) {
			return null;
		}
		if (ObjectUtils.equals(ALL, value)) {
			return ALL;
		}
		try {
			Constructor con = Class.forName(param.getType()).getConstructor(
					new Class[] { String.class });
			if (StringUtils.isEmpty(value)) {
				return null;
			}
			if (param.isMultiValue()) {
				Set valueSet = new HashSet();
				String[] values = StringUtils.split(value, ",");
				for (int i = 0; i < values.length; i++) {
					valueSet.add(con.newInstance(new Object[] { values[i] }));
				}
				return valueSet;
			} else {
				return con.newInstance(new Object[] { value });
			}
		} catch (Exception e) {
			throw new RuntimeException("exception with param type:" + param.getType() + " value:"
					+ value, e);
		}
	}

	public void merge(Param param, String value) {
		setItem(param, evictComma(SeqStringUtil.mergeSeq(getItem(param), value)));
	}

	public void shrink(Param param, String value) {
		setItem(param, evictComma(SeqStringUtil.subtractSeq(getItem(param), value)));
	}

	private static String evictComma(String str) {
		if (StringUtils.isEmpty(str))
			return str;
		else {
			if (str.startsWith(",") && str.endsWith(","))
				return str.substring(1, str.length() - 1);
			else if (str.startsWith(",")) {
				return str.substring(1);
			} else if (str.endsWith(",")) {
				return str.substring(0, str.length() - 1);
			} else {
				return str;
			}
		}
	}

}
