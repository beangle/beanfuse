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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Iterator;

import org.testng.annotations.Test;

@Test
public class ConditionTest {

	public void testGetNamedParams() {
		Condition condition = new Condition("std.id =:stAd_id1 and std.name like :name");
		for (Iterator iter = condition.getNamedParams().iterator(); iter.hasNext();) {
			System.out.println(iter.next());
		}
	}

	public void testVarArgs() {
		Condition c = new Condition("entity.code =:code  entity.id in (:ids)", "aa",
				new Long[] { new Long(1) });
		assertEquals(2, ConditionUtils.getParamMap(c).size());

		Condition c1 = new Condition("entity.id in (:ids)", new Long[] { new Long(1), new Long(2) });
		assertEquals(1, ConditionUtils.getParamMap(c1).size());
		assertTrue((ConditionUtils.getParamMap(c1).get("ids").getClass().isArray()));
	}

}
