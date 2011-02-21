//$Id: Action.java,v 1.1 2007-2-12 下午10:25:36 chaostone Exp $
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
 *chaostone      2007-2-12         Created
 *  
 ********************************************************************************/

package org.beanfuse.struts2.route;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * web路由的系统结点
 * 
 * @author chaostone
 * 
 */
public class Action {

	private String name;

	private String method;

	private Class clazz;

	private Map params;

	public Action() {
		super();
	}

	public Action(String method) {
		super();
		this.method = method;
	}

	public Action(Class clazz, String method) {
		this.clazz = clazz;
		this.method = method;
	}

	public Action(Object ctlObj, String method) {
		this.clazz = ctlObj.getClass();
		this.method = method;
	}

	public Action(Class clazz, String method, String params) {
		this.clazz = clazz;
		this.method = method;
		addParams(params);
	}

	public Action(String name, String method) {
		this.name = name;
		this.method = method;
	}

	public Action(String name, String method, String params) {
		this.name = name;
		this.method = method;
		addParams(params);
	}

	public String getNamespace() {
		String namespace = null;
		if (null != name) {
			namespace = name.substring(0, StringUtils.lastIndexOf(name, '/') + 1);
		}
		return namespace;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	public void addParam(String key, Object value) {
		if (null == params) {
			params = new HashMap();
		}
		params.put(key, value);
	}

	public void addParams(Map paramMap) {
		if (null == params) {
			params = new HashMap();
		}
		params.putAll(paramMap);
	}

	public void addParams(String paramStr) {
		if (null == params) {
			params = new HashMap();
		}
		if (StringUtils.isNotEmpty(paramStr)) {
			String[] paramPairs = StringUtils.split(paramStr, "&");
			for (int i = 0; i < paramPairs.length; i++) {
				String key = StringUtils.substringBefore(paramPairs[i], "=");
				String value = StringUtils.substringAfter(paramPairs[i], "=");
				if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
					params.put(key, value);
				}
			}
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("params", this.params).append("controller",
				this.name).append("method", this.method).toString();
	}

	public String getUri() {
		StringBuilder buf = new StringBuilder();
		if (null != getName()) {
			// all URI should start with /
			if (!getName().startsWith("/")) {
				buf.append("/");
			}
			buf.append(getName());
		}
		if (StringUtils.isNotEmpty(getMethod())) {
			buf.append("?method=").append(getMethod()).toString();
		}
		if (null != getParams() && getParams().size() > 0) {
			URLCodec codec = new URLCodec();
			for (Iterator iter = getParams().keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				String value = (String) getParams().get(key);
				try {
					buf.append("&").append(key).append("=").append(codec.encode(value, "UTF-8"));
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		return buf.toString();
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

}
