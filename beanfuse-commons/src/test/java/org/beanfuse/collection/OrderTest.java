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
package org.beanfuse.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class OrderTest {
	@Test
	public void testToString1() throws Exception {
		List sorts = Order.parse(null);
		if (sorts.isEmpty()) {
			sorts.add(new Order(" teachPlan.grade desc "));
			sorts.add(new Order(" teachPlan.major.code "));
			// sorts.add(new Order("teachPlan.stdType"));
		}
		assertEquals(Order.toSortString(sorts),
				"order by teachPlan.grade desc,teachPlan.major.code");
	}

	@Test
	public void testToString() throws Exception {
		List sorts = new ArrayList();
		sorts.add(new Order("id", false));
		sorts.add(Order.asc("name"));
		assertEquals(Order.toSortString(sorts), "order by id desc,name");
	}

	public void testParserOrder() throws Exception {
		List orders = Order.parse("std.code asc");
		for (Iterator iterator = orders.iterator(); iterator.hasNext();) {
			Order order = (Order) iterator.next();
			assertTrue(order.isAscending());
			assertEquals(order.getProperty(), "std.code");
		}
	}

	@Test
	public void testParserMutiOrder() throws Exception {
		List sorts = Order
				.parse("activity.time.year desc,activity.time.validWeeksNum,activity.time.weekId desc");
		assertEquals(sorts.size(), 3);
		Order order = (Order) sorts.get(0);
		assertEquals(order.getProperty(), "activity.time.year");
		assertFalse(order.isAscending());
		order = (Order) sorts.get(1);
		assertEquals(order.getProperty(), "activity.time.validWeeksNum");
		assertTrue(order.isAscending());
		order = (Order) sorts.get(2);
		assertEquals(order.getProperty(), "activity.time.weekId");
		assertFalse(order.isAscending());
	}

	@Test
	public void testParserComplexOrder() {
		List sorts = Order.parse("(case when ware.price is null then 0 else ware.price end) desc");
		assertEquals(sorts.size(), 1);
		Order order = (Order) sorts.get(0);
		assertEquals(order.getProperty(),
				"(case when ware.price is null then 0 else ware.price end)");
		assertFalse(order.isAscending());
	}
}
