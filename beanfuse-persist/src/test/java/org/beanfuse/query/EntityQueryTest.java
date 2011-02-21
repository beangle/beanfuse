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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;
public class EntityQueryTest {

	@Test
	public void testToCountString1() throws Exception {
		EntityQuery query = new EntityQuery(TestModel.class, "model");
		assertEquals("select count(*) from org.beanfuse.query.TestModel model", query
				.toCountString());
		assertEquals("select count(*) from org.beanfuse.query.TestModel model", query
				.toCountString());
		query.add(new Condition("name like :name", "testName"));
		assertTrue(query.toQueryString().endsWith("(name like :name)"));
	}

	@Test
	public void testToCountString2() throws Exception {
		EntityQuery query = new EntityQuery(
				"from Ware where price is not null order by releaseDate desc "
						+ " union all from Ware where price is null order by releaseDate desc");
		assertEquals("", query.toCountString());
	}

}
