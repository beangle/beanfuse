/*
 * Copyright c 2005-2009.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
/********************************************************************************
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * chaostone             2007-1-7            Created
 *  
 ********************************************************************************/
package org.beanfuse.bean.comparators;

import java.util.Comparator;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 属性比较器。<br>
 * 升序情况下，null排在前面，降序情况下，null排在后面。
 * 
 * @author chaostone
 * 
 */
public class PropertyComparator implements Comparator {

	private String cmpWhat;

	private int index = -1;

	private boolean asc;

	private Comparator comparator;

	private StringComparator stringComparator;

	/**
	 * new OrderedBeanComparator("id") or<br>
	 * new OrderedBeanComparator("name desc"); new
	 * OrderedBeanComparator("[0].name desc");
	 * 
	 * @param cmpStr
	 */
	public PropertyComparator(final String cmpStr) {
		if (StringUtils.isEmpty(cmpStr)) {
			return;
		}

		if (StringUtils.contains(cmpStr, ',')) {
			throw new RuntimeException(
					"PropertyComparator don't suport comma based order by."
							+ " Use MultiPropertyComparator ");
		}
		cmpWhat = cmpStr.trim();
		// 处理带有[]符号的字符串
		if ('[' == cmpWhat.charAt(0)) {
			index = NumberUtils.toInt(StringUtils.substringBetween(cmpWhat,
					"[", "]"));
			cmpWhat = StringUtils.substringAfter(cmpWhat, "]");
			if ('.' == cmpWhat.charAt(0)) {
				cmpWhat = cmpWhat.substring(1);
			}
		}
		// 处理排序
		asc = true;
		if (StringUtils.contains(cmpWhat, ' ')) {
			if (StringUtils.contains(cmpWhat, " desc")) {
				asc = false;
			}
			cmpWhat = cmpWhat.substring(0, cmpWhat.indexOf(' '));
		}
		stringComparator = new CollatorStringComparator(asc);
	}

	public PropertyComparator(final String cmpWhat, final boolean asc) {
		this(cmpWhat + " " + (asc ? "" : "desc"));
	}

	public int compare(Object arg0, Object arg1) {
		Object what0 = null;
		Object what1 = null;
		// 取出属性
		if (index > -1) {
			arg0 = ((Object[]) arg0)[index];
			arg1 = ((Object[]) arg1)[index];
			if (StringUtils.isEmpty(cmpWhat)) {
				what0 = arg0;
				what1 = arg1;
			}
		}
		if (StringUtils.isNotEmpty(cmpWhat)) {
			try {
				what0 = PropertyUtils.getProperty(arg0, cmpWhat);
				what1 = PropertyUtils.getProperty(arg1, cmpWhat);
			} catch (Exception e) {
				// 可能遇到了空的中间属性
				throw new RuntimeException(e);
			}
		}

		if (what0 == null && null == what1) {
			return 0;
		}

		// 进行比较
		if (null == comparator) {
			// 进行字符串比较
			if (what0 instanceof String || what1 instanceof String) {
				return stringComparator.compare(what0, what1);
			} else {
				if (what0 == null && null != what1) {
					return asc ? -1 : 1;
				}
				if (what0 != null && null == what1) {
					return asc ? 1 : -1;
				}
				if (asc) {
					return ((Comparable) what0).compareTo(what1);
				} else {
					return ((Comparable) what1).compareTo(what0);
				}
			}
		} else {
			return comparator.compare(what0, what1);
		}
	}

	public Comparator getComparator() {
		return comparator;
	}

	public void setComparator(final Comparator comparator) {
		this.comparator = comparator;
	}

	public StringComparator getStringComparator() {
		return stringComparator;
	}

	public void setStringComparator(final StringComparator stringComparator) {
		this.stringComparator = stringComparator;
	}

	public String getCmpWhat() {
		return cmpWhat;
	}

	public boolean isAsc() {
		return asc;
	}

}
