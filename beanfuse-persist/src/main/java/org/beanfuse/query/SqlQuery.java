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
 * chaostone             2006-12-19            Created
 *  
 ********************************************************************************/
package org.beanfuse.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.beanfuse.collection.Order;

/**
 * sql查询
 * 
 * @author chaostone
 * 
 */
public class SqlQuery extends AbstractQuery {

	public static final String INNER_JOIN = " left join ";

	public static final String OUTER_JOIN = " outer join ";

	public static final String LEFT_OUTER_JOIN = " left outer join ";

	public static final String RIGHT_OUTER_JOIN = " right outer join ";

	protected String select;

	protected String from;

	protected List conditions = new ArrayList();

	protected List orders = new ArrayList();

	protected List groups = new ArrayList();

	public SqlQuery() {
		super();
	}

	public SqlQuery(final String queryStr) {
		super();
		this.queryStr = queryStr;
	}

	public SqlQuery add(final Condition condition) {
		conditions.add(condition);
		return this;
	}

	/**
	 * 添加一组条件<br>
	 * query中不能添加条件集合作为一个条件,因此这里命名没有采用有区别性的addAll
	 * 
	 * @author
	 * @param cons
	 * @return
	 */
	public SqlQuery add(final Collection cons) {
		conditions.addAll(cons);
		return this;
	}

	public SqlQuery addOrder(final Order order) {
		if (null != order) {
			this.orders.add(order);
		}
		return this;
	}

	public SqlQuery addOrder(final List orders) {
		if (null != orders) {
			this.orders.addAll(orders);
		}
		return this;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(final String select) {
		if (null == select) {
			this.select = select;
		} else {
			if (StringUtils.contains(select.toLowerCase(), "select")) {
				this.select = select;
			} else {
				this.select = "select " + select;
			}
		}
	}

	public List getConditions() {
		return conditions;
	}

	public void setConditions(final List conditions) {
		this.conditions = conditions;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(final String from) {
		if (null == from) {
			this.from = from;
		} else {
			if (StringUtils.contains(from.toLowerCase(), "from")) {
				this.from = from;
			} else {
				this.from = " from " + from;
			}
		}
	}

	public List getOrders() {
		return orders;
	}

	public void setOrders(final List orders) {
		this.orders = orders;
	}

	public List getGroups() {
		return groups;
	}

	public void setGroups(final List groups) {
		this.groups = groups;
	}

	public SqlQuery groupBy(final String what) {
		if (StringUtils.isNotEmpty(what)) {
			groups.add(what);
		}
		return this;
	}

	/**
	 * 生成查询语句（如果查询语句已经存在则不进行生成）
	 */
	public String toQueryString() {
		if (StringUtils.isNotEmpty(queryStr)) {
			return queryStr;
		} else {
			return genQueryString(true);
		}
	}

	public String toCountString() {
		if (StringUtils.isNotEmpty(countStr)) {
			return countStr;
		} else {
			return "select count(*) from (" + genQueryString(false) + ")";
		}
	}

	protected String genQueryString(final boolean hasOrder) {
		if (null == from) {
			return queryStr;
		}
		final StringBuilder buf = new StringBuilder(50);
		buf.append((select == null) ? "" : select).append(' ').append(from);
		if (!conditions.isEmpty()) {
			buf.append(" where ").append(ConditionUtils.toQueryString(conditions));
		}
		if (!groups.isEmpty()) {
			buf.append(" group by ");
			for (final Iterator iter = groups.iterator(); iter.hasNext();) {
				final String groupBy = (String) iter.next();
				buf.append(groupBy).append(',');
			}
			buf.deleteCharAt(buf.length() - 1);
		}
		if (hasOrder && !CollectionUtils.isEmpty(orders)) {
			buf.append(' ').append(Order.toSortString(orders));
		}
		return buf.toString();
	}

	public Map getParams() {
		if (null == params) {
			return ConditionUtils.getParamMap(conditions);
		} else {
			return params;
		}
	}
}
