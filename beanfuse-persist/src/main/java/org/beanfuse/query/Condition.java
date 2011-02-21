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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 查询条件 使用例子如下
 * <p>
 * <blockquote>
 * 
 * <pre>
 *      new Condition(&quot;std.id=?&quot;,new Long(2));
 *      或者 Condition(&quot;std.id=:std_id&quot;,new Long(2));
 *      ?绑定单值.命名参数允许绑定多值.但是只能由字母,数组和下划线组成
 *      一组condition只能采取上面一种形式
 * </pre>
 * 
 * </blockquote>
 * <p>
 * 
 * @author chaostone
 * 
 */
public class Condition {

	private String content;

	private List values = new ArrayList(1);

	public Condition() {
		super();
	}

	public Condition(final String content) {
		this.content = content;
	}

	public Condition(final String content, Object param1) {
		this.content = content;
		values.add(param1);
	}

	public Condition(final String content, Object param1, Object param2) {
		this(content, param1, param2, null);
	}

	public Condition(final String content, Object param1, Object param2, Object param3) {
		this.content = content;
		if (null != param1) {
			values.add(param1);
		}
		if (null != param2) {
			values.add(param2);
		}
		if (null != param3) {
			values.add(param3);
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public List getValues() {
		return values;
	}

	public void setValues(final List values) {
		this.values = values;
	}

	public boolean isNamed() {
		return !StringUtils.contains(content, "?");
	}

	/**
	 * 得到查询条件中所有的命名参数.
	 * 
	 * @return
	 */
	public List getNamedParams() {
		if (!StringUtils.contains(content, ":")) {
			return Collections.EMPTY_LIST;
		}
		final List params = new ArrayList();
		int index = 0;
		do {
			final int colonIndex = content.indexOf(':', index);
			if (-1 == colonIndex) {
				break;
			}
			index = colonIndex + 1;
			while (index < content.length()) {
				final char c = content.charAt(index);
				if (isValidIdentifierStarter(c)) {
					index++;
				} else {
					break;
				}
			}
			final String paramName = content.substring(colonIndex + 1, index);
			if (!params.contains(paramName)) {
				params.add(paramName);
			}
		} while (index < content.length());
		return params;
	}

	public boolean isValidIdentifierStarter(final char ch) {
		return (('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z') || (ch == '_') || ('0' <= ch && ch <= '9'));
	}

	public Condition addValue(final Object value) {
		getValues().add(value);
		return this;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		final StringBuilder str = new StringBuilder(content).append(" ");
		for (final Iterator iter = values.iterator(); iter.hasNext();) {
			str.append(iter.next());
		}
		return str.toString();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(final Object obj) {
		if (null == getContent() || !(obj instanceof Condition)) {
			return false;
		} else {
			return getContent().equals(((Condition) obj).getContent());
		}
	}

	public int hashCode() {
		if (null == content) {
			return 0;
		} else {
			return content.hashCode();
		}
	}

	// 以下是几个方便的方法
	public static Condition eq(final String content, final Number value) {
		return new Condition(content + " = " + value);
	}

	public static Condition eq(final String content, final String value) {
		return new Condition(content + " = '" + value + "'");
	}

	public static Condition le(final String content, final Number value) {
		return new Condition(content + " <= " + value);
	}

	public static Condition ge(final String content, final Number value) {
		return new Condition(content + " >= " + value);
	}

	public static Condition ne(final String content, final Number value) {
		return new Condition(content + " <> " + value);
	}

	public static Condition like(final String content, final String value) {
		return new Condition(content + " like '%" + value + "%'");
	}
}
