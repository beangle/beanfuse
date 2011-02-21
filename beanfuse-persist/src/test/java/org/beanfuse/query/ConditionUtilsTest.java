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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ConditionUtilsTest {

	@BeforeClass
	protected void setUp() throws Exception {

	}

	@Test
	public void testGetParamMap() throws Exception {
		List conditions = new ArrayList();
		conditions.add(new Condition("std.id=:std_id", new Long(1)));
		Map params = ConditionUtils.getParamMap(conditions);
		for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			System.out.println(name + "->" + params.get(name));
		}
	}

	@Test
	public void testToQueryString() {
		List conditions = new ArrayList();
		conditions.add(new Condition("user.id=:user_id", new Long(1)));
		conditions.add(new Condition("user.name=:std_name", new String("name")));
		Assert.assertEquals("(user.id=:user_id) and (user.name=:std_name)", ConditionUtils
				.toQueryString(conditions));
	}

}
