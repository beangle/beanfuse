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
 * chaostone             2006-4-18            Created
 *  
 ********************************************************************************/
package org.beanfuse.transfer.exporter;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.beanfuse.text.TextResource;

/**
 * 缺省和简单的属性提取类
 * 
 * @author chaostone
 * 
 */
public class DefaultPropertyExtractor implements PropertyExtractor {

	protected TextResource textResource = null;

	public DefaultPropertyExtractor() {
		super();
	}

	public DefaultPropertyExtractor(TextResource textResource) {
		setTextResource(textResource);
	}

	protected Object extract(Object target, String property) throws Exception {
		String[] subProperties = StringUtils.split(property, '.');
		if (subProperties.length >= 1) {
			StringBuilder passedProperty = new StringBuilder(subProperties[0]);
			for (int i = 0; i < subProperties.length - 1; i++) {
				if (null != PropertyUtils.getProperty(target, passedProperty.toString()))
					passedProperty.append(".").append(subProperties[i + 1]);
				else
					return "";
			}
		}
		Object value = PropertyUtils.getProperty(target, property);
		if (value instanceof Boolean) {
			if (null == textResource) {
				return value;
			} else {
				if (Boolean.TRUE.equals(value))
					return getText("yes");
				else
					return getText("no");
			}
		} else {
			return value;
		}
	}

	public Object getPropertyValue(Object target, String property) throws Exception {
		return extract(target, property);
	}

	public String getPropertyIn(Collection collection, String property) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (null != collection) {
			for (Iterator iter = collection.iterator(); iter.hasNext();) {
				Object one = (Object) iter.next();
				sb.append(extract(one, property)).append(",");
			}
		}
		// 删除逗号
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	protected String getText(String key) {
		return textResource.getText(key, key);
	}

	public TextResource getTextResource() {
		return textResource;
	}

	public void setTextResource(TextResource textResource) {
		this.textResource = textResource;
	}
}
