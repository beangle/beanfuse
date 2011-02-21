//$Id: TestQueryPage.java 2008-9-11 下午10:46:37 chaostone Exp $
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
 * Name           Date          Description 
 * ============   ============  ============
 * chaostone      2008-9-11  Created
 *  
 ********************************************************************************/
package org.beanfuse.query.limit;

import java.util.Iterator;

import org.beanfuse.collection.page.PageLimit;
import org.beanfuse.query.EntityQuery;
import org.testng.annotations.Test;

public class TestQueryPage  {

	@Test
	public void testMove() throws Exception {
		EntityQuery query = new EntityQuery(TestQueryPage.class, "dd");
		query.setLimit(new PageLimit(1, 2));
		MockQueryPage page = new MockQueryPage(query);
		for (Iterator iterator = page.iterator(); iterator.hasNext();) {
			String data = (String) iterator.next();
		}
	}
}
